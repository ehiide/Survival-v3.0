package mc.server.survival.worlds.aether.mobs;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EtherHorse
{
    public static LivingEntity summon(final Location location)
    {
        final Entity entity = location.getWorld().spawnEntity(location, EntityType.SKELETON_HORSE);
        LivingEntity livingEntity = (LivingEntity) entity;
        SkeletonHorse skeletonHorse = (SkeletonHorse) livingEntity;

        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999, 2));

        skeletonHorse.setTrapped(false);
        skeletonHorse.setHealth(5.0D);
        skeletonHorse.setCollidable(false);
        skeletonHorse.setTamed(true);

        return livingEntity;
    }
}