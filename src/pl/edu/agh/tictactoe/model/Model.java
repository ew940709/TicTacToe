package pl.edu.agh.tictactoe.model;

/**
 * Created by Ewa on 19.11.2017.
 */
public class Model {

    private char[][]  gameBoard = new char[20][20];  // game board
    private char playerToMove;
    private boolean gameIsComplete;
    private char gameWinner;


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
        resetGameBoard();
    }

    // Returns true if a square is already played.
    public boolean squareHasBeenPlayed( int row, int col )
    {
        return gameBoard[row][col] != 'x' && gameBoard[row][col] != 'o' ? false : true;
    }


    // Makes a move in a game board square.
    public void makeMoveInSquare( int row, int col )
    {
        gameBoard[row][col] = playerToMove;           // make the move in the game board model

        if ( playerToMove == 'x' )                    // update the player to move
            playerToMove = 'o';
        else if ( playerToMove == 'o' )
            playerToMove = 'x';

       //TODO Check if it is the end of the game
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
}
