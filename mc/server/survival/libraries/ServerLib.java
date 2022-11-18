package mc.server.survival.libraries;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import mc.server.Broadcaster;
import mc.server.survival.files.Main;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.FileManager;
import mc.server.survival.protocols.Protocol;
import mc.server.survival.protocols.packet.PacketHandler;
import mc.server.survival.worlds.WorldHandler;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

public class ServerLib
{
	public static int getPing(final Player player)
	{
		return player.getPing();
	}

	public static boolean getPremiumState(final String name)
	{
		try
		{
			final URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
			final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			StringBuilder result = new StringBuilder();

			while ((line = reader.readLine()) != null)
				result.append(line);

			return !result.toString().equals("");
		}
		catch (IOException e) { e.printStackTrace(); }

		return false;
	}

	public static String getUUID(final Player player)
	{
		try
		{
			final URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + player.getName());
			final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			StringBuilder result = new StringBuilder();

			while ((line = reader.readLine()) != null)
				result.append(line);

			String reply = result.toString();

			int z = 0;

			for (int x = 0; x < reply.length(); x++)
				if (reply.substring(x, x + 1).equalsIgnoreCase("\""))
					if (++z == 7)
						for (int y = x + 1; y < reply.length(); y++)
							if (reply.substring(y, y + 1).equalsIgnoreCase("\""))
								if (++z == 8)
									return reply.substring(x + 1, y);
		}
		catch (IOException e) { e.printStackTrace(); }

		return null;
	}

	public static void login(@NotNull Player player)
	{
		try
		{
			ChatManager.sendNotification(player, "Nawiazywanie polaczenia z serwerami Mojang...", ChatManager.NotificationType.LOGGING);

			if (!Bukkit.getOnlineMode())
				if (getPremiumState(player.getName()))
				{
					ChatManager.sendNotification(player, "Dostosowywanie konta premium...", ChatManager.NotificationType.LOGGING);

					GameProfile profile = new Protocol(Main.getInstance().getServer()).getGameProfile(player);
					EntityPlayer entityPlayer = new Protocol(Main.getInstance().getServer()).getEntityPlayer(player);

					final URL url = new URL(String.format("https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false", getUUID(player)));
					final HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

					if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK)
					{
						BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
						String line;
						StringBuilder result = new StringBuilder();

						while ((line = reader.readLine()) != null)
							result.append(line);

						String reply = result.toString(), skin = null, signature = null;

						int z = 0;

						for (int x = 0; x < reply.length(); x++)
							if (reply.substring(x, x + 1).equalsIgnoreCase("\""))
								if (++z == 17)
									for (int y = x + 1; y < reply.length(); y++)
										if (reply.substring(y, y + 1).equalsIgnoreCase("\""))
											if (++z == 18)
												skin = reply.substring(x + 1, y);

						z = 0;

						for (int x = 0; x < reply.length(); x++)
							if (reply.substring(x, x + 1).equalsIgnoreCase("\""))
								if (++z == 21)
									for (int y = x + 1; y < reply.length(); y++)
										if (reply.substring(y, y + 1).equalsIgnoreCase("\""))
											if (++z == 22)
												signature = reply.substring(x + 1, y);

						if ((boolean) FileManager.getInstance().getConfigValue("function.authorization.restore-skins"))
							profile.getProperties().put("textures", new Property("textures", skin, signature));

						PacketHandler.getInstance().sendPacket(player,
								new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, entityPlayer),
								new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, entityPlayer));

						TaskLib.getInstance().runSync(() -> Bukkit.getOnlinePlayers().forEach(player1 -> player1.hidePlayer(Main.getInstance(), player)));
						TaskLib.getInstance().runSync(() -> Bukkit.getOnlinePlayers().forEach(player1 -> player1.showPlayer(Main.getInstance(), player)));

						ChatManager.sendNotification(player, "Odswiezanie sesji gracza...", ChatManager.NotificationType.LOGGING);

						final Location location = player.getLocation();

						TaskLib.getInstance().runSync(() -> player.teleport(new Location(WorldHandler.getWorld("auth"), 0, 300, 0, 0, 0)));
						TaskLib.getInstance().runSync(() -> player.teleport(location));
					}
					else
					{
						ChatManager.sendNotification(player, "#fc7474Wystapil nieoczekiwany problem podczas logowania.", ChatManager.NotificationType.LOGGING);
						TaskLib.getInstance().runSyncLater(() -> Bukkit.getConsoleSender().getServer().dispatchCommand(Bukkit.getConsoleSender(), "kick " + player.getName() + " Blad autoryzacji"), 40);
						return;
					}
				}
				else
					ChatManager.sendNotification(player, "Dostosowywanie konta non-premium...", ChatManager.NotificationType.LOGGING);

			ChatManager.sendNotification(player, "Zalogowano.", ChatManager.NotificationType.LOGGING);

			if (Broadcaster.getInstance().getAnnouncer().announcePlayerJoin())
				Broadcaster.broadcastMessage(FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "Gracz " + player.getName() + " dolaczyl na serwer!");

			TaskLib.getInstance().runSync(() -> VisualLib.showServerChangeTitle(player));

			if ((boolean) FileManager.getInstance().getConfigValue("visual.player.firework-when-enter"))
				TaskLib.getInstance().runSync(() -> VisualLib.spawnFirework(player.getLocation()));
		}
		catch (IOException e) { e.printStackTrace(); }
	}

	public static void loginAsync(@NotNull Player player)
	{
		TaskLib.getInstance().runAsyncLater(() -> login(player), 2);
	}

	public static boolean isVPNConnection(final InetAddress address)
	{
		final String ip = address.getHostAddress();

		try
		{
			final URL url = new URL("https://proxycheck.io/v2/" + ip + "?vpn=1&asn=1");
			final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			StringBuilder result = new StringBuilder();

			while ((line = reader.readLine()) != null)
				result.append(line);

			String value = null;

			int z = 0;

			for (int x = 0; x < result.length(); x++)
				if (result.substring(x, x + 1).equalsIgnoreCase("\""))
					if (++z == 51)
						for (int y = x + 1; y < result.length(); y++)
							if (result.substring(y, y + 1).equalsIgnoreCase("\""))
								if (++z == 52)
									value = result.substring(x + 1, y);

			if (value == null) return false;

			return value.equalsIgnoreCase("yes");
		}
		catch (IOException e) { e.printStackTrace(); }

		return false;
	}
}