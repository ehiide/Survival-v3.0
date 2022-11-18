package mc.server.survival.managers;

import mc.server.Logger;
import mc.server.survival.commands.Craftingi;
import mc.server.survival.files.Main;
import mc.server.survival.libraries.ItemLib;
import mc.server.survival.worlds.WorldHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager 
{
	private FileManager() {}

	static FileManager instance = new FileManager();
		
	public static FileManager getInstance() 
	{
		return instance;
	}

	public Schematica getSchematica() { return new Schematica(); }

	FileConfiguration playersConfiguration, gangsConfiguration, recipesConfiguration,
					  tradesConfiguration, settingsConfiguration, whitelistConfiguration;
	
	public FileConfiguration players() { return playersConfiguration; }
	public FileConfiguration gangs() { return gangsConfiguration; }
	public FileConfiguration recipes() { return recipesConfiguration; }
	public FileConfiguration settings() { return settingsConfiguration; }
	public FileConfiguration trades() { return tradesConfiguration; }
	public FileConfiguration whitelist() { return whitelistConfiguration; }

	public FileConfiguration logs(final Logger.StoreType storeType)
	{
		FileConfiguration fileConfiguration = null;

		switch (storeType)
		{
			case ADVANCEMENT -> fileConfiguration = logAdvancementConfiguration;
			case CHAT -> fileConfiguration = logChatConfiguration;
			case COMMAND -> fileConfiguration = logCommandConfiguration;
			case JOIN -> fileConfiguration = logJoinConfiguration;
			case LEAVE -> fileConfiguration = logLeaveConfiguration;
			case KILL -> fileConfiguration = logKillConfiguration;
			case DEATH -> fileConfiguration = logDeathConfiguration;
			case CONSUME -> fileConfiguration = logConsumeConfiguration;
			case CRAFT -> fileConfiguration = logCraftConfiguration;
			case ENCHANT -> fileConfiguration = logEnchantConfiguration;
			case PORTAL -> fileConfiguration = logPortalConfiguration;
			case SHOP -> fileConfiguration = logShopConfiguration;
			case SIGN -> fileConfiguration = logSignConfiguration;
		}

		return fileConfiguration;
	}

	File playersFile, gangsFile, recipesFile, tradesFile, settingsFile, whitelistFile;

	File logAdvancementFile, logChatFile, logCommandFile, logJoinFile, logLeaveFile, logKillFile,
			logDeathFile, logConsumeFile, logCraftFile, logEnchantFile, logPortalFile, logShopFile,
			logSignFile;

	FileConfiguration logAdvancementConfiguration, logChatConfiguration, logCommandConfiguration, logJoinConfiguration, logLeaveConfiguration, logKillConfiguration,
			logDeathConfiguration, logConsumeConfiguration, logCraftConfiguration, logEnchantConfiguration, logPortalConfiguration, logShopConfiguration, logSignConfiguration;

	private static int loadedFiles = 0;
	
	public void handleFiles(final Plugin plugin)
	{
		final String dataFolder = plugin.getDataFolder().getPath() + File.separator + "data",
				schematicsFolder = plugin.getDataFolder().getPath() + File.separator + "schematics",
				logsFolder = plugin.getDataFolder().getPath() + File.separator + "logs";

		if (!plugin.getDataFolder().exists())
			plugin.getDataFolder().mkdir();

		if (!new File(dataFolder).exists())
			new File(dataFolder).mkdir();

		if (!new File(schematicsFolder).exists())
			new File(schematicsFolder).mkdir();

		if (!new File(logsFolder).exists())
			new File(logsFolder).mkdir();

		playersFile = new File(dataFolder, "players.yml");
		gangsFile = new File(dataFolder, "gangs.yml");
		recipesFile = new File(dataFolder, "recipes.yml");
		tradesFile = new File(dataFolder, "trades.yml");
		settingsFile = new File(plugin.getDataFolder(), "settings.yml");
		whitelistFile = new File(plugin.getDataFolder(), "whitelist.yml");

		// Logging store files.
		logAdvancementFile = new File(logsFolder, "advancement.yml");
		logChatFile = new File(logsFolder, "chat.yml");
		logCommandFile = new File(logsFolder, "command.yml");
		logJoinFile = new File(logsFolder, "join.yml");
		logLeaveFile = new File(logsFolder, "leave.yml");
		logKillFile = new File(logsFolder, "kill.yml");
		logDeathFile = new File(logsFolder, "death.yml");
		logConsumeFile = new File(logsFolder, "consume.yml");
		logCraftFile = new File(logsFolder, "craft.yml");
		logEnchantFile = new File(logsFolder, "enchant.yml");
		logPortalFile = new File(logsFolder, "portal.yml");
		logSignFile = new File(logsFolder, "sign.yml");
		logShopFile = new File(logsFolder, "shop.yml");

		playersConfiguration = YamlConfiguration.loadConfiguration(playersFile);
		gangsConfiguration = YamlConfiguration.loadConfiguration(gangsFile);
		recipesConfiguration = YamlConfiguration.loadConfiguration(recipesFile);
		tradesConfiguration = YamlConfiguration.loadConfiguration(tradesFile);
		settingsConfiguration = new YamlConfiguration();
		whitelistConfiguration = new YamlConfiguration();

		// Logging store files.
		logAdvancementConfiguration = new YamlConfiguration();
		logChatConfiguration = new YamlConfiguration();
		logCommandConfiguration = new YamlConfiguration();
		logJoinConfiguration = new YamlConfiguration();
		logLeaveConfiguration = new YamlConfiguration();
		logKillConfiguration = new YamlConfiguration();
		logDeathConfiguration = new YamlConfiguration();
		logConsumeConfiguration = new YamlConfiguration();
		logCraftConfiguration = new YamlConfiguration();
		logEnchantConfiguration = new YamlConfiguration();
		logPortalConfiguration = new YamlConfiguration();
		logShopConfiguration = new YamlConfiguration();
		logSignConfiguration = new YamlConfiguration();

        load(playersConfiguration, playersFile, "players.yml");
        load(gangsConfiguration, gangsFile, "gangs.yml");
        load(recipesConfiguration, recipesFile, "recipes.yml");
        load(tradesConfiguration, tradesFile, "trades.yml");
        load(settingsConfiguration, settingsFile, "settings.yml");
        load(whitelistConfiguration, whitelistFile, "whitelist.yml");

		// Logging store files.
		load(logAdvancementConfiguration, logAdvancementFile, "advancement.yml");
		load(logChatConfiguration, logChatFile, "chat.yml");
		load(logCommandConfiguration, logCommandFile, "command.yml");
		load(logJoinConfiguration, logJoinFile, "join.yml");
		load(logLeaveConfiguration, logLeaveFile, "leave.yml");
		load(logKillConfiguration, logKillFile, "kill.yml");
		load(logDeathConfiguration, logDeathFile, "death.yml");
		load(logConsumeConfiguration, logConsumeFile, "consume.yml");
		load(logCraftConfiguration, logCraftFile, "craft.yml");
		load(logEnchantConfiguration, logEnchantFile, "enchant.yml");
		load(logPortalConfiguration, logPortalFile, "portal.yml");
		load(logSignConfiguration, logSignFile, "sign.yml");
		load(logShopConfiguration, logShopFile, "shop.yml");

        checkExist(playersConfiguration, playersFile, FileType.PLAYERS, "players.yml");
        checkExist(gangsConfiguration, gangsFile, FileType.GANGS, "gangs.yml");
        checkExist(recipesConfiguration, recipesFile, FileType.RECIPES, "recipes.yml");
        checkExist(tradesConfiguration, tradesFile, FileType.TRADES, "trades.yml");
		checkExist(whitelistConfiguration, whitelistFile, FileType.WHITELIST, "whitelist.yml");

		// Logging store files.
		checkExist(logAdvancementConfiguration, logAdvancementFile, FileType.LOGS, "advancement.yml");
		checkExist(logChatConfiguration, logChatFile, FileType.LOGS,"chat.yml");
		checkExist(logCommandConfiguration, logCommandFile, FileType.LOGS,"command.yml");
		checkExist(logJoinConfiguration, logJoinFile, FileType.LOGS,"join.yml");
		checkExist(logLeaveConfiguration, logLeaveFile, FileType.LOGS,"leave.yml");
		checkExist(logKillConfiguration, logKillFile, FileType.LOGS,"kill.yml");
		checkExist(logDeathConfiguration, logDeathFile, FileType.LOGS,"death.yml");
		checkExist(logConsumeConfiguration, logConsumeFile, FileType.LOGS,"consume.yml");
		checkExist(logCraftConfiguration, logCraftFile, FileType.LOGS,"craft.yml");
		checkExist(logEnchantConfiguration, logEnchantFile, FileType.LOGS,"enchant.yml");
		checkExist(logPortalConfiguration, logPortalFile, FileType.LOGS,"portal.yml");
		checkExist(logSignConfiguration, logSignFile, FileType.LOGS, "sign.yml");
		checkExist(logShopConfiguration, logShopFile, FileType.LOGS, "shop.yml");

		Main.getInstance().getAdapter().setFiles(loadedFiles);

		if (recipesConfiguration.get("recipes.enabled") == null)
		{
			ArrayList<String> recipeList = new ArrayList<>();
			int[] recipes = Craftingi.pages;

			for (int recipe : recipes) recipeList.add(String.valueOf(recipe));

			recipesConfiguration.set("recipes.enabled", recipeList);
			this.save(FileType.RECIPES);
		}

		if (whitelistConfiguration.get("whitelist") == null)
		{
			whitelistConfiguration.set("whitelist", new ArrayList<String>());
			this.save(FileType.WHITELIST);
		}

		if (!settingsFile.exists())
		{
			plugin.saveResource("settings.yml", false);

			InputStream inputStream = plugin.getResource("settings.yml");
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			settingsConfiguration = YamlConfiguration.loadConfiguration(reader);
		}
	}

	public enum FileType { PLAYERS, GANGS, RECIPES, TRADES, SETTINGS, WHITELIST, LOGS }
		
	public void save(FileType type)
	{
		switch (type)
		{
			case PLAYERS -> {
				try { playersConfiguration.save(playersFile); }
							 catch (IOException e) { Logger.log("Nie udalo sie zapisac bazy danych graczy!"); }
			}

			case GANGS -> {
				try { gangsConfiguration.save(gangsFile); }
						 	catch (IOException e) { Logger.log("Nie udalo sie zapisac bazy danych gangu!"); }
			}

			case RECIPES -> {
				try { recipesConfiguration.save(recipesFile); }
							catch (IOException e) { Logger.log("Nie udalo sie zapisac bazy danych receptur!"); }
			}

			case TRADES -> {
				try { tradesConfiguration.save(tradesFile); }
							catch (IOException e) { Logger.log("Nie udalo sie zapisac bazy danych rynku!"); }
			}

			case SETTINGS -> {
				try { settingsConfiguration.save(settingsFile); }
							catch (IOException e) { Logger.log("Nie udalo sie zapisac pliku konfiguracyjnego!"); }
			}

			case WHITELIST -> {
				try { whitelistConfiguration.save(whitelistFile); }
							catch (IOException e) { Logger.log("Nie udalo sie zapisac whitelisty graczy!"); }
			}
			case LOGS -> {
				try {
					logAdvancementConfiguration.save(logAdvancementFile);
					logChatConfiguration.save(logChatFile);
					logCommandConfiguration.save(logCommandFile);
					logJoinConfiguration.save(logJoinFile);
					logLeaveConfiguration.save(logLeaveFile);
					logKillConfiguration.save(logKillFile);
					logDeathConfiguration.save(logDeathFile);
					logConsumeConfiguration.save(logConsumeFile);
					logCraftConfiguration.save(logCraftFile);
					logEnchantConfiguration.save(logEnchantFile);
					logPortalConfiguration.save(logPortalFile);
					logSignConfiguration.save(logSignFile);
					logShopConfiguration.save(logShopFile);
				}
				catch (IOException e) { Logger.log("Proba zapisania danych przez loggera nie powiodla sie!"); }
			}

			default -> Logger.log("Wystapil blad podczas zapisywania plikow.");
		}
	}

	public void load(FileConfiguration fileConfiguration, File file, String fileName)
    {
        try
        {
            fileConfiguration.load(file); loadedFiles++;
        }
        catch (IOException | InvalidConfigurationException e)
        {
            Logger.log("Nie udalo sie odnalezc pliku " + fileName + ", za chwile nastapi proba jego stworzenia!");
        }
    }

    public void checkExist(FileConfiguration fileConfiguration, File file, FileType fileType, String fileName)
    {
        if (!file.exists())
        {
            try
            {
                file.createNewFile();

                try
                {
                    fileConfiguration.load(file);
                }
                catch (InvalidConfigurationException e)
                {
                    Logger.log("Nie udalo sie zaladowac pliku " + fileName + ", prosimy o zresetowanie pliku!");
                }
            }
            catch (IOException e) { Logger.log("Nie udalo sie stworzyc pliku " + fileName + "!"); }

			this.save(fileType);
        }
    }

	public Object getConfigValue(final String path)
	{
		try
		{
			return settingsConfiguration.get(path);
		}
		catch (Exception e)
		{
			Logger.asyncLog("&cWystapil problem z wczytaniem pliku konfiguracyjnego. Usun plik i pozwol stworzyc nowy!");
		}

		return null;
	}

	public class Schematica
	{
		public Schematica() {}

		public void handleSchematic(final Plugin plugin, final String name)
		{
			final String fullName = name.toUpperCase() + ".schematica";
			final File file = new File(plugin.getDataFolder().getPath() + File.separator + "schematics", fullName);

			if (!file.exists())
				plugin.saveResource("schematics" + File.separator + fullName, false);
		}

		public void handleSchematics(final Plugin plugin)
		{
			List<String> schematics = new ArrayList<>();

			schematics.add("aether_flower_1");
			schematics.add("aether_flower_2");
			schematics.add("aether_flower_3");
			schematics.add("aether_tree_1");
			schematics.add("aether_tree_2");
			schematics.add("aether_tree_3");
			schematics.add("aether_stone");
			schematics.add("aether_pantheon");
			schematics.add("aether_sacrum");
			schematics.add("aether_fallen_tree");

			schematics.add("twilight_tree_1");
			schematics.add("twilight_tree_2");
			schematics.add("twilight_bush_1");
			schematics.add("twilight_bush_2");
			schematics.add("twilight_mushroom_1");
			schematics.add("twilight_mushroom_2");
			schematics.add("twilight_ship");
			schematics.add("twilight_tower");

			for (final String schematic : schematics)
				handleSchematic(plugin, schematic);

			Main.getInstance().getAdapter().setSchematics(schematics.size());
		}

		public void createSchematic(final String name, final List<Block> blocks, final Location location)
		{
			final File file = new File(Main.getInstance().getDataFolder().getPath() + File.separator + "schematics", name.toUpperCase() + ".schematica");

			try
			{
				file.createNewFile();

				FileConfiguration fileConfiguration = new YamlConfiguration();

				final int baseX = location.getBlockX();
				final int baseY = location.getBlockY();
				final int baseZ = location.getBlockZ();

				for (final Block block : blocks)
					if (!block.getType().isAir())
					{
						final int x = block.getLocation().getBlockX() - baseX;
						final int y = block.getLocation().getBlockY() - baseY;
						final int z = block.getLocation().getBlockZ() - baseZ;

						final String blockData = block.getBlockData().getAsString();
						final String data = blockData.replace("minecraft:", "");

						if (block.getType() == Material.CHEST)
							fileConfiguration.set(x + "/" + y + "/" + z, "chest[type=default]");
						else if (block.getType() == Material.SPAWNER)
							fileConfiguration.set(x + "/" + y + "/" + z, "chest[entity=pig]");
						else
							fileConfiguration.set(x + "/" + y + "/" + z, data);
					}

				fileConfiguration.save(file);
			}
			catch (IOException ignored) {}
		}

		public boolean loadSchematic(final String name, final Location location)
		{
			final File file = new File(Main.getInstance().getDataFolder().getPath() + File.separator + "schematics", name.toUpperCase() + ".schematica");
			final boolean fileExists = file.exists();

			if (fileExists)
			{
				try
				{
					final FileConfiguration fileConfiguration = new YamlConfiguration();
					fileConfiguration.load(file);

					for (final String locations : fileConfiguration.getKeys(false))
					{
						final String[] coords = locations.split("/");
						final String values = ((String) fileConfiguration.get(locations));

						final boolean hasData = values.contains("=");
						final Material material = hasData ? Material.valueOf(values.split("\\[")[0].toUpperCase()) : Material.valueOf(values.toUpperCase());

						final int x = Integer.parseInt(coords[0]);
						final int y = Integer.parseInt(coords[1]);
						final int z = Integer.parseInt(coords[2]);

						final int finalX = location.getBlockX() + x;
						final int finalY = location.getBlockY() + y;
						final int finalZ = location.getBlockZ() + z;

						final Block block = location.getWorld().getBlockAt(finalX, finalY, finalZ);

						if (!block.getType().isSolid())
						{
							block.setType(material, false);

							if (material == Material.CHEST)
							{
								final String data = values.split("\\[")[1];

								WorldHandler.getInstance().getLootChestEditor().handleChest(block, WorldHandler.getInstance().getLootChestEditor().of(data.substring(5, data.length() - 1)));
							}
							else if (material == Material.SPAWNER)
							{
								final String data = values.split("\\[")[1];

								WorldHandler.getInstance().getBlockManipulator().setSpawnerEntity(block, ItemLib.getEntityTypeFromString(data.substring(7, data.length() - 1)));
							}
							else if (hasData)
							{
								final String data = values.split("\\[")[1];

								block.setBlockData(material.createBlockData("[" + data));
								block.getState().update(false, false);
							}
						}
					}
				}
				catch (IOException | InvalidConfigurationException | NullPointerException ignored) {}
			}

			return fileExists;
		}

		public boolean removeSchematic(final String name)
		{
			final File file = new File(Main.getInstance().getDataFolder().getPath() + File.separator + "schematics", name.toUpperCase() + ".schematica");
			final boolean fileExists = file.exists();

			if (fileExists) file.delete();

			return fileExists;
		}
	}
}