package com.gammel2012.utils.providers;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;

public abstract class BaseBlockModelProvider extends BlockModelProvider {
    public BaseBlockModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    public @NotNull String getBlockName(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).toString().replace(modid + ":", "");
    }

    public ResourceLocation blockLoc(Block block) {
        String name = getBlockName(block);
        return modLoc("block/" + name);
    }

    public void carpetModel(DeferredBlock<Block> dBlock) {
        String name = getBlockName(dBlock.get());
        carpet(name, blockLoc(dBlock.get())).renderType("minecraft:cutout");
    }

    public void copyModel(DeferredBlock<Block> new_block, DeferredBlock<Block> block_to_copy) {
        String block_name = getBlockName(new_block.get());

        ResourceLocation new_block_location = blockLoc(new_block.get());
        ResourceLocation copied_block_location = blockLoc(block_to_copy.get());
        String copy = getBlockName(block_to_copy.get());

        getBuilder("block/" + block_name)
                .parent(getExistingFile(copied_block_location))
                .texture("block", new_block_location)
                .texture("particle", new_block_location);
    }

    public void paneBlock(DeferredBlock<Block> dBlock) {
        Block pane = dBlock.get();
        String name = getBlockName(pane);

        ResourceLocation texture_pane = modLoc("block/" + name + "_pane");
        ResourceLocation texture_edge = modLoc("block/" + name + "_edge");

        panePost(
                name + "_panepost",
                texture_pane,
                texture_edge
        );

        paneSide(
                name + "_paneside",
                texture_pane,
                texture_edge
        );

        paneSideAlt(
                name + "_panesidealt",
                texture_pane,
                texture_edge
        );

        paneNoSide(
                name + "_panenoside",
                texture_pane
        );

        paneNoSideAlt(
                name + "_panenosidealt",
                texture_pane
        );
    }
}
