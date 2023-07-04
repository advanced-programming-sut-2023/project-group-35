package view;

import Enum.*;
import controller.GameController;
import controller.UserController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.*;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainMenu extends Menu{
    private UserController userController;
    private User loggedInUser;
    private Map selectedMap;
    private ArrayList<Rectangle> mapImages = new ArrayList<>();


    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        InitStyle.setBackGround(pane, ImageEnum.MAIN_MENU_IMAGE);
        pane.setPrefHeight(850);
        pane.setPrefWidth(1300);
        initializeMapImages(pane);
        initializeButtons(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
    public void setUserController(UserController userController) {
        this.userController = userController;
        loggedInUser = userController.getLoggedInUser();
        if(loggedInUser == null)
            System.out.println("logged in user null");
    }

    public void initializeMapImages(Pane pane) {
        ArrayList<Map> maps = loggedInUser.getMaps();

        for (int j = 0; j < 4; j++) {
            try {
                loadMapImage(pane, maps.get(j), j);
            } catch (Exception e) {
                loadMapImage(pane, null, j);
            }
        }
        setFocusedMap();
        buildNewMapButton(pane, 200);
        buildNewMapButton(pane, 400);

    }
    public void loadMapImage(Pane pane, Map map, int i) {
        Rectangle rectangle = new Rectangle();
        rectangle.setX(i * 130 + 40);
        rectangle.setY(60);
        rectangle.setHeight(100);
        rectangle.setWidth(100);
        pane.getChildren().add(rectangle);
        if(i == 0) {
            selectedMap = map;
            rectangle.setStroke(Color.rgb(156, 42, 42));
            rectangle.setStrokeWidth(5);
        }
        if(map == null) {
            rectangle.setFill(
                    new ImagePattern(ImageEnum.getImage(ImageEnum.DEFAULT_MAP_IMAGE, false)));
        } else {
            rectangle.setFill(new ImagePattern(new Image(MapMenu.class.getResource("/Images/map/" + i + ".png").toExternalForm())));
            mapImages.add(rectangle);
            pane.getChildren().add(getMapLabel(map.getName(), rectangle, i));
        }
    }
    public Label getMapLabel(String mapName, Rectangle mapRec, int i) {
        Label label = new Label(mapName);
        label.setLabelFor(mapRec);
        label.setLayoutX(i * 130 + 60);
        label.setLayoutY(170);
        label.setBackground(new Background(new BackgroundFill(Color.rgb(173, 146, 10), CornerRadii.EMPTY, Insets.EMPTY)));
        label.setTextFill(Color.rgb(125, 21, 42));
        label.setFont(Font.font("new times roman", FontWeight.BOLD, 15));
        return label;
    }
    public void setFocusedMap() {
//        for (int i = 0; i < mapImages.size(); i++) {
//            int finalI = i;
        for (Rectangle mapImage : mapImages) {
            mapImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedMap = loggedInUser.getMaps().get(mapImages.indexOf(mapImage));
                    mapImage.setStroke(Color.rgb(156, 42, 42));
                    mapImage.setStrokeWidth(5);
                    unselectOthers(mapImage);
                    if(mouseEvent.getClickCount() > 1) {
                        MapMenu mapMenu = new MapMenu();
                        mapMenu.setMap(selectedMap);
                        try {
                            mapMenu.start(Menu.stage);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        }
    }
    public void unselectOthers(Rectangle selected) {
        for (Rectangle mapImage : mapImages) {
            if(mapImage.equals(selected)) continue;
            mapImage.setStroke(Color.BLACK);
            mapImage.setStrokeWidth(1);
        }
    }
    public void initializeButtons(Pane pane) {
        VBox box = new VBox();
        box.setSpacing(20);
        box.setLayoutX(50);
        box.setLayoutY(200);
        Button newGame = addButton(box, "New Game");
        newGame.setOnMouseClicked(e -> {
            try {
                startNewGame();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        Button profileButton = addButton(box, "Profile Menu");
        profileButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Menu.startProfileMenu(loggedInUser);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Button logout = addButton(box, "Log Out");
        logout.setOnMouseClicked(e -> {
            try {
                logout();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        pane.getChildren().add(box);
    }
    public void startNewGame() throws Exception {
        AtomicBoolean sureToStartGame = Menu.alertForConfirmation("Start Game", "Are You Sure About Your Selected Map?", "Start New Game");
        System.out.println(sureToStartGame.get());
        if(sureToStartGame.get()) {
            System.out.println("here");
            StartGameMenu startGameMenu = new StartGameMenu();
            Map map = selectedMap.clone();
            if(map == null) System.out.println("clone map null ee");
            startGameMenu.setGameController(new GameController(new Game(loggedInUser, map)));
            startGameMenu.setLoggedInUser(loggedInUser);
            startGameMenu.start(Menu.stage);
        }
    }
    public void logout() throws Exception {
        AtomicBoolean sureToLogOut = Menu.alertForConfirmation("logout", "Are You Sure You Want To Logout?", "logout");
        System.out.println("sure to logout" + sureToLogOut.get());
        if(sureToLogOut.get()) {
            new LoginMenu().start(Menu.stage);
            FileWriter fileWriter = new FileWriter("loggedIn.txt");
            fileWriter.close();
        }
    }

    public Button addButton(VBox box, String name) {
        Button button = InitStyle.setGameButtonStyles(name, 40, 100);
        box.getChildren().add(button);
        return button;
    }


    public void buildNewMapButton(Pane pane, int dimension) {
        Button button = InitStyle.setGameButtonStyles("new map" + dimension + "", 40, 110);
        button.setLayoutX(130 * 4 + 30);
        button.setLayoutY(dimension == 200? 170 : 220);
        pane.getChildren().add(button);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(loggedInUser.getMaps().size() > 3) Menu.buildInformationAlert("YOU CAN'T HAVE MORE THAN 4 MAPS IN THE GAME!");
                else {
                    MapMenu mapMenu = new MapMenu();
                    mapMenu.setMap(Map.generateDefaultMap(loggedInUser.getUserName()));
                    try {
                        mapMenu.start(Menu.stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }



//    public void run() throws NoSuchAlgorithmException, IOException {
//        System.out.println("choose the menu you want to enter");
//        System.out.println("map menu");
//        System.out.println("start game menu");
//        System.out.println("profile menu");
//        System.out.println("or print <logout> if you want to logout");
//        while(true) {
//            this.input = scanner.nextLine();
//            if(input.matches(Commands.MAP_MENU.regex)) {
//                if(mapQuestion().equals("notChosen")) continue;
//                MapMenu mapMenu = new MapMenu(new MapController(loggedInUser.getMap(), false, null));
//                mapMenu.run();
//            } else if(input.matches(Commands.START_GAME.regex)) {
//                if(loggedInUser.getMap() == null)
//                    System.out.println("you must have map to create game!");
//                else if(loggedInUser.getMap().getNumberOfBases() < 3)
//                    System.out.println("your map doesn't have enough bases!\nadd new bases to your map or choose another one from template");
//                else {
//                    StartGameMenu gameMenu = new StartGameMenu(loggedInUser);
//                    gameMenu.run();
//                }
//            }
//            else if(input.matches(Commands.PROFILE_MENU.regex)) {
//                ProfileMenu profileMenu = new ProfileMenu(loggedInUser);
//                profileMenu.run();
//            }
//            else if(input.matches(Commands.LOGOUT.regex)){
//                if(isUserSureToLogout()){
//                    File tempFile = new File("loggedIn.txt");
//                    boolean exists = tempFile.exists();
//                    if(exists){
//                        tempFile.delete();
//                    }
//                    System.out.println("logout successful!");
//                    return;
//                }
//            } else if(input.matches("\\s*show\\s+menu\\s*")){
//                System.out.println("you are in the main menu!");
//            }
//            else{
//                System.out.println("invalid commend!");
//            }
//        }
//    }

//    public String mapQuestion() {
//        if(loggedInUser.getMap() == null){
//            System.out.println("You don't have a map.");
//            while (true) {
//                System.out.println("type <choose map> to choose a template map,\ntype <create> to make a now map,\n and <exit> to go back to main menu");
//                input = scanner.nextLine();
//                if (input.matches("\\s*choose\\s+map\\s*")) {userController.chooseMap();
//                return "map was chosen!";}
//                else if (input.matches("\\s*create\\s*")){ userController.createMap();
//                return "map was chosen!";}
//                else if (input.matches("\\s*exit\\s*")) return "notChosen";
//                else System.out.println("I didn't Understand, please try again");
//            }
//        }
//        else {
//            System.out.println("You already have a map");
//            while (true) {
//                System.out.println("type <new map> to choose another map,\ntype <new map> to create a new map \n otherwise type <continue>");
//                input = scanner.nextLine();
//                if (input.matches("new map")) userController.createMap();
//                else if (input.matches("choose another")) userController.chooseMap();
//                else if (input.matches("\\s*continue\\s*")){
//                    System.out.println("getting into map menu...");
//                    break;
//                } else System.out.println("sorry, I did not understand, please try again");
//            }
//        }
//        return "successful";
//    }
//    public static String getMapFromUser(String mapList) {
//        System.out.println("choose one of these template maps");
//        System.out.println(mapList);
//        return scanner.nextLine();
//    }
//    public boolean isUserSureToLogout() {
//        while (true) {
//            System.out.println("Are you sure you want to logout?");
//            input = scanner.nextLine();
//            if(input.matches("\\s*yes\\s*")) return true;
//            if(input.matches("\\s*no\\s*")) return false;
//            System.out.println("sorry i did not understand!");
//        }
//    }

}
