package mc.server.survival.commands;

import mc.server.Logger;
import mc.server.survival.libraries.ChatLib;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import mc.server.survival.managers.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Flight
implements CommandExecutor, TabCompleter
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
    {
        if (sender instanceof Player)
        {
            final Player player = (Player) sender;

            final String rank = DataManager.getInstance().getLocal(player).getRank();

            if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("flight").isForAdministrators() ||
                    rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("flight").isForModerators() ||
                    rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("flight").isForPlayers())
            {
                ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
                return true;
            }

            if (args.length == 0)
            {
                final boolean flying = player.isFlying();

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_BOTTLE_THROW, flying ? -6 : 6, 3);
                player.setAllowFlight(!flying);
                player.setFlying(!flying);

                ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cOd teraz " + (flying ? "nie mozesz" : "mozesz") + " latac!");
            }
            else
            {
                if (args.length > 1)
                {
                    ChatManager.sendNotification(player, "Co?! Wzor komendy: #ffc936/latanie [gracz]!", ChatManager.NotificationType.ERROR);
                    return true;
                }

                final String potencial_player = args[0];

                for (Player dplayer : Bukkit.getOnlinePlayers())
                    if (dplayer.getName().equalsIgnoreCase(potencial_player))
                    {
                        final boolean flying = dplayer.isFlying();

                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_BOTTLE_THROW, flying ? -6 : 6, 3);
                        dplayer.playSound(dplayer.getLocation(), Sound.ENTITY_EXPERIENCE_BOTTLE_THROW, flying ? -6 : 6, 3);
                        dplayer.setAllowFlight(!flying);
                        dplayer.setFlying(!flying);

                        ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cOd teraz gracz " + ChatLib.returnPlayerColor(dplayer) + dplayer.getName() + "#8c8c8c" + (flying ? " nie moze" : " moze") + " latac!");
                        return true;
                    }

                ChatManager.sendNotification(player, "Podany gracz nie jest on-line na serwerze!", ChatManager.NotificationType.ERROR);
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
        ArrayList<String> completions = new ArrayList<>();

        if (sender instanceof ConsoleCommandSender)
            return completions;

        if (args.length == 1)
            Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));

        return completions;
    }
}