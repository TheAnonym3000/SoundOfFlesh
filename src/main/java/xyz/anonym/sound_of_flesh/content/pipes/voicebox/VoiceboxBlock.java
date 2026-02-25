package xyz.anonym.sound_of_flesh.content.pipes.voicebox;

import com.simibubi.create.content.decoration.steamWhistle.WhistleBlock;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.fluids.tank.FluidTankBlock;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.block.IBE;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import xyz.anonym.sound_of_flesh.init.AllBlockEntities;
import xyz.anonym.sound_of_flesh.init.AllBlocks;
import xyz.anonym.sound_of_flesh.init.AllShapes;

import static xyz.anonym.sound_of_flesh.content.pipes.voicebox.VoiceboxExtensionBlock.VoiceboxExtenderShape.SINGLE;

public class VoiceboxBlock extends Block implements IBE<VoiceboxBlockEntity>, IWrenchable {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WALL = WhistleBlock.WALL;
    public static final BooleanProperty POWERED = WhistleBlock.POWERED;
    public static final EnumProperty<VoiceboxSize> SIZE = EnumProperty.create("size", VoiceboxSize.class);

    public enum VoiceboxSize implements StringRepresentable {

        MICRO, TINY, SMALL, MEDIUM, LARGE, HUGE, GIANT;

        @Override
        public String getSerializedName() {
            return Lang.asId(name());
        }

    }

    public VoiceboxBlock(Properties p_49795_) {
        super(p_49795_);
        registerDefaultState(defaultBlockState().setValue(POWERED, false)
                .setValue(WALL, false)
                .setValue(SIZE, VoiceboxSize.MEDIUM));
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        AdvancementBehaviour.setPlacedBy(pLevel, pPos, pPlacer);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        Direction facing = WhistleBlock.getAttachedDirection(pState);
        BlockState attached = pLevel.getBlockState(pPos.relative(facing));

        if (attached.getBlock().getClass().getName().equals("com.finchy.pipeorgans.content.windchest.WindchestBlock"))
            return true;

        return FluidTankBlock.isTank(pLevel.getBlockState(pPos.relative(WhistleBlock.getAttachedDirection(pState))));
    }

    @Override
    public BlockState getRotatedBlockState(BlockState originalState, Direction targetedFace) {
        return originalState.cycle(SIZE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(FACING, POWERED, SIZE, WALL));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Level level = pContext.getLevel();
        BlockPos clickedPos = pContext.getClickedPos();
        Direction face = pContext.getClickedFace();
        boolean wall = true;
        if (face.getAxis() == Direction.Axis.Y) {
            face = pContext.getHorizontalDirection()
                    .getOpposite();
            wall = false;
        }

        BlockState state = super.getStateForPlacement(pContext).setValue(FACING, face.getOpposite())
                .setValue(POWERED, level.hasNeighborSignal(clickedPos))
                .setValue(WALL, wall);
        if (!canSurvive(state, level, clickedPos))
            return null;
        return state;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
                                 BlockHitResult pHit) {
        if (pPlayer == null)
            return InteractionResult.PASS;

        ItemStack heldItem = pPlayer.getItemInHand(pHand);
        if (AllBlocks.VOICEBOX.isIn(heldItem)) {
            incrementSize(pLevel, pPos);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public static void incrementSize(LevelAccessor pLevel, BlockPos pPos) {
        BlockState base = pLevel.getBlockState(pPos);
        if (!base.hasProperty(SIZE))
            return;
        VoiceboxSize size = base.getValue(SIZE);
        SoundType soundtype = base.getSoundType();
        BlockPos currentPos = pPos.above();

        for (int i = 1; i <= 6; i++) {
            BlockState blockState = pLevel.getBlockState(currentPos);
            float pVolume = (soundtype.getVolume() + 1.0F) / 2.0F;
            SoundEvent growSound = SoundEvents.NOTE_BLOCK_XYLOPHONE.value();
            SoundEvent hitSound = soundtype.getHitSound();

            if (AllBlocks.VOICEBOX_EXTENSION.has(blockState)) {
                if (blockState.getValue(VoiceboxExtensionBlock.SHAPE) == SINGLE) {
                    pLevel.setBlock(currentPos, blockState.setValue(VoiceboxExtensionBlock.SHAPE, VoiceboxExtensionBlock.VoiceboxExtenderShape.DOUBLE), 3);
                    float pPitch = (float) Math.pow(2, -(i * 2) / 12.0);
                    if (soundtype != null) {
                        pLevel.playSound(null, currentPos, growSound, SoundSource.BLOCKS, pVolume / 4f, pPitch);
                        pLevel.playSound(null, currentPos, hitSound, SoundSource.BLOCKS, pVolume, pPitch);
                        return;
                    }
                }
                currentPos = currentPos.above();
                continue;
            }

            if (!blockState.canBeReplaced())
                return;

            pLevel.setBlock(currentPos, AllBlocks.VOICEBOX_EXTENSION.getDefaultState()
                    .setValue(SIZE, size), 3);
            float pPitch = (float) Math.pow(2, -(i * 2 - 1) / 12.0);
            pLevel.playSound(null, currentPos, growSound, SoundSource.BLOCKS, pVolume / 4f, pPitch);
            pLevel.playSound(null, currentPos, hitSound, SoundSource.BLOCKS, pVolume, pPitch);
            return;
        }
    }

    public static void queuePitchUpdate(LevelAccessor level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos);
        if (blockState.getBlock()instanceof VoiceboxBlock whistle && !level.getBlockTicks()
                .hasScheduledTick(pos, whistle))
            level.scheduleTick(pos, whistle, 1);
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        withBlockEntityDo(pLevel, pPos, VoiceboxBlockEntity::updatePitch);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        FluidTankBlock.updateBoilerState(pState, pLevel, pPos.relative(WhistleBlock.getAttachedDirection(pState)));
        if (pOldState.getBlock() != this || pOldState.getValue(SIZE) != pState.getValue(SIZE))
            queuePitchUpdate(pLevel, pPos);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        IBE.onRemove(pState, pLevel, pPos, pNewState);
        FluidTankBlock.updateBoilerState(pState, pLevel, pPos.relative(WhistleBlock.getAttachedDirection(pState)));
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
                                boolean isMoving) {
        if (worldIn.isClientSide)
            return;
        boolean previouslyPowered = state.getValue(POWERED);
        if (previouslyPowered != worldIn.hasNeighborSignal(pos))
            worldIn.setBlock(pos, state.cycle(POWERED), 2);
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel,
                                  BlockPos pCurrentPos, BlockPos pFacingPos) {
        return WhistleBlock.getAttachedDirection(pState) == pFacing && !pState.canSurvive(pLevel, pCurrentPos)
                ? Blocks.AIR.defaultBlockState()
                : pState;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        VoiceboxSize size = pState.getValue(SIZE);
        if (!pState.getValue(WALL))
            return size == VoiceboxSize.TINY ? AllShapes.WHISTLE_TINY_FLOOR :
                    size == VoiceboxSize.SMALL ? AllShapes.WHISTLE_SMALL_FLOOR :
                            size == VoiceboxSize.MEDIUM ? AllShapes.WHISTLE_MEDIUM_FLOOR :
                                    size == VoiceboxSize.LARGE ? AllShapes.WHISTLE_LARGE_FLOOR :
                                            AllShapes.WHISTLE_HUGE_FLOOR;

        Direction direction = pState.getValue(FACING);
        return (size == VoiceboxSize.TINY ? AllShapes.WHISTLE_TINY_WALL :
                size == VoiceboxSize.SMALL ? AllShapes.WHISTLE_SMALL_WALL :
                        size == VoiceboxSize.MEDIUM ? AllShapes.WHISTLE_MEDIUM_WALL :
                                size == VoiceboxSize.LARGE ? AllShapes.WHISTLE_LARGE_WALL :
                                        AllShapes.WHISTLE_HUGE_WALL).get(direction);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
        return false;
    }

    /*public static Direction getAttachedDirection(BlockState state) {
        return state.getValue(WALL) ? state.getValue(FACING) : Direction.DOWN;
    }
     */
    @Override
    public Class<VoiceboxBlockEntity> getBlockEntityClass() {
        return VoiceboxBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends VoiceboxBlockEntity> getBlockEntityType() {
        return AllBlockEntities.VOICEBOX_BLOCK_ENTITY.get();
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pMirror == Mirror.NONE ? pState : pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }
}