package com.gammel2012.yetanotherredstonegadgets.registers;

import com.gammel2012.yetanotherredstonegadgets.YetAnotherRedstoneGadgets;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(YetAnotherRedstoneGadgets.MODID);

    public static final DeferredItem<BlockItem> PURPLE_PROPAGATING_REDSTONE_LAMP_ITEM = registerSimpleBlockItem(ModBlocks.PURPLE_PROPAGATING_REDSTONE_LAMP_BLOCK);
    public static final DeferredItem<BlockItem> BLUE_PROPAGATING_REDSTONE_LAMP_ITEM = registerSimpleBlockItem(ModBlocks.BLUE_PROPAGATING_REDSTONE_LAMP_BLOCK);
    public static final DeferredItem<BlockItem> RED_PROPAGATING_REDSTONE_LAMP_ITEM = registerSimpleBlockItem(ModBlocks.RED_PROPAGATING_REDSTONE_LAMP_BLOCK);

    public static final DeferredItem<BlockItem> REDSTONE_DIAL_LAMP_ITEM = registerSimpleBlockItem(ModBlocks.REDSTONE_DIAL_LAMP_BLOCK);
    public static final DeferredItem<BlockItem> SEVEN_SEGMENT_LAMP_ITEM = registerSimpleBlockItem(ModBlocks.SEVEN_SEGMENT_LAMP_BLOCK);

    public static final DeferredItem<BlockItem> REDSTONE_DIVIDER_ITEM = registerSimpleBlockItem(ModBlocks.REDSTONE_DIVIDER_BLOCK);
    public static final DeferredItem<BlockItem> REDSTONE_DIAL_ITEM = registerSimpleBlockItem(ModBlocks.REDSTONE_DIAL_BLOCK);

    public static final DeferredItem<BlockItem> LONG_RANGE_OBSERVER_ITEM = registerSimpleBlockItem(ModBlocks.LONG_RANGE_OBSERVER_BLOCK);
    public static final DeferredItem<BlockItem> CALIBRATED_OBSERVER_ITEM = registerSimpleBlockItem(ModBlocks.CALIBRATED_OBSERVER_BLOCK);

    public static final DeferredItem<BlockItem> REGION_ANALOG_READER_ITEM = registerSimpleBlockItem(ModBlocks.REGION_ANALOG_READER_BLOCK);

    public static final DeferredItem<BlockItem> AMETHYST_RESONATOR_ITEM = registerSimpleBlockItem(ModBlocks.AMETHYST_RESONATOR_BLOCK);

    public static final DeferredItem<Item> REDSTONE_INFUSED_AMETHYST_SHARD_ITEM = registerSimpleItem(
            "redstone_infused_amethyst_shard"
    );

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

    private static DeferredItem<Item> registerSimpleItem(String name, Item.Properties properties) {
        return  ITEMS.registerSimpleItem(name, properties);
    }

    private static DeferredItem<Item> registerSimpleItem(String name) {
        return  registerSimpleItem(name, new Item.Properties());
    }
}
