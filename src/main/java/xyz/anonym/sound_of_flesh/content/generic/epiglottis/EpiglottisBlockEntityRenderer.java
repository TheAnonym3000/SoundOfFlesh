package xyz.anonym.sound_of_flesh.content.generic.epiglottis;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class EpiglottisBlockEntityRenderer extends GeoBlockRenderer<EpiglottisBlockEntity> {
    public EpiglottisBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new EpiglottisModel());
    }
}