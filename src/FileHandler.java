import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileHandler {
    static String[] stats = new String[]{"Name:", "Speed:", "Range:", "Health:", "Damage:", "Attack Speed:", "Sight Distance:", "Width:", "Height:", "Projectile:", "Type:"};

    //    Source: StackOverflow, Coding with John
    static void WriteToFile(String message) {
        ArrayList<String> lines = ReadFile();
        lines.add(message);
        WriteFile(lines);
    }

    /**
     * Liest die Datei aus und gibt alle Zeilen als ein String in einer ArrayList zurück
     * @return ArrayList mit jeder Zeile als einzelner String
     */
    static ArrayList<String> ReadFile(){
        ArrayList<String> string = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader("Clash Royal Cards.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                string.add(line);
//                System.out.println(line);
            }
                reader.close();
        }catch (IOException i){
            System.out.println("nee");
            i.printStackTrace();
        }
        return string;
    }

    /**
     * Wandelt die gespeicherten Daten in Karten um
     *
     * @return Die ausgelesenen Karten
     */
    static ArrayList<Card> GetCards(){
        ArrayList<Card> cards = new ArrayList<>();
        ArrayList<String> datas = ReadFile();
        for (String data : datas){
            if (data.equals("Cards:")) continue;
            cards.add(StringToCard(data, false));
        }
        return cards;
    }

    /**
     * Sucht die passende Karte zum Namen raus und gibt diese zurück
     * @param name Name des Projektils
     * @return Gibt die Karte zurück
     */
    static Card GetCard(String name, boolean projectile){
        ArrayList<String> datas = ReadFile();
        for (String data : datas){
            if (data.equals("Cards:")) continue;
            String stat = data.split(": ")[1].split(";")[0];
            if (stat.equals(name)){
                return StringToCard(data, projectile);
            }
        }
        return null;
    }

    /**
     * Wandelt einen String mit Informationen in eine Karte um.
     * Geht den String Stück für Stück durch und speichert alle Informationen.
     * @param data String mit den Informationen
     * @param projectile Ist die Karte ein Projektil? Ja, ist sie / Nein, nicht unbedingt, aber könnte
     * @return Neue Karte entsprechend der gespeicherten Informationen
     */
    static Card StringToCard(String data, boolean projectile){
        if (data.equals("Cards:")) return null;
        ArrayList<Integer> stats = new ArrayList<>();
        String name = "", imageRef = "", projName = "", type = "";
        try {
//            int x = 12;
            int x = FileHandler.stats.length + 1;
            for (int i = 0; i < x; i++) {
                String stat = data.split(": ")[1].split(";")[0];
                if (i != x -1)data = data.split(data.split(";")[0] + "; ")[1];
                else imageRef = stat;
                if (i == 0) name = stat;
                if (i == 9 ) projName = stat;
                if (i == 10) {
                    if (!projectile) type = stat;
                    else type = "projectile";
                }
                else {
                    try {
                        int newStat = Integer.parseInt(stat);
                        stats.add(newStat);
                    }catch (Exception e){
//                        System.out.println("Schlöecht");
                    }
                }
            }
        }catch (Exception e) {
            System.out.println("Das schlecht");
        }
//        String[] stats = new String[]{"Name:", "Speed:", "Range:", "Health:", "Damage:", "Attack Speed:", "Sight Distance:", "Width:", "Height:", "Projectile: ", "Type: "};
//        Card(String name, ImageIcon icon, int speed, int range, double health, double damage, int attackSpeed, int sightDistance, int width, int height, String projectileName, String cardType){
        return new Card(name, new ImageIcon(imageRef), stats.getFirst(), stats.get(1), stats.get(2), stats.get(3), stats.get(4), stats.get(5), stats.get(6), stats.getLast(), projName, type);
    }

    static void EditFile(int row, String newLine){
        ArrayList<String> lines = ReadFile();
        lines.set(row, newLine);
        WriteFile(lines);
    }

    static private void WriteFile(ArrayList<String> lines){
        try {
//            List<String> lines = Arrays.asList("The first line", "The second line");
            Path file = Paths.get("Clash Royal Cards.txt");
            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException i) {
            System.out.println("Weird");
        }
    }


}
