package draylar.snowdrift.logic;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class SnowTickEventHandler {

    private static final SnowIncrementer snowIncrementer = new SnowIncrementer();
    private static final SnowDecrementer snowDecrementer = new SnowDecrementer();

    @SubscribeEvent
    public void onTick(TickEvent.WorldTickEvent event) {
        ServerWorld world = event.world.getServer().func_241755_D_();
        List<Chunk> loadedChunks = getLoadedChunks(world);

        if (world.isRaining()) {
            snowIncrementer.tickSnow(world, loadedChunks);
        } else {
            snowDecrementer.tickClear(world, loadedChunks);
        }
    }

    /**
     * Retrieves a list of all loaded Chunks in the given world.
     * Accomplished by iterating over all connected players and storing each chunk within the server render distance from them.
     * @param world world to retrieve loaded chunks from
     * @return a list of loaded chunks in the given world
     */
    private List<Chunk> getLoadedChunks(ServerWorld world) {
        ArrayList<Chunk> loadedChunks = new ArrayList<>();
        int renderDistance = world.getServer().getPlayerList().getViewDistance();

        world.getPlayers().forEach(player -> {
            ChunkPos playerChunkPos = new ChunkPos(player.func_233580_cy_());
            Chunk chunk = world.getChunk(playerChunkPos.x, playerChunkPos.z);

            if(!loadedChunks.contains(chunk)) {
                loadedChunks.add(chunk);
            }

            for(int x = -renderDistance; x <= renderDistance; x++) {
                for(int z = -renderDistance; z <= renderDistance; z++) {
                    ChunkPos offsetChunkPos = new ChunkPos(playerChunkPos.x + x, playerChunkPos.z + z);
                    Chunk offsetChunk = world.getChunk(offsetChunkPos.x, offsetChunkPos.z);

                    if(!loadedChunks.contains(offsetChunk)) {
                        loadedChunks.add(offsetChunk);
                    }
                }
            }
        });

        return loadedChunks;
    }
}
