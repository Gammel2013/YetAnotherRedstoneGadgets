package com.gammel2012.yetanotherredstonegadgets.blocks;

import com.gammel2012.utils.SelectorIntegerProperty;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class RedstoneDividerBlock extends Block {

    private static final DirectionProperty FACING_DIRECTION = ModBlockProperties.HORIZONTAL_FACING_DIRECTION;
    private static final SelectorIntegerProperty DIVIDER = ModBlockProperties.DIVIDER;
    private static final IntegerProperty POWER = ModBlockProperties.POWER;
    private static final BooleanProperty ROUND_UP = ModBlockProperties.ROUND_UP;

    public static final Map<Integer, Float> PITCH_MAP = Map.of(
            2,
            0.45f,
            3,
            0.50f,
            5,
            0.55f,
            7,
            0.60f
    );

    public RedstoneDividerBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(
                this.getStateDefinition().any()
                        .setValue(FACING_DIRECTION, Direction.NORTH)
                        .setValue(POWER, 0)
                        .setValue(DIVIDER, 2)
                        .setValue(ROUND_UP, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING_DIRECTION);
        pBuilder.add(POWER);
        pBuilder.add(DIVIDER);
        pBuilder.add(ROUND_UP);
    }

    protected int getInputSignal(Level pLevel, BlockPos pPos, BlockState pState) {
        Direction direction = pState.getValue(FACING_DIRECTION).getOpposite();
        BlockPos blockpos = pPos.relative(direction);

        int input = pLevel.getSignal(blockpos, direction);
        return input;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pPlayer.getAbilities().mayBuild) {
            return InteractionResult.PASS;
        } else if (!pPlayer.isShiftKeyDown()) {
            // Regular rightclick: Cycle divider
            pState = pState.cycle(DIVIDER);
            int val = pState.getValue(DIVIDER);

            // Play sound effect
            float pitch = PITCH_MAP.get(val);
            pLevel.playSound(pPlayer, pPos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 0.3F, pitch);

            // Display status message
            if (pLevel.isClientSide) {
                String message = "block.yargmod.divider.divider";
                pPlayer.displayClientMessage(Component.translatable(message, val), true);
            }

            // Update block
            pLevel.setBlock(pPos, pState, 3);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        } else {
            // Shift rightclick: Cycle rounding
            pState = pState.cycle(ROUND_UP);
            boolean val = pState.getValue(ROUND_UP);

            // Play sound effect
            float pitch = val ? 0.55f : 0.50f;
            pLevel.playSound(pPlayer, pPos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 0.3F, pitch);

            // Display status message
            if (pLevel.isClientSide) {
                String message = val ? "block.yargmod.divider.round_up" : "block.yargmod.divider.round_down";
                pPlayer.displayClientMessage(Component.translatable(message), true);
            }

            // Update block
            pLevel.setBlock(pPos, pState, 3);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (!pLevel.isClientSide) {
            if (pState.canSurvive(pLevel, pPos)) {
                this.refreshState(pLevel, pPos, pState);
            } else {
                dropResources(pState, pLevel, pPos);
                pLevel.removeBlock(pPos, false);
            }
        }
    }

    private void refreshState(Level pLevel, BlockPos pPos, BlockState pState) {

        int current_power = pState.getValue(POWER);
        int input = getInputSignal(pLevel, pPos, pState);

        if (current_power != input) {
            pLevel.setBlock(pPos, pState.setValue(POWER, input), 3);
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

        int input = getInputSignal(pLevel, place_pos, state);

        return state.setValue(POWER, input);
    }

    @Override
    public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return pBlockState.getValue(FACING_DIRECTION).getOpposite() == pSide ? this.getOutputSignal(pBlockState) : 0;
    }

    public int getOutputSignal(BlockState pState) {

        boolean round_up = pState.getValue(ROUND_UP);

        int input = pState.getValue(POWER);
        int divider = pState.getValue(DIVIDER);

        float output = (float) input / (float) divider;

        return round_up ? (int) Math.ceil(output) : (int) Math.floor(output);
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
}
