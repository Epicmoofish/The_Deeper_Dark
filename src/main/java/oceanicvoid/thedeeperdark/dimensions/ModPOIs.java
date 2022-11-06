package oceanicvoid.thedeeperdark.dimensions;

import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import oceanicvoid.thedeeperdark.TheDeeperDark;
import oceanicvoid.thedeeperdark.blocks.ModBlocks;

public class ModPOIs {
    public static final DeferredRegister<PoiType> POI
            = DeferredRegister.create(ForgeRegistries.POI_TYPES, TheDeeperDark.MODID);

    public static final RegistryObject<PoiType> DEEPER_DARK_PORTAL =
            POI.register("the_deeper_dark_portal", () -> new PoiType(PoiTypes.getBlockStates(ModBlocks.DEEPER_DARK_PORTAL.get()),0,1));


    public static void register(IEventBus eventBus) {
        POI.register(eventBus);
    }
}
