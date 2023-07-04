package view;

import Server.GameData;
import controller.GameController;
import controller.ShopController;
import Enum.*;
import controller.TradeController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Game;

import java.net.URL;

public class ShopMenu extends Menu{
    ShopController shopController;
    @FXML
    Button buy;
    @FXML
    Button sell;
    @FXML
    Button left;
    @FXML
    Button right;
    @FXML
    Button submit;
    @FXML
    ImageView afterItem;
    @FXML
    ImageView beforeItem;
    @FXML
    ImageView gold;
    @FXML
    ImageView currentItem;
    @FXML
    Text goldYouHave;
    @FXML
    TextField amountOF;
    int pointer = 0;
    double amountYouWannaChange = 10;
    private static Pane pane;
    private Scene scene;
    String allItems[]={"WOOD","STONE","WHEAT","APPLE","CHEESE","MEAT","PROCESSED_MEAT","IRON","HOP","BEAR","FLOUR","BREAD","HORSE","TAR","BOW","SPEAR","ARMOR","SWORD"};
    public Stage shopStage;
    public static Game game;

    public void setShopController(ShopController shopController) {
        this.shopController = shopController;
    }

    @FXML
    public void initialize(){
        pointer = 0;
        setImages(pointer);
    }
    public void addWatcher(){
        amountOF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    amountOF.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @Override
    public void start(Stage stage) throws Exception {
        shopStage = stage;
        shopStage.setFullScreen(true);
        URL url = ProfileMenu.class.getResource("/FXML/ShopMenu.fxml");
        Background background = new Background(new BackgroundImage(new Image(ProfileMenu.class.getResource("/Images/BG/bgPM.jpg").toString(),
                Screen.getPrimary().getBounds().getHeight(), Screen.getPrimary().getBounds().getWidth(), false, false)
                , BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)
        );
        pane = FXMLLoader.load(url);
        pane.setBackground(background);
        Button button = new Button("Back");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                     @Override
                                     public void handle(MouseEvent mouseEvent) {
                                         try {
                                             shopStage.close();
                                         } catch (Exception e) {
                                             throw new RuntimeException(e);
                                         }
                                     }
                                 }
        );
        pane.getChildren().add(button);
        scene = new Scene(pane);
        shopStage.setScene(scene);
        shopStage.show();
    }
    public void setImages(int ptr){
        Image image1 = new Image(ShopMenu.class.getResource("/Images/Assests/0.png").toString());
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
        Image image2 = new Image(ShopMenu.class.getResource(("/Images/Assests/"+(lPtr+1)+".png")).toString());
        Image image3 = new Image(ShopMenu.class.getResource(("/Images/Assests/"+rPtr+".png")).toString());
        Image image4 = new Image(ShopMenu.class.getResource(("/Images/Assests/"+(ptr+1)+".png")).toString());
        beforeItem.setImage(image2);
        beforeItem.setPreserveRatio(true);
        beforeItem.setFitHeight(50);
        beforeItem.setFitHeight(50);
        afterItem.setImage(image3);
        afterItem.setPreserveRatio(true);
        afterItem.setFitHeight(50);
        afterItem.setFitHeight(50);
        currentItem.setImage(image4);
        currentItem.setPreserveRatio(true);
        currentItem.setFitHeight(150);
        currentItem.setFitHeight(150);
        gold.setImage(image1);
        gold.setPreserveRatio(true);
        gold.setFitHeight(150);
        gold.setFitHeight(150);
        goldYouHave.setText(""+game.getPlayingReign().getGold());
    }
    public void goLeft(){
        if(pointer == 0)
            pointer = 0;
        else
            pointer--;
        setImages(pointer);
    }
    public void goRight(){
        if(pointer == 17)
            pointer = 17;
        else
            pointer++;
        setImages(pointer);
    }
    public void submiting(){
        amountYouWannaChange = Double.parseDouble(amountOF.getText());
    }
    public void buying(){
        shopController = new ShopController(game);
        String res = shopController.purchase((int)amountYouWannaChange,Resource.getResourceByName(allItems[pointer]));
        Alert alert = new Alert(Alert.AlertType.INFORMATION, res, ButtonType.CLOSE);
        if(!res.equals("you have bought the resources successfully")){
            alert.setTitle("Failure!");
        }
        else{
            alert.setTitle("Success!");
        }
        alert.showAndWait();
    }
    public void selling(){
        shopController = new ShopController(game);
        String res = shopController.sell((int)amountYouWannaChange,Resource.getResourceByName(allItems[pointer]));
        Alert alert = new Alert(Alert.AlertType.INFORMATION, res, ButtonType.CLOSE);
        if(!res.equals("selling resources successful")){
            alert.setTitle("Failure!");
        }
        else{
            alert.setTitle("Success!");
        }
        alert.showAndWait();
    }
    public void enterTrade() throws Exception {
        shopStage.setFullScreen(false);
        TradeMenu tradeMenu = new TradeMenu();
        tradeMenu.setTradeController(new TradeController(game));
        tradeMenu.start(shopStage);
    }
    public void back() throws Exception {
        shopStage.close();
    }


    //todo price of buy and sell

    /*public void run() {
        System.out.println("You are in the shop menu now!");
        while (true) {
            input = scanner.nextLine();
            if(input.matches("show price list")) {
                System.out.println(shopController.showPriceList());
            } else if((matcher = getRealMatcher(input,Commands.PURCHASE,Commands.AMOUNT,Commands.ITEM)) != null) {
                System.out.println(shopController.purchase(matcher));
            } else if((matcher = getRealMatcher(input,Commands.SELL,Commands.AMOUNT,Commands.ITEM)) != null) {
                System.out.println(shopController.sell(matcher));
            } else if((input.matches(Commands.BACK.regex))){
                System.out.println("exiting shop menu...");
                return;
            } else if(input.matches("\\s*show\\s+menu\\s*")){
                System.out.println("you are in the shop menu right now!");
            }
            else System.out.println(ResponseToUser.INVALID_COMMAND);
        }
    }*/
}
