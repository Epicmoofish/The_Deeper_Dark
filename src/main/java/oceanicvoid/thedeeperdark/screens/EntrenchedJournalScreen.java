package oceanicvoid.thedeeperdark.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import oceanicvoid.thedeeperdark.TheDeeperDark;

public class EntrenchedJournalScreen extends Screen {

	private static ResourceLocation BOOK_BACKGROUND_LOCATION = new ResourceLocation(TheDeeperDark.MODID, "textures/gui/book_screen.png");

	public EntrenchedJournalScreen() {
		super(GameNarrator.NO_TITLE); // don't ask me why this is under the class GameNarrator
	}

	@Override
	public boolean shouldCloseOnEsc() {
		return true;
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float deltaTick) {
		super.render(poseStack, mouseX, mouseY, deltaTick);
		renderBook(poseStack);
	}

	private void renderBook(PoseStack poseStack) {
		int imageWidth = 300;
		int imageHeight = 200;

		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, BOOK_BACKGROUND_LOCATION);
		//width = screen width/gui scale

		float scaleModifier = 1f;
		poseStack.scale(scaleModifier, scaleModifier,1f);
		int x = (int) (this.width/(2*scaleModifier) - imageWidth/2); // width/(2*scale*modifier)
		int y = (int) (this.height/(2*scaleModifier) - imageHeight/2); // height/(2*scale*modifier)

		Screen.blit(poseStack, x, y, 0, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
	}
}
