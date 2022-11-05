package oceanicvoid.thedeeperdark.portalHelper;


import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import oceanicvoid.thedeeperdark.blocks.ModBlocks;

public class DeeperDarkPortalShape {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 6;
    public static final Block PORTAL_BLOCK = ModBlocks.DEEPER_DARK_PORTAL.get();
    public static final Block OUTSIDE_PORTAL_BLOCK = ModBlocks.ACTIVATED_DEEPSLATE.get();
    private static final BlockBehaviour.StatePredicate FRAME = (state, getter, pos) -> {
        return state.getBlock().equals(Blocks.REINFORCED_DEEPSLATE);
    };
    private final LevelAccessor level;
    private final Direction.Axis axis;
    private final Direction rightDir;
    private int numPortalBlocks;
    @Nullable
    private BlockPos bottomLeft;
    private int height;
    private final int width;

    public static Optional<DeeperDarkPortalShape> findEmptyPortalShape(LevelAccessor levelAccessor, BlockPos pos, Direction.Axis axis) {
        return findPortalShape(levelAccessor, pos, (deeperDarkPortalShape) -> {
            return deeperDarkPortalShape.isValid() && deeperDarkPortalShape.numPortalBlocks == 0;
        }, axis);
    }

    public static Optional<DeeperDarkPortalShape> findPortalShape(LevelAccessor levelAccessor, BlockPos pos, Predicate<DeeperDarkPortalShape> deeperDarkPortalShapePredicate, Direction.Axis axis) {
        Optional<DeeperDarkPortalShape> optional = Optional.of(new DeeperDarkPortalShape(levelAccessor, pos, axis)).filter(deeperDarkPortalShapePredicate);
        if (optional.isPresent()) {
            return optional;
        } else {
            Direction.Axis direction$axis = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
            return Optional.of(new DeeperDarkPortalShape(levelAccessor, pos, direction$axis)).filter(deeperDarkPortalShapePredicate);
        }
    }

    public DeeperDarkPortalShape(LevelAccessor levelAccessor, BlockPos pos, Direction.Axis axis) {
        this.level = levelAccessor;
        this.axis = axis;
        this.rightDir = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
        this.bottomLeft = this.calculateBottomLeft(pos);
        if (this.bottomLeft == null) {
            this.bottomLeft = pos;
            this.width = 1;
            this.height = 1;
        } else {
            this.width = this.calculateWidth();
            if (this.width > 0) {
                this.height = this.calculateHeight();
            }
        }

    }

    @Nullable
    private BlockPos calculateBottomLeft(BlockPos pos) {
        for(int i = Math.max(this.level.getMinBuildHeight(), pos.getY() - 21); pos.getY() > i && isEmpty(this.level.getBlockState(pos.below())); pos = pos.below()) {
        }

        Direction direction = this.rightDir.getOpposite();
        int j = this.getDistanceUntilEdgeAboveFrame(pos, direction) - 1;
        return j < 0 ? null : pos.relative(direction, j);
    }

    private int calculateWidth() {
        int i = this.getDistanceUntilEdgeAboveFrame(this.bottomLeft, this.rightDir);
        return i >= 2 && i <= 21 ? i : 0;
    }

    private int getDistanceUntilEdgeAboveFrame(BlockPos pos, Direction direction) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(int i = 0; i <= 21; ++i) {
            blockpos$mutableblockpos.set(pos).move(direction, i);
            BlockState blockstate = this.level.getBlockState(blockpos$mutableblockpos);
            if (!isEmpty(blockstate)) {
                if (FRAME.test(blockstate, this.level, blockpos$mutableblockpos)) {
                    return i;
                }
                break;
            }

            BlockState blockstate1 = this.level.getBlockState(blockpos$mutableblockpos.move(Direction.DOWN));
            if (!FRAME.test(blockstate1, this.level, blockpos$mutableblockpos)) {
                break;
            }
        }

        return 0;
    }

    private int calculateHeight() {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        int i = this.getDistanceUntilTop(blockpos$mutableblockpos);
        return i >= 3 && i <= 21 && this.hasTopFrame(blockpos$mutableblockpos, i) ? i : 0;
    }

    private boolean hasTopFrame(BlockPos.MutableBlockPos pos, int upAmount) {
        for(int i = 0; i < this.width; ++i) {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.set(this.bottomLeft).move(Direction.UP, upAmount).move(this.rightDir, i);
            if (!FRAME.test(this.level.getBlockState(blockpos$mutableblockpos), this.level, blockpos$mutableblockpos)) {
                return false;
            }
        }

        return true;
    }

    private int getDistanceUntilTop(BlockPos.MutableBlockPos pos) {
        for(int i = 0; i < 21; ++i) {
            pos.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, -1);
            if (!FRAME.test(this.level.getBlockState(pos), this.level, pos)) {
                return i;
            }

            pos.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, this.width);
            if (!FRAME.test(this.level.getBlockState(pos), this.level, pos)) {
                return i;
            }

            for(int j = 0; j < this.width; ++j) {
                pos.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, j);
                BlockState blockstate = this.level.getBlockState(pos);
                if (!isEmpty(blockstate)) {
                    return i;
                }

                if (blockstate.is(PORTAL_BLOCK)) {
                    ++this.numPortalBlocks;
                }
            }
        }

        return 21;
    }

    private static boolean isEmpty(BlockState state) {
        return state.isAir() || state.is(BlockTags.FIRE) || state.is(PORTAL_BLOCK);
    }

    public boolean isValid() {
        return this.bottomLeft != null && this.width == WIDTH && this.height == HEIGHT;
    }

    public void createPortalBlocks() {
        BlockState blockstate = PORTAL_BLOCK.defaultBlockState().setValue(NetherPortalBlock.AXIS, this.axis);
        BlockState outsidestate = OUTSIDE_PORTAL_BLOCK.defaultBlockState();
        BlockPos bottLeft = this.bottomLeft.relative(this.rightDir, -1).relative(Direction.UP, -1);
        BlockPos topRight = this.bottomLeft.relative(Direction.UP, this.height).relative(this.rightDir, this.width);
        BlockPos.betweenClosed(bottLeft, topRight).forEach((pos) -> {
            if (((pos.getX() == bottLeft.getX() || pos.getX() == topRight.getX()) && (pos.getZ() == bottLeft.getZ() || pos.getZ() == topRight.getZ())) || pos.getY() == bottLeft.getY() || pos.getY() == topRight.getY()) {
                this.level.setBlock(pos, outsidestate, 18);
            } else {
                this.level.setBlock(pos, blockstate, 18);
            }
        });
    }
    public boolean isComplete() {
        return this.isValid() && this.numPortalBlocks == this.width * this.height;
    }

    public static Vec3 getRelativePosition(BlockUtil.FoundRectangle rectangle, Direction.Axis axis, Vec3 vec3, EntityDimensions dimensions) {
        double d0 = (double)rectangle.axis1Size - (double)dimensions.width;
        double d1 = (double)rectangle.axis2Size - (double)dimensions.height;
        BlockPos blockpos = rectangle.minCorner;
        double d2;
        if (d0 > 0.0D) {
            float f = (float)blockpos.get(axis) + dimensions.width / 2.0F;
            d2 = Mth.clamp(Mth.inverseLerp(vec3.get(axis) - (double)f, 0.0D, d0), 0.0D, 1.0D);
        } else {
            d2 = 0.5D;
        }

        double d4;
        if (d1 > 0.0D) {
            Direction.Axis direction$axis = Direction.Axis.Y;
            d4 = Mth.clamp(Mth.inverseLerp(vec3.get(direction$axis) - (double)blockpos.get(direction$axis), 0.0D, d1), 0.0D, 1.0D);
        } else {
            d4 = 0.0D;
        }

        Direction.Axis direction$axis1 = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
        double d3 = vec3.get(direction$axis1) - ((double)blockpos.get(direction$axis1) + 0.5D);
        return new Vec3(d2, d4, d3);
    }
}