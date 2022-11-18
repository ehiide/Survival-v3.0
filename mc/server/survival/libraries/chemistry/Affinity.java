package mc.server.survival.libraries.chemistry;

public class Affinity
{
    private int serotonine, dopamine, noradrenaline, gaba;
    private int opioidic;

    public Affinity(int serotonine, int dopamine, int noradrenaline, int gaba)
    {
        this.serotonine = serotonine;
        this.dopamine = dopamine;
        this.noradrenaline = noradrenaline;
        this.gaba = gaba;
    }

    public Affinity(int opioidic)
    {
        this.opioidic = opioidic;
    }

    public boolean isOpioidic() { return serotonine == 0 && dopamine == 0 && noradrenaline == 0 && gaba == 0 && opioidic != 404; }

    public boolean isAmine() { return serotonine != 404 && dopamine != 404 && noradrenaline != 404 && gaba != 404 && opioidic == 0; }

    public int getSerotonine() { return serotonine; }

    public void setSerotonine(int serotonine) { this.serotonine = serotonine; }

    public int getDopamine() { return dopamine; }

    public void setDopamine(int dopamine) { this.dopamine = dopamine; }

    public int getNoradrenaline() { return noradrenaline; }

    public void setNoradrenaline(int noradrenaline) { this.noradrenaline = noradrenaline; }

    public int getGABA() { return gaba; }

    public void setGABA(int gaba) { this.gaba = gaba; }

    public int getOpioidic() { return opioidic; }

    public void setOpioidic(int opioidic) { this.opioidic = opioidic; }
}