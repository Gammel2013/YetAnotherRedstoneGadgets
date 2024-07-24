package com.gammel2012.propagatingredstonelampsmod.providers;

import com.gammel2012.propagatingredstonelampsmod.registers.ModItems;
import com.gammel2012.propagatingredstonelampsmod.PropagatingRedstoneLampsMod;
import com.gammel2012.utils.providers.BaseItemModelProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends BaseItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PropagatingRedstoneLampsMod.MODID, existingFileHelper);
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

        buildBasicItem(ModItems.REDSTONE_DIVIDER_ITEM);
    }
}
