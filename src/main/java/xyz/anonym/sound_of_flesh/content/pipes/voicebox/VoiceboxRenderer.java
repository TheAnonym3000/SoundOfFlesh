package xyz.anonym.sound_of_flesh.content.pipes.voicebox;

import com.finchy.pipeorgans.content.pipes.generic.EPipeSizes;
import com.finchy.pipeorgans.content.pipes.generic.EPipeSizes.PipeSize;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.TypeRewriteRule;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import xyz.anonym.sound_of_flesh.init.AllPartialModels;

import static xyz.anonym.sound_of_flesh.content.pipes.voicebox.VoiceboxBlock.ExpandedWhistleSize.GIANT;
import static xyz.anonym.sound_of_flesh.content.pipes.voicebox.VoiceboxBlock.ExpandedWhistleSize.MICRO;


public class VoiceboxRenderer extends SafeBlockEntityRenderer<VoiceboxBlockEntity> {

    public VoiceboxRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    protected void renderSafe(VoiceboxBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {

        BlockState blockState = be.getBlockState();
        if (!(blockState.getBlock() instanceof VoiceboxBlock))
            return;

        Direction direction = blockState.getValue(VoiceboxBlock.FACING);
        EPipeSizes.PipeSize size = blockState.getValue(VoiceboxBlock.SIZE);

        PartialModel mouth = switch (size) {
            case MICRO -> AllPartialModels.VOICEBOX_MOUTH_MICRO;
            case TINY -> AllPartialModels.VOICEBOX_MOUTH_TINY;
            case SMALL -> AllPartialModels.VOICEBOX_MOUTH_SMALL;
            case MEDIUM -> AllPartialModels.VOICEBOX_MOUTH_MEDIUM;
            case LARGE -> AllPartialModels.VOICEBOX_MOUTH_LARGE;
            case HUGE -> AllPartialModels.VOICEBOX_MOUTH_HUGE;
            case GIANT -> AllPartialModels.VOICEBOX_MOUTH_GIANT;
        };

        float offset = be.animation.getValue(partialTicks);
        if (be.animation.getChaseTarget() > 0 && be.animation.getValue() > 0.5f) {
            float wiggleProgress = (AnimationTickHolder.getTicks(be.getLevel()) + partialTicks) /8f;
            offset -= (float) (Math.sin(wiggleProgress * (2 * Mth.PI) * (4 - size.ordinal())) / 8f);
        }

        CachedBuffers.partial(mouth, blockState)
                .center()
                .rotateYDegrees(AngleHelper.horizontalAngle(direction))
                .uncenter()
                .translate(0, -offset / 16f, 0)
                .light(light)
                .renderInto(ms, bufferSource.getBuffer(RenderType.solid()));

    }
}