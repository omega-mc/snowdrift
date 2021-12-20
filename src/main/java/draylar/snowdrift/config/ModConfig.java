package draylar.snowdrift.config;

import draylar.omegaconfig.api.Comment;
import draylar.omegaconfig.api.Config;
import org.jetbrains.annotations.Nullable;

public class ModConfig implements Config {

    @Comment("The chance for a snow layer to attempt to appear at a random position in a chunk every tick. 200 is 1/200 chance, or about once every 10 seconds. 20 is about every second.")
    public int increaseChancePerChunk = 200;

    @Comment("The chance for a snow layer to attempt to disappear at a random position in a chunk every tick. 400 is 1/400 chance, or about once every 20 seconds. 20 is about every second.")
    public int decreaseChancePerChunk = 400;

    @Comment("Number of snow layers to keep during snow decay.")
    public int layersToKeep = 1;

    @Comment("Maximum amount of snow layers that is allowed to accumulate at one spot. 8 is the same as a single block.")
    public int maxLayers = 8;

    @Comment("Amount of snow blocks around a position that must be at least the same level of snow in order for snow to pile up.")
    public int smoothingRequirement = 2;

    @Comment("Enables the Blizzard functionality - the entire Overworld (and any similar dimension) is going to be cold and snowy.")
    public boolean enableBlizzard = false;

    @Comment("[Blizzard] Tired of seeing the sun? Enable this for eternal snowfall.")
    public boolean constantSnow = false;

    @Override
    public String getName() {
        return "snowdrift";
    }

    @Override
    public String getExtension() {
        return "json5";
    }

    @Override
    public @Nullable String getModid() {
        return "snowdrift";
    }
}
