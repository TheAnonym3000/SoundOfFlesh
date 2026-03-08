package xyz.anonym.sound_of_flesh.content.generic.epiglottis;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import xyz.anonym.sound_of_flesh.SoundOfFlesh;

public class EpiglottisModel extends DefaultedBlockGeoModel<EpiglottisBlockEntity> {
    public EpiglottisModel() {
        super(new ResourceLocation(SoundOfFlesh.MODID, "epiglottis"));
    }

    @Override
    public RenderType getRenderType(EpiglottisBlockEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}