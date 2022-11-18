package mc.server.survival.commands;

import mc.server.Logger;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import mc.server.survival.protocols.packet.PacketHandler;
import net.minecraft.network.protocol.game.PacketPlayOutExplosion;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Crash
implements CommandExecutor, TabCompleter
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
    {
        if (sender instanceof Player)
        {
            final Player player = (Player) sender;

            final String rank = DataManager.getInstance().getLocal(player).getRank();

            if (rank.equalsIgnoreCase("administrator") & !Commands.getInstance().getReader("crash").isForAdministrators() ||
                    rank.equalsIgnoreCase("moderator") & !Commands.getInstance().getReader("crash").isForModerators() ||
                    rank.equalsIgnoreCase("gracz") & !Commands.getInstance().getReader("crash").isForPlayers())
            {
                ChatManager.sendNotification(player, "Nie masz uprawnien do uzycia tej komendy!", ChatManager.NotificationType.ERROR);
                return true;
            }

            if (args.length == 0)
                ChatManager.sendNotification(player, "Ok, ale... kogo?", ChatManager.NotificationType.ERROR);
            else
            {
                final String potencial_player = args[0];

                for (Player dplayer : Bukkit.getOnlinePlayers())
                    if (dplayer.getName().equalsIgnoreCase(potencial_player))
                    {
                        PacketHandler.getInstance().sendPacket(dplayer, new PacketPlayOutExplosion(
                                Double.MAX_VALUE,
                                Double.MAX_VALUE,
                                Double.MAX_VALUE,
                                Float.MAX_VALUE,
                                Collections.EMPTY_LIST,
                                new Vec3D(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE)));
                        return true;
                    }

                ChatManager.sendNotification(player, "Podany gracz nie jest on-line na serwerze!", ChatManager.NotificationType.ERROR);
            }

            return true;
        }
        else if (sender instanceof ConsoleCommandSender)
        {
            if (args.length == 0)
                Logger.log("&cOk, ale... kogo?");
            else
            {
                final String potencial_player = args[0];

                for (Player dplayer : Bukkit.getOnlinePlayers())
                    if (dplayer.getName().equalsIgnoreCase(potencial_player))
                    {
                        PacketHandler.getInstance().sendPacket(dplayer, new PacketPlayOutExplosion(
                                Double.MAX_VALUE,
                                Double.MAX_VALUE,
                                Double.MAX_VALUE,
                                Float.MAX_VALUE,
                                Collections.EMPTY_LIST,
                                new Vec3D(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE)));
                        return true;
                    }

                Logger.log("&cPodany gracz nie jest on-line na serwerze!");
            }

            return true;
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args)
    {
        ArrayList<String> completions = new ArrayList<>();

        if (args.length == 1)
            Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));

        return completions;
    }
}
