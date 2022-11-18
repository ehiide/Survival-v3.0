package mc.server;

import mc.server.survival.libraries.TaskLib;
import mc.server.survival.libraries.TimeLib;
import mc.server.survival.managers.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Logger 
{
	private Logger() {}

	static Logger instance = new Logger();

	public static Logger getInstance() { return instance; }

	public static void log(final String log)
	{
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "[Survival] " + log));
	}
	
	public static void asyncLog(final String log)
	{
		TaskLib.getInstance().runAsync(() -> log(log));
	}

	public enum StoreType
	{
		ADVANCEMENT, CHAT, COMMAND, CONSUME, CRAFT, DEATH, ENCHANT, JOIN, KILL, LEAVE, PORTAL, SHOP, SIGN
	}

	public void store(final StoreType storeType, final String inputData)
	{
		TaskLib.getInstance().runAsync(() -> {

			if ((boolean) FileManager.getInstance().getConfigValue("global.logger." + storeType.toString().toLowerCase()))
			{
				FileManager.getInstance().logs(storeType).set(TimeLib.date(), inputData);
				FileManager.getInstance().save(FileManager.FileType.LOGS);
			}
		});
	}

	public void resetLogs(final StoreType storeType)
	{
		TaskLib.getInstance().runAsync(() -> {

			if ((boolean) FileManager.getInstance().getConfigValue("global.logger." + storeType.toString().toLowerCase()))
			{
				for (final String key : FileManager.getInstance().logs(storeType).getKeys(false))
					FileManager.getInstance().logs(storeType).set(key, null);

				FileManager.getInstance().save(FileManager.FileType.LOGS);
			}
		});
	}
}