package draylar.snowdrift;

import draylar.omegaconfig.OmegaConfig;
import draylar.snowdrift.config.ModConfig;
import draylar.snowdrift.logic.SnowTickEventHandler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Snowdrift implements ModInitializer {

    public static final String MODID = "snowdrift";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final ModConfig CONFIG = OmegaConfig.register(ModConfig.class);

    @Override
    public void onInitialize() {
        SnowTickEventHandler.init();
    }

    public static Identifier id(String name) {
        return new Identifier(MODID, name);
    }
}
