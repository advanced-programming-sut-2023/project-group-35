package view;

import controller.*;
import Enum.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Block;
import model.Map;
import model.Reign;

import static view.MapMenu.BLOCK_SIZE;


public class GameMenu extends Menu{
    private GameController gameController;
    private Pane mapPane;
    private Pane menuPane;
    private Map gameMap;
    private Rectangle selectedRectangle;
    public static int BuildingImageSize;
    public static int screenHeight = 912;
    public static int screenWidth = 1368;
    private BuildingType selectedBuilding;


    public void setGameController(GameController gameController) {
        this.gameController = gameController;
        this.gameMap = gameController.getMap();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setFullScreen(true);
        Pane root = new Pane();
        initializeMap(root);
        initMenuPane(root);
        initializeMenuBar();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initMenuPane(Pane root) {
        Pane pane = new Pane();
        root.getChildren().add(pane);
        pane.setLayoutY(600);
        pane.setLayoutX(0);
//        pane.setPrefWidth(root.getPrefWidth());
        pane.setPrefWidth(screenWidth);
        pane.setPrefHeight(200);
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(212, 195, 142), CornerRadii.EMPTY, Insets.EMPTY)));
        menuPane = pane;
    }
    public void initializeMap(Pane root){
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.pannableProperty().set(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setMaxHeight(600);
//        System.out.println(root.getHeight() + " wi: " + root.getWidth());
        scrollPane.setMaxWidth(1368);

        root.getChildren().add(scrollPane);
        //scrollPane.setBackground(new Background(new BackgroundImage()));
        mapPane = new Pane();
        scrollPane.setContent(mapPane);
        int i = 0;
        for (Block block : gameMap.getBlocks()) {
            System.out.println(i++);
            Rectangle rectangle = new Rectangle(block.getX() * BLOCK_SIZE,  block.getY() * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            rectangle.setFill(new ImagePattern(block.getFieldType().getFieldImage()));
            mapPane.getChildren().add(rectangle);
            if (block.hasABuilding()) {
                setMapBlockImageView(mapPane, block.getBuilding().getBuildingType().getImage(), block, true);
            }
            if (block.getTree() != null) {
                setMapBlockImageView(mapPane, block.getTree().getImage(), block, true);
            }
            if (block.hasBase() || gameMap.isABase(block.x, block.y)) {
                setMapBlockImageView(mapPane, BuildingType.BASE.getImage(), block, true);
            }
            setRectangleSettings(rectangle, block);
        }
    }
    public ImageView setMapBlockImageView(Pane pane, Image image, Block block, boolean isForMap) {
        ImageView imageView = new ImageView(image);
        imageView.setX(block.getX() * BLOCK_SIZE);
        imageView.setY(block.getY() * BLOCK_SIZE);
        if(isForMap) {
            imageView.setFitHeight(BLOCK_SIZE);
            imageView.setFitWidth(BLOCK_SIZE);
        }
        pane.getChildren().add(imageView);
        return imageView;
    }
    private void initializeMenuBar() {

        menuPane.getChildren().add(new Button("ala"));
        TabPane buildingsTabPane = createBuildingsTabPane();
        menuPane.getChildren().add(buildingsTabPane);
    }

    public TabPane createBuildingsTabPane() {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().add(getBuildingTabFor("1.png", BuildingType.BARRACK, BuildingType.MERCENARY_CAMP, BuildingType.ARMOURY, BuildingType.BIG_STONE_GATEHOUSE, BuildingType.SMALL_STONE_GATE, BuildingType.LOOKOUT_TOWER,BuildingType.LOOKOUT_TOWER, BuildingType.PERIMETER_TOWER, BuildingType.CIRCLE_TOWER, BuildingType.SQUARE_TOWER, BuildingType.ENGINEER_GUILD));
        tabPane.getTabs().add(getBuildingTabFor("2.png", BuildingType.STOCK_PILE, BuildingType.WOOD_CUTTER, BuildingType.STONE_MINE, BuildingType.IRON_MINE, BuildingType.PITCH_RIG, BuildingType.SHOP));
        tabPane.getTabs().add(getBuildingTabFor("3.png", BuildingType.APPLE_GARDEN, BuildingType.WHEAT_FARM, BuildingType.HOP_FARM));
        tabPane.getTabs().add(getBuildingTabFor("4.png", BuildingType.HOVEL, BuildingType.CHURCH, BuildingType.CATHEDRAL));
        tabPane.getTabs().add(getBuildingTabFor("5.png", BuildingType.FLETCHER, BuildingType.POLE_TURNER, BuildingType.BLACK_SMITH, BuildingType.ARMOURER));
        tabPane.getTabs().add(getBuildingTabFor("6.png", BuildingType.FOOD_STOCK_PILE, BuildingType.BAKERY, BuildingType.BREWERY, BuildingType.MILL, BuildingType.INN));

        return tabPane;
    }
    public Tab getBuildingTabFor(String iconUrl, BuildingType...buildingTypes) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        HBox hBox = InitStyle.createHbox();
        for (BuildingType building : buildingTypes) {
            ImageView imageView = InitStyle.getImageView(building.getImage(), 60, 60);
            hBox.getChildren().add(imageView);
            imageView.setOnDragDetected(event -> {
                Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(imageView.snapshot(new SnapshotParameters(),null));
                selectedBuilding = BuildingType.getBuildingByImage(imageView.getImage());
                System.out.println("selected building; " + selectedBuilding);
                //event.setDragDetect(true);
                db.setContent(content);
                event.consume();
            });
        }
        Tab tab = new Tab();
        tab.setContent(scrollPane);
        return tab;
    }
    public void setTabMenuIcon(Tab tab, Image image) {
        Circle circle = new Circle(10);
        circle.setFill(new ImagePattern(image, 0, 0, 10, 10, false));
        tab.setGraphic(circle);
    }

    public static Integer askUserTheUnitToSelect(String units) {
        System.out.println("you have more than one unit in this block!");
        System.out.println("which one do you want to select?");
        System.out.println(units);
        return scanner.nextInt();
    }

    public boolean isUserSureToFinishTurn() {
        while(true) {
            System.out.println("are you sure you want to finish your turn?");
            input = scanner.nextLine();
            if(input.matches("\\s*yes\\s*")) return true;
            if(input.matches("\\s*no\\s*")) return false;
            System.out.println("please try again");
        }
    }
    public boolean isUserSureToQuitGame() {
        while (true) {
            System.out.println("are you sure you want to quit the game?");
            System.out.println("remember if you quit the game you don't get any scores");
            input = scanner.nextLine();
            if(input.matches("\\s*yes\\s*")) return true;
            if(input.matches("\\s*no\\s*")) return false;
            System.out.println("I couldn't understand, please try again.");
        }
    }


    public static void announceDeadReign(Reign reign, int score) {
        System.out.println("reign " + reign.getNickName() + "is now dead and out of the game!");
        System.out.println("they could collect " + score + "scores!");
    }

    public void setRectangleSettings(Rectangle rectangle, Block block) {
        //todo find another way to do it the right way

//        rectangle.setOnDragOver(event -> {
//            event.acceptTransferModes(TransferMode.ANY);
//            if(event.getSource() == rectangle) return;
//            if(getBlockByRectangle(rectangle).isOccupied(map)) rectangle.setStroke(javafx.scene.paint.Color.rgb(138, 39, 36));
//            else rectangle.setStroke(javafx.scene.paint.Color.rgb(109, 161, 26));
//            //System.out.println("drag over");
//            event.consume();
//        });
//
//
//        rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                Tooltip.install(rectangle, InitStyle.BuildToolTip(block.getBlockInfo(true)));
//                rectangle.setOnKeyPressed(new EventHandler<KeyEvent>() {
//                    @Override
//                    public void handle(KeyEvent keyEvent) {
//                        if(keyEvent.getCode().equals(KeyCode.DELETE)) block.clearBlock(null); //todo check if works correctly
//                    }
//
//                });
//            }
//        });
//        rectangle.setOnDragDropped(event -> {
//            Dragboard db = event.getDragboard();
//            boolean success = false;
//            if (db.hasString()) {
//                rectangle.setStyle("-fx-border-width:2px;-fx-border-color:black;-fx-background-color: "+db.getString());
//                success = true;
//            }
//            System.out.println("drag dropped");
////            if(selectedTree != null) {
////                int x = getTheRightAxes(rectangle.getX(), INSET, BLOCK_SIZE);
////                int y = getTheRightAxes(rectangle.getY(), INSET, BLOCK_SIZE);
////                Block block1 = gameMap.getBlockByLocation(x, y);
////                //System.out.println(block1.x + " y; " + block1.y);
////                if(block1.isOccupied(gameMap)){
////                    selectedTree = null;
////                    return;
////                }
////                //System.out.println("done");
////                setMapBlockImageView(mapPane, selectedTree.getImage(), block1, true);
////                gameMap.getBlockByLocation(x, y).setTree(selectedTree);
////                selectedTree = null;
////            }
//            if(selectedFieldType != null) {
//                int x = getTheRightAxes(rectangle.getX(), INSET, BLOCK_SIZE);
//                int y = getTheRightAxes(rectangle.getY(), INSET, BLOCK_SIZE);
//                Block block1 = gameMap.getBlockByLocation(x, y);
//                if(block1.isOccupied(gameMap)) {
//                    selectedFieldType = null;
//                    return;
//                }
//                setMapBlockImageView(mapPane, selectedFieldType.getFieldImage(), block1, true);
//                gameMap.getBlockByLocation(x, y).setFieldType(selectedFieldType);
//                selectedFieldType = null;
//            }
//            if(hasSelectedBase) {
//                int x = getTheRightAxes(rectangle.getX(), INSET, BLOCK_SIZE);
//                int y = getTheRightAxes(rectangle.getY(), INSET, BLOCK_SIZE);
//                Block block1 = gameMap.getBlockByLocation(x, y);
//                if(block1.isOccupied(gameMap)){
//                    hasSelectedBase = false;
//                    return;
//                }
//                setMapBlockImageView(mapPane, BuildingType.BASE.getImage(), block1, true);
//                Map.makeNewBase(gameMap, x, y);
//                hasSelectedBase = false;
//            }
//            event.setDropCompleted(success);
//            event.consume();
//        });

        rectangle.hoverProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean isNowHover) {
                if (isNowHover) {
                    rectangle.setStroke(Color.rgb(102, 97, 73));
                    selectedRectangle = rectangle;
                }
                if (!isNowHover) rectangle.setStroke(null);
            }
        });
    }

    //    public void run() {
//        System.out.println("welcome to another game of crusaders!\n You are in the game menu now!\nwrite down your command!");
//        System.out.println("enter the menu you want to enter");
//        System.out.println("reign menu");
//        System.out.println("trade menu");
//        System.out.println("shop menu");
//        System.out.println("...");
//        while (true) {
//            input = scanner.nextLine();
//            if(input.matches("enter\\s*map\\s+menu\\s*")) {
//                EditAndShowMapMenu menu = new EditAndShowMapMenu(new MapController(gameController.getMap() , true , gameController.getPlayingReign()));
//                menu.run();
//                System.out.println("you are in the game menu now!");
//            } else if(input.matches("enter\\s*Reign\\s+menu\\s*")) {
//                ReignMenu reignMenu = new ReignMenu(new ReignController(gameController.getGame()));
//                reignMenu.run();
//                System.out.println("you are in the game menu now!");
//
//            } else if(input.matches("enter\\s*trade\\s+menu\\s*")) {
//                TradeMenu tradeMenu = new TradeMenu(new TradeController(gameController.getGame()));
//                tradeMenu.run();
//                System.out.println("you are in the game menu now!");
//            } else if(input.matches("enter\\s*shop\\s+menu\\s*")) {
//                ShopMenu shopMenu = new ShopMenu(new ShopController(gameController.getGame()));
//                shopMenu.run();
//                System.out.println("you are in the game menu now!");
//            } else if((matcher = getRealMatcher(input , Commands.SELECT_BUILDING, Commands.X,Commands.Y)) != null) {
//                String result = gameController.selectBuilding(matcher);
//                System.out.println(result);
//                if(result.equals("select building successful")) {
//                    BuildingMenu buildingMenu = new BuildingMenu(new BuildingController(gameController.getGame()));
//                    buildingMenu.run();
//                }
//            } else if((matcher = getRealMatcher(input , Commands.DROP_BUILDING, Commands.X, Commands.Y, Commands.TYPE)) != null) {
//                System.out.println(gameController.dropBuilding(matcher));
//            } else if((matcher = getRealMatcher(input, Commands.SELECT_UNIT, Commands.X, Commands.Y)) != null) {
//                result = gameController.selectUnit(matcher);
//                System.out.println(result);
//                if(result.equals("select units successful!")) {
//                    UnitSelectMenu menu = new UnitSelectMenu(gameController.getUnitController());
//                    menu.run();
//                }
//            } else if(input.matches("\\s*next\\s+turn\\s*")) {
//                if(isUserSureToFinishTurn()) {
//                    result = gameController.nextReign();
//                    if(result.equals("endGame")) System.out.println(gameController.endGame());
//                    else System.out.println(result);
//                }
//
//            } else if(input.matches(Commands.SHOW_TURNS_PASSED.regex)) {
//                System.out.println(gameController.showTurnsPassed());
//            } else if(input.matches("\\s*quit\\s+game\\s*")) {
//                if(isUserSureToQuitGame()){
//                    System.out.println(gameController.quitGame(gameController.getPlayingReign()));
//                }
//            } else if(input.matches("\\s*show\\s+menu\\s*")){
//                System.out.println("you are in the game menu!");
//            }
//            else System.out.println(ResponseToUser.INVALID_COMMAND);
//        }
//    }

    }
