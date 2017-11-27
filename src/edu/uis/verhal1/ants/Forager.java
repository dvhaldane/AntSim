package edu.uis.verhal1.ants;

import edu.uis.verhal1.driver.TickAction;
import edu.uis.verhal1.world.World;
import edu.uis.verhal1.world.WorldTile;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class Forager extends Ant implements TickAction
{
    private int food;
    private final int foodCapacity;
    private boolean foraging = true;
    private ArrayList<Point> movementHistory = new ArrayList<>();
    private boolean resolvingLoop = false;
    private int resolveCount = 0;
    private int resolveLimit = 4;

    public Forager()
    {
        this.food = 0;
        this.foodCapacity = 1;
        this.type = "FORAGER";
    }

    @Override
    public void doTickAction(World world, WorldTile tile)
    {
        if (ifDeadRemove(tile))
        {
            return;
        }
        else
        {
            if (food < foodCapacity)
            {

                if (tile.getFood() > 0 && !tile.isWorldSpawn())
                {
                    food = 1;
                    tile.setFood(tile.getFood() - 1);
                } else
                {
                    forage(world, tile);
                }
            } else
            {
                returnToNest(world, tile);
            }

            if (world.getDayChanged())
            {
                this.decrementLifeOneDay();
            }
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
        HashMap<Point, Integer> pheremoneMap = new HashMap<>();

        Collections.shuffle(validPointsForMovementCalc);

        Point tempPoint = validPointsForMovementCalc.get(0);

        //Declare and assign x y and tempMoveToCoords to be used within loop
        int x = (int)tile.getCoordinates().getX() - (int)tempPoint.getX();
        int y = (int)tile.getCoordinates().getY() - (int)tempPoint.getY();

        Point tempMoveToCoords = world.getTileFromTilemap(x,y).getCoordinates();

        //Move to square with highest level of pheremone
        for (Point p : validPointsForMovementCalc)
        {
            boolean blacklisted = false;
            x = (int)tile.getCoordinates().getX() - (int)p.getX();
            y = (int)tile.getCoordinates().getY() - (int)p.getY();

            if (world.getTileFromTilemap(x,y).getRevealed())
            {
                tempMoveToCoords = world.getTileFromTilemap(x,y).getCoordinates();

                if (world.blacklistedPoints.size() > 0)
                {
                    for (Point bp : world.blacklistedPoints)
                    {
                        if (bp.equals(world.getTileFromTilemap(x,y).getCoordinates()))
                        {
                            blacklisted = true;
                        }
                    }
                }

                //If has pheremone. Add for pheremone processing
                if (world.getTileFromTilemap(x, y).getPheremone() > 0 && !blacklisted)
                {
                    pheremoneMap.put(world.getTileFromTilemap(x,y).getCoordinates(), world.getTileFromTilemap(x, y).getPheremone());
                }
            }
        }

        //Process pheremone tiles
        if (resolveLoop(movementHistory, world, tile) && !resolvingLoop)
        {
            if (pheremoneMap.size() > 0)
            {
                ArrayList<Point> maxPheremoneTiles = new ArrayList<>();

                int max = Collections.max(pheremoneMap.values());

                if (movementHistory.size() > 0)
                {
                    //If the last moved square is in the pheremone map, remove this from the list of valid movements
                    if (pheremoneMap.containsKey(movementHistory.get(movementHistory.size() - 1)))
                    {
                        System.out.println(pheremoneMap.size());
                        pheremoneMap.remove((movementHistory.get(movementHistory.size() - 1)));
                    }
                }

                if (pheremoneMap.size() > 0)
                {

                    for (HashMap.Entry<Point, Integer> entry : pheremoneMap.entrySet())
                    {
                        if (entry.getValue().equals(max))
                        {
                            maxPheremoneTiles.add(entry.getKey());
                        }
                    }

                    //Set moveetocoord from max pheremone tile or randomly select if more than one max
                    if (maxPheremoneTiles.size() > 1)
                    {
                        Collections.shuffle(maxPheremoneTiles);
                    }

                    tempMoveToCoords = maxPheremoneTiles.get(0);
                }
            }
        }

        if (resolvingLoop)
        {
            if (resolveCount == resolveLimit)
            {
                resolvingLoop = false;
            }
            else if (resolveCount == 0)
            {
                movementHistory = trimMovementHistory(movementHistory, tile);
                resolveCount++;
            }
            else
            {
                resolveCount++;
            }
        }

        ArrayList<Point> toRemove = new ArrayList<>();

        if (world.blacklistedPoints.size() > 0)
        {
            for (Point p : world.blacklistedPoints)
            {
                if (world.getTileFromTilemap(p).getPheremone() == 1)
                {
                    toRemove.add(p);
                }
            }

            if (toRemove.size() > 0)
            {
                for (Point p : toRemove)
                {
                    world.blacklistedPoints.remove(p);
                }
            }
        }



        if (tile.isWorldSpawn())
        {
            movementHistory.add(tile.getCoordinates());
        }


         move(tile, world.getTileFromTilemap(tempMoveToCoords), this);
         movementHistory.add(tempMoveToCoords);

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
            if (foraging)
            {
                Collections.reverse(movementHistory);
                movementHistory.remove(0);
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

    private boolean resolveLoop(ArrayList<Point> movementHistory, World world, WorldTile tile)
    {
        if (movementHistory.size() < 4)
        {
            return true;
        }

        int dupes = 0;
        int dupethreshold = 9;

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < movementHistory.size(); j++)
            {
                if (movementHistory.get(i).equals(movementHistory.get(j)))
                {
                    dupes++;
                }
            }
        }



        if (dupes > dupethreshold)
        {
            if (tile.getPheremone() > 800 && tile.getFood() == 0)
            {
                world.blacklistedPoints.add(tile.getCoordinates());
            }
            resolvingLoop = true;
            return false;
        }

        return true;
    }

    private ArrayList<Point> trimMovementHistory(ArrayList<Point> movementHistory, WorldTile tile)
    {
        ArrayList<Point> tempHistory = new ArrayList<>();

        for (Point p : movementHistory)
        {
            tempHistory.add(p);

            if (tile.getCoordinates().equals(p))
            {
                return tempHistory;
            }
        }

        return movementHistory;
    }
}
