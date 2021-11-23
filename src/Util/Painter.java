package Util;

import Model.Grid;
import Model.Tile;
import Strategy.PathfindingStrategy.PathfindingStrategy;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
public final class Painter
{
    private static final Painter INSTANCE = new Painter();
    private final Executor executor;
    
    private Painter()
    {
        executor = Executors.newSingleThreadExecutor();
    }
    public static Painter getInstance()
    {
        return INSTANCE;
    }
    public void drawPath(List<Tile> path, Grid model) 
    {
        this.executor.execute(
        () ->
        {
            path.stream().filter((tile) -> !(tile == model.getTarget() || tile == model.getRoot())).map((tile) ->
            {
                tile.setAttributes(Tile.Type.PATH, tile.getWeight());
                return tile;                
            }).forEachOrdered((_item) ->
            {
                try
                {
                    Thread.sleep(20);
                }
                catch (InterruptedException ex)
                {
                    Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        });
    }
    public void drawTile(Tile tile, Tile target, Tile root, Tile.Type type, long sleep)
    {
        this.executor.execute(()->
        {
            if(tile != target && tile != root)
                tile.setAttributes(type, tile.getWeight());
           
            try
            {
                Thread.sleep(sleep);
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(PathfindingStrategy.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    public void highlightTile(Tile tile, long waitTime)
    {
        this.drawTile(tile, null, null, Tile.Type.HIGHLIGHT, waitTime);
        this.drawTile(tile, null, null, Tile.Type.EMPTY, waitTime);
    }
    public void highlightTile(Tile tile, Tile.Type type, long waitTime)
    {
        this.drawTile(tile, null, null, Tile.Type.HIGHLIGHT, waitTime);
        this.drawTile(tile, null, null, type, waitTime);
    }
    public void clearPath(Grid model)
    {
        this.executor.execute(()->
        {
            Tile tile;
            for(int y = 0; y < model.getYSize(); y++)
            {
                for(int x = 0; x < model.getXSize(); x++)
                {
                    tile = model.getGrid()[x][y]; 
                    if(tile.getType() == Tile.Type.PATH || tile.getType() == Tile.Type.VISITED)
                    {
                        tile.setAttributes(Tile.Type.EMPTY, tile.getWeight());
                    }
                }
            }
        });
    }
}
