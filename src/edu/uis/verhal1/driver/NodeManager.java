package edu.uis.verhal1.driver;

import edu.uis.verhal1.ants.Ant;
import edu.uis.verhal1.gui.ColonyNodeView;
import edu.uis.verhal1.world.WorldTile;

import java.util.*;

public abstract class NodeManager
{

    static HashMap<ColonyNodeView, WorldTile> queuedNodesForRefresh = new HashMap<>();

    public static void addToRefreshQueue(ColonyNodeView node, WorldTile tile)
    {
        queuedNodesForRefresh.put(node, tile);
    }

    public static void updateQueuedNodeDisplay()
    {
        Set set = queuedNodesForRefresh.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext())
        {
            Map.Entry mapEntry = (Map.Entry) iterator.next();

            updateNodeDisplay((ColonyNodeView)mapEntry.getKey(), (WorldTile)mapEntry.getValue());
        }

        queuedNodesForRefresh.clear();
    }

    public static void createSpawnNode(ColonyNodeView node)
    {
        node.setQueen(true);
        node.showQueenIcon();
    }

    public static void updateNodeDisplay(ColonyNodeView node, WorldTile tile)
    {

        int balaCount = tile.getBalaCount();
        int foragerCount = tile.getForagerCount();
        int scoutCount = tile.getScoutCount();
        int soldierCount = tile.getSoldierCount();

        if (balaCount > 0)
            node.showBalaIcon();
        else
            node.hideBalaIcon();

        if (foragerCount > 0)
            node.showForagerIcon();
        else
            node.hideForagerIcon();

        if (scoutCount > 0)
            node.showScoutIcon();
        else
            node.hideScoutIcon();

        if (soldierCount > 0)
            node.showSoldierIcon();
        else
            node.hideSoldierIcon();

        if (tile.isWorldSpawn())
        {
            node.setQueen(true);
            node.showQueenIcon();
        }


        node.setBalaCount(balaCount);
        node.setForagerCount(foragerCount);
        node.setPheromoneLevel(tile.getPheremone());
        node.setScoutCount(scoutCount);
        node.setSoldierCount(soldierCount);
        node.setFoodAmount(tile.getFood());
        node.setID(String.valueOf((int)tile.getCoordinates().getX()) + "," + String.valueOf((int)tile.getCoordinates().getY()));
        node.showNode();
    }

}
