package mc.server.survival.libraries;

import mc.server.survival.files.Main;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class TaskLib
{
    private TaskLib() {}

    static TaskLib instance = new TaskLib();

    public static TaskLib getInstance() {return instance;}

    public BukkitTask runSync(@NotNull Runnable runnable)
    {
        return Bukkit.getScheduler().runTask(Main.getInstance(), runnable);
    }

    public BukkitTask runSyncLater(@NotNull Runnable runnable, final int delay)
    {
        return Bukkit.getScheduler().runTaskLater(Main.getInstance(), runnable, delay);
    }

    public BukkitTask runSyncTimer(@NotNull Runnable runnable, final int interval)
    {
        return Bukkit.getScheduler().runTaskTimer(Main.getInstance(), runnable, interval, interval);
    }

    public BukkitTask runAsync(@NotNull Runnable runnable)
    {
        return Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), runnable);
    }

    public BukkitTask runAsyncLater(@NotNull Runnable runnable, final int delay)
    {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), runnable, delay);
    }

    public BukkitTask runAsyncTimer(@NotNull Runnable runnable, final int interval)
    {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), runnable, interval, interval);
    }
}