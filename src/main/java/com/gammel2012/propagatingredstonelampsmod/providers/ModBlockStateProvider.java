package com.gammel2012.propagatingredstonelampsmod.providers;

import com.gammel2012.propagatingredstonelampsmod.blocks.ModBlockProperties;
import com.gammel2012.propagatingredstonelampsmod.registers.ModBlocks;
import com.gammel2012.propagatingredstonelampsmod.blocks.PropagatingRedstoneLampBlock;
import com.gammel2012.propagatingredstonelampsmod.PropagatingRedstoneLampsMod;
import com.gammel2012.utils.providers.BaseBlockStateProvider;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BaseBlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, PropagatingRedstoneLampsMod.MODID, exFileHelper);
    }

    @Override
    protected DirectionProperty getHorizontalDirectionProperty() {
        return null;
    }

    @Override
    protected void registerStatesAndModels() {
        propagatingRedstoneLamp(ModBlocks.PURPLE_PROPAGATING_REDSTONE_LAMP_BLOCK);
        propagatingRedstoneLamp(ModBlocks.BLUE_PROPAGATING_REDSTONE_LAMP_BLOCK);
        propagatingRedstoneLamp(ModBlocks.RED_PROPAGATING_REDSTONE_LAMP_BLOCK);

        redstoneDivider(ModBlocks.REDSTONE_DIVIDER_BLOCK);
    }

    public void propagatingRedstoneLamp(DeferredBlock<Block> dBlock) {
        Block block = dBlock.get();
        String name = getBlockName(block);

        ModelFile model_on = models().getExistingFile(modLoc("block/" + name + "_on"));
        ModelFile model_off = models().getExistingFile(modLoc("block/" + name + "_off"));
        getVariantBuilder(block).forAllStates(
                state -> {
                    IntegerProperty signal_prop = PropagatingRedstoneLampBlock.SIGNAL_STRENGTH;
                    int strength = state.getValue(signal_prop);

                    if (strength > 0) {
                        return ConfiguredModel.builder()
                                .modelFile(model_on)
                                .build();
                    }
                    return ConfiguredModel.builder()
                            .modelFile(model_off)
                            .build();
                }
        );
    }

    private void redstoneDivider(DeferredBlock<Block> dBlock) {
        Block block = dBlock.get();
        String name = getBlockName(block);

        ModelFile base = models().getExistingFile(modLoc("block/" + name + "/base"));

        ModelFile torch_1_off = models().getExistingFile(modLoc("block/" + name + "/torch_1_off"));
        ModelFile torch_2_off = models().getExistingFile(modLoc("block/" + name + "/torch_2_off"));
        ModelFile torch_4_off = models().getExistingFile(modLoc("block/" + name + "/torch_4_off"));
        ModelFile torch_output_off = models().getExistingFile(modLoc("block/" + name + "/torch_output_off"));
        ModelFile torch_round_off = models().getExistingFile(modLoc("block/" + name + "/torch_round_off"));

        ModelFile torch_1_on = models().getExistingFile(modLoc("block/" + name + "/torch_1_on"));
        ModelFile torch_2_on = models().getExistingFile(modLoc("block/" + name + "/torch_2_on"));
        ModelFile torch_4_on = models().getExistingFile(modLoc("block/" + name + "/torch_4_on"));
        ModelFile torch_output_on = models().getExistingFile(modLoc("block/" + name + "/torch_output_on"));
        ModelFile torch_round_on = models().getExistingFile(modLoc("block/" + name + "/torch_round_on"));

        MultiPartBlockStateBuilder bld = getMultipartBuilder(block);

        for (Direction dir : ModBlockProperties.HORIZONTAL_FACING_DIRECTION.getPossibleValues()) {

            int rotation_y = ((int) dir.toYRot() + 180) % 360;

            int base_rotation = rotation_y;
            int torch_rotation = (rotation_y + 180) % 360;

            bld.part().modelFile(base).rotationY(base_rotation).addModel()
                    .condition(ModBlockProperties.HORIZONTAL_FACING_DIRECTION, dir);

            bld.part().modelFile(torch_round_off).rotationY(torch_rotation).addModel()
                    .condition(ModBlockProperties.HORIZONTAL_FACING_DIRECTION, dir)
                    .condition(ModBlockProperties.ROUND_UP, false);

            bld.part().modelFile(torch_round_on).rotationY(torch_rotation).addModel()
                    .condition(ModBlockProperties.HORIZONTAL_FACING_DIRECTION, dir)
                    .condition(ModBlockProperties.ROUND_UP, true);

            for (int divider : ModBlockProperties.DIVIDER.getPossibleValues()) {

                boolean t1 = (divider & 1) != 0;
                boolean t2 = (divider & 2) != 0;
                boolean t4 = (divider & 4) != 0;

                if (!t1) {
                    bld.part().modelFile(torch_1_off).rotationY(torch_rotation).addModel()
                            .condition(ModBlockProperties.HORIZONTAL_FACING_DIRECTION, dir)
                            .condition(ModBlockProperties.DIVIDER, divider);
                } else {
                    bld.part().modelFile(torch_1_on).rotationY(torch_rotation).addModel()
                            .condition(ModBlockProperties.HORIZONTAL_FACING_DIRECTION, dir)
                            .condition(ModBlockProperties.DIVIDER, divider);
                }

                if (!t2) {
                    bld.part().modelFile(torch_2_off).rotationY(torch_rotation).addModel()
                            .condition(ModBlockProperties.HORIZONTAL_FACING_DIRECTION, dir)
                            .condition(ModBlockProperties.DIVIDER, divider);
                } else {
                    bld.part().modelFile(torch_2_on).rotationY(torch_rotation).addModel()
                            .condition(ModBlockProperties.HORIZONTAL_FACING_DIRECTION, dir)
                            .condition(ModBlockProperties.DIVIDER, divider);
                }

                if (!t4) {
                    bld.part().modelFile(torch_4_off).rotationY(torch_rotation).addModel()
                            .condition(ModBlockProperties.HORIZONTAL_FACING_DIRECTION, dir)
                            .condition(ModBlockProperties.DIVIDER, divider);
                } else {
                    bld.part().modelFile(torch_4_on).rotationY(torch_rotation).addModel()
                            .condition(ModBlockProperties.HORIZONTAL_FACING_DIRECTION, dir)
                            .condition(ModBlockProperties.DIVIDER, divider);
                }

                for (int power : ModBlockProperties.POWER.getPossibleValues()) {

                    float output = (float) power / (float) divider;

                    int up = (int) Math.ceil(output);
                    int down = (int) Math.floor(output);

                    if (up == 0) {
                        bld.part().modelFile(torch_output_off).rotationY(torch_rotation).addModel()
                                .condition(ModBlockProperties.HORIZONTAL_FACING_DIRECTION, dir)
                                .condition(ModBlockProperties.DIVIDER, divider)
                                .condition(ModBlockProperties.POWER, power)
                                .condition(ModBlockProperties.ROUND_UP, true);
                    } else {
                        bld.part().modelFile(torch_output_on).rotationY(torch_rotation).addModel()
                                .condition(ModBlockProperties.HORIZONTAL_FACING_DIRECTION, dir)
                                .condition(ModBlockProperties.DIVIDER, divider)
                                .condition(ModBlockProperties.POWER, power)
                                .condition(ModBlockProperties.ROUND_UP, true);
                    }

                    if (down == 0) {
                        bld.part().modelFile(torch_output_off).rotationY(torch_rotation).addModel()
                                .condition(ModBlockProperties.HORIZONTAL_FACING_DIRECTION, dir)
                                .condition(ModBlockProperties.DIVIDER, divider)
                                .condition(ModBlockProperties.POWER, power)
                                .condition(ModBlockProperties.ROUND_UP, false);

                    } else {
                        bld.part().modelFile(torch_output_on).rotationY(torch_rotation).addModel()
                                .condition(ModBlockProperties.HORIZONTAL_FACING_DIRECTION, dir)
                                .condition(ModBlockProperties.DIVIDER, divider)
                                .condition(ModBlockProperties.POWER, power)
                                .condition(ModBlockProperties.ROUND_UP, false);
                    }
                }
            }
        }
    }
}
