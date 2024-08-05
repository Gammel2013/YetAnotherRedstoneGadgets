package com.gammel2012.yetanotherredstonegadgets.registers;

import com.gammel2012.yetanotherredstonegadgets.YetAnotherRedstoneGadgets;
import com.gammel2012.yetanotherredstonegadgets.blocks.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(YetAnotherRedstoneGadgets.MODID);

    public static final DeferredBlock<Block> PURPLE_PROPAGATING_REDSTONE_LAMP_BLOCK = registerBlock(
            "purple_propagating_redstone_lamp",
            (properties) -> new PropagatingRedstoneLampBlock(properties, 3),
            ModBlockBehaviourProperties.LAMP_PROPERTIES.copy().mapColor(MapColor.COLOR_PURPLE).toProperties()
    );

    public static final DeferredBlock<Block> BLUE_PROPAGATING_REDSTONE_LAMP_BLOCK = registerBlock(
            "blue_propagating_redstone_lamp",
            (properties) -> new PropagatingRedstoneLampBlock(properties, 2),
            ModBlockBehaviourProperties.LAMP_PROPERTIES.copy().mapColor(MapColor.COLOR_BLUE).toProperties()
    );

    public static final DeferredBlock<Block> RED_PROPAGATING_REDSTONE_LAMP_BLOCK = registerBlock(
            "red_propagating_redstone_lamp",
            (properties) -> new PropagatingRedstoneLampBlock(properties, 1),
            ModBlockBehaviourProperties.LAMP_PROPERTIES.copy().mapColor(MapColor.COLOR_RED).toProperties()
    );

    public static final DeferredBlock<Block> REDSTONE_DIVIDER_BLOCK = registerBlock(
            "redstone_divider",
            RedstoneDividerBlock::new,
            ModBlockBehaviourProperties.REDSTONE_COMPONENT_PROPERTIES.toProperties()
    );

    public static final DeferredBlock<Block> REDSTONE_DIAL_BLOCK = registerBlock(
            "redstone_dial",
            RedstoneDialBlock::new,
            ModBlockBehaviourProperties.REDSTONE_COMPONENT_PROPERTIES.toProperties()
    );

    public static final DeferredBlock<Block> REDSTONE_DIAL_LAMP_BLOCK = registerBlock(
            "redstone_dial_lamp",
            RedstoneDialLampBlock::new,
            ModBlockBehaviourProperties.DIAL_LAMP_PROPERTIES.toProperties()
    );

    public static final DeferredBlock<Block> SEVEN_SEGMENT_LAMP_BLOCK = registerBlock(
            "seven_segment_lamp",
            RedstoneDialLampBlock::new,
            ModBlockBehaviourProperties.SEVEN_SEGMENT_LAMP_PROPERTIES.toProperties()
    );

    public static final DeferredBlock<Block> LONG_RANGE_OBSERVER_BLOCK = registerBlock(
            "long_range_observer",
            LongRangeObserverBlock::new,
            ModBlockBehaviourProperties.LONG_RANGE_OBSERVER_PROPERTIES.toProperties()
    );

    public static final DeferredBlock<Block> CALIBRATED_OBSERVER_BLOCK = registerBlock(
            "calibrated_observer",
            CalibratedObserverBlock::new,
            ModBlockBehaviourProperties.LONG_RANGE_OBSERVER_PROPERTIES.toProperties()
    );

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }

    public static DeferredBlock<Block> registerBlock(
            String name,
            Function<BlockBehaviour.Properties, ? extends Block> func,
            BlockBehaviour.Properties props
    ) {
        return BLOCKS.registerBlock(name, func, props);
    }
}
