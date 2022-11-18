package mc.server.survival.events;

import mc.server.Broadcaster;
import mc.server.Logger;
import mc.server.survival.files.Main;
import mc.server.survival.libraries.QuestLib;
import mc.server.survival.libraries.ScoreboardLib;
import mc.server.survival.libraries.TimeLib;
import mc.server.survival.libraries.VisualLib;
import mc.server.survival.libraries.chemistry.ChemistryCore;
import mc.server.survival.libraries.chemistry.ChemistryEffect;
import mc.server.survival.managers.DataManager;
import mc.server.survival.managers.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoin 
implements Listener
{
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvent(PlayerJoinEvent event)
	{
		event.setJoinMessage(null);
		
		final Player player = event.getPlayer();

		player.setTicksLived(1);
		player.setWalkSpeed(0.2F);

		if ((boolean) FileManager.getInstance().getConfigValue("function.chemistry.status"))
			ChemistryCore.apply(player);

		ScoreboardLib.getInstance().reloadContents(player);

		player.setMaxHealth(20.0 + (4.0D * DataManager.getInstance().getLocal(player).getUpgradeLevel(player.getName(), "vitality")));

		if (TimeLib.getDifferenceInMinutes(DataManager.getInstance().getLocal(player).getLogged()) > 60*24*7)
			QuestLib.manageQuest(player, 10);

		for (Player onlinePlayer : Bukkit.getOnlinePlayers())
			if (DataManager.getInstance().getLocal(player).getVisibility())
				onlinePlayer.showPlayer(Main.getInstance(), player);
			else
				onlinePlayer.hidePlayer(Main.getInstance(), player);

		Logger.getInstance().store(Logger.StoreType.JOIN, player.getName() + " dolaczyl(a) na serwer");
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvent(PlayerQuitEvent event)
	{
		event.setQuitMessage(null);
		
		final Player player = event.getPlayer();

		if (player.getTicksLived() < 40) return;

		if (Broadcaster.getInstance().getAnnouncer().announcePlayerQuit())
			Broadcaster.broadcastMessage(FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "Gracz " + player.getName() + " opuscil serwer!");

		if ((boolean) FileManager.getInstance().getConfigValue("visual.player.firework-when-enter"))
			VisualLib.spawnFirework(player.getLocation());

		ChemistryEffect.cancelTasks(player);

		Logger.getInstance().store(Logger.StoreType.LEAVE, player.getName() + " opuscil(a) serwer");
	}
}