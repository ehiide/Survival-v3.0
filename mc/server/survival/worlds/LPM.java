package mc.server.survival.worlds;

import mc.server.survival.libraries.TaskLib;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Minecart;

import java.util.List;

public class LPM
{
    private static boolean isDrop(final Entity entity)
    {
        return entity.getCustomName() != null && entity instanceof Item && entity.getTicksLived() > 3000;
    }

    public static void runTask()
    {
        TaskLib.getInstance().runSyncTimer(() -> {
            final List<World> worlds = WorldHandler.getWorlds();

            for (final World world : worlds)
                for (final Entity entity : world.getEntities())
                {
                    if (isDrop(entity)) entity.remove();

                    if (entity instanceof ArmorStand armorStand)
                    {
                        if (armorStand.getEquipment().getArmorContents() != null) return;
                        if (armorStand.getEquipment().getItemInMainHand().getType() != Material.AIR) return;
                        if (armorStand.getEquipment().getItemInOffHand().getType() != Material.AIR) return;
                        if (armorStand.getCustomName() != null) return;

                        if (entity.getTicksLived() > 3000 && !entity.getLocation().getChunk().isLoaded())
                            entity.remove();
                    }

                    if (entity instanceof Minecart)
                        if (entity.getTicksLived() > 3000 && !entity.getLocation().getChunk().isLoaded())
                            entity.remove();
                }
        }, 6000);
    }
}