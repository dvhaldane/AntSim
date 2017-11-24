package edu.uis.verhal1.ants;

import edu.uis.verhal1.driver.TickAction;
import edu.uis.verhal1.world.World;
import edu.uis.verhal1.world.WorldTile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class Soldier extends Ant implements TickAction
{
    public Soldier()
    {
        this.type = "SOLDIER";
    }

    @Override
    public void doTickAction(World world, WorldTile tile)
    {
        boolean balaFound = false;
        Random random = new Random();

        if (this.ifDeadRemove(tile))
        {
            return;
        }
        else
        {
            if (tile.getBalaCount() > 0)
            {
                int roll = random.nextInt(2);

                System.out.println(tile.getCoordinates().getX() + "," + tile.getCoordinates().getY() + " rolled " + roll);

                if (roll == 1)
                {
                    Set set = tile.getAntMap().entrySet();
                    for (Object aSet : set)
                    {
                        Map.Entry mapEntry = (Map.Entry) aSet;

                        Object o = mapEntry.getValue();

                        if (o instanceof Bala)
                        {
                            if (!((Bala) o).isDead())
                            {
                                ((Bala) o).kill();
                                tile.queueAntRemove((Bala) o);
                                break;
                            }
                        }
                    }
                }
            } else // Find balas and move to adjacent squares or move randomly
            {

                ArrayList<Point> validMoveToTiles = getValidMovementPoints(world, pointList, tile.getCoordinates());

                for (Point p : validMoveToTiles)
                {
                    int x = (int) tile.getCoordinates().getX() - (int) p.getX();

                    int y = (int) tile.getCoordinates().getY() - (int) p.getY();

                    if (world.getTileFromTilemap(x, y).getBalaCount() > 0)
                    {

                        WorldTile targetTile = world.getTileFromTilemap(x, y);

                        move(tile, targetTile, this);
                        balaFound = true;
                    }
                }

                if (!balaFound)
                {

                    int pick = random.nextInt(validMoveToTiles.size());

                    Point point = validMoveToTiles.get(pick);

                    int x = (int) tile.getCoordinates().getX() - (int) point.getX();

                    int y = (int) tile.getCoordinates().getY() - (int) point.getY();

                    WorldTile targetTile = world.getTileFromTilemap(x, y);

                    move(tile, targetTile, this);
                }

            }
        }

    }
}
