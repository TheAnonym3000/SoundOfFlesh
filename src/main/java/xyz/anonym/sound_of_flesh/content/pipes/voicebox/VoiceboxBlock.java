package xyz.anonym.sound_of_flesh.content.pipes.voicebox;

import com.finchy.pipeorgans.content.pipes.generic.subtypes.DoublePipeBlock;
import com.finchy.pipeorgans.init.AllShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import xyz.anonym.sound_of_flesh.init.AllBlockEntities;
import xyz.anonym.sound_of_flesh.init.AllBlocks;

public class VoiceboxBlock extends DoublePipeBlock {
    public VoiceboxBlock(Properties pProperties) {
        super(pProperties);
        baseBlock = AllBlocks.VOICEBOX;
        extensionBlock = AllBlocks.VOICEBOX_EXTENSION;
        blockEntityType = AllBlockEntities.VOICEBOX_BLOCK_ENTITY;

    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return AllShapes.genericPipeShape(pState.getValue(SIZE), pState.getValue(WALL), pState.getValue(FACING));
    }
}