package Model;

import java.util.Observable;

public class PathfindingStatistics extends Observable
{
    private final int tilesTotal;
    private int tilesVisited;
    private boolean pathFound;
    private int pathCost;
    private long elapsedTime;
    private int wallSize;
    
    public PathfindingStatistics(Grid model)
    {
        this.addObserver(model);
        
        this.tilesTotal = model.getTiles().size();
        this.tilesVisited = 0;
        this.pathFound = false;
        this.pathCost = -1;
        this.elapsedTime = 0;
        this.wallSize = 0;
    }
    public void incrementVisited()
    {
        this.tilesVisited++;
    }
    public void setPathFound(boolean pathFound, int pathCost)
    {
        this.pathFound = pathFound;
        this.pathCost = (pathFound) ? pathCost : -1;
    }
    public void setElapsedTime(long elapsedTime)
    {
        this.elapsedTime = elapsedTime;
    }
    public int getTilesTotal()
    {
        return tilesTotal;
    }
    public int getTilesVisited()
    {
        return tilesVisited;
    }
    public boolean isPathFound()
    {
        return pathFound;
    }
    public int getPathCost()
    {
        return pathCost;
    }
    public double getElapsedTime()
    {
        return elapsedTime;
    }
    public int getWallSize()
    {
        return wallSize;
    }
    public void setWallSize(int wallSize)
    {
        this.wallSize = wallSize;
    }
    public void updateObservers()
    {
        setChanged();
        notifyObservers();
    }

//    @Override
//    public String toString()
//    {
//        String str = String.format("Pathfinding Statistics{ TotalTiles = %d, VisitedTiles = %d, PathFound = %b, PathCost = %d, ElapsedTime = %.4f milliseconds }" , 
//                this.getTilesTotal(), this.getTilesVisited(), this.isPathFound(), (this.isPathFound()) ? this.getPathCost() : -1, this.getElapsedTime() * Math.pow(10, -6));
//        
//        return str;
//    }
}
