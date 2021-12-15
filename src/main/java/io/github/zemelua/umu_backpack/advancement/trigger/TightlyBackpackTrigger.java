package io.github.zemelua.umu_backpack.advancement.trigger;

import com.google.gson.JsonObject;
import io.github.zemelua.umu_backpack.UMUBackpack;
import io.github.zemelua.umu_backpack.capability.BackpackCapabilityProvider;
import io.github.zemelua.umu_backpack.enchantment.ModEnchantments;
import io.github.zemelua.umu_backpack.item.ModItems;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.items.IItemHandler;

public class TightlyBackpackTrigger extends SimpleCriterionTrigger<TightlyBackpackTrigger.Instance> {
	private static final ResourceLocation ID = UMUBackpack.resource("tightly_backpack");

	public void trigger(ServerPlayer player, Inventory inventory, ItemStack backpack) {
		this.trigger(player, (instance) -> instance.matches(inventory, backpack));
	}

	@Override
	protected TightlyBackpackTrigger.Instance createInstance(JsonObject json, EntityPredicate.Composite player, DeserializationContext context) {
		return new TightlyBackpackTrigger.Instance(player);
	}

	@Override
	public ResourceLocation getId() {
		return TightlyBackpackTrigger.ID;
	}

	public static class Instance extends AbstractCriterionTriggerInstance {
		private Instance(EntityPredicate.Composite player) {
			super(TightlyBackpackTrigger.ID, player);
		}

		/**
		 * Minecraft 1.18時点で、バンドルが正式実装されていないため、このトリガーの条件は「アーマースロットを除くすべての
		 * インベントリとバックパックが最大スタック数のアイテムで埋まっていること」として実装します。バンドルの実装後、「
		 * アーマースロットを除くすべてのインベントリとバックパックが満杯のバンドルで満たされたシュルカーボックスで埋まっ
		 * ていること」に変更されます。
		 */
		public boolean matches(Inventory inventory, ItemStack backpack) {
			if (!backpack.is(ModItems.BACKPACK.get())) return false;
			if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.CRAM.get(), backpack)
					< ModEnchantments.CRAM.get().getMaxLevel()) return false;

			UMUBackpack.LOGGER.info(inventory.getContainerSize());
			for (int i = 0; i < inventory.getContainerSize(); i++) {
				ItemStack itemStack = inventory.getItem(i);

				if (i < 36 || i > 39) {
					if (itemStack.isEmpty() || itemStack.getCount() < itemStack.getMaxStackSize()) return false;
				}
			}

			IItemHandler container = BackpackCapabilityProvider.getInventory(backpack);
			UMUBackpack.LOGGER.info(container.getSlots());

			for (int i = 0; i < container.getSlots(); i++) {
				ItemStack itemStack = container.getStackInSlot(i);

				if (itemStack.isEmpty() || itemStack.getCount() < itemStack.getMaxStackSize()) return false;
			}

			return true;
		}
	}
}
