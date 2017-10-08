package edu.uis.verhal1.world;

import java.util.Random;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class World
{
    private int colonyWidth;
    private int colonyHeight;
    private int day;
    private boolean dayChanged;
    private boolean queenIsDead = false;
    private int turn;
    private boolean continuousMode;
    private WorldTile[][] worldTileMap;

    public World()
    {
        day = 0;
        turn = 0;
        colonyWidth = 27;
        colonyHeight = 27;
        continuousMode = false;
        worldTileMap = new WorldTile[27][27];
        dayChanged = false;
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
        System.out.println(x + "," + y);
        return worldTileMap[x][y];
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
