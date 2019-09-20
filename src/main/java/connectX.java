/*
File: connectX.java
Created: 13/09/2019
Author: Hisbaan Noorani
*/

import java.util.Scanner;

public class connectX {
    //Debug variable for tracing.
    public final boolean DEBUG = true;

    //Colours.
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    //Declaring + initializing variables.
    private Scanner sc = new Scanner(System.in);
    private boolean gameOver = false;
    private String[][] board = new String[7][6];
    private int chainLength = 4;
    private String redCharacter = ANSI_RED + "1" + ANSI_RESET;
    private String yellowCharacter = ANSI_YELLOW + "2" + ANSI_RESET;

    public static void main(String[] args) {
        new connectX();
    }

    private connectX() {
        mainMenu();
    }

    private void mainMenu() {
        System.out.println("Where would you like to go?\n\tA) Start game\n\tB) Instructions\n\tC) About\nAnswer: ");
//        System.out.println();
        boolean validInput;
        do { //Do while loop to insure valid input.
            switch (sc.nextLine().toLowerCase()) { //Switch on input to get the user's intentions.
                case "a":
                    validInput = true;
                    startGame();
                    break;
                case "b":
                    validInput = true;
                    instructions();
                    break;
                case "c":
                    validInput = true;
                    about();
                    break;
                default:
                    System.out.println("Invalid input. Please try again...");
                    validInput = false;
                    break;

            }
        } while (!validInput);
    }

    private void startGame() {
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 7; x++) {
                board[x][y] = "0";
            }
        }

        gameOver = false;

        while (!gameOver) {
            printBoard();
            winCheck();

            getPlayerOneInput();
            printBoard();
            winCheck();

            getPlayerTwoInput();
        }

        //TODO go back to the main menu
    }

    private void winCheck() {
        if (checkWinCondition() == 1) {
            gameOver = true;
            System.out.println("Player 1 wins!");
        } else if (checkWinCondition() == 2) {
            gameOver = true;
            System.out.println("Player 2 wins!");
        }
    }

    private void getPlayerOneInput() {
        boolean inputValid;
        int choice;

        do {
            System.out.println("\nPlayer 1, select a column: ");
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

    private void getPlayerTwoInput() {
        boolean inputValid;
        int choice;

        do {
            System.out.println("\nPlayer 2, select a column: ");

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

    private int lineCheck(int choice) { //checks what value to place the
        for (int y = 5; y >= 0; y--) {
            if (board[choice][y].equals("0")) {
                return y;
            }
        }

        return -1;
    }

    private int checkWinCondition() {
        boolean keepChecking = true;

        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 7; x++) {
                if (board[x][y].equals(redCharacter)) {
                    for (int z = 0; keepChecking; z++) {
                        try {
                            keepChecking = board[x + z][y].equals(redCharacter);
                        } catch (ArrayIndexOutOfBoundsException e) {

                        }
                        if(z == chainLength) {
                            System.out.println("Red wins");
                            return 1;
                        }
                    }
                    for (int z = 0; keepChecking; z++) {
                        try {
                            keepChecking = board[x][y + z].equals(redCharacter);
                        } catch (ArrayIndexOutOfBoundsException e) {

                        }
                        if(z == chainLength) {
                            System.out.println("Red wins");
                            return 1;
                        }
                    }
                    for(int z = 0; keepChecking; z++) {
                        try {
                            keepChecking = board[x + z][y + z].equals(redCharacter);
                        } catch (ArrayIndexOutOfBoundsException e) {

                        }
                        if(z == chainLength) {
                            System.out.println("Red wins");
                            return 1;
                        }
                    }
                    for(int z = 0; keepChecking; z++) {
                        try {
                            keepChecking = board[x + z][y - z].equals(redCharacter);
                        } catch (ArrayIndexOutOfBoundsException e) {

                        }
                        if(z == chainLength) {
                            System.out.println("Red wins");
                            return 1;
                        }
                    }
                }
            }
        }
        //if 1 wins, return 1
        //if 2 wins, return 2

        return 0;
    }

    private void instructions() {
        //TODO output instructions here
    }

    private void about() {
        //TODO make an about page here
    }
}