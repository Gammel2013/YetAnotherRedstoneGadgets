package com.gammel2012.yetanotherredstonegadgets.blockentities;

import com.gammel2012.utils.ExtendedBlockEntity;
import com.gammel2012.utils.TickingBE;
import com.gammel2012.yetanotherredstonegadgets.blocks.ModBlockProperties;
import com.gammel2012.yetanotherredstonegadgets.blocks.RegionAnalogReaderBlock;
import com.gammel2012.yetanotherredstonegadgets.enums.AnalogReaderArea;
import com.gammel2012.yetanotherredstonegadgets.registers.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;

public class RegionAnalogReaderBlockEntity extends ExtendedBlockEntity implements TickingBE {

    private final int[] signals = new int[125];
    private int current_index = 0;

    private AnalogReaderArea area = AnalogReaderArea.THREE_SQUARE;
    boolean has_ticked = false;

    public RegionAnalogReaderBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntityTypes.REGION_ANALOG_READER.get(), pPos, pBlockState);

        resetSignals();

        int power = pBlockState.getValue(RegionAnalogReaderBlock.ANALOG_POWER);

        AnalogReaderArea area = pBlockState.getValue(RegionAnalogReaderBlock.AREA);
        int volume = area.getVolume();

        for (int i = 0; i < volume; i++) {
            signals[i] = power;
        }
    }

    @Override
    protected void saveClientData(CompoundTag tag) {

    }

    @Override
    protected void loadClientData(CompoundTag tag) {

    }

    @Override
    public void tickServer(Level lvl, BlockPos pos, BlockState st, BlockEntity blockEntity) {
        if (!has_ticked) {
            area = lvl.getBlockState(worldPosition).getValue(ModBlockProperties.ANALOG_READER_AREA);
            has_ticked = true;
        }

        current_index = (current_index + 1) % area.getVolume();

        BlockPos pos_for_checking = getBlockPosAtIndex(current_index, area, st.getValue(ModBlockProperties.ALL_FACING), pos);
        BlockState state = lvl.getBlockState(pos_for_checking);

        int signal = state.hasAnalogOutputSignal() ? state.getAnalogOutputSignal(lvl, pos_for_checking) : -1;

        if (signal != signals[current_index]) {
            signals[current_index] = signal;
            recalculateOutput();
        }
    }

    @Override
    public void tickClient(Level lvl, BlockPos pos, BlockState st, BlockEntity blockEntity) {

    }

    private static BlockPos getBlockPosAtIndex(int index, AnalogReaderArea area, Direction facing_direction, BlockPos reader_pos) {
        Direction x_direction = Direction.NORTH;
        Direction y_direction = Direction.UP;

        switch (facing_direction) {
            case UP, DOWN:
                x_direction = Direction.NORTH;
                y_direction = Direction.EAST;
                break;
            case NORTH, SOUTH:
                x_direction = Direction.EAST;
                y_direction = Direction.UP;
                break;
            case EAST, WEST:
                x_direction = Direction.NORTH;
                y_direction = Direction.UP;
        }

        int subtract = area.getWidth() / 2;

        int z_index = index / (area.getWidth() * area.getHeight());
        int y_index = (index / area.getWidth()) % area.getHeight();
        int x_index = index % area.getWidth();

        BlockPos end_pos = reader_pos.relative(facing_direction, 1 + z_index)
                .relative(x_direction, x_index - subtract)
                .relative(y_direction, y_index - subtract);
        return end_pos;
    }

    private void recalculateOutput() {
        if (this.level == null) {
            return;
        }
        int output = (int) Arrays.stream(signals).filter((i) -> i != -1).average().orElse(0);

        output = Math.max(0, output);
        output = Math.min(15, output);

        BlockState old_state = level.getBlockState(worldPosition);

        if (old_state.getValue(ModBlockProperties.POWER) != output) {
            level.setBlock(worldPosition, old_state.setValue(ModBlockProperties.POWER, output), 3);
        }
    }

    private void resetSignals() {
        Arrays.fill(signals, -1);
        recalculateOutput();
    }

    public void setArea(AnalogReaderArea new_area) {
        if (!area.equals(new_area)) {
            area = new_area;
            resetSignals();
        }
    }
}
