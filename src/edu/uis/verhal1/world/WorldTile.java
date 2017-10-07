package edu.uis.verhal1.world;

import edu.uis.verhal1.ants.Ant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class WorldTile
{
    private int ants;
    private int food;
    private int pheremone;
    private Map antMap = Collections.synchronizedMap(new HashMap<String, Integer>());
    private Boolean revealed;

    public WorldTile()
    {
        ants = 0;
        int food = 0;
        int pheremone = 0;
        Boolean revealed = false;

        antMap.put("BALA", 0);
        antMap.put("FORAGER", 0);
        antMap.put("SCOUT", 0);
        antMap.put("SOLDIER", 0);
        antMap.put("QUEEN", 0);
    }

    public Map getAntMap()
    {
        return this.antMap;
    }

    public int getFood()
    {
        return this.food;
    }

    public void setFood(int food)
    {
        this.food = food;
    }

    public int getPheremone()
    {
        return this.pheremone;
    }

    public void setPheremone(int pheremone)
    {
        this.pheremone = pheremone;
    }

}
