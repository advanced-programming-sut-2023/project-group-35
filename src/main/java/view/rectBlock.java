package view;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.Block;
import model.Reign;
import model.structures.Structure;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

public class rectBlock extends Rectangle {
    private final Block block;
    private ImageView BuildingView;
    private ImageView treeView;
    private ArrayList<ImageView> imageViews = new ArrayList<>();
    private ArrayList<ImageView> troopsView = new ArrayList<>();
    private ArrayList<ImageView> structuresView = new ArrayList<>();

    public rectBlock(Block block, double x, double y, double height, double width) {
        super(x, y, height, width);
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    public ImageView getBuildingView() {
        return BuildingView;
    }

    public void setBuildingView(ImageView buildingView) {
        if(this.getBuildingView() != null || treeView != null) return;
        BuildingView = buildingView;
    }


    public ImageView getTreeView() {
        return treeView;
    }

    public void setTreeView(ImageView treeView) {
        if(this.getTreeView() != null || getBuildingView() != null) return;
        this.treeView = treeView;
    }

    public void clearBlock(Pane mapPane, Reign playing) {
        block.clearBlock(playing);
        if(this.getBuildingView() != null) {
            this.setBuildingView(null);
            mapPane.getChildren().remove(this.getBuildingView());
        }
        if(block.getTree() != null || treeView != null) {
            this.setTreeView(null);
            mapPane.getChildren().remove(treeView);
        }
        for (ImageView imageView : this.troopsView) {
            mapPane.getChildren().remove(imageView);
        } troopsView.clear();
        imageViews.clear();
    }

    public ArrayList<ImageView> getImageViews() {
        return imageViews;
    }

    public ArrayList<ImageView> getTroopsView() {
        return troopsView;
    }

    public ArrayList<ImageView> getStructuresView() {
        return structuresView;
    }

}
