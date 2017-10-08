package edu.uis.verhal1.ants;

import edu.uis.verhal1.driver.TickAction;
import edu.uis.verhal1.world.World;
import edu.uis.verhal1.world.WorldTile;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class Forager extends Ant implements TickAction
{
    public Forager()
    {
        this.type = "FORAGER";
    }

    @Override
    public void doTickAction(World world, WorldTile tile)
    {

    }
}
