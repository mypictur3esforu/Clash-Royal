import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Projectile extends Entity {
    Entity caster;
    double damage;
    double[] middleOfTarget;

    Projectile(Card card, double x, double y, Spieler affiliation, Entity target, Entity caster){
        super(card, x + (double) caster.card.width / 2, y + (double) caster.card.width / 2, affiliation);
        this.caster = caster;
        this.target = target;
        damage = caster.card.damage;
        healthBar.setVisible(false);
    }

    void TargetHit(){
//        Mitte des Targets, sonst fliegen die Projektile zur oberen Ecke des Targets
        if (DistanceTo(middleOfTarget) < card.range) {
            MakeDamage();
            health = 0;
        }
    }
    /**
     * Bewegung des Targets pro Timer Schritt
     */
    void Move(){
//        System.out.println("Name: " + target.card.name + " X: " + target.cords[0] + " Y: " + target.cords[1]);
        //tangens für winkel, speed ist hypo
//        double a = Math.toDegrees(Math.tanh(DistanceInDirection(middleOfTarget)[1] / DistanceInDirection(target.cords)[0]));
//        double xChange = Math.cos(a) * card.speed;
//        double yChange = Math.sin(a) * card.speed;
        double[] distance = DistanceInDirection(middleOfTarget);
        double relation = Math.abs(distance[0] / distance[1]);
        //Änderung in x und y Richtung; Als Variable, weil wird sonst zu schnell
        double xChange = (double) card.speed / 20 * relation * (distance[0] / Math.abs(distance[0]));
        double yChange = (double) card.speed / 20 / relation * (distance[1] / Math.abs(distance[1]));
        cords[0] += xChange;
        cords[1] += yChange;
    }

    void Update(ArrayList<Troop> troops, ArrayList<Tower> towers, ArrayList<Entity> bridges){
        middleOfTarget = new double[]{target.cords[0] + (double) caster.card.width / 2, target.cords[1] + (double) caster.card.height / 2};
        System.out.println("Target Cords: " + Arrays.toString(middleOfTarget));
        Move();
        label.setBounds((int) cords[0] - card.width / 2, (int) cords[1] - card.width / 2, card.width, card.height);
        TargetHit();
    }

    void MakeDamage() {
        target.TakeDamage(damage);
    }
}
