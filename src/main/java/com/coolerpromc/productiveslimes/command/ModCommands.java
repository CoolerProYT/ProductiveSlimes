package com.coolerpromc.productiveslimes.command;

import com.coolerpromc.productiveslimes.handler.SlimeData;
import com.coolerpromc.productiveslimes.item.ModItems;
import com.coolerpromc.productiveslimes.tier.ModTierLists;
import com.coolerpromc.productiveslimes.tier.ModTiers;
import com.coolerpromc.productiveslimes.tier.Tier;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import java.util.ArrayList;
import java.util.List;
public class ModCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("productiveslimes").requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                .then(
                        Commands.literal("give").then(
                                Commands.argument("slime_id", StringArgumentType.string()).suggests((context, builder) -> {
                                                    List<String> ids = new ArrayList<>();
                                                    for (Tier tier : Tier.values()) {
                                                        ids.add(tier.getTierName());
                                                    }
                                                    return SharedSuggestionProvider.suggest(ids, builder);
                                                }
                                        )
                                        .then(
                                                Commands.argument("size", IntegerArgumentType.integer(1, 4)).suggests((context, builder) -> {
                                                            List<String> sizes = new ArrayList<>();
                                                            sizes.add("1");
                                                            sizes.add("2");
                                                            sizes.add("3");
                                                            sizes.add("4");
                                                            return SharedSuggestionProvider.suggest(sizes, builder);
                                                        })
                                                        .executes(ModCommands::execute)
                                        )
                        )
                )
        );
    }
    public static int execute(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        // Ensure the command is executed by a player
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            source.sendFailure(Component.literal("This command can only be used by a player."));
            return 0;
        }
        // Get arguments
        String slimeId = StringArgumentType.getString(context, "slime_id");
        int size = IntegerArgumentType.getInteger(context, "size");
        SlimeData data = createSlimeData(slimeId, size, source);
        if (data == null) {
            return 0;
        }
        // Create the item with custom NBT
        ItemStack slimeItem = new ItemStack(ModItems.SLIME_ITEM.get()); // Replace with your mod's item
        CompoundTag tag = new CompoundTag();
        tag.put("slime_data", data.toTag(new CompoundTag()));
        slimeItem.setTag(tag);
        // Give the item to the player
        if (player.addItem(slimeItem)) {
            source.sendSuccess(Component.literal("Gave custom slime item!"), true);
            return Command.SINGLE_SUCCESS;
        } else {
            source.sendFailure(Component.literal("Failed to give item. Is your inventory full?"));
            return 0;
        }
    }
    private static SlimeData createSlimeData(String slimeId, int size, CommandSourceStack source) {
        try {
            ModTiers tier = ModTierLists.getTierByName(Tier.valueOf(slimeId.toUpperCase()));
            return new SlimeData(size, tier.color(), tier.cooldown(), new ItemStack(ModTierLists.getSlimeballItemByName(slimeId).get()), new ItemStack(ModTierLists.getItemByKey(tier.growthItemKey())), ModTierLists.getEntityByName(slimeId).get());
        } catch (IllegalArgumentException e) {
            source.sendFailure(Component.literal("Invalid slime ID. To get an id, pres f3+h and point on target slime in JEI/Inventory, for example, for productiveslimes:dirt_slime, the id in command is dirt"));
            return null;
        }
    }
}