package io.github.zemelua.umu_backpack.advancement.trigger;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.eventbus.api.IEventBus;

public final class ModTriggers {
	public static final TightlyBackpackTrigger TIGHTLY_BACKPACK = new TightlyBackpackTrigger();

	private ModTriggers() {}

	private static boolean initialized = false;

	public static void initialize(IEventBus forgeBus, IEventBus modBus) {
		if (initialized) throw new IllegalStateException("Triggers is already initialized!");

		CriteriaTriggers.register(ModTriggers.TIGHTLY_BACKPACK);

		initialized = true;
	}
}
