package com.gammel2012.propagatingredstonelampsmod.blocks;

import com.gammel2012.utils.SelectorIntegerProperty;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class ModBlockProperties {
    public static final IntegerProperty SIGNAL_STRENGTH = IntegerProperty.create("signal_strength", 0, 15);

    public static final DirectionProperty HORIZONTAL_FACING_DIRECTION = BlockStateProperties.HORIZONTAL_FACING;

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public static final SelectorIntegerProperty DIVIDER = SelectorIntegerProperty.create(
            "divider",
            2,
            3,
            5,
            7
    );

    public static final IntegerProperty POWER = BlockStateProperties.POWER;
}
