package com.cognitio.core.client;

import com.cognitio.core.CognitioCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(modid = CognitioCore.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
public class FrenzyOverlay {

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        float frenzy = ClientFrenzyCache.getFrenzy();
        if (frenzy <= 0) return;

        GuiGraphics graphics = event.getGuiGraphics();
        
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();
        
        // Desenha acima da barra de fôlego/armadura (seguro contra conflitos visuais)
        int barWidth = 100;
        int barHeight = 5;
        int x = (screenWidth - barWidth) / 2;
        int y = screenHeight - 60; 

        // Borda preta
        graphics.fill(x - 1, y - 1, x + barWidth + 1, y + barHeight + 1, 0xFF000000);
        // Fundo cinza escuro
        graphics.fill(x, y, x + barWidth, y + barHeight, 0xFF555555);
        
        // Barra preenchida (Roxa Escura Ocultista)
        int fillWidth = (int) ((frenzy / 100f) * barWidth);
        graphics.fill(x, y, x + fillWidth, y + barHeight, 0xFFA020F0);
        
        // O aviso em texto foi removido a pedido do usuário (mantemos só a barra visual).
    }
}
