/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategy.PathfindingStrategy;

import Model.Grid;
import Model.Tile;
import Strategy.HeuristicStrategy.HeuristicStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author frank
 */
public class AStarStrategy extends PathfindingStrategy
{
    private final boolean findOptimalSolution;
    private final HeuristicStrategy heuristic;
    public static enum Heuristic
    {
        Pythagoras,
        Eudclidean,
        Manhattan,
        Chebyshev,
        Octile,
       
    }
    public AStarStrategy(boolean findOptimalSolution, HeuristicStrategy heuristic)
    {
        super();
        this.findOptimalSolution = findOptimalSolution;
        this.heuristic = heuristic;
    }

    @Override
    public int runPathfinder(Grid model, List<Tile> path)
    {
        List<Node> nodes = new ArrayList<>();
        Node rootNode = null;
        Node targetNode = null;
        
        for(Tile tile : model.getTiles())
        {
            Node node = new Node(tile);
            
            if(model.getRoot() == tile)
                rootNode = node;
            else if(model.getTarget() == tile)
                targetNode = node;
                
            nodes.add(node);
        }
        
        nodes.forEach((node) ->
        {
            node.setNeighbors(nodes, model);
        });
        
        executeAStar(rootNode, targetNode, this.heuristic);
        
        this.addPath(nodes, path, model.getTarget());
        
        int cost = this.calculateCost(path) + 1;
        
        if(!path.isEmpty())
        {
            this.statistics.setPathFound(true, cost);
        }
        
        return cost;
    }
    
    private void executeAStar(Node rootNode, Node targetNode, HeuristicStrategy heuristic)
    {
        Node currentNode = rootNode;
        
        currentNode.setLocalGoal(0.0);
        currentNode.setGlobalGoal(heuristic.computeHeuristic(rootNode.getTile(), targetNode.getTile()));
        
        Queue<Node> notTestedNodes = new PriorityQueue<>();
        notTestedNodes.add(currentNode);
        
        while (!notTestedNodes.isEmpty())
        {
            if(!this.findOptimalSolution && currentNode == targetNode) break;
            
            while(!notTestedNodes.isEmpty() && notTestedNodes.peek().isIsVisited())
                notTestedNodes.poll();
            
            if(notTestedNodes.isEmpty())
                break;
            
            currentNode = notTestedNodes.poll();
            this.statistics.incrementVisited();
            painter.drawTile(currentNode.getTile(), rootNode.getTile(), targetNode.getTile(), Tile.Type.HIGHLIGHT, 2);
            currentNode.setIsVisited(true);
            
            for(Node nodeNeighbor : currentNode.getNeighbors())
            {
                if(!nodeNeighbor.isIsVisited() && !nodeNeighbor.isWall())
                    notTestedNodes.add(nodeNeighbor);
                
                double goal = currentNode.getLocalGoal() + nodeNeighbor.getTile().getWeight(); 
                
                if(goal < nodeNeighbor.getLocalGoal())
                {
                    nodeNeighbor.setParent(currentNode);
                    nodeNeighbor.setLocalGoal(goal);
                    
                    nodeNeighbor.setGlobalGoal(nodeNeighbor.getLocalGoal() + heuristic.computeHeuristic(nodeNeighbor.getTile(), targetNode.getTile()));
                }
            }
            painter.drawTile(currentNode.getTile(), rootNode.getTile(), targetNode.getTile(), Tile.Type.VISITED, 0);
        }
    }
    private int calculateCost(List<Tile> path)
    {
        int total = -1;
        total = path.stream().map((tile) -> tile.getWeight()).reduce(total, Integer::sum);
        return total;
    }
    private List<Tile> addPath(List<Node> nodes, List<Tile> path, Tile target)
    {
        Node parentNode = null;
        
        for(Node node : nodes)
        {
            if(node.getTile() == target)
            {
                parentNode = node;
                break;
            }
        }
        
        while(parentNode != null)
        {
            path.add(parentNode.getTile());
            parentNode = parentNode.getParent();
        }
        
        path.remove(path.size() - 1);
        
        Collections.reverse(path);
        
        return path;
    }
    private class Node implements Comparable<Node>
    {
        private final Tile tile;
        private final List<Node> neighbors;
        private Node parent;
        private boolean isVisited;
        private double globalGoal;
        private double localGoal;
        
        public Node(Tile tile)
        {
            this.tile = tile;
            this.parent = null;
            this.neighbors = new ArrayList<>();
            this.isVisited = false;
            this.globalGoal = Double.MAX_VALUE; 
            this.localGoal = Double.MAX_VALUE;
        }
        public double getGlobalGoal()
        {
            return globalGoal;
        }
        public void setGlobalGoal(double globalGoal)
        {
            this.globalGoal = globalGoal;
        }
        public double getLocalGoal()
        {
            return localGoal;
        }
        public void setLocalGoal(double localGoal)
        {
            this.localGoal = localGoal;
        }
        public boolean isIsVisited()
        {
            return isVisited;
        }
        public void setIsVisited(boolean isVisited)
        {
            this.isVisited = isVisited;
        }
        public List<Node> getNeighbors()
        {
            return neighbors;
        }
        public void setNeighbors(List<Node> nodes, Grid model)
        {
            List<Tile> tileNeighbors = model.getTileNeighbors(this.getTile());

            for(Node node : nodes)
            {
                if(tileNeighbors.contains(node.getTile()))
                {
                    this.neighbors.add(node);
                }
            }
        }
        public Tile getTile()
        {
            return tile;
        }
        public Node getParent()
        {
            return parent;
        }
        public void setParent(Node parent)
        {
            this.parent = parent;
        }
        
        public boolean isWall()
        {
            return this.tile.isWall();
        }
        
        public int getX()
        {
            return this.tile.getX();
        }
        
        public int getY()
        {
            return this.tile.getY();
        }

        @Override
        public int compareTo(Node other)
        {
            if(this.getGlobalGoal() < other.getGlobalGoal())
                return -1;
            if(this.getGlobalGoal() > other.getGlobalGoal())
                return 1;
            return 0;
        }

//        @Override
//        public String toString()
//        {
//            return "Node{" + "parent=" + parent + ", isVisited=" + isVisited + ", globalGoal=" + globalGoal + '}';
//        }
    }
}