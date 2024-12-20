import java.awt.*;
import java.util.ArrayList;

public class Spieler {
    private String name;
    private ArrayList<Card> cardSelection = new ArrayList<>();
    private Color color;
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

    public void SetElixir(double elixir){
        this.elixir = elixir;
    }

    Spieler(String name, ArrayList<Card> cardSelection, Color color){
        this.name = name;
//        this.cardSelection = cardSelection;
        if (cardSelection == null) return;
        for (int i = 0; i < 8; i++) {
            this.cardSelection.add(cardSelection.get(i % cardSelection.size()));
        }
        this.color = color;
    }

    void ActualizeSelection(Card placedCard){
        int place = cardSelection.indexOf(placedCard);
        cardSelection.set(place, cardSelection.get(4));
        cardSelection.add(cardSelection.get(4));
        cardSelection.remove(4);
    }

    void AddElixir(double amount){
        if (ElixirFull()) return;
        elixir += amount;
    }

    void SpendElixir(double amount){
        elixir -= amount;
    }

    boolean ElixirFull(){
        return elixir >= 10;
    }
}
