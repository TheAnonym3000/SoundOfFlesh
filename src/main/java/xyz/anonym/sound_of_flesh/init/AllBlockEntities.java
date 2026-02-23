package xyz.anonym.sound_of_flesh.init;

import com.finchy.pipeorgans.content.pipes.generic.GenericPipeBlockEntity;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.Block;
import xyz.anonym.sound_of_flesh.Sound_of_flesh;
import xyz.anonym.sound_of_flesh.content.pipes.voicebox.VoiceboxBlockEntity;
import xyz.anonym.sound_of_flesh.content.pipes.voicebox.VoiceboxRenderer;

public class AllBlockEntities {
    private static final CreateRegistrate REGISTRATE = Sound_of_flesh.registrate();


    public static final BlockEntityEntry<VoiceboxBlockEntity> VOICEBOX_BLOCK_ENTITY = registerPipeBlockEntity(
            "voicebox_block_entity",
            VoiceboxBlockEntity::new,
            AllBlocks.VOICEBOX,
            () -> VoiceboxRenderer::new);


    private static <T extends GenericPipeBlockEntity> BlockEntityEntry<T> registerPipeBlockEntity(
            String name, BlockEntityBuilder.BlockEntityFactory<T> factory, NonNullSupplier<? extends Block> block,
            NonNullSupplier<NonNullFunction<BlockEntityRendererProvider.Context, BlockEntityRenderer<? super T>>> renderer) {
        return REGISTRATE.blockEntity(name, factory)
                .validBlock(block)
                .renderer(renderer)
                .register();
    }



    public static void register() {
    }
}
