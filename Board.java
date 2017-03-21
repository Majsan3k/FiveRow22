//Maja Lund, malu 9669

//// TODO: kolla if-satserna i getEmptyPositions()

package alda.fiveRow;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Board {

    private Scanner scan = new Scanner(System.in);
    private int sizeX;
    private int sizeY;
    private char[][] board;


    public Board(int x, int y){
        this.sizeX = x;
        this.sizeY = y;
        this.board = new char[sizeX][sizeY];
        for(int i=0;i<x; i++) {
            for (int j = 0; j<y; j++) {
                board[i][j] = '+';
            }
        }
    }

    private void printBoard(){

        for(int i = 0; i<sizeY; i++){
            for(int j = 0; j<sizeX; j++){
                System.out.print("  " + board[j][i]);
            }
            System.out.println("");
        }
    }

    private void placePlayer(int x, int y, char player){
        System.out.println("");
        board[x][y] = player;
        printBoard();
    }

    private boolean getWinner(char player){

        ArrayList<Position> playerPos = getPlayerPositions(player);

        for(Position pos : playerPos) {
            if(playersInRow(pos, player, 3)){
                return true;
            }
        }
        return false;
    }

    private boolean playersInRow(Position pos, char player, int inRow){

        if(playersInRowHorizontal(pos, player, inRow) || playersInRowVertical(pos, player, inRow) || playersInRowDiagonalLeft(pos, player, inRow) || playersInRowDiagonalRight(pos, player,inRow)){
            return true;
        }
        return false;

    }

    private boolean playersInRowHorizontal(Position pos, char player, int inRow){

        int x = pos.getX();
        int y = pos.getY();
        int i = 1;

        for(; i<inRow; i++){
            if(x+i < 0 || x+i >= sizeX) {
                return false;
            }
            if (board[x + i][y] != player) {
                return false;
            }
        }
        return i == inRow;
    }

    private boolean playersInRowVertical(Position pos, char player, int inRow){

        int x = pos.getX();
        int y = pos.getY();
        int i = 1;

        for(; i<inRow; i++){
            if(y+i < 0 || y+i >= sizeY) {
                return false;
            }
            if (board[x][y + i] != player) {
                return false;
            }
        }
        return i == inRow;
    }

    private boolean playersInRowDiagonalLeft(Position pos, char player, int inRow){

        int x = pos.getX();
        int y = pos.getY();
        int i = 1;

        for(; i<inRow; i++) {
            if (x - i < 0 || y - i < 0 || x - i >= sizeX || y - i >= sizeY) {
                return false;
            }

            if (board[x - i][y - i] != player) {
                return false;

            }
        }
        return i == inRow;
    }

    private boolean playersInRowDiagonalRight(Position pos, char player, int inRow){

        int x = pos.getX();
        int y = pos.getY();
        int i = 1;

        for(; i<inRow; i++) {
            if (x - i < 0 || y + i < 0 || x - i >= sizeX || y + i >= sizeY) {
                return false;
            }
            if (board[x - i][y + i] != player) {
                return false;
            }
        }
        return i == inRow;
    }

    private boolean checkGameOver(){

        if(getWinner('X') || getWinner('O') || terminal()){
            return true;
        }
        return false;
    }

    private HashSet<Position> getEmptyPositions(char player){
        HashSet<Position> emptyPositions = new HashSet<>();

        ArrayList<Position> opponentPositions = getPlayerPositions(player);

        for(int i = 0; i < opponentPositions.size(); i++) {

            int x = opponentPositions.get(i).getX();
            int y = opponentPositions.get(i).getY();

            for(int xDirection = -1; xDirection<=1; xDirection++){
                int currX = x + xDirection;
                for(int yDirection = -1; yDirection<=1; yDirection++){

                    int currY = y + yDirection;

                    if(currX >= 0 && currX < sizeX && currY >= 0 && currY < sizeY){
                        if(board[currX][currY] == '+'){
                            emptyPositions.add(new Position(currX,currY));
                        }
                    }
                }
            }
        }
        return  emptyPositions;
    }

    private ArrayList<Position> getPlayerPositions(char player){
        ArrayList<Position> opponentPositions = new ArrayList<>();

        for(int i = 0; i < sizeY; i++){
            for(int j = 0; j < sizeX; j++){
                if(board[i][j] == player){
                    opponentPositions.add(new Position(i, j));
                }
            }
        }
        return  opponentPositions;
    }

    private boolean terminal(){
        for(int i = 0; i < sizeY; i++){
            for(int j = 0; j < sizeX; j++){
                if(board[i][j] == '+'){
                    return false;
                }
            }
        }
        return true;
    }

    private int evaluateMove(){

        ArrayList<Position> compPositions = getPlayerPositions('X');
        ArrayList<Position> humanPositions = getPlayerPositions('O');

        int compCount = 0;
        int humanCount = 0;

        for(Position playerPos : compPositions){
            if(playersInRow(playerPos, 'X', 2)){
                compCount = compCount > 2 ? compCount : 2;
            }
            if(playersInRow(playerPos, 'X', 3)){
                compCount = compCount > 3 ? compCount : 3;
            }
            if(playersInRow(playerPos, 'X', 4)){
                compCount = compCount > 4 ? compCount : 4;
            }
        }

        for(Position playerPos : humanPositions){
            if(playersInRow(playerPos, 'O', 2)){
                humanCount = humanCount > 2 ? humanCount : 2;
            }
            if(playersInRow(playerPos, 'O', 3)){
                humanCount = humanCount > 3 ? humanCount : 3;
            }
            if(playersInRow(playerPos, 'O', 4)){
                humanCount = humanCount > 4 ? humanCount : 4;
            }
        }

        if(compCount == 3){
            return 10;
        }
        if(humanCount == 2 && compCount == 2){
            return -1;
        }
        if(compCount < humanCount){
            return  -1;
        }else if(compCount > humanCount){
            return 2;
        }else{
            return 0;
        }
    }

    private Position minimaxComp(char player, int depth, Position move) {   //gör senare om till alphaBetaMinimax

        HashSet<Position> emptyPlaces = getEmptyPositions('O');

        if(emptyPlaces.isEmpty()){
            emptyPlaces = getEmptyPositions('X');
        }

        if(getWinner('X')){
            move.setScore(1);
            return move;
        }
        if(getWinner('O')){
            move.setScore(-1);
            return move;
        }

        int score = 0;
        Position bestMove = new Position(0, 0, score);
        Position humanMove;

        if(depth == 0){
            move.setScore(evaluateMove());
            return move;
        }

//        if(terminal()){
//            score = 0;
//        }
//        else

        else {
            score = -1;
            for (Position pos : emptyPlaces) {

                int x = pos.getX();
                int y = pos.getY();

                board[x][y] = player;
                humanMove = minimaxHuman('O', depth - 1, pos);
                board[x][y] = '+';

                if (humanMove.getScore() > score) {
                    score = humanMove.getScore();
                    bestMove = new Position(x, y, score);
                }
            }
        }
        System.out.println("Best move comp: ");
        printBoard();
        return bestMove;

    }

    private Position minimaxHuman(char player, int depth, Position move) {

        HashSet<Position> emptyPlaces = getEmptyPositions('X');

        if(emptyPlaces.isEmpty()) {
            emptyPlaces = getEmptyPositions('O');
        }

        if(getWinner('X')){
            move.setScore(1);
            return move;
        }
        if(getWinner('O')){
            move.setScore(-1);
            return move;
        }

        int score = 0;
        Position bestMove = new Position(0, 0, score);
        Position compMove;

        if(depth == 0){
            move.setScore(evaluateMove());
            return move;
        }
//        if(terminal()){
//            score = 0;
//        }
//
//        else

        else {
            score = 1;

            for(Position pos : emptyPlaces){

                int x = pos.getX();
                int y = pos.getY();

                board[x][y] = player;
                compMove = minimaxComp('X', depth - 1, pos);
                board[x][y] = '+';

                if (compMove.getScore() < score) {
                    score = compMove.getScore();
                    bestMove = new Position(x, y, score);
                }
            }
        }
        System.out.println("Best move human: ");
        printBoard();
        return bestMove;
    }

    private void play(){

        while(!checkGameOver()){

            //Human playing
            System.out.println("Your move, write to numbers, y and x");
            int x = scan.nextInt();
            int y = scan.nextInt();
//            while(board[x][y] != '+'){
//                System.out.println("The position occupied");
//                x = scan.nextInt();
//                y = scan.nextInt();
//            }
            placePlayer(x, y, 'O');

            if(getWinner('O')){
                System.out.println("Human wins");
                return;
            }
            if(terminal()){
                System.out.println("Draw");
                return;
            }

            //Computer playing
            Position place = minimaxComp('X', 8, new Position(0,0));
            placePlayer(place.getX(), place.getY(), 'X');

            if(getWinner('X')){
                System.out.println("Computer wins");
                //Computer won
                return;
            }
            if(terminal()){
                System.out.println("Draw");
                return;
            }
        }
    }

    public static void main(String [] args){

        Board board = new Board(3, 3);
        board.play();

    }
}
