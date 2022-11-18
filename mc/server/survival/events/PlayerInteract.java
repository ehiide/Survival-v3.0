package mc.server.survival.events;

import mc.server.Logger;
import mc.server.survival.files.Main;
import mc.server.survival.libraries.*;
import mc.server.survival.libraries.chemistry.Chemistries;
import mc.server.survival.libraries.chemistry.ChemistryCore;
import mc.server.survival.libraries.chemistry.ChemistryDrug;
import mc.server.survival.libraries.chemistry.ChemistryItem;
import mc.server.survival.libraries.enchantments.EnchantmentHandler;
import mc.server.survival.libraries.enchantments.Enchantments;
import mc.server.survival.managers.FileManager;
import mc.server.survival.worlds.WorldHandler;
import mc.server.survival.worlds.aether.Aether;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Openable;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.block.data.type.Candle;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Objects;

public class PlayerInteract 
implements Listener
{
	private static HashMap<Player, ClickSequence> sequence_map = new HashMap<>();
	private static HashMap<Player, ClickSequence> lastSequence_map = new HashMap<>();
	private static PlayerInteract.ClickSequence getSequence(Player player)
	{
		return sequence_map.get(player);
	}
	private static PlayerInteract.ClickSequence getLastSequence(Player player)
	{
		return lastSequence_map.get(player);
	}

	private static HashMap<Player, Block> sitting_status = new HashMap<>();
	public static Block getSitting(Player player)
	{
		return sitting_status.get(player);
	}
	public static void setSitting(Player player, Block block)
	{
		if (block == null) sitting_status.remove(player);
		else sitting_status.put(player, block);
	}

	public static boolean isSomeoneSittingHere(Block block)
	{
		for (Player player : sitting_status.keySet())
			if (getSitting(player).equals(block))
				return true; return false;
	}

	public static Player whoIsSittingHere(Block block)
	{
		for (Player player : sitting_status.keySet())
			if (getSitting(player).equals(block))
				return player; return null;
	}

	private static void setSequence(Player player, PlayerInteract.ClickSequence sequence)
	{
		sequence_map.put(player, sequence);
	}

	private static void setLastSequence(Player player, PlayerInteract.ClickSequence lastSequence)
	{
		lastSequence_map.put(player, lastSequence);
	}

	@EventHandler
	public void onEvent(PlayerInteractEvent event)
	{
        final Player player = event.getPlayer();
        final Action action = event.getAction();

		if ((boolean) FileManager.getInstance().getConfigValue("function.aether.status"))
			if (action == Action.RIGHT_CLICK_BLOCK)
			{
				if (event.getItem() != null && event.getItem().getType() == Material.WATER_BUCKET && event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.GLOWSTONE)
					if (Aether.getInstance().getAetherPortal().firePortal(event.getClickedBlock().getLocation(), event.getBlockFace()))
						event.setCancelled(true);

				if (event.getItem() != null && event.getItem().getType() == Material.WATER_BUCKET && event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.NETHER_PORTAL)
					event.setCancelled(true);
			}

		if (action == Action.PHYSICAL && Objects.requireNonNull(event.getClickedBlock()).getType() == Material.FARMLAND)
			if ((boolean) FileManager.getInstance().getConfigValue("function.plant-protection"))
				event.setCancelled(true);

		if (action == Action.LEFT_CLICK_BLOCK)
			if (event.getClickedBlock() != null)
				if (event.getClickedBlock().getLocation().clone().add(0, -1, 0).getBlock().getType() == Material.FARMLAND)
					if (event.hasItem() && event.getItem() != null)
						if (EnchantmentHandler.getInstance().hasEnchantment(event.getItem(), Enchantments.SEEDER))
						{
							 final Material material = event.getClickedBlock().getType();

							 if (material == Material.WHEAT)
								 if (player.getInventory().contains(Material.WHEAT_SEEDS))
								 {
									 Inventory.consumeItem(player, 1, Material.WHEAT_SEEDS);
									 TaskLib.getInstance().runSyncLater(() -> event.getClickedBlock().setType(Material.WHEAT), 1);
								 }

							if (material == Material.CARROTS)
								if (player.getInventory().contains(Material.CARROT))
								{
									Inventory.consumeItem(player, 1, Material.CARROT);
									TaskLib.getInstance().runSyncLater(() -> event.getClickedBlock().setType(Material.CARROTS), 1);
								}

							if (material == Material.POTATOES)
								if (player.getInventory().contains(Material.POTATO))
								{
									Inventory.consumeItem(player, 1, Material.POTATO);
									TaskLib.getInstance().runSyncLater(() -> event.getClickedBlock().setType(Material.POTATOES), 1);
								}

							if (material == Material.BEETROOTS)
								if (player.getInventory().contains(Material.BEETROOT))
								{
									Inventory.consumeItem(player, 1, Material.BEETROOT);
									TaskLib.getInstance().runSyncLater(() -> event.getClickedBlock().setType(Material.BEETROOTS), 1);
								}

							if (material == Material.NETHER_WART)
								if (player.getInventory().contains(Material.NETHER_WART))
								{
									Inventory.consumeItem(player, 1, Material.NETHER_WART);
									TaskLib.getInstance().runSyncLater(() -> event.getClickedBlock().setType(Material.NETHER_WART), 1);
								}
						}

		if (action == Action.LEFT_CLICK_BLOCK)
			if ((boolean) FileManager.getInstance().getConfigValue("function.fire-aspect-expander"))
				if (event.getClickedBlock() != null)
				{
					final Block block = event.getClickedBlock();

					if (event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().getItemMeta().hasEnchant(Enchantment.FIRE_ASPECT))
					{
						final Material material = block.getType();

						if (material == Material.CAMPFIRE || material == Material.SOUL_CAMPFIRE)
						{
							BlockData blockData = block.getBlockData();
							Campfire campfire = (Campfire) blockData;

							campfire.setLit(true);
							block.setBlockData(campfire);

							event.setCancelled(true);
						}

						if (material == Material.TNT)
						{
							block.setType(Material.AIR);
							block.getWorld().spawnEntity(block.getLocation(), EntityType.PRIMED_TNT);
						}

						if (material.toString().contains("CANDLE"))
						{
							BlockData blockData = block.getBlockData();
							Candle candle = (Candle) blockData;

							candle.setLit(true);
							block.setBlockData(candle);

							event.setCancelled(true);
						}
					}
				}

		if (player.getInventory().getItemInMainHand().isSimilar(new ItemLib().get("magic_rod")))
			if (!player.hasCooldown(Material.STONE_HOE))
            {
                if (getSequence(player) == null)
				{
                    final ClickSequence clickSequence = new ClickSequence(ClickType.EMPTY, ClickType.EMPTY, ClickType.EMPTY);
                    setSequence(player, clickSequence);
                }

                if (action == Action.RIGHT_CLICK_AIR)
				{
					ClickSequence clickSequence = getSequence(player);
					clickSequence.addSequence(ClickType.RIGHT);
					setSequence(player, clickSequence);

				}
				else if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK)
                {
					ClickSequence clickSequence = getSequence(player);
					clickSequence.addSequence(ClickType.LEFT);
					setSequence(player, clickSequence);
				}

				if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK ||
						action == Action.RIGHT_CLICK_AIR)
				{
					if (getSequence(player).isDone())
					{
						if (getSequence(player).toString().equalsIgnoreCase("RIGHTRIGHTRIGHT"))
						{
							player.getWorld().spawnParticle(Particle.HEART, player.getLocation().add(0, 0.5, 0), 100);
							player.setHealth(Math.min(player.getHealth() + 10, player.getMaxHealth()));
							player.setFoodLevel(24);
							player.setSaturation(6);
							player.setSaturatedRegenRate(80);
						}
						else if (getSequence(player).toString().equalsIgnoreCase("LEFTLEFTLEFT"))
						{
							EntityLib.getInstance().applyEntityMovement(player, 15);
						}
						else if (getSequence(player).toString().equalsIgnoreCase("RIGHTLEFTRIGHT"))
						{
							final Entity entity = player.getWorld().spawnEntity(player.getLocation().add(0, 1, 0), EntityType.FIREBALL);
							Fireball fireball = (Fireball) entity;
							fireball.setDirection(player.getEyeLocation().getDirection());
							fireball.setShooter(player);

							new BukkitRunnable() { @Override public void run() {
								if (fireball.isDead())
									this.cancel();
								else
									fireball.getWorld().spawnParticle(Particle.FLAME, fireball.getLocation(), 4);
							} }.runTaskTimerAsynchronously(Main.getInstance(), 2, 2);


						}
						else if (getSequence(player).toString().equalsIgnoreCase("LEFTRIGHTLEFT"))
						{
							player.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation().add(0, 0.5, 0), 100);
							Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
								if (!onlinePlayer.getName().equalsIgnoreCase(player.getName()))
								{
									onlinePlayer.hidePlayer(Main.getInstance(), player);
									TaskLib.getInstance().runSyncLater(() -> onlinePlayer.showPlayer(Main.getInstance(), player), 100);
								}
							});
						}
						else
							TaskLib.getInstance().runAsyncLater(() -> VisualLib.showDelayedTitle(player, "#fc7474✖", "", 0, 20, 0), 20);

						player.playSound(player.getLocation(), Sound.BLOCK_ANCIENT_DEBRIS_FALL, 1, 7);
						player.setCooldown(Material.STONE_HOE, 180);
					}

					setLastSequence(player, getSequence(player));

					String suquenceString = getSequence(player).toString();
					suquenceString = suquenceString.replaceAll("RIGHT", " &cPPM &7+");
					suquenceString = suquenceString.replaceAll("LEFT", " &cLPM &7+");
					suquenceString = suquenceString.replaceAll("EMPTY", " &e? &7+");
					suquenceString = suquenceString.substring(1, suquenceString.length() - 4);

					VisualLib.showDelayedTitle(player, "" + suquenceString, "&7", 0, 20, 0);

					final ClickSequence clickSequence = new ClickSequence(ClickType.EMPTY, ClickType.EMPTY, ClickType.EMPTY);

					TaskLib.getInstance().runAsyncLater(() -> {
						if (getSequence(player).toString().equalsIgnoreCase(getLastSequence(player).toString()))
							setSequence(player, clickSequence);
					}, 40);
				}
            }

		if (action == Action.RIGHT_CLICK_BLOCK)
			if (player.getFallDistance() >= 2.5)
				if (event.hasItem() && event.getItem().getType() == Material.WATER_BUCKET)
				{
					if (player.isInWater()) return;

					if (player.getLocation().add(0, -0.05, 0).getBlock().getType() == Material.WATER) return;
					if (player.getLocation().add(0, -1, 0).getBlock().getType() == Material.WATER) return;
					if (player.getLocation().add(0, -2, 0).getBlock().getType() == Material.WATER) return;
					if (player.getLocation().add(0, -3, 0).getBlock().getType() == Material.WATER) return;

					if (event.getClickedBlock().getLocation().getBlockX() == player.getLocation().getBlockX())
						if (event.getClickedBlock().getLocation().getBlockZ() == player.getLocation().getBlockZ())
							QuestLib.manageQuest(player, 9);
				}

		if (action == Action.RIGHT_CLICK_BLOCK)
			if (player.getInventory().getItemInMainHand().isSimilar(new ItemLib().get("brewing_stand")))
			{
				if (event.getBlockFace() != BlockFace.UP) return;

				if (!event.getClickedBlock().getLocation().clone().add(0, 1, 0).getBlock().getType().isAir()) return;
				if (!event.getClickedBlock().getLocation().clone().add(0, 2, 0).getBlock().getType().isAir()) return;

				event.getClickedBlock().getLocation().clone().add(0, 1, 0).getBlock().setType(Material.LAVA_CAULDRON);
				event.getClickedBlock().getLocation().clone().add(0,  2, 0).getBlock().setType(Material.BREWING_STAND);
			}

		if (action == Action.RIGHT_CLICK_BLOCK)
			if (!player.isSneaking())
				if (isDoors(event.getClickedBlock().getType()))
					if ((boolean) FileManager.getInstance().getConfigValue("function.double-door-opener"))
					{
						final Location location = event.getClickedBlock().getLocation();
						final Door door = (Door) event.getClickedBlock().getBlockData();
						final Door.Hinge hinge = door.getHinge();
						final BlockFace blockFace = door.getFacing();

						openDoors(location, door, hinge, blockFace);
					}

		if ((boolean) FileManager.getInstance().getConfigValue("function.sitting.status"))
			if (action == Action.RIGHT_CLICK_BLOCK)
				if (event.getClickedBlock() != null && event.getClickedBlock().getType().toString().contains("STAIRS"))
				{
					if (!event.getClickedBlock().getLocation().add(0, 1, 0).getBlock().getType().isAir()) return;

					if (player.getVehicle() != null) return;
					if (player.isSneaking()) return;

					if ((boolean) FileManager.getInstance().getConfigValue("function.sitting.block-unnatural-movements"))
					{
						if (Math.abs(player.getLocation().getBlockY() - event.getClickedBlock().getLocation().getBlockY()) > 1.25)
							return;

						if (player.getLocation().distance(event.getClickedBlock().getLocation()) > 4)
							return;
					}

					if (player.getInventory().getItemInMainHand().getType() != Material.AIR
							&& player.getInventory().getItemInOffHand().getType() != Material.SHIELD
							|| player.getInventory().getItemInOffHand().getType() != Material.AIR) return;

					if (event.getClickedBlock().getType().toString().contains("STAIRS"))
					{
						final Stairs stairs = (Stairs) event.getClickedBlock().getBlockData();
						final Block block = event.getClickedBlock();

						if (stairs.getHalf() == Bisected.Half.TOP) return;

						if (stairs.getShape() != Stairs.Shape.STRAIGHT && stairs.getShape() != Stairs.Shape.INNER_LEFT && stairs.getShape() != Stairs.Shape.INNER_RIGHT) return;

						if (WorldHandler.getInstance().getBlockManipulator().getChairRelative(stairs) == 404) return;

						if (getSitting(player) != null) return;

						if (isSomeoneSittingHere(block)) return;
						else setSitting(player, block);

						ArmorStand armorStand = (ArmorStand) Objects.requireNonNull(player.getWorld()).spawnEntity(new Location(event.getClickedBlock().getWorld(),
								event.getClickedBlock().getBoundingBox().getCenter().getX(),
								event.getClickedBlock().getBoundingBox().getCenter().getY() - 0.2,
								event.getClickedBlock().getBoundingBox().getCenter().getZ(),
										WorldHandler.getInstance().getBlockManipulator().getChairRelative(stairs),
										0),
								EntityType.ARMOR_STAND);

						player.teleport(new Location(event.getClickedBlock().getWorld(),
								event.getClickedBlock().getBoundingBox().getCenter().getX(),
								event.getClickedBlock().getBoundingBox().getCenter().getY() - 0.2,
								event.getClickedBlock().getBoundingBox().getCenter().getZ(),
								WorldHandler.getInstance().getBlockManipulator().getChairRelative(stairs),
										0));

						armorStand.setCustomName("CHAIR");
						armorStand.setCustomNameVisible(false);
						armorStand.setMarker(true);
						armorStand.setInvulnerable(true);
						armorStand.setCollidable(false);
						armorStand.setGravity(false);
						armorStand.setRemoveWhenFarAway(false);
						armorStand.setBasePlate(false);
						armorStand.setArms(true);
						armorStand.setVisible(false);
						armorStand.setPassenger(player);
					}
				}

		if (action == Action.RIGHT_CLICK_BLOCK)
			if (event.getClickedBlock() != null)
				if (event.getClickedBlock().getType() == Material.BREWING_STAND)
				{
					if (player.getInventory().getItemInMainHand().getType() == Material.AIR
							&& player.getInventory().getItemInOffHand().getType() == Material.SHIELD
							| player.getInventory().getItemInOffHand().getType() == Material.AIR)
						if (event.getClickedBlock().getLocation().add(0, -1, 0).getBlock().getType() == Material.LAVA_CAULDRON)
						{
							event.setCancelled(true);
							InventoryLib.createNewInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&c&lSTATYW ALCHEMICZNY &0&1"), "drug_table_amina");
						}
				}

		if ((boolean) FileManager.getInstance().getConfigValue("function.chemistry.status"))
			if (action == Action.RIGHT_CLICK_AIR)
			{
				if (event.getItem() == null) return;

				if (player.getInventory().getItemInOffHand().getType() != Material.AIR
					&& player.getInventory().getItemInOffHand().getType() != Material.SHIELD) return;

				final boolean isKnownDrug = Chemistries.getInstance().isKnown(player.getInventory().getItemInMainHand());
				final boolean isNotOnCooldown = !player.hasCooldown(Material.SUGAR) && !player.hasCooldown(Material.GUNPOWDER);
				final boolean haveDrug = event.getItem().getType() == Material.SUGAR || event.getItem().getType() == Material.GUNPOWDER;

				if (isKnownDrug && isNotOnCooldown && haveDrug)
				{
					final String itemName = event.getItem().hasItemMeta() ? event.getItem().getItemMeta().getDisplayName() : null;
					final ChemistryItem chemistryItem = Chemistries.getInstance().byName(itemName);
					final ItemStack itemStack = ChemistryDrug.getDrug(chemistryItem);

					Inventory.removeItem(player, itemStack, 1);

					if (chemistryItem.getAffinity().isOpioidic())
						ChemistryCore.normalize(player,
							chemistryItem.getAffinity().getOpioidic());
					else if (chemistryItem.getAffinity().isAmine())
						ChemistryCore.modify(player,
							chemistryItem.getAffinity().getSerotonine(),
							chemistryItem.getAffinity().getDopamine(),
							chemistryItem.getAffinity().getNoradrenaline(),
							chemistryItem.getAffinity().getGABA());

					if (chemistryItem.getAffinity().isAmine())
						QuestLib.manageQuest(player, 8);

					player.setCooldown(Material.SUGAR, 120); player.setCooldown(Material.GUNPOWDER, 120);
					Logger.getInstance().store(Logger.StoreType.CONSUME, player.getName() + " skonsumowal " + ChatColor.stripColor(itemName));
				}
			}
	}

	private boolean isDoors(final Material material)
	{
		return material.toString().contains("_DOOR");
	}

	private void openDoors(final Location location, final Door door, final Door.Hinge hinge, final BlockFace blockFace)
	{
		int x = 0, z = 0;

		if (blockFace == BlockFace.EAST)
			z = hinge == Door.Hinge.RIGHT ? -1 : 1;
		else if (blockFace == BlockFace.WEST)
			z = hinge == Door.Hinge.RIGHT ? 1 : -1;
		else if (blockFace == BlockFace.NORTH)
			x = hinge == Door.Hinge.RIGHT ? -1 : 1;
		else if (blockFace == BlockFace.SOUTH)
			x = hinge == Door.Hinge.RIGHT ? 1 : -1;

		if (isDoors(location.clone().add(x, 0, z).getBlock().getType()))
		{
			Door secondDoor = (Door) location.clone().add(x, 0, z).getBlock().getBlockData();
			final Door.Hinge secondHinge = secondDoor.getHinge();
			final BlockFace secondFace = secondDoor.getFacing();

			if (secondHinge == (hinge == Door.Hinge.LEFT ? Door.Hinge.RIGHT : Door.Hinge.LEFT) && secondFace == blockFace)
			{
				final BlockState blockState = location.clone().add(x, 0, z).getBlock().getState();
				final Openable doors = (Openable) blockState.getBlockData();

				doors.setOpen(!door.isOpen());
				blockState.setBlockData(doors);
				blockState.update();
			}
		}
	}

	public enum ClickType
	{
		RIGHT, LEFT, EMPTY
	}

	public class ClickSequence
	{
		private ClickType firstClick, secondClick, finalClick;

		public ClickSequence(ClickType firstClick, ClickType secondClick, ClickType finalClick)
		{
			this.firstClick = firstClick;
			this.secondClick = secondClick;
			this.finalClick = finalClick;
		}

		public ClickSequence addSequence(ClickType clickType)
		{
			if (firstClick == ClickType.EMPTY)
			{
				this.firstClick = clickType;
				return new ClickSequence(this.firstClick, this.secondClick, this.finalClick);
			}

			if (secondClick == ClickType.EMPTY)
			{
				this.secondClick = clickType;
                return new ClickSequence(this.firstClick, this.secondClick, this.finalClick);
			}

			if (finalClick == ClickType.EMPTY)
			{
				this.finalClick = clickType;
                return new ClickSequence(this.firstClick, this.secondClick, this.finalClick);
			}

			resetSequence();
			this.firstClick = clickType;

            return new ClickSequence(this.firstClick, this.secondClick, this.finalClick);
		}

		public boolean isDone() { return this.finalClick != ClickType.EMPTY; }

		public String toString() { return this.firstClick.toString() + this.secondClick.toString() + this.finalClick.toString(); }

		public void resetSequence()
		{
			this.firstClick = ClickType.EMPTY;
			this.secondClick = ClickType.EMPTY;
			this.finalClick = ClickType.EMPTY;
		}
	}
}