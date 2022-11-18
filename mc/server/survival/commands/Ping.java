package mc.server.survival.commands;

import mc.server.Logger;
import mc.server.survival.libraries.ChatLib;
import mc.server.survival.libraries.ServerLib;
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

public class Ping 
implements CommandExecutor, TabCompleter
{
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
	{
		if (sender instanceof Player)
		{
			final Player player = (Player) sender;

			final String rank = DataManager.getInstance().getLocal(player).getRank();

			if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("ping").isForAdministrators() ||
					rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("ping").isForModerators() ||
					rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("ping").isForPlayers())
			{
				ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
				return true;
			}

			if (args.length == 0)
				ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cTwoj ping wynosi #fff203" + ChatLib.returnPlayerColor(player) + ServerLib.getPing(player) + " ms!");
			else
			{
				final String potencial_player = args[0];
				
				for (Player dplayer : Bukkit.getOnlinePlayers())
					if (dplayer.getName().equalsIgnoreCase(potencial_player)) 
					{	
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cPing " + ChatLib.returnPlayerColor(player) + dplayer.getName() + "#8c8c8c wynosi " + ChatLib.returnPlayerColor(player) + ServerLib.getPing(dplayer) + " ms!");
						return true;
					}

				ChatManager.sendNotification(player, "Podany gracz nie jest on-line na serwerze!", ChatManager.NotificationType.ERROR);
			}
			return true;
		}
		else if (sender instanceof ConsoleCommandSender)
		{
			if (args.length == 0)
				Logger.log("Nie zgadniesz ile wyszlo! Rowniutkie ZERO!");
			else
			{
				final String potencial_player = args[0];

				for (Player dplayer : Bukkit.getOnlinePlayers())
					if (dplayer.getName().equalsIgnoreCase(potencial_player))
					{
						Logger.log("Ping " + ChatLib.returnDefinedPlayerColor(dplayer.getName()) + dplayer.getName() + " &r&7wynosi " + ChatLib.returnDefinedPlayerColor(dplayer.getName()) + ServerLib.getPing(dplayer) + " ms!");
						return true;
					}

				Logger.log("&cPodany gracz nie jest on-line na serwerze!");
			}
			return true;
		}

		return true;
	}

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args)
	{
		ArrayList<String> completions = new ArrayList<>();

		if (args.length == 1)
			Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));

		return completions;
	}
}