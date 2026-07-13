package com.cognitio.core.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class CognitioClientConfig {

    public static final ModConfigSpec SPEC;
    
    public static final ModConfigSpec.BooleanValue SHOW_HUD;
    public static final ModConfigSpec.IntValue HUD_OFFSET_X;
    public static final ModConfigSpec.IntValue HUD_OFFSET_Y;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("Configurações Client-Side do CognitioCore").push("hud");

        SHOW_HUD = builder
                .comment("Define se o ícone do Olho de Insight deve ser renderizado na tela.")
                .define("showHUD", true);

        HUD_OFFSET_X = builder
                .comment("Deslocamento horizontal do ícone do olho na tela (0 é o padrão centralizado superior).")
                .defineInRange("hudOffsetX", 0, -1000, 1000);

        HUD_OFFSET_Y = builder
                .comment("Deslocamento vertical do ícone do olho na tela.")
                .defineInRange("hudOffsetY", 0, -1000, 1000);

        builder.pop();
        SPEC = builder.build();
    }
}
