package io.github.zemelua.umu_backpack.client.model.item;

import io.github.zemelua.umu_backpack.UMUBackpack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class BackpackModel extends HumanoidModel<LivingEntity> {
	public static final String BASE_TEXTURE = UMUBackpack.resource("textures/models/armor/backpack.png").toString();
	public static final String OVERLAY_TEXTURE = UMUBackpack.resource("textures/models/armor/backpack_overlay.png").toString();

	public BackpackModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createLayer() {
		MeshDefinition meshDefinition = HumanoidModel.createMesh(new CubeDeformation(0.0F), 1.0F);
		PartDefinition partDefinition = meshDefinition.getRoot();

		partDefinition.getChild("body").addOrReplaceChild("backpack", CubeListBuilder.create()
						.texOffs(0, 0).addBox(-4.0F, 0.0F, 2.0F, 8.0F, 10.0F, 4.0F)
						.texOffs(0, 14).addBox(-3.0F, 4.0F, 6.0F, 6.0F, 6.0F, 2.0F)
						.texOffs(24, 0).addBox("straps", -4.0F, -0.05F, -3.0F, 8.0F, 8.0F, 5.0F),
				PartPose.offset(0.0F, 0.0F, 0.0F)
		);

		return LayerDefinition.create(meshDefinition, 64, 32);
	}
}
