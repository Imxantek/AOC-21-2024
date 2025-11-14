import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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
    public static boolean makesKeypadSense(int curr_x, int curr_y){
        if(curr_x<0 || curr_y<0 || curr_x>=4 || curr_y>=3){
            return false;
        }
        return true;
    }
    public static boolean makesRobotSense(int curr_x, int curr_y){
        if(curr_x<0 || curr_y<0 || curr_x>=2 || curr_y>=3){
            return false;
        }
        return true;
    }
    public static int runCode(String code){
        ArrayList<Searcher> fstLayer = new ArrayList<>();
        ArrayList<String> ret = new ArrayList<>();
        ArrayList<Searcher> sndLayer = new ArrayList<>();
        ArrayList<Searcher> trdLayer = new ArrayList<>();
        fstLayer=decode(code, true);
        for(Searcher s: fstLayer){
            ret.add(s.code);
        }
        for(String str: ret) {
            sndLayer.addAll(decode(str, false));
        }
        ret.clear();
        for(Searcher s: sndLayer){
            ret.add(s.code);
        }
        for(String str: ret){
            trdLayer.addAll(decode(str, false));
        }
        int min=Integer.MAX_VALUE;
        for(Searcher s: trdLayer){
            min=Math.min(min, s.code.length());
        }
        int numPart = Integer.parseInt(code.substring(0, code.length()-1));
        return min*numPart;
    }
    public static ArrayList<Searcher> decode(String code, boolean isKeypadInput){
        ArrayList<Searcher> result = new ArrayList<>();
        if(isKeypadInput){
            int current_x = 3, current_y = 2;
            int target_x=0, target_y=0;
            for(int i = 0; i < code.length(); i++){
                int minLen=Integer.MAX_VALUE;
                char curr=code.charAt(i);
                for(int j=0; j<4; j++){
                    for(int k=0; k<3; k++){
                        if(keypad[j][k]==curr){
                            target_x=j;
                            target_y=k;
                        }
                    }
                }
                Queue<KSearcher> queue = new LinkedList<>();
                if(result.isEmpty()){
                    queue.add(new KSearcher(current_x, current_y, ""));
                }else{
                    for(Searcher s: result){
                        if (s instanceof KSearcher ks){
                            ks.setUp();
                            queue.add(ks);
                        }
                    }
                    result.clear();
                }
                while(!queue.isEmpty()) {
                    KSearcher s = queue.poll();
                    if (makesKeypadSense(s.x - 1, s.y)){
                        if(!s.visited[s.x - 1][s.y]) {
                            s.visited[s.x - 1][s.y] = true;
                            KSearcher up = new KSearcher(s, '^');
                            up.x -= 1;
                            queue.add(up);
                        }
                    }
                    if (makesKeypadSense(s.x, s.y - 1)) {
                        if (!s.visited[s.x][s.y - 1]) {
                            s.visited[s.x][s.y - 1] = true;
                            KSearcher left = new KSearcher(s, '<');
                            left.y -= 1;
                            queue.add(left);
                        }
                    }
                    if (makesKeypadSense(s.x, s.y + 1)) {
                        if (!s.visited[s.x][s.y + 1]) {
                            s.visited[s.x][s.y + 1] = true;
                            KSearcher right = new KSearcher(s, '>');
                            right.y += 1;
                            queue.add(right);
                        }
                    }
                    if (makesKeypadSense(s.x + 1, s.y)){
                        if (!s.visited[s.x + 1][s.y]) {
                           s.visited[s.x + 1][s.y] = true;
                            KSearcher down = new KSearcher(s, 'v');
                            down.x += 1;
                            queue.add(down);
                        }
                    }
                    if(s.x==target_x && s.y==target_y){
                        s.code+='A';
                        if(s.code.length()<minLen){
                            minLen=s.code.length();
                            result.clear();
                            result.add(s);
                        }else if(s.code.length()==minLen){
                            result.add(s);
                        }
                    }
                }
            }
        }else{
            int current_x = 0, current_y = 2;
            int target_x=0, target_y=0;
            for(int i = 0; i < code.length(); i++){
                int minLen=Integer.MAX_VALUE;
                char curr=code.charAt(i);
                for(int j=0; j<2; j++){
                    for(int k=0; k<3; k++){
                        if(robotControl[j][k]==curr){
                            target_x=j;
                            target_y=k;
                        }
                    }
                }
                Queue<RSearcher> queue = new LinkedList<>();
                if(result.isEmpty()){
                    queue.add(new RSearcher(current_x, current_y, ""));
                }else{
                    for(Searcher s: result){
                        if(s instanceof RSearcher rs){
                            rs.setUp();
                            queue.add(rs);
                        }
                    }
                    result.clear();
                }
                while(!queue.isEmpty()) {
                    RSearcher s = queue.poll();
                    if(makesRobotSense(s.x - 1, s.y)){
                        if(!s.visited[s.x - 1][s.y]) {
                            s.visited[s.x - 1][s.y] = true;
                            RSearcher up = new RSearcher(s, '^');
                            up.x -= 1;
                            queue.add(up);
                        }
                    }
                    if(makesRobotSense(s.x, s.y - 1)) {
                        if (!s.visited[s.x][s.y - 1]) {
                            s.visited[s.x][s.y - 1] = true;
                            RSearcher left = new RSearcher(s, '<');
                            left.y -= 1;
                            queue.add(left);
                        }
                    }
                    if(makesRobotSense(s.x, s.y + 1)) {
                        if (!s.visited[s.x][s.y + 1]) {
                            s.visited[s.x][s.y + 1] = true;
                            RSearcher right = new RSearcher(s, '>');
                            right.y += 1;
                            queue.add(right);
                        }
                    }
                    if (makesRobotSense(s.x + 1, s.y)){
                        if (!s.visited[s.x + 1][s.y]) {
                            s.visited[s.x + 1][s.y] = true;
                            RSearcher down = new RSearcher(s, 'v');
                            down.x += 1;
                            queue.add(down);
                        }
                    }
                    if(s.x==target_x && s.y==target_y){
                        s.code+='A';
                        if(s.code.length()<minLen){
                            minLen=s.code.length();
                            result.clear();
                            result.add(s);
                        }else if(s.code.length()==minLen){
                            result.add(s);
                        }
                    }
                }
            }
        }
        return result;
    }
    public static void main(String[] args) {
        load();
        int count=0;
        for(String key : codes){
            count+=runCode(key);
        }
        System.out.println(count);
    }

}