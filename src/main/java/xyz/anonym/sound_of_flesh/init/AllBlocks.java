package xyz.anonym.sound_of_flesh.init;

import com.finchy.pipeorgans.content.pipes.generic.GenericExtensionBlock;
import com.finchy.pipeorgans.content.pipes.generic.GenericPipeBlock;
import com.finchy.pipeorgans.content.pipes.generic.GenericPipeBlockItem;
import com.finchy.pipeorgans.content.windchest.WindchestMasterBlock;
import com.finchy.pipeorgans.data.AssetLookup;
import com.finchy.pipeorgans.data.BlockStateGen;
import com.finchy.pipeorgans.init.AllTags;
import com.github.elenterius.biomancy.init.ModBlocks;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import xyz.anonym.sound_of_flesh.SoundOfFlesh;
import xyz.anonym.sound_of_flesh.content.generic.lung.LungBlock;
import xyz.anonym.sound_of_flesh.content.pipes.voicebox.VoiceboxBlock;
import xyz.anonym.sound_of_flesh.content.pipes.voicebox.VoiceboxExtensionBlock;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;

public class AllBlocks {
    private static final CreateRegistrate REGISTRATE = SoundOfFlesh.registrate();

    static {
        REGISTRATE.setCreativeTab(AllTabs.MAIN_TAB);
    }


    public static final BlockEntry<WindchestMasterBlock> TRACHEA = REGISTRATE.block("trachea", WindchestMasterBlock::new)
            .initialProperties(ModBlocks.TUBULAR_FLESH_BLOCK::get)
            .properties(p -> p
                    .requiresCorrectToolForDrops()
                    .noOcclusion())
            .lang("Trachea")
            .blockstate((c, p) -> p.horizontalBlock(c.get(), AssetLookup.forPowered(c, p)))
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<LungBlock> LUNG_BLOCK = REGISTRATE.block("lung_block", LungBlock::new)
            .initialProperties(ModBlocks.TUBULAR_FLESH_BLOCK::get)
            .properties(p -> p
                    .requiresCorrectToolForDrops()
                    .noOcclusion())
            .lang("Lung Block")
            .blockstate((c, p) -> p.horizontalBlock(c.get(), AssetLookup.forPowered(c, p)))
            .item()
            .transform(customItemModel())
            .register();


    public static final BlockEntry<VoiceboxBlock> VOICEBOX = registerPipeBlock(
            "voicebox",
            VoiceboxBlock::new,
            ModBlocks.TUBULAR_FLESH_BLOCK::get,
            GenericPipeBlockItem.StopSize.EIGHT,
            BlockTags.MINEABLE_WITH_PICKAXE);

    public static final BlockEntry<VoiceboxExtensionBlock> VOICEBOX_EXTENSION = registerExtensionBlock(
            "voicebox_extension",
            VoiceboxExtensionBlock::new,
            ModBlocks.TUBULAR_FLESH_BLOCK::get,
            BlockTags.MINEABLE_WITH_PICKAXE);


    private static <T extends GenericExtensionBlock<?>> BlockEntry<T> registerExtensionBlock(
            String name, NonNullFunction<BlockBehaviour.Properties, T> factory,
            NonNullSupplier<? extends Block> initialPropertiesCopier, TagKey<Block> toolTag) {
        return REGISTRATE.block(name, factory)
                .initialProperties(initialPropertiesCopier)
                .blockstate(new BlockStateGen.PipeExtensionGenerator()::generate)
                .tag(toolTag)
                .register();
    }


    private static <T extends GenericPipeBlock> BlockEntry<T> registerPipeBlock(
            String name,
            NonNullFunction<BlockBehaviour.Properties, T> factory,
            NonNullSupplier<? extends Block> initialPropertiesCopier,
            GenericPipeBlockItem.StopSize stopsize,
            TagKey<Block> toolTag) {
        return REGISTRATE.block(name, factory)
                .initialProperties(initialPropertiesCopier)
                .tag(AllTags.AllBlockTags.VALID_WHISTLE.tag)
                .blockstate(new BlockStateGen.PipeGenerator()::generate)
                .item((b,p) -> new GenericPipeBlockItem(b, p, stopsize))
                .transform(customItemModel())
                .tag(toolTag)
                .register();
    }

    public static void register() {
    }
}
