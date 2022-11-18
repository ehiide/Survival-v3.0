package mc.server.survival.commands;

import mc.server.Logger;
import mc.server.survival.libraries.ChatLib;
import mc.server.survival.libraries.TaskLib;
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

public class TPA
implements CommandExecutor, TabCompleter
{
	private static HashMap<Player, Player> tpa_queue = new HashMap<>();
	private static Player getTPA(Player player)
	{
		return tpa_queue.get(player);
	}
	private static void setTPA(Player player, Player address)
	{
		tpa_queue.put(player, address);
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
	{
		if (sender instanceof Player)
  		{
			final Player player = (Player) sender;
			final int length = args.length;

			final String rank = DataManager.getInstance().getLocal(player).getRank();

			if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("tpa").isForAdministrators() ||
					rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("tpa").isForModerators() ||
					rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("tpa").isForPlayers())
			{
				ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
				return true;
			}

			if (length == 0)
				ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cWpisz nazwe gracza! Wzor komendy: #ffc936/tpa <gracz>");
			else
			{
				if (length >= 3)
				{
					ChatManager.sendNotification(player, "Spokojnie, spokojnie, jeden gracz wystarczy! Wzor komendy: #ffc936/tpa <gracz>", ChatManager.NotificationType.ERROR);
					return true;
				}

				if (args[0].equalsIgnoreCase("akceptuj"))
				{
					if (length == 1)
					{
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cWpisz nazwe gracza, od ktorego otrzymales prosbe teleportacji! Wzor komendy: #ffc936/tpa akceptuj <gracz>");
						return true;
					}

					final String potencial_player = args[1];

					for (Player dplayer : Bukkit.getOnlinePlayers())
						if (dplayer.getName().equalsIgnoreCase(potencial_player))
						{
							if (getTPA(dplayer) == player)
							{
								ChatManager.sendMessage(player, "&c&l» &fProsba teleportacji od gracza " + ChatLib.returnPlayerColor(dplayer) + dplayer.getName() + " &fzostala zaakceptowana!");
								ChatManager.sendMessage(dplayer, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cTrwa teleportacja do gracza " + ChatLib.returnPlayerColor(player) + player.getName() + "... #fff203⌛");
								setTPA(dplayer, null);
								dplayer.teleport(player);
								return true;
							}

							ChatManager.sendNotification(player, "Wy mieliscie sie tepnac? Moze czas minal, moze gracz wyslal juz prosbe komus innemu, ja nic nie wiem!", ChatManager.NotificationType.ERROR);
							return true;
						}

					ChatManager.sendNotification(player, "Podany gracz nie jest on-line na serwerze!", ChatManager.NotificationType.ERROR);
					return true;
				}
				else if (args[0].equalsIgnoreCase("odrzuc"))
				{
					if (length == 1)
					{
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cWpisz nazwe gracza, od ktorego otrzymales prosbe teleportacji! Wzor komendy: #ffc936/tpa odrzuc <gracz>");
						return true;
					}

					final String potencial_player = args[1];

					for (Player dplayer : Bukkit.getOnlinePlayers())
						if (dplayer.getName().equalsIgnoreCase(potencial_player))
						{
							if (getTPA(dplayer)== player)
							{
								ChatManager.sendMessage(dplayer, "#f83044&l» #fc7474Prosba teleportacji do gracza " + ChatLib.returnPlayerColor(player) + player.getName() + " #fc7474zostala odrzucona!");
								ChatManager.sendMessage(player, "#f83044&l» &cProsba teleportacji od gracza " + ChatLib.returnPlayerColor(player) + dplayer.getName() + " &czostala odrzucona!");
								setTPA(dplayer, null);
								return true;
							}

							ChatManager.sendNotification(player, "Wy mieliscie sie tepnac? Moze czas minal, moze gracz wyslal juz prosbe komus innemu, ja nic nie wiem!", ChatManager.NotificationType.ERROR);
							return true;
						}

					ChatManager.sendNotification(player, "Podany gracz nie jest on-line na serwerze!", ChatManager.NotificationType.ERROR);
					return true;
				}

				final String potencial_player = args[0];

				for (Player dplayer : Bukkit.getOnlinePlayers())
				{
					if (dplayer.getName().equalsIgnoreCase(potencial_player))
					{
						setTPA(player, null);
						setTPA(player, dplayer);
						ChatManager.sendMessage(player, "#f83044&l» &cWyslano prosbe teleportacji do gracza " + ChatLib.returnPlayerColor(dplayer) + dplayer.getName() + "!");
						ChatManager.sendMessage(dplayer, "#f83044&l» &cOtrzymano prosbe teleportacji od gracza " + ChatLib.returnPlayerColor(player) + player.getName() + "!\n#fc7474&o/tpa akceptuj/odrzuc (gracz)&f&o - zaakceptowanie/odrzucenie\n#fc7474&o" + FileManager.getInstance().getConfigValue("function.timings.tpa") + " sekund&f&o - czas oczekiwania");
						VisualLib.showDelayedTitle(dplayer, "&7od: " + player.getName(), "#80ff1f⌛", 0, 20, 20);
						TaskLib.getInstance().runAsyncLater(() -> setTPA(player, null), 20*(int) FileManager.getInstance().getConfigValue("function.timings.tpa"));
						return true;
					}
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
		{
			completions.add("akceptuj");
			completions.add("odrzuc");
		}

		if (args.length == 1 || args.length == 2)
			Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));

		return completions;
	}
}