package xyz.anonym.sound_of_flesh.init;

import static com.simibubi.create.AllShapes.WHISTLE_BASE;
import static net.minecraft.core.Direction.NORTH;
import static net.minecraft.core.Direction.SOUTH;
import static net.minecraft.core.Direction.UP;

import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.BiFunction;


public class AllShapes {

    // Internally Shared Shapes
    private static final VoxelShape
            WHISTLE_TINY = shape(4, 3, 4, 12, 16, 12).build(),
            WHISTLE_SMALL = shape(4, 3, 4, 12, 16, 12).build(),
            WHISTLE_MEDIUM = shape(3, 3, 3, 13, 16, 13).build(),
            WHISTLE_LARGE = shape(2, 3, 2, 14, 16, 14).build(),
            WHISTLE_HUGE = shape(2, 3, 2, 14, 16, 14).build()

                    ;
    // Independent Shapers
    public static final VoxelShape

            WHISTLE_TINY_FLOOR = shape(WHISTLE_TINY).add(WHISTLE_BASE.get(UP))
            .build(),
            WHISTLE_SMALL_FLOOR = shape(WHISTLE_SMALL).add(WHISTLE_BASE.get(UP))
                    .build(),
            WHISTLE_MEDIUM_FLOOR = shape(WHISTLE_MEDIUM).add(WHISTLE_BASE.get(UP))
                    .build(),
            WHISTLE_LARGE_FLOOR = shape(WHISTLE_LARGE).add(WHISTLE_BASE.get(UP))
                    .build(),
            WHISTLE_HUGE_FLOOR = shape(WHISTLE_HUGE).add(WHISTLE_BASE.get(UP))
                    .build(),

    WHISTLE_EXTENDER_TINY = shape(4, 0, 4, 12, 10, 12).build(),
            WHISTLE_EXTENDER_SMALL = shape(4, 0, 4, 12, 10, 12).build(),
            WHISTLE_EXTENDER_MEDIUM = shape(3, 0, 3, 13, 10, 13).build(),
            WHISTLE_EXTENDER_LARGE = shape(2, 0, 2, 14, 10, 14).build(),
            WHISTLE_EXTENDER_HUGE = shape(2, 0, 2, 14, 10, 14).build(),
            WHISTLE_EXTENDER_TINY_DOUBLE = shape(4, 0, 4, 12, 18, 12).build(),
            WHISTLE_EXTENDER_SMALL_DOUBLE = shape(4, 0, 4, 12, 18, 12).build(),
            WHISTLE_EXTENDER_MEDIUM_DOUBLE = shape(3, 0, 3, 13, 18, 13).build(),
            WHISTLE_EXTENDER_LARGE_DOUBLE = shape(2, 0, 2, 14, 18, 14).build(),
            WHISTLE_EXTENDER_HUGE_DOUBLE = shape(2, 0, 2, 14, 18, 14).build(),
            WHISTLE_EXTENDER_TINY_DOUBLE_CONNECTED = shape(4, 0, 4, 12, 16, 12).build(),
            WHISTLE_EXTENDER_SMALL_DOUBLE_CONNECTED = shape(4, 0, 4, 12, 16, 12).build(),
            WHISTLE_EXTENDER_MEDIUM_DOUBLE_CONNECTED = shape(3, 0, 3, 13, 16, 13).build(),
            WHISTLE_EXTENDER_LARGE_DOUBLE_CONNECTED = shape(2, 0, 2, 14, 16, 14).build(),
            WHISTLE_EXTENDER_HUGE_DOUBLE_CONNECTED = shape(2, 0, 2, 14, 16, 14).build();

    // More Shapers
    public static final VoxelShaper

            WHISTLE_TINY_WALL = shape(WHISTLE_TINY).add(WHISTLE_BASE.get(NORTH))
            .forHorizontal(SOUTH),
            WHISTLE_SMALL_WALL = shape(WHISTLE_SMALL).add(WHISTLE_BASE.get(NORTH))
                    .forHorizontal(SOUTH),
            WHISTLE_MEDIUM_WALL = shape(WHISTLE_MEDIUM).add(WHISTLE_BASE.get(NORTH))
                    .forHorizontal(SOUTH),
            WHISTLE_LARGE_WALL = shape(WHISTLE_LARGE).add(WHISTLE_BASE.get(NORTH))
                    .forHorizontal(SOUTH),
            WHISTLE_HUGE_WALL = shape(WHISTLE_HUGE).add(WHISTLE_BASE.get(NORTH))
                    .forHorizontal(SOUTH)

                    ;

    public static final VoxelShaper LUNG_SHAPE =
            shape(1, 7, 2, 6, 11, 11)
                    .add(10, 7, 2,15, 11, 11)
                    .add(7, 8, 9, 9, 10 ,16)
                    .forHorizontal(NORTH);

    public static final VoxelShaper TRACHEA_SHAPE =
            shape(7, 8, 0, 9, 10, 16)
                    .forDirectional(NORTH);


    private static Builder shape(VoxelShape shape) {
        return new Builder(shape);
    }

    private static Builder shape(double x1, double y1, double z1, double x2, double y2, double z2) {
        return shape(cuboid(x1, y1, z1, x2, y2, z2));
    }

    private static VoxelShape cuboid(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Block.box(x1, y1, z1, x2, y2, z2);
    }

    public static class Builder {

        private VoxelShape shape;

        public Builder(VoxelShape shape) {
            this.shape = shape;
        }

        public Builder add(VoxelShape shape) {
            this.shape = Shapes.or(this.shape, shape);
            return this;
        }

        public Builder add(double x1, double y1, double z1, double x2, double y2, double z2) {
            return add(cuboid(x1, y1, z1, x2, y2, z2));
        }

        public Builder erase(double x1, double y1, double z1, double x2, double y2, double z2) {
            this.shape = Shapes.join(shape, cuboid(x1, y1, z1, x2, y2, z2), BooleanOp.ONLY_FIRST);
            return this;
        }

        public VoxelShape build() {
            return shape;
        }

        public VoxelShaper forHorizontal(Direction direction) {
            return VoxelShaper.forHorizontal(build(), direction);
        }

        public VoxelShaper build(BiFunction<VoxelShape, Direction, VoxelShaper> factory, Direction direction) {
            return factory.apply(shape, direction);
        }

        public VoxelShaper forDirectional(Direction direction) {
            return build(VoxelShaper::forDirectional, direction);
        }

    }

}