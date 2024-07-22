package com.gammel2012.utils.providers;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;

public abstract class BaseBlockStateProvider extends BlockStateProvider {
    protected final String modid;
    public BaseBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
        this.modid = modid;
    }

    public @NotNull String getBlockName(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).toString().replace(modid + ":", "");
    }

    public void simplePaneBlock(DeferredBlock<Block> dBlock) {
        IronBarsBlock block = (IronBarsBlock) dBlock.get();

        String name = getBlockName(block);

        ResourceLocation texture_pane = modLoc("block/" + name + "_pane");
        ResourceLocation texture_edge = modLoc("block/" + name + "_edge");

        paneBlock(
                block,
                texture_pane,
                texture_edge
        );
    }

    public void singleStateBlock(DeferredBlock<Block> dBlock) {
        Block block = dBlock.get();
        String name = getBlockName(block);
        ConfiguredModel model = new ConfiguredModel(models().getExistingFile(modLoc("block/" + name)));
        simpleBlock(block, model);
    }

    public void horizontalFacingBlock(DeferredBlock<Block> dBlock) {
        Block block = dBlock.get();
        String name = getBlockName(block);
        ModelFile model = models().getExistingFile(modLoc("block/" + name));
        getVariantBuilder(block).forAllStates(
                state -> {
                    DirectionProperty dir_h = getHorizontalDirectionProperty();
                    Direction facing_h = state.getValue(dir_h);
                    return ConfiguredModel.builder()
                            .modelFile(model)
                            .rotationY(dirToYAngle(facing_h))
                            .build();
                }
        );
    }

    protected abstract DirectionProperty getHorizontalDirectionProperty();

    public int dirToYAngle(Direction dir) {
        switch (dir) {
            case NORTH:
                return 0;
            case EAST:
                return 90;
            case SOUTH:
                return 180;
            case WEST:
                return 270;
        }
        return 0;
    }

    public int dirToXAngle(Direction dir) {
        switch (dir) {
            case UP:
                return 90;
            case DOWN:
                return -90;
        }
        return 0;
    }
}
