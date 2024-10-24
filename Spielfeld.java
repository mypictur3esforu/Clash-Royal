import java.util.*;
import java.util.Timer;

public class Spielfeld {
    ArrayList<Troop> troops = new ArrayList<>();
    ArrayList<Tower> towers = new ArrayList<>();
    ArrayList<Entity> units = new ArrayList<>();
    ArrayList<Entity> bridges = new ArrayList<>();
    Card selectedTroop;
    int selectedButton;
    double elixir = 0.5 / (double) (100/6);
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
//            System.out.println("Timer läuft");
            TimeShooter();
        }
    };
    Spieler[] players;
    Game game;

    Spielfeld(long cooldown, Spieler[] players){
        this.players = players;
        game = new Game(700, 1080, players[1].cardSelection);
        MainUI.game = game;
        UIConnections();
        CreateBridges();
        CreateTowers();
        timer.schedule(task, cooldown, cooldown);
    }

    void UIConnections(){
        game.overlayButton.addActionListener(ev->{ButtonClick();});
        for (int i = 0; i < game.buttons.length; i++) {
            int finalI = i;
            game.buttons[i].addActionListener(ev -> {
                SelectTroop(game.buttons[finalI].inheritedCard);
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
//            try {
        for (Entity unit : units){
//        System.out.println("Length: " + units.size());
                unit.Update(troops, towers, bridges);
//            System.out.println("Hier: " + unit.card.name + "; " + unit.attackState);
                if (unit.HasShot() && unit.TargetLocked() &&! (unit instanceof Projectile)) ready.add(unit);
                if (!unit.Alive()) victims.add(unit);
        }

        for (Entity shooter : ready){
            units.add(shooter.Shoot());
        }

        for (int i = 1; i < players.length; i++) {
            players[i].AddElixir(elixir);
        }

        for (Entity victim : victims){
            RemoveVictim(victim);
            victim.KickTheBucket();
        }
//            }catch (Exception e){
//                System.out.println("Weird Bug");
//            }
    }

    void RemoveVictim(Entity victim){
        units.remove(victim);
        if (victim instanceof Troop) {
            troops.remove(victim);
        } else if (victim instanceof Tower) {
            towers.remove(victim);
        }
        game.overlayButton.remove(victim.label);
        game.overlayButton.remove(victim.healthBar);
    }

    /**
     * Wandelt Karten in Truppen um und placed Truppen
     * @param newTroop die gewählte Karte
     * @param x X Koordinate
     * @param y Y Koordinate
     */
    void NewTroop(Card newTroop, int x, int y, Spieler playerAffiliation){
        if (newTroop == null || playerAffiliation.elixir < newTroop.elixir) return;
        playerAffiliation.SpendElixir(newTroop.elixir);
        Troop temp = new Troop(newTroop, x - (double) newTroop.width /2, y - (double) newTroop.height / 2, playerAffiliation);
        troops.add(temp);
        units.add(temp);
//        Klappt net → mies, weil sorgt für ungewollte Beziehung von MainUI und Game
//        game.map.add(units.getLast().label);
//        game.map.add(units.getLast().healthBar);
        selectedTroop = null;
        game.restrictHalf.setVisible(false);
        players[1].ActualizeSelection(selectedButton);
        game.buttons[selectedButton].NewCard(players[1].cardSelection.get(selectedButton));
        selectedButton = -1;
    }

    int switcher;
    void ButtonClick(){
//        System.out.println(GameUI.overlayButton.getMousePosition());

        /*Parsed Koordinaten und erzeugt eine neue Truppe an diesen*/
            System.out.println("Point: " + game.overlayButton.getMousePosition());
        try{
            int x = Integer.parseInt((game.overlayButton.getMousePosition()+"").split("x=")[1].split(",")[0]);
            int y = Integer.parseInt((game.overlayButton.getMousePosition()+"").split("y=")[1].split("]")[0]);
//            ClashRoyal.spiel.feld.NewTroop(new Troop(/*100, 100,*/ (int)(Math.floor(Math.random()*10)), x, y, new ImageIcon("images/SilvarroPixilart.png"),));
            if (switcher == 2) {
                switcher = 1;
            } else {
//              normalerweise = 2
                switcher = 2;
            }
            NewTroop(selectedTroop, x, y, players[switcher]);
        }catch (Exception e){
            System.out.println("Error parsing coordinates");
        }

    }

    void SelectTroop(Card chosenTroop){
        selectedTroop = chosenTroop;
        game.restrictHalf.setVisible(true);
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
        double[][] towerCords = new double[][]{{75, 130}, {325, 80}, {575, 130}, {75, 930}, {325, 980}, {575, 930}};
        for (int i = 0; i < towerCords.length; i++) {
            double[] towerCord = towerCords[i];
            if (towerCords.length / 2 <= i)playerAffil = players[1];
//            while (towerCord[1] > 700){
//                towerCord[1] -= 20;
//            }
            Tower tower = new Tower(ClashRoyal.GetCardByName("Tower"), towerCord[0], towerCord[1], playerAffil);
            towers.add(tower);
            units.add(tower);
        }
    }
}
