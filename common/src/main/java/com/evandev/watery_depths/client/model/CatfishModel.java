package com.evandev.watery_depths.client.model;

import com.evandev.watery_depths.Constants;
import com.evandev.watery_depths.entity.CatfishEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class CatfishModel extends GeoModel<CatfishEntity> {
    @Override
    public ResourceLocation getModelResource(CatfishEntity object) {
        return new ResourceLocation(Constants.MOD_ID, "geo/catfish.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CatfishEntity object) {
        return new ResourceLocation(Constants.MOD_ID, "textures/entity/catfish.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CatfishEntity object) {
        return new ResourceLocation(Constants.MOD_ID, "animations/entity/catfish_swim.json");
    }
}