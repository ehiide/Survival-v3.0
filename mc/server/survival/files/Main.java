package mc.server.survival.files;

import mc.server.Broadcaster;
import mc.server.survival.commands.*;
import mc.server.survival.events.*;
import mc.server.survival.libraries.EntityLib;
import mc.server.survival.libraries.RecipeLib;
import mc.server.survival.libraries.ScoreboardLib;
import mc.server.survival.libraries.TaskLib;
import mc.server.survival.libraries.chemistry.ChemistryCore;
import mc.server.survival.libraries.trading.Trading;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import mc.server.survival.managers.FileManager;
import mc.server.survival.worlds.WorldHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;

public class Main
extends JavaPlugin
{
	private static Main instance;
	
	public static Main getInstance()
	{
		return instance;
	}

	public Adapter getAdapter() { return new Adapter(); }

	public static String @NotNull [] AUTHORS = {"Eh1de", "schiziss", "misspill", "ProseczeqPL"};
	public static @NotNull String VERSION = "INFDEV3-9";

	private final @NotNull PluginManager plugin = getServer().getPluginManager();
	private static int registeredEvents, registeredCommands;

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String template)
	{
		if (template == null) template = "default"; return worldName.equalsIgnoreCase("survival_aether") ?
															WorldHandler.getInstance().getAetherDimension(template) :
															WorldHandler.getInstance().getTwilightDimension(template);
	}
	
	private void registerEvent(final @NotNull Listener event) { plugin.registerEvents(event, this); registeredEvents++; }

	private void registerEvents()
	{
		final Listener[] listeners = {new ServerPing(), new EntityDamageByEntity(), new AsyncChat(), new PlayerJoin(),
				new Inventory(), new PlayerDeath(), new BlockBreak(), new CommandPreProcess(), new Portal(),
				new PlayerInteract(), new Respawn(), new ItemDrop(), new EntityInteract(), new PrepareAnvil(),
				new ItemConsume(), new PlayerItemDamage(), new EntityDeath(), new PlayerFish(), new PlayerTeleport(),
				new ItemMerge(), new ItemThrow(), new Explode(), new Dismount(), new RegainHealth(), new PlayerDamage(),
				new PlayerLogin(), new PlayerPreLogin(), new EnchantItem(), new PlayerMove(), new MobSpawn(),
				new Advancement(), new CommandSend(), new BlockDropItem(), new BlockPlace(), new ChunkLoad(),
				new SignChange(), new ItemCraft()};

		Arrays.stream(listeners).forEach(this::registerEvent);
		getAdapter().setEvents(registeredEvents);
	}
	
	private void registerCommand(final @NotNull String name, final @NotNull Object command)
	{
		final PluginCommand pluginCommand = getCommand(name);

		if (!Commands.getInstance().getReader(name).isAvailable())
		{
			pluginCommand.setAliases(Collections.EMPTY_LIST);
			pluginCommand.setExecutor(null);
			pluginCommand.setTabCompleter(null);
			pluginCommand.setUsage("");
			return;
		}

		pluginCommand.setExecutor((CommandExecutor) command); registeredCommands++;

		if (command instanceof TabCompleter)
			pluginCommand.setTabCompleter((TabCompleter) command);
	}
	
	private void registerCommands() 
	{
		final String[] commandNames = {"dom", "wiadomosc", "odpowiedz", "schowek", "pomoc", "ping", "craftingi",
									   "zaplac", "paleta", "tpa", "sklep", "slub", "gang", "ip", "mute", "unmute",
									   "kick", "powiedz", "postac", "questy", "whitelist", "ranga", "quicksave",
									   "wystaw", "invsee", "ecsee", "crash", "tps", "ram", "vanish", "ban",
									   "unban", "heal", "feed", "gamemode", "tp", "flight", "ogloszenie",
									   "controller", "weather", "time"};

		final Object[] commandClass = {new Dom(), new Wiadomosc(), new Odpowiedz(), new Schowek(), new Pomoc(),
									   new Ping(), new Craftingi(), new Zaplac(), new Paleta(), new TPA(), new Sklep(),
									   new Slub(), new Gang(), new IP(), new Mute(), new Unmute(), new Kick(),
									   new Powiedz(), new Postac(), new Questy(), new Whitelist(), new Ranga(),
									   new Quicksave(), new Wystaw(), new Invsee(), new Ecsee(), new Crash(),
									   new TPS(), new RAM(), new Vanish(), new Ban(), new Unban(), new Heal(),
									   new Feed(), new Gamemode(), new TP(), new Flight(), new Ogloszenie(),
									   new Controller(), new Weather(), new Time()};

		for (int i = 0; i < commandNames.length; i++)
			registerCommand(commandNames[i], commandClass[i]);

		getAdapter().setCommands(registeredCommands);
	}
	
	@Override
	public void onEnable() 
	{
		instance = this;
		FileManager.getInstance().handleFiles(this);
		FileManager.getInstance().getSchematica().handleSchematics(this);

		WorldHandler.getInstance().loadWorlds();
		EntityLib.getInstance().removeEntities();
		RecipeLib.getInstance().reloadRecipes();

		if ((boolean) FileManager.getInstance().getConfigValue("function.lag-prevention-machine"))
			WorldHandler.getInstance().runLPM();

		if ((boolean) FileManager.getInstance().getConfigValue("visual.miscellaneous.falling-leaves"))
		{
			final WorldHandler.ChunkHandler chunkHandler = WorldHandler.getInstance().getChunkHandler();

			chunkHandler.reloadLeafChunks();

			if (Bukkit.getOnlinePlayers().size() != 0)
				chunkHandler.getLoadedLeafChunks().forEach(chunkHandler::handleLeafChunk);

			chunkHandler.handleLeaves();
		}

		for (final Player player : Bukkit.getOnlinePlayers())
		{
			TaskLib.getInstance().runAsync(() -> DataManager.getInstance().getLocal(player).handlePlayer());
			player.updateCommands();

			ChemistryCore.apply(player);
			ScoreboardLib.getInstance().clearData(player);
			ScoreboardLib.getInstance().reloadContents(player);
		}

		TaskLib.getInstance().runAsync(() -> DataManager.getInstance().getLocal(null).handleGangs());

		if ((boolean) FileManager.getInstance().getConfigValue("function.chemistry.status"))
			ChemistryCore.schedule();

		Trading.scheduleTrades();

		registerEvents();
		registerCommands();

		TaskLib.getInstance().runAsyncTimer(Broadcaster::scheduleGlobalMessages, 3600);

		ChatManager.sendConsoleMessages(
				"&7",
				"&eSurvival Core zostal uruchomiony.",
				"&ePonizej znajduja sie informacje dotyczace pluginu oraz serwera.",
				"&eWskazowki znajdziesz pod komenda /pomoc.",
				"&7",
				"Minecraft:",
				"  - Wersja serwera: &a" + Minecraft.getInstance().getBukkitVersion(this.getServer()),
				"  - Wersja natywna: &a" + Minecraft.getInstance().getVersion(this.getServer()),
				"  - Protokoly: " + (Minecraft.getInstance().benchmarkProtocols(this.getServer()) ? "&awspieraja wersje " : "&cnie wspieraja wersji") + Minecraft.getInstance().getVersion(this.getServer()),
				"&7",
				"Serwer:",
				"  - Limit pamieci operacyjnej: " + Runtime.getRuntime().maxMemory() / 1048576 + "MB",
				"  - Przydzielone watki procesora: " + Runtime.getRuntime().availableProcessors(),
				"  - Dedykowany system operacyjny: " + System.getProperty("os.name") + " " + System.getProperty("os.version") + " " + System.getProperty("os.arch"),
				"",
				"  - Wersja systemu: " + VERSION,
				"  - Autoryzacja graczy: " + (Bukkit.getOnlineMode() ? "&cwylaczona &7(tryb online)" : "&awlaczona &7(tryb offline)"),
				"&7",
				"Survival:",
				"  - Swiaty: &a" + getAdapter().getWorlds(),
				"  - Komendy: &a" + getAdapter().getCommands(),
				"  - Eventy: &a" + getAdapter().getEvents(),
				"  - Receptury: &a" + getAdapter().getCraftings(),
				"  - Pliki serwera: &a" + getAdapter().getFiles(),
				"  - Pliki schematyczne: &a" + getAdapter().getSchematics(),
				"&7",
				"&ePlugin tworzony z sercem przez @realehide i @bwnation",
				"&7"
		);
	}
	
	@Override
	public void onDisable()
	{
		final BukkitScheduler bukkitScheduler = getServer().getScheduler();

		bukkitScheduler.cancelTasks(this);
		bukkitScheduler.getPendingTasks().forEach(BukkitTask::cancel);
	}

	public class Adapter
	{
		private static int worlds = 0, commands = 0, events = 0, craftings = 0, files = 0, schematics = 0;

		public Adapter() {}

		public int getWorlds() { return worlds; }

		public void setWorlds(final int worlds) { this.worlds = worlds; }

		public int getCommands() { return commands; }

		public void setCommands(final int commands) { this.commands = commands; }

		public int getEvents() { return events; }

		public void setEvents(final int events) { this.events = events; }

		public int getCraftings() { return craftings; }

		public void setCraftings(final int craftings) { this.craftings = craftings; }

		public int getFiles() { return files; }

		public void setFiles(final int files) { this.files = files; }

		public int getSchematics() { return schematics; }

		public void setSchematics(final int schematics) { this.schematics = schematics; }
	}
}