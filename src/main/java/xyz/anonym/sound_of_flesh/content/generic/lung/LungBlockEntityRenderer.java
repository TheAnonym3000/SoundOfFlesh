package xyz.anonym.sound_of_flesh.content.generic.lung;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import xyz.anonym.sound_of_flesh.content.generic.lung.LungBlockEntity;

public class LungBlockEntityRenderer extends GeoBlockRenderer<LungBlockEntity> {
    public LungBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new LungBlockModel());
    }
}