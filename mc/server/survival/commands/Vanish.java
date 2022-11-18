package mc.server.survival.commands;

import mc.server.Logger;
import mc.server.survival.files.Main;
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

public class Vanish
implements CommandExecutor, TabCompleter
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
    {
        if (sender instanceof Player)
        {
            final Player player = (Player) sender;

            final String rank = DataManager.getInstance().getLocal(player).getRank();

            if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("vanish").isForAdministrators() ||
                    rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("vanish").isForModerators() ||
                    rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("vanish").isForPlayers())
            {
                ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
                return true;
            }

            if (args.length == 0)
            {
                final boolean visibility = DataManager.getInstance().getLocal(player).getVisibility();

                player.playSound(player.getLocation(), Sound.ITEM_TRIDENT_THROW, visibility ? -6 : 6, 3);
                DataManager.getInstance().getLocal(player).setVisibility(!visibility);

                if (visibility) Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.hidePlayer(Main.getInstance(), player));
                else Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.showPlayer(Main.getInstance(), player));

                ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cOd teraz jestes " + (visibility ? "niewidoczny" : "widoczny") + "!");
            }
            else
            {
                final String potencial_player = args[0];

                for (Player dplayer : Bukkit.getOnlinePlayers())
                    if (dplayer.getName().equalsIgnoreCase(potencial_player))
                    {
                        final boolean visibility = DataManager.getInstance().getLocal(dplayer).getVisibility();

                        player.playSound(player.getLocation(), Sound.ITEM_TRIDENT_THROW, visibility ? -6 : 6, 3);
                        dplayer.playSound(dplayer.getLocation(), Sound.ITEM_TRIDENT_THROW, visibility ? -6 : 6, 3);

                        DataManager.getInstance().getLocal(dplayer).setVisibility(!visibility);

                        if (visibility) Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.hidePlayer(Main.getInstance(), dplayer));
                        else Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.showPlayer(Main.getInstance(), dplayer));

                        ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cOd teraz gracz " + ChatLib.returnPlayerColor(dplayer) + dplayer.getName() + "#8c8c8c jest " + (visibility ? "niewidoczny" : "widoczny") + "!");
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