package mc.server.survival.commands;

import mc.server.Logger;
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

public class Weather
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
                ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cWprowadz typ pogody! Wzor komendy: #ffc936/pogoda <pogoda>");
            else
            {
                final String potencialWeather = args[0];

                if (potencialWeather.equalsIgnoreCase("slonce") ||
                        potencialWeather.equalsIgnoreCase("clear") ||
                        potencialWeather.equalsIgnoreCase("reset") ||
                        potencialWeather.equalsIgnoreCase("brak"))
                {
                    WorldHandler.getWorld("survival").setThundering(false);
                    WorldHandler.getWorld("survival").setStorm(false);
                }
                else if (potencialWeather.equalsIgnoreCase("deszcz") ||
                        potencialWeather.equalsIgnoreCase("rain"))
                {
                    WorldHandler.getWorld("survival").setThundering(true);
                    WorldHandler.getWorld("survival").setStorm(false);
                }
                else if (potencialWeather.equalsIgnoreCase("burza") ||
                        potencialWeather.equalsIgnoreCase("storm") ||
                        potencialWeather.equalsIgnoreCase("sztorm") ||
                        potencialWeather.equalsIgnoreCase("thunder"))
                {
                    WorldHandler.getWorld("survival").setThundering(true);
                    WorldHandler.getWorld("survival").setStorm(true);
                }
                else
                {
                    ChatManager.sendNotification(player, "Co ty pierdolisz!", ChatManager.NotificationType.ERROR);
                    return true;
                }

                WorldHandler.getWorld("survival").setWeatherDuration(20 * 60 * 20);
                ChatManager.sendNotification(player, "Pogoda zostala zmieniona na " + args[0].toUpperCase() + "!", ChatManager.NotificationType.SUCCESS);
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
            completions.add("slonce");
            completions.add("deszcz");
            completions.add("burza");
        }

        return completions;
    }
}
