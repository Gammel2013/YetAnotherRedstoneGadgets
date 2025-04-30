package com.gammel2012.utils;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class TickingEntityBlock extends Block implements EntityBlock {
    public TickingEntityBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) {
            return (lvl, pos, st, blockEntity) -> {
                if (blockEntity instanceof TickingBE be) {
                    be.tickClient(lvl, pos, st, blockEntity);
                }
            };
        } else {
            return (lvl, pos, st, blockEntity) -> {
                if (blockEntity instanceof TickingBE be) {
                    be.tickServer(lvl, pos, st, blockEntity);
                }
            };
        }
    }
}
