package view;

import controller.GameController;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.*;
import Enum.*;

import java.util.concurrent.atomic.AtomicBoolean;

public class StartGameMenu extends Menu{
    private User playingUser;
    private int leftUsersToAdd;
    private int addedNumber;
    private GameController gameController;

    public void setPlayingUser(User playingUser) {
        this.playingUser = playingUser;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public static int LayoutX;
    public static int LayoutY;
    public static int HEIGHT;
    public static int WIDTH;
    private Button startButton;

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        InitStyle.setBackGround(root, ImageEnum.START_GAME_MENU);
        root.setPrefHeight(850);
        root.setPrefWidth(1300);
        initTextFieldAndButton(root);
        backAndForwardButton(root);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initTextFieldAndButton(Pane root) {
        TextField textField = new TextField();
        textField.setPromptText("enter the username you're looking for");
        textField.setLayoutX(LayoutX);
        textField.setLayoutY(LayoutY - 60);
        textField.setPrefHeight(HEIGHT);
        textField.setPrefWidth(WIDTH);
        Button button = new Button("Find");
        button.setLayoutX(LayoutX + WIDTH + 10);
        button.setLayoutY(LayoutY);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                User user = User.getUserByUsername(textField.getText());
                if(user != null) {
                    gameController.getGame().addReign(user);
                    Label label = getLabel(user);
                    Circle circle = getAvatarImage(user);
                    root.getChildren().add(circle);
                    root.getChildren().add(label);
                    leftUsersToAdd--;
                    addedNumber++;
                    if(leftUsersToAdd == 0) {
                        startButton.setDisable(false);
                        button.setDisable(true);
                    }
                } else {
                    Menu.buildInformationAlert("Username Was Not Found!");
                }
            }
        });
    }
    public void backAndForwardButton(Pane pane) {
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.setLayoutX(LayoutX + 20);
        hBox.setLayoutY(500);
        Button back = InitStyle.setGameButtonStyles("back", 30, 50);
        back.setOnMouseClicked(e -> {
            try {
                backIfSure();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        startButton = InitStyle.setGameButtonStyles("Start", HEIGHT, 50);
        hBox.getChildren().addAll(back, startButton);
        startButton.setDisable(true);
        startButton.setOnMouseClicked(e -> {
            try {
                startGame();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    public void startGame() throws Exception {
        GameMenu gameMenu = new GameMenu();
        gameMenu.setGameController(gameController);
        new GameMenu().start(Menu.stage);
    }
    public Label getLabel(User user) {
        Label label = new Label(user.getUserName());
        label.setPrefHeight(HEIGHT);
        label.setPrefWidth(WIDTH);
        label.setLayoutX(LayoutX);
        label.setLayoutY(LayoutY + addedNumber * (HEIGHT + 20));
        return label;
    }
    public Circle getAvatarImage(User user) {
        Circle circle = new Circle(30, LayoutX - 50, LayoutY + addedNumber * (HEIGHT + 20));
        try {
            circle.setFill(new ImagePattern(new Image(user.getAvatarUrl())));
        } catch (IllegalArgumentException e) {
            circle.setFill(new ImagePattern(ImageEnum.getImage(ImageEnum.DEFAULT_AVATAR,false)));
        }
        return circle;

    }
    public void backIfSure() throws Exception {
        AtomicBoolean sureToExit = Menu.alertForConfirmation("exit", "Are You Sure You Want to Exit?", "back");
        if (sureToExit.get()) {
            Menu.startMainMenu(playingUser);
        }
    }
//    public void run() {
//        System.out.println("YOU ARE IN THE START GAME MENU");
//        System.out.println("print back to go back to main menu");
//        while (true) {
//            printLeftUsersToEnter();
//            input = scanner.nextLine();
//            if(input.matches(Commands.BACK.regex)) {
//                if(isUserSureToExit()) {
//                    System.out.println("exiting start game menu...");
//                    return;
//                }
//            } else if((matcher = getRealMatcher(input,Commands.ADD_USER,Commands.USERNAME)) != null) {
//                String result = gameController.addUser(matcher);
//                System.out.println(result);
//                if(result.equals("user added successfully")) leftUsersToAdd--;
//                if(leftUsersToAdd == 0) {
//                    System.out.println("great!\nlet's start the game!");
//                    GameMenu gameMenu = new GameMenu(gameController);
//                    gameMenu.run();
//                }
//            } else if(input.matches("\\s*show\\s+menu\\s*")) {
//                System.out.println("you are in the start game menu!");
//            }
//            else System.out.println(ResponseToUser.INVALID_COMMAND);
//        }
//    }
    public void printLeftUsersToEnter() {
        System.out.println("there are " + leftUsersToAdd + "users left to add");
        System.out.println("print their username to add them");
    }
    public boolean isUserSureToExit() {
        System.out.println("are you sure you want to exit start game menu?");
        System.out.println("print yes to exit");
        input = scanner.nextLine();
        if(input.matches("\\s*yes\\s*")) return true;
        return false;
    }
}
