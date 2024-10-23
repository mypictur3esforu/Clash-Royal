import java.awt.*;
import java.util.ArrayList;

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
        //a-tangens(GK / AK) = winkel, speed ist hypo → Strahlensatz :)
        double[] distance = DistanceInDirection(middleOfTarget);
        double a = Math.toDegrees(Math.atan(distance[1] / distance[0]));
        double xChange = Math.cos(a) * card.speed / 10;
        double yChange = Math.sin(a) * card.speed / 10;
        cords[0] += xChange;
        cords[1] += yChange;
    }

    void Update(ArrayList<Troop> troops, ArrayList<Tower> towers, ArrayList<Entity> bridges){
        middleOfTarget = new double[]{target.cords[0] + (double) target.card.width / 2, target.cords[1] + (double) target.card.height / 2};
        Move();
        Rotate();
        label.setBounds((int) cords[0] - card.width / 2, (int) cords[1] - card.width / 2, card.width, card.height);
        TargetHit();
    }

    /**
     * Soll das Projektil so drehen, dass es in die Richtung zeigt, in die es fliegt
     */
    void Rotate(){
        Graphics2D proj;
    }

    void MakeDamage() {
        target.TakeDamage(damage);
    }
}
