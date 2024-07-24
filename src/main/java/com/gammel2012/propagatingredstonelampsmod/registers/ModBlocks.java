package com.gammel2012.propagatingredstonelampsmod.registers;

import com.gammel2012.propagatingredstonelampsmod.blocks.ModBlockBehaviourProperties;
import com.gammel2012.propagatingredstonelampsmod.blocks.PropagatingRedstoneLampBlock;
import com.gammel2012.propagatingredstonelampsmod.PropagatingRedstoneLampsMod;
import com.gammel2012.propagatingredstonelampsmod.blocks.RedstoneDividerBlock;
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

    public static final DeferredBlock<Block> PURPLE_PROPAGATING_REDSTONE_LAMP_BLOCK = registerBlock(
            "purple_propagating_redstone_lamp",
            (properties) -> new PropagatingRedstoneLampBlock(properties, 3),
            ModBlockBehaviourProperties.LAMP_PROPERTIES.mapColor(MapColor.COLOR_PURPLE)
    );

    public static final DeferredBlock<Block> BLUE_PROPAGATING_REDSTONE_LAMP_BLOCK = registerBlock(
            "blue_propagating_redstone_lamp",
            (properties) -> new PropagatingRedstoneLampBlock(properties, 2),
            ModBlockBehaviourProperties.LAMP_PROPERTIES.mapColor(MapColor.COLOR_BLUE)
    );

    public static final DeferredBlock<Block> RED_PROPAGATING_REDSTONE_LAMP_BLOCK = registerBlock(
            "red_propagating_redstone_lamp",
            (properties) -> new PropagatingRedstoneLampBlock(properties, 1),
            ModBlockBehaviourProperties.LAMP_PROPERTIES.mapColor(MapColor.COLOR_RED)
    );

    public static final DeferredBlock<Block> REDSTONE_DIVIDER_BLOCK = registerBlock(
            "redstone_divider",
            RedstoneDividerBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(0.3f)
                    .sound(SoundType.STONE)
                    .noOcclusion()
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
