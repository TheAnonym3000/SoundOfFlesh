package xyz.anonym.sound_of_flesh;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import java.lang.reflect.Method;


public class CSOSHelper {

    public static boolean isWindchest(BlockState state) {
        return state.getBlock().getClass().getName()
                .equals("com.finchy.pipeorgans.content.windchest.WindchestBlock");
    }

    private static final String WINDCHEST_BLOCK =
            "com.finchy.pipeorgans.content.windchest.WindchestBlock";

    public static boolean isWindchestActive(Level level, BlockPos whistlePos, Direction facing) {
        BlockPos chestPos = whistlePos.relative(facing);
        BlockState state = level.getBlockState(chestPos);
        Block block = state.getBlock();

        if (!block.getClass().getName().equals(WINDCHEST_BLOCK))
            return false;

        try {
            Method m = block.getClass().getMethod(
                    "isMasterActive",
                    Level.class,
                    Direction.class,
                    BlockPos.class
            );

            return (boolean) m.invoke(block, level, state.getValue(
                    net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING
            ), chestPos);

        } catch (Throwable t) {
            return false;
        }
    }
}