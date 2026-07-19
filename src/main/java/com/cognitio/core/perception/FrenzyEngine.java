package com.cognitio.core.perception;

import com.cognitio.core.CognitioCore;
import com.cognitio.core.attachment.AttachmentRegister;
import com.cognitio.core.attachment.FrenzyData;
import com.cognitio.core.network.SyncFrenzyPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = CognitioCore.MODID, bus = EventBusSubscriber.Bus.GAME)
public class FrenzyEngine {

    /**
     * Adiciona Sobrecarga Mental (Frenesi).
     * @param player Jogador alvo.
     * @param amount Quantidade base por tick ou evento.
     */
    public static void addFrenzy(Player player, float amount) {
        if (player.level().isClientSide() || !(player instanceof ServerPlayer serverPlayer)) return;

        int insight = PerceptionEngine.getEffectivePerception(player);
        if (insight <= 0) return; // Imune (0 Insight)

        // Paradoxo da Sabedoria: 
        // Em 100 de insight, o multiplicador é 1x.
        // Em 500 de insight, o multiplicador é 3x (enche muito mais rápido).
        float multiplier = Math.max(0.1f, 1.0f + ((insight - 100) / 200.0f));
        
        float finalAmount = amount * multiplier;
        
        FrenzyData oldData = player.getData(AttachmentRegister.COGNITIO_FRENZY.get());
        float newFrenzy = Math.min(100f, oldData.amount() + finalAmount);
        
        if (oldData.amount() < 100f && newFrenzy >= 100f) {
            serverPlayer.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                com.cognitio.core.registry.ModEffects.FRENZY,
                300, // 15 segundos
                0, false, true, true
            ));
            newFrenzy = 0f; // Estouro de sanidade (zera a barra)
        }

        setFrenzy(serverPlayer, newFrenzy, true);
    }

    public static void setFrenzy(ServerPlayer player, float amount, boolean forceSync) {
        float clamped = Math.max(0f, Math.min(100f, amount));
        player.setData(AttachmentRegister.COGNITIO_FRENZY.get(), new FrenzyData(clamped));
        if (forceSync) {
            PacketDistributor.sendToPlayer(player, new SyncFrenzyPayload(clamped));
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            FrenzyData data = player.getData(AttachmentRegister.COGNITIO_FRENZY.get());
            float frenzy = data.amount();

            if (frenzy > 0) {
                // Decaimento Natural: -0.05 por tick (1 ponto por segundo)
                float newFrenzy = Math.max(0f, frenzy - 0.05f);
                


                if (newFrenzy != frenzy) {
                    player.setData(AttachmentRegister.COGNITIO_FRENZY.get(), new FrenzyData(newFrenzy));
                    // Sincroniza apenas a cada 10 ticks para poupar rede, ou se zerar
                    if (player.tickCount % 10 == 0 || newFrenzy == 0) {
                        PacketDistributor.sendToPlayer(player, new SyncFrenzyPayload(newFrenzy));
                    }
                }
            }
        }
    }
}
