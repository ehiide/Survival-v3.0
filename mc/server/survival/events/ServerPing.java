package mc.server.survival.events;

import mc.server.Logger;
import mc.server.survival.managers.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.io.File;

public class ServerPing 
implements Listener
{
	private final int maxPlayers = (int) FileManager.getInstance().getConfigValue("global.server.max-players");
	private final String serverIcon = (String) FileManager.getInstance().getConfigValue("global.server.server-icon");
	private final String serverMotd = (String) FileManager.getInstance().getConfigValue("global.server.server-motd");

	private final boolean iconChecker = (boolean) FileManager.getInstance().getConfigValue("global.survival.disable-icon-checker");

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvent(ServerListPingEvent event)
	{
		event.setMaxPlayers(maxPlayers);
		event.setMotd(ChatColor.translateAlternateColorCodes('&', serverMotd));
		
		try 
		{
			if (iconChecker) event.setServerIcon(Bukkit.loadServerIcon(new File(serverIcon)));
		} 
		catch (Exception ignored)
		{
			Logger.asyncLog("&cWystapil problem ze znalezieniem pliku ikony serwera!");
		}

		event.setServerIcon(Bukkit.getServerIcon());
	}
}