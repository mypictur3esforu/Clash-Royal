import javax.swing.*;
import java.util.ArrayList;

public class Troop extends Entity{

    Troop(Card card, double x, double y, Spieler affiliation) {
        super(card, x, y, affiliation);
    }

    void Move() {
        if (target == null) {
            return;
        }
        //range iwi immer 0
        if (TargetInRange()) {
            return;
        }
        double[] distance = DistanceInDirection(target.cords);
        double relation = Math.abs(distance[0] / distance[1]);
        //Änderung in x und y Richtung; Als Variable, weil wird sonst zu schnell
        double xChange = (double) card.speed / 20 * relation * (distance[0] / Math.abs(distance[0]));
        double yChange = (double) card.speed / 20 / relation * (distance[1] / Math.abs(distance[1]));
        double normedSpeed = (double) card.speed / 10;

        if (xChange > normedSpeed || xChange < -normedSpeed)
            xChange = (double) card.speed / 10 * (distance[0] / Math.abs(distance[0]) * 1.25);
        if (yChange > normedSpeed || yChange < -normedSpeed)
            yChange = (double) card.speed / 10 * (distance[1] / Math.abs(distance[1]) * 1.25);

        cords[0] += xChange;
        cords[1] += yChange;
        label.setBounds((int) cords[0], (int) cords[1], width, height);
    }

    void Actualize(ArrayList<Troop> troops, ArrayList<Tower> towers) {
        super.Actualize(troops, towers);
        Move();
    }
}
