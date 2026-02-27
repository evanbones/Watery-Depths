package com.evandev.watery_depths.compat;

import com.evandev.watery_depths.client.integration.ClothConfigIntegration;
import com.evandev.watery_depths.platform.Services;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (Services.PLATFORM.isModLoaded("cloth-config")) {
            return ClothConfigIntegration::createScreen;
        }
        return null;
    }
}