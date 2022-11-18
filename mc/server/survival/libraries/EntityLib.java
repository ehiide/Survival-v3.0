package mc.server.survival.libraries;

import mc.server.survival.worlds.WorldHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class EntityLib
{
    private EntityLib() {}

    static EntityLib instance = new EntityLib();

    public static EntityLib getInstance() {return instance;}

    public void createTempHologram(final String text, final Location location)
    {
        ArmorStand armorStand = (ArmorStand) Objects.requireNonNull(location.getWorld()).spawnEntity(location.add(0, 200 ,0), EntityType.ARMOR_STAND);

        armorStand.setSmall(true);
        armorStand.setCustomName(text);
        armorStand.setCustomNameVisible(true);
        armorStand.setMarker(true);
        armorStand.setInvulnerable(true);
        armorStand.setCollidable(false);
        armorStand.setInvisible(true);

        TaskLib.getInstance().runSyncLater(() -> armorStand.teleport(location.add(0, -200, 0)), 2);
        TaskLib.getInstance().runSyncLater(() -> removeEntity(armorStand), 20);
    }

    public void removeEntities()
    {
        final String[] entitiesName = {"   ", "&fFinal hit!", "CHAIR"};

        for (final World world : WorldHandler.getWorlds())
            for (final Entity entity : world.getEntities())
                for (final String name : entitiesName)
                {
                    if (entity.getName().equalsIgnoreCase(name) && Bukkit.getPlayer(entity.getName()) == null)
                        removeEntity(entity);

                    if (entity.getName().contains("-"))
                        removeEntity(entity);
                }
    }

    public void removeEntity(final Entity entity) {entity.remove();}

    public void setEntityMovement(@NotNull Entity entity, final double X, final double Y, final double Z)
    {
        entity.setVelocity(new Vector(X, Y, Z));
    }

    public void applyEntityMovement(@NotNull LivingEntity entity, final double strength)
    {
        TaskLib.getInstance().runAsyncLater(() -> {
            final double x = entity.getVelocity().getX() * strength;
            final double z = entity.getVelocity().getZ() * strength;
            final double y = entity.getVelocity().getY() * 0.9;
            setEntityMovement(entity, x, y, z);
        }, 1);
    }
}