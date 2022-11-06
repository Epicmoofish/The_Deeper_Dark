package oceanicvoid.thedeeperdark.mixin.client;

import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import oceanicvoid.thedeeperdark.mixininterfaces.IEntityMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @Inject(method = "handleNetherPortalClient()V",at=@At("HEAD"))
    private void injectedPortalWoosh(CallbackInfo ci){
        if (((IEntityMixin)(Entity)(LocalPlayer)(Object)(this)).getInDeeperDarkPortal()) {
            LocalPlayer player = (LocalPlayer)(Object)(this);
            if (player.minecraft.screen != null && !player.minecraft.screen.isPauseScreen() && !(player.minecraft.screen instanceof DeathScreen) && !(player.minecraft.screen instanceof ReceivingLevelScreen)) {
                if (player.minecraft.screen instanceof AbstractContainerScreen) {
                    player.closeContainer();
                }

                player.minecraft.setScreen((Screen)null);
            }

            if (player.portalTime == 0.0F) {
                player.minecraft.getSoundManager().play(SimpleSoundInstance.forLocalAmbience(SoundEvents.PORTAL_TRIGGER, player.getRandom().nextFloat() * 0.4F + 0.8F, 0.25F));
            }
            float counterAct=0.05F;
            if (player.hasEffect(MobEffects.CONFUSION) && player.getEffect(MobEffects.CONFUSION).getDuration() > 60) {
                counterAct=-0.006666667F;
            }
            player.portalTime += 0.0125F+counterAct;
            if (player.portalTime >= 1.0F) {
                player.portalTime = 1.0F;
            }
            ((IEntityMixin)(Entity)(LocalPlayer)(Object)(this)).setInDeeperDarkPortal(false);
        }
    }
}
