# 🚀 Varredura de Performance: CognitioCore

Fiz uma varredura completa nas classes principais do seu mod focando no ciclo de vida de eventos do NeoForge, interações com o disco/Attachments e renderização. O objetivo é garantir que o mod continue extremamente leve mesmo em servidores lotados ou com muitos mods instalados simultaneamente.

Encontrei **4 pontos críticos de performance** (alguns podem causar lag severo em servidores se não forem corrigidos). Aqui estão eles organizados do mais urgente para o menos urgente:

---

## 1. Spam de Eventos Customizados (Crítico) 🚨
> [!CAUTION]
> Na classe `InfluenceTriggerHandler.java`, eventos estão sendo disparados com uma frequência muito alta, o que pode causar gargalos no `NeoForge.EVENT_BUS`.

*   **`onPlayerTick`:** Atualmente, ele dispara um `InfluenceEvent.Psychic` para cada jogador **20 vezes por segundo** (a cada tick). O barramento de eventos do Forge não foi feito para lidar com esse volume contínuo de objetos sendo criados e repassados.
    *   **Solução:** Implementar um "cooldown" ou contador (ex: disparar a cada 20 ou 100 ticks), ou disparar esse evento apenas quando algo relevante acontecer com o jogador, não de forma contínua.
*   **`onEntitySpawn`:** Toda vez que *qualquer* entidade "nasce" no mundo (um zumbi, uma flecha, um item dropado), o código itera por *todos os jogadores do mundo* e dispara um `InfluenceEvent.Relational`. Pense em uma farm de monstros: isso vai congelar o servidor.
    *   **Solução:** Verificar primeiro se a entidade que spawnou é relevante para o sistema de Insight. Além disso, usar checagens de distância (bounding boxes) em vez de iterar por todos os jogadores do mundo.

## 2. Iteração de Jogadores por Tick (Muito Alto) ⚠️
> [!WARNING]
> Na classe `TimeManipulationHandler.java`, a iteração no `LevelTickEvent.Post` é extremamente custosa.

*   **O Problema:** A cada tick (20x por segundo) no Overworld, o código percorre a lista de *todos* os jogadores e acessa o Attachment deles via `getEffectivePerception(player)`. Ler attachments é rápido, mas não rápido o suficiente para ser feito 20 vezes por segundo para dezenas de jogadores.
*   **Solução:** O servidor deve manter uma variável global (ou no `SavedData` do level) do tipo `boolean hasObsessedPlayer`. Essa variável deve ser atualizada *apenas* quando o nível de Insight de um jogador muda (lá na `PerceptionEngine`), em vez de ser recalculada a cada tick do servidor. O `TimeManipulationHandler` apenas leria essa variável em cache, custo O(1).

## 3. Renderização HUD (Alto) 🎨
> [!TIP]
> A classe `InsightOverlay.java` roda a cada *frame* do cliente (potencialmente mais de 144 vezes por segundo).

*   **O Problema:** Atualmente, a leitura de `player.getData(AttachmentRegister.COGNITIO_INSIGHT.get())` acontece a cada frame desenhado na tela. Embora a leitura local do cliente não derrube o servidor, ela pode causar *micro-stutters* (quedas de FPS) no cliente se o mapa de Attachments sofrer contenção.
*   **Solução:** Fazer cache do valor no cliente. Como você já tem o pacote `SyncInsightPayload`, podemos salvar o valor do Insight em uma variável estática no cliente (ex: `ClientInsightCache.currentPoints`) sempre que o pacote chegar. A interface gráfica apenas leria esse número primitivo de graça, sem consultar attachments.

## 4. Atualização de Valores no BlockBreak (Médio) 💡
> [!NOTE]
> Na classe `InsightEventHandler.java`, a função `onBlockBreak` adiciona pontos diretamente.

*   **O Problema:** Quando o jogador usa uma ferramenta com *Eficiência V* ou Haste (minerando dezenas de blocos por segundo), o mod vai calcular tier e atualizar Attachments em altíssima frequência.
*   **Solução:** É comum implementar um pequeno "delay" ou acúmulo de pontos que são computados no final do tick para evitar processamento e salvamento de dados excessivo ao minerar muito rápido.

---

### Quer aplicar essas otimizações?
Se você concordar, posso corrigir esses problemas um a um. A recomendação seria começar pelo **Spam de Eventos** (Problema 1 e 2), pois são as partes da arquitetura que mais vão pesar no servidor a longo prazo.

Me diga qual prefere atacar primeiro ou se posso reescrever essas classes aplicando as melhores práticas de performance!
