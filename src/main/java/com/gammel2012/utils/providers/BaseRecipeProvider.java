package com.gammel2012.utils.providers;

import com.gammel2012.utils.DeferredItemSet;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.AndCondition;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public abstract class BaseRecipeProvider extends RecipeProvider {

    protected final String modid;

    public BaseRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> registries, String modid) {
        super(pOutput, registries);
        this.modid = modid;
    }

    public ResourceLocation toResourceLocation(String s) {
        return ResourceLocation.fromNamespaceAndPath(modid, s);
    }

    public static void shaped(
            ResourceLocation rl,
            RecipeOutput pRecipeOutput,
            ItemStack output,
            String row_top,
            String row_middle,
            String row_bottom,
            ShapedRecipeIngredient... ingredients
    ) {
        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output);

        ResourceLocation loc = rl.withPrefix("shaped/");

        List<ICondition> conditions = new ArrayList<>();

        if (!row_top.isEmpty()) {
            builder.pattern(row_top);
        }
        if (!row_middle.isEmpty()) {
            builder.pattern(row_middle);
        }
        if (!row_bottom.isEmpty()) {
            builder.pattern(row_bottom);
        }

        for (ShapedRecipeIngredient sIngredient : ingredients) {
            builder.define(sIngredient.symbol, sIngredient.ingredient);
            builder.unlockedBy(sIngredient.criterion_name, sIngredient.criterion);

            if (sIngredient.condition != null) {
                conditions.add(sIngredient.condition);
            }
        }

        builder.save(pRecipeOutput.withConditions(new AndCondition(conditions)), loc);
    }

    public static void shaped(
            ResourceLocation rl,
            RecipeOutput pRecipeOutput,
            Item output,
            int count,
            String row_top,
            String row_middle,
            String row_bottom,
            ShapedRecipeIngredient... ingredients
    ) {
        ItemStack outputStack = new ItemStack(output, count);
        shaped(
                rl,
                pRecipeOutput,
                outputStack,
                row_top,
                row_middle,
                row_bottom,
                ingredients
        );
    }

    public static void shapeless(
            ResourceLocation rl,
            RecipeOutput pRecipeOutput,
            ItemStack output,
            ShapelessRecipeIngredient... ingredients
    ) {
        ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output);

        ResourceLocation loc = rl.withPrefix("shapeless/");

        List<ICondition> conditions = new ArrayList<>();

        for (ShapelessRecipeIngredient sIngredient : ingredients) {
            builder.requires(sIngredient.ingredient, sIngredient.count);
            builder.unlockedBy(sIngredient.criterion_name, sIngredient.criterion);

            if (sIngredient.condition != null) {
                conditions.add(sIngredient.condition);
            }
        }

        builder.save(pRecipeOutput.withConditions(new AndCondition(conditions)), loc);
    }

    public static void shapeless(
            ResourceLocation rl,
            RecipeOutput pRecipeOutput,
            Item output,
            int count,
            ShapelessRecipeIngredient... ingredients
    ) {
        ItemStack stack = new ItemStack(output, count);
        shapeless(
                rl,
                pRecipeOutput,
                stack,
                ingredients
        );
    }

    public void addShapelessDyeRecipes(
            Map<DyeColor, DeferredItem<BlockItem>> dItems,
            DeferredItem<? extends Item> dBase_Item,
            RecipeOutput recipeOutput,
            DyeColor... exclude_colors
    ) {
        Item base_item = dBase_Item.get();

        DeferredItemSet input_blocks = new DeferredItemSet();
        input_blocks.add(dBase_Item);
        input_blocks.addAll(dItems.values());

        Ingredient inputs = input_blocks.toIngredient();

        for (DyeColor color : DyeColor.values()) {
            // skip any colors we want to exclude
            if (Arrays.asList(exclude_colors).contains(color)) {
                continue;
            }
            // add dye recipes for the rest
            Item output_item = dItems.get(color).get();
            ResourceLocation rl = dItems.get(color).getId().withPrefix("dyed_variants/");
            TagKey<Item> dye = color.getTag();
            shapeless(
                    rl,
                    recipeOutput,
                    output_item,
                    1,
                    new ShapelessRecipeIngredient(inputs, 1),
                    new ShapelessRecipeIngredient(dye, 1)
            );
        }
    }

    public class ShapelessRecipeIngredient extends RecipeIngredient {

        public int count;

        public ShapelessRecipeIngredient(Item item, int count) {
            super(item);
            this.count = count;
        }

        public ShapelessRecipeIngredient(TagKey<Item> tag, int count) {
            super(tag);
            this.count = count;
        }

        public ShapelessRecipeIngredient(Ingredient ingredient, int count) {
            super(ingredient);
            this.count = count;
        }
    }

    public class ShapedRecipeIngredient extends RecipeIngredient {

        public Character symbol;

        public ShapedRecipeIngredient(Character symbol, Item item) {
            super(item);
            this.symbol = symbol;
        }

        public ShapedRecipeIngredient(Character symbol, TagKey<Item> tag) {
            super(tag);
            this.symbol = symbol;
        }

        public ShapedRecipeIngredient(Character symbol, Ingredient ingredient) {
            super(ingredient);
            this.symbol = symbol;
        }
    }

    public class RecipeIngredient {

        public Ingredient ingredient;
        public Criterion<InventoryChangeTrigger.TriggerInstance> criterion;
        public String criterion_name;
        public ICondition condition;

        public RecipeIngredient(Item item) {
            this.ingredient = Ingredient.of(item);
            this.criterion = has(item);
            this.criterion_name = "has_item_" + item.toString();
        }

        public RecipeIngredient(TagKey<Item> tag) {
            this.ingredient = Ingredient.of(tag);
            this.criterion = has(tag);
            this.criterion_name = "has_tag_" + tag.location().toString();
            this.condition = new NotCondition(new TagEmptyCondition(tag));
        }

        public RecipeIngredient(Ingredient ingredient) {
            Item item_from_ingredient = ingredient.getItems()[0].getItem();
            this.ingredient = ingredient;
            this.criterion = has(item_from_ingredient);
            this.criterion_name = "has_tag_" + item_from_ingredient.toString();
        }
    }
}
