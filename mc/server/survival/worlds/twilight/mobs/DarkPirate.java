package mc.server.survival.worlds.twilight.mobs;

import mc.server.survival.libraries.ChatLib;
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

public class DarkPirate
{
    public static LivingEntity summon(final Location location)
    {
        final Entity entity = location.getWorld().spawnEntity(location, EntityType.WITHER_SKELETON);
        LivingEntity livingEntity = (LivingEntity) entity;

        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        sword.addEnchantment(Enchantment.FIRE_ASPECT, 1);
        sword = EnchantmentHandler.getInstance().addEnchantment(sword, Enchantments.IGNITE);
        sword = sword.clone();

        livingEntity.getEquipment().setItemInMainHand(sword);
        livingEntity.getEquipment().setItemInOffHand(new ItemStack(Material.SHIELD));

        livingEntity.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
        livingEntity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        livingEntity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        livingEntity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));

        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999, 1));
        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999, 3));
        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 9999, 2));
        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 9999, 5));
        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999, 1));

        livingEntity.setRemoveWhenFarAway(false);
        livingEntity.setHealth(20);

        livingEntity.setCustomName(ChatLib.ColorUtil.formatHEX("&eMroczny Pirat"));
        livingEntity.setCustomNameVisible(true);

        return livingEntity;
    }
}