package mc.server.survival.libraries;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class MathLib
{
    public static boolean chanceOf(final int chance)
    {
        final Random r = new Random();
        final int number = r.nextInt(100);

        return number <= chance;
    }

    public static boolean chanceOf(final float chance)
    {
        if (chance == 0) return false;

        final Random r = new Random();
        final float number = r.nextFloat(100);

        return number <= chance;
    }

    public static boolean chanceOf(final double chance)
    {
        if (chance == 0) return false;

        final Random r = new Random();
        final double number = r.nextDouble(100);

        return number <= chance;
    }

    public static int getBetween(final int min, final int max)
    {
        if (min == 0 || max == 0) return 0;

        if (min > max) return max;

        if (min == max) return min;

        final Random r = new Random();
        return r.nextInt(max - min) + min;
    }

    public static boolean isInteger(final @NotNull String arg)
    {
        try { Integer.parseInt(arg); }
        catch (NumberFormatException e) { return false; } return true;
    }
}