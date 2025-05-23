package com.gammel2012.yetanotherredstonegadgets.providers;

import com.gammel2012.utils.providers.BaseItemModelProvider;
import com.gammel2012.yetanotherredstonegadgets.YetAnotherRedstoneGadgets;
import com.gammel2012.yetanotherredstonegadgets.registers.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends BaseItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, YetAnotherRedstoneGadgets.MODID, existingFileHelper);
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

        cubeAll(
                getItemName(ModItems.SEVEN_SEGMENT_LAMP_ITEM.get()),
                blockLoc(ModItems.SEVEN_SEGMENT_LAMP_ITEM.get()).withSuffix("_off")
        );

        buildBasicItem(ModItems.REDSTONE_DIVIDER_ITEM);
        buildBasicItem(ModItems.REDSTONE_DIAL_ITEM);

        withExistingParent(
                getItemName(ModItems.LONG_RANGE_OBSERVER_ITEM.get()),
                blockLoc(ModItems.LONG_RANGE_OBSERVER_ITEM.get()).withSuffix("_off")
        );

        withExistingParent(
                getItemName(ModItems.CALIBRATED_OBSERVER_ITEM.get()),
                blockLoc(ModItems.CALIBRATED_OBSERVER_ITEM.get()).withSuffix("_off")
        );

        withExistingParent(
                getItemName(ModItems.AMETHYST_RESONATOR_ITEM.get()),
                blockLoc(ModItems.AMETHYST_RESONATOR_ITEM.get())
        );

        buildBasicItem(ModItems.REDSTONE_INFUSED_AMETHYST_SHARD_ITEM);

        withExistingParent(
                getItemName(ModItems.REGION_ANALOG_READER_ITEM.get()),
                blockLoc(ModItems.REGION_ANALOG_READER_ITEM.get())
        );
    }
}
