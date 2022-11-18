package mc.server.survival.events;

import mc.server.Logger;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.worlds.WorldHandler;
import mc.server.survival.worlds.aether.Aether;
import org.bukkit.Axis;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Portal 
implements Listener
{
	@EventHandler
	public void onEvent(PlayerPortalEvent event)
	{
		final Player player = event.getPlayer();
		final PlayerTeleportEvent.TeleportCause cause = event.getCause();

		if (cause == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)
		{
			if (getPortalType(event.getFrom()) == PortalType.AETHER)
			{
				final String world = event.getFrom().getWorld().getName();

				if (!world.equalsIgnoreCase("survival") && !world.equalsIgnoreCase("survival_aether"))
				{
					ChatManager.sendNotification(player, "Wrota do nieba sa zamkniete na tym swiecie!", ChatManager.NotificationType.ERROR);
					event.setCancelled(true);
					return;
				}
				else
				{
					event.setCancelled(true);

					final Block block = WorldHandler.getHighestBlockAt(
							world.equalsIgnoreCase("survival") ? WorldHandler.getWorld("survival_aether") : WorldHandler.getWorld("survival"),
							event.getFrom().getBlockX(),
							event.getFrom().getBlockZ());

					int y = block.getY();

					if (!block.getType().isSolid())
						if (!world.equalsIgnoreCase("survival_aether"))
							y = 192;

					if (world.equalsIgnoreCase("survival"))
						Aether.getInstance().getAetherPortal().createPlayerPortal(
								player,
								new Location(WorldHandler.getWorld("survival_aether"),
								event.getFrom().getBlockX(),
										y,
								event.getFrom().getBlockZ(),
								event.getFrom().getYaw(),
								event.getFrom().getPitch()));
					else
						Aether.getInstance().getAetherPortal().createPlayerPortal(
								player,
								new Location(WorldHandler.getWorld("survival"),
										event.getFrom().getBlockX(),
										y,
										event.getFrom().getBlockZ(),
										event.getFrom().getYaw(),
										event.getFrom().getPitch()));

					Logger.getInstance().store(Logger.StoreType.PORTAL, player.getName() + " uzyl portalu swiata nieba");
				}
			}
			else
				Logger.getInstance().store(Logger.StoreType.PORTAL, player.getName() + " uzyl portalu swiata piekla");
		}
		else if (cause == PlayerTeleportEvent.TeleportCause.END_PORTAL)
			Logger.getInstance().store(Logger.StoreType.PORTAL, player.getName() + " uzyl portalu kresu");
		else if (cause == PlayerTeleportEvent.TeleportCause.END_GATEWAY)
			Logger.getInstance().store(Logger.StoreType.PORTAL, player.getName() + " uzyl portalu bram kresu");

		WorldHandler.queueWorldChange(player);
	}

	public enum PortalType
	{
		NETHER, AETHER
	}

	public PortalType getPortalType(final Location location)
	{
		for (int x = -1; x <= 1; x++)
			for (int z = -1; z <= 1; z++)
				if (location.clone().add(x, 0, z).getBlock().getType() == Material.NETHER_PORTAL)
				{
					Block block = location.clone().add(x, 0, z).getBlock();
					BlockData bd = block.getBlockData();
					Orientable rot = (Orientable) bd;

					if (rot.getAxis() == Axis.X)
					{
						if (location.clone().add(x + 1, 0, z).getBlock().getType() == Material.GLOWSTONE &&
							location.clone().add(x - 2, 0, z).getBlock().getType() == Material.GLOWSTONE)
							return PortalType.AETHER;

						if (location.clone().add(x + 2, 0, z).getBlock().getType() == Material.GLOWSTONE &&
								location.clone().add(x - 1, 0, z).getBlock().getType() == Material.GLOWSTONE)
							return PortalType.AETHER;
					}

					if (rot.getAxis() == Axis.Z)
					{
						if (location.clone().add(x, 0, z + 1).getBlock().getType() == Material.GLOWSTONE &&
								location.clone().add(x, 0, z - 2).getBlock().getType() == Material.GLOWSTONE)
							return PortalType.AETHER;

						if (location.clone().add(x, 0, z + 2).getBlock().getType() == Material.GLOWSTONE &&
								location.clone().add(x, 0, z - 1).getBlock().getType() == Material.GLOWSTONE)
							return PortalType.AETHER;
					}
				}

		return PortalType.NETHER;
	}
}