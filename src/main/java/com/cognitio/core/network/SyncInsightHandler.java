package com.cognitio.core.network;

import com.cognitio.attachment.AttachmentRegister;
import com.cognitio.attachment.InsightData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SyncInsightHandler {
    public static void handle(SyncInsightPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                InsightData oldData = player.getData(AttachmentRegister.COGNITIO_INSIGHT.get());
                InsightData newData = new InsightData(payload.insightPoints(), oldData.multipliers(), oldData.bonuses());
                player.setData(AttachmentRegister.COGNITIO_INSIGHT.get(), newData);

                // Recria a instância do item da mão no cliente para forçar o recalculamento imediato do modelo wrapper
                ItemStack mainHand = player.getMainHandItem();
                if (!mainHand.isEmpty()) {
                    player.getInventory().setItem(player.getInventory().selected, mainHand.copy());
                }
                ItemStack offHand = player.getOffhandItem();
                if (!offHand.isEmpty()) {
                    player.getInventory().setItem(45, offHand.copy());
                }
            }
        });
    }
}