package io.github.zemelua.umu_backpack.client.model;

import io.github.zemelua.umu_backpack.UMUBackpack;
import io.github.zemelua.umu_backpack.client.model.item.BackpackModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;

public final class ModModelLayers {
	public static final ModelLayerLocation BACKPACK = create("backpack");

	@SuppressWarnings("SameParameterValue")
	private static ModelLayerLocation create(String name) {
		return new ModelLayerLocation(UMUBackpack.resource(name), "main");
	}

	public static void onRegisterLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(BACKPACK, BackpackModel::createLayer);
	}
}
