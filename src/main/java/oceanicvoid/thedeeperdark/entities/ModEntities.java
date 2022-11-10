package oceanicvoid.thedeeperdark.entities;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;
import oceanicvoid.thedeeperdark.TheDeeperDark;
import oceanicvoid.thedeeperdark.entities.projectile.SonicBombEntity;
import oceanicvoid.thedeeperdark.registry.ModRegistry;

public class ModEntities {
    public static final RegistryObject<EntityType<SonicBombEntity>> SONIC_BOMB_ENTITY = ModRegistry.ENTITIES.register(
            "sonic_bomb",
            () -> EntityType.Builder.<SonicBombEntity>of(SonicBombEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(new ResourceLocation(TheDeeperDark.MODID,"sonic_bomb").toString()));
    //this function is very important because if it is not run, no items will be registered, dont remove the init function
    public static void init() {}

    public static void registerRenderers() {
        EntityRenderers.register(ModEntities.SONIC_BOMB_ENTITY.get(), ThrownItemRenderer::new);
    }
}
