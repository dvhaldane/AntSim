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
                tile.queueAnt(new Forager());
            } else if (roll >= 50 && roll < 75)
            {
                tile.queueAnt(new Scout());
            } else
            {
                tile.queueAnt(new Soldier());
            }
        }
    }

    public void doTickAction(World world, WorldTile tile)
    {

        //Manage Life
        if (this.isAlive() == false)
        {
            world.endGame();
        }
        else
        {

            //Eat
            if (tile.getFood() == 0)
            {
                world.endGame();
            }
            else
            {
                this.eat(tile);
            }

            if (world.getDayChanged() == true)
            {
                this.hatch(tile);
                this.incrementLife();
                world.setDayChanged(false);
            }



        }
    }
}
