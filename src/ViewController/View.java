package ViewController;

import Model.Grid;
import Model.PathfindingStatistics;
import Model.Tile;
import Strategy.MazeGenerationStrategy.MazeGenerationStrategy;
import Strategy.MazeGenerationStrategy.MazeGenerationStrategy.MazeGen;
import Strategy.PathfindingStrategy.AStarStrategy;
import Strategy.PathfindingStrategy.PathfindingStrategy;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
public class View implements Observer
{
    private final int WIDTH = 1200;
    private final int HEIGHT = 660;
    private final TextField txtXTiles;//X
    private final TextField txtYTiles;//Y
    private final TextField txtTileSize;//Kích thước
    
    private final Separator separatorStats;//Cách ô thống kê
    private final Separator separatorAlgo;//Cách ô thuật toán
    
    private final Text txtStatsTitle;//Thống kê

//  
    private final Text txtStatsTilesTotal;//tổng ô
    private final Text txtStatsTilesTotalValue;//giá trị tổng ô
    private final Text txtStatsTilesVisited;//số ô đã thăm
    private final Text txtStatsTilesVisitedValue;//giá trị ô đã thăm
    private final Text txtStatsPathFound;//Tìm được đường đi
    private final Text txtStatsPathFoundValue;//Có/Không
    private final Text txtStatsPathCost;//Chi phí
    private final Text txtStatsPathCostValue;//Giá trị chi phí
    private final Text txtStatsWallAmount;//Số tường
    private final Text txtStatsWallAmountValue;//Giá trị
    private final Text txtStatsElapsedTime;//Thời gian chạy
    private final Text txtStatsElapsedTimeValue;//Giá trị thời gian
    
    private final Text txtAlgorithms;//Thuật toán
    private final Text txtAlgorithmsHeuristic;//hàm heuristics
    private final Text txtMaze;//Ma trận
    private final Button btnRun;//Chạy
    private final Button btnClear;//Xoá
    private final Button btnExit;//Thoát
    private final Button btnMaze;//Tạo ma trận
    private final Button btnCreateGrid;//Tạo kích thước ma trận
    private final ComboBox cbAlgorithmBox;//Thuật toán
    private final Button btnAlgorithm;
    private final ComboBox cbHeuristicBox;//Hàm heuristics
    private final ComboBox cbNodeBox;//Loại ô
    // Grid
    private final VBox leftPane;//VBox ở bên trái
    private final Pane parentGridPane;
    private Pane gridPane;
    private final Grid model;//tạo ma trận ô
    private final Scene scene;
    
    private final int padding = 2;
    private final String defaultXSize = "43";
    private final String defaultYSize = "31";
    private final String defaultTileSize = "20";
//    private final double leftPanelSize = 0.25;
    private final Font defaultFont = Font.font("Verdana", FontWeight.EXTRA_BOLD, 12);
    private final String defaultHboxStyle = "-fx-padding: 10;" ;
  
    
    public View(Grid model)
    {
        this.model = model;
        this.parentGridPane = new Pane();
        this.gridPane = null;
        
        this.leftPane = new VBox();
        this.leftPane.setPadding(new Insets(padding, padding, padding, padding));
        this.leftPane.setSpacing(10);
                
        VBox vboxCreateGrid = new VBox(padding);
        vboxCreateGrid.setStyle(defaultHboxStyle);
        GridPane createPane = new GridPane();
        createPane.setHgap(5);
        createPane.setPadding(new Insets(padding, padding, padding, padding));
        createPane.add(new Text("X: "), 0, 0);
        txtXTiles = new TextField(defaultXSize);
        createPane.add(txtXTiles, 1, 0);
        createPane.add(new Text("Y: "), 2, 0);
        txtYTiles = new TextField(defaultYSize);
        createPane.add(txtYTiles, 3, 0);
        createPane.add(new Text("KÍCH THƯỚC: "), 4, 0);
        txtTileSize = new TextField(defaultTileSize); 
        createPane.add(txtTileSize, 5, 0);
        HBox hboxCreateBtn = new HBox(padding);
        hboxCreateBtn.setAlignment(Pos.CENTER);
        btnCreateGrid = new Button("TẠO KÍCH THƯỚC MA TRẬN");
//        btnCreateGrid.setTooltip(new Tooltip("Overrides previous grid"));
        hboxCreateBtn.getChildren().add(btnCreateGrid);
//        HBox hboxShowCoords = new HBox(padding);
//        hboxShowCoords.setAlignment(Pos.CENTER);
        vboxCreateGrid.getChildren().addAll(createPane, hboxCreateBtn);
        
        HBox hboxNodeBox = new HBox(padding);
        hboxNodeBox.setAlignment(Pos.CENTER);
        hboxNodeBox.setStyle(defaultHboxStyle);
        Text txtNodeBox = new Text("LOẠI Ô: ");
        txtNodeBox.setFont(defaultFont);
        cbNodeBox = new ComboBox(FXCollections.observableArrayList(Tile.Type.values()));
        cbNodeBox.getItems().remove(Tile.Type.VISITED);
        cbNodeBox.getItems().remove(Tile.Type.PATH);
        cbNodeBox.getItems().remove(Tile.Type.HIGHLIGHT);
        cbNodeBox.getItems().remove(Tile.Type.VISITED_DENSE);
        cbNodeBox.getItems().remove(Tile.Type.VISITED_LIGHT);
        cbNodeBox.getItems().remove(Tile.Type.VISITED_MAX);
        cbNodeBox.getItems().remove(Tile.Type.VISITED_MEDIUM);
        cbNodeBox.getSelectionModel().selectFirst();
//        cbNodeBox.setTooltip(new Tooltip("Tile Type picker"));
        hboxNodeBox.getChildren().addAll(txtNodeBox, cbNodeBox);
        
        separatorAlgo = new Separator(Orientation.VERTICAL);
        VBox vboxAlgorithmBox = new VBox(padding);
        vboxAlgorithmBox.setAlignment(Pos.CENTER);
        vboxAlgorithmBox.setStyle(defaultHboxStyle);
        HBox hboxAlgorithmTxt = new HBox(padding);
        hboxAlgorithmTxt.setAlignment(Pos.BASELINE_CENTER);
        txtAlgorithms = new Text("THUẬT TOÁN");
        txtAlgorithms.setFont(defaultFont);
        txtAlgorithmsHeuristic = new Text("HÀM HEURSTICS");
        txtAlgorithmsHeuristic.setFont(defaultFont);
        hboxAlgorithmTxt.getChildren().addAll(txtAlgorithms, separatorAlgo, txtAlgorithmsHeuristic);
        HBox hboxcbAlgorithmBox = new HBox(padding);
        hboxcbAlgorithmBox.setAlignment(Pos.CENTER);
        btnAlgorithm = new Button("AStar");
        cbAlgorithmBox = new ComboBox(FXCollections.observableArrayList(PathfindingStrategy.Algorithms.values()));
//        cbAlgorithmBox.getItems().remove(PathfindingStrategy.Algorithms.Dijkstra);
        cbAlgorithmBox.getSelectionModel().selectFirst();
        
//        cbAlgorithmBox.setTooltip(new Tooltip("Algorithm picker"));
        cbHeuristicBox = new ComboBox(FXCollections.observableArrayList(AStarStrategy.Heuristic.values()));
        cbHeuristicBox.getItems().remove(AStarStrategy.Heuristic.Chebyshev);
        cbHeuristicBox.getItems().remove(AStarStrategy.Heuristic.Manhattan);
        cbHeuristicBox.getItems().remove(AStarStrategy.Heuristic.Octile);
        cbHeuristicBox.getSelectionModel().selectFirst();
    
//        cbHeuristicBox.setTooltip(new Tooltip("Heuristic for A* algorithm"));
        cbHeuristicBox.setDisable(false);
        hboxcbAlgorithmBox.getChildren().addAll(btnAlgorithm, cbHeuristicBox);
        vboxAlgorithmBox.getChildren().addAll(hboxAlgorithmTxt, hboxcbAlgorithmBox);
        
        VBox vboxMaze = new VBox(padding);
        vboxMaze.setAlignment(Pos.CENTER);
        vboxMaze.setStyle(defaultHboxStyle);
        HBox hboxMaze = new HBox(padding);
        hboxMaze.setAlignment(Pos.CENTER);
        txtMaze = new Text("MA TRẬN");
        txtMaze.setFont(defaultFont);
        hboxMaze.getChildren().add(txtMaze);
        HBox hboxMazeGen = new HBox(padding);
        hboxMazeGen.setAlignment(Pos.CENTER);
        btnMaze = new Button("TẠO");
//        btnMaze.setTooltip(new Tooltip("GENERATES A RANDOM MAZE"));
        hboxMazeGen.getChildren().addAll(btnMaze);
        vboxMaze.getChildren().addAll(hboxMaze, hboxMazeGen);
        
        HBox hboxUtilBtns = new HBox(padding);
        hboxUtilBtns.setAlignment(Pos.CENTER);
        hboxUtilBtns.setStyle(defaultHboxStyle);
        btnClear = new Button("XOÁ");
//        btnClear.setTooltip(new Tooltip("Resets all tiles to empty and no weight"));
        btnExit = new Button("THOÁT");
//        btnExit.setTooltip(new Tooltip("Exits the application"));
        btnRun = new Button("CHẠY");
//        btnRun.setTooltip(new Tooltip("Run Pathfinding Algorithm"));
        hboxUtilBtns.getChildren().addAll(btnRun, btnClear, btnExit);
        
        separatorStats = new Separator();
        VBox vboxStats = new VBox(padding);
        vboxStats.setAlignment(Pos.CENTER_LEFT);
        vboxStats.setStyle(defaultHboxStyle);
        HBox hboxStatsTitle = new HBox(padding);
        hboxStatsTitle.setAlignment(Pos.CENTER);
        txtStatsTitle = new Text("THỐNG KÊ");
        txtStatsTitle.setFont(defaultFont);
//        txtStatsTitleValue = new Text("");
        hboxStatsTitle.getChildren().addAll(txtStatsTitle);
        HBox hboxStatsWalls = new HBox(padding);
        txtStatsWallAmount = new Text("SỐ TƯỜNG: ");
        txtStatsWallAmountValue = new Text("");
        hboxStatsWalls.getChildren().addAll(txtStatsWallAmount, txtStatsWallAmountValue);
        HBox hboxStatsTotal = new HBox(padding);
        txtStatsTilesTotal = new Text("TỔNG Ô: ");
        txtStatsTilesTotalValue = new Text("");
        hboxStatsTotal.getChildren().addAll(txtStatsTilesTotal, txtStatsTilesTotalValue);
        HBox hboxStatsTilesVisited = new HBox(padding);
        txtStatsTilesVisited = new Text("Ô ĐÃ THĂM: ");
        txtStatsTilesVisitedValue = new Text("");
        hboxStatsTilesVisited.getChildren().addAll(txtStatsTilesVisited, txtStatsTilesVisitedValue);
        HBox hboxStatsPathFound = new HBox(padding);
        txtStatsPathFound = new Text("TÌM ĐƯỢC ĐƯỜNG ĐI: ");
        txtStatsPathFoundValue = new Text("");
        hboxStatsPathFound.getChildren().addAll(txtStatsPathFound, txtStatsPathFoundValue);
        HBox hboxStatsPathCost = new HBox(padding);
        txtStatsPathCost = new Text("CHI PHÍ: ");
        txtStatsPathCostValue = new Text("");
        hboxStatsPathCost.getChildren().addAll(txtStatsPathCost, txtStatsPathCostValue);
        HBox hboxStatsElapsedTime = new HBox(padding);
        txtStatsElapsedTime = new Text("THỜI GIAN CHẠY:");
        txtStatsElapsedTimeValue = new Text("");
        hboxStatsElapsedTime.getChildren().addAll(txtStatsElapsedTime, txtStatsElapsedTimeValue);
        vboxStats.getChildren().addAll(hboxStatsTitle, separatorStats, hboxStatsTotal, hboxStatsWalls, hboxStatsTilesVisited, hboxStatsPathFound, hboxStatsPathCost, hboxStatsElapsedTime);      
        leftPane.getChildren().addAll(vboxCreateGrid, hboxNodeBox, vboxAlgorithmBox, vboxMaze, vboxStats, hboxUtilBtns);
        leftPane.setMaxHeight(HEIGHT);
        leftPane.setMaxWidth(WIDTH/4);
        leftPane.setMinSize(0, 0);
        this.scene = new Scene(initComponents(), WIDTH, HEIGHT);
    }
    
    public void setTriggers(Controller controller)
    {
        // Changes type of tile on click
        cbNodeBox.setOnAction((event) -> 
        {
            FXCollections.observableArrayList(Tile.Type.values()).stream().filter((item) -> (cbNodeBox.getValue().toString().equals(item.toString()))).forEachOrdered((item) ->
            {
                controller.doChangeClickType(item);
            });
        });
        
        // Locks heuristic if algorithm is not A*
//        cbAlgorithmBox.setOnAction((event) ->
//        {
//            cbHeuristicBox.setDisable(!(cbAlgorithmBox.getSelectionModel().getSelectedItem().toString().contains("AStar")));
//        });
        
        // Clear button clears the grid
        btnClear.setOnAction((event) ->
        {
            controller.doClearGrid();
        });
        
        // Exits the application
        btnExit.setOnAction((event) -> 
        {
            System.exit(0);
        });
        
        
        // Generates a random maze
        btnMaze.setOnAction((event) ->
        {
	        if(gridPane != null)
	            controller.doGenerateMaze();
        });
        
        // Initialized the grid
        btnCreateGrid.setOnAction((event) ->
        {
            // Makes sure maze is only generated with odd x and y
            int x = Integer.valueOf(txtXTiles.getText());
            x = (x % 2 == 0) ? x - 1 : x; 
            int y = Integer.valueOf(txtYTiles.getText());
            y = (y % 2 == 0) ? y - 1 : y;
            int size = Integer.valueOf(txtTileSize.getText());
            
            if(parentGridPane.getChildren().contains(gridPane))
                parentGridPane.getChildren().remove(gridPane);
            
            // Initializes the grid
            model.gridInit(x, y, size);
            this.fillGrid(model.getGrid());
        });
        
        
        btnRun.setOnAction((event) -> 
        {
            PathfindingStrategy.Algorithms algorithm = null;
            AStarStrategy.Heuristic heuristic = null;
            
            for(PathfindingStrategy.Algorithms algo : FXCollections.observableArrayList(PathfindingStrategy.Algorithms.values()))
            {
                if(algo.toString() == btnAlgorithm.getText())
                {
                    algorithm = algo;
                }
            }
            
            for(AStarStrategy.Heuristic heur : FXCollections.observableArrayList(AStarStrategy.Heuristic.values()))
            {
                if(heur == cbHeuristicBox.getValue())
                {
                    heuristic = heur;
                }
            }
            
            boolean success;
            if(algorithm != null && heuristic != null)
            {
                try 
                {
                    success = controller.doShortestPathAlgorithm(algorithm, heuristic);
                } 
                catch (InterruptedException ex) 
                {
                    Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public Scene getScene()
    {
        return this.scene;
    }
    
    public void createGrid()
    {
        btnCreateGrid.fire();
    }
    
    private SplitPane initComponents()
    {
        VBox root = new VBox();        
        root.getChildren().add(this.parentGridPane); 
//        root.setMaxSize(3*WIDTH/4, HEIGHT);
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(root,this.leftPane);       
//        this.leftPane.maxWidthProperty().bind(splitPane.widthProperty().multiply(this.leftPanelSize));
//        this.leftPane.minWidthProperty().bind(splitPane.widthProperty().multiply(this.leftPanelSize));        
        return splitPane;
    }
    private void fillGrid(Tile[][] tiles)
    {
        this.gridPane = new Pane();
        for(Tile[] row : tiles)
        {
            for(Tile tile: row)
            {
                gridPane.getChildren().add(tile.getStackPane());
            }
        }
        this.parentGridPane.getChildren().add(gridPane);
    }
    @Override
    public void update(Observable o, Object arg)
    {
        if(o instanceof Grid)
        {
            if(arg instanceof PathfindingStatistics)
            {
                PathfindingStatistics stats = (PathfindingStatistics)arg;              
                this.txtStatsTilesTotalValue.setText(String.valueOf(stats.getTilesTotal()));
                this.txtStatsWallAmountValue.setText(String.valueOf(stats.getWallSize()));
                this.txtStatsTilesVisitedValue.setText(String.valueOf(stats.getTilesVisited()));
                this.txtStatsPathFoundValue.setText((stats.isPathFound()) ? "CÓ" : "KHÔNG");
                this.txtStatsPathCostValue.setText(String.valueOf(stats.getPathCost()));
                this.txtStatsElapsedTimeValue.setText(String.format("%.4f Milliseconds", stats.getElapsedTime() * Math.pow(10, -6)));
            }
        }
    }
}
