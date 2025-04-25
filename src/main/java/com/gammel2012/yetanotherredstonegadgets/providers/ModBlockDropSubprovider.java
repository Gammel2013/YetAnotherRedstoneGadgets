package com.gammel2012.yetanotherredstonegadgets.providers;

import com.gammel2012.yetanotherredstonegadgets.registers.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModBlockDropSubprovider extends BaseLootSubprovider {
    private BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output;

    public ModBlockDropSubprovider(HolderLookup.Provider registries) {
        super(registries);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> pOutput) {
        this.output = pOutput;

        addSelfDroppingBlock(ModBlocks.PURPLE_PROPAGATING_REDSTONE_LAMP_BLOCK);
        addSelfDroppingBlock(ModBlocks.BLUE_PROPAGATING_REDSTONE_LAMP_BLOCK);
        addSelfDroppingBlock(ModBlocks.RED_PROPAGATING_REDSTONE_LAMP_BLOCK);

        addSelfDroppingBlock(ModBlocks.REDSTONE_DIAL_LAMP_BLOCK);
        addSelfDroppingBlock(ModBlocks.SEVEN_SEGMENT_LAMP_BLOCK);

        addSelfDroppingBlock(ModBlocks.REDSTONE_DIVIDER_BLOCK);
        addSelfDroppingBlock(ModBlocks.REDSTONE_DIAL_BLOCK);
        addSelfDroppingBlock(ModBlocks.LONG_RANGE_OBSERVER_BLOCK);
        addSelfDroppingBlock(ModBlocks.CALIBRATED_OBSERVER_BLOCK);
        addSelfDroppingBlock(ModBlocks.AMETHYST_RESONATOR_BLOCK);
        addSelfDroppingBlock(ModBlocks.REGION_ANALOG_READER_BLOCK);
    }

    public LootPool.Builder dropSelf(DeferredBlock<Block> dBlock) {
        Item item = dBlock.get().asItem();
        return poolWithFixedItems(item).when(ExplosionCondition.survivesExplosion());
    }

    public ResourceKey<LootTable> getTableKey(DeferredBlock<Block> dBlock) {
        String path = dBlock.getId().getPath();
        return toResourceKey(path);
    }

    @Override
    public LootContextParamSet getLootContextParamSet() {
        return LootContextParamSets.BLOCK;
    }

    public void addTable(DeferredBlock<Block> dBlock, LootTable.Builder table) {
        ResourceKey<LootTable> key = getTableKey(dBlock);
        this.output.accept(key, table);
    }

    public void addSelfDroppingBlock(DeferredBlock<Block> dBlock, LootPool.Builder... bonusDrops) {
        ArrayList<LootPool.Builder> pool_list = new ArrayList<>(Arrays.asList(bonusDrops));
        pool_list.add(dropSelf(dBlock));

        LootPool.Builder[] pools = pool_list.toArray(new LootPool.Builder[0]);

        LootTable.Builder table = makeTable(pools);
        addTable(dBlock, table);
    }
}
