package mc.server.survival.worlds.twilight.mobs;

import mc.server.survival.libraries.ChatLib;
import mc.server.survival.libraries.TaskLib;
import mc.server.survival.libraries.enchantments.EnchantmentHandler;
import mc.server.survival.libraries.enchantments.Enchantments;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DarkWizard
{
    public static LivingEntity summon(final Location location)
    {
        final Entity entity = location.getWorld().spawnEntity(location, EntityType.STRAY);
        LivingEntity livingEntity = (LivingEntity) entity;

        ItemStack sword = new ItemStack(Material.GOLDEN_HOE);
        sword.addEnchantment(Enchantment.DURABILITY, 1);
        sword = EnchantmentHandler.getInstance().addEnchantment(sword, Enchantments.UNBREAKABLE);
        sword = sword.clone();

        livingEntity.getEquipment().setItemInMainHand(sword);

        livingEntity.getEquipment().setHelmet(new ItemStack(Material.GOLDEN_HELMET));
        livingEntity.getEquipment().setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE));
        livingEntity.getEquipment().setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS));
        livingEntity.getEquipment().setBoots(new ItemStack(Material.GOLDEN_BOOTS));

        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999, 2));
        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999, 3));
        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999, 2));

        livingEntity.setRemoveWhenFarAway(false);
        livingEntity.setHealth(20);

        livingEntity.setCustomName(ChatLib.ColorUtil.formatHEX("&eMroczny Czarodziej"));
        livingEntity.setCustomNameVisible(true);

        return livingEntity;
    }

    public static boolean isThat(final LivingEntity entity)
    {
        return entity.getCustomName() != null &&
                entity.isCustomNameVisible() &&
                entity.getCustomName() != null &&
                entity.getCustomName().equalsIgnoreCase(ChatLib.ColorUtil.formatHEX("&eMroczny Czarodziej")) &&
                entity.getEquipment() != null &&
                entity.getEquipment().getItemInMainHand().getType() == Material.GOLDEN_HOE;
    }

    public static void attack(final LivingEntity attacker, final Player receiver)
    {
        receiver.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, receiver.getLocation().add(0, 0.5, 0), 5, 0.5, 0.5, 0.5);
        receiver.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, receiver.getLocation().add(0, 0.5, 0), 25, 2.5, 2.5, 2.5);
        receiver.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1));
        receiver.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 1));
        receiver.damage(4.0D, attacker);

        attacker.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 60, 2));
        attacker.getWorld().spawnParticle(Particle.FLASH, receiver.getLocation().add(0, 0.5, 0), 25, 0.5, 0.5, 0.5);

        ItemStack sword = new ItemStack(Material.GOLDEN_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        sword = EnchantmentHandler.getInstance().addEnchantment(sword, Enchantments.UNBREAKABLE);
        sword = sword.clone();

        ItemStack hoe = new ItemStack(Material.GOLDEN_HOE);
        hoe.addEnchantment(Enchantment.DURABILITY, 1);
        hoe = EnchantmentHandler.getInstance().addEnchantment(hoe, Enchantments.UNBREAKABLE);
        final ItemStack finalHoe = hoe.clone();

        attacker.getEquipment().setItemInMainHand(sword);
        TaskLib.getInstance().runSyncLater(() -> attacker.getEquipment().setItemInMainHand(finalHoe), 120);
    }
}
