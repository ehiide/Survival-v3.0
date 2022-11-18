package mc.server.survival.worlds.aether.mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EtherWarrior
{
    public static LivingEntity summon(final Location location)
    {
        final Entity entity = location.getWorld().spawnEntity(location, EntityType.SKELETON);
        LivingEntity livingEntity = (LivingEntity) entity;

        livingEntity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD, 1));
        livingEntity.getEquipment().setItemInOffHand(new ItemStack(Material.SHIELD, 1));
        livingEntity.getEquipment().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET, 1));
        livingEntity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));

        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999, 2));
        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999, 1));
        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999, 1));
        livingEntity.setHealth(20);

        return livingEntity;
    }
}