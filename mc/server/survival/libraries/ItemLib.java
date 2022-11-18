package mc.server.survival.libraries;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemLib
{
    private ItemStack itemStack;

    public ItemLib()
    { }

    public ItemStack toItemStack(Material material)
    {
        return new ItemStack(material, 1);
    }

    public ItemStack getFinalItem()
    {
        return this.itemStack;
    }

    public ItemLib buildItem(final Material material, final String name, final String[] lore)
    {
        ItemStack itemStack = toItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> itemLore = new ArrayList<>();

        if (name != null)
        {
            assert itemMeta != null;
            itemMeta.setDisplayName(ChatLib.ColorUtil.formatHEX("&r" + name));
        }

        if (lore != null)
        {
            for (String verse : lore)
                itemLore.add(ChatLib.ColorUtil.formatHEX(verse));
        }

        assert itemMeta != null;
        itemMeta.setLore(itemLore);

        itemStack.setItemMeta(itemMeta);

        this.itemStack = itemStack;

        return this;
    }

    public ItemLib unbreakable()
    {
        ItemMeta itemMeta = this.itemStack.getItemMeta();

        assert itemMeta != null;
        itemMeta.setUnbreakable(true);

        itemStack.setItemMeta(itemMeta);

        this.itemStack = itemStack;

        return this;
    }

    public ItemLib enchant()
    {
        ItemMeta itemMeta = this.itemStack.getItemMeta();

        assert itemMeta != null;
        itemMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);

        itemStack.setItemMeta(itemMeta);

        this.itemStack = itemStack;

        return this;
    }

    public ItemLib hideAttributes()
    {
        ItemMeta itemMeta = this.itemStack.getItemMeta();

        assert itemMeta != null;
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        itemStack.setItemMeta(itemMeta);

        this.itemStack = itemStack;

        return this;
    }

    public ItemLib internalEnchant(final String enchant)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        ArrayList<String> itemLore = new ArrayList<>();

        itemLore.add(ChatLib.ColorUtil.formatHEX("&a" + enchant));

        if (itemMeta.hasLore())
            for (String verse : lore)
                itemLore.add(ChatLib.ColorUtil.formatHEX(verse));

        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);

        this.itemStack = itemStack;

        return this;
    }

    public ItemLib buildSkull(final String owner, final String name)
    {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skull_meta = (SkullMeta) itemStack.getItemMeta();

        assert skull_meta != null;
        skull_meta.setOwningPlayer(Bukkit.getOfflinePlayer(owner));
        itemStack.setItemMeta(skull_meta);
        skull_meta.setDisplayName(ChatLib.ColorUtil.formatHEX("&r" + name));
        itemStack.setItemMeta(skull_meta);

        this.itemStack = itemStack;

        return this;
    }

    public ItemStack get(final String ID)
    {
        if (ID.equalsIgnoreCase("book")) return new ItemLib().buildItem(Material.ENCHANTED_BOOK, "&eEnchanted Book", new String[]{"&aUnbreakable"}).getFinalItem();
        else if (ID.equalsIgnoreCase("book:unbreakable")) return new ItemLib().buildItem(Material.ENCHANTED_BOOK, "&eEnchanted Book", new String[]{}).internalEnchant("Unbreakable").getFinalItem();
        else if (ID.equalsIgnoreCase("book:vampirism")) return new ItemLib().buildItem(Material.ENCHANTED_BOOK, "&eEnchanted Book", new String[]{}).internalEnchant("Vampirism").getFinalItem();
        else if (ID.equalsIgnoreCase("book:instant_mining")) return new ItemLib().buildItem(Material.ENCHANTED_BOOK, "&eEnchanted Book", new String[]{}).internalEnchant("Instant Mining").getFinalItem();
        else if (ID.equalsIgnoreCase("book:ice_aspect")) return new ItemLib().buildItem(Material.ENCHANTED_BOOK, "&eEnchanted Book", new String[]{}).internalEnchant("Ice Aspect").getFinalItem();
        else if (ID.equalsIgnoreCase("book:explode_hash")) return new ItemLib().buildItem(Material.ENCHANTED_BOOK, "&eEnchanted Book", new String[]{}).internalEnchant("Explode Hash").getFinalItem();
        else if (ID.equalsIgnoreCase("book:fire_walker")) return new ItemLib().buildItem(Material.ENCHANTED_BOOK, "&eEnchanted Book", new String[]{}).internalEnchant("Fire Walker").getFinalItem();
        else if (ID.equalsIgnoreCase("book:ignite")) return new ItemLib().buildItem(Material.ENCHANTED_BOOK, "&eEnchanted Book", new String[]{}).internalEnchant("Ignite").getFinalItem();
        else if (ID.equalsIgnoreCase("book:strike")) return new ItemLib().buildItem(Material.ENCHANTED_BOOK, "&eEnchanted Book", new String[]{}).internalEnchant("Strike").getFinalItem();
        else if (ID.equalsIgnoreCase("book:dodge")) return new ItemLib().buildItem(Material.ENCHANTED_BOOK, "&eEnchanted Book", new String[]{}).internalEnchant("Dodge").getFinalItem();
        else if (ID.equalsIgnoreCase("magic_rod")) return new ItemLib().buildItem(Material.STONE_HOE, "&cKosa Stacha Jonesa", new String[]{"", "&eLPM &7+ &eLPM &7+ &eLPM", "&7Zaklecie przyspieszajace.", "", "&ePPM &7+ &ePPM &7+ &ePPM", "&7Zaklecie regenerujace.", "", "&ePPM &7+ &eLPM &7+ &ePPM", "&7Zaklecie atakujace.", "", "&eLPM &7+ &ePPM &7+ &eLPM", "&7Zaklecie spierdalajaco-znikajace.", ""}).unbreakable().enchant().hideAttributes().getFinalItem();
        else if (ID.equalsIgnoreCase("granate")) return new ItemLib().buildItem(Material.SNOWBALL, "&fGranat Dymny", new String[]{"", " &8> &7Granat tworzacy gigantyczna chmure,", "   &7przez ktora ciezko cos dostrzec!", ""}).getFinalItem();
        else if (ID.equalsIgnoreCase("brewing_stand")) return new ItemLib().buildItem(Material.BREWING_STAND, "&fStatyw chemiczny", new String[]{"", " &8> &7Postawienie na ziemii skutkuje uzycie statywu i", "   &7wygenerowania go w tym miejscu!", ""}).getFinalItem();
        else if (ID.equalsIgnoreCase("aether:angel_heart")) return new ItemLib().buildSkull("IM_", "&cAnielskie Serce").enchant().hideAttributes().getFinalItem();
        else if (ID.equalsIgnoreCase("aether:crystal_of_keeping")) return new ItemLib().buildItem(Material.AMETHYST_SHARD, "&eKrysztal Uwiazania", new String[]{"", " &8> &7Krysztal uzywany jest w momencie smierci,", "   &7powodujac, ze kazdy z twoich przedmiotow w", "   &7ekwipunku wroci bezposrednio do Ciebie!", ""}).enchant().hideAttributes().getFinalItem();

        return null;
    }

    public static boolean isLeaf(final Material material)
    {
        return material.toString().contains("LEAVES");
    }

    public static boolean isPickaxe(final ItemStack itemStack)
    {
        return itemStack.getType().toString().contains("_PICKAXE");
    }

    public static boolean isAxe(final ItemStack itemStack)
    {
        return itemStack.getType().toString().contains("_AXE");
    }

    public static boolean isSword(final ItemStack itemStack)
    {
        return itemStack.getType().toString().contains("_SWORD");
    }

    public static boolean isShotable(final ItemStack itemStack)
    {
        return itemStack.getType().toString().contains("BOW");
    }

    public static boolean isHoe(final ItemStack itemStack) { return itemStack.getType().toString().contains("_HOE"); }

    public static ItemStack getRandomEnchantedBook()
    {
        List<ItemStack> enchantedBooks = new ArrayList<>();

        enchantedBooks.add(new ItemLib().get("book:unbreakable"));
        enchantedBooks.add(new ItemLib().get("book:vampirism"));
        enchantedBooks.add(new ItemLib().get("book:instant_mining"));
        enchantedBooks.add(new ItemLib().get("book:ice_aspect"));
        enchantedBooks.add(new ItemLib().get("book:explode_hash"));
        enchantedBooks.add(new ItemLib().get("book:fire_walker"));
        enchantedBooks.add(new ItemLib().get("book:ignite"));
        enchantedBooks.add(new ItemLib().get("book:strike"));
        enchantedBooks.add(new ItemLib().get("book:dodge"));

        return enchantedBooks.get(enchantedBooks.size() - 1);
    }

    public static EntityType getEntityTypeFromString(final String string)
    {
        return EntityType.valueOf(string.toUpperCase());
    }
}