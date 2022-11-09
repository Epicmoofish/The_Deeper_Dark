package oceanicvoid.thedeeperdark.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import oceanicvoid.thedeeperdark.vibrations.ModVibrations;

public class SonicBombItem extends Item {
    public static final String ID = "sonic_bomb";
    public SonicBombItem(Properties properties) {
        super(addProperties(properties));
    }
    private static Properties addProperties(Properties properties) {
        properties.rarity(Rarity.EPIC);
        properties.tab(CreativeModeTab.TAB_TOOLS);
        return properties;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        p_41432_.gameEvent(null, ModVibrations.sonicBombVibration.get(), p_41433_.position());
        return super.use(p_41432_, p_41433_, p_41434_);
    }
}
