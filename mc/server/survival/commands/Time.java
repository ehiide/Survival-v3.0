package mc.server.survival.commands;

import mc.server.Logger;
import mc.server.survival.libraries.MathLib;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import mc.server.survival.managers.FileManager;
import mc.server.survival.worlds.WorldHandler;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Time
implements CommandExecutor, TabCompleter
{
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
                ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cWprowadz pore dnia! Wzor komendy: #ffc936/czas <czas>");
            else
            {
                long selectedTime;

                if (MathLib.isInteger(args[0]))
                {
                    final int potencialTime = Integer.parseInt(args[0]);

                    if (potencialTime < 0 || potencialTime > 24000)
                    {
                        ChatManager.sendNotification(player, "Podana jednostka czasu cos mi nie styka. Pamietaj, ze wartosc musi znajdowac sie w przedziale 0-24000!", ChatManager.NotificationType.ERROR);
                        return true;
                    }

                    selectedTime = potencialTime;
                }
                else
                {
                    final String potencialTime = args[0];

                    if (potencialTime.equalsIgnoreCase("wschod"))
                        selectedTime = 23000;
                    else if (potencialTime.equalsIgnoreCase("dzien"))
                        selectedTime = 1000;
                    else if (potencialTime.equalsIgnoreCase("poludnie"))
                        selectedTime = 6000;
                    else if (potencialTime.equalsIgnoreCase("zachod"))
                        selectedTime = 12000;
                    else if (potencialTime.equalsIgnoreCase("noc"))
                        selectedTime = 13000;
                    else if (potencialTime.equalsIgnoreCase("polnoc"))
                        selectedTime = 18000;
                    else
                    {
                        ChatManager.sendNotification(player, "Co ty pierdolisz!", ChatManager.NotificationType.ERROR);
                        return true;
                    }
                }

                WorldHandler.getWorld("survival").setTime(selectedTime);
                ChatManager.sendNotification(player, "Czas zostal zmieniony na " + args[0].toUpperCase() + "!", ChatManager.NotificationType.SUCCESS);
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
        {
            completions.add("wschod");
            completions.add("dzien");
            completions.add("poludnie");
            completions.add("zachod");
            completions.add("noc");
            completions.add("polnoc");
        }

        return completions;
    }
}