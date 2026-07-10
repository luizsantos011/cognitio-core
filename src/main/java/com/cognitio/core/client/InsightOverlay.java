package com.cognitio.core.client;

import com.cognitio.attachment.AttachmentRegister;
import com.cognitio.attachment.InsightData;
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

        InsightData data = player.getData(AttachmentRegister.COGNITIO_INSIGHT.get());
        int insight = data.points();

        GuiGraphics graphics = event.getGuiGraphics();
        
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        
        int x = screenWidth - 20;
        int y = 10;

        // Draw the eye icon
        graphics.blit(EYE_TEXTURE, x, y, 0, 0, 16, 16, 16, 16);

        // Draw the insight number to the left of the eye
        String text = String.valueOf(insight);
        int textWidth = mc.font.width(text);
        graphics.drawString(mc.font, text, x - textWidth - 5, y + 4, 0xFFFFFF);
    }
}
