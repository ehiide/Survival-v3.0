package mc.server.survival.protocols.packet;

import mc.server.survival.files.Main;
import mc.server.survival.protocols.Protocol;
import net.minecraft.network.protocol.Packet;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class PacketHandler
{
    private PacketHandler() {}

    static PacketHandler instance = new PacketHandler();

    public static PacketHandler getInstance() { return instance; }

    public void sendPacket(final Player player, final Packet<?> packet)
    {
        new Protocol(Main.getInstance().getServer()).getEntityPlayer(player).b.a(packet);
    }

    public void sendPacket(final Player player, final Packet<?>... packets)
    {
        Arrays.stream(packets).forEach(packet -> sendPacket(player, packet));
    }
}