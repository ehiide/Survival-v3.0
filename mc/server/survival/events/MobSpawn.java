package mc.server.survival.events;

import mc.server.survival.libraries.MathLib;
import mc.server.survival.managers.FileManager;
import mc.server.survival.worlds.aether.mobs.EtherHorse;
import mc.server.survival.worlds.aether.mobs.EtherWarrior;
import mc.server.survival.worlds.twilight.mobs.EvilSpider;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Random;

public class MobSpawn implements Listener
{
    private long lastSpawn = -1;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEvent(CreatureSpawnEvent event)
    {
        if (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL))
            if (event.getLocation().getWorld().getName().equalsIgnoreCase("survival_aether"))
            {
                if (MathLib.chanceOf(20))
                {
                    if (System.currentTimeMillis() - lastSpawn < 2000) return;

                    if ((boolean) FileManager.getInstance().getConfigValue("function.aether.mobs.ether-horse"))
                        for (int x = 0; x <= new Random().nextInt(2) + 2; x++)
                            EtherHorse.summon(event.getLocation());

                    lastSpawn = System.currentTimeMillis();
                }

                if (MathLib.chanceOf(15))
                {
                    if (System.currentTimeMillis() - lastSpawn < 2000) return;

                    if ((boolean) FileManager.getInstance().getConfigValue("function.aether.mobs.ether-warrior"))
                        for (int x = 0; x <= new Random().nextInt(3) + 1; x++)
                            EtherWarrior.summon(event.getLocation());

                    lastSpawn = System.currentTimeMillis();
                }
            }
            else if (event.getLocation().getWorld().getName().equalsIgnoreCase("survival_twilight"))
            {
                if (event.getEntityType().equals(EntityType.MAGMA_CUBE) || event.getEntityType().equals(EntityType.GHAST))
                    event.setCancelled(true);

                if (MathLib.chanceOf(15))
                {
                    if (System.currentTimeMillis() - lastSpawn < 2000) return;

                    if ((boolean) FileManager.getInstance().getConfigValue("function.twilight.mobs.evil-spider"))
                        for (int x = 0; x <= new Random().nextInt(3) + 1; x++)
                            EvilSpider.summon(event.getLocation());

                    lastSpawn = System.currentTimeMillis();
                }
            }
    }
}