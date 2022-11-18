package mc.server.survival.commands;

import mc.server.Logger;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RAM
implements CommandExecutor, TabCompleter
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
    {
        if (sender instanceof Player)
        {
            final Player player = (Player) sender;

            final String rank = DataManager.getInstance().getLocal(player).getRank();

            if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("ram").isForAdministrators() ||
                    rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("ram").isForModerators() ||
                    rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("ram").isForPlayers())
            {
                ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
                return true;
            }

            if (args.length == 0)
            {
                final long freeMem = Runtime.getRuntime().freeMemory() / 1048576;
                final long maxMem = Runtime.getRuntime().maxMemory() / 1048576;

                ChatManager.sendMessages(player, "", "&0█████&8 &7", "&0█&f█&0███   &0* #8c8c8cPanel wydajnosciowy serwera",
                        "&0██&f█&0██   &0* #8c8c8cDostepna pamiec RAM: " + freeMem + "MB", "&0█&f█&0███   &0* #8c8c8cLimit pamieci RAM:  " + maxMem + "MB", "&0█████", "");
            }
            else
                ChatManager.sendNotification(player, "Umiesz pisac?! Wystarczy napisac #ffc936/ram!", ChatManager.NotificationType.ERROR);

            return true;
        }
        else if (sender instanceof ConsoleCommandSender)
            Logger.log("&cNaprawde nie masz tego w panelu?!");

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args)
    {
        return null;
    }
}