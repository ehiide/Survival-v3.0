package mc.server.survival.events;

import mc.server.survival.libraries.chemistry.ChemistryCore;
import mc.server.survival.libraries.enchantments.EnchantmentHandler;
import mc.server.survival.libraries.enchantments.Enchantments;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamage implements Listener
{
    @EventHandler
    public void onEvent(EntityDamageEvent event)
    {
        if (event.getEntity() instanceof Player)
        {
           final Player player = (Player) event.getEntity();
           final EntityDamageEvent.DamageCause damageCause = event.getCause();

            if (damageCause.equals(EntityDamageEvent.DamageCause.LAVA) ||
                    damageCause.equals(EntityDamageEvent.DamageCause.FIRE) ||
                    damageCause.equals(EntityDamageEvent.DamageCause.FIRE_TICK))
                if (ChemistryCore.ABILITY_ENVIRONMENT_FIRE_RESISTANCE.get(player))
                    event.setCancelled(true);

            if (damageCause.equals(EntityDamageEvent.DamageCause.HOT_FLOOR))
                if (EnchantmentHandler.getInstance().hasEnchantment(player.getInventory().getBoots(), Enchantments.FIRE_WALKER))
                    event.setCancelled(true);

            if (damageCause.equals(EntityDamageEvent.DamageCause.CONTACT))
                event.setDamage(event.getDamage() / (0.0001 + ChemistryCore.ABILITY_ENVIRONMENT_SENSITIVE.get(player)));
        }
    }
}