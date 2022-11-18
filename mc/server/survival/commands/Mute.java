package mc.server.survival.commands;

import mc.server.Broadcaster;
import mc.server.Logger;
import mc.server.survival.libraries.ChatLib;
import mc.server.survival.libraries.TimeLib;
import mc.server.survival.libraries.VisualLib;
import mc.server.survival.libraries.java.WrappedString;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import mc.server.survival.managers.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Mute
implements CommandExecutor, TabCompleter
{
    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
    {
        if (sender instanceof Player)
        {
            final Player player = (Player) sender;
            final int length = args.length;

            final String rank = DataManager.getInstance().getLocal(player).getRank();

            if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("mute").isForAdministrators() ||
                    rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("mute").isForModerators() ||
                    rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("mute").isForPlayers())
            {
                ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
                return true;
            }

            if (length == 0)
            {
                ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cWprowadz nazwe gracza, ktorego chcesz wyciszyc!");
                return true;
            }
            else
            {
                final String potencial_player = args[0];

                for (Player dplayer : Bukkit.getOnlinePlayers())
                    if (dplayer.getName().equalsIgnoreCase(potencial_player))
                    {
                        if (TimeLib.getDifferenceInSeconds(DataManager.getInstance().getLocal(dplayer).getMute(dplayer.getName())) < DataManager.getInstance().getLocal(dplayer).getMuteLength(dplayer.getName()))
                        {
                            ChatManager.sendNotification(player, "Ten gracz jest juz wyciszony, wiec nie mozesz go wyciszyc!", ChatManager.NotificationType.ERROR);
                            return true;
                        }

                        if (length == 1)
                        {
                            ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cWprowadz czas, na jaki chcesz wyciszyc gracza!");
                            return true;
                        }

                        final String init = args[1];
                        final TimeLib.UnitUtil util = TimeLib.getInstance().getUnitUtil(init);

                        if (!util.isCorrectTime())
                        {
                            ChatManager.sendNotification(player, "Podany czas nie jest obslugiwany, naucz sie cyfr!", ChatManager.NotificationType.ERROR);
                            return true;
                        }

                        if (!util.isCorrectUnit())
                        {
                            ChatManager.sendNotification(player, "Podany format czasowy nie jest obslugiwany!", ChatManager.NotificationType.ERROR);
                            return true;
                        }

                        if (length == 2)
                            ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cWprowadz powod wyciszenia gracza!");
                        else
                        {
                            StringBuilder message = new StringBuilder();

                            for (int word = 2; word < length; word++)
                                if (word + 1 >= length)
                                    message.append(args[word].toLowerCase());
                                else
                                    message.append(args[word].toLowerCase()).append(" ");

                            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                            LocalDateTime now = LocalDateTime.now();
                            String date = dateTimeFormatter.format(now);
                            DataManager.getInstance().getLocal(dplayer).setMute(date);
                            DataManager.getInstance().getLocal(dplayer).setMuteLength(util.catchTime());

                            String muter = player.getName();
                            String cause = ChatLib.applyCorrection(message.toString());

                            if (length == 3 && args[2].equalsIgnoreCase("-konsola") |
                                    args[2].equalsIgnoreCase("-server") | args[2].equalsIgnoreCase("-serwer") |
                                    args[2].equalsIgnoreCase("-survival"))
                            {
                                muter = "Serwer";
                                cause = "Serwer";
                            }

                            if (length == 3 & args[2].equalsIgnoreCase("-ukryj") || args[2].equalsIgnoreCase("-anonim"))
                            {
                                muter = "Anonim";
                                cause = "Powod jest anonimowy!";
                            }

                            cause = cause.substring(0, cause.length() - 1);

                            Broadcaster.broadcastMessages("       ", "#ffc936██&f█#ffc936██", "#ffc936██&f█#ffc936██                     &f&l<#ffc936&l!&f&l> #ffc936&lMUTE &f&l<#ffc936&l!&f&l>",
                                    "#ffc936██&f█#ffc936██" + WrappedString.wrapString("#8c8c8cGracz #ffc936" + dplayer.getName() + " #8c8c8czamknal morde na okres #ffc936" + args[1].toLowerCase() + "#8c8c8c!").center(5, 0 ,50).getString(), "#ffc936█████" + WrappedString.wrapString(" #f8ff26Wymiar pokuty nalozyl " + muter + "!").center(1, 0 ,50).getString(), "#ffc936██&f█#ffc936██", "");
                            ChatManager.sendMessage(dplayer, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#ffc936Zostales wyciszony na czacie za: " + cause + "#ffc936!");
                            VisualLib.showDelayedTitle(dplayer, " ", "#ffc936✖", 0, 10, 10);
                        }
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
            final int length = args.length;

            if (length == 0)
            {
                Logger.log("Wprowadz nazwe gracza, ktorego chcesz wyciszyc!");
                return true;
            }
            else
            {
                final String potencial_player = args[0];

                for (Player dplayer : Bukkit.getOnlinePlayers())
                    if (dplayer.getName().equalsIgnoreCase(potencial_player))
                    {
                        if (TimeLib.getDifferenceInSeconds(DataManager.getInstance().getLocal(dplayer).getMute(dplayer.getName())) < DataManager.getInstance().getLocal(dplayer).getMuteLength(dplayer.getName()))
                        {
                            Logger.log("&cTen gracz jest juz wyciszony, wiec nie mozesz go wyciszyc!");
                            return true;
                        }

                        if (length == 1)
                        {
                            Logger.log("Wprowadz czas, na jaki chcesz wyciszyc gracza!");
                            return true;
                        }

                        final String init = args[1];
                        final TimeLib.UnitUtil util = TimeLib.getInstance().getUnitUtil(init);

                        if (!util.isCorrectTime())
                        {
                            Logger.log("&cPodany czas nie jest obslugiwany, naucz sie cyfr!");
                            return true;
                        }

                        if (!util.isCorrectUnit())
                        {
                            Logger.log("&cPodany format czasowy nie jest obslugiwany!");
                            return true;
                        }

                        if (length == 2)
                            Logger.log("Wprowadz powod wyciszenia gracza!");
                        else
                        {
                            StringBuilder message = new StringBuilder();

                            for (int word = 2; word < length; word++)
                                if (word + 1 >= length)
                                    message.append(args[word].toLowerCase());
                                else
                                    message.append(args[word].toLowerCase()).append(" ");

                            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                            LocalDateTime now = LocalDateTime.now();
                            String date = dateTimeFormatter.format(now);
                            DataManager.getInstance().getLocal(dplayer).setMute(date);
                            DataManager.getInstance().getLocal(dplayer).setMuteLength(util.catchTime());

                            String muter = "Serwer";
                            String cause = ChatLib.applyCorrection(message.toString());

                            if (length == 3 && args[2].equalsIgnoreCase("-konsola") |
                                    args[2].equalsIgnoreCase("-server") | args[2].equalsIgnoreCase("-serwer") |
                                    args[2].equalsIgnoreCase("-survival"))
                                cause = "Serwer";

                            if (length == 3 & args[2].equalsIgnoreCase("-ukryj") || args[2].equalsIgnoreCase("-anonim"))
                            {
                                muter = "Anonim";
                                cause = "Powod jest anonimowy!";
                            }

                            cause = cause.substring(0, cause.length() - 1);

                            Broadcaster.broadcastMessages("       ", "#ffc936██&f█#ffc936██", "#ffc936██&f█#ffc936██                     &f&l<#ffc936&l!&f&l> #ffc936&lMUTE &f&l<#ffc936&l!&f&l>",
                                    "#ffc936██&f█#ffc936██" + WrappedString.wrapString("#8c8c8cGracz #ffc936" + dplayer.getName() + " #8c8c8czamknal morde na okres #ffc936" + args[1].toLowerCase() + "#8c8c8c!").center(5, 0, 50).getString(), "#ffc936█████" + WrappedString.wrapString(" #f8ff26Wymiar pokuty nalozyl " + muter + "!").center(1, 0, 50).getString(), "#ffc936██&f█#ffc936██", "");
                            ChatManager.sendMessage(dplayer, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#ffc936Zostales wyciszony na czacie za: " + cause + "#ffc936!");
                            VisualLib.showDelayedTitle(dplayer, " ", "#ffc936✖", 0, 10, 10);
                        }
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

        if (args.length == 3)
        {
            completions.add("-anonim");
            completions.add("-konsola");
        }

        if (args.length == 1)
            Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));

        return completions;
    }
}