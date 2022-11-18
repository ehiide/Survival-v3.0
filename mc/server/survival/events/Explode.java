package mc.server.survival.events;

import mc.server.survival.managers.FileManager;
import mc.server.survival.worlds.WorldHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class Explode implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEvent(@NotNull EntityExplodeEvent event)
    {
        final Entity entity = event.getEntity();
        final World world = entity.getWorld();

        if ((boolean) FileManager.getInstance().getConfigValue("visual.miscellaneous.falling-leaves"))
        {
            event.blockList().forEach(block -> {
                final WorldHandler.ChunkHandler chunkHandler = WorldHandler.getInstance().getChunkHandler();

                if (chunkHandler.shouldBeLeafHandled(block))
                    chunkHandler.handleLeafChunk(block.getChunk());

                if (block.getType().toString().contains("STAIRS"))
                    if (PlayerInteract.isSomeoneSittingHere(block))
                    {
                        final Player sittingPlayer = PlayerInteract.whoIsSittingHere(block);
                        Dismount.dismount(sittingPlayer, sittingPlayer.getVehicle());
                    }
            });
        }

        if (!(boolean) FileManager.getInstance().getConfigValue("visual.miscellaneous.explode.status"))
            return;

        if (entity instanceof TNTPrimed)
        {
            world.spawnParticle(Particle.SMOKE_LARGE, event.getEntity().getLocation().add(0, 0.25, 0), 35, 5, 5, 5);

            final Material material = event.getLocation().add(0, -1, 0).getBlock().getType();
            final Location location = event.getLocation().add(0, 1, 0);

            for (int x = 0; x < (int) FileManager.getInstance().getConfigValue("visual.miscellaneous.explode.block-count"); x++)
            {
                FallingBlock fallingBlock = world.spawnFallingBlock(location, material.createBlockData());
                fallingBlock.setDropItem(false);
                fallingBlock.setHurtEntities(false);
                fallingBlock.setVelocity(new Vector(new Random().nextDouble() - 0.5, (new Random().nextDouble() + 0.45) / 2, new Random().nextDouble() - 0.5));
            }
        }
    }
}