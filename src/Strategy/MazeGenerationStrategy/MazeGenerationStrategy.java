package Strategy.MazeGenerationStrategy;

import Model.Grid;
import Model.Tile;
import Util.Painter;
import java.util.List;
import java.util.Random;
public abstract class MazeGenerationStrategy
{
    protected Random random;
    protected Painter painter;
    protected long painterWait;
    public static enum MazeGen{
        Backtracker
    }
    
    public MazeGenerationStrategy() {
    
        this.painter = Painter.getInstance();
        this.painterWait = 1;
    }
    
    public final void generate(Grid model)
    {
    	
        model.clearGrid();
        int y_size=model.getYSize();
    	int x_size=model.getXSize();
    	Tile grid[][]=model.getGrid();
    	for (int y = 0; y < y_size; y++) {
			for (int x = 0; x < x_size; x++) {
//				grid[x][y].clearTile();
				grid[x][y].setTileStroke(false);
			}
		}
        this.setDefaultWalls(model.getGrid(), model.getXSize(), model.getYSize());
        this.algorithm(model);
    }

    
    public abstract void algorithm(Grid model);
    
    protected void addNeighbors(Grid model, Tile tile, List<Tile> neighbors)
    {
        neighbors.clear();
        Tile temp;
        temp = model.getNorthTile(tile);
        if(temp != null)
            neighbors.add((temp.getY() % 2 != 0) ? model.getGrid()[temp.getX()][temp.getY() - 1] : temp);

        temp = model.getSouthTile(tile);
        if(temp != null)
            neighbors.add((temp.getY() % 2 != 0) ? model.getGrid()[temp.getX()][temp.getY() + 1] : temp);

        temp = model.getWestTile(tile);
        if(temp != null)
            neighbors.add((temp.getX() % 2 != 0) ? model.getGrid()[temp.getX() - 1][temp.getY()] : temp);

        temp = model.getEastTile(tile);
        if(temp != null) 
            neighbors.add((temp.getX() % 2 != 0) ? model.getGrid()[temp.getX() + 1][temp.getY()] : temp);
    }
    
    protected void setDefaultWalls(Tile[][] grid, int x_size, int y_size)
    {
        for(int y = 0; y < y_size; y++)
        {
            for(int x = 0; x < x_size; x++)
            {
                grid[x][y].setAttributes(Tile.Type.WALL, grid[x][y].getDefaultWeight());
            }
        }
    }
    
    protected void removeWallBetween(Tile[][] grid, Tile a, Tile b)
    {
        int x = a.getX();
        int y = a.getY();
        if     (a.getX() < b.getX()) x += 1;
        else if(a.getY() < b.getY()) y += 1;
        else if(a.getX() > b.getX()) x -= 1;
        else if(a.getY() > b.getY()) y -= 1;
        this.painter.highlightTile(grid[x][y], this.painterWait);
    }

    protected int getRandomInt(int max, int min)
    {
        this.random = new Random();
        return ((int) (Math.random()*(max - min))) + min; 
    }
}
