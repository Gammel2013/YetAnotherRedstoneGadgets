package com.gammel2012.yetanotherredstonegadgets.providers;

import com.gammel2012.yetanotherredstonegadgets.YetAnotherRedstoneGadgets;
import com.gammel2012.yetanotherredstonegadgets.blocks.ModBlockProperties;
import com.gammel2012.yetanotherredstonegadgets.registers.ModBlocks;
import com.gammel2012.utils.providers.BaseBlockStateProvider;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.ArrayList;
import java.util.List;

public class ModBlockStateProvider extends BaseBlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, YetAnotherRedstoneGadgets.MODID, exFileHelper);
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

        redstoneDialLamp(ModBlocks.REDSTONE_DIAL_LAMP_BLOCK);
        redstoneDialLamp(ModBlocks.SEVEN_SEGMENT_LAMP_BLOCK);

        redstoneDivider(ModBlocks.REDSTONE_DIVIDER_BLOCK);
        redstoneDial(ModBlocks.REDSTONE_DIAL_BLOCK);

        observer(ModBlocks.LONG_RANGE_OBSERVER_BLOCK);
        observer(ModBlocks.CALIBRATED_OBSERVER_BLOCK);
    }

    public void propagatingRedstoneLamp(DeferredBlock<Block> dBlock) {
        Block block = dBlock.get();
        String name = getBlockName(block);

        ModelFile model_on = models().getExistingFile(modLoc("block/" + name + "_on"));
        ModelFile model_off = models().getExistingFile(modLoc("block/" + name + "_off"));

        getVariantBuilder(block).forAllStates(
                state -> {
                    IntegerProperty signal_prop = ModBlockProperties.LAMP_SIGNAL_STRENGTH;
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

            List<Integer> torch_1_active = new ArrayList<>();
            List<Integer> torch_1_inactive = new ArrayList<>();

            List<Integer> torch_2_active = new ArrayList<>();
            List<Integer> torch_2_inactive = new ArrayList<>();

            List<Integer> torch_4_active = new ArrayList<>();
            List<Integer> torch_4_inactive = new ArrayList<>();

            for (int divider : ModBlockProperties.DIVIDER.getPossibleValues()) {

                boolean t1 = (divider & 1) != 0;
                boolean t2 = (divider & 2) != 0;
                boolean t4 = (divider & 4) != 0;

                if (!t1) {
                    torch_1_inactive.add(divider);
                } else {
                    torch_1_active.add(divider);
                }

                if (!t2) {
                    torch_2_inactive.add(divider);
                } else {
                    torch_2_active.add(divider);
                }

                if (!t4) {
                    torch_4_inactive.add(divider);
                } else {
                    torch_4_active.add(divider);
                }

                List<Integer> torch_output_round_up_active = new ArrayList<>();
                List<Integer> torch_output_round_down_active = new ArrayList<>();

                List<Integer> torch_output_round_up_inactive = new ArrayList<>();
                List<Integer> torch_output_round_down_inactive = new ArrayList<>();

                for (int power : ModBlockProperties.POWER.getPossibleValues()) {

                    float output = (float) power / (float) divider;

                    int up = (int) Math.ceil(output);
                    int down = (int) Math.floor(output);

                    if (up == 0) {
                        torch_output_round_up_inactive.add(power);
                    } else {
                        torch_output_round_up_active.add(power);
                    }

                    if (down == 0) {
                        torch_output_round_down_inactive.add(power);
                    } else {
                        torch_output_round_down_active.add(power);
                    }
                }

                Integer[] torch_on_up = torch_output_round_up_active.toArray(new Integer[0]);
                Integer[] torch_off_up = torch_output_round_up_inactive.toArray(new Integer[0]);

                Integer[] torch_on_down = torch_output_round_down_active.toArray(new Integer[0]);
                Integer[] torch_off_down = torch_output_round_down_inactive.toArray(new Integer[0]);

                bld.part().modelFile(torch_output_off).rotationY(torch_rotation).addModel()
                        .condition(ModBlockProperties.HORIZONTAL_FACING_DIRECTION, dir)
                        .condition(ModBlockProperties.DIVIDER, divider)
                        .condition(ModBlockProperties.POWER, torch_off_up)
                        .condition(ModBlockProperties.ROUND_UP, true);

                bld.part().modelFile(torch_output_on).rotationY(torch_rotation).addModel()
                        .condition(ModBlockProperties.HORIZONTAL_FACING_DIRECTION, dir)
                        .condition(ModBlockProperties.DIVIDER, divider)
                        .condition(ModBlockProperties.POWER, torch_on_up)
                        .condition(ModBlockProperties.ROUND_UP, true);

                bld.part().modelFile(torch_output_off).rotationY(torch_rotation).addModel()
                        .condition(ModBlockProperties.HORIZONTAL_FACING_DIRECTION, dir)
                        .condition(ModBlockProperties.DIVIDER, divider)
                        .condition(ModBlockProperties.POWER, torch_off_down)
                        .condition(ModBlockProperties.ROUND_UP, false);

                bld.part().modelFile(torch_output_on).rotationY(torch_rotation).addModel()
                        .condition(ModBlockProperties.HORIZONTAL_FACING_DIRECTION, dir)
                        .condition(ModBlockProperties.DIVIDER, divider)
                        .condition(ModBlockProperties.POWER, torch_on_down)
                        .condition(ModBlockProperties.ROUND_UP, false);
            }

            Integer[] torch_1_on_arr = torch_1_active.toArray(new Integer[0]);
            Integer[] torch_1_off_arr = torch_1_inactive.toArray(new Integer[0]);

            bld.part().modelFile(torch_1_on).rotationY(torch_rotation).addModel()
                    .condition(ModBlockProperties.HORIZONTAL_FACING_DIRECTION, dir)
                    .condition(ModBlockProperties.DIVIDER, torch_1_on_arr);
            bld.part().modelFile(torch_1_off).rotationY(torch_rotation).addModel()
                    .condition(ModBlockProperties.HORIZONTAL_FACING_DIRECTION, dir)
                    .condition(ModBlockProperties.DIVIDER, torch_1_off_arr);

            Integer[] torch_2_on_arr = torch_2_active.toArray(new Integer[0]);
            Integer[] torch_2_off_arr = torch_2_inactive.toArray(new Integer[0]);

            bld.part().modelFile(torch_2_on).rotationY(torch_rotation).addModel()
                    .condition(ModBlockProperties.HORIZONTAL_FACING_DIRECTION, dir)
                    .condition(ModBlockProperties.DIVIDER, torch_2_on_arr);
            bld.part().modelFile(torch_2_off).rotationY(torch_rotation).addModel()
                    .condition(ModBlockProperties.HORIZONTAL_FACING_DIRECTION, dir)
                    .condition(ModBlockProperties.DIVIDER, torch_2_off_arr);

            Integer[] torch_4_on_arr = torch_4_active.toArray(new Integer[0]);
            Integer[] torch_4_off_arr = torch_4_inactive.toArray(new Integer[0]);

            bld.part().modelFile(torch_4_on).rotationY(torch_rotation).addModel()
                    .condition(ModBlockProperties.HORIZONTAL_FACING_DIRECTION, dir)
                    .condition(ModBlockProperties.DIVIDER, torch_4_on_arr);
            bld.part().modelFile(torch_4_off).rotationY(torch_rotation).addModel()
                    .condition(ModBlockProperties.HORIZONTAL_FACING_DIRECTION, dir)
                    .condition(ModBlockProperties.DIVIDER, torch_4_off_arr);
        }
    }

    private void redstoneDial(DeferredBlock<Block> dBlock) {
        Block block = dBlock.get();
        String name = getBlockName(block);

        MultiPartBlockStateBuilder bld = getMultipartBuilder(block);

        for (Direction dir : ModBlockProperties.HORIZONTAL_FACING_DIRECTION.getPossibleValues()) {

            int rotation_y = ((int) dir.toYRot() + 180) % 360;

            for (int power : ModBlockProperties.POWER.getPossibleValues()) {

                ModelFile model_base = models().getExistingFile(modLoc("block/" + name + "/base_" + power));

                bld.part().modelFile(model_base).rotationY(rotation_y).addModel()
                        .condition(ModBlockProperties.HORIZONTAL_FACING_DIRECTION, dir)
                        .condition(ModBlockProperties.POWER, power);
            }
        }

        ModelFile torch_on = models().getExistingFile(modLoc("block/" + name + "/torch_on"));
        ModelFile torch_off = models().getExistingFile(modLoc("block/" + name + "/torch_off"));

        bld.part().modelFile(torch_on).addModel()
                .condition(
                        ModBlockProperties.POWER,
                        1,
                        2,
                        3,
                        4,
                        5,
                        6,
                        7,
                        8,
                        9,
                        10,
                        11,
                        12,
                        13,
                        14,
                        15
                );
        bld.part().modelFile(torch_off).addModel()
                .condition(ModBlockProperties.POWER, 0);
    }

    private void redstoneDialLamp(DeferredBlock<Block> dBlock) {
        Block block = dBlock.get();
        String name = getBlockName(block);

        getVariantBuilder(block).forAllStates(
                state -> {
                    IntegerProperty signal_prop = ModBlockProperties.POWER;
                    DirectionProperty facing = ModBlockProperties.HORIZONTAL_FACING_DIRECTION;

                    int power = state.getValue(signal_prop);
                    int angle_y = (int) state.getValue(facing).toYRot();

                    return ConfiguredModel.builder()
                            .modelFile(models().getExistingFile(modLoc("block/" + name + "_" + power)))
                            .rotationY(angle_y)
                            .build();
                }
        );
    }

    private void observer(DeferredBlock<Block> dBlock) {
        Block block = dBlock.get();
        String name = getBlockName(block);

        MultiPartBlockStateBuilder bld = getMultipartBuilder(block);

        ModelFile model_on = models().getExistingFile(modLoc("block/" + name + "_on"));
        ModelFile model_off = models().getExistingFile(modLoc("block/" + name + "_off"));

        for (Direction dir : ModBlockProperties.ALL_FACING.getPossibleValues()) {

            int rotation_y = ((int) dir.toYRot() + 180) % 360;

            int rotation_x = 0;
            if (dir == Direction.DOWN) {
                rotation_x = 90;
            } else if (dir == Direction.UP) {
                rotation_x = 270;
            }



            bld.part().modelFile(model_on).rotationY(rotation_y).rotationX(rotation_x).addModel()
                    .condition(ModBlockProperties.ALL_FACING, dir)
                    .condition(ModBlockProperties.POWERED, true);

            bld.part().modelFile(model_off).rotationY(rotation_y).rotationX(rotation_x).addModel()
                    .condition(ModBlockProperties.ALL_FACING, dir)
                    .condition(ModBlockProperties.POWERED, false);
        }
    }
}
