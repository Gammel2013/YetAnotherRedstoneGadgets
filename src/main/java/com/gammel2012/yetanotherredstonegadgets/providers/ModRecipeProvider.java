package com.gammel2012.yetanotherredstonegadgets.providers;

import com.gammel2012.yetanotherredstonegadgets.YetAnotherRedstoneGadgets;
import com.gammel2012.yetanotherredstonegadgets.registers.ModItems;
import com.gammel2012.utils.providers.BaseRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

public class ModRecipeProvider extends BaseRecipeProvider {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput, YetAnotherRedstoneGadgets.MODID);
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

        shaped(
                toResourceLocation("redstone_divider"),
                pRecipeOutput,
                ModItems.REDSTONE_DIVIDER_ITEM.get(),
                1,
                " r ",
                "rrr",
                "srs",
                new ShapedRecipeIngredient('r', Items.REDSTONE_TORCH),
                new ShapedRecipeIngredient('s', Tags.Items.STONE)
        );

        shaped(
                toResourceLocation("redstone_dial"),
                pRecipeOutput,
                ModItems.REDSTONE_DIAL_ITEM.get(),
                1,
                "rrr",
                "rtr",
                "sss",
                new ShapedRecipeIngredient('s', Tags.Items.STONE),
                new ShapedRecipeIngredient('r', Tags.Items.DUSTS_REDSTONE),
                new ShapedRecipeIngredient('t', Items.REDSTONE_TORCH)
        );

        shaped(
                toResourceLocation("dial_lamp"),
                pRecipeOutput,
                ModItems.REDSTONE_DIAL_LAMP_ITEM.get(),
                1,
                "rrr",
                "rgr",
                "",
                new ShapedRecipeIngredient('r', Tags.Items.DUSTS_REDSTONE),
                new ShapedRecipeIngredient('g', Items.GLOWSTONE)
        );

        shaped(
                toResourceLocation("seven_segment_lamp"),
                pRecipeOutput,
                ModItems.SEVEN_SEGMENT_LAMP_ITEM.get(),
                1,
                "qrq",
                "rgr",
                " r ",
                new ShapedRecipeIngredient('q', Tags.Items.GEMS_QUARTZ),
                new ShapedRecipeIngredient('r', Tags.Items.DUSTS_REDSTONE),
                new ShapedRecipeIngredient('g', Items.GLOWSTONE)
        );

        shaped(
                toResourceLocation("long_range_observer"),
                pRecipeOutput,
                ModItems.LONG_RANGE_OBSERVER_ITEM.get(),
                1,
                " e ",
                "eoe",
                " e ",
                new ShapedRecipeIngredient('e', Items.ECHO_SHARD),
                new ShapedRecipeIngredient('o', Items.OBSERVER)
        );
    }
}
