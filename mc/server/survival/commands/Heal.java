package mc.server.survival.commands;

import mc.server.Logger;
import mc.server.survival.libraries.ChatLib;
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

public class Heal
implements CommandExecutor, TabCompleter
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
    {
        if (sender instanceof Player)
        {
            final Player player = (Player) sender;

            final String rank = DataManager.getInstance().getLocal(player).getRank();

            if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("heal").isForAdministrators() ||
                    rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("heal").isForModerators() ||
                    rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("heal").isForPlayers())
            {
                ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
                return true;
            }

            if (args.length == 0)
            {
                player.setHealth(player.getMaxHealth());
                ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cTwoje zdrowie zostalo zregenerowane!");
            }
            else
            {
                final String potencial_player = args[0];

                for (Player dplayer : Bukkit.getOnlinePlayers())
                    if (dplayer.getName().equalsIgnoreCase(potencial_player))
                    {
                        dplayer.setHealth(dplayer.getMaxHealth());
                        ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cZregenerowano gracza " + ChatLib.returnPlayerColor(dplayer) + dplayer.getName() + "!");
                        return true;
                    }

                ChatManager.sendNotification(player, "Podany gracz nie jest on-line na serwerze!", ChatManager.NotificationType.ERROR);
            }

            return true;
        }
        else if (sender instanceof ConsoleCommandSender)
        {
            if (args.length == 0)
                Logger.log("&cChcesz zregenerowac konsole?!");
            else
            {
                final String potencial_player = args[0];

                for (Player dplayer : Bukkit.getOnlinePlayers())
                    if (dplayer.getName().equalsIgnoreCase(potencial_player))
                    {
                        dplayer.setHealth(dplayer.getMaxHealth());
                        Logger.log("Zregenerowano gracza " + ChatLib.returnDefinedPlayerColor(dplayer.getName()) + dplayer.getName() + "&7!");
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