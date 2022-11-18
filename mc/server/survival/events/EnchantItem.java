package mc.server.survival.events;

import mc.server.Logger;
import mc.server.survival.libraries.ItemLib;
import mc.server.survival.libraries.MathLib;
import mc.server.survival.libraries.enchantments.EnchantmentHandler;
import mc.server.survival.libraries.enchantments.Enchantments;
import mc.server.survival.libraries.java.WrappedMap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;

public class EnchantItem implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEvent(EnchantItemEvent event)
    {
        final ItemStack itemStack = event.getItem();

        if (MathLib.chanceOf(1))
            EnchantmentHandler.getInstance().addEnchantment(itemStack, Enchantments.UNBREAKABLE);

        if (ItemLib.isPickaxe(itemStack))
            if (MathLib.chanceOf(7))
                EnchantmentHandler.getInstance().addEnchantment(itemStack, Enchantments.INSTANT_MINING);

        if (ItemLib.isAxe(itemStack) || ItemLib.isSword(itemStack))
        {
            if (MathLib.chanceOf(9))
                EnchantmentHandler.getInstance().addEnchantment(itemStack, Enchantments.VAMPIRISM);

            if (MathLib.chanceOf(7))
                EnchantmentHandler.getInstance().addEnchantment(itemStack, Enchantments.IGNITE);

            if (MathLib.chanceOf(5))
                EnchantmentHandler.getInstance().addEnchantment(itemStack, Enchantments.STRIKE);
        }

        if (ItemLib.isSword(itemStack))
            if (MathLib.chanceOf(11))
                EnchantmentHandler.getInstance().addEnchantment(itemStack, Enchantments.ICE_ASPECT);

        if (ItemLib.isShotable(itemStack))
            if (MathLib.chanceOf(15))
                EnchantmentHandler.getInstance().addEnchantment(itemStack, Enchantments.EXPLODE_HASH);

        if (ItemLib.isHoe(itemStack))
            if (MathLib.chanceOf(4))
                EnchantmentHandler.getInstance().addEnchantment(itemStack, Enchantments.SEEDER);

        Logger.getInstance().store(Logger.StoreType.ENCHANT, event.getEnchanter().getName() + " zaklnal " + itemStack.getType() + " na " + WrappedMap.wrapMap(event.getEnchantsToAdd()).print());
    }
}