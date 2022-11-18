package mc.server.survival.worlds.aether.populators;

import mc.server.survival.libraries.MathLib;
import mc.server.survival.managers.FileManager;
import mc.server.survival.worlds.aether.Aether;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.Random;

public class StonePopulator
{
    private static final boolean shouldBePopulated = (boolean) FileManager.getInstance().getConfigValue("function.aether.structures.stones");

    public static void populate(final World world, final Random random, final Chunk chunk, final int y)
    {
        if (!shouldBePopulated) return;

        final int x = random.nextInt(15);
        final int z = random.nextInt(15);

        final ChunkSnapshot chunkSnapshot = chunk.getChunkSnapshot();
        final Material material = chunkSnapshot.getBlockType(x, y, z);

        final int finalX = chunkSnapshot.getX() * 16 + x;
        final int finalZ = chunkSnapshot.getZ() * 16 + z;

        if (material.isSolid() && material == Material.GRASS_BLOCK)
            if (MathLib.chanceOf(6))
                Aether.getInstance().getAetherGenerator().generate(world, finalX, y, finalZ, Aether.StructureType.AETHER_STONE);
    }
}