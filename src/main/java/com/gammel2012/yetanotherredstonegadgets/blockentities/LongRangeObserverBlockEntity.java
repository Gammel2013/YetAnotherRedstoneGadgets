package com.gammel2012.yetanotherredstonegadgets.blockentities;

import com.gammel2012.utils.TickingBE;
import com.gammel2012.yetanotherredstonegadgets.blocks.LongRangeObserverBlock;
import com.gammel2012.yetanotherredstonegadgets.registers.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LongRangeObserverBlockEntity extends BlockEntity implements TickingBE {

    BlockState old_state;
    int powered_timer;

    public LongRangeObserverBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntityTypes.LONG_RANGE_OBSERVER.get(), pPos, pBlockState);
        this.old_state = pBlockState;
    }

    @Override
    public void tickServer(Level lvl, BlockPos pos, BlockState st, BlockEntity blockEntity) {
        boolean powered = st.getValue(LongRangeObserverBlock.POWERED);

        if (powered_timer > 0) {
            powered_timer -= 1;
        }

        if (powered && powered_timer == 0) {
            st = st.setValue(LongRangeObserverBlock.POWERED, false);
            lvl.setBlock(pos, st, 3);

        } else {
            Direction facing = st.getValue(LongRangeObserverBlock.FACING);

            int range = st.getValue(LongRangeObserverBlock.RANGE);

            BlockPos observed_pos = pos.relative(facing, range);

            BlockState new_state = lvl.getBlockState(observed_pos);
            Block new_block = new_state.getBlock();

            if (!old_state.is(new_block) || !old_state.equals(new_state)) {
                st = st.setValue(LongRangeObserverBlock.POWERED, true);
                lvl.setBlock(pos, st, 3);

                powered_timer = 2;
            }

            old_state = new_state;
        }
    }

    @Override
    public void tickClient(Level lvl, BlockPos pos, BlockState st, BlockEntity blockEntity) {

    }
}
