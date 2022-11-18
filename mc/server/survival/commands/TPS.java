package mc.server.survival.commands;

import mc.server.Logger;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import net.minecraft.server.MinecraftServer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TPS
implements CommandExecutor, TabCompleter
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
    {
        if (sender instanceof Player)
        {
            final Player player = (Player) sender;

            final String rank = DataManager.getInstance().getLocal(player).getRank();

            if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("tps").isForAdministrators() ||
                    rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("tps").isForModerators() ||
                    rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("tps").isForPlayers())
            {
                ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
                return true;
            }

            if (args.length == 0)
            {
                final double[] tpss = MinecraftServer.getServer().recentTps;

                final String actualTPS = (Double.toString(tpss[0]).length() > 5) ? Double.toString(tpss[0]).substring(0, 5) : Double.toString(tpss[0]);
                final String recentTPS = (Double.toString(tpss[2]).length() > 5) ? Double.toString(tpss[2]).substring(0, 5) : Double.toString(tpss[2]);

                ChatManager.sendMessages(player, "", "&0█████&8 &6", "&0█&f█&0███   &0* #8c8c8cPanel wydajnosciowy serwera",
                        "&0██&f█&0██   &0* #8c8c8cAktualny TPS: " + actualTPS, "&0█&f█&0███   &0* #8c8c8cPrzecietny TPS:  " + recentTPS, "&0█████", "");
            }
            else
                ChatManager.sendNotification(player, "Umiesz pisac?! Wystarczy napisac #ffc936/tps!", ChatManager.NotificationType.ERROR);

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