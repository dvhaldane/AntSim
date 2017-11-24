package edu.uis.verhal1.ants;

import edu.uis.verhal1.driver.TickAction;
import edu.uis.verhal1.world.World;
import edu.uis.verhal1.world.WorldTile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class Scout extends Ant implements TickAction
{

    public Scout()
    {
        this.type = "SCOUT";
    }

    @Override
    public void doTickAction(World world, WorldTile tile)
    {
        if (this.ifDeadRemove(tile))
        {
            return;
        }
        else
        {
            //Movement Logic

            Random random = new Random();
            ArrayList<Point> validMoveToTiles = getValidMovementPoints(world, pointList, tile.getCoordinates());

            int pick = random.nextInt(validMoveToTiles.size());
            Point point = validMoveToTiles.get(pick);

            int x = (int) tile.getCoordinates().getX() - (int) point.getX();

            int y = (int) tile.getCoordinates().getY() - (int) point.getY();

            WorldTile targetTile = world.getTileFromTilemap(x, y);

            move(tile, targetTile, this);

            if (!targetTile.getRevealed())
            {
                int foodChance = random.nextInt(100);

                if (foodChance >= 75)
                {
                    int food = random.nextInt(500) + 501;
                    targetTile.setFood(targetTile.getFood() + food);
                }

                targetTile.reveal();

            }

            if (world.getDayChanged())
            {
                this.decrementLifeOneDay();
            }
        }
    }
}
