package oceanicvoid.thedeeperdark.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import oceanicvoid.thedeeperdark.blocks.entity.ModBlockEntities;
import oceanicvoid.thedeeperdark.blocks.entity.SculkSniperBlockEntity;
import org.jetbrains.annotations.Nullable;

public class SculkSniperBlock extends BaseEntityBlock {
    public SculkSniperBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVE, false));
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_154464_) {
        p_154464_.add(ACTIVE);
    }
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

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
    public static boolean getActive(BlockState p_154488_) {
        return p_154488_.getValue(ACTIVE);
    }

    public static boolean canActivate(BlockState p_154490_) {
        return !getActive(p_154490_);
    }
    public void tick(BlockState p_222137_, ServerLevel p_222138_, BlockPos p_222139_, RandomSource p_222140_) {
        deactivate(p_222138_, p_222139_, p_222137_);
    }
    public static void deactivate(Level p_154408_, BlockPos p_154409_, BlockState p_154410_) {
        p_154408_.setBlock(p_154409_, p_154410_.setValue(ACTIVE, false), 3);
    }

    public static void activate(Level p_222127_, BlockPos p_222128_, BlockState p_222129_) {
        p_222127_.setBlock(p_222128_, p_222129_.setValue(ACTIVE, true), 3);
        p_222127_.scheduleTick(p_222128_, p_222129_.getBlock(), 300);

    }
    @javax.annotation.Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_222173_, BlockState p_222174_, BlockEntityType<T> p_222175_) {
        return !p_222173_.isClientSide ? BaseEntityBlock.createTickerHelper(p_222175_, ModBlockEntities.SCULK_SNIPER_TYPE.get(), (p_222182_, p_222183_, p_222184_, p_222185_) -> {
            p_222185_.getListener().tick(p_222182_);
        }) : null;
    }
}
