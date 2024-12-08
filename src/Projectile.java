import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Projectile extends Entity {
    private Entity caster;
    private double damage;
    private double angle;

    Projectile(Card card, double x, double y, Spieler affiliation, Entity target, Entity caster){
        super(card, x, y, affiliation);
        this.caster = caster;
        this.SetTarget(target);
        damage = caster.GetCard().GetDamage();
        SetProgressVisibility(false);
        SetLabelsIcon(new ImageIcon( Rotate(MakeBufferedImage(card.GetIcon()), GetAngle())));
    }

    void TargetHit(){
//        Mitte des Targets, sonst fliegen die Projektile zur oberen Ecke des Targets
        if (DistanceTo(GetTarget().GetCords()) < GetCard().GetRange()) {
            MakeDamage();
            SetHealth(0);
        }
    }
    /**
     * Bewegung des Targets pro Timer Schritt
     */
    void Move(){
        //a-tangens(GK / AK) = winkel, speed ist hypo → Strahlensatz :)
        double[] distance = DistanceInDirection(GetTarget().GetCords());

        double a = Math.toDegrees(Math.atan(distance[1] / distance[0]));
        double xChange = Math.cos(a) * GetCard().GetSpeed() / 10;
        double yChange = Math.sin(a) * GetCard().GetSpeed() / 10;
        double[] cords = GetCords();
        cords[0] += xChange;
        cords[1] += yChange;
        if (caster instanceof Troop) System.out.println("X: " + xChange + ";" + "Y: " + yChange);
        SetCords(cords);
        if (GetTarget().TroopOnEntity(this)){
            SetCords(GetTarget().GetCords());
        }

    }

    void Update(ArrayList<Troop> troops, ArrayList<Tower> towers, ArrayList<Entity> bridges){
        Move();
        TargetHit();
        SetLabelToCords();
    }

    double GetAngle(){
        double[] distances = DistanceInDirection(GetTarget().GetCords());
        double ergebnis = Math.atan(distances[1] / distances[0]) + 1.5 * Math.PI;
        if (distances[0] > 0) ergebnis = Math.atan(distances[1] / distances[0]) + 0.5 * Math.PI;
//        if (distances[0] > 0) ergebnis += Math.PI;
//        ergebnis = Math.PI;
        return ergebnis;
    }

    BufferedImage MakeBufferedImage(ImageIcon icon){
        Image image = icon.getImage();
        BufferedImage buImg = new BufferedImage(GetCard().GetWidth(), GetCard().GetWidth(), BufferedImage.TYPE_INT_ARGB);
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
//        könnte mir gut vorstellen, dass des hier net geht
        GetTarget().TakeDamage(damage);
    }
}
