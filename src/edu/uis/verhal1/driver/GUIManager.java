package edu.uis.verhal1.driver;

import edu.uis.verhal1.gui.AntSimGUI;
import edu.uis.verhal1.world.World;

abstract class GUIManager
{
    public static void updateWorldTime(AntSimGUI gui, World world)
    {
        gui.setTime("Days: " + world.getDay() + " | Turns: " + world.getTurn() );
    }
}
