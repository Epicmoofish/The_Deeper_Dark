package oceanicvoid.thedeeperdark.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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

import java.util.Optional;

public class GatewayKeyItem extends Item {
    public GatewayKeyItem(Properties properties) {
        super(properties);
    }
    public InteractionResult useOn(UseOnContext useOnContext) {
        Player player = useOnContext.getPlayer();
        Level level = useOnContext.getLevel();
        BlockPos blockpos = useOnContext.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
       BlockPos blockpos1 = blockpos.relative(useOnContext.getClickedFace());
        if (BaseFireBlock.canBePlacedAt(level, blockpos1, useOnContext.getHorizontalDirection())) {
            Optional<PortalShape> optional = PortalShape.findEmptyPortalShape(level, blockpos1, Direction.Axis.X);
            optional = net.minecraftforge.event.ForgeEventFactory.onTrySpawnPortal(level, blockpos1, optional);
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
}
