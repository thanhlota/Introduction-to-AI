
package Factory;

import Strategy.MazeGenerationStrategy.BacktrackingStrategy;
import Strategy.MazeGenerationStrategy.MazeGenerationStrategy;
import Strategy.HeuristicStrategy.ChebyshevStrategy;
import Strategy.HeuristicStrategy.EuclideanStrategy;
import Strategy.HeuristicStrategy.HeuristicStrategy;
import Strategy.HeuristicStrategy.ManhattanStrategy;
import Strategy.HeuristicStrategy.OctileStrategy;
import Strategy.HeuristicStrategy.PythagorasStrategy;
import Strategy.PathfindingStrategy.AStarStrategy;
import Strategy.PathfindingStrategy.DijkstraStrategy;
import Strategy.PathfindingStrategy.PathfindingStrategy;

public class StrategyFactory
{
    public static PathfindingStrategy getPathfindingStrategy(PathfindingStrategy.Algorithms algorithmStrategy, HeuristicStrategy heuristicStrategy)
    {
        switch(algorithmStrategy)
        {
            case Dijkstra:
                return new DijkstraStrategy();
            case AStar:
                return new AStarStrategy(false, heuristicStrategy);
            default:
                throw new IllegalArgumentException("Chưa chọn thuật toán!");
        }
    }
    
    public static HeuristicStrategy getHeuristicStrategy(AStarStrategy.Heuristic strategy)
    {
        switch(strategy)
        {
            case Pythagoras:
                return new PythagorasStrategy();
            case Manhattan:
                return new ManhattanStrategy();
            case Chebyshev:
                return new ChebyshevStrategy();
            case Eudclidean:
                return new EuclideanStrategy();
            case Octile:
            	return new OctileStrategy();
            default:
                throw new IllegalArgumentException("Chưa chọn hàm heuristics!");
        }
    }

    public static MazeGenerationStrategy getMazeGenStrategy()
    {
    	return new BacktrackingStrategy();
    }
}
