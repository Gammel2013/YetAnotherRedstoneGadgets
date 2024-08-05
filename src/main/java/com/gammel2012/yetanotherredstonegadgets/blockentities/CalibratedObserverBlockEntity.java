package com.gammel2012.yetanotherredstonegadgets.blockentities;

import com.gammel2012.utils.ExtendedBlockEntity;
import com.gammel2012.utils.TickingBE;
import com.gammel2012.yetanotherredstonegadgets.blocks.LongRangeObserverBlock;
import com.gammel2012.yetanotherredstonegadgets.blocks.ModBlockProperties;
import com.gammel2012.yetanotherredstonegadgets.registers.ModBlockEntityTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.common.property.Properties;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class CalibratedObserverBlockEntity extends ExtendedBlockEntity implements TickingBE {

    private BlockState current_state;
    private int powered_timer = 0;

    private String observed_property_name = ModBlockProperties.CALIBRATED_OBSERVER_DUMMY.getName();

    public CalibratedObserverBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntityTypes.CALIBRATED_OBSERVER.get(), pPos, pBlockState);
    }

    @Override
    public void tickServer(Level lvl, BlockPos pos, BlockState st, BlockEntity blockEntity) {

        Property<?> observed_property = getMatchingPropertyInFront(lvl, pos, st);

        if (powered_timer > 0) {
            powered_timer -= 1;
        }

        boolean early_return = false;

        if (current_state == null || !current_state.hasProperty(observed_property)) {
            current_state = getStateInFront(lvl, pos, st);
            early_return = true;
        }

        if (observed_property.equals(ModBlockProperties.CALIBRATED_OBSERVER_DUMMY)) {
            early_return = true;
        }

        boolean powered = st.getValue(LongRangeObserverBlock.POWERED);

        if (powered && powered_timer == 0) {
            st = st.setValue(LongRangeObserverBlock.POWERED, false);
            lvl.setBlock(pos, st, 3);
            early_return = true;
        }

        if (early_return) return;

        BlockState observed_state = getStateInFront(lvl, pos, st);

        if (
                observed_state.hasProperty(observed_property)
                        && observed_state.getValue(observed_property) != current_state.getValue(observed_property)
        ) {
                st = st.setValue(LongRangeObserverBlock.POWERED, true);
                lvl.setBlock(pos, st, 3);

                powered_timer = 2;
                current_state = observed_state;
        }
    }

    @Override
    public void tickClient(Level lvl, BlockPos pos, BlockState st, BlockEntity blockEntity) {

    }

    public Collection<Property<?>> getPossibleProperties(Level lvl, BlockPos pos, BlockState st) {
        BlockState observed_state = getStateInFront(lvl, pos, st);
        return observed_state.getProperties();
    }

    public BlockState getStateInFront(Level lvl, BlockPos pos, BlockState st) {
        Direction facing = st.getValue(LongRangeObserverBlock.FACING);
        BlockPos observed_pos = pos.relative(facing);

        BlockState observed_state = lvl.getBlockState(observed_pos);
        return observed_state;
    }

    public String getObservedProperty() {
        return observed_property_name;
    }

    public void setObservedProperty(Property<?> observed_property) {
        this.observed_property_name = observed_property.getName();
    }

    public void setObservedProperty(String observed_property) {
        this.observed_property_name = observed_property;
    }

    public Property<?> getMatchingPropertyInFront(Level lvl, BlockPos pos, BlockState st){
        for (Property<?> p : getPossibleProperties(lvl, pos, st)) {
            if (p.getName().equals(observed_property_name)) {
                return p;
            }
        }
        return ModBlockProperties.CALIBRATED_OBSERVER_DUMMY;
    }

    protected void saveClientData(CompoundTag tag) {
        tag.put("observed_property", StringTag.valueOf(observed_property_name));
    }

    protected void loadClientData(CompoundTag tag) {
        observed_property_name = tag.getString("observed_property");
    }
}
