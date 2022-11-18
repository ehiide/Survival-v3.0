package mc.server.survival.commands;

import mc.server.Logger;
import mc.server.survival.libraries.ChatLib;
import mc.server.survival.libraries.TaskLib;
import mc.server.survival.libraries.chemistry.Chemistries;
import mc.server.survival.libraries.chemistry.ChemistryCore;
import mc.server.survival.libraries.chemistry.ChemistryDrug;
import mc.server.survival.libraries.chemistry.ChemistryItem;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import mc.server.survival.managers.FileManager;
import mc.server.survival.worlds.WorldHandler;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller
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

            if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("controller").isForAdministrators() ||
                    rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("controller").isForModerators() ||
                    rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("controller").isForPlayers())
            {
                ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
                return true;
            }

            if (length == 0)
                ChatManager.sendMessages(player, "",
                        "#f83044&m---------------------------------------",
                        "#fc7474/kontroler chemistry (daj/ustaw/reset) ... #8c8c8c- wewnetrzne komendy dodatku Chemistry",
                        "#fc7474/kontroler logger (reset) ... #8c8c8c- komendy do zarzadzania logami",
                        "#fc7474/kontroler schematica (stworz/wczytaj/usun) ... #8c8c8c- komendy ulatwiajace moderacje systemu schematow",
                        "#fc7474/kontroler reset (swiat) #8c8c8c- resetuje swiat (rudy, teren, struktury)",
                        "#fc7474/kontroler reload #8c8c8c- restartuje serwer",
                        "&8&o(argumenty wymagane - (), argumewnty opcjonalne - [])",
                        "#f83044&m---------------------------------------", "");
            else
            {
                if (args[0].equalsIgnoreCase("chemistry"))
                {
                    if (length == 1)
                        ChatManager.sendNotification(player, "Wprowadz dalsza czesc komendy! Wzor komendy: #ffc936/kontroler chemistry <ustaw/reset> [gracz] [S] [D] [N] [G]", ChatManager.NotificationType.INFORMATION);
                    else
                    {
                        if (!args[1].equalsIgnoreCase("daj") && !args[1].equalsIgnoreCase("ustaw") && !args[1].equalsIgnoreCase("reset"))
                        {
                            ChatManager.sendNotification(player, "No moze w przyszlosci taka funkcja bedzie, ale jak narazie to bieda!", ChatManager.NotificationType.ERROR);
                            return true;
                        }

                        if (length == 2)
                            ChatManager.sendNotification(player, "Wprowadz nazwe gracza! Wzor komendy: #ffc936/kontroler chemistry <daj/ustaw/reset> [gracz] [S] [D] [N] [G]", ChatManager.NotificationType.INFORMATION);

                        if (length > 2)
                        {
                            final String potencial_player = args[2];

                            for (Player dplayer : Bukkit.getOnlinePlayers())
                                if (dplayer.getName().equalsIgnoreCase(potencial_player))
                                {
                                    if (args[1].equalsIgnoreCase("reset"))
                                    {
                                        if (length > 3)
                                        {
                                            ChatManager.sendNotification(player, "Chyba cos Ci sie pojebalo!", ChatManager.NotificationType.ERROR);
                                            return true;
                                        }

                                        ChatManager.sendNotification(player, "Gracz " + ChatLib.returnPlayerColor(dplayer) + dplayer.getName() + "#80ff1f wytrzezwial!", ChatManager.NotificationType.SUCCESS);
                                    }
                                    else if (args[1].equalsIgnoreCase("ustaw"))
                                    {
                                        if (length != 7)
                                        {
                                            ChatManager.sendNotification(player, "Chyba cos Ci sie pojebalo!", ChatManager.NotificationType.ERROR);
                                            return true;
                                        }

                                        try
                                        {
                                            final int s = Integer.parseInt(args[3]);
                                            final int d = Integer.parseInt(args[4]);
                                            final int n = Integer.parseInt(args[5]);
                                            final int g = Integer.parseInt(args[6]);

                                            ChemistryCore.set(dplayer, s, d, n, g);
                                            ChatManager.sendNotification(player, "Wartosci chemiczne dla gracza " + ChatLib.returnPlayerColor(dplayer) + dplayer.getName() + "#80ff1f zostaly pomyslnie zmienione!", ChatManager.NotificationType.SUCCESS);
                                        }
                                        catch (NumberFormatException e)
                                        {
                                            ChatManager.sendNotification(player, "Wprowadz 4 wartosci, a nie jakies farmazony!", ChatManager.NotificationType.ERROR);
                                        }
                                    }
                                    else
                                    {
                                        if (length != 4)
                                        {
                                            ChatManager.sendNotification(player, "Chyba cos Ci sie pojebalo!", ChatManager.NotificationType.ERROR);
                                            return true;
                                        }

                                        final String chemicalName = args[3];
                                        final ChemistryItem chemical = Chemistries.getInstance().byName(chemicalName);

                                        if (chemical == null)
                                        {
                                            ChatManager.sendNotification(player, "Taki specjal jeszcze nie istnieje!", ChatManager.NotificationType.ERROR);
                                            return true;
                                        }

                                        player.getInventory().addItem(ChemistryDrug.getDrug(chemical));
                                    }

                                    return true;
                                }

                            ChatManager.sendNotification(player, "Podany gracz nie jest on-line na serwerze!", ChatManager.NotificationType.ERROR);
                        }
                    }

                    return true;
                }
                else if (args[0].equalsIgnoreCase("logger"))
                {
                    if (length == 1)
                        ChatManager.sendNotification(player, "Wprowadz dalsza czesc komendy! Wzor komendy: #ffc936/kontroler logger (reset) (plik)", ChatManager.NotificationType.INFORMATION);
                    else
                    {
                        if (!args[1].equalsIgnoreCase("reset"))
                        {
                            ChatManager.sendNotification(player, "No moze w przyszlosci taka funkcja bedzie, ale jak narazie to bieda!", ChatManager.NotificationType.ERROR);
                            return true;
                        }

                        if (length > 3)
                        {
                            ChatManager.sendNotification(player, "Chyba cos Ci sie pojebalo!", ChatManager.NotificationType.ERROR);
                            return true;
                        }

                        if (length == 2)
                            ChatManager.sendNotification(player, "Wprowadz nazwe pliku! Wzor komendy: #ffc936/kontroler logger (reset) (plik)", ChatManager.NotificationType.INFORMATION);

                        if (length == 3)
                        {
                            final String fileName = args[2];
                            final long startTime = System.currentTimeMillis();

                            if (fileName.equalsIgnoreCase("all"))
                            {
                                Logger.getInstance().resetLogs(Logger.StoreType.ADVANCEMENT);
                                Logger.getInstance().resetLogs(Logger.StoreType.CHAT);
                                Logger.getInstance().resetLogs(Logger.StoreType.COMMAND);
                                Logger.getInstance().resetLogs(Logger.StoreType.CONSUME);
                                Logger.getInstance().resetLogs(Logger.StoreType.CRAFT);
                                Logger.getInstance().resetLogs(Logger.StoreType.DEATH);
                                Logger.getInstance().resetLogs(Logger.StoreType.ENCHANT);
                                Logger.getInstance().resetLogs(Logger.StoreType.JOIN);
                                Logger.getInstance().resetLogs(Logger.StoreType.KILL);
                                Logger.getInstance().resetLogs(Logger.StoreType.LEAVE);
                                Logger.getInstance().resetLogs(Logger.StoreType.PORTAL);
                                Logger.getInstance().resetLogs(Logger.StoreType.SHOP);
                                Logger.getInstance().resetLogs(Logger.StoreType.SIGN);
                            }

                            if (fileName.equalsIgnoreCase("advancement"))
                                Logger.getInstance().resetLogs(Logger.StoreType.ADVANCEMENT);
                            else if (fileName.equalsIgnoreCase("chat"))
                                Logger.getInstance().resetLogs(Logger.StoreType.CHAT);
                            else if (fileName.equalsIgnoreCase("command"))
                                Logger.getInstance().resetLogs(Logger.StoreType.COMMAND);
                            else if (fileName.equalsIgnoreCase("consume"))
                                Logger.getInstance().resetLogs(Logger.StoreType.CONSUME);
                            else if (fileName.equalsIgnoreCase("craft"))
                                Logger.getInstance().resetLogs(Logger.StoreType.CRAFT);
                            else if (fileName.equalsIgnoreCase("death"))
                                Logger.getInstance().resetLogs(Logger.StoreType.DEATH);
                            else if (fileName.equalsIgnoreCase("enchant"))
                                Logger.getInstance().resetLogs(Logger.StoreType.ENCHANT);
                            else if (fileName.equalsIgnoreCase("join"))
                                Logger.getInstance().resetLogs(Logger.StoreType.JOIN);
                            else if (fileName.equalsIgnoreCase("kill"))
                                Logger.getInstance().resetLogs(Logger.StoreType.KILL);
                            else if (fileName.equalsIgnoreCase("leave"))
                                Logger.getInstance().resetLogs(Logger.StoreType.LEAVE);
                            else if (fileName.equalsIgnoreCase("portal"))
                                Logger.getInstance().resetLogs(Logger.StoreType.PORTAL);
                            else if (fileName.equalsIgnoreCase("shop"))
                                Logger.getInstance().resetLogs(Logger.StoreType.SHOP);
                            else if (fileName.equalsIgnoreCase("sign"))
                                Logger.getInstance().resetLogs(Logger.StoreType.SIGN);
                            else
                            {
                                ChatManager.sendNotification(player, "Chyba cos Ci sie pojebalo!", ChatManager.NotificationType.ERROR);
                                return true;
                            }

                            final long result = (System.currentTimeMillis() - startTime);
                            ChatManager.sendNotification(player, "Wybrane logi zostaly pomyslnie zresetowane w czasie " + result + "ms!", ChatManager.NotificationType.SUCCESS);
                            return true;
                        }
                    }
                }
                else if (args[0].equalsIgnoreCase("schematica"))
                {
                    if (length == 1)
                        ChatManager.sendNotification(player, "Wprowadz dalsza czesc komendy! Wzor komendy: #ffc936/kontroler schematica (stworz/wczytaj/usun) (plik) [lokalizacja]", ChatManager.NotificationType.INFORMATION);
                    else
                    {
                        if (!args[1].equalsIgnoreCase("stworz") && !args[1].equalsIgnoreCase("wczytaj") && !args[1].equalsIgnoreCase("usun"))
                        {
                            ChatManager.sendNotification(player, "No moze w przyszlosci taka funkcja bedzie, ale jak narazie to bieda!", ChatManager.NotificationType.ERROR);
                            return true;
                        }

                        if (args[1].equalsIgnoreCase("wczytaj") | args[1].equalsIgnoreCase("usun"))
                        {
                            if (length == 2)
                                ChatManager.sendNotification(player, "Wprowadz nazwe schematu! Wzor komendy: #ffc936/kontroler schematica (stworz/wczytaj/usun) (plik) [lokalizacja]", ChatManager.NotificationType.INFORMATION);

                            if (length == 3)
                            {
                                final String schematicName = args[2];

                                if (args[1].equalsIgnoreCase("wczytaj"))
                                {
                                    final boolean hasBeenLoaded = FileManager.getInstance().getSchematica().loadSchematic(schematicName, player.getLocation());

                                    if (hasBeenLoaded)
                                        ChatManager.sendNotification(player, "Wczytano plik schematyczny: &e" + schematicName.toUpperCase() + "!", ChatManager.NotificationType.SUCCESS);
                                    else
                                        ChatManager.sendNotification(player, "Chyba cos Ci sie pojebalo, ten schemat nie istnieje!", ChatManager.NotificationType.ERROR);
                                }
                                else
                                {
                                    final boolean hasBeenLoaded = FileManager.getInstance().getSchematica().removeSchematic(schematicName);

                                    if (hasBeenLoaded)
                                        ChatManager.sendNotification(player, "Usunieto plik schematyczny: &e" + schematicName.toUpperCase() + "!", ChatManager.NotificationType.SUCCESS);
                                    else
                                        ChatManager.sendNotification(player, "Chyba cos Ci sie pojebalo, ten schemat nie istnieje!", ChatManager.NotificationType.ERROR);
                                }
                            }

                            if (length > 3)
                                ChatManager.sendNotification(player, "Chyba cos Ci sie pojebalo!", ChatManager.NotificationType.ERROR);

                            return true;
                        }
                        else if (args[1].equalsIgnoreCase("stworz"))
                        {
                            if (length == 2)
                            {
                                ChatManager.sendNotification(player, "Wprowadz nazwe pliku! Wzor komendy: #ffc936/kontroler schematica (stworz/wczytaj/usun) (plik) [lokalizacja]", ChatManager.NotificationType.INFORMATION);
                                return true;
                            }

                            if (length == 3)
                            {
                                ChatManager.sendNotification(player, "Wprowadz koordynaty lokalizacji! Wzor komendy: #ffc936/kontroler schematica (stworz/wczytaj/usun) (plik) [lokalizacja]", ChatManager.NotificationType.INFORMATION);
                                return true;
                            }

                            if (length != 9)
                            {
                                ChatManager.sendNotification(player, "Chyba cos Ci sie pojebalo!", ChatManager.NotificationType.ERROR);
                                return true;
                            }

                            final String schematicName = args[2];

                            final World world = player.getWorld();
                            List<Block> blocks = new ArrayList<>();

                            try
                            {
                                final int x1 = Integer.parseInt(args[3]);
                                final int y1 = Integer.parseInt(args[4]);
                                final int z1 = Integer.parseInt(args[5]);

                                final int x2 = Integer.parseInt(args[6]);
                                final int y2 = Integer.parseInt(args[7]);
                                final int z2 = Integer.parseInt(args[8]);

                                final int lowestX = Math.min(x1, x2);
                                final int lowestY = Math.min(y1, y2);
                                final int lowestZ = Math.min(z1, z2);

                                final int highestX = lowestX == x1 ? x2 : x1;
                                final int highestY = lowestY == y1 ? y2 : y1;
                                final int highestZ = lowestZ == z1 ? z2 : z1;

                                for (int x = lowestX; x <= highestX; x++)
                                    for (int y = lowestY; y <= highestY; y++)
                                        for (int z = lowestZ; z <= highestZ; z++)
                                            blocks.add(world.getBlockAt(x, y, z));

                                FileManager.getInstance().getSchematica().createSchematic(schematicName, blocks, player.getLocation());
                                ChatManager.sendNotification(player, "Stworzono plik schematyczny: &e" + schematicName.toUpperCase() + "!", ChatManager.NotificationType.SUCCESS);
                            }
                            catch (NumberFormatException e)
                            {
                                ChatManager.sendNotification(player, "Wprowadz koordynaty, a nie jakies farmazony!", ChatManager.NotificationType.ERROR);
                            }

                            return true;
                        }

                        ChatManager.sendNotification(player, "Chyba cos Ci sie pojebalo!", ChatManager.NotificationType.ERROR);
                    }

                    return true;
                }
                else if (args[0].equalsIgnoreCase("reload"))
                {
                    if (length == 1)
                    {
                        ChatManager.sendNotification(player, "Trwa restartowanie serwera...", ChatManager.NotificationType.INFORMATION);
                        Bukkit.reload();
                        ChatManager.sendNotification(player, "Serwer zostal zrestartowany!", ChatManager.NotificationType.SUCCESS);
                    }
                    else
                        ChatManager.sendNotification(player, "Chyba cos Ci sie pojebalo!", ChatManager.NotificationType.ERROR);
                }
                else if (args[0].equalsIgnoreCase("reset"))
                {
                    if (length != 2)
                    {
                        ChatManager.sendNotification(player, "Chyba cos Ci sie pojebalo!", ChatManager.NotificationType.ERROR);
                        return true;
                    }

                    final String world = args[1].toLowerCase();
                    final String[] worlds = {"survival", "survival_nether", "survival_aether", "survival_the_end", "survival_twilight"};

                    for (final String w : worlds)
                        if (w.equalsIgnoreCase(world))
                        {
                            final World selectedWorld = WorldHandler.getWorld(w);

                            if (selectedWorld.getPlayers().size() != 0)
                            {
                                ChatManager.sendNotification(player, "W wybranym swiecie znajduja sie gracze, moze lepiej byloby przeniesc ich gdzies indziej?", ChatManager.NotificationType.ERROR);
                                return true;
                            }

                            ChatManager.sendNotification(player, "Trwa resetowanie swiata...", ChatManager.NotificationType.INFORMATION);

                            for (final Player onlinePlayer : selectedWorld.getPlayers())
                                onlinePlayer.kickPlayer("Ta wiadomosc nie powinna nigdy pojawic sie zadnemu graczowi, zglos to administracji.");

                            for (final Chunk chunk : selectedWorld.getLoadedChunks())
                                chunk.unload();

                            try
                            {
                                FileUtils.deleteDirectory(selectedWorld.getWorldFolder());
                                TaskLib.getInstance().runSyncLater(() -> {
                                    WorldHandler.getInstance().loadWorld(w);
                                    ChatManager.sendNotification(player, "Wybrany swiat zostal zresetowany!", ChatManager.NotificationType.SUCCESS);
                                }, 20);
                                return true;
                            }
                            catch (IOException e)
                            {
                                ChatManager.sendNotification(player, "Wystapil nieoczekiwany problem! Wybrany swiat nie istnieje lub mogl juz zostac usuniety.", ChatManager.NotificationType.ERROR);
                                return true;
                            }
                        }

                    ChatManager.sendNotification(player, "Taki swiat nie istnieje!", ChatManager.NotificationType.ERROR);
                }
                else
                    ChatManager.sendNotification(player, "No co Ty! Kontroler nie ma az tylu przyciskow!", ChatManager.NotificationType.ERROR);
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
            completions.add("chemistry");
            completions.add("logger");
            completions.add("schematica");
            completions.add("reset");
            completions.add("reload");
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("chemistry"))
        {
            completions.add("daj");
            completions.add("ustaw");
            completions.add("reset");
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("chemistry"))
            Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));

        if (args.length == 4 | args.length == 5 | args.length == 6 | args.length == 7 && args[0].equalsIgnoreCase("chemistry") && args[1].equalsIgnoreCase("ustaw"))
        {
            completions.add("0");
            completions.add("25");
            completions.add("50");
            completions.add("75");
            completions.add("100");
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("logger"))
            completions.add("reset");

        if (args.length == 3 && args[0].equalsIgnoreCase("logger") && args[1].equalsIgnoreCase("reset"))
        {
            completions.add("all");
            completions.add("advancement");
            completions.add("chat");
            completions.add("command");
            completions.add("consume");
            completions.add("craft");
            completions.add("death");
            completions.add("enchant");
            completions.add("join");
            completions.add("kill");
            completions.add("leave");
            completions.add("portal");
            completions.add("shop");
            completions.add("sign");
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("schematica"))
        {
            completions.add("stworz");
            completions.add("wczytaj");
            completions.add("usun");
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("reset"))
        {
            completions.add("survival");
            completions.add("survival_aether");
            completions.add("survival_nether");
            completions.add("survival_the_end");
            completions.add("survival_twilight");
        }

        if (sender instanceof Player)
        {
            final Player player = (Player) sender;

            if (args.length == 4 | args.length == 7 && args[0].equalsIgnoreCase("schematica") && args[1].equalsIgnoreCase("stworz"))
                completions.add(String.valueOf(player.getLocation().getBlockX()));

            if (args.length == 5 | args.length == 8 && args[0].equalsIgnoreCase("schematica") && args[1].equalsIgnoreCase("stworz"))
                completions.add(String.valueOf(player.getLocation().getBlockY()));

            if (args.length == 6 | args.length == 9 && args[0].equalsIgnoreCase("schematica") && args[1].equalsIgnoreCase("stworz"))
                completions.add(String.valueOf(player.getLocation().getBlockZ()));
        }

        return completions;
    }
}
