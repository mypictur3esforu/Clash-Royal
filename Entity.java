import javax.swing.*;
import java.util.ArrayList;

public class Entity {
    Card card;
    double[] cords;
    int health, width, height, attackState = 0;
    Entity target;
    JLabel label;
    Spieler affiliation;
    ArrayList<Entity> targetedBy = new ArrayList<>();
    JProgressBar healthBar = new JProgressBar(0, 100);


    Entity(Card card, double x, double y, Spieler affiliation){
        this.card = card;
        cords = new double[]{x, y};
        this.affiliation = affiliation;
        card.health = health;
        PlaceEntity();
    }

    void PlaceEntity() {
        label = new JLabel(card.icon);
        label.setBounds((int) cords[0], (int) cords[1], width, height);
//        GameUI.overlayButton.add(healthBar);
//        GameUI.overlayButton.add(label);
        GameUI.gamePanel.add(label);
    }

    void Actualize(ArrayList<Troop> troops, ArrayList<Tower> towers){
        Targeting(troops, towers);
        if (TargetInRange()) MakeDamage();
        HealthBar();
    }

    void DummyTarget(){
        target =  new Entity(new Card("0", null, 0, 0, 0, 0, 0, 0, 100, 100), 10000, 100000, new Spieler("Dummy"));
    }

    void Targeting(ArrayList<Troop> troops, ArrayList<Tower> towers) {
        if (target == null) DummyTarget();
        if (TargetLocked()) return;
        GetBridgeTarget();
        GetTowerTarget(towers);
        GetTroopTarget(troops);
    }

    void GetBridgeTarget(){

    }

    /**
     * Wenn Truppe innerhalb des Sichtfelds, dann Truppe neues target
     * @param fieldsTroops Alle auf dem Feld befindlichen Truppen
     */
    void GetTroopTarget(ArrayList<Troop> fieldsTroops) {
        for (Troop troop : fieldsTroops) {
            if (troop.affiliation == affiliation) continue;
            double distance = DistanceTo(troop.cords);
            if (distance < card.sightDistance && distance < DistanceTo(target.cords)) {
                NewTarget(troop);
            }
        }
    }

    void GetTowerTarget(ArrayList<Tower> towers){
        for (Tower tower : towers){
            if (tower.affiliation == affiliation) continue;
            if (DistanceTo(tower.cords) < DistanceTo(target.cords)){
                NewTarget(tower);
            }
        }
    }

    void NewTarget(Entity newTarget){
        target = newTarget;
        target.targetedBy.add(this);
    }

    double[] DistanceInDirection(double[] coordinates) {
        double x = coordinates[0] - cords[0];
        double y = coordinates[1] - cords[1];
        return new double[]{x, y};
    }

    /**
     * Methode berechnet insgesamten Abstand zu Koordinaten
     *
     * @param coordinates Koordinaten mit den verglichen wird
     * @return Gibt Distanz von Koordinaten zur Truppe zusammengerechnet zurück
     */
    double DistanceTo(double[] coordinates) {
        double x = coordinates[0] - cords[0];
        double y = coordinates[1] - cords[1];
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Gibt an ob das Target bereits in Reichweite ist
     *
     * @return true, Target in Reichweite; false, Target nicht in Reichweite
     */
    boolean TargetInRange() {
        if (target == null) return false;
        return DistanceTo(target.cords) <= card.range;
    }

    /**
     * Gibt an, ob das Target noch geändert werden kann bzw. ob Target in Reichweite ist
     * @return true Target fest, false Target wechselbar
     */
    boolean TargetLocked() {
        return TargetInRange();
    }

    void TakeDamage(double damage) {
        health -= damage;
    }

    void MakeDamage() {
        attackState++;
        if (attackState == card.attackSpeed) {
            target.TakeDamage(card.damage);
            attackState = 0;
        }
    }

    boolean Alive() {
        return health > 0;
    }

    void HealthBar() {
        healthBar.setValue((int) (100 / card.health * health));
        healthBar.setBounds((int) (cords[0] + 0.25 * width), (int) (cords[1] - 0.05 * height), 50, 15);
    }

}
