package com.github.draylar.snowdrift;

import com.github.draylar.snowdrift.config.ModConfig;
import com.github.draylar.snowdrift.logic.SnowTickEventHandler;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Snowdrift implements ModInitializer {

	public static final String MODID = "snowdrift";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	public static final ModConfig CONFIG = AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new).getConfig();

	@Override
	public void onInitialize() {
		SnowTickEventHandler.init();
	}

	public static Identifier id(String name) {
		return new Identifier(MODID, name);
	}
}
