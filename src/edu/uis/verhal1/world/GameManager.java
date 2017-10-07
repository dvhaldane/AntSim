package edu.uis.verhal1.world;

import edu.uis.verhal1.gui.*;

/**
 * Created by HaldaneDavidV on 10/7/2017.
 */
public abstract class GameManager implements SimulationEventListener
{

    public static void main(String[] args)
    {
        performGameSetup();
    }

    private static void performGameSetup()
    {
        //Set GUI Constraints
        AntSimGUI gui = new AntSimGUI();
        ColonyView view = new ColonyView(27,27);


        //Build World
        World world = new World();
        WorldTile tempwt = new WorldTile();


        //Push Tile To GUI
        ColonyNodeView spawn = new ColonyNodeView();

        //Set GUI Counts from World Tile
        spawn.setBalaCount((int)tempwt.getAntMap().get("BALA"));
        spawn.setForagerCount((int)tempwt.getAntMap().get("FORAGER"));
        spawn.setPheromoneLevel(tempwt.getPheremone());
        spawn.setScoutCount((int)tempwt.getAntMap().get("SCOUT"));
        spawn.setSoldierCount((int)tempwt.getAntMap().get("SOLDIER"));

        spawn.setFoodAmount(tempwt.getFood());
        view.addColonyNodeView(spawn, 13 ,13);
        spawn.showNode();


        //Init the gui
        gui.initGUI(view);

        //Post GUI Generation Settings
        gui.centerScrollBars();
        gui.setTime("Days: " + world.getDay() + " | Turns: " + world.getTurn() );



    }

    private static void performGameTick()
    {

    }
}
