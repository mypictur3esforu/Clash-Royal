import java.util.ArrayList;

public class Tower extends  Entity{

    Tower(Card card, double x, double y, Spieler affiliation) {
        super(card, x, y, affiliation);
    }

    void KickTheBucket(){
        super.KickTheBucket();
        System.out.println("Tower has fallen at: " + GetCords()[0] + " " + GetCords()[1]);
    }

    void Targeting(ArrayList<Troop> troops, ArrayList<Tower> towers, ArrayList<Entity> bridge) {
        if (GetTarget() == null) DummyTarget();
        if (TargetLocked()) return;
        super.GetTroopTarget(troops);
    }
}
