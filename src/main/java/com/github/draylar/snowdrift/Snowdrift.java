package com.github.draylar.snowdrift;

import com.github.draylar.snowdrift.config.ModConfig;
import com.github.draylar.snowdrift.logic.SnowTickEventHandler;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Snowdrift implements ModInitializer {

	public static final String MODID = "snowdrift";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	public static final ModConfig CONFIG = AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new).getConfig();
	public static List<DimensionType> snowdriftDimensions = new ArrayList<>();

	@Override
	public void onInitialize() {
		readValidDimensionsFromConfig();

		SnowTickEventHandler.init();
	}

	public void readValidDimensionsFromConfig() {
//		CONFIG.snowdriftDimensions.forEach(dimensionID -> {
//			if(Registry.DIMENSION.containsId(new Identifier(dimensionID))) {
//				DimensionType type = Registry.DIMENSION.get(new Identifier(dimensionID));
//				snowdriftDimensions.add(type);
//			}
//		});

		// tag me if you want dimension snow support
		snowdriftDimensions.add(DimensionType.OVERWORLD);
	}

	public static Identifier id(String name) {
		return new Identifier(MODID, name);
	}
}
