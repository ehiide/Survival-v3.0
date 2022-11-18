package mc.server;

import mc.server.survival.libraries.ChatLib;
import mc.server.survival.managers.FileManager;
import org.bukkit.Bukkit;

public class Broadcaster 
{
	private Broadcaster() {}

	static Broadcaster instance = new Broadcaster();

	public static Broadcaster getInstance()
	{
		return instance;
	}

	public Announcer getAnnouncer() { return new Announcer(); }

	public static void broadcastMessage(final String message)
	{
		Bukkit.broadcastMessage(ChatLib.ColorUtil.formatHEX(message));
	}

	public static void broadcastMessages(String... messages)
	{
		for (String verse : messages)
			broadcastMessage(verse);
	}
	
	private static int variant = 0;
	
	public static void scheduleGlobalMessages()
	{
		variant++;

		if (variant == 1)
			Broadcaster.broadcastMessages(" ", "#f8ff26█&f███#f8ff26█", "#f8ff26███&f█#f8ff26█           &f&l<#f8ff26&l!&f&l> #f8ff26&lCZY WIEDZIALES ZE &f&l<#f8ff26&l!&f&l>",
					"#f8ff26██&f█#f8ff26██       &fMozesz pisac we wszystkich kolorach?", "#f8ff26█████   &7&o(Koniecznie zapoznaj sie z komenda #ffc936&o/paleta&7&o)", "#f8ff26██&f█#f8ff26██", "");
		else if (variant == 2)
			Broadcaster.broadcastMessages(" ", "#f8ff26█&f███#f8ff26█", "#f8ff26███&f█#f8ff26█           &f&l<#f8ff26&l!&f&l> #f8ff26&lCZY WIEDZIALES ZE &f&l<#f8ff26&l!&f&l>",
					"#f8ff26██&f█#f8ff26██   &fMasz mozliwosc ustawienia wlasnego domku?", "#f8ff26█████ &7&o(Opis dzialania znajdziesz pod komenda #ffc936&o/dom&7&o)", "#f8ff26██&f█#f8ff26██", "");
		else if (variant == 3)
			Broadcaster.broadcastMessages(" ", "#f8ff26█&f███#f8ff26█", "#f8ff26███&f█#f8ff26█           &f&l<#f8ff26&l!&f&l> #f8ff26&lCZY WIEDZIALES ZE &f&l<#f8ff26&l!&f&l>",
					"#f8ff26██&f█#f8ff26██      &fMozesz wysylac wiadomosci prywatne?", "#f8ff26█████ &7&o(Potrzebujesz wpisac tylko komende #ffc936&o/wiadomosc&7&o)", "#f8ff26██&f█#f8ff26██", "");
		else if (variant == 4)
			Broadcaster.broadcastMessages(" ", "#f8ff26█&f███#f8ff26█", "#f8ff26███&f█#f8ff26█           &f&l<#f8ff26&l!&f&l> #f8ff26&lCZY WIEDZIALES ZE &f&l<#f8ff26&l!&f&l>",
					"#f8ff26██&f█#f8ff26██  &fJest metoda na przelanie komus swoich monet?", "#f8ff26█████ &7&o(Przelewy wykonasz za pomoca funkcji #ffc936&o/zaplac&7&o)", "#f8ff26██&f█#f8ff26██", "");
		else if (variant == 5)
			Broadcaster.broadcastMessages(" ", "#f8ff26█&f███#f8ff26█", "#f8ff26███&f█#f8ff26█           &f&l<#f8ff26&l!&f&l> #f8ff26&lCZY WIEDZIALES ZE &f&l<#f8ff26&l!&f&l>",
					"#f8ff26██&f█#f8ff26██  &fIstnieja dodatkowe receptury na przedmioty?", "#f8ff26█████   &7&o(A je wszystkie skrywa komenda #ffc936&o/craftingi&7&o)", "#f8ff26██&f█#f8ff26██", "");
		else if (variant == 6)
			Broadcaster.broadcastMessages(" ", "#f8ff26█&f███#f8ff26█", "#f8ff26███&f█#f8ff26█           &f&l<#f8ff26&l!&f&l> #f8ff26&lCZY WIEDZIALES ZE &f&l<#f8ff26&l!&f&l>",
					"#f8ff26██&f█#f8ff26██ &fTwoje itemy moga byc przechowywane w schowku?", "#f8ff26█████    &7&o(Zapnij wiec rozporek i wpisuj #ffc936&o/schowek&7&o)", "#f8ff26██&f█#f8ff26██", "");
		else if (variant == 7)
			Broadcaster.broadcastMessages(" ", "#f8ff26█&f███#f8ff26█", "#f8ff26███&f█#f8ff26█           &f&l<#f8ff26&l!&f&l> #f8ff26&lCZY WIEDZIALES ZE &f&l<#f8ff26&l!&f&l>",
					"#f8ff26██&f█#f8ff26██    &fTwoj schowek moze zostac powiekszony?", "#f8ff26█████       &7&o(Po prostu wpisz #ffc936&o/schowek ulepsz&7&o)", "#f8ff26██&f█#f8ff26██", "");
		else if (variant == 8)
			Broadcaster.broadcastMessages(" ", "#f8ff26█&f███#f8ff26█", "#f8ff26███&f█#f8ff26█           &f&l<#f8ff26&l!&f&l> #f8ff26&lCZY WIEDZIALES ZE &f&l<#f8ff26&l!&f&l>",
					"#f8ff26██&f█#f8ff26██    &fMozesz zakupic przedmioty, bloki & dodatki?", "#f8ff26█████   &7&o(To wszystko czeka na ciebie tutaj - #ffc936&o/sklep&7&o)", "#f8ff26██&f█#f8ff26██", "");
		else if (variant == 9)
			Broadcaster.broadcastMessages(" ", "#f8ff26█&f███#f8ff26█", "#f8ff26███&f█#f8ff26█           &f&l<#f8ff26&l!&f&l> #f8ff26&lCZY WIEDZIALES ZE &f&l<#f8ff26&l!&f&l>",
					"#f8ff26██&f█#f8ff26██ &fMozesz sprawdzic swoj lub kogos aktualny ping?", "#f8ff26█████  &7&o(Jak pewnie sie domyslasz.. tak.. komenda #ffc936&o/ping&7&o)", "#f8ff26██&f█#f8ff26██", "");
		else if (variant == 10)
			Broadcaster.broadcastMessages(" ", "#f8ff26█&f███#f8ff26█", "#f8ff26███&f█#f8ff26█           &f&l<#f8ff26&l!&f&l> #f8ff26&lCZY WIEDZIALES ZE &f&l<#f8ff26&l!&f&l>",
					"#f8ff26██&f█#f8ff26██        &fMozesz sprawdzic swoje statysyki?", "#f8ff26█████   &7&o(Dane i ulepszenia tylko tutaj! wpisz #ffc936&o/postac&7&o)", "#f8ff26██&f█#f8ff26██", "");
		else if (variant == 11)
			Broadcaster.broadcastMessages(" ", "#f8ff26█&f███#f8ff26█", "#f8ff26███&f█#f8ff26█           &f&l<#f8ff26&l!&f&l> #f8ff26&lCZY WIEDZIALES ZE &f&l<#f8ff26&l!&f&l>",
					"#f8ff26██&f█#f8ff26██ &fMozesz przeteleportowac sie do innego gracza?", "#f8ff26█████  &7&o(Wiem ze znasz ta komende ale trzymaj, #ffc936&o/tpa&7&o)", "#f8ff26██&f█#f8ff26██", "");
		else if (variant == 12)
			Broadcaster.broadcastMessages(" ", "#f8ff26█&f███#f8ff26█", "#f8ff26███&f█#f8ff26█           &f&l<#f8ff26&l!&f&l> #f8ff26&lCZY WIEDZIALES ZE &f&l<#f8ff26&l!&f&l>",
					"#f8ff26██&f█#f8ff26██         &fPotrafimy pomagac tutaj debilom?", "#f8ff26█████    &7&o(Wystarczy tylko wpisac komende #ffc936&o/pomoc&7&o)", "#f8ff26██&f█#f8ff26██", "");
		else if (variant == 13)
			Broadcaster.broadcastMessages(" ", "#f8ff26█&f███#f8ff26█", "#f8ff26███&f█#f8ff26█           &f&l<#f8ff26&l!&f&l> #f8ff26&lCZY WIEDZIALES ZE &f&l<#f8ff26&l!&f&l>",
					"#f8ff26██&f█#f8ff26██ &fZakochani moga wziac slub w tutejszym kosciele?", "#f8ff26█████  &7&o(Jedyne czego potrzebujesz to wpisanie #ffc936&o/slub&7&o)", "#f8ff26██&f█#f8ff26██", "");
		else if (variant == 14)
			Broadcaster.broadcastMessages(" ", "#f8ff26█&f███#f8ff26█", "#f8ff26███&f█#f8ff26█           &f&l<#f8ff26&l!&f&l> #f8ff26&lCZY WIEDZIALES ZE &f&l<#f8ff26&l!&f&l>",
					"#f8ff26██&f█#f8ff26██        &fMozesz zalozyc swoj wlasny gang?", "#f8ff26█████ &7&o(Nie zgadniesz jaka komenda do tego sluzy.. #ffc936&o/gang&7&o)", "#f8ff26██&f█#f8ff26██", "");
		else if (variant == 15)
			Broadcaster.broadcastMessages(" ", "#f8ff26█&f███#f8ff26█", "#f8ff26███&f█#f8ff26█           &f&l<#f8ff26&l!&f&l> #f8ff26&lCZY WIEDZIALES ZE &f&l<#f8ff26&l!&f&l>",
					"#f8ff26██&f█#f8ff26██  &fMozesz wykonywac wysoko nagradzone questy?", "#f8ff26█████    &7&o(Zbior zadan znajdziesz tutaj - #ffc936&o/questy&7&o)", "#f8ff26██&f█#f8ff26██", "");
		else if (variant == 16)
			Broadcaster.broadcastMessages(" ", "#f8ff26█&f███#f8ff26█", "#f8ff26███&f█#f8ff26█           &f&l<#f8ff26&l!&f&l> #f8ff26&lCZY WIEDZIALES ZE &f&l<#f8ff26&l!&f&l>",
					"#f8ff26██&f█#f8ff26██ &fMozesz pocalowac swojego partnera/partnerke?", "#f8ff26█████   &7&o(Wystarczy sie przyblizyc i przytrzymac PPM)", "#f8ff26██&f█#f8ff26██", "");
		else if (variant == 17)
			Broadcaster.broadcastMessages(" ", "#f8ff26█&f███#f8ff26█", "#f8ff26███&f█#f8ff26█           &f&l<#f8ff26&l!&f&l> #f8ff26&lCZY WIEDZIALES ZE &f&l<#f8ff26&l!&f&l>",
					"#f8ff26██&f█#f8ff26██ &fMozesz sprawdzic kto jest z kim w zwiazku?", "#f8ff26█████   &7&o(Po prostu wpisz komende #ffc936&o/slub status&7&o)", "#f8ff26██&f█#f8ff26██", "");
		else if (variant == 18)
			Broadcaster.broadcastMessages(" ", "#f8ff26█&f███#f8ff26█", "#f8ff26███&f█#f8ff26█           &f&l<#f8ff26&l!&f&l> #f8ff26&lCZY WIEDZIALES ZE &f&l<#f8ff26&l!&f&l>",
					"#f8ff26██&f█#f8ff26██ &fMozesz wystawic przedmiot na czarny rynek?", "#f8ff26█████     &7&o(Po prostu wpisz komende #ffc936&o/wystaws&7&o)", "#f8ff26██&f█#f8ff26██", "");
		else if (variant == 19)
		{
			variant = 0;
			scheduleGlobalMessages();
		}
	}

	public class Announcer
	{
		private static final boolean advancements = (boolean) FileManager.getInstance().getConfigValue("global.cosmetics.announce-advancements");
		private static final boolean deaths = (boolean) FileManager.getInstance().getConfigValue("global.cosmetics.announce-deaths");
		private static final boolean kills = (boolean) FileManager.getInstance().getConfigValue("global.cosmetics.announce-kills");
		private static final boolean join = (boolean) FileManager.getInstance().getConfigValue("global.cosmetics.announce-player-join");
		private static final boolean quit = (boolean) FileManager.getInstance().getConfigValue("global.cosmetics.announce-player-quit");
		private static final boolean quests = (boolean) FileManager.getInstance().getConfigValue("global.cosmetics.announce-quests");

		public Announcer() {}

		public boolean announceAdvancements() { return advancements; }
		public boolean announceDeaths() { return deaths; }
		public boolean announceKills() { return kills; }
		public boolean announcePlayerJoin() { return join; }
		public boolean announcePlayerQuit() { return quit; }
		public boolean announceQuests() { return quests; }
	}
}