package com.coolerpromc.productiveslimes.util;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;

import java.util.OptionalDouble;

public class ModRenderTypes extends RenderStateShard {
    public static final RenderType LINES_NONTRANSLUCENT = createDefault(
            ProductiveSlimes.MODID + ":nontranslucent_lines", DefaultVertexFormat.POSITION_COLOR_NORMAL, VertexFormat.Mode.LINES,
            RenderType.CompositeState.builder()
                    .setLineState(new LineStateShard(OptionalDouble.of(1)))
                    .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                    .setOutputState(ITEM_ENTITY_TARGET)
                    .createCompositeState(false)
    );

    public ModRenderTypes(String pName, Runnable pSetupState, Runnable pClearState) {
        super(pName, pSetupState, pClearState);
    }

    private static RenderType createDefault(String name, VertexFormat format, VertexFormat.Mode mode, RenderType.CompositeState state) {
        return RenderType.create(name, 256, false, false, RenderPipeline.builder().build(), state);
    }
}