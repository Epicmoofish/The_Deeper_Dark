package oceanicvoid.thedeeperdark.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import oceanicvoid.thedeeperdark.TheDeeperDark;
import oceanicvoid.thedeeperdark.blocks.ModBlocks;
import oceanicvoid.thedeeperdark.dimensions.ModDimensions;
import oceanicvoid.thedeeperdark.dimensions.ModPOIs;
import oceanicvoid.thedeeperdark.items.ModItems;
import oceanicvoid.thedeeperdark.particles.ModParticles;
public class ModRegistry {
	
	/* -------------------------------------------------------------------------- */
	public static final DeferredRegister<Block> BLOCKS = 
		DeferredRegister.create(ForgeRegistries.BLOCKS, TheDeeperDark.MODID);
    public static final DeferredRegister<Item> ITEMS = 
		DeferredRegister.create(ForgeRegistries.ITEMS, TheDeeperDark.MODID);
	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, TheDeeperDark.MODID);
/* -------------------------------------------------------------------------- */
	public static void register(IEventBus eventBus) {
		ModBlocks.init();
		BLOCKS.register(eventBus);

		ModItems.init();
		ITEMS.register(eventBus);

		ModDimensions.register();

		ModPOIs.register(eventBus);

		ModParticles.init();
		PARTICLES.register(eventBus);
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
