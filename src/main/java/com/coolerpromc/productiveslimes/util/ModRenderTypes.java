package com.coolerpromc.productiveslimes.util;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import org.lwjgl.opengl.GL11;

import java.util.OptionalDouble;

public class ModRenderTypes extends RenderState {
    public static final RenderType LINES_NONTRANSLUCENT = createDefault(
            ProductiveSlimes.MODID+":nontranslucent_lines", DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL,
            RenderType.State.builder()
                    .setLineState(new LineState(OptionalDouble.of(1)))
                    .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                    .setTransparencyState(NO_TRANSPARENCY)
                    .setOutputState(ITEM_ENTITY_TARGET)
                    .setWriteMaskState(COLOR_DEPTH_WRITE)
                    .setCullState(NO_CULL)
                    .createCompositeState(false)
    );

    public ModRenderTypes(String pName, Runnable pSetupState, Runnable pClearState) {
        super(pName, pSetupState, pClearState);
    }

    private static RenderType createDefault(String name, VertexFormat format, RenderType.State state) {
        return RenderType.create(name, format, 256, GL11.GL_LINE, state);
    }
}
