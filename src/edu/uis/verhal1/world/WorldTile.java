package edu.uis.verhal1.world;

import edu.uis.verhal1.ants.Ant;
import edu.uis.verhal1.gui.ColonyNodeView;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class WorldTile
{
    private int food;
    private boolean isWorldSpawn;
    private ColonyNodeView node;
    private int pheremone;
    private int posX;
    private int posY;
    private List<Ant> antList = new ArrayList<Ant>();
    private List<Ant> antQueue = new ArrayList<Ant>();
    private Map antCountMap = Collections.synchronizedMap(new HashMap<Ant, Integer>());
    private Boolean revealed;

    public WorldTile(int posX, int posY)
    {
        this.food = 0;
        this.pheremone = 0;
        this.revealed = false;
        this.posX = posX;
        this.posY = posY;
        this.isWorldSpawn = false;

    }

    public List getAntList()
    {
        return this.antList;
    }

    public void addAnt(Ant ant)
    {
        antList.add(ant);
    }

    public void queueAnt(Ant ant)
    {
        antQueue.add(ant);
    }

    public void mergeQueue()
    {
        for (Ant ant : antQueue)
        {
            antList.add(ant);
        }

        antQueue.clear();
    }

    public int getFood()
    {
        return this.food;
    }

    public void setFood(int food)
    {
        this.food = food;
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
        Point coords = new Point(posX,posY);

        return coords;
    }

}
