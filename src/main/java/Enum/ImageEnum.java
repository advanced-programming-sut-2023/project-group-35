package Enum;

import javafx.scene.image.Image;
import javafx.stage.Screen;

import java.net.URL;

public enum ImageEnum {
    LOGIN_MENU_IMAGE("/loginMenuImage.jpg"),
    REGISTER_MENU_IMAGE("/registerMenuImage.jpg"),
    MAIN_MENU_IMAGE("/mainMenuImage.jpg"),
    DEFAULT_MAP_IMAGE(""),
    UNSELECTED_MAP_IMAGE(""),
    ;

    ImageEnum(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;
    public URL test = ImageEnum.class.getResource("/Images/registerMenuImage.jpg");
    public static Image getImage(ImageEnum imageName, boolean isBackGround) {
        System.out.println("height = " + Screen.getPrimary().getBounds().getHeight() + "width + " + Screen.getPrimary().getBounds().getWidth());
        if(isBackGround) return new Image(ImageEnum.class.getResource("/Images" + imageName.imageUrl).toExternalForm(), Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight(), false, true);
        return new Image(ImageEnum.class.getResource("/Images" + imageName.imageUrl).toExternalForm());
    }
}

