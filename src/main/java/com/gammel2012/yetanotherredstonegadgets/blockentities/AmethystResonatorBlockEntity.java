package com.gammel2012.yetanotherredstonegadgets.blockentities;

import com.gammel2012.utils.TickingBE;
import com.gammel2012.utils.iterators.CubeBlockPosIterable;
import com.gammel2012.yetanotherredstonegadgets.blocks.AmethystResonatorBlock;
import com.gammel2012.yetanotherredstonegadgets.registers.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class AmethystResonatorBlockEntity extends BlockEntity implements TickingBE {

    private boolean has_ticked = false;
    private int sending_timer = 0;
    private int receiving_timer = 0;

    private List<AmethystResonatorBlockEntity> RESONATORS_IN_RANGE = new ArrayList<>();

    public AmethystResonatorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntityTypes.AMETHYST_RESONATOR.get(), pPos, pBlockState);
    }

    @Override
    public void tickServer(Level lvl, BlockPos pos, BlockState st, BlockEntity blockEntity) {
        if (!has_ticked) {
            getResonatorsInRange();
            has_ticked = true;
        }

        boolean is_sending = isSending();
        boolean is_receiving = isReceiving();

        if (sending_timer > 0) {
            sending_timer--;
        }
        if (receiving_timer > 0) {
            receiving_timer--;
        }

        if (sending_timer == 0 && is_sending) {
            lvl.setBlock(pos, st.setValue(AmethystResonatorBlock.POWERED, false), 3);
        }
        if (receiving_timer == 0 && is_receiving) {
            lvl.setBlock(pos, st.setValue(AmethystResonatorBlock.LIT, false), 3);
        }
    }

    @Override
    public void tickClient(Level lvl, BlockPos pos, BlockState st, BlockEntity blockEntity) {

    }

    protected void addResonator(AmethystResonatorBlockEntity be) {
        RESONATORS_IN_RANGE.add(be);
    }

    protected void getResonatorsInRange() {
        BlockPos own_position = worldPosition;

        int own_mask = getSignalMask();

        for (BlockPos pos : new CubeBlockPosIterable(own_position, 8)) {

            BlockEntity other = level.getBlockEntity(pos);

            if (own_position.equals(pos) || other == null || other.isRemoved()) {
                continue;
            }

            if (other instanceof AmethystResonatorBlockEntity resonator && (resonator.getSignalMask() & own_mask) > 0) {
                addResonator(resonator);
                resonator.addResonator(this);
            }
        }
    }

    public boolean isSending() {
        return sending_timer > 0;
    }

    public boolean isReceiving() {
        return receiving_timer > 0;
    }

    public void sendSignal() {
        clearRemovedResonators();

        if (isReceiving()) {
            return;
        }

        sending_timer = 2;

        for (AmethystResonatorBlockEntity resonator : RESONATORS_IN_RANGE) {
            resonator.receiveSignal();
        }

        BlockState state = level.getBlockState(worldPosition);

        boolean is_powered = state.getValue(AmethystResonatorBlock.POWERED);

        if (!is_powered) {
            level.setBlock(worldPosition, state.setValue(AmethystResonatorBlock.POWERED, true), 3);
        }
    }

    protected void receiveSignal() {
        if (isSending()) {
            return;
        }

        receiving_timer = 2;

        BlockState state = level.getBlockState(worldPosition);

        boolean is_powering = state.getValue(AmethystResonatorBlock.LIT);

        if (!is_powering) {
            level.setBlock(worldPosition, state.setValue(AmethystResonatorBlock.LIT, true), 3);
        }
    }

    protected void clearRemovedResonators() {
        List<AmethystResonatorBlockEntity> removed = new ArrayList<>();

        for (AmethystResonatorBlockEntity resonator : RESONATORS_IN_RANGE) {
            if (resonator.remove) {
                removed.add(resonator);
            }
        }

        for (AmethystResonatorBlockEntity removed_resonator : removed) {
            RESONATORS_IN_RANGE.remove(removed_resonator);
        }
    }

    public int getSignalMask() {
        BlockPos position = worldPosition;
        Block block = level.getBlockState(position).getBlock();

        if (block instanceof AmethystResonatorBlock resonatorBlock) {
            return resonatorBlock.getSignalMask();
        }

        return 0;
    }
}
