package edu.uis.verhal1.ants;

import edu.uis.verhal1.world.World;
import edu.uis.verhal1.world.WorldTile;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class Ant
{
    private static int index = 0;
    private final int id;
    private boolean isDead;
    int lifeInDays;
    String type;

    final ArrayList<Point> pointList = new ArrayList<Point>();

    Ant()
    {
        this.id = index;
        this.isDead = false;
        this.lifeInDays = 365;
        index++;

        pointList.add(new Point(-1,1));
        pointList.add(new Point(-1,0));
        pointList.add(new Point(-1,-1));
        pointList.add(new Point(0,1));
        pointList.add(new Point(0,-1));
        pointList.add(new Point(1,1));
        pointList.add(new Point(1,0));
        pointList.add(new Point(1,-1));

    }

    void kill()
    {
        this.isDead = true;
    }

    public boolean isDead()
    {
        return this.isDead;
    }

    public int getID()
    {
        return this.id;
    }

    void decrementLifeOneDay()
    {
        if (this.lifeInDays > 0)
        {
            this.lifeInDays--;
        }
        else
        {
            this.kill();
        }
    }

    public String getType()
    {
        return this.type;
    }

    ArrayList<Point> getValidMovementPoints(World world, ArrayList<Point> points, Point currentPosition)
    {
        ArrayList<Point> tempPoints = new ArrayList<>();

        for (Point p : points)
        {
            if ((int)currentPosition.getX() - (int)p.getX() >= 0 && (int)currentPosition.getX() - (int)p.getX() < world.getColonyWidth() && (int)currentPosition.getY() - (int)p.getY() >= 0 && (int)currentPosition.getY() - p.getY() < (int)world.getColonyHeight())
            {
                tempPoints.add(p);
            }
        }

        return tempPoints;
    }

    void move(WorldTile currentTile, WorldTile targetTile, Ant ant)
    {
        targetTile.queueAntAdd(ant);
        currentTile.queueAntRemove(ant);
    }

    boolean ifDeadRemove(WorldTile currentTile)
    {
        if (this.isDead)
        {
            currentTile.queueAntRemove(this);
            return true;
        }

        return false;
    }


}
