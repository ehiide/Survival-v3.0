package mc.server.survival.events;

import mc.server.Logger;
import mc.server.survival.commands.Commands;
import mc.server.survival.managers.ChatManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;

public class CommandPreProcess 
implements Listener
{
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) 
	{
		if (!event.isCancelled())
		{
			final String string = event.getMessage().split(" ")[0];
			final HelpTopic helptopic = Bukkit.getServer().getHelpMap().getHelpTopic(string);
			final Player player = event.getPlayer();

			Logger.getInstance().store(Logger.StoreType.COMMAND, player.getName() + " uzyl komendy " + event.getMessage());

			if (helptopic != null)
			{
				for (String command : Commands.getInstance().getCommandsAliases())
					if (string.equalsIgnoreCase("/" + command))
						return;

				wrongCommand(event, player);
			}
			else
				wrongCommand(event, event.getPlayer());
		}
	}

	private void wrongCommand(PlayerCommandPreprocessEvent event, final Player player)
	{
		ChatManager.sendNotification(player, "O chuj Ci chodzi! Taka komenda nie istnieje!", ChatManager.NotificationType.ERROR);
		event.setCancelled(true);
	}
}