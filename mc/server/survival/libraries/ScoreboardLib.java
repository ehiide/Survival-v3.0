package mc.server.survival.libraries;

import mc.server.survival.managers.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardLib
{
	private ScoreboardLib() {}

	static ScoreboardLib instance = new ScoreboardLib();

	public static ScoreboardLib getInstance() {return instance;}

	public void reloadContents(final Player player)
	{
		showScoreboard(player); showTablist(player); showPlayerTag(player); showNametag(player);
	}

	public void reloadContentsGlobal()
	{
		Bukkit.getOnlinePlayers().forEach(this::reloadContents);
	}

	public void clearData(final Player player)
	{
		final Scoreboard scoreboard = player.getScoreboard();

		scoreboard.resetScores(" ");
		scoreboard.resetScores("dummy");
		scoreboard.clearSlot(DisplaySlot.SIDEBAR);
		scoreboard.clearSlot(DisplaySlot.PLAYER_LIST);
		scoreboard.clearSlot(DisplaySlot.BELOW_NAME);
	}

	public void showScoreboard(final Player player)
	{
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		assert manager != null;
		Scoreboard scoreboard = manager.getNewScoreboard();
		
		Objective objective = scoreboard.registerNewObjective(" ", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Objective healthbar = scoreboard.registerNewObjective("health", "health");
        healthbar.setDisplaySlot(DisplaySlot.BELOW_NAME);
        healthbar.setRenderType(RenderType.HEARTS);
        healthbar.setDisplayName(ChatColor.RED + "❤");

		final String worldName = player.getWorld().getName();

		if (worldName.equalsIgnoreCase("survival"))
			objective.setDisplayName(ChatLib.ColorUtil.formatHEX("&7--{ &c&lSURVIVAL &7}--"));
		else if (worldName.equalsIgnoreCase("survival_nether"))
			objective.setDisplayName(ChatLib.ColorUtil.formatHEX("&7--{ &c&lNETHER &7}--"));
		else if (worldName.equalsIgnoreCase("survival_the_end"))
			objective.setDisplayName(ChatLib.ColorUtil.formatHEX("&7--{ &c&lTHE END &7}--"));
		else if (worldName.equalsIgnoreCase("survival_aether"))
			objective.setDisplayName(ChatLib.ColorUtil.formatHEX("&7--{ &c&lAETHER &7}--"));
		else if (worldName.equalsIgnoreCase("survival_twilight"))
			objective.setDisplayName(ChatLib.ColorUtil.formatHEX("&7--{ &c&lTWILIGHT &7}--"));
		else
			objective.setDisplayName(ChatLib.ColorUtil.formatHEX("&7--{ &c&lZADUPIE &7}--"));
		
		Score score = objective.getScore(ChatLib.ColorUtil.formatHEX(" &7 "));
		score.setScore(15);
		
		score = objective.getScore(ChatLib.ColorUtil.formatHEX("&c&l» &7" + player.getName()));
		score.setScore(14);
		
		score = objective.getScore(ChatLib.ColorUtil.formatHEX(" &8> &a" + DataManager.getInstance().getLocal(player).getRank()));
		score.setScore(13);
		
		score = objective.getScore(ChatLib.ColorUtil.formatHEX("  &7  "));
		score.setScore(12);
		
		score = objective.getScore(ChatLib.ColorUtil.formatHEX("&c&l» &7" + DataManager.getInstance().getLocal(player).getMoney() + " monet(y)"));
		score.setScore(11);

		if (worldName.equalsIgnoreCase("survival"))
			score = objective.getScore(ChatLib.ColorUtil.formatHEX(" &8> &aSwiat survivalowy"));
		else if (worldName.equalsIgnoreCase("survival_nether"))
			score = objective.getScore(ChatLib.ColorUtil.formatHEX(" &8> &aSwiat netheru"));
		else if (worldName.equalsIgnoreCase("survival_the_end"))
			score = objective.getScore(ChatLib.ColorUtil.formatHEX(" &8> &aSwiat endu"));
		else if (worldName.equalsIgnoreCase("survival_aether"))
			score = objective.getScore(ChatLib.ColorUtil.formatHEX(" &8> &aSwiat aetheru"));
		else if (worldName.equalsIgnoreCase("survival_twilight"))
			score = objective.getScore(ChatLib.ColorUtil.formatHEX(" &8> &aSwiat twilight"));
		else
			score = objective.getScore(ChatLib.ColorUtil.formatHEX(" &8> &aSwiat blizej nieokreslony"));

		score.setScore(10);
		
		player.setScoreboard(scoreboard);
	}

	public void showTablist(final Player player)
	{
		final String worldName = player.getWorld().getName();

		if (worldName.equalsIgnoreCase("survival"))
			player.setPlayerListHeaderFooter(
					ChatLib.ColorUtil.formatHEX("\n&7--{ &c&lS U R V I V A L &7}-- \n\n "
							+ "&c» &aZyczymy milej i przyjemnej gry, " + player.getName() +
							"! &c«\n &7&o(Spis wszystkich komend znajdziesz pod komenda /pomoc)\n"),
					ChatLib.ColorUtil.formatHEX("\n&c» &7------------------------ &c«\n"));
		else if (worldName.equalsIgnoreCase("survival_nether"))
			player.setPlayerListHeaderFooter(
					ChatLib.ColorUtil.formatHEX("\n&7--{ &c&lN E T H E R &7}-- \n\n "
							+ "&c» &aZyczymy milej i przyjemnej gry, " + player.getName() +
							"! &c«\n &7&o(Spis wszystkich komend znajdziesz pod komenda /pomoc)\n"),
					ChatLib.ColorUtil.formatHEX("\n&c» &7------------------------ &c«\n"));
		else if (worldName.equalsIgnoreCase("survival_aether"))
			player.setPlayerListHeaderFooter(
					ChatLib.ColorUtil.formatHEX("\n&7--{ &c&lA E T H E R &7}-- \n\n "
							+ "&c» &aZyczymy milej i przyjemnej gry, " + player.getName() +
							"! &c«\n &7&o(Spis wszystkich komend znajdziesz pod komenda /pomoc)\n"),
					ChatLib.ColorUtil.formatHEX("\n&c» &7------------------------ &c«\n"));
		else if (worldName.equalsIgnoreCase("survival_the_end"))
			player.setPlayerListHeaderFooter(
					ChatLib.ColorUtil.formatHEX("\n&7--{ &c&lT H E   E N D &7}-- \n\n "
							+ "&c» &aZyczymy milej i przyjemnej gry, " + player.getName() +
							"! &c«\n &7&o(Spis wszystkich komend znajdziesz pod komenda /pomoc)\n"),
					ChatLib.ColorUtil.formatHEX("\n&c» &7------------------------ &c«\n"));
		else if (worldName.equalsIgnoreCase("survival_twilight"))
			player.setPlayerListHeaderFooter(
					ChatLib.ColorUtil.formatHEX("\n&7--{ &c&lT W I L I G H T &7}-- \n\n "
							+ "&c» &aZyczymy milej i przyjemnej gry, " + player.getName() +
							"! &c«\n &7&o(Spis wszystkich komend znajdziesz pod komenda /pomoc)\n"),
					ChatLib.ColorUtil.formatHEX("\n&c» &7------------------------ &c«\n"));
		else
			player.setPlayerListHeaderFooter(
					ChatLib.ColorUtil.formatHEX("\n&7--{ &c&lD I M E N S I O N &7}-- \n\n "
							+ "&c» &aZyczymy milej i przyjemnej gry, " + player.getName() +
							"! &c«\n &7&o(Spis wszystkich komend znajdziesz pod komenda /pomoc)\n"),
					ChatLib.ColorUtil.formatHEX("\n&c» &7------------------------ &c«\n"));
	}

	public void showPlayerTag(final Player player)
	{
		player.setPlayerListName(ChatLib.ColorUtil.formatHEX(ChatLib.getRankInChat(player) + ChatLib.getGangInChat(DataManager.getInstance().getLocal(player).getGang()) + ChatLib.returnMarryPrefix(player) + ChatLib.returnPlayerColor(player) + player.getName()));
	}


	public void showNametag(final Player player)
	{
		final Scoreboard scoreboard = player.getScoreboard();

		for (final Player onlinePlayer : Bukkit.getOnlinePlayers())
		{
			Team team = handleTeam(scoreboard, onlinePlayer.getName());
			team.addEntry(onlinePlayer.getName());

			final String rank = ChatLib.getRankInChat(onlinePlayer);
			final String gang = ChatLib.getGangInChat(DataManager.getInstance().getLocal(onlinePlayer).getGang());
			final String marry = ChatLib.returnMarryPrefix(onlinePlayer);

			team.setPrefix(ChatLib.ColorUtil.formatHEX(rank + gang + marry));
			team.setColor(ChatLib.returnDefinedPlayerColor(onlinePlayer.getName()));
		}

		player.setScoreboard(scoreboard);
	}

	private Team handleTeam(final Scoreboard scoreboard, final String name)
	{
		Team team = scoreboard.getTeam(name);

		if (team == null)
			team = scoreboard.registerNewTeam(name);

		team.getEntries().forEach(team::removeEntry);

		return team;
	}
}