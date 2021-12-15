package io.github.zemelua.umu_backpack.mixin;

import com.mojang.datafixers.util.Pair;
import io.github.zemelua.umu_backpack.item.BackpackItem;
import io.github.zemelua.umu_backpack.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryMenu.class)
public abstract class InventoryMenuMixin extends RecipeBookMenu<CraftingContainer> {
	@Shadow @Final private Player owner;

	@Deprecated
	public InventoryMenuMixin(MenuType<?> type, int id) {
		super(type, id);
	}

	@Inject(at = @At("RETURN"), method = "<init>")
	private void constructor(Inventory inventory, boolean active, Player player, CallbackInfo callback) {
		this.slots.set(6, new Slot(inventory, 38, 8, 26) {
			{
				this.index = 6;
			}

			@Override
			public int getMaxStackSize() {
				return 1;
			}

			@Override
			public boolean mayPlace(ItemStack itemStack) {
				return itemStack.canEquip(EquipmentSlot.CHEST, InventoryMenuMixin.this.owner);
			}

			@Override
			public boolean mayPickup(Player player) {
				ItemStack itemStack = this.getItem();
				if (itemStack.isEmpty()) return true;
				if (player.isCreative()) return true;

				if (itemStack.is(ModItems.BACKPACK.get()) && !BackpackItem.isEmpty(itemStack)) return false;

				return !(EnchantmentHelper.hasBindingCurse(itemStack)
						|| (itemStack.is(ModItems.BACKPACK.get()) && !BackpackItem.isEmpty(itemStack)));
			}

			@Override
			public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
				return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_CHESTPLATE);
			}
		});
	}
}
