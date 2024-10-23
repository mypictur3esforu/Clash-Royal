import javax.swing.*;
import java.awt.*;

public class Card {
    String name, cardType, projName;
    ImageIcon icon;
    Card projectile;
    int speed, range, attackSpeed, sightDistance, width, height;
    double health, damage;
    String[] values;

    Card(String name, ImageIcon icon, int speed, int range, double health, double damage, int attackSpeed, int sightDistance, int width, int height, String projectileName, String cardType){
        this.name = name;
        if (icon != null) this.icon = ImageResizer(icon, width, height);
        this.speed = speed;
        this.range = range;
        this.health = health;
        this.damage = damage  * ((double) attackSpeed / 60);
        this.attackSpeed = attackSpeed;
        this.sightDistance = sightDistance;
        this.width = width;
        this.height = height;
        this.cardType = cardType;
        if (!cardType.equals("projectile")) this.projectile = GetProjectile(projectileName);
//        this.projName = projectileName;
        SaveValuesAsString();
    }

    void SaveValuesAsString(){
//        {"Name:", "Speed:", "Range:", "Health:", "Damage:", "Attack Speed:", "Sight Distance:", "Width:", "Height:", "Projectile: ", "Type: "};
        values = new String[]{name, speed+"", range+"", health+"", damage+"", attackSpeed+"", sightDistance+"", width+"", height+"", projectile+"", cardType};

    }

    static ImageIcon ImageResizer(ImageIcon paraImage, int width, int height){
        try {
            paraImage.setImage(paraImage.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
            return paraImage;
        }catch (Exception e) {
            System.out.println("Height and Width cannot be 0"); //maybe bs
            return null;
        }
    }

    private Card GetProjectile(String name){
        return FileHandler.GetCard(name, true);
    }

//    Card GetProjectile(){
//        if (projName == null) return null;
//        ArrayList<Card> cards = ClashRoyal.staticCardCollection;
//        for (Card card : cards) {
//            if (card.name.equals(projName)) return card;
//        }
//        return null;
//    }

    /**
     * Findet den Wert der gegebenen Kategorie
     * @param category Name des Stats
     * @return Wert des stats als String
     */
     String GetStat(String category){
            if (category.equals("Projectile:") && projectile != null) return projectile.name;
        String[] stats = FileHandler.stats;
        for (int i = 0; i < stats.length; i++) {
            if (stats[i].equals(category)) {
                try {
                    double number = Double.parseDouble(values[i]);
                    return Math.round(number) + "";
                }catch (Exception e){
                return values[i];
                }
            }
        }
        return "Error";
    }
}
