package io.github.zemelua.umu_backpack.mixin;

import com.mojang.authlib.GameProfile;
import io.github.zemelua.umu_backpack.advancement.trigger.ModTriggers;
import io.github.zemelua.umu_backpack.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {
	@Deprecated
	public ServerPlayerMixin(Level level, BlockPos pos, float yRot, GameProfile profile) {
		super(level, pos, yRot, profile);
	}

	@Inject(at = @At("RETURN"), method = "initMenu(Lnet/minecraft/world/inventory/AbstractContainerMenu;)V")
	@SuppressWarnings("SpellCheckingInspection")
	private void initMenu(AbstractContainerMenu menu, CallbackInfo callback) {
		menu.addSlotListener(new ContainerListener() {
			@Override
			public void slotChanged(AbstractContainerMenu menu, int index, ItemStack itemStack) {
				Slot slot = menu.getSlot(index);
				Inventory inventory = ServerPlayerMixin.this.getInventory();
				ItemStack chestStack = ServerPlayerMixin.this.getItemBySlot(EquipmentSlot.CHEST);

				if (!(slot instanceof ResultSlot) && slot.container == inventory && chestStack.is(ModItems.BACKPACK.get())) {
					ModTriggers.TIGHTLY_BACKPACK.trigger((ServerPlayer) (Object) ServerPlayerMixin.this, inventory, chestStack);
				}
			}

			@Override
			public void dataChanged(AbstractContainerMenu menu, int index, int data) {}
		});
	}
}
