/*
File: connectX.java
Created: 13/09/2019
Author: Hisbaan Noorani
*/

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class connectX {
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
    private int counter;

    private String boardHeader;

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
        System.out.println("Please resize the window until the above word reads \"CONNECT 4\"");
        System.out.println("Restart the program (if you are using repl.it) after resizing the window");
        System.out.println("Press Enter/Return to continue:");
        format();
        try {
            System.in.read(); //Pausing the runtime until the user presses Enter/Return.
        } catch (IOException e) {

        }
        clear();
    }

    //Method that holds the code for the main menu.
    private void mainMenu() {
        boolean validInput; //Variable to ensure that the input is valid.


        do { //Do while loop to insure valid input.

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
                    validInput = true;
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
        boolean inputValid; //Variable to ensure that input is valid.

        do { //Do while loop to ensure that input is valid.
            clear();
            System.out.println("What game-mode would you like to play?\n\t[1] Traditional\n\t[2] 10 X 7\n\t[3] 10 X 10\n\t[4] 5-in-a-row");
            format();

            String choice = sc.nextLine();
            clear();

            //Switch statement that allows for a choice of the game-mode the user would like to play, then sets the correct constants for said game-mdoe.
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
    }

    //Method to run the sequence of code that starts the game.
    private void startGame() {
        board = new Tile[boardXValue][boardYValue]; //Initializing the board array.

        //Block of code to setup the header for the board the default will look like this:
        /*
        1 2 3 4 5 6 7
        -------------
        Game board goes under the header
         */
        //This header helps the user know which column they are dropping their tile into.
        boardHeader = "\n\n";
        for (int i = 1; i < boardXValue + 1; i++) {
            boardHeader += "" + i + " ";
        }
        boardHeader += "\n";
        for (int i = 0; i < boardXValue; i++) {
            boardHeader += "--";
        }
        boardHeader = boardHeader.substring(0, boardHeader.length() - 1);

        //Resetting the counter variable to ensure that player 1 always goes first.
        counter = 0;

        //Resetting the game over variable so that the game will not end immediately.
        gameOver = false;

        //For loop that sets all indexes to "0" so the game starts correctly.
        for (int y = 0; y < boardYValue; y++) {
            for (int x = 0; x < boardXValue; x++) {
                board[x][y] = new Tile();
                board[x][y].player = 0;
                board[x][y].representation = "0";
            }
        }

        //While loop that sets the sequence of events for the game (i.e. printing --> checking if the board is full --> checking for win --> getting player one input --> printing --> checking if the board is full --> getting player two input --> repeat)
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

        //The game will return to the main menu after the game over screen.
        mainMenu();
    }

    //Method that checks for a win and prints a statement accordingly.
    private void winCheck() {
        //If player 1 wins, end the game and print the player 1 win screen.
        if (checkWinCondition() == 1) {
            gameOver = true;
            clear();
            printBoard();
            System.out.println(ANSI_RED + "\nPLAYER 1 WINS!" + ANSI_RESET);
            System.out.println("\n\nPress Enter/Return to continue:");
            format();
            try {
                System.in.read(); //Pauses runtime until the user presses Enter/Return.
            } catch (IOException e) {
            }
            clear();

            //If player 2 wins, end the game and print the player 2 win screen.
        } else if (checkWinCondition() == 2) {
            gameOver = true;
            clear();
            printBoard();
            System.out.println(ANSI_YELLOW + "\nPLAYER 2 WINS!" + ANSI_RESET);
            System.out.println("\n\nPress Enter/Return to continue:");
            format();
            try {
                System.in.read(); //Pauses runtime until the user presses Enter/Return.
            } catch (IOException e) {
            }
            clear();
        }
    }

    //Method to collect player input.
    private void getPlayerInput() {
        //Declaring Variables.
        String playerCharacter; //Variable for the character representing either player 1 or player 2.

        boolean inputValid; //Variable used to ensure that the input is valid.
        boolean exceptionThrown; //Another variable used to ensure that the input is valid.
        boolean columnFull = false; //Variable to track if the column that the user selects is full or not.

        int choice = 0; //Variable for the user's choice.
        int playerNumber; //Variable corresponding to the current player's turn (1 and 2).

        counter++; //Increments the counter to switch between player 1 and player 2.
        if (counter % 2 == 0) { //If player 2, set variables to correspond to player 2.
            playerCharacter = yellowCharacter;
            playerNumber = 2;
        } else { //If player 1, set variables to correspond to player 1.
            playerCharacter = redCharacter;
            playerNumber = 1;
        }

        //Do while loop to ensure that input is valid.
        do {
            //Another do while to ensure that the input is valid.
            do {
                System.out.println("\nPlayer " + playerCharacter + ", select a column: ");
                format();
                exceptionThrown = false;
                try {
                    choice = sc.nextInt(); //Gets user input.
                    clear();
                } catch (InputMismatchException e) { //If input is not an integer, this block of code is triggered, asking the user to try again.
                    clear();
                    System.out.println("\nInvalid input. Please enter an INTEGER from 1 to " + boardXValue + "...\n");
                    printBoard();
                    sc.next();
                    exceptionThrown = true;
                }
            } while (exceptionThrown);

            //If the choice is withing the valid integers... else ask the user to retry.
            if (choice <= boardXValue && choice >= 1) {
                choice--;
                inputValid = true;

                //If the column is full, ask the user to try another column.
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
                System.out.println("\nInvalid input. Please enter a number from 1 to " + boardXValue + "...\n");
                printBoard();
                inputValid = false;
            }

        } while (!inputValid || columnFull);

        //Sets the y value that the tile has to go to as the temp variable, y.
        int y = lineCheck(choice);

        //Sets the corresponding tile to the correct character.
        board[choice][y].player = playerNumber;
        board[choice][y].representation = playerCharacter;

    }

    //Method that prints the board.
    private void printBoard() {
        System.out.println(boardHeader);

        for (int y = 0; y < boardYValue; y++) {
            for (int x = 0; x < boardXValue; x++) {
                System.out.print(board[x][y].representation);
                System.out.print(" ");
            }
            System.out.println();
        }

    }

    //Methods that checks what value x value to place the tile at.
    private int lineCheck(int choice) {
        //Starts at the bottom of the column, then works its way up until it finds an empty space, then returns said space.
        for (int y = (boardYValue - 1); y >= 0; y--) {
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
        for (int y = 0; y < boardYValue; y++) {
            for (int x = 0; x < boardXValue; x++) {
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

    //Method to check if the entire grid is full.
    private void checkFull() {
        boolean full = true;

        //Checks the top piece of each column and if none of them are 0, then the grid is full.
        for (int x = 0; x < boardXValue; x++) {
            if (board[x][0].player == 0) {
                full = false;
                break;
            }
        }

        //If the grid is full, end the game.
        if (full) {
            System.out.println("\n\nTIE!\nThe board is full.");
            format();
            gameOver = true;
        }
    }

    //Method to clear the screen with 100 blank lines.
    private void clear() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    //Method to line up the board to the approximate center by printing 10 lines under it, pushing it up.
    private void format() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }

    //Method that prints the instructions.
    private void instructions() {
        clear();
        System.out.println(ANSI_CYAN + "Summary: \n" + ANSI_RESET +
                "\n" +
                "Connect 4 is a simple, 2 player strategy game which can be understood and played by just about anyone.\nIt is similar to tic-tac-toe but with some slightly different mechanics. \nPieces (Tiles) will fall to the bottom of the column in which they are placed and you (in the Traditional game) must connect 4 of these tiles in order to win.\n" +
                "\n" +
                ANSI_CYAN + "Instructions:\n" + ANSI_RESET +
                "\n" +
                "To navigate the menu, type in the number associated with the action you would like to perform followed by pressing Enter/Return.\n" +
                "\n" +
                "To Place a tile, type in the number associated with the column (number above the column) followed by pressing Enter/Return.\n" +
                "\n" +
                "The piece will drop into the desired column.\n" +
                "\n" +
                "The piece will land on top of the other tiles present in that column.\n" +
                "\n" +
                "It is then your opponent’s turn.\n" +
                "\n" +
                "When either of you manages to get 4 in a row (or 5 if you are playing that mode), the game will highlight the winning tiles and state which player has won.\n" +
                "\n" +
                "The game will return to the main menu, where you can start again if you wish.\n\n");
        System.out.println("\nPress Enter/Return to continue:");

        try {
            System.in.read(); //Pauses the runtime until the user presses Enter/Return.
        } catch (IOException e) {

        }
        clear();
        mainMenu();
    }

    //Method that prints the about screen.
    private void about() {
        System.out.println(ANSI_CYAN + "About:\n" + ANSI_RESET +
                ANSI_RED + "Author:" + ANSI_RESET + "Hisbaan Noorani\n" +
                ANSI_RED  + "Play Testers:" + ANSI_RESET + "Kendra Gordon, Benjamin Bortolotto\n" +
                ANSI_RED  + "Start Date:" + ANSI_RESET + " 13/09/2019\n" +
                ANSI_RED  + "End Date:" + ANSI_RESET + " 27/09/2019\n\n");
        System.out.println("\nPress Enter/Return to continue:");

        try {
            System.in.read(); //Pauses the runtime until the user presses Enter/Return.
        } catch (IOException e) {

        }
        clear();
        mainMenu();
    }

    //Method that says farewell to the user, closes the scanner, and then exits the game.
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