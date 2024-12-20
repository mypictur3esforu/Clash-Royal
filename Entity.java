import javax.swing.*;
import java.util.ArrayList;

public class Entity {
    private Card card;
    private double[] cords, necCords;
    private int attackState = 1;
    private double health;
    private Entity target;
    private JLabel label;
    private Spieler affiliation;
    private ArrayList<Entity> targetedBy = new ArrayList<>();
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

    Entity(Card card, double x, double y, Spieler affiliation){
        this.card = card;
        cords = new double[]{x, y};
        this.affiliation = affiliation;
        health = card.GetHealth();
        PlaceEntity();
    }

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
        MainUI.game.overlayButton.add(label);
        MainUI.game.overlayButton.add(healthBar);
    }

    void Update(ArrayList<Troop> troops, ArrayList<Tower> towers, ArrayList<Entity> bridges){
        Targeting(troops, towers, bridges);
//        if (TargetInRange()) MakeDamage();
        PrepareAttack();
        HealthBar();
//        if (!Alive()) KickTheBucket();
    }

    void DummyTarget(){
        target =  new Entity(ClashRoyal.GetCardByName("DummyTarget"), 10000, 100000, new Spieler("Dummy", null, null));
    }

    void Targeting(ArrayList<Troop> troops, ArrayList<Tower> towers, ArrayList<Entity> bridges) {
        if (target == null) DummyTarget();
        if (TargetLocked()) return;
        GetTowerTarget(towers);
        GetTroopTarget(troops);
        GetBridgeTarget(bridges);
    }

//    void GetBridgeTarget(ArrayList<Entity> bridges){
//        necCords = new double[]{10000, 100000};
//        for (Entity bridge : bridges){
//            if (DistanceTo(necCords) <= 20) {
//                necCords[0] = 10000;
//                break;
//            }
//            if (DistanceTo(bridge.cords) < DistanceTo(target.cords) && DistanceTo(bridge.cords) < DistanceTo(necCords)){
//                if (DistanceInDirection(target.cords)[1] < 0) {
//                necCords = new double[]{bridge.cords[0] + (double) bridge.card.width / 2, bridge.cords[1]};
//                }else {
//                necCords = new double[]{bridge.cords[0] + (double) bridge.card.width / 2, bridge.cords[1] + bridge.card.height};
//                }
//            }
//        }
//        if (necCords[0] == 10000) necCords = null;
//    }

//    erst schauen ob Brücke zwischen Truppe und Target
//    dann ob Truppe schon auf Brücke, wenn ja dann ende der Brücke als neues Target
//        void GetBridgeTarget(ArrayList<Entity> bridges){
//        necCords = new double[]{10000, 10000};
//        for (Entity bridge : bridges) {
//            if (!BridgeBetween(bridge) || DistanceTo(necCords) < DistanceTo(bridge.cords)) continue;
//            if (!TroopOnEntity(bridge)){
//                necCords = GetNearestBridgeEnd(bridge);
//             continue;
//            }
//
//        }
//        if (necCords[0] == 10000) necCords = null;
//        }

    void GetBridgeTarget(ArrayList<Entity> bridges){
        double distance = 100000;
        Entity targetBridge = bridges.getFirst(); //Wert irrelevant

        for (Entity bridge : bridges) {
            if (DistanceTo(bridge.cords) < distance) {
                distance = DistanceTo(bridge.cords);
                targetBridge = bridge;
            }
        }
        double[] nEnd = GetNearestBridgeEnd(targetBridge);
        double[] fEnd = GetFurthestBridgeEnd(targetBridge);
        if (YCordsBetween(fEnd)) necCords = fEnd;
        else if (YCordsBetween(nEnd)){
            necCords = nEnd;
        }else necCords = null;
    }

    /**
     * Findet das näher liegende Ende der einer Brücke
     * @param bridge Brücke dessen Enden verglichen werden
     * @return Koordinaten des näher liegenden Ende
     */
    double[] GetNearestBridgeEnd(Entity bridge){
        double x = bridge.cords[0] , y = bridge.cords[1];
        if (target.DistanceTo(new double[]{x, bridge.cords[1]}) > target.DistanceTo(new double[]{x, bridge.cords[1] + bridge.card.GetHeight()})) y += bridge.card.GetHeight();
        return new double[]{x, y};
    }
//    double[] GetBridgeEnd(Entity bridge, boolean nearest){
//        double x = bridge.cords[0] + (double) bridge.card.width / 2, y = bridge.cords[1];
//        boolean added = false;
//
//        if (target.DistanceTo(new double[]{x, bridge.cords[1]}) > target.DistanceTo(new double[]{x, bridge.cords[1] + bridge.card.height})) {
//            y += bridge.card.height;
//            added = true;
//        }
//
//        if (nearest == false ||(!nearest && !added)) {
//            return new double[]{x, y};
//        }else //!nearest && added
//            return new double[]{x, y - bridge.card.height};
//        }

    double[] GetFurthestBridgeEnd(Entity bridge){
        double x = bridge.cords[0], y = bridge.cords[1];
        if (target.DistanceTo(new double[]{x, bridge.cords[1]}) < target.DistanceTo(new double[]{x, bridge.cords[1] + bridge.card.GetHeight()})) y += bridge.card.GetHeight();
        return new double[]{x, y};
    }

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
     * Führt den Angriff aus, bzw erzeugt ein Projektil
     * @return Neues Projektil
     */
    Projectile Shoot(){
        return new Projectile(card.GetProjectile(), cords[0], cords[1], affiliation, target, this);
    }

    /**
     * Erhöht den Agriffsstatus
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

    boolean Alive() {
        return health > 0;
    }

    void HealthBar() {
        healthBar.setValue((int) (100 / card.GetHealth() * health));
//        int width = (int)(card.health / 10);
//        healthBar.setBounds((int) (cords[0] + card.width / 2 - width / 2), (int) (cords[1] + 5) - 30, width , 15);
        healthBar.setBounds((int) (cords[0] - 25), (int) (cords[1] - (card.GetHeight() / 2) - 20), 50 , 15);
    }

    void KickTheBucket(){
        label.setVisible(false);
        healthBar.setVisible(false);
        for (Entity hunter : targetedBy){
            hunter.target = null;
            hunter.attackState = 1;
        }
    }

    /**
     * Zeichnet das Label an die richtige Farbe
     */
    void SetLabelToCords(){
        label.setBounds((int) cords[0] - (card.GetWidth() / 2), (int) cords[1] - (card.GetHeight() / 2), card.GetWidth(), card.GetHeight());
    }

}
