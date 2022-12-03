package oceanicvoid.thedeeperdark.vibrations;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.registries.RegistryObject;
import oceanicvoid.thedeeperdark.TheDeeperDark;
import oceanicvoid.thedeeperdark.registry.ModRegistry;

public class ModVibrations {
    public static RegistryObject<GameEvent> sonicBombVibration = ModRegistry.GAME_EVENTS.register("sonic_bomb", ()->new GameEvent("sonic_bomb", 16));
    public static RegistryObject<GameEvent> sniperVibration = ModRegistry.GAME_EVENTS.register("sniper_trigger", ()->new GameEvent("sniper_trigger", 32));
    public static void init() {}
    public static void addVibrationsToSculkSensor() {
        Object2IntMap<GameEvent> newVibrationFrequency = Object2IntMaps.unmodifiable(Util.make(new Object2IntOpenHashMap<>(), (p_238254_) -> {
            for (GameEvent event: SculkSensorBlock.VIBRATION_FREQUENCY_FOR_EVENT.keySet()){
                p_238254_.put(event, SculkSensorBlock.VIBRATION_FREQUENCY_FOR_EVENT.get(event));
            }
            p_238254_.put(ModVibrations.sonicBombVibration.get(),9);
        }));
        SculkSensorBlock.VIBRATION_FREQUENCY_FOR_EVENT = newVibrationFrequency;
    }
    public static class Tag {
        public static final TagKey<GameEvent> SNIPER_CAN_LISTEN = GameEventTags.create(new ResourceLocation(TheDeeperDark.MODID, "sniper_can_listen").toString());
    }
}
