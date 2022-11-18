package mc.server.survival.commands;

import mc.server.Logger;
import mc.server.survival.libraries.InventoryLib;
import mc.server.survival.libraries.TaskLib;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Schowek
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

			if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("schowek").isForAdministrators() ||
					rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("schowek").isForModerators() ||
					rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("schowek").isForPlayers())
			{
				ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
				return true;
			}
			
			if (length == 0)
			{
				InventoryLib.createSchowek(player);
				return true;
			}
			else if (length == 1 && args[0].equalsIgnoreCase("ulepsz") | args[0].equalsIgnoreCase("ulepszenie") | args[0].equalsIgnoreCase("upgrade"))
			{
				ChatManager.sendNotification(player, "Trwa otwieranie menu ulepszen schowku... &e⌛", ChatManager.NotificationType.INFORMATION);
				TaskLib.getInstance().runSyncLater(() -> InventoryLib.createNewInventory(player, 27, ChatColor.translateAlternateColorCodes('&', "&c&lULEPSZENIE SCHOWKU"), "schowek"), 10);
				return true;
			}
			else
			{
				ChatManager.sendNotification(player, "Sluchaj no, " + player.getName() + "... poprawne wpisanie komendy to podstawa!", ChatManager.NotificationType.ERROR);
				return true;
			}
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
			completions.add("ulepsz");

		return completions;
	}
}