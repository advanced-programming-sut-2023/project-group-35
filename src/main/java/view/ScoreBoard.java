package view;

import com.beust.ah.A;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreBoard extends Menu {

    public static void main(String[] args) {
        launch(args);
    }
    private ArrayList<String> userData = new ArrayList<>();
    private ArrayList<User> ussr = removeRepeated(User.getUsers());
    private ObservableList<String> playerData;
    private final int pageSize = 10;
    private int currentPage = 0;
    @Override
    public void start(Stage stage) {
        ussr = sortIt(ussr);
        userData = fillData(ussr);
        playerData = FXCollections.observableArrayList(userData);
        stage.setTitle("Scoreboard");
        VBox vbox = new VBox();
        ScrollPane scrollPane = new ScrollPane(vbox);
        loadPlayers(currentPage, vbox);
        scrollPane.vvalueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.doubleValue() == scrollPane.getVmax()) {
                currentPage++;
                loadPlayers(currentPage, vbox);
            }
        });
        Scene scene = new Scene(scrollPane, 300, 400);
        stage.setScene(scene);
        stage.show();
    }
    public ArrayList<User> removeRepeated(ArrayList<User> starter){
        ArrayList<String> names = new ArrayList<>();
        ArrayList<User> backUp = new ArrayList<>();
        for(User cUser:starter){
            if(names.contains(cUser.getUserName())){
                int a = names.indexOf(cUser.getUserName());
                backUp.add(a,cUser);
            }
            else{
                names.add(cUser.getUserName());
                backUp.add(cUser);
            }
        }
        return backUp;
    }
    public static ArrayList<User> sortIt(ArrayList<User> input){
        Collections.sort(input,User::compareTo);
        return input;
    }
    public static ArrayList<String>fillData(ArrayList<User> input){
        String output = "";
        ArrayList<String> goneOut = new ArrayList<>();
        for(User cUser:input){
            output = cUser.getUserName() + " - " +cUser.getNickName() + " - "+cUser.highScore;
            goneOut.add(output);
        }
        return goneOut;
    }
    private void loadPlayers(int page, VBox container) {
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, playerData.size());
        container.getChildren().clear();
        for (int i = startIndex; i < endIndex; i++) {
            Label playerLabel = new Label(playerData.get(i));
            container.getChildren().add(playerLabel);
        }
    }
}
