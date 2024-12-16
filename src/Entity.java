import javax.swing.*;
import java.util.ArrayList;

/**
 * Entities sind die "fertigen Bauwerke" die nach der "Blaupause" (Karte) gebaut wurden.
 */
public class Entity {
    /**
     * Stats des Entities
     */
    private Card card;
    /**
     * Koordinaten der Truppe
     */
    private double[] cords;
    /**
     * Essenzielle Koordinaten zum Erreichen des Ziels;
     * Koordinaten von Brücken
     */
    private double[] necCords;
    /**
     * Status der Attacke
     */
    private int attackState = 1;
    /**
     * Trefferpunkte
     */
    private double health;
    /**
     * Das Entity, welches angegriffen wird
     */
    private Entity target;
    /**
     * Das Bild der Truppe
     */
    private JLabel label;
    /**
     * Team Zugehörigkeit
     */
    private Spieler affiliation;
    /**
     * Angreifer, die dieses Entity angreifen
     */
    private ArrayList<Entity> targetedBy = new ArrayList<>();
    /**
     * Trefferpunkte Anzeige
     */
    private JProgressBar healthBar = new JProgressBar(0, 100);


    public Card GetCard(){return card;}
    public double[] GetCords(){ return cords;}
    public double[] GetNecCords(){return necCords;}
    public double GetHealth(){return health;}
    public Entity GetTarget(){return target;}
    public JLabel GetLabel(){return label;}
    public JProgressBar GetProgressBar(){return healthBar;}
    public Spieler GetAffiliation(){return affiliation;}

    public void SetLabel(JLabel label){this.label = label;}
    public void SetTarget(Entity target){this.target = target;}
    public void SetHealth(double health){this.health = health;}
    public void SetProgressVisibility(boolean visibility){healthBar.setVisible(visibility);}
    public void SetLabelsIcon(ImageIcon icon){label.setIcon(icon);}
    public void SetCords(double[] cords){this.cords = cords;}

    /**
     * Erzeugt ein neues Entity, welches in der Regel aktiv am Spiel geschehen teilnimmt (Brücken und Dummy Target bilden die Ausnahme)
     * @param card Die Stats, die die Truppe besitz
     * @param x X Koordinate
     * @param y Y Koordinate
     * @param affiliation Team Zugehörigkeit
     */
    Entity(Card card, double x, double y, Spieler affiliation){
        this.card = card;
        cords = new double[]{x, y};
        this.affiliation = affiliation;
        health = card.GetHealth();
        PlaceEntity();
    }

    /**
     * Fügt das Entity der UI hinzu
     */
    void PlaceEntity(){
        label = new JLabel(card.GetIcon());
        SetLabelToCords();
//        healthBar.setForeground(new Color(0xFF59F8C3, true));
        healthBar.setForeground(affiliation.GetColor());
        healthBar.setBounds((int) (cords[0] + 0.25 * card.GetWidth()), (int) (cords[1] - 0.05 * card.GetHeight()), 50, 15);
        if (card.GetHealth() <= 0) healthBar.setVisible(false);

        label.setVisible(true);
//        GameUI.overlayButton.add(label);
//        GameUI.overlayButton.add(healthBar);
        MainUI.gameUI.overlayButton.add(label);
        MainUI.gameUI.overlayButton.add(healthBar);
    }

    /**
     * Aktualisiert das Entity. Sucht ggf. neue Ziele raus, erhöht Attack Status und aktualisiert Lebensanzeige
     * @param troops
     * @param towers
     * @param bridges
     */
    void Update(ArrayList<Troop> troops, ArrayList<Tower> towers, ArrayList<Entity> bridges){
        Targeting(troops, towers, bridges);
//        if (TargetInRange()) MakeDamage();
        PrepareAttack();
        HealthBar();
//        if (!Alive()) KickTheBucket();
    }

    /**
     * Erzeugt ein Dummy Target, welches anvisiert wird, falls kein Ziel vorhanden ist, damit keine Null-Pointer Exceptions entstehen
     */
    void DummyTarget(){
        target =  new Entity(ClashRoyal.GetCardByName("DummyTarget"), 10000000, 1000, new Spieler("Dummy", null, null));
    }

    /**
     * Sucht ein Ziel, welches anvisiert werden soll
     * @param troops Alle Truppen auf dem Feld
     * @param towers Türme der Spieler
     * @param bridges Brücken des Spielfelds
     */
    void Targeting(ArrayList<Troop> troops, ArrayList<Tower> towers, ArrayList<Entity> bridges) {
        if (target == null) DummyTarget();
        if (TargetLocked()) return;
        GetTowerTarget(towers);
        GetTroopTarget(troops);
        GetBridgeTarget(bridges);
    }

    /**
     * Falls eine Brücke auf dem Weg zum Ziel liegt, wird diese anvisiert
     * @param bridges Die Brücken auf dem Feld
     */
    void GetBridgeTarget(ArrayList<Entity> bridges){
        double distance = 100000;
        Entity targetBridge = bridges.getFirst(); //Wert irrelevant

        for (Entity bridge : bridges) {
            if (DistanceTo(bridge.cords) < distance) {
                distance = DistanceTo(bridge.cords);
                targetBridge = bridge;
            }
        }
//        double[] nEnd = GetNearestBridgeEnd(targetBridge);
//        double[] fEnd = GetFurthestBridgeEnd(targetBridge);
        double[] nEnd = GetBridgeEnd(targetBridge, true);
        double[] fEnd = GetBridgeEnd(targetBridge,false);
        if (YCordsBetween(fEnd)) necCords = fEnd;
        else if (YCordsBetween(nEnd)){
            necCords = nEnd;
        }else necCords = null;
    }


    /**
     * Gibt die Koordinaten des näheren oder weiter entferntem Endes einer Brücke
     * @param bridge Brücke deren Enden verglichen werden sollen
     * @param nearest Näheres Ende? Bei false wird das weiter entfernte Ende genommen
     * @return Koordinaten des gesuchten Endes
     */
    double[] GetBridgeEnd(Entity bridge, boolean nearest){
        double x = bridge.cords[0], y = bridge.cords[1];
        if (nearest && target.DistanceTo(new double[]{x, bridge.cords[1]}) < target.DistanceTo(new double[]{x, bridge.cords[1] + bridge.card.GetHeight()})) y += bridge.card.GetHeight();
        if (!nearest && target.DistanceTo(new double[]{x, bridge.cords[1]}) > target.DistanceTo(new double[]{x, bridge.cords[1] + bridge.card.GetHeight()})) y += bridge.card.GetHeight();
        return new double[]{x, y};
        }

//    /**
//     * Findet das näher liegende Ende der einer Brücke
//     * @param bridge Brücke dessen Enden verglichen werden
//     * @return Koordinaten des näher liegenden Ende
//     */
//    double[] GetNearestBridgeEnd(Entity bridge){
//        double x = bridge.cords[0] , y = bridge.cords[1];
//        if (target.DistanceTo(new double[]{x, bridge.cords[1]}) > target.DistanceTo(new double[]{x, bridge.cords[1] + bridge.card.GetHeight()})) y += bridge.card.GetHeight();
//        return new double[]{x, y};
//    }
//    double[] GetFurthestBridgeEnd(Entity bridge){
//        double x = bridge.cords[0], y = bridge.cords[1];
//        if (target.DistanceTo(new double[]{x, bridge.cords[1]}) < target.DistanceTo(new double[]{x, bridge.cords[1] + bridge.card.GetHeight()})) y += bridge.card.GetHeight();
//        return new double[]{x, y};
//    }

    /**
     * Schaut, ob der gegebene Punkt zwischen der Höhe der Truppe und der Höhe des Targets ist
     * @param coordinates Koordinaten die überprüft werden
     * @return Brücke dazwischen?
     */
    boolean YCordsBetween(double[] coordinates){
        return (cords[1] < coordinates[1] && coordinates[1] < target.cords[1]) || (cords[1] > coordinates[1] && coordinates[1] > target.cords[1]);
        }

    /**
     * Wenn Truppe innerhalb des Sichtfelds, dann Truppe neues target
     * @param fieldsTroops Alle auf dem Feld befindlichen Truppen
     */
    void GetTroopTarget(ArrayList<Troop> fieldsTroops) {
        for (Troop troop : fieldsTroops) {
            if (troop.GetAffiliation() == affiliation) continue;
            double distance = DistanceTo(troop.GetCords());
            if (distance < card.GetSightDistance() && distance < DistanceTo(target.cords)) {
                NewTarget(troop);
            }
        }
    }

    /**
     * Targeted den nächstgelegen Turm des Gegners
     * @param towers Die Türme auf dem Feld
     */
    void GetTowerTarget(ArrayList<Tower> towers){
        for (Tower tower : towers){
            if (tower.GetAffiliation() == affiliation) continue;
            if (DistanceTo(tower.GetCords()) < DistanceTo(target.cords)){
                NewTarget(tower);
            }
        }
    }

    void NewTarget(Entity newTarget){
        target = newTarget;
        target.targetedBy.add(this);
    }

    /**
     * Distanz in die jeweilige Richtung
     * @param coordinates Die Koordinate zu den die Entfernung ermittelt werden soll
     * @return [Entfernung X Achse; Entfernung Y Achse]
     */
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
     * Gibt an, ob das Target bereits in Reichweite ist
     *
     * @return true, Target in Reichweite; false, Target nicht in Reichweite
     */
    boolean TargetInRange() {
        if (target == null) return false;
        return DistanceTo(target.cords) <= card.GetRange();
    }

    /**
     * Gibt an, ob das Target noch geändert werden kann bzw. ob Target in Reichweite ist
     * @return true Target fest, false Target wechselbar
     */
    boolean TargetLocked() {
        return TargetInRange();
    }

    /**
     * Gibt an, ob sich die Truppe auf dem gegeben Entity befindet
     * @param entity Entity, auf welchem die Truppe stehen könnte
     * @return Steht Truppe auf Entity?
     */
    boolean TroopOnEntity(Entity entity){
        double halfCWidth = (card.GetWidth() / 2), halfCHeight = card.GetHeight() / 2;
        return (cords[0] - halfCWidth <= entity.cords[0] && entity.cords[0] <= cords[0] + halfCWidth && (cords[1] - halfCHeight <= entity.cords[1] && entity.cords[1] <= cords[1] + halfCHeight));
    }

    /**
     * Zieht erhaltenen Schaden von den Leben ab
     * @param damage Menge an erhaltenem Schaden
     */
    void TakeDamage(double damage) {
//        System.out.println(card.name + " took Damage: " + health);
        health -= damage;
    }

//    /**
//     * Fügt dem Target Damage zu
//     */
//    void MakeDamage() {
//            target.TakeDamage(card.damage);
//            attackState = 0;
//    }

    /**
     * Führt den Angriff aus, bzw. erzeugt ein Projektil welche bei Erreichen des Ziels Schaden zufügt
     * @return Neues Projektil
     */
    Projectile Shoot(){
        return new Projectile(card.GetProjectile(), cords[0], cords[1], affiliation, target, this);
    }

    /**
     * Erhöht den Angriffsstatus
     */
    void PrepareAttack(){
        attackState++;
        if (attackState == card.GetAttackSpeed()) attackState = 0;
    }

    /**
     * Gibt an ob this gerade geschossen hat
     * @return Gerade Geschossen?
     */
    boolean HasShot(){
        return attackState == 0;
    }

    /**
     * Gibt an, ob die Truppe noch am Leben ist
     * @return Leben > 0?
     */
    boolean Alive() {
        return health > 0;
    }

    /**
     * Aktualisiert die Lebensanzeige entsprechend der aktuellen Anzahl an Trefferpunkten
     */
    void HealthBar() {
        healthBar.setValue((int) (100 / card.GetHealth() * health));
//        int width = (int)(card.health / 10);
//        healthBar.setBounds((int) (cords[0] + card.width / 2 - width / 2), (int) (cords[1] + 5) - 30, width , 15);
        healthBar.setBounds((int) (cords[0] - 25), (int) (cords[1] - (card.GetHeight() / 2) - 20), 50 , 15);
    }

    /**
     * Wenn die Truppe krepiert ist, werden Bild und HealthBar entfernt und alle Angreifer dieser Truppen erhalten ein neues Ziel
     */
    void KickTheBucket(){
        label.setVisible(false);
        healthBar.setVisible(false);
        for (Entity hunter : targetedBy){
            hunter.target = null;
            hunter.attackState = 1;
        }
    }

    /**
     * Zeichnet das Label an die richtige Stelle
     */
    void SetLabelToCords(){
        label.setBounds((int) cords[0] - (card.GetWidth() / 2), (int) cords[1] - (card.GetHeight() / 2), card.GetWidth(), card.GetHeight());
    }

}
