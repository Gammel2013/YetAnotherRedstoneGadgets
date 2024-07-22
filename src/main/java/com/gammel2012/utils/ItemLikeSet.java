package com.gammel2012.utils;

import net.minecraft.world.item.crafting.Ingredient;

public interface ItemLikeSet {

    ItemSet toItemSet();

    Ingredient toIngredient();
}
