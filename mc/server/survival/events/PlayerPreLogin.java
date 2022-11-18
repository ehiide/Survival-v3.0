package mc.server.survival.events;

import mc.server.survival.libraries.ChatLib;
import mc.server.survival.libraries.ServerLib;
import mc.server.survival.managers.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PlayerPreLogin implements Listener
{
    @EventHandler
    public void onEvent(AsyncPlayerPreLoginEvent event)
    {
        if ((boolean) FileManager.getInstance().getConfigValue("function.authorization.anti-vpn"))
            if (ServerLib.isVPNConnection(event.getAddress()))
            {
                event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
                event.setKickMessage(ChatLib.ColorUtil.formatHEX("\n#fc7474██&f█#fc7474██\n#fc7474██&f█#fc7474██\n#fc7474██&f█#fc7474██\n#fc7474█████\n#fc7474██&f█#fc7474██\n\n" +
                        "#f83044&lUTRACONO POLACZENIE\n#fc7474Zostales wyrzucony z serwera!\n\n#8c8c8cPowod: #fc7474Autoryzacja (VPN)\n#8c8c8cSprawca: #fc7474Server" +
                        "\n\n#666666&o(Nastepnym razem wymiar kary moze byc surowszy!)"));
                return;
            }

        event.setLoginResult(AsyncPlayerPreLoginEvent.Result.ALLOWED);
    }
}