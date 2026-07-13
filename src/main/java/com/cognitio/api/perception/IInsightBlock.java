package com.cognitio.api.perception;

/**
 * Interface a ser implementada por Blocos customizados em outros mods (ex: Passagens Secretas, Altares).
 * Define que o bloco faz parte do sistema de Percepção Oculta.
 */
public interface IInsightBlock {
    
    /**
     * @return A quantidade mínima de Insight que um jogador precisa ter para poder interagir ou quebrar este bloco.
     */
    int getRequiredInsight();
}
