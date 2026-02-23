package xyz.anonym.sound_of_flesh.content;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.anonym.sound_of_flesh.Sound_of_flesh;

public class AllTheBlocks {

    //DefferedRegistera
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Sound_of_flesh.MODID);
    public static final RegistryObject<Block> LUNG_BLOCK = BLOCKS.register("lung_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
}
