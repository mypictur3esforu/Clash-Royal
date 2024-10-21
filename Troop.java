import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Troop extends Entity{

    Troop(Card card, double x, double y, Spieler affiliation) {
        super(card, x, y, affiliation);
    }

    void Move() {
//        if (target == null) {
//            return;
//        }
        if (TargetInRange()) {
            return;
        }
        double[] targetedCords;
        if (necCords != null){
            targetedCords = necCords;
        }else {
            targetedCords = target.cords;
        }
        double[] distance = DistanceInDirection(targetedCords);
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
        label.setBounds((int) cords[0], (int) cords[1], card.width, card.height);
    }

    void Actualize(ArrayList<Troop> troops, ArrayList<Tower> towers, ArrayList<Entity> bridges) {
        super.Actualize(troops, towers, bridges);
        Move();
    }

    void KickTheBucket(){
        super.KickTheBucket();
        System.out.println(card.name + " kicked the bucket at " + cords[0] + " " + cords[1]);
    }


}
