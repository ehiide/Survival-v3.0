package mc.server.survival.managers;

import mc.server.survival.files.Main;
import mc.server.survival.libraries.ChatLib;
import mc.server.survival.libraries.VisualLib;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ChatManager 
{
	public static void sendMessage(final Player player, String message)
	{
		player.sendMessage(ChatLib.ColorUtil.formatHEX(message));
	}

	public static void sendMessages(final Player player, String... message)
	{
		for (String line : message)
			player.sendMessage(ChatLib.ColorUtil.formatHEX(line));
	}

	public static void sendConsoleMessage(String message)
	{
		Main.getInstance().getServer().getConsoleSender().sendMessage(ChatLib.ColorUtil.formatHEX(message));
	}

	public static void sendConsoleMessages(String... message)
	{
		for (String line : message)
			sendConsoleMessage(line);
	}

	public static void sendAdminMessage(final String message)
	{
		for (Player player : Bukkit.getOnlinePlayers())
			if (DataManager.getInstance().getLocal(player).get(FileManager.getInstance().players(), player.getName().toLowerCase()) != null)
				if (DataManager.getInstance().getLocal(player).getRank().equalsIgnoreCase("administrator"))
					sendMessage(player, message);
	}

	public enum NotificationType
	{
		SUCCESS, ERROR, INFORMATION, LOGGING;
	}

	private static final boolean disableCustomSounds = (boolean) FileManager.getInstance().getConfigValue("global.survival.disable-custom-sounds");

	public static void sendNotification(final Player player, final String notify, final NotificationType type)
	{
		final String notification;

		switch (type)
		{
			case SUCCESS -> notification = "#80ff1f" + notify;
			case ERROR -> notification = "#fc7474" + notify;
			case INFORMATION -> notification = "#8c8c8c" + notify;
			case LOGGING -> notification = "&e" + notify;
			default -> notification = notify;
		}

		if (type != NotificationType.LOGGING || !((boolean) FileManager.getInstance().getConfigValue("function.authorization.hide-notifications")))
			sendMessage(player, (type == NotificationType.LOGGING ? (String) FileManager.getInstance().getConfigValue("global.survival.prefixes.logging") : (String) FileManager.getInstance().getConfigValue("global.survival.prefixes.chat")) + notification);

		if (type == NotificationType.SUCCESS)
			player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 4, disableCustomSounds ? 0 : 4);
		else if (type == NotificationType.ERROR)
		{
			VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
			player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 4, disableCustomSounds ? 0 : 4);
		}
		else if (type == NotificationType.LOGGING)
			VisualLib.showDelayedTitle(player, "#fff203⌛", "", 0, 20, 20);
	}
}