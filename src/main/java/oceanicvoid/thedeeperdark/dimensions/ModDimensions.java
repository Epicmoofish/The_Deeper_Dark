package oceanicvoid.thedeeperdark.dimensions;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import oceanicvoid.thedeeperdark.TheDeeperDark;

public class ModDimensions {
    public static final ResourceKey<Level> THEDEEPERDARK_KEY = ResourceKey.create(Registry.DIMENSION_REGISTRY,
            new ResourceLocation(TheDeeperDark.MODID, "deeper_dark"));
    public static final ResourceKey<DimensionType> THEDEEPERDARK_TYPE =
            ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, THEDEEPERDARK_KEY.registry());

    public static void register() {
        System.out.println("Registering ModDimensions for " + TheDeeperDark.MODID);
    }
}