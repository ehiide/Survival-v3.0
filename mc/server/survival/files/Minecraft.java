package mc.server.survival.files;

import org.bukkit.Server;

public class Minecraft
{
    private Minecraft() {}

    static Minecraft instance = new Minecraft();

    public static Minecraft getInstance()
    {
        return instance;
    }

    private final String[] VERSIONS = {"1.17", "1.18", "1.19"};

    public String getBukkitVersion(final Server server)
    {
        return server.getBukkitVersion();
    }

    public String getVersion(final Server server)
    {
        return server.getBukkitVersion().substring(0, 4);
    }

    /**
        From version 1.20 we will probably remove supported versions and make 1.20 native version.
        On the other hand we can create item converter or spigot will do it itself.
     */

    public boolean benchmarkProtocols(final Server server)
    {
        final String catchUpVersion = getVersion(server);

        for (String version : VERSIONS)
            if (catchUpVersion.equalsIgnoreCase(version))
                return true; return false;
    }
}