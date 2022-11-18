package mc.server.survival.events;

import mc.server.Logger;
import mc.server.survival.libraries.QuestLib;
import mc.server.survival.libraries.chemistry.Chemistries;
import mc.server.survival.libraries.chemistry.ChemistryCore;
import mc.server.survival.libraries.chemistry.ChemistryItem;
import mc.server.survival.managers.FileManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class ItemConsume implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEvent(PlayerItemConsumeEvent event)
    {
        final Player player = event.getPlayer();

        if ((boolean) FileManager.getInstance().getConfigValue("function.chemistry.status"))
        {
            if (event.getPlayer().getInventory().getItemInOffHand().getType() == Material.AIR ||
                event.getPlayer().getInventory().getItemInOffHand().getType() == Material.SHIELD)
            {
                try
                {
                    final boolean isKnownDrug = Chemistries.getInstance().isKnown(event.getPlayer().getInventory().getItemInMainHand());
                    final boolean isNotOnCooldown = !event.getPlayer().hasCooldown(Material.POTION);
                    final boolean haveDrug = event.getItem().getType() == Material.POTION;

                    if (isKnownDrug && isNotOnCooldown && haveDrug)
                    {
                        final String itemName = event.getItem().hasItemMeta() ? event.getItem().getItemMeta().getDisplayName() : null;
                        final ChemistryItem chemistryItem = Chemistries.getInstance().byName(itemName);

                        if (chemistryItem.getAffinity().isOpioidic())
                            ChemistryCore.normalize(event.getPlayer(),
                                    chemistryItem.getAffinity().getOpioidic());
                        else if (chemistryItem.getAffinity().isAmine())
                            ChemistryCore.modify(event.getPlayer(),
                                    chemistryItem.getAffinity().getSerotonine(),
                                    chemistryItem.getAffinity().getDopamine(),
                                    chemistryItem.getAffinity().getNoradrenaline(),
                                    chemistryItem.getAffinity().getGABA());

                        player.setFoodLevel(Math.min(player.getFoodLevel() + 4, 20));
                        QuestLib.manageQuest(player, 4);
                        event.getPlayer().setCooldown(Material.POTION, 120);

                        Logger.getInstance().store(Logger.StoreType.CONSUME, player.getName() + " skonsumowal " + ChatColor.stripColor(itemName));
                        return;
                    }
                }
                catch (Exception ignored) {}
            }
            else if (Chemistries.getInstance().isKnown(event.getPlayer().getInventory().getItemInMainHand())) event.setCancelled(true);
        }

        Logger.getInstance().store(Logger.StoreType.CONSUME, player.getName() + " skonsumowal " + event.getItem().getType());
    }
}