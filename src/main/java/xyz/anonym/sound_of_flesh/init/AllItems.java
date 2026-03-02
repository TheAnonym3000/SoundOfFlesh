package xyz.anonym.sound_of_flesh.init;

import com.finchy.pipeorgans.PipeOrgans;
import com.github.elenterius.biomancy.init.ModBlocks;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.anonym.sound_of_flesh.SoundOfFlesh;
import xyz.anonym.sound_of_flesh.content.generic.lung.LungBlock;

public class AllItems {
    private static final CreateRegistrate REGISTRATE = SoundOfFlesh.registrate();

    static {
        REGISTRATE.setCreativeTab(AllTabs.MAIN_TAB);
    }

    public static void register() {}
}
