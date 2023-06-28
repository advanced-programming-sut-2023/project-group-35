package view;

import controller.*;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Block;
import model.Map;

public class MapMenu extends Menu{
    private MapController mapController;
    private Map map;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setFullScreen(false);
        Pane root = new Pane();
        makeTheMap(root);
        Scene scene = new Scene(root, 1200, 800, Color.rgb(219, 214, 178));
        stage.setScene(scene);
        stage.show();
    }




    public void makeTheMap(Pane pane) {
        for (Block block : map.getBlocks()) {
            Rectangle rectangle = new Rectangle(200 + block.getX(), 200 + block.getY(), 40, 40);
            rectangle.setFill(new ImagePattern(block.getFieldType().getFieldImage()));
            if(block.hasABuilding()) {
                ImageView imageView = new ImageView(block.getBuilding().getBuildingType().getImage());
            }
            Tooltip.install(rectangle, InitStyle.BuildToolTip(block.getBlockInfo(true)));
            rectangle.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if(keyEvent.getCode().equals(KeyCode.DELETE)) block.clearBlock(null); //todo check if works correctly
                }

            });
        }
//        Rectangle rectangle = new Rectangle(200, 200 , 40, 40);
//        Rectangle rectangle1 = new Rectangle(220, 190, 40, 40);
//        Rectangle rectangle2 = new Rectangle(200, 240 , 40, 40);
//        Rectangle rectangle3 = new Rectangle(240, 240 , 40, 40);
        ImageView imageView = new ImageView(new Image(MapMenu.class.getResource("/Images/castle1.png").toExternalForm(), 40, 40, false, false ));
        imageView.setX(200);
        imageView.setY(200);
//        pane.getChildren().addAll(rectangle, rectangle3, rectangle2);
        //pane.getChildren().add(imageView);


      //  rectangle.setFill(new ImagePattern(FieldType.Stone.getFieldImage()));

//        pane.setScaleX(1.5);
//        pane.setScaleY(1.5);
//        //rectangle.setFill(new ImagePattern(FieldType.Stone.getFieldImage()));
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
