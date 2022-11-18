package mc.server.survival.worlds.twilight.populators;

import mc.server.survival.libraries.MathLib;
import mc.server.survival.managers.FileManager;
import mc.server.survival.worlds.twilight.Twilight;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.Random;

public class ShipPopulator
{
    private static final boolean shouldBePopulated = (boolean) FileManager.getInstance().getConfigValue("function.twilight.structures.twilight-ship");

    public static void populate(World world, Random random, Chunk chunk, final int y)
    {
        if (!shouldBePopulated) return;

        final int x = new Random().nextInt(15);
        final int z = new Random().nextInt(15);

        final ChunkSnapshot chunkSnapshot = chunk.getChunkSnapshot();
        final Material material = chunkSnapshot.getBlockType(x, y, z);

        final int finalX = chunkSnapshot.getX() * 16 + x;
        final int finalZ = chunkSnapshot.getZ() * 16 + z;

        if (chunkSnapshot.getBlockType(x, y + 1, z).isAir() && material == Material.WATER)
            if (MathLib.chanceOf(5))
                Twilight.getInstance().getTwilightGenerator().generate(world, finalX, y - 2, finalZ, Twilight.StructureType.TWILIGHT_SHIP);
    }
}