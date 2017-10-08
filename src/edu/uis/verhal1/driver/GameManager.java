package edu.uis.verhal1.driver;

import edu.uis.verhal1.ants.*;
import edu.uis.verhal1.gui.*;
import edu.uis.verhal1.world.World;
import edu.uis.verhal1.world.WorldTile;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class GameManager implements ActionListener
{
    private int colonyHeight;
    private int colonyWidth;
    private int colonyMidpointX;
    private int colonyMidpointY;
    private String endMessage = "Game has ended due to reason: ";
    private String endReason_QUEEN = "The Queen has died.";
    private AntSimGUI gui;
    private ColonyView view;
    private Timer gameTick = new Timer(2000,this);
    private World world = new World();

    GameManager()
    {
        colonyHeight = world.getColonyHeight();
        colonyWidth = world.getColonyWidth();
        performGameSetup();
        gameTick.start();
    }

    private void performGameSetup()
    {

        gui = new AntSimGUI();

        colonyMidpointY = (colonyHeight - 1) / 2;
        colonyMidpointX = (colonyWidth -1 ) / 2;

        view = new ColonyView(colonyHeight, colonyWidth);



        //Build Starting Tiles
        for (int x = 0; x < colonyWidth; x ++)
        {
            for (int y = 0; y < colonyHeight; y++)
            {
                WorldTile tile = new WorldTile(x, y);
                ColonyNodeView node = new ColonyNodeView();

                if (x > colonyMidpointX - 2 && x < colonyMidpointX + 2 && y > colonyMidpointY - 2 && y < colonyMidpointY + 2)
                {
                    if (x == colonyMidpointX && y == colonyMidpointY)
                    {
                        NodeManager.createSpawnNode(node);
                        TileManager.createSpawnTile(tile);
                    }

                    view.addColonyNodeView(node, x, y);
                    tile.setNode(node);
                    NodeManager.updateNodeDisplay(node, tile);
                    world.addTileToTileMap(tile, x, y);
                }
                else
                {
                    view.addColonyNodeView(node, x, y);
                    tile.setNode(node);
                    //NodeManager.updateNodeDisplay(node, tile);
                    world.addTileToTileMap(tile, x, y);
                }


            }
        }

        gui.initGUI(view);

        //Post GUI Generation Settings
        gui.centerScrollBars();
        GUIManager.updateWorldTime(gui, world);

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

        performGameTick();

        if (world.getQueenIsDead())
        {
            endGame();
        }

    }

    private void endGame()
    {
        gameTick.stop();
        JOptionPane.showMessageDialog(view,endMessage + endReason_QUEEN);
    }

    private void performGameTick()
    {

        //Manage Time Updates
        world.setTurn(world.getTurn() + 1);

        if (world.getTurn() > 0 && world.getTurn() % 10 == 0)
        {
            world.incrementDay();
            world.setDayChanged(true);
        }
        else
        {
            world.setDayChanged(false);
        }

        GUIManager.updateWorldTime(gui, world);

        //Do Ticks Per Tile
        for (int i = 0; i < world.getWorldTileMap().length; i++)
        {
            for (int j = 0; j <world.getWorldTileMap()[i].length; j++)
            {
                if (world.getWorldTileMap()[i][j] != null)
                {
                    WorldTile tile = world.getWorldTileMap()[i][j];

                    //Perform ant counts while iterating through ants
                    tile.resetCounts();

                    //Do Ant Ticks
                    Set set = tile.getAntMap().entrySet();
                    Iterator iterator = set.iterator();
                    while (iterator.hasNext())
                    {
                        Map.Entry mapEntry = (Map.Entry)iterator.next();

                        Object o = mapEntry.getValue();

                        if (o instanceof Bala)
                        {
                            tile.setBalaCount(tile.getBalaCount() + 1);
                            ((Bala) o).doTickAction(world, tile);
                        }
                        else if (o instanceof Forager)
                        {
                            tile.setForagerCount(tile.getForagerCount() + 1);
                            ((Forager) o).doTickAction(world, tile);
                        }
                        else if (o instanceof Scout)
                        {
                            tile.setScoutCount(tile.getScoutCount() + 1);
                            ((Scout) o).doTickAction(world, tile);
                        }
                        else if (o instanceof Soldier)
                        {
                            tile.setSoldierCount(tile.getSoldierCount() + 1);
                            ((Soldier) o).doTickAction(world, tile);
                        }
                        else if (o instanceof Queen)
                        {
                            ((Queen) o).doTickAction(world, tile);

                            if (((Queen) o).isDead())
                            {
                                world.setQueenIsDead();
                            }
                        }
                    }

                    //Merge Queued Ants
                    tile.mergeQueue();

                    if (tile.getNode().visible())
                    NodeManager.updateNodeDisplay(tile.getNode(), tile);
                    NodeManager.updateQueuedNodeDisplay();

                }
            }
        }





    }
}
