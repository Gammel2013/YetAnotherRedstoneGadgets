package com.gammel2012.yetanotherredstonegadgets.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class RedstoneDialLampBlock extends Block {

    private static final IntegerProperty POWER = ModBlockProperties.POWER;
    private static final DirectionProperty FACING_DIRECTION = ModBlockProperties.HORIZONTAL_FACING_DIRECTION;

    public RedstoneDialLampBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(
                this.getStateDefinition().any()
                        .setValue(FACING_DIRECTION, Direction.NORTH)
                        .setValue(POWER, 0)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING_DIRECTION);
        pBuilder.add(POWER);
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (!pLevel.isClientSide) {
            int power = pLevel.getBestNeighborSignal(pPos);
            pState = pState.setValue(POWER, power);

            pLevel.setBlock(pPos, pState, 3);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {

        Level pLevel = pContext.getLevel();
        BlockPos place_pos = pContext.getClickedPos();

        Direction dir = pContext.getHorizontalDirection().getOpposite();
        int power = pLevel.getBestNeighborSignal(place_pos);

        BlockState state = this.defaultBlockState()
                .setValue(FACING_DIRECTION, dir)
                .setValue(POWER, power);

        return state;
    }
}
