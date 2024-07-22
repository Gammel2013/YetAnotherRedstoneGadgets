package com.gammel2012.propagatingredstonelampsmod.registers;

import com.gammel2012.propagatingredstonelampsmod.PropagatingRedstoneLampsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PropagatingRedstoneLampsMod.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("propagatingredstonelampsmod", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.PropagatingRedstoneLamps")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.PURPLE_PROPAGATING_REDSTONE_LAMP_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.RED_PROPAGATING_REDSTONE_LAMP_ITEM.get());
                output.accept(ModItems.BLUE_PROPAGATING_REDSTONE_LAMP_ITEM.get());
                output.accept(ModItems.PURPLE_PROPAGATING_REDSTONE_LAMP_ITEM.get());// Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
