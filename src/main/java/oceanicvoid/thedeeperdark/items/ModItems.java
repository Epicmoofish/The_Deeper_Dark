package oceanicvoid.thedeeperdark.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import oceanicvoid.thedeeperdark.items.questbook.EntrenchedJournal;
import oceanicvoid.thedeeperdark.registry.ModRegistry;

public class ModItems {
	public static final RegistryObject<Item> ENTRENCHED_JOURNAL = 
		ModRegistry.ITEMS.register(
			EntrenchedJournal.ID,
			() -> new EntrenchedJournal(new Item.Properties())
		);
	//this function is very important because if it is not run, no items will be registered, dont remove the init function
	public static void init() {}
}
