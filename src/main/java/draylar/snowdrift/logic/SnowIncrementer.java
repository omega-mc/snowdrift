package draylar.snowdrift.logic;

import draylar.snowdrift.Snowdrift;
import draylar.snowdrift.mixins.BiomeAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.List;

public class SnowIncrementer {

    private static final float MAX_SNOW_TEMP = 0.15f;

    public void tickSnow(ServerWorld world, List<Chunk> loadedChunks) {
        loadedChunks.forEach(chunk -> {
            ChunkPos chunkPos = chunk.getPos();

            BlockPos snowPos = new BlockPos(chunkPos.getStartX() + world.random.nextInt(16), 0, chunkPos.getStartZ() + world.random.nextInt(16));
            tryIncrementSnowAt(world, snowPos);
        });
    }

    public static boolean canIncrementSnow(ServerWorld world) {
        return world.random.nextInt(Snowdrift.CONFIG.increaseChancePerChunk) == 0;
    }

    private void tryIncrementSnowAt(ServerWorld world, BlockPos basePos) {
        BlockPos topPos = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, basePos);
        BlockState topState = world.getBlockState(topPos);

        if (((BiomeAccessor) (Object) world.getBiomeAccess().getBiome(topPos)).callGetTemperature(topPos) > MAX_SNOW_TEMP) {
            return;
        }

        if (getSnowLevelAt(world, topPos) >= Snowdrift.CONFIG.maxLayers) {
            return;
        }

        // attempt to increment existing snow pile
        if (topState.getBlock().equals(Blocks.SNOW)) {

            int currentLayers = topState.get(SnowBlock.LAYERS);

            int higherPositionsAroundBlock = 0;
            for (int i : getLevelsAround(world, topPos)) {
                if (i >= currentLayers) {
                    higherPositionsAroundBlock++;
                }
            }

            if (higherPositionsAroundBlock >= Snowdrift.CONFIG.smoothingRequirement) {
                if (currentLayers < 8) {
                    world.setBlockState(topPos, topState.with(SnowBlock.LAYERS, currentLayers + 1));
                }
            }
            // There can possibly be a non-air block you can walk through in topPos
        } else if (topState.isAir() && Blocks.SNOW.getDefaultState().canPlaceAt(world, topPos)) {
            // place new snow pile
            world.setBlockState(topPos, Blocks.SNOW.getDefaultState());
        }
    }

    private ArrayList<Integer> getLevelsAround(ServerWorld world, BlockPos pos) {
        ArrayList<Integer> levels = new ArrayList<>();

        for (Direction direction : Direction.values()) {
            if (direction.getAxis() != Direction.Axis.Y) {
                BlockState offsetState = world.getBlockState(pos.offset(direction));

                if (offsetState.getBlock().equals(Blocks.SNOW)) {
                    int offsetLevel = offsetState.get(SnowBlock.LAYERS);
                    levels.add(offsetLevel);
                }
            }
        }

        return levels;
    }

    private int getSnowLevelAt(ServerWorld world, BlockPos pos) {
        int level = 0;

        if (world.getBlockState(pos).getBlock().equals(Blocks.SNOW)) {

            // get lowest snow layer position
            while (world.getBlockState(pos.down(1)).getBlock().equals(Blocks.SNOW)) {
                pos = pos.down(1);
            }

            // while there is snow upwards
            while (world.getBlockState(pos).getBlock().equals(Blocks.SNOW)) {
                level += world.getBlockState(pos).get(SnowBlock.LAYERS);
                pos = pos.up(1);
            }
        }

        return level;
    }
}
