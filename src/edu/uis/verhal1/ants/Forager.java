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

    }

    private void forage(World world, WorldTile tile)
    {

        ArrayList<Point> validMoveToTiles = getValidMovementPoints(world, pointList, tile.getCoordinates());

        Collections.shuffle(validMoveToTiles);

        Point temp = validMoveToTiles.get(0);

        int x = (int)tile.getCoordinates().getX() - (int)temp.getX();
        int y = (int)tile.getCoordinates().getY() - (int)temp.getY();

        WorldTile tempTile = new WorldTile(x,y);

        //Move to square with highest level of pheremone
        for (Point p : validMoveToTiles)
        {
            x = (int)tile.getCoordinates().getX() - (int)p.getX();
            y = (int)tile.getCoordinates().getY() - (int)p.getY();

            if (world.getTileFromTilemap(x,y).getPheremone() > world.getTileFromTilemap(x,y).getPheremone())
            {
                tempTile = world.getTileFromTilemap(x,y);
            }
        }

         move(tile, tempTile, this);
         movementHistory.add(tile.getCoordinates());

    }

    private void returnToNest(World world, WorldTile tile)
    {

    }
}
