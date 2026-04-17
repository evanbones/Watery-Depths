package com.evandev.watery_depths.client.renderer;

import com.evandev.watery_depths.client.model.CatfishModel;
import com.evandev.watery_depths.entity.CatfishEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CatfishRenderer extends GeoEntityRenderer<CatfishEntity> {
    public CatfishRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CatfishModel());
        this.shadowRadius = 0.4f;
    }
}