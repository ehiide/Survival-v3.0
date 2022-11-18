package mc.server.survival.libraries;

import mc.server.survival.files.Main;
import mc.server.survival.libraries.chemistry.Chemistries;
import mc.server.survival.libraries.chemistry.ChemistryDrug;
import mc.server.survival.libraries.trading.Trading;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import mc.server.survival.managers.FileManager;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class InventoryLib
{
	public static void createNewInventory(final Player player, final int size, final String name, final String type)
	{
		Inventory inventory = Bukkit.createInventory(player, size, name);
		fillInventory(inventory);

		if (type.equalsIgnoreCase("domki"))
		{
			inventory.setItem(11, getItem(player, "home1"));
			inventory.setItem(13, getItem(player, "homeinfo"));
			inventory.setItem(15, getItem(player, "home2"));
		}
		else if (type.equalsIgnoreCase("schowek"))
		{
			inventory.setItem(11, getItem(player, "schowekinfo"));
			inventory.setItem(15, getItem(player, "schowek1"));
		}
		else if (type.equalsIgnoreCase("pomoc"))
		{
			inventory.setItem(22, getItem(player, "pomocinfo"));
			inventory.setItem(25, getItem(player, "pomocp1"));
			inventory.setItem(15, getItem(player, "pomocp2"));
			inventory.setItem(34, getItem(player, "pomocp3"));
			inventory.setItem(11, getItem(player, "pomocl1"));
			inventory.setItem(19, getItem(player, "pomocl2"));
			inventory.setItem(28, getItem(player, "pomocl3"));
		}
		else if (type.equalsIgnoreCase("craftingi1"))
		{
			inventory.setItem(44, getItem(player, "arrownext"));
			inventory.setItem(24, new ItemStack(Material.SADDLE));
			inventory.setItem(22, new ItemStack(Material.STRING));
			inventory.setItem(21, null);
			inventory.setItem(20, new ItemStack(Material.STRING));
			inventory.setItem(31, new ItemStack(Material.IRON_INGOT));
			inventory.setItem(30, null);
			inventory.setItem(29, new ItemStack(Material.IRON_INGOT));
			inventory.setItem(13, new ItemStack(Material.LEATHER));
			inventory.setItem(12, new ItemStack(Material.LEATHER));
			inventory.setItem(11, new ItemStack(Material.LEATHER));
		}
		else if (type.equalsIgnoreCase("craftingi2"))
		{
			inventory.setItem(36, getItem(player, "arrowback"));
			inventory.setItem(44, getItem(player, "arrownext"));
			inventory.setItem(24, new ItemStack(Material.NAME_TAG));
			inventory.setItem(22, new ItemStack(Material.PAPER));
			inventory.setItem(21, new ItemStack(Material.PAPER));
			inventory.setItem(20, new ItemStack(Material.PAPER));
			inventory.setItem(31, new ItemStack(Material.STRING));
			inventory.setItem(30, new ItemStack(Material.IRON_INGOT));
			inventory.setItem(29, new ItemStack(Material.STRING));
			inventory.setItem(13, new ItemStack(Material.STRING));
			inventory.setItem(12, new ItemStack(Material.IRON_INGOT));
			inventory.setItem(11, new ItemStack(Material.STRING));
		}
		else if (type.equalsIgnoreCase("craftingi3"))
		{
			inventory.setItem(36, getItem(player, "arrowback"));
			inventory.setItem(44, getItem(player, "arrownext"));
			inventory.setItem(24, new ItemStack(Material.IRON_HORSE_ARMOR));
			inventory.setItem(22, new ItemStack(Material.IRON_INGOT));
			inventory.setItem(21, new ItemStack(Material.SADDLE));
			inventory.setItem(20, new ItemStack(Material.IRON_INGOT));
			inventory.setItem(31, null);
			inventory.setItem(30, null);
			inventory.setItem(29, new ItemStack(Material.IRON_INGOT));
			inventory.setItem(13, new ItemStack(Material.IRON_INGOT));
			inventory.setItem(12, null);
			inventory.setItem(11, null);
		}
		else if (type.equalsIgnoreCase("craftingi4"))
		{
			inventory.setItem(36, getItem(player, "arrowback"));
			inventory.setItem(44, getItem(player, "arrownext"));
			inventory.setItem(24, new ItemStack(Material.GOLDEN_HORSE_ARMOR));
			inventory.setItem(22, new ItemStack(Material.GOLD_INGOT));
			inventory.setItem(21, new ItemStack(Material.SADDLE));
			inventory.setItem(20, new ItemStack(Material.GOLD_INGOT));
			inventory.setItem(31, null);
			inventory.setItem(30, null);
			inventory.setItem(29, new ItemStack(Material.GOLD_INGOT));
			inventory.setItem(13, new ItemStack(Material.GOLD_INGOT));
			inventory.setItem(12, null);
			inventory.setItem(11, null);
		}
		else if (type.equalsIgnoreCase("craftingi5"))
		{
			inventory.setItem(36, getItem(player, "arrowback"));
			inventory.setItem(44, getItem(player, "arrownext"));
			inventory.setItem(24, new ItemStack(Material.DIAMOND_HORSE_ARMOR));
			inventory.setItem(22, new ItemStack(Material.DIAMOND));
			inventory.setItem(21, new ItemStack(Material.SADDLE));
			inventory.setItem(20, new ItemStack(Material.DIAMOND));
			inventory.setItem(31, null);
			inventory.setItem(30, null);
			inventory.setItem(29, new ItemStack(Material.DIAMOND));
			inventory.setItem(13, new ItemStack(Material.DIAMOND));
			inventory.setItem(12, null);
			inventory.setItem(11, null);
		}
		else if (type.equalsIgnoreCase("craftingi6"))
		{
			inventory.setItem(36, getItem(player, "arrowback"));
			inventory.setItem(44, getItem(player, "arrownext"));
			inventory.setItem(24, new ItemStack(Material.LEATHER));
			inventory.setItem(22, new ItemStack(Material.ROTTEN_FLESH));
			inventory.setItem(21, new ItemStack(Material.ROTTEN_FLESH));
			inventory.setItem(20, new ItemStack(Material.ROTTEN_FLESH));
			inventory.setItem(31, new ItemStack(Material.ROTTEN_FLESH));
			inventory.setItem(30, new ItemStack(Material.ROTTEN_FLESH));
			inventory.setItem(29, new ItemStack(Material.ROTTEN_FLESH));
			inventory.setItem(13, new ItemStack(Material.ROTTEN_FLESH));
			inventory.setItem(12, new ItemStack(Material.ROTTEN_FLESH));
			inventory.setItem(11, new ItemStack(Material.ROTTEN_FLESH));
		}
		else if (type.equalsIgnoreCase("craftingi7"))
		{
			inventory.setItem(36, getItem(player, "arrowback"));
			inventory.setItem(44, getItem(player, "arrownext"));
			inventory.setItem(24, new ItemStack(Material.SPECTRAL_ARROW));
			inventory.setItem(22, null);
			inventory.setItem(21, new ItemStack(Material.BLAZE_ROD));
			inventory.setItem(20, null);
			inventory.setItem(31, null);
			inventory.setItem(30, new ItemStack(Material.FEATHER));
			inventory.setItem(29, null);
			inventory.setItem(13, null);
			inventory.setItem(12, new ItemStack(Material.FLINT));
			inventory.setItem(11, null);
		}
		else if (type.equalsIgnoreCase("craftingi8"))
		{
			inventory.setItem(36, getItem(player, "arrowback"));
			inventory.setItem(44, getItem(player, "arrownext"));
			inventory.setItem(24, new ItemStack(Material.BUNDLE));
			inventory.setItem(22, new ItemStack(Material.LEATHER));
			inventory.setItem(21, new ItemStack(Material.CHEST));
			inventory.setItem(20, new ItemStack(Material.LEATHER));
			inventory.setItem(31, new ItemStack(Material.LEATHER));
			inventory.setItem(30, new ItemStack(Material.LEATHER));
			inventory.setItem(29, new ItemStack(Material.LEATHER));
			inventory.setItem(13, new ItemStack(Material.LEATHER));
			inventory.setItem(12, new ItemStack(Material.STRING));
			inventory.setItem(11, new ItemStack(Material.LEATHER));
		}
		else if (type.equalsIgnoreCase("craftingi9"))
		{
			inventory.setItem(36, getItem(player, "arrowback"));
			inventory.setItem(44, getItem(player, "arrownext"));
			inventory.setItem(24, new ItemStack(Material.EXPERIENCE_BOTTLE));
			inventory.setItem(22, new ItemStack(Material.IRON_NUGGET));
			inventory.setItem(21, new ItemStack(Material.GLASS_BOTTLE));
			inventory.setItem(20, new ItemStack(Material.IRON_NUGGET));
			inventory.setItem(31, null);
			inventory.setItem(30, new ItemStack(Material.GLOWSTONE_DUST));
			inventory.setItem(29, null);
			inventory.setItem(13, null);
			inventory.setItem(12, new ItemStack(Material.GLOWSTONE_DUST));
			inventory.setItem(11, null);
		}
		else if (type.equalsIgnoreCase("craftingi10"))
		{
			inventory.setItem(36, getItem(player, "arrowback"));
			inventory.setItem(44, getItem(player, "arrownext"));
			inventory.setItem(24, ChemistryDrug.getDrug(Chemistries.piwo));
			inventory.setItem(22, new ItemStack(Material.WHEAT));
			inventory.setItem(21, new ItemStack(Material.GLASS_BOTTLE));
			inventory.setItem(20, new ItemStack(Material.WHEAT));
			inventory.setItem(31, new ItemStack(Material.WHEAT));
			inventory.setItem(30, new ItemStack(Material.WHEAT));
			inventory.setItem(29, new ItemStack(Material.WHEAT));
			inventory.setItem(13, new ItemStack(Material.WHEAT));
			inventory.setItem(12, new ItemStack(Material.WHEAT));
			inventory.setItem(11, new ItemStack(Material.WHEAT));
		}
		else if (type.equalsIgnoreCase("craftingi11"))
		{
			inventory.setItem(36, getItem(player, "arrowback"));
			inventory.setItem(44, getItem(player, "arrownext"));
			inventory.setItem(24, ChemistryDrug.getDrug(Chemistries.wino));
			inventory.setItem(22, new ItemStack(Material.SUGAR));
			inventory.setItem(21, new ItemStack(Material.GLASS_BOTTLE));
			inventory.setItem(20, new ItemStack(Material.SUGAR));
			inventory.setItem(31, null);
			inventory.setItem(30, new ItemStack(Material.SWEET_BERRIES));
			inventory.setItem(29, null);
			inventory.setItem(13, null);
			inventory.setItem(12, new ItemStack(Material.SWEET_BERRIES));
			inventory.setItem(11, null);
		}
		else if (type.equalsIgnoreCase("craftingi12"))
		{
			inventory.setItem(36, getItem(player, "arrowback"));
			inventory.setItem(44, getItem(player, "arrownext"));
			inventory.setItem(24, ChemistryDrug.getDrug(Chemistries.bimber));
			inventory.setItem(22, new ItemStack(Material.GOLDEN_APPLE));
			inventory.setItem(21, new ItemStack(Material.GLASS_BOTTLE));
			inventory.setItem(20, new ItemStack(Material.GOLDEN_APPLE));
			inventory.setItem(31, new ItemStack(Material.APPLE));
			inventory.setItem(30, new ItemStack(Material.GOLDEN_APPLE));
			inventory.setItem(29, new ItemStack(Material.APPLE));
			inventory.setItem(13, new ItemStack(Material.HAY_BLOCK));
			inventory.setItem(12, new ItemStack(Material.GOLDEN_APPLE));
			inventory.setItem(11, new ItemStack(Material.HAY_BLOCK));
		}
		else if (type.equalsIgnoreCase("craftingi13"))
		{
			inventory.setItem(36, getItem(player, "arrowback"));
			inventory.setItem(24, new ItemLib().get("aether:crystal_of_keeping"));
			inventory.setItem(22, new ItemStack(Material.EMERALD_BLOCK));
			inventory.setItem(21, new ItemLib().get("aether:angel_heart"));
			inventory.setItem(20, new ItemStack(Material.EMERALD_BLOCK));
			inventory.setItem(31, null);
			inventory.setItem(30, new ItemStack(Material.GOLD_BLOCK));
			inventory.setItem(29, null);
			inventory.setItem(13, null);
			inventory.setItem(12, new ItemStack(Material.GOLD_BLOCK));
			inventory.setItem(11, null);
		}
		else if (type.equalsIgnoreCase("paleta"))
		{
			inventory.setItem(4, new ItemStack(Material.PINK_STAINED_GLASS_PANE));
			inventory.setItem(9, new ItemStack(Material.GREEN_STAINED_GLASS_PANE));
			inventory.setItem(11, new ItemStack(Material.RED_STAINED_GLASS_PANE));
			inventory.setItem(13, getItem(player, "paletainfo"));
			inventory.setItem(15, new ItemStack(Material.YELLOW_STAINED_GLASS_PANE));
			inventory.setItem(17, new ItemStack(Material.WHITE_STAINED_GLASS_PANE));
			inventory.setItem(22, new ItemStack(Material.BLUE_STAINED_GLASS_PANE));
		}
		else if (type.equalsIgnoreCase("sklep"))
		{
			inventory.setItem(11, getItem(player, "sklepefekty"));
			inventory.setItem(15, getItem(player, "skleptrading"));
			inventory.setItem(22, getItem(player, "sklepprzedmioty"));
			inventory.setItem(29, getItem(player, "sklepdodatki"));
			inventory.setItem(33, getItem(player, "sklepgangi"));
		}
		else if (type.equalsIgnoreCase("sklep_efekty"))
		{
			inventory.setItem(10, getItem(player, "sklepspeed"));
			inventory.setItem(11, getItem(player, "sklepjumpboost"));
			inventory.setItem(12, getItem(player, "sklepresistance"));
			inventory.setItem(13, getItem(player, "sklepstrength"));
			inventory.setItem(14, getItem(player, "sklephaste"));
			inventory.setItem(15, getItem(player, "sklepfireresistance"));
			inventory.setItem(16, getItem(player, "sklepregeneration"));
			inventory.setItem(21, getItem(player, "sklepinvis"));
			inventory.setItem(22, getItem(player, "sklepwaterbreathing"));
			inventory.setItem(23, getItem(player, "sklepnightvision"));

			inventory.setItem(40, getItem(player, "powrot"));
		}
		else if (type.equalsIgnoreCase("sklep_dodatki"))
		{
			inventory.setItem(10, getItem(player, "sklepkolor1"));
			inventory.setItem(11, getItem(player, "sklepkolor2"));
			inventory.setItem(12, getItem(player, "sklepkolor3"));
			inventory.setItem(19, getItem(player, "sklepkolor4"));
			inventory.setItem(20, getItem(player, "sklepkolor5"));
			inventory.setItem(21, getItem(player, "sklepkolor6"));
			inventory.setItem(28, getItem(player, "sklepkolor7"));
			inventory.setItem(29, getItem(player, "sklepkolor8"));
			inventory.setItem(14, getItem(player, "sklepschowek"));
			inventory.setItem(23, getItem(player, "sklepvoucher"));
			inventory.setItem(16, getItem(player, "sklepslub"));
			inventory.setItem(40, getItem(player, "powrot"));
		}
		else if (type.equalsIgnoreCase("sklep_gang"))
		{
			if (DataManager.getInstance().getLocal(player).getGang() == null)
				inventory.setItem(22, getItem(player, "sklepnogang"));
			else
			{
				inventory.setItem(10, getItem(player, "sklepgang1"));
				inventory.setItem(11, getItem(player, "sklepgang2"));
				inventory.setItem(12, getItem(player, "sklepgang3"));
				inventory.setItem(19, getItem(player, "sklepgang4"));
				inventory.setItem(20, getItem(player, "sklepgang5"));
				inventory.setItem(21, getItem(player, "sklepgang6"));
				inventory.setItem(28, getItem(player, "sklepgang7"));
				inventory.setItem(29, getItem(player, "sklepgang8"));
				inventory.setItem(14, getItem(player, "sklepgangp1"));
				inventory.setItem(23, getItem(player, "sklepgangp2"));
				inventory.setItem(32, getItem(player, "sklepgangp3"));
				inventory.setItem(16, getItem(player, "sklepgangstar"));
				inventory.setItem(25, getItem(player, "sklepgangchat"));
			}

			inventory.setItem(40, getItem(player, "powrot"));
		}
		else if (type.equalsIgnoreCase("sklep_przedmioty"))
		{
			inventory.setItem(9, getItem(player, "sklepitemy"));
			inventory.setItem(18, getItem(player, "sklepksiazki"));
			inventory.setItem(27, getItem(player, "sklepspecjalne"));

			inventory.setItem(22, getItem(player, "sklepinfo"));

			inventory.setItem(49, getItem(player, "powrot"));
			inventory.setItem(26, getItem(player, "sklepkupno"));
		}
		else if (type.equalsIgnoreCase("sklep_przedmioty_itemy"))
		{
			inventory.setItem(9, getItem(player, "sklepitemyselected"));
			inventory.setItem(18, getItem(player, "sklepksiazki"));
			inventory.setItem(27, getItem(player, "sklepspecjalne"));

			inventory.setItem(11, getItem(player, "sklepitemy1"));
			inventory.setItem(12, getItem(player, "sklepitemy2"));
			inventory.setItem(13, getItem(player, "sklepitemy3"));
			inventory.setItem(14, getItem(player, "sklepitemy4"));
			inventory.setItem(15, getItem(player, "sklepitemy5"));
			inventory.setItem(20, getItem(player, "sklepitemy6"));
			inventory.setItem(21, getItem(player, "sklepitemy7"));
			inventory.setItem(22, getItem(player, "sklepitemy8"));
			inventory.setItem(23, getItem(player, "sklepitemy9"));
			inventory.setItem(24, getItem(player, "sklepitemy10"));
			inventory.setItem(29, getItem(player, "sklepitemy11"));
			inventory.setItem(30, getItem(player, "sklepitemy12"));
			inventory.setItem(31, getItem(player, "sklepitemy13"));
			inventory.setItem(32, getItem(player, "sklepitemy14"));
			inventory.setItem(33, getItem(player, "sklepitemy15"));

			inventory.setItem(45, getItem(player, "arrowback"));
			inventory.setItem(53, getItem(player, "arrownext"));
			inventory.setItem(49, getItem(player, "powrot"));
			inventory.setItem(26, getItem(player, "sklepkupno"));
			inventory.setItem(47, getItem(player, "multisell"));
		}
		else if (type.equalsIgnoreCase("sklep_przedmioty_itemy2"))
		{
			inventory.setItem(9, getItem(player, "sklepitemyselected"));
			inventory.setItem(18, getItem(player, "sklepksiazki"));
			inventory.setItem(27, getItem(player, "sklepspecjalne"));

			inventory.setItem(11, getItem(player, "sklepitemy16"));
			inventory.setItem(12, getItem(player, "sklepitemy17"));
			inventory.setItem(13, getItem(player, "sklepitemy18"));
			inventory.setItem(14, getItem(player, "sklepitemy19"));
			inventory.setItem(15, getItem(player, "sklepitemy20"));
			inventory.setItem(20, getItem(player, "sklepitemy21"));
			inventory.setItem(21, getItem(player, "sklepitemy22"));
			inventory.setItem(22, getItem(player, "sklepitemy23"));
			inventory.setItem(23, getItem(player, "sklepitemy24"));
			inventory.setItem(24, getItem(player, "sklepitemy25"));
			inventory.setItem(29, getItem(player, "sklepitemy26"));
			inventory.setItem(30, getItem(player, "sklepitemy27"));
			inventory.setItem(31, getItem(player, "sklepitemy28"));
			inventory.setItem(32, getItem(player, "sklepitemy29"));
			inventory.setItem(33, getItem(player, "sklepitemy30"));

			inventory.setItem(45, getItem(player, "arrowback"));
			inventory.setItem(53, getItem(player, "arrownext"));
			inventory.setItem(49, getItem(player, "powrot"));
			inventory.setItem(26, getItem(player, "sklepkupno"));
			inventory.setItem(47, getItem(player, "multisell"));
		}
		else if (type.equalsIgnoreCase("sklep_przedmioty_itemy3"))
		{
			inventory.setItem(9, getItem(player, "sklepitemyselected"));
			inventory.setItem(18, getItem(player, "sklepksiazki"));
			inventory.setItem(27, getItem(player, "sklepspecjalne"));

			inventory.setItem(11, getItem(player, "sklepitemy31"));
			inventory.setItem(12, getItem(player, "sklepitemy32"));
			inventory.setItem(13, getItem(player, "sklepitemy33"));
			inventory.setItem(14, getItem(player, "sklepitemy34"));
			inventory.setItem(15, getItem(player, "sklepitemy35"));
			inventory.setItem(20, getItem(player, "sklepitemy36"));
			inventory.setItem(21, getItem(player, "sklepitemy37"));
			inventory.setItem(22, getItem(player, "sklepitemy38"));
			inventory.setItem(23, getItem(player, "sklepitemy39"));
			inventory.setItem(24, getItem(player, "sklepitemy40"));
			inventory.setItem(29, getItem(player, "sklepitemy41"));
			inventory.setItem(30, getItem(player, "sklepitemy42"));
			inventory.setItem(31, getItem(player, "sklepitemy43"));
			inventory.setItem(32, getItem(player, "sklepitemy44"));
			inventory.setItem(33, getItem(player, "sklepitemy45"));

			inventory.setItem(45, getItem(player, "arrowback"));
			inventory.setItem(53, getItem(player, "arrownext"));
			inventory.setItem(49, getItem(player, "powrot"));
			inventory.setItem(26, getItem(player, "sklepkupno"));
			inventory.setItem(47, getItem(player, "multisell"));
		}
		else if (type.equalsIgnoreCase("sklep_przedmioty_itemy4"))
		{
			inventory.setItem(9, getItem(player, "sklepitemyselected"));
			inventory.setItem(18, getItem(player, "sklepksiazki"));
			inventory.setItem(27, getItem(player, "sklepspecjalne"));

			inventory.setItem(11, getItem(player, "sklepitemy46"));
			inventory.setItem(12, getItem(player, "sklepitemy47"));
			inventory.setItem(13, getItem(player, "sklepitemy48"));
			inventory.setItem(14, getItem(player, "sklepitemy49"));
			inventory.setItem(15, getItem(player, "sklepitemy50"));
			inventory.setItem(20, getItem(player, "sklepitemy51"));
			inventory.setItem(21, getItem(player, "sklepitemy52"));
			inventory.setItem(22, getItem(player, "sklepitemy53"));
			inventory.setItem(23, getItem(player, "sklepitemy54"));
			inventory.setItem(24, getItem(player, "sklepitemy55"));
			inventory.setItem(29, getItem(player, "sklepitemy56"));
			inventory.setItem(30, getItem(player, "sklepitemy57"));
			inventory.setItem(31, getItem(player, "sklepitemy58"));
			inventory.setItem(32, getItem(player, "sklepitemy59"));
			inventory.setItem(33, getItem(player, "sklepitemy60"));

			inventory.setItem(45, getItem(player, "arrowback"));
			inventory.setItem(53, getItem(player, "arrownext"));
			inventory.setItem(49, getItem(player, "powrot"));
			inventory.setItem(26, getItem(player, "sklepkupno"));
			inventory.setItem(47, getItem(player, "multisell"));
		}
		else if (type.equalsIgnoreCase("sklep_przedmioty_itemy5"))
		{
			inventory.setItem(9, getItem(player, "sklepitemyselected"));
			inventory.setItem(18, getItem(player, "sklepksiazki"));
			inventory.setItem(27, getItem(player, "sklepspecjalne"));

			inventory.setItem(11, getItem(player, "sklepitemy61"));
			inventory.setItem(12, getItem(player, "sklepitemy62"));
			inventory.setItem(13, getItem(player, "sklepitemy63"));
			inventory.setItem(14, getItem(player, "sklepitemy64"));
			inventory.setItem(15, getItem(player, "sklepitemy65"));
			inventory.setItem(20, getItem(player, "sklepitemy66"));
			inventory.setItem(21, getItem(player, "sklepitemy67"));
			inventory.setItem(22, getItem(player, "sklepitemy68"));
			inventory.setItem(23, getItem(player, "sklepitemy69"));
			inventory.setItem(24, getItem(player, "sklepitemy70"));
			inventory.setItem(29, getItem(player, "sklepitemy71"));
			inventory.setItem(30, getItem(player, "sklepitemy72"));
			inventory.setItem(31, getItem(player, "sklepitemy73"));
			inventory.setItem(32, getItem(player, "sklepitemy74"));
			inventory.setItem(33, getItem(player, "sklepitemy75"));

			inventory.setItem(45, getItem(player, "arrowback"));
			inventory.setItem(53, getItem(player, "arrownext"));
			inventory.setItem(49, getItem(player, "powrot"));
			inventory.setItem(26, getItem(player, "sklepkupno"));
			inventory.setItem(47, getItem(player, "multisell"));
		}
		else if (type.equalsIgnoreCase("sklep_przedmioty_itemy6"))
		{
			inventory.setItem(9, getItem(player, "sklepitemyselected"));
			inventory.setItem(18, getItem(player, "sklepksiazki"));
			inventory.setItem(27, getItem(player, "sklepspecjalne"));

			inventory.setItem(11, getItem(player, "sklepitemy76"));
			inventory.setItem(12, getItem(player, "sklepitemy77"));
			inventory.setItem(13, getItem(player, "sklepitemy78"));
			inventory.setItem(14, getItem(player, "sklepitemy79"));
			inventory.setItem(15, getItem(player, "sklepitemy80"));
			inventory.setItem(20, getItem(player, "sklepitemy81"));
			inventory.setItem(21, getItem(player, "sklepitemy82"));
			inventory.setItem(22, getItem(player, "sklepitemy83"));
			inventory.setItem(23, getItem(player, "sklepitemy84"));

			inventory.setItem(45, getItem(player, "arrowback"));
			inventory.setItem(53, getItem(player, "arrownext"));
			inventory.setItem(49, getItem(player, "powrot"));
			inventory.setItem(26, getItem(player, "sklepkupno"));
			inventory.setItem(47, getItem(player, "multisell"));
		}
		else if (type.equalsIgnoreCase("sklep_przedmioty_ksiazki"))
		{
			inventory.setItem(9, getItem(player, "sklepitemy"));
			inventory.setItem(18, getItem(player, "sklepksiazkiselected"));
			inventory.setItem(27, getItem(player, "sklepspecjalne"));

			inventory.setItem(11, getItem(player, "sklepksiazki1"));
			inventory.setItem(12, getItem(player, "sklepksiazki2"));
			inventory.setItem(13, getItem(player, "sklepksiazki3"));
			inventory.setItem(14, getItem(player, "sklepksiazki4"));
			inventory.setItem(15, getItem(player, "sklepksiazki5"));
			inventory.setItem(20, getItem(player, "sklepksiazki6"));
			inventory.setItem(21, getItem(player, "sklepksiazki7"));
			inventory.setItem(22, getItem(player, "sklepksiazki8"));
			inventory.setItem(23, getItem(player, "sklepksiazki9"));
			inventory.setItem(24, getItem(player, "sklepksiazki10"));
			inventory.setItem(29, getItem(player, "sklepksiazki11"));
			inventory.setItem(30, getItem(player, "sklepksiazki12"));
			inventory.setItem(31, getItem(player, "sklepksiazki13"));
			inventory.setItem(32, getItem(player, "sklepksiazki14"));
			inventory.setItem(33, getItem(player, "sklepksiazki15"));

			inventory.setItem(45, getItem(player, "arrowback"));
			inventory.setItem(53, getItem(player, "arrownext"));
			inventory.setItem(49, getItem(player, "powrot"));
			inventory.setItem(26, getItem(player, "sklepkupno"));
		}
		else if (type.equalsIgnoreCase("sklep_przedmioty_ksiazki2"))
		{
			inventory.setItem(9, getItem(player, "sklepitemy"));
			inventory.setItem(18, getItem(player, "sklepksiazkiselected"));
			inventory.setItem(27, getItem(player, "sklepspecjalne"));

			inventory.setItem(11, getItem(player, "sklepksiazki16"));
			inventory.setItem(12, getItem(player, "sklepksiazki17"));
			inventory.setItem(13, getItem(player, "sklepksiazki18"));
			inventory.setItem(14, getItem(player, "sklepksiazki19"));
			inventory.setItem(15, getItem(player, "sklepksiazki20"));
			inventory.setItem(20, getItem(player, "sklepksiazki21"));
			inventory.setItem(21, getItem(player, "sklepksiazki22"));
			inventory.setItem(22, getItem(player, "sklepksiazki23"));
			inventory.setItem(23, getItem(player, "sklepksiazki24"));

			inventory.setItem(45, getItem(player, "arrowback"));
			inventory.setItem(53, getItem(player, "arrownext"));
			inventory.setItem(49, getItem(player, "powrot"));
			inventory.setItem(26, getItem(player, "sklepkupno"));
		}
		else if (type.equalsIgnoreCase("sklep_przedmioty_specjalne"))
		{
			inventory.setItem(9, getItem(player, "sklepitemy"));
			inventory.setItem(18, getItem(player, "sklepksiazki"));
			inventory.setItem(27, getItem(player, "sklepspecjalneselected"));

			inventory.setItem(11, getItem(player, "sklepspecjalne1"));
			inventory.setItem(12, getItem(player, "sklepspecjalne2"));
			inventory.setItem(13, getItem(player, "sklepspecjalne3"));
			inventory.setItem(14, new ItemStack(Material.BARRIER));

			TaskLib.getInstance().runAsync(() -> {
				if ((boolean) FileManager.getInstance().getConfigValue("function.chemistry.status"))
					inventory.setItem(14, getItem(player, "sklepspecjalne4"));
			});

			inventory.setItem(45, getItem(player, "arrowback"));
			inventory.setItem(53, getItem(player, "arrownext"));
			inventory.setItem(49, getItem(player, "powrot"));
			inventory.setItem(26, getItem(player, "sklepkupno"));
		}
        else if (type.equalsIgnoreCase("questy"))
        {
            inventory.setItem(10, getItem(player, "questy1"));
            inventory.setItem(11, getItem(player, "questy2"));
            inventory.setItem(12, getItem(player, "questy3"));
            inventory.setItem(14, getItem(player, "questy4"));
            inventory.setItem(15, getItem(player, "questy5"));
            inventory.setItem(16, getItem(player, "questy6"));

            inventory.setItem(20, getItem(player, "questy7"));
            inventory.setItem(21, getItem(player, "questy8"));
            inventory.setItem(22, getItem(player, "questy9"));
            inventory.setItem(23, getItem(player, "questy10"));
            inventory.setItem(24, getItem(player, "questy11"));

            inventory.setItem(30, getItem(player, "questy12"));
            inventory.setItem(31, getItem(player, "questy13"));
            inventory.setItem(32, getItem(player, "questy14"));
        }
        else if (type.equalsIgnoreCase("postac"))
        {
			inventory.setItem(13, getItem(player, "postacloading"));
			inventory.setItem(20, getItem(player, "postacslub"));
			inventory.setItem(24, getItem(player, "postacgang"));
			inventory.setItem(31, getItem(player, "postacupgrades"));
			inventory.setItem(38, getItem(player, "postacupgrade1"));
			inventory.setItem(39, getItem(player, "postacupgrade2"));
			inventory.setItem(40, getItem(player, "postacupgrade3"));
			inventory.setItem(41, getItem(player, "postacupgrade4"));
			inventory.setItem(42, getItem(player, "postacupgrade5"));

			TaskLib.getInstance().runAsync(() -> inventory.setItem(13, getItem(player, "postacstatystyki")));
        }
		else if (type.equalsIgnoreCase("drug_table_amina"))
		{
			TaskLib.getInstance().runAsync(() -> {
					inventory.setItem(11, ChemistryDrug.getDrug(Chemistries.metyloamina));
					inventory.setItem(12, ChemistryDrug.getDrug(Chemistries.metylenoamina));
					inventory.setItem(13, ChemistryDrug.getDrug(Chemistries.fenyloamina));
					inventory.setItem(14, ChemistryDrug.getDrug(Chemistries.fluoroamina));
					inventory.setItem(15, ChemistryDrug.getDrug(Chemistries.dimetoamina));

                    inventory.setItem(30, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
					inventory.setItem(31, ChemistryDrug.getDrug(Chemistries.amina));
                    inventory.setItem(32, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

					inventory.setItem(45, getItem(player, "drug_table_info"));
					inventory.setItem(49, getItem(player, "powrot"));
					inventory.setItem(53, getItem(player, "drug_table_info2"));
			});
		}
		else if (type.equalsIgnoreCase("drug_table_opium"))
		{
			TaskLib.getInstance().runAsync(() -> {
					inventory.setItem(11, ChemistryDrug.getDrug(Chemistries.alprazolam));
					inventory.setItem(12, ChemistryDrug.getDrug(Chemistries.metylomorfina));
					inventory.setItem(13, ChemistryDrug.getDrug(Chemistries.morfina));
					inventory.setItem(14, ChemistryDrug.getDrug(Chemistries.heroina));
					inventory.setItem(15, ChemistryDrug.getDrug(Chemistries.fentanyl));

                    inventory.setItem(30, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
                    inventory.setItem(31, ChemistryDrug.getDrug(Chemistries.opium));
                    inventory.setItem(32, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

					inventory.setItem(45, getItem(player, "drug_table_info"));
					inventory.setItem(49, getItem(player, "powrot"));
					inventory.setItem(53, getItem(player, "drug_table_info2"));
			});
		}
        else if (type.equalsIgnoreCase("drug_table_metyloamina"))
        {
			TaskLib.getInstance().runAsync(() -> {
				inventory.setItem(11, ChemistryDrug.getDrug(Chemistries.metamfetamina));
				inventory.setItem(12, ChemistryDrug.getDrug(Chemistries.metafedron));
				inventory.setItem(13, ChemistryDrug.getDrug(Chemistries.metylon));
				inventory.setItem(14, ChemistryDrug.getDrug(Chemistries.metylometkatynon));
				inventory.setItem(15, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

				inventory.setItem(30, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
				inventory.setItem(31, ChemistryDrug.getDrug(Chemistries.metyloamina));
				inventory.setItem(32, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

				inventory.setItem(45, getItem(player, "drug_table_info"));
				inventory.setItem(49, getItem(player, "powrot"));
				inventory.setItem(53, getItem(player, "drug_table_info2"));
			});
        }
		else if (type.equalsIgnoreCase("drug_table_metylenoamina"))
		{
			TaskLib.getInstance().runAsync(() -> {
				inventory.setItem(11, ChemistryDrug.getDrug(Chemistries.MDA));
				inventory.setItem(12, ChemistryDrug.getDrug(Chemistries.MDMA));
				inventory.setItem(13, ChemistryDrug.getDrug(Chemistries.MDPV));
				inventory.setItem(14, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
				inventory.setItem(15, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

				inventory.setItem(30, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
				inventory.setItem(31, ChemistryDrug.getDrug(Chemistries.metylenoamina));
				inventory.setItem(32, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

				inventory.setItem(45, getItem(player, "drug_table_info"));
				inventory.setItem(49, getItem(player, "powrot"));
				inventory.setItem(53, getItem(player, "drug_table_info2"));
			});
		}
		else if (type.equalsIgnoreCase("drug_table_fenyloamina"))
		{
			TaskLib.getInstance().runAsync(() -> {
				inventory.setItem(11, ChemistryDrug.getDrug(Chemistries.amfetamina));
				inventory.setItem(12, ChemistryDrug.getDrug(Chemistries.mefedron));
				inventory.setItem(13, ChemistryDrug.getDrug(Chemistries.klefedron));
				inventory.setItem(14, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
				inventory.setItem(15, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

				inventory.setItem(30, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
				inventory.setItem(31, ChemistryDrug.getDrug(Chemistries.fenyloamina));
				inventory.setItem(32, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

				inventory.setItem(45, getItem(player, "drug_table_info"));
				inventory.setItem(49, getItem(player, "powrot"));
				inventory.setItem(53, getItem(player, "drug_table_info2"));
			});
		}
		else if (type.equalsIgnoreCase("drug_table_fluoroamina"))
		{
			TaskLib.getInstance().runAsync(() -> {
				inventory.setItem(11, ChemistryDrug.getDrug(Chemistries.fluoroamfetamina));
				inventory.setItem(12, ChemistryDrug.getDrug(Chemistries.flefedron));
				inventory.setItem(13, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
				inventory.setItem(14, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
				inventory.setItem(15, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

				inventory.setItem(30, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
				inventory.setItem(31, ChemistryDrug.getDrug(Chemistries.fluoroamina));
				inventory.setItem(32, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

				inventory.setItem(45, getItem(player, "drug_table_info"));
				inventory.setItem(49, getItem(player, "powrot"));
				inventory.setItem(53, getItem(player, "drug_table_info2"));
			});
		}
		else if (type.equalsIgnoreCase("drug_table_dimetoamina"))
		{
			TaskLib.getInstance().runAsync(() -> {
				inventory.setItem(11, ChemistryDrug.getDrug(Chemistries.kokaina));
				inventory.setItem(12, ChemistryDrug.getDrug(Chemistries.kleksedron));
				inventory.setItem(13, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
				inventory.setItem(14, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
				inventory.setItem(15, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

				inventory.setItem(30, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
				inventory.setItem(31, ChemistryDrug.getDrug(Chemistries.dimetoamina));
				inventory.setItem(32, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

				inventory.setItem(45, getItem(player, "drug_table_info"));
				inventory.setItem(49, getItem(player, "powrot"));
				inventory.setItem(53, getItem(player, "drug_table_info2"));
			});
		}
		else if (type.equalsIgnoreCase("tradingserveritems"))
		{
			for (int slot = 0; slot < 36; slot++)
				inventory.setItem(slot, null);

			for (int slot = 45; slot < 54; slot++)
				inventory.setItem(slot, null);

			TaskLib.getInstance().runAsync(() -> inventory.setItem(50, getItem(player, "tradingplayer")));
			inventory.setItem(49, getItem(player, "powrot"));
			inventory.setItem(52, getItem(player, "tradinginfo"));
			inventory.setItem(48, getItem(player, "tradingofferss"));

			if (Trading.getTrades().size() == 0)
				inventory.setItem(22, getItem(player, "tradingclear"));

			int s = 0;
			for (ItemStack trade : Trading.getTradesView())
			{
				inventory.setItem(s, trade);
				s++;
			}
		}
		else if (type.equalsIgnoreCase("tradingplayeritems"))
		{
			for (int slot = 0; slot < 36; slot++)
				inventory.setItem(slot, null);

			for (int slot = 45; slot < 54; slot++)
				inventory.setItem(slot, null);

			TaskLib.getInstance().runAsync(() -> inventory.setItem(50, getItem(player, "tradingplayers")));
			inventory.setItem(49, getItem(player, "powrot"));
			inventory.setItem(52, getItem(player, "tradinginfo"));
			inventory.setItem(48, getItem(player, "tradingoffers"));

			if (Trading.getPlayerTradesView(player).size() == 0)
				inventory.setItem(22, getItem(player, "tradingnull"));

			int s = 20;

			for (ItemStack trade : Trading.getPlayerTradesView(player))
			{
				final String status = trade.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "STATUS"), PersistentDataType.STRING);

				if (status.equalsIgnoreCase("pending"))
				{
					inventory.setItem(s - 9, new ItemStack(Material.YELLOW_STAINED_GLASS_PANE));
					inventory.setItem(s + 9, new ItemStack(Material.YELLOW_STAINED_GLASS_PANE));
				}
				else if (status.equalsIgnoreCase("ended"))
				{
					inventory.setItem(s - 9, new ItemStack(Material.RED_STAINED_GLASS_PANE));
					inventory.setItem(s + 9, new ItemStack(Material.RED_STAINED_GLASS_PANE));
				}
				else if (status.equalsIgnoreCase("buyed"))
				{
					inventory.setItem(s - 9, new ItemStack(Material.LIME_STAINED_GLASS_PANE));
					inventory.setItem(s + 9, new ItemStack(Material.LIME_STAINED_GLASS_PANE));
				}

				inventory.setItem(s, trade);
				s++;
			}
		}

		if (type.contains("craftingi"))
		{
			if (DataManager.getInstance().getLocal(player).getRank().equalsIgnoreCase("Administrator"))
				if (((ArrayList<String>) FileManager.getInstance().recipes().get("recipes.enabled")).contains(type.replaceAll("craftingi", "")))
					inventory.setItem(8, getItem(player, "recipe_on"));
				else
					inventory.setItem(8, getItem(player, "recipe_off"));
			else
				inventory.setItem(8, getItem(player, "recipe_blocked"));
		}

		player.openInventory(inventory);
		player.updateInventory();
	}

	public static String getName(final Player player)
	{
		return player.getOpenInventory().getTitle();
	}

	private static void fillInventory(Inventory inventory)
	{
		final int size = inventory.getSize();

		for (int slot = 0; slot < size; slot++)
			inventory.setItem(slot, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
	}

	public static ItemStack getItem(final Player player, final String name)
	{
		if (name.equalsIgnoreCase("home1"))
		{
			ArrayList<String> lore = new ArrayList<String>();

			if (DataManager.getInstance().getLocal(player).isHomeExist("1"))
			{
				String[] no = {"&c&l» &a&lDOM 1", "", "&c&l» &aWspolrzedne:", " &8> &7Swiat : <brak danych>", " &8> &7X : <brak danych>"
						, " &8> &7Y : <brak danych>", " &8> &7Z : <brak danych>", " &8> &7Yaw : <brak danych>", " &8> &7Pitch : <brak danych>"
						, "", ChatLib.ColorUtil.formatHEX("   #666666(LPM - Teleportacja do domku"), ChatLib.ColorUtil.formatHEX("   #666666PPM - Zmiana miejsca polozenia domku)"), ""};

				for (String vers : no)
					lore.add(ChatColor.translateAlternateColorCodes('&', vers));
			}
			else
			{
				String[] yes  = {"&c&l» &a&lDOM 1", "", "&c&l» &aWspolrzedne:", " &8> &7Swiat : " + DataManager.getInstance().getLocal(player).getHome("1").getWorld().getName(), " &8> &7X : " + DataManager.getInstance().getLocal(player).getHome("1").getX()
						, " &8> &7Y : " + DataManager.getInstance().getLocal(player).getHome("1").getY(), " &8> &7Z : " + + DataManager.getInstance().getLocal(player).getHome("1").getZ(), " &8> &7Yaw : " + DataManager.getInstance().getLocal(player).getHome("1").getYaw(), " &8> &7Pitch : " + DataManager.getInstance().getLocal(player).getHome("1").getPitch()
						, "", ChatLib.ColorUtil.formatHEX("   #666666(LPM - Teleportacja do domku"), ChatLib.ColorUtil.formatHEX("   #666666PPM - Zmiana miejsca polozenia domku)"), ""};

				for (String vers : yes)
					lore.add(ChatColor.translateAlternateColorCodes('&', vers));
			}

			ItemStack item = new ItemStack(Material.OAK_DOOR);
			ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(" ");
			meta.setLore(lore);
			item.setItemMeta(meta);

			return item;
		}

		if (name.equalsIgnoreCase("home2"))
		{
			ArrayList<String> lore = new ArrayList<String>();

			if (DataManager.getInstance().getLocal(player).isHomeExist("2"))
			{
				String[] no = {"&c&l» &a&lDOM 2", "", "&c&l» &aWspolrzedne:", " &8> &7Swiat : <brak danych>", " &8> &7X : <brak danych>"
						, " &8> &7Y : <brak danych>", " &8> &7Z : <brak danych>", " &8> &7Yaw : <brak danych>", " &8> &7Pitch : <brak danych>"
						, "", ChatLib.ColorUtil.formatHEX("   #666666(LPM - Teleportacja do domku"), ChatLib.ColorUtil.formatHEX("   #666666PPM - Zmiana miejsca polozenia domku)"), ""};

				for (String vers : no)
					lore.add(ChatColor.translateAlternateColorCodes('&', vers));
			}
			else
			{
				String[] yes  = {"&c&l» &a&lDOM 2", "", "&c&l» &aWspolrzedne:", " &8> &7Swiat : " + DataManager.getInstance().getLocal(player).getHome("2").getWorld().getName(), " &8> &7X : " + DataManager.getInstance().getLocal(player).getHome("2").getX()
						, " &8> &7Y : " + DataManager.getInstance().getLocal(player).getHome("2").getY(), " &8> &7Z : " + + DataManager.getInstance().getLocal(player).getHome("2").getZ(), " &8> &7Yaw : " + DataManager.getInstance().getLocal(player).getHome("2").getYaw(), " &8> &7Pitch : " + DataManager.getInstance().getLocal(player).getHome("2").getPitch()
						, "", ChatLib.ColorUtil.formatHEX("   #666666(LPM - Teleportacja do domku"), ChatLib.ColorUtil.formatHEX("   #666666PPM - Zmiana miejsca polozenia domku)"), ""};

				for (String vers : yes)
					lore.add(ChatColor.translateAlternateColorCodes('&', vers));
			}

			ItemStack item = new ItemStack(Material.OAK_DOOR);
			ItemMeta meta = item.getItemMeta();

			meta.setDisplayName(" ");
			meta.setLore(lore);
			item.setItemMeta(meta);

			return item;
		}

		if (name.equalsIgnoreCase("homeinfo"))
		{
			final String[] lore = {"&c&l» &a&lINFORMACJE", "", "&c&l» &7Domki sluza do szybkiej teleportacji", "   &7miedzy wybranymi lokacjami na swiecie!", "",
							"&c&l» &7Kazdy gracz ma przypisane dwa", "   &7mozliwe do ustawienia i uzycia domki!", ""};

			return createInvItem(lore, Material.BOOK, false);
		}

		if (name.equalsIgnoreCase("schowekinfo"))
		{
			final String[] lore = {"&c&l» &a&lINFORMACJE", "", "&c&l» &7Za pomoca ulepszenia schowku", "   &7otrzymujesz mozliwosc powiekszenia go!", "",
							"&c&l» &7Kazdy gracz ma poczatkowo 27 slotow,", "   &7ktore moze powiekszyc do az 54!", ""};

			return createInvItem(lore, Material.BOOK, false);
		}

		if (name.equalsIgnoreCase("schowek1"))
		{
			final String[] lore = {"&c&l» &a&lULEPSZENIE", "", "&c&l» &7Kazdorazowe ulepszenie schowku powieksza", "   &7go o 9 slotow, co jest rowne jednemu paskowi.", "   &7Zakup jest finalny i nie ma mozliwosci cofniecia go!", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #ffc936200 ⛃"),
					ChatLib.ColorUtil.formatHEX(" &8> &7Aktualny poziom #80ff1f" + (DataManager.getInstance().getLocal(player).getSchowekLevel() + 1) + "&7/#80ff1f4"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Kupno ulepszenia)"), ""};

			return createInvItem(lore, Material.DIAMOND_PICKAXE, false);
		}

		if (name.equalsIgnoreCase("pomocinfo"))
		{
			final String[] lore = {"&c&l» &a&lCENTRUM POMOCY SERWERA", "", "&c&l» &7Ponizej znajdziesz liste wszystkich", "   &7niezbednych wskazowek i informacji!", "",
							"&c&l» &7Mamy nadzieje, ze uda Ci sie rozwiazac", "   &7problem!", "", "&c&l» &7Aby zglosic problem techniczny napisz ", "   " +
							"&7na naszym discordzie lub issue-tracker!", "", "   &7(Survival " + Main.VERSION + ")", ""};

			return createInvItem(lore, Material.NETHER_STAR, true);
		}

		if (name.equalsIgnoreCase("pomocp1"))
		{
			final String[] lore = {"&c&l» &a&lKOMENDY", "", "&c&l» &7Lista dostepnych dla Ciebie komend", "   &7zalezna jest od Twojej rangi na serwerze!", "",
					"&c&l» &7Nacisnij &c/&7, a nastepnia &cTAB!", ""};

			return createInvItem(lore, Material.BOOK, false);
		}

		if (name.equalsIgnoreCase("pomocp2"))
		{
			final String[] lore = {"&c&l» &a&lTRADING", "", "&c&l» &7Na serwerze istnieje wbudowany", "   &7system rynku graczy, w ktorym mozesz",
							"   &7sprzedawac i kupowac przedmioty", "   &7po swoich cenach ;)", "", "&c&l» &7Zakladka dostepna jest w &c/sklep!", ""};

			return createInvItem(lore, Material.OAK_SIGN, false);
		}

		if (name.equalsIgnoreCase("pomocp3"))
		{
			final String[] lore = {"&c&l» &a&lPLACEHOLDERS", "", "&c&l» &7Jesli nie wiesz czegos o sobie, badz", "   &7chcesz komus cos szybko przekazac, mozesz",
					"   &7uzyc zamiennikow czatowych. Ponizej znajdziesz", "   &7ich liste:",
					"",
					"   &c%swiat%&7 - wyswietla aktualny swiat, w jakim sie znajdujesz",
					"   &c%xyz%&7 - pokazuje Twoje aktualne koordynaty",
					"   &c%ping%&7 - wyswietla Twoj aktualny ping",
					"   &c%monety%&7 - wyswietla ilosc monet jaka posiadasz",
					"   &c%zabojstwa%&7 - ukazuje liczbe zabitych przez Ciebie osob",
					"   &c%questy%&7 - wyswietla liczbe ukonczonych questow",
					"   &c%osiagniecia%&7 - wyswietla liczbe ukonczonych osiagniec",
					"   &c%smierci%&7 - pokazuje ile razy odszedles z tego swiata",
					"   &c%przedmiot%&7 - pokazuje aktualnie trzymany przedmiot",
					"   &c%trzezwosc%&7 - pozwala pokazac Twoj stan trzezwosci",
					""};

			return createInvItem(lore, Material.BOOKSHELF, false);
		}

		if (name.equalsIgnoreCase("pomocl1"))
		{
			final String[] lore = {"&c&l» &a&lWHAAAT?!", "", "&c&l» &7Gowno! A tak naprawde najbardziej rozbudowany", "   &7i oddany graczom wytwor minecrafta, ktory", "   &7posiada wszystko, aby zapewnic ci frajde!", ""};

			return createInvItem(lore, Material.LILY_PAD, false);
		}

		if (name.equalsIgnoreCase("pomocl2"))
		{
			final String[] lore = {"&c&l» &a&lPOSTAC", "", "&c&l» &7Na serwerze wbudowany jest system zadan", "   &7oraz ulepszen Twojej postaci!", "", " &8> &7Lista serwerowych misji do wykonania", "   &7dostepna jest pod komenda &c/questy!", "", " &8> &7Menu ulepszen wraz z opisem znajdziesz pod", "   &7komenda &c/postac!", ""};

			return createInvItem(lore, Material.TRIDENT, false);
		}

		if (name.equalsIgnoreCase("pomocl3"))
		{
			final String[] lore = {"&c&l» &a&lSWIATY", "", "&c&l» &7Na serwerze znajduja sie dodatkowe", "   &7specjalne swiaty, na ktore mozesz sie dostac!", "", "&c&l» &7W kazdym ze swiatow czekaja na Ciebie", "   &7spejcalne przedmioty i atrakcje!", "", " &8> &cAether&7, odpowiednik niebianskiego swiata aniolow.", "", " &8> &cTwilight&7, odpowiednik mrocznego lasu bolesci.", ""};

			return createInvItem(lore, Material.GRASS_BLOCK, false);
		}

		if (name.equalsIgnoreCase("arrownext"))
		{
			final String[] lore = {"&c&l» &a&lNASTEPNA STRONA","", "&c&l» &7Kliknij, aby przejsc do nastepnej", "   &7strony w tej zakladce!", "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Przejscie do nastepnej strony)"),""};

			return createInvItem(lore, Material.ARROW, false);
		}

		if (name.equalsIgnoreCase("arrowback"))
		{
			final String[] lore = {"&c&l» &a&lPOPRZEDNIA STRONA", "", "&c&l» &7Kliknij, aby cofnac sie do poprzedniej", "   &7strony w tej zakladce!", "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Przejscie do poprzedniej strony)"),""};

			return createInvItem(lore, Material.ARROW, false);
		}

		if (name.equalsIgnoreCase("powrot"))
		{
			final String[] lore = {"&c&l» &a&lPOWROT", "", "&c&l» &7Kliknij, aby cofnac sie do poprzedniej", "   &7zakladki lub zamknac obecne menu!", "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Opuszczenie menu)"),""};

			return createInvItem(lore, Material.OAK_DOOR, false);
		}

		if (name.equalsIgnoreCase("paletainfo"))
		{
			final String[] lore = {"&c&l» &a&lJAK UZYWAC 16.7M KOLOROW?", "", "&c&l» &7Tworzenie koloru polega na wpisaniu", "   &7go w postaci koloru HEX. Jak to zrobic?",
					"   &7Wpisz w Google fraze &o'hex color'&r&7, a nastepnie", "   &7wybierz swoj kolor i skopiuj jego postac HEX.", "", "&c&l» &7Przyklady takich kolorow to na przyklad:",
					"   &e#F83044&7, &e#8C8C8C &7czy &e#03FC41", "", "&c&l» &7Przykladowa wiadomosc: &e#03fc41Czesc!", ChatLib.ColorUtil.formatHEX(" &8> &7Finalny widok: #03fc41Czesc!"), ""};

			return createInvItem(lore, Material.BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepprzedmioty"))
		{
			final String[] lore = {"&c&l» &a&lPRZEDMIOTY", "", "&c&l» &7W sklepie z przedmiotami mozesz nabyc", "   &7prestizowe przedmioty specjalne, ksiazki z enchantami,",
					"   &7mineraly oraz wiele innych blokow i surowcow!", "", ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Wybranie zakladki)"), ""};

			return createInvItem(lore, Material.GRASS_BLOCK, false);
		}

		if (name.equalsIgnoreCase("sklepefekty"))
		{
			final String[] lore = {"&c&l» &a&lEFEKTY", "", "&c&l» &7W sklepie z efektami czekaja na Ciebie", "   &7mozliwe do zakupu efekty wielu mikstur,",
					"   &7ktore mozesz zakupic na pewien okres czasu!", "", ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Wybranie zakladki)"), ""};

			return createInvItem(lore, Material.HONEY_BOTTLE, false);
		}

		if (name.equalsIgnoreCase("skleptrading"))
		{
			final String[] lore = {"&c&l» &a&lCZARNY RYNEK", "", "&c&l» &7Na czarnym rynku czekaja na Ciebie", "   &7oferty przedmiotow wystawionych przez graczy."
					, "", ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Wybranie zakladki)"), ""};

			return createInvItem(lore, Material.GLISTERING_MELON_SLICE, false);
		}

		if (name.equalsIgnoreCase("sklepgangi"))
		{
			final String[] lore = {"&c&l» &a&lGANGI", "", "&c&l» &7Specjalna zakladka, w ktorej znajduja sie", "   &7ulepszenia do Twojego gangu!", "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Wybranie zakladki)"), ""};

			return createInvItem(lore, Material.DIAMOND_SWORD, false);
		}

		if (name.equalsIgnoreCase("sklepdodatki"))
		{
			final String[] lore = {"&c&l» &a&lDODATKI", "", "&c&l» &7W sklepie z dodatkami mozesz zakupic", "   &7kosmetyki oraz ulepszenia tylko dla Ciebie!",
					"   &7Jesli interesuje Cie zmiana koloru nicku,", "   &7mozliwosc powiekszenia pojemnosci swojego", "   &7schowku, itp. to nie czekaj i klikaj!", "", ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Wybranie zakladki)"), ""};

			return createInvItem(lore, Material.NETHER_STAR, false);
		}

		if (name.equalsIgnoreCase("sklepspeed"))
		{
			final String[] lore = {"&c&l» &a&lSZYBKOSC", "", "&c&l» &7Mozesz zakupic i nalozyc na siebie", "   &7jeden z 10 dostepnych efektow mikstur. Pamietaj",
					"   &7ze wchodza one w interakcje z pewnymi substancjami,",
					"   &7wiec nie zaleca sie ich sumowania!", "", "&c&l» &7Efekt: &bSzybkosc", " &8> &7Nasilenie: 1",
					" &8> &7Czas dzialania: 3m/8m/30m", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203900 ⛃&7/#fff2031500 ⛃&7/#fff2034000 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno efektu na okres 3 minut"), ChatLib.ColorUtil.formatHEX("   #666666&o(SHIFT + LPM - Kupno efektu na okres 8 minut"),
					ChatLib.ColorUtil.formatHEX("   #666666&o(SCROLL - Kupno efektu na okres 30 minut"), ""};;

			return createPotionInvItem(lore, Color.AQUA);
		}

		if (name.equalsIgnoreCase("sklepjumpboost"))
		{
			final String[] lore = {"&c&l» &a&lWYSOKI SKOK", "", "&c&l» &7Mozesz zakupic i nalozyc na siebie", "   &7jeden z 10 dostepnych efektow mikstur. Pamietaj",
					"   &7ze wchodza one w interakcje z pewnymi substancjami,",
					"   &7wiec nie zaleca sie ich sumowania!", "", "&c&l» &7Efekt: &aWysoki skok", " &8> &7Nasilenie: 4",
					" &8> &7Czas dzialania: 3m/8m/30m", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203900 ⛃&7/#fff2031500 ⛃&7/#fff2034000 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno efektu na okres 3 minut"), ChatLib.ColorUtil.formatHEX("   #666666&o(SHIFT + LPM - Kupno efektu na okres 8 minut"),
					ChatLib.ColorUtil.formatHEX("   #666666&o(SCROLL - Kupno efektu na okres 30 minut"), ""};;

			return createPotionInvItem(lore, Color.GREEN);
		}

		if (name.equalsIgnoreCase("sklephaste"))
		{
			final String[] lore = {"&c&l» &a&lSZYBKOSC KOPANIA", "", "&c&l» &7Mozesz zakupic i nalozyc na siebie", "   &7jeden z 10 dostepnych efektow mikstur. Pamietaj",
					"   &7ze wchodza one w interakcje z pewnymi substancjami,",
					"   &7wiec nie zaleca sie ich sumowania!", "", "&c&l» &7Efekt: &eSzybkosc kopania", " &8> &7Nasilenie: 1",
					" &8> &7Czas dzialania: 3m/8m/30m", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203900 ⛃&7/#fff2031500 ⛃&7/#fff2034000 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno efektu na okres 3 minut"), ChatLib.ColorUtil.formatHEX("   #666666&o(SHIFT + LPM - Kupno efektu na okres 8 minut"),
					ChatLib.ColorUtil.formatHEX("   #666666&o(SCROLL - Kupno efektu na okres 30 minut"), ""};;

			return createPotionInvItem(lore, Color.YELLOW);
		}

		if (name.equalsIgnoreCase("sklepstrength"))
		{
			final String[] lore = {"&c&l» &a&lSILA", "", "&c&l» &7Mozesz zakupic i nalozyc na siebie", "   &7jeden z 10 dostepnych efektow mikstur. Pamietaj",
					"   &7ze wchodza one w interakcje z pewnymi substancjami,",
					"   &7wiec nie zaleca sie ich sumowania!", "", "&c&l» &7Efekt: &dSila", " &8> &7Nasilenie: 1",
					" &8> &7Czas dzialania: 3m/8m/30m", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203900 ⛃&7/#fff2031500 ⛃&7/#fff2034000 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno efektu na okres 3 minut"), ChatLib.ColorUtil.formatHEX("   #666666&o(SHIFT + LPM - Kupno efektu na okres 8 minut"),
					ChatLib.ColorUtil.formatHEX("   #666666&o(SCROLL - Kupno efektu na okres 30 minut"), ""};;

			return createPotionInvItem(lore, Color.FUCHSIA);
		}

		if (name.equalsIgnoreCase("sklepresistance"))
		{
			final String[] lore = {"&c&l» &a&lODPORNOSC", "", "&c&l» &7Mozesz zakupic i nalozyc na siebie", "   &7jeden z 10 dostepnych efektow mikstur. Pamietaj",
					"   &7ze wchodza one w interakcje z pewnymi substancjami,",
					"   &7wiec nie zaleca sie ich sumowania!", "", "&c&l» &7Efekt: &7Odpornosc", " &8> &7Nasilenie: 1",
					" &8> &7Czas dzialania: 3m/8m/30m", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203900 ⛃&7/#fff2031500 ⛃&7/#fff2034000 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno efektu na okres 3 minut"), ChatLib.ColorUtil.formatHEX("   #666666&o(SHIFT + LPM - Kupno efektu na okres 8 minut"),
					ChatLib.ColorUtil.formatHEX("   #666666&o(SCROLL - Kupno efektu na okres 30 minut"), ""};;

			return createPotionInvItem(lore, Color.WHITE);
		}

		if (name.equalsIgnoreCase("sklepfireresistance"))
		{
			final String[] lore = {"&c&l» &a&lODPORNOSC NA OGIEN", "", "&c&l» &7Mozesz zakupic i nalozyc na siebie", "   &7jeden z 10 dostepnych efektow mikstur. Pamietaj",
					"   &7ze wchodza one w interakcje z pewnymi substancjami,",
					"   &7wiec nie zaleca sie ich sumowania!", "", "&c&l» &7Efekt: &6Odpornosc na ogien", " &8> &7Nasilenie: 2",
					" &8> &7Czas dzialania: 3m/8m/30m", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203900 ⛃&7/#fff2031500 ⛃&7/#fff2034000 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno efektu na okres 3 minut"), ChatLib.ColorUtil.formatHEX("   #666666&o(SHIFT + LPM - Kupno efektu na okres 8 minut"),
					ChatLib.ColorUtil.formatHEX("   #666666&o(SCROLL - Kupno efektu na okres 30 minut"), ""};;

			return createPotionInvItem(lore, Color.ORANGE);
		}

		if (name.equalsIgnoreCase("sklepregeneration"))
		{
			final String[] lore = {"&c&l» &a&lREGENERACJA", "", "&c&l» &7Mozesz zakupic i nalozyc na siebie", "   &7jeden z 10 dostepnych efektow mikstur. Pamietaj",
					"   &7ze wchodza one w interakcje z pewnymi substancjami,",
					"   &7wiec nie zaleca sie ich sumowania!", "", "&c&l» &7Efekt: &cRegeneracja", " &8> &7Nasilenie: 1",
					" &8> &7Czas dzialania: 3m/8m/30m", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203900 ⛃&7/#fff2031500 ⛃&7/#fff2034000 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno efektu na okres 3 minut"), ChatLib.ColorUtil.formatHEX("   #666666&o(SHIFT + LPM - Kupno efektu na okres 8 minut"),
					ChatLib.ColorUtil.formatHEX("   #666666&o(SCROLL - Kupno efektu na okres 30 minut"), ""};;

			return createPotionInvItem(lore, Color.RED);
		}

		if (name.equalsIgnoreCase("sklepinvis"))
		{
			final String[] lore = {"&c&l» &a&lNIEWIDZIALNOSC", "", "&c&l» &7Mozesz zakupic i nalozyc na siebie", "   &7jeden z 10 dostepnych efektow mikstur. Pamietaj",
					"   &7ze wchodza one w interakcje z pewnymi substancjami,",
					"   &7wiec nie zaleca sie ich sumowania!", "", "&c&l» &7Efekt: &fNiewidzialnosc", " &8> &7Nasilenie: 2",
					" &8> &7Czas dzialania: 3m/8m/30m", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203900 ⛃&7/#fff2031500 ⛃&7/#fff2034000 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno efektu na okres 3 minut"), ChatLib.ColorUtil.formatHEX("   #666666&o(SHIFT + LPM - Kupno efektu na okres 8 minut"),
					ChatLib.ColorUtil.formatHEX("   #666666&o(SCROLL - Kupno efektu na okres 30 minut"), ""};;

			return createPotionInvItem(lore, Color.SILVER);
		}

		if (name.equalsIgnoreCase("sklepwaterbreathing"))
		{
			final String[] lore = {"&c&l» &a&lODDYCHANIE POD WODA", "", "&c&l» &7Mozesz zakupic i nalozyc na siebie", "   &7jeden z 10 dostepnych efektow mikstur. Pamietaj",
					"   &7ze wchodza one w interakcje z pewnymi substancjami,",
					"   &7wiec nie zaleca sie ich sumowania!", "", "&c&l» &7Efekt: &1Oddychanie pod woda", " &8> &7Nasilenie: 1",
					" &8> &7Czas dzialania: 3m/8m/30m", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203900 ⛃&7/#fff2031500 ⛃&7/#fff2034000 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno efektu na okres 3 minut"), ChatLib.ColorUtil.formatHEX("   #666666&o(SHIFT + LPM - Kupno efektu na okres 8 minut"),
					ChatLib.ColorUtil.formatHEX("   #666666&o(SCROLL - Kupno efektu na okres 30 minut"), ""};;

			return createPotionInvItem(lore, Color.NAVY);
		}

		if (name.equalsIgnoreCase("sklepnightvision"))
		{
			final String[] lore = {"&c&l» &a&lWIDZENIE W CIEMNOSCI", "", "&c&l» &7Mozesz zakupic i nalozyc na siebie", "   &7jeden z 10 dostepnych efektow mikstur. Pamietaj",
					"   &7ze wchodza one w interakcje z pewnymi substancjami,",
					"   &7wiec nie zaleca sie ich sumowania!", "", "&c&l» &7Efekt: &9Widzenie w ciemnosci", " &8> &7Nasilenie: 2",
					" &8> &7Czas dzialania: 3m/8m/30m", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203900 ⛃&7/#fff2031500 ⛃&7/#fff2034000 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno efektu na okres 3 minut"), ChatLib.ColorUtil.formatHEX("   #666666&o(SHIFT + LPM - Kupno efektu na okres 8 minut"),
					ChatLib.ColorUtil.formatHEX("   #666666&o(SCROLL - Kupno efektu na okres 30 minut"), ""};;

			return createPotionInvItem(lore, Color.BLUE);
		}

		if (name.equalsIgnoreCase("sklepschowek"))
		{
			final String[] lore = {"&c&l» &a&lPOWIEKSZENIE SCHOWKU", "", "&c&l» &7Kliknij aby przeniesc sie do menu ulepszenia", "   &7schowku!", "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Wybranie zakladki)"), ""};

			return createInvItem(lore, Material.CHEST, false);
		}

		if (name.equalsIgnoreCase("sklepvoucher"))
		{
			final String[] lore = {"&c&l» &a&lVOUCHER NA UNMUTE", "", "&c&l» &7Dodatek ten pozwoli Ci zdjac nalozone", "   &7wyciszenie na czacie, usuwajac je. Jezeli nie", "   &7jestes wyciszony - nie bedziesz w stanie zakupic",
					"   &7tego przedmiotu. Zakup jest finalny i jednorazowy!", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203300 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Zakup vouchera)"), ""};

			return createInvItem(lore, Material.MAP, false);
		}

		if (name.equalsIgnoreCase("sklepslub"))
		{
			final String[] lore = {"&c&l» &a&lSLUB", "", "&c&l» &7Mozesz wziac slub z dowolnym graczem", "   &7kompletnie za darmo za pomoca komendy &c/slub!", "",
							"&c&l» &7Pre-view Twojego nicku przed slubem:", " &8> " + ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.getGangInChat(DataManager.getInstance().getLocal(player).getGang()) + "&c" + "&r" + ChatLib.returnPlayerColor(player) + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c Czesc!"), "",
							"&c&l» &7Pre-view Twojego nicku po slubie:", " &8> " + ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.getGangInChat(DataManager.getInstance().getLocal(player).getGang()) + "&c❤ #fc7474" + "&r" + ChatLib.returnPlayerColor(player) + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c Czesc!"), "",};

			return createInvItem(lore, Material.POPPY, false);
		}

		if (name.equalsIgnoreCase("sklepkolor1"))
		{
			final String[] lore = {"&c&l» &a&lZMIANA KOLORU NICKU", "", "&c&l» &7Mozesz dowolnie kupic jeden z 8 dostepnych", "   &7kolorow nicku na czacie. Pamietaj jednak", "   &7ze zakup koloru jest finalny i jednorazowy!", "", "&c&l» &7Pre-view nowego koloru:",
					" &8> " + ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.getGangInChat(DataManager.getInstance().getLocal(player).getGang()) + "&c" + ChatLib.returnMarryPrefix(player) +  "&r#fc7474" + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c Czesc!"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Kolor: #fc7474CZERWONY"), " &8> &7Oznaczenie HEX: #fc7474", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203400 ⛃"), " &8> &7Status posiadania: " + ChatLib.ColorUtil.formatHEX(ChatLib.isPlayerColor(player, "#fc7474")), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Kupno i zmiana)"), ""};

			return createInvItem(lore, Material.RED_DYE, false);
		}

		if (name.equalsIgnoreCase("sklepkolor2"))
		{
			final String[] lore = {"&c&l» &a&lZMIANA KOLORU NICKU", "", "&c&l» &7Mozesz dowolnie kupic jeden z 8 dostepnych", "   &7kolorow nicku na czacie. Pamietaj jednak", "   &7ze zakup koloru jest finalny i jednorazowy!", "", "&c&l» &7Pre-view nowego koloru:",
					" &8> " + ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.getGangInChat(DataManager.getInstance().getLocal(player).getGang()) + "&c" + ChatLib.returnMarryPrefix(player) +  "&r#3075ff" + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c Czesc!"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Kolor: #3075ffNIEBIESKI"), " &8> &7Oznaczenie HEX: #3075ff", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203400 ⛃"), " &8> &7Status posiadania: " + ChatLib.ColorUtil.formatHEX(ChatLib.isPlayerColor(player, "#3075ff")), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Kupno i zmiana)"), ""};

			return createInvItem(lore, Material.LIGHT_BLUE_DYE, false);
		}

		if (name.equalsIgnoreCase("sklepkolor3"))
		{
			final String[] lore = {"&c&l» &a&lZMIANA KOLORU NICKU", "", "&c&l» &7Mozesz dowolnie kupic jeden z 8 dostepnych", "   &7kolorow nicku na czacie. Pamietaj jednak", "   &7ze zakup koloru jest finalny i jednorazowy!", "", "&c&l» &7Pre-view nowego koloru:",
					" &8> " + ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.getGangInChat(DataManager.getInstance().getLocal(player).getGang()) + "&c" + ChatLib.returnMarryPrefix(player) +  "&r#02d645" + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c Czesc!"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Kolor: #02d645ZIELONY"), " &8> &7Oznaczenie HEX: #02d645", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203400 ⛃"), " &8> &7Status posiadania: " + ChatLib.ColorUtil.formatHEX(ChatLib.isPlayerColor(player, "#02d645")), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Kupno i zmiana)"), ""};

			return createInvItem(lore, Material.LIME_DYE, false);
		}

		if (name.equalsIgnoreCase("sklepkolor4"))
		{
			final String[] lore = {"&c&l» &a&lZMIANA KOLORU NICKU", "", "&c&l» &7Mozesz dowolnie kupic jeden z 8 dostepnych", "   &7kolorow nicku na czacie. Pamietaj jednak", "   &7ze zakup koloru jest finalny i jednorazowy!", "", "&c&l» &7Pre-view nowego koloru:",
					" &8> " + ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.getGangInChat(DataManager.getInstance().getLocal(player).getGang()) + "&c" + ChatLib.returnMarryPrefix(player) +  "&r#fcff33" + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c Czesc!"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Kolor: #fcff33ZOLTY"), " &8> &7Oznaczenie HEX: #fcff33", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203400 ⛃"), " &8> &7Status posiadania: " + ChatLib.ColorUtil.formatHEX(ChatLib.isPlayerColor(player, "#fcff33")), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Kupno i zmiana)"), ""};

			return createInvItem(lore, Material.YELLOW_DYE, false);
		}

		if (name.equalsIgnoreCase("sklepkolor5"))
		{
			final String[] lore = {"&c&l» &a&lZMIANA KOLORU NICKU", "", "&c&l» &7Mozesz dowolnie kupic jeden z 8 dostepnych", "   &7kolorow nicku na czacie. Pamietaj jednak", "   &7ze zakup koloru jest finalny i jednorazowy!", "", "&c&l» &7Pre-view nowego koloru:",
					" &8> " + ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.getGangInChat(DataManager.getInstance().getLocal(player).getGang()) + "&c" + ChatLib.returnMarryPrefix(player) +  "&r#ffffff" + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c Czesc!"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Kolor: #ffffffBIALY"), " &8> &7Oznaczenie HEX: #ffffff", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203400 ⛃"), " &8> &7Status posiadania: " + ChatLib.ColorUtil.formatHEX(ChatLib.isPlayerColor(player, "#ffffff")), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Kupno i zmiana)"), ""};

			return createInvItem(lore, Material.WHITE_DYE, false);
		}

		if (name.equalsIgnoreCase("sklepkolor6"))
		{
			final String[] lore = {"&c&l» &a&lZMIANA KOLORU NICKU", "", "&c&l» &7Mozesz dowolnie kupic jeden z 8 dostepnych", "   &7kolorow nicku na czacie. Pamietaj jednak", "   &7ze zakup koloru jest finalny i jednorazowy!", "", "&c&l» &7Pre-view nowego koloru:",
					" &8> " + ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.getGangInChat(DataManager.getInstance().getLocal(player).getGang()) + "&c" + ChatLib.returnMarryPrefix(player) +  "&r#242424" + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c Czesc!"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Kolor: #242424SZARY"), " &8> &7Oznaczenie HEX: #242424", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203400 ⛃"), " &8> &7Status posiadania: " + ChatLib.ColorUtil.formatHEX(ChatLib.isPlayerColor(player, "#242424")), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Kupno i zmiana)"), ""};

			return createInvItem(lore, Material.LIGHT_GRAY_DYE, false);
		}

		if (name.equalsIgnoreCase("sklepkolor7"))
		{
			final String[] lore = {"&c&l» &a&lZMIANA KOLORU NICKU", "", "&c&l» &7Mozesz dowolnie kupic jeden z 8 dostepnych", "   &7kolorow nicku na czacie. Pamietaj jednak", "   &7ze zakup koloru jest finalny i jednorazowy!", "", "&c&l» &7Pre-view nowego koloru:",
					" &8> " + ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.getGangInChat(DataManager.getInstance().getLocal(player).getGang()) + "&c" + ChatLib.returnMarryPrefix(player) +  "&r#ffb338" + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c Czesc!"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Kolor: #ffb338POMARANCZOWY"), " &8> &7Oznaczenie HEX: #ffb338", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203400 ⛃"), " &8> &7Status posiadania: " + ChatLib.ColorUtil.formatHEX(ChatLib.isPlayerColor(player, "#ffb338")), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Kupno i zmiana)"), ""};

			return createInvItem(lore, Material.ORANGE_DYE, false);
		}

		if (name.equalsIgnoreCase("sklepkolor8"))
		{
			final String[] lore = {"&c&l» &a&lZMIANA KOLORU NICKU", "", "&c&l» &7Mozesz dowolnie kupic jeden z 8 dostepnych", "   &7kolorow nicku na czacie. Pamietaj jednak", "   &7ze zakup koloru jest finalny i jednorazowy!", "", "&c&l» &7Pre-view nowego koloru:",
					" &8> " + ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.getGangInChat(DataManager.getInstance().getLocal(player).getGang()) + "&c" + ChatLib.returnMarryPrefix(player) +  "&r#ff9ee7" + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c Czesc!"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Kolor: #ff9ee7ROZOWY"), " &8> &7Oznaczenie HEX: #ff9ee7", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203400 ⛃"), " &8> &7Status posiadania: " + ChatLib.ColorUtil.formatHEX(ChatLib.isPlayerColor(player, "#ff9ee7")), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Kupno i zmiana)"), ""};

			return createInvItem(lore, Material.PINK_DYE, false);
		}

		if (name.equalsIgnoreCase("sklepnogang"))
		{
			final String[] lore = {"&c&l» &a&lSKLEP GANGU", "", "&c&l» &7Ta zakladka sklepu jest niedostepna, poniewaz", "   &7nie nalezysz do zadnego z gangow!", "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(Gang mozesz stworzyc za 1000⛃ za pomoca komendy"), ChatLib.ColorUtil.formatHEX("   #666666&o/gang stworz. Mozesz takze dolaczyc do istniejacego"),
					ChatLib.ColorUtil.formatHEX("   #666666&ojuz gangu, lecz musisz najpierw otrzymac zaproszenie)"), ""};

			return createInvItem(lore, Material.BARRIER, false);
		}

		if (name.equalsIgnoreCase("sklepgang1"))
		{
			final String[] lore = {"&c&l» &a&lZMIANA KOLORU GANGU", "", "&c&l» &7Mozesz dowolnie kupic jeden z 8 dostepnych", "   &7kolorow gangu na czacie. Pamietaj jednak", "   &7ze zakup koloru jest finalny i jednorazowy!", "", "&c&l» &7Pre-view nowego koloru gangu:",
					" &8> " + ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.returnCustomGangPrefix(player, "&c", DataManager.getInstance().getLocal(player).getPrefixes(DataManager.getInstance().getLocal(player).getGang()), ChatLib.returnGangStar(DataManager.getInstance().getLocal(player).getGang())) + "&c" + ChatLib.returnMarryPrefix(player) + ChatLib.returnPlayerColor(player) + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c Czesc!"), "",
					"&c&l» &7Kolor: &cCZERWONY", " &8> &7Oznaczenie Bukkit: & c", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203250 ⛃"), " &8> &7Status posiadania: " + ChatLib.ColorUtil.formatHEX(ChatLib.isGangColor(DataManager.getInstance().getLocal(player).getGang(), "&c")), "",
					 ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Kupno i zmiana)"), ""};

			return createInvItem(lore, Material.RED_DYE, false);
		}

		if (name.equalsIgnoreCase("sklepgang2"))
		{
			final String[] lore = {"&c&l» &a&lZMIANA KOLORU GANGU", "", "&c&l» &7Mozesz dowolnie kupic jeden z 8 dostepnych", "   &7kolorow gangu na czacie. Pamietaj jednak", "   &7ze zakup koloru jest finalny i jednorazowy!", "", "&c&l» &7Pre-view nowego koloru gangu:",
					" &8> " + ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.returnCustomGangPrefix(player, "&b", DataManager.getInstance().getLocal(player).getPrefixes(DataManager.getInstance().getLocal(player).getGang()), ChatLib.returnGangStar(DataManager.getInstance().getLocal(player).getGang())) + "&c" + ChatLib.returnMarryPrefix(player) + ChatLib.returnPlayerColor(player) + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c Czesc!"), "",
					"&c&l» &7Kolor: &bNIEBIESKI", " &8> &7Oznaczenie Bukkit: & b", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203250 ⛃"), " &8> &7Status posiadania: " + ChatLib.ColorUtil.formatHEX(ChatLib.isGangColor(DataManager.getInstance().getLocal(player).getGang(), "&b")), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Kupno i zmiana)"), ""};

			return createInvItem(lore, Material.LIGHT_BLUE_DYE, false);
		}

		if (name.equalsIgnoreCase("sklepgang3"))
		{
			final String[] lore = {"&c&l» &a&lZMIANA KOLORU GANGU", "", "&c&l» &7Mozesz dowolnie kupic jeden z 8 dostepnych", "   &7kolorow gangu na czacie. Pamietaj jednak", "   &7ze zakup koloru jest finalny i jednorazowy!", "", "&c&l» &7Pre-view nowego koloru gangu:",
					" &8> " + ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.returnCustomGangPrefix(player, "&a", DataManager.getInstance().getLocal(player).getPrefixes(DataManager.getInstance().getLocal(player).getGang()), ChatLib.returnGangStar(DataManager.getInstance().getLocal(player).getGang())) + "&c" + ChatLib.returnMarryPrefix(player) + ChatLib.returnPlayerColor(player) + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c Czesc!"), "",
					"&c&l» &7Kolor: &aZIELONY", " &8> &7Oznaczenie Bukkit: & a", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203250 ⛃"), " &8> &7Status posiadania: " + ChatLib.ColorUtil.formatHEX(ChatLib.isGangColor(DataManager.getInstance().getLocal(player).getGang(), "&a")), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Kupno i zmiana)"), ""};

			return createInvItem(lore, Material.LIME_DYE, false);
		}

		if (name.equalsIgnoreCase("sklepgang4"))
		{
			final String[] lore = {"&c&l» &a&lZMIANA KOLORU GANGU", "", "&c&l» &7Mozesz dowolnie kupic jeden z 8 dostepnych", "   &7kolorow gangu na czacie. Pamietaj jednak", "   &7ze zakup koloru jest finalny i jednorazowy!", "", "&c&l» &7Pre-view nowego koloru gangu:",
					" &8> " + ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.returnCustomGangPrefix(player, "&e", DataManager.getInstance().getLocal(player).getPrefixes(DataManager.getInstance().getLocal(player).getGang()), ChatLib.returnGangStar(DataManager.getInstance().getLocal(player).getGang())) + "&c" + ChatLib.returnMarryPrefix(player) + ChatLib.returnPlayerColor(player) + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c Czesc!"), "",
					"&c&l» &7Kolor: &eZOLTY", " &8> &7Oznaczenie Bukkit: & e", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203250 ⛃"), " &8> &7Status posiadania: " + ChatLib.ColorUtil.formatHEX(ChatLib.isGangColor(DataManager.getInstance().getLocal(player).getGang(), "&e")), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Kupno i zmiana)"), ""};

			return createInvItem(lore, Material.YELLOW_DYE, false);
		}

		if (name.equalsIgnoreCase("sklepgang5"))
		{
			final String[] lore = {"&c&l» &a&lZMIANA KOLORU GANGU", "", "&c&l» &7Mozesz dowolnie kupic jeden z 8 dostepnych", "   &7kolorow gangu na czacie. Pamietaj jednak", "   &7ze zakup koloru jest finalny i jednorazowy!", "", "&c&l» &7Pre-view nowego koloru gangu:",
					" &8> " + ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.returnCustomGangPrefix(player, "&f", DataManager.getInstance().getLocal(player).getPrefixes(DataManager.getInstance().getLocal(player).getGang()), ChatLib.returnGangStar(DataManager.getInstance().getLocal(player).getGang())) + "&c" + ChatLib.returnMarryPrefix(player) + ChatLib.returnPlayerColor(player) + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c Czesc!"), "",
					"&c&l» &7Kolor: &fBIALY", " &8> &7Oznaczenie Bukkit: & f", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203250 ⛃"), " &8> &7Status posiadania: " + ChatLib.ColorUtil.formatHEX(ChatLib.isGangColor(DataManager.getInstance().getLocal(player).getGang(), "&f")), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Kupno i zmiana)"), ""};

			return createInvItem(lore, Material.WHITE_DYE, false);
		}

		if (name.equalsIgnoreCase("sklepgang6"))
		{
			final String[] lore = {"&c&l» &a&lZMIANA KOLORU GANGU", "", "&c&l» &7Mozesz dowolnie kupic jeden z 8 dostepnych", "   &7kolorow gangu na czacie. Pamietaj jednak", "   &7ze zakup koloru jest finalny i jednorazowy!", "", "&c&l» &7Pre-view nowego koloru gangu:",
					" &8> " + ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.returnCustomGangPrefix(player, "&7", DataManager.getInstance().getLocal(player).getPrefixes(DataManager.getInstance().getLocal(player).getGang()), ChatLib.returnGangStar(DataManager.getInstance().getLocal(player).getGang())) + "&c" + ChatLib.returnMarryPrefix(player) + ChatLib.returnPlayerColor(player) + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c Czesc!"), "",
					"&c&l» &7Kolor: &7SZARY", " &8> &7Oznaczenie Bukkit: & 7", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203250 ⛃"), " &8> &7Status posiadania: " + ChatLib.ColorUtil.formatHEX(ChatLib.isGangColor(DataManager.getInstance().getLocal(player).getGang(), "&7")), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Kupno i zmiana)"), ""};

			return createInvItem(lore, Material.LIGHT_GRAY_DYE, false);
		}

		if (name.equalsIgnoreCase("sklepgang7"))
		{
			final String[] lore = {"&c&l» &a&lZMIANA KOLORU GANGU", "", "&c&l» &7Mozesz dowolnie kupic jeden z 8 dostepnych", "   &7kolorow gangu na czacie. Pamietaj jednak", "   &7ze zakup koloru jest finalny i jednorazowy!", "", "&c&l» &7Pre-view nowego koloru gangu:",
					" &8> " + ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.returnCustomGangPrefix(player, "&6", DataManager.getInstance().getLocal(player).getPrefixes(DataManager.getInstance().getLocal(player).getGang()), ChatLib.returnGangStar(DataManager.getInstance().getLocal(player).getGang())) + "&c" + ChatLib.returnMarryPrefix(player) + ChatLib.returnPlayerColor(player) + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c Czesc!"), "",
					"&c&l» &7Kolor: &6POMARANCZOWY", " &8> &7Oznaczenie Bukkit: & 6", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203250 ⛃"), " &8> &7Status posiadania: " + ChatLib.ColorUtil.formatHEX(ChatLib.isGangColor(DataManager.getInstance().getLocal(player).getGang(), "&6")), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Kupno i zmiana)"), ""};

			return createInvItem(lore, Material.ORANGE_DYE, false);
		}

		if (name.equalsIgnoreCase("sklepgang8"))
		{
			final String[] lore = {"&c&l» &a&lZMIANA KOLORU GANGU", "", "&c&l» &7Mozesz dowolnie kupic jeden z 8 dostepnych", "   &7kolorow gangu na czacie. Pamietaj jednak", "   &7ze zakup koloru jest finalny i jednorazowy!", "", "&c&l» &7Pre-view nowego koloru gangu:",
					" &8> " + ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.returnCustomGangPrefix(player, "&d", DataManager.getInstance().getLocal(player).getPrefixes(DataManager.getInstance().getLocal(player).getGang()), ChatLib.returnGangStar(DataManager.getInstance().getLocal(player).getGang())) + "&c" + ChatLib.returnMarryPrefix(player) + ChatLib.returnPlayerColor(player) + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c Czesc!"), "",
					"&c&l» &7Kolor: &dROZOWY", " &8> &7Oznaczenie Bukkit: & d", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203250 ⛃"), " &8> &7Status posiadania: " + ChatLib.ColorUtil.formatHEX(ChatLib.isGangColor(DataManager.getInstance().getLocal(player).getGang(), "&d")), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Kupno i zmiana)"), ""};

			return createInvItem(lore, Material.PINK_DYE, false);
		}

		if (name.equalsIgnoreCase("sklepgangp1"))
		{
			final String[] lore = {"&c&l» &a&lZMIANA PREFIXOW GANGU", "", "&c&l» &7Mozesz dowolnie kupic jeden z 3 dostepnych", "   &7prefixow gangu na czacie. Pamietaj jednak", "   &7ze zakup prefixow jest finalny i jednorazowy!", "", "&c&l» &7Pre-view nowego prefixu gangu:",
					" &8> " + ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.returnCustomGangPrefix(player, ChatLib.getValidGangColor(DataManager.getInstance().getLocal(player).getGang()), "normal", ChatLib.returnGangStar(DataManager.getInstance().getLocal(player).getGang())) + "&c" + ChatLib.returnMarryPrefix(player) + ChatLib.returnPlayerColor(player) + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c Czesc!"), "",
					"&c&l» &7Prefixy: Kwadratowe", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203350 ⛃"), " &8> &7Status posiadania: " + ChatLib.ColorUtil.formatHEX(ChatLib.isGangPrefix(DataManager.getInstance().getLocal(player).getGang(), "normal")), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Kupno i zmiana)"), ""};

			return createInvItem(lore, Material.WOODEN_AXE, false);
		}

		if (name.equalsIgnoreCase("sklepgangp2"))
		{
			final String[] lore = {"&c&l» &a&lZMIANA PREFIXOW GANGU", "", "&c&l» &7Mozesz dowolnie kupic jeden z 3 dostepnych", "   &7prefixow gangu na czacie. Pamietaj jednak", "   &7ze zakup prefixow jest finalny i jednorazowy!", "", "&c&l» &7Pre-view nowego prefixu gangu:",
					" &8> " + ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.returnCustomGangPrefix(player, ChatLib.getValidGangColor(DataManager.getInstance().getLocal(player).getGang()), "rounded", ChatLib.returnGangStar(DataManager.getInstance().getLocal(player).getGang())) + "&c" + ChatLib.returnMarryPrefix(player) + ChatLib.returnPlayerColor(player) + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c Czesc!"), "",
					"&c&l» &7Prefixy: Zaokraglone", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203350 ⛃"), " &8> &7Status posiadania: " + ChatLib.ColorUtil.formatHEX(ChatLib.isGangPrefix(DataManager.getInstance().getLocal(player).getGang(), "rounded")), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Kupno i zmiana)"), ""};

			return createInvItem(lore, Material.STONE_AXE, false);
		}

		if (name.equalsIgnoreCase("sklepgangp3"))
		{
			final String[] lore = {"&c&l» &a&lZMIANA PREFIXOW GANGU", "", "&c&l» &7Mozesz dowolnie kupic jeden z 3 dostepnych", "   &7prefixow gangu na czacie. Pamietaj jednak", "   &7ze zakup prefixow jest finalny i jednorazowy!", "", "&c&l» &7Pre-view nowego prefixu gangu:",
					" &8> " + ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.returnCustomGangPrefix(player, ChatLib.getValidGangColor(DataManager.getInstance().getLocal(player).getGang()), "arrows", ChatLib.returnGangStar(DataManager.getInstance().getLocal(player).getGang())) + "&c" + ChatLib.returnMarryPrefix(player) + ChatLib.returnPlayerColor(player) + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c Czesc!"), "",
					"&c&l» &7Prefixy: Strzalkowe", "", ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203350 ⛃"), " &8> &7Status posiadania: " + ChatLib.ColorUtil.formatHEX(ChatLib.isGangPrefix(DataManager.getInstance().getLocal(player).getGang(), "arrows")), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Kupno i zmiana)"), ""};

			return createInvItem(lore, Material.GOLDEN_AXE, false);
		}

		if (name.equalsIgnoreCase("sklepgangchat"))
		{
			final String[] lore = {"&c&l» &a&lPRYWATNY CZAT GANGU", "", "&c&l» &7Mozesz zakupic specjalny, ekskluzywny prywatny", "   &7czat dla czlonkow swojego gangu. Aby go wykorzystac", "   &7pzed swoja wiadomoscia wpisz znak &e!&7.", "", "&c&l» &7Pre-view prywatnego czatu gangu:",
					" &8> " + ChatLib.ColorUtil.formatHEX(ChatLib.returnGangPlayerChatMessage(player, "!Czesc!")), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203500 ⛃"), " &8> &7Status posiadania: " + ChatLib.ColorUtil.formatHEX(ChatLib.isGangChat(DataManager.getInstance().getLocal(player).getGang())), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Kupno i zmiana)"), ""};

			return createInvItem(lore, Material.FEATHER, false);
		}

		if (name.equalsIgnoreCase("sklepgangstar"))
		{
			final String[] lore = {"&c&l» &a&lKOSMETYK GWIAZDY", "", "&c&l» &7Mozesz zakupic specjalny, ekskluzywny dodatek", "   &7do Twojego gangu na czacie, ktory uswiadomi", "   &7reszcie ktory gang jest najbogatszy!", "", "&c&l» &7Pre-view nowego wygladu gangu:",
					" &8> " + ChatLib.ColorUtil.formatHEX("#80ff1f[" + TimeLib.hour() + ":" + TimeLib.minute() + "#80ff1f] " + ChatLib.returnCustomGangPrefix(player, ChatLib.getValidGangColor(DataManager.getInstance().getLocal(player).getGang()), DataManager.getInstance().getLocal(player).getPrefixes(DataManager.getInstance().getLocal(player).getGang()), "#ffc936★ ") + "&c" + ChatLib.returnMarryPrefix(player) + ChatLib.returnPlayerColor(player) + Objects.requireNonNull(player.getPlayer()).getName() + "#8c8c8c Czesc!"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203900 ⛃"), " &8> &7Status posiadania: " + ChatLib.ColorUtil.formatHEX(ChatLib.isGangStar(DataManager.getInstance().getLocal(player).getGang())), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Kupno i zmiana)"), ""};

			return createInvItem(lore, Material.NETHER_STAR, false);
		}

		if (name.equalsIgnoreCase("sklepinfo"))
		{
			final String[] lore = {"&c&l» &a&lWYBIERZ KATEGORIE", "", "&c&l» &7Wybierz interesujaca Cie zakladke, sposrod", "   &73 dostepnych po lewej stronie menu!", ""};

			return createInvItem(lore, Material.BARRIER, false);
		}

		if (name.equalsIgnoreCase("sklepkupno"))
		{
			final String[] lore = {"&c&l» &a&lKUPNO I SPRZEDAZ", "", "&c&l» &7Aby kupic przedmiot, najedz na niego", "   &7i kliknij LPM, jesli chcesz sprzedac jakis",
					"   &7przedmiot - kliknij PPM. Dodatkowo kiedy uzyjesz", "   &7kombinacji SHIFT + LPM lub SHIFT + PPM, wowczas", "   &7manipulujesz calym stakiem danego przedmiotu!",
					"", "&c&l» &7Wszystkie dokonane kupna i sprzedaze w sklepie", "   &7sa finalne i nie ma mozliwosci ich cofniecia.", "   &7Oznacza to, ze jesli sie pomylisz i kupisz",
					"   &7cos innego - to chuj ci w dupie, sklep", "   &7umywa od ciebie rece!", ""};

			return createInvItem(lore, Material.GOLD_INGOT, false);
		}

		if (name.equalsIgnoreCase("sklepitemy"))

		{
			final String[] lore = {"&c&l» &a&lPRZEDMIOTY, BLOKI I SUROWCE", "", "&c&l» &7Kategoria sklepu z przedmiotami zawierajaca", "   &7wiekszosc znanych Ci blokow, ktore mozesz",
					"&7   swobodnie zakupic lub sprzedac!", "", ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Wybranie zakladki)"), ""};

			return createInvItem(lore, Material.GRASS_BLOCK, false);
		}

		if (name.equalsIgnoreCase("sklepitemyselected"))
		{
			final String[] lore = {"&c&l» &a&lPRZEDMIOTY, BLOKI I SUROWCE", "", "&c&l» &7Kategoria sklepu z przedmiotami zawierajaca", "   &7wiekszosc znanych Ci blokow, ktore mozesz",
					"&7   swobodnie zakupic lub sprzedac!", "", ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Wybranie zakladki)"), ""};

			return createInvItem(lore, Material.GRASS_BLOCK, true);
		}

		if (name.equalsIgnoreCase("sklepksiazki"))
		{
			final String[] lore = {"&c&l» &a&lKSIAZKI Z ENCHANTAMI", "", "&c&l» &7Kategoria sklepu z enchantowanymi", "   &7ksiazkami, ktore mozesz zakupic juz teraz!", "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Wybranie zakladki)"), ""};

			return createInvItem(lore, Material.ENCHANTING_TABLE, false);
		}

		if (name.equalsIgnoreCase("sklepksiazkiselected"))
		{
			final String[] lore = {"&c&l» &a&lKSIAZKI Z ENCHANTAMI", "", "&c&l» &7Kategoria sklepu z enchantowanymi", "   &7ksiazkami, ktore mozesz zakupic juz teraz!", "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Wybranie zakladki)"), ""};

			return createInvItem(lore, Material.ENCHANTING_TABLE, true);
		}

		if (name.equalsIgnoreCase("sklepspecjalne"))
		{
			final String[] lore = {"&c&l» &a&lPRZEDMIOTY SPECJALNE", "", "&c&l» &7Kategoria sklepu z ekskluzywnymi itemami,", "   &7ktorych nie da sie wytworzyc w craftingu. Ich dzialanie",
					"   &7jest dokladnie opisane, a satysfakcja z jego posiadania", "   &7i mozliwosci - gwarantowana!", "", ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Wybranie zakladki)"), ""};

			return createInvItem(lore, Material.EMERALD, false);
		}

		if (name.equalsIgnoreCase("sklepspecjalneselected"))
		{
			final String[] lore = {"&c&l» &a&lPRZEDMIOTY SPECJALNE", "", "&c&l» &7Kategoria sklepu z ekskluzywnymi itemami,", "   &7ktorych nie da sie wytworzyc w craftingu. Ich dzialanie",
							"   &7jest dokladnie opisane, a satysfakcja z jego posiadania", "   &7i jego mozliwosci - gwarantowana!", "", ChatLib.ColorUtil.formatHEX("   #666666&o(LPM/PPM - Wybranie zakladki)"), ""};

			return createInvItem(lore, Material.EMERALD, true);
		}

		if (name.equalsIgnoreCase("sklepksiazki1"))
		{
			final String[] lore = {"&c&l» &a&lENCHANTOWANA KSIAZKA", "", "&c&l» &7Ksiazka zakleta na podany enchant, ktorej", "   &7mozesz uzyc by ulepszyc swoje przedmioty!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Rodzaj zaklecia: #80ff1fMending"), ChatLib.ColorUtil.formatHEX(" &8> &7Poziom zaklecia: #fc7474✖"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2034200 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepksiazki2"))
		{
			final String[] lore = {"&c&l» &a&lENCHANTOWANA KSIAZKA", "", "&c&l» &7Ksiazka zakleta na podany enchant, ktorej", "   &7mozesz uzyc by ulepszyc swoje przedmioty!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Rodzaj zaklecia: #80ff1fInfinity"), ChatLib.ColorUtil.formatHEX(" &8> &7Poziom zaklecia: #fc7474✖"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2032700 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepksiazki3"))
		{
			final String[] lore = {"&c&l» &a&lENCHANTOWANA KSIAZKA", "", "&c&l» &7Ksiazka zakleta na podany enchant, ktorej", "   &7mozesz uzyc by ulepszyc swoje przedmioty!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Rodzaj zaklecia:#80ff1f Silk Touch"), ChatLib.ColorUtil.formatHEX(" &8> &7Poziom zaklecia: #fc7474✖"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2033000 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepksiazki4"))
		{
			final String[] lore = {"&c&l» &a&lENCHANTOWANA KSIAZKA", "", "&c&l» &7Ksiazka zakleta na podany enchant, ktorej", "   &7mozesz uzyc by ulepszyc swoje przedmioty!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Rodzaj zaklecia:#80ff1f Protection"), ChatLib.ColorUtil.formatHEX(" &8> &7Poziom zaklecia: &b4"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2033900 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepksiazki5"))
		{
			final String[] lore = {"&c&l» &a&lENCHANTOWANA KSIAZKA", "", "&c&l» &7Ksiazka zakleta na podany enchant, ktorej", "   &7mozesz uzyc by ulepszyc swoje przedmioty!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Rodzaj zaklecia:#80ff1f Sharpness"), ChatLib.ColorUtil.formatHEX(" &8> &7Poziom zaklecia: &b5"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2033700 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepksiazki6"))
		{
			final String[] lore = {"&c&l» &a&lENCHANTOWANA KSIAZKA", "", "&c&l» &7Ksiazka zakleta na podany enchant, ktorej", "   &7mozesz uzyc by ulepszyc swoje przedmioty!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Rodzaj zaklecia:#80ff1f Fire Aspect"), ChatLib.ColorUtil.formatHEX(" &8> &7Poziom zaklecia: &b2"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2032200 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepksiazki7"))
		{
			final String[] lore = {"&c&l» &a&lENCHANTOWANA KSIAZKA", "", "&c&l» &7Ksiazka zakleta na podany enchant, ktorej", "   &7mozesz uzyc by ulepszyc swoje przedmioty!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Rodzaj zaklecia:#80ff1f Power"), ChatLib.ColorUtil.formatHEX(" &8> &7Poziom zaklecia: &b5"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2033900 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepksiazki8"))
		{
			final String[] lore = {"&c&l» &a&lENCHANTOWANA KSIAZKA", "", "&c&l» &7Ksiazka zakleta na podany enchant, ktorej", "   &7mozesz uzyc by ulepszyc swoje przedmioty!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Rodzaj zaklecia:#80ff1f Flame"), ChatLib.ColorUtil.formatHEX(" &8> &7Poziom zaklecia: #fc7474✖"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2031400 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepksiazki9"))
		{
			final String[] lore = {"&c&l» &a&lENCHANTOWANA KSIAZKA", "", "&c&l» &7Ksiazka zakleta na podany enchant, ktorej", "   &7mozesz uzyc by ulepszyc swoje przedmioty!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Rodzaj zaklecia:#80ff1f Punch"), ChatLib.ColorUtil.formatHEX(" &8> &7Poziom zaklecia: &b2"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2032900 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepksiazki10"))
		{
			final String[] lore = {"&c&l» &a&lENCHANTOWANA KSIAZKA", "", "&c&l» &7Ksiazka zakleta na podany enchant, ktorej", "   &7mozesz uzyc by ulepszyc swoje przedmioty!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Rodzaj zaklecia:#80ff1f Efficiency"), ChatLib.ColorUtil.formatHEX(" &8> &7Poziom zaklecia: &b5"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2034000 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepksiazki11"))
		{
			final String[] lore = {"&c&l» &a&lENCHANTOWANA KSIAZKA", "", "&c&l» &7Ksiazka zakleta na podany enchant, ktorej", "   &7mozesz uzyc by ulepszyc swoje przedmioty!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Rodzaj zaklecia:#80ff1f Unbreaking"), ChatLib.ColorUtil.formatHEX(" &8> &7Poziom zaklecia: &b3"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2032500 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepksiazki12"))
		{
			final String[] lore = {"&c&l» &a&lENCHANTOWANA KSIAZKA", "", "&c&l» &7Ksiazka zakleta na podany enchant, ktorej", "   &7mozesz uzyc by ulepszyc swoje przedmioty!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Rodzaj zaklecia:#80ff1f Looting"), ChatLib.ColorUtil.formatHEX(" &8> &7Poziom zaklecia: &b3"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2033400 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepksiazki13"))
		{
			final String[] lore = {"&c&l» &a&lENCHANTOWANA KSIAZKA", "", "&c&l» &7Ksiazka zakleta na podany enchant, ktorej", "   &7mozesz uzyc by ulepszyc swoje przedmioty!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Rodzaj zaklecia:#80ff1f Fortune"), ChatLib.ColorUtil.formatHEX(" &8> &7Poziom zaklecia: &b3"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2033600 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepksiazki14"))
		{
			final String[] lore = {"&c&l» &a&lENCHANTOWANA KSIAZKA", "", "&c&l» &7Ksiazka zakleta na podany enchant, ktorej", "   &7mozesz uzyc by ulepszyc swoje przedmioty!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Rodzaj zaklecia:#80ff1f Feather Falling"), ChatLib.ColorUtil.formatHEX(" &8> &7Poziom zaklecia: &b4"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2033300 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepksiazki15"))
		{
			final String[] lore = {"&c&l» &a&lENCHANTOWANA KSIAZKA", "", "&c&l» &7Ksiazka zakleta na podany enchant, ktorej", "   &7mozesz uzyc by ulepszyc swoje przedmioty!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Rodzaj zaklecia:#80ff1f Thorns"), ChatLib.ColorUtil.formatHEX(" &8> &7Poziom zaklecia: &b3"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2033000 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepksiazki16"))
		{
			final String[] lore = {"&c&l» &a&lENCHANTOWANA KSIAZKA", "", "&c&l» &7Ksiazka zakleta na podany enchant, ktorej", "   &7mozesz uzyc by ulepszyc swoje przedmioty!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Rodzaj zaklecia:#80ff1f Depth Strider"), ChatLib.ColorUtil.formatHEX(" &8> &7Poziom zaklecia: &b3"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2033600 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepksiazki17"))
		{
			final String[] lore = {"&c&l» &a&lENCHANTOWANA KSIAZKA", "", "&c&l» &7Ksiazka zakleta na podany enchant, ktorej", "   &7mozesz uzyc by ulepszyc swoje przedmioty!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Rodzaj zaklecia:#80ff1f Luck Of The Sea"), ChatLib.ColorUtil.formatHEX(" &8> &7Poziom zaklecia: &b3"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2032700 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepksiazki18"))
		{
			final String[] lore = {"&c&l» &a&lENCHANTOWANA KSIAZKA", "", "&c&l» &7Ksiazka zakleta na podany enchant, ktorej", "   &7mozesz uzyc by ulepszyc swoje przedmioty!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Rodzaj zaklecia:#80ff1f Lure"), ChatLib.ColorUtil.formatHEX(" &8> &7Poziom zaklecia: &b3"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2032600 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepksiazki19"))
		{
			final String[] lore = {"&c&l» &a&lENCHANTOWANA KSIAZKA", "", "&c&l» &7Ksiazka zakleta na podany enchant, ktorej", "   &7mozesz uzyc by ulepszyc swoje przedmioty!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Rodzaj zaklecia:#80ff1f Quick Charge"), ChatLib.ColorUtil.formatHEX(" &8> &7Poziom zaklecia: &b3"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2032400 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepksiazki20"))
		{
			final String[] lore = {"&c&l» &a&lENCHANTOWANA KSIAZKA", "", "&c&l» &7Ksiazka zakleta na podany enchant, ktorej", "   &7mozesz uzyc by ulepszyc swoje przedmioty!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Rodzaj zaklecia:#80ff1f Channelling"), ChatLib.ColorUtil.formatHEX(" &8> &7Poziom zaklecia: #fc7474✖"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2032900 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepksiazki21"))
		{
			final String[] lore = {"&c&l» &a&lENCHANTOWANA KSIAZKA", "", "&c&l» &7Ksiazka zakleta na podany enchant, ktorej", "   &7mozesz uzyc by ulepszyc swoje przedmioty!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Rodzaj zaklecia:#80ff1f Multishot"), ChatLib.ColorUtil.formatHEX(" &8> &7Poziom zaklecia: #fc7474✖"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2033500 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepksiazki22"))
		{
			final String[] lore = {"&c&l» &a&lENCHANTOWANA KSIAZKA", "", "&c&l» &7Ksiazka zakleta na podany enchant, ktorej", "   &7mozesz uzyc by ulepszyc swoje przedmioty!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Rodzaj zaklecia:#80ff1f Impaling"), ChatLib.ColorUtil.formatHEX(" &8> &7Poziom zaklecia: &b5"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2033700 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepksiazki23"))
		{
			final String[] lore = {"&c&l» &a&lENCHANTOWANA KSIAZKA", "", "&c&l» &7Ksiazka zakleta na podany enchant, ktorej", "   &7mozesz uzyc by ulepszyc swoje przedmioty!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Rodzaj zaklecia:#80ff1f Piercing"), ChatLib.ColorUtil.formatHEX(" &8> &7Poziom zaklecia: &b4"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2033400 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepksiazki24"))
		{
			final String[] lore = {"&c&l» &a&lENCHANTOWANA KSIAZKA", "", "&c&l» &7Ksiazka zakleta na podany enchant, ktorej", "   &7mozesz uzyc by ulepszyc swoje przedmioty!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Rodzaj zaklecia:#80ff1f Riptide"), ChatLib.ColorUtil.formatHEX(" &8> &7Poziom zaklecia: &b3"), "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2031900 ⛃"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepspecjalne1"))
		{
			final String[] lore = {"&c&l» &a&lNIEZNISZCZALNE ZAKLECIE", "", "&c&l» &7Ksiazka z zakleciem wiecznej niezniszczalnosci", "   &7ktore sprawi, ze Twoj przedmiot nigdy sie nie zepsuje!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2037500 ⛃"), "", ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.ENCHANTED_BOOK, false);
		}

		if (name.equalsIgnoreCase("sklepspecjalne2"))
		{
			final String[] lore = {"&c&l» &a&lKOSA STACHA JONESA", "", "&c&l» &7Legendarna, magiczna kosa Stacha Jonesa,", "   &7z olbrzymia moca i bitewnymi mozliwosciami!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fc747414 ukonczonych questow"), "", ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Odbior przedmiotu)"), ""};

			return createInvItem(lore, Material.STONE_HOE, true);
		}

		if (name.equalsIgnoreCase("sklepspecjalne3"))
		{
			final String[] lore = {"&c&l» &a&lGRANAT DYMNY", "", "&c&l» &7Specjalny orez bojowy, jesli chcesz szybko", "   &7zniknac z pola widzenia przeciwnikow!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff203300 ⛃"), "", ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.SNOWBALL, true);
		}

		if (name.equalsIgnoreCase("sklepspecjalne4"))
		{
			final String[] lore = {"&c&l» &a&lSTATYW CHEMICZNY", "", "&c&l» &7Specjalny statyw do tworzenia nielegalnych", "   &7substancji!", "",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: #fff2031000 ⛃"), "", ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno przedmiotu)"), ""};

			return createInvItem(lore, Material.BREWING_STAND, true);
		}

		if (name.equalsIgnoreCase("sklepitemy1"))
			return createShopItem("cobblestone", Material.COBBLESTONE, 16, 4, 2);

		if (name.equalsIgnoreCase("sklepitemy2"))
			return createShopItem("netherrack", Material.NETHERRACK, 16, 4, 2);

		if (name.equalsIgnoreCase("sklepitemy3"))
			return createShopItem("kamien", Material.STONE, 16, 4, 3);

		if (name.equalsIgnoreCase("sklepitemy4"))
			return createShopItem("ziemia", Material.DIRT, 16, 8, 3);

		if (name.equalsIgnoreCase("sklepitemy5"))
			return createShopItem("blok trawy", Material.GRASS_BLOCK, 16, 16, 6);

		if (name.equalsIgnoreCase("sklepitemy6"))
			return createShopItem("blok netherowej trawy", Material.CRIMSON_NYLIUM, 16, 16, 8);

		if (name.equalsIgnoreCase("sklepitemy7"))
			return createShopItem("blok netherowej trawy", Material.WARPED_NYLIUM, 16, 16, 8);

		if (name.equalsIgnoreCase("sklepitemy8"))
			return createShopItem("granit", Material.GRANITE, 8, 8, 4);

		if (name.equalsIgnoreCase("sklepitemy9"))
			return createShopItem("dioryt", Material.DIORITE, 8, 8, 4);

		if (name.equalsIgnoreCase("sklepitemy10"))
			return createShopItem("andezyt", Material.ANDESITE, 8, 8, 4);

		if (name.equalsIgnoreCase("sklepitemy11"))
			return createShopItem("zwir", Material.GRAVEL, 8, 8, 4);

		if (name.equalsIgnoreCase("sklepitemy12"))
			return createShopItem("piasek", Material.SAND, 8, 4, 2);

		if (name.equalsIgnoreCase("sklepitemy13"))
			return createShopItem("sandstone", Material.SANDSTONE, 8, 4, 3);

		if (name.equalsIgnoreCase("sklepitemy14"))
			return createShopItem("czerwony piasek", Material.RED_SAND, 8, 8, 3);

		if (name.equalsIgnoreCase("sklepitemy15"))
			return createShopItem("czerwony sandstone", Material.RED_SANDSTONE, 8, 8, 4);

		if (name.equalsIgnoreCase("sklepitemy16"))
			return createShopItem("drewno debowe", Material.OAK_LOG, 8, 8, 4);

		if (name.equalsIgnoreCase("sklepitemy17"))
			return createShopItem("drewno swierkowe", Material.SPRUCE_LOG, 8, 8, 4);

		if (name.equalsIgnoreCase("sklepitemy18"))
			return createShopItem("drewno brzozowe", Material.BIRCH_LOG, 8, 8, 4);

		if (name.equalsIgnoreCase("sklepitemy19"))
			return createShopItem("drewno dzunglowe", Material.JUNGLE_LOG, 8, 8, 4);

		if (name.equalsIgnoreCase("sklepitemy20"))
			return createShopItem("drewno akacjowe", Material.ACACIA_LOG, 8, 8, 4);

		if (name.equalsIgnoreCase("sklepitemy21"))
			return createShopItem("drewno ciemnego debu", Material.DARK_OAK_LOG, 8, 8, 4);

		if (name.equalsIgnoreCase("sklepitemy22"))
			return createShopItem("drewno netherowe", Material.CRIMSON_STEM, 8, 12, 6);

		if (name.equalsIgnoreCase("sklepitemy23"))
			return createShopItem("drewno netherowe", Material.WARPED_STEM, 8, 12, 6);

		if (name.equalsIgnoreCase("sklepitemy24"))
			return createShopItem("liscie debowe", Material.OAK_LEAVES, 16, 8, 3);

		if (name.equalsIgnoreCase("sklepitemy25"))
			return createShopItem("liscie swierkowe", Material.SPRUCE_LEAVES, 16, 8, 3);

		if (name.equalsIgnoreCase("sklepitemy26"))
			return createShopItem("liscie brzozowe", Material.BIRCH_LEAVES, 16, 8, 3);

		if (name.equalsIgnoreCase("sklepitemy27"))
			return createShopItem("liscie dzunglowe", Material.JUNGLE_LEAVES, 16, 8, 3);

		if (name.equalsIgnoreCase("sklepitemy28"))
			return createShopItem("liscie akacjowe", Material.ACACIA_LEAVES, 16, 8, 3);

		if (name.equalsIgnoreCase("sklepitemy29"))
			return createShopItem("liscie ciemnego debu", Material.DARK_OAK_LEAVES, 16, 8, 3);

		if (name.equalsIgnoreCase("sklepitemy30"))
			return createShopItem("blok sniegu", Material.SNOW_BLOCK, 8, 32, 16);

		if (name.equalsIgnoreCase("sklepitemy31"))
			return createShopItem("lod", Material.ICE, 8, 12, 8);

		if (name.equalsIgnoreCase("sklepitemy32"))
			return createShopItem("zbity lod", Material.PACKED_ICE, 8, 24, 12);

		if (name.equalsIgnoreCase("sklepitemy33"))
			return createShopItem("morska latarnia", Material.SEA_LANTERN, 1, 8, -1);

		if (name.equalsIgnoreCase("sklepitemy34"))
			return createShopItem("pryzmaryn", Material.PRISMARINE, 8, 16, -1);

		if (name.equalsIgnoreCase("sklepitemy35"))
			return createShopItem("ciemny pryzmaryn", Material.DARK_PRISMARINE, 8, 24, 12);

		if (name.equalsIgnoreCase("sklepitemy36"))
			return createShopItem("cegly netherowe", Material.NETHER_BRICKS, 8, 8, 4);

		if (name.equalsIgnoreCase("sklepitemy37"))
			return createShopItem("jasnoglaz", Material.GLOWSTONE, 8, 24, 12);

		if (name.equalsIgnoreCase("sklepitemy38"))
			return createShopItem("piasek dusz", Material.SOUL_SAND, 8, 24, 6);

		if (name.equalsIgnoreCase("sklepitemy39"))
			return createShopItem("blackstone", Material.BLACKSTONE, 8, 8, 3);

		if (name.equalsIgnoreCase("sklepitemy40"))
			return createShopItem("obsydian", Material.OBSIDIAN, 8, 32, 16);

		if (name.equalsIgnoreCase("sklepitemy41"))
			return createShopItem("kamien kresu", Material.END_STONE, 16, 12, 8);

		if (name.equalsIgnoreCase("sklepitemy42"))
			return createShopItem("cegly kresu", Material.END_STONE_BRICKS, 8, 36, 23);

		if (name.equalsIgnoreCase("sklepitemy43"))
			return createShopItem("blok purpuru", Material.PURPUR_BLOCK, 8, 24, -1);

		if (name.equalsIgnoreCase("sklepitemy44"))
			return createShopItem("owoc chorusu", Material.CHORUS_FRUIT, 8, 32, 8);

		if (name.equalsIgnoreCase("sklepitemy45"))
			return createShopItem("kwiat chorusu", Material.CHORUS_FLOWER, 8, -1, 12);

		if (name.equalsIgnoreCase("sklepitemy46"))
			return createShopItem("netherite", Material.NETHERITE_INGOT, 1, 1000, 400);

		if (name.equalsIgnoreCase("sklepitemy47"))
			return createShopItem("szmaragd", Material.EMERALD, 1, 40, 20);

		if (name.equalsIgnoreCase("sklepitemy48"))
			return createShopItem("diament", Material.DIAMOND, 1, 160, 60);

		if (name.equalsIgnoreCase("sklepitemy49"))
			return createShopItem("zloto", Material.GOLD_INGOT, 1, 32, 6);

		if (name.equalsIgnoreCase("sklepitemy50"))
			return createShopItem("zelazo", Material.IRON_INGOT, 1, 16, 4);

		if (name.equalsIgnoreCase("sklepitemy51"))
			return createShopItem("kwarc", Material.QUARTZ, 8, 24, 16);

		if (name.equalsIgnoreCase("sklepitemy52"))
			return createShopItem("miedz", Material.COPPER_INGOT, 8, 24, 8);

		if (name.equalsIgnoreCase("sklepitemy53"))
			return createShopItem("redstone", Material.REDSTONE, 8, 12, 8);

		if (name.equalsIgnoreCase("sklepitemy54"))
			return createShopItem("lapis dla zuli", Material.LAPIS_LAZULI, 8, 24, 6);

		if (name.equalsIgnoreCase("sklepitemy55"))
			return createShopItem("wegiel", Material.COAL, 8, 12, 4);

		if (name.equalsIgnoreCase("sklepitemy56"))
			return createShopItem("pszenica", Material.WHEAT, 8, 12, 4);

		if (name.equalsIgnoreCase("sklepitemy57"))
			return createShopItem("marchewki", Material.CARROT, 8, 16, 6);

		if (name.equalsIgnoreCase("sklepitemy58"))
			return createShopItem("ziemniaki", Material.POTATO, 8, 12, 4);

		if (name.equalsIgnoreCase("sklepitemy59"))
			return createShopItem("melony", Material.MELON_SLICE, 8, 32, 6);

		if (name.equalsIgnoreCase("sklepitemy60"))
			return createShopItem("buraki", Material.BEETROOT, 8, 24, 8);

		if (name.equalsIgnoreCase("sklepitemy61"))
			return createShopItem("bambus", Material.BAMBOO, 16, 48, 4);

		if (name.equalsIgnoreCase("sklepitemy62"))
			return createShopItem("trzcina", Material.SUGAR_CANE, 8, 16, 8);

		if (name.equalsIgnoreCase("sklepitemy63"))
			return createShopItem("kaktus", Material.CACTUS, 8, 8, 6);

		if (name.equalsIgnoreCase("sklepitemy64"))
			return createShopItem("dynia", Material.PUMPKIN, 8, 12, 8);

		if (name.equalsIgnoreCase("sklepitemy65"))
			return createShopItem("jajka", Material.EGG, 8, 12, 4);

		if (name.equalsIgnoreCase("sklepitemy66"))
		    return createShopItem("jablko", Material.APPLE, 1, 8, 2);

		if (name.equalsIgnoreCase("sklepitemy67"))
		    return createShopItem("zlote jablko", Material.GOLDEN_APPLE, 1, 200, 64);

		if (name.equalsIgnoreCase("sklepitemy68"))
            return createShopItem("surowe mieso", Material.PORKCHOP, 16, 8, 4);

		if (name.equalsIgnoreCase("sklepitemy69"))
            return createShopItem("surowe mieso", Material.MUTTON, 16, 8, 4);

		if (name.equalsIgnoreCase("sklepitemy70"))
            return createShopItem("surowe mieso", Material.BEEF, 16, 8, 4);

		if (name.equalsIgnoreCase("sklepitemy71"))
            return createShopItem("surowe mieso", Material.CHICKEN, 16, 8, 4);

		if (name.equalsIgnoreCase("sklepitemy72"))
            return createShopItem("surowe mieso", Material.MUTTON, 16, 8, 4);

		if (name.equalsIgnoreCase("sklepitemy73"))
            return createShopItem("skora", Material.LEATHER, 8, 24, 12);

		if (name.equalsIgnoreCase("sklepitemy74"))
            return createShopItem("plastry miodu", Material.HONEYCOMB, 4, 16, 12);

		if (name.equalsIgnoreCase("sklepitemy75"))
            return createShopItem("brodawki", Material.NETHER_WART, 8, 48, 16);

		if (name.equalsIgnoreCase("sklepitemy76"))
		    return createShopItem("enderperla", Material.SLIME_BALL, 8, 64, -1);

		if (name.equalsIgnoreCase("sklepitemy77"))
            return createShopItem("enderperla", Material.ENDER_PEARL, 1, 32, 4);

		if (name.equalsIgnoreCase("sklepitemy78"))
            return createShopItem("blaze rod", Material.BLAZE_ROD, 1, 48, 8);

		if (name.equalsIgnoreCase("sklepitemy79"))
            return createShopItem("lza ghasta", Material.GHAST_TEAR, 1, 128, 32);

		if (name.equalsIgnoreCase("sklepitemy80"))
			return createShopItem("oko pajaka", Material.SPIDER_EYE, 1, 24, 8);

		if (name.equalsIgnoreCase("sklepitemy81"))
			return createShopItem("gunpowder", Material.GUNPOWDER, 8, 32, 16);

		if (name.equalsIgnoreCase("sklepitemy82"))
			return createShopItem("kosci", Material.BONE, 8, 24, 12);

		if (name.equalsIgnoreCase("sklepitemy83"))
			return createShopItem("nici", Material.STRING, 8, 24, 18);

		if (name.equalsIgnoreCase("sklepitemy84"))
			return createShopItem("gwiazda netheru", Material.NETHER_STAR, 1, -1, 25000);

        if (name.equalsIgnoreCase("postacslub"))
        {
            String[] lore;

            if (DataManager.getInstance().getLocal(player).getMarry() == null)
                lore = new String[]{"&c&l» &a&lSLUB",
                        "",
                        "&c&l» &7Zakochanym wiedza o ich partnerze jest bardzo",
                        "   &7wazna, wszelkie informacje o Twoim zwiazku znajdziesz",
                        "   &7pod komenda /wyspy. Nie no zartuje, sa ponizej!",
                        "",
                        " &8> &7Jednak najpierw musze Cie czegos nauczyc..",
                        "   &7Nie posiadasz zadnego partnera, wiec",
                        "   &7sugeruje umyc swoj brzydki ryj, kupic",
                        "   &7cos ladnego i znalezc sobie kogos!",
                        ""};
            else
            {
                final int timexp = TimeLib.getDifferenceInMinutes(DataManager.getInstance().getLocal(player).getMarryDate()) / 6;
                final int xp = DataManager.getInstance().getLocal(player).getMarryLevel() + DataManager.getInstance().getLocal(null).getMarryLevel(DataManager.getInstance().getLocal(player).getMarry()) + timexp;
                final int level = xp / 100;
                final String s = Integer.toString(xp);
                String percentage;

                if (xp < 10)
                    percentage = "0";
                else
                {
                    percentage = Integer.toString(xp).charAt(s.length() - 2) + "0";
                    if (percentage.equalsIgnoreCase("00"))
                        percentage = "0";
                }

                lore = new String[]{"&c&l» &a&lSLUB",
                        "",
                        "&c&l» &7Zakochanym wiedza o ich partnerze jest bardzo",
                        "   &7wazna, wszelkie informacje o Twoim zwiazku znajdziesz",
                        "   &7pod komenda /wyspy. Nie no zartuje, sa ponizej!",
                        "",
                        ChatLib.ColorUtil.formatHEX(" &8> #ffc936Jestes w zwiazku z: &7" + ChatLib.returnPlayerColor(DataManager.getInstance().getLocal(player).getMarry()) + DataManager.getInstance().getLocal(player).getMarry()),
                        ChatLib.ColorUtil.formatHEX(" &8> #ffc936Data waszego slubu: &7" + ChatLib.returnPlayerColor(DataManager.getInstance().getLocal(player).getMarry()) + DataManager.getInstance().getLocal(player).getMarryDate()),
                        ChatLib.ColorUtil.formatHEX(" &8> #ffc936Poziom waszego zwiazku: &7" + ChatLib.returnPlayerColor(DataManager.getInstance().getLocal(player).getMarry()) + level + " &7(" + percentage + "%)"),
                        ""};
            }

            return createInvItem(lore, Material.POPPY, false);
        }

        if (name.equalsIgnoreCase("postacstatystyki"))
        {
			final String[] lore = {"&c&l» &a&lSTATYSTYKI", "", "&c&l» &7Dzien dobry nasz &mwartosciowy&r &7graczu &6☻", "   &7Ponizej znajdziesz wszystkie swoje statystyki",
                    "   &7oraz informacje jakie o Tobie posiadamy!", "", ChatLib.ColorUtil.formatHEX(" &8> #ffc936Nazwa: &7" + player.getName()),
                    ChatLib.ColorUtil.formatHEX(" &8> #ffc936UUID: &7" + DataManager.getInstance().getLocal(player).getUUID()),
                    ChatLib.ColorUtil.formatHEX(" &8> #ffc936Adres IP: &7" + DataManager.getInstance().getLocal(player).getAddress()),
                    ChatLib.ColorUtil.formatHEX(" &8> #ffc936Opoznienie z serwerem: &7" + ServerLib.getPing(player) + "ms"),
                    ChatLib.ColorUtil.formatHEX(" &8> #ffc936Konto premium #ffc936: &7" + ChatLib.isPlayerPremium(player)),
                    "",
                    ChatLib.ColorUtil.formatHEX(" &8> #ffc936Ranga: &7" + ChatLib.getColoredRank(player)),
                    ChatLib.ColorUtil.formatHEX(" &8> #ffc936Kolor: &7" + ChatLib.returnColoredPlayerColor(player.getName())),
                    ChatLib.ColorUtil.formatHEX(" &8> #ffc936Ostatnie wyciszenie: &7" + ChatLib.getLastMuteColored(player.getName())),
                    ChatLib.ColorUtil.formatHEX(" &8> #ffc936Wykonane questy: #fcff33" + QuestLib.getCompletedQuests(player) + "/#fcff3314"),
                    ChatLib.ColorUtil.formatHEX(" &8> #ffc936Poziom ulepszenia schowku: #80ff1f" + (DataManager.getInstance().getLocal(player).getSchowekLevel() + 1) + "&7/#80ff1f4"),
                    "",
                    ChatLib.ColorUtil.formatHEX(" &8> #ffc936Monety: #fcff33" + DataManager.getInstance().getLocal(player).getMoney() + " ⛃"),
                    ChatLib.ColorUtil.formatHEX(" &8> #ffc936Punkty umiejetnosci: &e" + DataManager.getInstance().getLocal(player).getSP() + " ☀"),
                    ChatLib.ColorUtil.formatHEX(" &8> #ffc936Zabojstwa: #80ff1f" + DataManager.getInstance().getLocal(player).getKills() + " ⚔"),
                    ChatLib.ColorUtil.formatHEX(" &8> #ffc936Smierci: #fc7474" + DataManager.getInstance().getLocal(player).getDeaths() + " ☠"),
                    ""};

            return createPlayerHeadInvItem(lore, player.getName(), true);
        }

		if (name.equalsIgnoreCase("postacloading"))
		{
			final String[] lore = {"&c&l» &a&lSTATYSTYKI", "", "&c&l» &7Wczytywanie statystyk...", ""};

			return createPlayerHeadInvItem(lore, player.getName(), true);
		}

        if (name.equalsIgnoreCase("postacgang"))
        {
            String[] lore;

            if (DataManager.getInstance().getLocal(player).getGang() == null)
                lore = new String[]{"&c&l» &a&lGANG",
                        "",
                        "&c&l» &7Nie wiesz czegos o swoim gangu lub chcesz",
                        "   &7cos sprawdzic? Wszystkie szczegolowe informacje",
                        "   &7na temat gangu znajduja sie... no zgadnij gdzie!",
                        "",
                        " &8> &7Jednak najpierw musze Cie czegos nauczyc..",
                        "   &7Dzisiejszym tematem zajec bedzie idiotyzm!",
                        "   &7Nic nie sugeruje ale moglbys chociaz byc",
                        "   &7w ktoryms z gangow na serwerze!",
                        ""};
            else
            {
				final boolean base = DataManager.getInstance().getLocal(player).getBase(DataManager.getInstance().getLocal(player).getGang()) != null;

                if (base)
                {
					final String world = Objects.requireNonNull(DataManager.getInstance().getLocal(player).getBase(DataManager.getInstance().getLocal(player).getGang()).getWorld()).getName();
					final int x = DataManager.getInstance().getLocal(player).getBase(DataManager.getInstance().getLocal(player).getGang()).getBlockX();
					final int y = DataManager.getInstance().getLocal(player).getBase(DataManager.getInstance().getLocal(player).getGang()).getBlockY();
					final int z = DataManager.getInstance().getLocal(player).getBase(DataManager.getInstance().getLocal(player).getGang()).getBlockZ();

                    lore = new String[]{"&c&l» &a&lGANG",
                            "",
                            "&c&l» &7Nie wiesz czegos o swoim gangu lub chcesz",
                            "   &7cos sprawdzic? Wszystkie szczegolowe informacje",
                            "   &7na temat gangu znajduja sie... no zgadnij gdzie!",
                            "",
                            ChatLib.ColorUtil.formatHEX(" &8> #ffc936Nazwa gangu: &7" + ChatLib.getValidGangColor(DataManager.getInstance().getLocal(player).getGang()) + DataManager.getInstance().getLocal(player).getGang()),
                            ChatLib.ColorUtil.formatHEX(" &8> #ffc936Design gangu: &7" + ChatLib.getGangInChat(DataManager.getInstance().getLocal(player).getGang())),
                            "",
                            ChatLib.ColorUtil.formatHEX(" &8> #ffc936Liczba czlonkow: &7" + ChatLib.getValidGangColor(DataManager.getInstance().getLocal(player).getGang()) + DataManager.getInstance().getLocal(player).getMembers(DataManager.getInstance().getLocal(player).getGang())),
                            ChatLib.ColorUtil.formatHEX(" &8> #ffc936Lider gangu: &7" + ChatLib.getValidGangColor(DataManager.getInstance().getLocal(player).getGang()) + DataManager.getInstance().getLocal(player).getLider(DataManager.getInstance().getLocal(player).getGang())),
                            ChatLib.ColorUtil.formatHEX(" &8> #ffc936Friendly Fire: &7" + ChatLib.getValidGangColor(DataManager.getInstance().getLocal(player).getGang()) + ChatLib.isGangFriendlyFire(DataManager.getInstance().getLocal(player).getGang())),
                            "",
                            ChatLib.ColorUtil.formatHEX(" &8> #ffc936Kolor gangu: &7" + ChatLib.getColoredValidGangColor(DataManager.getInstance().getLocal(player).getGang())),
                            ChatLib.ColorUtil.formatHEX(" &8> #ffc936Prefixy gangu: &7" + ChatLib.getValidGangColor(DataManager.getInstance().getLocal(player).getGang()) + ChatLib.getColoredGangPrefixes(DataManager.getInstance().getLocal(player).getGang())),
                            ChatLib.ColorUtil.formatHEX(" &8> #ffc936Ekskluzywna gwiazda: &7" + ChatLib.isGangStar(DataManager.getInstance().getLocal(player).getGang())),
                            ChatLib.ColorUtil.formatHEX(" &8> #ffc936Czat prywatny gangu: &7" + ChatLib.isGangChat(DataManager.getInstance().getLocal(player).getGang())),
                            "",
                            ChatLib.ColorUtil.formatHEX(" &8> #ffc936Lokalizacja bazy gangu:"),
                            ChatLib.ColorUtil.formatHEX("   &8- &7swiat: " + world),
                            ChatLib.ColorUtil.formatHEX("   &8- &7x: " + x),
                            ChatLib.ColorUtil.formatHEX("   &8- &7y: " + y),
                            ChatLib.ColorUtil.formatHEX("   &8- &7z: " + z),
                            ""};
                }
                else
                {
                    lore = new String[]{"&c&l» &a&lGANG",
                            "",
                            "&c&l» &7Nie wiesz czegos o swoim gangu lub chcesz",
                            "   &7cos sprawdzic? Wszystkie szczegolowe informacje",
                            "   &7na temat gangu znajduja sie... no zgadnij gdzie!",
                            "",
                            ChatLib.ColorUtil.formatHEX(" &8> #ffc936Nazwa gangu: &7" + ChatLib.getValidGangColor(DataManager.getInstance().getLocal(player).getGang()) + DataManager.getInstance().getLocal(player).getGang()),
                            ChatLib.ColorUtil.formatHEX(" &8> #ffc936Design gangu: &7" + ChatLib.getGangInChat(DataManager.getInstance().getLocal(player).getGang())),
                            "",
                            ChatLib.ColorUtil.formatHEX(" &8> #ffc936Liczba czlonkow: &7" + ChatLib.getValidGangColor(DataManager.getInstance().getLocal(player).getGang()) + DataManager.getInstance().getLocal(player).getMembers(DataManager.getInstance().getLocal(player).getGang())),
                            ChatLib.ColorUtil.formatHEX(" &8> #ffc936Lider gangu: &7" + ChatLib.getValidGangColor(DataManager.getInstance().getLocal(player).getGang()) + DataManager.getInstance().getLocal(player).getLider(DataManager.getInstance().getLocal(player).getGang())),
                            ChatLib.ColorUtil.formatHEX(" &8> #ffc936Friendly Fire: &7" + ChatLib.getValidGangColor(DataManager.getInstance().getLocal(player).getGang()) + ChatLib.isGangFriendlyFire(DataManager.getInstance().getLocal(player).getGang())),
                            "",
                            ChatLib.ColorUtil.formatHEX(" &8> #ffc936Kolor gangu: &7" + ChatLib.getColoredValidGangColor(DataManager.getInstance().getLocal(player).getGang())),
                            ChatLib.ColorUtil.formatHEX(" &8> #ffc936Prefixy gangu: &7" + ChatLib.getValidGangColor(DataManager.getInstance().getLocal(player).getGang()) + ChatLib.getColoredGangPrefixes(DataManager.getInstance().getLocal(player).getGang())),
                            ChatLib.ColorUtil.formatHEX(" &8> #ffc936Ekskluzywna gwiazda: &7" + ChatLib.isGangStar(DataManager.getInstance().getLocal(player).getGang())),
                            ChatLib.ColorUtil.formatHEX(" &8> #ffc936Czat prywatny gangu: &7" + ChatLib.isGangChat(DataManager.getInstance().getLocal(player).getGang())),
                            "",
                            ChatLib.ColorUtil.formatHEX(" &8> #ffc936Lokalizacja bazy gangu:"),
                            ChatLib.ColorUtil.formatHEX("   &8- &7/gang baza ustaw"),
                            ""};
                }
            }

            return createInvItem(lore, Material.DIAMOND_SWORD, false);
        }

        if (name.equalsIgnoreCase("postacupgrades"))
        {
			final String[] lore = {"&c&l» &a&lINFORMACJE O UMIEJETNOSCIACH",
					"",
					"&c&l» &7Na serwerze istnieje mozliwosc ulepszania",
					"   &7siebie jako swojej postaci i nie, nie chodzi nam",
					"   &7o poprawe Twojej tlustej talii czy ujemnego IQ!",
					"",
					"&c&l» &7Twoja postac moze zyskac 5 dodatkowych umiejetnosci,",
					"   &7ktore posiadaja 5 poziomow ulepszen. Opis kazdej",
					"   &7z umiejetnosci zawarty jest ponizej!",
					"",
					"&c&l» &7Aby zakupic ulepszenie, musisz posiadac",
					"   &7specjalna walute, ktora mozesz otrzymac m. in.",
					"   &7wykonujac &c/questy &7na serwerze. Mowa tu",
					"   &7oczywiscie o &ePunktach umiejetnosci (☀)&7.",
					"   &7Kazde pojedyncze ulepszenie kosztuje 1 ☀",
					"   &7i nie ma mozliwosci jego cofniecia!",
					""};

            return createInvItem(lore, Material.ENCHANTED_BOOK, false);
        }

        if (name.equalsIgnoreCase("postacupgrade1"))
        {
			final String[] lore = {"&c&l» &c&lWITALNOSC",
					"",
					"&c&l» &7Witalnosc pozwala Twojej postaci zwiekszyc",
					"   &7swoje maksymalne zdrowie!",
					"",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Wskaznik ulepszenia ilosc zdrowia:"),
					ChatLib.ColorUtil.formatHEX(" &8> &f(+2❤) " + DataManager.getInstance().getLocal(player).getUpgradeBar(player.getName(), "vitality", "&c") + " #fc7474+10❤"),
					"",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: &e1 ☀"),
					ChatLib.ColorUtil.formatHEX(" &8> &7Aktualny poziom: &c" + DataManager.getInstance().getLocal(player).getUpgradeLevel(player.getName(), "vitality") + "&7/&c5"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno ulepszenia)"), ""};

            return createInvItem(lore, Material.BOOK, false);
        }

        if (name.equalsIgnoreCase("postacupgrade2"))
        {
			final String[] lore = {"&c&l» &e&lSZCZESCIE",
					"",
					"&c&l» &7Szczescie Twojej postaci odpowiada za",
					"   &7zwiekszenie szansy na wylowienie legendarnych morskich",
					"   &7skarbow oraz zmniejsza pobor monet po smierci!",
					"",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Wskaznik zmniejszenia poboru monet po smierci:"),
					ChatLib.ColorUtil.formatHEX(" &8> &f(-2%⛃) " + DataManager.getInstance().getLocal(player).getUpgradeBar(player.getName(), "luck", "&e") + " #ffc9360%⛃"),
					"",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Wskaznik zwiekszenia szczescia w lowieniu:"),
					ChatLib.ColorUtil.formatHEX(" &8> &f(+1%✪) " + DataManager.getInstance().getLocal(player).getUpgradeBar(player.getName(), "luck", "&e") + " #ffc9366%✪"),
					"",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: &e1 ☀"),
					ChatLib.ColorUtil.formatHEX(" &8> &7Aktualny poziom: &e" + DataManager.getInstance().getLocal(player).getUpgradeLevel(player.getName(), "luck") + "&7/&e5"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno ulepszenia)"), ""};

            return createInvItem(lore, Material.BOOK, false);
        }

        if (name.equalsIgnoreCase("postacupgrade3"))
        {
			final String[] lore = {"&c&l» &a&lGRABIEZ",
					"",
					"&c&l» &7Grabiez pozwoli Ci na zwiekszenie dropu",
					"   &7z rud netheritu w netherze i jablek z drzew!",
					"",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Wskaznik zwiekszenia szansy na obfitszy drop:"),
					ChatLib.ColorUtil.formatHEX(" &8> &f(+2%☠) " + DataManager.getInstance().getLocal(player).getUpgradeBar(player.getName(), "loot", "&a") + " #80ff1f10%☠"),
					"",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: &e1 ☀"),
					ChatLib.ColorUtil.formatHEX(" &8> &7Aktualny poziom: &a" + DataManager.getInstance().getLocal(player).getUpgradeLevel(player.getName(), "loot") + "&7/&a5"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno ulepszenia)"), ""};

            return createInvItem(lore, Material.BOOK, false);
        }

        if (name.equalsIgnoreCase("postacupgrade4"))
        {
			final String[] lore = {"&c&l» &b&lWALECZNOSC",
					"",
					"&c&l» &7Walecznosc to typowa umiejetnosc bitewna.",
					"   &7Pozwala absorpcje obrazen od teleportacji perla!",
					"",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Wskaznik absorpcji obrazen od perel:"),
					ChatLib.ColorUtil.formatHEX(" &8> &f(+20%☄) " + DataManager.getInstance().getLocal(player).getUpgradeBar(player.getName(), "honorable", "&b") + " #3075ff100%☄"),
					"",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: &e1 ☀"),
					ChatLib.ColorUtil.formatHEX(" &8> &7Aktualny poziom: &b" + DataManager.getInstance().getLocal(player).getUpgradeLevel(player.getName(), "honorable") + "&7/&b5"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno ulepszenia)"), ""};

            return createInvItem(lore, Material.BOOK, false);
        }

        if (name.equalsIgnoreCase("postacupgrade5"))
        {
			final String[] lore = {"&c&l» &6&lPRZEBIEGLOSC",
					"",
					"&c&l» &7Przebieglosc pozwoli zwiekszyc Twoj",
					"   &7wspolczynnik otrzymywanego XP!",
					"",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Wskaznik zwiekszenia otrzymywanego XP:"),
					ChatLib.ColorUtil.formatHEX(" &8> &60% &f(+10%❀) " + DataManager.getInstance().getLocal(player).getUpgradeBar(player.getName(), "thiefy", "&6") + " #ffb33850%❀"),
					"",
					ChatLib.ColorUtil.formatHEX("&c&l» &7Cena: &e1 ☀"),
					ChatLib.ColorUtil.formatHEX(" &8> &7Aktualny poziom: &6" + DataManager.getInstance().getLocal(player).getUpgradeLevel(player.getName(), "thiefy") + "&7/&65"), "",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno ulepszenia)"), ""};

            return createInvItem(lore, Material.BOOK, false);
        }

        if (name.equalsIgnoreCase("questy1"))
        {
			final String[] lore = {"&c&l» &a&lQUEST",
                    "",
                    "&c&l» &7Wykonuj serwerowe zadania, aby zdobyc punkty",
                    "   &7umiejetnosci i ulepszac swoja postac!",
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nazwa: #ffc936Drwal"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Trudnosc: #80ff1fLatwy"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Szczegoly questa: &7Silny badz jak drwale,"),
                    ChatLib.ColorUtil.formatHEX("   &7sztywno stawiaj pale. Chwyc za swoj topor i"),
                    ChatLib.ColorUtil.formatHEX("   &7zetnij co nieco drewna!"),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Status ukonczenia questa:"),
                    ChatLib.ColorUtil.formatHEX(" &8> " + DataManager.getInstance().getLocal(player).getQuestBar(1)),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nagrody za ukonczenie:"),
                    ChatLib.ColorUtil.formatHEX(" &8> &e1 ☀"),
                    ChatLib.ColorUtil.formatHEX(" &8> #fff20350 ⛃"),
                    ""};

            return createInvItem(lore, Material.BOOK, QuestLib.getInstance().getReader(DataManager.getInstance().getLocal(player).getQuest(1)).getStatus());
        }

        if (name.equalsIgnoreCase("questy2"))
        {
			final String[] lore = {"&c&l» &a&lQUEST",
                    "",
                    "&c&l» &7Wykonuj serwerowe zadania, aby zdobyc punkty",
                    "   &7umiejetnosci i ulepszac swoja postac!",
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nazwa: #ffc936Zbieracz kamykow"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Trudnosc: #80ff1fLatwy"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Szczegoly questa: &7Jezeli jestes gornikiem,"),
                    ChatLib.ColorUtil.formatHEX("   &7i nie posiadasz RTX 3090 to lepiej chwyc za"),
                    ChatLib.ColorUtil.formatHEX("   &7swoj kilof i wykop mi troche kamienia!"),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Status ukonczenia questa:"),
                    ChatLib.ColorUtil.formatHEX(" &8> " + DataManager.getInstance().getLocal(player).getQuestBar(2)),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nagrody za ukonczenie:"),
                    ChatLib.ColorUtil.formatHEX(" &8> &e1 ☀"),
                    ChatLib.ColorUtil.formatHEX(" &8> #fff203100 ⛃"),
                    ""};

            return createInvItem(lore, Material.BOOK, QuestLib.getInstance().getReader(DataManager.getInstance().getLocal(player).getQuest(2)).getStatus());
        }

        if (name.equalsIgnoreCase("questy3"))
        {
			final String[] lore = {"&c&l» &a&lQUEST",
                    "",
                    "&c&l» &7Wykonuj serwerowe zadania, aby zdobyc punkty",
                    "   &7umiejetnosci i ulepszac swoja postac!",
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nazwa: #ffc936Szpadel"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Trudnosc: #80ff1fLatwy"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Szczegoly questa: &7Widzisz ten piasek,"),
                    ChatLib.ColorUtil.formatHEX("   &7ziemie, zwir? Najebalo go tutaj ze japie**"),
                    ChatLib.ColorUtil.formatHEX("   &7Skop troche i wroc po nagrode!"),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Status ukonczenia questa:"),
                    ChatLib.ColorUtil.formatHEX(" &8> " + DataManager.getInstance().getLocal(player).getQuestBar(3)),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nagrody za ukonczenie:"),
                    ChatLib.ColorUtil.formatHEX(" &8> &e1 ☀"),
                    ChatLib.ColorUtil.formatHEX(" &8> #fff20375 ⛃"),
                    ""};

            return createInvItem(lore, Material.BOOK, QuestLib.getInstance().getReader(DataManager.getInstance().getLocal(player).getQuest(3)).getStatus());
        }

        if (name.equalsIgnoreCase("questy4"))
        {
			final String[] lore = {"&c&l» &a&lQUEST",
                    "",
                    "&c&l» &7Wykonuj serwerowe zadania, aby zdobyc punkty",
                    "   &7umiejetnosci i ulepszac swoja postac!",
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nazwa: #ffc936Najeb sie!"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Trudnosc: #80ff1fLatwy"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Szczegoly questa: &7Z pewnoscia zaliczysz"),
                    ChatLib.ColorUtil.formatHEX("   &7to cudowne zadanie, chwyc kilka piwek i"),
                    ChatLib.ColorUtil.formatHEX("   &7rozluznij sie troszke!"),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Status ukonczenia questa:"),
					ChatLib.ColorUtil.formatHEX(" &8> " + DataManager.getInstance().getLocal(player).getQuestBar(4)),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nagrody za ukonczenie:"),
                    ChatLib.ColorUtil.formatHEX(" &8> &e1 ☀"),
                    ChatLib.ColorUtil.formatHEX(" &8> #fff20350 ⛃"),
                    ChatLib.ColorUtil.formatHEX(" &8> &fPiwo 5% &f(x1)"),
                    ""};

            return createInvItem(lore, Material.BOOK, QuestLib.getInstance().getReader(DataManager.getInstance().getLocal(player).getQuest(4)).getStatus());
        }

        if (name.equalsIgnoreCase("questy5"))
        {
			final String[] lore = {"&c&l» &a&lQUEST",
                    "",
                    "&c&l» &7Wykonuj serwerowe zadania, aby zdobyc punkty",
                    "   &7umiejetnosci i ulepszac swoja postac!",
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nazwa: #ffc936Lowca"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Trudnosc: #80ff1fLatwy"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Szczegoly questa: &7Potwory sa straszne"),
                    ChatLib.ColorUtil.formatHEX("   &7tylko w nocy, ich tez dotyczy to zadanie."),
                    ChatLib.ColorUtil.formatHEX("   &7Wystarczy ze przyniesiesz mi ich glowy!"),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Status ukonczenia questa:"),
					ChatLib.ColorUtil.formatHEX(" &8> " + DataManager.getInstance().getLocal(player).getQuestBar(5)),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nagrody za ukonczenie:"),
                    ChatLib.ColorUtil.formatHEX(" &8> &e1 ☀"),
                    ChatLib.ColorUtil.formatHEX(" &8> #fff203100 ⛃"),
                    ""};

            return createInvItem(lore, Material.BOOK, QuestLib.getInstance().getReader(DataManager.getInstance().getLocal(player).getQuest(5)).getStatus());
        }

        if (name.equalsIgnoreCase("questy6"))
        {
			final String[] lore = {"&c&l» &a&lQUEST",
                    "",
                    "&c&l» &7Wykonuj serwerowe zadania, aby zdobyc punkty",
                    "   &7umiejetnosci i ulepszac swoja postac!",
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nazwa: #ffc936Psychopata"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Trudnosc: #80ff1fLatwy"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Szczegoly questa: &7Swiat jest pelen zlych"),
                    ChatLib.ColorUtil.formatHEX("   &7i falszywych ludzi, mnie obchodza wszyscy,"),
                    ChatLib.ColorUtil.formatHEX("   &7wiec bede wdzieczny jesli zabijesz ich"),
                    ChatLib.ColorUtil.formatHEX("   &7minimum 10!"),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Status ukonczenia questa:"),
					ChatLib.ColorUtil.formatHEX(" &8> " + DataManager.getInstance().getLocal(player).getQuestBar(6)),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nagrody za ukonczenie:"),
                    ChatLib.ColorUtil.formatHEX(" &8> &e1 ☀"),
                    ChatLib.ColorUtil.formatHEX(" &8> #fff203150 ⛃"),
                    ""};

            return createInvItem(lore, Material.BOOK, QuestLib.getInstance().getReader(DataManager.getInstance().getLocal(player).getQuest(6)).getStatus());
        }

        if (name.equalsIgnoreCase("questy7"))
        {
			final String[] lore = {"&c&l» &a&lQUEST",
                    "",
                    "&c&l» &7Wykonuj serwerowe zadania, aby zdobyc punkty",
                    "   &7umiejetnosci i ulepszac swoja postac!",
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nazwa: #ffc936Poszukiwacz"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Trudnosc: #fff203Sredni"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Szczegoly questa: &7Kazdemu zlodziejowi"),
                    ChatLib.ColorUtil.formatHEX("   &7jak i wedrowcowi potrzebne sa pojemne i"),
                    ChatLib.ColorUtil.formatHEX("   &7glebokie kieszenie, ulepsz wiec swoj"),
                    ChatLib.ColorUtil.formatHEX("   &7schowek na maksymalny poziom!"),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Status ukonczenia questa:"),
					ChatLib.ColorUtil.formatHEX(" &8> " + DataManager.getInstance().getLocal(player).getQuestBar(7)),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nagrody za ukonczenie:"),
                    ChatLib.ColorUtil.formatHEX(" &8> &e2 ☀"),
                    ChatLib.ColorUtil.formatHEX(" &8> #fff203250 ⛃"),
                    ""};

            return createInvItem(lore, Material.BOOK, QuestLib.getInstance().getReader(DataManager.getInstance().getLocal(player).getQuest(7)).getStatus());
        }

        if (name.equalsIgnoreCase("questy8"))
        {
			final String[] lore = {"&c&l» &a&lQUEST",
                    "",
                    "&c&l» &7Wykonuj serwerowe zadania, aby zdobyc punkty",
                    "   &7umiejetnosci i ulepszac swoja postac!",
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nazwa: #ffc936Psychonauta"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Trudnosc: #fff203Sredni"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Szczegoly questa: &7Gdzies tam daleko"),
                    ChatLib.ColorUtil.formatHEX("   &7swiat jest inny, a moze nawet i przed nami"),
                    ChatLib.ColorUtil.formatHEX("   &7lecz nie umiemy otworzyc oczu.."),
                    ChatLib.ColorUtil.formatHEX("   &7Wyprobuj jeden z serwerowych specjalow!"),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Status ukonczenia questa:"),
					ChatLib.ColorUtil.formatHEX(" &8> " + DataManager.getInstance().getLocal(player).getQuestBar(8)),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nagrody za ukonczenie:"),
                    ChatLib.ColorUtil.formatHEX(" &8> &e2 ☀"),
                    ChatLib.ColorUtil.formatHEX(" &8> #fff203200 ⛃"),
                    ChatLib.ColorUtil.formatHEX(" &8> &fHeroina &f(x1)"),
                    ""};

            return createInvItem(lore, Material.BOOK, QuestLib.getInstance().getReader(DataManager.getInstance().getLocal(player).getQuest(8)).getStatus());
        }

        if (name.equalsIgnoreCase("questy9"))
        {
			final String[] lore = {"&c&l» &a&lQUEST",
                    "",
                    "&c&l» &7Wykonuj serwerowe zadania, aby zdobyc punkty",
                    "   &7umiejetnosci i ulepszac swoja postac!",
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nazwa: #ffc936MLG"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Trudnosc: #fff203Sredni"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Szczegoly questa: &7Twoim wyzwaniem jest"),
                    ChatLib.ColorUtil.formatHEX("   &7wykonac minimum 50 water skilli!"),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Status ukonczenia questa:"),
					ChatLib.ColorUtil.formatHEX(" &8> " + DataManager.getInstance().getLocal(player).getQuestBar(9)),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nagrody za ukonczenie:"),
                    ChatLib.ColorUtil.formatHEX(" &8> &e2 ☀"),
                    ChatLib.ColorUtil.formatHEX(" &8> #fff203200 ⛃"),
                    ""};

            return createInvItem(lore, Material.BOOK, QuestLib.getInstance().getReader(DataManager.getInstance().getLocal(player).getQuest(9)).getStatus());
        }

        if (name.equalsIgnoreCase("questy10"))
        {
			final String[] lore = {"&c&l» &a&lQUEST",
                    "",
                    "&c&l» &7Wykonuj serwerowe zadania, aby zdobyc punkty",
                    "   &7umiejetnosci i ulepszac swoja postac!",
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nazwa: #ffc936Spoceniec"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Trudnosc: #fff203Sredni"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Szczegoly questa: &7Kto pierwszy ten.."),
                    ChatLib.ColorUtil.formatHEX("   &7najbardziej spocony! Przegraj na serwerze"),
                    ChatLib.ColorUtil.formatHEX("   &7czas wynoszacy minimum 7 dni!"),
                    ChatLib.ColorUtil.formatHEX("   &7Jesli ukonczyles questa, wejdz i wejdz"),
                    ChatLib.ColorUtil.formatHEX("   &7ponownie na serwer!"),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Status ukonczenia questa:"),
					ChatLib.ColorUtil.formatHEX(" &8> " + DataManager.getInstance().getLocal(player).getQuestBar(10)),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nagrody za ukonczenie:"),
                    ChatLib.ColorUtil.formatHEX(" &8> &e2 ☀"),
                    ChatLib.ColorUtil.formatHEX(" &8> #fff203300 ⛃"),
                    ""};

            return createInvItem(lore, Material.BOOK, QuestLib.getInstance().getReader(DataManager.getInstance().getLocal(player).getQuest(10)).getStatus());
        }

        if (name.equalsIgnoreCase("questy11"))
        {
			final String[] lore = {"&c&l» &a&lQUEST",
                    "",
                    "&c&l» &7Wykonuj serwerowe zadania, aby zdobyc punkty",
                    "   &7umiejetnosci i ulepszac swoja postac!",
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nazwa: #ffc936Ogrodnik"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Trudnosc: #fff203Sredni"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Szczegoly questa: &7Na swoim duzym"),
                    ChatLib.ColorUtil.formatHEX("   &7i pieknym polu, bez chaszczy i robali"),
                    ChatLib.ColorUtil.formatHEX("   &7poswiec czas zbierajac plony ze swojej"),
                    ChatLib.ColorUtil.formatHEX("   &7plantacji!"),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Status ukonczenia questa:"),
					ChatLib.ColorUtil.formatHEX(" &8> " + DataManager.getInstance().getLocal(player).getQuestBar(11)),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nagrody za ukonczenie:"),
                    ChatLib.ColorUtil.formatHEX(" &8> &e2 ☀"),
                    ChatLib.ColorUtil.formatHEX(" &8> #fff203250 ⛃"),
                    ""};

            return createInvItem(lore, Material.BOOK, QuestLib.getInstance().getReader(DataManager.getInstance().getLocal(player).getQuest(11)).getStatus());
        }

        if (name.equalsIgnoreCase("questy12"))
        {
			final String[] lore = {"&c&l» &a&lQUEST",
                    "",
                    "&c&l» &7Wykonuj serwerowe zadania, aby zdobyc punkty",
                    "   &7umiejetnosci i ulepszac swoja postac!",
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nazwa: #ffc936Zakochani po uszy"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Trudnosc: #fc7474Trudny"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Szczegoly questa: &7Im wiecej milosci"),
                    ChatLib.ColorUtil.formatHEX("   &7i szczescia w twym zwiazku, tym jeszcze"),
                    ChatLib.ColorUtil.formatHEX("   &7wiecej w kieszeni majatku. Osiagnij 100"),
                    ChatLib.ColorUtil.formatHEX("   &7poziom slubu! Jesli ukonczyles questa"),
                    ChatLib.ColorUtil.formatHEX("   &7na chwile przed otrzymaniem nagrody"),
                    ChatLib.ColorUtil.formatHEX("   &7pocaluj swoja druga polowke!"),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Status ukonczenia questa:"),
					ChatLib.ColorUtil.formatHEX(" &8> " + DataManager.getInstance().getLocal(player).getQuestBar(12)),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nagrody za ukonczenie:"),
                    ChatLib.ColorUtil.formatHEX(" &8> &e3 ☀"),
                    ChatLib.ColorUtil.formatHEX(" &8> #fff203450 ⛃"),
                    ""};

            return createInvItem(lore, Material.BOOK, QuestLib.getInstance().getReader(DataManager.getInstance().getLocal(player).getQuest(12)).getStatus());
        }

        if (name.equalsIgnoreCase("questy13"))
        {
			final String[] lore = {"&c&l» &a&lQUEST",
                    "",
                    "&c&l» &7Wykonuj serwerowe zadania, aby zdobyc punkty",
                    "   &7umiejetnosci i ulepszac swoja postac!",
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nazwa: #ffc936Partia serwera"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Trudnosc: #fc7474Trudny"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Szczegoly questa: &7Twoim zadaniem jest"),
                    ChatLib.ColorUtil.formatHEX("   &7zakupic ekskluzywny kosmetyk gwiazdy"),
                    ChatLib.ColorUtil.formatHEX("   &7dla swojego gangu. Jezeli jestes w gangu"),
                    ChatLib.ColorUtil.formatHEX("   &7i gwiazde kupi jego lider, wowczas"),
                    ChatLib.ColorUtil.formatHEX("   &7quest nie zostaje zaliczony poniewaz"),
                    ChatLib.ColorUtil.formatHEX("   &7nie tolerujemy darmozjadow!"),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Status ukonczenia questa:"),
					ChatLib.ColorUtil.formatHEX(" &8> " + DataManager.getInstance().getLocal(player).getQuestBar( 13)),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nagrody za ukonczenie:"),
                    ChatLib.ColorUtil.formatHEX(" &8> &e3 ☀"),
                    ChatLib.ColorUtil.formatHEX(" &8> #fff203500 ⛃"),
                    ""};

            return createInvItem(lore, Material.BOOK, QuestLib.getInstance().getReader(DataManager.getInstance().getLocal(player).getQuest(13)).getStatus());
        }

        if (name.equalsIgnoreCase("questy14"))
        {
			final String[] lore = {"&c&l» &a&lQUEST",
                    "",
                    "&c&l» &7Wykonuj serwerowe zadania, aby zdobyc punkty",
                    "   &7umiejetnosci i ulepszac swoja postac!",
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nazwa: #ffc936Piraci z karaibow"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Trudnosc: #fc7474Trudny"),
                    ChatLib.ColorUtil.formatHEX(" &8> &7Szczegoly questa: &7W morzu i jeziorach"),
                    ChatLib.ColorUtil.formatHEX("   &7znajduja sie fascynujace przedmioty,"),
                    ChatLib.ColorUtil.formatHEX("   &7ktore mozesz wylowic za pomoca swojego"),
                    ChatLib.ColorUtil.formatHEX("   &7kija ze sznurkiem i haczykiem. Twoim"),
                    ChatLib.ColorUtil.formatHEX("   &7zadaniem jest wylowienie 15 legendarnych"),
                    ChatLib.ColorUtil.formatHEX("   &7morskich skarbow!"),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Status ukonczenia questa:"),
					ChatLib.ColorUtil.formatHEX(" &8> " + DataManager.getInstance().getLocal(player).getQuestBar(14)),
                    "",
                    ChatLib.ColorUtil.formatHEX("&c&l» &7Nagrody za ukonczenie:"),
                    ChatLib.ColorUtil.formatHEX(" &8> &e3 ☀"),
                    ChatLib.ColorUtil.formatHEX(" &8> #fff203600 ⛃"),
                    ""};

            return createInvItem(lore, Material.BOOK, QuestLib.getInstance().getReader(DataManager.getInstance().getLocal(player).getQuest(14)).getStatus());
        }

		if (name.equalsIgnoreCase("drug_table_info"))
		{
			final String[] lore = {"&c&l» &a&lSYNTEZA",
					"",
					" &8> &7Aby rozpoczac swoja synteze, wybierz",
					"   &7jeden z pieciu szlakow, powyzej!",
					"",
					" &8> &7Domyslnie, synteezy rozpoczynaja sie",
					"   &7od podstawnika, ktorym jest &aAmina&7.",
					"   &7Aby ja zmienic, kliknij na nia.",
					"",
					" &8> &7Syntezy rozpoczac mozesz od aminy,",
					"   &7badz opium. Alkohole nabyc mozesz",
					"   &7&mwylacznie&r&7 w sklepie monopolowym.",
					""};

			return createInvItem(lore, Material.BREWING_STAND, false);
		}

		if (name.equalsIgnoreCase("drug_table_info2"))
		{
			final String[] lore = {"&c&l» &a&lINFORMACJE",
					"",
					" &8> &7Za kazda przeprowadzona synteze placisz",
					"   &7jednym poziomem doswiadczenia. Takze wypierdalaj",
					"   &7mi z tymi monetami i diamentami!",
					"",
					" &8> &7Nie ma mozliwosci desyntezacji -",
					"   &7wiec kazda reakcja chemiczna jest finalna,",
					"   &7podejmuj madre decyzje!",
					""};

			return createInvItem(lore, Material.BOOK, false);
		}

		if (name.equalsIgnoreCase("recipe_on"))
		{
			final String[] lore = {"&c&l» &a&lSTATUS RECEPTURY",
					"",
					" &8> &aReceptura jest obecnie wlaczona. Oznacza",
					"   &ato, ze wszyscy gracze moga jej uzywac.",
					"",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Zmiana statusu"),
					""};

			return createInvItem(lore, Material.LIME_DYE, false);
		}

		if (name.equalsIgnoreCase("recipe_off"))
		{
			final String[] lore = {"&c&l» &a&lSTATUS RECEPTURY",
					"",
					" &8> &cReceptura jest obecnie wylaczona. Oznacza",
					"   &cto, ze nie mozna jej uzywac.",
					"",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Zmiana statusu"),
					""};

			return createInvItem(lore, Material.RED_DYE, false);
		}

		if (name.equalsIgnoreCase("recipe_blocked"))
		{
			final String[] lore = {"&c&l» &7&lSTATUS RECEPTURY", "", "&c&l» &7Ta zakladka jest niedostepna dla Twojej rangi.", ""};

			return createInvItem(lore, Material.GUNPOWDER, false);
		}

		if (name.equalsIgnoreCase("tradinginfo"))
		{
			final String[] lore = {"&c&l» &a&lINFORMACJE", "",
					"&c&l» &7Witaj na czarnym rynku graczy, gdzie",
					"   &7mozesz sprzedawac i kupowac przedmioty.",
					"",
					"&c&l» &7Aby wystawic swoj przedmiot, trzymaj go" ,
					"   &7w lapce i wpisz komende &c/wystaw (cena)&7.",
					""};

			return createInvItem(lore, Material.BOOK, false);
		}

		if (name.equalsIgnoreCase("tradingoffers"))
		{
			final String[] lore = {"&c&l» &a&lPRZEGLADAJ OFERTY", "",
					"&c&l» &7Kliknij, aby przejsc do zakladki, w ktorej",
					"   &7znajduja sie obecnie wystawione przedmioty.",
					"",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Wybranie zakladki"),
					""};

			return createInvItem(lore, Material.ENDER_EYE, false);
		}

		if (name.equalsIgnoreCase("tradingplayer"))
		{
			final String[] lore = {"&c&l» &a&lTWOJE OFERTY", "",
					"&c&l» &7Kliknij, aby przejsc do zakladki, w ktorej",
					"   &7znajduja sie Twoje wystawione przedmioty.",
					"",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Wybranie zakladki"),
					""};

			return createPlayerHeadInvItem(lore, player.getName(), false);
		}

		if (name.equalsIgnoreCase("tradingofferss"))
		{
			final String[] lore = {"&c&l» &a&lPRZEGLADAJ OFERTY", "",
					"&c&l» &7Kliknij, aby przejsc do zakladki, w ktorej",
					"   &7znajduja sie obecnie wystawione przedmioty.",
					"",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Wybranie zakladki"),
					""};

			return createInvItem(lore, Material.ENDER_EYE, true);
		}

		if (name.equalsIgnoreCase("tradingplayers"))
		{
			final String[] lore = {"&c&l» &a&lTWOJE OFERTY", "",
					"&c&l» &7Kliknij, aby przejsc do zakladki, w ktorej",
					"   &7znajduja sie Twoje wystawione przedmioty.",
					"",
					ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Wybranie zakladki"),
					""};

			return createPlayerHeadInvItem(lore, player.getName(), true);
		}

		if (name.equalsIgnoreCase("tradingclear"))
		{
			final String[] lore = {"&c&l» &a&lBRAK OFERT", "",
					"&c&l» &7Na czarnym rynku nie ma obecnie zadnych",
					"   &7ofert, badz zatem pierwszy!",
					"",
			};

			return createInvItem(lore, Material.BARRIER, false);
		}

		if (name.equalsIgnoreCase("tradingnull"))
		{
			final String[] lore = {"&c&l» &a&lBRAK OFERT", "",
					"&c&l» &7Nie posiadasz zadnych wystawionych przedmiotow!",
					"",
			};

			return createInvItem(lore, Material.BARRIER, false);
		}

		if (name.equalsIgnoreCase("multisell"))
		{
			final String[] lore = {"&c&l» &a&lMULTI SPRZEDAZ", "",
					"&c&l» &7Chcesz sprzedac wszystkie swoje przedmioty",
					"   &7za jednym kliknieciem? Wyprobuj multi sprzedaz!",
					"",
			};

			return createInvItem(lore, Material.NETHER_STAR, false);
		}

		return null;
	}

	public static boolean isFullInventory(final Player player)
	{
		for (int slot = 0; slot < 36; slot++)
			if (player.getInventory().getItem(slot) == null)
				return false;

		return true;
	}

	public static boolean canBeStacked(final ItemStack item, final int amount)
	{
		if (item != null)
			return item.getAmount() + amount <= item.getMaxStackSize();

		return false;
	}

	public static boolean canStackItem(final Player player, final Material material, final int amount)
	{
		for (int slot = 0; slot < 36; slot++)
			if (Objects.requireNonNull(player.getInventory().getItem(slot)).getType() == material)
				if (canBeStacked(player.getInventory().getItem(slot), amount))
					return true;

		return false;
	}

	public static ItemStack getEnchantmentBook(final Enchantment enchantment, final int tier)
	{
		ItemStack enchBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
		EnchantmentStorageMeta meta = (EnchantmentStorageMeta) enchBook.getItemMeta();
		assert meta != null;
		meta.addStoredEnchant(enchantment, tier, true);
		enchBook.setItemMeta(meta);

		return enchBook;
	}

	public static ItemStack createInvItem(final String[] lore, final Material material, final boolean enchGlowing)
	{
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();

		ArrayList<String> itemLore = new ArrayList<>();

		for (String vers : lore)
			itemLore.add(ChatColor.translateAlternateColorCodes('&', vers));

		assert meta != null;

		if (enchGlowing)
			meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);

		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setDisplayName(" ");
		meta.setLore(itemLore);
		item.setItemMeta(meta);

		return item;
	}

    public static ItemStack createPlayerHeadInvItem(final String[] lore, final String name, final boolean enchGlowing)
	{
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta skull_meta = (SkullMeta) skull.getItemMeta();

		ArrayList<String> itemLore = new ArrayList<>();

		for (String vers : lore)
			itemLore.add(ChatColor.translateAlternateColorCodes('&', vers));

		assert skull_meta != null;

		if (enchGlowing)
			skull_meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);

		skull_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		skull_meta.setOwningPlayer(Bukkit.getOfflinePlayer(name));
		skull.setItemMeta(skull_meta);
		skull_meta.setDisplayName(" ");
		skull_meta.setLore(itemLore);
		skull.setItemMeta(skull_meta);

		return skull;
	}

	public static ItemStack createPotionInvItem(final String[] lore, final Color color)
	{
		ItemStack potion = new ItemStack(Material.SPLASH_POTION);
		PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();

		ArrayList<String> itemLore = new ArrayList<>();

		for (String vers : lore)
			itemLore.add(ChatColor.translateAlternateColorCodes('&', vers));

		assert potionMeta != null;
		potionMeta.setColor(color);
		potionMeta.setBasePotionData(new PotionData(PotionType.WATER));

		potionMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		potionMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

		potion.setItemMeta(potionMeta);
		potionMeta.setDisplayName(" ");
		potionMeta.setLore(itemLore);
		potion.setItemMeta(potionMeta);

		return potion;
	}

	public static ItemStack createAmountedInvItem(final String[] lore, final Material material, final int amount, final boolean enchGlowing)
	{
		ItemStack item = new ItemStack(material, amount);
		ItemMeta meta = item.getItemMeta();

		ArrayList<String> itemLore = new ArrayList<>();

		for (String vers : lore)
			itemLore.add(ChatColor.translateAlternateColorCodes('&', vers));

		assert meta != null;

		if (enchGlowing)
			meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);

		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setDisplayName(" ");
		meta.setLore(itemLore);
		item.setItemMeta(meta);

		return item;
	}

	public static ItemStack createShopItem(final String name, final Material material, final int amount, final int buy, final int sell)
	{
		String buyStr;
		String sellStr;

		if (buy == -1)
			buyStr = ChatLib.ColorUtil.formatHEX("&c&l» &7Cena kupna: #80ff1f✖ &7(Nie ma kurwa!)");
		else
			buyStr = ChatLib.ColorUtil.formatHEX("&c&l» &7Cena kupna: #80ff1f" + buy + " ⛃ &7(" + buy * (material.getMaxStackSize() / amount) + " ⛃/stak)");

		if (sell == -1)
			sellStr = ChatLib.ColorUtil.formatHEX("&c&l» &7Cena sprzedazy: #fc7474✖ &7(Nie ma kurwa!)");
		else
			sellStr = ChatLib.ColorUtil.formatHEX("&c&l» &7Cena sprzedazy: #fc7474" + sell + " ⛃ &7(" + sell * (material.getMaxStackSize() / amount) + " ⛃/stak)");


		final String[] lore = {"&c&l» &a&l" + name.toUpperCase(), "", "&c&l» &7Przedmiot: " + name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase(), " &8> &7Ilosc: 8", "",
				buyStr,
				sellStr, "",
				ChatLib.ColorUtil.formatHEX("   #666666&o(LPM - Kupno jednej sztuki przedmiotu"),
				ChatLib.ColorUtil.formatHEX("   #666666&oPPM - Sprzedaz jednej sztuki przedmiotu"),
				ChatLib.ColorUtil.formatHEX("   #666666&oSHIFT + LPM - Kupno staka przedmiotu"),
				ChatLib.ColorUtil.formatHEX("   #666666&oSHIFT + PPM - Sprzedaz staka przedmiotu)"),""};

		return createAmountedInvItem(lore, material, amount, false);
	}

	public static void createSchowek(final Player player)
	{
		Inventory inventory = Bukkit.createInventory(
				player,
				9*3 + (DataManager.getInstance().getLocal(player).getSchowekLevel() * 9),
				ChatColor.translateAlternateColorCodes('&', "&c&lSCHOWEK"));

		if (DataManager.getInstance().getLocal(player).getSchowek() != null)
		{
			final ArrayList<ItemStack> items = (ArrayList<ItemStack>) DataManager.getInstance().getLocal(player).getSchowek();
			final ItemStack[] contents = Objects.requireNonNull(items).toArray(new ItemStack[items.size()]);

            inventory.setContents(contents);
			player.openInventory(inventory);

			return;
		}

		ChatManager.sendNotification(player, "Trwa tworzenie Twojego schowku... &e⌛", ChatManager.NotificationType.INFORMATION);

		final ItemStack[] items = inventory.getContents();
		final List<ItemStack> contents = new ArrayList<>(Arrays.asList(items));

        DataManager.getInstance().getLocal(player).setSchowek(contents);
		ChatManager.sendNotification(player, "Twoj schowek zostal utworzony, mozesz go otworzyc pod komenda #fc7474/schowek", ChatManager.NotificationType.SUCCESS);
	}

	public class ItemShop
	{
		public static boolean isShopable(final Material material)
		{
			return getValueOf(material)[0] != -1;
		}

		public static boolean isSellable(final Material material)
		{
			return getValueOf(material)[1] != -1;
		}

		/**@param material [0] -> Buy value, [1] -> Sell value, [2] -> Stacked value */
		public static int @NotNull [] getValueOf(final Material material)
		{
			int[] values = {-1, -1, -1};

			switch (material)
			{
				case COBBLESTONE -> values = new int[]{4, 2, 8};
				case NETHERRACK -> values = new int[]{4, 2, 16};
				case STONE -> values = new int[]{4, 3, 16};
				case DIRT -> values = new int[]{8, 3, 8};
				case GRASS_BLOCK -> values = new int[]{16, 6, 16};
				case CRIMSON_NYLIUM -> values = new int[]{16, 6, 8};
				case WARPED_NYLIUM -> values = new int[]{16, 6, 8};
				case GRANITE -> values = new int[]{8, 4, 8};
				case DIORITE -> values = new int[]{8, 4, 8};
				case ANDESITE -> values = new int[]{8, 4, 8};
				case GRAVEL -> values = new int[]{8, 4, 8};
				case SAND -> values = new int[]{8, 2, 8};
				case SANDSTONE -> values = new int[]{8, 3, 8};
				case RED_SAND -> values = new int[]{8, 3, 8};
				case RED_SANDSTONE -> values = new int[]{8, 4, 8};

				case OAK_LOG -> values = new int[]{8, 4, 8};
				case SPRUCE_LOG -> values = new int[]{8, 4, 8};
				case BIRCH_LOG -> values = new int[]{8, 4, 8};
				case JUNGLE_LOG -> values = new int[]{8, 4, 8};
				case ACACIA_LOG -> values = new int[]{8, 4, 8};
				case DARK_OAK_LOG -> values = new int[]{8, 4, 8};
				case CRIMSON_STEM -> values = new int[]{12, 6, 8};
				case WARPED_STEM -> values = new int[]{12, 6, 8};
				case OAK_LEAVES -> values = new int[]{8, 3, 16};
				case SPRUCE_LEAVES -> values = new int[]{8, 3, 16};
				case BIRCH_LEAVES -> values = new int[]{8, 3, 16};
				case JUNGLE_LEAVES -> values = new int[]{8, 3, 16};
				case ACACIA_LEAVES -> values = new int[]{8, 3, 16};
				case DARK_OAK_LEAVES -> values = new int[]{8, 3, 16};
				case SNOW_BLOCK -> values = new int[]{32, 8, 16};

				case ICE -> values = new int[]{16, 8, 8};
				case PACKED_ICE -> values = new int[]{24, 12, 8};
				case SEA_LANTERN -> values = new int[]{8, -1, 1};
				case PRISMARINE -> values = new int[]{16, -1, 8};
				case DARK_PRISMARINE -> values = new int[]{24, 12, 8};
				case NETHER_BRICKS -> values = new int[]{8, 4, 8};
				case GLOWSTONE -> values = new int[]{24, 12, 8};
				case SOUL_SAND -> values = new int[]{24, 6, 8};
				case BLACKSTONE -> values = new int[]{8, 3, 8};
				case OBSIDIAN -> values = new int[]{32, 16, 8};
				case END_STONE -> values = new int[]{12, 8, 16};
				case END_STONE_BRICKS -> values = new int[]{36, 12, 8};
				case PURPUR_BLOCK -> values = new int[]{24, -1, 8};
				case CHORUS_FRUIT -> values = new int[]{32, 8, 8};
				case CHORUS_FLOWER -> values = new int[]{-1, 12, 8};

				case NETHERITE_INGOT -> values = new int[]{1000, 400, 1};
				case EMERALD -> values = new int[]{40, 20, 1};
				case DIAMOND -> values = new int[]{160, 60, 1};
				case GOLD_INGOT -> values = new int[]{32, 6, 1};
				case IRON_INGOT -> values = new int[]{16, 4, 1};
				case QUARTZ -> values = new int[]{24, 16, 8};
				case COPPER_INGOT -> values = new int[]{24, 8, 8};
				case REDSTONE -> values = new int[]{12, 8, 8};
				case LAPIS_LAZULI -> values = new int[]{24, 6, 8};
				case COAL -> values = new int[]{12, 4, 8};
				case WHEAT -> values = new int[]{12, 4, 8};
				case CARROT -> values = new int[]{16, 4, 8};
				case POTATO -> values = new int[]{12, 4, 8};
				case MELON_SLICE -> values = new int[]{32, 6, 8};
				case BEETROOT -> values = new int[]{24, 8, 8};

				case BAMBOO -> values = new int[]{48, 4, 8};
				case SUGAR_CANE -> values = new int[]{16, 8, 8};
				case CACTUS -> values = new int[]{8, 6, 8};
				case PUMPKIN -> values = new int[]{12, 8, 8};
				case EGG -> values = new int[]{12, 4, 8};
				case APPLE -> values = new int[]{8, 2, 1};
				case GOLDEN_APPLE -> values = new int[]{200, 64, 1};
				case PORKCHOP -> values = new int[]{8, 4, 16};
				case RABBIT -> values = new int[]{8, 4, 16};
				case BEEF -> values = new int[]{8, 4, 16};
				case CHICKEN -> values = new int[]{8, 4, 16};
				case MUTTON -> values = new int[]{8, 4, 16};
				case LEATHER -> values = new int[]{8, 24, 12};
				case HONEYCOMB -> values = new int[]{16, 12, 4};
				case NETHER_WART -> values = new int[]{48, 16, 8};

				case SLIME_BALL -> values = new int[]{64, -1, 8};
				case ENDER_PEARL -> values = new int[]{32, 4, 1};
				case BLAZE_ROD -> values = new int[]{48, 8, 1};
				case GHAST_TEAR -> values = new int[]{128, 32, 1};
				case SPIDER_EYE -> values = new int[]{24, 8, 1};
				case GUNPOWDER -> values = new int[]{32, 16, 8};
				case BONE -> values = new int[]{24, 12, 8};
				case STRING -> values = new int[]{24, 18, 8};
				case NETHER_STAR -> values = new int[]{-1, 25000, 1};
			}

			return values;
		}
	}
}