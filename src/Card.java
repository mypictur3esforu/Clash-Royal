import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Karten sind die Spezifizierungen der einzelnen Eigenschaften eines Entities, also wie gut ein Entity eine bestimmte Sache kann.
 * Die Karte ist universell d.h. jedes Entity mit dem gleichen Typen hat den gleichen Namen, Schaden, usw.
 * Im Laufe des Spiels können sich die einzelnen Entities unterscheiden, wenn sie z.B. Schaden nehmen sinken die Trefferpunkte des Entities.
 * Ein anderes Entity mit gleichem Karten Typ würde davon nicht beeinflusst werden
 */
public class Card {
    /**
     * Name der Truppe
     */
    private String name;
    /**
     * Was für eine Art der Karte bzw. Entity
     */
    private String cardType;
    /**
     * Die Adresse der Datei
     */
    private String imagePath;
    /**
     * Das Bild der Karte
     */
    private ImageIcon icon;
    /**
     * Das Projektil, welches geworfen wird
     */
    private Card projectile;
    /**
     * Das Tempo mit dem sich eine Truppe bewegt
     */
    private int speed;
    /**
     * Die Reichweite der Attacke
     */
    private int range;
    /**
     * Das Tempo mit dem eine Truppe angreift
     */
    private int attackSpeed;
    /**
     * Die Distanz in der eine Truppe ein andere sehen kann
     */
    private int sightDistance;
    /**
     * Die Breite der Truppe; für die UI wichtig
     */
    private int width;
    /**
     * Die Höhe der Karte; für die UI wichtig
     */
    private int height;
    /**
     * Die Kosten die Karte in eine Truppe umzuwandeln bzw. diese Karte zu setzen
     */
    private int elixir;
    /**
     * Die Trefferpunkte einer Truppe
     */
    private double health;
    /**
     * Höhe des angerichteten Schadens einer Truppe
     */
    private double damage;
    /**
     * Eine Sammlung aller Attribute einer Karte, um CardEditor/-Creator einfach machen zu können → weniger Copy Paste
     */
    private String[] values;

    public int GetSpeed(){
        return speed;
    }
    public int GetRange (){
        return range;
    }
    public int GetAttackSpeed (){
        return attackSpeed;
    }
    public int GetSightDistance (){
        return sightDistance;
    }
    public int GetWidth (){
        return width;
    }
    public int GetHeight (){
        return height;
    }
    public int GetElixir (){
        return elixir;
    }
    public String GetImagePath(){return imagePath;}


    public ImageIcon GetIcon(){
        return icon;
    }
    public Card GetProjectile (){
        return projectile;
    }
    public double GetHealth (){
        return health;
    }
    public double GetDamage (){
        return damage;
    }
    public String GetName (){
        return name;
    }
    public String GetCardType (){
        return cardType;
    }

    /**
     * Erstellt eine Karte mit den gegebenen Attributen. Diese Karte wird, falls im Deck vorhanden, im Spiel dabei sein und kann dort zu einer Truppe umgewandelt werden. Projektile haben
     * zwar auch Karten, jedoch sind diese für den Spieler nicht verfügbar, sie werden einzig von den Truppen automatisch erzeugt.
     * @param name
     * @param imagePath
     * @param speed
     * @param range
     * @param health
     * @param damage
     * @param attackSpeed
     * @param sightDistance
     * @param width
     * @param height
     * @param projectileName
     * @param cardType
     * @param elixir
     */
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
        if (!cardType.equals("projectile")) this.projectile = GetProjectileByName(projectileName);
        SaveValuesAsString();
    }

    void SaveValuesAsString(){
//        {"Name:", "Speed:", "Range:", "Health:", "Damage:", "Attack Speed:", "Sight Distance:", "Width:", "Height:", "Projectile: ", "Type: "};
        values = new String[]{name, speed+"", range+"", health+"", damage+"", attackSpeed+"", sightDistance+"", width+"", height+"", projectile+"", cardType, elixir+"", imagePath};

    }

    /**
     * Verändert die Größe eines Bildes und skaliert den Content mit
     * @param paraImage Das Bild, welches neu skaliert werden soll
     * @param width Gewünschte Breite
     * @param height Gewünschte Höhe
     * @return Skaliertes Bild
     */
    static ImageIcon ImageResizer(ImageIcon paraImage, int width, int height){
        try {
            paraImage.setImage(paraImage.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
            return paraImage;
        }catch (Exception e) {
            System.out.println("Height and Width cannot be 0");
            return null;
        }
    }
    
    /**
     * Findet ein Projektil mit dem gegebenem Namen
     * @param name Name des Projektils
     * @return Die gespeicherte Karte des Projektils
     */
    private Card GetProjectileByName(String name){
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
        String[] stats = FileHandler.GetStats();
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
     * @param categories Kategorien/Namen der gewünschten Stats
     * @return Werte als String[]
     */
    ArrayList<String> GetMultipleStats(String[] categories){
        ArrayList<String> gathered = new ArrayList<>();
        for (String searchedStat : categories) {
            gathered.add(GetStat(searchedStat));
        }
        return gathered;
    }
}
