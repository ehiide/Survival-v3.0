package mc.server.survival.events;

import mc.server.survival.libraries.java.WrappedList;
import mc.server.survival.worlds.WorldHandler;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ChunkLoad implements Listener
{
    private static final @NotNull HashMap<Chunk, WrappedList<Location>> locations = new HashMap<>();
    private static final @NotNull WrappedList<Chunk> activeChunks = new WrappedList<>();

    public static @NotNull WrappedList<Chunk> getActiveLeafChunks() { return activeChunks; }
    public static @NotNull HashMap<Chunk, WrappedList<Location>> getLeafLocations() { return locations; }

    public static void reloadLeafChunks() { locations.clear(); activeChunks.clear(); }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEvent(ChunkLoadEvent event)
    {
        if (WorldHandler.getInstance().isNether(event.getWorld()) || WorldHandler.getInstance().isEnd(event.getWorld())) return;

        final Chunk chunk = event.getChunk();

        if (locations.containsKey(chunk))
            locations.put(chunk, new WrappedList<>());

        WorldHandler.getInstance().getChunkHandler().handleLeafChunk(chunk);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEvent(ChunkUnloadEvent event)
    {
        if (WorldHandler.getInstance().isNether(event.getWorld()) || WorldHandler.getInstance().isEnd(event.getWorld())) return;

        final Chunk chunk = event.getChunk();

        activeChunks.remove(chunk);
        locations.put(chunk, new WrappedList<>());
    }
}