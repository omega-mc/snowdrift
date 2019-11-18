package com.github.draylar.snowdrift.config;

import com.github.draylar.snowdrift.Snowdrift;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigManager;
import me.sargunvohra.mcmods.autoconfig1u.serializer.ConfigSerializer;

public class ConfigUtils {

    private ConfigUtils() {
        // NO-OP
    }

    public static void serializeConfig() {
        try {
            ((ConfigManager<ModConfig>) AutoConfig.getConfigHolder(ModConfig.class)).getSerializer().serialize(Snowdrift.CONFIG);
        } catch (ConfigSerializer.SerializationException serializeException) {
            Snowdrift.LOGGER.error("Failed to serialize " + Snowdrift.LOGGER.getName() + "'s config!", serializeException);
        }
    }
}
