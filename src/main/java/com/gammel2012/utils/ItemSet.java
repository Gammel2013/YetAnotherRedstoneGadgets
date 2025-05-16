package com.gammel2012.utils;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.antlr.v4.runtime.misc.OrderedHashSet;

import java.util.Collection;

public class ItemSet extends OrderedHashSet<Item> implements ItemLikeSet {

    public void add(ItemLike... items) {
        for (ItemLike item : items) {
            add(item.asItem());
        }
    }

    public void addCollection(Collection<ItemLike> items) {
        for (ItemLike item : items) {
            add(item.asItem());
        }
    }

    public ItemStackSet getDefaultInstances() {
        ItemStackSet result = new ItemStackSet();
        for (Item i : elements()) {
            result.addDefault(i);
        }
        return result;
    }

    @Override
    public ItemSet toItemSet() {
        return this;
    }

    @Override
    public Ingredient toIngredient() {
        return Ingredient.of(elements().toArray(new Item[0]));
    }
}
