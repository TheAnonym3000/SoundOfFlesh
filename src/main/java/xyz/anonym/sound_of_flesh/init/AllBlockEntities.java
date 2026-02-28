package xyz.anonym.sound_of_flesh.init;

import com.finchy.pipeorgans.content.pipes.generic.GenericPipeBlockEntity;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import xyz.anonym.sound_of_flesh.SoundOfFlesh;
import xyz.anonym.sound_of_flesh.content.generic.lung.LungBlockEntity;
import xyz.anonym.sound_of_flesh.content.pipes.voicebox.VoiceboxBlockEntity;
import xyz.anonym.sound_of_flesh.content.pipes.voicebox.VoiceboxRenderer;

import java.util.function.Supplier;

public class AllBlockEntities {
    private static final CreateRegistrate REGISTRATE = SoundOfFlesh.registrate();

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, SoundOfFlesh.MODID);

    public static final BlockEntityEntry<VoiceboxBlockEntity> VOICEBOX_BLOCK_ENTITY = REGISTRATE
            .blockEntity("voicebox", VoiceboxBlockEntity::new)
            .validBlocks(AllBlocks.VOICEBOX)
            .renderer(() -> VoiceboxRenderer::new)
            .register();

    public static final Supplier<BlockEntityType<LungBlockEntity>> LUNG_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "lung_block_entity",
            // The block entity type.
            () -> new BlockEntityType<LungBlockEntity>(
                    // The supplier to use for constructing the block entity instances.
                    LungBlockEntity::new,
                    // An optional value that, when true, only allows players with OP permissions
                    // to load NBT data (e.g. placing a block item)
                    false,
                    // A vararg of blocks that can have this block entity.
                    // This assumes the existence of the referenced blocks as DeferredBlock<Block>s.
                    AllBlocks.LUNG_BLOCK.get()
            )
    );

    public static void register() {
    }
}
