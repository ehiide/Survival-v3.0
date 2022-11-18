package mc.server.survival.events;

import mc.server.survival.files.Main;
import mc.server.survival.libraries.*;
import mc.server.survival.libraries.enchantments.EnchantmentHandler;
import mc.server.survival.libraries.enchantments.Enchantments;
import mc.server.survival.managers.FileManager;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Random;

public class ItemThrow implements Listener
{
    private static HashMap<Projectile, BukkitTask> projectileTask = new HashMap<>();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEvent(EntityDamageEvent event)
    {
        if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE)
        {
            final LivingEntity damagedEntity = (LivingEntity) event.getEntity();

            if (damagedEntity.getHealth() - event.getFinalDamage() <= 0)
            {
                event.getEntity().getWorld().spawnParticle(Particle.BLOCK_CRACK, event.getEntity().getLocation().add(0, damagedEntity.getEyeHeight(), 0), 50, 0.02, 0.02, 0.02, Material.REDSTONE_BLOCK.createBlockData());

                if ((boolean) FileManager.getInstance().getConfigValue("visual.combat.damage-indicator-hologram"))
                    EntityLib.getInstance().createTempHologram(ChatLib.ColorUtil.formatHEX("&c-" + ((int) damagedEntity.getHealth()) + "❤"), damagedEntity.getLocation().add(0, damagedEntity.getEyeHeight(), 0));
            }
            else
                if ((boolean) FileManager.getInstance().getConfigValue("visual.combat.damage-indicator-hologram"))
                    EntityLib.getInstance().createTempHologram(ChatLib.ColorUtil.formatHEX("&c-" + ((int) event.getFinalDamage()) + "❤"), damagedEntity.getLocation().add(0, damagedEntity.getEyeHeight(), 0));

            TaskLib.getInstance().runAsyncLater(() -> damagedEntity.setNoDamageTicks((int) FileManager.getInstance().getConfigValue("function.better-hits.hit-delay")), 1);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEvent(ProjectileLaunchEvent event)
    {
        final Projectile projectile = event.getEntity();
        final ProjectileSource projectileSource = projectile.getShooter();

        if (projectile instanceof Snowball || projectile instanceof Egg)
            if (projectileSource instanceof Player)
            {
                Player player = (Player) projectileSource;
                player.setCooldown(((ThrowableProjectile) projectile).getItem().getType(), 4);
            }

        if (projectileSource instanceof Player)
            if (((Player) projectileSource).getInventory().getItemInMainHand().isSimilar(new ItemLib().get("granate")))
                projectile.setMetadata("granate", new FixedMetadataValue(Main.getInstance(), "0"));

        if (projectileSource instanceof LivingEntity)
            if (EnchantmentHandler.getInstance().hasEnchantment(((LivingEntity) projectileSource).getEquipment().getItemInMainHand(), Enchantments.EXPLODE_HASH))
                projectile.setMetadata("explode_hash", new FixedMetadataValue(Main.getInstance(), "0"));

        if ((boolean) FileManager.getInstance().getConfigValue("visual.entities.projectile-trail"))
            if (projectile instanceof Snowball || projectile instanceof Egg || projectile instanceof Arrow ||
                projectile instanceof SpectralArrow || projectile instanceof Trident || projectile instanceof EnderPearl)
            {
                if (projectileTask.get(projectile) == null)
                    projectileTask.put(projectile,  new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            if (projectile.hasMetadata("destroyed"))
                            {
                                this.cancel();
                                projectileTask.remove(projectile);
                            }

                            final World world = projectile.getWorld();
                            Particle.DustTransition transition;

                            if (projectile instanceof Snowball) transition = new Particle.DustTransition(Color.AQUA, Color.WHITE, 1);
                            else if (projectile instanceof Egg) transition = new Particle.DustTransition(Color.fromBGR(207, 248, 249), Color.WHITE, 1);
                            else if (projectile instanceof Arrow)
                                if (((Arrow) projectile).getBasePotionData().getType().equals(PotionType.UNCRAFTABLE))
                                    transition = new Particle.DustTransition(Color.RED, Color.WHITE, 1);
                                else
                                    transition = new Particle.DustTransition(((Arrow) projectile).getColor(), Color.WHITE, 1);
                            else if (projectile instanceof SpectralArrow) transition = new Particle.DustTransition(Color.YELLOW, Color.WHITE, 1);
                            else if (projectile instanceof Trident) transition = new Particle.DustTransition(Color.GREEN, Color.AQUA, 1);
                                // Ender-Pearl
                            else transition = new Particle.DustTransition(Color.TEAL, Color.AQUA, 1);

                            world.spawnParticle(Particle.DUST_COLOR_TRANSITION, projectile.getLocation().add(0, 0, 0), 5, 0.1, 0.1, 0.1, 1, transition);
                        }
                    }.runTaskTimerAsynchronously(Main.getInstance(), 1, 1));
            }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEvent(ProjectileHitEvent event)
    {
        final Projectile projectile = event.getEntity();
        Entity hitEntity = null;
        Block hitBlock = null;

        event.getEntity().setMetadata("destroyed", new FixedMetadataValue(Main.getInstance(), "0"));

        if (event.getHitEntity() != null) hitEntity = event.getHitEntity();
        if (event.getHitBlock() != null) hitBlock = event.getHitBlock();

        if (projectile instanceof Arrow || projectile instanceof SpectralArrow || projectile instanceof Trident) if (hitBlock != null)
            projectile.getWorld().spawnParticle(Particle.BLOCK_DUST, projectile.getLocation(), 20, 0.4, 0, 0.4, hitBlock.getType().createBlockData());

        if ((boolean) FileManager.getInstance().getConfigValue("visual.entities.snowball-terrain-freeze"))
            if (projectile instanceof Snowball) if (hitBlock != null)
            {
                projectile.getWorld().spawnParticle(Particle.SNOW_SHOVEL, projectile.getLocation(), 20, 0.75, 0, 0.75);

                if (hitBlock.getType() != Material.SNOW_BLOCK && hitBlock.getType() != Material.SNOW &&
                        !hitBlock.getType().toString().contains("STAIRS") && !hitBlock.getType().toString().contains("SLAB") &&
                        !hitBlock.getType().toString().contains("TRAPDOOR") && !hitBlock.getType().toString().contains("DOOR") &&
                        !hitBlock.getType().toString().contains("FENCE") && !hitBlock.getType().toString().contains("GATE") &&
                        !hitBlock.getType().toString().contains("SKULL") && !hitBlock.getType().toString().contains("HEAD") &&
                        !hitBlock.getType().toString().contains("BED")

                && hitBlock.getLocation().clone().add(0, 1, 0).getBlock().getType().isSolid() | hitBlock.getLocation().clone().add(0, 1, 0).getBlock().getType() == Material.AIR
                ) {
                    final Block block = hitBlock;

                    for (final Player player : block.getWorld().getPlayers())
                    {
                        player.sendBlockChange(block.getLocation(), Material.SNOW_BLOCK.createBlockData());
                        TaskLib.getInstance().runAsyncLater(() -> player.sendBlockChange(block.getLocation(), block.getBlockData()), 20);
                    }

                    for (int x = -1; x <= 1; x++) {
                        for (int y = -2; y <= 2; y++) {
                            for (int z = -1; z <= 1; z++) {
                                if (block.getWorld().getBlockAt(block.getLocation().clone().add(x, y, z)).getType() == Material.AIR)
                                {
                                    final Block selectedPos = block.getWorld().getBlockAt(block.getLocation().add(x, y, z));

                                    if (selectedPos.getWorld().getBlockAt(selectedPos.getLocation().clone().add(0, -1, 0)).getType() == Material.SNOW) return;

                                    if (MathLib.chanceOf(50))
                                    {
                                        if (!selectedPos.getWorld().getBlockAt(selectedPos.getLocation().clone().add(0, -1, 0)).getType().isSolid())
                                            continue;

                                        for (final Player player : block.getWorld().getPlayers())
                                        {
                                            player.sendBlockChange(selectedPos.getLocation(), Material.SNOW.createBlockData());
                                            TaskLib.getInstance().runAsyncLater(() -> player.sendBlockChange(selectedPos.getLocation(), selectedPos.getBlockData()), ((int) (new Random().nextDouble() * 10)) + 20);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        if (projectile instanceof Egg) if (hitBlock != null)
            projectile.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, projectile.getLocation(), 20, 0.75, 0, 0.75);

        if (hitEntity != null)
            projectile.getWorld().spawnParticle(Particle.SWEEP_ATTACK, projectile.getLocation(), 1, 0, 0, 0);

        if (hitBlock != null || hitEntity != null)
        {
            if (projectile instanceof Fireball)
                if (!(projectile.getShooter() instanceof Ghast))
                    projectile.getWorld().createExplosion(projectile.getLocation(), 6, false);

            if (projectile.hasMetadata("explode_hash"))
                projectile.getWorld().createExplosion(projectile.getLocation(), 2, false);
        }

        if (projectile.hasMetadata("granate"))
            for (int x = 0; x < 60; x++)
                TaskLib.getInstance().runAsyncLater(() -> projectile.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, event.getEntity().getLocation().add(0, 0, 0), 1000, 5, 5, 5), 1 + x);

        if (projectile instanceof Egg || projectile instanceof Snowball)
            if (hitEntity instanceof Player)
            {
                Player player = (Player) hitEntity;
                player.damage(0.5, projectile);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*3, 0));
                player.setVelocity(new Vector(projectile.getVelocity().getX() / 3, 0.25, projectile.getVelocity().getZ() / 3));
            }
            else
                if (hitEntity != null && !hitEntity.isDead() && hitEntity.getTicksLived() > 20)
                    ((LivingEntity) hitEntity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*3, 0));

        if (!(projectile instanceof FishHook) && !(projectile instanceof EnderPearl) && !(projectile instanceof Trident)) event.getEntity().remove();
    }
}