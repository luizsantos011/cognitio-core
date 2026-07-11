# 🚀 Abstração de API & Insight Dinâmico

Pronto! Nós transformamos o "ganho de Insight" em um sistema profissional. Agora, tanto o Minecraft Vanilla quanto os seus próximos mods podem se comunicar perfeitamente com o motor de percepção sem precisarem acessar o código fonte um do outro!

## O que foi implementado:

### 1. 🔌 O Nascimento da InsightAPI
Criamos a classe segura **`InsightAPI`** na pasta do seu pacote `api`.
A partir de agora, qualquer mod que você criar (como o seu futuro mod de Herbologia), só precisará chamar uma única linha de código para dar pontos ao jogador:

```java
InsightAPI.grantInsight(player, 1, InsightSource.CONSUMABLE);
```

Toda a matemática e a atualização do pacote na rede (Sync) acontecem magicamente nos bastidores, gerenciados pelo motor do `core`!

### 2. 🧟 O Gerenciador de Ganhos (`InsightGainManager`)
Apagamos aquele código antigo que te dava 10 pontos de insight toda vez que você quebrava um bloco de terra. Era só para teste, afinal! 
Criamos um arquivo dedicado (`InsightGainManager`) para escutar todos os eventos do próprio Vanilla, onde implementamos duas novas regras naturais de progressão:

- 🕷️ **A Dieta Exótica:** Se o jogador comer um **Olho de Aranha** (Cru ou Fermentado), ele ganha `+1 de Insight`.
- 👁️ **A Caçada Interdimensional:** Matar um **Enderman** concede uma faísca de percepção oculta, dando `+5 de Insight`.

## O que testar a seguir?
1. Rode o seu `./gradlew runClient` aí no terminal.
2. Pegue um Olho de Aranha no criativo e coma-o. Veja o número no seu HUD subir instantaneamente.
3. Spawn um Enderman, pegue uma espada, mate-o, e assista ao seu Insight aumentar mais ainda!
