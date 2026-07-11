package com.cognitio.core.influence;

import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import static com.cognitio.core.perception.PerceptionEngine.*;

public class TimeManipulationHandler {
    private boolean skipTick = false;

    @SubscribeEvent
    public void onLevelTick(LevelTickEvent.Post event) {
        if (event.getLevel() instanceof ServerLevel serverLevel && serverLevel.dimension() == ServerLevel.OVERWORLD) {

            if (com.cognitio.core.perception.PerceptionTierManager.hasObsessedPlayer) {
                if (skipTick) {
                    long currentTime = serverLevel.getDayTime();
                    serverLevel.setDayTime(currentTime - 1);
                }
                skipTick = !skipTick;
            }
        }
    }
}
