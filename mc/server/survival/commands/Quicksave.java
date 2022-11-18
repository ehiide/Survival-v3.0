package mc.server.survival.commands;

import mc.server.Logger;
import mc.server.survival.files.Main;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import mc.server.survival.managers.FileManager;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Quicksave
implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
    {
        if (sender instanceof Player)
        {
            final Player player = (Player) sender;

            final String rank = DataManager.getInstance().getLocal(player).getRank();

            if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("quicksave").isForAdministrators() ||
                    rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("quicksave").isForModerators() ||
                    rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("quicksave").isForPlayers())
            {
                ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
                return true;
            }

            if (args.length == 0)
            {
                final long time = System.currentTimeMillis();
                ChatManager.sendNotification(player, "Trwa zapisywanie, moze to chwile potrwac... &e⌛", ChatManager.NotificationType.INFORMATION);

                FileManager.getInstance().save(FileManager.FileType.PLAYERS);
                FileManager.getInstance().save(FileManager.FileType.GANGS);
                FileManager.getInstance().save(FileManager.FileType.WHITELIST);
                Main.getInstance().getServer().savePlayers();
                Main.getInstance().getServer().getWorlds().forEach(World::save);

                final long result = System.currentTimeMillis() - time;
                ChatManager.sendNotification(player, "Pomyslnie zapisano pliki, graczy i swiaty w " + result + "ms!", ChatManager.NotificationType.SUCCESS);
            }
            else
                ChatManager.sendNotification(player, "Umiesz pisac?! Wystarczy napisac #ffc936/quicksave!", ChatManager.NotificationType.ERROR);

            return true;
        }
        else if (sender instanceof ConsoleCommandSender)
        {
            if (args.length == 0)
            {
                final long time = System.currentTimeMillis();
                Logger.log("Trwa zapisywanie, moze to chwile potrwac... &e⌛");

                FileManager.getInstance().save(FileManager.FileType.PLAYERS);
                FileManager.getInstance().save(FileManager.FileType.GANGS);
                FileManager.getInstance().save(FileManager.FileType.WHITELIST);
                Main.getInstance().getServer().savePlayers();
                Main.getInstance().getServer().getWorlds().forEach(world -> world.save());

                final long result = System.currentTimeMillis() - time;
                Logger.log("&aPomyslnie zapisano pliki, graczy i swiaty w " + result + "ms!");
            }
            else
                Logger.log("&cUmiesz pisac?! Wystarczy napisac &6/quicksave!");
        }

        return true;
    }
}