package oceanicvoid.thedeeperdark.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import oceanicvoid.thedeeperdark.registry.ModRegistry;

public class ModItems {
	public static final RegistryObject<Item> ENTRENCHED_JOURNAL = 
		ModRegistry.ITEMS.register(
			"entrenched_journal", 
			() -> new Item(new Item.Properties())
		);
}
