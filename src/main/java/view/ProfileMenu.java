package view;

import java.awt.*;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;

import com.sun.javafx.charts.Legend;
import controller.UserController;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.User;
import Enum.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ProfileMenu extends Menu{
    private UserController profileMenu = new UserController();
    private User loggedInUser;
    private static Pane pane;
    private Scene scene;
    @FXML
    TextField usernameTextField;
    @FXML
    TextField nicknameTextField;
    @FXML
    TextField emailTextField;
    @FXML
    TextField sloganTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    TextField confirmationTextField;
    @FXML
    TextField oldPasswordTextField;
    @FXML
    TextField textFieldToShowPassword;
    @FXML
    TextField textFieldToShowConfirmation;
    @FXML
    TextField textFieldToShowOldPassword;
    @FXML
    Label confirmationText;
    @FXML
    Label oldPasswordText;
    @FXML
    Label usernameWarningLabel;
    @FXML
    Label nicknameWarningLabel;
    @FXML
    Label emailWarningLabel;
    @FXML
    Label passwordWarningLabel;
    @FXML
    Label confirmationWarningLabel;
    @FXML
    Button submitUsername;
    @FXML
    Button submitNickname;
    @FXML
    Button submitEmail;
    @FXML
    Button submitSlogan;
    @FXML
    Button submitPassword;
    @FXML
    Button changeUsername;
    @FXML
    Button changeNickname;
    @FXML
    Button changeEmail;
    @FXML
    Button changeSlogan;
    @FXML
    Button changePassword;
    @FXML
    Button chooseFromDeviceButton;
    @FXML
    Button chooseAnExistingAvatarButton;
    @FXML
    Button enterScoreBoardButton;
    @FXML
    Rectangle avatar;
    @FXML
    CheckBox showPasswordCheckBox;



    @Override
    public void start(Stage stage) throws Exception {
        stage.setFullScreen(true);
        this.stage = stage;
        URL url = ProfileMenu.class.getResource("/FXML/ProfileMenu.fxml");
        Background background = new Background(new BackgroundImage(new Image(ProfileMenu.class.getResource("/Images/BG/bgPM.jpg").toString(),
                Screen.getPrimary().getBounds().getHeight(), Screen.getPrimary().getBounds().getWidth(), false, false)
                ,BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)
                );
        pane = FXMLLoader.load(url);
        pane.setBackground(background);
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    public void initialize(){
        resetAll();
        addListeners();
        if(loggedInUser.avatar == null){
            loggedInUser.avatar = ProfileMenu.class.getResource("/Images/1.jpeg").toString();
        }
        avatar.setFill(new ImagePattern(new Image(ProfileMenu.class.getResource("/Images/1.jpeg").toString()+
                loggedInUser.avatar,
                Screen.getPrimary().getBounds().getHeight(),
                Screen.getPrimary().getBounds().getWidth(),false,false)));
    }
    public void addListeners(){
        nicknameTextField.textProperty().addListener(new javafx.beans.value.ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1 == null || t1.isEmpty()) nicknameWarningLabel.setText("Enter nickname!");
                else nicknameWarningLabel.setText("");
            }
        });
        //end of nickname
        emailTextField.textProperty().addListener(new javafx.beans.value.ChangeListener<String>(){
            @Override
            public void changed(ObservableValue <? extends String>observableValue,String s,String t1){
                if (t1 == null || t1.isEmpty()) {
                    emailWarningLabel.setText("Enter email!");
                } else if (!t1.matches("[\\w\\.]+@[\\w\\.]+\\.[\\w\\.]+")) emailWarningLabel.setText("Invalid email format!");
                else if (User.getUserByEmail(t1) != null)
                    emailWarningLabel.setText("This email is used!");
                else emailWarningLabel.setText("");
            }
        });
        //end of email
        usernameTextField.textProperty().addListener(new javafx.beans.value.ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String>observableValue,String s,String t1){
                if (t1 == null || t1.isEmpty()) {
                    usernameWarningLabel.setText("Enter username!");
                } else if (!t1.matches("\\w+")){
                    usernameWarningLabel.setText("Invalid username format!");
                }else if (User.getUserByUsername(t1) != null) {
                    usernameWarningLabel.setText("This username is used");
                }else usernameWarningLabel.setText("");
            }
        });
        //end of username
        passwordTextField.textProperty().addListener(new javafx.beans.value.ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String>observableValue,String s,String t1){
                textFieldToShowPassword.setText(t1);
                if (t1 == null || t1.isEmpty()) {
                    passwordWarningLabel.setText("Enter password");
                    passwordWarningLabel.setBackground(Color.RED);
                } else if (!RegisterAndLoginMenu.checkPasswordErrors(t1)) {
                    passwordWarningLabel.setText("Weak!");
                    passwordWarningLabel.setBackground(Color.RED);
                }
                else{
                    passwordWarningLabel.setText("Strong enough!");
                    passwordWarningLabel.setBackground(Color.RED);
                }
            }
        });
        textFieldToShowPassword.textProperty().addListener(new javafx.beans.value.ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                passwordTextField.setText(t1);
            }
        });
        confirmationTextField.textProperty().addListener(new javafx.beans.value.ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                textFieldToShowConfirmation.setText(t1);
                if (!t1.equals(passwordTextField.getText())) {
                    confirmationWarningLabel.setText("Passwords do not match!");
                } else {
                    confirmationWarningLabel.setText("");
                }
            }
        });
        textFieldToShowConfirmation.textProperty().addListener(new javafx.beans.value.ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                confirmationTextField.setText(t1);
            }
        });
        textFieldToShowOldPassword.textProperty().addListener(new javafx.beans.value.ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                oldPasswordTextField.setText(t1);
            }
        });
        oldPasswordTextField.textProperty().addListener(new javafx.beans.value.ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                textFieldToShowOldPassword.setText(t1);
            }
        });


    }
    public void resetAll(){
        profileMenu.setLoggedInUser(loggedInUser);
        submitUsername.setVisible(false);
        submitNickname.setVisible(false);
        submitEmail.setVisible(false);
        submitSlogan.setVisible(false);
        submitPassword.setVisible(false);
        chooseAnExistingAvatarButton.setVisible(false);
        chooseFromDeviceButton.setVisible(false);
        usernameTextField.setText(profileMenu.getLoggedInUser().getUserName());
        usernameTextField.setDisable(true);
        nicknameTextField.setText(profileMenu.getLoggedInUser().getNickName());
        nicknameTextField.setDisable(true);
        emailTextField.setText(profileMenu.getLoggedInUser().getEmail());
        emailTextField.setDisable(true);
        if(profileMenu.getLoggedInUser().getSloganOfUser() != null) {
            sloganTextField.setText(profileMenu.getLoggedInUser().getSloganOfUser());
            sloganTextField.setDisable(true);
        }
        else{
            sloganTextField.setText("You Don't have any slogan!");
            sloganTextField.setDisable(true);
        }
        passwordTextField.setText("");
        passwordTextField.setDisable(true);
        confirmationTextField.setText("");
        confirmationTextField.setVisible(false);
        oldPasswordTextField.setText("");
        oldPasswordTextField.setVisible(false);
        textFieldToShowPassword.setVisible(false);
        textFieldToShowConfirmation.setVisible(false);
        textFieldToShowOldPassword.setVisible(false);
        showPasswordCheckBox.setVisible(false);
        confirmationText.setVisible(false);
        oldPasswordText.setVisible(false);
        nicknameWarningLabel.setText("");
        passwordWarningLabel.setText("");
        confirmationWarningLabel.setText("");
        emailWarningLabel.setText("");
        usernameWarningLabel.setText("");

    }
    @FXML
    private void enableUsernameField(MouseEvent mouseEvent) {
        usernameTextField.setText("Enter username!");
        usernameTextField.setDisable(false);
        submitUsername.setVisible(true);
    }

    @FXML
    public void enableEmailField(MouseEvent mouseEvent) {
        emailTextField.setText("Enter Email!");
        emailTextField.setDisable(false);
        submitEmail.setVisible(true);
    }

    @FXML
    private void enableNickNameField(MouseEvent mouseEvent) {
        nicknameTextField.setText("Enter Nickname!");
        nicknameTextField.setDisable(false);
        submitNickname.setVisible(true);
    }

    @FXML
    private void enableSloganField(MouseEvent mouseEvent) {
        sloganTextField.setText("Enter Slogan!");
        sloganTextField.setDisable(false);
        submitSlogan.setVisible(true);
    }
    @FXML
    private void showPasswordHandler(ActionEvent actionEvent) {
        if (showPasswordCheckBox.isSelected()) {
            textFieldToShowPassword.setVisible(true);
            passwordTextField.setVisible(false);
            textFieldToShowConfirmation.setVisible(true);
            confirmationTextField.setVisible(false);
            textFieldToShowOldPassword.setVisible(true);
            oldPasswordTextField.setVisible(false);
        } else {
            textFieldToShowPassword.setVisible(false);
            passwordTextField.setVisible(true);
            textFieldToShowConfirmation.setVisible(false);
            confirmationTextField.setVisible(true);
            textFieldToShowOldPassword.setVisible(false);
            oldPasswordTextField.setVisible(true);
        }
    }
    @FXML
    private void enableChoosingAvatar() {
        chooseAnExistingAvatarButton.setVisible(true);
        chooseFromDeviceButton.setVisible(true);
    }
    @FXML
    private void enablePasswordField(MouseEvent mouseEvent) {
        passwordTextField.setText("Enter Password!");
        oldPasswordText.setText("Default!");
        confirmationText.setText("");
        confirmationTextField.setText("");
        oldPasswordTextField.setText(loggedInUser.getPassword());
        oldPasswordText.setText(loggedInUser.getPassword());
        passwordTextField.setDisable(false);
        passwordTextField.setVisible(true);
        submitPassword.setVisible(true);
        confirmationTextField.setVisible(true);
        oldPasswordTextField.setVisible(true);
        showPasswordCheckBox.setVisible(true);
        showPasswordCheckBox.setSelected(false);
        confirmationText.setVisible(true);
        confirmationWarningLabel.setVisible(true);
        oldPasswordText.setVisible(true);
    }
    @FXML
    public void submitUsername(MouseEvent mouseEvent) throws IOException, NoSuchAlgorithmException {
        Alert alertOfUsername = new Alert(Alert.AlertType.ERROR);
        alertOfUsername.setTitle("User Name");
        String res = profileMenu.usernameChange(usernameTextField.getText());
            if(res.equals("Invalid username format!")) {
                alertOfUsername.setHeaderText("Invalid!");
            }else if(res.equals("This name already Exists!")){
            alertOfUsername.setHeaderText("Already Exists!");
            }else{
            alertOfUsername.setAlertType(Alert.AlertType.INFORMATION);
            alertOfUsername.setHeaderText("Change Was Successful!");
        }
        stage.setFullScreen(false);
        alertOfUsername.showAndWait();
        stage.setFullScreen(true);
        usernameTextField.setText(profileMenu.getLoggedInUser().getUserName());
        usernameWarningLabel.setText("");
    }
    @FXML
    public void submitEmail(MouseEvent mouseEvent) throws IOException, NoSuchAlgorithmException {
        Alert alertOfEmail = new Alert(Alert.AlertType.ERROR);
        alertOfEmail.setTitle("Email");
        String res = profileMenu.emailChange(emailTextField.getText());
        if(res.equals("Invalid Email format!")) {
            alertOfEmail.setHeaderText("Invalid!");
        }else if(res.equals("this email is already in database!")){
            alertOfEmail.setHeaderText("Already Exists!");
        }else{
            alertOfEmail.setAlertType(Alert.AlertType.INFORMATION);
            alertOfEmail.setHeaderText("Change Was Successful!");
        }
        stage.setFullScreen(false);
        alertOfEmail.showAndWait();
        stage.setFullScreen(true);
        emailTextField.setText(profileMenu.getLoggedInUser().getEmail());
        emailWarningLabel.setText("");
    }
    @FXML
    public void submitNickname(MouseEvent mouseEvent) throws IOException, NoSuchAlgorithmException {
        Alert alertOfNickname = new Alert(Alert.AlertType.ERROR);
        alertOfNickname.setTitle("NickName");
        String res = profileMenu.nicknameChange(nicknameTextField.getText());
        if(res.equals("Invalid username format!")) {
            alertOfNickname.setHeaderText("Invalid!");
        }
        else{
            alertOfNickname.setAlertType(Alert.AlertType.INFORMATION);
            alertOfNickname.setHeaderText("Change Was Successful!");
        }
        stage.setFullScreen(false);
        alertOfNickname.showAndWait();
        stage.setFullScreen(true);
        nicknameTextField.setText(profileMenu.getLoggedInUser().getNickName());
        nicknameWarningLabel.setText("");
    }
    @FXML
    public void submitSlogan(MouseEvent mouseEvent) throws IOException, NoSuchAlgorithmException {
        Alert alertOfSlogan = new Alert(Alert.AlertType.ERROR);
        alertOfSlogan.setTitle("Slogan");
        String res = profileMenu.changeOrRemoveSlogan(sloganTextField.getText());
        if(res.equals("you have no slogan to remove!")) {
            alertOfSlogan.setHeaderText("Invalid Try!");
        }else if(res.equals("your slogan removed")){
            alertOfSlogan.setAlertType(Alert.AlertType.INFORMATION);
            alertOfSlogan.setHeaderText("Removed!");
        }
        else{
            alertOfSlogan.setAlertType(Alert.AlertType.INFORMATION);
            alertOfSlogan.setHeaderText("Change Was Successful!");
        }
        stage.setFullScreen(false);
        alertOfSlogan.showAndWait();
        stage.setFullScreen(true);
        if(profileMenu.getLoggedInUser().getSloganOfUser() != null)
        sloganTextField.setText(profileMenu.getLoggedInUser().getSloganOfUser());
        else sloganTextField.setText("Doesn't has slogan!");
    }
    @FXML
    public void SubmitPassword(MouseEvent mouseEvent) throws IOException, NoSuchAlgorithmException {
        Alert alertOfPassword = new Alert(Alert.AlertType.ERROR);
        alertOfPassword.setTitle("Password");
        String res = profileMenu.passwordChanger(oldPasswordTextField.getText(),
               passwordTextField.getText(),confirmationTextField.getText());
        if(res.equals("You entered wrong password!")) {
            alertOfPassword.setHeaderText("Invalid Try!");
        }else if(res.equals("your new password is same as last one")){
            alertOfPassword.setHeaderText("WTF!");
        }else if(res.equals("so you got the error...")){
            alertOfPassword.setHeaderText("Weak!");
        }else if(res.equals("confirm doesn't match!")){
            alertOfPassword.setHeaderText("Lack of Confirmation!");
        }
        else{
            alertOfPassword.setAlertType(Alert.AlertType.INFORMATION);
            alertOfPassword.setHeaderText("Change Was Successful!");
        }
        stage.setFullScreen(false);
        alertOfPassword.showAndWait();
        stage.setFullScreen(true);
        passwordTextField.setText("");
        passwordWarningLabel.setText("");
        oldPasswordText.setText("");
        oldPasswordTextField.setText(profileMenu.getLoggedInUser().getPassword());
        confirmationTextField.setText("");
        confirmationText.setText("");
    }
    @FXML
    public void chooseAvatarFromDevice(){
        FileChooser fileChooser = new FileChooser();
        File image = fileChooser.showOpenDialog(stage);
        stage.setFullScreen(true);
        if (image != null) {
            try{
                File destination = new File(ProfileMenu.class.getResource("/Images/Avatar").toString().substring("file:".length())
                        + image.getName());
                Files.copy(image.toPath(), destination.toPath());
                image =  destination;
            }catch (IOException e){
                e.printStackTrace();
                image = null;
            }
        }
        profileMenu.getLoggedInUser().setAvatar(image.getName());
        avatar.setFill(new ImagePattern(new Image(ProfileMenu.class.getResource("/Images/1.jpeg").toString()+
                loggedInUser.avatar,
                Screen.getPrimary().getBounds().getHeight(),
                Screen.getPrimary().getBounds().getWidth(),false,false)));
        }
    @FXML
    private void chooseAnExistingAvatar(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(ProfileMenu.class.getResource("/Images/Avatars").toString().substring("file:/".length())));
        stage.setFullScreen(false);
        File image = fileChooser.showOpenDialog(stage);
        stage.setFullScreen(true);
        if (image != null) {
            profileMenu.getLoggedInUser().setAvatar(image.getName());
            avatar.setFill(new ImagePattern(new Image(ProfileMenu.class.getResource("/Images/1.jpeg").toString()+
                    loggedInUser.avatar,
                    Screen.getPrimary().getBounds().getHeight(),
                    Screen.getPrimary().getBounds().getWidth(),false,false)));
        }
    }
    @FXML
    public void back(){

    }
    @FXML
    public void scoreboard(){
        stage.setFullScreen(false);
        new ScoreBoard().start(new Stage());
    }


   /* public void run() throws NoSuchAlgorithmException, IOException {
        while (true) {
            input = scanner.nextLine();
            if ((matcher = getRealMatcher(input,Commands.CHANGE_USERNAME,Commands.USERNAME)) != null) {
                System.out.println(profileMenu.usernameChange(matcher));
            } else if((matcher = getRealMatcher(input,Commands.CHANGE_PASSWORD,Commands.PASSWORD_NOT_IN_LOGIN)) != null) {
                System.out.println(profileMenu.passwordChanger(matcher));
            } else if ((matcher = getRealMatcher(input,Commands.CHANGE_EMAIL,Commands.EMAIL)) != null) {
                System.out.println(profileMenu.emailChange(matcher));
            }else if ((matcher = Commands.getMatcher(input, Commands.CHANGE_SLOGAN)) != null ||
                    (matcher = Commands.getMatcher(input, Commands.REMOVE_SLOGAN)) != null) {
                System.out.println(profileMenu.changeOrRemoveSlogan(extractSlogan(input)));
            }else if ((matcher = getRealMatcher(input,Commands.CHANGE_NICKNAME,Commands.NICKNAME)) != null) {
                System.out.println(profileMenu.nicknameChange(matcher));
            } else if ((matcher = getRealMatcher(input, Commands.SHOW_HIGHSCORE)) != null) {
                System.out.println(profileMenu.displayHighScore());
            }else if ((matcher = getRealMatcher(input, Commands.SHOW_RANK)) != null) {
                System.out.println(profileMenu.displayRank());
            }else if ((matcher = getRealMatcher(input, Commands.SHOW_SLOGAN)) != null) {
                System.out.println(profileMenu.displaySlogan());
            }else if ((matcher = getRealMatcher(input, Commands.SHOW_INFO)) != null) {
                System.out.println(profileMenu.displayProfile());
            }else if ((matcher = Commands.getMatcher(input, Commands.BACK)) != null) {
                System.out.println("exiting profile menu...");
                return;
            } else if(input.matches("\\s*show\\s+menu\\s*")){
                System.out.println("you are in the profile menu!");
            }
            else {
                System.out.println(ResponseToUser.INVALID_COMMAND);
            }

        }

    }
*/
    public ProfileMenu(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        profileMenu.setLoggedInUser(loggedInUser);
    }


    /*private String extractSlogan(String text) {
        String slogan = null;
        if (Commands.getMatcher(text, Commands.SLOGAN) != null) {
            Matcher sloganMarcher = Commands.getMatcher(text, Commands.SLOGAN);
            slogan = profileMenu.checkForQuotation(sloganMarcher.group("slogan"));
            if (slogan.length() < 1)
                return null;
        }
        return slogan;
    }*/




}
