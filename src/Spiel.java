import java.awt.*;
import java.util.ArrayList;

/**
 * Ein Spiel bestimmt die Gegebenheiten, die im Spielfeld herrschen.
 * Also den Spielmodus, der Aspekte wie die Schnelligkeit des Elixir Erhaltens vorgibt und variieren kann
 */
public class Spiel {
    /**
     * Name des Spielmodus (bisher ohne Bedeutung)
     */
    private String gamemode;
    /**
     *
     */
    private Spielfeld feld;

    /**
     * Erstellt ein Spiel
     * @param gamemode Name des Spielmodus
     * @param troopsForGame
     */
    Spiel(String gamemode, ArrayList<Card> troopsForGame){
    Spieler[] player = new Spieler[]{new Spieler("Game", null, null),
            new Spieler("Lukas", TeamPanel.GetStaticTeam(), new Color(0x00EDFF)),
            new Bot("Bot", TeamPanel.GetStaticTeam(), new Color(0xFF19B8))};
        this.gamemode = gamemode;
        feld = new Spielfeld(1000/60, player);
//        gamesTroops = new TroopSelection(troopsForGame);
    }
}
