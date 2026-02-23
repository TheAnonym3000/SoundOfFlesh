package xyz.anonym.sound_of_flesh.init;

import com.simibubi.create.foundation.data.CreateRegistrate;
import xyz.anonym.sound_of_flesh.SoundOfFlesh;

public class AllItems {
    private static final CreateRegistrate REGISTRATE = SoundOfFlesh.registrate();

    static {
        REGISTRATE.setCreativeTab(AllTabs.MAIN_TAB);
    }
}
