import java.util.ArrayList;

public class Spiel {
    String gamemode;
    Spielfeld feld;

    Spiel(String gamemode, ArrayList<Card> troopsForGame){
    Spieler[] player = new Spieler[]{new Spieler("Game", null), new Spieler("Lukas", troopsForGame), new Spieler("Bot", troopsForGame)};
        this.gamemode = gamemode;
        feld = new Spielfeld(1000/60, player);
//        gamesTroops = new TroopSelection(troopsForGame);
    }
}
