package edu.uis.verhal1.ants;

import edu.uis.verhal1.driver.TickAction;
import edu.uis.verhal1.world.World;
import edu.uis.verhal1.world.WorldTile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class Forager extends Ant implements TickAction
{
    private int food;
    private int foodCapacity;
    private boolean foraging = true;
    private ArrayList<Point> movementHistory = new ArrayList<>();

    public Forager()
    {
        this.food = 0;
        this.foodCapacity = 1;
        this.type = "FORAGER";
    }

    @Override
    public void doTickAction(World world, WorldTile tile)
    {

        if (food < foodCapacity)
        {

            if (tile.getFood() > 0 && tile.isWorldSpawn() == false)
            {
                food = 1;
                tile.setFood(tile.getFood() - 1);
            }
            else
            {
                forage(world, tile);
            }
        }
        else
        {
            returnToNest(world, tile);
        }

        if (world.getDayChanged() == true)
        {
            this.decrementLifeOneDay();
        }

    }

    public void kill(WorldTile tile)
    {
        this.lifeInDays = 0;
        tile.setFood(tile.getFood() + this.getFood());
    }

    private void forage(World world, WorldTile tile)
    {

        ArrayList<Point> validPointsForMovementCalc = getValidMovementPoints(world, pointList, tile.getCoordinates());

        Collections.shuffle(validPointsForMovementCalc);

        Point tempPoint = validPointsForMovementCalc.get(0);

        int x = (int)tile.getCoordinates().getX() - (int)tempPoint.getX();
        int y = (int)tile.getCoordinates().getY() - (int)tempPoint.getY();

        Point tempMoveToCoords = world.getTileFromTilemap(x,y).getCoordinates();

        //Move to square with highest level of pheremone
        for (Point p : validPointsForMovementCalc)
        {
            x = (int)tile.getCoordinates().getX() - (int)p.getX();
            y = (int)tile.getCoordinates().getY() - (int)p.getY();

            if (world.getTileFromTilemap(x,y).getRevealed())
            {
                tempMoveToCoords = world.getTileFromTilemap(x,y).getCoordinates();

                if (world.getTileFromTilemap(x, y).getPheremone() > world.getTileFromTilemap(tempMoveToCoords).getPheremone())
                {
                    tempMoveToCoords = world.getTileFromTilemap(x, y).getCoordinates();
                }
            }
        }

         move(tile, world.getTileFromTilemap(tempMoveToCoords), this);
         movementHistory.add(tile.getCoordinates());

    }

    public int getFood()
    {
        return this.food;
    }

    private void returnToNest(World world, WorldTile tile)
    {
        //Stop at spawn for debug
        if (tile.isWorldSpawn())
        {
            tile.setFood(tile.getFood() + 1);
            this.food = 0;
            movementHistory.clear();
            foraging = true;
        }
        else
        {
            if (foraging == true)
            {
                Collections.reverse(movementHistory);
                foraging = false;
            }
            if (tile.getPheremone() < 1000)
            {
                tile.setPheremone(tile.getPheremone() + 10);
            }
            move(tile, world.getTileFromTilemap(movementHistory.get(0)), this);
            movementHistory.remove(0);
        }
    }

    private void breakCircle()
    {
        //If movementHistory contains the same point more than X times block the ant from going to that point.
    }
}
