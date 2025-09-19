package com.coolerpromc.productiveslimes.block.entity.renderstate;

import com.coolerpromc.productiveslimes.block.entity.DnaExtractorBlockEntity;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class DnaExtractorBlockEntityRenderState extends BlockEntityRenderState {
    public float rotation;
    public DnaExtractorBlockEntity blockEntity;
    public ItemStackRenderState itemStackRenderState;

    public float getRenderingRotation() {
        rotation += 1f;
        if(rotation >= 360) {
            rotation = 0;
        }
        return rotation;
    }
}
