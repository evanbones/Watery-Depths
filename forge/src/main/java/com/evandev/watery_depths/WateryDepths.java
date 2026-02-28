package com.evandev.watery_depths;

import com.evandev.watery_depths.client.ClientConfigSetup;
import com.evandev.watery_depths.module.ModBlocks;
import com.evandev.watery_depths.module.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(Constants.MOD_ID)
public class WateryDepths {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

    public WateryDepths() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CommonClass.init();

        ModBlocks.registerBlocks(
                (id, block) -> BLOCKS.register(id.getPath(), () -> block),
                (id, item) -> ITEMS.register(id.getPath(), () -> item)
        );

        ModItems.registerItems(
                (id, item) -> ITEMS.register(id.getPath(), () -> item)
        );

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        if (FMLEnvironment.dist.isClient()) {
            ClientConfigSetup.register(ModLoadingContext.get().getActiveContainer());
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(CommonClass::commonSetup);
    }
}