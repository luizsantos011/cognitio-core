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
    public static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        int insight = ClientInsightCache.getPoints();

        GuiGraphics graphics = event.getGuiGraphics();
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        String text = String.valueOf(insight);
        int textWidth = mc.font.width(text);
        
        int textX = screenWidth - textWidth - 10;
        int textY = 10 + 4;
        graphics.drawString(mc.font, text, textX, textY, 0xFFFFFF);

        int iconX = textX - 16 - 5;
        int iconY = 10;
        graphics.blit(EYE_TEXTURE, iconX, iconY, 0, 0, 16, 16, 16, 16);
    }
}

