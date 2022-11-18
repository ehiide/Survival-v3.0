package mc.server.survival.events;

import mc.server.Logger;
import mc.server.survival.commands.Craftingi;
import mc.server.survival.files.Main;
import mc.server.survival.libraries.*;
import mc.server.survival.libraries.chemistry.Chemistries;
import mc.server.survival.libraries.chemistry.ChemistryDrug;
import mc.server.survival.libraries.chemistry.ChemistryItem;
import mc.server.survival.libraries.enchantments.EnchantmentHandler;
import mc.server.survival.libraries.enchantments.EnchantmentWrapper;
import mc.server.survival.libraries.trading.Trading;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import mc.server.survival.managers.FileManager;
import mc.server.survival.worlds.WorldHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.GrindstoneInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class Inventory implements Listener
{
	@EventHandler
	public void onEvent(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		final int slot =  event.getSlot();

		if (checkName(event, "&c&lDOMKI"))
		{
			event.setCancelled(true);

			if (slot == 11)
			{
				if (event.getClick() == ClickType.LEFT)
				{
					if (DataManager.getInstance().getLocal(player).isHomeExist("1"))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Nie wiem co piles, ale wlasnie probujesz przeteleportowac sie do domku, ktorego nawet nie ustawiles!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
						return;
					}

					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cTrwa teleportacja do wybranego domku... #fff203⌛");
					player.teleport(DataManager.getInstance().getLocal(player).getHome("1"));

					TaskLib.getInstance().runSyncLater(() -> WorldHandler.queueWorldChange(player), 20);
				}

				if (event.getClick() == ClickType.RIGHT)
					DataManager.getInstance().getLocal(player).setHome("1", player.getLocation());

				InventoryLib.createNewInventory(player, 27, ChatColor.translateAlternateColorCodes('&', "&c&lDOMKI"), "domki");
			}

			if (slot == 15)
			{
				if (event.getClick() == ClickType.LEFT)
				{
					if (DataManager.getInstance().getLocal(player).isHomeExist("2"))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Nie wiem co piles, ale wlasnie probujesz przeteleportowac sie do domku, ktorego nawet nie ustawiles!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
						return;
					}

					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cTrwa teleportacja do wybranego domku... #fff203⌛");
					player.teleport(DataManager.getInstance().getLocal(player).getHome("2"));

					TaskLib.getInstance().runSyncLater(() -> WorldHandler.queueWorldChange(player), 20);
				}

				if (event.getClick() == ClickType.RIGHT)
					DataManager.getInstance().getLocal(player).setHome("2", player.getLocation());

				InventoryLib.createNewInventory(player, 27, ChatColor.translateAlternateColorCodes('&', "&c&lDOMKI"), "domki");
			}
		}
		else if (checkName(event, "&c&lSCHOWEK"))
			schedule(player, event.getInventory());
		else if (checkName(event, "&c&lULEPSZENIE SCHOWKU"))
		{
			event.setCancelled(true);

			if (slot == 15)
			{
				if (event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT)
				{
					if (DataManager.getInstance().getLocal(player).getSchowekLevel() >= 3)
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Posiadasz juz maksymalny poziom ulepszenia schowku!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
						return;
					}

					final boolean isAble = DataManager.getInstance().getLocal(player).getMoney() >= 200;

					if (!isAble)
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Marzenia marzeniami, ale najpierw uzbieraj sume na zakup ulepszenia, troszke Ci brakuje!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
						return;
					}
					else
					{
						DataManager.getInstance().getLocal(player).removeMoney(200);
						DataManager.getInstance().getLocal(player).setSchowekLevel(DataManager.getInstance().getLocal(player).getSchowekLevel() + 1);

						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#8c8c8cGratulacje! Twoj schowek zostal powiekszony, wpisz komende #fc7474/schowek #8c8c8ci zajrzyj do srodka!");
						ScoreboardLib.getInstance().reloadContents(player);
						QuestLib.manageQuest(player, 7);
					}

					player.closeInventory();
				}
			}
		}
		else if (checkName(event, "&c&lSKLEP"))
		{
			event.setCancelled(true);

			if (event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT)
				if (slot == 22)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI"), "sklep_przedmioty");
				else if (slot == 11)
					InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z EFEKTAMI"), "sklep_efekty");
				else if (slot == 15)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lCZARNY RYNEK"), "tradingserveritems");
				else if (slot == 33)
					InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP GANGU"), "sklep_gang");
				else if (slot == 29)
					InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z DODATKAMI"), "sklep_dodatki");
		}
		else if (checkName(event, "&c&lCZARNY RYNEK"))
		{
			event.setCancelled(true);

			if (event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT)
				if (slot == 48)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lCZARNY RYNEK"), "tradingserveritems");
				else if (slot == 49)
					InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP"), "sklep");
				else if (slot == 50)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lCZARNY RYNEK"), "tradingplayeritems");
				else
				{
					if (event.getCurrentItem() == null) return;

					if (event.getCurrentItem().getType() == Material.AIR) return;

					final String ID = event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "ID"), PersistentDataType.STRING);

					if (ID != null && Trading.getTrades().contains(ID))
					{
						ItemStack item = Trading.getTrade(ID);
						int cost = (int) FileManager.getInstance().trades().get(ID + ".cost");
						String owner = (String) FileManager.getInstance().trades().get(ID + ".owner");

						final String STATUS = event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "STATUS"), PersistentDataType.STRING);
						final String VIEW = event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "VIEW"), PersistentDataType.STRING);

						if (STATUS.equalsIgnoreCase("pending"))
						{
							if (VIEW.equalsIgnoreCase("global"))
							{
								if (DataManager.getInstance().getLocal(player).getMoney() >= cost)
								{
									DataManager.getInstance().getLocal(player).removeMoney(cost);
									ScoreboardLib.getInstance().reloadContents(player);

									if (InventoryLib.isFullInventory(player))
										player.getWorld().dropItemNaturally(player.getLocation(), item);
									else
										player.getInventory().addItem(item);

									Trading.buyTrade(ID);
									ChatManager.sendNotification(player, "Pomyslnie zakupiles przedmiot z czarnego rynku #8c8c8c(" + cost + "⛃)#80ff1f!", ChatManager.NotificationType.SUCCESS);
								}
								else
								{
									castByNoMoney(player);
									return;
								}

								InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lCZARNY RYNEK"), "tradingserveritems");
							}
							else
							{
								if (InventoryLib.isFullInventory(player))
									player.getWorld().dropItemNaturally(player.getLocation(), item);
								else
									player.getInventory().addItem(item);

								Trading.removeTrade(ID);
								InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lCZARNY RYNEK"), "tradingplayeritems");
							}
						}
						else if (STATUS.equalsIgnoreCase("ended"))
						{
							if (owner.equalsIgnoreCase(player.getName().toLowerCase()))
							{
								if (InventoryLib.isFullInventory(player))
									player.getWorld().dropItemNaturally(player.getLocation(), item);
								else
									player.getInventory().addItem(item);
							}

							Trading.removeTrade(ID);
							InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lCZARNY RYNEK"), "tradingplayeritems");
						}
						else if (STATUS.equalsIgnoreCase("buyed"))
						{
							if (owner.equalsIgnoreCase(player.getName().toLowerCase()))
							{
								DataManager.getInstance().getLocal(player).addMoney(cost);
								ScoreboardLib.getInstance().reloadContents(player);
							}

							Trading.removeTrade(ID);
							InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lCZARNY RYNEK"), "tradingplayeritems");
						}
					}
					else return;
				}
		}
		else if (checkName(event, "&c&lSKLEP Z PRZEDMIOTAMI"))
		{
			event.setCancelled(true);

			if (event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT)
				if (slot == 9)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (I, 1/6)"), "sklep_przedmioty_itemy");
				else if (slot == 18)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
				else if (slot == 27)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (S, 1/1)"), "sklep_przedmioty_specjalne");
				else if (slot == 49)
					InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP"), "sklep");
		}
		else if (checkName(event, "&c&lSKLEP Z PRZEDMIOTAMI (I, 1/6)"))
		{
			event.setCancelled(true);

			if (event.getSlot() == 11 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy1")))
				manageItem(event, Material.COBBLESTONE, 16, 4 ,2);
			else if (event.getSlot() == 12 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy2")))
				manageItem(event, Material.NETHERRACK, 16, 4 ,2);
			else if (event.getSlot() == 13 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy3")))
				manageItem(event, Material.STONE, 16, 4 ,3);
			else if (event.getSlot() == 14 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy4")))
				manageItem(event, Material.DIRT, 16, 8 ,3);
			else if (event.getSlot() == 15 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy5")))
				manageItem(event, Material.GRASS_BLOCK, 16, 16 ,6);
			else if (event.getSlot() == 20 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy6")))
				manageItem(event, Material.CRIMSON_NYLIUM, 16, 16 ,8);
			else if (event.getSlot() == 21 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy7")))
				manageItem(event, Material.WARPED_NYLIUM, 16, 16 ,8);
			else if (event.getSlot() == 22 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy8")))
				manageItem(event, Material.GRANITE, 8, 8 ,4);
			else if (event.getSlot() == 23 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy9")))
				manageItem(event, Material.DIORITE, 8, 8 ,4);
			else if (event.getSlot() == 24 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy10")))
				manageItem(event, Material.ANDESITE, 8, 8 ,4);
			else if (event.getSlot() == 29 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy11")))
				manageItem(event, Material.GRAVEL, 8, 8 ,4);
			else if (event.getSlot() == 30 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy12")))
				manageItem(event, Material.SAND, 8, 4, 2);
			else if (event.getSlot() == 31 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy13")))
				manageItem(event, Material.SANDSTONE, 8, 4, 3);
			else if (event.getSlot() == 32 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy14")))
				manageItem(event, Material.RED_SAND, 8, 8, 3);
			else if (event.getSlot() == 33 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy15")))
				manageItem(event, Material.RED_SANDSTONE, 8, 8, 4);

			if (event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT)
				if (slot == 18)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
				else if (slot == 27)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (S, 1/1)"), "sklep_przedmioty_specjalne");
				else if (slot == 47)
					manageMultiSell(player);
				else if (slot == 49)
					InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP"), "sklep");
				else if (slot == 53)
                    InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (I, 2/6)"), "sklep_przedmioty_itemy2");
		}
        else if (checkName(event, "&c&lSKLEP Z PRZEDMIOTAMI (I, 2/6)"))
        {
			event.setCancelled(true);

			if (event.getSlot() == 11 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy16")))
				manageItem(event, Material.OAK_LOG, 8, 8 ,4);
			else if (event.getSlot() == 12 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy17")))
				manageItem(event, Material.SPRUCE_LOG, 8, 8 ,4);
			else if (event.getSlot() == 13 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy18")))
				manageItem(event, Material.BIRCH_LOG, 8, 8 ,4);
			else if (event.getSlot() == 14 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy19")))
				manageItem(event, Material.JUNGLE_LOG, 8, 8 ,4);
			else if (event.getSlot() == 15 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy20")))
				manageItem(event, Material.ACACIA_LOG, 8, 8 ,4);
			else if (event.getSlot() == 20 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy21")))
				manageItem(event, Material.DARK_OAK_LOG, 8, 8 ,4);
			else if (event.getSlot() == 21 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy22")))
				manageItem(event, Material.CRIMSON_STEM, 8, 12 ,6);
			else if (event.getSlot() == 22 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy23")))
				manageItem(event, Material.WARPED_STEM, 8, 12 ,6);
			else if (event.getSlot() == 23 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy24")))
				manageItem(event, Material.OAK_LEAVES, 16, 8 ,3);
			else if (event.getSlot() == 24 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy25")))
				manageItem(event, Material.SPRUCE_LEAVES, 16, 8 ,3);
			else if (event.getSlot() == 29 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy26")))
				manageItem(event, Material.BIRCH_LEAVES, 16, 8 ,3);
			else if (event.getSlot() == 30 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy27")))
				manageItem(event, Material.JUNGLE_LEAVES, 16, 8 ,3);
			else if (event.getSlot() == 31 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy28")))
				manageItem(event, Material.ACACIA_LEAVES, 16, 8 ,3);
			else if (event.getSlot() == 32 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy29")))
				manageItem(event, Material.DARK_OAK_LEAVES, 16, 8 ,3);
			else if (event.getSlot() == 33 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy30")))
				manageItem(event, Material.SNOW_BLOCK, 8, 32, 16);

            if (event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT)
                if (slot == 18)
                    InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
                else if (slot == 27)
                    InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (S, 1/1)"), "sklep_przedmioty_specjalne");
				else if (slot == 47)
					manageMultiSell(player);
				else if (slot == 49)
                    InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP"), "sklep");
                else if (slot == 45)
                    InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (I, 1/6)"), "sklep_przedmioty_itemy");
				else if (slot == 53)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (I, 3/6)"), "sklep_przedmioty_itemy3");
		}
		else if (checkName(event, "&c&lSKLEP Z PRZEDMIOTAMI (I, 3/6)"))
		{
			event.setCancelled(true);

			if (event.getSlot() == 11 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy31")))
				manageItem(event, Material.ICE, 8, 16 ,8);
			else if (event.getSlot() == 12 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy32")))
				manageItem(event, Material.PACKED_ICE, 8, 24 ,12);
			else if (event.getSlot() == 13 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy33")))
				manageItem(event, Material.SEA_LANTERN, 1, 8 ,-1);
			else if (event.getSlot() == 14 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy34")))
				manageItem(event, Material.PRISMARINE, 8, 16 ,-1);
			else if (event.getSlot() == 15 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy35")))
				manageItem(event, Material.DARK_PRISMARINE, 8, 24 ,12);
			else if (event.getSlot() == 20 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy36")))
				manageItem(event, Material.NETHER_BRICKS, 8, 8 ,4);
			else if (event.getSlot() == 21 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy37")))
				manageItem(event, Material.GLOWSTONE, 8, 24 ,12);
			else if (event.getSlot() == 22 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy38")))
				manageItem(event, Material.SOUL_SAND, 8, 24 ,6);
			else if (event.getSlot() == 23 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy39")))
				manageItem(event, Material.BLACKSTONE, 8, 8 ,3);
			else if (event.getSlot() == 24 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy40")))
				manageItem(event, Material.OBSIDIAN, 8, 32 ,16);
			else if (event.getSlot() == 29 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy41")))
				manageItem(event, Material.END_STONE, 16, 12 ,8);
			else if (event.getSlot() == 30 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy42")))
				manageItem(event, Material.END_STONE_BRICKS, 8, 36 ,12);
			else if (event.getSlot() == 31 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy43")))
				manageItem(event, Material.PURPUR_BLOCK, 8, 24 ,-1);
			else if (event.getSlot() == 32 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy44")))
				manageItem(event, Material.CHORUS_FRUIT, 8, 32 ,8);
			else if (event.getSlot() == 33 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy45")))
				manageItem(event, Material.CHORUS_FLOWER, 8, -1, 12);

			if (event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT)
				if (slot == 18)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
				else if (slot == 27)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (S, 1/1)"), "sklep_przedmioty_specjalne");
				else if (slot == 47)
					manageMultiSell(player);
				else if (slot == 49)
					InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP"), "sklep");
				else if (slot == 45)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (I, 2/6)"), "sklep_przedmioty_itemy2");
				else if (slot == 53)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (I, 4/6)"), "sklep_przedmioty_itemy4");
		}
		else if (checkName(event, "&c&lSKLEP Z PRZEDMIOTAMI (I, 4/6)"))
		{
			event.setCancelled(true);

			if (event.getSlot() == 11 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy46")))
				manageItem(event, Material.NETHERITE_INGOT, 1, 1000 ,400);
			else if (event.getSlot() == 12 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy47")))
				manageItem(event, Material.EMERALD, 1, 40 ,20);
			else if (event.getSlot() == 13 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy48")))
				manageItem(event, Material.DIAMOND, 1, 160 ,60);
			else if (event.getSlot() == 14 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy49")))
				manageItem(event, Material.GOLD_INGOT, 1,  32,6);
			else if (event.getSlot() == 15 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy50")))
				manageItem(event, Material.IRON_INGOT, 1, 16 ,4);
			else if (event.getSlot() == 20 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy51")))
				manageItem(event, Material.QUARTZ, 8, 24 ,16);
			else if (event.getSlot() == 21 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy52")))
				manageItem(event, Material.COPPER_INGOT, 8, 24 ,8);
			else if (event.getSlot() == 22 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy53")))
				manageItem(event, Material.REDSTONE, 8, 12 ,8);
			else if (event.getSlot() == 23 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy54")))
				manageItem(event, Material.LAPIS_LAZULI, 8, 24 ,6);
			else if (event.getSlot() == 24 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy55")))
				manageItem(event, Material.COAL, 8, 12 ,4);
			else if (event.getSlot() == 29 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy56")))
				manageItem(event, Material.WHEAT, 8, 12 ,4);
			else if (event.getSlot() == 30 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy57")))
				manageItem(event, Material.CARROT, 8, 16 ,6);
			else if (event.getSlot() == 31 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy58")))
				manageItem(event, Material.POTATO, 8, 12 ,4);
			else if (event.getSlot() == 32 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy59")))
				manageItem(event, Material.MELON_SLICE, 8, 32 ,6);
			else if (event.getSlot() == 33 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy60")))
				manageItem(event, Material.BEETROOT, 8, 24 , 8);

			if (event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT)
				if (slot == 18)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
				else if (slot == 27)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (S, 1/1)"), "sklep_przedmioty_specjalne");
				else if (slot == 47)
					manageMultiSell(player);
				else if (slot == 49)
					InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP"), "sklep");
				else if (slot == 45)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (I, 3/6)"), "sklep_przedmioty_itemy3");
				else if (slot == 53)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (I, 5/6)"), "sklep_przedmioty_itemy5");
		}
		else if (checkName(event, "&c&lSKLEP Z PRZEDMIOTAMI (I, 5/6)"))
		{
			event.setCancelled(true);

			if (event.getSlot() == 11 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy61")))
				manageItem(event, Material.BAMBOO, 16, 48 ,4);
			else if (event.getSlot() == 12 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy62")))
				manageItem(event, Material.SUGAR_CANE, 8, 16 ,8);
			else if (event.getSlot() == 13 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy63")))
				manageItem(event, Material.CACTUS, 8, 8 ,6);
			else if (event.getSlot() == 14 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy64")))
				manageItem(event, Material.PUMPKIN, 8,  12,8);
			else if (event.getSlot() == 15 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy65")))
				manageItem(event, Material.EGG, 8, 12 ,4);
			else if (event.getSlot() == 20 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy66")))
				manageItem(event, Material.APPLE, 1, 8 ,2);
			else if (event.getSlot() == 21 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy67")))
				manageItem(event, Material.GOLDEN_APPLE, 1, 200 ,64);
			else if (event.getSlot() == 22 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy68")))
				manageItem(event, Material.PORKCHOP, 16, 8 ,4);
			else if (event.getSlot() == 23 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy69")))
				manageItem(event, Material.RABBIT, 16, 8 ,4);
			else if (event.getSlot() == 24 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy70")))
				manageItem(event, Material.BEEF, 16, 8 ,4);
			else if (event.getSlot() == 29 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy71")))
				manageItem(event, Material.CHICKEN, 16, 8 ,4);
			else if (event.getSlot() == 30 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy72")))
				manageItem(event, Material.MUTTON, 16, 8 ,4);
			else if (event.getSlot() == 31 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy73")))
				manageItem(event, Material.LEATHER, 8, 24 ,12);
			else if (event.getSlot() == 32 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy74")))
				manageItem(event, Material.HONEYCOMB, 4, 16 ,12);
			else if (event.getSlot() == 33 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy75")))
				manageItem(event, Material.NETHER_WART, 8, 48 , 16);

			if (event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT)
				if (slot == 18)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
				else if (slot == 27)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (S, 1/1)"), "sklep_przedmioty_specjalne");
				else if (slot == 47)
					manageMultiSell(player);
				else if (slot == 49)
					InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP"), "sklep");
				else if (slot == 45)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (I, 4/6)"), "sklep_przedmioty_itemy4");
				else if (slot == 53)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (I, 6/6)"), "sklep_przedmioty_itemy6");
		}
		else if (checkName(event, "&c&lSKLEP Z PRZEDMIOTAMI (I, 6/6)"))
		{
			event.setCancelled(true);

			if (event.getSlot() == 11 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy76")))
				manageItem(event, Material.SLIME_BALL, 8, 64 ,-1);
			else if (event.getSlot() == 12 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy77")))
				manageItem(event, Material.ENDER_PEARL, 1, 32 ,4);
			else if (event.getSlot() == 13 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy78")))
				manageItem(event, Material.BLAZE_ROD, 1, 48 ,8);
			else if (event.getSlot() == 14 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy79")))
				manageItem(event, Material.GHAST_TEAR, 1,  128,32);
			else if (event.getSlot() == 15 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy80")))
				manageItem(event, Material.SPIDER_EYE, 1, 24 ,8);
			else if (event.getSlot() == 20 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy81")))
				manageItem(event, Material.GUNPOWDER, 8, 32 ,16);
			else if (event.getSlot() == 21 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy82")))
				manageItem(event, Material.BONE, 8, 24 ,12);
			else if (event.getSlot() == 22 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy83")))
				manageItem(event, Material.STRING, 8, 24 ,18);
			else if (event.getSlot() == 23 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepitemy84")))
				manageItem(event, Material.NETHER_STAR, 1, -1 ,25000);

			if (event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT)
				if (slot == 18)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
				else if (slot == 27)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (S, 1/1)"), "sklep_przedmioty_specjalne");
				else if (slot == 47)
					manageMultiSell(player);
				else if (slot == 49)
					InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP"), "sklep");
				else if (slot == 45)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (I, 5/6)"), "sklep_przedmioty_itemy5");
		}
		else if (checkName(event, "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"))
		{
			event.setCancelled(true);

			if (event.getSlot() == 11 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepksiazki1")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 4200)
				{
					player.getInventory().addItem(InventoryLib.getEnchantmentBook(Enchantment.MENDING, 1));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 4200);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles zakleta ksiazke #8c8c8c(4200⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}
			else if (event.getSlot() == 12 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepksiazki2")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 2700)
				{
					player.getInventory().addItem(InventoryLib.getEnchantmentBook(Enchantment.ARROW_INFINITE, 1));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 2700);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles zakleta ksiazke #8c8c8c(2700⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}
			else if (event.getSlot() == 13 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepksiazki3")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 3000)
				{
					player.getInventory().addItem(InventoryLib.getEnchantmentBook(Enchantment.SILK_TOUCH, 1));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 3000);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles zakleta ksiazke #8c8c8c(3000⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}
			else if (event.getSlot() == 14 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepksiazki4")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 3900)
				{
					player.getInventory().addItem(InventoryLib.getEnchantmentBook(Enchantment.PROTECTION_ENVIRONMENTAL, 4));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 3900);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles zakleta ksiazke #8c8c8c(3900⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}
			else if (event.getSlot() == 15 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepksiazki5")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 3700)
				{
					player.getInventory().addItem(InventoryLib.getEnchantmentBook(Enchantment.DAMAGE_ALL, 5));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 3700);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles zakleta ksiazke #8c8c8c(3700⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}
			else if (event.getSlot() == 20 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepksiazki6")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 2200)
				{
					player.getInventory().addItem(InventoryLib.getEnchantmentBook(Enchantment.FIRE_ASPECT, 2));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 2200);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles zakleta ksiazke #8c8c8c(2200⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}
			else if (event.getSlot() == 21 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepksiazki7")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 3900)
				{
					player.getInventory().addItem(InventoryLib.getEnchantmentBook(Enchantment.ARROW_DAMAGE, 5));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 3900);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles zakleta ksiazke #8c8c8c(3900⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}
			else if (event.getSlot() == 22 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepksiazki8")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 1400)
				{
					player.getInventory().addItem(InventoryLib.getEnchantmentBook(Enchantment.ARROW_FIRE, 2));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 1400);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles zakleta ksiazke #8c8c8c(1400⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}
			else if (event.getSlot() == 23 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepksiazki9")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 2900)
				{
					player.getInventory().addItem(InventoryLib.getEnchantmentBook(Enchantment.ARROW_KNOCKBACK, 2));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 2900);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles zakleta ksiazke #8c8c8c(2900⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}
			else if (event.getSlot() == 24 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepksiazki10")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 4000)
				{
					player.getInventory().addItem(InventoryLib.getEnchantmentBook(Enchantment.DIG_SPEED, 5));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 4000);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles zakleta ksiazke #8c8c8c(4000⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}
			else if (event.getSlot() == 29 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepksiazki11")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 2500)
				{
					player.getInventory().addItem(InventoryLib.getEnchantmentBook(Enchantment.DURABILITY, 3));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 2500);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles zakleta ksiazke #8c8c8c(2500⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}
			else if (event.getSlot() == 30 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepksiazki12")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 3400)
				{
					player.getInventory().addItem(InventoryLib.getEnchantmentBook(Enchantment.LOOT_BONUS_MOBS, 3));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 3400);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles zakleta ksiazke #8c8c8c(3400⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}
			else if (event.getSlot() == 31 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepksiazki13")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 3600)
				{
					player.getInventory().addItem(InventoryLib.getEnchantmentBook(Enchantment.LOOT_BONUS_BLOCKS, 3));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 3600);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles zakleta ksiazke #8c8c8c(3600⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}
			else if (event.getSlot() == 32 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepksiazki14")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 3300)
				{
					player.getInventory().addItem(InventoryLib.getEnchantmentBook(Enchantment.PROTECTION_FALL, 4));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 3300);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles zakleta ksiazke #8c8c8c(3300⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}
			else if (event.getSlot() == 33 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepksiazki15")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 3000)
				{
					player.getInventory().addItem(InventoryLib.getEnchantmentBook(Enchantment.THORNS, 3));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 3000);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles zakleta ksiazke #8c8c8c(3000⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}

			if (event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT)
				if (slot == 9)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (I, 1/6)"), "sklep_przedmioty_itemy");
				else if (slot == 27)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (S, 1/1)"), "sklep_przedmioty_specjalne");
				else if (slot == 49)
					InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP"), "sklep");
				else if (slot == 53)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 2/2)"), "sklep_przedmioty_ksiazki2");
				else if (slot == 45)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
		}
		else if (checkName(event, "&c&lSKLEP Z PRZEDMIOTAMI (K, 2/2)"))
		{
			event.setCancelled(true);

			if (event.getSlot() == 11 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepksiazki16")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 3600)
				{
					player.getInventory().addItem(InventoryLib.getEnchantmentBook(Enchantment.DEPTH_STRIDER, 3));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 3600);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 2/2)"), "sklep_przedmioty_ksiazki2");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles zakleta ksiazke #8c8c8c(3600⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}
			else if (event.getSlot() == 12 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepksiazki17")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 2700)
				{
					player.getInventory().addItem(InventoryLib.getEnchantmentBook(Enchantment.LUCK, 3));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 2700);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 2/2)"), "sklep_przedmioty_ksiazki2");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles zakleta ksiazke #8c8c8c(2700⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}
			else if (event.getSlot() == 13 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepksiazki18")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 2600)
				{
					player.getInventory().addItem(InventoryLib.getEnchantmentBook(Enchantment.LURE, 3));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 2600);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 2/2)"), "sklep_przedmioty_ksiazki2");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles zakleta ksiazke #8c8c8c(2600⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}
			else if (event.getSlot() == 14 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepksiazki19")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 2400)
				{
					player.getInventory().addItem(InventoryLib.getEnchantmentBook(Enchantment.QUICK_CHARGE, 3));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 2400);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 2/2)"), "sklep_przedmioty_ksiazki2");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles zakleta ksiazke #8c8c8c(2400⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}
			else if (event.getSlot() == 15 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepksiazki20")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 2900)
				{
					player.getInventory().addItem(InventoryLib.getEnchantmentBook(Enchantment.CHANNELING, 1));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 2900);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 2/2)"), "sklep_przedmioty_ksiazki2");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles zakleta ksiazke #8c8c8c(2900⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}
			else if (event.getSlot() == 20 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepksiazki21")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 3500)
				{
					player.getInventory().addItem(InventoryLib.getEnchantmentBook(Enchantment.MULTISHOT, 1));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 3500);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 2/2)"), "sklep_przedmioty_ksiazki2");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles zakleta ksiazke #8c8c8c(3500⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}
			else if (event.getSlot() == 21 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepksiazki22")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 3700)
				{
					player.getInventory().addItem(InventoryLib.getEnchantmentBook(Enchantment.IMPALING, 5));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 3700);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 2/2)"), "sklep_przedmioty_ksiazki2");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles zakleta ksiazke #8c8c8c(3700⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}
			else if (event.getSlot() == 22 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepksiazki23")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 3400)
				{
					player.getInventory().addItem(InventoryLib.getEnchantmentBook(Enchantment.PIERCING, 4));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 3400);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 2/2)"), "sklep_przedmioty_ksiazki2");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles zakleta ksiazke #8c8c8c(3400⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}
			else if (event.getSlot() == 23 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepksiazki24")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 1900)
				{
					player.getInventory().addItem(InventoryLib.getEnchantmentBook(Enchantment.RIPTIDE, 3));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 1900);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 2/2)"), "sklep_przedmioty_ksiazki2");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles zakleta ksiazke #8c8c8c(1900⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}

			if (event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT)
				if (slot == 9)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (I, 1/6)"), "sklep_przedmioty_itemy");
				else if (slot == 27)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (S, 1/1)"), "sklep_przedmioty_specjalne");
				else if (slot == 45)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
				else if (slot == 49)
					InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP"), "sklep");
		}
		else if (checkName(event, "&c&lSKLEP Z PRZEDMIOTAMI (S, 1/1)"))
		{
			event.setCancelled(true);

			if (event.getSlot() == 11 && event.getClick() == ClickType.LEFT && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepspecjalne1")))
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 7500)
				{
					player.getInventory().addItem(new ItemLib().get("book"));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 7500);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (S, 1/1)"), "sklep_przedmioty_specjalne");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles nowy przedmiot specjalny #8c8c8c(7500⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}
			else if (event.getSlot() == 12 && event.getClick() == ClickType.LEFT)
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (QuestLib.getCompletedQuests(player) >= 14 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepspecjalne2")))
				{
					player.getInventory().addItem(new ItemLib().get("magic_rod"));
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (S, 1/1)"), "sklep_przedmioty_specjalne");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie odebrales specjalny przedmiot!");
				}
				else
					castByNoQuests(player);
			}
			else if (event.getSlot() == 13 && event.getClick() == ClickType.LEFT)
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 300)
				{
					player.getInventory().addItem(new ItemLib().get("granate"));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 300);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (S, 1/1)"), "sklep_przedmioty_specjalne");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles nowy przedmiot specjalny #8c8c8c(300⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}
			else if (event.getSlot() == 14 && event.getClick() == ClickType.LEFT)
			{
				if (InventoryLib.isFullInventory(player))
				{
					castByFullInv(player);
					return;
				}

				if (DataManager.getInstance().getLocal(player).getMoney() >= 1000)
				{
					player.getInventory().addItem(new ItemLib().get("brewing_stand"));
					DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 1000);
					ScoreboardLib.getInstance().reloadContents(player);
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (S, 1/1)"), "sklep_przedmioty_specjalne");
					ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles nowy przedmiot specjalny #8c8c8c(1000⛃)#80ff1f!");
				}
				else
					castByNoMoney(player);
			}

			if (event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT)
				if (slot == 9)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (I, 1/6" +")"), "sklep_przedmioty_itemy");
				else if (slot == 18)
					InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z PRZEDMIOTAMI (K, 1/2)"), "sklep_przedmioty_ksiazki");
				else if (slot == 49)
					InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP"), "sklep");
		}
		else if (checkName(event, "&c&lSKLEP GANGU"))
		{
			event.setCancelled(true);

			if (event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT)
				if (slot == 10 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepgang1")))
				{
					if (!DataManager.getInstance().getLocal(player).getLider(DataManager.getInstance().getLocal(player).getGang()).equalsIgnoreCase(player.getName()))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Tylko lider gangu moze decydowac o jego designie!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
						return;
					}

					if (DataManager.getInstance().getLocal(player).getColor(DataManager.getInstance().getLocal(player).getGang()).equalsIgnoreCase("red"))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Twoj gang posiada juz ten kolor!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
					}
					else
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 250)
						{
							DataManager.getInstance().getLocal(player).setColor(DataManager.getInstance().getLocal(player).getGang(), "red");
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 250);
							ScoreboardLib.getInstance().reloadContentsGlobal();
							InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP GANGU"), "sklep_gang");
							ChatLib.sendGangAllChatMessage(DataManager.getInstance().getLocal(player).getGang(), "#ffc936Gracz " + player.getName() + " zakupil nowy kolor dla gangu [&c█#ffc936] #8c8c8c(200⛃)#ffc936!");
						}
						else
							castByNoMoney(player);
					}
				}
				else if (slot == 11 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepgang2")))
				{
					if (!DataManager.getInstance().getLocal(player).getLider(DataManager.getInstance().getLocal(player).getGang()).equalsIgnoreCase(player.getName()))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Tylko lider gangu moze decydowac o jego designie!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
						return;
					}

					if (DataManager.getInstance().getLocal(player).getColor(DataManager.getInstance().getLocal(player).getGang()).equalsIgnoreCase("blue"))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Twoj gang posiada juz ten kolor!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
					}
					else
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 250)
						{
							DataManager.getInstance().getLocal(player).setColor(DataManager.getInstance().getLocal(player).getGang(), "blue");
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 250);
							ScoreboardLib.getInstance().reloadContentsGlobal();
							InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP GANGU"), "sklep_gang");
							ChatLib.sendGangAllChatMessage(DataManager.getInstance().getLocal(player).getGang(), "#ffc936Gracz " + player.getName() + " zakupil nowy kolor dla gangu [&b█#ffc936] #8c8c8c(200⛃)#ffc936!");
						}
						else
							castByNoMoney(player);
					}
				}
				else if (slot == 12 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepgang3")))
				{
					if (!DataManager.getInstance().getLocal(player).getLider(DataManager.getInstance().getLocal(player).getGang()).equalsIgnoreCase(player.getName()))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Tylko lider gangu moze decydowac o jego designie!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
						return;
					}

					if (DataManager.getInstance().getLocal(player).getColor(DataManager.getInstance().getLocal(player).getGang()).equalsIgnoreCase("green"))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Twoj gang posiada juz ten kolor!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
					}
					else
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 250)
						{
							DataManager.getInstance().getLocal(player).setColor(DataManager.getInstance().getLocal(player).getGang(), "green");
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 250);
							ScoreboardLib.getInstance().reloadContentsGlobal();
							InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP GANGU"), "sklep_gang");
							ChatLib.sendGangAllChatMessage(DataManager.getInstance().getLocal(player).getGang(), "#ffc936Gracz " + player.getName() + " zakupil nowy kolor dla gangu [&a█#ffc936] #8c8c8c(200⛃)#ffc936!");
						}
						else
							castByNoMoney(player);
					}
				}
				else if (slot == 19 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepgang4")))
				{
					if (!DataManager.getInstance().getLocal(player).getLider(DataManager.getInstance().getLocal(player).getGang()).equalsIgnoreCase(player.getName()))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Tylko lider gangu moze decydowac o jego designie!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
						return;
					}

					if (DataManager.getInstance().getLocal(player).getColor(DataManager.getInstance().getLocal(player).getGang()).equalsIgnoreCase("yellow"))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Twoj gang posiada juz ten kolor!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
					}
					else
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 250)
						{
							DataManager.getInstance().getLocal(player).setColor(DataManager.getInstance().getLocal(player).getGang(), "yellow");
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 250);
							ScoreboardLib.getInstance().reloadContentsGlobal();
							InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP GANGU"), "sklep_gang");
							ChatLib.sendGangAllChatMessage(DataManager.getInstance().getLocal(player).getGang(), "#ffc936Gracz " + player.getName() + " zakupil nowy kolor dla gangu [&e█#ffc936] #8c8c8c(200⛃)#ffc936!");
						}
						else
							castByNoMoney(player);
					}
				}
				else if (slot == 20 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepgang5")))
				{
					if (!DataManager.getInstance().getLocal(player).getLider(DataManager.getInstance().getLocal(player).getGang()).equalsIgnoreCase(player.getName()))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Tylko lider gangu moze decydowac o jego designie!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
						return;
					}

					if (DataManager.getInstance().getLocal(player).getColor(DataManager.getInstance().getLocal(player).getGang()).equalsIgnoreCase("white"))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Twoj gang posiada juz ten kolor!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
					}
					else
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 250)
						{
							DataManager.getInstance().getLocal(player).setColor(DataManager.getInstance().getLocal(player).getGang(), "white");
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 250);
							ScoreboardLib.getInstance().reloadContentsGlobal();
							InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP GANGU"), "sklep_gang");
							ChatLib.sendGangAllChatMessage(DataManager.getInstance().getLocal(player).getGang(), "#ffc936Gracz " + player.getName() + " zakupil nowy kolor dla gangu [&f█#ffc936] #8c8c8c(200⛃)#ffc936!");
						}
						else
							castByNoMoney(player);
					}
				}
				else if (slot == 21 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepgang6")))
				{
					if (!DataManager.getInstance().getLocal(player).getLider(DataManager.getInstance().getLocal(player).getGang()).equalsIgnoreCase(player.getName()))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Tylko lider gangu moze decydowac o jego designie!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
						return;
					}

					if (DataManager.getInstance().getLocal(player).getColor(DataManager.getInstance().getLocal(player).getGang()).equalsIgnoreCase("gray"))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Twoj gang posiada juz ten kolor!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
					}
					else
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 250)
						{
							DataManager.getInstance().getLocal(player).setColor(DataManager.getInstance().getLocal(player).getGang(), "gray");
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 250);
							ScoreboardLib.getInstance().reloadContentsGlobal();
							InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP GANGU"), "sklep_gang");
							ChatLib.sendGangAllChatMessage(DataManager.getInstance().getLocal(player).getGang(), "#ffc936Gracz " + player.getName() + " zakupil nowy kolor dla gangu [&7█#ffc936] #8c8c8c(200⛃)#ffc936!");
						}
						else
							castByNoMoney(player);
					}
				}
				else if (slot == 28 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepgang7")))
				{
					if (!DataManager.getInstance().getLocal(player).getLider(DataManager.getInstance().getLocal(player).getGang()).equalsIgnoreCase(player.getName()))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Tylko lider gangu moze decydowac o jego designie!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
						return;
					}

					if (DataManager.getInstance().getLocal(player).getColor(DataManager.getInstance().getLocal(player).getGang()).equalsIgnoreCase("orange"))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Twoj gang posiada juz ten kolor!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
					}
					else
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 250)
						{
							DataManager.getInstance().getLocal(player).setColor(DataManager.getInstance().getLocal(player).getGang(), "orange");
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 250);
							ScoreboardLib.getInstance().reloadContentsGlobal();
							InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP GANGU"), "sklep_gang");
							ChatLib.sendGangAllChatMessage(DataManager.getInstance().getLocal(player).getGang(), "#ffc936Gracz " + player.getName() + " zakupil nowy kolor dla gangu [&6█#ffc936] #8c8c8c(200⛃)#ffc936!");
						}
						else
							castByNoMoney(player);
					}
				}
				else if (slot == 29 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepgang8")))
				{
					if (!DataManager.getInstance().getLocal(player).getLider(DataManager.getInstance().getLocal(player).getGang()).equalsIgnoreCase(player.getName()))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Tylko lider gangu moze decydowac o jego designie!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
						return;
					}

					if (DataManager.getInstance().getLocal(player).getColor(DataManager.getInstance().getLocal(player).getGang()).equalsIgnoreCase("pink"))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Twoj gang posiada juz ten kolor!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
					}
					else
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 250)
						{
							DataManager.getInstance().getLocal(player).setColor(DataManager.getInstance().getLocal(player).getGang(), "pink");
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 250);
							ScoreboardLib.getInstance().reloadContentsGlobal();
							InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP GANGU"), "sklep_gang");
							ChatLib.sendGangAllChatMessage(DataManager.getInstance().getLocal(player).getGang(), "#ffc936Gracz " + player.getName() + " zakupil nowy kolor dla gangu [&d█#ffc936] #8c8c8c(200⛃)#ffc936!");
						}
						else
							castByNoMoney(player);
					}
				}
				else if (slot == 14 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepgangp1")))
				{
					if (!DataManager.getInstance().getLocal(player).getLider(DataManager.getInstance().getLocal(player).getGang()).equalsIgnoreCase(player.getName()))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Tylko lider gangu moze decydowac o jego designie!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
						return;
					}

					if (DataManager.getInstance().getLocal(player).getPrefixes(DataManager.getInstance().getLocal(player).getGang()).equalsIgnoreCase("normal"))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Twoj gang posiada juz te prefixy!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
					}
					else
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 350)
						{
							DataManager.getInstance().getLocal(player).setPrefixes(DataManager.getInstance().getLocal(player).getGang(), "normal");
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 350);
							ScoreboardLib.getInstance().reloadContentsGlobal();
							InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP GANGU"), "sklep_gang");
							ChatLib.sendGangAllChatMessage(DataManager.getInstance().getLocal(player).getGang(), "#ffc936Gracz " + player.getName() + " zakupil nowe prefixy dla gangu [&7Kwadratowe#ffc936] #8c8c8c(350⛃)#ffc936!");
						}
						else
							castByNoMoney(player);
					}
				}
				else if (slot == 23 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepgangp2")))
				{
					if (!DataManager.getInstance().getLocal(player).getLider(DataManager.getInstance().getLocal(player).getGang()).equalsIgnoreCase(player.getName()))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Tylko lider gangu moze decydowac o jego designie!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
						return;
					}

					if (DataManager.getInstance().getLocal(player).getPrefixes(DataManager.getInstance().getLocal(player).getGang()).equalsIgnoreCase("rounded"))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Twoj gang posiada juz te prefixy!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
					}
					else
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 350)
						{
							DataManager.getInstance().getLocal(player).setPrefixes(DataManager.getInstance().getLocal(player).getGang(), "rounded");
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 350);
							ScoreboardLib.getInstance().reloadContentsGlobal();
							InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP GANGU"), "sklep_gang");
							ChatLib.sendGangAllChatMessage(DataManager.getInstance().getLocal(player).getGang(), "#ffc936Gracz " + player.getName() + " zakupil nowe prefixy dla gangu [&7Zaokraglone#ffc936] #8c8c8c(350⛃)#ffc936!");
						}
						else
							castByNoMoney(player);
					}
				}
				else if (slot == 32 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepgangp3")))
				{
					if (!DataManager.getInstance().getLocal(player).getLider(DataManager.getInstance().getLocal(player).getGang()).equalsIgnoreCase(player.getName()))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Tylko lider gangu moze decydowac o jego designie!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
						return;
					}

					if (DataManager.getInstance().getLocal(player).getPrefixes(DataManager.getInstance().getLocal(player).getGang()).equalsIgnoreCase("arrows"))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Twoj gang posiada juz te prefixy!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
					}
					else
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 350)
						{
							DataManager.getInstance().getLocal(player).setPrefixes(DataManager.getInstance().getLocal(player).getGang(), "arrows");
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 350);
							ScoreboardLib.getInstance().reloadContentsGlobal();
							InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP GANGU"), "sklep_gang");
							ChatLib.sendGangAllChatMessage(DataManager.getInstance().getLocal(player).getGang(), "#ffc936Gracz " + player.getName() + " zakupil nowe prefixy dla gangu [&7Strzalkowe#ffc936] #8c8c8c(350⛃)#ffc936!");
						}
						else
							castByNoMoney(player);
					}
				}
				else if (slot == 16 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepgangstar")))
				{
					if (!DataManager.getInstance().getLocal(player).getLider(DataManager.getInstance().getLocal(player).getGang()).equalsIgnoreCase(player.getName()))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Tylko lider gangu moze decydowac o jego designie!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
						return;
					}

					if (DataManager.getInstance().getLocal(player).getStar(DataManager.getInstance().getLocal(player).getGang()))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Twoj gang posiada juz kosmetyk gwiazdy!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
					}
					else
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 900)
						{
							DataManager.getInstance().getLocal(player).setStar(DataManager.getInstance().getLocal(player).getGang(), true);
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 900);
							ScoreboardLib.getInstance().reloadContentsGlobal();
							InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP GANGU"), "sklep_gang");
							ChatLib.sendGangAllChatMessage(DataManager.getInstance().getLocal(player).getGang(), "#ffc936Gracz " + player.getName() + " zakupil ekskluzywna gwiazde dla gangu [★] #8c8c8c(900⛃)#ffc936!");
							QuestLib.manageQuest(player, 13);
						}
						else
							castByNoMoney(player);
					}
				}
				else if (slot == 25 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepgangchat")))
				{
					if (DataManager.getInstance().getLocal(player).getChat(DataManager.getInstance().getLocal(player).getGang()))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Twoj gang posiada juz odblokowany czat prywatny!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
					}
					else
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 500)
						{
							DataManager.getInstance().getLocal(player).setChat(DataManager.getInstance().getLocal(player).getGang(), true);
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 500);
							ScoreboardLib.getInstance().reloadContents(player);
							InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP GANGU"), "sklep_gang");
							ChatLib.sendGangAllChatMessage(DataManager.getInstance().getLocal(player).getGang(), "#ffc936Gracz " + player.getName() + " zakupil prywatny czat dla gangu [#80ff1f!wiadomosc#ffc936] #8c8c8c(500⛃)#ffc936!");
						}
						else
							castByNoMoney(player);
					}
				}
				else if (slot == 40)
					InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP"), "sklep");
		}
		else if (checkName(event, "&c&lSKLEP Z DODATKAMI"))
		{
			event.setCancelled(true);

			if (event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT)
				if (slot == 10 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepkolor1")))
					if (DataManager.getInstance().getLocal(player).getChatColor().equalsIgnoreCase("red"))
						castByNowActive(player);
					else
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 400)
						{
							DataManager.getInstance().getLocal(player).setChatColor("red");
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 400);
							ScoreboardLib.getInstance().reloadContentsGlobal();
							InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z DODATKAMI"), "sklep_dodatki");
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles nowy kolor swojego nicku [#fc7474█#80ff1f] #8c8c8c(400⛃)#80ff1f!");
						}
						else
							castByNoMoney(player);
					}
				else if (slot == 11 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepkolor2")))
					if (DataManager.getInstance().getLocal(player).getChatColor().equalsIgnoreCase("blue"))
						castByNowActive(player);
					else 
						{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 400) {
							DataManager.getInstance().getLocal(player).setChatColor("blue");
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 400);
							ScoreboardLib.getInstance().reloadContentsGlobal();
							InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z DODATKAMI"), "sklep_dodatki");
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles nowy kolor swojego nicku [#3075ff█#80ff1f] #8c8c8c(400⛃)#80ff1f!");
						} else
							castByNoMoney(player);
					}
				else if (slot == 12 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepkolor3")))
					if (DataManager.getInstance().getLocal(player).getChatColor().equalsIgnoreCase("green"))
						castByNowActive(player);
					else
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 400) {
							DataManager.getInstance().getLocal(player).setChatColor("green");
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 400);
							ScoreboardLib.getInstance().reloadContentsGlobal();
							InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z DODATKAMI"), "sklep_dodatki");
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles nowy kolor swojego nicku [#02d645█#80ff1f] #8c8c8c(400⛃)#80ff1f!");
						} else
							castByNoMoney(player);
					}
				else if (slot == 19 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepkolor4")))
					if (DataManager.getInstance().getLocal(player).getChatColor().equalsIgnoreCase("yellow"))
						castByNowActive(player);
					else
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 400) {
							DataManager.getInstance().getLocal(player).setChatColor("yellow");
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 400);
							ScoreboardLib.getInstance().reloadContentsGlobal();
							InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z DODATKAMI"), "sklep_dodatki");
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles nowy kolor swojego nicku [#fcff33█#80ff1f] #8c8c8c(400⛃)#80ff1f!");
						} else
							castByNoMoney(player);
					}
				else if (slot == 20 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepkolor5")))
					if (DataManager.getInstance().getLocal(player).getChatColor().equalsIgnoreCase("white"))
						castByNowActive(player);
					else
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 400) {
							DataManager.getInstance().getLocal(player).setChatColor("white");
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 400);
							ScoreboardLib.getInstance().reloadContentsGlobal();
							InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z DODATKAMI"), "sklep_dodatki");
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles nowy kolor swojego nicku [#ffffff█#80ff1f] #8c8c8c(400⛃)#80ff1f!");
						} else
							castByNoMoney(player);
					}
				else if (slot == 21 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepkolor6")))
					if (DataManager.getInstance().getLocal(player).getChatColor().equalsIgnoreCase("gray"))
						castByNowActive(player);
					else
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 400) {
							DataManager.getInstance().getLocal(player).setChatColor("gray");
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 400);
							ScoreboardLib.getInstance().reloadContentsGlobal();
							InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z DODATKAMI"), "sklep_dodatki");
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles nowy kolor swojego nicku [#242424█#80ff1f] #8c8c8c(400⛃)#80ff1f!");
						} else
							castByNoMoney(player);
					}
				else if (slot == 28 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepkolor7")))
					if (DataManager.getInstance().getLocal(player).getChatColor().equalsIgnoreCase("orange"))
						castByNowActive(player);
					else
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 400) {
							DataManager.getInstance().getLocal(player).setChatColor("orange");
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 400);
							ScoreboardLib.getInstance().reloadContentsGlobal();
							InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z DODATKAMI"), "sklep_dodatki");
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles nowy kolor swojego nicku [#ffb338█#80ff1f] #8c8c8c(400⛃)#80ff1f!");
						} else
							castByNoMoney(player);
					}
				else if (slot == 29 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepkolor8")))
					if (DataManager.getInstance().getLocal(player).getChatColor().equalsIgnoreCase("pink"))
						castByNowActive(player);
					else
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 400) {
							DataManager.getInstance().getLocal(player).setChatColor("pink");
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 400);
							ScoreboardLib.getInstance().reloadContentsGlobal();
							InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z DODATKAMI"), "sklep_dodatki");
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles nowy kolor swojego nicku [#ff9ee7█#80ff1f] #8c8c8c(400⛃)#80ff1f!");
						} else
							castByNoMoney(player);
					}
				else if (slot == 14 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepschowek")))
					InventoryLib.createNewInventory(player, 27, ChatColor.translateAlternateColorCodes('&', "&c&lULEPSZENIE SCHOWKU"), "schowek");
				else if (slot == 23 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepvoucher")))
				{
					if (!(TimeLib.getDifferenceInSeconds(DataManager.getInstance().getLocal(player).getMute(player.getName())) < DataManager.getInstance().getLocal(player).getMuteLength(player.getName())))
					{
						player.closeInventory();
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Nie jestes nawet wyciszony, to tak jakbys kupil szampon do wlosow bedac lysym!");
						VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
					}
					else
						{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 300)
						{
							DataManager.getInstance().getLocal(player).setMuteLength(0);
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 300);
							ScoreboardLib.getInstance().reloadContents(player);
							InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP Z DODATKAMI"), "sklep_dodatki");
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles voucher na odciszenie #8c8c8c(300⛃)#80ff1f!");
						}
						else
							castByNoMoney(player);
					}
				}
				else if (slot == 40)
					InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP"), "sklep");
		}
		else if (checkName(event, "&c&lSKLEP Z EFEKTAMI"))
		{
			event.setCancelled(true);

			if (event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT)
				if (slot == 40)
					InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lSKLEP"), "sklep");


			if (event.getSlot() == 10 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepspeed"))) {
				if (event.getClick() == ClickType.LEFT) {
					if (DataManager.getInstance().getLocal(player).getMoney() >= 900) {
						DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 900);
						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 60 * 3, 1));
						ScoreboardLib.getInstance().reloadContents(player);
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury szybkosci 2 na okres 3 minut #8c8c8c(900⛃)#80ff1f!");
					} else
						castByNoMoney(player);
				} else if (event.getClick() == ClickType.SHIFT_LEFT) {
					if (DataManager.getInstance().getLocal(player).getMoney() >= 1500) {
						DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 1500);
						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 60 * 8, 1));
						ScoreboardLib.getInstance().reloadContents(player);
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury szybkosci 2 na okres 8 minut #8c8c8c(1500⛃)#80ff1f!");
					} else
						castByNoMoney(player);
				} else if (event.getClick() == ClickType.MIDDLE) {
					if (DataManager.getInstance().getLocal(player).getMoney() >= 4000) {
						DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 4000);
						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 60 * 30, 1));
						ScoreboardLib.getInstance().reloadContents(player);
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury szybkosci 2 na okres 30 minut #8c8c8c(4000⛃)#80ff1f!");
					} else
						castByNoMoney(player);
				}
			} else if (event.getSlot() == 11 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepjumpboost"))) {
				if (event.getClick() == ClickType.LEFT) {
					if (DataManager.getInstance().getLocal(player).getMoney() >= 900) {
						DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 900);
						player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 60 * 3, 3));
						ScoreboardLib.getInstance().reloadContents(player);
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury zwiekszonego skoku 4 na okres 3 minut #8c8c8c(900⛃)#80ff1f!");
					} else
						castByNoMoney(player);
				} else if (event.getClick() == ClickType.SHIFT_LEFT) {
					if (DataManager.getInstance().getLocal(player).getMoney() >= 1500) {
						DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 1500);
						player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 60 * 8, 3));
						ScoreboardLib.getInstance().reloadContents(player);
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury zwiekszonego skoku 4 na okres 8 minut #8c8c8c(1500⛃)#80ff1f!");
					} else
						castByNoMoney(player);
				} else if (event.getClick() == ClickType.MIDDLE) {
					if (DataManager.getInstance().getLocal(player).getMoney() >= 4000) {
						DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 4000);
						player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 60 * 30, 3));
						ScoreboardLib.getInstance().reloadContents(player);
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury zwiekszonego skoku 4 na okres 30 minut #8c8c8c(4000⛃)#80ff1f!");
					} else
						castByNoMoney(player);
				}
			} else if (event.getSlot() == 12 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepresistance"))) {
				if (event.getClick() == ClickType.LEFT) {
					if (DataManager.getInstance().getLocal(player).getMoney() >= 900) {
						DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 900);
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 60 * 3, 0));
						ScoreboardLib.getInstance().reloadContents(player);
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury odpornosci na okres 3 minut #8c8c8c(900⛃)#80ff1f!");
					} else
						castByNoMoney(player);
				} else if (event.getClick() == ClickType.SHIFT_LEFT) {
					if (DataManager.getInstance().getLocal(player).getMoney() >= 1500) {
						DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 1500);
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 60 * 8, 0));
						ScoreboardLib.getInstance().reloadContents(player);
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury odpornosci na okres 8 minut #8c8c8c(1500⛃)#80ff1f!");
					} else
						castByNoMoney(player);
				} else if (event.getClick() == ClickType.MIDDLE) {
					if (DataManager.getInstance().getLocal(player).getMoney() >= 4000) {
						DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 4000);
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 60 * 30, 0));
						ScoreboardLib.getInstance().reloadContents(player);
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury odpornosci na okres 30 minut #8c8c8c(4000⛃)#80ff1f!");
					} else
						castByNoMoney(player);
				}
			} else if (event.getSlot() == 13 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepstrength"))) {
				if (event.getClick() == ClickType.LEFT) {
					if (DataManager.getInstance().getLocal(player).getMoney() >= 900) {
						DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 900);
						player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 60 * 3, 0));
						ScoreboardLib.getInstance().reloadContents(player);
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury sily na okres 3 minut #8c8c8c(900⛃)#80ff1f!");
					} else
						castByNoMoney(player);
				} else if (event.getClick() == ClickType.SHIFT_LEFT) {
					if (DataManager.getInstance().getLocal(player).getMoney() >= 1500) {
						DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 1500);
						player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 60 * 8, 0));
						ScoreboardLib.getInstance().reloadContents(player);
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury sily na okres 8 minut #8c8c8c(1500⛃)#80ff1f!");
					} else
						castByNoMoney(player);
				} else if (event.getClick() == ClickType.MIDDLE) {
					if (DataManager.getInstance().getLocal(player).getMoney() >= 4000) {
						DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 4000);
						player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 60 * 30, 0));
						ScoreboardLib.getInstance().reloadContents(player);
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury sily na okres 30 minut #8c8c8c(4000⛃)#80ff1f!");
					} else
						castByNoMoney(player);
				}
			} else if (event.getSlot() == 14 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklephaste"))) {
				if (event.getClick() == ClickType.LEFT) {
					if (DataManager.getInstance().getLocal(player).getMoney() >= 900) {
						DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 900);
						player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 60 * 3, 0));
						ScoreboardLib.getInstance().reloadContents(player);
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury szybkosci kopania na okres 3 minut #8c8c8c(900⛃)#80ff1f!");
					} else
						castByNoMoney(player);
				} else if (event.getClick() == ClickType.SHIFT_LEFT) {
					if (DataManager.getInstance().getLocal(player).getMoney() >= 1500) {
						DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 1500);
						player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 60 * 8, 0));
						ScoreboardLib.getInstance().reloadContents(player);
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury szybkosci kopania na okres 8 minut #8c8c8c(1500⛃)#80ff1f!");
					} else
						castByNoMoney(player);
				} else if (event.getClick() == ClickType.MIDDLE) {
					if (DataManager.getInstance().getLocal(player).getMoney() >= 4000) {
						DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 4000);
						player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 60 * 30, 0));
						ScoreboardLib.getInstance().reloadContents(player);
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury szybkosci kopania na okres 30 minut #8c8c8c(4000⛃)#80ff1f!");
					} else
						castByNoMoney(player);
				}
			}
			else if (event.getSlot() == 15 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepfireresistance")))
				{
					if (event.getClick() == ClickType.LEFT)
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 900)
						{
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 900);
							player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20*60 * 3, 1));
							ScoreboardLib.getInstance().reloadContents(player);
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury odpornosci na ogien 2 na okres 3 minut #8c8c8c(900⛃)#80ff1f!");
						}
						else
							castByNoMoney(player);
					}
					else if (event.getClick() == ClickType.SHIFT_LEFT)
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 1500)
						{
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 1500);
							player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20*60 * 8, 1));
							ScoreboardLib.getInstance().reloadContents(player);
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury odpornosci na ogien 2 na okres 8 minut #8c8c8c(1500⛃)#80ff1f!");
						}
						else
							castByNoMoney(player);
					}
					else if (event.getClick() == ClickType.MIDDLE)
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 4000)
						{
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 4000);
							player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20*60 * 30, 1));
							ScoreboardLib.getInstance().reloadContents(player);
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury odpornosci na ogien 2 na okres 30 minut #8c8c8c(4000⛃)#80ff1f!");
						}
						else
							castByNoMoney(player);
					}
				}
				else if (event.getSlot() == 16 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepregeneration")))
				{
					if (event.getClick() == ClickType.LEFT)
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 900)
						{
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 900);
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*60 * 3, 0));
							ScoreboardLib.getInstance().reloadContents(player);
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury regeneracji na okres 3 minut #8c8c8c(900⛃)#80ff1f!");
						}
						else
							castByNoMoney(player);
					}
					else if (event.getClick() == ClickType.SHIFT_LEFT)
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 1500)
						{
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 1500);
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*60 * 8, 0));
							ScoreboardLib.getInstance().reloadContents(player);
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury regeneracji na okres 8 minut #8c8c8c(1500⛃)#80ff1f!");
						}
						else
							castByNoMoney(player);
					}
					else if (event.getClick() == ClickType.MIDDLE)
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 4000)
						{
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 4000);
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*60 * 30, 0));
							ScoreboardLib.getInstance().reloadContents(player);
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury regeneracji na okres 30 minut #8c8c8c(4000⛃)#80ff1f!");
						}
						else
							castByNoMoney(player);
					}
				}
				else if (event.getSlot() == 21 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepinvis")))
				{
					if (event.getClick() == ClickType.LEFT)
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 900)
						{
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 900);
							player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20*60 * 3, 1));
							ScoreboardLib.getInstance().reloadContents(player);
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury niewidzialnosci 2 na okres 3 minut #8c8c8c(900⛃)#80ff1f!");
						}
						else
							castByNoMoney(player);
					}
					else if (event.getClick() == ClickType.SHIFT_LEFT)
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 1500)
						{
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 1500);
							player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20*60 * 8, 1));
							ScoreboardLib.getInstance().reloadContents(player);
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury niewidzialnosci 2 na okres 8 minut #8c8c8c(1500⛃)#80ff1f!");
						}
						else
							castByNoMoney(player);
					}
					else if (event.getClick() == ClickType.MIDDLE)
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 4000)
						{
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 4000);
							player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20*60 * 30, 1));
							ScoreboardLib.getInstance().reloadContents(player);
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury niewidzialnosci 2 na okres 30 minut #8c8c8c(4000⛃)#80ff1f!");
						}
						else
							castByNoMoney(player);
					}
				}
				else if (event.getSlot() == 22 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepwaterbreathing")))
				{
					if (event.getClick() == ClickType.LEFT)
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 900)
						{
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 900);
							player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 20*60 * 3, 0));
							ScoreboardLib.getInstance().reloadContents(player);
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury oddychania pod woda na okres 3 minut #8c8c8c(900⛃)#80ff1f!");
						}
						else
							castByNoMoney(player);
					}
					else if (event.getClick() == ClickType.SHIFT_LEFT)
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 1500)
						{
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 1500);
							player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 20*60 * 8, 0));
							ScoreboardLib.getInstance().reloadContents(player);
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury oddychania pod woda na okres 8 minut #8c8c8c(1500⛃)#80ff1f!");
						}
						else
							castByNoMoney(player);
					}
					else if (event.getClick() == ClickType.MIDDLE)
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 4000)
						{
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 4000);
							player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 20*60 * 30, 0));
							ScoreboardLib.getInstance().reloadContents(player);
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury oddychania pod woda na okres 30 minut #8c8c8c(4000⛃)#80ff1f!");
						}
						else
							castByNoMoney(player);
					}
				}
				else if (event.getSlot() == 23 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "sklepnightvision")))
				{
					if (event.getClick() == ClickType.LEFT)
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 900)
						{
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 900);
							player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20*60 * 3, 1));
							ScoreboardLib.getInstance().reloadContents(player);
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury noktowizji 2 na okres 3 minut #8c8c8c(900⛃)#80ff1f!");
						}
						else
							castByNoMoney(player);
					}
					else if (event.getClick() == ClickType.SHIFT_LEFT)
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 1500)
						{
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 1500);
							player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20*60 * 8, 1));
							ScoreboardLib.getInstance().reloadContents(player);
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury noktowizji 2 na okres 8 minut #8c8c8c(1500⛃)#80ff1f!");
						}
						else
							castByNoMoney(player);
					}
					else if (event.getClick() == ClickType.MIDDLE)
					{
						if (DataManager.getInstance().getLocal(player).getMoney() >= 4000)
						{
							DataManager.getInstance().getLocal(player).setMoney(DataManager.getInstance().getLocal(player).getMoney() - 4000);
							player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20*60 * 30, 1));
							ScoreboardLib.getInstance().reloadContents(player);
							ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles efekt mikstury noktowizji 2 na okres 30 minut #8c8c8c(4000⛃)#80ff1f!");
						}
						else
							castByNoMoney(player);
					}
				}
		}
		else if (checkName(event, "&c&lCENTRUM POMOCY SERWERA"))
			event.setCancelled(true);
		else if (checkName(event, "&c&lSTATYW ALCHEMICZNY &0&1"))
		{
			event.setCancelled(true);

			if (!event.getClick().isLeftClick()) return;

			if (slot == 11)
				InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSTATYW ALCHEMICZNY &0&2"), "drug_table_metyloamina");
			else if (slot == 12)
				InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSTATYW ALCHEMICZNY &0&3"), "drug_table_metylenoamina");
			else if (slot == 13)
				InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSTATYW ALCHEMICZNY &0&4"), "drug_table_fenyloamina");
			else if (slot == 14)
				InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSTATYW ALCHEMICZNY &0&5"), "drug_table_fluoroamina");
			else if (slot == 15)
				InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSTATYW ALCHEMICZNY &0&6"), "drug_table_dimetoamina");
			else if (slot == 31)
				InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSTATYW ALCHEMICZNY &1&1"), "drug_table_opium");
			else if (slot == 49)
				player.closeInventory();
		}
		else if (checkName(event, "&c&lSTATYW ALCHEMICZNY &0&2"))
		{
			event.setCancelled(true);

			if (!event.getClick().isLeftClick()) return;

			if (slot == 11)
				manageDrug(player, Chemistries.metamfetamina);
			else if (slot == 12)
				manageDrug(player, Chemistries.metafedron);
			else if (slot == 13)
				manageDrug(player, Chemistries.metylon);
			else if (slot == 14)
				manageDrug(player, Chemistries.metylometkatynon);
			else if (slot == 49)
				InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSTATYW ALCHEMICZNY &0&1"), "drug_table_amina");
		}
		else if (checkName(event, "&c&lSTATYW ALCHEMICZNY &0&3"))
		{
			event.setCancelled(true);

			if (!event.getClick().isLeftClick()) return;

			if (slot == 11)
				manageDrug(player, Chemistries.MDA);
			else if (slot == 12)
				manageDrug(player, Chemistries.MDMA);
			else if (slot == 13)
				manageDrug(player, Chemistries.MDPV);
			else if (slot == 49)
				InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSTATYW ALCHEMICZNY &0&1"), "drug_table_amina");
		}
		else if (checkName(event, "&c&lSTATYW ALCHEMICZNY &0&4"))
		{
			event.setCancelled(true);

			if (!event.getClick().isLeftClick()) return;

			if (slot == 11)
				manageDrug(player, Chemistries.amfetamina);
			else if (slot == 12)
				manageDrug(player, Chemistries.mefedron);
			else if (slot == 13)
				manageDrug(player, Chemistries.klefedron);
			else if (slot == 49)
				InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSTATYW ALCHEMICZNY &0&1"), "drug_table_amina");
		}
		else if (checkName(event, "&c&lSTATYW ALCHEMICZNY &0&5"))
		{
			event.setCancelled(true);

			if (!event.getClick().isLeftClick()) return;

			if (slot == 11)
				manageDrug(player, Chemistries.fluoroamfetamina);
			else if (slot == 12)
				manageDrug(player, Chemistries.flefedron);
			else if (slot == 49)
				InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSTATYW ALCHEMICZNY &0&1"), "drug_table_amina");
		}
		else if (checkName(event, "&c&lSTATYW ALCHEMICZNY &0&6"))
		{
			event.setCancelled(true);

			if (!event.getClick().isLeftClick()) return;

			if (slot == 11)
				manageDrug(player, Chemistries.kokaina);
			else if (slot == 12)
				manageDrug(player, Chemistries.kleksedron);
			else if (slot == 49)
				InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSTATYW ALCHEMICZNY &0&1"), "drug_table_amina");
		}
		else if (checkName(event, "&c&lSTATYW ALCHEMICZNY &1&1"))
		{
			event.setCancelled(true);

			if (!event.getClick().isLeftClick()) return;

			if (slot == 11)
				manageDrug(player, Chemistries.alprazolam);
			else if (slot == 12)
				manageDrug(player, Chemistries.metylomorfina);
			else if (slot == 13)
				manageDrug(player, Chemistries.morfina);
			else if (slot == 14)
				manageDrug(player, Chemistries.heroina);
			else if (slot == 15)
				manageDrug(player, Chemistries.fentanyl);
			else if (slot == 31)
				InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSTATYW ALCHEMICZNY &0&1"), "drug_table_amina");
			else if (slot == 49)
				player.closeInventory();
		}
		else if (checkName(event, "&c&lDOSTEPNE KOLORY"))
			event.setCancelled(true);
		else if (checkName(event, "&c&lCRAFTINGI (1/" + Craftingi.getRecipeCount() + ")"))
		{
			if (slot == 8)
			{
				if (((ArrayList<String>) FileManager.getInstance().recipes().get("recipes.enabled")).contains("1"))
				{
					ArrayList<String> schematic = ((ArrayList<String>) FileManager.getInstance().recipes().get("recipes.enabled"));
					schematic.remove("1");
					FileManager.getInstance().recipes().set("recipes.enabled", schematic);
				}
				else
				{
					ArrayList<String> schematic = ((ArrayList<String>) FileManager.getInstance().recipes().get("recipes.enabled"));
					schematic.add("1");
					FileManager.getInstance().recipes().set("recipes.enabled", schematic);
				}

				Logger.log("Zastosowano zmiany w statusie receptur, trwa ich odswiezanie...");
				RecipeLib.getInstance().reloadRecipes();
				FileManager.getInstance().save(FileManager.FileType.RECIPES);
				InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lCRAFTINGI (" + "1" + "/" + Craftingi.getRecipeCount() + ")"), "craftingi" + "1");
			}

			if (slot == 44)
				InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lCRAFTINGI (2/" + Craftingi.getRecipeCount() + ")"), "craftingi2");

			event.setCancelled(true);
		}
		else if (checkName(event, "&c&lCRAFTINGI (" + Craftingi.getRecipeCount() + "/" + Craftingi.getRecipeCount() + ")"))
		{
			if (slot == 8)
			{
				if (((ArrayList<String>) FileManager.getInstance().recipes().get("recipes.enabled")).contains(String.valueOf(Craftingi.getRecipeCount())))
				{
					ArrayList<String> schematic = ((ArrayList<String>) FileManager.getInstance().recipes().get("recipes.enabled"));
					schematic.remove(String.valueOf(Craftingi.getRecipeCount()));
					FileManager.getInstance().recipes().set("recipes.enabled", schematic);
				}
				else
				{
					ArrayList<String> schematic = ((ArrayList<String>) FileManager.getInstance().recipes().get("recipes.enabled"));
					schematic.add(String.valueOf(Craftingi.getRecipeCount()));
					FileManager.getInstance().recipes().set("recipes.enabled", schematic);
				}

				Logger.log("Zastosowano zmiany w statusie receptur, trwa ich odswiezanie...");
				RecipeLib.getInstance().reloadRecipes();
				FileManager.getInstance().save(FileManager.FileType.RECIPES);
				InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lCRAFTINGI (" + Craftingi.getRecipeCount() + "/" + Craftingi.getRecipeCount() + ")"), "craftingi" + Craftingi.getRecipeCount());
			}

			if (slot == 36)
				InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lCRAFTINGI (" + (Craftingi.getRecipeCount() - 1) + "/" + Craftingi.getRecipeCount() + ")"), "craftingi" + (Craftingi.getRecipeCount() - 1));

			event.setCancelled(true);
		}
		else if (checkNameContains(event, "&c&lCRAFTINGI"))
		{
			for (int page : Craftingi.pages)
				if (checkName(event, "&c&lCRAFTINGI (" + page + "/" + Craftingi.getRecipeCount() + ")"))
				{
					if (slot == 8)
					{
						if (((ArrayList<String>) FileManager.getInstance().recipes().get("recipes.enabled")).contains(String.valueOf(page)))
						{
							ArrayList<String> schematic = ((ArrayList<String>) FileManager.getInstance().recipes().get("recipes.enabled"));
							schematic.remove(String.valueOf(page));
							FileManager.getInstance().recipes().set("recipes.enabled", schematic);
						}
						else
						{
							ArrayList<String> schematic = ((ArrayList<String>) FileManager.getInstance().recipes().get("recipes.enabled"));
							schematic.add(String.valueOf(page));
							FileManager.getInstance().recipes().set("recipes.enabled", schematic);
						}

						Logger.log("Zastosowano zmiany w statusie receptur, trwa ich odswiezanie...");
						RecipeLib.getInstance().reloadRecipes();
						FileManager.getInstance().save(FileManager.FileType.RECIPES);
						InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lCRAFTINGI (" + page + "/" + Craftingi.getRecipeCount() + ")"), "craftingi" + page);
					}

					if (slot == 36)
						InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lCRAFTINGI (" + (page - 1) + "/" + Craftingi.getRecipeCount() + ")"), "craftingi" + (page - 1));
					if (slot == 44)
						InventoryLib.createNewInventory(player, 45, ChatColor.translateAlternateColorCodes('&', "&c&lCRAFTINGI (" + (page + 1) + "/" + Craftingi.getRecipeCount() + ")"), "craftingi" + (page + 1));

					event.setCancelled(true);
				}
		}
		else if (checkName(event, "&c&lQUESTY"))
		{
			event.setCancelled(true);
			player.closeInventory();
			ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Czy tam pisze zeby cos kliknac? Jasny chuj!");
		}
		else if (checkName(event, "&c&lPOSTAC"))
		{
			if (slot == 38 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "postacupgrade1")))
			{
				if (event.getClick() == ClickType.LEFT)
				{
					if (DataManager.getInstance().getLocal(player).getUpgradeLevel(player.getName(), "vitality") >= 5)
					{
						castByMaxUpgrade(player);
						return;
					}

					if (DataManager.getInstance().getLocal(player).getSP() >= 1)
					{
						DataManager.getInstance().getLocal(player).addUpgradeLevel(player.getName(), "vitality");
						DataManager.getInstance().getLocal(player).setSP(DataManager.getInstance().getLocal(player).getSP() - 1);
						ScoreboardLib.getInstance().reloadContents(player);
						player.setMaxHealth(20.0 + (4.0D * DataManager.getInstance().getLocal(player).getUpgradeLevel(player.getName(), "vitality")));
						player.setHealth(player.getMaxHealth());
						InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lPOSTAC"), "postac");
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles ulepszenie postaci #8c8c8c(1☀)#80ff1f!");
					}
					else
						castByNoSP(player);
				}
			}
			else if (slot == 39 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "postacupgrade2")))
			{
				if (event.getClick() == ClickType.LEFT)
				{
					if (DataManager.getInstance().getLocal(player).getUpgradeLevel(player.getName(), "luck") >= 5)
					{
						castByMaxUpgrade(player);
						return;
					}

					if (DataManager.getInstance().getLocal(player).getSP() >= 1)
					{
						DataManager.getInstance().getLocal(player).addUpgradeLevel(player.getName(), "luck");
						DataManager.getInstance().getLocal(player).setSP(DataManager.getInstance().getLocal(player).getSP() - 1);
						ScoreboardLib.getInstance().reloadContents(player);
						InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lPOSTAC"), "postac");
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles ulepszenie postaci #8c8c8c(1☀)#80ff1f!");
					}
					else
						castByNoSP(player);
				}
			}
			else if (slot == 40 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "postacupgrade3")))
			{
				if (event.getClick() == ClickType.LEFT)
				{
					if (DataManager.getInstance().getLocal(player).getUpgradeLevel(player.getName(), "loot") >= 5)
					{
						castByMaxUpgrade(player);
						return;
					}

					if (DataManager.getInstance().getLocal(player).getSP() >= 1)
					{
						DataManager.getInstance().getLocal(player).addUpgradeLevel(player.getName(), "loot");
						DataManager.getInstance().getLocal(player).setSP(DataManager.getInstance().getLocal(player).getSP() - 1);
						ScoreboardLib.getInstance().reloadContents(player);
						InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lPOSTAC"), "postac");
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles ulepszenie postaci #8c8c8c(1☀)#80ff1f!");
					}
					else
						castByNoSP(player);
				}
			}
			else if (slot == 41 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "postacupgrade4")))
			{
				if (event.getClick() == ClickType.LEFT)
				{
					if (DataManager.getInstance().getLocal(player).getUpgradeLevel(player.getName(), "honorable") >= 5)
					{
						castByMaxUpgrade(player);
						return;
					}

					if (DataManager.getInstance().getLocal(player).getSP() >= 1)
					{
						DataManager.getInstance().getLocal(player).addUpgradeLevel(player.getName(), "honorable");
						DataManager.getInstance().getLocal(player).setSP(DataManager.getInstance().getLocal(player).getSP() - 1);
						ScoreboardLib.getInstance().reloadContents(player);
						InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lPOSTAC"), "postac");
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles ulepszenie postaci #8c8c8c(1☀)#80ff1f!");
					}
					else
						castByNoSP(player);
				}
			}
			else if (slot == 42 && Objects.equals(event.getCurrentItem(), InventoryLib.getItem(player, "postacupgrade5")))
			{
				if (event.getClick() == ClickType.LEFT)
				{
					if (DataManager.getInstance().getLocal(player).getUpgradeLevel(player.getName(), "thiefy") >= 5)
					{
						castByMaxUpgrade(player);
						return;
					}

					if (DataManager.getInstance().getLocal(player).getSP() >= 1)
					{
						DataManager.getInstance().getLocal(player).addUpgradeLevel(player.getName(), "thiefy");
						DataManager.getInstance().getLocal(player).setSP(DataManager.getInstance().getLocal(player).getSP() - 1);
						ScoreboardLib.getInstance().reloadContents(player);
						InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lPOSTAC"), "postac");
						ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie zakupiles ulepszenie postaci #8c8c8c(1☀)#80ff1f!");
					}
					else
						castByNoSP(player);
				}
			}

			event.setCancelled(true);
		}
		else if (event.getInventory() instanceof AnvilInventory)
		{
			TaskLib.getInstance().runSyncLater(() -> {
				if (event.getInventory().getItem(0) != null && event.getInventory().getItem(1) != null)
				{
					if (EnchantmentHandler.getInstance().isEnchantmentBook(event.getInventory().getItem(1)))
					{
						ItemStack itemStack = EnchantmentHandler.getInstance().addEnchantment(event.getInventory().getItem(0).clone(), EnchantmentHandler.getInstance().getEnchantmentFromBook(event.getInventory().getItem(1)));
						event.getInventory().setItem(2, itemStack);
					}

					if (event.getInventory().getItem(2) != null)
					{
						ItemStack itemStack1 = event.getInventory().getItem(0);
						ItemStack itemStack2 = event.getInventory().getItem(1);

						Set<EnchantmentWrapper> enchantments = new HashSet<>();

						for (EnchantmentWrapper enchantmentWrapper : EnchantmentHandler.getInstance().getEnchantments(itemStack1))
							enchantments.add(enchantmentWrapper);

						for (EnchantmentWrapper enchantmentWrapper : EnchantmentHandler.getInstance().getEnchantments(itemStack2))
							enchantments.add(enchantmentWrapper);

						EnchantmentHandler.getInstance().removeEnchantments(event.getInventory().getItem(2));

						for (EnchantmentWrapper enchantmentWrapper : enchantments)
							EnchantmentHandler.getInstance().addEnchantment(event.getInventory().getItem(2), enchantmentWrapper);
					}
				}
			}, 1);
		}
		else if (event.getInventory() instanceof GrindstoneInventory)
		{
			TaskLib.getInstance().runSyncLater(() -> {
				if (event.getInventory().getItem(0) != null && event.getInventory().getItem(1) == null)
				{
					if (event.getInventory().getItem(0).isSimilar(new ItemLib().get("magic_rod"))) { player.closeInventory(); return; }

					ItemStack itemStack = event.getInventory().getItem(0).clone();
					EnchantmentHandler.getInstance().removeEnchantments(itemStack);
					itemStack.getEnchantments().keySet().forEach(itemStack::removeEnchantment);
					event.getInventory().setItem(2, itemStack);
				}
				if (event.getInventory().getItem(1) != null && event.getInventory().getItem(0) == null)
				{
					if (event.getInventory().getItem(1).isSimilar(new ItemLib().get("magic_rod"))) { player.closeInventory(); return; }

					ItemStack itemStack = event.getInventory().getItem(1).clone();
					EnchantmentHandler.getInstance().removeEnchantments(itemStack);
					itemStack.getEnchantments().keySet().forEach(itemStack::removeEnchantment);
					event.getInventory().setItem(2, itemStack);
				}
			}, 1);
		}
	}

	@EventHandler
	public void onEvent(InventoryCloseEvent event)
	{
		if (checkName(event, "&c&lSCHOWEK"))
			schedule((Player) event.getPlayer(), event.getInventory());
	}
	
	public void schedule(final Player player, org.bukkit.inventory.Inventory inventory)
	{
		ItemStack[] items = inventory.getContents();
		List<ItemStack> contents = new ArrayList<>();

		Collections.addAll(contents, items);
		DataManager.getInstance().getLocal(player).setSchowek(contents);
	}

	private boolean checkNameContains(InventoryClickEvent event, String name) { return event.getView().getTitle().contains(ChatColor.translateAlternateColorCodes('&', name)); }
	private boolean checkName(InventoryClickEvent event, String name) { return event.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', name)); }
	private boolean checkName(InventoryCloseEvent event, String name) { return event.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', name)); }

	private void castByNoMoney(Player player)
	{
		player.closeInventory();
		ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Nie posiadasz wystarczajaco monet, aby zakupic ten przedmiot!");
		VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
	}

	private void castByNoSP(Player player)
	{
		player.closeInventory();
		ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Nie posiadasz wystarczajaco punktow umiejetnosci, aby zakupic ten przedmiot!");
		VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
	}

	private void castByFullInv(Player player)
	{
		player.closeInventory();
		ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Gdzie ty masz kieszenie! Najpierw zrob wolne miejsce w ekwipunku, aby cos kupic!");
		VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
	}

	private void castByNowActive(Player player)
	{
		player.closeInventory();
		ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Posiadasz juz ustawiony ten kolor jako swoj aktualny!");
		VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
	}

	private void castByMaxUpgrade(Player player)
	{
		player.closeInventory();
		ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Posiadasz juz maksymalny poziom tego ulepszenia!");
		VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
	}

	private void castByNotEnoughItem(Player player)
	{
		player.closeInventory();
		ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474No chyba nie, probujesz sprzedac przedmiot ktorego nawet nie posiadasz?!");
		VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
	}

	private void castByNotEnoughISpace(Player player)
	{
		player.closeInventory();
		ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Powieksz swoje kieszenie bo chyba masz za malo miejsca!");
		VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
	}

	private void castByNoPermission(Player player)
	{
		player.closeInventory();
		ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474A ty slepy czy co! Pisze ze nie ma to nie ma kurwa!");
		VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
	}

	private void castByNoQuests(Player player)
	{
		player.closeInventory();
		ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#fc7474Czy Ty smiertelniku chciales odebrac cos za nic? Wypierdalaj robic questy!");
		VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 20);
	}

	public static boolean consumeItem(Player player, int count, Material mat)
	{
		Map<Integer, ? extends ItemStack> ammo = player.getInventory().all(mat);
		int found = 0;

		for (ItemStack stack : ammo.values())
			found += stack.getAmount();

		if (count > found)
			return false;

		for (Integer index : ammo.keySet())
		{
			ItemStack stack = ammo.get(index);

			int removed = Math.min(count, stack.getAmount());
			count -= removed;

			if (stack.getAmount() == removed)
				player.getInventory().setItem(index, null);
			else
				stack.setAmount(stack.getAmount() - removed);

			if (count <= 0)
				break;
		}

		player.updateInventory();
		return true;
	}

	public static void removeItem(Player player, ItemStack itemStack, int amount)
	{
		if (!(player.getInventory().getItemInMainHand().getAmount() < amount))
			player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);

		player.updateInventory();
	}

	private void manageItem(InventoryClickEvent event, Material item, int amount, int buyCost, int sellCost)
	{
		Player player = (Player) event.getWhoClicked();

		if (event.getClick() == ClickType.LEFT)
		{
			if (buyCost == -1)
			{
				castByNoPermission(player);
				return;
			}

			if (DataManager.getInstance().getLocal(player).getMoney() < buyCost)
			{
				castByNoMoney(player);
				return;
			}

			if (InventoryLib.isFullInventory(player))
				if (!InventoryLib.canStackItem(player, item, amount))
				{
					castByNotEnoughISpace(player);
					return;
				}

			DataManager.getInstance().getLocal(player).removeMoney(buyCost);
			ScoreboardLib.getInstance().reloadContents(player);
			player.getInventory().addItem(new ItemStack(item, amount));
			ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie kupiles #ffc936" + item + "x" + amount + " #8c8c8c(" + buyCost + "⛃)#80ff1f!");

			Logger.getInstance().store(Logger.StoreType.SHOP, player.getName() + " kupil " + item + "x" + amount + " za " + buyCost + " monet");
		}
		else if (event.getClick() == ClickType.SHIFT_LEFT)
		{
			if (buyCost == -1)
			{
				castByNoPermission(player);
				return;
			}

			if (DataManager.getInstance().getLocal(player).getMoney() < (item.getMaxStackSize() / amount) * buyCost)
			{
				castByNoMoney(player);
				return;
			}

			if (InventoryLib.isFullInventory(player))
			{
				castByNotEnoughISpace(player);
				return;
			}

			DataManager.getInstance().getLocal(player).removeMoney((item.getMaxStackSize() / amount) * buyCost);
			ScoreboardLib.getInstance().reloadContents(player);
			player.getInventory().addItem(new ItemStack(item, item.getMaxStackSize()));
			ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie kupiles #ffc936" + item + "x" + item.getMaxStackSize() + " #8c8c8c(" + ((item.getMaxStackSize() / amount) * buyCost) + "⛃)#80ff1f!");

			Logger.getInstance().store(Logger.StoreType.SHOP, player.getName() + " kupil " + item + "x" + item.getMaxStackSize() + " za " + ((item.getMaxStackSize() / amount) * buyCost) + " monet");
		}
		else if (event.getClick() == ClickType.RIGHT)
		{
			if (sellCost == -1)
			{
				castByNoPermission(player);
				return;
			}

			boolean sell = consumeItem(player, amount, item);

			if (sell)
			{
				ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie sprzedales #ffc936" + item.toString() + "x" + amount + " #8c8c8c(+" + sellCost + "⛃)#80ff1f!");
				DataManager.getInstance().getLocal(player).addMoney(sellCost);
				ScoreboardLib.getInstance().reloadContents(player);

				Logger.getInstance().store(Logger.StoreType.SHOP, player.getName() + " sprzedal " + item + "x" + amount + " za " + sellCost + " monet");
			}
			else
				castByNotEnoughItem(player);
		}
		else if (event.getClick() == ClickType.SHIFT_RIGHT)
		{
			if (sellCost == -1)
			{
				castByNoPermission(player);
				return;
			}

			boolean sell = consumeItem(player, item.getMaxStackSize(), item);

			if (sell)
			{
				ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie sprzedales #ffc936" + item + "x" + item.getMaxStackSize() + " #8c8c8c(+" + ((item.getMaxStackSize() / amount) * sellCost) + "⛃)#80ff1f!");
				DataManager.getInstance().getLocal(player).addMoney((item.getMaxStackSize() / amount) * sellCost);
				ScoreboardLib.getInstance().reloadContents(player);

				Logger.getInstance().store(Logger.StoreType.SHOP, player.getName() + " sprzedal " + item + "x" + item.getMaxStackSize() + " za " + ((item.getMaxStackSize() / amount) * sellCost) + " monet");
			}
			else
				castByNotEnoughItem(player);
		}
	}

	private void manageMultiSell(Player player)
	{
		int money = 0;
		Map<Material, Integer> inventoryItem = new HashMap<>();

		for (ItemStack item : player.getInventory().getContents())
			if (item != null && item.getType() != Material.AIR)
				if (InventoryLib.ItemShop.isShopable(item.getType()) && InventoryLib.ItemShop.isSellable(item.getType()))
				{
					if (inventoryItem.get(item.getType()) == null)
						inventoryItem.put(item.getType(), item.getAmount());
					else
						inventoryItem.put(item.getType(), inventoryItem.get(item.getType()) + item.getAmount());
				}

		for (Material material : inventoryItem.keySet())
		{
			final int loop = inventoryItem.get(material) / InventoryLib.ItemShop.getValueOf(material)[2];

			for (int x = 0; x < loop; x++)
			{
				consumeItem(player, InventoryLib.ItemShop.getValueOf(material)[2], material);
				money = money + InventoryLib.ItemShop.getValueOf(material)[1];
			}
		}

		inventoryItem.clear();
		player.updateInventory();

		if (money > 0)
			ChatManager.sendMessage(player, FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "#80ff1fPomyslnie sprzedales swoje przedmioty za pomoca multi sprzedazy #8c8c8c(+" + money + "⛃)#80ff1f!");
		else
			ChatManager.sendNotification(player, "Nie masz zadnych przedmiotow na sprzedaz!", ChatManager.NotificationType.ERROR);

		DataManager.getInstance().getLocal(player).addMoney(money);
		ScoreboardLib.getInstance().reloadContents(player);

		Logger.getInstance().store(Logger.StoreType.SHOP, player.getName() + " dokonal multi-sprzedazy");
	}

	private void manageDrug(Player player, ChemistryItem chemistryItem)
	{
		if (!(player.getLevel() >= 1))
		{
			player.closeInventory();
			ChatManager.sendNotification(player, "No i pierdlolło... musisz nazbierac wiecej XP'a kolego!", ChatManager.NotificationType.ERROR);
		}
		else
		{
			ItemStack item = ChemistryDrug.getDrug(chemistryItem);
			player.setLevel(player.getLevel() - 1);

			if (!InventoryLib.isFullInventory(player))
				player.getInventory().addItem(item);
			else
				player.getWorld().dropItemNaturally(player.getLocation(), item);
		}
	}
}