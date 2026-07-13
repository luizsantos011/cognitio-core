# CognitioCore

CognitioCore is a foundational library and perception engine for NeoForge 1.21. It provides a robust, data-driven API for implementing occult, insanity, and insight mechanics in Minecraft mods.

## Features

- **Insight API**: A standardized, network-synchronized system for tracking a player's mental enlightenment (Insight) via NeoForge Attachments.
- **Data-Driven Progression**: Add Insight triggers (consumables or entity kills) via Datapacks (JSON files) without writing Java code.
- **Perception Engine**: Seamlessly manages effective insight, supporting temporary multipliers and flat bonuses.
- **Frenzy Engine**: A built-in sanity overload mechanic that penalizes players with high Insight when exposed to eldritch horrors, featuring natural decay and configurable damage scaling.
- **Dynamic Perception (IInsight Interfaces)**: Allows specific blocks, entities, and items to remain obfuscated or entirely invisible to players who lack the necessary Insight threshold.

## For Developers

CognitioCore abstracts the complex network synchronization, math calculations, and data persistence away from your content mods. To utilize the engine, add the compiled `.jar` to your `libs` directory and implement it as a dependency in your `build.gradle`.

### Key Interfaces

- `InsightAPI`: Use `InsightAPI.grantInsight` or `InsightAPI.grantInsightFromTier` to manipulate player insight.
- `IInsightBlock`: Implement this on your custom blocks to hide them from players below a specific insight tier.
- `IInsightEntity`: Implement this on your custom entities to hide their rendering.
- `IInsightItem`: Implement this to obfuscate item tooltips and rendering.
- `FrenzyEngine`: Call `FrenzyEngine.addFrenzy` to trigger sanity buildup. The engine automatically scales the multiplier based on the player's total Insight.

## Usage

This project acts solely as an API and framework. It requires dependent content mods to inject gameplay content utilizing these systems.
