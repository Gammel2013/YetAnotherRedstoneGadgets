package com.gammel2012.yetanotherredstonegadgets.providers;

import com.gammel2012.yetanotherredstonegadgets.YetAnotherRedstoneGadgets;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public abstract class BaseLootSubprovider implements LootTableSubProvider {

    private HolderLookup.Provider registries;

    private static final ResourceKey<? extends Registry<LootTable>> REG_KEY = ResourceKey.createRegistryKey(
            ResourceLocation.fromNamespaceAndPath("", "")
    );

    public  BaseLootSubprovider(HolderLookup.Provider registries) {
        this.registries = registries;
    }

    public abstract LootContextParamSet getLootContextParamSet();

    public static HashMap<LootContextParamSet, String> SUBFOLDER_MAP = new HashMap<>();
    static
    {
        SUBFOLDER_MAP.put(LootContextParamSets.BLOCK, "blocks/");
    }

    public LootPool.Builder poolWithFixedItems(ItemLike... items) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1));
        for (ItemLike item : items) {
            builder.add(LootItem.lootTableItem(item));
        }
        return builder;
    }

    public LootTable.Builder makeTable(LootPool.Builder... pools) {
        LootTable.Builder table = LootTable.lootTable();
        for (LootPool.Builder pool : pools) {
            table = table.withPool(pool);
        }
        table = table.setParamSet(getLootContextParamSet());
        return table;
    }

    public ResourceKey<LootTable> toResourceKey(String path) {
        ResourceLocation loc = toResourceLocation(path);
        return ResourceKey.create(Registries.LOOT_TABLE, loc);
    }

    public ResourceLocation toResourceLocation(String path) {
        String subfolder = SUBFOLDER_MAP.get(getLootContextParamSet());
        return ResourceLocation.fromNamespaceAndPath(YetAnotherRedstoneGadgets.MODID, path).withPrefix(subfolder);
    }
}
