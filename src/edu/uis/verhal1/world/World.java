package edu.uis.verhal1.world;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class World
{
    private int day;
    private boolean end = false;
    private int turn;
    private boolean continuousMode;
    private WorldTile[][] grid;

    public World()
    {
        day = 0;
        turn = 0;
        continuousMode = false;
        grid = new WorldTile[27][27];
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
    }

    public boolean getContinuousMode()
    {
        return continuousMode;
    }

    public void setContinousMode(Boolean mode)
    {
        this.continuousMode = mode;
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

    public WorldTile getTile(int x, int y)
    {
        return grid[x][y];
    }

    public void setTile(WorldTile tile, int x, int y)
    {
        grid[x][y] = tile;
    }


}
