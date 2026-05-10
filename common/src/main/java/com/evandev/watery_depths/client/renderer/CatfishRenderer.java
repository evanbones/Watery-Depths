package com.evandev.watery_depths.client.renderer;

import com.evandev.watery_depths.Constants;
import com.evandev.watery_depths.client.model.CatfishModel;
import com.evandev.watery_depths.entity.CatfishEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class CatfishRenderer extends MobRenderer<CatfishEntity, CatfishModel> {
    public static final ModelLayerLocation CATFISH_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "catfish"), "main");

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/entity/catfish.png");

    public CatfishRenderer(EntityRendererProvider.Context context) {
        super(context, new CatfishModel(context.bakeLayer(CATFISH_LAYER)), 0.4f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull CatfishEntity entity) {
        return TEXTURE;
    }
}