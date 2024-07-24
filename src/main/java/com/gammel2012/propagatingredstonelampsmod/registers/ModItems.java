package com.gammel2012.propagatingredstonelampsmod.registers;

import com.gammel2012.propagatingredstonelampsmod.PropagatingRedstoneLampsMod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(PropagatingRedstoneLampsMod.MODID);

    public static final DeferredItem<BlockItem> PURPLE_PROPAGATING_REDSTONE_LAMP_ITEM = registerSimpleBlockItem(ModBlocks.PURPLE_PROPAGATING_REDSTONE_LAMP_BLOCK);
    public static final DeferredItem<BlockItem> BLUE_PROPAGATING_REDSTONE_LAMP_ITEM = registerSimpleBlockItem(ModBlocks.BLUE_PROPAGATING_REDSTONE_LAMP_BLOCK);
    public static final DeferredItem<BlockItem> RED_PROPAGATING_REDSTONE_LAMP_ITEM = registerSimpleBlockItem(ModBlocks.RED_PROPAGATING_REDSTONE_LAMP_BLOCK);

    public static final DeferredItem<BlockItem> REDSTONE_DIVIDER_ITEM = registerSimpleBlockItem(ModBlocks.REDSTONE_DIVIDER_BLOCK);

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }

    private static DeferredItem<BlockItem> registerSimpleBlockItem(DeferredBlock<Block> block, Item.Properties props) {
        DeferredItem<BlockItem> blockItem = ITEMS.registerSimpleBlockItem(block, props);
        return blockItem;
    }

    private static DeferredItem<BlockItem> registerSimpleBlockItem(DeferredBlock<Block> block) {
        return registerSimpleBlockItem(block, new Item.Properties());
    }
}
