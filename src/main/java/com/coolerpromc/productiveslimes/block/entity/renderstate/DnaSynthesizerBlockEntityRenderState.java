package com.coolerpromc.productiveslimes.block.entity.renderstate;

import com.coolerpromc.productiveslimes.block.entity.DnaSynthesizerBlockEntity;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

import java.util.ArrayList;
import java.util.List;

public class DnaSynthesizerBlockEntityRenderState extends BlockEntityRenderState {
    public DnaSynthesizerBlockEntity blockEntity;
    public List<ItemStackRenderState> itemStackRenderStates = new ArrayList<>();
}
