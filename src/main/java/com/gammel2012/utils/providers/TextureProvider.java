package com.gammel2012.utils.providers;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;

import net.minecraft.Util;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

// TODO: Switch to ResourceKeys

public abstract class TextureProvider implements DataProvider {

    public static final ExistingFileHelper.ResourceType TEXTURE = new ExistingFileHelper.ResourceType(PackType.CLIENT_RESOURCES, ".png", "textures");

    // ToDo: Figure out if we can use the existing file helper to make stuff easier

    private PackOutput output;
    private String modid;
    private ExistingFileHelper existingFileHelper;

    private Map<ResourceLocation, BufferedImage> generatedTextures = new HashMap<>();

    public TextureProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        this.output = output;
        this.modid = modid;
        this.existingFileHelper = existingFileHelper;
    }

    protected void clear() {
        generatedTextures.clear();
    }

    @Override
    public CompletableFuture<?> run(CachedOutput pOutput) {
        clear();
        try {
            registerTextures();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return generateAll(pOutput);
    }

    protected abstract void registerTextures() throws IOException;

    protected CompletableFuture<?> generateAll(CachedOutput cache) {
        CompletableFuture<?>[] futures = new CompletableFuture<?>[this.generatedTextures.size()];
        int i = 0;

        for (Map.Entry<ResourceLocation, BufferedImage> entry : this.generatedTextures.entrySet()) {

            ResourceLocation rl = entry.getKey();
            BufferedImage img = entry.getValue();


            try {
                Path target = getNewResource(rl.getPath()).toPath();
                futures[i++] = saveStableImage(cache, img, target);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return CompletableFuture.allOf(futures);
    }

    static CompletableFuture<?> saveStableImage(CachedOutput pOutput, BufferedImage image, Path pPath) {
        return CompletableFuture.runAsync(() -> {
            try {
                ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
                HashingOutputStream hashingoutputstream = new HashingOutputStream(Hashing.sha1(), bytearrayoutputstream);

                try {
                    ImageIO.write(image, "png", hashingoutputstream);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                pOutput.writeIfNeeded(pPath, bytearrayoutputstream.toByteArray(), hashingoutputstream.hash());
            } catch (IOException ioexception) {
                LOGGER.error("Failed to save file to {}", pPath, ioexception);
            }
        }, Util.backgroundExecutor());
    }

    public void saveImage(String name, BufferedImage img) {
        ResourceLocation rl = ResourceLocation.fromNamespaceAndPath(this.modid, getSubfolder() + "/" + name);
        this.existingFileHelper.trackGenerated(rl, TEXTURE);
        this.generatedTextures.put(rl, img);
    }

    protected abstract String getSubfolder();

    public static Path getAssetDirectory(String namespace) throws IOException {
        Path path = Paths.get(System.getProperty("user.dir"));
        path = path.getParent().getParent();
        path = path.resolve("src/main/resources/assets/" + namespace);
        return path;
    }

    public Path getGeneratedAssetDirectory(String namespace) {
        Path path = Paths.get(System.getProperty("user.dir"));
        path = path.getParent().getParent();
        path = path.resolve("src/generated/resources/assets/" + namespace);
        return path;
    }

    public File getExistingResource(String namespace, String texture_path) throws IOException {

        if (!texture_path.endsWith(".png")) {
            texture_path += ".png";
        }

        Path asset_dir = getAssetDirectory(namespace).resolve("textures");
        Path gen_dir = getGeneratedAssetDirectory(namespace).resolve("textures");

        File asset_dir_file = asset_dir.resolve(texture_path).toFile();
        File gen_dir_file = gen_dir.resolve(texture_path).toFile();

        if (asset_dir_file.exists() && gen_dir_file.exists()) {
            throw new IOException("Duplicate files:\n" + asset_dir_file.getAbsolutePath() + "\n" + gen_dir_file.getAbsolutePath());
        }
        if (!(asset_dir_file.exists() || gen_dir_file.exists())) {
            throw new IOException("Resource " + texture_path + " doesn't exist");
        }

        if (asset_dir_file.exists()) {
            return asset_dir_file;
        } else {
            return gen_dir_file;
        }
    }

    public File getExistingResource(String texture_path) throws IOException {
        return getExistingResource(this.modid, texture_path);
    }

    public File getNewResource(String texture_path) throws IOException {
        if (!texture_path.endsWith(".png")) {
            texture_path += ".png";
        }
        File path = getGeneratedAssetDirectory(this.modid).resolve("textures").resolve(texture_path).toFile();
        return new File(path.getAbsolutePath());
    }

    public BufferedImage getImage(String image_path) throws IOException {
        return getImage(this.modid, image_path);
    }

    public BufferedImage getImage(String namespace, String image_path) throws IOException {
        File img = getExistingResource(namespace, image_path);
        return ImageIO.read(img);
    }
}
