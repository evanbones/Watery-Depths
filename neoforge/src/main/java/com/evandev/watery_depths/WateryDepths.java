package com.evandev.watery_depths;

import com.evandev.watery_depths.client.ClientConfigSetup;
import com.evandev.watery_depths.content.ModBlocks;
import com.evandev.watery_depths.content.ModItems;
import com.evandev.watery_depths.registration.holders.BlockDataHolder;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(Constants.MOD_ID)
public class WateryDepths {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, Constants.MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Constants.MOD_ID);

    public WateryDepths() {
        IEventBus modEventBus = ModLoadingContext.get().getActiveContainer().getEventBus();
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