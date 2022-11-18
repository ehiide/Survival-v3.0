package mc.server.survival.commands;

import mc.server.survival.files.Main;
import mc.server.survival.managers.DataManager;
import mc.server.survival.managers.FileManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class Commands
{
    private Commands() {}

    static Commands instance = new Commands();

    public static Commands getInstance()
    {
        return instance;
    }

    public CommmandReader getReader(final String command) { return new CommmandReader(command); }

    public LinkedHashSet<String> getCommands()
    {
        return (LinkedHashSet<String>) FileManager.getInstance().settings().getConfigurationSection("global.commands").getKeys(false);
    }

    public List<String> getCommandAliases(String command)
    {
        List<String> aliases = Main.getInstance().getCommand(command).getAliases();
        aliases.add(command);

        return aliases;
    }

    public List<String> getCommandsAliases()
    {
        List<String> commands = new ArrayList<>();

        for (String command : getCommands())
            commands.addAll(getCommandAliases(command));

        return commands;
    }

    public List<String> getAvailableCommands(final Player player)
    {
        final String rank = DataManager.getInstance().getLocal(player).getRank();

        List<String> availableCommands = new ArrayList<>();

        if (rank.equalsIgnoreCase("administrator"))
            for (final String command : getCommands())
                if (getReader(command).isAvailable() && getReader(command).isForAdministrators())
                    availableCommands.addAll(getCommandAliases(command));

        if (rank.equalsIgnoreCase("moderator"))
            for (final String command : getCommands())
                if (getReader(command).isAvailable() && getReader(command).isForModerators())
                    availableCommands.addAll(getCommandAliases(command));

        if (rank.equalsIgnoreCase("gracz"))
            for (final String command : getCommands())
                if (getReader(command).isAvailable() && getReader(command).isForPlayers())
                    availableCommands.addAll(getCommandAliases(command));

        return availableCommands;
    }

    public class CommmandReader
    {
        private String command;

        public CommmandReader(String command) {this.command = command;}

        private boolean getValue(final int position)
        {
            final String data = (String) FileManager.getInstance().getConfigValue("global.commands." + command);
            final String[] values = data.split(", ");

            return values[position].equalsIgnoreCase("true");
        }

        public boolean isAvailable() { return getValue(0); }
        public boolean isForAdministrators() { return getValue(1); }
        public boolean isForModerators() { return getValue(2); }
        public boolean isForPlayers() { return getValue(3); }
    }
}