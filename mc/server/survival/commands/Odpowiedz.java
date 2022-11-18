package mc.server.survival.commands;

import mc.server.Logger;
import mc.server.survival.libraries.ChatLib;
import mc.server.survival.libraries.VisualLib;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import mc.server.survival.managers.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Odpowiedz
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

            if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("odpowiedz").isForAdministrators() ||
                    rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("odpowiedz").isForModerators() ||
                    rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("odpowiedz").isForPlayers())
            {
                ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
                return true;
            }

            if (Wiadomosc.getReply(player) == null)
            {
                ChatManager.sendNotification(player, "Nie masz do kogo odpowiedziec!", ChatManager.NotificationType.ERROR);
                return true;
            }

            if (length == 0)
            {
                ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cWprowadz swoja wiadomosc! Wzor komendy: #ffc936/odpowiedz <wiadomosc>");
                return true;
            }
            else
            {
                final String potencial_player = Wiadomosc.getReply(player).getName();

                for (Player dplayer : Bukkit.getOnlinePlayers())
                    if (dplayer.getName().equalsIgnoreCase(potencial_player))
                    {
                        StringBuilder message = new StringBuilder();

                        for (int word = 0; word < length; word++)
                            if (word + 1 >= length)
                                message.append(args[word].toLowerCase());
                            else
                                message.append(args[word].toLowerCase()).append(" ");

                        ChatManager.sendMessage(player, "#f83044&l» &cDo " + ChatLib.returnPlayerColor(dplayer) + dplayer.getName() + ": &r&7" + ChatLib.applyCorrection(ChatLib.getPlaceholder(player, message.toString())));
                        ChatManager.sendMessage(dplayer, "#f83044&l» &cWiadomosc od " + ChatLib.returnPlayerColor(player) + player.getName() + ": &r&7" + ChatLib.applyCorrection(ChatLib.getPlaceholder(player, message.toString())));
                        VisualLib.showDelayedTitle(dplayer, "&7od: " + player.getName(), "#faff26✉", 0, 10, 10);

                        Wiadomosc.setReply(player, dplayer);
                        Wiadomosc.setReply(dplayer, player);

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