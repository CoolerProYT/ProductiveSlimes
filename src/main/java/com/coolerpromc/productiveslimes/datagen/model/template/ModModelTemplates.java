package com.coolerpromc.productiveslimes.datagen.model.template;

import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;

public class ModModelTemplates {
    public static final ModelTemplate TRAPDOOR_TOP = ModelTemplates.TRAPDOOR_TOP.extend().renderType("cutout").build();
    public static final ModelTemplate TRAPDOOR_BOTTOM = ModelTemplates.TRAPDOOR_BOTTOM.extend().renderType("cutout").build();
    public static final ModelTemplate TRAPDOOR_OPEN = ModelTemplates.TRAPDOOR_OPEN.extend().renderType("cutout").build();

    public static final ModelTemplate DOOR_TOP_LEFT = ModelTemplates.DOOR_TOP_LEFT.extend().renderType("cutout").build();
    public static final ModelTemplate DOOR_TOP_LEFT_OPEN = ModelTemplates.DOOR_TOP_LEFT_OPEN.extend().renderType("cutout").build();
    public static final ModelTemplate DOOR_TOP_RIGHT = ModelTemplates.DOOR_TOP_RIGHT.extend().renderType("cutout").build();
    public static final ModelTemplate DOOR_TOP_RIGHT_OPEN = ModelTemplates.DOOR_TOP_RIGHT_OPEN.extend().renderType("cutout").build();
}
