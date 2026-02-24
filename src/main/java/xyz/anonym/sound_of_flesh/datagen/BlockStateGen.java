package xyz.anonym.sound_of_flesh.datagen;

import xyz.anonym.sound_of_flesh.content.pipes.voicebox.VoiceboxBlock;
import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.client.model.generators.ModelFile;

import static xyz.anonym.sound_of_flesh.content.pipes.voicebox.VoiceboxBlock.SIZE;
import static xyz.anonym.sound_of_flesh.content.pipes.voicebox.VoiceboxExtensionBlock.SHAPE;

public class BlockStateGen {

    public static class VoiceboxGenerator extends SpecialBlockStateGen {

        @Override
        protected int getXRotation(BlockState state) { return 0; }

        @Override
        protected int getYRotation(BlockState state) {
            return horizontalAngle(state.getValue(VoiceboxBlock.FACING));
        }

        @Override
        public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx,
                                                    RegistrateBlockstateProvider prov,
                                                    BlockState state) {
            String size = state.getValue(SIZE).getSerializedName();
            String placement = state.getValue(VoiceboxBlock.WALL) ? "wall" : "floor";

            ModelFile model = AssetLookup.partialBaseModel(ctx, prov, size, placement);

            if (state.getValue(VoiceboxBlock.POWERED)) {
                return prov.models()
                        .withExistingParent(size + "_" + placement + "_powered", model.getLocation())
                        .texture("0", "expanded_steam_whistles:block/copper_redstone_plate_powered");
            }

            return model;
        }
    }



    public static SpecialBlockStateGen whistleExtender() {
        return new SpecialBlockStateGen() {
            @Override
            protected int getXRotation(BlockState state) {
                return 0;
            }

            @Override
            protected int getYRotation(BlockState state) {
                // Extension inherits FACING from the base block
                return state.hasProperty(VoiceboxBlock.FACING)
                        ? horizontalAngle(state.getValue(VoiceboxBlock.FACING))
                        : 0;
            }

            @Override
            public <T extends Block> ModelFile getModel(
                    DataGenContext<Block, T> ctx,
                    RegistrateBlockstateProvider prov,
                    BlockState state
            ) {
                String size = state.getValue(SIZE).getSerializedName();
                String shape = state.getValue(SHAPE).getSerializedName().toLowerCase(); // "single", "double", "double_connected"
                return AssetLookup.partialExtensionModel(ctx, prov, size, shape);
            }
        };
    }
}