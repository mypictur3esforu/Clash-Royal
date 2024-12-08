import java.util.*;
import java.util.Timer;

public class Spielfeld {
    private ArrayList<Troop> troops = new ArrayList<>();
    private ArrayList<Tower> towers = new ArrayList<>();
    private ArrayList<Entity> units = new ArrayList<>();
    private ArrayList<Entity> bridges = new ArrayList<>();
    private Card selectedTroop;
    private int selectedButton;
    private double elixir = 1 / (double) (100/6);
    private Timer timer = new Timer();
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
//            System.out.println("Timer läuft");
            TimeShooter();
        }
    };
    private Spieler[] players;
    private GameUI gameUI;

    Spielfeld(long cooldown, Spieler[] players){
        this.players = players;
        gameUI = new GameUI(700, MainUI.screenHeight, players[1].GetCardSelection());
        MainUI.gameUI = gameUI;
        UIConnections();
        CreateBridges();
        CreateTowers();
        timer.schedule(task, cooldown, cooldown);
    }

    void UIConnections(){
        gameUI.overlayButton.addActionListener(ev->{ButtonClick();});
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
    void TimeShooter(){
        ArrayList<Entity> victims = new ArrayList<>();
        ArrayList<Entity> ready = new ArrayList<>();
            try {
        for (Entity unit : units){
                unit.Update(troops, towers, bridges);
                if (unit.HasShot() && unit.TargetLocked() &&! (unit instanceof Projectile)) ready.add(unit);
                if (!unit.Alive()) victims.add(unit);
        }

        for (Entity shooter : ready){
            units.add(shooter.Shoot());
        }

        for (int i = 1; i < players.length; i++) {
            players[i].AddElixir(elixir);
            if (players[i] instanceof Bot){
                //((Bot) players[i]).Update();
                //Entity newbies = ((Bot) players[i]).MakePlacementOrder();
                //if (newbies != null) NewTroop(newbies.card, (int) newbies.cords[0], (int) newbies.cords[1], newbies.affiliation);
            }
        }

        if (selectedTroop == null) gameUI.UpdateElixirBar(players[1].GetElixir(),0);
        else gameUI.UpdateElixirBar(players[1].GetElixir(), selectedTroop.GetElixir());

        for (Entity victim : victims){
            RemoveVictim(victim);
            victim.KickTheBucket();
            gameUI.repaint();
        }
            }catch (Exception e){
                System.out.println("ConcurrentModificationException");
            }
    }

    void RemoveVictim(Entity victim){
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
     * @param newTroop die gewählte Karte
     * @param x X Koordinate
     * @param y Y Koordinate
     */
    void NewTroop(Card newTroop, int x, int y, Spieler playerAffiliation){
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
        if (! (playerAffiliation instanceof Bot)){
        gameUI.buttons[selectedButton].NewCard(players[1].GetCardSelection().get(selectedButton)); //maybe klappt das hier auch nicht
        selectedButton = -1;
        }
    }

    int switcher;
    void ButtonClick(){
        /*Parsed Koordinaten und erzeugt eine neue Truppe an diesen*/
            System.out.println("Point: " + gameUI.overlayButton.getMousePosition());
        try{
            //int x = Integer.parseInt((game.overlayButton.getMousePosition()+"").split("x=")[1].split(",")[0]);
            //int y = Integer.parseInt((game.overlayButton.getMousePosition()+"").split("y=")[1].split("]")[0]);
            if (switcher == 2) {
                switcher = 1;
            } else {
//              normalerweise = 2
                switcher = 1;
            }
            NewTroop(selectedTroop, gameUI.overlayButton.getMousePosition().x, gameUI.overlayButton.getMousePosition().y, players[switcher]);
        }catch (Exception e){
            System.out.println("Error parsing coordinates");
        }

    }

    void SelectTroop(Card chosenTroop){
        selectedTroop = chosenTroop;
        gameUI.SetRestrictHalfVisible(true);
    }

    void CreateBridges(){
        int width = 80, height = 180;

        double[][] bridgeCords = new double[][]{{(double) MainUI.gameWidth / 3 - 2 * width, (double) MainUI.gameHeight / 2 - (double) height / 2}, { (double)  2 * MainUI.gameWidth / 3 + width, (double) MainUI.gameHeight / 2 - (double) height / 2}};

        for (double[] bridgeCord : bridgeCords) {
        Entity bridge = new Entity(ClashRoyal.GetCardByName("Bridge"), bridgeCord[0], bridgeCord[1], players[0]);
        bridges.add(bridge);
        }
    }

    void CreateTowers(){
        Spieler playerAffil = players[2];
        //double[][] towerCords = new double[][]{{75, 130}, {325, 80}, {575, 130}, {75, 930}, {325, 980}, {575, 930}};
        double[] towerXCords = new double[]{gameUI.width / 5, gameUI.width / 2, 4 * gameUI.width / 5};
        int numberOfTowers = 6;
        double tenPercentHeight = ((double) gameUI.height / 100) * 10;
        for (int i = 0; i < numberOfTowers; i++) {
                double[] towerCords = new double[]{towerXCords[2 - (i % 3)], tenPercentHeight};
            if (numberOfTowers / 2 <= i){
                playerAffil = players[1];
                towerCords[1] = gameUI.height - tenPercentHeight;
            }
            Tower tower = new Tower(ClashRoyal.GetCardByName("Tower"), towerCords[0], towerCords[1], playerAffil);
            towers.add(tower);
            units.add(tower);
        }
    }
}
