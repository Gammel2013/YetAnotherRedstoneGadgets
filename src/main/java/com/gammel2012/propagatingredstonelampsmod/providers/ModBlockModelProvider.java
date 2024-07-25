package com.gammel2012.propagatingredstonelampsmod.providers;

import com.gammel2012.propagatingredstonelampsmod.registers.ModBlocks;
import com.gammel2012.propagatingredstonelampsmod.PropagatingRedstoneLampsMod;
import com.gammel2012.utils.providers.BaseBlockModelProvider;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockModelProvider extends BaseBlockModelProvider {
    public ModBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PropagatingRedstoneLampsMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        cubeAll(
                getBlockName(ModBlocks.PURPLE_PROPAGATING_REDSTONE_LAMP_BLOCK.get()) + "_on",
                blockLoc(ModBlocks.PURPLE_PROPAGATING_REDSTONE_LAMP_BLOCK.get()).withSuffix("_on")
        );
        cubeAll(
                getBlockName(ModBlocks.PURPLE_PROPAGATING_REDSTONE_LAMP_BLOCK.get()) + "_off",
                blockLoc(ModBlocks.PURPLE_PROPAGATING_REDSTONE_LAMP_BLOCK.get()).withSuffix("_off")
        );

        cubeAll(
                getBlockName(ModBlocks.BLUE_PROPAGATING_REDSTONE_LAMP_BLOCK.get()) + "_on",
                blockLoc(ModBlocks.BLUE_PROPAGATING_REDSTONE_LAMP_BLOCK.get()).withSuffix("_on")
        );
        cubeAll(
                getBlockName(ModBlocks.BLUE_PROPAGATING_REDSTONE_LAMP_BLOCK.get()) + "_off",
                blockLoc(ModBlocks.BLUE_PROPAGATING_REDSTONE_LAMP_BLOCK.get()).withSuffix("_off")
        );

        cubeAll(
                getBlockName(ModBlocks.RED_PROPAGATING_REDSTONE_LAMP_BLOCK.get()) + "_on",
                blockLoc(ModBlocks.RED_PROPAGATING_REDSTONE_LAMP_BLOCK.get()).withSuffix("_on")
        );
        cubeAll(
                getBlockName(ModBlocks.RED_PROPAGATING_REDSTONE_LAMP_BLOCK.get()) + "_off",
                blockLoc(ModBlocks.RED_PROPAGATING_REDSTONE_LAMP_BLOCK.get()).withSuffix("_off")
        );

        registerRedstoneDividerModels();
        registerRedstoneDialModels();
    }

    private void registerRedstoneDividerModels() {

        String block_name = "block/" + getBlockName(ModBlocks.REDSTONE_DIVIDER_BLOCK.get());

        String base_name = block_name + "/base";
        BlockModelBuilder builder = getBuilder(base_name);

        builder.element()
                .from(0, 0, 0)
                .to(16, 2, 16)
                .allFaces((direction, faceBuilder) -> faceBuilder.texture("#txt"))
                .end();

        builder.texture("txt", modLoc("block/divider_slab_base"));
        builder.texture("particle", modLoc("block/divider_slab_base"));
        builder.renderType("solid");

        redstone_torch_off(block_name, "torch_1_off", 3, 0, 2);
        redstone_torch_off(block_name, "torch_2_off", 7, 0, 2);
        redstone_torch_off(block_name, "torch_4_off", 11, 0, 2);

        redstone_torch_off(block_name, "torch_round_off", 7, 0, 8);
        redstone_torch_off(block_name, "torch_output_off", 7, 2, 13);

        redstone_torch_on(block_name, "torch_1_on", 3, 0, 2);
        redstone_torch_on(block_name, "torch_2_on", 7, 0, 2);
        redstone_torch_on(block_name, "torch_4_on", 11, 0, 2);

        redstone_torch_on(block_name, "torch_round_on", 7, 0, 8);
        redstone_torch_on(block_name, "torch_output_on", 7, 2, 13);
    }

    private void redstone_torch_off(String path, String name, int x, int y, int z) {
        BlockModelBuilder builder = getBuilder(path + "/" + name);

        builder.element()
                .from(x, y, z)
                .to(x+2, y+5, z+2)
                .face(Direction.NORTH).texture("#torch_off").uvs(7, 6, 9, 11).end()
                .face(Direction.EAST).texture("#torch_off").uvs(7, 6, 9, 11).end()
                .face(Direction.SOUTH).texture("#torch_off").uvs(7, 6, 9, 11).end()
                .face(Direction.WEST).texture("#torch_off").uvs(7, 6, 9, 11).end()
                .face(Direction.UP).texture("#torch_off").uvs(7, 6, 9, 8).end()
                .face(Direction.DOWN).texture("#torch_off").uvs(7, 6, 9, 8).end()
                .end();

        builder.texture("torch_off", mcLoc("block/redstone_torch_off"));
    }

    private void redstone_torch_on(String path, String name, int x, int y, int z) {
        BlockModelBuilder builder = getBuilder(path + "/" + name);

        builder.element()
                .from(x, y, z)
                .to(x+2, y+5, z+2)
                .face(Direction.NORTH).texture("#torch_on").uvs(7, 6, 9, 11).end()
                .face(Direction.EAST).texture("#torch_on").uvs(7, 6, 9, 11).end()
                .face(Direction.SOUTH).texture("#torch_on").uvs(7, 6, 9, 11).end()
                .face(Direction.WEST).texture("#torch_on").uvs(7, 6, 9, 11).end()
                .face(Direction.UP).texture("#torch_on").uvs(7, 6, 9, 8).end()
                .face(Direction.DOWN).texture("#torch_on").uvs(7, 6, 9, 8).end()
                .end();

        builder.element()
                .from(x, y+5, z)
                .to(x+2, y+6, z+2)
                .face(Direction.NORTH).texture("#torch_on").uvs(7, 5, 9, 6).end()
                .face(Direction.SOUTH).texture("#torch_on").uvs(7, 5, 9, 6).end()
                .face(Direction.EAST).texture("#torch_on").uvs(7, 5, 9, 6).end()
                .face(Direction.WEST).texture("#torch_on").uvs(7, 5, 9, 6).end();

        builder.element()
                .from(x+2, y+3, z)
                .to(x+3, y+5, z+2)
                .face(Direction.NORTH).texture("#torch_on").uvs(6, 6, 6, 8).end()
                .face(Direction.SOUTH).texture("#torch_on").uvs(6, 6, 6, 8).end();

        builder.element()
                .from(x-1, y+3, z)
                .to(x, y+5, z+2)
                .face(Direction.NORTH).texture("#torch_on").uvs(6, 6, 6, 8).end()
                .face(Direction.SOUTH).texture("#torch_on").uvs(6, 6, 6, 8).end();

        builder.element()
                .from(x, y+3, z+2)
                .to(x+2, y+5, z+3)
                .face(Direction.EAST).texture("#torch_on").uvs(6, 6, 6, 8).end()
                .face(Direction.WEST).texture("#torch_on").uvs(6, 6, 6, 8).end();

        builder.element()
                .from(x, y+3, z-1)
                .to(x+2, y+5, z)
                .face(Direction.EAST).texture("#torch_on").uvs(6, 6, 6, 8).end()
                .face(Direction.WEST).texture("#torch_on").uvs(6, 6, 6, 8).end();

        builder.texture("torch_on", mcLoc("block/redstone_torch"));
        builder.renderType("cutout");
    }

    private void registerRedstoneDialModels() {
        String block_name = "block/" + getBlockName(ModBlocks.REDSTONE_DIAL_BLOCK.get());

        for (int power = 0; power < 16; power++) {
            String name = block_name + "/base_" + power;

            ResourceLocation texture = modLoc("block/redstone_dial_base_" + power);

            BlockModelBuilder builder = getBuilder(name);
            builder.element()
                    .from(0, 0, 0)
                    .to(16, 2, 16)
                    .allFaces((direction, faceBuilder) -> faceBuilder.texture("#txt"))
                    .end();

            builder.texture("txt", texture);
            builder.texture("particle", texture);
            builder.renderType("solid");
        }

        redstone_torch_off(block_name, "torch_off", 7, 0, 7);
        redstone_torch_on(block_name, "torch_on", 7, 0, 7);
    }
}
