package mc.server.survival.events;

import mc.server.survival.libraries.ItemLib;
import mc.server.survival.libraries.MathLib;
import mc.server.survival.libraries.chemistry.ChemistryCore;
import mc.server.survival.libraries.enchantments.EnchantmentHandler;
import mc.server.survival.libraries.enchantments.Enchantments;
import mc.server.survival.managers.DataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class PlayerItemDamage implements Listener
{
    @EventHandler
    public void onEvent(PlayerItemDamageEvent event)
    {
        if (event.getItem().isSimilar(new ItemLib().get("magic_rod"))) {event.setCancelled(true); return;}

        if (EnchantmentHandler.getInstance().hasEnchantment(event.getItem(), Enchantments.UNBREAKABLE)) {event.setCancelled(true); return;}

        if (!ChemistryCore.isDrugged(event.getPlayer())) return;

        final int noradrenaline = DataManager.getInstance().getLocal(event.getPlayer()).getNoradrenaline();

        if (noradrenaline > 30)
            event.setDamage(event.getDamage() * ((noradrenaline / 14)));

        if (MathLib.chanceOf(ChemistryCore.ABILITY_PLAYER_LUCK.get(event.getPlayer()) / 2))
            event.setDamage(0);
    }
}