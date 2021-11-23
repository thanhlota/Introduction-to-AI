package ViewController;

import Model.Grid;
import Model.Tile;
import Strategy.PathfindingStrategy.PathfindingStrategy;
import Factory.StrategyFactory;
import Strategy.HeuristicStrategy.HeuristicStrategy;
import Strategy.MazeGenerationStrategy.MazeGenerationStrategy;
import Strategy.PathfindingStrategy.AStarStrategy;
public class Controller
{
    private final Grid model;
    private final View view;
    
    public Controller(Grid model, View view)
    {
        this.model = model;
        this.view = view;
        this.view.setTriggers(this);
        this.view.createGrid();
        
        this.model.addObserver(view);
    }
    
    public void doClearGrid()
    {
        this.model.clearGrid();
    }
    
    public void doChangeClickType(Tile.Type type)
    {   
        this.model.changeClickType(type);
    }
    
    public void doAddRandomWeights()
    {
        this.model.addRandomWeights();
    }
    
    public void doAddRandomWalls()
    {
        this.model.addRandomWalls();
    }
    
    public void doToggleTileBorder(boolean setBorder)
    {
        this.model.addTileBorders(setBorder);
    }

    public void doGenerateMaze()
    {
        MazeGenerationStrategy mazeGenerationStrategy = StrategyFactory.getMazeGenStrategy();
        this.model.generateRandomMaze(mazeGenerationStrategy);
    }
    
    public void doToggleTileCoords(boolean toAdd)
    {
        this.model.toggleCoords(toAdd);
    }
    
    public boolean doShortestPathAlgorithm(PathfindingStrategy.Algorithms algorithm, AStarStrategy.Heuristic heuristic) throws InterruptedException
    {
        HeuristicStrategy heuristicStrategy = StrategyFactory.getHeuristicStrategy(heuristic);
        PathfindingStrategy pathfindingStrategy = StrategyFactory.getPathfindingStrategy(algorithm, heuristicStrategy);
        return this.model.executePathfinding(pathfindingStrategy);
    }
}
