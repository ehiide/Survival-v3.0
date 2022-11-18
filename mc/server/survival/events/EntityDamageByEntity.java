package mc.server.survival.events;

import mc.server.survival.files.Main;
import mc.server.survival.libraries.ChatLib;
import mc.server.survival.libraries.EntityLib;
import mc.server.survival.libraries.MathLib;
import mc.server.survival.libraries.TaskLib;
import mc.server.survival.libraries.chemistry.ChemistryCore;
import mc.server.survival.libraries.enchantments.EnchantmentHandler;
import mc.server.survival.libraries.enchantments.Enchantments;
import mc.server.survival.managers.DataManager;
import mc.server.survival.managers.FileManager;
import mc.server.survival.protocols.Protocol;
import mc.server.survival.protocols.packet.PacketHandler;
import mc.server.survival.worlds.twilight.mobs.DarkWizard;
import net.minecraft.network.protocol.game.PacketPlayOutAnimation;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntity 
implements Listener
{
	private final boolean blindWhenCritical = (boolean) FileManager.getInstance().getConfigValue("visual.combat.blind-when-critial");
	private final boolean bloodWhenDeath = (boolean) FileManager.getInstance().getConfigValue("visual.combat.blood-when-death");
	private final boolean damageIndicatorHologram = (boolean) FileManager.getInstance().getConfigValue("visual.combat.damage-indicator-hologram");
	private final boolean sharpWhenHit = (boolean) FileManager.getInstance().getConfigValue("visual.combat.sharp-when-hit");

	private final int hitDelay = (int) FileManager.getInstance().getConfigValue("function.better-hits.hit-delay");
	private final double knockback = (double) FileManager.getInstance().getConfigValue("function.better-hits.knockback");
	private final boolean swingMultiplier = (boolean) FileManager.getInstance().getConfigValue("function.better-hits.swing-multiplier");

	@EventHandler
	public void onEvent(EntityDamageByEntityEvent event) 
	{
		if (event.getEntity() instanceof Player)
		{
			final Player player = (Player) event.getEntity();

			if (blindWhenCritical)
				if (event.getDamager().getFallDistance() > 0)
				{
					final PacketPlayOutAnimation blind = new PacketPlayOutAnimation(new Protocol(Main.getInstance().getServer()).getEntityPlayer(player), 2),
												 particle = new PacketPlayOutAnimation(new Protocol(Main.getInstance().getServer()).getEntityPlayer(player), 5);
					PacketHandler.getInstance().sendPacket(player, blind, particle);
				}

			if (EnchantmentHandler.getInstance().hasEnchantment(player.getInventory().getItemInOffHand(), Enchantments.DODGE))
				if (player.isBlocking())
					((LivingEntity) event.getDamager()).damage(2.0, player);

			event.setDamage(event.getDamage() / (0.0001 + ChemistryCore.ABILITY_ENVIRONMENT_SENSITIVE.get(player)));
		}
		if (event.getDamager() instanceof Player) 
		{
			final Player player = (Player) event.getDamager();
			final LivingEntity entity = (LivingEntity) event.getEntity();

			if (entity.getHealth() - event.getFinalDamage() <= 0)
			{
				if (entity.isDead())
				{
					event.setCancelled(true);
					return;
				}

				if (bloodWhenDeath)
					player.getWorld().spawnParticle(Particle.BLOCK_CRACK, entity.getLocation().add(0, entity.getEyeHeight(), 0), 50, 0.02, 0.02, 0.02, Material.REDSTONE_BLOCK.createBlockData());

				if (damageIndicatorHologram)
					EntityLib.getInstance().createTempHologram(ChatLib.ColorUtil.formatHEX("&c-" + ((int) entity.getHealth()) + "❤ &7(☠)"), entity.getLocation().add(0, entity.getEyeHeight(), 0));
			}
			else
				if (damageIndicatorHologram)
					if (player.getFallDistance() <= 0)
						EntityLib.getInstance().createTempHologram(ChatLib.ColorUtil.formatHEX("&c-" + ((int) event.getFinalDamage()) + "❤"), entity.getLocation().add(0, entity.getEyeHeight(), 0));
					else
						EntityLib.getInstance().createTempHologram(ChatLib.ColorUtil.formatHEX("&c-" + ((int) event.getFinalDamage()) + "❤ &6(Critical)"), entity.getLocation().add(0, entity.getEyeHeight(), 0));

			TaskLib.getInstance().runAsync(() -> entity.setNoDamageTicks(hitDelay));

			if (sharpWhenHit)
				entity.getWorld().spawnParticle(Particle.SWEEP_ATTACK, entity.getLocation().add(0, 0.5, 0), 2, 0.5, 0.5, 0.5);

			if (EnchantmentHandler.getInstance().hasEnchantment(player.getInventory().getItemInMainHand(), Enchantments.VAMPIRISM))
			{
				player.setHealth(Math.min(player.getHealthScale(), player.getHealth() + (event.getFinalDamage() / 3)));
				player.getWorld().spawnParticle(Particle.DAMAGE_INDICATOR, player.getLocation().add(0, 0.5, 0), 2, 0.5, 0.5, 0.5);
			}

			if (EnchantmentHandler.getInstance().hasEnchantment(player.getInventory().getItemInMainHand(), Enchantments.ICE_ASPECT))
			{
				player.setFreezeTicks(50);
				player.getWorld().spawnParticle(Particle.SNOWFLAKE, player.getLocation().add(0, 0.75, 0), 2, 0.5, 0.5, 0.5);
			}

			if (EnchantmentHandler.getInstance().hasEnchantment(player.getInventory().getItemInMainHand(), Enchantments.STRIKE))
				entity.getWorld().strikeLightning(entity.getLocation());

			if (EnchantmentHandler.getInstance().hasEnchantment(player.getInventory().getItemInMainHand(), Enchantments.IGNITE))
			{
				TaskLib.getInstance().runSyncLater(() -> {
					entity.setFireTicks(40);
					entity.damage(event.getDamage() * 0.2);
				}, 40);
			}

			if (DarkWizard.isThat(entity)) if (MathLib.chanceOf(25)) DarkWizard.attack(entity, player);

			float swingProgress;

			if (swingMultiplier) swingProgress = player.getAttackCooldown();
			else swingProgress = 1.0F;

			if (!(entity instanceof Player))
				EntityLib.getInstance().applyEntityMovement(entity, (knockback * swingProgress) * ChemistryCore.ABILITY_COMBAT_KNOCKBACK.get(((Player) event.getDamager()).getPlayer()));
		}

		if (event.getDamager() instanceof final Player attacker && event.getEntity() instanceof final Player victim)
			if (DataManager.getInstance().getLocal(attacker).getGang() != null && DataManager.getInstance().getLocal(victim).getGang() != null)
				if (DataManager.getInstance().getLocal(attacker).getGang().equalsIgnoreCase(DataManager.getInstance().getLocal(victim).getGang()))
					if (!DataManager.getInstance().getLocal(attacker).getFriendlyFire(DataManager.getInstance().getLocal(attacker).getGang()))
						event.setCancelled(true);
	}
} 