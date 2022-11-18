package mc.server.survival.worlds.aether.populators;

import mc.server.survival.libraries.MathLib;
import mc.server.survival.managers.FileManager;
import mc.server.survival.worlds.WorldHandler;
import mc.server.survival.worlds.aether.Aether;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.Random;

public class PantheonPopulator
{
    private static final boolean shouldBePopulated = (boolean) FileManager.getInstance().getConfigValue("function.aether.structures.pantheon");

    public static void populate(final World world, final Random random, final Chunk chunk, final int y)
    {
        if (!shouldBePopulated) return;

        final int x = random.nextInt(15);
        final int z = random.nextInt(15);

        final ChunkSnapshot chunkSnapshot = chunk.getChunkSnapshot();
        final Material material = chunkSnapshot.getBlockType(x, y, z);

        final int finalX = chunkSnapshot.getX() * 16 + x;
        final int finalZ = chunkSnapshot.getZ() * 16 + z;

        if (material.isSolid() && material == Material.WHITE_STAINED_GLASS)
            if (MathLib.chanceOf(1))
                if (!WorldHandler.getInstance().getBlockAnalyzer().isNearBlock(world, finalX, y, finalZ, Material.STONE, 12, 8, 12))
                    Aether.getInstance().getAetherGenerator().generate(world, finalX, y + 1, finalZ, Aether.StructureType.AETHER_PANTHEON);
    }
}