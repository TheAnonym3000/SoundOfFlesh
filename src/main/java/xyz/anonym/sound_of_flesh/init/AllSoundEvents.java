package xyz.anonym.sound_of_flesh.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.anonym.sound_of_flesh.SoundOfFlesh;

public class AllSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SoundOfFlesh.MODID);


    // declare sounds here

    public static final RegistryObject<SoundEvent>
    VOICEBOX_SUPERSUPERHIGH = registerSoundEvent("voicebox_supersuperhigh"),
    VOICEBOX_SUPERHIGH = registerSoundEvent("voicebox_superhigh"),
    VOICEBOX_HIGH = registerSoundEvent("voicebox_high"),
    VOICEBOX_MEDIUM = registerSoundEvent("voicebox_medium"),
    VOICEBOX_LOW = registerSoundEvent("voicebox_low"),
    VOICEBOX_DEEP = registerSoundEvent("voicebox_deep"),
    VOICEBOX_SUPERDEEP = registerSoundEvent("voicebox_superdeep");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(SoundOfFlesh.MODID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
