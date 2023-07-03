package Server;

import java.util.ArrayList;

public class GameData {
    private String order;
    private long StoppageTime;
    public static ArrayList<ArrayList<GameData>> historyOfMatches = new ArrayList<>();
    static {
        ArrayList<GameData> gameData = new ArrayList<>();
        GameData gameData1 = new GameData("game started",1);
        gameData.add(gameData1);
        gameData.add(new GameData("Alireza1:create hovel -x 25 -y 30",5));
        gameData.add(new GameData("Alireza1:next turn",2));
        gameData.add(new GameData("Abolfazl:create mercenary camp -x 30 -y 32",3));
        gameData.add(new GameData("Abolfazl:create unit arabian swordman -x 30 -y 32 -a 20",3));
        gameData.add(new GameData("Abolfazl:move units From:-x 30 -y 32 To:-x 25 -y 30",3));
        gameData.add(new GameData("Abolfazl:next turn",2));
        gameData.add(new GameData("Alireza1:next turn",2));
        gameData.add(new GameData("Abolfazl:attack units From:-x 25 -y 30 To:-x 25 -y 30",3));
        gameData.add(new GameData("Abolfazl:set unit stance -x 25 -y 30:ATTACKING",8));
        gameData.add(new GameData("Abolfazl:Alireza1 base was destroid",10));
        gameData.add(new GameData("Game finished:Winner= Abolfazl",1));
        historyOfMatches.add(gameData);
    }

    public GameData(String order, int stoppageTime) {
        this.order = order;
        this.StoppageTime = stoppageTime;
    }
    public static void playTheHistory(ArrayList<GameData> playing){
        long currentTime = System.currentTimeMillis();
        for(GameData gd:playing){
            while(true){
                if(System.currentTimeMillis() - currentTime > gd.StoppageTime)
                    break;
            }
            System.out.println(gd.order+"\n");
        }
    }


}
