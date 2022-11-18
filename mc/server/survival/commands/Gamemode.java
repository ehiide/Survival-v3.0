package mc.server.survival.commands;

import mc.server.Logger;
import mc.server.survival.libraries.ChatLib;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import mc.server.survival.managers.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Gamemode
implements CommandExecutor, TabCompleter
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
    {
        if (sender instanceof Player)
        {
            final Player player = (Player) sender;

            final String rank = DataManager.getInstance().getLocal(player).getRank();

            if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("gamemode").isForAdministrators() ||
                    rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("gamemode").isForModerators() ||
                    rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("gamemode").isForPlayers())
            {
                ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
                return true;
            }

            if (label.equalsIgnoreCase("gms") || label.equalsIgnoreCase("gma") ||
                    label.equalsIgnoreCase("gmc") || label.equalsIgnoreCase("gmsp"))
            {
                final GameMode pickedGamemode;

                if (label.equalsIgnoreCase("gms"))
                    pickedGamemode = GameMode.SURVIVAL;
                else if (label.equalsIgnoreCase("gmc"))
                    pickedGamemode = GameMode.CREATIVE;
                else if (label.equalsIgnoreCase("gma"))
                    pickedGamemode = GameMode.ADVENTURE;
                else
                    pickedGamemode = GameMode.SPECTATOR;

                if (args.length == 0)
                {
                    player.setGameMode(pickedGamemode);
                    ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cTwoj tryb gry zostal zmieniony na: " + player.getGameMode() + "!");
                }
                else
                {
                    if (args.length > 1)
                    {
                        ChatManager.sendNotification(player, "Co?! Wzor komendy: #ffc936/gamemode <tryb gry> [gracz]!", ChatManager.NotificationType.ERROR);
                        return true;
                    }

                    final String potencial_player = args[0];

                    for (Player dplayer : Bukkit.getOnlinePlayers())
                        if (dplayer.getName().equalsIgnoreCase(potencial_player))
                        {
                            dplayer.setGameMode(pickedGamemode);
                            ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cTryb gry gracza " + ChatLib.returnPlayerColor(dplayer) + dplayer.getName() + "#8c8c8c zostal zmieniony na: " + dplayer.getGameMode() + "!");
                            return true;
                        }

                    ChatManager.sendNotification(player, "Podany gracz nie jest on-line na serwerze!", ChatManager.NotificationType.ERROR);
                    return true;
                }

                return true;
            }

            if (args.length == 0)
                ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cCzego tylko dusza zapragnie. Wzor komendy: #ffc936/gamemode <tryb gry> [gracz]!");
            else
            {
                if (args.length > 2)
                {
                    ChatManager.sendNotification(player, "Co?! Wzor komendy: #ffc936/gamemode <tryb gry> [gracz]!", ChatManager.NotificationType.ERROR);
                    return true;
                }

                final String gamemode = args[0];
                final GameMode pickedGamemode;

                if (gamemode.equalsIgnoreCase("0") || gamemode.equalsIgnoreCase("survival") || gamemode.equalsIgnoreCase("s"))
                    pickedGamemode = GameMode.SURVIVAL;
                else if (gamemode.equalsIgnoreCase("1") || gamemode.equalsIgnoreCase("creative") || gamemode.equalsIgnoreCase("c"))
                    pickedGamemode = GameMode.CREATIVE;
                else if (gamemode.equalsIgnoreCase("2") || gamemode.equalsIgnoreCase("adventure") || gamemode.equalsIgnoreCase("a"))
                    pickedGamemode = GameMode.ADVENTURE;
                else if (gamemode.equalsIgnoreCase("3") || gamemode.equalsIgnoreCase("spectator") || gamemode.equalsIgnoreCase("sp"))
                    pickedGamemode = GameMode.SPECTATOR;
                else
                {
                    ChatManager.sendNotification(player, "Taki tryb gry nie istnieje! Wzor komendy: #ffc936/gamemode <tryb gry> [gracz]!", ChatManager.NotificationType.ERROR);
                    return true;
                }

                if (args.length > 1)
                {
                    final String potencial_player = args[1];

                    for (Player dplayer : Bukkit.getOnlinePlayers())
                        if (dplayer.getName().equalsIgnoreCase(potencial_player))
                        {
                            dplayer.setGameMode(pickedGamemode);
                            ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cTryb gry gracza " + ChatLib.returnPlayerColor(dplayer) + dplayer.getName() + "#8c8c8c zostal zmieniony na: " + dplayer.getGameMode() + "!");
                            return true;
                        }

                    ChatManager.sendNotification(player, "Podany gracz nie jest on-line na serwerze!", ChatManager.NotificationType.ERROR);
                    return true;
                }

                player.setGameMode(pickedGamemode);
                ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cTwoj tryb gry zostal zmieniony na: " + player.getGameMode() + "!");
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

        if (alias.equalsIgnoreCase("gamemode") || alias.equalsIgnoreCase("gm") || alias.equalsIgnoreCase("tryb"))
        {
            if (args.length == 1)
            {
                completions.add("adventure");
                completions.add("creative");
                completions.add("spectator");
                completions.add("survival");
            }

            if (args.length == 2)
                Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));
        }
        else
            if (args.length == 1)
                Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));

        return completions;
    }
}