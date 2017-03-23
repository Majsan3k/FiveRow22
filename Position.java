//Maja Lund malu9669

package alda.fiveRow;

public class Position {

    private int x;
    private int y;
    private int score;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Position(int x, int y, int score){
        this.x = x;
        this.y = y;
        this.score = score;
    }

    public int getScore(){
        return score;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setScore(int newScore){
        this.score = newScore;
    }

    public String toString(){
        return "" + x + " " + y + " " + score;
    }
}
