package oceanicvoid.thedeeperdark.blocks.entity;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.entity.SculkCatalystBlockEntity;
import net.minecraftforge.registries.RegistryObject;
import oceanicvoid.thedeeperdark.blocks.ModBlocks;
import oceanicvoid.thedeeperdark.registry.ModRegistry;

public class ModBlockEntities {
        public static final RegistryObject<BlockEntityType<SculkSniperBlockEntity>> SCULK_SNIPER_TYPE = ModRegistry.BLOCK_ENTITY_TYPES.register("sculk_sniper_type", () -> BlockEntityType.Builder.of(SculkSniperBlockEntity::new, ModBlocks.SCULK_SNIPER.get()).build(null));

        //this function is very important because if it is not run, no items will be registered, dont remove the init function
        public static void init() {
        }
        private static RegistryObject<BlockEntityType<? extends BlockEntity>> registerBlockEntity(String name, BlockEntityType.BlockEntitySupplier<? extends BlockEntity> blockEntityClass, Block block) {
                return ModRegistry.BLOCK_ENTITY_TYPES.register(name, () -> BlockEntityType.Builder.of(blockEntityClass, block).build(null));
        }
        private static <T extends BlockEntityType<?>> RegistryObject<BlockEntityType<SculkSniperBlockEntity>> registerSculkSniper(String name, BlockEntityType.BlockEntitySupplier<SculkSniperBlockEntity> blockEntityClass, Block block) {
                return ModRegistry.BLOCK_ENTITY_TYPES.register(name, () -> BlockEntityType.Builder.of(blockEntityClass, block).build(null));
        }
}
