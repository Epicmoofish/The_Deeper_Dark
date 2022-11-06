package oceanicvoid.thedeeperdark.items.questbook;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import oceanicvoid.thedeeperdark.TheDeeperDark;
import oceanicvoid.thedeeperdark.items.general.LoreItem;
import oceanicvoid.thedeeperdark.screens.EntrenchedJournalScreen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EntrenchedJournal extends Item {

	public static final String ID = "entrenched_journal";

	public EntrenchedJournal(Properties properties) {
		super(addProperties(properties));
	}

	/**
	 * Appends the base properties of this item to a given properties
	 * object. Mainly for organization purposes.
	 * @param properties The properties to append to
	 * @return The modified properties
	 */
	private static Properties addProperties(Properties properties) {
		properties.stacksTo(1);
		properties.rarity(Rarity.EPIC);
		properties.tab(CreativeModeTab.TAB_TOOLS);
		return properties;
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		// i'm not messing with offhand
		if (interactionHand == InteractionHand.MAIN_HAND) {
			// the client opens the gui for the player
			if (level.isClientSide()) {
				Minecraft.getInstance().setScreen(new EntrenchedJournalScreen());
			}
			return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
		}
		return InteractionResultHolder.fail(player.getItemInHand(interactionHand));
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
		LoreItem.addLore(ID, tooltip);
	}

}
