package mc.server.survival.events;

import mc.server.Broadcaster;
import mc.server.Logger;
import mc.server.survival.libraries.ItemLib;
import mc.server.survival.libraries.QuestLib;
import mc.server.survival.libraries.ScoreboardLib;
import mc.server.survival.libraries.TaskLib;
import mc.server.survival.managers.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerDeath implements Listener
{
	@EventHandler
	public void onEvent(PlayerDeathEvent event)
	{
		event.setDeathMessage(null);

		final Player death = event.getEntity();
		
		if (event.getEntity().getKiller() != null)
		{
			final Player killer = event.getEntity().getKiller();

			DataManager.getInstance().getLocal(killer).setKills(DataManager.getInstance().getLocal(killer).getKills() + 1);
			ScoreboardLib.getInstance().reloadContents(killer);

			if (Broadcaster.getInstance().getAnnouncer().announceKills())
				Broadcaster.broadcastMessage("#f83044[⚔] #8c8c8cGracz " + death.getName() + " (#80ff1f" + DataManager.getInstance().getLocal(death).getKills() + "⚔#8c8c8c/#fc7474" + DataManager.getInstance().getLocal(death).getDeaths() + "☠#8c8c8c) zostal zabity przez " + killer.getName() + " (#80ff1f" + DataManager.getInstance().getLocal(killer).getKills() + "⚔#8c8c8c/#fc7474" + DataManager.getInstance().getLocal(killer).getDeaths() + "☠#8c8c8c)!");

			QuestLib.manageQuest(killer, 6);

			ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta skull_meta = (SkullMeta) skull.getItemMeta();
			assert skull_meta != null;
			skull_meta.setOwningPlayer(Bukkit.getOfflinePlayer(death.getName()));
			skull.setItemMeta(skull_meta);
			event.getDrops().add(skull);

			Logger.getInstance().store(Logger.StoreType.DEATH, death.getName() + " zostal zabity przez " + killer.getName());
			Logger.getInstance().store(Logger.StoreType.KILL, killer.getName() + " zamordowal " + death.getName());
		}
		else
		{
			if (Broadcaster.getInstance().getAnnouncer().announceDeaths())
				Broadcaster.broadcastMessage("#f83044[⚔] #8c8c8c" + getDeathMessage(death));

			if (DataManager.getInstance().getLocal(death).getMoney() >= 10)
			{
				DataManager.getInstance().getLocal(death).setMoney(DataManager.getInstance().getLocal(death).getMoney() - (int) (DataManager.getInstance().getLocal(death).getMoney() * getMoneyBack(death)));
				ScoreboardLib.getInstance().reloadContents(death);
			}

			Logger.getInstance().store(Logger.StoreType.DEATH, death.getName() + " zginal (" + death.getLastDamageCause().getCause() + ")");
		}

		for (ItemStack item : event.getDrops())
			if (item.isSimilar(new ItemLib().get("aether:crystal_of_keeping")))
			{
				event.setKeepInventory(true);
				event.setKeepLevel(true);
				event.getDrops().clear();
				event.setDroppedExp(0);

				TaskLib.getInstance().runSyncLater(() -> {
					if (item.getAmount() == 1) death.getInventory().removeItem(item);
					else item.setAmount(item.getAmount() - 1);
				}, 1);

				break;
			}

		TaskLib.getInstance().runSyncLater(() -> {
			death.spigot().respawn();
			death.setMaxHealth(20.0 + (4.0D * DataManager.getInstance().getLocal(death).getUpgradeLevel(death.getName(), "vitality")));
			death.setHealth(death.getMaxHealth());
			death.setNoDamageTicks(100);
		}, 1);

		DataManager.getInstance().getLocal(death).setDeaths(DataManager.getInstance().getLocal(death).getDeaths() + 1);
		ScoreboardLib.getInstance().reloadContents(death);
	}

	private double getMoneyBack(final Player player)
	{
		return 0.1 - (0.02 * DataManager.getInstance().getLocal(player).getUpgradeLevel(player.getName(), "luck"));
	}

	private String getDeathMessage(final Player player)
	{
		final String name = player.getName();

		switch (player.getLastDamageCause().getCause())
		{
			case LAVA -> { return "Gracz " + name + " utopil sie w lawie, coz za masochistyczny piroman!"; }
			case FIRE, FIRE_TICK -> { return "Gracz " + name + " spalil sie, a mowili - prochow nie wciagaj, bo w proch sie obrocisz!"; }
			case DROWNING -> { return "Gracz " + name + " utopil sie, naucz sie plywac!"; }
			case ENTITY_ATTACK, ENTITY_SWEEP_ATTACK -> { return "Gracz " + name + " zginal w bitwie!"; }
			case BLOCK_EXPLOSION, ENTITY_EXPLOSION -> { return "Gracz " + name + " zostal wypierdolony w powietrze!"; }
			case FALL -> { return "Gracz " + name + " polamal nozki!"; }
			case FALLING_BLOCK -> { return "Gracz " + name + " zostal zasypany!"; }
			case FREEZE -> { return "Gracz " + name + " zamrozil sie na smierc!"; }
			case HOT_FLOOR -> { return "Gracz " + name + " popazyl sobie nozki!"; }
			case VOID -> { return "Gracz " + name + " wypadl z orbity!"; }
			case LIGHTNING -> { return "Gracz " + name + " dostal piorunem!"; }
			case PROJECTILE -> { return "Gracz " + name + " dostal strzala w leb!"; }
			case DRAGON_BREATH -> { return "Gracz " + name + " probowal wdychac opary smoka!"; }
			default -> { return "Gracz " + name + " zginal!"; }
		}
	}
}