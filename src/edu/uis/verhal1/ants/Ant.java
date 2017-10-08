package edu.uis.verhal1.ants;

import edu.uis.verhal1.driver.TickAction;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class Ant implements TickAction
{
    private static int index = 0;
    private int id;
    private boolean isAlive;
    protected int lifeInDays;
    protected String type;

    public Ant()
    {
        this.id = index;
        this.isAlive = true;
        this.lifeInDays = 365;
        index++;
    }

    public int getID()
    {
        return this.id;
    }

    public boolean isAlive()
    {
        if (isAlive)
            return true;
        else
            return false;
    }

    public void incrementLife()
    {
        this.lifeInDays--;
    }

    public String getType()
    {
        return this.type;
    }
}
