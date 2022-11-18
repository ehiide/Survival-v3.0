package mc.server.survival.commands;

import mc.server.Logger;
import mc.server.survival.libraries.ChatLib;
import mc.server.survival.libraries.MathLib;
import mc.server.survival.libraries.ScoreboardLib;
import mc.server.survival.libraries.VisualLib;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import mc.server.survival.managers.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Zaplac
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

			if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("zaplac").isForAdministrators() ||
					rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("zaplac").isForModerators() ||
					rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("zaplac").isForPlayers())
			{
				ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
				return true;
			}
			
			if (length >= 3)
			{
				ChatManager.sendNotification(player, "Co Ty tworzysz! Wzor komendy: #ffc936/zaplac <gracz> <ilosc>!", ChatManager.NotificationType.ERROR);
				return true;
			}
			
			if (length == 0)
				ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cSponsor, widze! Sluchaj... podaj nazwe gracza, ktoremu chcesz zaplacic!");
			else
			{
				final String potencial_player = args[0];
				
				for (Player dplayer : Bukkit.getOnlinePlayers())
					if (dplayer.getName().equalsIgnoreCase(potencial_player)) 
					{	
						if (length == 1)
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cWprowadz sume monet jaka chcesz przelac graczowi " + ChatLib.returnPlayerColor(player) + dplayer.getName() + "!");
						else
							{
							if (MathLib.isInteger(args[1]))
							{
								final int check = Integer.parseInt(args[1]);
								
								if (check > DataManager.getInstance().getLocal(player).getMoney())
								{
									ChatManager.sendNotification(player, "Nie posiadasz nawet tyle w portfelu!", ChatManager.NotificationType.ERROR);
									return true;
								}

								if (check > 100000)
								{
									ChatManager.sendNotification(player, "Nie tak duzo, bo ci podatek nalicze!", ChatManager.NotificationType.ERROR);
									return true;
								}

								if (check <= 0)
								{
									ChatManager.sendNotification(player, "Ty ale daj mu cos zgredzie, a nie!", ChatManager.NotificationType.ERROR);
									return true;
								}

								DataManager.getInstance().getLocal(player).removeMoney(check);
								DataManager.getInstance().getLocal(dplayer).addMoney(check);
								ChatManager.sendMessage(player, "#f83044&l» &cPrzelano kwote &e" + check + ",- &emonet &cdo " + ChatLib.returnPlayerColor(dplayer) + dplayer.getName() + "!");
								ChatManager.sendMessage(dplayer, "#f83044&l» &cOtrzymano kwote &e" + check + ",- &emonet &cod " + ChatLib.returnPlayerColor(player) + player.getName() + "!");
								VisualLib.showDelayedTitle(dplayer, "&7od: " + player.getName(), "#ffc936⛃", 0, 10, 10);
								ScoreboardLib.getInstance().reloadContents(player);
								ScoreboardLib.getInstance().reloadContents(dplayer);
								return true;
							}

							ChatManager.sendNotification(player, "Ta kwota to liczba?! Wzor komendy: #ffc936/zaplac <gracz> <ilosc>!", ChatManager.NotificationType.ERROR);
						}

						return true;
					}

				ChatManager.sendNotification(player, "Podany gracz nie jest on-line na serwerze!", ChatManager.NotificationType.ERROR);
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
			Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));

		return completions;
	}
}