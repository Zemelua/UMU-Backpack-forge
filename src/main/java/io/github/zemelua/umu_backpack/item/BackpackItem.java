package io.github.zemelua.umu_backpack.item;

import io.github.zemelua.umu_backpack.capability.BackpackCapabilityProvider;
import io.github.zemelua.umu_backpack.client.model.ModModelLayers;
import io.github.zemelua.umu_backpack.client.model.item.BackpackModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
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
			if (itemStack.getMaxDamage() - itemStack.getDamageValue() - amount > 0) return amount;

			Containers.dropContents(entity.level, entity.blockPosition(), getContents(itemStack));
		}

		return super.damageItem(itemStack, amount, entity, onBroken);
	}

	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack itemStack, @Nullable CompoundTag nbt) {
		return new BackpackCapabilityProvider();
	}

	@SuppressWarnings("ConstantConditions")
	@Nullable
	@Override
	public String getArmorTexture(ItemStack itemStack, Entity entity, EquipmentSlot slot, String type) {
		return type != null && type.equals("overlay") ? BackpackModel.BACKPACK_OVERLAY_TEXTURE : BackpackModel.BACKPACK_TEXTURE;
	}

	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		consumer.accept(
				new IItemRenderProperties() {
					@SuppressWarnings("unchecked")
					@Override
					public <A extends HumanoidModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, A _default) {
						return (A) new BackpackModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.BACKPACK));
					}
				}
		);
	}

	public static NonNullList<ItemStack> getContents(ItemStack backpackStack) {
		IItemHandler inventory = BackpackCapabilityProvider.getInventory(backpackStack);
		NonNullList<ItemStack> contents = NonNullList.withSize(inventory.getSlots(), ItemStack.EMPTY);

		for (int i = 0; i < inventory.getSlots(); i++) {
			contents.set(i, inventory.getStackInSlot(i));
		}

		return contents;
	}

	public static boolean isEmpty(ItemStack backpackStack) {
		IItemHandler inventory = BackpackCapabilityProvider.getInventory(backpackStack);

		for (int i = 0; i < inventory.getSlots(); i++) {
			if (!inventory.getStackInSlot(i).isEmpty()) return false;
		}

		return true;
	}

	protected static void onLivingDeath(final LivingDeathEvent event) {
		LivingEntity living = event.getEntityLiving();
		ItemStack itemStack = living.getItemBySlot(EquipmentSlot.CHEST);
		if (living instanceof Player player && player.getAbilities().instabuild) return;
		if (!itemStack.is(ModItems.BACKPACK.get())) return;

		Containers.dropContents(living.level, living.blockPosition(), getContents(itemStack));
	}
}
