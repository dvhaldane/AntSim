package edu.uis.verhal1.world;

import edu.uis.verhal1.ants.Ant;
import java.util.*;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class WorldTile
{
    private int ants;
    private int food;
    private int pheremone;
    private List<Ant> antList = new ArrayList<Ant>();
    private List<Ant> hatchList = new ArrayList<Ant>();
    private Map antCountMap = Collections.synchronizedMap(new HashMap<Ant, Integer>());
    private Boolean revealed;

    public WorldTile()
    {
        ants = 0;
        int food = 0;
        int pheremone = 0;
        Boolean revealed = false;

    }

    public Map getAntCountMap()
    {
        return this.antCountMap;
    }

    public List getAntList()
    {
        return this.antList;
    }

    public void addAnt(Ant ant)
    {
        antList.add(ant);
    }

    public void queueHatch(Ant ant)
    {
        hatchList.add(ant);
    }

    public void reconcileHatches()
    {
        for (Ant ant : hatchList)
        {
            antList.add(ant);
        }
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
