package mc.server.survival.commands;

import mc.server.Broadcaster;
import mc.server.Logger;
import mc.server.survival.libraries.ChatLib;
import mc.server.survival.libraries.TimeLib;
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

public class Ban
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

            if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("ban").isForAdministrators() ||
                    rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("ban").isForModerators() ||
                    rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("ban").isForPlayers())
            {
                ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
                return true;
            }

            if (length == 0)
            {
                ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cWprowadz nazwe gracza, ktorego chcesz zbanowac!");
                return true;
            }
            else
            {
                final String potencial_player = args[0];

                for (Player dplayer : Bukkit.getOnlinePlayers())
                    if (dplayer.getName().equalsIgnoreCase(potencial_player))
                    {
                        if (TimeLib.getDifferenceInSeconds(DataManager.getInstance().getLocal(dplayer).getBan(dplayer.getName())) < DataManager.getInstance().getLocal(dplayer).getBanLength(dplayer.getName()))
                        {
                            ChatManager.sendNotification(player, "Ten gracz jest juz zbanowany, wiec nie mozesz go zbanowac!", ChatManager.NotificationType.ERROR);
                            return true;
                        }

                        if (length == 1)
                        {
                            ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cWprowadz czas, na jaki chcesz zbanowac gracza!");
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
                            ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cWprowadz powod zbanowania gracza!");
                        else
                        {
                            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                            LocalDateTime now = LocalDateTime.now();
                            String date = dateTimeFormatter.format(now);
                            DataManager.getInstance().getLocal(dplayer).setBan(date);
                            DataManager.getInstance().getLocal(dplayer).setBanLength(util.catchTime());

                            String banner = player.getName();

                            if (length == 3 && args[2].equalsIgnoreCase("-konsola") |
                                    args[2].equalsIgnoreCase("-server") | args[2].equalsIgnoreCase("-serwer") |
                                    args[2].equalsIgnoreCase("-survival"))
                                banner = "Serwer";

                            if (length == 3 && args[2].equalsIgnoreCase("-ukryj") | args[2].equalsIgnoreCase("-anonim"))
                                banner = "Anonim";

                            Broadcaster.broadcastMessages("       ", "#fc7474██&f█#fc7474██", "#fc7474██&f█#fc7474██                      &f&l<#fc7474&l!&f&l> #fc7474&lBAN &f&l<#fc7474&l!&f&l>",
                                    "#fc7474██&f█#fc7474██" + WrappedString.wrapString("#8c8c8cGracz #fc7474" + dplayer.getName() + " #8c8c8cdostal bana na okres #fc7474" + args[1].toLowerCase() + "#8c8c8c!").center(5, 0 ,50).getString(), "#fc7474█████" + WrappedString.wrapString(" #fc7474Wymiar pokuty nalozyl " + banner + "!").center(1, 0 ,50).getString(), "#fc7474██&f█#fc7474██", "");

                            dplayer.kickPlayer(ChatLib.ColorUtil.formatHEX("\n#fc7474██&f█#fc7474██\n#fc7474██&f█#fc7474██\n#fc7474██&f█#fc7474██\n#fc7474█████\n#fc7474██&f█#fc7474██\n\n" +
                                    "#f83044&lUTRACONO POLACZENIE\n#fc7474Twoje konto jest zbanowane!"));
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
                Logger.log("Wprowadz nazwe gracza, ktorego chcesz zbanowac!");
                return true;
            }
            else
            {
                final String potencial_player = args[0];

                for (Player dplayer : Bukkit.getOnlinePlayers())
                    if (dplayer.getName().equalsIgnoreCase(potencial_player))
                    {
                        if (TimeLib.getDifferenceInSeconds(DataManager.getInstance().getLocal(dplayer).getBan(dplayer.getName())) < DataManager.getInstance().getLocal(dplayer).getBanLength(dplayer.getName()))
                        {
                            Logger.log("&cTen gracz jest juz zbanowany, wiec nie mozesz go zbanowac!");
                            return true;
                        }

                        if (length == 1)
                        {
                            Logger.log("Wprowadz czas, na jaki chcesz zbanowac gracza!");
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
                            Logger.log("Wprowadz powod zbanowania gracza!");
                        else
                        {
                            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                            LocalDateTime now = LocalDateTime.now();
                            String date = dateTimeFormatter.format(now);
                            DataManager.getInstance().getLocal(dplayer).setBan(date);
                            DataManager.getInstance().getLocal(dplayer).setBanLength(util.catchTime());

                            String banner = "Serwer";

                            if (length == 3 && args[2].equalsIgnoreCase("-ukryj") | args[2].equalsIgnoreCase("-anonim"))
                                banner = "Anonim";

                            Broadcaster.broadcastMessages("       ", "#fc7474██&f█#fc7474██", "#fc7474██&f█#fc7474██                      &f&l<#fc7474&l!&f&l> #fc7474&lBAN &f&l<#fc7474&l!&f&l>",
                                    "#fc7474██&f█#fc7474██" + WrappedString.wrapString("#8c8c8cGracz #fc7474" + dplayer.getName() + " #8c8c8cdostal bana na okres #fc7474" + args[1].toLowerCase() + "#8c8c8c!").center(5, 0 ,50).getString(), "#fc7474█████" + WrappedString.wrapString(" #fc7474Wymiar pokuty nalozyl " + banner + "!").center(1, 0 ,50).getString(), "#fc7474██&f█#fc7474██", "");
                            dplayer.kickPlayer(ChatLib.ColorUtil.formatHEX("\n#fc7474██&f█#fc7474██\n#fc7474██&f█#fc7474██\n#fc7474██&f█#fc7474██\n#fc7474█████\n#fc7474██&f█#fc7474██\n\n" +
                                    "#f83044&lUTRACONO POLACZENIE\n#fc7474Twoje konto jest zbanowane!"));
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