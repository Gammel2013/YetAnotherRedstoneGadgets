package com.gammel2012.yetanotherredstonegadgets.blocks;

import com.gammel2012.utils.SelectorIntegerProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class ModBlockProperties {
    public static final IntegerProperty LAMP_SIGNAL_STRENGTH = IntegerProperty.create("signal_strength", 0, 15);

    public static final SelectorIntegerProperty DIVIDER = SelectorIntegerProperty.create(
            "divider",
            2,
            3,
            5,
            7
    );

    public static final IntegerProperty LONG_OBSERVER_RANGE = IntegerProperty.create("observer_range", 1, 8);
    public static final BooleanProperty CALIBRATED_OBSERVER_DUMMY = BooleanProperty.create("calibrated_observer_dummy");

    public static final BooleanProperty ROUND_UP = BooleanProperty.create("round_up");

    public static final IntegerProperty POWER = BlockStateProperties.POWER;
    public static final DirectionProperty HORIZONTAL_FACING_DIRECTION = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final DirectionProperty ALL_FACING = BlockStateProperties.FACING;
}
