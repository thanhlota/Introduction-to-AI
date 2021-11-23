package Strategy.HeuristicStrategy;

import Model.Tile;
public abstract class HeuristicStrategy
{
    //protected final boolean breakTies;
            
    public HeuristicStrategy()
    {
        
    }    
    public abstract double computeHeuristic(Tile root, Tile target);
}
