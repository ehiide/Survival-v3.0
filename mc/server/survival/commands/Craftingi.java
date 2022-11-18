package mc.server.survival.commands;

import mc.server.Logger;
import mc.server.survival.libraries.InventoryLib;
import mc.server.survival.libraries.MathLib;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Craftingi
implements CommandExecutor, TabCompleter
{
	public static final int[] pages = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
	{
		if (sender instanceof Player)
		{
			final Player player = (Player) sender;
			final int length = args.length;

			final String rank = DataManager.getInstance().getLocal(player).getRank();

			if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("craftingi").isForAdministrators() ||
					rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("craftingi").isForModerators() ||
					rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("craftingi").isForPlayers())
			{
				ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
				return true;
			}
			
			if (length == 0)
				InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lCRAFTINGI (1/" + getRecipeCount() + ")"), "craftingi1");
			else
			{
				if (length <= 1)
				{
					final String page = args[0];

					if (MathLib.isInteger(page))
					{
						final int finalPage = Integer.parseInt(page);

						for (int pagee : pages)
							if (pagee == finalPage)
							{
								InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lCRAFTINGI (" + page + "/" + getRecipeCount() + ")"), "craftingi" + page);
								return true;
							}

						ChatManager.sendNotification(player, "Koledze sie chyba strony pojebaly! Mozesz wybrac strone #ffc9361-" + getRecipeCount() + "!", ChatManager.NotificationType.ERROR);
						return true;
					}
					else
						ChatManager.sendNotification(player, "Masz wpisac liczbe a nie jakies farmazony!", ChatManager.NotificationType.ERROR);
				}
				else
					ChatManager.sendNotification(player, "Umiesz pisac?! Wystarczy napisac #ffc936/craftingi [strona]!", ChatManager.NotificationType.ERROR);
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
			for (int page : pages) completions.add(String.valueOf(page));

		return completions;
	}

	public static int getRecipeCount()
	{
		return (int) Arrays.stream(pages).count();
	}
}