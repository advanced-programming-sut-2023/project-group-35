package view;

import controller.GameController;
import controller.ReignController;
import controller.UserController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Game;
import model.Reign;
import model.User;

import java.util.ArrayList;
import java.util.regex.Matcher;
import Enum.*;
public class ReignMenu extends Menu{

    private ReignController reignController;
    private ListView<String> criteriaListView;
    private ImageView popularityImageView;
    private ImageView popularityCImageView;
    private Label popularityChangeLabel;
    public Stage reignStage = new Stage();

    public void setReignController(ReignController reignController) {
        this.reignController = reignController;
    }

    @Override
    public void start(Stage stage) throws Exception {
            reignStage = stage;
            Pane pane = new Pane();
        Background background = new Background(new BackgroundImage(new Image(ProfileMenu.class.getResource("/Images/BG/bgPM.jpg").toString(),
                Screen.getPrimary().getBounds().getHeight(), Screen.getPrimary().getBounds().getWidth(), false, false)
                , BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)
        );
        pane.setBackground(background);
            VBox root = new VBox();
            root.setSpacing(10);
            root.setPadding(new Insets(20));
            Button button1 = new Button("Showing factors");
            button1.setOnAction(event -> {
                openShowMenu();
            });

            Button button2 = new Button("Changing factors");
            button2.setOnAction(event -> {
                openChangingMenu();
            });
            root.setAlignment(Pos.CENTER);
            root.getChildren().addAll(button1, button2);
            pane.getChildren().add(root);
            Scene scene = new Scene(pane, 300, 200);
            reignStage.setTitle("Reign Menu");
            reignStage.setScene(scene);
            reignStage.show();
    }
    public void openShowMenu(){
        BorderPane root = new BorderPane();
        VBox popularitySection = createPopularitySection();
        root.setCenter(popularitySection);
        VBox criteriaSection = createCriteriaSection();
        root.setRight(criteriaSection);
        Pane pane = new Pane();
        Background background = new Background(new BackgroundImage(new Image(ProfileMenu.class.getResource("/Images/BG/bgPM.jpg").toString(),
                Screen.getPrimary().getBounds().getHeight(), Screen.getPrimary().getBounds().getWidth(), false, false)
                , BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)
        );
        pane.setBackground(background);
        pane.getChildren().add(root);
        Scene scene = new Scene(pane, 800, 600);
        reignStage.setTitle("View Menu");
        reignStage.setScene(scene);
        reignStage.show();
    }
    private VBox createPopularitySection() {
        VBox section = new VBox();
        section.setAlignment(Pos.CENTER);
        section.setSpacing(20);
        Label popularityLabel = new Label("-"+reignController.showPopularity()+"-");
        popularityLabel.setStyle("-fx-font-size: 24px;");
        Image currentPopularityImage = null;
        assignColorAndPicture(popularityLabel,currentPopularityImage,30);
        if(reignController.getPlayingReign().getPopularity() > 66){
            popularityLabel.setTextFill(Color.GREEN);
            currentPopularityImage = new Image(ProfileMenu.class.getResource("/Images/Masks/1.png").toString(),30,30,false,false);
        }else if(reignController.getPlayingReign().getPopularity() > 33){
            popularityLabel.setTextFill(Color.YELLOW);
            currentPopularityImage = new Image(ProfileMenu.class.getResource("/Images/Masks/0.png").toString(),30,30,false,false);
        }else{
            popularityLabel.setTextFill(Color.RED);
            currentPopularityImage = new Image(ProfileMenu.class.getResource("/Images/Masks/2.png").toString(),30,30,false,false);
        }
        popularityImageView = new ImageView(currentPopularityImage);
        HBox imageContainer = new HBox(popularityImageView);
        imageContainer.setAlignment(Pos.CENTER);
        popularityChangeLabel = new Label(reignController.showAmountOfChange());
        popularityChangeLabel.setStyle("-fx-font-size: 18px;");
        Image popularityChange = null;
        if(Integer.parseInt(popularityChangeLabel.getText()) > 0){
            popularityChangeLabel.setTextFill(Color.GREEN);
            popularityChange = new Image(ProfileMenu.class.getResource("/Images/Masks/1.png").toString(),5,5,false,false);
        }else if(Integer.parseInt(popularityChangeLabel.getText()) == 0){
            popularityChangeLabel.setTextFill(Color.YELLOW);
            popularityChange = new Image(ProfileMenu.class.getResource("/Images/Masks/0.png").toString(),5,5,false,false);
        }else{
            popularityChangeLabel.setTextFill(Color.RED);
            popularityChange = new Image(ProfileMenu.class.getResource("/Images/Masks/2.png").toString(),5,5,false,false);
        }
        popularityCImageView = new ImageView(popularityChange);
        HBox imageContainer2 = new HBox(popularityCImageView);
        assignColorAndPicture(popularityChangeLabel,popularityChange,5);
        if(Integer.parseInt(popularityChangeLabel.getText()) > 0){
            popularityChangeLabel.setTextFill(Color.GREEN);
            popularityChange = new Image(ProfileMenu.class.getResource("/Images/Masks/1.png").toString(),5,5,false,false);
        }else if(Integer.parseInt(popularityChangeLabel.getText()) == 0){
            popularityChangeLabel.setTextFill(Color.YELLOW);
            popularityChange = new Image(ProfileMenu.class.getResource("/Images/Masks/0.png").toString(),5,5,false,false);
        }else{
            popularityChangeLabel.setTextFill(Color.RED);
            popularityChange = new Image(ProfileMenu.class.getResource("/Images/Masks/2.png").toString(),5,5,false,false);
        }
        Button button = new Button("Back");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                   reignStage.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        );
        section.getChildren().addAll(popularityLabel, imageContainer, popularityChangeLabel,imageContainer2,button);
        return section;
    }
    public void assignColorAndPicture(Label popularityLabel,Image currentPopularity,int size){
        if(reignController.getPlayingReign().getPopularity() > 66){
            popularityLabel.setTextFill(Color.GREEN);
            currentPopularity = new Image(ProfileMenu.class.getResource("/Images/Masks/1.png").toString(),size,size,false,false);
        }else if(reignController.getPlayingReign().getPopularity() > 33){
            popularityLabel.setTextFill(Color.YELLOW);
            currentPopularity = new Image(ProfileMenu.class.getResource("/Images/Masks/0.png").toString(),size,size,false,false);
        }else{
                popularityLabel.setTextFill(Color.GREEN);
            currentPopularity = new Image(ProfileMenu.class.getResource("/Images/Masks/2.png").toString(),size,size,false,false);
        }
    }
    private VBox createCriteriaSection() {
        VBox section = new VBox();
        section.setAlignment(Pos.CENTER);
        section.setPadding(new Insets(20));
        section.setSpacing(10);
        ArrayList<String> factors = new ArrayList<>();
        factors.add(reignController.showTaxRate());
        factors.add(reignController.showFoodRate());
        factors.add(reignController.showFearRate());
        factors.add(reignController.showPopularity());
        factors.add(reignController.getPlayingReign().getPopulation()+"");
        factors.add(reignController.getPlayingReign().getGold()+"");
        criteriaListView = new ListView<>();
        criteriaListView.getItems().addAll(factors);
        Button button = new Button("Back");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                     @Override
                                     public void handle(MouseEvent mouseEvent) {
                                         try {
                                             reignStage.close();
                                         } catch (Exception e) {
                                             throw new RuntimeException(e);
                                         }
                                     }
                                 }
        );
        section.getChildren().addAll(criteriaListView,button);
        return section;
    }
    public void openChangingMenu(){
        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(20));
        Label taxLabel = new Label("Tax Rate:");
        Slider taxSlider = createSlider(-2, 8, 0);
        taxSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            reignController.setTaxRate(newValue.intValue());
        });
        root.getChildren().addAll(taxLabel, taxSlider);
        Label fearLabel = new Label("Fear Factor:");
        Slider fearSlider = createSlider(-5, 5, 0);
        fearSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
           reignController.setFearRate(newValue.intValue());
        });
        root.getChildren().addAll(fearLabel, fearSlider);
        Label foodLabel = new Label("Food Rate:");
        Slider foodSlider = createSlider(-2, 2, 0);
        foodSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
           reignController.setFoodRate(newValue.intValue());
        });
        root.getChildren().addAll(foodLabel, foodSlider);
        Pane pane = new Pane();
        Background background = new Background(new BackgroundImage(new Image(ProfileMenu.class.getResource("/Images/BG/bgPM.jpg").toString(),
                Screen.getPrimary().getBounds().getHeight(), Screen.getPrimary().getBounds().getWidth(), false, false)
                , BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)
        );
        pane.setBackground(background);
        pane.getChildren().add(root);
        Scene scene = new Scene(pane, 300, 200);
        reignStage.setTitle("Setting factors");
        reignStage.setScene(scene);
        reignStage.show();
    }
    private Slider createSlider(double min, double max, double value) {
        Slider slider = new Slider(min, max, value);
        slider.setOrientation(Orientation.HORIZONTAL);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1);
        slider.setBlockIncrement(1);
        return slider;
    }


   /* public void run() {
        System.out.println("You are in the reign menu now!");
        while (true) {
            input = scanner.nextLine();
            if ((matcher = Commands.getMatcher(input, Commands.SHOW_POPULARITY_FACTORS)) != null) {
                System.out.println(reignController.showPopularityFactors());
            } else if((matcher = Commands.getMatcher(input, Commands.SHOW_POPULARITY)) != null) {
                System.out.println(reignController.showPopularity());
            } else if ((matcher = Commands.getMatcher(input, Commands.SHOW_FOOD_LIST)) != null) {
                System.out.println(reignController.showFoodList());
            }else if ((matcher = Commands.getMatcher(input, Commands.FOOD_RATE_SHOW)) != null) {
                System.out.println(reignController.showFoodRate());
            }else if ((matcher = Commands.getMatcher(input, Commands.TAX_RATE_SHOW)) != null) {
                System.out.println(reignController.showTaxRate());
            } else if ((matcher = getRealMatcher(input,Commands.FOOD_RATE,Commands.RATE)) != null) {
                System.out.println(reignController.setFoodRate(matcher));
            }else if ((matcher = Commands.getMatcher(input, Commands.FEAR_RATE_SHOW)) != null) {
                System.out.println(reignController.showFearRate());
            } else if ((matcher = getRealMatcher(input, Commands.TAX_RATE,Commands.RATE)) != null) {
                System.out.println(reignController.setTaxRate(matcher));
            }else if ((matcher = getRealMatcher(input, Commands.FEAR_RATE,Commands.RATE)) != null) {
                System.out.println(reignController.setFearRate(matcher));
            }else if ((matcher = Commands.getMatcher(input, Commands.BACK)) != null) {
                System.out.println("exiting reign menu");
                return;
            } else if(input.matches("\\s*show\\s+menu\\s*")){
                System.out.println("you are in the reign menu right now!");
            }
            else {
                System.out.println(ResponseToUser.INVALID_COMMAND);
            }

        }
    }*/
}
