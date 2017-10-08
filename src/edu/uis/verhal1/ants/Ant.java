package edu.uis.verhal1.ants;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class Ant
{
    private static int index = 0;
    private int id;
    protected String type;

    public Ant()
    {
        this.id = index;

        index++;
    }

    public int getID()
    {
        return this.id;
    }

    public String getType()
    {
        return this.type;
    }
}
