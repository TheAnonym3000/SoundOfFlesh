package xyz.anonym.sound_of_flesh.content.pipes.voicebox;

import com.finchy.pipeorgans.content.pipes.generic.EPipeSizes;
import com.finchy.pipeorgans.content.pipes.generic.GenericSoundInstance;
import net.minecraft.core.BlockPos;

import static xyz.anonym.sound_of_flesh.init.AllSoundEvents.*;

public class VoiceboxSoundInstance extends GenericSoundInstance {

    public VoiceboxSoundInstance(EPipeSizes.PipeSize size, BlockPos worldPosition) {
        super(size, worldPosition,
                (switch (size) {
                    case TINY -> VOICEBOX_SUPERHIGH;
                    case SMALL -> VOICEBOX_HIGH;
                    case MEDIUM -> VOICEBOX_MEDIUM;
                    case LARGE -> VOICEBOX_LOW;
                    case HUGE -> VOICEBOX_DEEP;
                }).get()
        );
    }
}