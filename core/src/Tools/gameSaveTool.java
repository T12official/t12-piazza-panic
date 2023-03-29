package Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.team13.piazzapanic.PlayScreen;


public class gameSaveTool {
    public static void saveMyGame(PlayScreen game){
        FileHandle file = Gdx.files.local("gameSave.txt"); // create a file handle for a local file
        saveGameData data = new saveGameData();
        populateData(game,data);
        Json json = new Json();
        String jsonString = json.toJson(data);
        file.writeString(jsonString, false);
// print the JSON string
        System.out.println(jsonString);
    }

    private static void populateData(PlayScreen game, saveGameData data){
        data.chef1Posx = game.getChef1().b2body.getTransform().getPosition().x;
        data.chef1PosY = game.getChef1().b2body.getTransform().getPosition().y;
        data.chef2Posx = game.getChef2().b2body.getTransform().getPosition().x;
        data.chef2PosY = game.getChef2().b2body.getTransform().getPosition().y;
        data.chef3Posx = game.getChef3().b2body.getTransform().getPosition().x;
        data.chef3PosY = game.getChef3().b2body.getTransform().getPosition().y;
        data.money = game.getHud().getScore();
        data.repPoints = game.getHud().getRepPoints();
        data.minutes = game.getHud().getWorldTimerM();
        data.seconds = game.getHud().getWorldTimerS();
    }
}

class saveGameData {
    float chef1Posx;
    float chef1PosY;
    float chef2Posx;
    float chef2PosY;
    float chef3Posx;
    float chef3PosY;
    int money;
    int repPoints;
    int minutes;
    int seconds;
}
