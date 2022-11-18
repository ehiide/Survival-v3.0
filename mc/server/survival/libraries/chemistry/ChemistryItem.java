package mc.server.survival.libraries.chemistry;

import org.bukkit.Color;
import org.bukkit.Material;

public class ChemistryItem
{
    private String name;
    private String[] lore;
    private Material type;
    private Color color;
    private Affinity affinity;

    public ChemistryItem(String name, String[] lore, Material type, Affinity affinity)
    {
        this.name = name;
        this.lore = lore;
        this.type = type;
        this.affinity = affinity;
    }

    public ChemistryItem(String name, String[] lore, Color color, Affinity affinity)
    {
        this.name = name;
        this.lore = lore;
        this.color = color;
        this.affinity = affinity;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String[] getLore() { return lore; }

    public void setLore(String[] lore) { this.lore = lore; }

    public Material getType() { return type; }

    public void setType(Material type) { this.type = type; }

    public Color getColor() { return color; }

    public void setColor(Color color) { this.color = color; }

    public Affinity getAffinity() { return affinity; }

    public void setAffinity(Affinity affinity) { this.affinity = affinity; }

    class ExampleDrug
    {
        public final ChemistryItem amfetamina = new ChemistryItem("Amfetamina", new String[]{"Chemistry 3", "Example drug"}, Material.SUGAR, new Affinity(6, 15, 25, -7));
    }
}