package edu.uis.verhal1.ants;

import edu.uis.verhal1.driver.TickAction;
import edu.uis.verhal1.world.World;
import edu.uis.verhal1.world.WorldTile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class Bala extends Ant implements TickAction
{

    private Point startPoint = new Point(0,0);

    public Bala()
    {
        this.type = "BALA";
        this.startPoint = getStartPoint();
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
            if (tile.getAntMap().size() > 1)
            {

                Random random = new Random();

                boolean killAnAnt = random.nextBoolean();

                if (killAnAnt)
                {

                    Ant targetAnt = this;

                    while(this.getID() == targetAnt.getID())
                    {

                        ArrayList<Integer> keysArray = new ArrayList<Integer>(tile.getAntMap().keySet());

                        int selectedKey = keysArray.get(random.nextInt(keysArray.size()));

                        targetAnt = tile.getAntMap().get(selectedKey);

                        if (this.getID() != tile.getAntMap().get(selectedKey).getID())
                        {
                            tile.getAntMap().get(selectedKey).kill();
                            tile.queueAntRemove(tile.getAntMap().get(selectedKey));
                        }

                    }
                }


            }
            else
            {
                ArrayList<Point> validPointsForMovementCalc = getValidMovementPoints(world, pointList, tile.getCoordinates());

                Collections.shuffle(validPointsForMovementCalc);

                Point tempPoint = validPointsForMovementCalc.get(0);

                int x = (int) tile.getCoordinates().getX() - (int) tempPoint.getX();
                int y = (int) tile.getCoordinates().getY() - (int) tempPoint.getY();

                Point tempMoveToCoords = world.getTileFromTilemap(x, y).getCoordinates();

                move(tile, world.getTileFromTilemap(tempMoveToCoords), this);
            }
        }
    }

    public Point getStartPoint()
    {
        Random random = new Random();

        int randomPoint = random.nextInt(27);
        int x = 0;
        int y = 0;

        int roll = random.nextInt(100);

        if (roll < 50)
        {
            x = randomPoint;
        }
        else
        {
            y = randomPoint;
        }

        return new Point(x,y);
    }
}
