# Cognitio Core - Guia de Arquitetura

Bem-vindo à documentação oficial da arquitetura do **CognitioCore**. Este documento foi criado para explicar como o mod funciona nos bastidores, qual é a responsabilidade de cada classe e por que a estrutura foi desenhada dessa forma (Clean Architecture).

---

## 1. O Conceito Principal
O **CognitioCore** não é apenas um mod, ele é um *motor (engine)* de sanidade/percepção (Insight) para o NeoForge 1.21. A sua missão primária é ser a base para um ecossistema de mods (como o mod de Herbologia). Portanto, sua estrutura precisa ser imaculada:
- **Core (`com.cognitio.core`)**: A parte secreta e pesada. Cuida de salvar os dados no jogador, sincronizar via rede e rodar a matemática. Nenhum outro mod precisa enxergar isso.
- **API (`com.cognitio.api`)**: A vitrine pública. É a única camada com a qual outros mods devem interagir.

---

## 2. A Camada API (`com.cognitio.api.perception`)
Tudo que está na pasta API foi desenhado para evitar *Dependências Circulares* e permitir que terceiros interajam com o Insight de forma simples.

- **`InsightAPI`**: A porta de entrada. Se você estiver desenvolvendo o mod de Herbologia e o jogador tomar uma Poção Alucinógena, você simplesmente chama `InsightAPI.grantInsight(player, 1, InsightSource.CONSUMABLE)`. Todo o trabalho sujo é delegado para o Core.
- **`InsightSource`**: Identificadores (ResourceLocations) que dizem *como* o Insight foi ganho. Permite que eventos sejam filtrados.
- **`InsightEvent`**: Eventos disparados na bus do Forge. Permite que outros mods saibam quando o jogador ganha ou perde Insight e cancelem a ação se necessário.

---

## 3. O Motor Interno (`com.cognitio.core`)

### 3.1. Attachments (`core.attachment`)
O Minecraft vanilla não sabe o que é "Insight". Nós colamos essa informação no jogador através dos **Attachments** do NeoForge.
- **`AttachmentRegister`**: Diz ao jogo que a variável customizada existe.
- **`InsightData`**: Uma *Record* que guarda os pontos atuais do jogador e seus multiplicadores.

### 3.2. A Matemática (`core.perception`)
- **`PerceptionEngine`**: É o coração do mod. Ele implementa a interface da API. Quando a `InsightAPI` é chamada, é esta classe que abre a ficha do jogador, soma os pontos matemáticos, cria um novo `InsightData` e avisa ao servidor para sincronizar os dados.
- **`PerceptionTierManager`**: Avalia os pontos que o jogador tem e decide se ele atingiu um nível crítico (Cegueira, Loucura, etc).

### 3.3. Os Ganhos Passivos (`core.event`)
Nem todo Insight virá de outros mods. O próprio jogo base provê alguns!
- **`InsightGainManager`**: Escuta as ações do jogador no mundo. Se ele comer um Olho de Aranha Cru ou matar um Enderman, este Manager chama a `InsightAPI` internamente e dá os pontos.

### 3.4. O Pesadelo (`core.influence`)
Quando o jogador tem muito Insight, o mundo reage a ele. Esta é a camada de influência:
- **`InfluenceTriggerHandler`**: Um "despertador" que roda a cada 20 ticks (1 segundo) e avisa que o jogador está sendo influenciado.
- **`InfluenceMentalHandler`**: Cuida do lado do cliente (Client-side). Sons sussurrantes, partículas falsas, interface tremendo.
- **`InfluenceRelationalHandler`**: Cuida dos Mobs. Endermen param de atacar o jogador? Zumbis ficam mais fortes perto de você? É aqui que isso vive.
- **`InfluenceOntologicalHandler`**: Altera a física ou o tempo. Fazer o céu escurecer ou a água secar se o jogador olhar pra ela com muita loucura.

---

## Conclusão
O código foi mantido enxuto, agrupado por "domínio", e fortemente protegido por trás de uma API de abstração. Com isso, os desenvolvedores de Addons não precisarão desvendar a matriz inteira de dados: eles usarão eventos simples, mantendo todos os projetos do seu ecossistema saudáveis e rodando no topo do NeoForge 1.21.
