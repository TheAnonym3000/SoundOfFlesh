package xyz.anonym.sound_of_flesh.init;

import com.finchy.pipeorgans.content.windchest.WindchestMasterBlock;
import com.github.elenterius.biomancy.init.ModBlocks;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.material.MapColor;
import xyz.anonym.sound_of_flesh.SoundOfFlesh;
import xyz.anonym.sound_of_flesh.content.generic.lung.LungBlock;
import xyz.anonym.sound_of_flesh.content.pipes.voicebox.VoiceboxBlock;
import xyz.anonym.sound_of_flesh.datagen.AssetLookup;
import xyz.anonym.sound_of_flesh.datagen.BlockStateGen;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

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


    // Main whistle
    public static final BlockEntry<VoiceboxBlock> VOICEBOX =
            REGISTRATE.block("voicebox", VoiceboxBlock::new)
                    .initialProperties(SharedProperties::copperMetal)
                    .properties(p -> p.mapColor(MapColor.GOLD))
                    .transform(pickaxeOnly())
                    .blockstate(new BlockStateGen.VoiceboxGenerator()::generate)
                    .item()
                    .transform(customItemModel())
                    .register();

    // Extension block
    public static final BlockEntry<VoiceboxBlock> VOICEBOX_EXTENSION =
            REGISTRATE.block("voicebox_extension", VoiceboxBlock::new)
                    .initialProperties(SharedProperties::copperMetal)
                    .properties(p -> p.mapColor(MapColor.GOLD)
                            .forceSolidOn())
                    .transform(pickaxeOnly())
                    .blockstate(BlockStateGen.whistleExtender()::generate) // << important fix
                    .register();


    public static void register() {
    }
}
