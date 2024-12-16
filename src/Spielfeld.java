import java.util.*;
import java.util.Timer;

/**
 * In der Klasse Spielfeld findet das ganze Spielgeschehen statt. Sie ist das Spielfeld, sie weiß welche Truppen, Türme, usw. sich gerade auf dem Feld befinden und sie
 * über sie läuft alles, was etwas am Spiel verändert wie z.B. Truppen hinzufügen, Truppen entfernen, Elixir erhöhen u.ä.
 */
public class Spielfeld {
    /**
     * Speichert alle Truppen auf dem Feld. Türme die von einem Spieler platziert werden gelten als Truppen
     */
    private ArrayList<Troop> troops = new ArrayList<>();
    /**
     * Speichert alle Türme auf dem Feld.
     */
    private ArrayList<Tower> towers = new ArrayList<>();
    /**
     * Speichert alles, was sich auf dem Feld befindet und aktualisiert werden muss (Truppen, Brücken, Projektile; keine Brücken)
     */
    private ArrayList<Entity> units = new ArrayList<>();
    /**
     * Speichert die Brücken auf dem Feld
     */
    private ArrayList<Entity> bridges = new ArrayList<>();
    /**
     * Speichert welche bzw. ob eine Karte ausgewählt wurde
     */
    private Card selectedTroop;
    /**
     * Speichert die Nummer des Buttons, der angeklickt würde (Nur Buttons für Kartenauswahl).
     */
    private int selectedButton;
    /**
     * Menge an Elixir, die ein Spieler bei jedem Timer Durchlauf erhält
     */
    private double elixir = 1 / (double) (100 / 6);
    /**
     * Der Timer, der das Spiel aktualisiert
     */
    private Timer timer = new Timer();
    /**
     * Die Aufgabe, die der Timer erledigen soll bzw. die Methode, die ausgeführt werden soll
     */
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
//            System.out.println("Timer läuft");
            TimeShooter();
        }
    };
    /**
     * Die Spieler, die das Spiel spielen und der obligatorische Spieler 0
     */
    private Spieler[] players;
    /**
     * Die UI des Spielfelds
     */
    private GameUI gameUI;

    /**
     * Erzeugt ein Spielfeld
     * @param cooldown Die Zeit zwischen Timer Durchläufen
     * @param players Die Spieler in diesem Spiel + der System-Spieler
     */
    Spielfeld(int cooldown, Spieler[] players) {
        this.players = players;
        gameUI = new GameUI(700, MainUI.screenHeight, players[1].GetCardSelection());
        MainUI.gameUI = gameUI;
        UIConnections();
        CreateBridges();
        CreateTowers();
        timer.schedule(task, cooldown, cooldown);
    }

    /**
     * Gibt den UI Komponenten ActionListener mit der zugehörigen Methode. Wird in der Klasse gemacht, um so Geheimnisprinzip besser umsetzen zu können.
     */
    private void UIConnections() {
        gameUI.overlayButton.addActionListener(ev -> {
            ButtonClick();
        });
        for (int i = 0; i < gameUI.buttons.length; i++) {
            int finalI = i;
            gameUI.buttons[i].addActionListener(ev -> {
                SelectTroop(gameUI.buttons[finalI].GetInheritedCard());
//                players[1].ActualizeSelection(finalI);
//                game.buttons[finalI].NewCard(players[1].cardSelection.get(finalI));
                selectedButton = finalI;
            });
        }
    }

    /**
     * Timer der das Game laufen lässt.
     * Normalerweise 60fps.
     * Lässt Truppen bewegen, targets suchen, damage machen, krepieren, beseitigen.
     */
    private void TimeShooter() {
        ArrayList<Entity> victims = new ArrayList<>();
        ArrayList<Entity> ready = new ArrayList<>();
        try {
            for (Entity unit : units) {
                unit.Update(troops, towers, bridges);
                if (unit.HasShot() && unit.TargetLocked() && !(unit instanceof Projectile)) ready.add(unit);
                if (!unit.Alive()) victims.add(unit);
            }

            for (Entity shooter : ready) {
                units.add(shooter.Shoot());
            }


            players[2].AddElixir(elixir);
            if (players[2] instanceof Bot) {
                Entity newbies = ((Bot) players[2]).Update();
                if (newbies != null)
                    NewTroop(newbies.GetCard(), (int) newbies.GetCords()[0], (int) newbies.GetCords()[1], newbies.GetAffiliation());
            }

            if (selectedTroop == null) gameUI.UpdateElixirBar(players[1].GetElixir(), 0);
            else gameUI.UpdateElixirBar(players[1].GetElixir(), selectedTroop.GetElixir());

            for (Entity victim : victims) {
                RemoveVictim(victim);
                victim.KickTheBucket();
                gameUI.repaint();
            }
        } catch (Exception e) {
            System.out.println("ConcurrentModificationException");
        }
    }

    /**
     * Entfernt Truppen die keine Leben mehr haben.
     *
     * @param victim Das Entity das krepiert ist.
     */
    private void RemoveVictim(Entity victim) {
        units.remove(victim);
        if (victim instanceof Troop) {
            troops.remove(victim);
        } else if (victim instanceof Tower) {
            towers.remove(victim);
        }
        gameUI.overlayButton.remove(victim.GetLabel()); //maybe klappt das hier net
        gameUI.overlayButton.remove(victim.GetProgressBar());
    }

    /**
     * Wandelt Karten in Truppen um und placed Truppen
     *
     * @param newTroop die gewählte Karte
     * @param x        X Koordinate
     * @param y        Y Koordinate
     */
    private void NewTroop(Card newTroop, int x, int y, Spieler playerAffiliation) {
        if (newTroop == null || playerAffiliation.GetElixir() < newTroop.GetElixir()) return;
        playerAffiliation.SpendElixir(newTroop.GetElixir());
        //Troop temp = new Troop(newTroop, x - (double) newTroop.width /2, y - (double) newTroop.height / 2, playerAffiliation);
        Troop temp = new Troop(newTroop, x, y, playerAffiliation);
        troops.add(temp);
        units.add(temp);
//        Klappt net → mies, weil sorgt für ungewollte Beziehung von MainUI und Game
//        game.map.add(units.getLast().label);
//        game.map.add(units.getLast().healthBar);
        selectedTroop = null;
        gameUI.SetRestrictHalfVisible(false);
        playerAffiliation.ActualizeSelection(newTroop);
        if (!(playerAffiliation instanceof Bot)) {
            gameUI.buttons[selectedButton].NewCard(players[1].GetCardSelection().get(selectedButton)); //maybe klappt das hier auch nicht
            selectedButton = -1;
        }
    }

    /**
     * Wechselt die Spieler, für Testzwecke
     */
    int switcher;

    /**
     * Bei Klick auf das Spielfeld wird versucht diese Truppe zu platzieren
     */
    private void ButtonClick() {
        /*Parsed Koordinaten und erzeugt eine neue Truppe an diesen*/
        System.out.println("Point: " + gameUI.overlayButton.getMousePosition());
        try {
            //int x = Integer.parseInt((game.overlayButton.getMousePosition()+"").split("x=")[1].split(",")[0]);
            //int y = Integer.parseInt((game.overlayButton.getMousePosition()+"").split("y=")[1].split("]")[0]);
            if (switcher == 2) {
                switcher = 1;
            } else {
//              normalerweise = 2
                switcher = 1;
            }
            NewTroop(selectedTroop, gameUI.overlayButton.getMousePosition().x, gameUI.overlayButton.getMousePosition().y, players[switcher]);
        } catch (Exception e) {
            System.out.println("Error parsing coordinates");
        }

    }

    /**
     * Bei Klick auf eine Karte wird die Karte ausgewählt
     *
     * @param chosenTroop Die Karte die ausgewählt worden ist
     */
    private void SelectTroop(Card chosenTroop) {
        selectedTroop = chosenTroop;
        gameUI.SetRestrictHalfVisible(true);
    }

    /**
     * Erzeugt Brücken für das Spiel
     */
    private void CreateBridges() {
        int width = 80, height = 180;

        double[][] bridgeCords = new double[][]{{(double) MainUI.gameWidth / 3 - 2 * width, (double) MainUI.gameHeight / 2 - (double) height / 2}, {(double) 2 * MainUI.gameWidth / 3 + width, (double) MainUI.gameHeight / 2 - (double) height / 2}};

        for (double[] bridgeCord : bridgeCords) {
            Entity bridge = new Entity(ClashRoyal.GetCardByName("Bridge"), bridgeCord[0], bridgeCord[1], players[0]);
            bridges.add(bridge);
        }
    }

    /**
     * Erzeugt die Türme für beide Spieler in diesem Spiel
     */
    private void CreateTowers() {
        Spieler playerAffil = players[2];
        //double[][] towerCords = new double[][]{{75, 130}, {325, 80}, {575, 130}, {75, 930}, {325, 980}, {575, 930}};
        double[] towerXCords = new double[]{gameUI.width / 5, gameUI.width / 2, 4 * gameUI.width / 5};
        int numberOfTowers = 6;
        double tenPercentHeight = ((double) gameUI.height / 100) * 10;
        for (int i = 0; i < numberOfTowers; i++) {
            double[] towerCords = new double[]{towerXCords[2 - (i % 3)], tenPercentHeight};
            if (numberOfTowers / 2 <= i) {
                playerAffil = players[1];
                towerCords[1] = gameUI.height - tenPercentHeight;
            }
            Tower tower = new Tower(ClashRoyal.GetCardByName("Tower"), towerCords[0], towerCords[1], playerAffil);
            towers.add(tower);
            units.add(tower);
        }
    }
}
