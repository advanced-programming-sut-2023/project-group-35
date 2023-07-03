package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import Enum.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;





public class InitStyle {
    public static Color buttonBackGround = Color.rgb(146, 31, 22);
    public static void setBackGround(Pane pane, ImageEnum imageEnum) {
        BackgroundSize backgroundSize = new BackgroundSize(-1.0, -1.0, true, true, true, false);
        pane.setBackground(new Background(new BackgroundImage(ImageEnum.getImage(imageEnum, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
    }
    public static void setBackGroundColor(Label label , Color color) {
        label.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }
    public static Image makeImageBySize(String address, int width, int height ) {
        return new Image(ImageEnum.class.getResource("Images/Avatar").toExternalForm()+address, width, height, false, true);
    }
    public static Button setGameButtonStyles(String name, int height, int width) {
        Button button = new Button(name);
        button.setStyle("-fx-border-color: black");
        button.setFont(Font.font("New Times Roman", FontWeight.BOLD, 13));
        //button.setStyle("-fx-text-fill: Times Roman");
//        button.setBorder(new Border());
        button.setPrefHeight(height);
        button.setPrefWidth(width);
        //button.setStyle("-fx-background-radius: 15px");

        //button.setStyle("-fx-border-radius: 10px");
        button.setTextFill(Color.rgb(230, 217, 156));
        button.setBackground(new Background(new BackgroundFill(buttonBackGround, CornerRadii.EMPTY, Insets.EMPTY)));
        return button;
    }
    public static Tooltip BuildToolTip(String tip) {
        Tooltip toolTip = new Tooltip(tip);
        toolTip.setFont(Font.font("new times roman", 13));
        toolTip.setShowDelay(Duration.seconds(0.4));
        toolTip.setHideDelay(Duration.seconds(0.8));
        toolTip.setTextAlignment(TextAlignment.CENTER);
        toolTip.setAnchorX(10);
        toolTip.setAnchorY(10);
        return toolTip;
    }
    public static HBox createHbox() {
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(30, 20, 30, 20));
        return hBox;
    }
    public static ImageView getImageView(Image image, int height, int width) {
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        return imageView;
    }
}
