package mc.server.survival.commands;

import mc.server.Logger;
import mc.server.survival.libraries.ChatLib;
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
import java.util.HashMap;
import java.util.List;

public class Wiadomosc
implements CommandExecutor, TabCompleter
{
	private static HashMap<Player, Player> reply_queue = new HashMap<>();
	protected static Player getReply(Player player)
	{
		return reply_queue.get(player);
	}
	protected static void setReply(Player player, Player address)
	{
		reply_queue.put(player, address);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
	{
		if (sender instanceof Player)
		{
			final Player player = (Player) sender;
			final int length = args.length;

			final String rank = DataManager.getInstance().getLocal(player).getRank();

			if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("wiadomosc").isForAdministrators() ||
					rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("wiadomosc").isForModerators() ||
					rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("wiadomosc").isForPlayers())
			{
				ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
				return true;
			}
			
			if (length == 0)
			{
				ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cWprowadz nazwe gracza! Wzor komendy: #ffc936/wiadomosc <gracz> <wiadomosc>");
				return true;
			}
			else
			{
				final String potencial_player = args[0];
				
				for (Player dplayer : Bukkit.getOnlinePlayers())
					if (dplayer.getName().equalsIgnoreCase(potencial_player))
						if (length == 1)
						{
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cWprowadz swoja wiadomosc! Wzor komendy: #ffc936/wiadomosc <gracz> <wiadomosc>");
							return true;
						}
						else
						{
							StringBuilder message = new StringBuilder();

							for (int word = 1; word < length; word++)
								if (word + 1 >= length)
									message.append(args[word].toLowerCase());
								else
									message.append(args[word].toLowerCase()).append(" ");

							ChatManager.sendMessage(player, "#f83044&l» &cDo " + ChatLib.returnPlayerColor(dplayer) + dplayer.getName() + ": &r&7" + ChatLib.applyCorrection(ChatLib.getPlaceholder(player, message.toString())));
							ChatManager.sendMessage(dplayer, "#f83044&l» &cWiadomosc od " + ChatLib.returnPlayerColor(player) + player.getName() + ": &r&7" + ChatLib.applyCorrection(ChatLib.getPlaceholder(player, message.toString())));
							VisualLib.showDelayedTitle(dplayer, "&7od: " + player.getName(), "#faff26✉", 0, 10, 10);

							setReply(dplayer, player);
							setReply(player, dplayer);

							return true;
						}
				
				if (!Bukkit.getOfflinePlayer(potencial_player).isOnline()) 
				{
					ChatManager.sendNotification(player, "Podany gracz nie jest on-line na serwerze!", ChatManager.NotificationType.ERROR);
					return true;
				}
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
			Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));

		return completions;
	}
}