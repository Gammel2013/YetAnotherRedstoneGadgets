package com.gammel2012.yetanotherredstonegadgets;

import com.gammel2012.yetanotherredstonegadgets.providers.*;
import com.gammel2012.yetanotherredstonegadgets.registers.ModBlockEntityTypes;
import com.gammel2012.yetanotherredstonegadgets.registers.ModBlocks;
import com.gammel2012.yetanotherredstonegadgets.registers.ModCreativeTabs;
import com.gammel2012.yetanotherredstonegadgets.registers.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(YetAnotherRedstoneGadgets.MODID)
public class YetAnotherRedstoneGadgets
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "yetanotherredstonegadgets";
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public YetAnotherRedstoneGadgets(IEventBus modEventBus)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        modEventBus.addListener(YetAnotherRedstoneGadgets::gatherData);

        handleRegisters(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (YetAnotherRedstoneGadgets) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        // ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void handleRegisters(IEventBus modEventBus) {
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModBlockEntityTypes.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
        }
    }

    public static void gatherData(GatherDataEvent event) {
        try {
            DataGenerator generator = event.getGenerator();
            PackOutput output = generator.getPackOutput();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
            CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();


            BlockTagsProvider blockTags = new ModBlockTagsProvider(output, lookupProvider, existingFileHelper);

            generator.addProvider(true, new ModBlockModelProvider(output, existingFileHelper));
            generator.addProvider(true, new ModBlockStateProvider(output, existingFileHelper));
            generator.addProvider(true, new ModItemModelProvider(output, existingFileHelper));
            generator.addProvider(true, blockTags);
            generator.addProvider(true, new ModItemTagsProvider(output, lookupProvider, blockTags.contentsGetter()));
            generator.addProvider(true, new ModRecipeProvider(output, lookupProvider));
            generator.addProvider(true, new ModEnglishUSLanguageProvider(output));
            generator.addProvider(true, new ModLootTableProvider(output));
        } catch (RuntimeException e) {
            LOGGER.error("Failed to gather data", e);
        }
    }
}
