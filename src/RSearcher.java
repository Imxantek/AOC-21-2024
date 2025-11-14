public class RSearcher extends Searcher{
    boolean[][] visited = new boolean[2][3];
    public RSearcher(int x, int y, String code){
        super(x, y, code);
        setUp();
    }
    public RSearcher(RSearcher other, char code){
        super();
        this.x=other.x;
        this.y=other.y;
        this.code=other.code+code;
        for(int i=0; i<2; i++){
            for(int j=0; j<3; j++){
                this.visited[i][j]=other.visited[i][j];
            }
        }
    }
    public void setUp(){
        visited = new boolean[2][3];
        visited[0][0] = true;
        visited[x][y] = true;
    }
}
