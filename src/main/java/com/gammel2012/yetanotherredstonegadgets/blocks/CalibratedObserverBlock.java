package com.gammel2012.yetanotherredstonegadgets.blocks;

import com.gammel2012.utils.TickingEntityBlock;
import com.gammel2012.yetanotherredstonegadgets.blockentities.CalibratedObserverBlockEntity;
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
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;

public class CalibratedObserverBlock extends TickingEntityBlock {

    public static final DirectionProperty FACING = ModBlockProperties.ALL_FACING;
    public static final BooleanProperty POWERED = ModBlockProperties.POWERED;

    public CalibratedObserverBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(
                this.getStateDefinition().any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(POWERED, false)
        );
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CalibratedObserverBlockEntity(pPos, pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        pBuilder.add(POWERED);
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

    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHit) {
        if (!pPlayer.getAbilities().mayBuild) {
            return InteractionResult.PASS;
        }

        // Cycle through possible blockstate values to observe
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (be instanceof CalibratedObserverBlockEntity calibrated_observer) {

            Collection<Property<?>> possible_properties = calibrated_observer.getPossibleProperties(pLevel, pPos, pState);
            Property<?> current_property = calibrated_observer.getMatchingPropertyInFront(pLevel, pPos, pState);

            Property<?>[] props = possible_properties.toArray(new Property<?>[0]);

            if (props.length == 0) {
                // Display status message
                if (pLevel.isClientSide) {
                    String message = "block.yargmod.calibratedobserver.nothingtosee";
                    pPlayer.displayClientMessage(Component.translatable(message), true);
                }

                return InteractionResult.PASS;
            }

            Property<?> new_prop;

            if (Arrays.stream(props).noneMatch(current_property::equals)) {
                new_prop = props[0];
            } else {
                int index = Arrays.asList(props).indexOf(current_property);
                index = (index + 1) % props.length;
                new_prop = props[index];
            }

            // Play sound effect
            float pitch = 0.5f;
            pLevel.playSound(pPlayer, pPos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 0.3F, pitch);

            // Display status message
            if (pLevel.isClientSide) {
                String message = "block.yargmod.calibratedobserver.filter";
                pPlayer.displayClientMessage(Component.translatable(message, new_prop.getName()), true);
            }

            calibrated_observer.setObservedProperty(new_prop);

            // Update block
            pLevel.setBlock(pPos, pState, 3);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return InteractionResult.PASS;
    }
}
