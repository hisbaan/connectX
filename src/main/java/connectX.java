/*
File: connectX.java
Created: 13/09/2019
Author: Hisbaan Noorani
*/

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class connectX {
    //Debug variable for tracing.
    public static final boolean DEBUG = true;

    //Colours.
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001b[36m";

    //Coloured characters.
    private static final String redCharacter = ANSI_RED + "1" + ANSI_RESET;
    private static final String yellowCharacter = ANSI_YELLOW + "2" + ANSI_RESET;

    //Declaring + initializing variables.
    private Scanner sc = new Scanner(System.in);
    private boolean gameOver = false;
    private int chainLength;
    private int boardXValue;
    private int boardYValue;

    String boardHeader;

    private Tile[][] board;

    public static void main(String[] args) {
        new connectX();
    }

    private connectX() {
        printFiglet();
        mainMenu();
    }

    private void printFiglet() {
        clear();
        System.out.println("\n" +
                " ██████╗ ██████╗ ███╗   ██╗███╗   ██╗███████╗ ██████╗████████╗    ██╗  ██╗\n" +
                "██╔════╝██╔═══██╗████╗  ██║████╗  ██║██╔════╝██╔════╝╚══██╔══╝    ██║  ██║\n" +
                "██║     ██║   ██║██╔██╗ ██║██╔██╗ ██║█████╗  ██║        ██║       ███████║\n" +
                "██║     ██║   ██║██║╚██╗██║██║╚██╗██║██╔══╝  ██║        ██║       ╚════██║\n" +
                "╚██████╗╚██████╔╝██║ ╚████║██║ ╚████║███████╗╚██████╗   ██║            ██║\n" +
                " ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝  ╚═══╝╚══════╝ ╚═════╝   ╚═╝            ╚═╝\n" +
                "                                                                          \n");

        System.out.println("Press Enter/Return to continue:");
        format();
        try {
            System.in.read();
        } catch (IOException e) {

        }
        clear();
    }

    //Method that holds the code for the main menu.
    private void mainMenu() {
        boolean validInput = true;


        do { //Do while loop to insure valid input.

//            clear();
            System.out.println("\n\nWhere would you like to go?\n\t[1] Start game\n\t[2] Instructions\n\t[3] About\n\t[4] Quit");
            format();

            String choice = sc.nextLine();
            clear();

            switch (choice) { //Switch on input to get the user's intentions.
                case "1":
                    validInput = true;
                    selectSize();
                    startGame();
                    break;
                case "2":
                    validInput = true;
                    instructions();
                    break;
                case "3":
                    validInput = true;
                    about();
                    break;
                case "4":
                    quit();
                    break;
                default:
                    System.out.println("\nInvalid input. Please enter a number between 1 and 4...\n\n");
                    format();
                    validInput = false;
                    break;
            }
        } while (!validInput);
    }


    private void selectSize() {
        boolean inputValid = true;

        do {
            clear();
            System.out.println("What game-mode would you like to play?\n\t[1] Traditional\n\t[2] 10 X 7\n\t[3] 10 X 10\n\t[4] 5-in-a-row");
            format();

            String choice = sc.nextLine();
            clear();

            switch (choice) {
                case "1":
                    boardXValue = 7;
                    boardYValue = 6;
                    chainLength = 4;
                    inputValid = true;
                    break;
                case "2":
                    boardXValue = 10;
                    boardYValue = 7;
                    chainLength = 4;
                    inputValid = true;
                    break;
                case "3":
                    boardXValue = 10;
                    boardYValue = 10;
                    chainLength = 4;
                    inputValid = true;
                    break;
                case "4":
                    boardXValue = 9;
                    boardYValue = 6;
                    chainLength = 5;
                    inputValid = true;
                    break;
                default:
                    System.out.println("Invalid input, please enter a number between 1 and 4...");
                    inputValid = false;
                    break;

            }
        } while (!inputValid);

        board = new Tile[boardXValue][boardYValue];
        boardHeader = "\n\n";

        for(int i = 1; i < boardXValue + 1; i++) {
            boardHeader += "" + i + "  ";
        }
        boardHeader += "\n";
        for(int i = 0; i < boardXValue; i++) {
            boardHeader += "---";
        }
        boardHeader = boardHeader.substring(0, boardHeader.length() - 2);
    }

    //Method to run the sequence of code that starts the game.
    private void startGame() {
        counter = 0;
        //For loop that sets all indexes to "0" so the game starts correctly.
        for (int y = 0; y < boardYValue; y++) {
            for (int x = 0; x < boardXValue; x++) {
                board[x][y] = new Tile();
                board[x][y].player = 0;
                board[x][y].representation = "0";
            }
        }

        gameOver = false;

        //While loop that sets the sequence of events for the game (i.e. printing --> checking for win --> getting player one input --> printing --> getting player two input --> repeat)
        while (!gameOver) {
            printBoard();
            checkFull();

            winCheck();
            if (gameOver) break;

            getPlayerInput();
            printBoard();
            checkFull();

            winCheck();
            if (gameOver) break;

            getPlayerInput();
        }

        mainMenu();
    }

    //Method that checks for a win and prints a statement accordingly.
    private void winCheck() {
        if (checkWinCondition() == 1) {
            gameOver = true;
            clear();
            printBoard();
            System.out.println(ANSI_RED + "\nPLAYER 1 WINS!" + ANSI_RESET);
            System.out.println("\n\nPress Enter/Return to continue:");
            format();
            try {
                System.in.read();
            } catch (IOException e) {

            }
            clear();
        } else if (checkWinCondition() == 2) {
            gameOver = true;
            clear();
            printBoard();
            System.out.println(ANSI_YELLOW + "\nPLAYER 2 WINS!" + ANSI_RESET);
            System.out.println("\n\nPress Enter/Return to continue:");
            format();
            try {
                System.in.read();
            } catch (IOException e) {

            }
            clear();
        }
    }

    private int counter = 0;

    private void getPlayerInput() {
        String playerCharacter;
        boolean inputValid;
        boolean exceptionThrown;
        boolean columnFull = false;
        int choice = 0;
        int playerNumber;

        counter++;
        if (counter % 2 == 0) {
            playerCharacter = yellowCharacter;
            playerNumber = 2;
        } else {
            playerCharacter = redCharacter;
            playerNumber = 1;
        }

        do {
            do {
                System.out.println("\nPlayer " + playerCharacter + ", select a column: ");
                format();
                exceptionThrown = false;
                try {
                    choice = sc.nextInt();
                    clear();
                } catch (InputMismatchException e) {
                    clear();
                    System.out.println("\nInvalid input. Please enter an INTEGER from 1 to " + boardYValue + "...\n");
                    printBoard();
                    sc.next();
                    exceptionThrown = true;
                }
            } while (exceptionThrown);

            if (choice <= boardXValue && choice >= 1) {
                choice--;
                inputValid = true;

                if (lineCheck(choice) == -1) {
                    columnFull = true;
                    clear();
                    System.out.println("\nColumn is full, please try another column...\n");
                    printBoard();
                } else {
                    columnFull = false;
                }
            } else {
                clear();
                System.out.println("\nInvalid input. Please enter a number from 1 to " + boardYValue + "...\n");
                printBoard();
                inputValid = false;
            }

        } while (!inputValid || columnFull);

        int boardYValue = lineCheck(choice);

        board[choice][boardYValue].player = playerNumber;
        board[choice][boardYValue].representation = playerCharacter;

    }

    //Method that prints the board.
    private void printBoard() {
        System.out.println(boardHeader);

        for (int y = 0; y < boardYValue; y++) {
            for (int x = 0; x < boardXValue; x++) {
                System.out.print(board[x][y].representation);
                System.out.print("  ");
            }
            System.out.println();
        }

    }

    private int lineCheck(int choice) { //Checks what value x value to place the tile at.
        for (int y = 5; y >= 0; y--) {
            if (board[choice][y].player == 0) {
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
                if (board[x][y].player == 1) { //Checks if the specific index is a red tile.
                    //Check right, down, diagonal up, diagonal down for red.

                    //Resetting variables.
                    tilesInARow = 0;
                    keepChecking = true;

                    //The proceeding 8 blocks of code are all relatively similar so I'm only commenting the first one.

                    //For loop to where z acts as a counter to check the next tile in line.
                    for (int z = 1; keepChecking; z++) {
                        try {
                            if (board[x + z][y].player == 1) { //If the next index is also a red tile, the tilesInARow variable is incremented by 1.
                                tilesInARow++;
                            } else { //Else, the keep checking variable is set to false, breaking the loop.
                                keepChecking = false;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            keepChecking = false; //If the checking goes out of bounds, set keepChecking to false to break the loop.
                        }

                        if (tilesInARow == chainLength - 1) { //If the amount of tiles in a row matches the amount needed to win, return an int indicating which player wins.
                            for (int i = 0; i < chainLength; i++) {
                                board[x + i][y].representation = "" + ANSI_CYAN + board[x][y].player + ANSI_RESET; //TODO come back
                            }
                            return 1;
                        }
                    }

                    tilesInARow = 0;
                    keepChecking = true;
                    for (int z = 1; keepChecking; z++) {
                        try {
                            if (board[x][y + z].player == 1) {
                                tilesInARow++;
                            } else {
                                keepChecking = false;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            keepChecking = false;
                        }

                        if (tilesInARow == chainLength - 1) {
                            for (int i = 0; i < chainLength; i++) {
                                board[x][y + i].representation = "" + ANSI_CYAN + board[x][y].player + ANSI_RESET; //TODO come back
                            }
                            return 1;
                        }
                    }

                    tilesInARow = 0;
                    keepChecking = true;
                    for (int z = 1; keepChecking; z++) {
                        try {
                            if (board[x + z][y + z].player == 1) {
                                tilesInARow++;
                            } else {
                                keepChecking = false;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            keepChecking = false;
                        }

                        if (tilesInARow == chainLength - 1) {
                            for (int i = 0; i < chainLength; i++) {
                                board[x + i][y + i].representation = "" + ANSI_CYAN + board[x][y].player + ANSI_RESET; //TODO come back
                            }
                            return 1;
                        }
                    }

                    tilesInARow = 0;
                    keepChecking = true;
                    for (int z = 1; keepChecking; z++) {
                        try {
                            if (board[x + z][y - z].player == 1) {
                                tilesInARow++;
                            } else {
                                keepChecking = false;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            keepChecking = false;
                        }

                        if (tilesInARow == chainLength - 1) {
                            for (int i = 0; i < chainLength; i++) {
                                board[x + i][y - i].representation = "" + ANSI_CYAN + board[x][y].player + ANSI_RESET; //TODO come back
                            }
                            return 1;
                        }
                    }
                }

                if (board[x][y].player == 2) {
                    //check right, down, diagonal up, diagonal down for yellow

                    tilesInARow = 0;
                    keepChecking = true;
                    for (int z = 1; keepChecking; z++) {
                        try {
                            if (board[x + z][y].player == 2) {
                                tilesInARow++;
                            } else {
                                keepChecking = false;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            keepChecking = false;
                        }

                        if (tilesInARow == chainLength - 1) {
                            for (int i = 0; i < chainLength; i++) {
                                board[x + i][y].representation = "" + ANSI_CYAN + board[x][y].player + ANSI_RESET; //TODO come back
                            }
                            return 2;
                        }
                    }

                    tilesInARow = 0;
                    keepChecking = true;
                    for (int z = 1; keepChecking; z++) {
                        try {
                            if (board[x][y + z].player == 2) {
                                tilesInARow++;
                            } else {
                                keepChecking = false;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            keepChecking = false;
                        }

                        if (tilesInARow == chainLength - 1) {
                            for (int i = 0; i < chainLength; i++) {
                                board[x][y + i].representation = "" + ANSI_CYAN + board[x][y].player + ANSI_RESET; //TODO come back
                            }
                            return 2;
                        }
                    }

                    tilesInARow = 0;
                    keepChecking = true;
                    for (int z = 1; keepChecking; z++) {
                        try {
                            if (board[x + z][y + z].player == 2) {
                                tilesInARow++;
                            } else {
                                keepChecking = false;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            keepChecking = false;
                        }

                        if (tilesInARow == chainLength - 1) {
                            for (int i = 0; i < chainLength; i++) {
                                board[x + i][y + i].representation = "" + ANSI_CYAN + board[x][y].player + ANSI_RESET; //TODO come back
                            }
                            return 2;
                        }
                    }

                    tilesInARow = 0;
                    keepChecking = true;
                    for (int z = 1; keepChecking; z++) {
                        try {
                            if (board[x + z][y - z].player == 2) {
                                tilesInARow++;

                            } else {
                                keepChecking = false;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            keepChecking = false;
                        }

                        if (tilesInARow == chainLength - 1) {
                            for (int i = 0; i < chainLength; i++) {
                                board[x + i][y - i].representation = "" + ANSI_CYAN + board[x][y].player + ANSI_RESET; //TODO come back
                            }
                            return 2;
                        }
                    }
                }
            }
        }

        return 0;
    }

    private void checkFull() {
        boolean full = true;

        for (int x = 0; x < boardXValue; x++) {
            if (board[x][0].player == 0) {
                full = false;
            }
        }

        if (full) {
            System.out.println("TIE!\nThe board is full.");
            format();
            gameOver = true;
        }
    }

    private void clear() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    private void format() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }

    private void instructions() {
        //TODO output instructions here.
    }

    private void about() {
        //TODO make an about page here.
    }

    private void quit() {
        clear();
        System.out.println("Thank you for playing!\n\n");
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {

        }
        System.out.println("\nGood Bye!");
        format();
        sc.close();
        System.exit(0);
    }
}