package mc.server.survival.commands;

import mc.server.Logger;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import mc.server.survival.managers.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Whitelist
implements CommandExecutor, TabCompleter
{
    @NotNull protected FileConfiguration whitelist = FileManager.getInstance().whitelist();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
    {
        final int length = args.length;

        if (sender instanceof Player)
        {
            final Player player = (Player) sender;

            final String rank = DataManager.getInstance().getLocal(player).getRank();

            if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("whitelist").isForAdministrators() ||
                    rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("whitelist").isForModerators() ||
                    rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("whitelist").isForPlayers())
            {
                ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
                return true;
            }

            if (!(boolean) FileManager.getInstance().getConfigValue("function.whitelist.status"))
            {
                ChatManager.sendNotification(player, "Funkcja whitelisty jest obecnie wylaczona. Mozesz ja wlaczyc w pliku konfiguracyjnym!", ChatManager.NotificationType.ERROR);
                return true;
            }

            if (length > 2)
            {
                ChatManager.sendNotification(player, "Gowno! Jeszcze raz!", ChatManager.NotificationType.ERROR);
                return true;
            }

            if (length == 0)
                ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cGracze wpisani na whiteliste: #fff203" + whitelist.get("whitelist") + ".");
            else
            {
                if (args[0].equalsIgnoreCase("dodaj") || args[0].equalsIgnoreCase("add") ||
                        args[0].equalsIgnoreCase("wpisz") || args[0].equalsIgnoreCase("zapisz") ||
                        args[0].equalsIgnoreCase("+"))
                {
                    if (length == 1)
                    {
                        ChatManager.sendNotification(player, "Ale kogo chcesz dodac?!", ChatManager.NotificationType.ERROR);
                        return true;
                    }

                    final String potencial_player = args[1];
                    List<String> players = (List<String>) whitelist.get("whitelist");

                    if (players != null && players.contains(potencial_player.toLowerCase()))
                    {
                        ChatManager.sendNotification(player, "Podany gracz jest juz na whiteliscie!", ChatManager.NotificationType.ERROR);
                        return true;
                    }

                    players.add(potencial_player.toLowerCase());
                    whitelist.set("whitelist", players);
                    FileManager.getInstance().save(FileManager.FileType.WHITELIST);
                    ChatManager.sendNotification(player, "Gracz " + potencial_player.toUpperCase() + " zostal dodany na whiteliste!", ChatManager.NotificationType.SUCCESS);
                    return true;
                }
                else if (args[0].equalsIgnoreCase("usun") || args[0].equalsIgnoreCase("remove") ||
                        args[0].equalsIgnoreCase("wypisz") || args[0].equalsIgnoreCase("delete") ||
                        args[0].equalsIgnoreCase("-"))
                {
                    if (length == 1)
                    {
                        ChatManager.sendNotification(player, "Ale kogo chcesz usunac?!", ChatManager.NotificationType.ERROR);
                        return true;
                    }

                    final String potencial_player = args[1];
                    List<String> players = (List<String>) whitelist.get("whitelist");

                    if (players != null && players.contains(potencial_player.toLowerCase()))
                    {
                        players.remove(potencial_player.toLowerCase());
                        whitelist.set("whitelist", players);

                        if (players == null)
                            whitelist.set("whitelist", new ArrayList<String>());

                        FileManager.getInstance().save(FileManager.FileType.WHITELIST);
                        ChatManager.sendNotification(player, "Gracz " + potencial_player.toUpperCase() + " zostal usuniety z whitelisty!", ChatManager.NotificationType.SUCCESS);
                        return true;
                    }

                    ChatManager.sendNotification(player, "Podany gracz nie jest nawet na whiteliscie!", ChatManager.NotificationType.ERROR);
                    return true;
                }
                else if (args[0].equalsIgnoreCase("wyczysc") || args[0].equalsIgnoreCase("clear"))
                {
                    whitelist.set("whitelist", new ArrayList<String>());
                    FileManager.getInstance().save(FileManager.FileType.WHITELIST);
                    ChatManager.sendNotification(player, "Whitelista zostala wyczyszczona!", ChatManager.NotificationType.SUCCESS);
                    return true;
                }
                else
                    ChatManager.sendNotification(player, "Naucz sie pisac komendy przyjebie!", ChatManager.NotificationType.ERROR);
            }
            return true;
        }
        else if (sender instanceof ConsoleCommandSender)
        {
            if (length > 2)
            {
                Logger.log("&cGowno! Jeszcze raz!");
                return true;
            }

            if (length == 0)
                Logger.log("Gracze wpisani na whiteliste: " + whitelist.get("whitelist") + ".");
            else
            {
                if (args[0].equalsIgnoreCase("dodaj") || args[0].equalsIgnoreCase("add") ||
                        args[0].equalsIgnoreCase("wpisz") || args[0].equalsIgnoreCase("zapisz") ||
                        args[0].equalsIgnoreCase("+"))
                {
                    if (length == 1)
                    {
                        Logger.log("&cAle kogo chcesz dodac?!");
                        return true;
                    }

                    final String potencial_player = args[1];
                    List<String> players = (List<String>) whitelist.get("whitelist");

                    if (players != null && players.contains(potencial_player.toLowerCase()))
                    {
                        Logger.log("&cPodany gracz jest juz na whiteliscie!");
                        return true;
                    }

                    players.add(potencial_player.toLowerCase());
                    whitelist.set("whitelist", players);
                    FileManager.getInstance().save(FileManager.FileType.WHITELIST);
                    Logger.log("&aGracz " + potencial_player.toUpperCase() + " zostal dodany na whiteliste!");
                    return true;
                }
                else if (args[0].equalsIgnoreCase("usun") || args[0].equalsIgnoreCase("remove") ||
                        args[0].equalsIgnoreCase("wypisz") || args[0].equalsIgnoreCase("delete") ||
                        args[0].equalsIgnoreCase("-"))
                {
                    if (length == 1)
                    {
                        Logger.log("&cAle kogo chcesz usunac?!");
                        return true;
                    }

                    final String potencial_player = args[1];
                    List<String> players = (List<String>) whitelist.get("whitelist");

                    if (players != null && players.contains(potencial_player.toLowerCase()))
                    {
                        players.remove(potencial_player.toLowerCase());
                        whitelist.set("whitelist", players);

                        if (players == null)
                            whitelist.set("whitelist", new ArrayList<String>());

                        FileManager.getInstance().save(FileManager.FileType.WHITELIST);
                        Logger.log("&aGracz " + potencial_player.toUpperCase() + " zostal usuniety z whitelisty!");
                        return true;
                    }

                    Logger.log("&cPodany gracz nie jest nawet na whiteliscie!");
                    return true;
                }
                else if (args[0].equalsIgnoreCase("wyczysc") || args[0].equalsIgnoreCase("clear"))
                {
                    whitelist.set("whitelist", new ArrayList<String>());
                    FileManager.getInstance().save(FileManager.FileType.WHITELIST);
                    Logger.log("&aWhitelista zostala wyczyszczona!");
                    return true;
                }
                else
                    Logger.log("&cNaucz sie pisac komendy przyjebie!");
            }
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args)
    {
        ArrayList<String> completions = new ArrayList<>();

        if (args.length == 1)
        {
            completions.add("dodaj");
            completions.add("usun");
            completions.add("wyczysc");
        }

        if (args.length == 2)
            Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));

        return completions;
    }
}