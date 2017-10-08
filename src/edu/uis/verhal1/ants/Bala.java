package edu.uis.verhal1.ants;

import edu.uis.verhal1.driver.TickAction;
import edu.uis.verhal1.world.World;
import edu.uis.verhal1.world.WorldTile;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class Bala extends Ant implements TickAction
{

    public Bala()
    {
        this.type = "BALA";
    }

    @Override
    public void doTickAction(World world, WorldTile tile)
    {

    }
}
