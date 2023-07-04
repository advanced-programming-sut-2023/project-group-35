package Enum;

import javafx.scene.image.Image;
import javafx.stage.Screen;

import java.net.URL;

public enum ImageEnum {
    LOGIN_MENU_IMAGE("/loginMenuImage.jpg"),
    REGISTER_MENU_IMAGE("/registerMenuImage.jpg"),
    MAIN_MENU_IMAGE("/mainMenuImage.jpg"),
    DEFAULT_MAP_IMAGE("/map/default.PNG"),
    START_GAME_MENU("/startGameMenu.jpg"),
    DEFAULT_AVATAR("/user.png"),
    TAB_MENU_IMAGE("/tabMenu.jpg"),
    //BACK_GROUND("backGround.png"),
    REPAIR_ICON("/icon/delete.png"),
    DELETE_ICON("/icon/repair.png"),
    REIGN_MENU_MAN("/man.png"),
    BACK("/icon/back.png"),
    DOWN("/icon/down.png"),
    RIGHT("/icon/right.png"),
    UP("/icon/up.png"),
    POPUP_BACKGROUND("/popupBackGround.PNG"),
    PAPER_BACK_GROUND("/oldPaper.png"),
    MINI_MAP("/miniMap.png"),



    //UNSELECTED_MAP_IMAGE(""),
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
    public Image getImageWithSize(int width, int height) {
        return new Image(ImageEnum.class.getResource("/Images" + this.imageUrl).toExternalForm(), width, height, false, true);
    }

}

