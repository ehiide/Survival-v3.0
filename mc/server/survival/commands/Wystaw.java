package mc.server.survival.commands;

import mc.server.Logger;
import mc.server.survival.libraries.MathLib;
import mc.server.survival.libraries.trading.Trading;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import mc.server.survival.managers.FileManager;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Wystaw
implements CommandExecutor, TabCompleter
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
    {
        if (sender instanceof Player)
        {
            final Player player = (Player) sender;

            final String rank = DataManager.getInstance().getLocal(player).getRank();

            if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("wystaw").isForAdministrators() ||
                    rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("wystaw").isForModerators() ||
                    rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("wystaw").isForPlayers())
            {
                ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
                return true;
            }

            if (args.length == 0)
                ChatManager.sendNotification(player, "Powinienes ustalic jakas cene za ten przedmiot!", ChatManager.NotificationType.INFORMATION);
            else
            {
                if (MathLib.isInteger(args[0]))
                {
                    final int cost = Integer.parseInt(args[0]);
                    final int maxCost = (int) FileManager.getInstance().getConfigValue("function.trade-me.max-trade-cost");

                    if (cost > maxCost)
                    {
                        ChatManager.sendNotification(player, "Nie za duzo pragniesz za to cos? Zmniejsz cene ponizej " + maxCost + " monet!", ChatManager.NotificationType.ERROR);
                        return true;
                    }

                    if (cost <= 0)
                    {
                        ChatManager.sendNotification(player, "Nie za malo pragniesz za to cos? Zwieksz cene do minimum 1 monety!", ChatManager.NotificationType.ERROR);
                        return true;
                    }

                    final ItemStack item = player.getInventory().getItemInMainHand();

                    if (item.getType() == Material.AIR)
                    {
                        ChatManager.sendNotification(player, "Co ty chcesz wystawic? Powietrze!", ChatManager.NotificationType.ERROR);
                        return true;
                    }

                    final String itemID = UUID.randomUUID().toString();
                    final Set<String> keys = Trading.getTrades();

                    if (keys.size() >= 36)
                    {
                        ChatManager.sendNotification(player, "Czarny rynek jest pelen przedmiotow, poczekaj az ktos cos wykupi lub skonczy sie okres aukcyjny!", ChatManager.NotificationType.ERROR);
                        return true;
                    }

                    final int perPlayerTrades = (int) FileManager.getInstance().getConfigValue("function.trade-me.max-player-trades");
                    int trades = 0;
                    assert keys.size() != 0;
                    for (String key : keys)
                    {
                        if (key.equalsIgnoreCase(itemID))
                        {
                            ChatManager.sendNotification(player, "Wystapil najrzadszy z najrzadszych bledow tego swiata, czyli podwojone ID przedmiotu, prosimy sprobowac ponownie!", ChatManager.NotificationType.ERROR);
                            return true;
                        }

                        if (((String) FileManager.getInstance().trades().get(key + ".owner")).equalsIgnoreCase(player.getName().toLowerCase()))
                            if (trades++ >= perPlayerTrades - 1)
                            {
                                ChatManager.sendNotification(player, "Nie mozesz wystawic wiecej niz " + perPlayerTrades + " przedmiotow", ChatManager.NotificationType.ERROR);
                                return true;
                            }
                    }

                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    String date = dateTimeFormatter.format(now);

                    FileManager.getInstance().trades().set(itemID + ".owner", player.getName().toLowerCase());
                    FileManager.getInstance().trades().set(itemID + ".cost", cost);
                    FileManager.getInstance().trades().set(itemID + ".date", date);
                    FileManager.getInstance().trades().set(itemID + ".status", "pending");
                    FileManager.getInstance().trades().set(itemID + ".item", item);
                    FileManager.getInstance().save(FileManager.FileType.TRADES);

                    player.getInventory().removeItem(item);

                    return true;
                }

                ChatManager.sendNotification(player, "Ta kwota to liczba?! Wzor komendy: #ffc936/wystaw <cena>!", ChatManager.NotificationType.ERROR);
            }

            return true;
        }
        else if (sender instanceof ConsoleCommandSender)
            Logger.log("&cJak mam Ci wykonac ta komende w konsoli?!");

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args)
    {
        return null;
    }
}