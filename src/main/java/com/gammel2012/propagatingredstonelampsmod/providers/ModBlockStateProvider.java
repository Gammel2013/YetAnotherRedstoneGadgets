package com.gammel2012.propagatingredstonelampsmod.providers;

import com.gammel2012.propagatingredstonelampsmod.registers.ModBlocks;
import com.gammel2012.propagatingredstonelampsmod.blocks.PropagatingRedstoneLampBlock;
import com.gammel2012.propagatingredstonelampsmod.PropagatingRedstoneLampsMod;
import com.gammel2012.utils.providers.BaseBlockStateProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BaseBlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, PropagatingRedstoneLampsMod.MODID, exFileHelper);
    }

    @Override
    protected DirectionProperty getHorizontalDirectionProperty() {
        return null;
    }

    @Override
    protected void registerStatesAndModels() {
        propagatingRedstoneLamp(ModBlocks.PURPLE_PROPAGATING_REDSTONE_LAMP_BLOCK);
        propagatingRedstoneLamp(ModBlocks.BLUE_PROPAGATING_REDSTONE_LAMP_BLOCK);
        propagatingRedstoneLamp(ModBlocks.RED_PROPAGATING_REDSTONE_LAMP_BLOCK);
    }

    public void propagatingRedstoneLamp(DeferredBlock<Block> dBlock) {
        Block block = dBlock.get();
        String name = getBlockName(block);

        ModelFile model_on = models().getExistingFile(modLoc("block/" + name + "_on"));
        ModelFile model_off = models().getExistingFile(modLoc("block/" + name + "_off"));
        getVariantBuilder(block).forAllStates(
                state -> {
                    IntegerProperty signal_prop = PropagatingRedstoneLampBlock.SIGNAL_STRENGTH;
                    int strength = state.getValue(signal_prop);

                    if (strength > 0) {
                        return ConfiguredModel.builder()
                                .modelFile(model_on)
                                .build();
                    }
                    return ConfiguredModel.builder()
                            .modelFile(model_off)
                            .build();
                }
        );
    }
}
