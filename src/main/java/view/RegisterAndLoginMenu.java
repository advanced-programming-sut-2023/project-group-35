package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import controller.UserController;
import Enum.Slogan;
import Enum.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Random;


public class RegisterAndLoginMenu extends Menu {
    public TextField usernameField;
    public Label usernameControllerLabel;
    public TextField nicknameField;
    public Label passwordControllerLabel;
    public PasswordField passwordField;
    public PasswordField confirmPasswordField;
    public TextField emailField;
    public CheckBox sloganCheckBox;
    public AnchorPane sloganPane;
    public TextField sloganField;
    public Label nickNameControllerLabel;
    public Label sloganControllerLabel;
    public Label confirmPasswordController;
    public Label emailControllerLabel;
    private String password = "";
    private String confirmPass = "";
    private boolean isPasswordShowing = false;
    private SecurityQuestion securityQuestion;
    private String answerToSecurityQuestion;
    private HashMap<Label, Boolean> textFieldsChecker = new HashMap<>();


    private UserController userController;
    public static Stage stage;

    public RegisterAndLoginMenu() {
        userController = new UserController();
    }

    public void initializeHashmapFields() {
        textFieldsChecker.put(usernameControllerLabel, false);
        textFieldsChecker.put(passwordControllerLabel, false);
        textFieldsChecker.put(emailControllerLabel, false);
    }

    public void initialize() {

        initializeHashmapFields();
        usernameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String old, String now) {
                ResponseToUser responseToUser;
                if ((responseToUser = userController.isUsernameFormatCorrect(now)) != ResponseToUser.CORRECT_FIELD) {
                    fillTheControllerLabel(usernameControllerLabel, responseToUser.text, false);
                } else fillTheControllerLabel(usernameControllerLabel, responseToUser.text, true);
            }
        });
        passwordField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.length() > s.length()) {
                    password += t1.charAt(t1.length() - 1);
                    if (!isPasswordShowing) {
                        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.8), actionEvent -> passwordField.setText(buildBulletString(t1.length()))));
                        timeline.setCycleCount(1);
                        timeline.play();
                    }
                }
                if (t1.length() < s.length()) password = password.substring(0, password.length() - 2);

                String result = UserController.checkPasswordErrors(password);
                if (result.equals("correct")) {
                    fillTheControllerLabel(passwordControllerLabel, result, true);
                } else fillTheControllerLabel(passwordControllerLabel, result, false);

            }
        });
        confirmPasswordField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.length() > s.length()) {
                    confirmPass += t1.charAt(t1.length() - 1);
                    if (!isPasswordShowing) {
                        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.8), actionEvent -> passwordField.setText(buildBulletString(t1.length()))));
                        timeline.setCycleCount(1);
                        timeline.play();
                    }
                }
                if (t1.length() < s.length()) password = password.substring(0, password.length() - 2);
            }
        });
        emailField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                ResponseToUser responseToUser = userController.checkTheEmail(t1);
                if (responseToUser.equals(ResponseToUser.CORRECT_FIELD)) {
                    fillTheControllerLabel(emailControllerLabel, responseToUser.text, true);
                } else fillTheControllerLabel(emailControllerLabel, responseToUser.text, false);
            }
        });


    }

    public void register(MouseEvent mouseEvent) throws IOException, NoSuchAlgorithmException {
        if (!checkIfFieldsAreFilled()) {
            Menu.buildInformationAlert("please fill out the text fields first");
        } else if (!areFieldsFilledCorrectly()) {
            Menu.buildInformationAlert("please correct the fields with red alert");
        } else if (!password.equals(confirmPass)) {
            Menu.buildInformationAlert("password and confirmation are not the same");
        } else {
            pickTheSecurityQuestion();
            if (answerToSecurityQuestion.length() == 0 || securityQuestion == null) {
                securityQuestion = null;
                answerToSecurityQuestion = "";
                Menu.buildInformationAlert("question or answer is not set");
            } else
                userController.register(usernameField.getText(), password, nicknameField.getText(), emailField.getText(), sloganField.getText(), securityQuestion, answerToSecurityQuestion);
        }
    }

    public void pickTheSecurityQuestion() {
        Popup popup = new Popup();
        popup.setWidth(400);
        popup.setHeight(300);
        ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList(SecurityQuestion.values()));
        choiceBox.getSelectionModel().select(0);
        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                securityQuestion = (SecurityQuestion) t1;
            }
        });

        TextField textField = new TextField();
        textField.setPromptText("answer?");
        Button okButton = new Button("OK");
        okButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (textField.getText().isEmpty()) {
                    Menu.buildInformationAlert("please answer the security question first");
                } else {
                    Alert alert = new Alert(Alert.AlertType.NONE, "delete account", ButtonType.YES, ButtonType.CANCEL);
                    alert.setContentText("this is your answer: <" + textField.getText() + ">\nare you sure about it?");
                    alert.setTitle("security question");
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.YES) {
                            try {
                                answerToSecurityQuestion = textField.getText();
                                alert.close();
                                popup.hide();

                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        } else if (response == ButtonType.CANCEL)
                            alert.close();
                    });
                }

            }
        });
        Button cancleButton = new Button("cancel");
        cancleButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                popup.hide();
            }
        });
        VBox box = new VBox();
        box.getChildren().addAll(choiceBox, textField, okButton, cancleButton);
        popup.getContent().add(box);
        popup.show(Menu.stage);
    }

    public boolean checkIfFieldsAreFilled() {
        boolean canProceed = true;
        if (isFieldEmpty(usernameField, usernameControllerLabel)) canProceed = false;
        if (isFieldEmpty(passwordField, passwordControllerLabel)) canProceed = false;
        if (isFieldEmpty(confirmPasswordField, confirmPasswordController)) canProceed = false;
        if (isFieldEmpty(nicknameField, nickNameControllerLabel)) canProceed = false;
        if (isFieldEmpty(emailField, nickNameControllerLabel)) canProceed = false;
        if (sloganCheckBox.isSelected()) {
            if (isFieldEmpty(sloganField, sloganControllerLabel)) canProceed = false;
        }
        return canProceed;
    }

    public boolean isFieldEmpty(TextField textField, Label controller) {
        if (textField.getText().length() == 0) {
            controller.setText("You need to fill this Field!");
            return true;
        }
        return false;
    }

    public boolean areFieldsFilledCorrectly() {
        if (!textFieldsChecker.get(usernameControllerLabel)) return false;
        if (!textFieldsChecker.get(passwordControllerLabel)) return false;
        if (!textFieldsChecker.get(emailControllerLabel)) return false;
        return true;
    }

    private void fillTheControllerLabel(Label controllerLabel, String responseToUser, boolean isFilledCorrectly) {
        controllerLabel.setText(responseToUser);
        if (isFilledCorrectly) {
            controllerLabel.setTextFill(Menu.successGreenColor);
        }
        else controllerLabel.setTextFill(Menu.failRedColor);
        if(textFieldsChecker.containsKey(controllerLabel)) textFieldsChecker.put(controllerLabel, isFilledCorrectly);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane pane = FXMLLoader.load(RegisterAndLoginMenu.class.getResource("/fxml/RegisterAndLoginMenu.fxml"));
        stage.setScene(new Scene(pane));
        stage.show();
        //new MainMenu(this.userController).start(Menu.stage);
    }

    public void showAndHidePasswordField(MouseEvent mouseEvent) {
        if (isPasswordShowing) {
            passwordField.setText(buildBulletString(password.length()));
            isPasswordShowing = false;
        } else {
            passwordField.setText(password);
            isPasswordShowing = true;
        }
    }


    public void back(MouseEvent mouseEvent) throws Exception {
        new LoginMenu().start(Menu.stage);
    }


    public String buildBulletString(int a) {
        String bulletStr = "";
        for (int i = 0; i < a; i++) {
            bulletStr += '\u2022';
        }
        return bulletStr;
    }


    public void generateRandomPassword(MouseEvent mouseEvent) {
        Random random = new Random();
        String randomPassword = "";
        if (usernameField.getText().length() > 5) randomPassword = usernameField.getText();
        else randomPassword = getRandomWord();
        randomPassword += random.nextInt(999) + "!@";
        passwordField.setText(randomPassword);
        isPasswordShowing = true;
    }

    public String getRandomWord() {
        String word = "";
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            word += (char) ('A' + random.nextInt(25));
        }
        for (int i = 0; i < 3; i++) {
            word += (char) ('a' + random.nextInt(25));
        }
        return word;
    }

    public void generateRandomSlogan(MouseEvent mouseEvent) {
        Slogan slogan = Slogan.getRandomSlogan();
        sloganField.setText(slogan.slogan);
        Menu.buildInformationAlert("your slogan is: \n" + slogan.slogan);
    }


//    public void run() throws IOException, NoSuchAlgorithmException {
//        System.out.println("welcome to crusaders!\n please login or sign up if you don't have an account.");
//        while (true) {
//            input = scanner.nextLine();
//            if ((matcher = getRealMatcher(input,Commands.CREATE_USER,Commands.USERNAME, Commands.PASSWORD_NOT_IN_LOGIN, Commands.EMAIL,
//                    Commands.NICKNAME,Commands.SLOGAN)) != null || (matcher = getRealMatcher(input,Commands.CREATE_USER,Commands.USERNAME, Commands.PASSWORD_NOT_IN_LOGIN, Commands.EMAIL,
//                    Commands.NICKNAME)) != null) {
//                System.out.println(userController.register(matcher,extractSlogan(input)));
//            } else if((matcher = getRealMatcher(input,Commands.USER_LOGIN, Commands.USERNAME, Commands.PASSWORD_USED_IN_LOGIN)) != null) {
//                String resOfLogin = userController.login(matcher,input);
//                System.out.println(resOfLogin);
//                if(resOfLogin.equals("logged in successfully")) {
//                    MainMenu mainMenu = new MainMenu(this.userController);
//                    UserController.saveTheData();
//                    mainMenu.run();
//                }
//            } else if ((matcher = getRealMatcher(input,Commands.FORGOT_MY_PASSWORD,
//                    Commands.USERNAME,Commands.PASSWORD_USED_IN_LOGIN)) != null) {
//                System.out.println(userController.forgotMyPassword(matcher));
//            }
//            else if((matcher = getRealMatcher(input,Commands.EXIT))!= null){
//                if(isUserSureToExit()) {
//                    UserController.saveTheData();
//                    System.out.println("game is now finished");
//                    return;
//                }
//            } else if(input.matches("\\s*show\\s+menu\\s*")){
//                System.out.println("you are in register and login menu");
//            } else {
//                System.out.println(ResponseToUser.INVALID_COMMAND);
//            }
//
//        }
//    }
//
//    public RegisterAndLoginMenu(UserController userController) {
//        this.userController = userController;
//    }
//
//
//    public static SecurityQuestion getSafetyQuestion() {
//        int counter = 1;
//        String lastLine;
//        SecurityQuestion question = null;
//        Scanner scn = new Scanner(System.in);
//        System.out.println("pick one question :");
//        for (SecurityQuestion questionSecurity : SecurityQuestion.values()) {
//            System.out.println(questionSecurity.question);
//        }
//        int numberChosen;
//        while (true) {
//            lastLine = scn.nextLine();
//            if(!Commands.getMatcher(lastLine,Commands.ISNUMERIC).matches()) {
//                System.out.println("Wtf, we asked you for a number!");
//                continue;
//            }
//            numberChosen = Integer.parseInt(lastLine);
//            if (numberChosen > 3 || numberChosen < 1)
//                System.out.println("Invalid question number");
//            else break;
//        }

//

//

//

//    public static boolean checkTheSecurityHitAndPass(User user){
//        System.out.println(user.getSecurityQuestion());
//        Scanner scn = new Scanner(System.in);
//        String lastLine = scn.nextLine();
//        if(lastLine.equals(user.getSecurityAnswer()))
//            return true;
//        return false;
//    }
//



}
