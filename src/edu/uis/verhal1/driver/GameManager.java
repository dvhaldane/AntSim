package edu.uis.verhal1.driver;

import edu.uis.verhal1.ants.Ant;
import edu.uis.verhal1.ants.Queen;
import edu.uis.verhal1.gui.*;
import edu.uis.verhal1.world.World;
import edu.uis.verhal1.world.WorldTile;
import javafx.scene.Node;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class GameManager implements ActionListener
{
    private int colonyHeight;
    private int colonyWidth;
    private int colonyMidpointX;
    private int colonyMidpointY;
    private ColonyView view;
    private World world;
    private AntSimGUI gui;

    boolean gameEnd = false;
    private Timer gameTick = new Timer(1000,this);

    GameManager()
    {
        colonyHeight = 27;
        colonyWidth = 27;
        performGameSetup();
        gameTick.start();
    }

    private void performGameSetup()
    {

        //Set GUI Constraints
        gui = new AntSimGUI();

        //Get Colony Midpoints
        colonyMidpointY = (colonyHeight - 1) / 2;
        colonyMidpointX = (colonyWidth -1 ) / 2;
        System.out.println(colonyMidpointX);
        System.out.println(colonyMidpointY);

        view = new ColonyView(colonyHeight, colonyWidth);

        //Build World
        world = new World();

        //Build Spawn Tile
        WorldTile spawnTile = new WorldTile();
        TileManager.setSpawnTile(spawnTile);

        ColonyNodeView spawnNode = new ColonyNodeView();
        NodeManager.setSpawnNode(spawnNode);

        //Build Adjacent Tiles
        //Update GUI
        NodeManager.updateNodeCounts(spawnNode, spawnTile);

        //Add Node to GUI World Board and WorldTileMap
        WorldManager.addToTileMap(world, spawnTile, colonyMidpointX, colonyMidpointY);
        view.addColonyNodeView(spawnNode, colonyMidpointX, colonyMidpointY);

        //Init the GUI
        gui.initGUI(view);

        //Post GUI Generation Settings
        spawnNode.showNode();
        gui.centerScrollBars();
        GUIManager.updateWorldTime(gui, world);

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (gameEnd)
        {
            gameTick.stop();
        }
        else
        {
            performGameTick();
        }
    }

    private void performGameTick()
    {

        //Manage Time Updates
        world.setTurn(world.getTurn() + 1);

        if (world.getTurn() > 0 && world.getTurn() % 10 == 0)
        {
            world.incrementDay();
        }

        GUIManager.updateWorldTime(gui, world);

        if (world.getEndGame() == true)
        {
            gameTick.stop();
        }


        //Do ticks per tile
        for (int i = 0; i < world.getWorldTileMap().length; i++)
        {
            for (int j = 0; j <world.getWorldTileMap()[i].length; j++)
            {
                if (world.getWorldTileMap()[i][j] != null)
                {
                    WorldTile tile = world.getWorldTileMap()[i][j];
                    AntManager.doAntListGameTick(world, tile, tile.getAntList());

                    ColonyNodeView node = new ColonyNodeView();
                    NodeManager.updateNodeCounts(node, tile);
                    view.addColonyNodeView(node, i, j);
                }
            }
        }





    }
}
