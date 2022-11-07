package oceanicvoid.thedeeperdark.blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import oceanicvoid.thedeeperdark.dimensions.ModDimensions;
import oceanicvoid.thedeeperdark.dimensions.teleporter.DeeperDarkTeleporter;
import oceanicvoid.thedeeperdark.mixin.EntityMixin;
import oceanicvoid.thedeeperdark.mixininterfaces.IEntityMixin;
import oceanicvoid.thedeeperdark.particles.ModParticles;

public class DeeperDarkPortalBlock extends Block {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    protected static final int AABB_OFFSET = 2;
    protected static final VoxelShape X_AXIS_AABB = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape Z_AXIS_AABB = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    public DeeperDarkPortalBlock(BlockBehaviour.Properties p_54909_) {
        super(p_54909_);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X));
    }

    @Override
    public boolean canBeReplaced(BlockState p_60470_, Fluid p_60471_) {
        return false;
    }
    public VoxelShape getShape(BlockState p_54942_, BlockGetter p_54943_, BlockPos p_54944_, CollisionContext p_54945_) {
        switch ((Direction.Axis)p_54942_.getValue(AXIS)) {
            case Z:
                return Z_AXIS_AABB;
            case X:
            default:
                return X_AXIS_AABB;
        }
    }

    public void randomTick(BlockState p_221799_, ServerLevel p_221800_, BlockPos p_221801_, RandomSource p_221802_) {
        if (p_221800_.dimensionType().natural() && p_221800_.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING) && p_221802_.nextInt(2000) < p_221800_.getDifficulty().getId()) {
            while(p_221800_.getBlockState(p_221801_).is(this)) {
                p_221801_ = p_221801_.below();
            }

            if (p_221800_.getBlockState(p_221801_).isValidSpawn(p_221800_, p_221801_, EntityType.WARDEN)) {
                Entity entity = EntityType.WARDEN.spawn(p_221800_, (CompoundTag)null, (Component)null, (Player)null, p_221801_.above(), MobSpawnType.STRUCTURE, false, false);
                if (entity != null) {
                    entity.setPortalCooldown();
                }
            }
        }

    }
    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entity) {
        if(!entity.isPassenger() && !entity.isVehicle() && entity.canChangeDimensions()) {
            ((IEntityMixin) entity).setInDeeperDarkPortal(true);
            if(entity.isOnPortalCooldown()) {
                entity.setPortalCooldown();
            }
            else {
                int i = entity.getPortalWaitTime();
                if(!entity.level.isClientSide && !pos.equals(entity.portalEntrancePos)) {
                    entity.portalEntrancePos = pos.immutable();
                }
                Level entityWorld = entity.level;
                if(entityWorld != null) {
                    MinecraftServer minecraftserver = entityWorld.getServer();
                    ResourceKey<Level> destination = entity.level.dimension() == ModDimensions.THEDEEPERDARK_KEY
                            ? Level.OVERWORLD : ModDimensions.THEDEEPERDARK_KEY;
                    if(minecraftserver != null) {
                        ServerLevel destinationWorld = minecraftserver.getLevel(destination);
                        if(destinationWorld != null && minecraftserver.isNetherEnabled() && !entity.isPassenger() && entity.portalTime >= i) {
                            entity.level.getProfiler().push("the_deeper_dark_portal");
                            entity.setPortalCooldown();
                            entity.changeDimension(destinationWorld, new DeeperDarkTeleporter(destinationWorld));
                            entity.level.getProfiler().pop();
                        }
                    }
                }
            }
        }
    }


    public void animateTick(BlockState p_221794_, Level p_221795_, BlockPos p_221796_, RandomSource p_221797_) {
        if (p_221797_.nextInt(100) == 0) {
            p_221795_.playLocalSound((double)p_221796_.getX() + 0.5D, (double)p_221796_.getY() + 0.5D, (double)p_221796_.getZ() + 0.5D, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.5F, p_221797_.nextFloat() * 0.4F + 0.8F, false);
        }

        for(int i = 0; i < 4; ++i) {
            double d0 = (double)p_221796_.getX() + p_221797_.nextDouble();
            double d1 = (double)p_221796_.getY() + p_221797_.nextDouble();
            double d2 = (double)p_221796_.getZ() + p_221797_.nextDouble();
            double d3 = ((double)p_221797_.nextFloat() - 0.5D) * 0.5D;
            double d4 = ((double)p_221797_.nextFloat() - 0.5D) * 0.5D;
            double d5 = ((double)p_221797_.nextFloat() - 0.5D) * 0.5D;
            int j = p_221797_.nextInt(2) * 2 - 1;
            if (!p_221795_.getBlockState(p_221796_.west()).is(this) && !p_221795_.getBlockState(p_221796_.east()).is(this)) {
                d0 = (double)p_221796_.getX() + 0.5D + 0.25D * (double)j;
                d3 = (double)(p_221797_.nextFloat() * 2.0F * (float)j);
            } else {
                d2 = (double)p_221796_.getZ() + 0.5D + 0.25D * (double)j;
                d5 = (double)(p_221797_.nextFloat() * 2.0F * (float)j);
            }
            p_221795_.addParticle(ModParticles.DEEPER_DARK_PORTAL.get(), d0, d1, d2, d3, d4, d5);
        }

    }

    public ItemStack getCloneItemStack(BlockGetter p_54911_, BlockPos p_54912_, BlockState p_54913_) {
        return ItemStack.EMPTY;
    }

    public BlockState rotate(BlockState p_54925_, Rotation p_54926_) {
        switch (p_54926_) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch ((Direction.Axis)p_54925_.getValue(AXIS)) {
                    case Z:
                        return p_54925_.setValue(AXIS, Direction.Axis.X);
                    case X:
                        return p_54925_.setValue(AXIS, Direction.Axis.Z);
                    default:
                        return p_54925_;
                }
            default:
                return p_54925_;
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54935_) {
        p_54935_.add(AXIS);
    }
}