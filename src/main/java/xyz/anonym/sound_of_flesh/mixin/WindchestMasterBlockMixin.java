package xyz.anonym.sound_of_flesh.mixin;

import com.finchy.pipeorgans.content.windchest.WindchestMasterBlock;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.fan.EncasedFanBlock;
import com.simibubi.create.content.kinetics.fan.EncasedFanBlockEntity;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import xyz.anonym.sound_of_flesh.content.generic.lung.LungBlock;

import static com.finchy.pipeorgans.content.windchest.WindchestMasterBlock.*;

@Mixin(WindchestMasterBlock.class)
public abstract class WindchestMasterBlockMixin extends Block implements IWrenchable {
    @Shadow
    public abstract @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext);

    public WindchestMasterBlockMixin(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pNeighborBlock, BlockPos pNeighborPos, boolean isMoving) {
        if (pNeighborBlock instanceof EncasedFanBlock || pNeighborBlock instanceof LungBlock) { sound_of_flesh$updateMasterWindy(pLevel, pPos); }
    }

    @Unique
    private static void sound_of_flesh$updateMasterWindy(Level level, BlockPos masterPos) {
        if (level.isClientSide) { return; }
        int activeFans = 0;
        for (Direction d : Iterate.directions) {
            if (level.getBlockEntity(masterPos.relative(d)) instanceof EncasedFanBlockEntity fanBE) {
                BlockState fanState = fanBE.getBlockState();
                if (fanState.getValue(EncasedFanBlock.FACING) == d.getOpposite() && (fanBE.getSpeed()*d.getAxisDirection().getStep() < 0)) {
                    activeFans++;
                }
            } else if (level.getBlockState(masterPos.relative(d)).getBlock() instanceof LungBlock LungBlock && level.getBlockState(masterPos.relative(d)).getValue(xyz.anonym.sound_of_flesh.content.generic.lung.LungBlock.FACING) == d.getOpposite()) {
                activeFans++;
            }
        }
        level.setBlock(masterPos, level.getBlockState(masterPos).setValue(WINDY, activeFans>0), 2);
    }
}
