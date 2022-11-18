package mc.server.survival.libraries.enchantments;

import mc.server.survival.libraries.ChatLib;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EnchantmentHandler
{
    private EnchantmentHandler() {}

    static EnchantmentHandler instance = new EnchantmentHandler();

    public static EnchantmentHandler getInstance() { return instance; }

    public ItemStack addEnchantment(ItemStack itemStack, EnchantmentWrapper enchantmentWrapper)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();

        if (!hasEnchantment(itemStack, enchantmentWrapper))
            lore.add(ChatLib.ColorUtil.formatHEX("&a" + enchantmentWrapper.getName()));

        if (itemMeta.hasLore())
            for (String verse : itemMeta.getLore())
                lore.add(verse);

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public ItemStack removeEnchantment(ItemStack itemStack, EnchantmentWrapper enchantmentWrapper)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();

        if (itemMeta.hasLore())
            for (String verse : itemMeta.getLore())
                if (!ChatLib.ColorUtil.formatHEX("&a" + verse).contains(ChatLib.ColorUtil.formatHEX("&a" + enchantmentWrapper.getName())))
                    lore.add(verse);

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public void removeEnchantments(ItemStack itemStack)
    {
        for (EnchantmentWrapper enchantmentWrapper : Enchantments.getInstance().getEnchantments())
            if (hasEnchantment(itemStack, enchantmentWrapper))
                removeEnchantment(itemStack, enchantmentWrapper);
    }

    public boolean hasEnchantment(ItemStack itemStack, EnchantmentWrapper enchantmentWrapper)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null)
            if (itemMeta.hasLore())
                for (String verse : itemMeta.getLore())
                    if (ChatLib.ColorUtil.formatHEX("&a" + verse).contains(ChatLib.ColorUtil.formatHEX("&a" + enchantmentWrapper.getName())))
                        return true; return false;
    }

    public Set<EnchantmentWrapper> getEnchantments(ItemStack itemStack)
    {
        Set<EnchantmentWrapper> enchantmentWrappers = new HashSet<>();

        for (EnchantmentWrapper enchantmentWrapper : Enchantments.getInstance().getEnchantments())
            if (hasEnchantment(itemStack, enchantmentWrapper))
                enchantmentWrappers.add(enchantmentWrapper);

        return enchantmentWrappers;
    }

    public boolean isEnchantmentBook(ItemStack itemStack)
    {
        for (EnchantmentWrapper enchantmentWrapper : Enchantments.getInstance().getEnchantments())
            if (hasEnchantment(itemStack, enchantmentWrapper))
                return true;

        return false;
    }

    public EnchantmentWrapper getEnchantmentFromBook(ItemStack itemStack)
    {
        for (EnchantmentWrapper enchantmentWrapper : Enchantments.getInstance().getEnchantments())
            if (hasEnchantment(itemStack, enchantmentWrapper))
                return enchantmentWrapper;

        return null;
    }
}