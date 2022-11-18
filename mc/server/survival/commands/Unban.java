package mc.server.survival.commands;

import mc.server.Broadcaster;
import mc.server.Logger;
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

import java.util.ArrayList;
import java.util.List;

public class Unban
implements CommandExecutor, TabCompleter
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            final int length = args.length;

            final String rank = DataManager.getInstance().getLocal(player).getRank();

            if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("unban").isForAdministrators() ||
                    rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("unban").isForModerators() ||
                    rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("unban").isForPlayers())
            {
                ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
                return true;
            }

            if (length == 0)
            {
                ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("general.prefixes.chat") + "#8c8c8cWprowadz nazwe gracza, ktorego chcesz odbanowac!");
                return true;
            }
            else
            {
                final String potencial_player = args[0];

                for (Player dplayer : Bukkit.getOnlinePlayers())
                    if (dplayer.getName().equalsIgnoreCase(potencial_player))
                    {
                        if (!(TimeLib.getDifferenceInSeconds(DataManager.getInstance().getLocal(dplayer).getBan(dplayer.getName())) < DataManager.getInstance().getLocal(dplayer).getBanLength(dplayer.getName())))
                        {
                            ChatManager.sendNotification(player, "Ten gracz nie jest nawet zbanowany, wiemy ze masz dobre serduszko ale bez przesady!", ChatManager.NotificationType.ERROR);
                            return true;
                        }

                        DataManager.getInstance().getLocal(dplayer).setBanLength(0);
                        Broadcaster.broadcastMessages("       ", "#80ff1f██&f█#80ff1f██", "#80ff1f██&f█#80ff1f██                     &f&l<#80ff1f&l!&f&l> #80ff1f&lUNBAN &f&l<#80ff1f&l!&f&l>",
                                "#80ff1f██&f█#80ff1f██" + WrappedString.wrapString("#8c8c8cGracz #80ff1f" + dplayer.getName() + " #8c8c8czostal wlasnie odbanowany!").center(3, 0 ,50).getString(), "#80ff1f█████" + WrappedString.wrapString(" &aOznacza to, ze moze na nowo cieszyc sie gra!").center(0, 1 ,50).getString(), "#80ff1f██&f█#80ff1f██", "");
                        return true;
                    }
                ChatManager.sendNotification(player, "Podany gracz nie jest on-line na serwerze!", ChatManager.NotificationType.ERROR);
            }

            return true;
        }
        else if (sender instanceof ConsoleCommandSender)
        {
            final int length = args.length;

            if (length == 0)
            {
                Logger.log("Wprowadz nazwe gracza, ktorego chcesz odbanowac!");
                return true;
            }
            else
            {
                final String potencial_player = args[0];

                for (Player dplayer : Bukkit.getOnlinePlayers())
                    if (dplayer.getName().equalsIgnoreCase(potencial_player))
                    {
                        if (!(TimeLib.getDifferenceInSeconds(DataManager.getInstance().getLocal(dplayer).getBan(dplayer.getName())) < DataManager.getInstance().getLocal(dplayer).getBanLength(dplayer.getName())))
                        {
                            Logger.log("&cTen gracz nie jest nawet zbanowany, wiemy ze masz dobre serduszko ale bez przesady!");
                            return true;
                        }

                        DataManager.getInstance().getLocal(dplayer).setBanLength(0);
                        Broadcaster.broadcastMessages("       ", "#80ff1f██&f█#80ff1f██", "#80ff1f██&f█#80ff1f██                     &f&l<#80ff1f&l!&f&l> #80ff1f&lUNBAN &f&l<#80ff1f&l!&f&l>",
                                "#80ff1f██&f█#80ff1f██" + WrappedString.wrapString("#8c8c8cGracz #80ff1f" + dplayer.getName() + " #8c8c8czostal wlasnie odbanowany!").center(3, 0 ,50).getString(), "#80ff1f█████" + WrappedString.wrapString(" &aOznacza to, ze moze na nowo cieszyc sie gra!").center(0, 1,50).getString(), "#80ff1f██&f█#80ff1f██", "");
                        return true;
                    }

                Logger.log("&cPodany gracz nie jest on-line na serwerze!");
            }

            return true;
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args)
    {
        ArrayList<String> completions = new ArrayList<>();

        if (args.length == 1)
            Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));

        return completions;
    }
}