package io.github.zemelua.umu_backpack.fix;

import com.mojang.datafixers.util.Pair;
import io.github.zemelua.umu_backpack.item.BackpackItem;
import io.github.zemelua.umu_backpack.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.TickEvent;

public class FixedInventoryMenu extends InventoryMenu {
	private final Player player;

	public FixedInventoryMenu(Player player) {
		this(player.getInventory(), !player.level.isClientSide(), player);
	}

	public FixedInventoryMenu(Inventory playerInventory, boolean active, Player player) {
		super(playerInventory, active, player);

		this.player = player;
		this.slots.set(6, new FixedChestSlot(playerInventory));
	}

	private class FixedChestSlot extends Slot {
		public FixedChestSlot(Container inventory) {
			super(inventory, 38, 8, 44);
		}

		@Override
		public int getMaxStackSize() {
			return 1;
		}

		@Override
		public boolean mayPlace(ItemStack itemStack) {
			return itemStack.canEquip(EquipmentSlot.CHEST, FixedInventoryMenu.this.player);
		}

		@Override
		public boolean mayPickup(Player player) {
			ItemStack itemStack = this.getItem();
			if (itemStack.is(ModItems.BACKPACK.get()) && !BackpackItem.isEmpty(itemStack)) return false;

			return (itemStack.isEmpty() || player.isCreative() || !EnchantmentHelper.hasBindingCurse(itemStack))
					&& super.mayPickup(player);
		}

		@Override
		public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
			return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_CHESTPLATE);
		}
	}

	public static void onPlayerTick(final TickEvent.PlayerTickEvent event) {
		Player player = event.player;

		if (event.phase == TickEvent.Phase.END) {
			if (player.containerMenu.getClass() == InventoryMenu.class) {
				player.containerMenu = new FixedInventoryMenu(player);
			}
		}
	}
}
