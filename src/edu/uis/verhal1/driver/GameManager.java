package edu.uis.verhal1.driver;
import edu.uis.verhal1.ants.*;
import edu.uis.verhal1.gui.*;
import edu.uis.verhal1.world.World;
import edu.uis.verhal1.world.WorldTile;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
class GameManager implements ActionListener, SimulationEventListener
{
    private final int colonyHeight;
    private final int colonyWidth;
    private int colonyMidpointX;
    private int colonyMidpointY;
    private final String stopBeforeReset = "You must stop the game before resetting it.";
    private final String setupGame = "You must set the game up before running it.";
    private final String endMessage = "Game has ended due to reason: ";
    private final String endReason_QUEEN = "The Queen has died.";
    private AntSimGUI gui;
    private ColonyView view;
    private final Timer gameTick = new Timer(1000,this);
    private final World world = new World();
    private boolean isSetup = false;

    GameManager()
    {
        colonyHeight = world.getColonyHeight();
        colonyWidth = world.getColonyWidth();
        performGameSetup();
    }

    public void simulationEventOccurred(SimulationEvent simEvent)
    {
        if (simEvent.getEventType() == SimulationEvent.NORMAL_SETUP_EVENT)
        {
            if (!isSetup)
            {
                setStartingTiles();
                isSetup = true;
            }
        }
        else if (simEvent.getEventType() == SimulationEvent.RESET_EVENT)
        {
            if (isSetup && !gameTick.isRunning())
            {
                gui.dispose();
                performGameSetup();
                reset();
            }
            else if (gameTick.isRunning())
            {
                JOptionPane.showMessageDialog(view,stopBeforeReset);
            }

        }
        else if (simEvent.getEventType() == SimulationEvent.RUN_EVENT)
        {
            if (!isSetup)
            {
                JOptionPane.showMessageDialog(view,setupGame);
            }
            else if (gameTick.isRunning())
            {
                gameTick.stop();
            }
            else
            {
                gameTick.start();
            }
        }
        else if (simEvent.getEventType() == SimulationEvent.STEP_EVENT)
        {
            if (!isSetup)
            {
                JOptionPane.showMessageDialog(view,setupGame);
            }
            else
            {
                gameTick.stop();
                performGameTick();
            }
        }
        else
        {
            System.out.println("Invalid");
        }
    }

    private void performGameSetup()
    {
        gui = new AntSimGUI();
        colonyMidpointY = (colonyHeight - 1) / 2;
        colonyMidpointX = (colonyWidth -1 ) / 2;
        view = new ColonyView(colonyHeight, colonyWidth);

        //Build Starting Tiles


        gui.initGUI(view);

        //Post GUI Generation Settings
        gui.centerScrollBars();
        GUIManager.updateWorldTime(gui, world);
        gui.addSimulationEventListener(this);

    }

    private void setStartingTiles()
    {
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

                    tile.reveal();
                    view.addColonyNodeView(node, x, y);
                    tile.setNode(node);
                    NodeManager.updateNodeDisplay(node, tile);
                    world.addTileToTileMap(tile, x, y);
                }
                else
                {
                    view.addColonyNodeView(node, x, y);
                    tile.setNode(node);
                    world.addTileToTileMap(tile, x, y);
                }
            }
        }
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

    private void reset()
    {

        for (int i = 0; i < world.getWorldTileMap().length; i++)
        {
            for (int j = 0; j < world.getWorldTileMap()[i].length; j++)
            {
                world.getWorldTileMap()[i][j] = null;
            }
        }

        setStartingTiles();

        //Postprocess Tiles
        /*
        for (int i = 0; i < world.getWorldTileMap().length; i++)
        {
            for (int j = 0; j < world.getWorldTileMap()[i].length; j++)
            {
                if (world.getWorldTileMap()[i][j] != null && world.getWorldTileMap()[i][j].isRevealed())
                {
                    WorldTile tile = world.getWorldTileMap()[i][j];
                    NodeManager.updateNodeDisplay(tile.getNode(), tile);
                }
            }
        }
        */

        world.setDay(0);
        world.setTurn(0);
    }

    private void endGame()
    {
        gameTick.stop();
        JOptionPane.showMessageDialog(view,endMessage + endReason_QUEEN);
    }

    private boolean shouldHatchBala()
    {
        Random random = new Random();
        int roll = random.nextInt(100);

        if (roll < 3)
        {
            return true;
        }

        return false;
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

        //Process Tiles
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
                    for (Object aSet : set)
                    {
                        Map.Entry mapEntry = (Map.Entry) aSet;

                        Object o = mapEntry.getValue();

                        if (o instanceof Bala)
                        {
                            tile.setBalaCount(tile.getBalaCount() + 1);
                            ((Bala) o).doTickAction(world, tile);
                        } else if (o instanceof Forager)
                        {
                            tile.setForagerCount(tile.getForagerCount() + 1);
                            ((Forager) o).doTickAction(world, tile);
                        } else if (o instanceof Scout)
                        {
                            tile.setScoutCount(tile.getScoutCount() + 1);
                            ((Scout) o).doTickAction(world, tile);
                        } else if (o instanceof Soldier)
                        {
                            tile.setSoldierCount(tile.getSoldierCount() + 1);
                            ((Soldier) o).doTickAction(world, tile);
                        } else if (o instanceof Queen)
                        {
                            ((Queen) o).doTickAction(world, tile);

                            if (((Queen) o).isDead())
                            {
                                world.setQueenIsDead();
                            }
                        }
                    }
                }
            }
        }

        //Spawn a bala
        if (shouldHatchBala())
        {
            Bala bala = new Bala();
            world.getTileFromTilemap(bala.getStartPoint()).addAnt(bala);
        }

        //Postprocess Tiles
        for (int i = 0; i < world.getWorldTileMap().length; i++)
        {
            for (int j = 0; j < world.getWorldTileMap()[i].length; j++)
            {
                if (world.getWorldTileMap()[i][j] != null)
                {
                    WorldTile tile = world.getWorldTileMap()[i][j];

                    tile.mergeAntQueue();

                    if (tile.getNode().visible() || tile.isRevealed())
                    {
                        if (world.getDayChanged())
                        {
                            tile.degradePheremone();
                        }

                        NodeManager.updateNodeDisplay(tile.getNode(), tile);

                    }
                }
            }
        }

        System.gc();

    }
}
