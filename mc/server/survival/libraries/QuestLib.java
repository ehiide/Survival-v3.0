package mc.server.survival.libraries;

import mc.server.Broadcaster;
import mc.server.survival.libraries.chemistry.Chemistries;
import mc.server.survival.libraries.chemistry.ChemistryDrug;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class QuestLib
{
    private QuestLib() {}

    static QuestLib instance = new QuestLib();

    public static QuestLib getInstance() { return instance; }

    public Reader getReader(final String string) { return new Reader(string); }

    public class Reader
    {
        private final String string;

        public Reader(final String string)
        {
            this.string = string;
        }

        public boolean getStatus()
        {
            return string.substring(1, 2).equalsIgnoreCase("t");
        }

        public String setStatus(final boolean status)
        {
            final String pre = string.substring(0, 1);
            final String post = string.substring(2);

            return pre + (status ? "t" : "f") + post;
        }

        public int getCurrent()
        {
            for (int x = 3; x < string.length(); x++)
                if (string.substring(x, x + 1).equalsIgnoreCase("!"))
                    return Integer.parseInt(string.substring(3, x)); return 0;
        }

        public String addCurrent()
        {
            final String pre = string.substring(0, 3);

            for (int x = 3; x < string.length(); x++)
                if (string.substring(x, x + 1).equalsIgnoreCase("!"))
                    return pre + (Integer.parseInt(string.substring(3, x)) + 1) + string.substring(x); return "#ER@RO!R";
        }

        public int getRequired()
        {
            for (int x = 4; x < string.length(); x++)
                if (string.substring(x -1, x).equalsIgnoreCase("!"))
                    return Integer.parseInt(string.substring(x)); return 0;
        }
    }

    public static void manageQuest(@NotNull final Player player, final int quest)
    {
        if (!getInstance().getReader(DataManager.getInstance().getLocal(player).getQuest(quest)).getStatus())
        {
            DataManager.getInstance().getLocal(player).addQuestPoint(quest);

            if (DataManager.getInstance().getLocal(player).getQuestCompleting(quest) == 100)
            {
                addQuestTreasure(player, quest);
                DataManager.getInstance().getLocal(player).setQuest(quest, getInstance().getReader(DataManager.getInstance().getLocal(player).getQuest(quest)).setStatus(true));
                DataManager.getInstance().getLocal(player).setSP(DataManager.getInstance().getLocal(player).getSP() + getSP(quest));
                ChatManager.sendMessage(player, "#ffc936[★] #80ff1fPomyslnie ukonczyles questa: #ffc936" + getName(quest) + "#80ff1f");

                if (Broadcaster.getInstance().getAnnouncer().announceQuests())
                    Broadcaster.broadcastMessage("#ffc936[★] #8c8c8cGracz " + player.getName() + " pomyslnie ukonczyl questa: #ffc936" + getName(quest));
            }
        }
    }

    public static void addQuestTreasure(@NotNull Player player, final int quest)
    {
        if (quest == 1)
            DataManager.getInstance().getLocal(player).addMoney(50);
        else if (quest == 2)
            DataManager.getInstance().getLocal(player).addMoney(100);
        else if (quest == 3)
            DataManager.getInstance().getLocal(player).addMoney(75);
        else if (quest == 4)
        {
            DataManager.getInstance().getLocal(player).addMoney(50);
            if (InventoryLib.isFullInventory(player))
                player.getWorld().dropItemNaturally(player.getLocation().add(0, 1, 0), ChemistryDrug.getDrug(Chemistries.piwo));
            else
                player.getInventory().addItem(ChemistryDrug.getDrug(Chemistries.piwo));
        }
        else if (quest == 5)
            DataManager.getInstance().getLocal(player).addMoney(100);
        else if (quest == 6)
            DataManager.getInstance().getLocal(player).addMoney(150);
        else if (quest == 7)
            DataManager.getInstance().getLocal(player).addMoney(250);
        else if (quest == 8)
        {
            DataManager.getInstance().getLocal(player).addMoney(200);
            if (InventoryLib.isFullInventory(player))
                player.getWorld().dropItemNaturally(player.getLocation().add(0, 1, 0), ChemistryDrug.getDrug(Chemistries.heroina));
            else
                player.getInventory().addItem(ChemistryDrug.getDrug(Chemistries.heroina));
        }
        else if (quest == 9)
            DataManager.getInstance().getLocal(player).addMoney(200);
        else if (quest == 10)
            DataManager.getInstance().getLocal(player).addMoney(300);
        else if (quest == 11)
            DataManager.getInstance().getLocal(player).addMoney(250);
        else if (quest == 12)
            DataManager.getInstance().getLocal(player).addMoney(450);
        else if (quest == 13)
            DataManager.getInstance().getLocal(player).addMoney(500);
        else if (quest == 14)
            DataManager.getInstance().getLocal(player).addMoney(600);

        ScoreboardLib.getInstance().reloadContents(player);
    }

    public static int getSP(final int quest)
    {
        if (quest > 6 && quest <= 11)
            return 2;
        else if (quest > 11)
            return 3;

        return 1;
    }

    public static String getName(final int quest)
    {
        if (quest == 1)
            return "Drwal";

        if (quest == 2)
            return "Zbieracz kamykow";

        if (quest == 3)
            return "Szpadel";

        if (quest == 4)
            return "Najeb sie!";

        if (quest == 5)
            return "Lowca";

        if (quest == 6)
            return "Psychopata";

        if (quest == 7)
            return "Poszukiwacz";

        if (quest == 8)
            return "Psychonauta";

        if (quest == 9)
            return "MLG";

        if (quest == 10)
            return "Spoceniec";

        if (quest == 11)
            return "Ogrodnik";

        if (quest == 12)
            return "Zakochani po uszy";

        if (quest == 13)
            return "Partia serwera";

        return "Piraci z karaibow";
    }

    public static int getCompletedQuests(final Player player)
    {
        int quests = 0;

        for (int quest = 1; quest <= 14; quest++)
            if (getInstance().getReader(DataManager.getInstance().getLocal(player).getQuest(quest)).getStatus())
                quests++;

        return quests;
    }
}