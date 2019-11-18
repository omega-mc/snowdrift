package com.github.draylar.snowdrift.config;

import com.github.draylar.snowdrift.Snowdrift;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Config(name = Snowdrift.MODID)
public class ModConfig implements ConfigData {
    @Comment("The chance for a snow layer to appear at a random position in a chunk every tick. 200 is 1/200 chance, or about once every 10 seconds. 20 is about every second.")
    public int increaseChancePerChunk = 200;

    @Comment("The chance for a snow layer to disappear at a random position in a chunk every tick. 400 is 1/400 chance, or about once every 20 seconds. 20 is about every second.")
    public int decreaseChancePerChunk = 400;

    @Comment("Number of snow layers to keep during snow decay.")
    public int layersToKeep = 1;

    @Comment("Amount of snow blocks around a position that must be at least the same level of snow in order for snow to pile up.")
    public int smoothingRequirement = 2;
}
