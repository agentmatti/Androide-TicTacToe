package com.example.martin.tictactoe.feature;


import java.util.*;

class MM_Point {

    int x, y;

    public MM_Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}

class MM_PointsAndScores {

    int score;
    MM_Point point;

    MM_PointsAndScores(int score, MM_Point point) {
        this.score = score;
        this.point = point;
    }
}

class MM_Board {

    List<MM_Point> availablePoints;
    Scanner scan = new Scanner(System.in);
    int[][] board = new int[3][3];

    public MM_Board() {
    }

    public boolean isGameOver() {
        //Game is over is someone has won, or board is full (draw)
        return (hasXWon() || hasOWon() || getAvailableStates().isEmpty());
    }

    public boolean hasXWon() {
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 1) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 1)) {
            //System.out.println("X Diagonal Win");
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if (((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 1)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 1))) {
                // System.out.println("X Row or Column win");
                return true;
            }
        }
        return false;
    }

    public boolean hasOWon() {
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 2) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 2)) {
            // System.out.println("O Diagonal Win");
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 2)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 2)) {
                //  System.out.println("O Row or Column win");
                return true;
            }
        }

        return false;
    }

    public List<MM_Point> getAvailableStates() {
        availablePoints = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (board[i][j] == 0) {
                    availablePoints.add(new MM_Point(i, j));
                }
            }
        }
        return availablePoints;
    }

    public void placeAMove(MM_Point point, int player) {
        board[point.x][point.y] = player;   //player = 1 for X, 2 for O
    }

    public MM_Point returnBestMove() {
        int MAX = -100000;
        int best = -1;

        for (int i = 0; i < rootsChildrenScores.size(); ++i) {
            if (MAX < rootsChildrenScores.get(i).score) {
                MAX = rootsChildrenScores.get(i).score;
                best = i;
            }
        }

        return rootsChildrenScores.get(best).point;
    }

    void takeHumanInput() {
        System.out.println("Your move: ");
        int x = scan.nextInt();
        int y = scan.nextInt();
        MM_Point point = new MM_Point(x, y);
        placeAMove(point, 2);
    }

    public void displayBoard() {
        System.out.println();

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();

        }
    }

    public int returnMin(List<Integer> list) {
        int min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) < min) {
                min = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }

    public int returnMax(List<Integer> list) {
        int max = Integer.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) > max) {
                max = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }

    List<MM_PointsAndScores> rootsChildrenScores;

    public void callMinimax(int depth, int turn){
        rootsChildrenScores = new ArrayList<>();
        minimax(depth, turn);
    }

    public int minimax(int depth, int turn) {

        if (hasXWon()) return +1;
        if (hasOWon()) return -1;

        List<MM_Point> pointsAvailable = getAvailableStates();
        if (pointsAvailable.isEmpty()) return 0;

        List<Integer> scores = new ArrayList<>();

        for (int i = 0; i < pointsAvailable.size(); ++i) {
            MM_Point point = pointsAvailable.get(i);

            if (turn == 1) { //X's turn select the highest from below minimax() call
                placeAMove(point, 1);
                int currentScore = minimax(depth + 1, 2);
                scores.add(currentScore);

                if (depth == 0)
                    rootsChildrenScores.add(new MM_PointsAndScores(currentScore, point));

            } else if (turn == 2) {//O's turn select the lowest from below minimax() call
                placeAMove(point, 2);
                scores.add(minimax(depth + 1, 1));
            }
            board[point.x][point.y] = 0; //Reset this point
        }
        return turn == 1 ? returnMax(scores) : returnMin(scores);
    }
}

public class MM_GameEngineAdapter {

    private MM_Board board;

    public MM_GameEngineAdapter() {
        board = new MM_Board();
        Random rand = new Random();
    }

    // player 1 = X (Player), 2 = O (AI)
    public int AI_move() {
        if( isGameOver() ) {
            return -1;
        }
        board.callMinimax(0, 1);
        MM_Point p = board.returnBestMove();
        board.placeAMove( p, 2);
        return p.x + (3 * p.y);
    }

    public int Player_move( int x, int y) {
        MM_Point p = new MM_Point(x,y);
        if( ! board.getAvailableStates().contains(p)) {
            board.placeAMove(p, 1);
            return y + 3*x;
        } else {
            return -1;
        }
    }

    public boolean isGameOver() {
        return board.isGameOver();
    }

    public char getWinner() {
        if( ! isGameOver() ) {
            return(' ');
        } else if(board.hasOWon()) {
            return 'O';
        } else if(board.hasXWon() ) {
            return 'X';
        } else {
            return 'T';
        }
    }
}
