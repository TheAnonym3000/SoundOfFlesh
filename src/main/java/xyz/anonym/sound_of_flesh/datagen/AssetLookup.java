package xyz.anonym.sound_of_flesh.datagen;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.function.Function;

/**
 * AssetLookup for SoundOfFlesh
 * Handles:
 * - Base whistle models
 * - Extension models (single, double, double_connected)
 * - Item models
 * - Powered / boolean variants
 */
public class AssetLookup {

    /**
     * Base whistle model
     * Example: block/sound_of_flesh/voicebox_small_wall
     */
    public static ModelFile partialBaseModel(DataGenContext<?, ?> ctx, RegistrateBlockstateProvider prov, String... suffix) {
        String path = "/" + ctx.getName();
        for (String suf : suffix)
            if (!suf.isEmpty())
                path += "_" + suf;
        String location = "block/" + ctx.getName() + path;
        return prov.models().getExistingFile(prov.modLoc(location));
    }

    /**
     * Extension model
     * Example: block/sound_of_flesh/extension/voicebox_tiny_single
     */
    public static ModelFile partialExtensionModel(DataGenContext<?, ?> ctx, RegistrateBlockstateProvider prov, String... suffix) {
        String baseName;
        if (ctx.getName().endsWith("_extension"))
            baseName = ctx.getName().substring(0, ctx.getName().length() - "_extension".length());
        else {
            throw new IllegalArgumentException("Block is not an extension or is incorrectly named: " + ctx.getName());
        }

        String path = "/extension/" + baseName;
        for (String suf : suffix)
            if (!suf.isEmpty())
                path += "_" + suf;
        String location = "block/" + baseName + path;
        return prov.models().getExistingFile(prov.modLoc(location));
    }

    /**
     * Standard item models
     */
    public static <T extends Item> NonNullBiConsumer<DataGenContext<Item, T>, RegistrateItemModelProvider> existingItemModel() {
        return (c, p) -> p.getExistingFile(p.modLoc("item/" + c.getName()));
    }

    /**
     * Model for powered blocks (like whistles that can be "powered")
     */
    public static Function<BlockState, ModelFile> forPowered(DataGenContext<?, ?> ctx, RegistrateBlockstateProvider prov) {
        return state -> state.getValue(BlockStateProperties.POWERED)
                ? partialBaseModel(ctx, prov, "powered")
                : partialBaseModel(ctx, prov);
    }

    /**
     * Helper for any boolean property (like wall/floor)
     */
    public static Function<BlockState, ModelFile> forBooleanProperty(BooleanProperty property, String suffix, DataGenContext<?, ?> ctx, RegistrateBlockstateProvider prov) {
        return state -> state.getValue(property)
                ? partialBaseModel(ctx, prov, suffix)
                : partialBaseModel(ctx, prov);
    }

}