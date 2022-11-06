package oceanicvoid.thedeeperdark.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.client.event.ViewportEvent;
import oceanicvoid.thedeeperdark.mixininterfaces.IEntityMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method="renderLevel",at=@At(value="INVOKE",target="Ljava/lang/Double;floatValue()F"),locals=LocalCapture.CAPTURE_FAILSOFT)
    private void makeScreenWavy(float p_109090_, long p_109091_, PoseStack p_109092_, CallbackInfo ci, boolean flag, Camera camera, PoseStack posestack, double d0){
        GameRenderer gameRenderer = (GameRenderer)(Object)this;
        IEntityMixin entityMixin = (IEntityMixin) gameRenderer.getMinecraft().player;
        float f = gameRenderer.getMinecraft().options.screenEffectScale().get().floatValue();
        float f1 = Mth.lerp(p_109090_, entityMixin.getoDeeperDarkPortalTime(), entityMixin.getDeeperDarkPortalTime()) * f * f;
        if (f1 > 0.0F) {
            int i = gameRenderer.getMinecraft().player.hasEffect(MobEffects.CONFUSION) ? 7 : 20;
            float f2 = 5.0F / (f1 * f1 + 5.0F) - f1 * 0.04F;
            f2 *= f2;
            Vector3f vector3f = new Vector3f(0.0F, Mth.SQRT_OF_TWO / 2.0F, Mth.SQRT_OF_TWO / 2.0F);
            posestack.mulPose(vector3f.rotationDegrees(((float)gameRenderer.tick + p_109090_) * (float)i));
            posestack.scale(1.0F / f2, 1.0F, 1.0F);
            float f3 = -((float)gameRenderer.tick + p_109090_) * (float)i;
            posestack.mulPose(vector3f.rotationDegrees(f3));
        }
    }
}