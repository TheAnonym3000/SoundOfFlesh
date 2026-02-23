package xyz.anonym.sound_of_flesh.content.pipes.voicebox;

import com.finchy.pipeorgans.content.pipes.generic.subtypes.DoubleExtensionBlock;
import com.finchy.pipeorgans.init.AllShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import xyz.anonym.sound_of_flesh.init.AllBlocks;

public class VoiceboxExtensionBlock extends DoubleExtensionBlock {
    public VoiceboxExtensionBlock(Properties pProperties) {
        super(pProperties);
        this.baseBlock = AllBlocks.VOICEBOX;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return AllShapes.genericExtensionShape(pState.getValue(SHAPE), pState.getValue(SIZE));
    }
}