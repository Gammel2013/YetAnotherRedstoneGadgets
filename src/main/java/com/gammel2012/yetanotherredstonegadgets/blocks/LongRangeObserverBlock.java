package com.gammel2012.yetanotherredstonegadgets.blocks;

import com.gammel2012.utils.TickingEntityBlock;
import com.gammel2012.yetanotherredstonegadgets.blockentities.LongRangeObserverBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LongRangeObserverBlock extends TickingEntityBlock {

    public static final DirectionProperty FACING = ModBlockProperties.ALL_FACING;
    public static final BooleanProperty POWERED = ModBlockProperties.POWERED;
    public static final IntegerProperty RANGE = ModBlockProperties.LONG_OBSERVER_RANGE;

    public LongRangeObserverBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(
                this.getStateDefinition().any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(POWERED, false)
                        .setValue(RANGE, 2)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        pBuilder.add(POWERED);
        pBuilder.add(RANGE);
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
        return (pBlockState.getValue(FACING) == pSide && pBlockState.getValue(POWERED)) ? 15 : 0;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new LongRangeObserverBlockEntity(pPos, pState);
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHit) {
        if (!pPlayer.getAbilities().mayBuild) {
            return InteractionResult.PASS;
        } else {

            int val = 0;
            if (!pPlayer.isShiftKeyDown()) {
                // Regular rightclick: Cycle forwards
                val = pState.getValue(RANGE) == 8 ? 1 : pState.getValue(RANGE) + 1;
            } else {
                // Shift rightclick: Cycle backwards
                val = pState.getValue(RANGE) == 1 ? 8 : pState.getValue(RANGE) - 1;
            }

            pState = pState.setValue(RANGE, val);

            // Play sound effect
            float pitch = 0.45f + 0.02f * val;
            pLevel.playSound(pPlayer, pPos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 0.3F, pitch);

            // Display status message
            if (pLevel.isClientSide) {
                String message = "block.yargmod.longobserver.range";
                pPlayer.displayClientMessage(Component.translatable(message, val), true);
            }

            // Update block
            pLevel.setBlock(pPos, pState, 3);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
    }
}
