package mc.server.survival.events;

import mc.server.survival.files.Main;
import mc.server.survival.libraries.ItemLib;
import mc.server.survival.libraries.MathLib;
import mc.server.survival.libraries.QuestLib;
import mc.server.survival.libraries.TaskLib;
import mc.server.survival.libraries.chemistry.ChemistryCore;
import mc.server.survival.libraries.enchantments.EnchantmentHandler;
import mc.server.survival.libraries.enchantments.Enchantments;
import mc.server.survival.managers.DataManager;
import mc.server.survival.managers.FileManager;
import mc.server.survival.worlds.WorldHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class BlockBreak 
implements Listener
{
    private final Material @NotNull [] ores = {Material.COAL_ORE, Material.COPPER_ORE, Material.IRON_ORE, Material.GOLD_ORE, Material.NETHER_GOLD_ORE,
                                     Material.EMERALD_ORE, Material.DIAMOND_ORE, Material.LAPIS_ORE, Material.REDSTONE_ORE,
                                     Material.NETHER_QUARTZ_ORE, Material.DEEPSLATE_COAL_ORE, Material.DEEPSLATE_COPPER_ORE,
									 Material.DEEPSLATE_GOLD_ORE, Material.DEEPSLATE_EMERALD_ORE, Material.DEEPSLATE_DIAMOND_ORE,
									 Material.DEEPSLATE_LAPIS_ORE, Material.DEEPSLATE_REDSTONE_ORE, Material.DEEPSLATE_IRON_ORE};

	private final Material @NotNull [] logs = {Material.OAK_LOG, Material.SPRUCE_LOG, Material.JUNGLE_LOG, Material.ACACIA_LOG,
									 Material.DARK_OAK_LOG, Material.BIRCH_LOG, Material.CRIMSON_STEM, Material.WARPED_STEM,
									 /* Aether trees */ Material.STRIPPED_BIRCH_LOG, Material.STRIPPED_OAK_LOG,
									 /* Twilight trees */ Material.STRIPPED_DARK_OAK_LOG};

	private final boolean gravityExpander = (boolean) FileManager.getInstance().getConfigValue("function.gravity-expander");
	private final boolean blockInteractions = (boolean) FileManager.getInstance().getConfigValue("function.sitting.block-interactions");
	private final boolean silkTouchSpawners = (boolean) FileManager.getInstance().getConfigValue("function.silk-touch-spawners");
	private final boolean timberLeavesGrind = (boolean) FileManager.getInstance().getConfigValue("visual.miscellaneous.timber-leaves-grind");

	@EventHandler
	public void onEvent(BlockBreakEvent event)
	{
		final Player player = event.getPlayer();
		final Block block = event.getBlock();

		if (WorldHandler.getInstance().getChunkHandler().shouldBeLeafHandled(block))
			if (!WorldHandler.getInstance().isEnd(player.getWorld()) && !WorldHandler.getInstance().isNether(player.getWorld()))
				WorldHandler.getInstance().getChunkHandler().handleLeafChunk(event.getBlock().getChunk());

		TaskLib.getInstance().runAsync(() ->
		{
			if (silkTouchSpawners)
				if (!player.getInventory().getItemInMainHand().getEnchantments().isEmpty())
					if (player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH))
						TaskLib.getInstance().runSync(() -> block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.SPAWNER, 1)));

		});

		if (blockInteractions) event.setCancelled(PlayerInteract.getSitting(player) != null);

		if (block.getType().toString().contains("STAIRS"))
			if (PlayerInteract.isSomeoneSittingHere(block))
			{
				final Player sittingPlayer = PlayerInteract.whoIsSittingHere(block);
				Dismount.dismount(sittingPlayer, sittingPlayer.getVehicle());
			}

		if (gravityExpander)
		{
			final Block blockAbove = block.getLocation().clone().add(0.5, 1.0625, 0.5).getBlock();

			if (blockAbove.getType() == Material.SNOW || blockAbove.getType().toString().contains("CARPET") ||
					blockAbove.getType().toString().contains("PRESSURE_PLATE") || blockAbove.getType().toString().contains("RAIL"))
			{
				final BlockData blockData = blockAbove.getBlockData().clone();
				final Location location = blockAbove.getLocation().clone().add(0.5, 0.0625, 0.5);

				TaskLib.getInstance().runSyncLater(() -> block.getWorld().spawnFallingBlock(location, blockData), 2);
			}
		}

		if ((boolean) FileManager.getInstance().getConfigValue("function.timber"))
			for (final Material log : logs)
				if (block.getType() == log)
				{
					if (block.getType() == Material.STRIPPED_OAK_LOG & !player.getWorld().getName().equalsIgnoreCase("survival_aether") ||
							block.getType() == Material.STRIPPED_BIRCH_LOG & !player.getWorld().getName().equalsIgnoreCase("survival_aether") ||
							block.getType() == Material.STRIPPED_DARK_OAK_LOG && !player.getWorld().getName().equalsIgnoreCase("survival_twilight"))
						return;

					final ItemStack item = player.getInventory().getItemInMainHand();

					if (!ItemLib.isAxe(item)) return;

					if (!player.isSneaking())
						if (isTree(block))
							runTimber(player, block, log, getLeavesType(log));
				}

		if ((boolean) FileManager.getInstance().getConfigValue("function.ore-miner"))
			for (final Material ore : ores)
				if (block.getType() == ore)
				{
					final ItemStack item = player.getInventory().getItemInMainHand();

					if (!ItemLib.isPickaxe(item)) return;

					if (!player.isSneaking())
						runMiner(player, block, block.getType());
					else
					{
						if (block.getType() == Material.NETHER_QUARTZ_ORE || block.getType() == Material.NETHER_GOLD_ORE)
							player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.NETHERRACK));
						else
							player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.COBBLESTONE));

						Collection<ItemStack> itemStacks = block.getDrops(item);

						if (EnchantmentHandler.getInstance().hasEnchantment(item, Enchantments.INSTANT_MINING))
							for (ItemStack itemStack : itemStacks)
							{
								if (itemStack.getType() == Material.RAW_COPPER)
								{
									itemStacks.remove(itemStack);
									itemStacks.add(new ItemStack(Material.COPPER_INGOT, itemStack.getAmount()));
								}
								if (itemStack.getType() == Material.RAW_GOLD)
								{
									itemStacks.remove(itemStack);
									itemStacks.add(new ItemStack(Material.GOLD_INGOT, itemStack.getAmount()));
								}
								if (itemStack.getType() == Material.RAW_IRON)
								{
									itemStacks.remove(itemStack);
									itemStacks.add(new ItemStack(Material.IRON_INGOT, itemStack.getAmount()));
								}
							}

						for (ItemStack itemStack : itemStacks)
							player.getWorld().dropItemNaturally(block.getLocation(), itemStack);

						block.setType(Material.AIR);
						breakTool(player, item);
					}
				}

		if (block.getType() == Material.STONE || block.getType() == Material.NETHERRACK)
			if (!player.isSneaking())
				if (DataManager.getInstance().getLocal(player).getNoradrenaline() > 5)
					if (MathLib.chanceOf(DataManager.getInstance().getLocal(player).getNoradrenaline()))
					{
						ItemStack item = player.getInventory().getItemInMainHand();

						for (int x = -1; x < 1; x++)
							for (int z = -1; z < 1; z++)
								for (int y = -1; y < 1; y++)
									if (block.getLocation().add(x, y, z).getBlock().getType() == Material.STONE || block.getType() == Material.NETHERRACK)
										if (MathLib.chanceOf(50))
											block.getLocation().add(x, y, z).getBlock().breakNaturally(item);
					}
		
		if (block.getType() == Material.ACACIA_LEAVES || block.getType() == Material.BIRCH_LEAVES || block.getType() == Material.OAK_LEAVES ||
			block.getType() == Material.SPRUCE_LEAVES || block.getType() == Material.JUNGLE_LEAVES || block.getType() == Material.DARK_OAK_LEAVES)
		{
			if (MathLib.chanceOf(5 + (4 * (DataManager.getInstance().getLocal(player).getUpgradeLevel(player.getName(), "loot")))))
				player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.APPLE));

			if (MathLib.chanceOf(15 + (2 * (DataManager.getInstance().getLocal(player).getUpgradeLevel(player.getName(), "loot")))))
				player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.STICK));

			if (ChemistryCore.ABILITY_PLAYER_LUCK.get(player) > 25)
				if (MathLib.chanceOf(ChemistryCore.ABILITY_PLAYER_LUCK.get(player) / 300))
					player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GOLDEN_APPLE));
		}

		if (block.getType() == Material.ANCIENT_DEBRIS)
			if (MathLib.chanceOf(1 + (2 * (DataManager.getInstance().getLocal(player).getUpgradeLevel(player.getName(), "loot")))))
				player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.NETHERITE_SCRAP));

		if (block.getType() == Material.ACACIA_LOG || block.getType() == Material.BIRCH_LOG || block.getType() == Material.OAK_LOG ||
			block.getType() == Material.SPRUCE_LOG || block.getType() == Material.JUNGLE_LOG || block.getType() == Material.DARK_OAK_LOG)
			QuestLib.manageQuest(player, 1);

		if (block.getType() == Material.STONE || block.getType() == Material.COBBLESTONE)
			QuestLib.manageQuest(player, 2);

		if (block.getType() == Material.SAND || block.getType() == Material.RED_SAND ||
			block.getType() == Material.GRAVEL || block.getType() == Material.DIRT || block.getType() == Material.GRASS_BLOCK)
			QuestLib.manageQuest(player, 3);

		if (block.getType() == Material.WHEAT || block.getType() == Material.CARROTS ||
			block.getType() == Material.BEETROOTS || block.getType() == Material.POTATOES ||
			block.getType() == Material.NETHER_WART)
		{
			Ageable ageable = (Ageable) block.getBlockData();
			if (ageable.getAge() == ageable.getMaximumAge())
				QuestLib.manageQuest(player, 11);

			if (DataManager.getInstance().getLocal(player).getNoradrenaline() > 5)
				if (MathLib.chanceOf(50))
				{
					if (block.getType() == Material.WHEAT)
					{
						if (player.getInventory().contains(Material.WHEAT_SEEDS))
						{
							TaskLib.getInstance().runSyncLater(() -> block.setType(Material.WHEAT_SEEDS), 2);
							Inventory.removeItem(player, new ItemStack(Material.WHEAT_SEEDS), 1);
						}
					}
					else if (block.getType() == Material.CARROTS)
					{
						if (player.getInventory().contains(Material.CARROTS))
						{
							TaskLib.getInstance().runSyncLater(() -> block.setType(Material.CARROTS), 2);
							Inventory.removeItem(player, new ItemStack(Material.CARROTS), 1);
						}
					}
					else if (block.getType() == Material.BEETROOTS)
					{
						if (player.getInventory().contains(Material.BEETROOTS))
						{
							TaskLib.getInstance().runSyncLater(() -> block.setType(Material.BEETROOTS), 2);
							Inventory.removeItem(player, new ItemStack(Material.BEETROOTS), 1);
						}
					}
					else if (block.getType() == Material.POTATOES)
					{
						if (player.getInventory().contains(Material.POTATOES))
						{
							TaskLib.getInstance().runSyncLater(() -> block.setType(Material.POTATOES), 2);
							Inventory.removeItem(player, new ItemStack(Material.POTATOES), 1);
						}
					}
					else if (block.getType() == Material.NETHER_WART)
					{
						if (player.getInventory().contains(Material.NETHER_WART))
						{
							TaskLib.getInstance().runSyncLater(() -> block.setType(Material.NETHER_WART), 2);
							Inventory.removeItem(player, new ItemStack(Material.NETHER_WART), 1);
						}
					}
				}
		}
	}

	private Material getLeavesType(final Material material)
	{
		if (material == Material.OAK_LOG) return Material.OAK_LEAVES;
		if (material == Material.SPRUCE_LOG) return Material.SPRUCE_LEAVES;
		if (material == Material.BIRCH_LOG) return Material.BIRCH_LEAVES;
		if (material == Material.DARK_OAK_LOG) return Material.DARK_OAK_LEAVES;
		if (material == Material.ACACIA_LOG) return Material.ACACIA_LEAVES;
		if (material == Material.JUNGLE_LOG) return Material.JUNGLE_LEAVES;
		if (material == Material.CRIMSON_STEM) return Material.NETHER_WART_BLOCK;
		if (material == Material.WARPED_STEM) return Material.WARPED_WART_BLOCK;
		if (material == Material.STRIPPED_BIRCH_LOG) return Material.AZALEA_LEAVES;
		if (material == Material.STRIPPED_OAK_LOG) return Material.AZALEA_LEAVES;
		if (material == Material.STRIPPED_DARK_OAK_LOG) return Material.SPRUCE_LEAVES;
		return null;
	}

	private void runTimber(final Player player, Block block, Material road, Material leaves)
	{
		if (player.isSneaking())
			return;

		final ItemStack item = player.getInventory().getItemInMainHand();
		final ItemStack concurrentItem = player.getInventory().getItemInOffHand();
		ItemStack finalItem;

		if (!ItemLib.isAxe(item))
			finalItem = concurrentItem;
		else
			finalItem = item;

		ArrayList<Block> blockRoad = new ArrayList<>();

		for (int x = -1; x <= 1; x++)
		{
			for (int y = -1; y <= 1; y++)
			{
				for (int z = -1; z <= 1; z++)
				{
					if (player.getWorld().getBlockAt(block.getLocation().add(x, y, z)).getType().toString().equalsIgnoreCase(road.toString()))
					{
						blockRoad.add(player.getWorld().getBlockAt(block.getLocation().add(x, y, z)));
						Block targetBlock = player.getWorld().getBlockAt(block.getLocation().add(x, y, z));

						Collection<ItemStack> itemStacks = targetBlock.getDrops(finalItem);

						for (ItemStack itemStack : itemStacks)
							player.getWorld().dropItemNaturally(targetBlock.getLocation(), itemStack);

						if (MathLib.chanceOf(ChemistryCore.ABILITY_PLAYER_LUCK.get(player) / 2))
							for (ItemStack itemStack : itemStacks)
								player.getWorld().dropItemNaturally(targetBlock.getLocation(), itemStack);

						targetBlock.setType(Material.AIR);
						breakTool(player, finalItem);

						if (MathLib.chanceOf(10))
							player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.APPLE));

						if (MathLib.chanceOf(25))
							player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.STICK));

						for (int x2 = -2; x2 <= 2; x2++)
						{
							for (int y2 = -4; y2 <= 4; y2++)
							{
								for (int z2 = -2; z2 <= 2; z2++)
								{
									if (player.getWorld().getBlockAt(block.getLocation().add(x2, y2, z2).add(x, y, z)).getType().toString().equalsIgnoreCase(leaves.toString()))
									{
										WorldHandler.getInstance().getChunkHandler().handleLeafChunk(block.getLocation().add(x2, y2, z2).add(x, y, z).getChunk());

										if (MathLib.chanceOf(10))
											player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(leaves));

										targetBlock.getLocation().add(x2, y2, z2).getBlock().setType(Material.AIR);
										FallingBlock fallingBlock = player.getWorld().spawnFallingBlock(targetBlock.getLocation().add(x2, y2, z2), leaves.createBlockData());
										fallingBlock.setDropItem(false);
										fallingBlock.setHurtEntities(false);

										new BukkitRunnable() { @Override public void run() {
											if (fallingBlock.getVelocity().getY() == 0)
											{
												if (timberLeavesGrind) player.getWorld().spawnParticle(Particle.COMPOSTER, fallingBlock.getLocation(), 2, 0.5, 0.5, 0.5);
												fallingBlock.getLocation().getBlock().setType(Material.AIR);
												this.cancel();
											}
										} }.runTaskTimer(Main.getInstance(), 2, 2);
									}
								}
							}
						}
					}
				}
			}
		}

		for (Block blocks : blockRoad)
			runTimber(player, blocks, road, leaves);
	}

	private void runMiner(final Player player, Block block, Material road)
    {
		WorldHandler.getInstance().getChunkHandler().handleLeafChunk(block.getChunk());

        if (player.isSneaking())
            return;

		final ItemStack item = player.getInventory().getItemInMainHand();
		final ItemStack concurrentItem = player.getInventory().getItemInOffHand();
		ItemStack finalItem;

		if (!ItemLib.isPickaxe(item))
			finalItem = concurrentItem;
		else
			finalItem = item;

        ArrayList<Block> blockRoad = new ArrayList<>();

        for (int x = -1; x <= 1; x++)
        {
            for (int y = -1; y <= 1; y++)
            {
                for (int z = -1; z <= 1; z++)
                {
                    if (player.getWorld().getBlockAt(block.getLocation().add(x, y, z)).getType().toString().equalsIgnoreCase(road.toString()))
                    {
						WorldHandler.getInstance().getChunkHandler().handleLeafChunk(block.getLocation().add(x, y, z).getChunk());

                        blockRoad.add(player.getWorld().getBlockAt(block.getLocation().add(x, y, z)));
                        Block targetBlock = player.getWorld().getBlockAt(block.getLocation().add(x, y, z));

						Collection<ItemStack> itemStacks = targetBlock.getDrops(item);

						if (EnchantmentHandler.getInstance().hasEnchantment(finalItem, Enchantments.INSTANT_MINING))
							for (ItemStack itemStack : itemStacks)
							{
								if (itemStack.getType() == Material.RAW_COPPER)
								{
									itemStacks.remove(itemStack);
									itemStacks.add(new ItemStack(Material.COPPER_INGOT, itemStack.getAmount()));
								}
								if (itemStack.getType() == Material.RAW_GOLD)
								{
									itemStacks.remove(itemStack);
									itemStacks.add(new ItemStack(Material.GOLD_INGOT, itemStack.getAmount()));
								}
								if (itemStack.getType() == Material.RAW_IRON)
								{
									itemStacks.remove(itemStack);
									itemStacks.add(new ItemStack(Material.IRON_INGOT, itemStack.getAmount()));
								}
							}

						for (ItemStack itemStack : itemStacks)
							player.getWorld().dropItemNaturally(targetBlock.getLocation(), itemStack);

						if (MathLib.chanceOf(ChemistryCore.ABILITY_PLAYER_LUCK.get(player) / 2))
							for (ItemStack itemStack : itemStacks)
								player.getWorld().dropItemNaturally(targetBlock.getLocation(), itemStack);

						targetBlock.setType(Material.AIR);
						breakTool(player, finalItem);

						if (road == Material.NETHER_QUARTZ_ORE || road == Material.NETHER_GOLD_ORE)
							player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.NETHERRACK));
						else
							player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.COBBLESTONE));
                    }
                }
            }
        }

        for (Block blocks : blockRoad)
            runMiner(player, blocks, road);
    }

	private void breakTool(final Player player, ItemStack itemStack)
	{
		if (EnchantmentHandler.getInstance().hasEnchantment(itemStack, Enchantments.UNBREAKABLE))
			return;

		if (!MathLib.chanceOf(ChemistryCore.ABILITY_PLAYER_LUCK.get(player) / 2))
			if (itemStack.getItemMeta() != null && !itemStack.getItemMeta().isUnbreakable())
			{
				itemStack.setDurability((short) (itemStack.getDurability() - (short) -1));

				if (itemStack.getDurability() >= itemStack.getType().getMaxDurability())
					player.getInventory().removeItem(itemStack);
			}
	}

	private boolean isTree(final @NotNull Block block)
	{
		int height = 0;
		int leaves = 0;

		for (int x = -2; x <= 2; x++)
			for (int y = 0; y <= 12; y++)
				for (int z = -2; z <= 2; z++)
				{
					if (block.getLocation().add(x, y, z).getBlock().getType().toString().contains("_LEAVES"))
						leaves = leaves + 1;

					for (Material log : logs)
						if (block.getLocation().add(x, y, z).getBlock().getType() == log)
							height = height + 1;
				}

		return height >= 4 && leaves > 16;
	}
}