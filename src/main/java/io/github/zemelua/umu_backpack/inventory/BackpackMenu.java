package io.github.zemelua.umu_backpack.inventory;

import io.github.zemelua.umu_backpack.capability.BackpackCapabilityProvider;
import io.github.zemelua.umu_backpack.enchantment.ModEnchantments;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class BackpackMenu extends AbstractContainerMenu {
	private final int capacity;

	protected BackpackMenu(int id, Inventory playerInventory, FriendlyByteBuf buffer) {
		this(id, playerInventory, buffer.readItem());
	}

	public BackpackMenu(int id, Inventory playerInventory, ItemStack backpackStack) {
		this(id, playerInventory, BackpackCapabilityProvider.getInventory(backpackStack),
				EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.CRAM.get(), backpackStack) + 1);
	}

	public BackpackMenu(int id, Inventory playerInventory, IItemHandler backpackInventory, int capacity) {
		super(ModMenus.BACKPACK.get(), id);

		this.capacity = capacity;

		for(int i = 0; i < this.getCapacity(); i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlot(new SlotItemHandler(backpackInventory, j + i * 9, 8 + j * 18, 18 + i * 18));
			}
		}
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 18 + this.getCapacity() * 18 + 14 + i * 18));
			}
		}
		for(int i = 0; i < 9; i++) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 18 + this.getCapacity() * 18 + 14 + 3 * 18 + 4));
		}
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);

		if (slot.hasItem()) {
			ItemStack currentStack = slot.getItem();
			itemStack = currentStack.copy();

			if (index < this.getInventorySize()) {
				if (!this.moveItemStackTo(currentStack, this.getInventorySize(), this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(currentStack, 0, this.getInventorySize(), false)) {
				return ItemStack.EMPTY;
			}

			if (currentStack.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}

		return itemStack;
	}

	@Override
	public boolean stillValid(Player p_38874_) {
		return true;
	}

	public int getInventorySize() {
		return this.getCapacity() * 9;
	}

	public int getCapacity() {
		return this.capacity;
	}
}
