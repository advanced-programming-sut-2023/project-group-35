package Server;

import controller.GameController;
import controller.ReignController;
import controller.UnitController;
import model.Game;
import model.User;
import view.ReignMenu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;

public class GameMenuPhaseOne {
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    User currentUser;
    private final GameControllerPhaseOne gameMenuController;
    private ShopMenuServer shopServer;
    private final MapMenuServer mapMenuServer;
    private TradeMenuServer tradeMenuServer;
    private final Game game;

    public GameMenuPhaseOne(DataOutputStream dataOutputStream,
                            DataInputStream dataInputStream,
                            User currentUser, GameControllerPhaseOne gameControllerPhaseOne, Game game) {
        this.gameMenuController = gameControllerPhaseOne;
        this.game = game;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        this.currentUser = currentUser;
        this.mapMenuServer = new MapMenuServer(dataInputStream, dataOutputStream, game, currentUser);

    }

    public void gameHandler() throws IOException {
        dataOutputStream.writeUTF("You have entered the game");
        while (true) {
            String sendMessage = null;
            if (dataInputStream.available() != 0) {
                String input = dataInputStream.readUTF();
                Matcher matcher;
                if (!game.getPlayingReign().getUser().equals(currentUser)) {
                    sendMessage = "It's not your turn!";
                } else if ((matcher = Commands.getMatcher(input, Commands.DROP_BUILDING)) != null) {
                    sendMessage = gameMenuController.dropBuilding(matcher);
                } else if (Commands.getMatcher(input, Commands.SHOW_CURRENT_PLAYER) != null) {
                    sendMessage = gameMenuController.getPlayingReign().getNickName();
                } else if (input.equals("enter map menu")){
                    mapMenuServer.run(input);
                } else if ((matcher = Commands.getMatcher(input, Commands.SELECT_BUILDING)) != null) {
                    sendMessage = gameMenuController.selectBuilding(matcher);
                } else if ((matcher = Commands.getMatcher(input, Commands.SELECT_PIXEL_UNIT)) != null) {
                    sendMessage = gameMenuController.selectUnit(matcher);
                } else if ((matcher = Commands.getMatcher(input, Commands.MOVE_UNIT)) != null) {
                    UnitController unitController = new UnitController(game);
                    sendMessage = unitController.moveUnitCommand(Integer.parseInt(matcher.group("X"))
                            , Integer.parseInt(matcher.group("Y")));
                } else if (Commands.getMatcher(input, Commands.SET_STANCE) != null) {
                    UnitController unitController = new UnitController(game);
                    sendMessage = unitController.setUnitState(input);
                } else if ((matcher = Commands.getMatcher(input, Commands.ATTACK_ENEMY)) != null) {
                    UnitController unitController = new UnitController(game);
                    sendMessage = unitController.attackEnemy(Integer.parseInt(matcher.group("row")),Integer.parseInt(matcher.group("coloum")));
                }  else if (Commands.getMatcher(input, Commands.DISBAND_UNIT) != null) {
                    UnitController unitController = new UnitController(game);
                    sendMessage = unitController.disbandUnit();
                } else if (Commands.getMatcher(input, Commands.SHOW_POPULARITY) != null) {
                    ReignController reignController = new ReignController(game);
                    sendMessage = ""+reignController.getPopularity();
                } else if (Commands.getMatcher(input, Commands.SHOW_POPULARITY_FACTORS) != null) {
                    ReignController reignController = new ReignController(game);
                    sendMessage = reignController.showPopularityFactors();
                } else if ((matcher = Commands.getMatcher(input, Commands.FEAR_RATE)) != null) {
                    ReignController reignController = new ReignController(game);
                    sendMessage = reignController.setFearRate(Integer.parseInt(matcher.group("rate")));
                } else if (Commands.getMatcher(input, Commands.FEAR_RATE_SHOW) != null) {
                    ReignController reignController = new ReignController(game);
                    sendMessage = ""+game.getPlayingReign().getFearRate();
                } else if (Commands.getMatcher(input, Commands.FOOD_RATE_SHOW) != null) {
                }  else if ((matcher = Commands.getMatcher(input, Commands.FOOD_RATE)) != null) {
                    ReignController reignController = new ReignController(game);
                    sendMessage = reignController.setFoodRate(Integer.parseInt(matcher.group("rate")));
                } else if (Commands.getMatcher(input, Commands.SHOW_GOLD) != null) {
                    sendMessage = ""+game.getPlayingReign().getGold();
                } else if (Commands.getMatcher(input, Commands.TAX_RATE_SHOW) != null) {
                    sendMessage = ""+game.getPlayingReign().getTaxRate();
                } else if ((matcher = Commands.getMatcher(input, Commands.TAX_RATE))
                        != null) {
                    ReignController reignController = new ReignController(game);
                    sendMessage = reignController.setTaxRate(Integer.parseInt(matcher.group("rate")));
                } else if (Commands.getMatcher(input, Commands.SHOW_POPULATION) != null) {
                    sendMessage = ""+game.getPlayingReign().getPopulation();
                } else if (Commands.getMatcher(input, Commands.NEXT_TURN) != null) {
                    String returnMessage = gameMenuController.nextTurn();
                    if (returnMessage != null && returnMessage.contains("End")) {
                        return;
                    }
                } else if (Commands.getMatcher(input, Commands.ENTER_TRADE_MENU) != null) {
                    sendMessage = "You have entered the trade menu";
                    tradeMenuServer.run(dataInputStream, dataOutputStream);
                } else {
                    sendMessage = "Invalid command!";
                }
                if (sendMessage != null) {
                    dataOutputStream.writeUTF(sendMessage);
                }
            }
        }
    }
}
