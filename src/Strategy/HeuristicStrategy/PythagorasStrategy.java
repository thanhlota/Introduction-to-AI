package Strategy.HeuristicStrategy;

import Model.Tile;
public class PythagorasStrategy extends HeuristicStrategy
{
    public PythagorasStrategy()
    {
        super();
    }
    
    @Override
    public double computeHeuristic(Tile root, Tile target)
    {
    	double D= 1.0;
        return D * Math.sqrt((root.getX() - target.getX()) * (root.getX() - target.getX()) + (root.getY() - target.getY()) * (root.getY() - target.getY()));
    }
}
