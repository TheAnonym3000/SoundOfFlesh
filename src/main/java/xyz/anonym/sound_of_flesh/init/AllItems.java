package xyz.anonym.sound_of_flesh.content;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.anonym.sound_of_flesh.Sound_of_flesh;

public class AllTheItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Sound_of_flesh.MODID);
    public static final RegistryObject<Item> LUNG_BLOCK_ITEM = ITEMS.register("lung_block", () -> new BlockItem(AllTheBlocks.LUNG_BLOCK.get(), new Item.Properties()));
}
