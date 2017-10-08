package edu.uis.verhal1.ants;

import edu.uis.verhal1.world.World;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class Ant
{
    private static int index = 0;
    private int id;
    private boolean isDead;
    protected int lifeInDays;
    protected String type;

    protected final ArrayList<Point> pointList = new ArrayList<Point>();

    public Ant()
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

    public void kill()
    {
        this.isDead = true;
    }

    public int getID()
    {
        return this.id;
    }

    public boolean isDead()
    {
        if (isDead)
            return true;
        else
            return false;
    }

    public void decrementLifeOneDay()
    {
        this.lifeInDays--;
    }

    public String getType()
    {
        return this.type;
    }

    public ArrayList<Point> getValidMovementPoints(World world, ArrayList<Point> points, Point currentPosition)
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
}
