package mc.server.survival.libraries.enchantments;

public class EnchantmentWrapper
{
    private final String id;
    private final String name;

    public EnchantmentWrapper(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }

    public String getName() { return name; }
}