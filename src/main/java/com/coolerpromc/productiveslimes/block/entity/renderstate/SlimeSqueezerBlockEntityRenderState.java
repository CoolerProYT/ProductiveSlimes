package com.coolerpromc.productiveslimes.block.entity.renderstate;

import com.coolerpromc.productiveslimes.block.entity.SlimeSqueezerBlockEntity;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

import java.util.ArrayList;
import java.util.List;

public class SlimeSqueezerBlockEntityRenderState extends BlockEntityRenderState {
    public SlimeSqueezerBlockEntity blockEntity;
    public List<ItemStackRenderState> itemStackRenderStates = new ArrayList<>();
}
