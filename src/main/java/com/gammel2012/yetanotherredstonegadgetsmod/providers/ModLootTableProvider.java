package com.gammel2012.yetanotherredstonegadgetsmod.providers;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class ModLootTableProvider extends LootTableProvider {

    public ModLootTableProvider(PackOutput pOutput) {
        super(pOutput, Set.of(), List.of());
    }

    @Override
    public @NotNull List<SubProviderEntry> getTables()
    {
        return ImmutableList.of(
                new SubProviderEntry(ModBlockDropSubprovider::new, LootContextParamSets.BLOCK)
        );
    }
}
