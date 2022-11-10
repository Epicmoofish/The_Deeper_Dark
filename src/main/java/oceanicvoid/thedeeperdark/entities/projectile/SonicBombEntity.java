package oceanicvoid.thedeeperdark.entities.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import oceanicvoid.thedeeperdark.entities.ModEntities;
import oceanicvoid.thedeeperdark.items.ModItems;
import oceanicvoid.thedeeperdark.vibrations.ModVibrations;

public class SonicBombEntity extends ThrowableItemProjectile {
    public SonicBombEntity(EntityType<? extends SonicBombEntity> entityEntityType, Level level) {
        super(entityEntityType,level);
    }
    public SonicBombEntity(Level p_37399_, LivingEntity p_37400_) {
        super(ModEntities.SONIC_BOMB_ENTITY.get(), p_37400_, p_37399_);
    }

    public SonicBombEntity(Level p_37394_, double p_37395_, double p_37396_, double p_37397_) {
        super(ModEntities.SONIC_BOMB_ENTITY.get(), p_37395_, p_37396_, p_37397_, p_37394_);
    }


    protected Item getDefaultItem() {
        return ModItems.SONIC_BOMB.get();
    }

//    private ParticleOptions getParticle() {
//        ItemStack itemstack = this.getItemRaw();
//        return (ParticleOptions)(itemstack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleOption(ParticleTypes.ITEM, itemstack));
//    }

    public void handleEntityEvent(byte p_37402_) {

    }

    @Override
    public void tick() {
        this.hasBeenShot=true;
        super.tick();
    }

    protected void onHit(HitResult p_37260_) {
        HitResult.Type hitresult$type = p_37260_.getType();
        if (hitresult$type == HitResult.Type.ENTITY) {
            this.onHitEntity((EntityHitResult)p_37260_);
            this.level.gameEvent(ModVibrations.sonicBombVibration.get(), p_37260_.getLocation(), GameEvent.Context.of(this, (BlockState)null));
        } else if (hitresult$type == HitResult.Type.BLOCK) {
            BlockHitResult blockhitresult = (BlockHitResult)p_37260_;
            this.onHitBlock(blockhitresult);
            BlockPos blockpos = blockhitresult.getBlockPos();
            this.level.gameEvent(ModVibrations.sonicBombVibration.get(), blockpos, GameEvent.Context.of(this, this.level.getBlockState(blockpos)));
        }
    }
}
