package view;

import controller.UserController;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import Enum.ResponseToUser;
import javafx.util.Duration;
import model.User;
import Enum.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class LoginMenu extends Application  {
    public CheckBox stayLoggedInCheckBox;
    public Label timeLabel;
    public Button loginButton;
    private UserController userController;
    public TextField usernameField;
    public TextField passwordField;
    public Label passwordLabel;
    public Label usernameLabel;
    private int failedAttempts = 0;

//    public LoginMenu(UserController userController) {
//        this.userController = userController;
//    }

    public LoginMenu() {
        userController = new UserController();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setFullScreen(true);
        AnchorPane loginPane = FXMLLoader.load(LoginMenu.class.getResource("/fxml/LoginMenu.fxml"));
        InitStyle.setBackGround(loginPane, ImageEnum.LOGIN_MENU_IMAGE);
        stage.setScene(new Scene(loginPane));
        stage.show();

    }

    public void login(MouseEvent mouseEvent) throws Exception {
        System.out.println("here");
        String username = usernameField.getText();
        String password = passwordField.getText();
        if(username.length() == 0){
            passwordLabel.setText("");
            usernameLabel.setText("please enter your username");
            return;
        }
        if(password.length() == 0) {
            usernameLabel.setText("");
            passwordLabel.setText("please enter your password");
            return;
        }
        ResponseToUser responseToUser = userController.login(username, password, stayLoggedInCheckBox.isSelected());
        if(responseToUser.equals(ResponseToUser.USERNAME_NOT_FOUND)) usernameLabel.setText(responseToUser.text);
        else if(responseToUser.equals(ResponseToUser.WRONG_PASSWORD)) {
            failedAttempts++;
            passwordLabel.setText(ResponseToUser.WRONG_PASSWORD.text);
            initializeTimerTimeLine((long) failedAttempts * 10 * 1000);
            loginButton.setDisable(true);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(failedAttempts * 10), actionEvent -> loginButton.setDisable(false)));
            timeline.setCycleCount(1);
            timeline.play();
        }
        else {
            timeLabel.setText("success");
            timeLabel.setTextFill(Color.rgb(22, 145 , 22));
            Menu.startMainMenu(userController);
        }
    }

    public void initializeTimerTimeLine(long fullTime) {
        long startTime = System.currentTimeMillis();
        AnimationTimer animationTimer;
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long elapsedMillis = fullTime - (System.currentTimeMillis() - startTime) ;
                timeLabel.setText(elapsedMillis/60000 + ":" + elapsedMillis / 1000);
                //System.out.println("time left: " + elapsedMillis);
                if(elapsedMillis <= 0) {
                    try {
                        this.stop();
                        timeLabel.setText("");
                        passwordField.setText("");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        animationTimer.start();

    }

    public void startRegisterMenu(MouseEvent mouseEvent) throws Exception {
        new RegisterAndLoginMenu().start(Menu.stage);

    }

    public void passwordRecovery(MouseEvent mouseEvent) {
        if(usernameField.getText().length() == 0) {
            timeLabel.setText("please enter your username first");
            return;
        }
        User user = User.getUserByUsername(usernameField.getText());
        if(user == null) {
            timeLabel.setText("please enter the right username");
            return;
        }
        String answer = Menu.buildATextInputDialog(user.getSecurityQuestion(), "Security Question");
        if(!answer.equalsIgnoreCase(user.getSecurityAnswer())) {
            Menu.buildInformationAlert("your answer was not correct");
            return;
        }

        TextField newPass = new TextField("new password");
        TextField confirmPass = new TextField("confirm password");
        Label label = new Label("");
        label.setTextFill(Color.rgb(166, 52, 27));

        Button okButton = new Button("OK");
        Button cancelButton = new Button("Cancel");
        HBox hBox = new HBox();
        hBox.getChildren().add(okButton);
        hBox.getChildren().add(cancelButton);
        hBox.setSpacing(20);
        VBox box = new VBox();
        box.getChildren().addAll(newPass, confirmPass, label, hBox );
        box.setSpacing(15);
        box.setLayoutX(600);
        box.setLayoutY(150);
        Pane pane = new Pane();
        pane.getStylesheets().add(LoginMenu.class.getResource("/CSS/style.css").toExternalForm());
        pane.getChildren().add(box);
        pane.setMinHeight(600);
        pane.setMinWidth(900);
//        InitStyle.setBackGround(pane, ImageEnum.POPUP_BACKGROUND);
        pane.setBackground(new Background(new BackgroundImage(new Image(LoginMenu.class.getResource("/Images/popupBackGround.PNG").toExternalForm()), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        Popup popup = new Popup();
//        popup.setHeight(600);
//        popup.setWidth(900);
        popup.getContent().add(pane);
        popup.show(Menu.stage);

        okButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    String result = userController.changePassword(newPass.getText(), confirmPass.getText(), user);
                    if(result.equals("your password changed successfully!")) {
                        Menu.buildInformationAlert(result);
                        popup.hide();
                    }
                    else {
                        label.setText(result);
                    }
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        cancelButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                popup.hide();
            }
        });

    }
}
