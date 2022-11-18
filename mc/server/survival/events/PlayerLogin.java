package mc.server.survival.events;

import mc.server.survival.libraries.ChatLib;
import mc.server.survival.libraries.ServerLib;
import mc.server.survival.libraries.TaskLib;
import mc.server.survival.libraries.TimeLib;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import mc.server.survival.managers.FileManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.List;

public class PlayerLogin implements Listener
{
    @EventHandler
    public void onEvent(PlayerLoginEvent event)
    {
        Player player = event.getPlayer();

        if ((boolean) FileManager.getInstance().getConfigValue("function.whitelist.status"))
        {
            List<String> players = (List<String>) FileManager.getInstance().whitelist().get("whitelist");

            if (!players.contains(player.getName().toLowerCase()))
            {
                if ((boolean) FileManager.getInstance().getConfigValue("function.whitelist.blocked-connection-notify"))
                    ChatManager.sendAdminMessage(FileManager.getInstance().getConfigValue("global.survival.prefixes.chat") + "&eGracz " + player.getName() + " probowal sie wpierdolic na serwer, ale zapomnial, ze nie ma go na liscie!");

                event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
                event.setKickMessage(ChatLib.ColorUtil.formatHEX("\n#fc7474██&f█#fc7474██\n#fc7474██&f█#fc7474██\n#fc7474██&f█#fc7474██\n#fc7474█████\n#fc7474██&f█#fc7474██\n\n" +
                        "#f83044&lUTRACONO POLACZENIE\n#fc7474Zostales wyrzucony z serwera!\n\n#8c8c8cPowod: #fc7474Whitelista? Slyszales o tym?!\n#8c8c8cSprawca: #fc7474Server" +
                        "\n\n#666666&o(Nastepnym razem wymiar kary moze byc surowszy!)"));
                return;
            }
        }

        DataManager.getInstance().getLocal(player).handlePlayer();

        if (TimeLib.getDifferenceInSeconds(DataManager.getInstance().getLocal(null).getBan(player.getName())) < DataManager.getInstance().getLocal(null).getBanLength(player.getName()))
        {
            TaskLib.getInstance().runSyncLater(() -> player.kickPlayer(ChatLib.ColorUtil.formatHEX("\n#fc7474██&f█#fc7474██\n#fc7474██&f█#fc7474██\n#fc7474██&f█#fc7474██\n#fc7474█████\n#fc7474██&f█#fc7474██\n\n" +
                    "#f83044&lUTRACONO POLACZENIE\n#fc7474Twoje konto jest zbanowane!")), 3);
            return;
        }

        ServerLib.loginAsync(player);
        event.setResult(PlayerLoginEvent.Result.ALLOWED);
    }
}