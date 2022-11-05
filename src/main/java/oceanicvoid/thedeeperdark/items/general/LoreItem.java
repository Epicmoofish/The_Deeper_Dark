package oceanicvoid.thedeeperdark.items.general;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import oceanicvoid.thedeeperdark.TheDeeperDark;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A standard item from this mod with structured lore added on.
 * item.modid.item_id.tooltip and item.modid.item_id.status in any lang file sets its lore.
 */
public class LoreItem {

	// append the lore from the lang file when requested
	public static void addLore(String itemId, List<Component> tooltip) {
		// Story-esque lore
		tooltip.add(
				Component.translatable(
						"item.%s.%s.tooltip".formatted(TheDeeperDark.MODID, itemId)
				).withStyle(ChatFormatting.GRAY)
		);
		// status (orange) lore
		tooltip.add(
				Component.translatable(
						"item.%s.%s.status".formatted(TheDeeperDark.MODID, itemId)
				).withStyle(ChatFormatting.GOLD)
		);
	}
}
