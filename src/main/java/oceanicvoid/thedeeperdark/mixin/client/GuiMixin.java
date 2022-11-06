package oceanicvoid.thedeeperdark.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import oceanicvoid.thedeeperdark.blocks.ModBlocks;
import oceanicvoid.thedeeperdark.mixininterfaces.IEntityMixin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ForgeGui.class)
public class GuiMixin {
@Inject(method="renderPortalOverlay",at = @At("HEAD"))
    private void modifyTexture(float partialTick, CallbackInfo ci) {
        Gui gui = (Gui)(Object)(this);
        IEntityMixin entityMixin = (IEntityMixin)(Entity)(Object)gui.minecraft.player;
        float f2 = Mth.lerp(partialTick, entityMixin.getoDeeperDarkPortalTime(), entityMixin.getDeeperDarkPortalTime());
        if (f2 > 0.0F && !gui.minecraft.player.hasEffect(MobEffects.CONFUSION)) {
            this.renderDeeperDarkPortalOverlay(f2);
        }
    }
    protected void renderDeeperDarkPortalOverlay(float p_93008_) {
        Gui gui = (Gui)(Object)(this);
        if (p_93008_ < 1.0F) {
            p_93008_ *= p_93008_;
            p_93008_ *= p_93008_;
            p_93008_ = p_93008_ * 0.8F + 0.2F;
        }

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, p_93008_);
        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        TextureAtlasSprite textureatlassprite = gui.minecraft.getBlockRenderer().getBlockModelShaper().getParticleIcon(ModBlocks.DEEPER_DARK_PORTAL.get().defaultBlockState());
        float f = textureatlassprite.getU0();
        float f1 = textureatlassprite.getV0();
        float f2 = textureatlassprite.getU1();
        float f3 = textureatlassprite.getV1();
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(0.0D, (double)gui.screenHeight, -90.0D).uv(f, f3).endVertex();
        bufferbuilder.vertex((double)gui.screenWidth, (double)gui.screenHeight, -90.0D).uv(f2, f3).endVertex();
        bufferbuilder.vertex((double)gui.screenWidth, 0.0D, -90.0D).uv(f2, f1).endVertex();
        bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(f, f1).endVertex();
        tesselator.end();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
