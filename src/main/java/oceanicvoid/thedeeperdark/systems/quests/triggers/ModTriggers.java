package oceanicvoid.thedeeperdark.systems.quests.triggers;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class ModTriggers {
	// register custom triggers with the default minecraft ones here
	public static final CustomTrigger CUSTOM_TRIGGER = CriteriaTriggers.register(new CustomTrigger(new ResourceLocation("custom_trigger")));
}
