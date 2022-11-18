package mc.server.survival.commands;

import mc.server.Broadcaster;
import mc.server.Logger;
import mc.server.survival.libraries.ChatLib;
import mc.server.survival.libraries.TimeLib;
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
import java.util.Objects;

public class Powiedz
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

            if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("powiedz").isForAdministrators() ||
                    rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("powiedz").isForModerators() ||
                    rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("powiedz").isForPlayers())
            {
                Broadcaster.broadcastMessage(ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.getRankInChat(player) + ChatLib.getGangInChat(DataManager.getInstance().getLocal(player).getGang()) + ChatLib.returnMarryPrefix(player) +  "&r" + ChatLib.returnPlayerColor(player) + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c Debil ze mnie."));
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
                        if (length == 1)
                        {
                            ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cWprowadz swoja falszywa wiadomosc, ktora chcesz wyslac!");
                            return true;
                        }
                        else
                        {
                            StringBuilder message = new StringBuilder();

                            for (int word = 1; word < length; word++)
                                if (word + 1 >= length)
                                    message.append(args[word].toLowerCase());
                                else
                                    message.append(args[word].toLowerCase()).append(" ");

                            final String fakeMessage = ChatLib.getPlaceholder(dplayer, ChatLib.applyCorrection(message.toString()));
                            Broadcaster.broadcastMessage(ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.getRankInChat(dplayer) + ChatLib.getGangInChat(DataManager.getInstance().getLocal(dplayer).getGang()) + ChatLib.returnMarryPrefix(dplayer) +  "&r" + ChatLib.returnPlayerColor(dplayer) + Objects.requireNonNull(dplayer.getPlayer()).getName() + "#8c8c8c " + fakeMessage));
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
                        if (length == 1)
                        {
                            Logger.log("Wprowadz swoja falszywa wiadomosc, ktora chcesz wyslac!");
                            return true;
                        }
                        else
                        {
                            StringBuilder message = new StringBuilder();

                            for (int word = 1; word < length; word++)
                                if (word + 1 >= length)
                                    message.append(args[word].toLowerCase());
                                else
                                    message.append(args[word].toLowerCase()).append(" ");

                            final String fakeMessage = ChatLib.getPlaceholder(dplayer, ChatLib.applyCorrection(message.toString()));
                            Broadcaster.broadcastMessage(ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.getRankInChat(dplayer) + ChatLib.getGangInChat(DataManager.getInstance().getLocal(dplayer).getGang()) + ChatLib.returnMarryPrefix(dplayer) +  "&r" + ChatLib.returnPlayerColor(dplayer) + Objects.requireNonNull(dplayer.getPlayer()).getName() + "#8c8c8c " + fakeMessage));
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

        if (args.length == 1)
            Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));

        return completions;
    }
}