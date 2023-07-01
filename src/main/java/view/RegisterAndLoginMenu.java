package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import controller.UserController;
import Enum.Slogan;
import Enum.*;
import org.testng.SkipException;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterAndLoginMenu extends Menu {
    public TextField usernameField;
    public Label usernameControllerLabel;
    public TextField nicknameField;
    public Label passwordControllerLabel;
    public TextField passwordField;
    public TextField confirmPasswordField;
    public TextField emailField;
    public CheckBox sloganCheckBox;
    public AnchorPane sloganPane;
    public TextField sloganField;
    public Label nickNameControllerLabel;
    public Label sloganControllerLabel;
    public Label confirmPasswordController;
    public Label emailControllerLabel;
    public ChoiceBox sloganChoiceBox;
    public Label registerResultLabel;
    public ImageView showHideIv;
    private String password = "";
    private String confirmPass = "";
    private String captchaNumber;
    private boolean isPasswordShowing = false;
    private SecurityQuestion securityQuestion;
    private String answerToSecurityQuestion = "";
    private HashMap<Label, Boolean> textFieldsChecker = new HashMap<>();


    private UserController userController;
    //public static Stage stage;

    public RegisterAndLoginMenu() {
        userController = new UserController();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setFullScreen(true);
        AnchorPane pane = FXMLLoader.load(RegisterAndLoginMenu.class.getResource("/fxml/RegisterAndLoginMenu.fxml"));

        InitStyle.setBackGround(pane, ImageEnum.REGISTER_MENU_IMAGE);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        //new MainMenu(this.userController).start(Menu.stage);

    }

    public void initialize() {
        initializeHashmapFields();
        initSloganChoiceBox();
        showHideIv.setImage(new Image(RegisterAndLoginMenu.class.getResource("/Images/download.jpg").toExternalForm()));

        showHideIv.setBlendMode(BlendMode.LIGHTEN);
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
                    //password = t1;
                    System.out.println(password);
                    if (!isPasswordShowing) {
//                        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.8), actionEvent -> passwordField.setText(buildBulletString(t1.length()))));
//                        timeline.setCycleCount(1);
//                        timeline.play();
                        passwordField.setText(buildBulletString(t1.length()));
                    }
                }
                if (t1.length() < s.length()) password = password.substring(0, Math.max(password.length() - 1, 0));

                String result = UserController.checkPasswordErrors(password);
                if (result.equals("perfect")) {
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
//                        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.8), actionEvent -> passwordField.setText(buildBulletString(t1.length()))));
//                        timeline.setCycleCount(1);
//                        timeline.play();
                        confirmPasswordField.setText(buildBulletString(t1.length()));
                    }
                }
                if (t1.length() < s.length()) confirmPass = confirmPass.substring(0, Math.max(confirmPass.length() - 1, 0));
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
        nicknameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(!t1.isEmpty()) nickNameControllerLabel.setText("");
            }
        });
        sloganField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(!t1.isEmpty()) sloganControllerLabel.setText("");
                if(Math.abs(t1.length() - s.length()) < 2) sloganChoiceBox.getSelectionModel().selectFirst();
            }
        });

        sloganPane.setVisible(false);
        sloganCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                sloganPane.setVisible(t1.equals(true));
            }
        });
    }

    public void initializeHashmapFields() {
        textFieldsChecker.put(usernameControllerLabel, false);
        textFieldsChecker.put(passwordControllerLabel, false);
        textFieldsChecker.put(emailControllerLabel, false);
    }
    public ChoiceBox initSloganChoiceBox() {
        sloganChoiceBox = new ChoiceBox(FXCollections.observableArrayList(Slogan.values()));
        sloganChoiceBox.getSelectionModel().selectFirst();
        sloganChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                Slogan slogan = (Slogan) t1;
                sloganField.setText(slogan.slogan);
            }
        });
        sloganPane.getChildren().add(sloganChoiceBox);
        sloganChoiceBox.setLayoutY(25);
        sloganChoiceBox.setMaxWidth(60);
        return sloganChoiceBox;
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

        System.out.println("getting out");
        }
    }

    public void showAndHidePasswordField(MouseEvent mouseEvent) {
        System.out.println("here");
        System.out.println(password);
        //todo background for hide/unhide
        if (isPasswordShowing) {
            passwordField.setText(buildBulletString(password.length()));
            confirmPasswordField.setText(buildBulletString(confirmPass.length()));
            isPasswordShowing = false;
        } else {
            passwordField.setText(password);
            confirmPasswordField.setText(confirmPass);
            System.out.println(password);
            isPasswordShowing = true;
        }
        //todo set the cursor at the right position
    }
    public void pickTheSecurityQuestion() throws IOException {
        Popup popup = new Popup();
        popup.setWidth(400);
        popup.setHeight(300);
        ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList(SecurityQuestion.values()));
        choiceBox.getSelectionModel().select(0);
        securityQuestion = SecurityQuestion.values()[0];
        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                securityQuestion = (SecurityQuestion) t1;
            }
        });

        TextField securityQuestionTextField = new TextField();
        securityQuestionTextField.setPromptText("answer?");
        ImageView imageView = new ImageView();
        String captchaNum = generateRandomCaptcha(imageView);
        TextField captchaTextField = new TextField();
        captchaTextField.setPromptText("enter the number above");
        ImageView reloadIV = new ImageView();
        reloadIV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    generateRandomCaptcha(imageView);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Image reload = new Image(RegisterAndLoginMenu.class.getResource("/Images/reloadCaptcha.png").toExternalForm(), 40, 40, true, true);
        reloadIV.setImage(reload);
        HBox captchaHBox = new HBox(imageView, reloadIV);
        captchaHBox.setSpacing(30);

        Button okButton = InitStyle.setGameButtonStyles("ok", 20, 60);
        Button cancleButton =InitStyle.setGameButtonStyles("cancel", 20, 60);
        HBox hBox = new HBox(okButton, cancleButton);
        hBox.setSpacing(20);
        cancleButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                popup.hide();
            }
        });
        okButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //popup.hide();
                if (securityQuestionTextField.getText().isEmpty()) {
                    Menu.buildInformationAlert("please answer the security question first");
                    //popup.show(Menu.stage);
                } else if(captchaTextField.getText().isEmpty()) {
                    Menu.buildInformationAlert("please write the captcha number");
                    //popup.show(Menu.stage);
                } else if(!captchaTextField.getText().equals(captchaNumber)) {
                    System.out.println("written:<" + captchaTextField.getText() + ">");
                    System.out.println("captchanum: <" + captchaNumber + ">");
                    Menu.buildInformationAlert("the captcha is not correct!\nplease try again!");
                    try {
                        generateRandomCaptcha(imageView);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    //popup.show(Menu.stage);
                } else {
                    Alert alert = new Alert(Alert.AlertType.NONE, "security question", ButtonType.YES, ButtonType.CANCEL);
                    alert.setContentText("this is your answer: <" + securityQuestionTextField.getText() + ">\nare you sure about it?");
                    alert.setTitle("security question");
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.YES) {
                            try {
                                answerToSecurityQuestion = securityQuestionTextField.getText();
                                String result = userController.register(usernameField.getText(), password, nicknameField.getText(), emailField.getText(), sloganField.getText(), securityQuestion, answerToSecurityQuestion);
                                System.out.println("done");
                                clearFields();
                                alert.close();
                                popup.hide();
                                registerResultLabel.setText(result);

                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        } else if (response == ButtonType.CANCEL) {
                            alert.close();
                            popup.show(Menu.stage);
                        }
                    });
                }
            }
        });

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        box.setBackground(new Background(new BackgroundFill(Color.rgb(212, 196, 174), CornerRadii.EMPTY, new Insets(2))));
        box.getChildren().addAll(choiceBox, securityQuestionTextField);
        box.getChildren().addAll(captchaHBox, captchaTextField);
        box.getChildren().add(hBox);
//        Button button = new Button("salam");
//        button.setStyle("-fx-border-color: rgb(192,22,198)");
//        captchaHBox.getChildren().add(button);
        //AnchorPane anchorPane = new AnchorPane();
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(box);
        borderPane.setMinHeight(400);
        borderPane.setMinWidth(300);
        //borderPane.setBackground(new Background(new BackgroundImage()));


        popup.getContent().add(borderPane);
        popup.show(Menu.stage);
    }

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        nicknameField.setText("");
        sloganField.setText("");
        emailField.setText("");
        usernameControllerLabel.setText("");
        passwordControllerLabel.setText("");
        emailControllerLabel.setText("");
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
        if (usernameField.getText().length() > 5) randomPassword = usernameField.getText() + "Zz";
        else randomPassword = getRandomWord();
        randomPassword += random.nextInt(999) + "!@";
        isPasswordShowing = true;
        password = randomPassword.substring(0, randomPassword.length()-1);
        passwordField.setText(randomPassword);
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
        int index =  Slogan.getRandomSlogan();
        sloganField.setText(Slogan.values()[index].slogan);
        sloganChoiceBox.getSelectionModel().select(index);
        //Menu.buildInformationAlert("your slogan is: \n" + slogan.slogan);
    }
    public String generateRandomCaptcha(ImageView imageView) throws IOException {
        URL url = RegisterAndLoginMenu.class.getResource("/Images/captcha");
        File file = new File(url.getPath());
        if(!file.isDirectory()) System.out.println("not a directory");
        else {
            File[] files = file.listFiles();
            File captcha = files[new Random().nextInt(files.length)];
            String captchaName = captcha.getName();
            System.out.println("captchaname:" + captchaName);
            System.out.println("number of ccaptcha: " + getNumberOfCaptcha(captchaName));
            URL url1 = RegisterAndLoginMenu.class.getResource("/Images/captcha/" + captchaName);
            Image image = new Image(url1.toExternalForm());
            System.out.println("url: " + image.getUrl());
            imageView.setImage(image);
            captchaNumber = getNumberOfCaptcha(captchaName);
            System.out.println("just set captcha to :" + captchaNumber );
            return getNumberOfCaptcha(captchaName);
        }
        return null;
    }
    public String getNumberOfCaptcha(String fullName) {
        Matcher matcher = Pattern.compile("^(?<number>\\d+)\\.png").matcher(fullName);
        if(matcher.matches()){
            System.out.println(matcher.group("number"));
            return matcher.group("number");
        }
        return null;
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
