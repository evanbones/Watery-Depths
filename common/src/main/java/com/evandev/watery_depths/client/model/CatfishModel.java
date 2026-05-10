package com.evandev.watery_depths.client.model;

import com.evandev.watery_depths.client.animation.CatfishAnimation;
import com.evandev.watery_depths.entity.CatfishEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.NotNull;

public class CatfishModel extends HierarchicalModel<CatfishEntity> {
    private final ModelPart root;

    public CatfishModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition torso = partdefinition.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 9).addBox(-3.5F, -4.0F, -7.0F, 7.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, -1.0F));

        PartDefinition topfin = torso.addOrReplaceChild("topfin", CubeListBuilder.create().texOffs(25, 10).addBox(0.0F, -3.0F, -3.5F, 0.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, -2.5F));

        PartDefinition leftfin = torso.addOrReplaceChild("leftfin", CubeListBuilder.create(), PartPose.offsetAndRotation(3.5F, 0.0F, -7.0F, 0.0F, 0.1745F, 0.0F));
        leftfin.addOrReplaceChild("leftfin_r1", CubeListBuilder.create().texOffs(36, 17).addBox(0.0F, -2.0F, 0.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.3927F, 0.0F));

        PartDefinition rightfin = torso.addOrReplaceChild("rightfin", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.5F, 0.0F, -7.0F, 0.0F, -0.2182F, 0.0F));
        rightfin.addOrReplaceChild("rightfin_r1", CubeListBuilder.create().texOffs(36, 17).addBox(0.0F, -2.0F, 0.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.3927F, 0.0F));

        PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -1.5F, -6.0F, 7.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -7.0F));

        head.addOrReplaceChild("leftbigwhisker", CubeListBuilder.create().texOffs(20, 5).addBox(-3.5F, -2.0F, -5.0F, 4.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 1.5F, -1.0F));
        head.addOrReplaceChild("rightbigwhisker", CubeListBuilder.create().texOffs(20, 5).addBox(-4.0F, -0.5F, 0.0F, 4.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 0.0F, -6.0F));

        head.addOrReplaceChild("smallwhiskers", CubeListBuilder.create().texOffs(17, 24).addBox(2.5F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(17, 24).addBox(-3.5F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.5F, -6.0F));

        PartDefinition rear = torso.addOrReplaceChild("rear", CubeListBuilder.create(), PartPose.offset(0.0F, -2.5F, 4.0F));

        PartDefinition tail = rear.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(18, 30).addBox(0.0F, -3.5F, 6.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(14, 31).addBox(0.0F, 1.5F, 0.0F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 24).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        tail.addOrReplaceChild("backfin", CubeListBuilder.create().texOffs(0, 33).addBox(0.0F, -3.5F, -1.0F, 0.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 11.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(CatfishEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animate(entity.swimAnimationState, CatfishAnimation.SWIM, ageInTicks, 1.0F);
    }
}