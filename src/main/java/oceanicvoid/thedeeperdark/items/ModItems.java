package oceanicvoid.thedeeperdark.items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import oceanicvoid.thedeeperdark.registry.ModRegistry;

public class ModItems {
	public static final RegistryObject<Item> ENTRENCHED_JOURNAL = 
		ModRegistry.ITEMS.register(
			"entrenched_journal", 
			() -> new Item(new Item.Properties())
		);
	public static final RegistryObject<Item> GATEWAY_KEY =
			ModRegistry.ITEMS.register(
					"gateway_key",
					() -> new GatewayKeyItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS))
			);
	//this function is very important because if it is not run, no items will be registered, dont remove the init function
	public static void init(){}
}
