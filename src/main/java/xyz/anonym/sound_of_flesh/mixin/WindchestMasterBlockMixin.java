package xyz.anonym.sound_of_flesh.mixin;

import com.finchy.pipeorgans.content.windchest.WindchestMasterBlock;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.fan.EncasedFanBlock;
import com.simibubi.create.content.kinetics.fan.EncasedFanBlockEntity;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import xyz.anonym.sound_of_flesh.SoundOfFlesh;
import xyz.anonym.sound_of_flesh.content.generic.lung.LungBlock;

import static com.finchy.pipeorgans.content.windchest.WindchestMasterBlock.*;

@Mixin(WindchestMasterBlock.class)
public abstract class WindchestMasterBlockMixin extends Block implements IWrenchable {
    @Shadow
    public abstract void updateSlaves(BlockState state, Level level, BlockPos pos, boolean powered);

    public WindchestMasterBlockMixin(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void neighborChanged(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Block pNeighborBlock, @NotNull BlockPos pNeighborPos, boolean isMoving) {
        if (pLevel.isClientSide) // only on serverside
            return;
        if (pPos.relative(pState.getValue(FACING)).equals(pNeighborPos)) { return; } // suppress updates from where windchests would be

        boolean previouslyPowered = pState.getValue(POWERED);
        boolean powered = false;
        Direction facing = pState.getValue(FACING);
        for (Direction i : Direction.values()) {
            if (i == facing) { continue; }
            if (pLevel.getSignal(pPos.relative(i), i)>0) { powered = true; break; }
        }
        //boolean powered = pLevel.hasNeighborSignal(pPos);
        if (previouslyPowered != powered) {
            pLevel.setBlock(pPos, pState.setValue(POWERED, powered), 2);
            updateSlaves(pState, pLevel, pPos, powered);
        }
        sound_of_flesh$updateMasterWindy(pLevel, pPos, pNeighborBlock);
    }

    @Unique
    private static void sound_of_flesh$updateMasterWindy(Level level, BlockPos masterPos, Block neighborBlock) {
        if (level.isClientSide) { return; }
        int activeFans = 0;
        for (Direction d : Iterate.directions) {
        }
        level.setBlock(masterPos, level.getBlockState(masterPos).setValue(WINDY, activeFans>0), 2);
    }
}
