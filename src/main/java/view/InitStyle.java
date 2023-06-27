package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import Enum.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class InitStyle {
    public static Color buttonBackGround = Color.rgb(146, 31, 22);
    public static void setBackGround(Pane pane, ImageEnum imageEnum) {
        BackgroundSize backgroundSize = new BackgroundSize(-1.0, -1.0, true, true, true, false);
        pane.setBackground(new Background(new BackgroundImage(ImageEnum.getImage(imageEnum, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize)));
    }
    public static Button setGameButtonStyles(String name, int height, int width) {
        Button button = new Button(name);
        button.setStyle("-fx-border-color: black");
        button.setPrefHeight(height);
        button.setPrefWidth(width);
        button.setStyle("-fx-background-radius: 10");
        button.setStyle("-fx-border-radius: 10");
        button.setTextFill(Color.rgb(230, 217, 156));
        button.setBackground(new Background(new BackgroundFill(buttonBackGround, CornerRadii.EMPTY, Insets.EMPTY)));
        return button;

    }
}
