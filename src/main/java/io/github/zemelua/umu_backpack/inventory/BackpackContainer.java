package io.github.zemelua.umu_backpack.inventory;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class BackpackContainer extends AbstractContainerMenu {
	private final int inventorySize;

	protected BackpackContainer(int id, Inventory playerInventory, FriendlyByteBuf buffer) {
		this(id, playerInventory, new ItemStackHandler(buffer.readVarInt()));
	}

	public BackpackContainer(int id, Inventory playerInventory, IItemHandler backpackInventory) {
		super(null, id);

		this.inventorySize = backpackInventory.getSlots();

		for(int i = 0; i < this.getInventoryRows(); i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlot(new SlotItemHandler(backpackInventory, j + i * 9, 8 + j * 18, 18 + i * 18));
			}
		}
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 18 + this.getInventoryRows() * 18 + 14 + i * 18));
			}
		}
		for(int i = 0; i < 9; i++) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 18 + this.getInventoryRows() * 18 + 14 + 3 * 18 + 4));
		}
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);

		if (slot.hasItem()) {
			ItemStack currentStack = slot.getItem();
			itemStack = currentStack.copy();

			if (index < this.inventorySize) {
				if (!this.moveItemStackTo(currentStack, this.inventorySize, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(currentStack, 0, this.inventorySize, false)) {
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
		return this.inventorySize;
	}

	public int getInventoryRows() {
		return this.getInventorySize() / 9;
	}
}
