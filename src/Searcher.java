public abstract class Searcher {
    int x;
    int y;
    String code;
//    boolean [][] visited = new boolean[4][3];
    public Searcher(int x, int y,  String code) {
        this.x = x;
        this.y = y;
        this.code = code;
//        setUp();
    }
    public Searcher(){}
    public Searcher(Searcher other, char code){
        this.x = other.x;
        this.y = other.y;
        this.code = other.code + code;
    }
}
