package mc.server.survival.worlds.twilight.mobs;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EvilSpider
{
    public static LivingEntity summon(final Location location)
    {
        final Entity entity = location.getWorld().spawnEntity(location, EntityType.CAVE_SPIDER);
        LivingEntity livingEntity = (LivingEntity) entity;

        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999, 2));
        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 9999, 2));

        return livingEntity;
    }
}