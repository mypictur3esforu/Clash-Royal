import java.awt.*;
import java.util.ArrayList;

/**
 * Spieler sind die, die am Spielgeschehen durch Truppen platzieren Einfluss am Spielgeschehen nehmen können
 */
public class Spieler {
    /**
     * Name des Spielers
     */
    private String name;
    /**
     * Karten des Spielers im Spiel
     */
    private ArrayList<Card> cardSelection = new ArrayList<>();
    /**
     * Farbe der Karten des Spielers
     */
    private Color color;
    /**
     * Elixir
     */
    private double elixir = 0;

    public ArrayList<Card> GetCardSelection(){
        return cardSelection;
    }

    public Color GetColor(){
        return color;
    }

    public double GetElixir(){
        return elixir;
    }

    /**
     * Erstellt einen Spieler der Truppen aus seinem Repertoire auf dem Feld platzieren kann
     * @param name Name des Spielers
     * @param cardSelection Karten die im Spiel verfügbar sind
     * @param color Farbe der Karten des Spielers
     */
    Spieler(String name, ArrayList<Card> cardSelection, Color color){
        this.name = name;
//        this.cardSelection = cardSelection;
        if (cardSelection == null) return;
        for (int i = 0; i < 8; i++) {
            this.cardSelection.add(cardSelection.get(i % cardSelection.size()));
        }
        this.color = color;
    }

    /**
     * Aktualisiert die Karten, die ein Spieler setzen kann
     * @param placedCard
     */
    public void ActualizeSelection(Card placedCard){
        int place = cardSelection.indexOf(placedCard);
        cardSelection.set(place, cardSelection.get(4));
        cardSelection.add(cardSelection.get(4));
        cardSelection.remove(4);
    }

    /**
     * Fügt dem Spieler Elixier hinzu
     * @param amount Menge an hinzugefügtem Elixier
     */
    public void AddElixir(double amount){
        if (!ElixirFull()) elixir += amount;
    }

    /**
     * Subtrahiert Elixier von dem, das der Spieler hat
     * @param amount Menge ab abgezogenem Elixier
     */
    public void SpendElixir(double amount){
        elixir -= amount;
    }

    /**
     * Gibt an, ob das Elixier voll ist
     * @return Elixier >= 10?
     */
    private boolean ElixirFull(){
        return elixir >= 10;
    }
}
