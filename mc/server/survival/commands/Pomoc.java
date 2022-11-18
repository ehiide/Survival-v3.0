package mc.server.survival.commands;

import mc.server.survival.libraries.InventoryLib;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Pomoc
implements CommandExecutor, TabCompleter
{
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
	{
		if (sender instanceof Player)
		{
			final Player player = (Player) sender;

			final String rank = DataManager.getInstance().getLocal(player).getRank();

			if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("pomoc").isForAdministrators() ||
					rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("pomoc").isForModerators() ||
					rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("pomoc").isForPlayers())
			{
				ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
				return true;
			}

			if (args.length == 0)
				InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lCENTRUM POMOCY SERWERA"), "pomoc");
			else
				ChatManager.sendNotification(player, "Umiesz pisac?! Wystarczy napisac #ffc936/pomoc!", ChatManager.NotificationType.ERROR);

			return true;
		}
		else if (sender instanceof ConsoleCommandSender)
			ChatManager.sendConsoleMessages(
					"&7",
					"&a&lCENTRUM POMOCY SERWERA",
					"&eMamy nadzieje ze znajdziesz odpowiedz na swoje pytania!",
					"&7",
					"&aAether/Twilight nie dziala. Co mam zrobic?",
					"&7Jezeli swiat aetheru lub twilighu nie generuje sie prawidlowo lub masz z nim",
					"&7inny problem, przejdz do pliku &ebukkit.yml&7, i dodaj na samym dole nastepujace dane:",
					"&7",
					"&oworlds:",
					"&o  survival_aether:",
					"&o    generator: Survival",
					"&o  survival_twilight:",
					"&o    generator: Survival",
					"&7",
					"&aOdczuwam lagi i opoznienia na serwerze. Jak temu zaradzic?",
					"&7Mimo najszerszych staran nad optymalizacja wtyczki Survival, ta nadal moze",
					"&7produkowac male opoznienia. Najbardziej zasobozernymi funkcjami sa efekty wizualne.",
					"&7Ograniczenie ich dzialania moze skutkowac poprawa wydajnosci.",
					"&7",
					"&aJak wlaczyc/wylaczyc zintegrowane dodatki?",
					"&7Panel wszystkich mozliwych do kontroli dodatkow, znajduje sie",
					"&7w pliku konfiguracyjnym &esettings.yml&7, w folderze pluginu.",
					"&7",
					"&aJak moge dodac wlasny dodatek na swoj serwer?",
					"&7Uzywajac wtyczki Survival mozesz uzywac, dodawac i tworzyc wlasne dodatki.",
					"&7Klasy operacyjne sa otwarte, &enp. VisualUtil&7. Istnieje rowniez wbudowane API",
					"&7kryjace sie pod nazwa SurvivalAPI.",
					"&7",
					"&aMam inne pytanie :/",
					"&7W celu zadania indywidulanego pytania prosimy o bezposredni kontakt z ",
					"&7deweloperami na naszym issue-tracker.",
					"&7"
			);

		return true;
	}

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args)
	{
		return null;
	}
}