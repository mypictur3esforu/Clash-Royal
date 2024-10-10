import javax.swing.*;
import java.util.*;
import java.util.Timer;

public class Spielfeld {
    ArrayList<Troop> troops = new ArrayList<>();
    ArrayList<Troop> tower = new ArrayList<>();
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
        UI.canvasButton.addActionListener(ev->{ButtonClick();});
        CreateTowers();
        timer.schedule(task, cooldown, cooldown);
    }

    /**
     * Timer der das Game laufen lässt.
     * Normalerweise 60fps.
     * Lässt Truppen bewegen, targets suchen, damage machen, krepieren, beseitigen.
     */
    void TimeShooter(){
        ArrayList<Troop> bucketKicker = new ArrayList<>();
        for (Troop troop : troops) {
            troop.Move();
            troop.GetTarget(troops, tower);
            if (troop.TargetInRange()) {
                troop.MakeDamage();
                //hier schlechter
            }
            //funktioniert hier iwi besser
                if (!troop.Alive()) {
                    bucketKicker.add(troop);
                }else{
                    troop.HealthBar();
                }
        }
            for (Troop troop : bucketKicker){
                KickTheBucket(troop);
        }
            TowerActualizer();
    }

    void TowerActualizer(){
        ArrayList<Troop> bucketKicker = new ArrayList<>();
        for (Troop currentTower : tower) {
            if (!currentTower.Alive()) bucketKicker.add(currentTower);
            if (currentTower.health < 0){
                System.out.println("lol");
            }
            currentTower.GetTarget(troops, tower);
            if (currentTower.TargetInRange()){
            currentTower.MakeDamage();
            }
            currentTower.HealthBar();
        }
        for (Troop troop : bucketKicker){
            KickTheBucket(troop);
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
        troops.add(new Troop(newTroop, x, y, playerAffiliation));
        //Truppe wird deklariert und eigentlich auch gespeichert, aber wenn der Timer die Truppen durchgeht ists auf einmal null //WTF?
        System.out.println(Arrays.toString(troops.getLast().cords));
        selectedTroop = null;
    }

    void KickTheBucket(Troop victim){
        if(Objects.equals(victim.card.name, "Tower")) RemoveTower(victim);
        System.out.println(victim.card.name + " kicked the bucket at " + victim.cords[0] + " " + victim.cords[1]);;
        for (Troop troop : victim.targetedBy) {
            troop.target = null;
        }
        RemoveTroop(victim);
    }

    void RemoveTower(Troop victimTower){
        tower.remove(victimTower);
        victimTower.label.setVisible(false);
        victimTower.healthBar.setVisible(false);
    }

    int switcher;
    void ButtonClick(){
        System.out.println(UI.canvasButton.getMousePosition());

        /*Parsed Koordinaten und erzeugt eine neue Truppe an diesen*/
        try{
            int x = Integer.parseInt((UI.canvasButton.getMousePosition()+"").split("x=")[1].split(",")[0]);
            int y = Integer.parseInt((UI.canvasButton.getMousePosition()+"").split("y=")[1].split("]")[0]);
//            ClashRoyal.spiel.feld.NewTroop(new Troop(/*100, 100,*/ (int)(Math.floor(Math.random()*10)), x, y, new ImageIcon("images/SilvarroPixilart.png"),));
            if (switcher == 0) {
                switcher = 1;
            } else {
//              normalerweise = 0
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
        UI.canvasButton.remove(troop.label);
    }

    void SelectTroop(Card chosenTroop){
        selectedTroop = chosenTroop;
    }

    void CreateTowers(){
        Spieler playerAffil = player[0];
        double[][] towerCords = new double[][]{{125, 130}, {375, 80}, {625, 130}, {125, 930}, {375, 980}, {625, 930}};
        for (int i = 0; i < towerCords.length; i++) {
            double[] towerCord = towerCords[i];
            if (towerCords.length / 2 <= i)playerAffil = player[1];
            Troop tower = new Troop(new Card("Tower", new ImageIcon("images/tower.png"), 0, 400, 3500, 10, 30), towerCord[0], towerCord[1], playerAffil);
            this.tower.add(tower);
//            tower.label.setBounds((int) towerCord[0], (int) towerCord[1], 100, 100);
        }
    }
}
