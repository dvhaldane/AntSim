package edu.uis.verhal1.driver;

import edu.uis.verhal1.ants.Ant;
import edu.uis.verhal1.ants.Queen;
import edu.uis.verhal1.gui.*;
import edu.uis.verhal1.world.World;
import edu.uis.verhal1.world.WorldTile;
import javafx.scene.Node;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public class GameManager implements ActionListener
{
    private int colonyHeight;
    private int colonyWidth;
    private int colonyMidpointX;
    private int colonyMidpointY;
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

        ColonyView view = new ColonyView(colonyHeight, colonyWidth);

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

        //Add Node to GUI World Board
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

    private void performGameEndCheck(World world)
    {
        WorldTile tile = world.getTile(0,0);
        List list = tile.getAntList();

        for (Object o : list)
        {

        }


    }

    private void performGameTick()
    {

        //Manage Time Updates
        world.setTurn(world.getTurn() + 1);

        if (world.getTurn() > 0 && world.getTurn() % 10 == 0)
        {
            world.setDay(world.getDay() + 1);
        }

        GUIManager.updateWorldTime(gui, world);
    }
}
