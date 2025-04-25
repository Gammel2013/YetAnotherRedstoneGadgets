package com.gammel2012.yetanotherredstonegadgets.providers;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends LootTableProvider {

    public ModLootTableProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(pOutput, Set.of(), List.of(), registries);
    }

    @Override
    public @NotNull List<SubProviderEntry> getTables()
    {
        return ImmutableList.of(
                new SubProviderEntry(ModBlockDropSubprovider::new, LootContextParamSets.BLOCK)
        );
    }
}
