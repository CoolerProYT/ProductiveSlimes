package com.coolerpromc.productiveslimes.event;

import com.coolerpromc.productiveslimes.ProductiveSlimes;
import com.coolerpromc.productiveslimes.block.entity.ModBlockEntities;
import net.minecraft.core.Direction;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = ProductiveSlimes.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModCapabilities {
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,
                ModBlockEntities.MELTING_STATION_BE.get(),
                (be, side) -> be.getEnergyHandler());

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.MELTING_STATION_BE.get(),
                (be, side) -> {
                    if (side == Direction.DOWN) {
                        return be.getOutputHandler();
                    }
                    else if (side == Direction.UP) {
                        return be.getBucketHandler();
                    }
                    else {
                        return be.getInputHandler();
                    }
                });

        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,
                ModBlockEntities.SOLIDING_STATION_BE.get(),
                (be, side) -> be.getEnergyHandler());

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.SOLIDING_STATION_BE.get(),
                (be, side) -> {
                    if (side == Direction.DOWN) {
                        return be.getOutputHandler();
                    }
                    else {
                        return be.getInputHandler();
                    }
                });

        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,
                ModBlockEntities.ENERGY_GENERATOR_BE.get(),
                (be, side) -> be.getEnergyHandler());

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.ENERGY_GENERATOR_BE.get(),
                (be, side) -> {
                    return be.getItemHandler();
                });

        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,
                ModBlockEntities.CABLE_BE.get(),
                (be, side) -> be);

        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,
                ModBlockEntities.DNA_EXTRACTOR_BE.get(),
                (be, side) -> be.getEnergyHandler());

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.DNA_EXTRACTOR_BE.get(),
                (be, side) -> {
                    if (side == Direction.DOWN) {
                        return be.getOutputHandler();
                    }
                    else {
                        return be.getInputHandler();
                    }
                });

        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,
                ModBlockEntities.DNA_SYNTHESIZER_BE.get(),
                (be, side) -> be.getEnergyHandler());

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.DNA_EXTRACTOR_BE.get(),
                (be, side) -> {
                    if (side == Direction.DOWN) {
                        return be.getOutputHandler();
                    }
                    else {
                        return be.getInputHandler();
                    }
                });
    }
}