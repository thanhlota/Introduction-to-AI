package Strategy.MazeGenerationStrategy;

import Model.Grid;
import Model.Tile;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class BacktrackingStrategy extends MazeGenerationStrategy
{

    public BacktrackingStrategy()
    {
        super();
    }
    
    @Override
    public void algorithm(Grid model)
    {
        Tile[][] grid = model.getGrid();
        
        Stack<Tile> stack = new Stack<>();
        List<Tile> neighbors = new ArrayList<>();
        Set<Tile> visited = new HashSet<>();
        int k=model.getXSize();
        int h=model.getYSize();
        Random randx= new Random(); 
        int randomX = randx.nextInt(k/2) *2;
        Random randy= new Random(); 
        int randomY = randy.nextInt(k/2) *2;
        Tile currentTile = grid[randomX][randomY];     
        stack.push(currentTile);
        visited.add(currentTile);
        
        while(!stack.isEmpty())
        {
            this.addNeighbors(model, currentTile, neighbors);
            for(Iterator<Tile> iter = neighbors.iterator(); iter.hasNext();)
                if(visited.contains(iter.next()))
                    iter.remove();
            
            if(neighbors.isEmpty())
            {
                currentTile = stack.pop();
                this.painter.highlightTile(currentTile, painterWait);
                continue;
            }
            
            Tile randomNeighbor = neighbors.get(this.getRandomInt(neighbors.size(), 0));
            this.painter.drawTile(randomNeighbor, null, null, Tile.Type.EMPTY, this.painterWait);
            
            this.removeWallBetween(grid, currentTile, randomNeighbor);
            
            currentTile = randomNeighbor;
            visited.add(randomNeighbor);
            stack.push(randomNeighbor);
            
            this.painter.highlightTile(randomNeighbor, this.painterWait);
        }
    }
}