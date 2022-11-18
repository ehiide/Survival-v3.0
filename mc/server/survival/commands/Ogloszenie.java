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

import java.util.List;

public class Ogloszenie
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

            if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("ogloszenie").isForAdministrators() ||
                    rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("ogloszenie").isForModerators() ||
                    rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("ogloszenie").isForPlayers())
            {
                ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
                return true;
            }

            if (length == 0)
                ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cWprowadz swoje ogloszenie! Wzor komendy: #ffc936/ogloszenie <wiadomosc>");
            else
            {
                StringBuilder message = new StringBuilder();

                for (int word = 0; word < length; word++)
                    if (word + 1 >= length)
                        message.append(args[word].toLowerCase());
                else
                    message.append(args[word].toLowerCase()).append(" ");

                Bukkit.getOnlinePlayers().forEach(onlinePlayer -> ChatManager.sendMessage(onlinePlayer, FileManager.getInstance().getConfigValue("global.survival.prefixes.broadcast") + "#8c8c8c" + ChatLib.applyCorrection(ChatLib.getPlaceholder(player, message.toString()))));
                Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 6, 6));
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
        return null;
    }
}