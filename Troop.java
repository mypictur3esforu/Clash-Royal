import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Troop{
    Card card;
    double[] cords;
    JLabel label;
    Troop target;
    ArrayList<Troop> targetedBy = new ArrayList<>();
    Spieler affiliation;

    Troop(Card card, double x, double y, Spieler affiliation){
        this.card = card;
        cords = new double[]{x - 50, y - 50};
        this.affiliation = affiliation;
        PlaceTroop();
    }

    void PlaceTroop(){
        label = new JLabel(card.icon);
        label.setBounds((int) cords[0], (int) cords[1], 100, 100);
        UI.canvasButton.add(label);
    }

    void Move(){
        if (target != null) {
            //range iwi immer 0
            if (TargetInRange()) {
                return;
            }
            double[] distance = DistanceInDirection(target.cords);
            double relation = Math.abs(distance[0] / distance[1]);
            cords[0] += (double) card.speed / 20 * relation * (distance[0] / Math.abs(distance[0]));
            cords[1] += (double) card.speed / 20 / relation * (distance[1] / Math.abs(distance[1]));
        }else {
        cords[1] -= (double) card.speed / 10;
        }
        label.setBounds((int) cords[0], (int) cords[1], 100, 100);
    }

    double CheckCords(int cord /*0 = x, 1 = y*/){
        return cords[cord];
    }

    /**
     * Findet das Ziel, welches die Truppe verfolgen soll
     * @param fieldsTroops Array mit den Truppen die gerade auf dem Feld sind
     */
    void GetTarget(ArrayList<Troop> fieldsTroops, ArrayList<Troop> towers){
        // Vermeidet null pointer Exceptions
        if (target == null || Objects.equals(target.card.name, "tower")) {
            target = new Troop(towers.getFirst().card, 100000, 1000000,towers.getFirst().affiliation);
            target.cords = new double[]{10000, 10000};
        }else{return;}
        for (int i = 0; i < towers.size(); i++) {
            Troop tower = towers.get(i);
            //gefährlich, weil sobald i == 0 nicht erste bedingung null exception
            if (i == 0 || DistanceTo(tower.cords) < DistanceTo(target.cords)) {
                if (affiliation != tower.affiliation) {
                target = tower;
                }
            }
        }
        for (Troop fieldsTroop : fieldsTroops) {
            double distance = DistanceTo(fieldsTroop.cords);
            if (distance == 0 && Objects.equals(fieldsTroop.card.name, card.name) || fieldsTroop.affiliation == affiliation) {
                continue;
            }
            if (distance < 200) {
                target = fieldsTroop;
                fieldsTroop.targetedBy.add(this);
            }
        }


    }

           /*         try {
        //wenn Truppe näher dran dann neues Target //Useless ich will das doch anders implementieren
        if (DistanceTo(target.cords) > distance) {
            target = fieldsTroop;
        }
    } catch (Exception e) {
        target = fieldsTroop;
    }*/

    /**
     * Gibt die Distanz zu gegebenen Koordinaten an
     * @param coordinates Koordinaten mit denen Verglichen wird
     * @return Gibt den Betrag der Distanz zurück. 0 ist x-Achse, 1 ist y-Achse
     */
    double[] DistanceInDirection(double[] coordinates){
        double x = coordinates[0] - cords[0];
        double y = coordinates[1] - cords[1];
        return new double[]{x, y};
    }

    /**
     * Methode berechnet insgesamten Abstand zu Koordinaten
     * @param coordinates Koordinaten mit den verglichen wird
     * @return Gibt Distanz von Koordinaten zur Truppe zusammengerechnet zurück
     */
    double DistanceTo(double[] coordinates){
        double x = coordinates[0] - cords[0];
        double y = coordinates[1] - cords[1];
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Gibt an ob das Target bereits in Reichweite ist
     * @return true, Target in Reichweite; false, Target nicht in Reichweite
     */
    boolean TargetInRange(){
        if (target == null) return false;
        return DistanceTo(target.cords) <= card.range;
    }

    void TakeDamage(double damage){
        card.health -= damage;
    }

    void MakeDamage(){
        target.TakeDamage(card.damage);
    }

    boolean Alive(){
        return card.health > 0;
    }

}
