import java.awt.*;
import java.util.ArrayList;

public class Spiel {
    private String gamemode;
    private Spielfeld feld;

    Spiel(String gamemode, ArrayList<Card> troopsForGame){
    Spieler[] player = new Spieler[]{new Spieler("Game", null, null),
            new Spieler("Lukas", Team.GetStaticTeam(), new Color(0x00EDFF)),
            new Bot("Bot", Team.GetStaticTeam(), new Color(0xFF19B8))};
        this.gamemode = gamemode;
        feld = new Spielfeld(1000/60, player);
//        gamesTroops = new TroopSelection(troopsForGame);
    }
}
