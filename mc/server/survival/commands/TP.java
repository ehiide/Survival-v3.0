package mc.server.survival.commands;

import mc.server.Logger;
import mc.server.survival.libraries.*;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import mc.server.survival.managers.FileManager;
import mc.server.survival.worlds.WorldHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TP
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

            if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("tp").isForAdministrators() ||
                    rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("tp").isForModerators() ||
                    rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("tp").isForPlayers())
            {
                ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
                return true;
            }

            if (length == 0)
                ChatManager.sendNotification(player, "Ok... ale gdzie? Wzor komendy: #ffc936/tp <gracz/xyz/swiat> [gracz]", ChatManager.NotificationType.ERROR);
            else
            {
                if (length == 1)
                {
                    final String potencial_player = args[0];

                    for (Player dplayer : Bukkit.getOnlinePlayers())
                        if (dplayer.getName().equalsIgnoreCase(potencial_player))
                        {
                            ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cZostales przeteleportowany do gracza " + ChatLib.returnPlayerColor(dplayer) + dplayer.getName() + "!");
                            player.teleport(dplayer);
                            return true;
                        }

                    if (potencial_player.equalsIgnoreCase("survival") || potencial_player.equalsIgnoreCase("overworld"))
                    {
                        final World world = WorldHandler.getWorld("survival");
                        final int x = player.getLocation().getBlockX();
                        final int z = player.getLocation().getBlockZ();
                        final int y = WorldHandler.getHighestBlockAt(world, x, z).getLocation().getBlockY();

                        player.teleport(new Location(world, x, y, z));

                        TaskLib.getInstance().runSyncLater(() -> {
                            ScoreboardLib.getInstance().reloadContents(player);
                            VisualLib.showServerChangeTitle(player);
                        }, 20);

                        ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cZostales przeteleportowany do glownego swiata!");
                        return true;
                    }
                    else if (potencial_player.equalsIgnoreCase("survival_nether") || potencial_player.equalsIgnoreCase("nether"))
                    {
                        final World world = WorldHandler.getWorld("survival_nether");
                        final int x = player.getLocation().getBlockX();
                        final int z = player.getLocation().getBlockZ();
                        final int y = 64;

                        player.teleport(new Location(world, x, y, z));

                        TaskLib.getInstance().runSyncLater(() -> {
                            ScoreboardLib.getInstance().reloadContents(player);
                            VisualLib.showServerChangeTitle(player);
                        }, 20);

                        ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cZostales przeteleportowany do swiata netheru!");
                        return true;
                    }
                    else if (potencial_player.equalsIgnoreCase("survival_the_end") || potencial_player.equalsIgnoreCase("end"))
                    {
                        final World world = WorldHandler.getWorld("survival_the_end");
                        final int x = player.getLocation().getBlockX();
                        final int z = player.getLocation().getBlockZ();
                        final int y = 64;

                        player.teleport(new Location(world, x, y, z));

                        TaskLib.getInstance().runSyncLater(() -> {
                            ScoreboardLib.getInstance().reloadContents(player);
                            VisualLib.showServerChangeTitle(player);
                        }, 20);

                        ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cZostales przeteleportowany do swiata endu!");
                        return true;
                    }
                    else if (potencial_player.equalsIgnoreCase("survival_aether") || potencial_player.equalsIgnoreCase("aether"))
                    {
                        final World world = WorldHandler.getWorld("survival_aether");
                        final int x = player.getLocation().getBlockX();
                        final int z = player.getLocation().getBlockZ();
                        final int y = 216;

                        player.teleport(new Location(world, x, y, z));

                        TaskLib.getInstance().runSyncLater(() -> {
                            ScoreboardLib.getInstance().reloadContents(player);
                            VisualLib.showServerChangeTitle(player);
                        }, 20);

                        ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cZostales przeteleportowany do swiata nieba!");
                        return true;
                    }
                    else if (potencial_player.equalsIgnoreCase("survival_twilight") || potencial_player.equalsIgnoreCase("twilight"))
                    {
                        final World world = WorldHandler.getWorld("survival_twilight");
                        final int x = player.getLocation().getBlockX();
                        final int z = player.getLocation().getBlockZ();
                        final int y = WorldHandler.getHighestBlockAt(world, x, z).getLocation().getBlockY();

                        player.teleport(new Location(world, x, y, z));

                        TaskLib.getInstance().runSyncLater(() -> {
                            ScoreboardLib.getInstance().reloadContents(player);
                            VisualLib.showServerChangeTitle(player);
                        }, 20);

                        ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cZostales przeteleportowany do swiata twilight!");
                        return true;
                    }

                    if (MathLib.isInteger(potencial_player))
                    {
                        ChatManager.sendNotification(player, "Gosciu, Ty zyjesz w jednym wymiarze? Podaj mi no wiecej koordynatow!", ChatManager.NotificationType.ERROR);
                        return true;
                    }

                    ChatManager.sendNotification(player, "Podany gracz nie jest on-line na serwerze!", ChatManager.NotificationType.ERROR);
                }
                else if (length == 2)
                {
                    final String potencial_player = args[0];

                    for (Player dplayer : Bukkit.getOnlinePlayers())
                        if (dplayer.getName().equalsIgnoreCase(potencial_player))
                        {
                            final String potencial_player2 = args[1];

                            for (Player dplayer2 : Bukkit.getOnlinePlayers())
                                if (dplayer2.getName().equalsIgnoreCase(potencial_player2))
                                {
                                    ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cGracz " + ChatLib.returnPlayerColor(dplayer) + dplayer.getName() + "#8c8c8c zostal przeteleportowany do gracza " + ChatLib.returnPlayerColor(dplayer2) + dplayer2.getName() + "!");
                                    dplayer.teleport(dplayer2);
                                    return true;
                                }
                        }

                    if (MathLib.isInteger(args[0]) && MathLib.isInteger(args[1]))
                    {
                        ChatManager.sendNotification(player, "Gosciu, Ty zyjesz w dwuwymiarze? Podaj mi no wiecej koordynatow!", ChatManager.NotificationType.ERROR);
                        return true;
                    }

                    ChatManager.sendNotification(player, "Jeden z podanych graczy nie jest on-line na serwerze!", ChatManager.NotificationType.ERROR);
                }
                else if (length == 3)
                {
                    if (MathLib.isInteger(args[0]) && MathLib.isInteger(args[1]) && MathLib.isInteger(args[2]))
                    {
                        ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cZostales przeteleportowany na wybrane koordynaty!");
                        player.teleport(new Location(player.getWorld(), Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2])));
                        return true;
                    }

                    ChatManager.sendNotification(player, "Podaj mi koordynaty, a nie jakies farmazony!", ChatManager.NotificationType.ERROR);
                }
                else
                    ChatManager.sendNotification(player, "Gdzie kurwa! Wzor komendy: #ffc936/tp <gracz/xyz/swiat> [gracz]", ChatManager.NotificationType.ERROR);
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
            completions.add("survival");
            completions.add("nether");
            completions.add("end");
            completions.add("aether");
            completions.add("twilight");
        }

        if (args.length == 1 || args.length == 2)
            Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));

        return completions;
    }
}