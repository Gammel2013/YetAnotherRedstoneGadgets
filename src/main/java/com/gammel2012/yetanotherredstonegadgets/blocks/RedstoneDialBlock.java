package com.gammel2012.yetanotherredstonegadgets.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RedstoneDialBlock extends Block {

    private static final IntegerProperty POWER = ModBlockProperties.POWER;
    private static final DirectionProperty FACING_DIRECTION = ModBlockProperties.HORIZONTAL_FACING_DIRECTION;

    public RedstoneDialBlock(Properties properties) {
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
            if (!pState.canSurvive(pLevel, pPos)) {
                dropResources(pState, pLevel, pPos);
                pLevel.removeBlock(pPos, false);
            }
        }
    }

    @Override
    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {

        Level pLevel = pContext.getLevel();
        BlockPos place_pos = pContext.getClickedPos();
        BlockState state = this.defaultBlockState().setValue(FACING_DIRECTION, pContext.getHorizontalDirection());

        return state;
    }

    @Override
    public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return pBlockState.getValue(FACING_DIRECTION).getOpposite() == pSide ? pBlockState.getValue(POWER) : 0;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        return this.canSurviveOn(pLevel, blockpos, pLevel.getBlockState(blockpos));
    }

    protected boolean canSurviveOn(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        return pState.isFaceSturdy(pLevel, pPos, Direction.UP, SupportType.RIGID);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return box(0, 0, 0, 16, 2, 16);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pPlayer.getAbilities().mayBuild) {
            return InteractionResult.PASS;
        } else {

            int val = 0;
            if (!pPlayer.isShiftKeyDown()) {
                // Regular rightclick: Cycle forwards
                val = (pState.getValue(POWER) + 1) % 16;
            } else {
                // Shift rightclick: Cycle backwards
                val = pState.getValue(POWER) == 0 ? 15 : pState.getValue(POWER) - 1;
            }

            pState = pState.setValue(POWER, val);

            // Play sound effect
            float pitch = 0.4f + 0.02f * val;
            pLevel.playSound(pPlayer, pPos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 0.3F, pitch);

            // Display status message
            if (pLevel.isClientSide) {
                String message = "block.yargmod.dial.power";
                pPlayer.displayClientMessage(Component.translatable(message, val), true);
            }

            // Update block
            pLevel.setBlock(pPos, pState, 3);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
    }
}
