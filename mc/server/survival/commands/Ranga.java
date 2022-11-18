package mc.server.survival.commands;

import mc.server.Logger;
import mc.server.survival.libraries.ScoreboardLib;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import mc.server.survival.managers.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Ranga
implements CommandExecutor, TabCompleter
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
    {
        final int length = args.length;

        if (sender instanceof Player)
        {
            final Player player = (Player) sender;

            final String senderRank = DataManager.getInstance().getLocal(player).getRank();

            if (senderRank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("ranga").isForAdministrators() ||
                    senderRank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("ranga").isForModerators() ||
                    senderRank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("ranga").isForPlayers())
            {
                ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
                return true;
            }

            if (length == 0)
            {
                ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cWprowadz nazwe gracza! Wzor komendy: #ffc936/ranga <gracz> <ranga>");
                return true;
            }
            else
            {
                final String potencial_player = args[0];

                for (Player dplayer : Bukkit.getOnlinePlayers())
                    if (dplayer.getName().equalsIgnoreCase(potencial_player))
                        if (length == 1)
                        {
                            ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cWprowadz nowa range! Wzor komendy: #ffc936/ranga <gracz> <ranga>");
                            return true;
                        }
                        else
                        {
                            String rank;

                            if (args[1].equalsIgnoreCase("administrator") || args[1].equalsIgnoreCase("admin") || args[1].equalsIgnoreCase("adm"))
                                rank = "Administrator";
                            else if (args[1].equalsIgnoreCase("moderator") || args[1].equalsIgnoreCase("mod"))
                                rank = "Moderator";
                            else if (args[1].equalsIgnoreCase("gracz") || args[1].equalsIgnoreCase("reset"))
                                rank = "Gracz";
                            else
                            {
                                ChatManager.sendNotification(player, "Taka ranga nie istnieje!", ChatManager.NotificationType.ERROR);
                                return true;
                            }

                            ChatManager.sendNotification(player, "Pomyslnie ustawiono range " + rank.toLowerCase() + "a, dla " + dplayer.getName() + "!", ChatManager.NotificationType.SUCCESS);
                            DataManager.getInstance().getLocal(dplayer).setRank(rank);
                            ScoreboardLib.getInstance().reloadContentsGlobal();
                            dplayer.updateCommands();
                            return true;
                        }

                if (!Bukkit.getOfflinePlayer(potencial_player).isOnline())
                {
                    ChatManager.sendNotification(player, "Podany gracz nie jest on-line na serwerze!", ChatManager.NotificationType.ERROR);
                    return true;
                }
            }
        }
        else if (sender instanceof ConsoleCommandSender)
        {
            if (length == 0)
            {
                Logger.log("&7Wprowadz nazwe gracza! Wzor komendy: &e/ranga <gracz> <ranga>");
                return true;
            }
            else
            {
                final String potencial_player = args[0];

                for (Player dplayer : Bukkit.getOnlinePlayers())
                    if (dplayer.getName().equalsIgnoreCase(potencial_player))
                        if (length == 1)
                        {
                            Logger.log("&7Wprowadz nowa range! Wzor komendy: &e/ranga <gracz> <ranga>");
                            return true;
                        }
                        else
                        {
                            String rank;

                            if (args[1].equalsIgnoreCase("administrator") || args[1].equalsIgnoreCase("admin") || args[1].equalsIgnoreCase("adm"))
                                rank = "Administrator";
                            else if (args[1].equalsIgnoreCase("moderator") || args[1].equalsIgnoreCase("mod"))
                                rank = "Moderator";
                            else if (args[1].equalsIgnoreCase("gracz") || args[1].equalsIgnoreCase("reset"))
                                rank = "Gracz";
                            else
                            {
                                Logger.log("&cTaka ranga nie istnieje!");
                                return true;
                            }

                            Logger.log("&aPomyslnie ustawiono range " + rank.toLowerCase() + "a, dla " + dplayer.getName() + "!");
                            DataManager.getInstance().getLocal(dplayer).setRank(rank);
                            ScoreboardLib.getInstance().reloadContentsGlobal();
                            dplayer.updateCommands();
                            return true;
                        }

                if (!Bukkit.getOfflinePlayer(potencial_player).isOnline())
                {
                    Logger.log("&cPodany gracz nie jest on-line na serwerze!");
                    return true;
                }
            }
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args)
    {
        ArrayList<String> completions = new ArrayList<>();

        if (args.length == 2)
        {
            completions.add("administrator");
            completions.add("moderator");
            completions.add("gracz");
        }

        if (args.length == 1)
            Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));

        return completions;
    }
}