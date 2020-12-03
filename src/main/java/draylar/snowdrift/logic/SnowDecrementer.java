package draylar.snowdrift.logic;

import draylar.snowdrift.Snowdrift;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.chunk.Chunk;

import java.util.List;

public class SnowDecrementer {

    public void tickClear(ServerWorld world, List<Chunk> loadedChunks) {
        loadedChunks.forEach(chunk -> {
            ChunkPos chunkPos = chunk.getPos();

            if (canDecrementSnow(world)) {
                BlockPos snowPos = new BlockPos(chunkPos.getStartX() + world.random.nextInt(16), 0, chunkPos.getStartZ() + world.random.nextInt(16));
                tryDecrementSnowAt(world, snowPos);
            }
        });
    }

    private boolean canDecrementSnow(ServerWorld world) {
        return world.random.nextInt(Snowdrift.CONFIG.decreaseChancePerChunk) == 0;
    }

    private void tryDecrementSnowAt(ServerWorld world, BlockPos basePos) {
        BlockPos topPos = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, basePos);
        BlockState topState = world.getBlockState(topPos);
        BlockState underState = world.getBlockState(topPos.down(1));

        // attempt to increment existing snow pile
        if (topState.getBlock().equals(Blocks.SNOW)) {

            int currentLayers = topState.get(SnowBlock.LAYERS);

            // has snow layers underneath, don't account for minimum layers to leave behind
            if (underState.getBlock().equals(Blocks.SNOW)) {
                if (currentLayers - 1 == 0) {
                    world.setBlockState(topPos, Blocks.AIR.getDefaultState(), 3);
                } else {
                    world.setBlockState(topPos, topState.with(SnowBlock.LAYERS, currentLayers - 1));
                }
            } else {
                if (currentLayers > Snowdrift.CONFIG.layersToKeep) {
                    world.setBlockState(topPos, topState.with(SnowBlock.LAYERS, currentLayers - 1));
                }
            }
        }
    }
}
