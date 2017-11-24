package edu.uis.verhal1.ants;

import edu.uis.verhal1.driver.TickAction;
import edu.uis.verhal1.world.World;
import edu.uis.verhal1.world.WorldTile;

import java.util.Random;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class Queen extends Ant implements TickAction
{
    public Queen()
    {
        this.type = "QUEEN";
        this.lifeInDays = 365 * 20;
    }

    private void eat(WorldTile tile)
    {
        tile.setFood(tile.getFood() - 1);
    }

    private void hatch(WorldTile tile)
    {
        Random random = new Random();

        int roll = random.nextInt(100);
        {
            if (roll >= 0 && roll < 50)
            {
                tile.queueAntAdd(new Forager());
            }
            else if (roll >= 50 && roll < 75)
            {
                tile.queueAntAdd(new Scout());
            }
            else
            {
                tile.queueAntAdd(new Soldier());
            }
        }
    }

    public void doTickAction(World world, WorldTile tile)
    {
        //Eat
        if (tile.getFood() == 0)
        {
            this.kill();
        }
        else
        {
            this.eat(tile);
        }

        if (world.getDayChanged())
        {
            this.hatch(tile);
            this.decrementLifeOneDay();
        }
    }
}
