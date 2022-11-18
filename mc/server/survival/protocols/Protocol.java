package mc.server.survival.protocols;

import com.mojang.authlib.GameProfile;
import mc.server.survival.files.Minecraft;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class Protocol
{
    private Server server;

    public Protocol(Server server)
    {
        this.server = server;
    }

    public String getProtocolVersion()
    {
        return Minecraft.getInstance().getVersion(server);
    }

    public EntityPlayer getEntityPlayer(final Player player)
    {
        if (getProtocolVersion().equalsIgnoreCase("1.17")) return new V1_17().getEntityPlayer(player);
        if (getProtocolVersion().equalsIgnoreCase("1.18")) return new V1_18().getEntityPlayer(player);
        if (getProtocolVersion().equalsIgnoreCase("1.19")) return new V1_19().getEntityPlayer(player);
        return null;
    }

    public GameProfile getGameProfile(final Player player)
    {
        if (getProtocolVersion().equalsIgnoreCase("1.17")) return new V1_17().getGameProfile(player);
        if (getProtocolVersion().equalsIgnoreCase("1.18")) return new V1_18().getGameProfile(player);
        if (getProtocolVersion().equalsIgnoreCase("1.19")) return new V1_19().getGameProfile(player);
        return null;
    }
}