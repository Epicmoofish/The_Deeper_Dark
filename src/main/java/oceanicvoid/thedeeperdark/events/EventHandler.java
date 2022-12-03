package oceanicvoid.thedeeperdark.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import oceanicvoid.thedeeperdark.vibrations.ModVibrations;

@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void sendSniperTrigger(LivingEvent.LivingTickEvent event) {
        Level level = event.getEntity().getLevel();
        BlockPos blockpos = event.getEntity().getOnPos();
        if (level.getGameTime() % 5 == 0) {
            level.gameEvent(ModVibrations.sniperVibration.get(), blockpos, GameEvent.Context.of(event.getEntity(), level.getBlockState(blockpos)));
        }
    }
}
