package xyz.anonym.sound_of_flesh.mixin;

import com.finchy.pipeorgans.content.windchest.WindchestBlock;
import com.finchy.pipeorgans.content.windchest.WindchestMasterBlock;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.anonym.sound_of_flesh.content.generic.trachea.TracheaBlock;
import xyz.anonym.sound_of_flesh.init.AllBlocks;

import static xyz.anonym.sound_of_flesh.content.generic.trachea.TracheaBlock.FACING;

@Mixin(WindchestBlock.class)
public class WindchestBlockMixin extends Block implements IWrenchable {

    public WindchestBlockMixin(Properties p_49795_) {
        super(p_49795_);
    }

    public boolean isMasterWindy(Level level, Direction facing, BlockPos pos) {
        BlockPos masterPos = getMasterPos(level, facing, pos);
        if (masterPos != pos) {
            if (level.getBlockState(masterPos).getBlock() == AllBlocks.TRACHEA.get()) {
                return level.getBlockState(masterPos).getValue(TracheaBlock.WINDY);
            }
            return level.getBlockState(masterPos).getValue(WindchestMasterBlock.WINDY);
        }
        return false;
    }

    public boolean isMasterPowered(Level level, Direction facing, BlockPos pos) {
        BlockPos masterPos = getMasterPos(level, facing, pos);
        if (masterPos != pos) {
            if (level.getBlockState(masterPos).getBlock() == AllBlocks.TRACHEA.get()) {
                return level.getBlockState(masterPos).getValue(TracheaBlock.POWERED);
            }
            return level.getBlockState(masterPos).getValue(WindchestBlock.POWERED);
        }
        return false;
    }

    public boolean isMasterActive(Level level, Direction facing, BlockPos pos) {
        BlockPos masterPos = getMasterPos(level, facing, pos);
        BlockState masterState = level.getBlockState(masterPos);
        if (masterPos != pos) {
            if (level.getBlockState(masterPos).getBlock() == AllBlocks.TRACHEA.get()) {
                return masterState.getValue(TracheaBlock.POWERED) && masterState.getValue(TracheaBlock.WINDY);
            }
            return masterState.getValue(WindchestBlock.POWERED) && masterState.getValue(WindchestMasterBlock.WINDY);
        }
        return false;

    }

    public BlockPos getMasterPos(Level level, Direction facing, BlockPos pos) {
        BlockPos currentPos = pos;
        for (int i=0; i<=12; i++) {
            currentPos = currentPos.relative(facing);
            BlockState currentBlock = level.getBlockState(currentPos);
            if (currentBlock.getBlock() instanceof WindchestMasterBlock && currentBlock.getValue(WindchestMasterBlock.FACING) == facing.getOpposite()) {
                return currentPos;
            } else if (currentBlock.getBlock() instanceof TracheaBlock && currentBlock.getValue(FACING) == facing.getOpposite()) {
                return currentPos;
            }
            if ( !(currentBlock.getBlock() instanceof WindchestBlock
                    && (currentBlock.getValue(WindchestBlock.FACING) == facing)) ) {
                break;
            }
        }
        return pos;
    }

    @Inject(method = "neighborChanged", at = @At(value = "TAIL"))
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pNeighborBlock, BlockPos pNeighborPos, boolean pMovedByPiston, CallbackInfo ci) {
        Direction facing = pState.getValue(FACING);
        if (pPos.relative(facing).equals(pNeighborPos) ) {
            pLevel.setBlock(pPos, pState.setValue(WindchestBlock.POWERED, isMasterPowered(pLevel, facing, pPos)), 3);
        }
    }
}
