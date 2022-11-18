package mc.server.survival.libraries.java;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class WrappedMap
{
    private @NotNull Map map;

    public WrappedMap(@NotNull Map map)
    {
        this.map = map;
    }

    public static WrappedMap wrapMap(@NotNull Map map)
    {
        return new WrappedMap(map);
    }

    public @NotNull Map getMap() { return this.map; }

    public String print()
    {
        StringBuilder stringBuilder = new StringBuilder();

        for (final Object object : map.keySet())
        {
            if (this.map.size() > 1 & stringBuilder.length() > 0)
                stringBuilder.append(", ");

            stringBuilder.append(object).append("(").append(this.map.get(object)).append(")");
        }

        return stringBuilder.toString();
    }
}