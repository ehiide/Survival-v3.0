package mc.server.survival.events;

import mc.server.survival.commands.Commands;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.Collection;

public class CommandSend implements Listener
{
    @EventHandler
    public void onEvent(PlayerCommandSendEvent event)
    {
        final Player player = event.getPlayer();
        Collection<String> commands = event.getCommands();

        commands.clear();
        commands.addAll(Commands.getInstance().getAvailableCommands(player));
    }
}