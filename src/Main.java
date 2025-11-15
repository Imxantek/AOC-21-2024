import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    static ArrayList<String> codes = new ArrayList<>();
    static char [][] keypad = {{'7', '8', '9'}, {'4', '5', '6'}, {'1', '2', '3'}, {'/', '0', 'A'}};
    static char [][] robotControl = {{'/', '^', 'A'}, {'<', 'v', '>'}};
    static Map<String, List<String>> KCache = new HashMap<>();
    static Map<String, List<String>> RCache = new HashMap<>();
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
    public static void BFS_K(char start, char end){
        String key=start+"->"+end;
        List<String> result = new ArrayList<>();
        if(start==end){
            result.add("A");
            KCache.put(key,result);
        }
        int start_x=0, start_y=0, target_x=0, target_y=0;
        for(int i=0;i<4;i++){
            for(int j=0;j<3;j++){
                if(keypad[i][j] == start){
                    start_x = i;
                    start_y = j;
                }
                if(keypad[i][j] == end){
                    target_x = i;
                    target_y = j;
                }
            }
        }
        Queue<KSearcher> queue = new LinkedList<>();
        queue.add(new KSearcher(start_x, start_y, ""));
        while(!queue.isEmpty()){
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
                if(result.isEmpty() || result.get(0).length()==s.code.length()){
                    result.add(s.code);
                }else{
                    break;
                }
            }
        }
        KCache.put(key,result);
    }
    public static void BFS_R(char start, char end){
        String key=start+"->"+end;
        List<String> result = new ArrayList<>();
        if(start==end){
            result.add("A");
            RCache.put(key,result);
        }
        int start_x=0, start_y=0, target_x=0, target_y=0;
        for(int i=0;i<2;i++){
            for(int j=0;j<3;j++){
                if(robotControl[i][j] == start){
                    start_x = i;
                    start_y = j;
                }
                if(robotControl[i][j] == end){
                    target_x = i;
                    target_y = j;
                }
            }
        }
        Queue<RSearcher> queue = new LinkedList<>();
        queue.add(new RSearcher(start_x, start_y, ""));
        while(!queue.isEmpty()){
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
                if(result.isEmpty() || result.get(0).length()==s.code.length()){
                    result.add(s.code);
                }else{
                    break;
                }
            }
        }
        RCache.put(key,result);
    }
    public static ArrayList<String> runKeypad(String code){
        ArrayList<String> result = new ArrayList<>();
        code="A"+code;
        for(int i=0;i<code.length()-1;i++){
            char start=code.charAt(i);
            char end=code.charAt(i+1);
            String key=start+"->"+end;
//            System.out.println(start+"->"+end);
            List<String> cache = KCache.get(key);
            if(result.isEmpty()){
                result.addAll(cache);
            }else{
                ArrayList<String> temp = new ArrayList<>();
                for(String s:result){
                    for(String s1:cache){
                        temp.add(s+s1);
                    }
                }
                result=new ArrayList<>(temp);
            }
//            System.out.println(result);
        }
        return result;
    }
    public static ArrayList<String> runRobot(String code){
        ArrayList<String> result = new ArrayList<>();
        code="A"+code;
        for(int i=0;i<code.length()-1;i++){
            char start=code.charAt(i);
            char end=code.charAt(i+1);
            String key=start+"->"+end;
//            System.out.println(start+"->"+end);
            List<String> cache = RCache.get(key);
            if(result.isEmpty()){
                result.addAll(cache);
            }else{
                ArrayList<String> temp = new ArrayList<>();
                for(String s:result){
                    for(String s1:cache){
                        temp.add(s+s1);
                    }
                }
                result=new ArrayList<>(temp);
            }
//            System.out.println(result);
        }
        return result;
    }
    public static ArrayList<String> runRobot(ArrayList<String> codes){
        ArrayList<String> result = new ArrayList<>();
        for(String code:codes){
            result.addAll(runRobot(code));
        }
        return result;
    }
    public static int runCode(String code){
        int min=Integer.MAX_VALUE;
        ArrayList<String> result = runRobot(runRobot(runKeypad(code)));
        for(int i=0;i<result.size();i++){
            min=Math.min(min, result.get(i).length());
        }
        int numPart = Integer.parseInt(code.substring(0, code.length()-1));
        return min*numPart;
    }
    public static void createCaches(){
        for(int i=0; i<4; i++){
            for(int j=0; j<3; j++){
                if(i==3 && j==0){
                    continue;
                }
                for(int k=0; k<4; k++){
                    for(int l=0; l<3; l++){
                        if(k==3 && l==0){
                            continue;
                        }
                        BFS_K(keypad[i][j], keypad[k][l]);
                    }
                }
            }
        }
        for(int i=0; i<2; i++){
            for(int j=0; j<3; j++){
                if(i==0 && j==0){
                    continue;
                }
                for(int k=0; k<2; k++){
                    for(int l=0; l<3; l++){
                        if(k==0 && l==0){
                            continue;
                        }
                        BFS_R(robotControl[i][j], robotControl[k][l]);
                    }
                }
            }
        }
        for(String str: KCache.keySet()){
            System.out.println(str);
            System.out.println(KCache.get(str));
        }
        for(String str: RCache.keySet()){
            System.out.println(str);
            System.out.println(RCache.get(str));
        }
    }
    public static void main(String[] args) {
        load();
        createCaches();
        int count=0;
        for(String key : codes){
            count+=runCode(key);
        }
        System.out.println(count);
    }

}