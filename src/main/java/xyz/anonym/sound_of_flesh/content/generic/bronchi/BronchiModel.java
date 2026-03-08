package xyz.anonym.sound_of_flesh.content.generic.bronchi;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import xyz.anonym.sound_of_flesh.SoundOfFlesh;

public class BronchiModel extends DefaultedBlockGeoModel<BronchiBlockEntity> {
    public BronchiModel() {
        super(new ResourceLocation(SoundOfFlesh.MODID, "bronchi"));
    }

    @Override
    public RenderType getRenderType(BronchiBlockEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}