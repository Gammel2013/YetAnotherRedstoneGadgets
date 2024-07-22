package com.gammel2012.propagatingredstonelampsmod.providers;

import com.gammel2012.propagatingredstonelampsmod.registers.ModBlocks;
import com.gammel2012.propagatingredstonelampsmod.PropagatingRedstoneLampsMod;
import com.gammel2012.utils.providers.BaseBlockModelProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockModelProvider extends BaseBlockModelProvider {
    public ModBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PropagatingRedstoneLampsMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        System.out.println("Registering Models");
        cubeAll(
                getBlockName(ModBlocks.PURPLE_PROPAGATING_REDSTONE_LAMP_BLOCK.get()) + "_on",
                blockLoc(ModBlocks.PURPLE_PROPAGATING_REDSTONE_LAMP_BLOCK.get()).withSuffix("_on")
        );
        cubeAll(
                getBlockName(ModBlocks.PURPLE_PROPAGATING_REDSTONE_LAMP_BLOCK.get()) + "_off",
                blockLoc(ModBlocks.PURPLE_PROPAGATING_REDSTONE_LAMP_BLOCK.get()).withSuffix("_off")
        );

        cubeAll(
                getBlockName(ModBlocks.BLUE_PROPAGATING_REDSTONE_LAMP_BLOCK.get()) + "_on",
                blockLoc(ModBlocks.BLUE_PROPAGATING_REDSTONE_LAMP_BLOCK.get()).withSuffix("_on")
        );
        cubeAll(
                getBlockName(ModBlocks.BLUE_PROPAGATING_REDSTONE_LAMP_BLOCK.get()) + "_off",
                blockLoc(ModBlocks.BLUE_PROPAGATING_REDSTONE_LAMP_BLOCK.get()).withSuffix("_off")
        );

        cubeAll(
                getBlockName(ModBlocks.RED_PROPAGATING_REDSTONE_LAMP_BLOCK.get()) + "_on",
                blockLoc(ModBlocks.RED_PROPAGATING_REDSTONE_LAMP_BLOCK.get()).withSuffix("_on")
        );
        cubeAll(
                getBlockName(ModBlocks.RED_PROPAGATING_REDSTONE_LAMP_BLOCK.get()) + "_off",
                blockLoc(ModBlocks.RED_PROPAGATING_REDSTONE_LAMP_BLOCK.get()).withSuffix("_off")
        );
    }
}
