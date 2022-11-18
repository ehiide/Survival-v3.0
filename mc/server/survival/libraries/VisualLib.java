package mc.server.survival.libraries;

import mc.server.survival.managers.FileManager;
import org.bukkit.*;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.jetbrains.annotations.NotNull;

public class VisualLib
{
	public static void spawnFirework(final Location location)
	{
		for (int y = 0; y <= 8; y++)
			if (location.add(0, y, 0).getBlock().getType() != Material.AIR)
				return;

		final Firework firework = location.getWorld().spawn(location.add(0, 2, 0), Firework.class);
		FireworkMeta meta = firework.getFireworkMeta();
		
		meta.addEffects(FireworkEffect.builder().flicker(true).withColor(Color.RED).trail(true).with(Type.STAR).withFade(Color.WHITE).build());
		meta.setPower(1);
		firework.setFireworkMeta(meta);
	}
	
	public static void showDelayedTitle(final Player player, String top, String bottom, final int start, final int time, final int end)
	{
		player.sendTitle(ChatLib.ColorUtil.formatHEX("&7" + bottom), ChatLib.ColorUtil.formatHEX("&7" + top), start, time, end);
	}

	public static void showServerChangeTitle(@NotNull final Player player)
	{
		if (!((boolean) FileManager.getInstance().getConfigValue("visual.player.title-sending"))) return;

		String worldName = player.getWorld().getName().toUpperCase();

		if (worldName.equalsIgnoreCase("survival_nether".toUpperCase()) || worldName.equalsIgnoreCase("survival_the_end".toUpperCase()) ||
			worldName.equalsIgnoreCase("survival_aether".toUpperCase()) || worldName.equalsIgnoreCase("survival_twilight".toUpperCase()))
			worldName = worldName.substring(9);

		worldName = worldName.replace("_", " ");
		final int worldNameLength = worldName.length();

		for (int x = 0; x <= worldNameLength; x++)
		{
			String factor = worldName.substring(0, x);

			TaskLib.getInstance().runAsyncLater(() -> showDelayedTitle(player, "", "&a&l✦ #f83044&l" + factor + " &a&l✦", 0, 10, 0), 20 + x);
		}

		for (int x = 0; x <= worldNameLength; x++)
		{
			final String factor = worldName.substring(0, x);
			final String factor2 = worldName.substring(x, worldNameLength);

			TaskLib.getInstance().runAsyncLater(() -> showDelayedTitle(player, "", "&a&l✦ #fff203&l" + factor + "#f83044&l" + factor2 + " &a&l✦", 0, 10, 0), 21 + worldNameLength + x);
		}

		for (int x = 0; x <= worldNameLength; x++)
		{
			final String factor = worldName.substring(0, x);
			final String factor2 = worldName.substring(x, worldNameLength);

			TaskLib.getInstance().runAsyncLater(() -> showDelayedTitle(player, "", "&a&l✦ #f83044&l" + factor + "#fff203&l" + factor2 + " &a&l✦", 0, 10, 0), 22 + worldNameLength * 2 + x);
		}

		for (int x = worldNameLength; x >= 0; x--)
		{
			final String factor = worldName.substring(0, x);

			TaskLib.getInstance().runAsyncLater(() -> showDelayedTitle(player, "", "&a&l✦ #f83044&l" + factor + " &a&l✦", 0, 3, 0), 23 + worldNameLength * 3 + (worldNameLength - x));
		}
	}

	public static void simulateFakeExplosion(final Player player, final int delay)
	{
		TaskLib.getInstance().runSyncLater(() -> player.spawnParticle(Particle.EXPLOSION_HUGE, player.getLocation().add(0, 1, 0), 1, 15, 2, 15), 20*delay);
	}
}