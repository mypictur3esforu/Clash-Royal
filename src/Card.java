import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Card {
    String name, cardType, imagePath;
    ImageIcon icon;
    Card projectile;
    int speed, range, attackSpeed, sightDistance, width, height, elixir;
    double health, damage;
    String[] values;

    Card(String name, String imagePath, int speed, int range, double health, double damage, int attackSpeed, int sightDistance, int width, int height, String projectileName, String cardType, int elixir){
        this.name = name;
        if (imagePath != null) icon = ImageResizer(new ImageIcon(imagePath), width, height);
        this.speed = speed;
        this.range = range;
        this.health = health;
        this.damage = damage  * ((double) attackSpeed / 60);
        this.attackSpeed = attackSpeed;
        this.sightDistance = sightDistance;
        this.width = width;
        this.height = height;
        this.cardType = cardType;
        this.imagePath = imagePath;
        this.elixir = elixir;
        if (!cardType.equals("projectile")) this.projectile = GetProjectile(projectileName);
        SaveValuesAsString();
    }

    void SaveValuesAsString(){
//        {"Name:", "Speed:", "Range:", "Health:", "Damage:", "Attack Speed:", "Sight Distance:", "Width:", "Height:", "Projectile: ", "Type: "};
        values = new String[]{name, speed+"", range+"", health+"", damage+"", attackSpeed+"", sightDistance+"", width+"", height+"", projectile+"", cardType, elixir+"", imagePath};

    }

    static ImageIcon ImageResizer(ImageIcon paraImage, int width, int height){
        try {
            paraImage.setImage(paraImage.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
            return paraImage;
        }catch (Exception e) {
            System.out.println("Height and Width cannot be 0");
            return null;
        }
    }

    private Card GetProjectile(String name){
        return FileHandler.GetCard(name, true);
//        return ClashRoyal.GetCardByName(name);
    }

    /**
     * Findet den Wert der gegebenen Kategorie. FileHandler.stats und value müssen übereinstimmen
     * @param category Name des Stats
     * @return Wert des stats als String.
     */
     String GetStat(String category){
            if (category.equals("Projectile:") && projectile != null) return projectile.name;
        String[] stats = FileHandler.stats;
        for (int i = 0; i < stats.length; i++) {
            if (!stats[i].equals(category)) continue;
            try {
                    double number = Double.parseDouble(values[i]);
                    return Math.round(number) + "";
            }catch (Exception e){
                return values[i];
            }
        }
        return "Error";
    }

    /**
     * Gibt die Werte der Karte in gegebenen Kategorien
     * @param searchedStats Namen der gewünschten Stats
     * @return Werte als String[]
     */
    ArrayList<String> GetCardValues(String[] searchedStats){
        ArrayList<String> gathered = new ArrayList<>();
        for (String searchedStat : searchedStats) {
            gathered.add(GetStat(searchedStat));
        }
        return gathered;
    }
}
