package draylar.snowdrift.config;


import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {

    public static final ForgeConfigSpec SPEC;
    public static final Server SERVER;

    static {
        Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Server::new);
        SPEC = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    public static class Server {

        public final ForgeConfigSpec.IntValue increaseChancePerChunk;
        public final ForgeConfigSpec.IntValue decreaseChancePerChunk;
        public final ForgeConfigSpec.IntValue layersToKeep;
        public final ForgeConfigSpec.IntValue maxLayers;
        public final ForgeConfigSpec.IntValue smoothingRequirement;

        public Server(ForgeConfigSpec.Builder builder) {
            increaseChancePerChunk = builder
                    .comment("The chance for a snow layer to attempt to appear at a random position in a chunk every tick. 200 is 1/200 chance, or about once every 10 seconds. 20 is about every second.")
                    .defineInRange("increase_chance", 200, 0,  Integer.MAX_VALUE);

            decreaseChancePerChunk = builder
                    .comment("The chance for a snow layer to attempt to disappear at a random position in a chunk every tick. 400 is 1/400 chance, or about once every 20 seconds. 20 is about every second.")
                    .defineInRange("decrease_chance", 400, 0,  Integer.MAX_VALUE);

            layersToKeep = builder
                    .comment("Number of snow layers to keep during snow decay.")
                    .defineInRange("layers_to_keep", 1, 0,  Integer.MAX_VALUE);

            maxLayers = builder
                    .comment("Maximum amount of snow layers that is allowed to accumulate at one spot. 8 is the same as a single block.")
                    .defineInRange("max_layers", 8, 0,  Integer.MAX_VALUE);

            smoothingRequirement = builder
                    .comment("Amount of snow blocks around a position that must be at least the same level of snow in order for snow to pile up.")
                    .defineInRange("smoothing_requirement", 2, 0,  Integer.MAX_VALUE);
        }
    }
}
