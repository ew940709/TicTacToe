package pl.edu.agh.tictactoe.model;

/**
 * Created by Ewa on 19.11.2017.
 */
public class Model {

    private char[][]  gameBoard = new char[20][20];  // game board
    private char playerToMove;
    private boolean gameIsComplete;
    private char gameWinner;
    private int moveCount;


    public Model(){
        startNewGame();
    }


    public char[][] getGameBoard() { return gameBoard; }
    public char getPlayerToMove() { return  playerToMove; }
    public boolean  gameIsComplete()      { return gameIsComplete; }
    public char     getGameWinner()       { return gameWinner; }


    public void startNewGame(){
        playerToMove = 'x';  // x always first
        gameIsComplete = false;
        gameWinner= ' ';
        moveCount = 0;
        resetGameBoard();
    }

    // Returns true if a square is already played.
    public boolean squareHasBeenPlayed( int row, int col )
    {
        return gameBoard[row][col] == 'x' || gameBoard[row][col] == 'o';
    }


    // Makes a move in a game board square.
    public void makeMoveInSquare( int row, int col )
    {
        gameBoard[row][col] = playerToMove;           // make the move in the game board model

        if ( playerToMove == 'x' )                    // update the player to move
            playerToMove = 'o';
        else if ( playerToMove == 'o' )
            playerToMove = 'x';

        ++moveCount;
        if ( rowWins() || colWins() || diagWins() ) gameIsComplete = true;
        if ( moveCount == 400) gameIsComplete = true;

    }

    // Sets the game board to its default empty state.
    private void resetGameBoard()
    {
        for ( int i = 0; i < 20; i++ ) {
            for ( int j = 0; j < 20; j++ ) {
                gameBoard[i][j] = ' ';
            }
        }
    }


    // Returns true if a winning row is found.
    // Sets the winner and the win path.
    private boolean rowWins()
    {
        for ( int i = 0; i < 20; i++ ) {
            char rowsOfSymbol = ' ';
            int count = 0;
            for ( int j = 0; j < 20; j++ ) {

                if(gameBoard[i][j] != ' '){
                    rowsOfSymbol = gameBoard[i][j];
                    count = 1;
                    for(int k = 1; k < 5; k++){
                        if(j+k < 20 && gameBoard[i][j+k] == rowsOfSymbol){
                            count++;
                        }
                        else{
                            count = 0;
                            break;
                        }
                    }

                    if ( count ==  5) {
                        if ( rowsOfSymbol == 'x' ) gameWinner = 'x';
                        if ( rowsOfSymbol == 'o' ) gameWinner = 'o';
                        return true;
                    }
                }

            }


        }
        return false;
    } // end rowWins()

    // Returns true if a winning column is found.
    // Sets the winner and the win path.
    private boolean colWins()
    {
        for ( int i = 0; i < 20; i++ ) {
            char rowsOfSymbol = ' ';
            int count = 0;
            for ( int j = 0; j < 20; j++ ) {
                if(gameBoard[j][i] != ' '){
                    rowsOfSymbol = gameBoard[j][i];
                    count = 1;
                    for(int k = 1; k < 5; k++){
                        if(j+k < 20 && gameBoard[j+k][i] == rowsOfSymbol){
                            count++;
                        } else{
                            count = 0;
                            break;
                        }
                    }

                    if ( count ==  5) {
                        if ( rowsOfSymbol == 'x' ) gameWinner = 'x';
                        if ( rowsOfSymbol == 'o' ) gameWinner = 'o';
                        return true;
                    }
                }
            }

        }
        return false;
    } // end colWins()

    // Returns true if a winning diagonal is found.
    // Sets the winner and the win path.
    private boolean diagWins()
    {
        for ( int i = 0; i < 20; i++ ) {
            char rowsOfSymbol = ' ';
            int count = 0;
            for (int j = 0; j < 20; j++) {
                if (gameBoard[i][j] != ' ') {
                    rowsOfSymbol = gameBoard[i][j];
                    count = 1;
                    for (int k = 1; k < 5; k++) {
                        if (i + k < 20 && j + k < 20 && gameBoard[i+k][j+k] == rowsOfSymbol) {
                            count++;
                        } else {
                            count = 0;
                            break;
                        }
                    }

                    if (count == 5) {
                        if (rowsOfSymbol == 'x') gameWinner = 'x';
                        if (rowsOfSymbol == 'o') gameWinner = 'o';
                        return true;
                    }
                }
            }

        }

            return false;
    } // end diagWins()
}
