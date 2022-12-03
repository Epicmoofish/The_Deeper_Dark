package oceanicvoid.thedeeperdark.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import oceanicvoid.thedeeperdark.TheDeeperDark;
import oceanicvoid.thedeeperdark.blocks.ModBlocks;
import oceanicvoid.thedeeperdark.blocks.entity.ModBlockEntities;
import oceanicvoid.thedeeperdark.dimensions.ModDimensions;
import oceanicvoid.thedeeperdark.dimensions.ModPOIs;
import oceanicvoid.thedeeperdark.entities.ModEntities;
import oceanicvoid.thedeeperdark.items.ModItems;
import oceanicvoid.thedeeperdark.particles.ModParticles;
import oceanicvoid.thedeeperdark.vibrations.ModVibrations;

public class ModRegistry {
	
	/* -------------------------------------------------------------------------- */
	public static final DeferredRegister<Block> BLOCKS = 
		DeferredRegister.create(ForgeRegistries.BLOCKS, TheDeeperDark.MODID);
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
			DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TheDeeperDark.MODID);
    public static final DeferredRegister<Item> ITEMS = 
		DeferredRegister.create(ForgeRegistries.ITEMS, TheDeeperDark.MODID);
	public static final DeferredRegister<GameEvent> GAME_EVENTS =
			DeferredRegister.create(Registry.GAME_EVENT_REGISTRY, TheDeeperDark.MODID);
	public static final DeferredRegister<EntityType<?>> ENTITIES =
			DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TheDeeperDark.MODID);
	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, TheDeeperDark.MODID);
/* -------------------------------------------------------------------------- */
	public static void register(IEventBus eventBus) {
		ModBlocks.init();
		BLOCKS.register(eventBus);

		ModBlockEntities.init();
		BLOCK_ENTITY_TYPES.register(eventBus);

		ModItems.init();
		ITEMS.register(eventBus);

		ModDimensions.register();

		ModPOIs.register(eventBus);

		ModParticles.init();
		PARTICLES.register(eventBus);

		ModVibrations.init();
		GAME_EVENTS.register(eventBus);

		ModEntities.init();
		ENTITIES.register(eventBus);
	}
	@Mod.EventBusSubscriber(modid=TheDeeperDark.MODID, value=Dist.CLIENT, bus=Mod.EventBusSubscriber.Bus.MOD)
	public static class ClientRegistry {
		@SubscribeEvent
		public static void registerParticles(RegisterParticleProvidersEvent event) {
			ModParticles.registerParticles();
		}
	}
/* -------------------------------------------------------------------------- */
}
