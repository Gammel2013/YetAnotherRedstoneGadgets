package com.gammel2012.yetanotherredstonegadgets.blocks;

import com.gammel2012.utils.TickingEntityBlock;
import com.gammel2012.yetanotherredstonegadgets.blockentities.AmethystResonatorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class AmethystResonatorBlock extends TickingEntityBlock {

    public static final DirectionProperty FACING = ModBlockProperties.ALL_FACING;
    public static final BooleanProperty POWERED = ModBlockProperties.POWERED;
    public static final BooleanProperty LIT = ModBlockProperties.LIT;

    private final int SIGNAL_MASK;

    public AmethystResonatorBlock(Properties properties, int signal_mask) {
        super(properties);

        this.registerDefaultState(
                this.getStateDefinition().any()
                        .setValue(FACING, Direction.DOWN)
                        .setValue(POWERED, false)
                        .setValue(LIT, false)
        );

        this.SIGNAL_MASK = signal_mask;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        pBuilder.add(POWERED);
        pBuilder.add(LIT);
    }

    @Override
    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState state = this.defaultBlockState().setValue(FACING, pContext.getNearestLookingDirection());
        return state;
    }

    @Override
    public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return (pBlockState.getValue(FACING).getOpposite() == pSide && pBlockState.getValue(LIT)) ? 15 : 0;
    }

    @Override
    public int getDirectSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return getSignal(pBlockState, pBlockAccess, pPos, pSide);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AmethystResonatorBlockEntity(pPos, pState);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {

        Direction dir = pState.getValue(FACING);

        int x1 = 0;
        int y1 = 0;
        int z1 = 0;
        int x2 = 16;
        int y2 = 16;
        int z2 = 16;

        if (dir.getAxis() == Direction.Axis.X && dir.getAxisDirection() == Direction.AxisDirection.NEGATIVE) {
            x2 = 6;
        }
        if (dir.getAxis() == Direction.Axis.X && dir.getAxisDirection() == Direction.AxisDirection.POSITIVE) {
            x1 = 10;
        }
        if (dir.getAxis() == Direction.Axis.Y && dir.getAxisDirection() == Direction.AxisDirection.NEGATIVE) {
            y2 = 6;
        }
        if (dir.getAxis() == Direction.Axis.Y && dir.getAxisDirection() == Direction.AxisDirection.POSITIVE) {
            y1 = 10;
        }
        if (dir.getAxis() == Direction.Axis.Z && dir.getAxisDirection() == Direction.AxisDirection.NEGATIVE) {
            z2 = 6;
        }
        if (dir.getAxis() == Direction.Axis.Z && dir.getAxisDirection() == Direction.AxisDirection.POSITIVE) {
            z1 = 10;
        }

        return box(x1, y1, z1, x2, y2, z2);
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {

        if (pLevel.isClientSide || pState.getValue(LIT)) {
            return;
        }

        if (getInputSignal(pState, pLevel, pPos) != 0) {
            BlockEntity be = pLevel.getBlockEntity(pPos);

            if (be instanceof AmethystResonatorBlockEntity resonator) {
                resonator.sendSignal();
            }
        }
    }

    protected int getInputSignal(BlockState pState, Level pLevel, BlockPos pPos) {

        Direction.Axis facing_axis = pState.getValue(FACING).getAxis();

        int input_signal = 0;

        for (Direction dir : Direction.values()) {

            if (dir.getAxis() == facing_axis) {
                continue;
            }

            int side_signal = pLevel.getSignal(pPos.relative(dir), dir);

            input_signal = Math.max(input_signal, side_signal);
        }

        return input_signal;
    }

    public int getSignalMask() {
        return SIGNAL_MASK;
    }
}
