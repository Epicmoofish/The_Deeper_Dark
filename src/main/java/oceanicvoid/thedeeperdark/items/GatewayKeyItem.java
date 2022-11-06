package oceanicvoid.thedeeperdark.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.portal.PortalShape;
import oceanicvoid.thedeeperdark.items.general.LoreItem;
import oceanicvoid.thedeeperdark.portalHelper.DeeperDarkPortalShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class GatewayKeyItem extends Item {
    public static final String ID = "gateway_key";
    public GatewayKeyItem(Properties properties) {
        super(addProperties(properties));
    }
    private static Properties addProperties(Properties properties) {
        properties.stacksTo(1);
        properties.rarity(Rarity.EPIC);
        properties.tab(CreativeModeTab.TAB_TOOLS);
        return properties;
    }
    public InteractionResult useOn(UseOnContext useOnContext) {
        Player player = useOnContext.getPlayer();
        Level level = useOnContext.getLevel();
        BlockPos blockpos = useOnContext.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
       BlockPos blockpos1 = blockpos.relative(useOnContext.getClickedFace());
        if (BaseFireBlock.canBePlacedAt(level, blockpos1, useOnContext.getHorizontalDirection())) {
            Optional<DeeperDarkPortalShape> optional = DeeperDarkPortalShape.findEmptyPortalShape(level, blockpos1, Direction.Axis.X);
            if (optional.isPresent()) {
                optional.get().createPortalBlocks();
                level.playSound(player, blockpos1, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                ItemStack itemstack = useOnContext.getItemInHand();
                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, blockpos1, itemstack);
                }
                useOnContext.getItemInHand().shrink(1);
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }
            return InteractionResult.FAIL;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> loreComponent, TooltipFlag toolTipFlag) {
        LoreItem.addLore(this,loreComponent);
    }
}
