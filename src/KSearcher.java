public class KSearcher extends Searcher{
    boolean[][] visited = new boolean[4][3];
    public KSearcher(int x, int y, String code){
        super(x, y, code);
        setUp();
    }
    public KSearcher(KSearcher other, char code){
        super();
        this.x = other.x;
        this.y = other.y;
        this.code = other.code+code;
        this.visited = new boolean[4][3];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                this.visited[i][j] = other.visited[i][j];
            }
        }
    }
    public void setUp(){
        visited = new boolean[4][3];
        visited[x][y] = true;
        visited[3][0] = true;
    }

}
