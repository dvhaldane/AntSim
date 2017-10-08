package edu.uis.verhal1.driver;

import edu.uis.verhal1.ants.Forager;
import edu.uis.verhal1.ants.Queen;
import edu.uis.verhal1.ants.Scout;
import edu.uis.verhal1.ants.Soldier;
import edu.uis.verhal1.world.WorldTile;

public abstract class TileManager
{
    public static void createSpawnTile(WorldTile tile)
    {
        tile.setWorldSpawn();

        tile.setFood(1000);

        Queen queen = new Queen();
        tile.addAnt(queen);

        for (int i = 0; i < 10; i++)
        {
            Soldier soldier = new Soldier();
            tile.addAnt(soldier);
            tile.setSoldierCount(tile.getSoldierCount() + 1);
        }

        for (int i = 0; i < 50; i++)
        {
            Forager forager = new Forager();
            tile.addAnt(forager);
            tile.setForagerCount(tile.getForagerCount() + 1);
        }

        for (int i = 0; i < 4; i ++)
        {
            Scout scout = new Scout();
            tile.addAnt(scout);
            tile.setScoutCount(tile.getScoutCount() + 1);
        }

    }
}
