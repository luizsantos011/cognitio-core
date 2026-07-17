package com.cognitio.api.perception;

import net.minecraft.world.item.Item;
import java.util.ArrayList;
import java.util.List;

/**
 * API for registering physical inventory transmutations based on Insight tiers.
 */
public class TransmutationAPI {
    
    public record TransmutationRule(Item original, Item target, EnlightenmentTier tier) {}
    
    private static final List<TransmutationRule> RULES = new ArrayList<>();

    /**
     * Registers a new transmutation rule.
     * @param original The normal mundane item.
     * @param target The discerned magical item.
     * @param tier The required insight tier to perceive the target item.
     */
    public static void register(Item original, Item target, EnlightenmentTier tier) {
        RULES.add(new TransmutationRule(original, target, tier));
    }
    
    public static List<TransmutationRule> getRules() {
        return RULES;
    }
}
