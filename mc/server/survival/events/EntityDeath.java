package mc.server.survival.events;

import mc.server.survival.libraries.ItemLib;
import mc.server.survival.libraries.MathLib;
import mc.server.survival.libraries.QuestLib;
import mc.server.survival.managers.DataManager;
import mc.server.survival.worlds.aether.mobs.EtherKing;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class EntityDeath implements Listener
{
    @EventHandler
    public void onEvent(EntityDeathEvent event)
    {
        if (!(event.getEntity() instanceof Player) && event.getEntity().getKiller() != null)
        {
            if (EtherKing.isThat(event.getEntity()))
            {
                event.setDroppedExp(event.getDroppedExp() * 20);
                event.getDrops().clear();
                event.getDrops().add(new ItemLib().get("aether:angel_heart"));
            }

            final int serotonine = DataManager.getInstance().getLocal(event.getEntity().getKiller()).getSerotonine();
            final int dopamine = DataManager.getInstance().getLocal(event.getEntity().getKiller()).getDopamine();
            int xp = event.getDroppedExp();

            if (serotonine >= 60)
                xp = xp * 2;

            if (serotonine <= -20)
                xp = 0;

            if (serotonine <= -40)
                event.getDrops().clear();

            if (dopamine > 5)
            {
                if (event.getEntity() instanceof WitherSkeleton)
                {
                    if (MathLib.chanceOf(dopamine / 15))
                        event.getEntity().getKiller().getInventory().addItem(new ItemStack(Material.WITHER_SKELETON_SKULL, 1));
                }
                else if (event.getEntity() instanceof Skeleton)
                {
                    if (MathLib.chanceOf(dopamine / 14))
                        event.getEntity().getKiller().getInventory().addItem(new ItemStack(Material.SKELETON_SKULL, 1));
                }
                else if (event.getEntity() instanceof Zombie)
                {
                    if (MathLib.chanceOf(dopamine / 10))
                        event.getEntity().getKiller().getInventory().addItem(new ItemStack(Material.ZOMBIE_HEAD, 1));
                }
                else if (event.getEntity() instanceof Creeper)
                {
                    if (MathLib.chanceOf(dopamine / 17))
                        event.getEntity().getKiller().getInventory().addItem(new ItemStack(Material.CREEPER_HEAD, 1));
                }
            }

            final Player player = event.getEntity().getKiller();

            player.setHealth(player.getHealth() < player.getHealthScale() - 1 ? player.getHealth() + 1 : player.getHealthScale());

            event.setDroppedExp(xp * (DataManager.getInstance().getLocal(event.getEntity().getKiller()).getUpgradeLevel(event.getEntity().getKiller().getName(), "thiefy")));
            QuestLib.manageQuest(event.getEntity().getKiller(), 5);
        }
    }
}