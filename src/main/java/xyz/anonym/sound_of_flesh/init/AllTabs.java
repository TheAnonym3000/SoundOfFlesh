package xyz.anonym.sound_of_flesh.content;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import xyz.anonym.sound_of_flesh.Sound_of_flesh;

public class AllTheTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Sound_of_flesh.MODID);
    // Creates a creative tab with the id "sound_of_flesh:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register("main_tab", () -> CreativeModeTab.builder().withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> AllTheItems.LUNG_BLOCK_ITEM.get().getDefaultInstance()).displayItems((parameters, output) -> {
         output.accept(AllTheItems.LUNG_BLOCK_ITEM.get());
    }).build());
    // Add the example block item to the building blocks tab
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
    }
}
