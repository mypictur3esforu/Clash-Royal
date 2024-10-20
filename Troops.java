//import javax.swing.*;
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.Objects;
//
//public class Troops {
//    Card card;
////    Troop target;
//    Spieler affiliation;
//
////    double[] cords;
//    int attackState = 0, height, width;
//    double health;
//
//    ArrayList<Troops> targetedBy = new ArrayList<>();
//
//    JLabel label;
//    JProgressBar healthBar = new JProgressBar(0, 100);
//
//    Troops(Card card, double x, double y, Spieler affiliation) {
//        super(x, y,);
//        healthBar.setForeground(new Color(0xFF59F8C3, true));
//        this.card = card;
//        health = card.health;
//        height = 100;
//        width = 100;
//        cords = new double[]{x - 50, y - 50};
//        this.affiliation = affiliation;
//        PlaceEntity();
//    }
//
////    void PlaceTroop() {
////        label = new JLabel(card.icon);
////        label.setBounds((int) cords[0], (int) cords[1], width, height);
////        GameUI.overlayButton.add(healthBar);
////        GameUI.overlayButton.add(label);
////    }
//
//    /**
//     * Bewegt Truppen abhängig von der Entfernung zum Tower.
//     * Berechnet Relation zwischen x und y Kord und passt die Kords passend zur Relation an.
//     * Max speed ist speed / 10 · 1.25;
//     */
//    void Move() {
//        if (target == null) {
//            return;
//        }
//        //range iwi immer 0
//        if (TargetInRange()) {
//            return;
//        }
//        double[] distance = DistanceInDirection(target.cords);
//        double relation = Math.abs(distance[0] / distance[1]);
//        //Änderung in x und y Richtung; Als Variable, weil wird sonst zu schnell
//        double xChange = (double) card.speed / 20 * relation * (distance[0] / Math.abs(distance[0]));
//        double yChange = (double) card.speed / 20 / relation * (distance[1] / Math.abs(distance[1]));
//        double normedSpeed = (double) card.speed / 10;
//
//        if (xChange > normedSpeed || xChange < -normedSpeed)
//            xChange = (double) card.speed / 10 * (distance[0] / Math.abs(distance[0]) * 1.25);
//        if (yChange > normedSpeed || yChange < -normedSpeed)
//            yChange = (double) card.speed / 10 * (distance[1] / Math.abs(distance[1]) * 1.25);
//
//        cords[0] += xChange;
//        cords[1] += yChange;
//        label.setBounds((int) cords[0], (int) cords[1], width, height);
//    }
//
//    void DummyTarget(){
//       target =  new Troops(new Card("0", null, 0, 0, 0, 0, 0, 0), 10000, 100000, new Spieler("Dummy"));
//    }
//
//    void Targeting(ArrayList<Troops> troops, ArrayList<Troops> towers) {
//        if (target == null) DummyTarget();
//        if (TargetLocked()) return;
//        GetBridgeTarget();
//        GetTowerTarget(towers);
//        GetTroopTarget(troops);
//    }
//
//    void GetBridgeTarget(){
//
//    }
//
//    /**
//     * Wenn Truppe innerhalb des Sichtfelds, dann Truppe neues target
//     * @param fieldsTroops Alle auf dem Feld befindlichen Truppen
//     */
//    void GetTroopTarget(ArrayList<Troops> fieldsTroops) {
//        for (Troops troop : fieldsTroops) {
//            if (troop.affiliation == affiliation) continue;
//            double distance = DistanceTo(troop.cords);
//            if (distance < card.sightDistance && distance < DistanceTo(target.cords)) {
//                NewTarget(troop);
//            }
//        }
//    }
//
//    void GetTowerTarget(ArrayList<Troops> towers){
//        for (Troops tower : towers){
//            if (tower.affiliation == affiliation) continue;
//            if (DistanceTo(tower.cords) < DistanceTo(target.cords)){
//                NewTarget(tower);
//            }
//        }
//    }
//
//    void NewTarget(Troops newTarget){
//        target = newTarget;
//        target.targetedBy.add(this);
//    }
//
//    /**
//     * Findet das Ziel, welches die Truppe verfolgen soll
//     *
//     * @param fieldsTroops Array mit den Truppen die gerade auf dem Feld sind
//     */
//    void GetTarget(ArrayList<Troops> fieldsTroops, ArrayList<Troops> towers) {
//        // Vermeidet null pointer Exceptions
//        if (target == null || Objects.equals(target.card.name, "Tower") && !TargetInRange()) {
//            target = new Troops(towers.getFirst().card, 100000, 1000000, towers.getFirst().affiliation);
//            target.cords = new double[]{10000, 10000};
//        } else {
//            return;
//        }
//        for (int i = 0; i < towers.size(); i++) {
//            Troops tower = towers.get(i);
//            //gefährlich, weil sobald i == 0 nicht erste bedingung null exception
//            if (i == 0 || DistanceTo(tower.cords) < DistanceTo(target.cords)) {
//                if (affiliation != tower.affiliation) {
//                    target = tower;
//                }
//            }
//        }
//        for (Troops fieldsTroop : fieldsTroops) {
//            double distance = DistanceTo(fieldsTroop.cords);
//            if (distance == 0 && Objects.equals(fieldsTroop.card.name, card.name) || fieldsTroop.affiliation == affiliation) {
//                continue;
//            }
//            if (distance < card.sightDistance) {
//                target = fieldsTroop;
//                fieldsTroop.targetedBy.add(this);
//            }
//        }
//    }
//
//           /*         try {
//        //wenn Truppe näher dran dann neues Target //Useless ich will das doch anders implementieren
//        if (DistanceTo(target.cords) > distance) {
//            target = fieldsTroop;
//        }
//    } catch (Exception e) {
//        target = fieldsTroop;
//    }*/
//
//    /**
//     * Gibt die Distanz zu gegebenen Koordinaten an
//     *
//     * @param coordinates Koordinaten mit denen Verglichen wird
//     * @return Gibt den Betrag der Distanz zurück. 0 ist x-Achse, 1 ist y-Achse
//     */
//    double[] DistanceInDirection(double[] coordinates) {
//        double x = coordinates[0] - cords[0];
//        double y = coordinates[1] - cords[1];
//        return new double[]{x, y};
//    }
//
//    /**
//     * Methode berechnet insgesamten Abstand zu Koordinaten
//     *
//     * @param coordinates Koordinaten mit den verglichen wird
//     * @return Gibt Distanz von Koordinaten zur Truppe zusammengerechnet zurück
//     */
//    double DistanceTo(double[] coordinates) {
//        double x = coordinates[0] - cords[0];
//        double y = coordinates[1] - cords[1];
//        return Math.sqrt(x * x + y * y);
//    }
//
//    /**
//     * Gibt an ob das Target bereits in Reichweite ist
//     *
//     * @return true, Target in Reichweite; false, Target nicht in Reichweite
//     */
//    boolean TargetInRange() {
//        if (target == null) return false;
//        return DistanceTo(target.cords) <= card.range;
//    }
//
//    /**
//     * Gibt an, ob das Target noch geändert werden kann bzw. ob Target in Reichweite ist
//     * @return true Target fest, false Target wechselbar
//     */
//    boolean TargetLocked() {
//        return TargetInRange();
//    }
//
//    void TakeDamage(double damage) {
//        health -= damage;
//    }
//
//    void MakeDamage() {
//        attackState++;
//        if (attackState == card.attackSpeed) {
//            target.TakeDamage(card.damage);
//            attackState = 0;
//        }
//    }
//
//    boolean Alive() {
//        return health > 0;
//    }
//
//    void HealthBar() {
//        healthBar.setValue((int) (100 / card.health * health));
//        healthBar.setBounds((int) (cords[0] + 0.25 * width), (int) (cords[1] - 0.05 * height), 50, 15);
//    }
//
//}
