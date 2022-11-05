package oceanicvoid.thedeeperdark.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import oceanicvoid.thedeeperdark.TheDeeperDark;
import org.intellij.lang.annotations.JdkConstants;

public class EntrenchedJournalScreen extends Screen {

	public static final int BOOK_IMAGE_WIDTH = 300;
	public static final int BOOK_IMAGE_HEIGHT = 200;
	private static final ResourceLocation BOOK_PAGES_LOCATION = new ResourceLocation(TheDeeperDark.MODID, "textures/gui/book_screen.png");
	private static final ResourceLocation BOOK_OUTLINE_LOCATION = new ResourceLocation(TheDeeperDark.MODID, "textures/gui/book_outline.png");

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
		this.renderBackground(poseStack);
//		renderBook(poseStack);

		int guiScale = Minecraft.getInstance().options.guiScale().get();
		int xCorner = getCornerPosition(this.width, BOOK_IMAGE_WIDTH);
		int yCorner = getCornerPosition(this.height, BOOK_IMAGE_HEIGHT);

		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		RenderSystem.setShaderColor(1,1,1,1);
		// hex color format is ARGB, with 2 hex digits indicating each
		final int rectOffsetMagicNumber = 4;
		Screen.fill(
			poseStack,
			xCorner+rectOffsetMagicNumber,
			yCorner+rectOffsetMagicNumber,
			this.width-xCorner-rectOffsetMagicNumber,
			this.height-yCorner-rectOffsetMagicNumber,
			0xDF444444
		);

		renderBookOutline(poseStack);
	}

	private void renderBookOutline(PoseStack poseStack) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, BOOK_OUTLINE_LOCATION);
		renderBookAlike(poseStack);
	}

	private void renderBook(PoseStack poseStack) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, BOOK_PAGES_LOCATION);
		renderBookAlike(poseStack);
	}

	private void renderBookAlike(PoseStack poseStack) {
		//width = screen width/gui scale
		int x = getCornerPosition(this.width, BOOK_IMAGE_WIDTH);
		int y = getCornerPosition(this.height, BOOK_IMAGE_HEIGHT);

		Screen.blit(poseStack, x, y, 0, 0, 0, BOOK_IMAGE_WIDTH, BOOK_IMAGE_HEIGHT, BOOK_IMAGE_WIDTH, BOOK_IMAGE_HEIGHT);
	}

	private int getCornerPosition(int elemLength, int axisLength) {
		return (int) (elemLength/2 - axisLength/2);
	}
}
