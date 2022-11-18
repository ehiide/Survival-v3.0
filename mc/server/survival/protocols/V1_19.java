package mc.server.survival.protocols;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class V1_19
{
    public GameProfile getGameProfile(Player player) {return ((CraftPlayer) player).getProfile();}

    public EntityPlayer getEntityPlayer(final Player player) {return ((CraftPlayer) player).getHandle(); }
}