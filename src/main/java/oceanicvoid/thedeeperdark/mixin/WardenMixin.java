package oceanicvoid.thedeeperdark.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.monster.warden.WardenAi;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import oceanicvoid.thedeeperdark.vibrations.ModVibrations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Warden.class)
public class WardenMixin {
    @Inject(method="onSignalReceive",at=@At("TAIL"))
    private void makeWardenInvestigateSonicBomb(ServerLevel p_219362_, GameEventListener p_219363_, BlockPos p_219364_, GameEvent p_219365_, Entity p_219366_, Entity p_219367_, float p_219368_, CallbackInfo ci){
        Warden warden = (Warden)(Object)this;
        if (p_219365_ == ModVibrations.sonicBombVibration.get()) {
            warden.getBrain().setMemoryWithExpiry(MemoryModuleType.VIBRATION_COOLDOWN, Unit.INSTANCE, 400L);
            warden.getBrain().setMemoryWithExpiry(MemoryModuleType.SNIFF_COOLDOWN, Unit.INSTANCE, 400L);
            if (p_219366_ != null) {
                BlockPos blockpos = p_219366_.blockPosition();
                WardenAi.setDisturbanceLocation(warden, blockpos);
                if (p_219367_!=null) {
                    warden.clearAnger(p_219367_);
                }
            }
        }
    }
    @Inject(method="shouldListen", at=@At("HEAD"), cancellable = true)
    private void makeAlwaysListen(ServerLevel p_219370_, GameEventListener p_219371_, BlockPos p_219372_, GameEvent p_219373_, GameEvent.Context p_219374_, CallbackInfoReturnable<Boolean> cir) {
        Warden warden = (Warden) (Object) this;
        if (!warden.isNoAi() && !warden.isDeadOrDying() && p_219373_ == ModVibrations.sonicBombVibration.get() && !warden.isDiggingOrEmerging() && p_219370_.getWorldBorder().isWithinBounds(p_219372_) && !warden.isRemoved() && warden.level == p_219370_) {
            cir.setReturnValue(true);
        }
    }
}
