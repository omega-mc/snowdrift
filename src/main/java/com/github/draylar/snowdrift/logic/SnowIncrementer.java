package com.github.draylar.snowdrift.logic;

import com.github.draylar.snowdrift.Snowdrift;
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

    public void tickSnow(ServerWorld world, List<Chunk> loadedChunks) {
        loadedChunks.forEach(chunk -> {
            ChunkPos chunkPos = chunk.getPos();

            if(canIncrementSnow(world)) {
                BlockPos snowPos = new BlockPos(chunkPos.getStartX() + world.random.nextInt(16), 0, chunkPos.getStartZ() + world.random.nextInt(16));
                tryIncrementSnowAt(world, snowPos);
            }
        });
    }

    private boolean canIncrementSnow(ServerWorld world) {
        return world.random.nextInt(Snowdrift.CONFIG.increaseChancePerChunk) == 0;
    }

    // todo: what if snow is layer 0? do we enter a deadlock where no snow can pile at certain positions?
    private void tryIncrementSnowAt(ServerWorld world, BlockPos basePos) {
        BlockPos topPos = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, basePos);
        BlockState topState = world.getBlockState(topPos);

        // attempt to increment existing snow pile
        if(topState.getBlock().equals(Blocks.SNOW)) {

            int currentLayers = topState.get(SnowBlock.LAYERS);

            int higherPositionsAroundBlock = 0;
            for(int i : getLevelsAround(world, topPos)) {
                if(i >= currentLayers) {
                    higherPositionsAroundBlock++;
                }
            }

            if(higherPositionsAroundBlock >= Snowdrift.CONFIG.smoothingRequirement) {
                if (currentLayers < 8) {
                    world.setBlockState(topPos, topState.with(SnowBlock.LAYERS, currentLayers + 1));
                    return;
                }
            }
        }

        // place new snow pile
        world.setBlockState(topPos, Blocks.SNOW.getDefaultState());
    }

    private ArrayList<Integer> getLevelsAround(ServerWorld world, BlockPos pos) {
        ArrayList<Integer> levels = new ArrayList<>();

        for(Direction direction : Direction.values()) {
            if(direction.getAxis() != Direction.Axis.Y) {
                BlockState offsetState = world.getBlockState(pos.offset(direction));

                if(offsetState.getBlock().equals(Blocks.SNOW)) {
                    int offsetLevel = offsetState.get(SnowBlock.LAYERS);
                    levels.add(offsetLevel);
                }
            }
        }

        return levels;
    }
}
