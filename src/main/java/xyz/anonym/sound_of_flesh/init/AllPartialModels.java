package xyz.anonym.sound_of_flesh.init;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import xyz.anonym.sound_of_flesh.SoundOfFlesh;
import xyz.anonym.sound_of_flesh.content.pipes.voicebox.VoiceboxExtensionBlock;

public class AllPartialModels {
    public static final PartialModel
    VOICEBOX_MOUTH_MICRO = block("voicebox/voicebox_micro_mouth"),
    VOICEBOX_MOUTH_TINY = block("voicebox/voicebox_tiny_mouth"),
    VOICEBOX_MOUTH_SMALL = block("voicebox/voicebox_small_mouth"),
    VOICEBOX_MOUTH_MEDIUM = block("voicebox/voicebox_medium_mouth"),
    VOICEBOX_MOUTH_LARGE = block("voicebox/voicebox_large_mouth"),
    VOICEBOX_MOUTH_HUGE = block("voicebox/voicebox_huge_mouth"),
    VOICEBOX_MOUTH_GIANT = block("voicebox/voicebox_giant_mouth");

    private static PartialModel block(String path) {
        return PartialModel.of(SoundOfFlesh.asResource("block/" + path));
    }

    public static void init() {}
}
