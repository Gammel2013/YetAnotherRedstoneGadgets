package com.gammel2012.yetanotherredstonegadgets.providers;

import com.gammel2012.utils.providers.BaseBlockModelProvider;
import com.gammel2012.yetanotherredstonegadgets.YetAnotherRedstoneGadgets;
import com.gammel2012.yetanotherredstonegadgets.registers.ModBlocks;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockModelProvider extends BaseBlockModelProvider {
    public ModBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, YetAnotherRedstoneGadgets.MODID, existingFileHelper);
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
        registerDialLampModels(ModBlocks.REDSTONE_DIAL_LAMP_BLOCK);
        registerDialLampModels(ModBlocks.SEVEN_SEGMENT_LAMP_BLOCK);
        registerObserver(ModBlocks.LONG_RANGE_OBSERVER_BLOCK.get());
        registerObserver(ModBlocks.CALIBRATED_OBSERVER_BLOCK.get());
        registerAmethystResonator(ModBlocks.AMETHYST_RESONATOR_BLOCK.get());
        registerAnalogReader(ModBlocks.REGION_ANALOG_READER_BLOCK.get());
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

    private void registerDialLampModels(DeferredBlock<Block> dBlock) {

        String name = getBlockName(dBlock.get());
        ResourceLocation base_texture_path = blockLoc(dBlock.get());

        for (int power = 0; power < 16; power++) {
            cubeAll(
                    "block/" + name + "/" + power,
                    base_texture_path.withSuffix("_" + power)
            );
        }
    }

    private void registerObserver(Block observer_block) {

        String name = getBlockName(observer_block);

        ResourceLocation front_texture = blockLoc(observer_block).withSuffix("_front");
        ResourceLocation top_texture = mcLoc("block/observer_top");
        ResourceLocation side_texture = mcLoc("block/observer_side");
        ResourceLocation back_off_texture = mcLoc("block/observer_back");
        ResourceLocation back_on_texture = mcLoc("block/observer_back_on");

        cube(name + "_off",
                top_texture,
                top_texture,
                front_texture,
                back_off_texture,
                side_texture,
                side_texture
        ).texture("particle", front_texture);

        cube(name + "_on",
                top_texture,
                top_texture,
                front_texture,
                back_on_texture,
                side_texture,
                side_texture
        ).texture("particle", front_texture);
    }

    private void registerAmethystResonator(Block resonator_block) {

        String name = getBlockName(resonator_block);

        ResourceLocation top_texture = blockLoc(resonator_block).withSuffix("_top");
        ResourceLocation side_texture = blockLoc(resonator_block).withSuffix("_side");
        ResourceLocation bottom_off_texture = blockLoc(resonator_block).withSuffix("_bottom");
        ResourceLocation bottom_on_texture = blockLoc(resonator_block).withSuffix("_bottom_on");

        ResourceLocation amethyst_texture = modLoc("block/resonator_amethyst");

        BlockModelBuilder builder_off = getBuilder(name);
        builder_off.parent(getExistingFile(mcLoc("block/block")));
        builder_off.guiLight(BlockModel.GuiLight.FRONT);
        builder_off.element()
                .from(0, 0, 0)
                .to(16, 6, 16)
                .face(Direction.UP).texture("#top").end()
                .face(Direction.DOWN).texture("#bottom").end()
                .face(Direction.NORTH).texture("#side").end()
                .face(Direction.EAST).texture("#side").end()
                .face(Direction.SOUTH).texture("#side").end()
                .face(Direction.WEST).texture("#side").end();
        builder_off.texture("particle", top_texture);
        builder_off.texture("top", top_texture);
        builder_off.texture("side", side_texture);
        builder_off.texture("bottom", bottom_off_texture);
        builder_off.element()
                .from(4, 6, 8)
                .to(11, 16, 8)
                .face(Direction.NORTH).texture("#crystal").uvs(4, 4, 11, 14).end()
                .face(Direction.SOUTH).texture("#crystal").uvs(4, 4, 11, 14).end()
                .rotation().origin(8, 6, 8).axis(Direction.Axis.Y).angle(45);
        builder_off.element()
                .from(4, 6, 8)
                .to(11, 16, 8)
                .face(Direction.NORTH).texture("#crystal").uvs(4, 4, 11, 14).end()
                .face(Direction.SOUTH).texture("#crystal").uvs(4, 4, 11, 14).end()
                .rotation().origin(8, 6, 8).axis(Direction.Axis.Y).angle(-45);
        builder_off.texture("crystal", amethyst_texture);
        builder_off.renderType("cutout");

        BlockModelBuilder builder_on = getBuilder(name + "_on");
        builder_on.element()
                .from(0, 0, 0)
                .to(16, 6, 16)
                .face(Direction.UP).texture("#top").end()
                .face(Direction.DOWN).texture("#bottom").end()
                .face(Direction.NORTH).texture("#side").end()
                .face(Direction.EAST).texture("#side").end()
                .face(Direction.SOUTH).texture("#side").end()
                .face(Direction.WEST).texture("#side").end();
        builder_on.texture("particle", top_texture);
        builder_on.texture("top", top_texture);
        builder_on.texture("side", side_texture);
        builder_on.texture("bottom", bottom_on_texture);
        builder_on.element()
                .from(4, 6, 8)
                .to(11, 16, 8)
                .face(Direction.NORTH).texture("#crystal").uvs(4, 4, 11, 14).end()
                .face(Direction.SOUTH).texture("#crystal").uvs(4, 4, 11, 14).end()
                .rotation().origin(8, 6, 8).axis(Direction.Axis.Y).angle(45);
        builder_on.element()
                .from(4, 6, 8)
                .to(11, 16, 8)
                .face(Direction.NORTH).texture("#crystal").uvs(4, 4, 11, 14).end()
                .face(Direction.SOUTH).texture("#crystal").uvs(4, 4, 11, 14).end()
                .rotation().origin(8, 6, 8).axis(Direction.Axis.Y).angle(-45);
        builder_on.texture("crystal", amethyst_texture);
        builder_on.renderType("cutout");
    }

    private void registerAnalogReader(Block analog_reader) {
        String name = getBlockName(analog_reader);

        ResourceLocation front_texture = blockLoc(analog_reader).withSuffix("_front");
        ResourceLocation side_texture = blockLoc(analog_reader).withSuffix("_side");
        ResourceLocation back_texture = blockLoc(analog_reader).withSuffix("_back");

        BlockModelBuilder builder = getBuilder(name);
        builder.parent(getExistingFile(mcLoc("block/block")))
                .element().from(0, 0, 0).to(16, 16, 16)
                    .face(Direction.UP).texture("#side").rotation(ModelBuilder.FaceRotation.CLOCKWISE_90).end()
                    .face(Direction.DOWN).texture("#side").rotation(ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90).end()
                    .face(Direction.NORTH).texture("#front").end()
                    .face(Direction.SOUTH).texture("#back").end()
                    .face(Direction.EAST).texture("#side").rotation(ModelBuilder.FaceRotation.UPSIDE_DOWN).end()
                    .face(Direction.WEST).texture("#side").end();


        builder.texture("particle", front_texture)
                .texture("front", front_texture)
                .texture("side", side_texture)
                .texture("back", back_texture);
    }
}
