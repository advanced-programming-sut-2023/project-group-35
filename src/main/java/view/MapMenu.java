package view;

import controller.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Block;
import model.Map;
import Enum.*;
import model.User;
import model.buildings.Base;

public class MapMenu extends Menu {
    private MapController mapController;
    private Map map;
    public static int BLOCK_SIZE = 40;
    public static int INSET = 0;
    public static Pane mapPane;
    private Tree selectedTree;
    private Rectangle selectedRectangle;
    private boolean hasSelectedBase;
    private FieldType selectedFieldType;

    @Override
    public void start(Stage stage) throws Exception {
        //stage.setFullScreen(false);
        Pane root = new Pane();

//        for (Block baseBlock : map.getBaseBlocks()) {
//            System.out.println("base: " + baseBlock.getY() + " x:" + baseBlock.getX());
//        }
//        System.out.println("size: " + map.getBaseBlocks().size());
        makeTheMap(root);
        // menu
        initializeMenuItems(root);


        Scene scene = new Scene(root, 1200, 800, Color.rgb(219, 214, 178));
        stage.setScene(scene);
        stage.show();
    }

    public void initializeMenuItems(Pane root) {
        Pane pane = new Pane();
        root.getChildren().add(pane);
        pane.setLayoutY(600);
        pane.setLayoutX(0);
//        pane.setPrefWidth(root.getPrefWidth());
        pane.setPrefWidth(1200);
        pane.setPrefHeight(200);
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(212, 195, 142), CornerRadii.EMPTY, Insets.EMPTY)));
        pane.getChildren().add(new Button("ala"));
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().add(getTabForTexture());
        tabPane.getTabs().add(getTreeTab());
        tabPane.getTabs().add(getTabForBase());
        tabPane.getTabs().add(getExitTap());
        pane.getChildren().add(tabPane);
    }

    public Tab getExitTap() {
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(30, 20, 30, 20));
        Button saveButton = InitStyle.setGameButtonStyles("saveAndExit", 50, 100);
        saveButton.setBorder(Border.stroke(Color.BLACK));
        hBox.getChildren().add(saveButton);
        saveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Menu.startMainMenu(User.getUserByUsername(map.getOwnerUsername()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Tab tab = new Tab("Exit", hBox);
        return tab;
    }
    public void makeTheMap(Pane root) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.pannableProperty().set(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scrollPane.setMaxHeight(600);
        System.out.println(root.getHeight() + " wi: " + root.getWidth());
        scrollPane.setMaxWidth(1368);
        root.getChildren().add(scrollPane);
        //scrollPane.setBackground(new Background(new BackgroundImage()));
        mapPane = new Pane();
        scrollPane.setContent(mapPane);
        int i = 0;
        for (Block block : map.getBlocks()) {
            System.out.println(i++);
            Rectangle rectangle = new Rectangle(INSET + block.getX() * BLOCK_SIZE, INSET + block.getY() * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            rectangle.setFill(new ImagePattern(block.getFieldType().getFieldImage()));
            mapPane.getChildren().add(rectangle);
            if (block.hasABuilding()) {
                setMapBlockImageView(mapPane, block.getBuilding().getBuildingType().getImage(), block, true);
            }
            if (block.getTree() != null) {
                setMapBlockImageView(mapPane, block.getTree().getImage(), block, true);
            }
            if (block.hasBase() || map.isABase(block.x, block.y)) {
                setMapBlockImageView(mapPane, BuildingType.BASE.getImage(), block, true);
            }
            setRectangleSettings(rectangle, block);
        }
    }
    public Tab getTreeTab() {
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(30, 20, 30, 20));
        for (Tree value : Tree.values()) {
            ImageView imageView = new ImageView(value.getImage());
            //imageView.setFitWidth(); //todo make image bigger
            imageView.setFitWidth(60);
            imageView.setFitHeight(60);
            hBox.getChildren().add(imageView);

            imageView.setOnDragDetected(event -> {
                Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(imageView.snapshot(new SnapshotParameters(),null));
                selectedTree = Tree.getTreeByImage(imageView.getImage());
                System.out.println(selectedTree);
                System.out.println("drag ditected");
                //event.setDragDetect(true);
                db.setContent(content);
                event.consume();
            });
        }
        return new Tab("tree", hBox);
    }

    public Tab getTabForTexture() {
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(30, 20, 30, 20));
        //int i = 0;
        for (FieldType value : FieldType.values()) {
            //if(++i > 2) break;
            ImageView imageView = new ImageView(value.getFieldImage());
            //imageView.setFitWidth(); //todo make image bigger
            imageView.setFitWidth(60);
            imageView.setFitHeight(60);
            hBox.getChildren().add(imageView);

            imageView.setOnDragDetected(event -> {
                Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(imageView.snapshot(new SnapshotParameters(),null));
                selectedTree = Tree.getTreeByImage(imageView.getImage());
                selectedFieldType = FieldType.getFieldTypeByImage(imageView.getImage());
                //event.setDragDetect(true);
                db.setContent(content);
                event.consume();
            });
        }
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(hBox);
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Tab tab = new Tab("texture", scrollPane);
        return tab;
    }
    public Tab getTabForBase() {
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(30, 20, 30, 20));

        ImageView imageView = new ImageView(BuildingType.BASE.getImage());
        //imageView.setFitWidth(); //todo make image bigger
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        imageView.setOnDragDetected(event -> {
            Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putImage(imageView.snapshot(new SnapshotParameters(),null));
            hasSelectedBase = true;
            db.setContent(content);
            event.consume();
        });
        hBox.getChildren().add(imageView);
        return new Tab("Base", hBox);
    }




    public ImageView setMapBlockImageView(Pane pane, Image image, Block block, boolean isForMap) {
        ImageView imageView = new ImageView(image);
        imageView.setX(block.getX() * BLOCK_SIZE + INSET);
        imageView.setY(block.getY() * BLOCK_SIZE + INSET);
        if(isForMap) {
            imageView.setFitHeight(BLOCK_SIZE);
            imageView.setFitWidth(BLOCK_SIZE);
        }
        pane.getChildren().add(imageView);
        return imageView;
    }

    public void setRectangleSettings(Rectangle rectangle, Block block) {
        rectangle.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.ANY);
            if(event.getSource() == rectangle) return;
            if(getBlockByRectangle(rectangle).isOccupied(map)) rectangle.setStroke(Color.rgb(138, 39, 36));
            else rectangle.setStroke(Color.rgb(109, 161, 26));
            //System.out.println("drag over");
            event.consume();
        });


        rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Tooltip.install(rectangle, InitStyle.BuildToolTip(block.getBlockInfo(true)));
                rectangle.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if(keyEvent.getCode().equals(KeyCode.DELETE)) block.clearBlock(null); //todo check if works correctly
                }

            });
            }
        });
        rectangle.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                rectangle.setStyle("-fx-border-width:2px;-fx-border-color:black;-fx-background-color: "+db.getString());
                success = true;
            }
            System.out.println("drag dropped");
            if(selectedTree != null) {
                int x = getTheRightAxes(rectangle.getX(), INSET, BLOCK_SIZE);
                int y = getTheRightAxes(rectangle.getY(), INSET, BLOCK_SIZE);
                Block block1 = map.getBlockByLocation(x, y);
                //System.out.println(block1.x + " y; " + block1.y);
                if(block1.isOccupied(map)){
                    selectedTree = null;
                    return;
                }
                //System.out.println("done");
                setMapBlockImageView(mapPane, selectedTree.getImage(), block1, true);
                map.getBlockByLocation(x, y).setTree(selectedTree);
                selectedTree = null;
            }
            if(selectedFieldType != null) {
                int x = getTheRightAxes(rectangle.getX(), INSET, BLOCK_SIZE);
                int y = getTheRightAxes(rectangle.getY(), INSET, BLOCK_SIZE);
                Block block1 = map.getBlockByLocation(x, y);
                if(block1.isOccupied(map)) {
                    selectedFieldType = null;
                    return;
                }
                setMapBlockImageView(mapPane, selectedFieldType.getFieldImage(), block1, true);
                map.getBlockByLocation(x, y).setFieldType(selectedFieldType);
                selectedFieldType = null;
            }
            if(hasSelectedBase) {
                int x = getTheRightAxes(rectangle.getX(), INSET, BLOCK_SIZE);
                int y = getTheRightAxes(rectangle.getY(), INSET, BLOCK_SIZE);
                Block block1 = map.getBlockByLocation(x, y);
                if(block1.isOccupied(map)){
                    hasSelectedBase = false;
                    return;
                }
                setMapBlockImageView(mapPane, BuildingType.BASE.getImage(), block1, true);
                Map.makeNewBase(map, x, y);
                hasSelectedBase = false;
            }
            event.setDropCompleted(success);
            event.consume();
        });

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

    public ImageView makeTheCopy(Rectangle rectangle) {
        Image image = ((ImagePattern) rectangle.getFill()).getImage(); // check if right
        ImageView imageView = new ImageView(image);
        imageView.setOpacity(0.5); //todo check
        return imageView;
    }

    public Block getBlockByRectangleBounds(double x1, double y1) {
        int x = (int) (x1 - 200) / BLOCK_SIZE;
        int y = (int) (y1 - 200) / BLOCK_SIZE;
        return map.getBlockByLocation(x, y);
    }

    public int getTheRightAxes(double x, int inset, int BLOCK_SIZE) {
        x -= inset;
        return (int) x / BLOCK_SIZE;
    }


    public Block getBlockByRectangle(Rectangle rectangle) {
        return getBlockByRectangleBounds(rectangle.getX(), rectangle.getY());
    }
    public void setMapController(MapController mapController) {
        this.mapController = mapController;
    }


//    public void run() {
//        System.out.println("you are in the map menu!");
//        while(true) {
//            input = scanner.nextLine();
//            if(((matcher = getRealMatcher(input, Commands.SHOW_MAP, Commands.X, Commands.Y))) != null) {
//                System.out.println(mapController.showMap(matcher));
//            } else if(input.matches(Commands.MOVE_MAP.regex)) {
//                HashMap<Direction, Integer> directions = getDirections(input);
//                if (directions == null) {
//                    System.out.println("Invalid command!");
//                    continue;
//                }
//                System.out.println(mapController.moveMap(directions));
//            } else if((matcher = getRealMatcher(input , Commands.SHOW_MAP_DETAILS,Commands.X,Commands.Y)) != null) {
//                System.out.println(mapController.showDetail(matcher));
//            } else if((matcher = getRealMatcher(input , Commands.SET_TEXTURE,Commands.X,Commands.Y,Commands.TYPE)) != null) {
//                System.out.println(mapController.setTextureOfBlock(matcher));
//            } else if((matcher = getRealMatcher(input, Commands.DROP_ROCK,Commands.DIRECTION,Commands.X,Commands.Y)) != null) {
//                System.out.println(mapController.dropRock(matcher));
//            } else if((matcher = getRealMatcher(input , Commands.CLEAR,Commands.X,Commands.Y)) != null) {
//                System.out.println(mapController.clearBlock(matcher));
//            } else if((matcher = getRealMatcher(input , Commands.DROP_TREE,Commands.X,Commands.Y,Commands.TREE)) != null) {
//                System.out.println(mapController.dropTree(matcher));
//            } else if((matcher = getRealMatcher(input , Commands.SET_AREA_TEXTURE,Commands.X1,Commands.Y1,Commands.X2,Commands.Y2,Commands.TYPE)) != null) {
//                System.out.println(mapController.setTextureOfArea(matcher));
//            } else if((matcher = getRealMatcher(input , Commands.DROP_BUILDING,Commands.X,Commands.Y,Commands.TYPE)) != null) {
//                System.out.println(mapController.dropBuilding(matcher));
//            } else if((matcher = getRealMatcher(input , Commands.DROP_UNIT,Commands.X,Commands.Y,Commands.TYPE,Commands.AMOUNT)) != null) {
//                System.out.println(mapController.dropUnit(matcher));
//            }
//            else if((matcher = getRealMatcher(input, Commands.SET_BASE,Commands.X,Commands.Y))!= null){
//                System.out.println(mapController.setNewBase(matcher));
//            } else if((matcher = getRealMatcher(input,Commands.SAVE)) != null){
//                Map.getTemplateMaps().add(mapController.getMap());
//                MapController.saveTheMaps();
//            }
//            else if((matcher = getRealMatcher(input,Commands.REMOVE_BASE,Commands.X,Commands.Y)) != null){
//                System.out.println(mapController.removeBase(matcher));
//            } else if(input.matches(Commands.BACK.regex)) {
//                System.out.println("exiting map menu...");
//                return;
//            } else if(input.matches("\\s*show\\s+menu\\s*")){
//                System.out.println("you are in the map menu!");
//            }
//            else System.out.println(ResponseToUser.INVALID_COMMAND);
//        }
//    }
//
//    public HashMap<Direction, Integer> getDirections(String input) {
//        HashMap<Direction, Integer> directions = new HashMap<>();
//        boolean flag = false;
//        for (Direction value : Direction.values()) {
//            if(!value.isMajor) continue;
//            int number = getDirectionNumber(input, value);
//            if(number == -1) return null;
//            if(number > 0) flag = true;
//            directions.put(value, number);
//        }
//        if(!flag) return null;
//        return directions;
//    }
//    public int getDirectionNumber(String input, Direction dir) {
//        Matcher matcher = Pattern.compile(dir.getNumberRegex()).matcher(input);
//        if(!matcher.find()) return 0;
//        if(matcher.group("number") == null) return 1;
//        int number = getInt(matcher, "number");
//        if(matcher.find()) return -1;
//        return number;
//    }


    public void setMap(Map map) {
        this.map = map;
    }
}
