package edu.uis.verhal1.driver;

import edu.uis.verhal1.world.World;
import edu.uis.verhal1.world.WorldTile;

public abstract class WorldManager
{
    public static void addToTileMap(World world, WorldTile tile, int posX, int posY)
    {
        world.getWorldTileMap()[posX][posY] = tile;
    }
}
