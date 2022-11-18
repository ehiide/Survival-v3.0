package mc.server.survival.worlds.twilight.mobs;

import mc.server.survival.libraries.enchantments.EnchantmentHandler;
import mc.server.survival.libraries.enchantments.Enchantments;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EvilPirate
{
    public static LivingEntity summon(final Location location)
    {
        final Entity entity = location.getWorld().spawnEntity(location, EntityType.SKELETON);
        LivingEntity livingEntity = (LivingEntity) entity;

        ItemStack bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
        bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
        bow = EnchantmentHandler.getInstance().addEnchantment(bow, Enchantments.EXPLODE_HASH);
        bow = bow.clone();

        livingEntity.getEquipment().setItemInMainHand(bow);
        livingEntity.getEquipment().setItemInOffHand(new ItemStack(Material.SHIELD));

        livingEntity.getEquipment().setHelmet(new ItemStack(Material.GOLDEN_HELMET));
        livingEntity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));

        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999, 1));
        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999, 1));
        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 9999, 2));

        livingEntity.setRemoveWhenFarAway(false);
        livingEntity.setHealth(20);

        return livingEntity;
    }
}