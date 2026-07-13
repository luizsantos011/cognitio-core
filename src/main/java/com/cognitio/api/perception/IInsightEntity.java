package com.cognitio.api.perception;

/**
 * Interface a ser implementada por Entidades customizadas em outros mods (ex: Monstros Herbológicos).
 * Define que a entidade faz parte do sistema de Percepção Oculta (Amygdala).
 */
public interface IInsightEntity {
    
    /**
     * @return A quantidade mínima de Insight que um jogador precisa ter para poder ver e interagir com esta entidade.
     */
    int getRequiredInsight();
}
