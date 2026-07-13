package com.cognitio.core.client;


import com.cognitio.core.CognitioCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(modid = CognitioCore.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
public class InsightOverlay {

    private static final ResourceLocation EYE_TEXTURE = ResourceLocation.fromNamespaceAndPath(CognitioCore.MODID, "textures/gui/eye.png");

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Pre event) {
        if (!com.cognitio.core.config.CognitioClientConfig.SHOW_HUD.get()) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        int insight = ClientInsightCache.getPoints();

        GuiGraphics graphics = event.getGuiGraphics();
        
        // Posição ajustada pela configuração do jogador
        int iconX = (mc.getWindow().getGuiScaledWidth() / 2) - 8 + com.cognitio.core.config.CognitioClientConfig.HUD_OFFSET_X.get();
        int iconY = 5 + com.cognitio.core.config.CognitioClientConfig.HUD_OFFSET_Y.get();

        String text = String.valueOf(insight);
        
        // Posição do texto à direita do ícone
        int textX = iconX + 16 + 5;
        int textY = iconY + 4;

        graphics.drawString(mc.font, text, textX, textY, 0xFFFFFF);
        graphics.blit(EYE_TEXTURE, iconX, iconY, 0, 0, 16, 16, 16, 16);
    }
}

