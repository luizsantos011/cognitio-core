package com.cognitio.core.event;

import com.cognitio.core.perception.PerceptionEngine;
import com.cognitio.core.attachment.AttachmentRegister;
import com.cognitio.core.attachment.InsightData;
import com.cognitio.core.CognitioCore;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = CognitioCore.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ModCommands {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
                Commands.literal("cognitio")
                        .requires(source -> source.hasPermission(2)) // Requer nível de op (cheat ativo)
                        .then(Commands.literal("modifier")
                                .then(Commands.literal("add")
                                        .then(Commands.argument("id", StringArgumentType.word())
                                                .then(Commands.argument("multiplier", DoubleArgumentType.doubleArg())
                                                        .then(Commands.argument("bonus", IntegerArgumentType.integer())
                                                                .executes(context -> {
                                                                    CommandSourceStack source = context.getSource();
                                                                    Player player = source.getPlayerOrException();

                                                                    String id = StringArgumentType.getString(context, "id");
                                                                    double multiplier = DoubleArgumentType.getDouble(context, "multiplier");
                                                                    int bonus = IntegerArgumentType.getInteger(context, "bonus");

                                                                    PerceptionEngine.applyModifier(player, id, multiplier, bonus);

                                                                    InsightData data = player.getData(AttachmentRegister.COGNITIO_INSIGHT.get());
                                                                    source.sendSuccess(() -> Component.literal("Mapas atuais: " + data.bonuses()), true);

                                                                    return 1;
                                                                })
                                                        )
                                                )
                                        )
                                )
                                .then(Commands.literal("remove")
                                        .then(Commands.argument("id", StringArgumentType.word())
                                                .executes(context -> {
                                                    CommandSourceStack source = context.getSource();
                                                    Player player = source.getPlayerOrException();

                                                    String id = StringArgumentType.getString(context, "id");

                                                    PerceptionEngine.removeModifier(player, id);

                                                    InsightData data = player.getData(AttachmentRegister.COGNITIO_INSIGHT.get());
                                                    source.sendSuccess(() -> Component.literal("Mapas após remoção: " + data.bonuses()), true);

                                                    return 1;
                                                })
                                        )
                                )
                        )
        );
    }
}

