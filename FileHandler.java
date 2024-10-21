import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileHandler {

    static PrintWriter writer;

    //    Source: StackOverflow, Coding with John
    static void WriteToFile(String message) {
        try {
//            List<String> lines = Arrays.asList("The first line", "The second line");
            ArrayList<String> lines = ReadFile();
            lines.add(message);
            Path file = Paths.get("Clash Royal Cards.txt");
            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException i) {
            System.out.println("Weird");
        }
    }

    static ArrayList<String> ReadFile(){
        ArrayList<String> string = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader("Clash Royal Cards.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                string.add(line);
                System.out.println(line);
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
//        int[] stats = new int[0];
            if (data.equals("Cards:")) continue;
        ArrayList<Integer> stats = new ArrayList<>();
        String name = "", imageRef = "";
        try {
            for (int i = 0; i < 10; i++) {
                String stat = data.split(": ")[1].split(";")[0];
                if (i != 9)data = data.split(data.split(";")[0] + "; ")[1];
                if (i == 0) name = stat;
                else {
                    try {
                        int newStat = Integer.parseInt(stat);
                        stats.add(newStat);
                    }catch (Exception e){
                        imageRef = stat;
                    }
                }
            }
        }catch (Exception e) {
            System.out.println("Das schlecht");
        }
        cards.add(new Card(name, new ImageIcon(imageRef), stats.getFirst(), stats.get(1), stats.get(2), stats.get(3), stats.get(4), stats.get(5), stats.get(6), stats.getLast()));
        }
        return cards;
    }

//    static void SaveCard(Card card){
//        ArrayList<String>
//    }


}
