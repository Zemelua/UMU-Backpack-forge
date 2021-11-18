package io.github.zemelua.umu_backpack.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BackpackCapabilityProvider implements ICapabilitySerializable<CompoundTag> {
	private final ItemStackHandler inventory = new ItemStackHandler(54);
	private final LazyOptional<IItemHandler> inventoryOptional = LazyOptional.of(() -> this.inventory);

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
		return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(capability, this.inventoryOptional);
	}

	@Override
	public CompoundTag serializeNBT() {
		return this.inventory.serializeNBT();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.inventory.deserializeNBT(nbt);
	}
}
