package view;

import javafx.scene.layout.*;
import Enum.*;
public class InitStyle {
    public static void setBackGround(Pane pane, ImageEnum imageEnum) {
        BackgroundSize backgroundSize = new BackgroundSize(-1.0, -1.0, true, true, true, false);
        pane.setBackground(new Background(new BackgroundImage(ImageEnum.getImage(imageEnum, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize)));
    }
}
