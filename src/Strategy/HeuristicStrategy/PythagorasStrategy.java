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
        return (root.getX() - target.getX()) * (root.getX() - target.getX()) + (root.getY() - target.getY()) * (root.getY() - target.getY());
    }
}
