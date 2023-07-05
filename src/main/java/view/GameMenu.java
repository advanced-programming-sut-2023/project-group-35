package view;

import controller.*;
import Enum.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Block;
import model.Map;
import model.Reign;
import model.buildings.Building;
import model.people.MilitaryUnit;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static view.MapMenu.BLOCK_SIZE;


public class GameMenu extends Menu{
    private GameController gameController;
    private Pane mapPane;
    private Pane menuPane;
    private TabPane buildingTabPane;
    private Map gameMap;
    private RectBlock selectedRectangle;
    private RectBlock selectedRectForUnitsAndBuilding;
    private Building selectedBuilding; //todo make sure to turn back to null
    private MilitaryUnit selectedUnit; //todo make sure to turn back to null
    private GameTabMenuMode menuMode = GameTabMenuMode.DROP_BUILDING;
    public static int MENU_ITEM_SIZE = 60;
    public static int BuildingImageSize;
    public static int MapPaneHeight = 740;
    public static int SCREEN_HEIGHT =912;
    public static int SCREEN_WIDTH =1348;
    private BuildingType selectedBuildingType;
    private boolean isMovingAllowed = false;

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
        initializeMiniMap(root);
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
        InitStyle.setBackGround(pane, ImageEnum.PAPER_BACK_GROUND);
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
            RectBlock rectangle = new RectBlock(block, block.getX() * BLOCK_SIZE,  block.getY() * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
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
            menuPane.getChildren().add(getBuildingSelectedMenu(selectedBuilding));
        }
        else {
            menuPane.getChildren().clear();
            menuPane.getChildren().add(getUnitSelectedMenu());
        }

    }
    public Pane getUnitSelectedMenu() {
        Pane pane = new Pane();
        pane.setPrefHeight(SCREEN_HEIGHT - MapPaneHeight);
        pane.setPrefWidth(SCREEN_WIDTH / 3);
        Rectangle back = InitStyle.getPointerIcon(ImageEnum.BACK);
        back.setX(20);
        back.setY(20);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedUnit = null;
                menuMode = GameTabMenuMode.DROP_BUILDING;
                initializeMenuBar();
                selectedRectForUnitsAndBuilding = null;
            }
        });

        pane.getChildren().addAll(back, getHpLabel(), getMoveButton(),  getStateButtons());
        return pane;
    }
    public Button getMoveButton() {
        Button button = InitStyle.setGameButtonStyles("MOVE", 40, 120);
        button.setLayoutY(10);
        button.setLayoutX(290);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                isMovingAllowed = true;
                Menu.buildInformationAlert("choose the block you want to move the unit to");
            }
        });
        return button;
    }
    public Label getHpLabel() {
        Label label = new Label("HP: " + selectedUnit.getHp());
        label.setLayoutY(20);
        label.setLayoutX(120);
        label.setFont(Font.font("times", 17));
        InitStyle.setBackGroundColor(label, Color.rgb(166, 20, 34));
        return label;
    }
    public HBox getStateButtons() {
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.setLayoutY(63);
        hBox.setPadding(new Insets(0, 40, 0, 40));
        ArrayList<Button> stateButtons = new ArrayList<>();
        for (UnitState value : UnitState.values()) {
            Button button = InitStyle.setGameButtonStyles(value.name(), 40, 100);
            button.setStyle("-fx-font-family: times new roman");
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    button.requestFocus();
                }
            });
            hBox.getChildren().add(button);
        }
        for (Button stateButton : stateButtons) {
            stateButton.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean now) {
                    if(now) {
                        stateButton.setBorder(new Border(new BorderStroke(Color.rgb(53, 54, 56), BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.FULL)));
                        selectedUnit.setUnitState(UnitState.getUnitState(stateButton.getText()));
                    } else stateButton.setBorder(null);
                }
            });
        }
        return hBox;
    }
    public HBox getBuildingSelectedMenu(Building selected) {
        HBox hBox = InitStyle.createHbox();
        hBox.setLayoutY(-20);
        hBox.setPrefWidth(SCREEN_WIDTH / 3);
        hBox.getChildren().add(createBuildingsOptions());
        BuildingType type = selected.getBuildingType();

        if(type.isEqualToAnyOf(BuildingType.MERCENARY_CAMP, BuildingType.BARRACK, BuildingType.ENGINEER_GUILD, BuildingType.CATHEDRAL)) {
            hBox.getChildren().add(getUnitsTabCreatedByBuilding(selected));
        }

        //if(type.isEqualToAnyOf(BuildingType.INN)) //todo
        //if(type.isEqualToAnyOf()
//        TabPane tabPane = new TabPane();
//        tabPane.getTabs().add(new Tab("buildingSelect" , hBox));
//        return tabPane;
        return hBox;
    }
    public VBox createBuildingsOptions() {
        VBox box = new VBox();
        box.setSpacing(10);
        box.setMaxWidth(25);
        box.setMaxHeight(100);
        //box.setBackground();
        Rectangle backRectangle = InitStyle.getPointerIcon(ImageEnum.BACK);
        backRectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedBuilding = null;
                menuMode = GameTabMenuMode.DROP_BUILDING;
                selectedRectForUnitsAndBuilding = null;
                initializeMenuBar();

            }
        });
        Circle repair = createIcon(ImageEnum.REPAIR_ICON);
        repair.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String result = BuildingController.repair(selectedBuilding);
                Menu.buildInformationAlert(result);
            }
        });
        Circle delete = createIcon(ImageEnum.DELETE_ICON);
        delete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedRectangle.removeBuilding(gameController.getGame().getPlayingReign(), gameController);
                String result = gameController.removeBuilding(selectedBuilding);
                Menu.buildInformationAlert(result);
            }
        });
        box.getChildren().add(backRectangle);
        box.getChildren().add(repair);
        box.getChildren().add(delete);
        return box;
    }


    public Circle createIcon(ImageEnum image) {
        Circle circle = new Circle(10);
        circle.setFill(new ImagePattern(ImageEnum.getImage(image, false), 0, 0, 10, 10, false));
        return circle;
    }

    public ScrollPane getUnitsTabCreatedByBuilding(Building building) {
        ArrayList<UnitType> units = UnitType.getUnitsProducedIn(building.getBuildingType());
        HBox hBox = InitStyle.createHbox();
        hBox.setLayoutY(-40);
        for (UnitType unit : units) {
            ImageView imageView = InitStyle.getImageView((unit.getImage()), MENU_ITEM_SIZE, MENU_ITEM_SIZE);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getClickCount() > 1) {
                        createUnit(UnitType.getUnitByImage(imageView.getImage()));
                    }
                }
            });
            hBox.getChildren().add(imageView);
        }
        //hBox.setMaxHeight(); needed??
        ScrollPane scrollPane = InitStyle.makeScrollPane(true, ScrollPane.ScrollBarPolicy.NEVER, ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setContent(hBox);
        scrollPane.setMaxWidth(SCREEN_WIDTH / 3 - 25);
        return scrollPane;
    }

    public void createUnit(UnitType unitType) {
        String message;
        int count = getUnitNumberFromUser();
        if(count == -1) {
            message = "you did not write a valid number!";
        } else message = BuildingController.createUnit(selectedBuilding.getBlock(), unitType, count, gameController.getPlayingReign(), gameController.getGame());
        if(message.equals("create unit successful")) {
            dropUnit(unitType, count, false);
            selectedUnit = null;
        }
        Menu.buildInformationAlert(message);

    }
    public void dropUnit(UnitType unitType, int number, boolean moving) {
        RectBlock rectangle = selectedRectangle;
        int x = (int) selectedRectangle.getX();
        int y = (int) (selectedRectangle.getY() + BLOCK_SIZE - 13);
        if(number > 10) number = 10;
        double delta = (double) BLOCK_SIZE / (number + 1);

        for (int i = 1; i <= number; i++) {
            ImageView imageView = InitStyle.getImageView(unitType.getImage(), 18, 18);
            imageView.setY(y);
            imageView.setX(x + i * delta);
            mapPane.getChildren().add(imageView);
            rectangle.getTroopsView().add(imageView);
        }
        if(moving && (unitType.equals(UnitType.SLAVE) || unitType.equals(UnitType.FIRETHROWER))) {
            ImageView imageView = InitStyle.getImageView(ImageEnum.getImage(ImageEnum.FIRE, false), 10, 10);
            imageView.setY(y);
            imageView.setX(x -5);
            mapPane.getChildren().add(imageView);
            rectangle.setFireView(imageView);
        }
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
        tabPane.getTabs().add(getOptionsTab());
        return tabPane;
    }

    public Tab getOptionsTab() {
        HBox hBox = new HBox();
        hBox.setBackground(new Background(new BackgroundImage(ImageEnum.getImage(ImageEnum.PAPER_BACK_GROUND, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        hBox.setSpacing(20);
        hBox.setPadding(new Insets(30, 30, 0, 30));
        hBox.getChildren().add(getNextTurnButton());
        //hBox.getChildren().add(getLeaveGame()); //todo
        hBox.getChildren().add(getBriefing());
//        hBox.setLayoutY(30);
        Tab tab = new Tab();
        tab.setContent(hBox);
        return tab;
    }
    public Button getBriefing() {
        Popup popup = new Popup();
        Pane briefing = new Pane();
        popup.setHeight(300);
        popup.setWidth(400);
        popup.getContent().add(briefing);
        InitStyle.setBackGround(briefing, ImageEnum.PAPER_BACK_GROUND);
        VBox box = new VBox();
        box.setSpacing(40);

        int i = 1;
        box.getChildren().add(InitStyle.getLabel("Reigns left in the game:", 35, 200));
        for (Reign reign : gameController.getGame().getReigns()) {
            String content = i + "- " + reign.getNickName();
            if(gameController.getGame().getPlayingReign().equals(reign)) content += " ***";
            Label label = InitStyle.getLabel(content, 40, 250);
            box.getChildren().add(label);
            label.setBackground(new Background(new BackgroundFill(Color.rgb(179, 84, 43), CornerRadii.EMPTY, Insets.EMPTY)));
        }


        Button back = InitStyle.setGameButtonStyles("back", 30, 100);
        back.setLayoutX(180);
        back.setLayoutY(260);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                popup.hide();
            }
        });
        Button briefingBtn = InitStyle.setGameButtonStyles("Briefing", 40, 120);
        briefingBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                popup.show(Menu.stage);
            }
        });
        briefing.getChildren().add(box);
        briefing.getChildren().add(back);
        return briefingBtn;
    }
    public Button getNextTurnButton() {
        Button button = InitStyle.setGameButtonStyles("Next Turn", 40 ,120);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                AtomicBoolean isSureToEndTurn = Menu.alertForConfirmation("next turn", "Are you sure you want to end Your turn?","end turn");
                if(!isSureToEndTurn.get()) return;
                gameController.nextReign();
                buildInformationAlert(gameController.getGame().getPlayingReign().getNickName() + " is now playing");
                applyChangesOfTurn();
            }
        });
        return button;
    }

    public void applyChangesOfTurn() {
        //gameController.getGame().getbl
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

    public void selectBlock(RectBlock rectangle) {
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
            System.out.println("more than 1");
            chooseWhichComponentToSelect(block, block.hasABuilding());
        } selectedRectForUnitsAndBuilding = rectangle;

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
        if(input.equals("B") || input.equals("b")) {
            menuMode = GameTabMenuMode.BUILDING_SELECTED;
            selectedBuilding = block.getBuilding();
            return;
        }
        try {
            int j = Integer.parseInt(input);
            selectedUnit = block.getMilitaryUnits().get(j-1);
            menuMode = GameTabMenuMode.UNIT_SELECTED;
            System.out.println("units selected");
        } catch (Exception ignored) {
        }
    }
    public void setRectangleSettings(RectBlock rectangle, Block block) {
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
                // move with key move
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
                if(mouseEvent.getClickCount() == 1) {
                    if(menuMode != GameTabMenuMode.DROP_BUILDING) {
                        menuMode = GameTabMenuMode.DROP_BUILDING;
                        initializeMenuBar();
                    }
                }
                if(isMovingAllowed) {
                    moveUnit(selectedUnit, rectangle);
                    dropUnit(selectedUnit.unitType, selectedUnit.getNumber(), true);
                    isMovingAllowed = false;
                }
                Tooltip.install(rectangle, InitStyle.BuildToolTip(block.getBlockInfo(true)));
                if(mouseEvent.getClickCount() > 1) {
                selectBlock(rectangle);
                initializeMenuBar();
                System.out.println("from rectangle");
                }
            }
        });
    }
    public void moveUnit(MilitaryUnit unit, RectBlock rectangle) {
        UnitController unitController = new UnitController(gameController.getGame());
        unitController.setSelectedUnit(unit);
        String result = unitController.moveUnitCommand(rectangle.getBlock().getX(), rectangle.getBlock().getY());
        Menu.buildInformationAlert(result);
        selectedRectForUnitsAndBuilding.removeUnit(unit, mapPane);
    }

    public void dropBuilding(RectBlock rectangle, BuildingType buildingType) {
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
    public void initializeMiniMap(Pane root) {
        //ImageView miniMap = InitStyle.getImageView(ImageEnum.getImage(ImageEnum.MINI_MAP, false), 200, 200);
        Rectangle miniMap = new Rectangle(0, 0, 200, 200);
        miniMap.setFill(InitStyle.getImagePattern(ImageEnum.getImage(ImageEnum.MINI_MAP, true), 200, 200));

        miniMap.setStroke(Color.rgb(166, 20, 34));
        miniMap.setStrokeWidth(3.7);



        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPannable(true);
        scrollPane.setFitToHeight(false);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToHeight(false);
        scrollPane.setContent(miniMap);
        scrollPane.setMaxHeight(120);
        scrollPane.setMaxWidth(120);

        Pane pane = new Pane();
        pane.setMaxHeight(120);
        pane.setMaxWidth(120);
        pane.getChildren().add(scrollPane);
        pane.setLayoutX(200);
        pane.setLayoutY(700);
        root.getChildren().add(pane);
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
