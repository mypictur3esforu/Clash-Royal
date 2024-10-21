import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Filed {

    static PrintWriter writer;

    //    Source: w3schools
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
}
