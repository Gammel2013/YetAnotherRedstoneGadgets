package com.gammel2012.yetanotherredstonegadgets.blocks;

import com.gammel2012.utils.TickingEntityBlock;
import com.gammel2012.yetanotherredstonegadgets.blockentities.RegionAnalogReaderBlockEntity;
import com.gammel2012.yetanotherredstonegadgets.enums.AnalogReaderArea;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RegionAnalogReaderBlock extends TickingEntityBlock {

    public static final DirectionProperty FACING = ModBlockProperties.ALL_FACING;
    public static final IntegerProperty ANALOG_POWER = ModBlockProperties.POWER;
    public static final EnumProperty<AnalogReaderArea> AREA = ModBlockProperties.ANALOG_READER_AREA;

    public RegionAnalogReaderBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(
                this.getStateDefinition().any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(ANALOG_POWER, 0)
                        .setValue(AREA, AnalogReaderArea.THREE_SQUARE)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        pBuilder.add(ANALOG_POWER);
        pBuilder.add(AREA);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState state = this.defaultBlockState().setValue(FACING, pContext.getNearestLookingDirection());

        return state;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new RegionAnalogReaderBlockEntity(pPos, pState);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pBlockState, Level pLevel, BlockPos pPos) {
        return pBlockState.getValue(ANALOG_POWER);
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHit) {
        if (!pPlayer.getAbilities().mayBuild) {
            return InteractionResult.PASS;
        } else {

            pState = pState.cycle(AREA);

            AnalogReaderArea area = pState.getValue(AREA);

            // Play sound effect
            float pitch = 0.5f;
            pLevel.playSound(pPlayer, pPos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 0.3F, pitch);

            // Display status message
            if (pLevel.isClientSide) {
                String message = "block.yargmod.area_reader.area";
                pPlayer.displayClientMessage(Component.translatable(message, area.getSerializedName()), true);
            }

            // Update block
            pLevel.setBlock(pPos, pState, 3);
            ((RegionAnalogReaderBlockEntity) pLevel.getBlockEntity(pPos)).setArea(area);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
    }
}
