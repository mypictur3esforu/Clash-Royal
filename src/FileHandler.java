import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

//Neue Kategorie Anleitung:
//1. stats erweitern
//2. Card Parameter und Attribute erweitern (wenn nötig Attribut = Parameter nicht vergessen)
//3. Card SaveAsString erweitern
//4. Alte Karten Updaten

public class FileHandler {
    static String[] stats = new String[]{"Name:", "Speed:", "Range:", "Health:", "Damage:", "Attack Speed:", "Sight Distance:", "Width:", "Height:", "Projectile:", "Type:", "Elixir:"};

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
//                        System.out.println("String zu Int geht nicht, aber ist egal");
                    }
                }
            }
        }catch (Exception e) {
            System.out.println("Das schlecht");
        }
//        String[] stats = new String[]{"Name:", "Speed:", "Range:", "Health:", "Damage:", "Attack Speed:", "Sight Distance:", "Width:", "Height:", "Projectile:", "Type:", "Elixir:};
//        Card(String name, ImageIcon icon, int speed, int range, double health, double damage, int attackSpeed, int sightDistance, int width, int height, String projectileName, String cardType){

//        Erstellt die neue Karte; stats ist der Integer mit den Zahlwerten; stats hat die gleiche Reihenfolge wie FileHandler.stats
        return new Card(name, imageRef, stats.getFirst(), stats.get(1), stats.get(2), stats.get(3), stats.get(4), stats.get(5), stats.get(6), stats.get(7), projName, type, stats.getLast());
    }

    /**
     * Speichert eine bestimmte Zeile neu
     * @param row Nummer der Zeile
     * @param newLine Neue Zeile, die gespeichert werden soll
     */
    static void EditFile(int row, String newLine){
        ArrayList<String> lines = ReadFile();
        lines.set(row, newLine);
        WriteFile(lines);
    }

    static private void WriteFile(ArrayList<String> lines){
        try {
//            List<String> lines = Arrays.asList("The first line", "The second line"); //Eine Methode wie man FileWriten kann
            if (!lines.getFirst().equals("Cards:")) lines.addFirst("Cards:");
            Path file = Paths.get("Clash Royal Cards.txt");
            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException i) {
            System.out.println("Weird");
        }
    }

    /**
     * Speichert alle Informationen/String in der Datei
     * @param lines Zeilen mit den Karten
     */
    static void SaveCards(ArrayList<String> lines){
            WriteFile(lines);
    }


    /**
     * Erzeugt einen String zum Karten erstellen, bzw. eine Zeile die gespeichert werden kann.
     * @param number Nummer der Zahl (Die Zahl am Anfang)
     * @param inputs JTexFields, in der die Informationen stehen. Müssen die gleiche Reihenfolge haben wie FilHandler.stats und das letzte TextField muss der ImagePath sein
     * @return Ein String den das Programm speichern kann
     */
    static String CreateMainString(int number, ArrayList<JTextField> inputs){
        StringBuilder string;
        string = new StringBuilder(number + ". ");
        String[] stats = FileHandler.stats;
        for (int i = 0; i < stats.length; i++) {
//            string += stats[i] + " " + values.get(i) + "; ";
            string.append(stats[i]).append(" ").append(inputs.get(i).getText()).append("; ");
        }
        string.append("Icon: ").append(inputs.getLast().getText()).append("; ");
        System.out.println(string);
        return string + "";
    }


}
