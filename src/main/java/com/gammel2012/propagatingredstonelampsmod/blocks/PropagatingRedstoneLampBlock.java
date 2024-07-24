package com.gammel2012.propagatingredstonelampsmod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import javax.annotation.Nullable;

public class PropagatingRedstoneLampBlock extends Block {

    public static final IntegerProperty SIGNAL_STRENGTH = ModBlockProperties.LAMP_SIGNAL_STRENGTH;

    public final int mask_value;

    public PropagatingRedstoneLampBlock(Properties properties, int interaction_mask) {
        super(properties);

        mask_value = interaction_mask;

        this.registerDefaultState(
                this.getStateDefinition().any().setValue(SIGNAL_STRENGTH, 0)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(SIGNAL_STRENGTH);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {

        BlockPos place_pos = pContext.getClickedPos();
        Level level = pContext.getLevel();

        int neighbour_prl_signal = getHighestNeighbourLampSignal(place_pos, level);
        int neighbour_signal = level.getBestNeighborSignal(place_pos);

        int new_strength = Math.max(neighbour_signal, neighbour_prl_signal);

        return this.defaultBlockState()
                .setValue(SIGNAL_STRENGTH, new_strength);
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (!pLevel.isClientSide) {
            int old_strength = pState.getValue(SIGNAL_STRENGTH);

            int neighbour_prl_signal = getHighestNeighbourLampSignal(pPos, pLevel);
            int neighbour_signal = pLevel.getBestNeighborSignal(pPos);

            int new_strength = Math.max(neighbour_signal, neighbour_prl_signal);

            if (new_strength != old_strength) {
                pLevel.setBlock(pPos, pState.setValue(SIGNAL_STRENGTH, new_strength), 3);
            }
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pBlockState, Level pLevel, BlockPos pPos) {
        return pBlockState.getValue(SIGNAL_STRENGTH);
    }

    private int getHighestNeighbourLampSignal(BlockPos pPos, Level pLevel) {

        BlockPos[] neighbours = new BlockPos[]{
                pPos.above(),
                pPos.below(),
                pPos.north(),
                pPos.south(),
                pPos.east(),
                pPos.west()
        };

        int neighbour_prl_signal = 0;

        for (BlockPos neighbour_pos : neighbours) {
            BlockState state = pLevel.getBlockState(neighbour_pos);

            Block block = state.getBlock();

            if (state.hasProperty(SIGNAL_STRENGTH) && block instanceof PropagatingRedstoneLampBlock other_lamp && (other_lamp.mask_value & this.mask_value) != 0) {
                neighbour_prl_signal = Math.max(neighbour_prl_signal, state.getValue(SIGNAL_STRENGTH));
            }
        }

        return neighbour_prl_signal-1;
    }
}
