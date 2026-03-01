package xyz.anonym.sound_of_flesh.content.generic.trachea;

import com.finchy.pipeorgans.content.windchest.WindchestBlock;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.fan.EncasedFanBlock;
import com.simibubi.create.content.kinetics.fan.EncasedFanBlockEntity;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.anonym.sound_of_flesh.content.generic.lung.LungBlock;
import xyz.anonym.sound_of_flesh.content.generic.lung.LungBlockEntity;
import xyz.anonym.sound_of_flesh.init.AllBlockEntities;
import xyz.anonym.sound_of_flesh.init.AllShapes;

public class TracheaBlock extends Block implements IWrenchable, EntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty WINDY = BooleanProperty.create("windy");

    public TracheaBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(POWERED, false)
                .setValue(WINDY, false));
    }

    // define blockstate params
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, POWERED, WINDY);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        Direction facing = pContext.getHorizontalDirection();

        Player player = pContext.getPlayer();
        boolean sneaking = player != null && player.isShiftKeyDown();

        return super.getStateForPlacement(pContext)
                .setValue(FACING, sneaking ? facing.getOpposite() : facing)
                .setValue(POWERED, level.hasNeighborSignal(pos));
    }


    public void updateSlaves(BlockState state, Level level, BlockPos pos, boolean powered) {

        Direction facing = state.getValue(FACING);
        BlockPos currentPos = pos;
        for (int i=0; i<=12; i++) {
            currentPos = currentPos.relative(facing);
            BlockState currentBlock = level.getBlockState(currentPos);
            if (currentBlock.getBlock() instanceof WindchestBlock && currentBlock.getValue(FACING) == facing.getOpposite()) {
                level.setBlock(currentPos, currentBlock.setValue(POWERED, powered), 2);
            } else { return; }
        }
    }

    // if neighbour updates
    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pNeighborBlock, BlockPos pNeighborPos, boolean isMoving) {
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
            if (powered) {
                triggerAnimation(pLevel, pPos, "controller");
            } else  {
                stopAnimation(pLevel, pPos, "controller");
            }
        }
        if (pNeighborBlock instanceof EncasedFanBlock || pNeighborBlock instanceof LungBlock) {
            updateMasterWindy(pLevel, pPos);
        }

    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        if (pLevel.isClientSide) // only on serverside
            return;
        updateSlaves(pState, pLevel, pPos, pLevel.hasNeighborSignal(pPos));
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pLevel.isClientSide) // only on serverside
            return;

        updateSlaves(pState, pLevel, pPos, false);
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    public static boolean updateMasterWindy(Level level, BlockPos masterPos) {
        if (level.isClientSide) { return false; }
        int activeFans = 0;
        for (Direction d : Iterate.directions) {
            if (level.getBlockEntity(masterPos.relative(d)) instanceof EncasedFanBlockEntity fanBE) {
                BlockState fanState = fanBE.getBlockState();
                if (fanState.getValue(EncasedFanBlock.FACING) == d.getOpposite() && (fanBE.getSpeed()*d.getAxisDirection().getStep() < 0)) {
                    activeFans++;
                }
            } else if (level.getBlockEntity(masterPos.relative(d)) instanceof LungBlockEntity lungBE) {
                BlockState lungState = lungBE.getBlockState();
                if (lungState.getValue(LungBlock.FACING) == d) {
                    activeFans++;
                }
            }
        }
        level.setBlock(masterPos, level.getBlockState(masterPos).setValue(WINDY, activeFans>0), 2);
        return activeFans > 0;
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pMirror == Mirror.NONE ? pState : pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return AllShapes.TRACHEA_SHAPE.get(state.getValue(FACING));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return AllBlockEntities.TRACHEA_BLOCK_ENTITY.get().create(pos, state);
    }

    private void triggerAnimation(Level level, BlockPos pos, String controllerName) {
        BlockEntity be = level.getBlockEntity(pos);
        assert be != null;
        Direction facing = be.getBlockState().getValue(FACING);
        BlockEntity otherBe = level.getBlockEntity(pos.relative(facing.getOpposite()));

        if (be instanceof TracheaBlockEntity tracheaBE) {
            tracheaBE.triggerAnim(controllerName, "powered");
        }
        if (otherBe instanceof LungBlockEntity lungBE) {
            lungBE.triggerAnim(controllerName, "breathe");
        }
    }

    private void stopAnimation(Level level, BlockPos pos, String controllerName) {
        BlockEntity be = level.getBlockEntity(pos);
        assert be != null;
        Direction facing = be.getBlockState().getValue(FACING);
        BlockEntity otherBe = level.getBlockEntity(pos.relative(facing.getOpposite()));

        if (be instanceof TracheaBlockEntity tracheaBE) {
            tracheaBE.stopTriggeredAnimation(controllerName, "powered");
        }
        if (otherBe instanceof LungBlockEntity lungBE) {
            lungBE.stopTriggeredAnimation(controllerName, "breathe");
        }
    }
}