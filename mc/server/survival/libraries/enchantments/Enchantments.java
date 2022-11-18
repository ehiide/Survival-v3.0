package mc.server.survival.libraries.enchantments;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Enchantments
{
    public static final @NotNull EnchantmentWrapper DODGE = new EnchantmentWrapper("dodge", "Dodge");
    public static final @NotNull EnchantmentWrapper EXPLODE_HASH = new EnchantmentWrapper("explode_hash", "Explode Hash");
    public static final @NotNull EnchantmentWrapper FIRE_WALKER = new EnchantmentWrapper("fire_walker", "Fire Walker");
    public static final @NotNull EnchantmentWrapper ICE_ASPECT = new EnchantmentWrapper("ice_aspect", "Ice Aspect");
    public static final @NotNull EnchantmentWrapper IGNITE = new EnchantmentWrapper("ignite", "Ignite");
    public static final @NotNull EnchantmentWrapper INSTANT_MINING = new EnchantmentWrapper("instant_mining", "Instant Mining");
    public static final @NotNull EnchantmentWrapper UNBREAKABLE = new EnchantmentWrapper("unbreakable", "Unbreakable");
    public static final @NotNull EnchantmentWrapper SEEDER = new EnchantmentWrapper("seeder", "Seeder");
    public static final @NotNull EnchantmentWrapper STRIKE = new EnchantmentWrapper("strike", "Strike");
    public static final @NotNull EnchantmentWrapper VAMPIRISM = new EnchantmentWrapper("vampirism", "Vampirism");

    private Enchantments() {}

    static Enchantments instance = new Enchantments();

    public static Enchantments getInstance() { return instance; }

    public ArrayList<EnchantmentWrapper> getEnchantments()
    {
        ArrayList<EnchantmentWrapper> enchantmentWrappers = new ArrayList<>();
        final Field[] fields = this.getClass().getFields();

        for (Field field : fields)
            if (field.getType() == EnchantmentWrapper.class)
            {
                field.setAccessible(true);

                try
                {
                    EnchantmentWrapper enchantmentWrapper = (EnchantmentWrapper) field.get(Enchantments.class);
                    enchantmentWrappers.add(enchantmentWrapper);
                }
                catch (IllegalAccessException ignored)
                { }
            }

        return enchantmentWrappers;
    }
}