package com.gammel2012.yetanotherredstonegadgetsmod.providers;

import com.gammel2012.yetanotherredstonegadgetsmod.registers.ModItems;
import com.gammel2012.yetanotherredstonegadgetsmod.YetAnotherRedstoneGadgetsMod;
import com.gammel2012.utils.providers.BaseItemModelProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends BaseItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, YetAnotherRedstoneGadgetsMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        cubeAll(
                getItemName(ModItems.PURPLE_PROPAGATING_REDSTONE_LAMP_ITEM.get()),
                blockLoc(ModItems.PURPLE_PROPAGATING_REDSTONE_LAMP_ITEM.get()).withSuffix("_off")
        );

        cubeAll(
                getItemName(ModItems.BLUE_PROPAGATING_REDSTONE_LAMP_ITEM.get()),
                blockLoc(ModItems.BLUE_PROPAGATING_REDSTONE_LAMP_ITEM.get()).withSuffix("_off")
        );

        cubeAll(
                getItemName(ModItems.RED_PROPAGATING_REDSTONE_LAMP_ITEM.get()),
                blockLoc(ModItems.RED_PROPAGATING_REDSTONE_LAMP_ITEM.get()).withSuffix("_off")
        );

        cubeAll(
                getItemName(ModItems.REDSTONE_DIAL_LAMP_ITEM.get()),
                blockLoc(ModItems.REDSTONE_DIAL_LAMP_ITEM.get()).withSuffix("_0")
        );

        buildBasicItem(ModItems.REDSTONE_DIVIDER_ITEM);
        buildBasicItem(ModItems.REDSTONE_DIAL_ITEM);
    }
}
