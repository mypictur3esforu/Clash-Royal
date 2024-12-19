import java.util.ArrayList;

/**
 * Die Klasse Troop sind Entities, die sich zum Teil bewegen und ein Projektile casten können, welches bei Erreichen des Ziels diesem Schaden zufügt
 */
public class Troop extends Entity{

    /**
     * Erzeugt eine Truppe
     * @param card Karte mit allen Stats
     * @param x X Koordinate
     * @param y Y Koordinate
     * @param affiliation Zugehörigkeit zum Spieler/Zu wessen Team gehört die Karte
     */
    Troop(Card card, double x, double y, Spieler affiliation) {
        super(card, x, y, affiliation);
    }

    /**
     * Die Truppe bewegt sich in Richtung ihres Ziels.
     */
    private void Move() {
        if (TargetInRange()) {
            return;
        }
        double[] targetedCords;
        if (GetNecCords() != null){
            targetedCords = GetNecCords();
        }else {
            targetedCords = GetTarget().GetCords();
        }
        double[] distance = DistanceInDirection(targetedCords);
        double relation = Math.abs(distance[0] / distance[1]);
        //Änderung in x und y Richtung
        double xChange = (double) GetCard().GetSpeed() / 20 * relation * (distance[0] / Math.abs(distance[0]));
        double yChange = (double) GetCard().GetSpeed() / 20 / relation * (distance[1] / Math.abs(distance[1]));
        double normedSpeed = (double) GetCard().GetSpeed() / 10;

        if (xChange > normedSpeed || xChange < -normedSpeed)
            xChange = (double) GetCard().GetSpeed() / 10 * (distance[0] / Math.abs(distance[0]) * 1.25);
        if (yChange > normedSpeed || yChange < -normedSpeed)
            yChange = (double) GetCard().GetSpeed() / 10 * (distance[1] / Math.abs(distance[1]) * 1.25);

        double[] cords = new double[]{GetCords()[0], GetCords()[1]};
        cords[0] += xChange;
        cords[1] += yChange;
        SetCords(cords);
        SetLabelToCords();
    }

    /**
     * Wird bei jedem Timer Durchgang aufgerufen und aktualisiert alles, was sich geändert haben könnte
     * @param troops Alle Truppen auf dem Feld
     * @param towers Alle Türme auf dem Feld
     * @param bridges Alle Brücken auf dem Feld
     */
    public void Update(ArrayList<Troop> troops, ArrayList<Tower> towers, ArrayList<Entity> bridges) {
        super.Update(troops, towers, bridges);
        Move();
    }

    /**
     * Entfernt die Truppen die keine Leben mehr haben
     */
    public void KickTheBucket(){
        super.KickTheBucket();
        System.out.println(GetCard().GetName() + " kicked the bucket at " + GetCords()[0] + " " + GetCords()[1]);
    }
}