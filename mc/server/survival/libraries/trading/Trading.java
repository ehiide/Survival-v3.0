package mc.server.survival.libraries.trading;

import mc.server.survival.files.Main;
import mc.server.survival.libraries.ChatLib;
import mc.server.survival.libraries.TaskLib;
import mc.server.survival.libraries.TimeLib;
import mc.server.survival.managers.FileManager;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Trading
{
    public static ItemStack getTrade(final String ID)
    {
        return FileManager.getInstance().trades().getItemStack(ID + ".item");
    }

    public static Set<String> getTrades()
    {
        return FileManager.getInstance().trades().getKeys(false);
    }

    public static void endTrade(final String ID)
    {
        FileManager.getInstance().trades().set(ID + ".status", "ended");
        FileManager.getInstance().save(FileManager.FileType.TRADES);
    }

    public static void buyTrade(final String ID)
    {
        FileManager.getInstance().trades().set(ID + ".status", "buyed");
        FileManager.getInstance().save(FileManager.FileType.TRADES);
    }

    public static void removeTrade(final String ID)
    {
        FileManager.getInstance().trades().set(ID, null);
        FileManager.getInstance().save(FileManager.FileType.TRADES);
    }

    public static Set<ItemStack> getTradesView()
    {
        Set<ItemStack> list = new HashSet<>();

        for (String trade : getTrades())
            if (((String) FileManager.getInstance().trades().get(trade + ".status")).equalsIgnoreCase("pending"))
            {
                ItemStack item = new ItemStack(getTrade(trade).getType(), getTrade(trade).getAmount());
                item.setItemMeta(getTrade(trade).getItemMeta());

                ItemMeta meta = item.getItemMeta();
                List<String> lore = new ArrayList<>();

                if (meta.getLore() != null)
                    lore = meta.getLore();

                lore.add(ChatLib.ColorUtil.formatHEX(" &8> &7Wygasa za: &e" + getEstimatedTradeTime((String) FileManager.getInstance().trades().get(trade + ".date"))));
                lore.add(ChatLib.ColorUtil.formatHEX(" &8> &7Sprzedajacy: &a" + FileManager.getInstance().trades().get(trade + ".owner")));
                lore.add(ChatLib.ColorUtil.formatHEX(" &8> &7Cena: &c" + FileManager.getInstance().trades().get(trade + ".cost") + " ⛃"));
                meta.setLore(lore);

                NamespacedKey key = new NamespacedKey(Main.getInstance(), "ID");
                NamespacedKey key2 = new NamespacedKey(Main.getInstance(), "STATUS");
                NamespacedKey key3 = new NamespacedKey(Main.getInstance(), "VIEW");
                PersistentDataContainer container = meta.getPersistentDataContainer();
                container.set(key, PersistentDataType.STRING, trade);
                container.set(key2, PersistentDataType.STRING, "pending");
                container.set(key3, PersistentDataType.STRING, "global");
                item.setItemMeta(meta);

                list.add(item);
            }

        return list;
    }

    public static Set<ItemStack> getPlayerTradesView(final Player player)
    {
        Set<ItemStack> list = new HashSet<>();

        for (String trade : getTrades())
            if (((String) FileManager.getInstance().trades().get(trade + ".owner")).equalsIgnoreCase(player.getPlayer().getName().toLowerCase()))
                if (    ((String) FileManager.getInstance().trades().get(trade + ".status")).equalsIgnoreCase("pending") ||
                        ((String) FileManager.getInstance().trades().get(trade + ".status")).equalsIgnoreCase("ended") ||
                        ((String) FileManager.getInstance().trades().get(trade + ".status")).equalsIgnoreCase("buyed"))
                {
                    ItemStack item = new ItemStack(getTrade(trade).getType(), getTrade(trade).getAmount());
                    item.setItemMeta(getTrade(trade).getItemMeta());

                    ItemMeta meta = item.getItemMeta();
                    List<String> lore = new ArrayList<>();

                    if (meta.getLore() != null)
                        lore = meta.getLore();

                    final String status = ((String) FileManager.getInstance().trades().get(trade + ".status"));

                    if (status.equalsIgnoreCase("pending"))
                        lore.add(ChatLib.ColorUtil.formatHEX(" &8> &eTwoja oferta jest na rynku, czekaj cierpliwie na klientow!"));
                    else if (status.equalsIgnoreCase("ended"))
                        lore.add(ChatLib.ColorUtil.formatHEX(" &8> &cTwoja oferta wygasla! Kliknij, aby odebrac przedmiot!"));
                    else if (status.equalsIgnoreCase("buyed"))
                        lore.add(ChatLib.ColorUtil.formatHEX(" &8> &aKtos kupil ten przedmiot, kliknij aby odebrac&e " + FileManager.getInstance().trades().get(trade + ".cost") + "⛃ &a!"));

                    meta.setLore(lore);

                    NamespacedKey key = new NamespacedKey(Main.getInstance(), "ID");
                    NamespacedKey key2 = new NamespacedKey(Main.getInstance(), "STATUS");
                    NamespacedKey key3 = new NamespacedKey(Main.getInstance(), "VIEW");
                    PersistentDataContainer container = meta.getPersistentDataContainer();
                    container.set(key, PersistentDataType.STRING, trade);
                    container.set(key2, PersistentDataType.STRING, status);
                    container.set(key3, PersistentDataType.STRING, "player");
                    item.setItemMeta(meta);

                    list.add(item);
                }

        return list;
    }

    public static String getEstimatedTradeTime(final String date)
    {
        if (TimeLib.getDifferenceInHours(date) >= 23)
            return ( 60 - TimeLib.getDifferenceInMinutes(date)) + "m";
        else
            return ( 24 - TimeLib.getDifferenceInHours(date)) + "h";
    }

    public static void scheduleTrades()
    {
        TaskLib.getInstance().runAsyncTimer(() -> {
            for (String ID : getTrades())
                if (TimeLib.getDifferenceInHours((String) FileManager.getInstance().trades().get(ID + ".date")) >= 24)
                    endTrade(ID);
        }, 20 * 60);
    }
}
