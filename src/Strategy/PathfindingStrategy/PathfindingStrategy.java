package Strategy.PathfindingStrategy;

import Model.Grid;
import Model.PathfindingStatistics;
import Model.Tile;
import Util.Painter;
import java.util.List;
public abstract class PathfindingStrategy 
{
    protected Painter painter;
    protected PathfindingStatistics statistics;
    
    public static enum Algorithms{
        Dijkstra,
        AStar
    }
    
    public PathfindingStrategy()
    {
        this.painter = Painter.getInstance();
    }
    public final int algorithm(Grid model, List<Tile> path)
    {
        long start = System.nanoTime();
        this.statistics = new PathfindingStatistics(model);
        this.statistics.setWallSize(model.getWallsAmount());
        
        int cost = this.runPathfinder(model, path);

        long end = System.nanoTime();
        this.statistics.setElapsedTime(end - start);
        this.statistics.updateObservers();
        this.painter.drawPath(path, model);
        
        System.out.println(this.statistics);
        
        return cost;
    }
    protected abstract int runPathfinder(Grid model, List<Tile> path);
}