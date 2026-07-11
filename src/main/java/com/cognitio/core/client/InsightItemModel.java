package com.cognitio.core.client;

import com.cognitio.core.perception.PerceptionEngine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class InsightItemModel extends BakedModelWrapper<BakedModel> {

    private BakedModel suspiciousStewModel;
    private final ItemOverrides overrides;

    public InsightItemModel(BakedModel originalModel, Map<ModelResourceLocation, BakedModel> modelRegistry) {
        super(originalModel);

        this.overrides = new ItemOverrides() {
            @Override
            public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
                Player player = entity instanceof Player p ? p : Minecraft.getInstance().player;

                if (player != null && PerceptionEngine.getEffectivePerception(player) >= 50) {
                    if (suspiciousStewModel == null) {
                        ModelResourceLocation stewLocation = new ModelResourceLocation(ResourceLocation.withDefaultNamespace("suspicious_stew"), "inventory");
                        suspiciousStewModel = Minecraft.getInstance().getModelManager().getModel(stewLocation);
                    }
                    if (suspiciousStewModel != null) {
                        return suspiciousStewModel;
                    }
                }
                return originalModel.getOverrides().resolve(originalModel, stack, level, entity, seed);
            }
        };
    }

    @Override
    public ItemOverrides getOverrides() {
        return this.overrides;
    }
}
