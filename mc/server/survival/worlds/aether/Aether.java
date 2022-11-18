package mc.server.survival.worlds.aether;

import mc.server.survival.files.Main;
import mc.server.survival.libraries.MathLib;
import mc.server.survival.libraries.TaskLib;
import mc.server.survival.managers.FileManager;
import mc.server.survival.worlds.WorldHandler;
import mc.server.survival.worlds.aether.mobs.EtherKing;
import mc.server.survival.worlds.aether.populators.*;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Aether extends ChunkGenerator
{
    @NotNull Main survival;

    private Aether() {}

    static Aether instance = new Aether();

    public static Aether getInstance()
    {
        return instance;
    }

    public Aether(@NotNull String template)
    {
        this.survival = Main.getInstance();
    }

    public int getBaseLevel() { return 192; }
    public int getCloudsLevel() { return getBaseLevel() - 13; }
    public int getFallenEtherLevel() { return 176; }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world)
    {
        return List.of(getAetherPopulator());
    }

    public AetherPortal getAetherPortal() { return new AetherPortal(); }
    public AetherGenerator getAetherGenerator() { return new AetherGenerator(); }
    public AetherPopulator getAetherPopulator() { return new AetherPopulator(); }

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomeGrid)
    {
        ChunkData chunkData = createChunkData(world);

        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(world.getSeed(), 8);
        generator.setScale(0.006);

        for (int x = 0; x < 16; x++)
            for (int z = 0; z < 16; z++)
            {
                final int finalX = chunkX * 16 + x;
                final int finalZ = chunkZ * 16 + z;

                final double amplitude = 0.57;
                final int culling = 22;
                final double frequency = 1.71;

                final int level = getBaseLevel();

                int y = (int) ((generator.noise(finalX, finalZ, frequency, amplitude, true) + 1) * 20D + level - culling);

                if (y < level)
                {
                    chunkData.setBlock(x, y, z, Material.AIR);

                    if (y == level - 4 | y == level - 6 && MathLib.chanceOf(90))
                    {
                        if (MathLib.chanceOf(75))
                            chunkData.setBlock(x, level - 4, z, Material.WHITE_STAINED_GLASS);

                        chunkData.setBlock(x, (level - 4) - 1, z, Material.WHITE_STAINED_GLASS);
                    }

                    if (y == level - 12 || y == level - 13)
                    {
                        getAetherGenerator().set(chunkData, x, y + 4, z, StructureType.TERRAIN_LOWER);
                        getAetherGenerator().set(chunkData, x, y + 5, z, StructureType.TERRAIN_DECORATIONAL_LOWER);
                    }
                }
                else
                {
                    getAetherGenerator().set(chunkData, x, y, z, StructureType.TERRAIN);

                    if (MathLib.chanceOf(20))
                        getAetherGenerator().set(chunkData, x, y + 1, z, StructureType.TERRAIN_DECORATIONAL);

                    getAetherGenerator().set(chunkData, x, y - 1, z, StructureType.TERRAIN_UNDERGROUND);

                    for (int xS = -1; xS <= 1; xS++)
                        for (int zS = -1; zS <= 1; zS++)
                            if (chunkData.getType(x + xS, y - (y - level), z + zS) == Material.AIR)
                                if (MathLib.chanceOf(75))
                                    getAetherGenerator().set(chunkData, x + xS, y - (y - level), z + zS, StructureType.TERRAIN_DEEP_UNDERGROUND);

                    for (int yS = y - 2; yS > level; yS--)
                        getAetherGenerator().set(chunkData, x, yS, z, StructureType.TERRAIN_DEEP_UNDERGROUND);

                    for (int xS = -1; xS <= 1; xS++)
                        for (int zS = -1; zS <= 1; zS++)
                            if (chunkData.getType(x + xS, y - ((y - level) * 4), z + zS) == Material.AIR)
                                if (MathLib.chanceOf(75))
                                {
                                    if (y == level)
                                        getAetherGenerator().set(chunkData, x + xS, y - ((y - level) * 4), z + zS, StructureType.TERRAIN_SIDE);
                                    else
                                        getAetherGenerator().set(chunkData, x + xS, y - ((y - level) * 4), z + zS, StructureType.TERRAIN_VERY_DEEP_UNDERGROUND);

                                    if (y != level)
                                    {
                                        if (chunkData.getType(x + xS, y - ((y - level) * 4) + 1, z + zS) == Material.AIR)
                                            getAetherGenerator().set(chunkData, x + xS, y - ((y - level) * 4) + 1, z + zS, StructureType.TERRAIN_VERY_DEEP_UNDERGROUND);

                                        if (chunkData.getType(x + xS, y - ((y - level) * 4) + 2, z + zS) == Material.AIR)
                                            getAetherGenerator().set(chunkData, x + xS, y - ((y - level) * 4) + 2, z + zS, StructureType.TERRAIN_VERY_DEEP_UNDERGROUND);

                                        if (y > level - 1)
                                        {
                                            for (int xS2 = -2; xS2 <= 2; xS2++)
                                                for (int zS2 = -2; zS2 <= 2; zS2++)
                                                    if (chunkData.getType(x + xS + xS2, y - ((y - level) * 4) + 2, z + zS + zS2) == Material.AIR)
                                                        if (chunkData.getType(x + xS + xS2, y - ((y - level) * 4) + 2 + 1, z + zS + zS2) != Material.AIR)
                                                            getAetherGenerator().set(chunkData, x + xS + xS2, y - ((y - level) * 4) + 2, z + zS + zS2, StructureType.TERRAIN_VERY_DEEP_UNDERGROUND);
                                        }

                                        if (y > level - 1)
                                        {
                                            for (int xS2 = -1; xS2 <= 1; xS2++)
                                                for (int zS2 = -1; zS2 <= 1; zS2++)
                                                    if (chunkData.getType(x + xS + xS2, y - ((y - level) * 4) + 1, z + zS + zS2) == Material.AIR)
                                                        if (chunkData.getType(x + xS + xS2, y - ((y - level) * 4) + 1 + 1, z + zS + zS2) != Material.AIR)
                                                            getAetherGenerator().set(chunkData, x + xS + xS2, y - ((y - level) * 4) + 1, z + zS + zS2, StructureType.TERRAIN_VERY_DEEP_UNDERGROUND);
                                        }
                                    }
                                }

                    int islandCheck = -1;

                    for (int yS = 70; yS < level; yS++)
                        if (chunkData.getType(x, yS, z) == Material.STONE || chunkData.getType(x, yS, z) == Material.COBBLESTONE ||
                                chunkData.getType(x, yS, z) == Material.ANDESITE || chunkData.getType(x, yS, z) == Material.GOLD_ORE)
                            islandCheck = yS;

                    if (islandCheck != -1)
                        for (int yS = islandCheck; yS < level; yS++)
                            getAetherGenerator().set(chunkData, x, yS, z, StructureType.TERRAIN_VERY_DEEP_UNDERGROUND);
                }

                biomeGrid.setBiome(x, y, z, Biome.JUNGLE);
            }

        return chunkData;
    }

    @Override
    public boolean isParallelCapable() { return true; }

    public class AetherPopulator extends BlockPopulator
    {
        @Override
        public void populate(World world, Random random, Chunk chunk)
        {
            final int minY = getFallenEtherLevel();

            for (int y = world.getMaxHeight() - 1; y > minY; y--)
            {
                if (y > getFallenEtherLevel() && y < getBaseLevel())
                {
                    FallenTreePopulator.populate(world, random, chunk, y);
                    FallenSacrumPopulator.populate(world, random, chunk, y);
                }

                if (y >= getCloudsLevel() && y < getBaseLevel())
                {
                    PantheonPopulator.populate(world, random, chunk, y);
                }

                if (y == getBaseLevel())
                {
                    WaterPopulator.populate(world, random, chunk, y);
                    FlowerPopulator.populate(world, random, chunk, y);
                    RuinedPortalPopulator.populate(world, random, chunk, y);
                }

                if (y >= getBaseLevel())
                    TreePopulator.populate(world, random, chunk, y);

                if (y > getBaseLevel())
                    StonePopulator.populate(world, random, chunk, y);
            }
        }
    }

    public class AetherPortal
    {
        private static final @NotNull Material portal = Material.GLOWSTONE;

        public boolean firePortal(final Location location, final BlockFace blockFace)
        {
            if (location.getBlock().getType() != portal) return false;

            for (int x = -4; x < 4; x++)
                for (int z = -4; z < 4; z++)
                    for (int y = -4; y < 4; y++)
                        if (location.clone().add(x, y, z).getBlock().getType() == Material.NETHER_PORTAL)
                            return false;

            int startX = 0, startZ = 0, startY = 0;

            if (blockFace == BlockFace.UP)
                startY = 1;

            if (blockFace == BlockFace.DOWN)
                startY = -1;

            if (blockFace == BlockFace.EAST)
                startX = 1;

            if (blockFace == BlockFace.WEST)
                startX = -2;

            if (blockFace == BlockFace.SOUTH)
                startZ = 1;

            if (blockFace == BlockFace.NORTH)
                startZ = -2;

            List<Block> frames = new ArrayList<>();

            if (startY == 1)
            {
                if (location.clone().add(startX, startY + 3, startZ).getBlock().getType() != Material.GLOWSTONE) return false;

                for (int y = 0; y < 3; y++)
                {
                    for (int x = -1; x <= 1; x = x + 1)
                        for (int z = -1; z <= 1; z = z + 1)
                            if (Math.abs(x) + Math.abs(z) == 1)
                                if (location.clone().add(startX + x, startY + y, startZ + z).getBlock().getType() == Material.GLOWSTONE)
                                {
                                    final Block block = location.clone().add(startX, startY + y, startZ).getBlock();

                                    if (block.getType() == Material.AIR)
                                    {
                                        block.setType(Material.NETHER_PORTAL);
                                        frames.add(block);
                                    }

                                    if (Math.abs(z) > Math.abs(x))
                                        WorldHandler.getInstance().getBlockManipulator().setBlockAxis(location.clone().add(startX, startY + y, startZ).getBlock(), Axis.Z);
                                }

                    for (int x = -2; x <= 2; x = x + 2)
                        for (int z = -2; z <= 2; z = z + 2)
                            if (Math.abs(x) + Math.abs(z) == 2)
                                if (location.clone().add(startX + x, startY + y, startZ + z).getBlock().getType() == Material.GLOWSTONE)
                                {
                                    final int finalX = startX + (x == 2 ? x - 1 : x == -2 ? x + 1 : x);
                                    final int finalZ = startZ + (z == 2 ? z - 1 : z == -2 ? z + 1 : z);

                                    if (y == 2 & location.clone().add(finalX, startY + 3, finalZ).getBlock().getType() != Material.GLOWSTONE
                                            ||
                                            y == 0 & location.clone().add(finalX, 0, finalZ).getBlock().getType() != Material.GLOWSTONE)
                                    {
                                        for (Block block : frames)
                                            block.setType(Material.AIR);

                                        return false;
                                    }

                                    final Block block = location.clone().add(finalX, startY + y, finalZ).getBlock();

                                    if (block.getType() == Material.AIR)
                                    {
                                        block.setType(Material.NETHER_PORTAL);
                                        frames.add(block);
                                    }

                                    if (Math.abs(finalZ) > Math.abs(finalX))
                                        WorldHandler.getInstance().getBlockManipulator().setBlockAxis(block, Axis.Z);
                                }
                }
            }
            else if (startY == -1)
            {
                if (location.clone().add(startX, startY - 3, startZ).getBlock().getType() != Material.GLOWSTONE) return false;

                for (int y = 0; y < 3; y++)
                {
                    for (int x = -1; x <= 1; x = x + 1)
                        for (int z = -1; z <= 1; z = z + 1)
                            if (Math.abs(x) + Math.abs(z) == 1)
                                if (location.clone().add(startX + x, startY - y, startZ + z).getBlock().getType() == Material.GLOWSTONE)
                                {
                                    final Block block = location.clone().add(startX, startY - y, startZ).getBlock();

                                    if (block.getType() == Material.AIR)
                                    {
                                        block.setType(Material.NETHER_PORTAL);
                                        frames.add(block);
                                    }

                                    if (Math.abs(z) > Math.abs(x))
                                        WorldHandler.getInstance().getBlockManipulator().setBlockAxis(location.clone().add(startX, startY - y, startZ).getBlock(), Axis.Z);
                                }

                    for (int x = -2; x <= 2; x = x + 2)
                        for (int z = -2; z <= 2; z = z + 2)
                            if (Math.abs(x) + Math.abs(z) == 2)
                                if (location.clone().add(startX + x, startY - y, startZ + z).getBlock().getType() == Material.GLOWSTONE)
                                {
                                    final int finalX = startX + (x == 2 ? x - 1 : x == -2 ? x + 1 : x);
                                    final int finalZ = startZ + (z == 2 ? z - 1 : z == -2 ? z + 1 : z);

                                    if (y == 2 & location.clone().add(finalX, startY - 3, finalZ).getBlock().getType() != Material.GLOWSTONE
                                            ||
                                            y == 0 & location.clone().add(finalX, 0, finalZ).getBlock().getType() != Material.GLOWSTONE)
                                    {
                                        for (Block block : frames)
                                            block.setType(Material.AIR);

                                        return false;
                                    }

                                    final Block block = location.clone().add(finalX, startY - y, finalZ).getBlock();

                                    if (block.getType() == Material.AIR)
                                    {
                                        block.setType(Material.NETHER_PORTAL);
                                        frames.add(block);
                                    }

                                    if (Math.abs(finalZ) > Math.abs(finalX))
                                        WorldHandler.getInstance().getBlockManipulator().setBlockAxis(block, Axis.Z);
                                }
                }
            }
            else if (startX == 1 || startX == -2)
            {
                if (location.clone().add(startX, 1, 0).getBlock().getType() == Material.GLOWSTONE)
                {
                    if (location.clone().add(startX - 1, startY, startZ).getBlock().getType() == Material.GLOWSTONE &&
                            location.clone().add(startX, startY + 1, startZ).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block = location.clone().add(startX, startY, startZ).getBlock();

                        if (block.getType() == Material.AIR)
                        {
                            block.setType(Material.NETHER_PORTAL);
                            frames.add(block);
                        }
                    }

                    if (location.clone().add(startX + 2, startY, startZ).getBlock().getType() == Material.GLOWSTONE &&
                            location.clone().add(startX + 1, startY + 1, startZ).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block2 = location.clone().add(startX + 1, startY, startZ).getBlock();

                        if (block2.getType() == Material.AIR)
                        {
                            block2.setType(Material.NETHER_PORTAL);
                            frames.add(block2);
                        }
                    }

                    if (location.clone().add(startX - 1, startY - 1, startZ).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block3 = location.clone().add(startX, startY - 1, startZ).getBlock();

                        if (block3.getType() == Material.AIR)
                        {
                            block3.setType(Material.NETHER_PORTAL);
                            frames.add(block3);
                        }
                    }

                    if (location.clone().add(startX + 2, startY - 1, startZ).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block4 = location.clone().add(startX + 1, startY - 1, startZ).getBlock();

                        if (block4.getType() == Material.AIR)
                        {
                            block4.setType(Material.NETHER_PORTAL);
                            frames.add(block4);
                        }
                    }

                    if (location.clone().add(startX - 1, startY - 2, startZ).getBlock().getType() == Material.GLOWSTONE &&
                            location.clone().add(startX, startY - 3, startZ).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block5 = location.clone().add(startX, startY - 2, startZ).getBlock();

                        if (block5.getType() == Material.AIR)
                        {
                            block5.setType(Material.NETHER_PORTAL);
                            frames.add(block5);
                        }
                    }

                    if (location.clone().add(startX + 2, startY - 2, startZ).getBlock().getType() == Material.GLOWSTONE &&
                            location.clone().add(startX + 1, startY - 3, startZ).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block6 = location.clone().add(startX + 1, startY - 2, startZ).getBlock();

                        if (block6.getType() == Material.AIR)
                        {
                            block6.setType(Material.NETHER_PORTAL);
                            frames.add(block6);
                        }
                    }
                }
                else if (location.clone().add(startX, -1, 0).getBlock().getType() == Material.GLOWSTONE)
                {
                    if (location.clone().add(startX - 1, startY, startZ).getBlock().getType() == Material.GLOWSTONE &&
                            location.clone().add(startX, startY - 1, startZ).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block = location.clone().add(startX, startY, startZ).getBlock();

                        if (block.getType() == Material.AIR)
                        {
                            block.setType(Material.NETHER_PORTAL);
                            frames.add(block);
                        }
                    }

                    if (location.clone().add(startX + 2, startY, startZ).getBlock().getType() == Material.GLOWSTONE &&
                            location.clone().add(startX + 1, startY - 1, startZ).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block2 = location.clone().add(startX + 1, startY, startZ).getBlock();

                        if (block2.getType() == Material.AIR)
                        {
                            block2.setType(Material.NETHER_PORTAL);
                            frames.add(block2);
                        }
                    }

                    if (location.clone().add(startX - 1, startY + 1, startZ).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block3 = location.clone().add(startX, startY + 1, startZ).getBlock();

                        if (block3.getType() == Material.AIR)
                        {
                            block3.setType(Material.NETHER_PORTAL);
                            frames.add(block3);
                        }
                    }

                    if (location.clone().add(startX + 2, startY + 1, startZ).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block4 = location.clone().add(startX + 1, startY + 1, startZ).getBlock();

                        if (block4.getType() == Material.AIR)
                        {
                            block4.setType(Material.NETHER_PORTAL);
                            frames.add(block4);
                        }
                    }

                    if (location.clone().add(startX - 1, startY + 2, startZ).getBlock().getType() == Material.GLOWSTONE &&
                            location.clone().add(startX, startY + 3, startZ).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block5 = location.clone().add(startX, startY + 2, startZ).getBlock();

                        if (block5.getType() == Material.AIR)
                        {
                            block5.setType(Material.NETHER_PORTAL);
                            frames.add(block5);
                        }
                    }

                    if (location.clone().add(startX + 2, startY + 2, startZ).getBlock().getType() == Material.GLOWSTONE &&
                            location.clone().add(startX + 1, startY + 3, startZ).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block6 = location.clone().add(startX + 1, startY + 2, startZ).getBlock();

                        if (block6.getType() == Material.AIR)
                        {
                            block6.setType(Material.NETHER_PORTAL);
                            frames.add(block6);
                        }
                    }
                }
                else
                {
                    final Block block1 = location.clone().add(startX, startY, startZ).getBlock();
                    block1.setType(Material.NETHER_PORTAL);
                    frames.add(block1);

                    if (location.clone().add(startX + 2, startY, startZ).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block2 = location.clone().add(startX + 1, startY, startZ).getBlock();

                        if (block2.getType() == Material.AIR)
                        {
                            block2.setType(Material.NETHER_PORTAL);
                            frames.add(block2);
                        }
                    }

                    if (location.clone().add(startX - 1, startY - 1, startZ).getBlock().getType() == Material.GLOWSTONE &&
                            location.clone().add(startX, startY - 2, startZ).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block3 = location.clone().add(startX, startY - 1, startZ).getBlock();

                        if (block3.getType() == Material.AIR)
                        {
                            block3.setType(Material.NETHER_PORTAL);
                            frames.add(block3);
                        }
                    }

                    if (location.clone().add(startX + 1, startY - 2, startZ).getBlock().getType() == Material.GLOWSTONE &&
                            location.clone().add(startX + 2, startY - 1, startZ).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block4 = location.clone().add(startX + 1, startY - 1, startZ).getBlock();

                        if (block4.getType() == Material.AIR)
                        {
                            block4.setType(Material.NETHER_PORTAL);
                            frames.add(block4);
                        }
                    }

                    if (location.clone().add(startX - 1, startY + 1, startZ).getBlock().getType() == Material.GLOWSTONE &&
                            location.clone().add(startX, startY + 2, startZ).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block5 = location.clone().add(startX, startY + 1, startZ).getBlock();

                        if (block5.getType() == Material.AIR)
                        {
                            block5.setType(Material.NETHER_PORTAL);
                            frames.add(block5);
                        }
                    }

                    if (location.clone().add(startX + 1, startY + 2, startZ).getBlock().getType() == Material.GLOWSTONE &&
                            location.clone().add(startX + 2, startY + 1, startZ).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block6 = location.clone().add(startX + 1, startY + 1, startZ).getBlock();

                        if (block6.getType() == Material.AIR)
                        {
                            block6.setType(Material.NETHER_PORTAL);
                            frames.add(block6);
                        }
                    }
                }
            }
            else if (startZ == 1 || startZ == -2)
            {
                if (location.clone().add(startX, 1, startZ).getBlock().getType() == Material.GLOWSTONE)
                {
                    if (location.clone().add(startX, startY, startZ - 1).getBlock().getType() == Material.GLOWSTONE &&
                            location.clone().add(startX, startY + 1, startZ).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block = location.clone().add(startX, startY, startZ).getBlock();

                        if (block.getType() == Material.AIR)
                        {
                            block.setType(Material.NETHER_PORTAL);
                            frames.add(block);
                            WorldHandler.getInstance().getBlockManipulator().setBlockAxis(block, Axis.Z);
                        }
                    }

                    if (location.clone().add(startX, startY, startZ + 2).getBlock().getType() == Material.GLOWSTONE &&
                            location.clone().add(startX, startY + 1, startZ + 1).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block2 = location.clone().add(startX, startY, startZ + 1).getBlock();

                        if (block2.getType() == Material.AIR)
                        {
                            block2.setType(Material.NETHER_PORTAL);
                            frames.add(block2);
                            WorldHandler.getInstance().getBlockManipulator().setBlockAxis(block2, Axis.Z);
                        }
                    }

                    if (location.clone().add(startX, startY - 1, startZ - 1).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block3 = location.clone().add(startX, startY - 1, startZ).getBlock();

                        if (block3.getType() == Material.AIR)
                        {
                            block3.setType(Material.NETHER_PORTAL);
                            frames.add(block3);
                            WorldHandler.getInstance().getBlockManipulator().setBlockAxis(block3, Axis.Z);
                        }
                    }

                    if (location.clone().add(startX, startY - 1, startZ + 2).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block4 = location.clone().add(startX, startY - 1, startZ + 1).getBlock();

                        if (block4.getType() == Material.AIR)
                        {
                            block4.setType(Material.NETHER_PORTAL);
                            frames.add(block4);
                            WorldHandler.getInstance().getBlockManipulator().setBlockAxis(block4, Axis.Z);
                        }
                    }

                    if (location.clone().add(startX, startY - 2, startZ - 1).getBlock().getType() == Material.GLOWSTONE &&
                            location.clone().add(startX, startY - 3, startZ).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block5 = location.clone().add(startX, startY - 2, startZ).getBlock();

                        if (block5.getType() == Material.AIR)
                        {
                            block5.setType(Material.NETHER_PORTAL);
                            frames.add(block5);
                            WorldHandler.getInstance().getBlockManipulator().setBlockAxis(block5, Axis.Z);
                        }
                    }

                    if (location.clone().add(startX, startY - 2, startZ + 2).getBlock().getType() == Material.GLOWSTONE &&
                            location.clone().add(startX, startY - 3, startZ + 1).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block6 = location.clone().add(startX, startY - 2, startZ + 1).getBlock();

                        if (block6.getType() == Material.AIR)
                        {
                            block6.setType(Material.NETHER_PORTAL);
                            frames.add(block6);
                            WorldHandler.getInstance().getBlockManipulator().setBlockAxis(block6, Axis.Z);
                        }
                    }
                }
                else if (location.clone().add(startX, -1, startZ).getBlock().getType() == Material.GLOWSTONE)
                {
                    if (location.clone().add(startX, startY, startZ - 1).getBlock().getType() == Material.GLOWSTONE &&
                            location.clone().add(startX, startY - 1, startZ).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block = location.clone().add(startX, startY, startZ).getBlock();

                        if (block.getType() == Material.AIR)
                        {
                            block.setType(Material.NETHER_PORTAL);
                            frames.add(block);
                            WorldHandler.getInstance().getBlockManipulator().setBlockAxis(block, Axis.Z);
                        }
                    }

                    if (location.clone().add(startX, startY, startZ + 2).getBlock().getType() == Material.GLOWSTONE &&
                            location.clone().add(startX, startY - 1, startZ + 1).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block2 = location.clone().add(startX, startY, startZ + 1).getBlock();

                        if (block2.getType() == Material.AIR)
                        {
                            block2.setType(Material.NETHER_PORTAL);
                            frames.add(block2);
                            WorldHandler.getInstance().getBlockManipulator().setBlockAxis(block2, Axis.Z);
                        }
                    }

                    if (location.clone().add(startX, startY + 1, startZ - 1).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block3 = location.clone().add(startX, startY + 1, startZ).getBlock();

                        if (block3.getType() == Material.AIR)
                        {
                            block3.setType(Material.NETHER_PORTAL);
                            frames.add(block3);
                            WorldHandler.getInstance().getBlockManipulator().setBlockAxis(block3, Axis.Z);
                        }
                    }

                    if (location.clone().add(startX, startY + 1, startZ + 2).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block4 = location.clone().add(startX, startY + 1, startZ + 1).getBlock();

                        if (block4.getType() == Material.AIR)
                        {
                            block4.setType(Material.NETHER_PORTAL);
                            frames.add(block4);
                            WorldHandler.getInstance().getBlockManipulator().setBlockAxis(block4, Axis.Z);
                        }
                    }

                    if (location.clone().add(startX, startY + 2, startZ - 1).getBlock().getType() == Material.GLOWSTONE &&
                            location.clone().add(startX, startY + 3, startZ).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block5 = location.clone().add(startX, startY + 2, startZ).getBlock();

                        if (block5.getType() == Material.AIR)
                        {
                            block5.setType(Material.NETHER_PORTAL);
                            frames.add(block5);
                            WorldHandler.getInstance().getBlockManipulator().setBlockAxis(block5, Axis.Z);
                        }
                    }

                    if (location.clone().add(startX, startY + 2, startZ + 2).getBlock().getType() == Material.GLOWSTONE &&
                            location.clone().add(startX, startY + 3, startZ + 1).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block6 = location.clone().add(startX, startY + 2, startZ + 1).getBlock();

                        if (block6.getType() == Material.AIR)
                        {
                            block6.setType(Material.NETHER_PORTAL);
                            frames.add(block6);
                            WorldHandler.getInstance().getBlockManipulator().setBlockAxis(block6, Axis.Z);
                        }
                    }
                }
                else
                {
                    final Block block1 = location.clone().add(startX, startY, startZ).getBlock();
                    block1.setType(Material.NETHER_PORTAL);
                    frames.add(block1);
                    WorldHandler.getInstance().getBlockManipulator().setBlockAxis(block1, Axis.Z);

                    if (location.clone().add(startX, startY, startZ + 2).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block2 = location.clone().add(startX, startY, startZ + 1).getBlock();

                        if (block2.getType() == Material.AIR)
                        {
                            block2.setType(Material.NETHER_PORTAL);
                            frames.add(block2);
                            WorldHandler.getInstance().getBlockManipulator().setBlockAxis(block2, Axis.Z);
                        }
                    }

                    if (location.clone().add(startX, startY - 1, startZ - 1).getBlock().getType() == Material.GLOWSTONE &&
                            location.clone().add(startX, startY - 2, startZ).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block3 = location.clone().add(startX, startY - 1, startZ).getBlock();

                        if (block3.getType() == Material.AIR)
                        {
                            block3.setType(Material.NETHER_PORTAL);
                            frames.add(block3);
                            WorldHandler.getInstance().getBlockManipulator().setBlockAxis(block3, Axis.Z);
                        }
                    }

                    if (location.clone().add(startX, startY - 2, startZ + 1).getBlock().getType() == Material.GLOWSTONE &&
                            location.clone().add(startX, startY - 1, startZ + 2).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block4 = location.clone().add(startX, startY - 1, startZ + 1).getBlock();

                        if (block4.getType() == Material.AIR)
                        {
                            block4.setType(Material.NETHER_PORTAL);
                            frames.add(block4);
                            WorldHandler.getInstance().getBlockManipulator().setBlockAxis(block4, Axis.Z);
                        }
                    }

                    if (location.clone().add(startX, startY + 1, startZ - 1).getBlock().getType() == Material.GLOWSTONE &&
                            location.clone().add(startX, startY + 2, startZ).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block5 = location.clone().add(startX, startY + 1, startZ).getBlock();

                        if (block5.getType() == Material.AIR)
                        {
                            block5.setType(Material.NETHER_PORTAL);
                            frames.add(block5);
                            WorldHandler.getInstance().getBlockManipulator().setBlockAxis(block5, Axis.Z);
                        }
                    }

                    if (location.clone().add(startX, startY + 2, startZ + 1).getBlock().getType() == Material.GLOWSTONE &&
                            location.clone().add(startX, startY + 1, startZ + 2).getBlock().getType() == Material.GLOWSTONE)
                    {
                        final Block block6 = location.clone().add(startX, startY + 1, startZ + 1).getBlock();

                        if (block6.getType() == Material.AIR)
                        {
                            block6.setType(Material.NETHER_PORTAL);
                            frames.add(block6);
                            WorldHandler.getInstance().getBlockManipulator().setBlockAxis(block6, Axis.Z);
                        }
                    }
                }
            }

            if (frames.size() != 6)
            {
                for (Block block : frames)
                    block.setType(Material.AIR);

                return false;
            }

            return true;
        }

        public Location createPortal(final Location location)
        {
            for (int x = -8; x < 8; x++)
                for (int z = -8; z < 8; z++)
                    for (int y = -8; y < 8; y++)
                        if (location.clone().add(x, y, z).getBlock().getType() == Material.GLOWSTONE)
                            return location.clone().add(x, y, z);

            location.clone().add(-1, 0, 0).getBlock().setType(Material.GRASS_BLOCK);
            location.clone().add(0, 0, 0).getBlock().setType(Material.GRASS_BLOCK);
            location.clone().add(1, 0, 0).getBlock().setType(Material.GRASS_BLOCK);
            location.clone().add(2, 0, 0).getBlock().setType(Material.GRASS_BLOCK);
            location.clone().add(3, 0, 0).getBlock().setType(Material.GRASS_BLOCK);
            location.clone().add(4, 0, 0).getBlock().setType(Material.GRASS_BLOCK);
            location.clone().add(-1, 0, 1).getBlock().setType(Material.GRASS_BLOCK);
            location.clone().add(0, 0, 1).getBlock().setType(Material.GRASS_BLOCK);
            location.clone().add(1, 0, 1).getBlock().setType(Material.GRASS_BLOCK);
            location.clone().add(2, 0, 1).getBlock().setType(Material.GRASS_BLOCK);
            location.clone().add(3, 0, 1).getBlock().setType(Material.GRASS_BLOCK);
            location.clone().add(4, 0, 1).getBlock().setType(Material.GRASS_BLOCK);
            location.clone().add(-1, 0, -1).getBlock().setType(Material.GRASS_BLOCK);
            location.clone().add(0, 0, -1).getBlock().setType(Material.GRASS_BLOCK);
            location.clone().add(1, 0, -1).getBlock().setType(Material.GRASS_BLOCK);
            location.clone().add(2, 0, -1).getBlock().setType(Material.GRASS_BLOCK);
            location.clone().add(3, 0, -1).getBlock().setType(Material.GRASS_BLOCK);
            location.clone().add(4, 0, -1).getBlock().setType(Material.GRASS_BLOCK);
            location.clone().add(0, -1, 0).getBlock().setType(Material.DIRT);
            location.clone().add(1, -1, 0).getBlock().setType(Material.DIRT);
            location.clone().add(2, -1, 0).getBlock().setType(Material.STONE);
            location.clone().add(3, -1, 0).getBlock().setType(Material.DIRT);
            location.clone().add(2, -2, 0).getBlock().setType(Material.STONE);
            location.clone().add(1, -2, 0).getBlock().setType(Material.STONE);
            location.clone().add(1, -3, 0).getBlock().setType(Material.STONE);

            location.clone().add(0, 1, 0).getBlock().setType(portal);
            location.clone().add(1, 1, 0).getBlock().setType(portal);
            location.clone().add(2, 1, 0).getBlock().setType(portal);
            location.clone().add(3, 1, 0).getBlock().setType(portal);

            location.clone().add(0, 5, 0).getBlock().setType(portal);
            location.clone().add(1, 5, 0).getBlock().setType(portal);
            location.clone().add(2, 5, 0).getBlock().setType(portal);
            location.clone().add(3, 5, 0).getBlock().setType(portal);

            location.clone().add(0, 2, 0).getBlock().setType(portal);
            location.clone().add(3, 2, 0).getBlock().setType(portal);
            location.clone().add(0, 3, 0).getBlock().setType(portal);
            location.clone().add(3, 3, 0).getBlock().setType(portal);
            location.clone().add(0, 4, 0).getBlock().setType(portal);
            location.clone().add(3, 4, 0).getBlock().setType(portal);

            location.clone().add(1, 2, 0).getBlock().setType(Material.NETHER_PORTAL);
            location.clone().add(2, 2, 0).getBlock().setType(Material.NETHER_PORTAL);
            location.clone().add(1, 3, 0).getBlock().setType(Material.NETHER_PORTAL);
            location.clone().add(2, 3, 0).getBlock().setType(Material.NETHER_PORTAL);
            location.clone().add(1, 4, 0).getBlock().setType(Material.NETHER_PORTAL);
            location.clone().add(2, 4, 0).getBlock().setType(Material.NETHER_PORTAL);

            return null;
        }

        public void createPlayerPortal(final Player player, final Location location)
        {
            final Location portalLocation = createPortal(location);

            if (portalLocation != null)
                player.teleport(portalLocation);
            else
                player.teleport(location);
        }
    }

    public enum StructureType
    {
        TERRAIN, TERRAIN_LOWER, TERRAIN_SIDE, TERRAIN_UNDERGROUND, TERRAIN_DEEP_UNDERGROUND, TERRAIN_VERY_DEEP_UNDERGROUND, TERRAIN_DECORATIONAL, TERRAIN_DECORATIONAL_LOWER,
        AETHER_FLOWER_1, AETHER_FLOWER_2, AETHER_FLOWER_3,
        AETHER_TREE_1, AETHER_TREE_2, AETHER_TREE_3,
        AETHER_STONE,
        AETHER_RUINED_PORTAL, AETHER_PANTHEON,
        AETHER_SACRUM, AETHER_FALLEN_TREE
    }

    public class AetherGenerator
    {
        public AetherGenerator() {}

        public void set(final ChunkData chunkData, final int x, final int y, final int z, final StructureType structureType)
        {
            switch (structureType)
            {
                case TERRAIN -> chunkData.setBlock(x, y, z, MathLib.chanceOf(75) ? Material.GRASS_BLOCK : MathLib.chanceOf(50) ? Material.ROOTED_DIRT : MathLib.chanceOf(50) ? Material.COARSE_DIRT : MathLib.chanceOf(50) ? Material.COBBLESTONE : Material.STONE);
                case TERRAIN_LOWER -> chunkData.setBlock(x, y, z, MathLib.chanceOf(45) ? Material.TUFF : MathLib.chanceOf(15) ? Material.MOSSY_COBBLESTONE_SLAB : MathLib.chanceOf(35) ? Material.COARSE_DIRT : MathLib.chanceOf(75) ? Material.COBBLESTONE : Material.STONE);
                case TERRAIN_SIDE -> chunkData.setBlock(x, y, z, MathLib.chanceOf(65) ? Material.ANDESITE : MathLib.chanceOf(50) ? Material.ANDESITE_SLAB : MathLib.chanceOf(50) ? Material.DIORITE : MathLib.chanceOf(50) ? Material.DIORITE_SLAB : Material.AIR);
                case TERRAIN_UNDERGROUND -> chunkData.setBlock(x, y, z, MathLib.chanceOf(80) ? Material.DIRT : MathLib.chanceOf(50) ? Material.ROOTED_DIRT : Material.COARSE_DIRT);
                case TERRAIN_DEEP_UNDERGROUND -> chunkData.setBlock(x, y, z, MathLib.chanceOf(60) ? Material.DIRT : MathLib.chanceOf(40) ? Material.ROOTED_DIRT : Material.STONE);
                case TERRAIN_VERY_DEEP_UNDERGROUND -> chunkData.setBlock(x, y, z, MathLib.chanceOf(70) ? Material.ANDESITE : MathLib.chanceOf(60) ? Material.DIORITE : MathLib.chanceOf(55) ? Material.STONE : Material.GOLD_ORE);
                case TERRAIN_DECORATIONAL -> chunkData.setBlock(x, y, z, MathLib.chanceOf(12) ? Material.SWEET_BERRY_BUSH : MathLib.chanceOf(35) ? Material.FERN : MathLib.chanceOf(75) ? Material.GRASS : MathLib.chanceOf(22) ? Material.BLUE_ORCHID : MathLib.chanceOf(17) ? Material.LILY_OF_THE_VALLEY : MathLib.chanceOf(19) ? Material.POPPY : Material.AIR);
                case TERRAIN_DECORATIONAL_LOWER -> chunkData.setBlock(x, y, z, MathLib.chanceOf(0.5) ? Material.COBBLESTONE_WALL : Material.AIR);
            }
        }

        public void generate(final World world, final int x, final int y, final int z, final StructureType structureType)
        {
            final FileManager.Schematica schematica = FileManager.getInstance().getSchematica();
            final Location location = new Location(world, x, y, z);

            switch (structureType) {
                case AETHER_FLOWER_1 -> {
                    if (!WorldHandler.getInstance().getBlockAnalyzer().isNearBlock(world, x, y, z, Material.GREEN_CONCRETE, 4))
                        schematica.loadSchematic("aether_flower_1", location);
                }
                case AETHER_FLOWER_2 -> {
                    if (!WorldHandler.getInstance().getBlockAnalyzer().isNearBlock(world, x, y, z, Material.GREEN_CONCRETE, 4))
                        schematica.loadSchematic("aether_flower_2", location);
                }
                case AETHER_FLOWER_3 -> {
                    if (!WorldHandler.getInstance().getBlockAnalyzer().isNearBlock(world, x, y, z, Material.GREEN_CONCRETE, 4))
                        schematica.loadSchematic("aether_flower_3", location);
                }
                case AETHER_TREE_1 -> {
                    if (!WorldHandler.getInstance().getBlockAnalyzer().isNearBlock(world, x, y, z, Material.STRIPPED_BIRCH_LOG, 4))
                        schematica.loadSchematic("aether_tree_1", location);
                }
                case AETHER_TREE_2 -> {
                    if (!WorldHandler.getInstance().getBlockAnalyzer().isNearBlock(world, x, y, z, Material.STRIPPED_BIRCH_LOG, 4))
                        schematica.loadSchematic("aether_tree_2", location);
                }
                case AETHER_TREE_3 -> {
                    if (!WorldHandler.getInstance().getBlockAnalyzer().isNearBlock(world, x, y, z, Material.STRIPPED_BIRCH_LOG, 3))
                        schematica.loadSchematic("aether_tree_3", location);
                }
                case AETHER_STONE -> schematica.loadSchematic("aether_stone", location);
                case AETHER_RUINED_PORTAL -> schematica.loadSchematic("aether_ruined_portal", location);
                case AETHER_PANTHEON -> {
                    if (!WorldHandler.getInstance().getBlockAnalyzer().isNearBlock(world, x, y, z, Material.DIORITE, 16))
                        schematica.loadSchematic("aether_pantheon", location);

                    if ((boolean) FileManager.getInstance().getConfigValue("function.aether.mobs.ether-king"))
                        TaskLib.getInstance().runSyncLater(() -> new CreatureSpawnEvent(EtherKing.summon(new Location(world, x, y + 5, z - 3)), CreatureSpawnEvent.SpawnReason.REINFORCEMENTS), 1);
                }
                case AETHER_SACRUM -> {
                    if (!WorldHandler.getInstance().getBlockAnalyzer().isNearBlock(world, x, y, z, Material.DEEPSLATE_TILE_WALL, 3) &&
                            !WorldHandler.getInstance().getBlockAnalyzer().isNearBlock(world, x, y, z, Material.DEEPSLATE_TILE_WALL, 8))
                        schematica.loadSchematic("aether_sacrum", location);
                }
                case AETHER_FALLEN_TREE -> {
                    if (!WorldHandler.getInstance().getBlockAnalyzer().isNearBlock(world, x, y, z, Material.SMOOTH_BASALT, 6))
                        schematica.loadSchematic("aether_fallen_tree", location);
                }
            }
        }
    }
}