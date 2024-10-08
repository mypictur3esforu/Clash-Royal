import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

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

    void TimeShooter(){
        ArrayList<Troop> bucketKicker = new ArrayList<>();
        for (int i = 0; i < troops.size(); i++) {
//            if (troops.get(i).card.health < -2) {
//                System.out.println("Frechheit");
//            }
            Troop troop = troops.get(i);
            troop.Move();
            if (troop.CheckCords(1) < 0) {
                troop.cords[1] = 500;
            }
            troop.GetTarget(troops, tower);
            if (troop.TargetInRange()) {
                troop.MakeDamage();
            }
            //funktioniert hier iwi besser
                if (!troop.Alive()) {
                    bucketKicker.add(troop);
                }
        }
//        if (!troops.isEmpty()) {
//        if (troops.getFirst().card.health < -2 || troops.getLast().card.health < -2) {
//            System.out.println("Frechheit");
//        }
//        }
            for (Troop troop : bucketKicker){
                KickTheBucket(troop);
        }
//        UI.canvasButton.repaint();
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
        System.out.println(victim.card.name + " kicked the bucket at " + victim.cords[0] + " " + victim.cords[1]);;
        for (Troop troop : victim.targetedBy) {
            troop.target = null;
        }
        RemoveTroop(victim);
        victim.card.health = 100;
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
                switcher = 0;
            }
            NewTroop(selectedTroop, x, y, player[switcher]);
        }catch (Exception e){
            System.out.println("Error parsing coordinates");
        }

    }

    void RemoveTroop(Troop troop){
        troops.remove(troop);
        troop.label.setVisible(false);
        UI.canvasButton.remove(troop.label);
    }

    void SelectTroop(Card chosenTroop){
        selectedTroop = chosenTroop;
    }

    void CreateTowers(){
        Spieler playerAffil = player[0];
        double[][] towerCords = new double[][]{{75, 130}, {325, 80}, {575, 130}, {75, 930}, {325, 980}, {575, 930}};
        for (int i = 0; i < towerCords.length; i++) {
            double[] towerCord = towerCords[i];
            if (towerCords.length / 2 <= i)playerAffil = player[1];
            Troop tower = new Troop(new Card("Tower", new ImageIcon("images/tower.png"), 0, 400, 3500, 30), towerCord[0], towerCord[1], playerAffil);
            this.tower.add(tower);
            tower.label.setBounds((int) towerCord[0], (int) towerCord[1], 50, 50);
        }
    }
}
