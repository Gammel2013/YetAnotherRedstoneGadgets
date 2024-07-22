package com.gammel2012.utils.providers;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

public abstract class BaseItemModelProvider extends ItemModelProvider {
    public BaseItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    public @NotNull String getItemName(Item item) {
        String name = BuiltInRegistries.ITEM.getKey(item).toString().replace(modid + ":", "");
        return name;
    }

    public void buildBasicItem(DeferredItem<?> dItem) {
        Item item = dItem.get();
        String name = getItemName(item);
        getBuilder(name)
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", "item/" + name);
    }

    public void copyBlockModel(DeferredItem<BlockItem> dBlockItem) {
        Item item = dBlockItem.get();
        String name = getItemName(item);
        getBuilder(name).parent(getExistingFile(modLoc("block/" + name)));
    }

    public ResourceLocation blockLoc(Item item) {
        String name = getItemName(item);
        return modLoc("block/" + name);
    }
}
