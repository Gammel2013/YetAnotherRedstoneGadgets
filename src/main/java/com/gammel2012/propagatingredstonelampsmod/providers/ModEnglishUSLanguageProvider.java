package com.gammel2012.propagatingredstonelampsmod.providers;

import com.gammel2012.propagatingredstonelampsmod.registers.ModBlocks;
import com.gammel2012.propagatingredstonelampsmod.registers.ModItems;
import com.gammel2012.propagatingredstonelampsmod.PropagatingRedstoneLampsMod;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import org.apache.commons.lang3.text.WordUtils;

import java.util.HashSet;
import java.util.Set;

public class ModEnglishUSLanguageProvider extends LanguageProvider {
    private static final String LOCALE = "en_us";

    private Set<String> known_keys = new HashSet<>();

    public ModEnglishUSLanguageProvider(PackOutput output) {
        super(output, PropagatingRedstoneLampsMod.MODID, LOCALE);
    }

    @Override
    protected void addTranslations() {

        // Mod name
        add("itemGroup.PropagatingRedstoneLamps", "Propagating Redstone Lamps Mod");

        // Normal blocks
        add(ModBlocks.PURPLE_PROPAGATING_REDSTONE_LAMP_BLOCK, "Purple Propagating Redstone Lamp");
        add(ModBlocks.BLUE_PROPAGATING_REDSTONE_LAMP_BLOCK, "Blue Propagating Redstone Lamp");
        add(ModBlocks.RED_PROPAGATING_REDSTONE_LAMP_BLOCK, "Red Propagating Redstone Lamp");
        add(ModBlocks.REDSTONE_DIVIDER_BLOCK, "Redstone Divider");

        checkMissingTranslations();
    }

    public void add(DeferredBlock<Block> dBlock, String name) {
        add(dBlock.get(), name);
        known_keys.add(dBlock.get().getDescriptionId());
    }

    public void add(DeferredItem<?> dItem, String name) {
        add(dItem.get(), name);
        known_keys.add(dItem.get().getDescriptionId());
    }

    public String dyedName(DyeColor color, String name) {
        String color_name = color.getName().replace('_', ' ');
        String capitalized_color_name = WordUtils.capitalize(color_name);

        return capitalized_color_name + " " + name;
    }

    protected void checkMissingTranslations() {

        // Check items
        for (DeferredHolder<Item, ? extends Item> dItem : ModItems.ITEMS.getEntries()) {
            Item item = dItem.get();
            String translation_key = item.getDescriptionId();
            if (!(item instanceof BlockItem) && !(known_keys.contains(translation_key))) {
                throw new RuntimeException("Translation key " + translation_key + " for language " + LOCALE + " doesn't have a translation");
            }
        }

        // Check blocks
        for (DeferredHolder<Block, ? extends Block> dBlock : ModBlocks.BLOCKS.getEntries()) {
            Block block = dBlock.get();
            String translation_key = block.getDescriptionId();
            if (!(known_keys.contains(translation_key))) {
                throw new RuntimeException("Translation key " + translation_key + " for language " + LOCALE + " doesn't have a translation");
            }
        }
    }
}
