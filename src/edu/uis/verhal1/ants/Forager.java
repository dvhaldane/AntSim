package edu.uis.verhal1.ants;

import edu.uis.verhal1.driver.TickAction;
import edu.uis.verhal1.world.World;
import edu.uis.verhal1.world.WorldTile;

import java.awt.*;
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
            x = (int)tile.getCoordinates().getX() - (int)p.getX();
            y = (int)tile.getCoordinates().getY() - (int)p.getY();

            if (world.getTileFromTilemap(x,y).isRevealed() && !movementHistory.contains(world.getTileFromTilemap(x,y).getCoordinates()))
            {
                tempMoveToCoords = world.getTileFromTilemap(x,y).getCoordinates();

                //If has pheremone. Add for pheremone processing
                if (world.getTileFromTilemap(x, y).getPheremone() > 0)
                {
                    pheremoneMap.put(world.getTileFromTilemap(x,y).getCoordinates(), world.getTileFromTilemap(x, y).getPheremone());
                }
            }
        }

        //Process pheremone tiles
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
        if (tile.isWorldSpawn())
        {
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


            if (world.getTileFromTilemap(movementHistory.get(0)).isWorldSpawn())
            {
                world.getTileFromTilemap(movementHistory.get(0)).setFood(world.getTileFromTilemap(movementHistory.get(0)).getFood() + 1);
                this.food = 0;
            }

            movementHistory.remove(0);

        }
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
