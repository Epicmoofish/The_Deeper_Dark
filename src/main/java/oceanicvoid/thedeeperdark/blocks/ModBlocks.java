package oceanicvoid.thedeeperdark.blocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.RegistryObject;
import oceanicvoid.thedeeperdark.items.GatewayKeyItem;
import oceanicvoid.thedeeperdark.items.ModItems;
import oceanicvoid.thedeeperdark.items.questbook.EntrenchedJournal;
import oceanicvoid.thedeeperdark.registry.ModRegistry;

import java.util.function.Supplier;

public class ModBlocks {
    public static final RegistryObject<Block> ACTIVATED_DEEPSLATE =
            registerBlock(
                    "activated_deepslate",
                    () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.DEEPSLATE).sound(SoundType.DEEPSLATE).strength(-1.0F, 3600000.0F).noLootTable().lightLevel(state -> 15)),
                    CreativeModeTab.TAB_BUILDING_BLOCKS
            );
    public static final RegistryObject<Block> DEEPER_DARK_PORTAL =
            registerBlockNoItem(
                    "deeper_dark_portal",
                    () -> new DeeperDarkPortalBlock(BlockBehaviour.Properties.of(Material.PORTAL).noCollission().randomTicks().strength(-1.0F,3600000.0F).sound(SoundType.GLASS).lightLevel((p_50870_) -> {
                        return 11;
                    }))
            );
    //this function is very important because if it is not run, no items will be registered, dont remove the init function
    public static void init() {}
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = ModRegistry.BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }
    private static <T extends Block> RegistryObject<T> registerBlockNoItem(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = ModRegistry.BLOCKS.register(name, block);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab) {
        return ModRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }
}
