package oceanicvoid.thedeeperdark.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import oceanicvoid.thedeeperdark.mixininterfaces.IEntityMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin implements IEntityMixin {
    public boolean inDeeperDarkPortal = false;
    public float deeperDarkPortalTime = 0.0f;
    public float odeeperDarkPortalTime = 0.0f;
    @Override
    public void setInDeeperDarkPortal(boolean inPortal) {
        inDeeperDarkPortal = inPortal;
    }

    @Override
    public boolean getInDeeperDarkPortal() {
        return inDeeperDarkPortal;
    }

    @Override
    public void setDeeperDarkPortalTime(float portalTime) {
        deeperDarkPortalTime = portalTime;
    }

    @Override
    public float getDeeperDarkPortalTime() {
        return deeperDarkPortalTime;
    }
    @Override
    public void setoDeeperDarkPortalTime(float oportalTime) {
        odeeperDarkPortalTime = oportalTime;
    }

    @Override
    public float getoDeeperDarkPortalTime() {
        return odeeperDarkPortalTime;
    }

    @Inject(method= "handleNetherPortal()V",at = @At(value="HEAD"))
    private void handleDeeperDarkPortal(CallbackInfo ci){
        if (((Entity)(Object)this).level instanceof ServerLevel) {
            if (this.inDeeperDarkPortal) {
                ((Entity) (Object) this).portalTime += 5;
                this.setInDeeperDarkPortal(false);
            }
        }
    }
}
