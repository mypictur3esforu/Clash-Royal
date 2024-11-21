import java.awt.*;
import java.util.ArrayList;

public class Bot extends Spieler{

    Bot(String name, ArrayList<Card> team, Color color){
        super(name,team, color);
    }

    void Update(){
        ShouldPlaceTroop();
    }

    /**
     * Lässt alle Truppen finden, die gerade setzbar sind (genug Elex und auswählbar) und lässt, wenn es welche gibt placen.
     */
     Card ShouldPlaceTroop(){
        ArrayList<Card> playableTroops = GetPlayableTroops();
        if (playableTroops == null) return null;
        return ChooseTroop(GetPlayableTroops());
    }

    Entity MakePlacementOrder(){
         Card order = ShouldPlaceTroop();
         if (order == null) return null;
        return new Entity(order, 100, 100, this);
    }

    ArrayList<Card> GetPlayableTroops(){
        ArrayList<Card> playableTroops = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            if (GetCardSelection().get(i).GetElixir() <= GetElixir()) playableTroops.add(GetCardSelection().get(i));
        }
        return playableTroops;
    }

    Card ChooseTroop(ArrayList<Card> playableCards){
         if (playableCards.isEmpty()) return null;
        int random = (int) Math.round(Math.random() * playableCards.size());
        if (random == playableCards.size()) random--;
        return playableCards.get(random);
    }
}
