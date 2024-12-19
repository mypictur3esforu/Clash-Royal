import java.util.ArrayList;

/**
 * Türme sind Entities, die sich nicht bewegen, jedoch in ihrem Radius Schaden anrichten können
 */
public class Tower extends  Entity{

    /**
     * Erzeugt einen neuen Turm
     * @param card Karte des Turms
     * @param x X-Koordinate
     * @param y Y-Koordinate
     * @param affiliation Spieler Zugehörigkeit
     */
    Tower(Card card, double x, double y, Spieler affiliation) {
        super(card, x, y, affiliation);
    }

    /**
     * Entfernt den Turm vom Feld
     */
    void KickTheBucket(){
        super.KickTheBucket();
        System.out.println("Tower has fallen at: " + GetCords()[0] + " " + GetCords()[1]);
    }

    /**
     * Sucht nach neuen Targets
     * @param troops Alle Truppen auf dem Feld
     * @param towers Türme der Spieler
     * @param bridge Brücken des Spielfelds
     */
    void Targeting(ArrayList<Troop> troops, ArrayList<Tower> towers, ArrayList<Entity> bridge) {
        if (GetTarget() == null) DummyTarget();
        if (TargetLocked()) return;
        super.GetTroopTarget(troops);
    }
}
