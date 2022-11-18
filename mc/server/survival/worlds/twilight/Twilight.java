package mc.server.survival.worlds.twilight;

import mc.server.survival.files.Main;
import mc.server.survival.libraries.MathLib;
import mc.server.survival.libraries.TaskLib;
import mc.server.survival.managers.FileManager;
import mc.server.survival.worlds.WorldHandler;
import mc.server.survival.worlds.twilight.mobs.DarkPirate;
import mc.server.survival.worlds.twilight.mobs.DarkWizard;
import mc.server.survival.worlds.twilight.mobs.EvilPirate;
import mc.server.survival.worlds.twilight.populators.*;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Twilight extends ChunkGenerator
{
    @NotNull Main survival;

    private Twilight() {}

    static Twilight instance = new Twilight();

    public static Twilight getInstance()
    {
        return instance;
    }

    public Twilight(@NotNull String template)
    {
        this.survival = Main.getInstance();
    }

    private static int mountainLevel = 94;
    public int getMountainLevel() { return mountainLevel; }

    public int getWaterLevel() { return 67; }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world)
    {
        return List.of(getTwilightPopulator());
    }

    public TwilightPortal getTwilightPortal() { return new TwilightPortal(); }
    public TwilightGenerator getTwilightGenerator() { return new TwilightGenerator(); }
    public TwilightPopulator getTwilightPopulator() { return new TwilightPopulator(); }

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomeGrid)
    {
        ChunkData chunkData = createChunkData(world);

        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(world.getSeed(), 8);
        generator.setScale(0.004);

        for (int x = 0; x < 16; x++)
            for (int z = 0; z < 16; z++)
            {
                final int finalX = chunkX * 16 + x;
                final int finalZ = chunkZ * 16 + z;

                final double newDouble = random.nextDouble();

                final double amplitude = 0.015;
                final double frequency = 0.025;
                final double sharpness = 1.9;

                final double handleY1 = Math.pow(((generator.noise(finalX, finalZ, frequency, amplitude, false)) + 1), sharpness);
                final double handleY2 = Math.pow(((generator.noise(finalX, finalZ, frequency, amplitude, false)) + 1), sharpness * 1.125);
                final double handleY3 = Math.pow(((generator.noise(finalX, finalZ, frequency, amplitude, false)) + 1), sharpness / 1.125);

                final double handleY = (handleY1 + handleY2 + handleY3 + (newDouble / 16)) / 3;
                int y = (int) (handleY * 32) + 64;

                // Coordinate Y various between 64 - 184.

                final boolean shouldGradient = MathLib.chanceOf(75);

                final double mountainRandom = newDouble * (shouldGradient ? 9 : 18);
                mountainLevel = (int) (94 + mountainRandom);
                final boolean isMountain = y > mountainLevel;

                if (isMountain) y = (int) (y + (newDouble * (y / 184) * 4));

                final double snowRandom = newDouble * (shouldGradient ? 7 : 14);
                final int snowLevel = (int) (148 + snowRandom);
                final boolean isSnow = y > snowLevel;

                final double sandRandom = newDouble * (shouldGradient ? 1 : 2);
                final int sandLevel = (int) (68 + sandRandom);
                final boolean isSand = y <= sandLevel;

                final int waterLevel = 67;
                final boolean isWater = y <= waterLevel;

                getTwilightGenerator().set(chunkData, x, y, z, isSnow ? StructureType.TERRAIN_MOUNTAIN_PEAK : isMountain ? StructureType.TERRAIN_MOUNTAIN : (isWater ? StructureType.TERRAIN_UNDERWATER : StructureType.TERRAIN));

                if (!isWater && !isSand && !isMountain && chunkData.getType(x, y, z) == Material.GRASS_BLOCK)
                    getTwilightGenerator().set(chunkData, x, y + 1, z, StructureType.TERRAIN_DECORATIONAL);

                if (isSnow && MathLib.chanceOf(25))
                    chunkData.setBlock(x, y, z, Material.SNOW);

                getTwilightGenerator().set(chunkData, x, y - 1, z, isMountain ? StructureType.TERRAIN_MOUNTAIN_UNDERGROUND : StructureType.TERRAIN_UNDERGROUND);

                if (y <= waterLevel)
                    for (int actualY = y; actualY <= waterLevel; actualY++)
                        if (chunkData.getType(x, actualY, z) == Material.AIR)
                            chunkData.setBlock(x, actualY, z, Material.WATER);

                if (y <= sandLevel & y >= waterLevel)
                    getTwilightGenerator().set(chunkData, x, y, z, StructureType.TERRAIN_WATER);

                for (int underground = y - 2; underground > 0; underground--)
                    chunkData.setBlock(x, underground, z, Material.STONE);

                biomeGrid.setBiome(finalX, finalZ, Biome.OLD_GROWTH_PINE_TAIGA);
                chunkData.setBlock(x, 0, z, Material.BEDROCK);
            }

        return chunkData;
    }

    @Override
    public boolean isParallelCapable() { return true; }

    public class TwilightPopulator extends BlockPopulator
    {
        @Override
        public void populate(World world, Random random, Chunk chunk)
        {
            final int maxY = getMountainLevel() + 12;

            for (int y = 64; y <= maxY; y++)
            {
                if (y <= getWaterLevel())
                    ShipPopulator.populate(world, random, chunk, y);

                if (y <= getMountainLevel())
                {
                    TowerPopulator.populate(world, random, chunk, y);
                    TreePopulator.populate(world, random, chunk, y);
                    BushPopulator.populate(world, random, chunk, y);
                }

                if (y > getMountainLevel() && y - 12 <= getMountainLevel())
                    MushroomPopulator.populate(world, random, chunk, y);
            }
        }
    }

    public class TwilightPortal
    {
        private final Material @NotNull [] portal = {Material.BLUE_ORCHID, Material.ROSE_BUSH, Material.POPPY, Material.WHITE_TULIP, Material.ORANGE_TULIP, Material.RED_TULIP,
                Material.PINK_TULIP, Material.DANDELION, Material.ALLIUM, Material.AZURE_BLUET, Material.CORNFLOWER, Material.OXEYE_DAISY,
                Material.LILY_OF_THE_VALLEY, Material.PEONY, Material.LILAC, Material.SUNFLOWER, Material.WITHER_ROSE};

        public boolean isPortal(final Location location)
        {
            final Location loc = location.getBlock().getLocation();

            final Block block1 = loc.clone().add(1, 0, 0).getBlock();
            final Block block2 = loc.clone().add(1, 0, 1).getBlock();
            final Block block3 = loc.clone().add(0, 0, 1).getBlock();

            final Block block4 = loc.clone().add(0, 0, 1).getBlock();
            final Block block5 = loc.clone().add(-1, 0, 1).getBlock();
            final Block block6 = loc.clone().add(-1, 0, 0).getBlock();

            final Block block7 = loc.clone().add(0, 0, -1).getBlock();
            final Block block8 = loc.clone().add(-1, 0, -1).getBlock();
            final Block block9 = loc.clone().add(-1, 0, 0).getBlock();

            final Block block10 = loc.clone().add(1, 0, 0).getBlock();
            final Block block11 = loc.clone().add(1, 0, -1).getBlock();
            final Block block12 = loc.clone().add(0, 0, -1).getBlock();

            List<Block> blocks = new ArrayList<>();

            if (block1.getType() == Material.WATER && block2.getType() == Material.WATER && block3.getType() == Material.WATER)
            {
                final Block block1s = loc.clone().add(2, 0, 0).getBlock();
                final Block block2s = loc.clone().add(2, 0, 1).getBlock();
                final Block block3s = loc.clone().add(1, 0, 2).getBlock();
                final Block block4s = loc.clone().add(0, 0, 2).getBlock();
                final Block block5s = loc.clone().add(-1, 0, 1).getBlock();
                final Block block6s = loc.clone().add(-1, 0, 0).getBlock();
                final Block block7s = loc.clone().add(0, 0, -1).getBlock();
                final Block block8s = loc.clone().add(1, 0, -1).getBlock();

                if (block1s.getType() == Material.GRASS_BLOCK && block2s.getType() == Material.GRASS_BLOCK && block3s.getType() == Material.GRASS_BLOCK &&
                        block4s.getType() == Material.GRASS_BLOCK && block5s.getType() == Material.GRASS_BLOCK && block6s.getType() == Material.GRASS_BLOCK &&
                        block7s.getType() == Material.GRASS_BLOCK && block8s.getType() == Material.GRASS_BLOCK)
                {
                    final Block[] blocksTo = {block1s, block2s, block3s, block4s, block5s, block6s, block7s, block8s};
                    blocks.addAll(Arrays.asList(blocksTo));
                }
            }
            else if (block4.getType() == Material.WATER && block5.getType() == Material.WATER && block6.getType() == Material.WATER)
            {
                final Block block1s = loc.clone().add(1, 0, 0).getBlock();
                final Block block2s = loc.clone().add(1, 0, 1).getBlock();
                final Block block3s = loc.clone().add(2, 0, 0).getBlock();
                final Block block4s = loc.clone().add(2, 0, -1).getBlock();
                final Block block5s = loc.clone().add(-2, 0, 1).getBlock();
                final Block block6s = loc.clone().add(-2, 0, 0).getBlock();
                final Block block7s = loc.clone().add(0, 0, -1).getBlock();
                final Block block8s = loc.clone().add(-1, 0, -1).getBlock();

                if (block1s.getType() == Material.GRASS_BLOCK && block2s.getType() == Material.GRASS_BLOCK && block3s.getType() == Material.GRASS_BLOCK &&
                        block4s.getType() == Material.GRASS_BLOCK && block5s.getType() == Material.GRASS_BLOCK && block6s.getType() == Material.GRASS_BLOCK &&
                        block7s.getType() == Material.GRASS_BLOCK && block8s.getType() == Material.GRASS_BLOCK)
                {
                    final Block[] blocksTo = {block1s, block2s, block3s, block4s, block5s, block6s, block7s, block8s};
                    blocks.addAll(Arrays.asList(blocksTo));
                }
            }
            else if (block7.getType() == Material.WATER && block8.getType() == Material.WATER && block9.getType() == Material.WATER)
            {
                final Block block1s = loc.clone().add(1, 0, 0).getBlock();
                final Block block2s = loc.clone().add(1, 0, -1).getBlock();
                final Block block3s = loc.clone().add(1, 0, 0).getBlock();
                final Block block4s = loc.clone().add(1, 0, -1).getBlock();
                final Block block5s = loc.clone().add(-2, 0, 0).getBlock();
                final Block block6s = loc.clone().add(-2, 0, -1).getBlock();
                final Block block7s = loc.clone().add(0, 0, -2).getBlock();
                final Block block8s = loc.clone().add(-1, 0, -2).getBlock();

                if (block1s.getType() == Material.GRASS_BLOCK && block2s.getType() == Material.GRASS_BLOCK && block3s.getType() == Material.GRASS_BLOCK &&
                        block4s.getType() == Material.GRASS_BLOCK && block5s.getType() == Material.GRASS_BLOCK && block6s.getType() == Material.GRASS_BLOCK &&
                        block7s.getType() == Material.GRASS_BLOCK && block8s.getType() == Material.GRASS_BLOCK)
                {
                    final Block[] blocksTo = {block1s, block2s, block3s, block4s, block5s, block6s, block7s, block8s};
                    blocks.addAll(Arrays.asList(blocksTo));
                }
            }
            else if (block10.getType() == Material.WATER && block11.getType() == Material.WATER && block12.getType() == Material.WATER)
            {
                final Block block1s = loc.clone().add(2, 0, 0).getBlock();
                final Block block2s = loc.clone().add(2, 0, -1).getBlock();
                final Block block3s = loc.clone().add(1, 0, -2).getBlock();
                final Block block4s = loc.clone().add(0, 0, -2).getBlock();
                final Block block5s = loc.clone().add(-1, 0, 0).getBlock();
                final Block block6s = loc.clone().add(-1, 0, -1).getBlock();
                final Block block7s = loc.clone().add(0, 0, 1).getBlock();
                final Block block8s = loc.clone().add(1, 0, 1).getBlock();

                if (block1s.getType() == Material.GRASS_BLOCK && block2s.getType() == Material.GRASS_BLOCK && block3s.getType() == Material.GRASS_BLOCK &&
                        block4s.getType() == Material.GRASS_BLOCK && block5s.getType() == Material.GRASS_BLOCK && block6s.getType() == Material.GRASS_BLOCK &&
                        block7s.getType() == Material.GRASS_BLOCK && block8s.getType() == Material.GRASS_BLOCK)
                {
                    final Block[] blocksTo = {block1s, block2s, block3s, block4s, block5s, block6s, block7s, block8s};
                    blocks.addAll(Arrays.asList(blocksTo));
                }
            }

            int flowers = 0;

            for (Block block : blocks)
                for (Material material : portal)
                    if (block.getLocation().clone().add(0, 1, 0).getBlock().getType() == material)
                        flowers++;

            return flowers == 8;
        }
    }

    public enum StructureType
    {
        TERRAIN, TERRAIN_WATER, TERRAIN_UNDERWATER, TERRAIN_UNDERGROUND, TERRAIN_DECORATIONAL, TERRAIN_MOUNTAIN, TERRAIN_MOUNTAIN_PEAK, TERRAIN_MOUNTAIN_UNDERGROUND,
        TWILIGHT_TREE_1, TWILIGHT_TREE_2,
        TWILIGHT_BUSH_1, TWILIGHT_BUSH_2,
        TWILIGHT_MUSHROOM_1, TWILIGHT_MUSHROOM_2,
        TWILIGHT_SHIP,
        TWILIGHT_TOWER
    }

    public class TwilightGenerator
    {
        public TwilightGenerator() {}

        public void set(final ChunkData chunkData, final int x, final int y, final int z, final StructureType structureType)
        {
            switch (structureType)
            {
                case TERRAIN -> chunkData.setBlock(x, y, z, MathLib.chanceOf(5) ? Material.PODZOL : Material.GRASS_BLOCK);
                case TERRAIN_WATER -> chunkData.setBlock(x, y, z, MathLib.chanceOf(15) ? Material.SANDSTONE : Material.SAND);
                case TERRAIN_UNDERWATER -> chunkData.setBlock(x, y, z, MathLib.chanceOf(30) ? Material.DIRT : MathLib.chanceOf(15) ? Material.GRAVEL : Material.SAND);
                case TERRAIN_UNDERGROUND -> chunkData.setBlock(x, y, z, MathLib.chanceOf(15) ? Material.STONE : MathLib.chanceOf(10) ? Material.COARSE_DIRT : Material.DIRT);
                case TERRAIN_DECORATIONAL -> chunkData.setBlock(x, y, z, MathLib.chanceOf(60) ? Material.GRASS : MathLib.chanceOf(40) ? Material.FERN : MathLib.chanceOf(1) ? Material.SWEET_BERRY_BUSH : Material.AIR);
                case TERRAIN_MOUNTAIN -> chunkData.setBlock(x, y, z, MathLib.chanceOf(30) ? Material.ANDESITE : MathLib.chanceOf(20) ? Material.COBBLESTONE : Material.STONE);
                case TERRAIN_MOUNTAIN_PEAK -> chunkData.setBlock(x, y, z, MathLib.chanceOf(10) ? Material.COBBLESTONE : MathLib.chanceOf(15) ? Material.TUFF : Material.SNOW_BLOCK);
                case TERRAIN_MOUNTAIN_UNDERGROUND -> chunkData.setBlock(x, y, z, MathLib.chanceOf(10) ? Material.ANDESITE : MathLib.chanceOf(1) ? Material.EMERALD_ORE : Material.STONE);
            }
        }

        public void generate(final World world, final int x, final int y, final int z, final StructureType structureType)
        {
            final FileManager.Schematica schematica = FileManager.getInstance().getSchematica();
            final Location location = new Location(world, x, y, z);

            switch (structureType)
            {
                case TWILIGHT_TREE_1 -> {
                    if (!WorldHandler.getInstance().getBlockAnalyzer().isNearBlock(world, x, y, z, Material.STRIPPED_DARK_OAK_LOG, 3))
                        schematica.loadSchematic("twilight_tree_1", location);
                }
                case TWILIGHT_TREE_2 -> {
                    if (!WorldHandler.getInstance().getBlockAnalyzer().isNearBlock(world, x, y, z, Material.STRIPPED_DARK_OAK_LOG, 4))
                        schematica.loadSchematic("twilight_tree_2", location);
                }
                case TWILIGHT_BUSH_1 -> {
                    if (!WorldHandler.getInstance().getBlockAnalyzer().isNearBlock(world, x, y, z, Material.STRIPPED_DARK_OAK_LOG, 2))
                        schematica.loadSchematic("twilight_bush_1", location);
                }
                case TWILIGHT_BUSH_2 -> {
                    if (!WorldHandler.getInstance().getBlockAnalyzer().isNearBlock(world, x, y, z, Material.STRIPPED_DARK_OAK_LOG, 2))
                        schematica.loadSchematic("twilight_bush_2", location);
                }
                case TWILIGHT_MUSHROOM_1 -> {
                    if (!WorldHandler.getInstance().getBlockAnalyzer().isNearBlock(world, x, y, z, Material.RED_MUSHROOM_BLOCK, 3) &&
                            !WorldHandler.getInstance().getBlockAnalyzer().isNearBlock(world, x, y, z, Material.STRIPPED_DARK_OAK_LOG, 3))
                        schematica.loadSchematic("twilight_mushroom_1", location);
                }
                case TWILIGHT_MUSHROOM_2 -> {
                    if (!WorldHandler.getInstance().getBlockAnalyzer().isNearBlock(world, x, y, z, Material.RED_MUSHROOM_BLOCK, 3) &&
                            !WorldHandler.getInstance().getBlockAnalyzer().isNearBlock(world, x, y, z, Material.STRIPPED_DARK_OAK_LOG, 4))
                        schematica.loadSchematic("twilight_mushroom_2", location);
                }
                case TWILIGHT_SHIP -> {
                    if (!WorldHandler.getInstance().getBlockAnalyzer().isNearBlock(world, x, y, z, Material.SPRUCE_PLANKS, 24))
                    {
                        schematica.loadSchematic("twilight_ship", location);

                        if ((boolean) FileManager.getInstance().getConfigValue("function.twilight.mobs.evil-pirate"))
                            for (int i = 0; i < 3; i++)
                                TaskLib.getInstance().runSyncLater(() -> new CreatureSpawnEvent(EvilPirate.summon(new Location(world, x + 2, y + 3, z)), CreatureSpawnEvent.SpawnReason.REINFORCEMENTS), 1);

                        if ((boolean) FileManager.getInstance().getConfigValue("function.twilight.mobs.dark-pirate"))
                            TaskLib.getInstance().runSyncLater(() -> new CreatureSpawnEvent(DarkPirate.summon(new Location(world, x + 2, y + 3, z)), CreatureSpawnEvent.SpawnReason.REINFORCEMENTS), 1);
                    }
                }
                case TWILIGHT_TOWER -> {
                    if (!WorldHandler.getInstance().getBlockAnalyzer().isNearBlock(world, x, y, z, Material.STONE_BRICKS, 24))
                    {
                        schematica.loadSchematic("twilight_tower", location);

                        if ((boolean) FileManager.getInstance().getConfigValue("function.twilight.mobs.dark-wizard"))
                            TaskLib.getInstance().runSyncLater(() -> new CreatureSpawnEvent(DarkWizard.summon(new Location(world, x, y + 43, z - 2)), CreatureSpawnEvent.SpawnReason.REINFORCEMENTS), 1);
                    }
                }
            }
        }
    }
}