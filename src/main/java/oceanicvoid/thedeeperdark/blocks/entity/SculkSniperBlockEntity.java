package oceanicvoid.thedeeperdark.blocks.entity;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;
import net.minecraft.world.phys.Vec3;
import oceanicvoid.thedeeperdark.blocks.SculkSniperBlock;
import oceanicvoid.thedeeperdark.vibrations.ModVibrations;
import org.jetbrains.annotations.Nullable;

public class SculkSniperBlockEntity extends BlockEntity implements VibrationListener.VibrationListenerConfig {
    public SculkSniperBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }
    public SculkSniperBlockEntity(BlockPos p_222835_, BlockState p_222836_) {
        super(ModBlockEntities.SCULK_SNIPER_TYPE.get(), p_222835_, p_222836_);
    }
    private VibrationListener listener = new VibrationListener(new BlockPositionSource(this.worldPosition), 32, this, (VibrationListener.ReceivingEvent)null, 128.0f, 5);

    @Override
    public boolean shouldListen(ServerLevel p_223872_, GameEventListener p_223873_, BlockPos p_223874_, GameEvent event, GameEvent.Context p_223876_) {
        if (!SculkSniperBlock.canActivate(p_223872_.getBlockState(this.getBlockPos()))) return false;
        if (p_223876_.sourceEntity()==null) return false;
        if (!p_223876_.sourceEntity().isAttackable()) return false;
        if (p_223876_.sourceEntity() instanceof LivingEntity ent) {
            if (ent.isDeadOrDying()) return false;
        }
        if (p_223876_.sourceEntity() instanceof Player player) {
            if (player.isCreative() || player.isSpectator()) return false;
        }
        return true;
    }
    @Override
    public boolean isValidVibration(GameEvent p_223878_, GameEvent.Context p_223879_) {
        if (!p_223878_.is(this.getListenableEvents())) {
            return false;
        } else {
            Entity entity = p_223879_.sourceEntity();
            if (entity != null) {
                if (entity.isSpectator()) {
                    return false;
                }
            }

            if (p_223879_.affectedState() != null) {
                return !p_223879_.affectedState().is(BlockTags.DAMPENS_VIBRATIONS);
            } else {
                return true;
            }
        }
    }
    public TagKey<GameEvent> getListenableEvents() {
        return ModVibrations.Tag.SNIPER_CAN_LISTEN;
    }
    @Override
    public void onSignalReceive(ServerLevel p_223865_, GameEventListener listener, BlockPos p_223867_, GameEvent event, @Nullable Entity entity, @Nullable Entity p_223870_, float p_223871_) {
        if (entity != null) {
            Vec3 vec3 = entity.position().add(0.0D, (double)1.6F, 0.0D);
            Vec3 vec31 = new Vec3(this.getBlockPos().getX(),this.getBlockPos().getY(),this.getBlockPos().getZ()).subtract(vec3);
            Vec3 vec32 = vec31.normalize();

            for(int i = 1; i < Mth.floor(vec31.length()) + 7; ++i) {
                Vec3 vec33 = vec3.add(vec32.scale((double)i));
                if (level instanceof ServerLevel sl) {
                    sl.sendParticles(ParticleTypes.SONIC_BOOM, vec33.x, vec33.y, vec33.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                }
            }
            this.level.playSound((Player)null, this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, 3.0F, 1.0F);
            entity.hurt((new DamageSource("sonic_boom")).bypassArmor().bypassEnchantments().setMagic(), 500);
            if (this.level.getBlockState(this.getBlockPos()).getBlock() instanceof SculkSniperBlock sniperBlock) {
                if (level instanceof ServerLevel sl && SculkSniperBlock.canActivate(sl.getBlockState(this.getBlockPos()))) {
                    SculkSniperBlock.activate(sl, this.getBlockPos(), sl.getBlockState(this.getBlockPos()));
                }
            }
        }
    }

    public VibrationListener getListener() {
        return this.listener;
    }
}
