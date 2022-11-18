package mc.server.survival.worlds.aether.mobs;

import mc.server.survival.libraries.enchantments.EnchantmentHandler;
import mc.server.survival.libraries.enchantments.Enchantments;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EtherKing
{
    public static LivingEntity summon(final Location location)
    {
        final Entity entity = location.getWorld().spawnEntity(location, EntityType.SKELETON);
        LivingEntity livingEntity = (LivingEntity) entity;

        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword = EnchantmentHandler.getInstance().addEnchantment(sword, Enchantments.IGNITE);
        sword = EnchantmentHandler.getInstance().addEnchantment(sword, Enchantments.VAMPIRISM);
        sword = sword.clone();

        livingEntity.getEquipment().setItemInMainHand(sword);
        livingEntity.getEquipment().setItemInOffHand(sword);

        livingEntity.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET, 1));
        livingEntity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
        livingEntity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
        livingEntity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS, 1));

        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999, 1));
        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999, 2));
        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999, 3));

        livingEntity.setRemoveWhenFarAway(false);
        livingEntity.setHealth(20);

        livingEntity.setCustomName("Król Niebios");
        livingEntity.setCustomNameVisible(true);

        return livingEntity;
    }

    public static boolean isThat(final Entity entity)
    {
        return ((LivingEntity) entity).getEquipment().getItemInOffHand().getType() == Material.DIAMOND_SWORD;
    }
}