package mc.server.survival.worlds;

import mc.server.survival.events.ChunkLoad;
import mc.server.survival.files.Main;
import mc.server.survival.libraries.*;
import mc.server.survival.libraries.chemistry.Chemistries;
import mc.server.survival.libraries.chemistry.ChemistryDrug;
import mc.server.survival.libraries.java.WrappedList;
import mc.server.survival.managers.FileManager;
import mc.server.survival.worlds.aether.Aether;
import mc.server.survival.worlds.twilight.Twilight;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class WorldHandler
{
    private WorldHandler() {}

    static WorldHandler instance = new WorldHandler();

    public static WorldHandler getInstance() {return instance;}

    public BlockManipulator getBlockManipulator() { return new BlockManipulator(); }
    public BlockAnalyzer getBlockAnalyzer() { return new BlockAnalyzer(); }
    public ChunkHandler getChunkHandler() { return new ChunkHandler(); }
    public LootChestEditor getLootChestEditor() { return new LootChestEditor(); }

    private static int loadedWorlds = 0, preparedWorlds = 0;

    private static final String @NotNull [] worlds = {"survival", "survival_nether", "survival_aether", "survival_the_end", "survival_twilight", "auth"};

    public static World getWorld(final String name)
    {
        return Bukkit.getWorld(name);
    }

    public static List<World> getWorlds()
    {
        List<World> allWorlds = new ArrayList<>();

        for (final String worldName : worlds)
            allWorlds.add(getWorld(worldName));

        return allWorlds;
    }

    public void loadWorlds()
    {
        for (final String world : worlds)
            loadWorld(world);
    }

    public void loadWorld(final String name)
    {
        if (preparedWorlds++ >= worlds.length - 1)
            Main.getInstance().getAdapter().setWorlds(loadedWorlds);

        final WorldCreator worldCreator = new WorldCreator(name);
        final World world = name.equalsIgnoreCase("auth")
            ? worldCreator.type(WorldType.FLAT).environment(World.Environment.NORMAL).createWorld()
            : worldCreator.createWorld();

        world.setKeepSpawnInMemory(false);
        applyGameRules(name);

        loadedWorlds++;
    }

    public static void applyGameRules(final String name)
    {
        World world = getWorld(name);

        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        world.setGameRule(GameRule.KEEP_INVENTORY, false);
        world.setGameRule(GameRule.MOB_GRIEFING, true);
        world.setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, 50);

        if (name.equalsIgnoreCase("auth"))
        {
            world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            world.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
            world.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
            world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            return;
        }

        if (name.equalsIgnoreCase("survival_aether"))
        {
            world.setGameRule(GameRule.DO_MOB_SPAWNING, true);
            world.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
            world.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
            world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setGameRule(GameRule.FALL_DAMAGE, false);
            world.setTime(3000);
            world.setClearWeatherDuration(Integer.MAX_VALUE);
            return;
        }

        if (name.equalsIgnoreCase("survival_twilight"))
        {
            world.setGameRule(GameRule.DO_MOB_SPAWNING, true);
            world.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
            world.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
            world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setTime(18000);
            return;
        }

        world.setGameRule(GameRule.DO_TRADER_SPAWNING, true);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, true);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
        world.setGameRule(GameRule.RANDOM_TICK_SPEED, 2);
    }

    public boolean isEnd(final World world)
    {
        return world.getName().equalsIgnoreCase("survival_the_end");
    }

    public boolean isNether(final World world)
    {
        return world.getName().equalsIgnoreCase("survival_nether");
    }

    public static void queueWorldChange(@NotNull Player player)
    {
        TaskLib.getInstance().runSyncLater(() -> {
            VisualLib.showServerChangeTitle(player);
            ScoreboardLib.getInstance().reloadContents(player);
            VisualLib.spawnFirework(player.getLocation());
        }, 20);
    }

    public static Block getHighestBlockAt(final World world, final int x, final int z)
    {
        return world.getHighestBlockAt(x, z);
    }

    public void runLPM() { LPM.runTask(); }

    @SuppressWarnings("unused")
    public enum DimensionType { AETHER, TWILIGHT }

    public Aether getAetherDimension(String template) {return new Aether(template);}

    public Twilight getTwilightDimension(String template) {return new Twilight(template);}

    public class BlockManipulator
    {
        public Block setBlockAxis(final Block block, final Axis axis)
        {
            BlockData bd = block.getBlockData();
            Orientable rot = (Orientable) bd;
            rot.setAxis(axis);
            block.setBlockData(bd);

            return block;
        }

        public Block setSpawnerEntity(final Block block, final EntityType entityType)
        {
            CreatureSpawner creatureSpawner = (CreatureSpawner) block.getState();
            creatureSpawner.setSpawnedType(entityType);
            creatureSpawner.update(true);

            return block;
        }

        public float getChairRelative(final Stairs stairs)
        {
            final BlockFace blockFace = stairs.getFacing();
            final Stairs.Shape blockShape = stairs.getShape();

            if (blockShape == Stairs.Shape.STRAIGHT)
            {
                if (blockFace == BlockFace.WEST) return -90;
                else if (blockFace == BlockFace.EAST) return 90;
                else if (blockFace == BlockFace.NORTH) return 0;
                else if (blockFace == BlockFace.SOUTH) return 180;
                else return 404;
            }
            else if (blockShape == Stairs.Shape.INNER_LEFT)
            {
                if (blockFace == BlockFace.WEST) return -135;
                else if (blockFace == BlockFace.EAST) return 45;
                else if (blockFace == BlockFace.NORTH) return -45;
                else if (blockFace == BlockFace.SOUTH) return 135;
                else return 404;
            }
            else if (blockShape == Stairs.Shape.INNER_RIGHT)
            {
                if (blockFace == BlockFace.WEST) return -45;
                else if (blockFace == BlockFace.EAST) return 135;
                else if (blockFace == BlockFace.NORTH) return 45;
                else if (blockFace == BlockFace.SOUTH) return -135;
                else return 404;
            }

            return 404;
        }
    }

    public class BlockAnalyzer
    {
        public boolean isNearBlock(final World world, final int x, final int y, final int z, final Material material, final int radius)
        {
            return isNearBlock(world, x, y, z, material, radius, radius, radius);
        }

        public boolean isNearBlock(final World world, final int x, final int y, final int z, final Material material, final int radiusX, final int radiusY, final int radiusZ)
        {
            boolean isNear = false;

            for (int xS = -radiusX; xS <= radiusX; xS++)
                for (int yS = -radiusY; yS <= radiusY; yS++)
                    for (int zS = -radiusZ; zS <= radiusZ; zS++)
                        if (world.getBlockAt(x + xS, y + yS, z + zS).getType() == material)
                            isNear = true;

            return isNear;
        }
    }

    public class ChunkHandler
    {
        private final boolean fallingLeaves = (boolean) FileManager.getInstance().getConfigValue("visual.miscellaneous.falling-leaves");

        public @NotNull WrappedList<Chunk> getLoadedLeafChunks()
        {
            WrappedList<Chunk> chunks = new WrappedList<>();

            for (final World world : getWorlds())
                if (!isEnd(world) && !isNether(world))
                    chunks.addAll(Arrays.asList(world.getLoadedChunks()));

            return chunks;
        }

        public void reloadLeafChunks() { ChunkLoad.reloadLeafChunks(); }

        public void handleLeaves()
        {
            TaskLib.getInstance().runSyncTimer(() -> {

                if (ChunkLoad.getActiveLeafChunks().size() < 1) return;

                final WrappedList<Chunk> activeLeafChunk = ChunkLoad.getActiveLeafChunks();

                for (final Chunk chunk : activeLeafChunk)
                {
                    if (!activeLeafChunk.contains(chunk)) continue;

                    final HashMap<Chunk, WrappedList<Location>> wrappedLocations = ChunkLoad.getLeafLocations();

                    for (final Location location : wrappedLocations.get(chunk))
                    {
                        if (!wrappedLocations.containsKey(chunk)) continue;

                        final Block block = location.clone().getBlock();

                        if (MathLib.chanceOf(60))
                            location.getWorld().spawnParticle(Particle.FALLING_DUST, location.clone().add(0.5, 0.1, 0.5), 1, 0.25, 0.25, 0.25, 255, block.getBlockData());
                    }
                }
            }, 20);
        }

        public boolean shouldBeLeafHandled(final Block block)
        {
            if (!fallingLeaves) return false;

            if (ItemLib.isLeaf(block.getType()))
                return true;

            for (int x = -1; x <= 1; x++)
                for (int y = -1; y <= 1; y++)
                    for (int z = -1; z <= 1; z++)
                        if (ItemLib.isLeaf(block.getLocation().clone().add(x, y, z).getBlock().getType()))
                            return true;

            return false;
        }

        public void handleLeafChunk(final Chunk chunk)
        {
            if (!fallingLeaves) return;

            if (!chunk.isLoaded()) return;

            ChunkLoad.getActiveLeafChunks().remove(chunk);

            if (ChunkLoad.getLeafLocations().containsKey(chunk))
                ChunkLoad.getLeafLocations().put(chunk, new WrappedList<>());

            final ChunkSnapshot chunkSnapshot = chunk.getChunkSnapshot();

            TaskLib.getInstance().runAsync(() -> {
                final World world = WorldHandler.getWorld(chunkSnapshot.getWorldName());
                final int heightLimit = world.getMaxHeight();

                for (int x = 0; x < 16; x++)
                    for (int z = 0; z < 16; z++)
                        for (int y = 1; y < heightLimit; y++)
                            if (ItemLib.isLeaf(chunkSnapshot.getBlockType(x, y, z)))
                                if (!chunkSnapshot.getBlockType(x, y - 1, z).isSolid())
                                {
                                    final int finalX = chunkSnapshot.getX() * 16 + x;
                                    final int finalZ = chunkSnapshot.getZ() * 16 + z;
                                    final Location finalLocation = new Location(world, finalX, y, finalZ);

                                    if (!ItemLib.isLeaf(finalLocation.clone().getBlock().getType()))
                                        continue;

                                    TaskLib.getInstance().runSync(() -> {
                                        ChunkLoad.getActiveLeafChunks().add(chunk);

                                        if (ChunkLoad.getLeafLocations().get(chunk) == null)
                                            ChunkLoad.getLeafLocations().put(chunk, new WrappedList<>());

                                        ChunkLoad.getLeafLocations().get(chunk).add(finalLocation);
                                    });
                                }
            });
        }
    }

    public class LootChestEditor
    {
        public class LootItem
        {
            private final @NotNull ItemStack itemStack;
            private final int chance;
            private final int quantityMin;
            private final int quantityMax;

            public LootItem(ItemStack itemStack, int chance, int quantityMin, int quantityMax)
            {
                this.itemStack = itemStack;
                this.chance = chance;
                this.quantityMin = quantityMin;
                this.quantityMax = quantityMax;
            }

            public ItemStack getItemStack() { return itemStack; }

            public int getChance() { return chance; }

            public int getQuantityMin() { return quantityMin; }

            public int getQuantityMax() { return quantityMax; }

            public ItemStack get()
            {
                if (MathLib.chanceOf(getChance()))
                {
                    if (!itemStack.getType().isAir())
                        itemStack.setAmount(MathLib.getBetween(getQuantityMin(), getQuantityMax()));

                    return itemStack;
                }

                return new ItemStack(Material.AIR);
            }
        }

        public enum LootType
        {
            DEFAULT,
            AETHER_PORTAL, AETHER_PANTHEON,
            TWILIGHT_SHIP, TWILIGHT_TOWER
        }

        public LootType of(final String type)
        {
            if (type.equalsIgnoreCase("AETHER_PORTAL"))
                return LootType.AETHER_PORTAL;
            else if (type.equalsIgnoreCase("AETHER_PANTHEON"))
                return LootType.AETHER_PANTHEON;
            else if (type.equalsIgnoreCase("TWILIGHT_SHIP"))
                return LootType.TWILIGHT_SHIP;
            else if (type.equalsIgnoreCase("TWILIGHT_TOWER"))
                return LootType.TWILIGHT_TOWER;
            else
                return LootType.DEFAULT;
        }

        public List<LootItem> getPossibleLootItems(final LootType lootType)
        {
            List<LootItem> itemStacks = new ArrayList<>();

            switch (lootType)
            {
                case DEFAULT -> {
                    itemStacks.add(new LootItem(new ItemStack(Material.AIR), 0, 1, 1));
                    itemStacks.add(new LootItem(new ItemStack(Material.AIR), 25, 1, 1));
                    itemStacks.add(new LootItem(new ItemStack(Material.AIR), 50, 1, 1));
                    itemStacks.add(new LootItem(new ItemStack(Material.AIR), 75, 1, 1));
                    itemStacks.add(new LootItem(new ItemStack(Material.AIR), 100, 1, 1));
                }
                case AETHER_PORTAL -> {
                    itemStacks.add(new LootItem(new ItemStack(Material.BOOK), 45, 2, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.GLOWSTONE), 30, 1, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.WATER_BUCKET), 15, 1, 1));
                    itemStacks.add(new LootItem(new ItemStack(Material.BUCKET), 20, 1, 1));
                    itemStacks.add(new LootItem(new ItemStack(Material.OAK_LEAVES), 65, 2, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.GOLD_NUGGET), 65, 3, 7));
                    itemStacks.add(new LootItem(new ItemStack(Material.IRON_NUGGET), 75, 4, 7));
                    itemStacks.add(new LootItem(new ItemStack(Material.EMERALD), 14, 2, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.SPRUCE_LOG), 35, 1, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.OAK_LOG), 35, 1, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.BREAD), 40, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.WHEAT), 65, 2, 6));
                    itemStacks.add(new LootItem(new ItemStack(Material.BEETROOT_SEEDS), 75, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.PUMPKIN_SEEDS), 75, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.QUARTZ), 55, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.FEATHER), 80, 1, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.STICK), 85, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.LILY_OF_THE_VALLEY), 85, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.ROSE_BUSH), 85, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.BLUE_ORCHID), 85, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.RED_TULIP), 85, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.PHANTOM_MEMBRANE), 60, 1, 2));
                }
                case AETHER_PANTHEON -> {
                    itemStacks.add(new LootItem(new ItemStack(Material.SKELETON_SPAWN_EGG), 15, 1, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.ZOMBIE_SPAWN_EGG), 15, 1, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.SPIDER_SPAWN_EGG), 15, 1, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.ENDERMAN_SPAWN_EGG), 15, 1, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.PIG_SPAWN_EGG), 15, 1, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.COW_SPAWN_EGG), 15, 1, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.GOLD_NUGGET), 75, 2, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.GOLD_INGOT), 65, 1, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.IRON_NUGGET), 80, 2, 6));
                    itemStacks.add(new LootItem(new ItemStack(Material.IRON_INGOT), 70, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.EMERALD), 40, 2, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.SPRUCE_LOG), 35, 1, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.OAK_LOG), 35, 1, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.BREAD), 55, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.WHEAT), 70, 2, 6));
                    itemStacks.add(new LootItem(new ItemStack(Material.BEETROOT_SEEDS), 70, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.PUMPKIN_SEEDS), 70, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.QUARTZ), 60, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.FEATHER), 85, 1, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.STICK), 90, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.LILY_OF_THE_VALLEY), 90, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.ROSE_BUSH), 90, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.BLUE_ORCHID), 90, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.RED_TULIP), 90, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.PHANTOM_MEMBRANE), 75, 1, 3));
                    itemStacks.add(new LootItem(ChemistryDrug.getDrug(Chemistries.metylomorfina), 7, 1, 1));
                    itemStacks.add(new LootItem(ChemistryDrug.getDrug(Chemistries.alprazolam), 9, 1, 2));
                    itemStacks.add(new LootItem(ItemLib.getRandomEnchantedBook(), 4, 1, 1));
                }
                case TWILIGHT_SHIP -> {
                    itemStacks.add(new LootItem(new ItemStack(Material.DIAMOND), 5, 1, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.GOLD_NUGGET), 75, 3, 7));
                    itemStacks.add(new LootItem(new ItemStack(Material.IRON_NUGGET), 80, 4, 7));
                    itemStacks.add(new LootItem(new ItemStack(Material.EMERALD), 18, 2, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.SPRUCE_LOG), 35, 1, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.OAK_LOG), 35, 1, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.DARK_OAK_LOG), 35, 1, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.BREAD), 45, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.WHEAT), 70, 2, 6));
                    itemStacks.add(new LootItem(new ItemStack(Material.BEETROOT_SEEDS), 70, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.PUMPKIN_SEEDS), 70, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.FEATHER), 85, 1, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.STICK), 90, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.GOLDEN_SWORD), 40, 1, 1));
                    itemStacks.add(new LootItem(ChemistryDrug.getDrug(Chemistries.alprazolam), 3, 1, 1));
                    itemStacks.add(new LootItem(ItemLib.getRandomEnchantedBook(), 2, 1, 1));
                }
                case TWILIGHT_TOWER -> {
                    itemStacks.add(new LootItem(new ItemStack(Material.DIAMOND), 20, 1, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.GOLD_NUGGET), 85, 3, 7));
                    itemStacks.add(new LootItem(new ItemStack(Material.IRON_NUGGET), 90, 4, 7));
                    itemStacks.add(new LootItem(new ItemStack(Material.GOLDEN_APPLE), 7, 1, 1));
                    itemStacks.add(new LootItem(new ItemStack(Material.EMERALD), 50, 2, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.SPRUCE_LOG), 35, 1, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.OAK_LOG), 35, 1, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.DARK_OAK_LOG), 35, 1, 3));
                    itemStacks.add(new LootItem(new ItemStack(Material.COOKED_PORKCHOP), 70, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.BEETROOT_SEEDS), 70, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.PUMPKIN_SEEDS), 70, 1, 4));
                    itemStacks.add(new LootItem(new ItemStack(Material.BEETROOT_SOUP), 55, 1, 1));
                    itemStacks.add(new LootItem(ChemistryDrug.getDrug(Chemistries.metafedron), 5, 1, 1));
                    itemStacks.add(new LootItem(ChemistryDrug.getDrug(Chemistries.kleksedron), 6, 1, 1));
                    itemStacks.add(new LootItem(ChemistryDrug.getDrug(Chemistries.alprazolam), 7, 1, 1));
                    itemStacks.add(new LootItem(ItemLib.getRandomEnchantedBook(), 5, 1, 1));
                }
            }

            return itemStacks;
        }

        public ItemStack getLoot(final LootType lootType)
        {
            final List<LootItem> itemStacks = getPossibleLootItems(lootType);
            final Random random = new Random();

            return itemStacks.get(random.nextInt(itemStacks.size())).get();
        }

        public void handleChest(final Block block, final LootType lootType)
        {
            TaskLib.getInstance().runSyncLater(() -> {
                Chest chest = (Chest) block.getState();

                for (int slot = 0; slot < chest.getBlockInventory().getSize(); slot++)
                    chest.getBlockInventory().setItem(slot, getLoot(lootType));
            }, 1);
        }
    }
}