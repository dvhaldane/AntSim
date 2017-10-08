package edu.uis.verhal1.ants;

import edu.uis.verhal1.driver.TickAction;
import edu.uis.verhal1.world.World;
import edu.uis.verhal1.world.WorldTile;

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

    }
}
