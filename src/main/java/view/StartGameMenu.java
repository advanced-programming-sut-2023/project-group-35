package view;

import controller.GameController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.*;
import Enum.*;

import java.util.concurrent.atomic.AtomicBoolean;

public class StartGameMenu extends Menu{
    private User loggedInUser;
    private int leftUsersToAdd;
    private int addedNumber = 0;
    private GameController gameController;

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        leftUsersToAdd = gameController.getGame().getMap().getNumberOfBases();
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public static int LayoutX = 50;
    public static int LayoutY = 200;
    public static int HEIGHT = 40;
    public static int BUTTON_WIDTH = 80;
    public static int BUTTON_HEIGHT = 40;
    public static int WIDTH = 300;
    public String labelStr = "left users to add: ";
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
        for (Block baseBlock : gameController.getGame().getMap().getBaseBlocks()) {
            System.out.println("base: " + baseBlock.getY() + " x:" + baseBlock.getX());
        }
        System.out.println("size: " + gameController.getGame().getMap().getBaseBlocks().size());
    }

    public void initTextFieldAndButton(Pane root) {
        TextField textField = new TextField();
        textField.setPromptText("enter the username you're looking for");
        textField.setLayoutX(LayoutX);
        textField.setLayoutY(LayoutY - 60);
        textField.setPrefHeight(HEIGHT);
        textField.setPrefWidth(WIDTH);
        Label leftToAddLabel = new Label(labelStr + leftUsersToAdd);
        root.getChildren().add(leftToAddLabel);
        leftToAddLabel.setTextFill(Color.rgb(230, 217, 156));
        leftToAddLabel.setFont(Font.font("times", FontWeight.BOLD, FontPosture.ITALIC, 16));

        Button button = InitStyle.setGameButtonStyles("find", BUTTON_HEIGHT, BUTTON_WIDTH);
        button.setLayoutX(LayoutX + WIDTH + 10);
        button.setLayoutY(LayoutY - 60);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                User user = User.getUserByUsername(textField.getText());
                if(user == null) Menu.buildInformationAlert("Username Was Not Found!");
                else if(gameController.getGame().isAlreadyInTheGame(user)) Menu.buildInformationAlert("this user is already in the game");
                else {
                    gameController.getGame().addReign(user);
                    Label label = getLabel(user);
                    Circle circle = getAvatarImage(user);
                    root.getChildren().add(circle);
                    root.getChildren().add(label);
                    leftUsersToAdd--;
                    addedNumber++;
                    leftToAddLabel.setText(labelStr + leftUsersToAdd);
                    textField.setText("");
                    if(leftUsersToAdd == 0) {
                        startButton.setDisable(false);
                        button.setDisable(true);
                    }
                }
            }
        });
        root.getChildren().addAll(textField, button);
    }
    public void backAndForwardButton(Pane root) {
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

        startButton = InitStyle.setGameButtonStyles("Start", 30, 50);
        hBox.getChildren().addAll(back, startButton);
        root.getChildren().add(hBox);
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
        gameMenu.setGameMap(gameController.getMap());
//        new GameMenu().start(Menu.stage);
        gameMenu.start(Menu.stage);
    }
    public Label getLabel(User user) {
        Label label = new Label();
        label.setText(user.getUserName() + "  NickName: " + user.getNickName());
        label.setPrefHeight(HEIGHT);
        label.setPrefWidth(WIDTH);
        label.setLayoutX(LayoutX + 40);
        label.setLayoutY(LayoutY + addedNumber * (HEIGHT + 20));
        //label.setTextFill(Color.rgb(230, 217, 156));
        label.setTextFill(Color.BLACK);
        label.setBackground(new Background(new BackgroundFill(Color.rgb(94, 99, 75), CornerRadii.EMPTY, Insets.EMPTY)));
        label.setFont(Font.font("times", FontWeight.BOLD, FontPosture.ITALIC, 15));
        return label;
    }
    public Circle getAvatarImage(User user) {
        Circle circle = new Circle(LayoutX, LayoutY + addedNumber * (HEIGHT + 20), 20);
        try {
            circle.setFill(new ImagePattern(InitStyle.makeImageBySize(loggedInUser.getAvatar(), 20, 20)));
        } catch (NullPointerException e) {
            circle.setFill(new ImagePattern(ImageEnum.DEFAULT_AVATAR.getImageWithSize(20, 20)));
        }
        return circle;
    }

    public void backIfSure() throws Exception {
        AtomicBoolean sureToExit = Menu.alertForConfirmation("exit", "Are You Sure You Want to Exit?", "back");
        if (sureToExit.get()) {
            Menu.startMainMenu(loggedInUser);
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
//    public void printLeftUsersToEnter() {
//        System.out.println("there are " + leftUsersToAdd + "users left to add");
//        System.out.println("print their username to add them");
//    }
//    public boolean isUserSureToExit() {
//        System.out.println("are you sure you want to exit start game menu?");
//        System.out.println("print yes to exit");
//        input = scanner.nextLine();
//        if(input.matches("\\s*yes\\s*")) return true;
//        return false;
//    }
}
