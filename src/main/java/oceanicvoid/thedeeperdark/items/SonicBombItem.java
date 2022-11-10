package oceanicvoid.thedeeperdark.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import oceanicvoid.thedeeperdark.entities.projectile.SonicBombEntity;
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
    public InteractionResultHolder<ItemStack> use(Level p_43142_, Player p_43143_, InteractionHand p_43144_) {
        ItemStack itemstack = p_43143_.getItemInHand(p_43144_);
        p_43142_.playSound((Player)null, p_43143_.getX(), p_43143_.getY(), p_43143_.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (p_43142_.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!p_43142_.isClientSide) {
            SonicBombEntity sonicBomb = new SonicBombEntity(p_43142_, p_43143_);
            sonicBomb.setItem(itemstack);
            sonicBomb.shootFromRotation(p_43143_, p_43143_.getXRot(), p_43143_.getYRot(), 0.0F, 1.5F, 1.0F);
            p_43142_.addFreshEntity(sonicBomb);
        }

        p_43143_.awardStat(Stats.ITEM_USED.get(this));
        if (!p_43143_.getAbilities().instabuild) {
            itemstack.shrink(1);
        }
        return super.use(p_43142_, p_43143_, p_43144_);
    }
}
