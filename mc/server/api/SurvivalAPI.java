package mc.server.api;

import mc.server.survival.events.AsyncChat;
import mc.server.survival.files.Main;
import mc.server.survival.managers.DataManager;
import mc.server.survival.managers.FileManager;
import mc.server.survival.protocols.Protocol;
import mc.server.survival.worlds.WorldHandler;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@SuppressWarnings("unused")
public class SurvivalAPI
{
    private SurvivalAPI() {}

    static SurvivalAPI api;

    public static SurvivalAPI getAPI() { return api; }

    public Plugin getPlugin() { return Bukkit.getPluginManager().getPlugin(Main.getInstance().getName()); }

    public DataManager.LocalData getData(final Player player)
    {
        return DataManager.getInstance().getLocal(player);
    }

    public DataManager.ServerData getServerData() {return DataManager.getInstance().getData(); }

    public void sudoMessage(final Player player, String message)
    {
        new AsyncChat().compileMessage(message, player);
    }

    public Protocol getProtocol(final Server server) { return new Protocol(server); }

    public PluginCommand getCommand(final String name) { return Main.getInstance().getCommand(name); }

    public Object getDimension(final WorldHandler.DimensionType dimensionType, final String template)
    {
        return (dimensionType == WorldHandler.DimensionType.AETHER) ? WorldHandler.getInstance().getAetherDimension(template) : WorldHandler.getInstance().getTwilightDimension(template);
    }

    public FileConfiguration getFileConfiguration(final FileManager.FileType fileType)
    {
        final FileManager fileManager = FileManager.getInstance();

        switch (fileType)
        {
            case PLAYERS -> { return fileManager.players(); }
            case GANGS -> { return fileManager.gangs(); }
            case RECIPES -> { return fileManager.recipes(); }
            case TRADES -> { return fileManager.trades(); }
            case WHITELIST -> { return fileManager.whitelist(); }
        }   return null;
    }
}