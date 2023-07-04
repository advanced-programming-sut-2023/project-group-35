package view;

import controller.GameController;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.Block;
import model.Reign;
import model.buildings.Building;
import javafx.scene.image.ImageView;
import model.people.MilitaryUnit;
import Enum.*;

import java.util.ArrayList;

import static view.MapMenu.mapPane;

public class RectBlock extends Rectangle {
    private final Block block;
    private ImageView BuildingView;
    private ImageView treeView;
    private ArrayList<ImageView> imageViews = new ArrayList<>();
    private ArrayList<ImageView> troopsView = new ArrayList<>();
    private ArrayList<ImageView> structuresView = new ArrayList<>();

    public RectBlock(Block block, double x, double y, double height, double width) {
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

    public String removeBuilding(Reign reign, GameController gameController) {
        if(!block.hasABuilding()) return "not a building here";
        Building building = block.getBuilding();
        if(!building.getOwner().equals(reign)) return "you are not the owner of this building";
        if(this.getBuildingView() != null) {
            this.setBuildingView(null);
            mapPane.getChildren().remove(this.getBuildingView());
        }
        gameController.removeBuilding(building);
        return "building removed successfully!";
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


    public void removeUnit(MilitaryUnit unit, Pane mapPane) {
        ArrayList<ImageView> views = new ArrayList<>();
        for (ImageView imageView : troopsView) {
            UnitType type = UnitType.getUnitByImage(imageView.getImage());
            if(unit.getUnitType().equals(type)) {
                mapPane.getChildren().remove(imageView);
                views.add(imageView);
            }
        }
        for (ImageView view : views) {
            troopsView.remove(view);
        }
    }
}
