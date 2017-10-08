package edu.uis.verhal1.world;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class World
{
    private int day;
    private boolean dayChanged;
    private boolean endGame = false;
    private int turn;
    private boolean continuousMode;
    private WorldTile[][] worldTileMap;

    public World()
    {
        day = 0;
        turn = 0;
        continuousMode = false;
        worldTileMap = new WorldTile[27][27];
        dayChanged = false;
    }

    public void endGame()
    {
        this.endGame = true;
    }

    public Boolean getEndGame()
    {
        return endGame;
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

    public WorldTile getTile(int x, int y)
    {
        return worldTileMap[x][y];
    }

    public void setTile(WorldTile tile, int x, int y)
    {
        worldTileMap[x][y] = tile;
    }

    public WorldTile[][] getWorldTileMap()
    {
        return worldTileMap;
    }

}
