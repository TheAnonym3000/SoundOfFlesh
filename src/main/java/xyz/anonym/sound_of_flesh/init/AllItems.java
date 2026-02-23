package xyz.anonym.sound_of_flesh.init;

import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.anonym.sound_of_flesh.Sound_of_flesh;

public class AllItems {
    private static final CreateRegistrate REGISTRATE = Sound_of_flesh.registrate();

    static {
        REGISTRATE.setCreativeTab(AllTabs.MAIN_TAB);
    }
}
