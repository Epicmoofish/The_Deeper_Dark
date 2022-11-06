package oceanicvoid.thedeeperdark.systems.quests.triggers;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

// custom triggers like these can be used in custom advancements to set completion criteria.
public class CustomTrigger extends SimpleCriterionTrigger<CustomTrigger.TriggerInstance> {
	private final ResourceLocation ID;

	public CustomTrigger(ResourceLocation resourceLocation)
	{
		ID = resourceLocation;
	}

	public ResourceLocation getId()
	{
		return ID;
	}

	public TriggerInstance createInstance(JsonObject jsonObject, EntityPredicate.Composite entityPred, DeserializationContext deserializationContext) {
		return new TriggerInstance(this.ID, entityPred);
	}


	// this is the method that should be called, by functions that enact this trigger!
	public void trigger(ServerPlayer player)
	{
		// there is no context for this trigger; so whenever it is called, return true
		// without a matches() function
		this.trigger(player, (triggerInstance) -> true);
	}

	public static class TriggerInstance extends AbstractCriterionTriggerInstance
	{
		public TriggerInstance(ResourceLocation resourceLocation, EntityPredicate.Composite entityPred) {
			super(resourceLocation, entityPred);
		}

		// other ways to construct trigger instance go after here (see PlayerTrigger)

	}
}
