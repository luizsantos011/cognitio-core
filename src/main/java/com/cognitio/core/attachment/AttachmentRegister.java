package com.cognitio.core.attachment;

import com.cognitio.core.CognitioCore;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.*;
import java.util.function.Supplier;

public class AttachmentRegister {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, CognitioCore.MODID);

    public static final Supplier<AttachmentType<InsightData>> COGNITIO_INSIGHT = ATTACHMENT_TYPES.register(
            "insight",
            () -> AttachmentType.builder(() -> new InsightData(0))
                    .serialize(InsightData.CODEC)
                    .copyOnDeath()
                    .build()
    );

    public static final Supplier<AttachmentType<FrenzyData>> COGNITIO_FRENZY = ATTACHMENT_TYPES.register(
            "frenzy",
            () -> AttachmentType.builder(() -> new FrenzyData(0f))
                    .serialize(FrenzyData.CODEC)
                    // FRENESI zera ao morrer, então não tem .copyOnDeath()
                    .build()
    );
}


