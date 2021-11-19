package io.github.zemelua.umu_backpack.item;

import io.github.zemelua.umu_backpack.capability.BackpackCapabilityProvider;
import io.github.zemelua.umu_backpack.client.model.item.BackpackModel;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class BackpackItem extends DyeableArmorItem {
	public BackpackItem(ArmorMaterial material, EquipmentSlot slot, Properties properties) {
		super(material, slot, properties);
	}

	@Override
	public void onDestroyed(ItemEntity itemEntity) {
		ItemUtils.onContainerDestroyed(itemEntity, getContents(itemEntity.getItem()).stream());
	}

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack itemStack, int amount, T entity, Consumer<T> onBroken) {
		if (!entity.level.isClientSide()) {
			onBroken = onBroken.andThen(entityArg -> {
				Containers.dropContents(entityArg.level, entityArg.blockPosition(), getContents(itemStack));
			});
		}

		return super.damageItem(itemStack, amount, entity, onBroken);
	}

	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack itemStack, @Nullable CompoundTag nbt) {
		return new BackpackCapabilityProvider();
	}

	@Nullable
	@Override
	public String getArmorTexture(ItemStack itemStack, Entity entity, EquipmentSlot slot, String type) {
		return type.equals("overlay") ? BackpackModel.BACKPACK_OVERLAY_TEXTURE : BackpackModel.BACKPACK_TEXTURE;
	}

	public static NonNullList<ItemStack> getContents(ItemStack backpackStack) {
		IItemHandler inventory = BackpackCapabilityProvider.getInventory(backpackStack);
		NonNullList<ItemStack> contents = NonNullList.of(ItemStack.EMPTY);

		for (int i = 0; i < inventory.getSlots(); i++) {
			contents.add(inventory.getStackInSlot(i));
		}

		return contents;
	}
}
