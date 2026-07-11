# Organização do Workspace e Documentação Arquitetural

Para fecharmos o dia com chave de ouro, precisamos garantir que o seu repositório seja limpo, profissional e muito bem documentado, refletindo o alto nível técnico do código que construímos.

## User Review Required
Revise as propostas de movimentação de arquivos e a estrutura da documentação abaixo. Se estiver tudo de acordo, eu procederei com a criação dos arquivos e a limpeza do projeto.

## Proposed Changes

### 1. Limpeza de Arquivos Soltos (Scripts)
Os scripts auxiliares em PowerShell que criamos durante a sessão não devem ficar espalhados na raiz do projeto.
- Criar a pasta `scripts/` na raiz do projeto.
- Mover `make_eye.ps1`, `fix_packages.ps1` e `refactor.ps1` para dentro de `scripts/`.
- Esses scripts ficarão versionados caso você queira reutilizar o gerador de texturas no futuro.

### 2. Estruturação da Documentação (`docs/`)
Criaremos uma pasta `docs/` dedicada a armazenar a documentação do projeto.
- **`docs/ARCHITECTURE.md`**: Um documento profundo e detalhado explicando:
  - O fluxo de dados do `CognitioCore` (Attachments, UI, Syncing).
  - A separação entre as camadas `core` e `api`.
  - Como a `InsightAPI` deve ser usada por outros mods (ex: Herbologia).
  - A importância da arquitetura limpa (Clean Architecture) para a escalabilidade do mod.
  - O papel de cada Handler (`Mental`, `Relacional`, `Ontológico`, `GainManager`).
- **`docs/history/`**: Uma subpasta onde salvarei os registros das nossas sessões (Walkthroughs, Implementation Plans e Tasks anteriores), para que você tenha todo o histórico de decisões técnicas guardado no seu repositório do Git.

### 3. Atualização do `.gitignore`
Vou revisar o arquivo `.gitignore` para garantir que arquivos temporários, logs antigos ou artefatos indesejados da IDE não poluam seus commits futuros.
- Adicionarei regras para ignorar arquivos `*.log` e a pasta `.gemini/` (onde meus artefatos temporários rodam).

## Verification Plan
1. Rodar os comandos Git para mover os scripts sem perder o histórico.
2. Criar e preencher toda a pasta `docs/`.
3. Commitar tudo no GitHub para encerrarmos a sessão com o projeto "brilhando".
