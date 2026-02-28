package com.evandev.watery_depths;

import com.evandev.watery_depths.module.BlockModuleFabric;
import net.fabricmc.api.ModInitializer;

public class WateryDepths implements ModInitializer {
    @Override
    public void onInitialize() {
        CommonClass.init();
        BlockModuleFabric.registerBlocks();
        CommonClass.commonSetup();
    }
}