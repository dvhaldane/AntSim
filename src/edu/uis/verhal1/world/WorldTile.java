package edu.uis.verhal1.world;

import edu.uis.verhal1.ants.Ant;
import edu.uis.verhal1.ants.Forager;
import edu.uis.verhal1.gui.ColonyNodeView;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class WorldTile
{
    private int balaCount;
    private int foragerCount;
    private boolean revealed;
    private int scoutCount;
    private int soldierCount;
    private int food;
    private int pheremone;
    private final int posX;
    private final int posY;
    private boolean isWorldSpawn;
    private ColonyNodeView node;

    private final HashMap<Integer, Ant> antMap = new HashMap<>();

    private final List<Ant> addQueue = new ArrayList<Ant>();
    private final List<Ant> removeQueue = new ArrayList<Ant>();

    public WorldTile(int posX, int posY)
    {
        this.food = 0;
        this.pheremone = 0;
        this.revealed = false;
        this.posX = posX;
        this.posY = posY;
        this.isWorldSpawn = false;

    }

    public HashMap<Integer, Ant> getAntMap()
    {
        return this.antMap;
    }

    public void addAnt(Ant ant)
    {
        antMap.put(ant.getID(),ant);
    }

    public void queueAntAdd(Ant ant)
    {
        addQueue.add(ant);
    }

    public void removeAnt(Ant ant)
    {
        antMap.remove(ant.getID());
    }

    public void queueAntRemove(Ant ant)
    {
        removeQueue.add(ant);
    }

    public void mergeAntQueue()
    {
        for (Ant ant : addQueue)
        {
            antMap.put(ant.getID(),ant);
        }

        for (Ant ant : removeQueue)
        {
            if (ant.isDead())
            {
                if (ant.getType() == "FORAGER" && ant.isDead())
                {
                    this.setFood(((Forager) ant).getFood() + this.getFood());
                }
            }
            antMap.remove(ant.getID(),ant);
        }

        addQueue.clear();
        removeQueue.clear();
    }

    public int getFood()
    {
        return this.food;
    }

    public void setFood(int food)
    {
        this.food = food;
    }

    public int getBalaCount()
    {
        return this.balaCount;
    }
    public void setBalaCount(int count)
    {
        this.balaCount = count;
    }

    public int getForagerCount()
    {
        return this.foragerCount;
    }

    public void setForagerCount(int count)
    {
        this.foragerCount = count;
    }

    public boolean getRevealed()
    {
        return this.revealed;
    }

    public void reveal()
    {
        this.revealed = true;
    }

    public int getScoutCount()
    {
        return scoutCount;
    }

    public void setScoutCount(int count)
    {
        this.scoutCount = count;
    }

    public int getSoldierCount()
    {
        return this.soldierCount;
    }

    public void setSoldierCount(int count)
    {
        this.soldierCount = count;
    }

    public void resetCounts()
    {
        this.balaCount = 0;
        this.foragerCount = 0;
        this.scoutCount = 0;
        this.soldierCount = 0;
    }

    public ColonyNodeView getNode()
    {
        return this.node;
    }

    public void setNode(ColonyNodeView node)
    {
        this.node = node;
    }

    public int getPheremone()
    {
        return this.pheremone;
    }

    public void setPheremone(int pheremone)
    {
        this.pheremone = pheremone;
    }

    public void degradePheremone()
    {
        if (this.pheremone > 1)
        {
            this.pheremone = this.pheremone / 2;
        }
    }

    public boolean isWorldSpawn()
    {
        return isWorldSpawn;
    }

    public void setWorldSpawn()
    {
        this.isWorldSpawn = true;
    }

    public Point getCoordinates()
    {
        return new Point(posX,posY);
    }

}
