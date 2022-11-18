package mc.server.survival.libraries.chemistry;

import mc.server.survival.files.Main;
import mc.server.survival.libraries.MathLib;
import mc.server.survival.libraries.TaskLib;
import mc.server.survival.managers.DataManager;
import mc.server.survival.protocols.Protocol;
import mc.server.survival.protocols.packet.PacketHandler;
import net.minecraft.network.protocol.game.PacketPlayOutAnimation;
import net.minecraft.network.protocol.game.PacketPlayOutPosition;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ChemistryEffect
{
    private static final @NotNull Set<PacketPlayOutPosition.EnumPlayerTeleportFlags> teleportFlags = new HashSet<>(Arrays.asList(PacketPlayOutPosition.EnumPlayerTeleportFlags.a,
            PacketPlayOutPosition.EnumPlayerTeleportFlags.b, PacketPlayOutPosition.EnumPlayerTeleportFlags.c));

    public static HashMap<Player, BukkitTask> shakeEffect = new HashMap<Player, BukkitTask>();
    public static HashMap<Player, BukkitTask> regenerateEffect = new HashMap<Player, BukkitTask>();
    public static HashMap<Player, BukkitTask> overdoseEffect = new HashMap<Player, BukkitTask>();
    public static HashMap<Player, BukkitTask> heartEffect = new HashMap<Player, BukkitTask>();
    public static HashMap<Player, BukkitTask> uncoordinatedEffect = new HashMap<Player, BukkitTask>();
    public static HashMap<Player, BukkitTask> hallucinationEffect = new HashMap<Player, BukkitTask>();
    private static final HashMap<Player, Float> lastYaw = new HashMap<Player, Float>();
    private static final HashMap<Player, Float> lastPitch = new HashMap<>();

    public static BukkitTask getShakeEffect(Player player)
    {
        return shakeEffect.get(player);
    }

    public static void setShakeEffect(Player player, BukkitTask task)
    {
        shakeEffect.put(player, task);
    }

    public static BukkitTask getRegenerateEffect(Player player)
    {
        return regenerateEffect.get(player);
    }

    public static void setRegenerateEffect(Player player, BukkitTask task)
    {
        regenerateEffect.put(player, task);
    }

    public static BukkitTask getOverdoseEffect(Player player)
    {
        return overdoseEffect.get(player);
    }

    public static void setOverdoseEffect(Player player, BukkitTask task)
    {
        overdoseEffect.put(player, task);
    }

    public static BukkitTask getHeartEffect(Player player)
    {
        return heartEffect.get(player);
    }

    public static void setHeartEffect(Player player, BukkitTask task)
    {
        heartEffect.put(player, task);
    }

    public static BukkitTask getUncoordinatedEffect(Player player)
    {
        return uncoordinatedEffect.get(player);
    }

    public static void setUncoordinatedEffect(Player player, BukkitTask task)
    {
        uncoordinatedEffect.put(player, task);
    }

    public static BukkitTask getHallucinationEffect(Player player)
    {
        return hallucinationEffect.get(player);
    }

    public static void setHallucinationEffect(Player player, BukkitTask task)
    {
        hallucinationEffect.put(player, task);
    }

    public static void shakeCamera(final Player player, final int delay, final double strength)
    {
        setShakeEffect(player, new BukkitRunnable()
        {
            @Override
            public void run()
            {
                final float yaw = player.getLocation().getYaw();
                final float pitch = player.getLocation().getPitch();
                final Random random = new Random();

                final int a = random.nextInt(20) - 10;
                final int b = random.nextInt(20) - 10;
                final int c = random.nextInt(20) - 10;
                final int d = random.nextInt(20) - 10;

                final float randomShakeMove = (float) ((strength * a) - (strength * b));
                final float randomShakeMove2 = (float) ((strength * c) - (strength * d));

                final float finalYaw = yaw + randomShakeMove;
                final float finalPitch = pitch + randomShakeMove2;

                if (lastYaw.get(player) == null) lastYaw.put(player, (float) 0);
                if (lastPitch.get(player) == null) lastPitch.put(player, (float) 0);

                if (MathLib.chanceOf(DataManager.getInstance().getLocal(player).getNoradrenaline() / 900))
                    player.swingMainHand();

                if (Math.abs(yaw - lastYaw.get(player)) <= (strength * 10) && Math.abs(pitch - lastPitch.get(player)) <= (strength * 10))
                {
                    final PacketPlayOutPosition packet = new PacketPlayOutPosition(0.0, 0.0, 0.0, finalYaw, finalPitch, teleportFlags, 0, player.isOnGround());
                    PacketHandler.getInstance().sendPacket(player, packet);
                }

                lastYaw.put(player, yaw);
                lastPitch.put(player, pitch);
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0, delay));
    }

    public static void regenerateEffect(Player player, int delay, boolean overdose)
    {
        setRegenerateEffect(player, new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if (overdose)
                    player.playEffect(EntityEffect.HURT);

                if (overdose)
                    player.setHealth(player.getHealth() < 1 ? player.getHealth() : player.getHealth() - 1);
                else
                    player.setHealth(player.getHealth() < player.getHealthScale() - 1 ? player.getHealth() + 1 : player.getHealthScale());
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0, delay));
    }

    public static void overdoseEffect(Player player, int delay)
    {
        setOverdoseEffect(player, new BukkitRunnable()
        {
            @Override
            public void run()
            {
                PacketPlayOutAnimation packet = new PacketPlayOutAnimation(new Protocol(Main.getInstance().getServer()).getEntityPlayer(player), 2);
                PacketHandler.getInstance().sendPacket(player, packet);
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0, delay));
    }

    public static void heartEffect(Player player, int delay, int heartAmount, double strength)
    {
        setHeartEffect(player, new BukkitRunnable()
        {
            @Override
            public void run()
            {
                player.spawnParticle(Particle.HEART, player.getLocation().add(0, 1, 0), heartAmount, strength, strength, strength);
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0, delay));
    }

    public static void uncoordinatedEffect(Player player, int delay)
    {
        setUncoordinatedEffect(player, new BukkitRunnable()
        {
            @Override
            public void run()
            {
                player.getInventory().setHeldItemSlot(new Random().nextInt(9));
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0, delay));
    }

    public static void hallucinationEffect(Player player, int strength)
    {
        setHallucinationEffect(player, new BukkitRunnable()
        {
            @Override
            public void run()
            {
                Random r = new Random();

                for (int i = 0; i < strength; i++)
                {
                    int x = -12 + r.nextInt(25);
                    int y = -12 + r.nextInt(25);
                    int z = -12 + r.nextInt(25);

                    Location blockLoc = player.getLocation().add(x, y, z);
                    Block thatBlock = player.getWorld().getBlockAt(blockLoc);

                    if (thatBlock.getLocation().add(0, 1, 0).getBlock().getType().isSolid() &&
                            thatBlock.getLocation().add(0, -1, 0).getBlock().getType().isSolid() &&
                            thatBlock.getLocation().add(1, 0, 0).getBlock().getType().isSolid() &&
                            thatBlock.getLocation().add(-1, 0, 0).getBlock().getType().isSolid() &&
                            thatBlock.getLocation().add(0, 0, 1).getBlock().getType().isSolid() &&
                            thatBlock.getLocation().add(0, 0, -1).getBlock().getType().isSolid() ||
                            thatBlock.getType().toString().contains("STAIRS") | thatBlock.getType().toString().contains("SLAB") |
                                    thatBlock.getType().toString().contains("TRAPDOOR") | thatBlock.getType().toString().contains("DOOR") |
                                    thatBlock.getType().toString().contains("FENCE") | thatBlock.getType().toString().contains("GATE") |
                                    thatBlock.getType().toString().contains("SKULL") | thatBlock.getType().toString().contains("HEAD") |
                                    thatBlock.getType().toString().contains("BED"))
                        i--;
                    else
                    {
                        if (thatBlock.getType().isSolid())
                            player.sendBlockChange(blockLoc, Material.BARRIER.createBlockData());

                        TaskLib.getInstance().runAsyncLater(() -> player.sendBlockChange(blockLoc, thatBlock.getBlockData()), 20);
                    }
                }
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0, 1));
    }

    public static void cancelTask(final Player player, HashMap<Player, BukkitTask> task)
    {
        if (task.isEmpty() || task.get(player) == null) return;

        task.get(player).cancel();
        task.put(player, null);
    }

    public static void cancelTasks(final Player player)
    {
        cancelTask(player, shakeEffect);
        cancelTask(player, regenerateEffect);
        cancelTask(player, overdoseEffect);
        cancelTask(player, heartEffect);
        cancelTask(player, uncoordinatedEffect);
        cancelTask(player, hallucinationEffect);
    }
}