package com.gammel2012.utils;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredItem;
import org.antlr.v4.runtime.misc.OrderedHashSet;

import java.util.Arrays;
import java.util.Collection;

public class DeferredItemSet extends OrderedHashSet<DeferredItem<? extends Item>> implements ItemLikeSet {

    public void add(DeferredItem<? extends Item>... items) {
        this.addAll(Arrays.asList(items));
    }

    public void addCollection(Collection<DeferredItem<Item>> items) {
        this.addAll(items);
    }

    public void addBlockCollection(Collection<DeferredItem<BlockItem>> items) {
        this.addAll(items);
    }

    public ItemStackSet getDefaultInstances() {
        ItemStackSet result = new ItemStackSet();
        for (DeferredItem<? extends Item> i : elements()) {
            result.addDefault(i);
        }
        return result;
    }

    public ItemSet toItemSet() {
        ItemSet result = new ItemSet();

        for (DeferredItem<? extends Item> i : elements()) {
            result.add(i.asItem());
        }
        return result;
    }

    @Override
    public Ingredient toIngredient() {
        return Ingredient.of(elements().toArray(new ItemLike[0]));
    }
}
