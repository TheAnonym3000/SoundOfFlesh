package xyz.anonym.sound_of_flesh.content.generic.bronchi;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class BronchiBlockEntityRenderer extends GeoBlockRenderer<BronchiBlockEntity> {
    public BronchiBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new BronchiModel());
    }
}