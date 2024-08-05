package com.gammel2012.yetanotherredstonegadgets.registers;

import com.gammel2012.yetanotherredstonegadgets.YetAnotherRedstoneGadgets;
import com.gammel2012.yetanotherredstonegadgets.blockentities.CalibratedObserverBlockEntity;
import com.gammel2012.yetanotherredstonegadgets.blockentities.LongRangeObserverBlockEntity;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, YetAnotherRedstoneGadgets.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LongRangeObserverBlockEntity>> LONG_RANGE_OBSERVER = BLOCK_ENTITY_TYPES.register(
            "long_range_observer",
            () -> new BlockEntityType<>(
                    LongRangeObserverBlockEntity::new,
                    ImmutableSet.of(ModBlocks.LONG_RANGE_OBSERVER_BLOCK.get()),
                    null
            )
    );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CalibratedObserverBlockEntity>> CALIBRATED_OBSERVER = BLOCK_ENTITY_TYPES.register(
            "calibrated_observer",
            () -> new BlockEntityType<>(
                    CalibratedObserverBlockEntity::new,
                    ImmutableSet.of(ModBlocks.CALIBRATED_OBSERVER_BLOCK.get()),
                    null
            )
    );

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITY_TYPES.register(modEventBus);
    }
}
