package mc.server.survival.libraries.chemistry;

import mc.server.survival.libraries.ChatLib;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;

public class ChemistryDrug
{
    public static ItemStack getDrug(ChemistryItem chemistryItem)
    {
        ItemStack item;

        if (chemistryItem.getType() != null)
            item = new ItemStack(chemistryItem.getType(), 1);
        else
            item = new ItemStack(Material.POTION, 1);

        ItemMeta meta = item.getItemMeta();

        if (chemistryItem.getColor() != null)
        {
            PotionMeta potion = (PotionMeta) meta;
            assert potion != null;
            potion.setColor(chemistryItem.getColor());
            potion.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            potion.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            potion.setBasePotionData(new PotionData(PotionType.WATER));
        }

        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatLib.ColorUtil.formatHEX(""));

        if (!chemistryItem.getAffinity().isAmine() && !chemistryItem.getAffinity().isOpioidic())
            lore.add(ChatLib.ColorUtil.formatHEX(" &8> &7Opis: Ta substancja moze byc uzyta w syntezach."));
        else
            for (String verse : chemistryItem.getLore())
                lore.add(ChatLib.ColorUtil.formatHEX(verse));

        lore.add(ChatLib.ColorUtil.formatHEX(" &8> &7" + getEffects(chemistryItem.getAffinity())));
        lore.add(ChatLib.ColorUtil.formatHEX(""));

        assert meta != null;
        meta.setDisplayName(ChatColor.RESET + chemistryItem.getName());
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public static String getEffects(Affinity affinity)
    {
        final int serotonine = affinity.getSerotonine();
        final int dopamine = affinity.getDopamine();
        final int noradrenaline = affinity.getNoradrenaline();
        final int gaba = affinity.getGABA();
        final int opioidic = affinity.getOpioidic();

        StringBuilder effects = new StringBuilder();
        effects.append("Dzialanie: ");

        if (affinity.isAmine())
        {
            if (serotonine >= 50)
                effects.append("empatogenne, ");
            else if (serotonine >= 30)
                effects.append("silnie euforyczne, ");
            else if (serotonine >= 20)
                effects.append("euforyczne, ");
            else if (serotonine >= 10)
                effects.append("poprawiajace nastroj, ");
            else if (serotonine <= -15)
                effects.append("depresyjne, ");
            else if (serotonine <= -5)
                effects.append("wyciszajace, ");

            if (dopamine >= 50)
                effects.append("bardzo silnie pobudzajace, ");
            else if (dopamine >= 30)
                effects.append("silnie pobudzajace, ");
            else if (dopamine >= 20)
                effects.append("pobudzajace, ");
            else if (dopamine >= 10)
                effects.append("motywujace, ");
            else if (dopamine <= -15)
                effects.append("oslabiajace, ");

            if (noradrenaline >= 50)
                effects.append("bardzo silnie stymulujace, ");
            else if (noradrenaline >= 30)
                effects.append("silnie stymulujace, ");
            else if (noradrenaline >= 20)
                effects.append("stymulujace, ");
            else if (noradrenaline >= 10)
                effects.append("lekko stymulujace, ");
            else if (noradrenaline <= -10)
                effects.append("uspokajajace, ");

            if (gaba >= 50)
                effects.append("silnie hamujace, ");
            else if (gaba >= 30)
                effects.append("silnie relaksujace, ");
            else if (gaba >= 20)
                effects.append("relaksujace, ");
            else if (gaba >= 10)
                effects.append("rozluzniajace, ");
            else if (gaba <= -10)
                effects.append("rozdrazniajace, ");

            if (effects.toString().equalsIgnoreCase("Dzialanie: "))
                effects.append("nieznaczne");

            String finalEffects = effects.substring(effects.toString().length() - 2).equalsIgnoreCase(", ") ? effects.substring(0, effects.toString().length() - 2) : effects.toString();
            return finalEffects + ".";
        }
        else if (affinity.isOpioidic())
        {
            if (opioidic >= 100)
                return effects.append("bardzo silnie uspokajajace.").toString();
            else if (opioidic >= 50)
                return effects.append("silnie uspokajajace.").toString();
            else if (opioidic >= 25)
                return effects.append("uspokajajace.").toString();
            else if (opioidic >= 15)
                return effects.append("nasenne.").toString();

            if (effects.toString().equalsIgnoreCase("Dzialanie: "))
                effects.append("nieznaczne");

            effects.append(".");

            return effects.toString();
        }
        else
            return effects.append("Ta substancja nie wywoluje zadnych efektow.").toString();
    }
}