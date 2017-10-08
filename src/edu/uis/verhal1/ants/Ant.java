package edu.uis.verhal1.ants;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class Ant
{
    private static int index = 0;
    private int id;
    protected int life;
    protected String type;

    public Ant()
    {
        this.id = index;
        this.life = 365;
        index++;
    }

    public int getID()
    {
        return this.id;
    }

    public int getLife()
    {
        return this.life;
    }

    public int setLife()
    {
        return this.life;
    }

    public void incrementLife()
    {
        this.life--;
    }

    public String getType()
    {
        return this.type;
    }
}
