package com.cognitio.api.perception;

/**
 * Interface a ser implementada por Itens customizados em outros mods (ex: Tomos Mágicos, Artefatos).
 * Define que o item terá seu nome e lore ocultados por linguagem cifrada até que o jogador tenha o Insight necessário.
 */
public interface IInsightItem {
    
    /**
     * @return A quantidade mínima de Insight que um jogador precisa ter para ler as descrições e o nome real do item.
     */
    int getRequiredInsight();
}
