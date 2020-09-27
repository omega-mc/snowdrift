package draylar.snowdrift;

import draylar.snowdrift.config.Config;
import draylar.snowdrift.logic.SnowTickEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("snowdrift")
public class Snowdrift {

	public static final String MODID = "snowdrift";
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public Snowdrift() {
		MinecraftForge.EVENT_BUS.register(new SnowTickEventHandler());
		ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, Config.SPEC);
	}
}
