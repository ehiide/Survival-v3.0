package mc.server.survival.libraries.chemistry;

import mc.server.Broadcaster;
import mc.server.survival.libraries.ChatLib;
import mc.server.survival.libraries.MathLib;
import mc.server.survival.libraries.TaskLib;
import mc.server.survival.libraries.VisualLib;
import mc.server.survival.managers.DataManager;
import mc.server.survival.managers.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ChemistryCore
{
    private static final int timeInterval = (int) FileManager.getInstance().getConfigValue("function.chemistry.restore-interval");

    public static void schedule()
    {
        TaskLib.getInstance().runSyncTimer(() -> Bukkit.getOnlinePlayers().forEach(player -> {if (isDrugged(player)) normalize(player, 1);}), timeInterval);
    }

    public static @NotNull HashMap<Player, Float> ABILITY_MOVEMENT_SPEED = new HashMap<>();
    public static @NotNull HashMap<Player, Double> ABILITY_COMBAT_KNOCKBACK = new HashMap<>();
    public static @NotNull HashMap<Player, Integer> ABILITY_PLAYER_REGENERATION = new HashMap<>();
    public static @NotNull HashMap<Player, Double> ABILITY_PLAYER_REGENERATION_BOOST = new HashMap<>();
    public static @NotNull HashMap<Player, Double> ABILITY_PLAYER_LUCK = new HashMap<>();
    public static @NotNull HashMap<Player, Double> ABILITY_ENVIRONMENT_SENSITIVE = new HashMap<>();
    public static @NotNull HashMap<Player, Boolean> ABILITY_ENVIRONMENT_FIRE_RESISTANCE = new HashMap<>();

    public static void changeAbilityValue(final Player player, final HashMap<Player, Double> ability, final double modifier)
    {
        ability.put(player, ability.get(player) + modifier);
    }

    public static void changeAbilityValue(final Player player, final HashMap<Player, Float> ability, final float modifier)
    {
        ability.put(player, ability.get(player) + modifier);
    }

    public static void changeAbilityValue(final Player player, final HashMap<Player, Integer> ability, final int modifier)
    {
        ability.put(player, ability.get(player) + modifier);
    }

    public static void changeAbilityValue(final Player player, final HashMap<Player, Boolean> ability, final boolean modifier)
    {
        ability.put(player, modifier);
    }

    private static void refreshAbilities(final Player player)
    {
        ABILITY_MOVEMENT_SPEED.put(player, 0.2F);
        ABILITY_COMBAT_KNOCKBACK.put(player, 1D);
        ABILITY_PLAYER_REGENERATION.put(player, 20);
        ABILITY_PLAYER_REGENERATION_BOOST.put(player, 1D);
        ABILITY_PLAYER_LUCK.put(player, 0.001D);
        ABILITY_ENVIRONMENT_SENSITIVE.put(player, 1D);
        ABILITY_ENVIRONMENT_FIRE_RESISTANCE.put(player, false);
    }

    public static void apply(Player player)
    {
        final double serotonine = DataManager.getInstance().getLocal(player).getSerotonine();
        final double dopamine = DataManager.getInstance().getLocal(player).getDopamine();
        final double noradrenaline = DataManager.getInstance().getLocal(player).getNoradrenaline();
        final double gaba = DataManager.getInstance().getLocal(player).getGABA();

        refreshAbilities(player);

        ChemistryEffect.cancelTasks(player);
        player.resetPlayerTime();
        player.resetPlayerWeather();
        player.setCollidable(true);
        player.setCanPickupItems(true);

        if (Math.abs(serotonine) > 150 || Math.abs(dopamine) > 150 || Math.abs(noradrenaline) > 150 || Math.abs(gaba) > 150)
        {
            set(player, 0, 0, 0, 0);
            player.setHealth(0);
            Broadcaster.broadcastMessage("#f83044[⚔] #8c8c8cGracz " + player.getName() + " (#80ff1f" + DataManager.getInstance().getLocal(player).getKills() + "⚔#8c8c8c/#fc7474" + DataManager.getInstance().getLocal(player).getDeaths() + "☠#8c8c8c) przedawkowal!");
            return;
        }

        if (Math.abs(serotonine) > 120 || Math.abs(dopamine) > 120 || Math.abs(noradrenaline) > 120 || Math.abs(gaba) > 120)
            if (ChemistryEffect.getRegenerateEffect(player) == null)
                ChemistryEffect.regenerateEffect(player, 20, true);

        // Serotonin level.

        if (serotonine > 0)
        {
            changeAbilityValue(player, ABILITY_PLAYER_LUCK, serotonine);
            changeAbilityValue(player, ABILITY_PLAYER_REGENERATION, (int) (70 - serotonine));
            changeAbilityValue(player, ABILITY_PLAYER_REGENERATION_BOOST, serotonine / 75);
            changeAbilityValue(player, ABILITY_MOVEMENT_SPEED, (float) (serotonine > 10 ? -(serotonine / 2900) : +(serotonine / 1450)));

            if (serotonine > 15)
                player.setPlayerWeather(WeatherType.CLEAR);

            if (serotonine > 20)
                player.setPlayerTime(6000, true);

            if (serotonine > 25)
                if (ChemistryEffect.getHeartEffect(player) == null)
                    ChemistryEffect.heartEffect(player, serotonine > 30 ? 4 : 10, (int) (serotonine / 5), serotonine / 25);

            if (serotonine > 35)
                ChatLib.scheduleDruggedMessages(player, 10);

            if (serotonine > 40)
                if (ChemistryEffect.getRegenerateEffect(player) == null)
                    ChemistryEffect.regenerateEffect(player, ABILITY_PLAYER_REGENERATION.get(player), false);
        }
        else if (serotonine < 0)
        {
            changeAbilityValue(player, ABILITY_PLAYER_LUCK, serotonine);

            if (serotonine < -15)
                player.setPlayerWeather(WeatherType.DOWNFALL);

            changeAbilityValue(player, ABILITY_ENVIRONMENT_SENSITIVE, -(Math.abs(serotonine) / 80));
        }

        // Dopamine level.

        if (dopamine > 0)
        {
            changeAbilityValue(player, ABILITY_MOVEMENT_SPEED, (float) (dopamine / 1200));
            changeAbilityValue(player, ABILITY_ENVIRONMENT_SENSITIVE, dopamine / 200);

            if (dopamine > 20)
                changeAbilityValue(player, ABILITY_ENVIRONMENT_FIRE_RESISTANCE, true);

            if (dopamine > 30)
                changeAbilityValue(player, ABILITY_COMBAT_KNOCKBACK, (float) (dopamine / 100));

            if (dopamine > 35)
                player.setCollidable(false);

            if (dopamine > 40)
                VisualLib.simulateFakeExplosion(player, dopamine > 80 ? 3 : 15);

            if (dopamine > 70)
                if (ChemistryEffect.getHallucinationEffect(player) == null)
                    ChemistryEffect.hallucinationEffect(player, (int) (dopamine / 15));
        }
        else if (dopamine < 0)
        {
            changeAbilityValue(player, ABILITY_MOVEMENT_SPEED, (float) -(Math.abs(dopamine) / 1400));

            if (dopamine < -20)
                if (ChemistryEffect.getOverdoseEffect(player) == null)
                    ChemistryEffect.overdoseEffect(player, Math.abs((100 - (int) Math.abs(dopamine))) / 10);

            if (dopamine < -30)
                changeAbilityValue(player, ABILITY_COMBAT_KNOCKBACK, (float) -(Math.abs(dopamine) / 200));

            if (MathLib.chanceOf((int) (Math.abs(dopamine) / 1.5)))
                player.dropItem(true);
        }

        // Noradrenaline level.

        if (noradrenaline > 0)
        {
            changeAbilityValue(player, ABILITY_MOVEMENT_SPEED, (float) (noradrenaline / 400));
            changeAbilityValue(player, ABILITY_PLAYER_REGENERATION_BOOST, noradrenaline / 200);

            if (noradrenaline > 25)
                changeAbilityValue(player, ABILITY_ENVIRONMENT_FIRE_RESISTANCE, true);

            if (noradrenaline > 35)
                player.setCollidable(false);

            if (noradrenaline > 40)
            {
                if (ChemistryEffect.getShakeEffect(player) == null)
                    ChemistryEffect.shakeCamera(player, 2, noradrenaline / 12000);
            }
        }
        else if (noradrenaline < 0)
        {
            changeAbilityValue(player, ABILITY_MOVEMENT_SPEED, (float) -(Math.abs(noradrenaline) / 1000));
            changeAbilityValue(player, ABILITY_ENVIRONMENT_SENSITIVE, -(Math.abs(noradrenaline) / 100));

            if (noradrenaline < -30)
                changeAbilityValue(player, ABILITY_ENVIRONMENT_FIRE_RESISTANCE, false);
        }

        // GABA level.

        if (gaba > 0)
        {
            changeAbilityValue(player, ABILITY_PLAYER_REGENERATION, (int) (70 - gaba));
            changeAbilityValue(player, ABILITY_PLAYER_REGENERATION_BOOST, gaba / 100);
            changeAbilityValue(player, ABILITY_MOVEMENT_SPEED, (float) (gaba > 10 ? -(gaba / 700) : +(gaba / 600)));
            changeAbilityValue(player, ABILITY_ENVIRONMENT_SENSITIVE, gaba / 150);

            if (gaba < 50)
            {
                if (ChemistryEffect.getRegenerateEffect(player) == null)
                    ChemistryEffect.regenerateEffect(player, ABILITY_PLAYER_REGENERATION.get(player), false);
            }
            else
            {
                if (ChemistryEffect.getRegenerateEffect(player) == null)
                    ChemistryEffect.regenerateEffect(player, 20, !(gaba < 120));

                if (ChemistryEffect.getUncoordinatedEffect(player) == null)
                    ChemistryEffect.uncoordinatedEffect(player, (int) Math.abs(120 - gaba));
            }

            if (gaba > 60)
                if (ChemistryEffect.getHallucinationEffect(player) == null)
                    ChemistryEffect.hallucinationEffect(player, (int) (gaba / 15));

            if (MathLib.chanceOf((int) (gaba / 2)))
                player.dropItem(true);

            player.setCanPickupItems(gaba < 80);
        }
        else if (gaba < 0)
        {
            changeAbilityValue(player, ABILITY_MOVEMENT_SPEED, (float) -(Math.abs(gaba) / 1800));
            changeAbilityValue(player, ABILITY_PLAYER_REGENERATION_BOOST, -(Math.abs(gaba) / 200));

            if (gaba < -20)
                if (ChemistryEffect.getOverdoseEffect(player) == null)
                    ChemistryEffect.overdoseEffect(player, Math.abs((100 - (int) Math.abs(gaba))) / 10);

            if (gaba < -30)
                changeAbilityValue(player, ABILITY_ENVIRONMENT_FIRE_RESISTANCE, false);

            player.setCanPickupItems(gaba < -60);
        }

        if (ABILITY_MOVEMENT_SPEED.get(player) < 0F) ABILITY_MOVEMENT_SPEED.put(player, 0F);

        player.setWalkSpeed(ABILITY_MOVEMENT_SPEED.get(player));
    }

    public static void set(final Player player, final int s, final int d, final int n, final int g)
    {
        DataManager.getInstance().getLocal(player).setSerotonine(s);
        DataManager.getInstance().getLocal(player).setDopamine(d);
        DataManager.getInstance().getLocal(player).setNoradrenaline(n);
        DataManager.getInstance().getLocal(player).setGABA(g);
        apply(player);
    }

    public static void modify(final Player player, final int s, final int d, final int n, final int g)
    {
        set(player, DataManager.getInstance().getLocal(player).getSerotonine() + s, DataManager.getInstance().getLocal(player).getDopamine() + d, DataManager.getInstance().getLocal(player).getNoradrenaline() + n, DataManager.getInstance().getLocal(player).getGABA() + g);
        apply(player);
    }

    public static void normalize(final Player player, final int value)
    {
        int serotonine = DataManager.getInstance().getLocal(player).getSerotonine();
        int dopamine = DataManager.getInstance().getLocal(player).getDopamine();
        int noradrenaline = DataManager.getInstance().getLocal(player).getNoradrenaline();
        int gaba = DataManager.getInstance().getLocal(player).getGABA();

        if (Math.abs(serotonine) <= value)
            serotonine = 0;
        else
        {
            if (serotonine > value)
                serotonine = serotonine - value;
            else if (serotonine < -value)
                serotonine = serotonine + value;
        }

        if (Math.abs(dopamine) <= value)
            dopamine = 0;
        else
        {
            if (dopamine > value)
                dopamine = dopamine - value;
            else if (dopamine < -value)
                dopamine = dopamine + value;
        }

        if (Math.abs(noradrenaline) <= value)
            noradrenaline = 0;
        else
        {
            if (noradrenaline > value)
                noradrenaline = noradrenaline - value;
            else if (noradrenaline < -value)
                noradrenaline = noradrenaline + value;
        }

        if (Math.abs(gaba) <= value)
            gaba = 0;
        else
        {
            if (gaba > value)
                gaba = gaba - value;
            else if (gaba < -value)
                gaba = gaba + value;
        }

        set(player, serotonine, dopamine, noradrenaline, gaba);
    }

    public static boolean isDrugged(final Player player)
    {
        return DataManager.getInstance().getLocal(player).getSerotonine() != 0 || DataManager.getInstance().getLocal(player).getDopamine() != 0 ||
                DataManager.getInstance().getLocal(player).getNoradrenaline() != 0 || DataManager.getInstance().getLocal(player).getGABA() != 0;
    }

    public static int getDruggedPercentage(final Player player)
    {
        final int serotonine = DataManager.getInstance().getLocal(player).getSerotonine();
        final int dopamine = DataManager.getInstance().getLocal(player).getDopamine();
        final int noradrenaline = DataManager.getInstance().getLocal(player).getNoradrenaline();
        final int gaba = DataManager.getInstance().getLocal(player).getGABA();

        int overall = serotonine + dopamine + noradrenaline + gaba;
        if (overall > 400) overall = 400;

        return 100 - (overall / 4);
    }
}