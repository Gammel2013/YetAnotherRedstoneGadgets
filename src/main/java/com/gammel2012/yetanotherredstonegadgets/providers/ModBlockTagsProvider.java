package com.gammel2012.yetanotherredstonegadgets.providers;

import com.gammel2012.yetanotherredstonegadgets.YetAnotherRedstoneGadgets;
import com.gammel2012.yetanotherredstonegadgets.registers.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, YetAnotherRedstoneGadgets.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        addBlocksToTag(
                BlockTags.MINEABLE_WITH_PICKAXE,
                ModBlocks.LONG_RANGE_OBSERVER_BLOCK.get(),
                ModBlocks.CALIBRATED_OBSERVER_BLOCK.get(),
                ModBlocks.AMETHYST_RESONATOR_BLOCK.get(),
                ModBlocks.REGION_ANALOG_READER_BLOCK.get()
        );
    }

    protected void addBlocksToTag(TagKey<Block> blocktag, Block... blocks) {
        for (Block block : blocks) {
            tag(blocktag).add(block);
        }
    }
}
