package com.gammel2012.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface TickingBE {
    public void tickServer(Level lvl, BlockPos pos, BlockState st, BlockEntity blockEntity);
    public void tickClient(Level lvl, BlockPos pos, BlockState st, BlockEntity blockEntity);
}
