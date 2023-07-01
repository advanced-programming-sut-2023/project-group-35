package Enum;

import javafx.scene.image.Image;
import model.buildings.Building;

import javax.swing.plaf.PanelUI;
import java.net.URL;
import java.util.ArrayList;

public enum Tree {
    CHESTNUT("tree_chestnut"),
    OAK("tree_oak"),
    PINE("tree_pine"),
    APPLE("tree_apple"),
    PALM("tree_birch");
    private Image image;
    private ArrayList<Image> images = new ArrayList<>();

    Tree(String imageUrl) {
        this.image = new Image(Tree.class.getResource("/Images/plant/" + imageUrl + "/0_0img0.png").toExternalForm());
//        for (int i = 0; i < 12; i++) {
//            images.add(new Image(Tree.class.getResource("/Images/plant/" + imageUrl + (2 * i) + ".png").toExternalForm()))
//        }
    }

    public Image getImage() {
        return image;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public static Tree getTreeByName(String name) {
        for (Tree value : Tree.values()) {
            if(value.name().toLowerCase().equals(name)) return value;
        }
        return null;
    }
    public static Tree getTreeByImage(Image image) {
        String url = image.getUrl();
        for (Tree value : values()) {
            if(url.contains(value.name().toLowerCase())) return value;
        }
        return null;
    }

}
