package com.gammel2012.propagatingredstonelampsmod.registers;

import com.gammel2012.propagatingredstonelampsmod.blocks.PropagatingRedstoneLampBlock;
import com.gammel2012.propagatingredstonelampsmod.PropagatingRedstoneLampsMod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(PropagatingRedstoneLampsMod.MODID);

    public static final BlockBehaviour.Properties LAMP_PROPERTIES = BlockBehaviour.Properties.of()
            .sound(SoundType.GLASS)
            .lightLevel((blockState) -> {
                return blockState.getValue(PropagatingRedstoneLampBlock.SIGNAL_STRENGTH) > 0 ? 15 : 0;
            })
            .strength(0.3F)
            .isValidSpawn((state, getter, blockPos, entityType) -> true);

    public static final DeferredBlock<Block> PURPLE_PROPAGATING_REDSTONE_LAMP_BLOCK = registerBlock(
            "purple_propagating_redstone_lamp",
            (properties) -> new PropagatingRedstoneLampBlock(properties, 3),
            LAMP_PROPERTIES.mapColor(MapColor.COLOR_PURPLE)
    );

    public static final DeferredBlock<Block> BLUE_PROPAGATING_REDSTONE_LAMP_BLOCK = registerBlock(
            "blue_propagating_redstone_lamp",
            (properties) -> new PropagatingRedstoneLampBlock(properties, 2),
            LAMP_PROPERTIES.mapColor(MapColor.COLOR_BLUE)
    );

    public static final DeferredBlock<Block> RED_PROPAGATING_REDSTONE_LAMP_BLOCK = registerBlock(
            "red_propagating_redstone_lamp",
            (properties) -> new PropagatingRedstoneLampBlock(properties, 1),
            LAMP_PROPERTIES.mapColor(MapColor.COLOR_RED)
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
