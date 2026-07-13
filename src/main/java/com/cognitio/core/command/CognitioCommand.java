package com.cognitio.core.command;

import com.cognitio.api.perception.InsightSource;
import com.cognitio.core.CognitioCore;
import com.cognitio.core.perception.PerceptionEngine;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = CognitioCore.MODID, bus = EventBusSubscriber.Bus.GAME)
public class CognitioCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("cognitio")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("insight")
                        .then(Commands.literal("get")
                                .then(Commands.argument("target", EntityArgument.player())
                                        .executes(context -> getInsight(context.getSource(), EntityArgument.getPlayer(context, "target")))
                                )
                        )
                        .then(Commands.literal("add")
                                .then(Commands.argument("target", EntityArgument.player())
                                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                                                .executes(context -> addInsight(context.getSource(), EntityArgument.getPlayer(context, "target"), IntegerArgumentType.getInteger(context, "amount")))
                                        )
                                )
                        )
                        .then(Commands.literal("set")
                                .then(Commands.argument("target", EntityArgument.player())
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                                .executes(context -> setInsight(context.getSource(), EntityArgument.getPlayer(context, "target"), IntegerArgumentType.getInteger(context, "amount")))
                                        )
                                )
                        )
                )
        );
    }

    private static int getInsight(CommandSourceStack source, ServerPlayer target) {
        int insight = PerceptionEngine.getEffectivePerception(target);
        source.sendSuccess(() -> Component.literal(target.getDisplayName().getString() + " possui " + insight + " de Insight."), false);
        return insight;
    }

    private static int addInsight(CommandSourceStack source, ServerPlayer target, int amount) {
        if (amount > 0) {
            PerceptionEngine.addInsight(target, amount, InsightSource.COMMAND);
        } else if (amount < 0) {
            // Se for negativo, ajustamos através de setInsight para subtrair, pois addInsight barra < 0 (por causa do evento)
            int current = target.getData(com.cognitio.core.attachment.AttachmentRegister.COGNITIO_INSIGHT.get()).points();
            PerceptionEngine.setInsight(target, Math.max(0, current + amount));
        }
        
        int newInsight = PerceptionEngine.getEffectivePerception(target);
        source.sendSuccess(() -> Component.literal("Insight de " + target.getDisplayName().getString() + " modificado. Novo valor: " + newInsight), true);
        return newInsight;
    }

    private static int setInsight(CommandSourceStack source, ServerPlayer target, int amount) {
        PerceptionEngine.setInsight(target, amount);
        int newInsight = PerceptionEngine.getEffectivePerception(target);
        source.sendSuccess(() -> Component.literal("Insight de " + target.getDisplayName().getString() + " definido para " + newInsight + "."), true);
        return newInsight;
    }
}
