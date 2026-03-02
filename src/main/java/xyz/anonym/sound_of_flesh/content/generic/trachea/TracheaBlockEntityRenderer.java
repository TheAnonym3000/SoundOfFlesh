package xyz.anonym.sound_of_flesh.content.generic.trachea;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class TracheaBlockEntityRenderer extends GeoBlockRenderer<TracheaBlockEntity> {
    public TracheaBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new TracheaBlockModel());
    }
}