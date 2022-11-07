package oceanicvoid.thedeeperdark.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.RegistryObject;
import oceanicvoid.thedeeperdark.registry.ModRegistry;

public class ModParticles {
    public static RegistryObject<SimpleParticleType> DEEPER_DARK_PORTAL = ModRegistry.PARTICLES.register("deeper_dark_portal", () -> new SimpleParticleType(true));
    public static void init(){}
    public static void registerParticles(){
        Minecraft.getInstance().particleEngine.register(ModParticles.DEEPER_DARK_PORTAL.get(), DeeperDarkPortalParticle.Provider::new);
    }
}
