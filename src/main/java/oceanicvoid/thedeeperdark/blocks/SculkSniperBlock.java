package oceanicvoid.thedeeperdark.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEventListener;
import oceanicvoid.thedeeperdark.blocks.entity.ModBlockEntities;
import oceanicvoid.thedeeperdark.blocks.entity.SculkSniperBlockEntity;
import org.jetbrains.annotations.Nullable;

public class SculkSniperBlock extends BaseEntityBlock {
    public SculkSniperBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new SculkSniperBlockEntity(p_153215_, p_153216_);
    }
    @javax.annotation.Nullable
    public <T extends BlockEntity> GameEventListener getListener(ServerLevel p_222165_, T p_222166_) {
        if (p_222166_ instanceof SculkSniperBlockEntity sculksniperblockentity) {
            return sculksniperblockentity.getListener();
        } else {
            return null;
        }
    }

    @javax.annotation.Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_222173_, BlockState p_222174_, BlockEntityType<T> p_222175_) {
        return !p_222173_.isClientSide ? BaseEntityBlock.createTickerHelper(p_222175_, ModBlockEntities.SCULK_SNIPER_TYPE.get(), (p_222182_, p_222183_, p_222184_, p_222185_) -> {
            p_222185_.getListener().tick(p_222182_);
        }) : null;
    }
}
