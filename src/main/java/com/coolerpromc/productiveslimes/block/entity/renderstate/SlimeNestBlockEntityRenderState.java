package com.coolerpromc.productiveslimes.block.entity.renderstate;

import com.coolerpromc.productiveslimes.block.entity.SlimeNestBlockEntity;
import com.coolerpromc.productiveslimes.block.entity.SlimeballCollectorBlockEntity;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class SlimeNestBlockEntityRenderState extends BlockEntityRenderState {
    public SlimeNestBlockEntity blockEntity;
    public ItemStackRenderState itemStackRenderState;
    public int slimeColor;
}
