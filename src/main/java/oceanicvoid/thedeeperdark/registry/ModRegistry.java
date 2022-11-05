package oceanicvoid.thedeeperdark.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import oceanicvoid.thedeeperdark.TheDeeperDark;
import oceanicvoid.thedeeperdark.items.ModItems;

public class ModRegistry {
	
	/* -------------------------------------------------------------------------- */
	public static final DeferredRegister<Block> BLOCKS = 
		DeferredRegister.create(ForgeRegistries.BLOCKS, TheDeeperDark.MODID);
    public static final DeferredRegister<Item> ITEMS = 
		DeferredRegister.create(ForgeRegistries.ITEMS, TheDeeperDark.MODID);
/* -------------------------------------------------------------------------- */
	public static void register(IEventBus eventBus) {
		ModItems.init();
		ITEMS.register(eventBus);

		// BLOCKS.register(eventBus);
	}
/* -------------------------------------------------------------------------- */
}
