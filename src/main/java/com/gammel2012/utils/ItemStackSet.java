package com.gammel2012.utils;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.registries.DeferredItem;
import org.antlr.v4.runtime.misc.OrderedHashSet;

import java.util.Collection;

public class ItemStackSet extends OrderedHashSet<ItemStack> implements ItemLikeSet {

    public void addDefault(ItemLike... items) {
        for (ItemLike item : items) {
            add(item.asItem().getDefaultInstance());
        }
    }

    public void addDefaultCollection(Collection<? extends ItemLike> items) {
        for (ItemLike item : items) {
            add(item.asItem().getDefaultInstance());
        }
    }

    public void add(ItemStack... stacks) {
        for (ItemStack stack : stacks) {
            add(stack);
        }
    }

    public void add(Collection<ItemStack> stacks) {
        for (ItemStack stack : stacks) {
            add(stack);
        }
    }

    @Override
    public ItemSet toItemSet() {
        ItemSet result = new ItemSet();

        for (ItemStack i : elements()) {
            result.add(i.getItem());
        }
        return result;
    }

    @Override
    public Ingredient toIngredient() {
        return Ingredient.of(elements().toArray(new ItemStack[0]));
    }
}
