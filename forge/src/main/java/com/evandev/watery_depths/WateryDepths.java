package com.evandev.watery_depths;

import com.evandev.watery_depths.client.ClientConfigSetup;
import com.evandev.watery_depths.module.ModBlocks;
import com.evandev.watery_depths.module.ModItems;
import com.evandev.watery_depths.registration.holders.BlockDataHolder;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
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
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CommonClass.init();

        ModBlocks.registerBlocks(
                (id, blockSupplier) -> BLOCKS.register(id.getPath(), blockSupplier),
                (id, itemSupplier) -> ITEMS.register(id.getPath(), itemSupplier)
        );

        ModItems.registerItems(
                (id, itemSupplier) -> ITEMS.register(id.getPath(), itemSupplier)
        );

        modEventBus.addListener(this::commonSetup);
        if (FMLEnvironment.dist.isClient()) {
            ClientConfigSetup.register(ModLoadingContext.get().getActiveContainer());
            modEventBus.addListener(this::clientSetup);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(CommonClass::commonSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            BlockDataHolder.getCutoutBlocks().forEach(holder -> {
                ItemBlockRenderTypes.setRenderLayer(holder.get(), RenderType.cutout());
            });
        });
    }
}