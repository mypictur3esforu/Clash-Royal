import java.awt.*;
import java.util.ArrayList;

public class Spiel {
    String gamemode;
    Spielfeld feld;

    Spiel(String gamemode, ArrayList<Card> troopsForGame){
    Spieler[] player = new Spieler[]{new Spieler("Game", null, null), new Spieler("Lukas", troopsForGame, new Color(0x00EDFF)), new Spieler("Bot", troopsForGame, new Color(0xFF0000))};
        this.gamemode = gamemode;
        feld = new Spielfeld(1000/60, player);
//        gamesTroops = new TroopSelection(troopsForGame);
    }
}
