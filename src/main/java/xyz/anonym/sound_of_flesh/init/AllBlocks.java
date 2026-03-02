package xyz.anonym.sound_of_flesh.init;

import com.github.elenterius.biomancy.init.ModBlocks;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.material.MapColor;
import xyz.anonym.sound_of_flesh.SoundOfFlesh;
import xyz.anonym.sound_of_flesh.content.generic.lung.LungBlock;
import xyz.anonym.sound_of_flesh.content.generic.trachea.TracheaBlock;
import xyz.anonym.sound_of_flesh.content.pipes.voicebox.VoiceboxBlock;
import xyz.anonym.sound_of_flesh.content.pipes.voicebox.VoiceboxExtensionBlock;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class AllBlocks {
    private static final CreateRegistrate REGISTRATE = SoundOfFlesh.registrate();

    static {
        REGISTRATE.setCreativeTab(AllTabs.MAIN_TAB);
    }


    public static final BlockEntry<TracheaBlock> TRACHEA = REGISTRATE.block("trachea", TracheaBlock::new)
            .initialProperties(ModBlocks.TUBULAR_FLESH_BLOCK::get)
            .properties(p -> p
                    .requiresCorrectToolForDrops()
                    .noOcclusion())
            .lang("Trachea")
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<LungBlock> LUNG_BLOCK = REGISTRATE.block("lung_block", LungBlock::new)
            .initialProperties(ModBlocks.TUBULAR_FLESH_BLOCK::get)
            .properties(p -> p
                    .requiresCorrectToolForDrops()
                    .noOcclusion())
            .lang("Lung Block")
            .item()
            .transform(customItemModel())
            .register();


    // Main whistle
    public static final BlockEntry<VoiceboxBlock> VOICEBOX =
            REGISTRATE.block("voicebox", VoiceboxBlock::new)
                    .initialProperties(SharedProperties::copperMetal)
                    .properties(p -> p.mapColor(MapColor.GOLD))
                    .transform(pickaxeOnly())
                    //.blockstate(new BlockStateGen.VoiceboxGenerator()::generate)
                    .item()
                    .transform(customItemModel())
                    .register();

    // Extension block
    public static final BlockEntry<VoiceboxExtensionBlock> VOICEBOX_EXTENSION =
            REGISTRATE.block("voicebox_extension", VoiceboxExtensionBlock::new)
                    .initialProperties(SharedProperties::copperMetal)
                    .properties(p -> p.mapColor(MapColor.GOLD)
                            .forceSolidOn())
                    .transform(pickaxeOnly())
                    //.blockstate(BlockStateGen.whistleExtender()::generate)
                    .register();


    public static void register() {
    }

    public static <I extends BlockItem, P> NonNullFunction<ItemBuilder<I, P>, P>  ex() {
        return b -> b.model(com.simibubi.create.foundation.data.AssetLookup::customItemModel)
                .build();
    }
}
