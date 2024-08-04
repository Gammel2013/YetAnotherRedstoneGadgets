package com.gammel2012.yetanotherredstonegadgets.registers;

import com.gammel2012.yetanotherredstonegadgets.YetAnotherRedstoneGadgets;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, YetAnotherRedstoneGadgets.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> REDSTONE_STUFF_TAB = CREATIVE_MODE_TABS.register("yetanotherredstonegadgetsmod", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.YetAnotherRedstoneGadgets.tab")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.PURPLE_PROPAGATING_REDSTONE_LAMP_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.RED_PROPAGATING_REDSTONE_LAMP_ITEM.get());
                output.accept(ModItems.BLUE_PROPAGATING_REDSTONE_LAMP_ITEM.get());
                output.accept(ModItems.PURPLE_PROPAGATING_REDSTONE_LAMP_ITEM.get());
                output.accept(ModItems.REDSTONE_DIAL_LAMP_ITEM.get());
                output.accept(ModItems.REDSTONE_DIVIDER_ITEM.get());
                output.accept(ModItems.REDSTONE_DIAL_ITEM.get());
                output.accept(ModItems.SEVEN_SEGMENT_LAMP_ITEM.get());
                output.accept(ModItems.LONG_RANGE_OBSERVER_ITEM);
            }).build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
