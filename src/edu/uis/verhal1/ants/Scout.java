package edu.uis.verhal1.ants;

import edu.uis.verhal1.driver.NodeManager;
import edu.uis.verhal1.driver.TickAction;
import edu.uis.verhal1.driver.TileManager;
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
        //Movement Logic

        Random random = new Random();
        ArrayList<Point> temp = getValidMovementPoints(world, pointList, tile.getCoordinates());

        int pick = random.nextInt(temp.size());
        Point point = temp.get(pick);

        int x = (int)tile.getCoordinates().getX() - (int)point.getX();
        int y = (int)tile.getCoordinates().getY() - (int)point.getY();

        if (world.getTileFromTilemap(x,y) != null)
        {
            world.getTileFromTilemap(x, y).queueAntAdd(this);
            TileManager.queueTileForRefresh(world.getTileFromTilemap(x, y));
        }

        NodeManager.addToRefreshQueue(world.getTileFromTilemap(x,y).getNode(), world.getTileFromTilemap(x,y));

        tile.queueAntRemove(this);
        //Remove Ant from this tile


    }

}
