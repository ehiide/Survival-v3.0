package mc.server.survival.libraries;

import mc.server.survival.files.Main;
import mc.server.survival.libraries.chemistry.ChemistryCore;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import mc.server.survival.managers.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatLib
{
	public static String applyCorrection(final String message)
	{
		String var1 = message;
		
		String[] var2 = {"D:", ":(", ":<", ":{", ":[", ":sad:"};
		
		for (String var3 : var2)
			var1 = var1.replace(var3, setEmote("&6☹"));
		
		String[] var4 = {":D", ":)", ":>", ":}", ":]", ":happy:", ":smile:"};
		
		for (String var5 : var4)
			var1 = var1.replace(var5, setRandomEmote("smile"));
		
		String[] var6 = {"#>", "<3", "<#", ":heart:"};
		
		for (String var7 : var6)
			var1 = var1.replace(var7, setRandomEmote("heart"));
		
		var1 = var1.replace(":star:", setEmote("&e★"));
		var1 = var1.replace(":rounded_heart:", setEmote("&d❥"));
		var1 = var1.replace(":time:", setEmote("&e⌛"));
		var1 = var1.replace(":lighting:", setEmote("&b↯"));
		var1 = var1.replace(":flower:", setEmote("&a❀"));
		var1 = var1.replace(":crown:", setEmote("&e♔"));
		var1 = var1.replace(":cloud:", setEmote("&f☁"));
		var1 = var1.replace(":sun:", setEmote("&e☀"));
		var1 = var1.replace(":moon:", setEmote("&7☾"));
		var1 = var1.replace(":money:", setEmote("&e⛃"));
		var1 = var1.replace(":meh:", setEmote("&e¯\\_(ツ)_/¯"));
		var1 = var1.replace(":love:", setEmote("&d(｡♥v♥｡)"));
		var1 = var1.replace(":panda:", setEmote("&7ʕ&f•&0ᴥ&f•&7ʔ"));
		var1 = var1.replace(":phone:", setEmote("&7☎"));
		var1 = var1.replace(":umbrella:", setEmote("&f☔"));
		var1 = var1.replace(":clover:", setEmote("&a☘"));
		var1 = var1.replace(":horse:", setEmote("&e♞"));
		var1 = var1.replace(":cross:", setEmote("&c♰"));
		var1 = var1.replace(":recycling:", setEmote("&a♻"));
		var1 = var1.replace(":flag:", setEmote("&f⚐"));
		var1 = var1.replace(":pickaxe:", setEmote("&e⛏"));
		var1 = var1.replace(":arrow:", setEmote("&e➔"));
		var1 = var1.replace(":arrows:", setEmote("&e➽"));
		var1 = var1.replace(":scissors:", setEmote("&f✂"));
		var1 = var1.replace(":snow:", setEmote("&f❄"));

		var1 = var1.replace(":gwiazda:", setEmote("&e★"));
		var1 = var1.replace(":serce:", setEmote("&d❥"));
		var1 = var1.replace(":czas:", setEmote("&e⌛"));
		var1 = var1.replace(":piorun:", setEmote("&b↯"));
		var1 = var1.replace(":kwiat:", setEmote("&a❀"));
		var1 = var1.replace(":korona:", setEmote("&e♔"));
		var1 = var1.replace(":chmura:", setEmote("&f☁"));
		var1 = var1.replace(":slonce:", setEmote("&e☀"));
		var1 = var1.replace(":ksiezyc:", setEmote("&7☾"));
		var1 = var1.replace(":pieniadze:", setEmote("&e⛃"));
		var1 = var1.replace(":milosc:", setEmote("&d(｡♥v♥｡)"));
		var1 = var1.replace(":telefon:", setEmote("&7☎"));
		var1 = var1.replace(":parasol:", setEmote("&f☔"));
		var1 = var1.replace(":koniczyna:", setEmote("&a☘"));
		var1 = var1.replace(":kon:", setEmote("&e♞"));
		var1 = var1.replace(":krzyz:", setEmote("&c♰"));
		var1 = var1.replace(":recykling:", setEmote("&a♻"));
		var1 = var1.replace(":flaga:", setEmote("&f⚐"));
		var1 = var1.replace(":kilof:", setEmote("&e⛏"));
		var1 = var1.replace(":strzalka:", setEmote("&e➔"));
		var1 = var1.replace(":strzala:", setEmote("&e➽"));
		var1 = var1.replace(":nozyczki:", setEmote("&f✂"));
		var1 = var1.replace(":snieg:", setEmote("&f❄"));

		var1 = var1.substring(0, 1).toUpperCase() + var1.substring(1);
		
		String var3 = var1.substring(var1.length() - 1);
			
		if (!var3.equalsIgnoreCase(".") && !var3.equalsIgnoreCase("!") && !var3.equalsIgnoreCase("?") && !var3.equalsIgnoreCase(";") && !var3.equalsIgnoreCase(":"))
			var1 = var1 + ".";

		return compileMessage(ColorUtil.formatHEX(var1));
	}
	
	private static String compileMessage(final String message)
	{
		String var1 = ChatColor.stripColor(message);
		String firstChar = var1.substring(0, 1);

		if (firstChar.equalsIgnoreCase(" "))
			var1 = var1.substring(1);

		var1 = var1.substring(0, 1).toUpperCase() + var1.substring(1).toLowerCase();
		
		return var1;
	}

	public static String setEmote(final String var1)
	{
		return ChatColor.translateAlternateColorCodes('&', var1 + "#8c8c8c");
	}
	
	public static String setRandomEmote(final String var1)
	{
		if (var1.equalsIgnoreCase("heart"))
		{
			final Random var2 = new Random();
			final int var3 = var2.nextInt(3);
			
			if (var3 == 1)
				return setEmote("#ff03e2❤");
			else if (var3 == 2)
				return setEmote("#ff03e2❣");
			else
				return setEmote("#ff03e2♥");
		}
		else if (var1.equalsIgnoreCase("smile"))
		{
			final Random var2 = new Random();
			final int var3 = var2.nextInt(3);
			
			if (var3 == 1)
				return setEmote("&6ツ");
			else if (var3 == 2)
				return setEmote("&6☻");
			else
				return setEmote("&6☺");
		}
		else
			return "";
	}

	public static String addDruggedEmotes(final Player player, String message)
	{
		final int serotonine = DataManager.getInstance().getLocal(player).getSerotonine();

		if (serotonine < 0)
		{
			ArrayList<String> messages = new ArrayList<>();
			messages.add("Idz sobie");
			messages.add("Zycie jest do dupy");
			messages.add("Boze to nie ma sensu");
			messages.add("Myslicie ze jak sie zabije to sie zrespie szczesliwsza?");
			messages.add("Kys");
			messages.add("Spierdalaj wkurwiasz mnie tylko");
			messages.add("Nie");
			messages.add("Nie chce juz");
			messages.add("Dobijacie mnie");
			messages.add("Jebane lzy");
			messages.add("Podetne sie normalnie");

			if (MathLib.chanceOf(Math.abs(serotonine)))
				message = messages.get(new Random().nextInt(messages.size()));

			if (message.length() > 5)
				message = message.replaceAll(" tak ", " nie ");
			else if (message.equalsIgnoreCase("tak"))
				message = message.replaceAll("tak", "nie");

			message = message.replaceAll(" zawsze ", " nigdy ");

			if (MathLib.chanceOf(Math.abs(serotonine)))
				message = message + " " + "&6☹";

			if (MathLib.chanceOf(Math.abs(serotonine)))
				message = message + "...";
		}

		if (serotonine > 0)
		{
			ArrayList<String> messages = new ArrayList<>();
			messages.add("Dziekuje ze jestescie");
			messages.add("Pamietajcie ze was kocham");
			messages.add("Zawsze bedziecie w moim sercu");
			messages.add("Az mam zachcianke kogos wysziftowac");
			messages.add("Boze jak ja was uwielbiam");
			messages.add("Kc");
			messages.add("Kct");
			messages.add("Kooham was");
			messages.add("Zajebiste to jest");
			messages.add("Jedna w zyciu dziurke mialem, a to byl traf niewielki, a to dziurka od butelki");
			messages.add("Widzicie ta aure miedzy nami?");
			messages.add("Dziekuje");
			messages.add("Dziekuje wam");
			messages.add("Dziekuje wam bardzo");
			messages.add("Chodz dam ci buzi");
			messages.add("Ale super jest");
			messages.add("Kocham was mocniej niz skid dzieci");

			if (MathLib.chanceOf(serotonine))
				message = messages.get(new Random().nextInt(messages.size()));

			if (message.length() > 5)
				message = message.replaceAll(" nie ", " tak ");
			else if (message.equalsIgnoreCase("nie"))
				message = message.replaceAll("nie", "tak");

			message = message.replaceAll(" nigdy ", " zawsze ");

			if (MathLib.chanceOf(serotonine))
				message = message + " " + setRandomEmote("heart");

			if (MathLib.chanceOf(serotonine))
				message = message + " " + setRandomEmote("smile");
		}

		return message;
	}

	public static void scheduleDruggedMessages(final Player player, final int delay)
	{
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				ArrayList<String> allPlayers = new ArrayList<>();
				Player onlinePlayer = null;

				for (Player onlinePlayers : Bukkit.getOnlinePlayers())
                    if (!onlinePlayers.getName().equalsIgnoreCase(player.getName()))
                    {
                        allPlayers.add(onlinePlayers.getName());
                        int randomSelect = new Random().nextInt(allPlayers.size());
                        onlinePlayer = Bukkit.getPlayer(allPlayers.get(randomSelect));
                    }

				if (onlinePlayer == null)
					return;

				if (MathLib.chanceOf(50))
				{
					if (MathLib.chanceOf(10))
						ChatManager.sendMessage(player, ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.getGangInChat(DataManager.getInstance().getLocal(onlinePlayer).getGang()) + ChatLib.returnMarryPrefix(onlinePlayer) + "&r" + ChatLib.returnPlayerColor(onlinePlayer) + Objects.requireNonNull(onlinePlayer.getPlayer()).getName() + "#8c8c8c " +
								applyCorrection("co tam " + player.getName())));
					else if (MathLib.chanceOf(10))
						ChatManager.sendMessage(player, ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.getGangInChat(DataManager.getInstance().getLocal(onlinePlayer).getGang()) + ChatLib.returnMarryPrefix(onlinePlayer) + "&r" + ChatLib.returnPlayerColor(onlinePlayer) + Objects.requireNonNull(onlinePlayer.getPlayer()).getName() + "#8c8c8c " +
								applyCorrection("ej " + player.getName() + " jestes super <3")));
					else if (MathLib.chanceOf(10))
						ChatManager.sendMessage(player, ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.getGangInChat(DataManager.getInstance().getLocal(onlinePlayer).getGang()) + ChatLib.returnMarryPrefix(onlinePlayer) + "&r" + ChatLib.returnPlayerColor(onlinePlayer) + Objects.requireNonNull(onlinePlayer.getPlayer()).getName() + "#8c8c8c " +
								applyCorrection("chodz do mnie skarbie")));
					else if (MathLib.chanceOf(10))
						ChatManager.sendMessage(player, ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.getGangInChat(DataManager.getInstance().getLocal(onlinePlayer).getGang()) + ChatLib.returnMarryPrefix(onlinePlayer) + "&r" + ChatLib.returnPlayerColor(onlinePlayer) + Objects.requireNonNull(onlinePlayer.getPlayer()).getName() + "#8c8c8c " +
								applyCorrection("swiat z toba jest taki piekny")));
					else if (MathLib.chanceOf(10))
						ChatManager.sendMessage(player, ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.getGangInChat(DataManager.getInstance().getLocal(onlinePlayer).getGang()) + ChatLib.returnMarryPrefix(onlinePlayer) + "&r" + ChatLib.returnPlayerColor(onlinePlayer) + Objects.requireNonNull(onlinePlayer.getPlayer()).getName() + "#8c8c8c " +
								applyCorrection("swiat z toba jest taki piekny jak " + player.getName())));
					else if (MathLib.chanceOf(10))
						ChatManager.sendMessage(player, ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.getGangInChat(DataManager.getInstance().getLocal(onlinePlayer).getGang()) + ChatLib.returnMarryPrefix(onlinePlayer) + "&r" + ChatLib.returnPlayerColor(onlinePlayer) + Objects.requireNonNull(onlinePlayer.getPlayer()).getName() + "#8c8c8c " +
								applyCorrection(player.getName() + " tpa")));
				}
				else
				{
					if (MathLib.chanceOf(10))
					{
						ChatManager.sendMessage(player, "&c&l» &f&oWiadomosc od " + ChatLib.returnPlayerColor(onlinePlayer) + "&o" + onlinePlayer.getName() + ": &r&7" + ChatLib.applyCorrection("tepnij sie na chwile kotek"));
						VisualLib.showDelayedTitle(player, "&7od: " + onlinePlayer.getName(), "#faff26✉", 0, 20, 20);
					}
					else if (MathLib.chanceOf(10))
					{
						ChatManager.sendMessage(player, "&c&l» &f&oWiadomosc od " + ChatLib.returnPlayerColor(onlinePlayer) + "&o" + onlinePlayer.getName() + ": &r&7" + ChatLib.applyCorrection("pieknie dzis wygladasz"));
						VisualLib.showDelayedTitle(player, "&7od: " + onlinePlayer.getName(), "#faff26✉", 0, 20, 20);
					}
					else if (MathLib.chanceOf(10))
					{
						ChatManager.sendMessage(player, "&c&l» &f&oWiadomosc od " + ChatLib.returnPlayerColor(onlinePlayer) + "&o" + onlinePlayer.getName() + ": &r&7" + ChatLib.applyCorrection("ciesze sie ze moj idol to ty XD"));
						VisualLib.showDelayedTitle(player, "&7od: " + onlinePlayer.getName(), "#faff26✉", 0, 20, 20);
					}
					else if (MathLib.chanceOf(10))
					{
						ChatManager.sendMessage(player, "&c&l» &f&oWiadomosc od " + ChatLib.returnPlayerColor(onlinePlayer) + "&o" + onlinePlayer.getName() + ": &r&7" + ChatLib.applyCorrection("uwielbiam cie"));
						VisualLib.showDelayedTitle(player, "&7od: " + onlinePlayer.getName(), "#faff26✉", 0, 20, 20);
					}
					else if (MathLib.chanceOf(10))
					{
						ChatManager.sendMessage(player, "&c&l» &f&oWiadomosc od " + ChatLib.returnPlayerColor(onlinePlayer) + "&o" + onlinePlayer.getName() + ": &r&7" + ChatLib.applyCorrection("uwielbiam cie haha"));
						VisualLib.showDelayedTitle(player, "&7od: " + onlinePlayer.getName(), "#faff26✉", 0, 20, 20);
					}
					else if (MathLib.chanceOf(10))
					{
						ChatManager.sendMessage(player, "&c&l» &f&oWiadomosc od " + ChatLib.returnPlayerColor(onlinePlayer) + "&o" + onlinePlayer.getName() + ": &r&7" + ChatLib.applyCorrection("nastepym razem bierzemy razem <3"));
						VisualLib.showDelayedTitle(player, "&7od: " + onlinePlayer.getName(), "#faff26✉", 0, 20, 20);
					}
				}
			}
		}.runTaskLater(Main.getInstance(), 20L * new Random().nextInt(delay));
	}

	public static String getPlaceholder(final Player player, String placeholder)
	{
		placeholder = placeholder.replace("%ping%", "#8c8c8c" + ServerLib.getPing(player) + " ms" + " #666666&o(%ping%)#8c8c8c");
		placeholder = placeholder.replace("%trzezwosc%", "#8c8c8c" + ChemistryCore.getDruggedPercentage(player) + "% trzezwosci" + " #666666&o(%trzezwosc%)#8c8c8c");
		placeholder = placeholder.replace("%monety%", "#8c8c8c" + DataManager.getInstance().getLocal(player).getMoney() + " monet" + " #666666&o(%monety%)#8c8c8c");
		placeholder = placeholder.replace("%smierci%", "#8c8c8c" + DataManager.getInstance().getLocal(player).getDeaths() + " smierci" + " #666666&o(%smierci%)#8c8c8c");
		placeholder = placeholder.replace("%zabojstwa%", "#8c8c8c" + DataManager.getInstance().getLocal(player).getKills() + " zaboojstw" + " #666666&o(%zaboojstwa%)#8c8c8c");
		placeholder = placeholder.replace("%questy%", "#8c8c8c" + QuestLib.getCompletedQuests(player) + " ukonczonych questow" + " #666666&o(%questy%)#8c8c8c");
		placeholder = placeholder.replace("%osiagniecia%", "#8c8c8c" + DataManager.getInstance().getLocal(player).getFinishedAdvancements() + " ukonczonych osiagniec" + " #666666&o(%osiagniecia%)#8c8c8c");
		placeholder = placeholder.replace("%xyz%", "#8c8c8cx: " + player.getLocation().getBlockX() + "#8c8c8c, y: " + player.getLocation().getBlockY() + "#8c8c8c, z: #8c8c8c" + player.getLocation().getBlockZ() + " #666666&o(%xyz%)#8c8c8c");
		placeholder = placeholder.replace("%xy%", "#8c8c8cx: " + player.getLocation().getBlockX() + "#8c8c8c, y: " + player.getLocation().getBlockY() + " #666666&o(%xy%)#8c8c8c");
		placeholder = placeholder.replace("%yz%", "#8c8c8cy: " + player.getLocation().getBlockY() + "#8c8c8c, z: " + player.getLocation().getBlockZ() + " #666666&o(%yz%)#8c8c8c");
		placeholder = placeholder.replace("%xz%", "#8c8c8cx: " + player.getLocation().getBlockX() + "#8c8c8c, z: " + player.getLocation().getBlockZ() + " #666666&o(%xz%)#8c8c8c");
		placeholder = placeholder.replace("%x%", "#8c8c8cx: " + player.getLocation().getBlockX() + " #666666&o(%x%)#8c8c8c");
		placeholder = placeholder.replace("%y%", "#8c8c8cy: " + player.getLocation().getBlockY() + " #666666&o(%y%)#8c8c8c");
		placeholder = placeholder.replace("%z%", "#8c8c8cz: " + player.getLocation().getBlockZ() + " #666666&o(%z%)#8c8c8c");

		if (player.getInventory().getItemInMainHand().getType() != Material.AIR)
			placeholder = placeholder.replace("%przedmiot%", "#8c8c8c&a" + player.getInventory().getItemInMainHand().getType() + " &ex" + player.getInventory().getItemInMainHand().getAmount() + " #666666&o(%przedmiot%)#8c8c8c");

		String message = placeholder;

		if (player.getWorld().getName().equalsIgnoreCase("survival"))
			message = placeholder.replace("%swiat%", "#8c8c8cswiat survivalowy" + " #666666&o(%swiat%)#8c8c8c");
		else if (player.getWorld().getName().equalsIgnoreCase("survival_nether"))
			message = placeholder.replace("%swiat%", "#8c8c8cswiat netheru" + " #666666&o(%swiat%)#8c8c8c");
		else if (player.getWorld().getName().equalsIgnoreCase("survival_the_end"))
			message = placeholder.replace("%swiat%", "#8c8c8cswiat endu" + " #666666&o(%swiat%)#8c8c8c");
		else if (player.getWorld().getName().equalsIgnoreCase("survival_aether"))
			message = placeholder.replace("%swiat%", "#8c8c8cswiat aetheru" + " #666666&o(%swiat%)#8c8c8c");
		else if (player.getWorld().getName().equalsIgnoreCase("survival_twilight"))
			message = placeholder.replace("%swiat%", "#8c8c8cswiat twilight" + " #666666&o(%swiat%)#8c8c8c");

		return message;
	}
	
	public static String returnMarryPrefix(final Player player)
	{
		if (DataManager.getInstance().getLocal(player).getMarry() != null)
			return "&c❤ ";
		
		return "";
	}

	public static String returnPlayerColor(final Player player)
	{
		final String chatColor = DataManager.getInstance().getLocal(player).getChatColor();

		if (chatColor.equalsIgnoreCase("red"))
			return "#fc7474";
		else if (chatColor.equalsIgnoreCase("blue"))
			return "#3075ff";
		else if (chatColor.equalsIgnoreCase("green"))
			return "#02d645";
		else if (chatColor.equalsIgnoreCase("yellow"))
			return "#fcff33";
		else if (chatColor.equalsIgnoreCase("white"))
			return "#ffffff";
		else if (chatColor.equalsIgnoreCase("gray"))
			return "#242424";
		else if (chatColor.equalsIgnoreCase("orange"))
			return "#ffb338";
		else if (chatColor.equalsIgnoreCase("pink"))
			return "#ff9ee7";

		return "";
	}

	public static String returnPlayerColor(final String player)
	{
		final String chatColor = DataManager.getInstance().getLocal(null).getChatColor(player);

		if (chatColor.equalsIgnoreCase("red"))
			return "#fc7474";
		else if (chatColor.equalsIgnoreCase("blue"))
			return "#3075ff";
		else if (chatColor.equalsIgnoreCase("green"))
			return "#02d645";
		else if (chatColor.equalsIgnoreCase("yellow"))
			return "#fcff33";
		else if (chatColor.equalsIgnoreCase("white"))
			return "#ffffff";
		else if (chatColor.equalsIgnoreCase("gray"))
			return "#242424";
		else if (chatColor.equalsIgnoreCase("orange"))
			return "#ffb338";
		else if (chatColor.equalsIgnoreCase("pink"))
			return "#ff9ee7";

		return "";
	}

	public static String returnCustomGangPrefix(final Player player, final String color, final String prefixes, final String star)
	{
		final String gang = DataManager.getInstance().getLocal(player).getGang();

		if (gang == null)
			return "";
		if (prefixes.equalsIgnoreCase("normal"))
			return "&7[" + star + color + gang + "&7] ";
		if (prefixes.equalsIgnoreCase("rounded"))
			return "&7(" + star + color + gang + "&7) ";
		if (prefixes.equalsIgnoreCase("arrows"))
			return "&7<" + star + color + gang + "&7> ";

		return "";
	}

	public static String getValidGangColor(final String name)
	{
		final String color = DataManager.getInstance().getLocal(null).getColor(name);

		if (color.equalsIgnoreCase("red"))
			return "&c";
		else if (color.equalsIgnoreCase("blue"))
			return "&b";
		else if (color.equalsIgnoreCase("green"))
			return "&a";
		else if (color.equalsIgnoreCase("yellow"))
			return "&e";
		else if (color.equalsIgnoreCase("white"))
			return "&f";
		else if (color.equalsIgnoreCase("gray"))
			return "&7";
		else if (color.equalsIgnoreCase("orange"))
			return "&6";
		else if (color.equalsIgnoreCase("pink"))
			return "&d";

		return "";
	}

	public static String returnGangStar(final String name)
	{
		if (DataManager.getInstance().getLocal(null).getStar(name))
			return "#ffc936★ ";

		return "";
	}

	public static String getGangInChat(final String name)
	{
		if (name == null) return "";

		final String prefixes = DataManager.getInstance().getLocal(null).getPrefixes(name);

		if (prefixes.equalsIgnoreCase("normal"))
			return "&7[" + returnGangStar(name) + getValidGangColor(name) + name.toUpperCase() + "&7] ";
		else if (prefixes.equalsIgnoreCase("rounded"))
			return "&7(" + returnGangStar(name) + getValidGangColor(name) + name.toUpperCase() + "&7) ";
		else if (prefixes.equalsIgnoreCase("arrows"))
			return "&7<" + returnGangStar(name) +  getValidGangColor(name) + name.toUpperCase() + "&7> ";

		return "";
	}

	public static String returnGangPlayerChatMessage(final Player player, String message)
	{
		String isLider = "";
		
		if (DataManager.getInstance().getLocal(null).getLider(DataManager.getInstance().getLocal(player).getGang()).equalsIgnoreCase(player.getName()))
			isLider = " #ffc936[LIDER]";

		message = compileMessage(message.substring(1));
		message = ColorUtil.formatHEX("#80ff1f[CZAT GANGU] " + "&d" + ChatLib.returnMarryPrefix(player) + "&r" + ChatLib.returnPlayerColor(player) + Objects.requireNonNull(player.getPlayer()).getName() + isLider + "#8c8c8c " + message);

		return message;
	}

	public static String returnGangGangChatMessage(final String message)
	{
		return ColorUtil.formatHEX("#80ff1f[CZAT GANGU] " + "#8c8c8c" + message);
	}

	public static void sendGangChatMessage(final Player player, String message)
	{
		if (DataManager.getInstance().getLocal(player).getGang() == null)
		{
			ChatManager.sendNotification(player, "Aby pisac na czacie gangu musisz najpierw do niego dolaczyc!", ChatManager.NotificationType.ERROR);
			return;
		}

		if (!DataManager.getInstance().getLocal(null).getChat(DataManager.getInstance().getLocal(player).getGang()))
		{
			ChatManager.sendNotification(player, "Twoj gang nie posiada zakupionego czatu, aby go odblokowac odwiedz zakladke ulepszen gangu lub wpisz komende #ffc936/gang ulepsz#fc7474!", ChatManager.NotificationType.ERROR);
			return;
		}

		if (message.length() < 2)
		{
			ChatManager.sendNotification(player, "Wprowadz swoja wiadomosc, ktora chcesz wyslac na czat gangu!", ChatManager.NotificationType.INFORMATION);
			return;
		}

		for (String member : DataManager.getInstance().getLocal(null).getPlayerMembers(DataManager.getInstance().getLocal(player).getGang()))
			if (Bukkit.getPlayer(member) != null && Objects.requireNonNull(Bukkit.getPlayer(member)).isOnline())
			{
				Player dplayer = Bukkit.getPlayer(member);
				assert dplayer != null;
				ChatManager.sendMessage(dplayer, returnGangPlayerChatMessage(player, message));
			}
	}

	public static void sendGangAllChatMessage(final String name, final String message)
	{
		for (String member : DataManager.getInstance().getLocal(null).getPlayerMembers(name))
			if (Bukkit.getPlayer(member) != null && Objects.requireNonNull(Bukkit.getPlayer(member)).isOnline())
			{
				final Player dplayer = Bukkit.getPlayer(member);
				assert dplayer != null;
				ChatManager.sendMessage(dplayer, returnGangGangChatMessage(message));
			}
	}

	public static void sendAdminMessage(final Player player, final String message)
	{
		final boolean reqRank = DataManager.getInstance().getLocal(player).getRank().equalsIgnoreCase("administrator") ||
			DataManager.getInstance().getLocal(player).getRank().equalsIgnoreCase("moderator") &
					(boolean) FileManager.getInstance().getConfigValue("global.cosmetics.admin-chat-for-moderators");

		if (reqRank)
		{
			if (message.length() < 2)
			{
				ChatManager.sendNotification(player, "Wprowadz swoja wiadomosc, ktora chcesz wyslac na czat administracyjny!", ChatManager.NotificationType.INFORMATION);
				return;
			}

			for (Player onlinePlayer : Bukkit.getOnlinePlayers())
				if (DataManager.getInstance().getLocal(onlinePlayer).getRank().equalsIgnoreCase("administrator") ||
					DataManager.getInstance().getLocal(onlinePlayer).getRank().equalsIgnoreCase("moderator") &
					(boolean) FileManager.getInstance().getConfigValue("global.cosmetics.admin-chat-for-moderators"))
						ChatManager.sendMessage(onlinePlayer, ColorUtil.formatHEX("#fc7474[CZAT ADMINISTRACYJNY] " + ChatLib.getRankInChat(player) + "&d" + ChatLib.returnMarryPrefix(player) + "&r" + ChatLib.getGangInChat(DataManager.getInstance().getLocal(player).getGang()) + "&r" + ChatLib.returnPlayerColor(player) + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c " + message.substring(1)));
		}
		else
			ChatManager.sendNotification(player, "Aby pisac na czacie administracyjnym musisz posiadac range administracyjna!", ChatManager.NotificationType.ERROR);
	}

	public static String isGangColor(final String name, final String color)
	{
		if (getValidGangColor(name).equalsIgnoreCase(color))
			return "#80ff1fTAK";

		return "#fc7474NIE";
	}

	public static String isGangPrefix(final String name, final String prefix)
	{
		if (DataManager.getInstance().getLocal(null).getPrefixes(name).equalsIgnoreCase(prefix))
			return "#80ff1fTAK";

		return "#fc7474NIE";
	}

	public static String isGangStar(final String name)
	{
		if (DataManager.getInstance().getLocal(null).getStar(name))
			return "#80ff1fTAK";

		return "#fc7474NIE";
	}

	public static String isGangChat(final String name)
	{
		if (DataManager.getInstance().getLocal(null).getChat(name))
			return "#80ff1fTAK";

		return "#fc7474NIE";
	}

	public static String isGangFriendlyFire(final String name)
	{
		if (DataManager.getInstance().getLocal(null).getFriendlyFire(name))
			return "#80ff1fTAK";

		return "#fc7474NIE";
	}

	public static String isPlayerColor(final Player player, final String color)
	{
		if (returnPlayerColor(player).equalsIgnoreCase(color))
			return "#80ff1fTAK";

		return "#fc7474NIE";
	}

	public static String isPlayerPremium(final Player player)
	{
		if (ServerLib.getPremiumState(player.getName()))
			return "#80ff1fTAK";

		return "#fc7474NIE";
	}

	public static String getColoredRank(final Player player)
	{
		if (DataManager.getInstance().getLocal(player).getRank().equalsIgnoreCase("administrator"))
			return "#fc7474Administrator";

		return "&7Gracz";
	}

	public static String returnColoredPlayerColor(final String player)
	{
		final String chatColor = DataManager.getInstance().getLocal(null).getChatColor(player);

		if (chatColor.equalsIgnoreCase("red"))
			return "#fc7474Czerwony";
		else if (chatColor.equalsIgnoreCase("blue"))
			return "#3075ffNiebieski";
		else if (chatColor.equalsIgnoreCase("green"))
			return "#02d645Zielony";
		else if (chatColor.equalsIgnoreCase("yellow"))
			return "#fcff33Zolty";
		else if (chatColor.equalsIgnoreCase("white"))
			return "#ffffffBialy";
		else if (chatColor.equalsIgnoreCase("gray"))
			return "#242424Szary";
		else if (chatColor.equalsIgnoreCase("orange"))
			return "#ffb338Pomaranczowy";
		else if (chatColor.equalsIgnoreCase("pink"))
			return "#feb0ffRozowy";

		return "";
	}

	public static ChatColor returnDefinedPlayerColor(final String player)
	{
		final String chatColor = DataManager.getInstance().getLocal(null).getChatColor(player);

		if (chatColor.equalsIgnoreCase("red"))
			return ChatColor.RED;
		else if (chatColor.equalsIgnoreCase("blue"))
			return ChatColor.DARK_AQUA;
		else if (chatColor.equalsIgnoreCase("green"))
			return ChatColor.GREEN;
		else if (chatColor.equalsIgnoreCase("yellow"))
			return ChatColor.YELLOW;
		else if (chatColor.equalsIgnoreCase("white"))
			return ChatColor.WHITE;
		else if (chatColor.equalsIgnoreCase("gray"))
			return ChatColor.DARK_GRAY;
		else if (chatColor.equalsIgnoreCase("orange"))
			return ChatColor.GOLD;
		else if (chatColor.equalsIgnoreCase("pink"))
			return ChatColor.LIGHT_PURPLE;

		return ChatColor.RED;
	}

	public static String getLastMuteColored(final String player)
	{
		final String mute = DataManager.getInstance().getLocal(null).getMute(player);

		if (mute.equalsIgnoreCase("2000/01/01 12:00:00"))
			return "&7Brak";

		return mute;
	}

	public static String getColoredValidGangColor(final String name)
	{
		final String color = DataManager.getInstance().getLocal(null).getColor(name);

		if (color.equalsIgnoreCase("red"))
			return "&cCzerwony";
		else if (color.equalsIgnoreCase("blue"))
			return "&bNiebieski";
		else if (color.equalsIgnoreCase("green"))
			return "&aZielony";
		else if (color.equalsIgnoreCase("yellow"))
			return "&eZolty";
		else if (color.equalsIgnoreCase("white"))
			return "&fBialy";
		else if (color.equalsIgnoreCase("gray"))
			return "&7Szary";
		else if (color.equalsIgnoreCase("orange"))
			return "&6Pomaranczowy";
		else if (color.equalsIgnoreCase("pink"))
			return "&dRozowy";

		return "";
	}

	public static String getColoredGangPrefixes(final String name)
	{
		final String prefixes = DataManager.getInstance().getLocal(null).getPrefixes(name);

		if (prefixes.equalsIgnoreCase("normal"))
			return "Kwadratowe";
		else if (prefixes.equalsIgnoreCase("rounded"))
			return "Zaokraglone";
		else if (prefixes.equalsIgnoreCase("arrows"))
			return "Strzalkowe";

		return "A chuj wie!";
	}

	public static String getRankPrefix(final Player player)
	{
		return DataManager.getInstance().getLocal(player).getRank().substring(0, 3).toUpperCase();
	}

	public static String getRankInChat(final Player player)
	{
		if (!(boolean) FileManager.getInstance().getConfigValue("global.cosmetics.visible-player-rank")) return "";

		return getRankPrefix(player).equalsIgnoreCase("ADM") ? ColorUtil.formatHEX("&c&lADM ") : getRankPrefix(player).equalsIgnoreCase("MOD") ? ColorUtil.formatHEX("&a&lMOD ") : "";
	}

	public static class ColorUtil
	{
		private static final @NotNull Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

		public static String formatHEX(@NotNull String message)
		{
			Matcher matcher = pattern.matcher(message);

			while (matcher.find())
			{
				final String color = message.substring(matcher.start(), matcher.end());

				message = message.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
				matcher = pattern.matcher(message);
			}

			return ChatColor.translateAlternateColorCodes('&', message);
		}
	}
}