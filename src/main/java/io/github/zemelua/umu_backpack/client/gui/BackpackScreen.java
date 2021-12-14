package io.github.zemelua.umu_backpack.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.zemelua.umu_backpack.UMUBackpack;
import io.github.zemelua.umu_backpack.inventory.BackpackMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BackpackScreen extends AbstractContainerScreen<BackpackMenu> {
	private static final ResourceLocation BACKPACK_GUI_TEXTURE_0 = new ResourceLocation(UMUBackpack.MOD_ID, "textures/gui/backpack_0.png");
	private static final ResourceLocation BACKPACK_GUI_TEXTURE_1 = new ResourceLocation(UMUBackpack.MOD_ID, "textures/gui/backpack_1.png");
	private static final ResourceLocation BACKPACK_GUI_TEXTURE_2 = new ResourceLocation(UMUBackpack.MOD_ID, "textures/gui/backpack_2.png");
	private static final ResourceLocation BACKPACK_GUI_TEXTURE_3 = new ResourceLocation(UMUBackpack.MOD_ID, "textures/gui/backpack_3.png");
	private static final ResourceLocation BACKPACK_GUI_TEXTURE_4 = new ResourceLocation(UMUBackpack.MOD_ID, "textures/gui/backpack_4.png");
	private static final ResourceLocation BACKPACK_GUI_TEXTURE_5 = new ResourceLocation(UMUBackpack.MOD_ID, "textures/gui/backpack_5.png");

	public BackpackScreen(BackpackMenu container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);

		this.imageHeight = 114 + this.getMenu().getCapacity() * 18;
		this.inventoryLabelY = this.imageHeight - 94;
	}

	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, switch (this.getMenu().getCapacity() - 1) {
			case 0 -> BackpackScreen.BACKPACK_GUI_TEXTURE_0;
			case 1 -> BackpackScreen.BACKPACK_GUI_TEXTURE_1;
			case 2 -> BackpackScreen.BACKPACK_GUI_TEXTURE_2;
			case 3 -> BackpackScreen.BACKPACK_GUI_TEXTURE_3;
			case 4 -> BackpackScreen.BACKPACK_GUI_TEXTURE_4;
			case 5 -> BackpackScreen.BACKPACK_GUI_TEXTURE_5;
			default -> throw new IllegalStateException("Unexpected value!");
		});
		int drawX = (this.width - this.imageWidth) / 2;
		int drawY = (this.height - this.imageHeight) / 2;

		this.blit(matrixStack, drawX, drawY, 0, 0, this.imageWidth, this.imageHeight);
	}
}
