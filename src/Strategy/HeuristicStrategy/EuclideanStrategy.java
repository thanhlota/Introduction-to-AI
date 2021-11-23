package Strategy.HeuristicStrategy;

import Model.Tile;
public class EuclideanStrategy extends HeuristicStrategy
{

    public EuclideanStrategy()
    {
        super();
    }
    
    @Override
    public double computeHeuristic(Tile root, Tile target)
    {
        double D = 1.0;
        double dx = Math.abs(root.getX() - target.getX());
        double dy = Math.abs(root.getY() - target.getY());
        
        return D * Math.sqrt(dx * dx + dy * dy);
    }
    
}
