package pl.edu.agh.tictactoe.controller;

import pl.edu.agh.tictactoe.gui.Gui;
import pl.edu.agh.tictactoe.model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Ewa on 19.11.2017.
 */
public class GameController {

    //--------------------------------------------------------------------//
    //    COMPONENT LABELS                                                //
    //    The controller is responsible for updating the views labels.    //
    //    Customization of the update messages can be done here.          //
    //--------------------------------------------------------------------//

    private final String  STATUS_START        = "X moves to start the game";
    private final String  STATUS_CATS         = "Cat's game";
    private final String  STATUS_X_WINS       = "X wins the game!";
    private final String  STATUS_O_WINS       = "O wins the game!";
    private final String  STATUS_X_MOVES      = "X to move";
    private final String  STATUS_O_MOVES      = "O to move";
    private final String  STATUS_CP_MOVES     = "Computer is thinking...";

    //------------------------------//
    //    TIC-TAC-TOE CONTROLLER    //
    //------------------------------//

    private Gui view;
    private Model model;

    // Tic Tac Toe controller constructor.
    // Provides controller with access to the model & view and adds view event listeners.
    public GameController( Gui view, Model model )
    {
        this.view = view;
        this.model = model;

        this.view.addGameBoardSquareButtonListener( new SquareListener() );
        this.view.addGameBoardSquareButtonHoverListener( new SquareHoverListener() );
        this.view.addNewGameButtonListener( new NewGameListener() );
    }

    //-----------------//
    //    LISTENERS    //
    //-----------------//

    // Class SquareListener.
    // Handles game board square clicks from the view.
    private class SquareListener implements ActionListener
    {
        // Used to prevent user moves while computer moves are in progress.
        private boolean blockMove = false;

        @Override // A Square has been clicked
        public void actionPerformed( ActionEvent e )
        {
            if ( !blockMove ) {
                String gameStatus;  // string will store game status to update view label.
                JButton square = (JButton) e.getSource();
                int row = (Integer) square.getClientProperty("row");  // store square identifiers
                int col = (Integer) square.getClientProperty("col");  // to pass to model

                // Prevent square interaction if game is complete or square has been played.
                if ( model.gameIsComplete() ) return;
                if ( model.squareHasBeenPlayed( row, col )) return;

                // Tell model to make the move since the square is empty.
                model.makeMoveInSquare( row, col );

                if ( model.getPlayerToMove() == 'x' ) {
                    gameStatus = STATUS_X_MOVES;
                } else {
                    gameStatus = STATUS_O_MOVES;
                }

                // Ask model if game is complete so we can update the game status for that scenario.
                if ( model.gameIsComplete() ) {
                    if ( model.getGameWinner() == ' ' ) gameStatus = STATUS_CATS;
                    if ( model.getGameWinner() == 'x' ) gameStatus = STATUS_X_WINS;
                    if ( model.getGameWinner() == 'o' ) gameStatus = STATUS_O_WINS;
                }

                // Update the view UI to display results of the move.
                view.updateGameStatusLabelText( gameStatus );
                view.updateGameBoardUI( model.getGameBoard() );

            } // end if (!blockMove)
        } // end SquareListener actionPerformed

    } // end class SquareListener

    // Class SquareHoverListener
    // Controls game board square hover state. Hover states are prevented if
    // the game is not in progress or a square has already been played.
    private class SquareHoverListener extends MouseAdapter {

        @Override
        public void mouseEntered( MouseEvent e ) {
            JButton square = (JButton) e.getSource();
            int row = (Integer) square.getClientProperty("row");
            int col = (Integer) square.getClientProperty("col");
            if ( !model.gameIsComplete() && !model.squareHasBeenPlayed(row, col) ) {
                view.updateSquareUIForHoverState( row, col );
            }
        }

        @Override
        public void mouseExited( MouseEvent e ) {
            JButton square = (JButton) e.getSource();
            int row = (Integer) square.getClientProperty("row");
            int col = (Integer) square.getClientProperty("col");
            view.updateSquareUIForNormalState( row, col );
        }

    } // end class SquareHoverListener

    // Class NewGameListener.
    // Handles clicks of the Start New Game button.
    class NewGameListener implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent e )
        {
            model.startNewGame();                            // reset model
            view.updateGameBoardUI( model.getGameBoard() );  // reset view gameboard
            view.updateGameStatusLabelText( STATUS_START );  // reset view game status label
        }

    } // end class NewGameButtonListener
}
