import java.awt.*;
import java.util.ArrayList;

/**
 * Bots sind autonome Spieler. Sie treten gegen einen Spieler an und versuchen gegen diesen zu gewinnen.
 * Prinzipiell können sie alles, was ein Spieler kann, nur platzieren sie ihre Truppen automatisch
 */
public class Bot extends Spieler{

    /**
     * Erstellt den Bot der gegen den Spieler spielt
     * @param name Name des Bots
     * @param team Karten, die im Spiel verfügbar sind
     * @param color Teamfarbe des Bots
     */
    Bot(String name, ArrayList<Card> team, Color color){
        super(name,team, color);
    }

    /**
     * Aktualisiert den Bot bzw. gibt eine Truppe zurück, wenn eine platziert werden soll
     * @return Gibt die Truppe zurück, die platziert werden soll
     */
    Entity Update(){
        Card order = ShouldPlaceTroop();
        if (order == null) return null;
        return new Entity(order, Math.floor(Math.random() * 700), Math.floor(Math.random() * 400), this);
    }

    /**
     * Lässt alle Truppen finden, die gerade setzbar sind (genug Elixier und auswählbar) und lässt diese, wenn es welche gibt, platzieren.
     */
     Card ShouldPlaceTroop(){
        ArrayList<Card> playableTroops = GetPlayableTroops();
        if (playableTroops == null) return null;
        return ChooseTroop(GetPlayableTroops());
    }

    /**
     * Findet alle spielbaren Karten
     * @return Alle zur Zeit spielbaren Karten
     */
    ArrayList<Card> GetPlayableTroops(){
        ArrayList<Card> playableTroops = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            if (GetCardSelection().get(i).GetElixir() <= GetElixir()) playableTroops.add(GetCardSelection().get(i));
        }
        return playableTroops;
    }

    /**
     * Sucht, aktuell noch zufällig, eine Truppe aus den spielbaren Truppen aus
     * @param playableCards Aktuell spielbare Karten
     * @return Die ausgewählte Karte
     */
    Card ChooseTroop(ArrayList<Card> playableCards){
         if (playableCards.isEmpty()) return null;
        int random = (int) Math.round(Math.random() * playableCards.size());
        return playableCards.get(random);
    }
}
