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
        double dx = root.getX() - target.getX();
        double dy = root.getY() - target.getY();       
        return D * Math.sqrt(dx * dx + dy * dy);
    }
    
}
