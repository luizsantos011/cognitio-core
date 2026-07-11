package com.cognitio.core;

import com.cognitio.core.attachment.AttachmentRegister;
import com.cognitio.core.influence.*;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(CognitioCore.MODID)
public class CognitioCore {
    public static final String MODID = "cognitio_core";
    public static final Logger LOGGER = LogUtils.getLogger();

    // Registradores mantidos vazios para uso futuro do ecossistema
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public CognitioCore(IEventBus modEventBus, ModContainer modContainer) {
        // Inicialização dos barramentos de eventos essenciais
        modEventBus.addListener(this::commonSetup);

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(new InfluenceTriggerHandler());
        NeoForge.EVENT_BUS.register(new InfluenceMentalHandler());
        NeoForge.EVENT_BUS.register(new InfluenceRelationalHandler());
        NeoForge.EVENT_BUS.register(new InfluenceOntologicalHandler());
        NeoForge.EVENT_BUS.register(new TimeManipulationHandler());

        // Registro do arquivo de configuração do Mod
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        AttachmentRegister.ATTACHMENT_TYPES.register(modEventBus);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Cognitio Core: Inicializando sistemas base...");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Cognitio Core: Servidor carregado.");
    }

    @EventBusSubscriber(modid = CognitioCore.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    static class ClientModEvents {
        @SubscribeEvent
        static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("Cognitio Core: Inicializando lado do Cliente.");
        }
    }
}
