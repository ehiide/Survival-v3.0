package mc.server.survival.commands;

import mc.server.Broadcaster;
import mc.server.Logger;
import mc.server.survival.libraries.ChatLib;
import mc.server.survival.libraries.java.WrappedString;
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

public class Kick
implements CommandExecutor, TabCompleter
{
    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
    {
        final int length = args.length;

        if (sender instanceof Player)
        {
            final Player player = (Player) sender;

            final String rank = DataManager.getInstance().getLocal(player).getRank();

            if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("kick").isForAdministrators() ||
                    rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("kick").isForModerators() ||
                    rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("kick").isForPlayers())
            {
                player.kickPlayer(ChatLib.ColorUtil.formatHEX("\n#fc7474██&f█#fc7474██\n#fc7474██&f█#fc7474██\n#fc7474██&f█#fc7474██\n#fc7474█████\n#fc7474██&f█#fc7474██\n\n" +
                        "#f83044&lUTRACONO POLACZENIE\n#fc7474Zostales wyrzucony z serwera!\n\n#8c8c8cPowod: #fc7474Kto komu dolki kopie ten sam w nie wpada!\n#8c8c8cSprawca: #fc7474ANTI-TOXIC" +
                        "\n\n#666666&o(Nastepnym razem wymiar kary moze byc surowszy!)"));
                return true;
            }

            if (length == 0)
            {
                ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cWprowadz nazwe gracza, aby kontynuowac!");
                return true;
            }
            else
            {
                final String potencial_player = args[0];

                for (Player dplayer : Bukkit.getOnlinePlayers())
                    if (dplayer.getName().equalsIgnoreCase(potencial_player))
                    {
                        String kicker = player.getName();

                        if (length == 1)
                        {
                            dplayer.kickPlayer(ChatLib.ColorUtil.formatHEX("\n#fc7474██&f█#fc7474██\n#fc7474██&f█#fc7474██\n#fc7474██&f█#fc7474██\n#fc7474█████\n#fc7474██&f█#fc7474██\n\n" +
                                    "#f83044&lUTRACONO POLACZENIE\n#fc7474Zostales wyrzucony z serwera!\n\n#8c8c8cPowod: #fc7474Brak powodu.\n#8c8c8cSprawca: #fc7474" + kicker +
                                    "\n\n#666666&o(Nastepnym razem wymiar kary moze byc surowszy!)"));
                        }
                        else
                        {
                            StringBuilder message = new StringBuilder();

                            for (int word = 1; word < length; word++)
                                if (word + 1 >= length)
                                    message.append(args[word].toLowerCase());
                                else
                                    message.append(args[word].toLowerCase()).append(" ");

                            String cause = ChatLib.applyCorrection(String.valueOf(message));

                            if (length == 2 && args[1].equalsIgnoreCase("-konsola") |
                                    args[1].equalsIgnoreCase("-server") | args[1].equalsIgnoreCase("-serwer") |
                                    args[1].equalsIgnoreCase("-survival"))
                            {
                                kicker = "Serwer";
                                cause = "Serwer";
                            }

                            if (length == 2 & args[1].equalsIgnoreCase("-ukryj") || args[1].equalsIgnoreCase("-anonim"))
                            {
                                kicker = "Anonim";
                                cause = "Powod jest anonimowy!";
                            }

                            if (length == 2 & args[1].equalsIgnoreCase("-fake:Null"))
                            {
                                dplayer.kickPlayer("Internal Exception: java.lang.NullPointerException");
                                return true;
                            }

                            if (length == 2 & args[1].equalsIgnoreCase("-fake:Packets"))
                            {
                                dplayer.kickPlayer("You are sending too many packets!");
                                return true;
                            }

                            if (length == 2 & args[1].equalsIgnoreCase("-fake:TimedOut"))
                            {
                                dplayer.kickPlayer("Timed out");
                                return true;
                            }

                            dplayer.kickPlayer(ChatLib.ColorUtil.formatHEX("\n#fc7474██&f█#fc7474██\n#fc7474██&f█#fc7474██\n#fc7474██&f█#fc7474██\n#fc7474█████\n#fc7474██&f█#fc7474██\n\n" +
                                    "#f83044&lUTRACONO POLACZENIE\n#fc7474Zostales wyrzucony z serwera!\n\n#8c8c8cPowod: #fc7474" + cause + "\n#8c8c8cSprawca: #fc7474" + kicker +
                                    "\n\n#666666&o(Nastepnym razem wymiar kary moze byc surowszy!)"));
                        }

                        Broadcaster.broadcastMessages("       ", "#fc7474██&f█#fc7474██", "#fc7474██&f█#fc7474██                     &f&l<#fc7474&l!&f&l> #fc7474&lKICK &f&l<#fc7474&l!&f&l>",
                                "#fc7474██&f█#fc7474██" + WrappedString.wrapString("#8c8c8cGracz #fc7474" + dplayer.getName() + " #8c8c8czostal wyjebany!").center(3, 0 ,50), "#fc7474█████" + WrappedString.wrapString(" &cWymiar pokuty nalozyl " + kicker + "!").center(0, 1,50).getString(), "#fc7474██&f█#fc7474██", "");
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
                Logger.log("Wprowadz nazwe gracza, aby kontynuowac!");
                return true;
            }
            else
            {
                final String potencial_player = args[0];

                for (Player dplayer : Bukkit.getOnlinePlayers())
                    if (dplayer.getName().equalsIgnoreCase(potencial_player))
                    {
                        String kicker = "Server";

                        if (length == 1)
                        {
                            dplayer.kickPlayer(ChatLib.ColorUtil.formatHEX("\n#fc7474██&f█#fc7474██\n#fc7474██&f█#fc7474██\n#fc7474██&f█#fc7474██\n#fc7474█████\n#fc7474██&f█#fc7474██\n\n" +
                                    "#f83044&lUTRACONO POLACZENIE\n#fc7474Zostales wyrzucony z serwera!\n\n#8c8c8cPowod: #fc7474Brak powodu.\n#8c8c8cSprawca: #fc7474" + kicker +
                                    "\n\n#666666&o(Nastepnym razem wymiar kary moze byc surowszy!)"));
                        }
                        else
                        {
                            StringBuilder message = new StringBuilder();

                            for (int word = 1; word < length; word++)
                                if (word + 1 >= length)
                                    message.append(args[word].toLowerCase());
                                else
                                    message.append(args[word].toLowerCase()).append(" ");

                            String cause = ChatLib.applyCorrection(String.valueOf(message));

                            if (length == 2 && args[1].equalsIgnoreCase("-konsola") |
                                    args[1].equalsIgnoreCase("-server") | args[1].equalsIgnoreCase("-serwer") |
                                    args[1].equalsIgnoreCase("-survival"))
                            {
                                kicker = "Serwer";
                                cause = "Serwer";
                            }

                            if (length == 2 & args[1].equalsIgnoreCase("-ukryj"))
                            {
                                kicker = "Anonim";
                                cause = "Powod jest anonimowy!";
                            }

                            if (length == 2 & args[1].equalsIgnoreCase("-fake:Null"))
                            {
                                dplayer.kickPlayer("Internal Exception: java.lang.NullPointerException");
                                return true;
                            }

                            if (length == 2 & args[1].equalsIgnoreCase("-fake:Packets"))
                            {
                                dplayer.kickPlayer("You are sending too many packets!");
                                return true;
                            }

                            if (length == 2 & args[1].equalsIgnoreCase("-fake:TimedOut"))
                            {
                                dplayer.kickPlayer("Timed out");
                                return true;
                            }

                            dplayer.kickPlayer(ChatLib.ColorUtil.formatHEX("\n#fc7474██&f█#fc7474██\n#fc7474██&f█#fc7474██\n#fc7474██&f█#fc7474██\n#fc7474█████\n#fc7474██&f█#fc7474██\n\n" +
                                    "#f83044&lUTRACONO POLACZENIE\n#fc7474Zostales wyrzucony z serwera!\n\n#8c8c8cPowod: #fc7474" + cause + "\n#8c8c8cSprawca: #fc7474" + kicker +
                                    "\n\n#666666&o(Nastepnym razem wymiar kary moze byc surowszy!)"));
                        }

                        Broadcaster.broadcastMessages("       ", "#fc7474██&f█#fc7474██", "#fc7474██&f█#fc7474██                     &f&l<#fc7474&l!&f&l> #fc7474&lKICK &f&l<#fc7474&l!&f&l>",
                                "#fc7474██&f█#fc7474██" + WrappedString.wrapString("#8c8c8cGracz #fc7474" + dplayer.getName() + " #8c8c8czostal wyjebany!").center(3, 0, 50).getString(), "#fc7474█████" + WrappedString.wrapString(" &cWymiar pokuty nalozyl " + kicker + "!").center(0, 1, 50).getString(), "#fc7474██&f█#fc7474██", "");
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
            completions.add("-anonim");
            completions.add("-konsola");
            completions.add("-fake:null");
            completions.add("-fake:packets");
            completions.add("-fake:timedout");
        }

        if (args.length == 1)
            Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));

        return completions;
    }
}