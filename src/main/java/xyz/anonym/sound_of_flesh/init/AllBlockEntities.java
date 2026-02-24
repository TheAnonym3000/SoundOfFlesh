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
import xyz.anonym.sound_of_flesh.SoundOfFlesh;
import xyz.anonym.sound_of_flesh.content.pipes.voicebox.VoiceboxBlockEntity;
import xyz.anonym.sound_of_flesh.content.pipes.voicebox.VoiceboxRenderer;

public class AllBlockEntities {
    private static final CreateRegistrate REGISTRATE = SoundOfFlesh.registrate();


    public static final BlockEntityEntry<VoiceboxBlockEntity> VOICEBOX_BLOCK_ENTITY = REGISTRATE
            .blockEntity("expanded_steam_whistle", VoiceboxBlockEntity::new)
            .validBlocks(AllBlocks.VOICEBOX)
            .renderer(() -> VoiceboxRenderer::new)
            .register();

    public static void register() {
    }
}
