package view;

import controller.*;
import Enum.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Block;
import model.Map;
import model.buildings.Building;
import model.people.MilitaryUnit;

import java.util.ArrayList;

import static view.MapMenu.BLOCK_SIZE;


public class GameMenu extends Menu{
    private GameController gameController;
    private Pane mapPane;
    private Pane menuPane;
    private TabPane buildingTabPane;
    private Map gameMap;
    private Rectangle selectedRectangle;
    private Building selectedBuilding; //todo make sure to turn back to null
    private MilitaryUnit selectedUnit; //todo make sure to turn back to null
    private GameTabMenuMode menuMode = GameTabMenuMode.DROP_BUILDING;
    public static int MENU_ITEM_SIZE = 60;
    public static int BuildingImageSize;
    public static int MapPaneHeight = 740;
    public static int SCREEN_HEIGHT = 912;
    public static int SCREEN_WIDTH = 1348;
    private BuildingType selectedBuildingType;

    private Building copiedBuilding;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setGameMap(Map gameMap) {
        this.gameMap = gameMap;
        //if(gameMap == null) System.out.println("game map null in set game map");
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setFullScreen(true);
        Pane root = new Pane();

        initMenuPane(root);


        buildingTabPane = createBuildingsTabPane();
        initializeMap(root);
        initializeMenuBar();
        initReignMenuOpenerImage(root);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initMenuPane(Pane root) {


        Pane pane = new Pane();
        root.getChildren().add(pane);
        pane.setLayoutY(MapPaneHeight);
        pane.setLayoutX(0);
//        pane.setPrefWidth(root.getPrefWidth());
        pane.setPrefWidth(SCREEN_WIDTH);
        pane.setPrefHeight(200);
        //pane.getChildren().add(reignMenuOpener());
        //pane.setBackground(new Background(new BackgroundFill(Color.rgb(212, 195, 142), CornerRadii.EMPTY, Insets.EMPTY)));
        HBox hBox = new HBox();
        pane.getChildren().add(hBox);
        ImageView tabMenuIcon = InitStyle.getImageView(ImageEnum.getImage(ImageEnum.TAB_MENU_IMAGE, false),SCREEN_HEIGHT - MapPaneHeight, SCREEN_WIDTH / 3);
        ImageView tabMenuIcon1 = InitStyle.getImageView(ImageEnum.getImage(ImageEnum.TAB_MENU_IMAGE, false),SCREEN_HEIGHT - MapPaneHeight, SCREEN_WIDTH / 3);
        Pane menuPane = new Pane();

        menuPane.setPrefHeight(SCREEN_HEIGHT - MapPaneHeight);
        //InitStyle.setBackGround(pane, ImageEnum.BACK_GROUND);
        hBox.getChildren().add(tabMenuIcon);
        hBox.getChildren().add(menuPane);
        hBox.getChildren().add(tabMenuIcon1);
        this.menuPane = menuPane;
    }
    public void initializeMap(Pane root){
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.pannableProperty().set(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setMaxHeight(MapPaneHeight);
//        System.out.println(root.getHeight() + " wi: " + root.getWidth());
        scrollPane.setMaxWidth(1368);

        root.getChildren().add(scrollPane);
        //scrollPane.setBackground(new Background(new BackgroundImage()));
        mapPane = new Pane();
        scrollPane.setContent(mapPane);
        int i = 0;
        for (Block block : gameMap.getBlocks()) {
            System.out.println(i++);
           // Rectangle rectangle = new Rectangle(block.getX() * BLOCK_SIZE,  block.getY() * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            rectBlock rectangle = new rectBlock(block, block.getX() * BLOCK_SIZE,  block.getY() * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            rectangle.setFill(new ImagePattern(block.getFieldType().getFieldImage()));
            mapPane.getChildren().add(rectangle);
//            if (block.hasABuilding()) {
//                ImageView building = setMapBlockImageView(mapPane, block.getBuilding().getBuildingType().getImage(), block, true);
//                rectangle.setBuildingView(building);
//            }
            if (block.getTree() != null) {
                ImageView treeView = setMapBlockImageView(mapPane, block.getTree().getImage(), block, true);
                rectangle.setTreeView(treeView);
            }
            if (block.hasBase() || gameMap.isABase(block.x, block.y)) {
                ImageView baseView = setMapBlockImageView(mapPane, BuildingType.BASE.getImage(), block, true);
                rectangle.setBuildingView(baseView);
            }
            setRectangleSettings(rectangle, block);
        }
    }
    private void initializeMenuBar() {
        if(menuMode.equals(GameTabMenuMode.DROP_BUILDING)){
            menuPane.getChildren().clear();
            menuPane.getChildren().add(buildingTabPane);
        }
        else if(menuMode.equals(GameTabMenuMode.BUILDING_SELECTED)) {
            menuPane.getChildren().clear();
            System.out.println("initializemenubar befor");
            menuPane.getChildren().add(getBuildingSelectedMenu(selectedBuilding));
            System.out.println("initializemenubar selected buildign");
        }
        //else menuPane.getChildren().add(getUnitSelectedMenu());

    }
    public TabPane getBuildingSelectedMenu(Building selected) {
        HBox hBox = InitStyle.createHbox();
        BuildingType type = selected.getBuildingType();
        if(type.isEqualToAnyOf(BuildingType.MERCENARY_CAMP, BuildingType.BARRACK, BuildingType.ENGINEER_GUILD, BuildingType.CATHEDRAL)) {
            hBox.getChildren().add(getUnitsTabCreatedByBuilding(type));
        }
        System.out.println("salam");
        hBox.getChildren().add(createBuildingsOptions());
        System.out.println("khodafes");
        //if(type.isEqualToAnyOf(BuildingType.INN)) //todo
        //if(type.isEqualToAnyOf()
        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(new Tab("buildingSelect" , hBox));
        return tabPane;
    }
    public VBox createBuildingsOptions() {
        VBox box = new VBox();
        box.setSpacing(10);
        //box.setBackground();
        Circle repair = createIcon(ImageEnum.REPAIR_ICON);
        repair.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String result = BuildingController.repair(selectedBuilding);
                Menu.buildInformationAlert(result);
            }
        });
        // todo rest like delete
        box.getChildren().add(repair);
        return box;
    }

    public Circle createIcon(ImageEnum image) {
        Circle circle = new Circle(10);
        circle.setFill(new ImagePattern(ImageEnum.getImage(image, false), 0, 0, 10, 10, false));
        return circle;
    }

    public ScrollPane getUnitsTabCreatedByBuilding(BuildingType buildingType) {
        ArrayList<UnitType> units = UnitType.getUnitsProducedIn(buildingType);
        HBox hBox = InitStyle.createHbox();
        for (UnitType unit : units) {
            //ImageView imageView = InitStyle.getImageView(new Image(GameMenu.class.getResource("/Images/castle1.png").toExternalForm()), MENU_ITEM_SIZE, MENU_ITEM_SIZE);
//            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent mouseEvent) {
//                    if(mouseEvent.getClickCount() > 1) {
//                        createUnit(UnitType.getUnitByImage(imageView.getImage()));
//                    }
//                }
//            });
            //hBox.getChildren().add(imageView);
        }
        //hBox.setMaxHeight(); needed??
        ScrollPane scrollPane = InitStyle.makeScrollPane(true, ScrollPane.ScrollBarPolicy.NEVER, ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setContent(hBox);
        return scrollPane;
    }

    public void createUnit(UnitType unitType) {
        String message;
        int count = getUnitNumberFromUser();
        if(count == -1) {
            message = "you did not write a valid number!";
        } else message = BuildingController.createUnit(selectedBuilding.getBlock(), unitType, count, gameController.getPlayingReign(), gameController.getGame());
        Menu.buildInformationAlert(message);
    }

    public TabPane createBuildingsTabPane() {
        TabPane tabPane = new TabPane();
        tabPane.setPrefWidth(SCREEN_WIDTH / 3);
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
            System.out.println("buildings type for menu bar: " + building);
            ImageView imageView = InitStyle.getImageView(building.getImage(), 60, 60);
            hBox.getChildren().add(imageView);
            imageView.setOnDragDetected(event -> {
                Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(imageView.snapshot(new SnapshotParameters(),null));
                selectedBuildingType = BuildingType.getBuildingByImage(imageView.getImage());
                System.out.println("selected building; " + selectedBuildingType);
                //event.setDragDetect(true);
                db.setContent(content);
                event.consume();
            });
        }
        scrollPane.setContent(hBox);
        Tab tab = new Tab();
        setTabMenuIcon(tab, new Image(GameMenu.class.getResource("/Images/icon/" + iconUrl).toExternalForm(),10, 10,true, true ));
        tab.setContent(scrollPane);
        return tab;
    }

    public void setTabMenuIcon(Tab tab, Image image) {
        Circle circle = new Circle(10);
        circle.setFill(new ImagePattern(image, 0, 0, 10, 10, false));
        tab.setGraphic(circle);
    }

    public void selectBlock(rectBlock rectangle) {
        Block block = rectangle.getBlock();
        ArrayList<GameTabMenuMode> modes = block.getModesOfBlock();
        if(modes.size() == 0) return;
        if(modes.size() == 1) {
            if(block.hasABuilding()) {
                selectedBuilding = block.getBuilding();
                menuMode = GameTabMenuMode.BUILDING_SELECTED;
                System.out.println("building selected " + selectedBuilding.getBuildingType() );
            } else {
                menuMode = GameTabMenuMode.UNIT_SELECTED;
                selectedUnit = block.getMilitaryUnits().get(0);
            }
        } if(modes.size() > 1) {
            System.out.println("more than 1"); //todo contunue to select one
            chooseWhichComponentToSelect(block, block.hasABuilding());
        }

//        initializeMenuBar();


    }

    public void chooseWhichComponentToSelect(Block block, boolean hasBuilding) {
        TextInputDialog textInputDialog = new TextInputDialog("text");
        String component = "";
        if(hasBuilding) {
            component += "B- building: " + block.getBuilding().getBuildingType();
        }
        int i =1;
        for (MilitaryUnit militaryUnit : block.getMilitaryUnits()) {
            component += "\n" + i++ + "- " + militaryUnit.getUnitType();
        }
        textInputDialog.setContentText("choose one of these to be selected in the block!\n" + component);
        textInputDialog.showAndWait();
        String input = textInputDialog.getEditor().getText();
        if(input.equals("B")) {
            menuMode = GameTabMenuMode.BUILDING_SELECTED;
            selectedBuilding = block.getBuilding();
            return;
        }
        try {
            int j = Integer.parseInt(input);
            selectedUnit = block.getMilitaryUnits().get(j);
        } catch (Exception ignored) {
        }
    }
    public void setRectangleSettings(rectBlock rectangle, Block block) {
        //todo find another way to do it the right way
        rectangle.hoverProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean isNowHover) {
                if (isNowHover) {
                    rectangle.setStroke(Color.rgb(102, 97, 73));
                }
                if (!isNowHover) rectangle.setStroke(null);
            }
        });

        rectangle.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean isFocused) {
                if(isFocused) {
                    System.out.println("rectangle " + rectangle.getBlock().getX() + " ," + rectangle.getBlock().getY() + "selected");
                    rectangle.setStroke(Color.rgb(224, 66, 18));
                    rectangle.setStrokeWidth(3);
                    selectedRectangle = rectangle;
                    selectedRectangle.requestFocus();
                } else resetRectangle(rectangle);
            }
        });
        rectangle.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                System.out.println("here at least");
                if(keyEvent.getCode().equals(KeyCode.DELETE)) rectangle.clearBlock(mapPane, gameController.getGame().getPlayingReign());
                if(keyEvent.getCode().equals(KeyCode.R)) {
                    try {
                        Menu.startReignMenu(gameController.getGame());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                if(keyEvent.getCode().equals(KeyCode.T)) {
                    try {
                        Menu.startShopMenu(gameController.getGame());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                if(keyEvent.getCode().equals(KeyCode.C)) {
                    if(rectangle.getBlock().hasABuilding()) {
                        copiedBuilding = rectangle.getBlock().getBuilding();
                    } else copiedBuilding = null;
                }
                if(keyEvent.getCode().equals(KeyCode.P)) {
                    if(copiedBuilding != null) {
//
//                        String result = gameController.dropBuilding(copiedBuilding.getBuildingType(), rectangle.getBlock());
//                        Menu.buildInformationAlert(result);
                        dropBuilding(rectangle, copiedBuilding.getBuildingType());
                        copiedBuilding = null;
                    }
                }
            }
        });

        rectangle.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.ANY);
            if(event.getSource() == rectangle) return;
            if(rectangle.getBlock().isOccupied(gameMap)) rectangle.setStroke(javafx.scene.paint.Color.rgb(138, 39, 36));
            else rectangle.setStroke(javafx.scene.paint.Color.rgb(109, 161, 26));
            System.out.println("drag over");
            event.consume();
        });

        rectangle.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                rectangle.setStyle("-fx-border-width:2px;-fx-border-color:black;-fx-background-color: "+db.getString());
                success = true;
            }
            System.out.println("drag dropped");

            if(selectedRectangle != null && !selectedRectangle.equals(rectangle)) {
                // todo handle select multiple block
            }
            if(selectedBuildingType != null) {
//                if(block.isOccupied(gameMap)){
//                    selectedBuildingType = null;
//                    return;
//                }
//                ImageView buildingView = setMapBlockImageView(mapPane, selectedBuildingType.getImage(), block, true);
//                rectangle.setBuildingView(buildingView);
//                buildingView.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent mouseEvent) {
//                        if(mouseEvent.getClickCount() > 1) {
//                            System.out.println("select request from imageview");
//                            //selectBlock(rectangle);
//
//                        }
//                    }
//                });
////                Map.makeNewBase(gameMap, x, y);
//                String result = gameController.dropBuilding(selectedBuildingType, block);
//                Menu.buildInformationAlert(result);
                dropBuilding(rectangle, selectedBuildingType);
            }
            selectedBuildingType = null;
            event.setDropCompleted(success);
            event.consume();
        });
        rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                rectangle.requestFocus();

                Tooltip.install(rectangle, InitStyle.BuildToolTip(block.getBlockInfo(true)));
                //if(mouseEvent.getClickCount() > 1) {
                selectBlock(rectangle);
                initializeMenuBar();
                System.out.println("from rectangle");
                //}
            }
        });
    }

    public void dropBuilding(rectBlock rectangle, BuildingType buildingType) {
        ImageView buildingView = setMapBlockImageView(mapPane, buildingType.getImage(), rectangle.getBlock(), true);
        rectangle.setBuildingView(buildingView);
        buildingView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount() > 1) {
                    System.out.println("select request from imageview");
                    //selectBlock(rectangle);

                }
            }
        });
        String result = gameController.dropBuilding(buildingType, rectangle.getBlock());
        Menu.buildInformationAlert(result);
    }

    public int getUnitNumberFromUser() {
        TextInputDialog numberDialog = new TextInputDialog("1");
        numberDialog.setContentText("enter the number of units you want to build");
        numberDialog.setTitle("Unit Number");
        numberDialog.showAndWait();
        String numberStr = numberDialog.getEditor().getText();
        int number;
        try{
            number = Integer.parseInt(numberStr);
        }catch (Exception e) {
            return -1;
        }
        return number;
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

    public void resetRectangle(Rectangle rectangle) {
        rectangle.setStroke(null);
        rectangle.setStrokeWidth(0);
    }
    public ImageView initReignMenuOpenerImage(Pane root) {
        ImageView imageView = InitStyle.getImageView(ImageEnum.getImage(ImageEnum.REIGN_MENU_MAN, false),180, 180);
        imageView.setLayoutX(900);
        imageView.setLayoutY(700);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Menu.startReignMenu(gameController.getGame());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        root.getChildren().add(imageView);
        return imageView;
    }

//    public Block getBlockByRectangle(Rectangle rectangle) {
//        int x = (int) (rectangle.getX() - INSET) / BLOCK_SIZE;
//        int y = (int) (rectangle.getY() - INSET) / BLOCK_SIZE;
//        return gameMap.getBlockByLocation(x, y);
//    } //todo get rectangleByBlock
       //public void run() {
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
