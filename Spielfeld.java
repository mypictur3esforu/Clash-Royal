import javax.swing.*;
import java.util.*;
import java.util.Timer;

public class Spielfeld {
    ArrayList<Troop> troops = new ArrayList<>();
    ArrayList<Tower> towers = new ArrayList<>();
    ArrayList<Entity> units = new ArrayList<>();
    ArrayList<Entity> bridges = new ArrayList<>();
    Card selectedTroop;
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
//            System.out.println("Timer läuft");
            TimeShooter();
        }
    };
    Spieler[] player;

    Spielfeld(long cooldown, Spieler[] player){
        this.player = player;
        GameUI.overlayButton.addActionListener(ev->{ButtonClick();});
        CreateBridges();
        CreateTowers();
        timer.schedule(task, cooldown, cooldown);
    }

    /**
     * Timer der das Game laufen lässt.
     * Normalerweise 60fps.
     * Lässt Truppen bewegen, targets suchen, damage machen, krepieren, beseitigen.
     */
    void TimeShooter(){
        ArrayList<Entity> victims = new ArrayList<>();
            try {
        for (Entity entity : units){
                entity.Actualize(troops, towers, bridges);
                if (!entity.Alive()) victims.add(entity);
        }
        for (Entity victim : victims){
            RemoveVictim(victim);
            victim.KickTheBucket();
        }
            }catch (Exception e){
                System.out.println("Weird Bug");
            }
    }

    void RemoveVictim(Entity victim){
        units.remove(victim);
        if (victim instanceof Troop) {
            troops.remove(victim);
        } else if (victim instanceof Tower) {
            towers.remove(victim);
        }
    }

    /**
     * Wandelt Karten in Truppen um und placed Truppen
     * @param newTroop die gewählte Karte
     * @param x X Koordinate
     * @param y Y Koordinate
     */
    void NewTroop(Card newTroop, int x, int y, Spieler playerAffiliation){
        if (newTroop == null) return;
        Troop temp = new Troop(newTroop, x, y, playerAffiliation);
        troops.add(temp);
        units.add(temp);
        //Truppe wird deklariert und eigentlich auch gespeichert, aber wenn der Timer die Truppen durchgeht ists auf einmal null //WTF?
        System.out.println(Arrays.toString(troops.getLast().cords));
        selectedTroop = null;
    }

//    void KickTheBucket(Entity victim){
//        if(Objects.equals(victim.card.name, "Tower")) RemoveTower(victim);
//        System.out.println(victim.card.name + " kicked the bucket at " + victim.cords[0] + " " + victim.cords[1]);
//        for (Entity troop : victim.targetedBy) {
//            troop.target = null;
//        }
//        RemoveTroop(victim);
//    }

//    void RemoveTower(Tower victimTower){
//        towers.remove(victimTower);
//        victimTower.label.setVisible(false);
//        victimTower.healthBar.setVisible(false);
//    }

    int switcher;
    void ButtonClick(){
        System.out.println(GameUI.overlayButton.getMousePosition());

        /*Parsed Koordinaten und erzeugt eine neue Truppe an diesen*/
        try{
            int x = Integer.parseInt((GameUI.overlayButton.getMousePosition()+"").split("x=")[1].split(",")[0]);
            int y = Integer.parseInt((GameUI.overlayButton.getMousePosition()+"").split("y=")[1].split("]")[0]);
//            ClashRoyal.spiel.feld.NewTroop(new Troop(/*100, 100,*/ (int)(Math.floor(Math.random()*10)), x, y, new ImageIcon("images/SilvarroPixilart.png"),));
            if (switcher == 2) {
                switcher = 1;
            } else {
//              normalerweise = 2
                switcher = 1;
            }
            NewTroop(selectedTroop, x, y, player[switcher]);
        }catch (Exception e){
            System.out.println("Error parsing coordinates");
        }

    }

    void RemoveTroop(Troop troop){
        troops.remove(troop);
        troop.label.setVisible(false);
        troop.healthBar.setVisible(false);
        GameUI.overlayButton.remove(troop.label);
    }

    void SelectTroop(Card chosenTroop){
        selectedTroop = chosenTroop;
    }

    void CreateBridges(){
        int width = 80, height = 180;

        double[][] bridgeCords = new double[][]{{(double) GameUI.gameWidth / 3 - 2 * width, (double) GameUI.gameHeight / 2 - (double) height / 2}, { (double)  2 * GameUI.gameWidth / 3 + width, (double) GameUI.gameHeight / 2 - (double) height / 2}};

        for (double[] bridgeCord : bridgeCords) {
        Entity bridge = new Entity(new Card("Bridge", null,0, 0, 0, 0, 0, 0, width, height), bridgeCord[0], bridgeCord[1], player[0]);
        bridges.add(bridge);
        }
    }

    void CreateTowers(){
        Spieler playerAffil = player[2];
        double[][] towerCords = new double[][]{{75, 130}, {325, 80}, {575, 130}, {75, 930}, {325, 980}, {575, 930}};
        for (int i = 0; i < towerCords.length; i++) {
            double[] towerCord = towerCords[i];
            if (towerCords.length / 2 <= i)playerAffil = player[1];
            Tower tower = new Tower(new Card("Tower", new ImageIcon("images/tower.png"), 0, 350, 500, 20, 20, 400,
                    50, 50), towerCord[0], towerCord[1], playerAffil);
            towers.add(tower);
            units.add(tower);
//            System.out.println(Arrays.toString(tower.DistanceInDirection(new double[]{560, 450})));
            System.out.println(tower.DistanceTo(new double[]{560, 450}));
//            tower.label.setBounds((int) towerCord[0], (int) towerCord[1], 100, 100);
        }
    }
}
