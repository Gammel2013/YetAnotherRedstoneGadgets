package com.gammel2012.utils;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class DisplayItemsGeneratorHelper {

    public static CreativeModeTab.DisplayItemsGenerator combine(CreativeModeTab.DisplayItemsGenerator... generators) {
        return (displayParams, output) -> {
            for (CreativeModeTab.DisplayItemsGenerator gen : generators) {
                gen.accept(displayParams, output);
            }
        };
    }

    public static CreativeModeTab.DisplayItemsGenerator makeGenerator(ItemLikeSet input) {

        if (input instanceof ItemStackSet) {
            return (displayParams, output) -> {
                for (ItemStack stack : (ItemStackSet) input) {
                    output.accept(stack.copy());
                }
            };
        } else {
            return (displayParams, output) -> {
                for (ItemStack stack : input.toItemSet().getDefaultInstances()) {
                    output.accept(stack.copy());
                }
            };
        }
    }
}
