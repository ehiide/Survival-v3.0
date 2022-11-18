package mc.server.survival.libraries.chemistry;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Chemistries
{
    private Chemistries() {}

    static Chemistries instance = new Chemistries();

    public static Chemistries getInstance()
    {
        return instance;
    }

    public static final @NotNull ChemistryItem opium = new ChemistryItem("Opium", new String[]{" &8> &7Opis: Naturalna substancja, sluzaca do", "   &7syntezy opioidow."}, Material.GUNPOWDER, new Affinity(404));

    // from opium
    public static final @NotNull ChemistryItem alprazolam = new ChemistryItem("Alprazolam", new String[]{" &8> &7Opis: Chill, spokoj i opanowanie - lyknij alpre", "   &7i poczuj sie fajnie."}, Material.GUNPOWDER, new Affinity(10));
    public static final @NotNull ChemistryItem metylomorfina = new ChemistryItem("Metylomorfina", new String[]{" &8> &7Opis: Mokry sen kazdego malolata, kodeina", "   &7w postaci syropu."}, Material.GUNPOWDER, new Affinity(20));
    public static final @NotNull ChemistryItem morfina = new ChemistryItem("Morfina", new String[]{" &8> &7Opis: Najpopularniejszy opioid dostepny", "   &7rowniez tutaj."}, Material.GUNPOWDER, new Affinity(40));
    public static final @NotNull ChemistryItem heroina = new ChemistryItem("Heroina", new String[]{" &8> &7Opis: Wystarczy jedna dawka, abys poczul", "   &7lepszy swiat."}, Material.GUNPOWDER, new Affinity(90));
    public static final @NotNull ChemistryItem fentanyl = new ChemistryItem("Fentanyl", new String[]{" &8> &7Opis: Najsilniejszy opioid wystepujacy na", "   &7serwerze."}, Material.GUNPOWDER, new Affinity(150));

    public static final @NotNull ChemistryItem amina = new ChemistryItem("Amina", new String[]{" &8> &7Opis: Syntetyczna substancja, sluzaca do", "   &7syntezy psychotropow."}, Material.SUGAR, new Affinity(404, 0, 0, 0));

    // from amina
    public static final @NotNull ChemistryItem metyloamina = new ChemistryItem("Metyloamina", new String[]{" &8> &7Opis: Brak."}, Material.SUGAR, new Affinity(404, 404, 404, 404));
    public static final @NotNull ChemistryItem metylenoamina = new ChemistryItem("Metylenoamina", new String[]{" &8> &7Opis: Brak."}, Material.SUGAR, new Affinity(404, 404, 404, 404));
    public static final @NotNull ChemistryItem fenyloamina = new ChemistryItem("Fenyloamina", new String[]{" &8> &7Opis: Brak."}, Material.SUGAR, new Affinity(404, 404, 404, 404));
    public static final @NotNull ChemistryItem fluoroamina = new ChemistryItem("Fluoroamina", new String[]{" &8> &7Opis: Brak."}, Material.SUGAR, new Affinity(404, 404, 404, 404));
    public static final @NotNull ChemistryItem dimetoamina = new ChemistryItem("Dimetoamina", new String[]{" &8> &7Opis: Brak."}, Material.SUGAR, new Affinity(404, 404, 404, 404));

    // from metyloamina
    public static final @NotNull ChemistryItem metamfetamina = new ChemistryItem("Metamfetamina", new String[]{" &8> &7Opis: Stosowana na zolnierzach, aby byli jak", "   &7najlepszymi zawodnikami frontu."}, Material.SUGAR, new Affinity(25, 20, 15, -8));
    public static final @NotNull ChemistryItem metafedron = new ChemistryItem("Metafedron", new String[]{" &8> &7Opis: Brak."}, Material.SUGAR, new Affinity(13, 31, 9, -2));
    public static final @NotNull ChemistryItem metylon = new ChemistryItem("Metylon", new String[]{" &8> &7Opis: Czesto jest sprzedawany jako zamiennik MDMA,", "   &7do ktorego dzialaniem jest zblizony."}, Material.SUGAR, new Affinity(17, 16, 14, -9));
    public static final @NotNull ChemistryItem metylometkatynon = new ChemistryItem("Metylometkatynon", new String[]{" &8> &7Opis: Euforyczny katynon, ktory pozwoli Ci", "   &7poczuc szczescie jeszcze raz."}, Material.SUGAR, new Affinity(21, 12, -7, -2));

    // from metylenoamina
    public static final @NotNull ChemistryItem MDA = new ChemistryItem("MDA", new String[]{" &8> &7Opis: MDA jest najpotezniejszym stymulantem na", "   &7serwerze."}, Material.SUGAR, new Affinity(4, 14, 50, -5));
    public static final @NotNull ChemistryItem MDMA = new ChemistryItem("MDMA", new String[]{" &8> &7Opis: MDMA jest najsilniejsza substancja euforyczna", "   &7na serwerze."}, Material.SUGAR, new Affinity(55, 35, 20, 0));
    public static final @NotNull ChemistryItem MDPV = new ChemistryItem("MDPV", new String[]{" &8> &7Opis: Interesujacy stymulant z efektami ubocznymi", "   &7nienalezacymi do najprzyjemniejszych."}, Material.SUGAR, new Affinity(-2, 9, 42, -20));

    // from fenyloamina
    public static final @NotNull ChemistryItem amfetamina = new ChemistryItem("Amfetamina", new String[]{" &8> &7Opis: Klasyczny stymulant, ktorego przedstawiac", "   &7nie trzeba nikomu."}, Material.SUGAR, new Affinity(6, 15, 25, -7));
    public static final @NotNull ChemistryItem mefedron = new ChemistryItem("Mefedron", new String[]{" &8> &7Opis: Kiedys legalnie wystepowal w proszkach", "   &7do pielegnacji roslin."}, Material.SUGAR, new Affinity(25, 24, 23, 4));
    public static final @NotNull ChemistryItem klefedron = new ChemistryItem("Klefedron", new String[]{" &8> &7Opis: Brak."}, Material.SUGAR, new Affinity(21, 22, 15, 10));

    // from fluoroamina
    public static final @NotNull ChemistryItem fluoroamfetamina = new ChemistryItem("Fluoroamfetamina", new String[]{" &8> &7Opis: Brak."}, Material.SUGAR, new Affinity(16, 23, 17, 0));
    public static final @NotNull ChemistryItem flefedron = new ChemistryItem("Flefedron", new String[]{" &8> &7Opis: Brak."}, Material.SUGAR, new Affinity(8, 15, 14, -12));

    // from dimetoamina
    public static final @NotNull ChemistryItem kokaina = new ChemistryItem("Kokaina", new String[]{" &8> &7Opis: Naturualny stymulant prosto z", "   &7Kolumbii."}, Material.SUGAR, new Affinity(12, 50, 25, -5));
    public static final @NotNull ChemistryItem kleksedron = new ChemistryItem("Kleksedron", new String[]{" &8> &7Opis: Brak."}, Material.SUGAR, new Affinity(10, 17, -11, 4));

    // alcohols
    public static final @NotNull ChemistryItem piwo = new ChemistryItem("Piwo", new String[]{" &8> &7Opis: jezeli chcesz sie napic dla towarzystwa,", "   &7nie wahaj sie i chwyc za piwo."}, Color.YELLOW, new Affinity(1, 0, 0, 4));
    public static final @NotNull ChemistryItem wino = new ChemistryItem("Wino", new String[]{" &8> &7Opis: potrzebujesz czegos na romantyczny wieczor", "   &7lub cos slodkiego i owocowego? Oto wino!"}, Color.fromBGR(153, 153, 255), new Affinity(3, -1, 0, 7));
    public static final @NotNull ChemistryItem szampan = new ChemistryItem("Szampan", new String[]{" &8> &7Opis: gdy w kosciele nowa para, biegnij", "   &7szybko po szampana!"}, Color.RED, new Affinity(0, 1, -1, 11));
    public static final @NotNull ChemistryItem whisky = new ChemistryItem("Whisky", new String[]{" &8> &7Opis: wytrawny, luksusowy alkohol, ktory", "   &7oswiadczy sie Twoim kubkom smakowym!"}, Color.fromBGR(153, 255, 255), new Affinity(-1, 3, 2, 24));
    public static final @NotNull ChemistryItem wodka = new ChemistryItem("Wodka", new String[]{" &8> &7Opis: polska czy ruska, zawsze dobrze smakuje", "   &7kto wodki nie pije, ten nie baluje!"}, Color.fromBGR(255, 255, 255), new Affinity(3, 4, 3, 40));
    public static final @NotNull ChemistryItem bimber = new ChemistryItem("Bimber", new String[]{" &8> &7Opis: legendarny bimber, pedzony przez samego", "   &7proseczka!"}, Color.BLACK, new Affinity(5, 10, -2, 80));

    public ArrayList<ChemistryItem> getChemistries()
    {
        ArrayList<ChemistryItem> chemistries = new ArrayList<>();
        final Field[] fields = this.getClass().getFields();

        for (Field field : fields)
            if (field.getType() == ChemistryItem.class)
            {
                field.setAccessible(true);

                try
                {
                    ChemistryItem chemistryItem = (ChemistryItem) field.get(Chemistries.class);
                    chemistries.add(chemistryItem);
                }
                catch (IllegalAccessException ignored)
                { }
            }

        return chemistries;
    }

    public ChemistryItem byName(final String name)
    {
        for (ChemistryItem chemistryItem : getChemistries())
            if (chemistryItem.getName().equalsIgnoreCase(name))
                return chemistryItem; return null;
    }

    public boolean isKnown(final ItemStack item)
    {
        for (ChemistryItem chemistryItem : getChemistries())
            if (ChemistryDrug.getDrug(chemistryItem).isSimilar(item))
                return true; return false;
    }
}