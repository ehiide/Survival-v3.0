package mc.server.survival.events;

import mc.server.survival.libraries.*;
import mc.server.survival.libraries.chemistry.Chemistries;
import mc.server.survival.libraries.chemistry.ChemistryDrug;
import mc.server.survival.managers.ChatManager;
import mc.server.survival.managers.DataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class PlayerFish implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEvent(PlayerFishEvent event)
    {
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH)
        {
            Player player = event.getPlayer();

            if (MathLib.chanceOf(1 + (DataManager.getInstance().getLocal(player).getSerotonine() / 10) + DataManager.getInstance().getLocal(player).getUpgradeLevel(player.getName(), "luck")))
                legendaryLootFound(player);
            else if (MathLib.chanceOf(5 + (DataManager.getInstance().getLocal(player).getSerotonine() / 10)))
                mysteryLootFound(player);
            else if (MathLib.chanceOf(12 + (DataManager.getInstance().getLocal(player).getSerotonine() / 10)))
                lootFound(player);
        }
    }

    private void lootFound(final Player player)
    {
        ArrayList<String> loot = new ArrayList<>();

        int money = 10 + new Random().nextInt(50);
        boolean piwo = MathLib.chanceOf(50);

        loot.add("#fcff33" + money + " monet(y)&7");
        if (piwo)
            loot.add("&ePiwo &f(x1)&7");

        String loots = loot.toString().substring(1, loot.toString().length() - 1);

        DataManager.getInstance().getLocal(player).addMoney(money);
        ScoreboardLib.getInstance().reloadContents(player);

        if (piwo)
            assumeLoot(player, ChemistryDrug.getDrug(Chemistries.piwo));

        ChatManager.sendMessage(player, "&e[★] #ffc936Wyglada na to, ze znalazles morski skarb, a oto jego zawartosc: " + loots);
    }

    private void mysteryLootFound(final Player player)
    {
        ArrayList<String> loot = new ArrayList<>();

        int money = 20 + new Random().nextInt(100);
        boolean piwo = MathLib.chanceOf(70);
        boolean wino = MathLib.chanceOf(50);
        boolean szampan = MathLib.chanceOf(30);
        boolean ksiazka = MathLib.chanceOf(0.1);

        loot.add("#fcff33" + money + " monet(y)&7");
        if (piwo)
            loot.add("&ePiwo &f(x1)&7");
        if (wino)
            loot.add("&eWino &f(x1)&7");
        if (szampan)
            loot.add("&eSzampan &f(x1)&7");
        if (ksiazka)
            loot.add("&aEnchanted Book &f(x1)&7");

        String loots = loot.toString().substring(1, loot.toString().length() - 1);

        DataManager.getInstance().getLocal(player).addMoney(money);
        ScoreboardLib.getInstance().reloadContents(player);

        if (piwo)
            assumeLoot(player, ChemistryDrug.getDrug(Chemistries.piwo));

        if (wino)
            assumeLoot(player, ChemistryDrug.getDrug(Chemistries.wino));

        if (szampan)
            assumeLoot(player, ChemistryDrug.getDrug(Chemistries.szampan));

        if (ksiazka)
            assumeLoot(player, MathLib.chanceOf(50) ? new ItemLib().get("book:vampirism") :
                            MathLib.chanceOf(50) ? new ItemLib().get("book:ice_aspect") :
                            MathLib.chanceOf(50) ? new ItemLib().get("book:unbreakable") :
                            MathLib.chanceOf(50) ? new ItemLib().get("book:fire_walker") :
                            MathLib.chanceOf(50) ? new ItemLib().get("book:ignite") :
                            MathLib.chanceOf(50) ? new ItemLib().get("book:strike") :
                            MathLib.chanceOf(50) ? new ItemLib().get("book:instant_mining") : new ItemLib().get("book:explode_hash"));

        ChatManager.sendMessage(player, "&e[★] #ffc936Wyglada na to, ze znalazles mityczny morski skarb, a oto jego zawartosc: " + loots);
    }

    private void legendaryLootFound(final Player player)
    {
        ArrayList<String> loot = new ArrayList<>();

        int money = 50 + new Random().nextInt(150);
        boolean piwo = MathLib.chanceOf(90);
        boolean wino = MathLib.chanceOf(70);
        boolean szampan = MathLib.chanceOf(50);
        boolean whisky = MathLib.chanceOf(30);
        boolean xanax = MathLib.chanceOf(10);
        boolean ksiazka = MathLib.chanceOf(1);

        loot.add("#fcff33" + money + " monet(y)&7");
        if (piwo)
            loot.add("&ePiwo &f(x1)&7");
        if (wino)
            loot.add("&eWino &f(x1)&7");
        if (szampan)
            loot.add("&eSzampan &f(x1)&7");
        if (whisky)
            loot.add("&eWhisky &f(x1)&7");
        if (xanax)
            loot.add("&aAlprazolam &f(x1)&7");
        if (ksiazka)
            loot.add("&aEnchanted Book &f(x1)&7");

        String loots = loot.toString().substring(1, loot.toString().length() - 1);

        DataManager.getInstance().getLocal(player).addMoney(money);
        ScoreboardLib.getInstance().reloadContents(player);

        if (piwo)
            assumeLoot(player, ChemistryDrug.getDrug(Chemistries.piwo));

        if (wino)
            assumeLoot(player, ChemistryDrug.getDrug(Chemistries.wino));

        if (szampan)
            assumeLoot(player, ChemistryDrug.getDrug(Chemistries.szampan));

        if (whisky)
            assumeLoot(player, ChemistryDrug.getDrug(Chemistries.whisky));

        if (xanax)
            assumeLoot(player, ChemistryDrug.getDrug(Chemistries.alprazolam));

        if (ksiazka)
            assumeLoot(player, MathLib.chanceOf(50) ? new ItemLib().get("book:vampirism") :
                            MathLib.chanceOf(50) ? new ItemLib().get("book:ice_aspect") :
                            MathLib.chanceOf(50) ? new ItemLib().get("book:unbreakable") :
                            MathLib.chanceOf(50) ? new ItemLib().get("book:fire_walker") :
                            MathLib.chanceOf(50) ? new ItemLib().get("book:ignite") :
                            MathLib.chanceOf(50) ? new ItemLib().get("book:strike") :
                            MathLib.chanceOf(50) ? new ItemLib().get("book:instant_mining") : new ItemLib().get("book:explode_hash"));

        ChatManager.sendMessage(player, "&e[★] #ffc936Wyglada na to, ze znalazles legendarny morski skarb, a oto jego zawartosc: " + loots);
        QuestLib.manageQuest(player, 14);
    }

    private void assumeLoot(final Player player, final ItemStack itemStack)
    {
        if (InventoryLib.isFullInventory(player))
            player.getWorld().dropItemNaturally(player.getLocation().add(0, 1, 0), itemStack);
        else
            player.getInventory().addItem(itemStack);
    }
}