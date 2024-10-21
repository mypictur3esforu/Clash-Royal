import java.util.ArrayList;

public class Tower extends  Entity{

    Tower(Card card, double x, double y, Spieler affiliation) {
        super(card, x, y, affiliation);
    }

    void KickTheBucket(){
        super.KickTheBucket();
        System.out.println("Tower has fallen at: " + cords[0] + " " + cords[1]);
    }

    void Targeting(ArrayList<Troop> troops, ArrayList<Tower> towers, ArrayList<Entity> bridge) {
        if (target == null) DummyTarget();
        if (TargetLocked()) return;
        super.GetTroopTarget(troops);
    }
}
