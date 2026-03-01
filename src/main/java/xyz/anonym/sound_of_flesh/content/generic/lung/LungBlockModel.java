package xyz.anonym.sound_of_flesh.content.generic.lung;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import xyz.anonym.sound_of_flesh.SoundOfFlesh;

public class LungBlockModel extends DefaultedBlockGeoModel<LungBlockEntity> {
    public LungBlockModel() {
        super(new ResourceLocation(SoundOfFlesh.MODID, "lung_block"));
    }

    @Override
    public RenderType getRenderType(LungBlockEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}