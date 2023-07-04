package view;

import controller.ShopController;
import controller.TradeController;
import Enum.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Reign;
import model.TradeItem;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;

public class TradeMenu extends Menu{
    TradeController tradeController;
    public int pointer = 0;
    String allItems[]={"WOOD","STONE","WHEAT","APPLE","CHEESE","MEAT","PROCESSED_MEAT","IRON","HOP","BEAR","FLOUR","BREAD","HORSE","TAR","BOW","SPEAR","ARMOR","SWORD"};
    Reign oppositeSide = null;
    String chosenItemInString;
    String type = null;
    int quantity = 0;
    String message;
    ImageView beforeItem = new ImageView();
    ImageView afterItem = new ImageView();
    Button goRight = new Button();
    Button goLeft = new Button();

    private ObservableList<String> userReceivedOffers = FXCollections.observableList(tradeController.showMyRequestsFromOthers());
    private ObservableList<String> userSentOffers = FXCollections.observableList(tradeController.showRequestsFromMe());

    public void setTradeController(TradeController tradeController) {
        this.tradeController = tradeController;
    }

    @FXML
    public void initialize(){
    }
    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        Scene scene;
        stage.setFullScreen(true);
        this.stage = stage;
        URL url = ProfileMenu.class.getResource("/FXML/TradeMenu.fxml");
        Background background = new Background(new BackgroundImage(new Image(ProfileMenu.class.getResource("/Images/BG/bgPM.jpg").toString(),
                Screen.getPrimary().getBounds().getHeight(), Screen.getPrimary().getBounds().getWidth(), false, false)
                , BackgroundRepeat.NO_REPEAT,
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
    public void openTheList(){
        ArrayList<Reign> allReigns = tradeController.getGame().getReigns();
        ArrayList<String> reignName = new ArrayList<>();
        for(Reign reign:allReigns){
            reignName.add(reign.getNickName());
        }
        ObservableList<String> userList = FXCollections.observableArrayList(reignName);
        ListView<String> listView = new ListView<>(userList);
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String res = tradeController.chooseSecondReign(newValue);
                if(res.equals("Wrong")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("NO valid User");
                    alert.setHeaderText("NOT VALID!");
                    alert.showAndWait();
                }
                else {
                    oppositeSide = tradeController.getSecondReign();
                    openTradeMenuWithPerson();
                }
            }
        });
        ScrollPane scrollPane = new ScrollPane(listView);
        scrollPane.setFitToHeight(true);
        VBox vbox = new VBox(scrollPane);
        Scene scene = new Scene(vbox, 200, 300);
        stage.setScene(scene);
        stage.setTitle("User List");
        stage.show();
    }
    public void openTradeMenuWithPerson(){
        stage.setTitle("Resource Trading Page");
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        Label resourcesLabel = new Label("Available Resources");
        HBox resourceBox = new HBox(10);
        setImages(pointer);
        resourceBox.getChildren().addAll(afterItem,beforeItem,goLeft,goRight);
        goRight.setOnMouseClicked(e -> goRightr());
        goLeft.setOnMouseClicked(e -> goLeftl());
        root.getChildren().addAll(resourcesLabel, resourceBox);
        Label quantityLabel = new Label("Select quantity:");
        Button decreaseButton = new Button("-");
        decreaseButton.setOnMouseClicked(e -> decreaseQuantity());
        Button increaseButton = new Button("+");
        increaseButton.setOnMouseClicked(e -> increaseQuantity());
        TextField quantityField = new TextField(String.valueOf(quantity));
        quantityField.setPrefWidth(50);
        quantityField.setEditable(false);
        HBox quantityBox = new HBox(10, decreaseButton, quantityField, increaseButton);
        root.getChildren().addAll(quantityLabel, quantityBox);
        Label proposalLabel = new Label("Donate/Request:");
        ToggleGroup proposalToggleGroup = new ToggleGroup();
        RadioButton donateRadioButton = new RadioButton("Donate");
        donateRadioButton.setToggleGroup(proposalToggleGroup);
        donateRadioButton.setOnAction(e -> selectProposal("donate"));
        RadioButton requestRadioButton = new RadioButton("Request");
        requestRadioButton.setToggleGroup(proposalToggleGroup);
        requestRadioButton.setOnAction(e -> selectProposal("request"));
        VBox proposalBox = new VBox(5, donateRadioButton, requestRadioButton);
        root.getChildren().addAll(proposalLabel, proposalBox);
        Label messageLabel = new Label("Leave a message:");
        TextArea messageTextArea = new TextArea();
        messageTextArea.setPrefHeight(100);
        messageTextArea.textProperty().addListener((observable, oldValue, newValue) -> message = newValue);
        root.getChildren().addAll(messageLabel, messageTextArea);
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> submitOffer());
        root.getChildren().add(submitButton);
        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.show();
    }
    public void setImages(int ptr){
        goLeft.setText("Left");
        goRight.setText("Right");
        int lPtr;
        int rPtr;
        if(ptr == 0)
            lPtr = 0;
        else
            lPtr = ptr - 1;
        if(ptr == 17)
            rPtr = 17;
        else
            rPtr = ptr+1;
        Image image2 = new Image(ShopMenu.class.getResource(("/Assests/+"+(lPtr+1)+".png")).toString());
        Image image3 = new Image(ShopMenu.class.getResource(("/Assests/+"+rPtr+".png")).toString());
        beforeItem.setImage(image2);
        beforeItem.setPreserveRatio(true);
        beforeItem.setFitHeight(50);
        beforeItem.setFitHeight(50);
        afterItem.setImage(image3);
        afterItem.setPreserveRatio(true);
        afterItem.setFitHeight(50);
        afterItem.setFitHeight(50);
    }
    public void goLeftl(){
        if(pointer == 0)
            pointer = 0;
        else
            pointer--;
        setImages(pointer);
    }
    public void goRightr(){
        if(pointer == 17)
            pointer = 17;
        else
            pointer++;
        setImages(pointer);
    }
    private void increaseQuantity() {
        quantity++;
        System.out.println("Quantity increased: " + quantity);
    }

    private void decreaseQuantity() {
        if (quantity > 0) {
            quantity--;
            System.out.println("Quantity decreased: " + quantity);
        }
    }

    private void selectProposal(String type) {
        this.type = type;
    }

    private void submitOffer() {
        String res;
        if(type.equals("Donate"))
            res = tradeController.donate(quantity,Resource.getResourceByName(allItems[pointer]),message);
        else
            res = tradeController.addRequest(quantity,Resource.getResourceByName(allItems[pointer]),message);
        if(!(res.equals("add request successful")|res.equals("donation successful"))){
            Alert alert =  new Alert(Alert.AlertType.INFORMATION, res, ButtonType.CLOSE);
            alert.setTitle("Failure!");
            alert.showAndWait();
        }
        else{
            Alert alert =  new Alert(Alert.AlertType.INFORMATION, res, ButtonType.CLOSE);
            alert.setTitle("Success!");
            alert.showAndWait();
        }
    }
    public void openViewingPart(){
        ListView<String> sentOffersListView = new ListView<>(userSentOffers);
        Label sentOffersLabel = new Label("Suggestions Sent by User");
        ScrollPane sentOffersScrollPane = new ScrollPane(sentOffersListView);
        VBox sentOffersVBox = new VBox(sentOffersLabel, sentOffersScrollPane);
        sentOffersVBox.setSpacing(10);
        sentOffersVBox.setPadding(new Insets(10));
        ListView<String> receivedOffersListView = new ListView<>(userReceivedOffers);
        receivedOffersListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                chosenItemInString = newValue;
            }
        });
        Label receivedOffersLabel = new Label("Suggestions Received by User");
        ScrollPane receivedOffersScrollPane = new ScrollPane(receivedOffersListView);
        VBox receivedOffersVBox = new VBox(receivedOffersLabel, receivedOffersScrollPane);
        receivedOffersVBox.setSpacing(10);
        receivedOffersVBox.setPadding(new Insets(10));
        Button acceptTrade = new Button("accept");
        Button rejectTrade = new Button("reject");
        acceptTrade.setOnMouseClicked(e -> acceptIt());
        rejectTrade.setOnMouseClicked(e -> rejectIt());
        VBox Buttons = new VBox(acceptTrade,rejectTrade);
        Buttons.setSpacing(10);
        Buttons.setPadding(new Insets(10));
        VBox mainVBox = new VBox(sentOffersVBox, receivedOffersVBox,Buttons);
        mainVBox.setSpacing(20);
        mainVBox.setPadding(new Insets(20));
        Scene scene = new Scene(mainVBox);
        stage.setTitle("Game Page");
        stage.setScene(scene);
        stage.show();
    }
    public TradeItem selector(String str){
        for(TradeItem tradeItem:tradeController.getPlayingReign().getRequestsFromMe()){
            if(tradeController.turnIntoString(tradeItem).equals(str))
                return tradeItem;
        }
        for (TradeItem tradeItem:tradeController.getPlayingReign().getRequestsFromOthers()){
            if(tradeController.turnIntoString(tradeItem).equals(str))
                return tradeItem;
        }
        return null;
    }
    public void acceptIt(){
        if(selector(chosenItemInString) == null) {
            Alert alert =  new Alert(Alert.AlertType.INFORMATION,"Critical Fail", ButtonType.CLOSE);
            alert.setTitle("Failure!");
            alert.showAndWait();
        }
      String res = tradeController.acceptTrade(selector(chosenItemInString).getItemId(),"accepted!");
      if(!res.equals("the trade was accepted successfully")){
          Alert alert =  new Alert(Alert.AlertType.INFORMATION, res, ButtonType.CLOSE);
          alert.setTitle("Failure!");
          alert.showAndWait();
      }
      else{
          Alert alert =  new Alert(Alert.AlertType.INFORMATION, res, ButtonType.CLOSE);
          alert.setTitle("Success!");
          alert.showAndWait();
      }
    }
    public void rejectIt(){
        if(selector(chosenItemInString) == null) {
            Alert alert =  new Alert(Alert.AlertType.INFORMATION,"Critical Fail", ButtonType.CLOSE);
            alert.setTitle("Failure!");
            alert.showAndWait();
        }
        String res = tradeController.rejectTrade(selector(chosenItemInString).getItemId(),"accepted!");
        if(!res.equals("the trade was rejected successfully")){
            Alert alert =  new Alert(Alert.AlertType.INFORMATION, res, ButtonType.CLOSE);
            alert.setTitle("Failure!");
            alert.showAndWait();
        }
        else{
            Alert alert =  new Alert(Alert.AlertType.INFORMATION, res, ButtonType.CLOSE);
            alert.setTitle("Success!");
            alert.showAndWait();
        }
    }
    public void showHistory(){
        ArrayList<String> historyOf = tradeController.getPlayingReign().getHistoryOfTrades();
        ObservableList<String> userList = FXCollections.observableArrayList(historyOf);
        ListView<String> listView = new ListView<>(userList);
        ScrollPane scrollPane = new ScrollPane(listView);
        scrollPane.setFitToHeight(true);
        VBox vbox = new VBox(scrollPane);
        Scene scene = new Scene(vbox, 200, 300);
        stage.setScene(scene);
        stage.setTitle("Trade History");
        stage.show();
    }
    public void back() throws Exception {
        stage.setFullScreen(false);
        ShopMenu tradeMenu = new ShopMenu();
        tradeMenu.setShopController(new ShopController(this.tradeController.getGame()));
        tradeMenu.start(stage);
    }

    /*public void run() {
        System.out.println("You are in the trade menu now!");
        System.out.println(tradeController.notification());
        while(true) {
            input = scanner.nextLine();
            if((matcher = getRealMatcher(input,Commands.ADD_REQUEST,Commands.AMOUNT,Commands.PRICE,Commands.TYPE,
                    Commands.MESSAGE)) != null) {
                System.out.println(tradeController.addRequest(matcher));
            } else if(input.matches("show list of Reigns")){
                System.out.println(tradeController.showMembers());
                if(tradeController.chooseSecondReign().equals("back")) continue;
                runChosenUser();
                tradeController.deleteSecondReign();
            } else if(input.matches("show trade list")) {
                System.out.println(tradeController.showTradeList());
            } else if(input.matches("show my requests from others")) {
                System.out.println(tradeController.showMyRequestsFromOthers());
            } else if((matcher = getRealMatcher(input,Commands.DELETE_TRADE,Commands.ID)) != null) {
                System.out.println(tradeController.deleteTrade(matcher));
            } else if((matcher = getRealMatcher(input,Commands.ACCEPT_REQUEST,Commands.ID,Commands.MESSAGE)) != null) {
                System.out.println(tradeController.acceptTrade(matcher));
            } else if(input.matches("show trade history")) {
                System.out.println(tradeController.showTradeHistory());
            } else if(input.matches("back")) {
                System.out.println("exiting trade menu...");
                tradeController.clearNotification();
                return;
            } else if(input.matches("\\s*show\\s+menu\\s*")) {
                System.out.println("you are in the trade menu!");
            }
            else System.out.println(ResponseToUser.INVALID_COMMAND);
        }
    }*/

    public static String getReignFromUser() {
        System.out.println("enter Regin's nick name to trade with them or print 'back'");
        String str = scanner.nextLine();
        return str;
    }


    public static void nickNameNotFound() {
        System.out.println("nick name not found, please try again");
    }


}
