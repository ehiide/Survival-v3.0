package mc.server.survival.events;

import mc.server.Logger;
import mc.server.survival.libraries.ChatLib;
import mc.server.survival.libraries.MathLib;
import mc.server.survival.libraries.TimeLib;
import mc.server.survival.libraries.VisualLib;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import mc.server.survival.managers.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Objects;
import java.util.Random;

public class AsyncChat
implements Listener
{
	@EventHandler
	public void onEvent(AsyncPlayerChatEvent event)
	{
		event.setCancelled(true);

		final Player player = event.getPlayer();
		final String message = event.getMessage();

		if (TimeLib.getDifferenceInSeconds(DataManager.getInstance().getLocal(null).getMute(player.getName())) < DataManager.getInstance().getLocal(null).getMuteLength(player.getName()))
		{
			ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Nie mozesz pisac na czacie, poniewaz jestes wyciszony, odczekaj chwile i sprobuj ponownie!");
			VisualLib.showDelayedTitle(player, " ", "#ffc936✖", 0, 10, 10);
			return;
		}

		compileMessage(ChatLib.getPlaceholder(player, ChatLib.applyCorrection(changeLetters(player, ChatLib.addDruggedEmotes(player, message)))), player);

		Logger.getInstance().store(Logger.StoreType.CHAT, "<" + player.getName() + "> " + message);
	}

	public void compileMessage(final String message, final Player player)
	{
		final boolean isGangPrefixed = message.substring(0, 1).equalsIgnoreCase("!"),
			          isAdminPrefixed = message.substring(0, 1).equalsIgnoreCase("@");

		if (isGangPrefixed)
			ChatLib.sendGangChatMessage(player, message);
		else if (isAdminPrefixed)
			ChatLib.sendAdminMessage(player, message);
		else
			Bukkit.broadcastMessage(ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.getRankInChat(player) + ChatLib.getGangInChat(DataManager.getInstance().getLocal(player).getGang()) + ChatLib.returnMarryPrefix(player) +  "&r" + ChatLib.returnPlayerColor(player) + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c " + message));

		player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 1.0F, 8.0F);
	}

	private String changeLetters(final Player player, String message)
	{
		final int gaba = DataManager.getInstance().getLocal(player).getGABA();
		if (gaba <= 0) return message;

		String alphabet = "abcdefghijklmnoprstuwyzxv";

		for (int slot = 0; slot < message.length(); slot++)
			if (MathLib.chanceOf(gaba / 2) && message.charAt(slot) != ' ')
			{
				Random random = new Random();
				StringBuilder stringBuilder = new StringBuilder();

				stringBuilder.append(message);
				stringBuilder.setCharAt(slot, alphabet.charAt(random.nextInt(alphabet.length())));
				message = stringBuilder.toString();
			}

		return message;
	}
}