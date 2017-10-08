package edu.uis.verhal1.driver;

import edu.uis.verhal1.ants.*;
import edu.uis.verhal1.world.World;
import edu.uis.verhal1.world.WorldTile;

import java.util.List;

public abstract class AntManager
{
    public static void doAntListGameTick(World world, WorldTile tile, List<Ant> antList)
    {
        for (Object o : antList)
        {
            if (o instanceof Bala)
            {
                roleBala(world, (Bala) o);
            }
            else if (o instanceof Forager)
            {
                roleForager(world, (Forager) o);
            }
            else if (o instanceof Scout)
            {
                roleScout(world, (Scout) o);
            }
            else if (o instanceof Soldier)
            {
                roleSoldier(world, (Soldier) o);
            }
            else if (o instanceof Queen)
            {
                roleQueen(world, tile, (Queen) o);
            }
        }

        tile.mergeQueue();
    }

    public static void roleBala(World world, Bala bala)
    {

    }

    public static void roleForager(World world, Forager forager)
    {

    }

    public static void roleScout(World world, Scout scout)
    {

    }

    public static void roleSoldier(World world, Soldier soldier)
    {

    }

    public static void roleQueen(World world, WorldTile tile, Queen queen)
    {

        //Manage Life
        if (queen.getLife() == 0)
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
                queen.eat(tile);
            }

            if (world.getDayChanged() == true)
            {
                queen.hatch(tile);
                queen.incrementLife();
                world.setDayChanged(false);
            }



        }
    }
}
