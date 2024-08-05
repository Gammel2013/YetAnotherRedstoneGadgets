package com.gammel2012.yetanotherredstonegadgets.providers;

import com.gammel2012.yetanotherredstonegadgets.registers.ModBlocks;
import com.gammel2012.yetanotherredstonegadgets.registers.ModItems;
import com.gammel2012.yetanotherredstonegadgets.YetAnotherRedstoneGadgets;
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
        super(output, YetAnotherRedstoneGadgets.MODID, LOCALE);
    }

    @Override
    protected void addTranslations() {

        // Mod name
        add("itemGroup.YetAnotherRedstoneGadgets", "Yet Another Redstone Gadgets (YARG)");

        // Creative tab
        add("itemGroup.YetAnotherRedstoneGadgets.tab", "Yet Another Redstone Gadgets");

        // Display messages for the divider
        add("block.yargmod.divider.round_up", "Rounding up");
        add("block.yargmod.divider.round_down", "Rounding down");
        add("block.yargmod.divider.divider", "Dividing by %1$s");

        add("block.yargmod.dial.power", "Output strength: %1$s");

        add("block.yargmod.longobserver.range", "Observing block %1$sm in front");
        add("block.yargmod.calibratedobserver.filter", "Observing property '%1$s'");
        add("block.yargmod.calibratedobserver.nothingtosee", "The block in front has no observable properties");

        // Normal blocks
        add(ModBlocks.PURPLE_PROPAGATING_REDSTONE_LAMP_BLOCK, "Purple Propagating Redstone Lamp");
        add(ModBlocks.BLUE_PROPAGATING_REDSTONE_LAMP_BLOCK, "Blue Propagating Redstone Lamp");
        add(ModBlocks.RED_PROPAGATING_REDSTONE_LAMP_BLOCK, "Red Propagating Redstone Lamp");
        add(ModBlocks.REDSTONE_DIVIDER_BLOCK, "Redstone Divider");
        add(ModBlocks.REDSTONE_DIAL_BLOCK, "Redstone Dial");
        add(ModBlocks.REDSTONE_DIAL_LAMP_BLOCK, "Redstone Dial Lamp");
        add(ModBlocks.SEVEN_SEGMENT_LAMP_BLOCK, "Seven Segment Lamp");
        add(ModBlocks.LONG_RANGE_OBSERVER_BLOCK, "Long Range Observer");
        add(ModBlocks.CALIBRATED_OBSERVER_BLOCK, "Calibrated Observer");

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
