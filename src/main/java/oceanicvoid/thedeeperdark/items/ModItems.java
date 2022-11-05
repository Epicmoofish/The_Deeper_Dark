package oceanicvoid.thedeeperdark.items;

import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import oceanicvoid.thedeeperdark.blocks.ModBlocks;
import oceanicvoid.thedeeperdark.items.questbook.EntrenchedJournal;
import oceanicvoid.thedeeperdark.registry.ModRegistry;

import java.util.function.Supplier;

public class ModItems {
	public static final RegistryObject<Item> ENTRENCHED_JOURNAL = ModRegistry.ITEMS.register(
			EntrenchedJournal.ID,
			() -> new EntrenchedJournal(new Item.Properties()));
	public static final RegistryObject<Item> GATEWAY_KEY = ModRegistry.ITEMS.register(
			GatewayKeyItem.ID,
			() -> new GatewayKeyItem(new Item.Properties()));
	//this function is very important because if it is not run, no items will be registered, dont remove the init function
	public static void init() {}
}
