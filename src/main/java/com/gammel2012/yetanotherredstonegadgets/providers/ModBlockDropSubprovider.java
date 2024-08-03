package com.gammel2012.yetanotherredstonegadgets.providers;

import com.gammel2012.yetanotherredstonegadgets.registers.ModBlocks;
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
import java.util.function.BiConsumer;

public class ModBlockDropSubprovider extends BaseLootSubprovider {
    private BiConsumer<ResourceLocation, LootTable.Builder> output;

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> pOutput) {
        this.output = pOutput;

        addSelfDroppingBlock(ModBlocks.PURPLE_PROPAGATING_REDSTONE_LAMP_BLOCK);
        addSelfDroppingBlock(ModBlocks.BLUE_PROPAGATING_REDSTONE_LAMP_BLOCK);
        addSelfDroppingBlock(ModBlocks.RED_PROPAGATING_REDSTONE_LAMP_BLOCK);

        addSelfDroppingBlock(ModBlocks.REDSTONE_DIAL_LAMP_BLOCK);
        addSelfDroppingBlock(ModBlocks.SEVEN_SEGMENT_LAMP_BLOCK);

        addSelfDroppingBlock(ModBlocks.REDSTONE_DIVIDER_BLOCK);
        addSelfDroppingBlock(ModBlocks.REDSTONE_DIAL_BLOCK);
    }

    public LootPool.Builder dropSelf(DeferredBlock<Block> dBlock) {
        Item item = dBlock.get().asItem();
        return poolWithFixedItems(item).when(ExplosionCondition.survivesExplosion());
    }

    public ResourceLocation getTableLocation(DeferredBlock<Block> dBlock) {
        String path = dBlock.getId().getPath();
        return toResourceLocation(path);
    }

    @Override
    public LootContextParamSet getLootContextParamSet() {
        return LootContextParamSets.BLOCK;
    }

    public void addTable(DeferredBlock<Block> dBlock, LootTable.Builder table) {
        ResourceLocation loc = getTableLocation(dBlock);
        this.output.accept(loc, table);
    }

    public void addSelfDroppingBlock(DeferredBlock<Block> dBlock, LootPool.Builder... bonusDrops) {
        ArrayList<LootPool.Builder> pool_list = new ArrayList<>(Arrays.asList(bonusDrops));
        pool_list.add(dropSelf(dBlock));

        LootPool.Builder[] pools = pool_list.toArray(new LootPool.Builder[0]);

        LootTable.Builder table = makeTable(pools);
        addTable(dBlock, table);
    }
}
