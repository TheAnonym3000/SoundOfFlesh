package xyz.anonym.sound_of_flesh.content.pipes.voicebox;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import static xyz.anonym.sound_of_flesh.content.pipes.voicebox.VoiceboxBlock.VoiceboxSize.*;
import static xyz.anonym.sound_of_flesh.init.AllSoundEvents.*;

public class VoiceboxSoundInstance extends AbstractTickableSoundInstance {

    private boolean active;
    private int keepAlive;
    private final VoiceboxBlock.VoiceboxSize size;

    public VoiceboxSoundInstance(VoiceboxBlock.VoiceboxSize size, BlockPos worldPosition) {
        super((
                  size == MICRO ? VOICEBOX_SUPERDUPERHIGH
                : size == TINY ? VOICEBOX_SUPERHIGH
                : size == SMALL ? VOICEBOX_HIGH
                : size == MEDIUM ? VOICEBOX_MEDIUM
                : size == LARGE ? VOICEBOX_LOW
                : size == HUGE ? VOICEBOX_DEEP
                : VOICEBOX_SUPERDEEP)
                .get(), SoundSource.RECORDS, SoundInstance.createUnseededRandom());

        this.size = size;
        looping = true;
        active = true;
        volume = 0.05f;
        delay = 0;
        keepAlive();
        Vec3 v = Vec3.atCenterOf(worldPosition);
        x = v.x;
        y = v.y;
        z = v.z;
    }

    public VoiceboxBlock.VoiceboxSize getOctave() {
        return size;
    }

    public void fadeOut() {
        this.active = false;
    }

    public void keepAlive() {
        keepAlive = 2;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    @Override
    public void tick() {
        if (active) {
            volume = Math.min(1, volume + .25f);
            keepAlive--;
            if (keepAlive == 0)
                fadeOut();
            return;

        }
        volume = Math.max(0, volume - .25f);
        if (volume == 0)
            stop();
    }
    @Override
    public @NotNull Attenuation getAttenuation() {
        return Attenuation.LINEAR;
    }


}