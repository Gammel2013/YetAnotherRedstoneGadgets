package com.gammel2012.propagatingredstonelampsmod.providers;

import com.gammel2012.propagatingredstonelampsmod.registers.ModItems;
import com.gammel2012.propagatingredstonelampsmod.PropagatingRedstoneLampsMod;
import com.gammel2012.utils.providers.BaseRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

public class ModRecipeProvider extends BaseRecipeProvider {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput, PropagatingRedstoneLampsMod.MODID);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        shapeless(
                toResourceLocation("propagating_redstone_lamp"),
                pRecipeOutput,
                ModItems.PURPLE_PROPAGATING_REDSTONE_LAMP_ITEM.get(),
                1,
                new ShapelessRecipeIngredient(Items.REDSTONE_LAMP, 1),
                new ShapelessRecipeIngredient(Tags.Items.GEMS_AMETHYST, 1)
        );

        shapeless(
                toResourceLocation("red_propagating_redstone_lamp"),
                pRecipeOutput,
                ModItems.RED_PROPAGATING_REDSTONE_LAMP_ITEM.get(),
                1,
                new ShapelessRecipeIngredient(ModItems.PURPLE_PROPAGATING_REDSTONE_LAMP_ITEM.get(), 1)
        );

        shapeless(
                toResourceLocation("blue_propagating_redstone_lamp"),
                pRecipeOutput,
                ModItems.BLUE_PROPAGATING_REDSTONE_LAMP_ITEM.get(),
                1,
                new ShapelessRecipeIngredient(ModItems.RED_PROPAGATING_REDSTONE_LAMP_ITEM.get(), 1)
        );

        shapeless(
                toResourceLocation("purple_propagating_redstone_lamp"),
                pRecipeOutput,
                ModItems.PURPLE_PROPAGATING_REDSTONE_LAMP_ITEM.get(),
                1,
                new ShapelessRecipeIngredient(ModItems.BLUE_PROPAGATING_REDSTONE_LAMP_ITEM.get(), 1)
        );
    }
}
