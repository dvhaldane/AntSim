package edu.uis.verhal1.driver;

import edu.uis.verhal1.ants.Ant;
import edu.uis.verhal1.gui.ColonyNodeView;
import edu.uis.verhal1.world.WorldTile;

public abstract class NodeManager
{
    public static void setSpawnNode(ColonyNodeView node)
    {
        node.setQueen(true);

    }


    public static void updateNodeDisplay(ColonyNodeView node, WorldTile tile)
    {
        int balaCount = 0;
        int foragerCount = 0;
        int scoutCount = 0;
        int soldierCount = 0;
        int foodAmount = 0;

        for (Object o : tile.getAntList())
        {
            if (o instanceof Ant)
            {
                switch (((Ant) o).getType())
                {
                    case "BALA":
                        balaCount++;
                    case "FORAGER":
                        foragerCount++;
                    case "SCOUT":
                        scoutCount++;
                    case "SOLDIER":
                        soldierCount++;
                }
            }
        }

        node.setBalaCount(balaCount);
        node.setForagerCount(foragerCount);
        node.setPheromoneLevel(tile.getPheremone());
        node.setScoutCount(scoutCount);
        node.setSoldierCount(soldierCount);
        node.setFoodAmount(tile.getFood());
        node.setID(String.valueOf((int)tile.getCoordinates().getX()) + "," + String.valueOf((int)tile.getCoordinates().getY()));
    }

}
