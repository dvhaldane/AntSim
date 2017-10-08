package edu.uis.verhal1.ants;

import edu.uis.verhal1.world.WorldTile;

import java.util.Random;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class Queen extends Ant
{
    public Queen()
    {
        this.type = "QUEEN";
        this.life = 365 * 20;
    }

    public void eat(WorldTile tile)
    {
        tile.setFood(tile.getFood() - 1);
    }

    public void hatch(WorldTile tile)
    {
        Random random = new Random();

        int roll = random.nextInt(100);
        {
            if (roll >= 0 && roll < 50)
            {
                tile.queueHatch(new Forager());
            } else if (roll >= 50 && roll < 75)
            {
                tile.queueHatch(new Scout());
            } else
            {
                tile.queueHatch(new Soldier());
            }
        }
    }
}
