package mc.server.survival.worlds.twilight.populators;

import mc.server.survival.libraries.MathLib;
import mc.server.survival.managers.FileManager;
import mc.server.survival.worlds.twilight.Twilight;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.Random;

public class TreePopulator
{
    private static final boolean shouldBePopulated = (boolean) FileManager.getInstance().getConfigValue("function.twilight.structures.trees");

    public static void populate(World world, Random random, Chunk chunk, final int y)
    {
        if (!shouldBePopulated) return;

        prepopulate(world, random, chunk, y); prepopulate(world, random, chunk, y);
    }

    private static void prepopulate(final World world, final Random random, final Chunk chunk, final int y)
    {
        final int x = random.nextInt(15);
        final int z = random.nextInt(15);

        final ChunkSnapshot chunkSnapshot = chunk.getChunkSnapshot();
        final Material material = chunkSnapshot.getBlockType(x, y, z);

        final int finalX = chunkSnapshot.getX() * 16 + x;
        final int finalZ = chunkSnapshot.getZ() * 16 + z;

        if (material.isSolid() && material == Material.GRASS_BLOCK)
            if (MathLib.chanceOf(35))
                Twilight.getInstance().getTwilightGenerator().generate(world, finalX, y + 1, finalZ, Twilight.StructureType.TWILIGHT_TREE_1);
            else
                Twilight.getInstance().getTwilightGenerator().generate(world, finalX, y + 1, finalZ, Twilight.StructureType.TWILIGHT_TREE_2);
    }
}