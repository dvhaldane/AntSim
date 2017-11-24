package edu.uis.verhal1.world;

import java.awt.*;
import java.util.Random;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class World
{
    private int day = 0;
    private int turn = 0;
    private final int colonyWidth = 27;
    private final int colonyHeight = 27;
    private boolean continuousMode = false;
    private final WorldTile[][] worldTileMap = new WorldTile[27][27];
    private boolean dayChanged = false;
    private boolean queenIsDead = false;

    public World()
    {
        this.day = day;
        this.turn = turn;
        this.continuousMode = continuousMode;
        this.dayChanged = dayChanged;
        this.queenIsDead = queenIsDead;
    }

    public void setQueenIsDead()
    {
        this.queenIsDead = true;
    }

    public Boolean getQueenIsDead()
    {
        return queenIsDead;
    }

    public int getDay()
    {
        return this.day;
    }

    public void setDay(int day)
    {
        this.day = day;
    }

    public void incrementDay()
    {
        this.day += 1;
        this.dayChanged = true;
    }

    public int getColonyWidth()
    {
        return this.colonyWidth;
    }

    public int getColonyHeight()
    {
        return this.colonyHeight;
    }

    public boolean getContinuousMode()
    {
        return continuousMode;
    }

    public void setContinousMode(Boolean mode)
    {
        this.continuousMode = mode;
    }

    public void reset()
    {
        this.day = day;
        this.turn = turn;
        this.continuousMode = continuousMode;
        this.dayChanged = dayChanged;
        this.queenIsDead = queenIsDead;

        System.out.println(day);
        System.out.println(turn);
    }

    public boolean getDayChanged()
    {
        return this.dayChanged;
    }

    public void setDayChanged(boolean changed)
    {
        this.dayChanged = changed;
    }

    public int getTurn()
    {
        return this.turn;
    }

    public void setTurn(int turn)
    {
        this.turn = turn;
    }

    public void incrementTurn()
    {
        this.turn += 1;
    }

    public WorldTile getTileFromTilemap(int x, int y)
    {
        return worldTileMap[x][y];
    }

    public WorldTile getTileFromTilemap(Point p)
    {
        return worldTileMap[(int)p.getX()][(int)p.getY()];
    }

    public void addTileToTileMap(WorldTile tile, int x, int y)
    {
        worldTileMap[x][y] = tile;
    }

    public void addTileToTileMap(int x, int y)
    {
        worldTileMap[x][y] = new WorldTile(x,y);

        Random random = new Random();
        int foodGenChance = random.nextInt(100);

        if (foodGenChance >= 75)
        {
            int food = random.nextInt(501) + 500;

            worldTileMap[x][y].setFood(food);
        }


    }

    public WorldTile[][] getWorldTileMap()
    {
        return worldTileMap;
    }

}
