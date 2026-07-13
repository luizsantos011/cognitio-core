package com.cognitio.api.perception;

import net.minecraft.world.entity.player.Player;

/**
 * Interface principal para mods externos (como Herbologia) interagirem com o Insight dos jogadores de forma segura.
 */
public class InsightAPI {

    private static IInsightProvider provider;

    /**
     * Define o provedor interno (chamado apenas pelo CognitioCore).
     */
    public static void setProvider(IInsightProvider newProvider) {
        if (provider == null) {
            provider = newProvider;
        }
    }

    /**
     * Adiciona Insight ao jogador.
     * @param player O jogador alvo.
     * @param amount Quantidade a ser ganha.
     * @param source Origem do ganho (útil para rastreamento ou interceptação de eventos).
     * @return true se o insight foi processado com sucesso.
     */
    public static boolean grantInsight(Player player, int amount, InsightSource source) {
        if (provider != null) {
            return provider.addInsight(player, amount, source);
        }
        return false;
    }

    /**
     * Adiciona Insight ao jogador, respeitando o Hard Cap (limite) da categoria da ação.
     */
    public static boolean grantInsightFromTier(Player player, EnlightenmentTier tier, int amount, InsightSource source) {
        if (provider != null) {
            return provider.addInsightFromTier(player, tier, amount, source);
        }
        return false;
    }

    public interface IInsightProvider {
        boolean addInsight(Player player, int amount, InsightSource source);
        boolean addInsightFromTier(Player player, EnlightenmentTier tier, int amount, InsightSource source);
    }
}
