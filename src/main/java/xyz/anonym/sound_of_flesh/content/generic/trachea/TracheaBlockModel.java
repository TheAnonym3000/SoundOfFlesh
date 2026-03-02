package xyz.anonym.sound_of_flesh.content.generic.trachea;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import xyz.anonym.sound_of_flesh.SoundOfFlesh;

public class TracheaBlockModel extends DefaultedBlockGeoModel<TracheaBlockEntity> {
    public TracheaBlockModel() {
        super(new ResourceLocation(SoundOfFlesh.MODID, "trachea"));
    }

    @Override
    public RenderType getRenderType(TracheaBlockEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}