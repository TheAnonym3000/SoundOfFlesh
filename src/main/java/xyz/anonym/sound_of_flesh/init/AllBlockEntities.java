package xyz.anonym.sound_of_flesh.init;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import xyz.anonym.sound_of_flesh.SoundOfFlesh;
import xyz.anonym.sound_of_flesh.content.generic.bronchi.BronchiBlockEntity;
import xyz.anonym.sound_of_flesh.content.generic.bronchi.BronchiBlockEntityRenderer;
import xyz.anonym.sound_of_flesh.content.generic.epiglottis.EpiglottisBlockEntity;
import xyz.anonym.sound_of_flesh.content.generic.epiglottis.EpiglottisBlockEntityRenderer;
import xyz.anonym.sound_of_flesh.content.generic.lung.LungBlockEntity;
import xyz.anonym.sound_of_flesh.content.generic.lung.LungBlockEntityRenderer;
import xyz.anonym.sound_of_flesh.content.generic.trachea.TracheaBlockEntity;
import xyz.anonym.sound_of_flesh.content.generic.trachea.TracheaBlockEntityRenderer;
import xyz.anonym.sound_of_flesh.content.pipes.voicebox.VoiceboxBlockEntity;
import xyz.anonym.sound_of_flesh.content.pipes.voicebox.VoiceboxRenderer;

public class AllBlockEntities {
    private static final CreateRegistrate REGISTRATE = SoundOfFlesh.registrate();

    public static final BlockEntityEntry<VoiceboxBlockEntity> VOICEBOX_BLOCK_ENTITY = REGISTRATE
            .blockEntity("voicebox", VoiceboxBlockEntity::new)
            .validBlocks(AllBlocks.VOICEBOX)
            .renderer(() -> VoiceboxRenderer::new)
            .register();

    public static final BlockEntityEntry<LungBlockEntity> LUNG_BLOCK_ENTITY = REGISTRATE
            .blockEntity("lung_block", LungBlockEntity::new)
            .validBlocks(AllBlocks.LUNG_BLOCK)
            .renderer(() -> LungBlockEntityRenderer::new)
            .register();

    public static final BlockEntityEntry<TracheaBlockEntity> TRACHEA_BLOCK_ENTITY = REGISTRATE
            .blockEntity("trachea", TracheaBlockEntity::new)
            .validBlocks(AllBlocks.TRACHEA)
            .renderer(() -> TracheaBlockEntityRenderer::new)
            .register();

    public static final BlockEntityEntry<BronchiBlockEntity> BRONCHI_BLOCK_ENTITY = REGISTRATE
            .blockEntity("bronchi", BronchiBlockEntity::new)
            .validBlocks(AllBlocks.BRONCHI_BLOCK)
            .renderer(() -> BronchiBlockEntityRenderer::new)
            .register();

    public static final BlockEntityEntry<EpiglottisBlockEntity> EPIGLOTTIS_BLOCK_ENTITY = REGISTRATE
            .blockEntity("epiglottis", EpiglottisBlockEntity::new)
            .validBlocks(AllBlocks.EPIGLOTTIS_BLOCK)
            .renderer(() -> EpiglottisBlockEntityRenderer::new)
            .register();

    public static void register() {
    }
}
