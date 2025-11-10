import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {
    static ArrayList<String> codes = new ArrayList<>();
    static char [][] keypad = {{'7', '8', '9'}, {'4', '5', '6'}, {'1', '2', '3'}, {'/', '0', 'A'}};
    static char [][] robotControl = {{'/', '^', 'A'}, {'<', 'v', '>'}};
    public static void load(){
        try{
            String line="";
            BufferedReader br = new BufferedReader(new FileReader("AOC-21.txt"));
            while((line=br.readLine())!=null){
                codes.add(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static String keyToRobot(String key) {
        StringBuilder sb = new StringBuilder();
        Integer current_x = 3, current_y = 2; // Start na 'A'
        int null_x = 3, null_y = 0; // Pozycja '/'

        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            int result_x = 0, result_y = 0;
            for (int j = 0; j < keypad.length; j++) {
                for (int k = 0; k < keypad[j].length; k++) {
                    if (keypad[j][k] == c) {
                        result_x = j;
                        result_y = k;
                    }
                }
            }

            if (current_x == null_x) { // Jesteśmy w "niebezpiecznym" rzędzie (3)
                // Musimy najpierw wykonać ruch pionowy
                while (result_x < current_x) { current_x--; sb.append('^'); }
                while (result_x > current_x) { current_x++; sb.append('v'); }
                // Potem ruch poziomy
                while (result_y < current_y) { current_y--; sb.append('<'); }
                while (result_y > current_y) { current_y++; sb.append('>'); }
            }
            else if (current_y == null_y) { // Jesteśmy w "niebezpiecznej" kolumnie (0)
                // Musimy najpierw wykonać ruch poziomy
                while (result_y < current_y) { current_y--; sb.append('<'); }
                while (result_y > current_y) { current_y++; sb.append('>'); }
                // Potem ruch pionowy
                while (result_x < current_x) { current_x--; sb.append('^'); }
                while (result_x > current_x) { current_x++; sb.append('v'); }
            }
            else { // Jesteśmy w "bezpiecznym" miejscu (ani rząd 3, ani kolumna 0)
                // Kolejność dowolna, np. najpierw pionowo
                while (result_x < current_x) { current_x--; sb.append('^'); }
                while (result_x > current_x) { current_x++; sb.append('v'); }
                // Potem poziomo
                while (result_y < current_y) { current_y--; sb.append('<'); }
                while (result_y > current_y) { current_y++; sb.append('>'); }
            }
            sb.append('A'); // Wciśnij klawisz
        }
        return sb.toString();
    }
    //to co gemini wypluł ma chyba sens
    //poniekąd robie bfs ale nie musze nigdzie tego przechowywac
    //po prostu w mainie sprawdzam najkrotsza trase
    //poprzez dodanie innej logiki przechodzenia po klawiaturze
    //i sprawdzenia na każdej warstwie robota
    //dzisiaj jak wroce do domu to sie tym zajme
    //teraz czas na ocamla i scale
    public static String robotToRobot(String key) {
        StringBuilder sb = new StringBuilder();
        int current_x = 0, current_y = 2; // Start na 'A'
        int null_x = 0, null_y = 0; // Pozycja '/'

        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            int result_x = 0, result_y = 0;
            for (int j = 0; j < robotControl.length; j++) {
                for (int k = 0; k < robotControl[j].length; k++) {
                    if (robotControl[j][k] == c) {
                        result_x = j;
                        result_y = k;
                    }
                }
            }

            if (current_x == null_x) { // Jesteśmy w "niebezpiecznym" rzędzie (0)
                // Musimy najpierw wykonać ruch pionowy
                while (result_x < current_x) { current_x--; sb.append('^'); }
                while (result_x > current_x) { current_x++; sb.append('v'); }
                // Potem ruch poziomy
                while (result_y < current_y) { current_y--; sb.append('<'); }
                while (result_y > current_y) { current_y++; sb.append('>'); }
            }
            else if (current_y == null_y) { // Jesteśmy w "niebezpiecznej" kolumnie (0)
                // Musimy najpierw wykonać ruch poziomy
                while (result_y < current_y) { current_y--; sb.append('<'); }
                while (result_y > current_y) { current_y++; sb.append('>'); }
                // Potem ruch pionowy
                while (result_x < current_x) { current_x--; sb.append('^'); }
                while (result_x > current_x) { current_x++; sb.append('v'); }
            }
            else { // Jesteśmy w "bezpiecznym" miejscu (rząd 1, kolumny 1 lub 2)
                // Kolejność dowolna, np. najpierw pionowo
                while (result_x < current_x) { current_x--; sb.append('^'); }
                while (result_x > current_x) { current_x++; sb.append('v'); }
                // Potem poziomo
                while (result_y < current_y) { current_y--; sb.append('<'); }
                while (result_y > current_y) { current_y++; sb.append('>'); }
            }
            sb.append('A'); // Wciśnij klawisz
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        load();
        int count=0;
        for(int i=0; i<codes.size(); i++){
            int numPart=Integer.parseInt((codes.get(i).substring(0, codes.get(i).length()-1)));
            System.out.println(numPart+" * "+(robotToRobot(robotToRobot(keyToRobot(codes.get(i))))).length());
            count+=numPart*(robotToRobot(robotToRobot(keyToRobot(codes.get(i))))).length();
            System.out.println(robotToRobot(robotToRobot(keyToRobot(codes.get(i)))));
            System.out.println(robotToRobot(keyToRobot(codes.get(i))));
            System.out.println(keyToRobot(codes.get(i)));
            System.out.println(codes.get(i));
            System.out.println(numPart*(robotToRobot(robotToRobot(keyToRobot(codes.get(i))))).length());
        }
        System.out.println(count);
    }

}