package com.example.martin.tictactoe.feature;

import java.util.Random;

public class GameEngine {
    private static final Random RANDOM = new Random();
    private char[] field;
    private char currentPlayer;
    private boolean ended;
    private MM_GameEngineAdapter ki;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    private int level;

    // start new game
    public GameEngine() {
        field = new char[9];
        // initial level is 0
        level = 0;
        newGame();
    }

    // return the flag whether the game is finished
    public boolean isEnded() {
        return ended;
    }

    public char play(int x, int y) {
        System.out.println("play(" + currentPlayer + ")");
        // check if field is not used
        // return error if not
        if (' ' != field[3 * y + x]) {
            return 'F';
        } else if (!ended && field[3 * y + x] == ' ') {
            // normal move
            field[3 * y + x] = currentPlayer;
            if(level == 9) {
                ki.Player_move(x, y);
            }
            changePlayer();
        }
        return checkEnd();
    }

    public void changePlayer() {
        System.out.println("changePlayer, current = " + getCurrentPlayer());
        currentPlayer = (currentPlayer == 'X' ? 'O' : 'X');
        System.out.println("changePlayer, new = " + getCurrentPlayer());
    }

    char getCurrentPlayer() {
        return currentPlayer;
    }

    // get the status of a field
    public char getField(int x, int y) {
        return field[3 * y + x];
    }

    // start a new game
    public void newGame() {
        System.out.println("----- new Gaihm -----");
        ki = new MM_GameEngineAdapter();

        // set all fields to ' ' empty
        for (int i = 0; i < field.length; i++) {
            field[i] = ' ';
        }
        // start with random player
        int rand = RANDOM.nextInt(9);
        if (rand < 5) {
            currentPlayer = 'X';
        } else {
            currentPlayer = 'O';
        }
        System.out.println("start with Plaiher" + currentPlayer);
        // set ended flag to false
        ended = false;
    }


    // check whether one player has 3 in a row
    public char checkEnd() {
        for (int i = 0; i < 3; i++) {
            if (getField(i, 0) != ' ' &&
                    getField(i, 0) == getField(i, 1) &&
                    getField(i, 1) == getField(i, 2)) {
                ended = true;
                return getField(i, 0);
            }

            if (getField(0, i) != ' ' &&
                    getField(0, i) == getField(1, i) &&
                    getField(1, i) == getField(2, i)) {
                ended = true;
                return getField(0, i);
            }
        }

        if (getField(0, 0) != ' ' &&
                getField(0, 0) == getField(1, 1) &&
                getField(1, 1) == getField(2, 2)) {
            ended = true;
            return getField(0, 0);
        }

        if (getField(2, 0) != ' ' &&
                getField(2, 0) == getField(1, 1) &&
                getField(1, 1) == getField(0, 2)) {
            ended = true;
            return getField(2, 0);
        }

        for (int i = 0; i < 9; i++) {
            if (field[i] == ' ')
                return ' ';
        }
        return 'T';
    }

    public int checkDefenseOrAttack(char Player, int sublevel) {
        // prüfe auf verteidigung
        // waagerecht
        System.out.println("CheckDefenseOrAttack: " + Player + ":, sublevel = ");

        for (int y = 0; y <= 2; y++) {
            if ((getField(0, y) == Player) && (getField(1, y) == Player) && (getField(2, y) == ' ')) {
                return 2 + (y * 3);
            } else if ((getField(0, y) == Player) && (getField(2, y) == Player) && (getField(1, y) == ' ')) {
                return 1 + (y * 3);
            } else if ((getField(1, y) == Player) && (getField(2, y) == Player) && (getField(0, y) == ' ')) {
                return 0 + (y * 3);
            }
        } //senkrecht (vertauschen von x und y um gedreht zu prüfen)

        if (sublevel >= 1) {
            for (int y = 0; y <= 2; y++) {
                if ((getField(y, 0) == Player) && (getField(y, 1) == Player) && (getField(y, 2) == ' ')) {
                    return y + (2 * 3);
                } else if ((getField(y, 0) == Player) && (getField(y, 2) == Player) && (getField(y, 1) == ' ')) {
                    return y + (1 * 3);
                } else if ((getField(y, 1) == Player) && (getField(y, 2) == Player) && (getField(y, 0) == ' ')) {
                    return y + (0 * 3);
                }
            }
        }
        // diagonal
        if (sublevel >= 2) {
            if (getField(1, 1) == Player) {
                if (getField(0, 0) == Player && getField(2, 2) == ' ') {
                    return 8;
                } else if (getField(0, 2) == Player && getField(2, 0) == ' ') {
                    return 2;
                } else if (getField(2, 0) == Player && getField(0, 2) == ' ') {
                    return 6;
                } else if (getField(2, 2) == Player && getField(0, 0) == ' ') {
                    return 0;
                }
            }
        }
        if (sublevel >= 3) {
            //prüfe Zwickmühle
            // (unten rechts)
            if (getField(2, 1) == Player && getField(1, 2) == Player && getField(2, 2) == ' ') {
                return 8;
            } // unten links
            else if (getField(0, 1) == Player && getField(1, 2) == Player && getField(0, 2) == ' ') {
                return 6;
            }// oben links
            else if (getField(1, 0) == Player && getField(0, 1) == Player && getField(0, 0) == ' ') {
                return 0;
            }// oben rechts
            else if (getField(1, 0) == Player && getField(2, 1) == Player && getField(2, 0) == ' ') {
                return 2;
            }
        }
        return -1;
    }
//0 zufall
//1 middle
//2-4 defense
//5-7 attack
//8 zwick
//9 minmax

    public int computeLevel(int tmpLevel) {
        System.out.println("wie sind jetzt level " + tmpLevel);
        int position = -1;
        if (tmpLevel == 0) {
            return setRandomField();
        } else if (tmpLevel == 1) {
            position = setMiddle();
            if (position == -1) {
                return computeLevel(tmpLevel - 1);
            }
        } else if (tmpLevel == 2) {
            position = checkDefenseOrAttack('X', 0);
            if (position == -1) {
                return computeLevel(tmpLevel - 1);
            } else field[position] = currentPlayer;
        } else if (tmpLevel == 3) {
            // Angriff
            position = checkDefenseOrAttack('X', 1);
            if (position == -1) {
                return computeLevel(tmpLevel - 1);

            } else field[position] = currentPlayer;
        } else if (tmpLevel == 4) {
            // Angriff
            position = checkDefenseOrAttack('X', 2);
            if (position == -1) {
                return computeLevel(tmpLevel - 1);
            } else field[position] = currentPlayer;
        } else if (tmpLevel == 5) {
            // angriff
            position = checkDefenseOrAttack('O', 0);
            if (position == -1) {
                return computeLevel(tmpLevel - 1);
            } else field[position] = currentPlayer;
        } else if (tmpLevel == 6) {
            int f = checkDefenseOrAttack('O', 1);
            if (f == -1) {
                computeLevel(tmpLevel - 1);
            } else field[f] = currentPlayer;
        } else if (tmpLevel == 7) {
            position = checkDefenseOrAttack('O', 2);
            if (position == -1) {
                return computeLevel(tmpLevel - 1);
            } else field[position] = currentPlayer;
        } else if (tmpLevel == 8) {
            position = checkDefenseOrAttack('O', 2);
            if (position == -1) {
                int f2 = checkDefenseOrAttack('X', 3);
                if (f2 == -1) {
                    return computeLevel(tmpLevel - 1);
                } else field[f2] = currentPlayer;
            } else field[position] = currentPlayer;
        } else if (tmpLevel == 9) {
            position = ki.AI_move();
            field[position] = currentPlayer;
            // minmax
        } else {
            return computeLevel(9);
        }
        return position;
    }


    // the computer player
    public char computer() {
        System.out.println("ich setze jetzt");
        if (!ended) {
            computeLevel(level);
            changePlayer();
        }
        return checkEnd();
    }

    int setMiddle() {
        System.out.println("setMiddle");
        int position = -1;

        if (field[4] == ' ') {
            // prüfe und setze in die mitte
            field[4] = currentPlayer;
            position = 4;
        }
        return position;
    }

    int setRandomField() {
        System.out.println("setRandom");
        int position = -1;
        // try setting a random field which is not used
        do {
            position = RANDOM.nextInt(9);
        } while (field[position] != ' ');

        field[position] = currentPlayer;

        return position;
    }

}
