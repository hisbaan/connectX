/*
File: connectX.java
Created: 13/09/2019
Author: Hisbaan Noorani
*/

import java.util.Scanner;

public class connectX {
    //Debug variable for tracing.
    public static final boolean DEBUG = true;

    //Colours.
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    //Coloured characters.
    private static final String redCharacter = ANSI_RED + "1" + ANSI_RESET;
    private static final String yellowCharacter = ANSI_YELLOW + "2" + ANSI_RESET;

    //Declaring + initializing variables.
    private Scanner sc = new Scanner(System.in);
    private boolean gameOver = false;
    private int chainLength = 4;
    private int boardXValue = 7;
    private int boardYValue = 6;

    private String[][] board = new String[boardXValue][boardYValue];

    public static void main(String[] args) {
        new connectX();
    }

    private connectX() {
        mainMenu();
    }

    //Method that holds the code for the main menu.
    private void mainMenu() {
        System.out.println("Where would you like to go?\n\t[1] Start game\n\t[2] Instructions\n\t[3] About\n\t[4] Quit");
        boolean validInput = true;
        boolean exceptionThrown;
        do { //Do while loop to insure valid input.
            int choice = 0;
            do {
                exceptionThrown = false;
                try {
                    choice = Integer.parseInt(sc.nextLine());
                } catch (Exception e) {
                    System.out.println("\nInvalid input. Please enter an integer and between 1 and 4 and try again...\n\n");
                    exceptionThrown = true;
                }
            } while (exceptionThrown);
            switch (choice) { //Switch on input to get the user's intentions.
                case 1:
                    validInput = true;
                    startGame();
                    break;
                case 2:
                    validInput = true;
                    instructions();
                    break;
                case 3:
                    validInput = true;
                    about();
                    break;
                case 4:
                    quit();
                    break;
                default:
                    System.out.println("\nInvalid input. Please try again...\n\n");
                    validInput = false;
                    break;
            }
        } while (!validInput);
    }

    private void startGame() {
        //For loop that sets all indexes to "0" so the game starts correctly.
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 7; x++) {
                board[x][y] = "0";
            }
        }

        gameOver = false;

        //While loop that sets the sequence of events for the game (i.e. printing --> checking for win --> getting player one input --> printing --> getting player two input --> repeat)
        while (!gameOver) {
            printBoard();

            winCheck();
            if (gameOver) break;

            getPlayerOneInput();
            printBoard();

            winCheck();
            if (gameOver) break;

            getPlayerTwoInput();
        }

        //TODO go back to the main menu when the game ends.
    }

    //Method that checks for a win and prints a statement accordingly.
    private void winCheck() {
        if (checkWinCondition() == 1) {
            gameOver = true;
            System.out.println(ANSI_RED + "\nPLAYER 1 WINS!" + ANSI_RESET);
        } else if (checkWinCondition() == 2) {
            gameOver = true;
            System.out.println(ANSI_YELLOW + "\nPLAYER 2 WINS" + ANSI_RESET);
        }
    }

    //TODO make inputting a string/char not possible with try catches
    //Method that gets the first player's input.
    private void getPlayerOneInput() {
        boolean inputValid;
        int choice;

        do {
            System.out.println("\nPlayer " + redCharacter + ", select a column: ");
            choice = sc.nextInt();

            if (choice > 7 || choice < 1) {
                System.out.println("\nInvalid input. Please enter a number from 1 to 7");
                inputValid = false;
            } else {
                choice--;
                inputValid = true;
            }
        } while (!inputValid);

        board[choice][lineCheck(choice)] = redCharacter;
    }

    //Method that gets the second player's input.
    private void getPlayerTwoInput() {
        boolean inputValid;
        int choice;

        do {
            System.out.println("\nPlayer " + yellowCharacter + ", select a column: ");

            choice = sc.nextInt() - 1;
            if (choice > 6 || choice < 0) {
                System.out.println("\nInvalid input. Please enter a number from 1 to 7");
                inputValid = false;
            } else {
                inputValid = true;
            }
        } while (!inputValid);

        board[choice][lineCheck(choice)] = yellowCharacter;
    }

    //Method that prints the board.
    private void printBoard() {
        System.out.println("\n\n1  2  3  4  5  6  7\n--------------------");

        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 7; x++) {

                System.out.print(board[x][y]);
                System.out.print("  ");
            }
            System.out.println();
        }

    }

    //TODO make a check for if the grid fills up by checking for -1 as a return.
    private int lineCheck(int choice) { //Checks what value x value to place the tile at.
        for (int y = 5; y >= 0; y--) {
            if (board[choice][y].equals("0")) {
                return y;
            }
        }

        return -1;
    }

    //Method to check the win condition and return which player wins.
    private int checkWinCondition() {
        //Variables that need to be accessed throughout the method.
        boolean keepChecking;
        int tilesInARow;

        //For loop to iterate through all the indices in the 2D array.
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 7; x++) {
                if (board[x][y].equals(redCharacter)) { //Checks if the specific index is a red tile.
                    //Check right, down, diagonal up, diagonal down for red.

                    //Resetting variables.
                    tilesInARow = 0;
                    keepChecking = true;

                    //The proceeding 8 blocks of code are all relatively similar so I'm only commenting the first one.

                    //For loop to where z acts as a counter to check the next tile in line.
                    for (int z = 1; keepChecking; z++) {
                        try {
                            if (board[x + z][y].equals(redCharacter)) { //If the next index is also a red tile, the tilesInARow variable is incremented by 1.
                                tilesInARow++;
                            } else { //Else, the keep checking variable is set to false, breaking the loop.
                                keepChecking = false;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            keepChecking = false; //If the checking goes out of bounds, set keepChecking to false to break the loop.
                        }

                        if (tilesInARow == chainLength - 1) { //If the amount of tiles in a row matches the amount needed to win, return an int indicating which player wins.
                            return 1;
                        }
                    }

                    tilesInARow = 0;
                    keepChecking = true;
                    for (int z = 1; keepChecking; z++) {
                        try {
                            if (board[x][y + z].equals(redCharacter)) {
                                tilesInARow++;
                            } else {
                                keepChecking = false;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            keepChecking = false;
                        }

                        if (tilesInARow == chainLength - 1) {
                            return 1;
                        }
                    }

                    tilesInARow = 0;
                    keepChecking = true;
                    for (int z = 1; keepChecking; z++) {
                        try {
                            if (board[x + z][y + z].equals(redCharacter)) {
                                tilesInARow++;
                            } else {
                                keepChecking = false;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            keepChecking = false;
                        }

                        if (tilesInARow == chainLength - 1) {
                            return 1;
                        }
                    }

                    tilesInARow = 0;
                    keepChecking = true;
                    for (int z = 1; keepChecking; z++) {
                        try {
                            if (board[x + z][y - z].equals(redCharacter)) {
                                tilesInARow++;
                            } else {
                                keepChecking = false;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            keepChecking = false;
                        }

                        if (tilesInARow == chainLength - 1) {
                            return 1;
                        }
                    }
                }

                if (board[x][y].equals(yellowCharacter)) {
                    //check right, down, diagonal up, diagonal down for yellow

                    tilesInARow = 0;
                    keepChecking = true;
                    for (int z = 1; keepChecking; z++) {
                        try {
                            if (board[x + z][y].equals(yellowCharacter)) {
                                tilesInARow++;
                            } else {
                                keepChecking = false;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            keepChecking = false;
                        }

                        if (tilesInARow == chainLength - 1) {
                            return 2;
                        }
                    }

                    tilesInARow = 0;
                    keepChecking = true;
                    for (int z = 1; keepChecking; z++) {
                        try {
                            if (board[x][y + z].equals(yellowCharacter)) {
                                tilesInARow++;
                            } else {
                                keepChecking = false;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            keepChecking = false;
                        }

                        if (tilesInARow == chainLength - 1) {
                            return 2;
                        }
                    }

                    tilesInARow = 0;
                    keepChecking = true;
                    for (int z = 1; keepChecking; z++) {
                        try {
                            if (board[x + z][y + z].equals(yellowCharacter)) {
                                tilesInARow++;
                            } else {
                                keepChecking = false;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            keepChecking = false;
                        }

                        if (tilesInARow == chainLength - 1) {
                            return 2;
                        }
                    }

                    tilesInARow = 0;
                    keepChecking = true;
                    for (int z = 1; keepChecking; z++) {
                        try {
                            if (board[x + z][y - z].equals(yellowCharacter)) {
                                tilesInARow++;

                            } else {
                                keepChecking = false;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            keepChecking = false;
                        }

                        if (tilesInARow == chainLength - 1) {
                            return 2;
                        }
                    }
                }
            }
        }

        return 0;
    }

    private void instructions() {
        //TODO output instructions here.
    }

    private void about() {
        //TODO make an about page here.
    }

    private void quit() {
        //TODO quit the game here.
    }
}