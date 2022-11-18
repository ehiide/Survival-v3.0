package mc.server.survival.worlds.aether.populators;

import mc.server.survival.libraries.MathLib;
import mc.server.survival.managers.FileManager;
import mc.server.survival.worlds.aether.Aether;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.Random;

public class TreePopulator
{
    private static final boolean shouldBePopulated = (boolean) FileManager.getInstance().getConfigValue("function.aether.structures.trees");

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
        {
            if (y == Aether.getInstance().getBaseLevel())
                if (MathLib.chanceOf(85))
                {
                    Aether.getInstance().getAetherGenerator().generate(world, finalX, y + 1, finalZ, Aether.StructureType.AETHER_TREE_3);
                    return;
                }

            if (MathLib.chanceOf(85))
                Aether.getInstance().getAetherGenerator().generate(world, finalX, y + 1, finalZ, Aether.StructureType.AETHER_TREE_1);
            else if (MathLib.chanceOf(60))
                Aether.getInstance().getAetherGenerator().generate(world, finalX, y + 1, finalZ, Aether.StructureType.AETHER_TREE_2);
        }
    }
}