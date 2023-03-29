package Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.team13.piazzapanic.PlayScreen;

public class loadGameSave {
    //TODO IMPLEMENT ERROR HANDLING INCASE THE SAVE FILE IS MALFORMED!!!
    public static void loadMyGame(PlayScreen game){
        FileHandle file = Gdx.files.local("gameSave.txt");
        String fileData = file.readString();

        Json json = new Json();
        saveGameData data = json.fromJson(saveGameData.class, fileData);
        System.out.println(fileData);
        System.out.println(data.money);
        //Assigning class values

        game.getChef1().b2body.setTransform(data.chef1Posx, data.chef1PosY, 0);

        game.getChef2().b2body.setTransform(data.chef2Posx, data.chef2PosY, 0);

        game.getChef3().b2body.setTransform(data.chef3Posx, data.chef3PosY,0);

        game.getHud().setScore(data.money);
        game.getHud().setWorldTimerM(data.minutes);
        game.getHud().setWorldTimerS(data.seconds);
        game.getHud().setRepPoints(data.repPoints);

    }
}
