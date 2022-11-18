package mc.server.survival.worlds.twilight.populators;

import mc.server.survival.libraries.MathLib;
import mc.server.survival.managers.FileManager;
import mc.server.survival.worlds.twilight.Twilight;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.Random;

public class MushroomPopulator
{
    private static final boolean shouldBePopulated = (boolean) FileManager.getInstance().getConfigValue("function.twilight.structures.mushrooms");

    public static void populate(World world, Random random, Chunk chunk, final int y)
    {
        if (!shouldBePopulated) return;

        final int x = random.nextInt(15);
        final int z = random.nextInt(15);

        final ChunkSnapshot chunkSnapshot = chunk.getChunkSnapshot();
        final Material material = chunkSnapshot.getBlockType(x, y, z);

        final int finalX = chunkSnapshot.getX() * 16 + x;
        final int finalZ = chunkSnapshot.getZ() * 16 + z;

        if (chunkSnapshot.getBlockType(x, y + 1, z).isAir() && material.isSolid() && material == Material.STONE)
            if (MathLib.chanceOf(3))
                Twilight.getInstance().getTwilightGenerator().generate(world, finalX, y + 1, finalZ, Twilight.StructureType.TWILIGHT_MUSHROOM_1);
            else if (MathLib.chanceOf(2))
                Twilight.getInstance().getTwilightGenerator().generate(world, finalX, y + 1, finalZ, Twilight.StructureType.TWILIGHT_MUSHROOM_2);
    }
}