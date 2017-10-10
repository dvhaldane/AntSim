package edu.uis.verhal1.driver;

import edu.uis.verhal1.world.World;
import edu.uis.verhal1.world.WorldTile;

public interface TickAction
{
    void doTickAction(World world, WorldTile tile);
}
