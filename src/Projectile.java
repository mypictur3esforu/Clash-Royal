import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Projectile extends Entity {
    Entity caster;
    double damage;
    //double[] middleOfTarget;

    Projectile(Card card, double x, double y, Spieler affiliation, Entity target, Entity caster){
        super(card, x, y, affiliation);
        this.caster = caster;
        this.target = target;
        //middleOfTarget = new double[]{target.cords[0] + (double) target.card.width / 2, target.cords[1] + (double) target.card.height / 2};
        damage = caster.card.damage;
        healthBar.setVisible(false);
        label.setIcon(new ImageIcon( Rotate(MakeBufferedImage(card.icon), GetAngle())));
    }

    void TargetHit(){
//        Mitte des Targets, sonst fliegen die Projektile zur oberen Ecke des Targets
        if (DistanceTo(target.cords) < card.range) {
            MakeDamage();
            health = 0;
        }
    }
    /**
     * Bewegung des Targets pro Timer Schritt
     */
    void Move(){
        //a-tangens(GK / AK) = winkel, speed ist hypo → Strahlensatz :)
        double[] distance = DistanceInDirection(target.cords);
        double a = Math.toDegrees(Math.atan(distance[1] / distance[0]));
        double xChange = Math.cos(a) * card.speed / 10;
        double yChange = Math.sin(a) * card.speed / 10;
        cords[0] += xChange;
        cords[1] += yChange;
//        if (DistanceTo(middleOfTarget) <= (double) (card.speed / 10) + card.range){
        if (target.TroopOnEntity(this)){
            cords[0] = target.cords[0];
            cords[1] = target.cords[1];
        }
    }

    void Update(ArrayList<Troop> troops, ArrayList<Tower> towers, ArrayList<Entity> bridges){
        //middleOfTarget = new double[]{target.cords[0] + (double) target.card.width / 2, target.cords[1] + (double) target.card.height / 2};
        Move();
        TargetHit();
        SetLabelToCords();
    }

    double GetAngle(){
        double[] distances = DistanceInDirection(target.cords);
        double ergebnis = Math.atan(distances[1] / distances[0]) + 1.5 * Math.PI;
        if (distances[0] > 0) ergebnis = Math.atan(distances[1] / distances[0]) + 0.5 * Math.PI;
//        if (distances[0] > 0) ergebnis += Math.PI;
//        ergebnis = Math.PI;
        return ergebnis;
    }

    BufferedImage MakeBufferedImage(ImageIcon icon){
        Image image = icon.getImage();
        BufferedImage buImg = new BufferedImage(card.width, card.height, BufferedImage.TYPE_INT_ARGB);
        buImg.getGraphics().drawImage(image, 0, 0, null);
        return buImg;
    }

    /**
     * Soll das Projektil so drehen, dass es in die Richtung zeigt, in die es fliegt
     */
    public static BufferedImage Rotate(BufferedImage image, double angle) {
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = image.getWidth(), h = image.getHeight();
        int neww = (int)Math.floor(w*cos+h*sin), newh = (int) Math.floor(h * cos + w * sin);
        GraphicsConfiguration gc = getDefaultConfiguration();
        BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(angle, (double) w / 2, (double) h / 2);
        g.drawRenderedImage(image, null);
        g.dispose();
        return result;
    }

    private static GraphicsConfiguration getDefaultConfiguration() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        return gd.getDefaultConfiguration();
    }

    void MakeDamage() {
        target.TakeDamage(damage);
    }
}
