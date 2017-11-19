package pl.edu.agh.tictactoe.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Ewa on 19.11.2017.
 */
public class Gui extends JFrame{
    //----------------------------------------------------------//
    //    CUSTOMIZE INITIAL TIC-TAC-TOE VIEW APPEARANCE HERE    //
    //----------------------------------------------------------//

    // General.
    private final String   APP_TITLE           = "TIC-TAC-TOE";
    private final String   APP_FONT            = "Sans Serif";
    private final int      APP_WIDTH           = 680;
    private final int      APP_HEIGHT          = 1024;
    private final int      APP_PADDING         = 25;

    // Color scheme.
    private final Color    BG_COLOR            = Color.WHITE;
    private final Color    BG_COLOR_2          = Color.decode( "#EFFFF7" );  // off white
    private final Color    TIC_TAC_NAVY        = Color.decode( "#34485D" );
    private final Color    TIC_TAC_GREEN       = Color.decode( "#19BC9C" );
    private final Color    TIC_TAC_RED         = Color.decode( "#E74C3C" );  // normal
    private final Color    TIC_TAC_RED_2       = Color.decode( "#D94334" );  // hover
    private final Color    TIC_TAC_RED_3       = Color.decode( "#EA6052" );  // press

    // FSU title bar.
    private final String   TITLE_TEXT          = "Tic-Tac-Toe";
    private final Color    TITLE_COLOR         = TIC_TAC_GREEN;
    private final int      TITLE_FONT_SIZE     = 35;
    private final int      TITLE_TOP_PAD       = 10;
    private final int      TITLE_BTM_PAD       = 20;
    // Gameboard
    private final int      GAME_BOARD_SIZE     = APP_WIDTH - APP_PADDING;
    private final Color    GAME_FONT_COLOR     = TIC_TAC_NAVY;
    private final int      GAME_FONT_SIZE      = 25;
    private final int      GAME_LINE_WIDTH     = 3;
    private final Color    GAME_LINE_COLOR     = TIC_TAC_GREEN;
    private final Color    GAME_HOVER_COLOR    = BG_COLOR_2;
    // Game status label
    private final String   STATUS_TEXT         = "X moves to start the game";
    private final int      STATUS_FONT_SIZE    = 20;
    private final Color    STATUS_COLOR        = TIC_TAC_NAVY;
    private final int      STATUS_TOP_PAD      = 30;
    private final int      STATUS_BTM_PAD      = 30;
    // Bottom buttons
    private final int      BTN_GAP             = 5;
    private final int      BTN_HGT             = 125;
    private final int      BTN_FONT_SIZE       = 16;
    private final Color    BTN_TEXT_COLOR      = Color.WHITE;
    private final String   NEW_GAME_BTN_TEXT   = "Start New Game";
    private final Color    RESET_BG_COLOR      = TIC_TAC_RED;
    private final Color    RESET_BG_HOVER      = TIC_TAC_RED_2;
    private final Color    RESET_BG_PRESS      = TIC_TAC_RED_3;


    //------------------------//
    //    TIC-TAC-TOE VIEW    //
    //------------------------//

    private JButton    square[][]        = new JButton[20][20];                         // gameboard
    private JLabel     gameStatusLabel   = new JLabel( STATUS_TEXT, JLabel.CENTER );  // game status
    private JButton    resetBtn          = new JButton();                             // new game button

    // Tic Tac Toe view constructor.
    // Sets up the view and adds all view components.
    public Gui()
    {
        // JFrame containing application.
        this.setTitle( APP_TITLE );
        this.setSize( APP_WIDTH, APP_HEIGHT );
        this.setMinimumSize( new Dimension( APP_WIDTH, APP_HEIGHT ));
        this.setMaximumSize( new Dimension( APP_WIDTH, APP_HEIGHT ));
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        // JPanel containing the tic-tac-toe game.
        JPanel ticTacPanel = new JPanel();
        ticTacPanel.setLayout( new BoxLayout( ticTacPanel, BoxLayout.PAGE_AXIS ));
        ticTacPanel.setSize( this.getContentPane().getWidth(), this.getContentPane().getHeight() );
        ticTacPanel.setBackground( BG_COLOR );
        this.add( ticTacPanel );

        // FSU Tic-Tac-Nole title bar.
        ticTacPanel.add( Box.createRigidArea( new Dimension( 0, TITLE_TOP_PAD )));  // pad top
        JLabel titleBarLabel = new JLabel( TITLE_TEXT, JLabel.CENTER );
        titleBarLabel.setForeground( TITLE_COLOR );
        titleBarLabel.setFont( new Font( APP_FONT, Font.BOLD, TITLE_FONT_SIZE ));
        titleBarLabel.setAlignmentX( Component.CENTER_ALIGNMENT );
        ticTacPanel.add( titleBarLabel );
        ticTacPanel.add( Box.createRigidArea( new Dimension( 0, TITLE_BTM_PAD )));  // pad bottom

        // Layered pane contains the game board and winner line
        JLayeredPane gameBoardOverlayHandler = new JLayeredPane();
        gameBoardOverlayHandler.setOpaque( false );
        gameBoardOverlayHandler.setPreferredSize( new Dimension( GAME_BOARD_SIZE, GAME_BOARD_SIZE ));
        gameBoardOverlayHandler.setMinimumSize( new Dimension( GAME_BOARD_SIZE, GAME_BOARD_SIZE ));
        gameBoardOverlayHandler.setMaximumSize( new Dimension( GAME_BOARD_SIZE, GAME_BOARD_SIZE ));
        gameBoardOverlayHandler.setLayout( new OverlayLayout( gameBoardOverlayHandler ));
        ticTacPanel.add( gameBoardOverlayHandler );

        // JPanel containing tic-tac-toe game board.
        JPanel gameBoard = new JPanel();
        gameBoard.setOpaque( false );
        gameBoard.setLayout( new GridLayout( 20, 20 ));
        gameBoardOverlayHandler.add( gameBoard );

        gameBoardOverlayHandler.setLayer( gameBoard, 1 );

        // Setup gameboard squares within the game board.
        for( int i = 0; i < 20; i++ ) {
            for( int j = 0; j < 20; j++ ) {
                // Square setup.
                square[i][j] = new JButton();
                square[i][j].setPreferredSize( new Dimension( GAME_BOARD_SIZE / 20, GAME_BOARD_SIZE / 20 ));
                square[i][j].setMinimumSize( new Dimension( GAME_BOARD_SIZE / 20, GAME_BOARD_SIZE / 20 ));
                square[i][j].setMaximumSize( new Dimension( GAME_BOARD_SIZE / 20, GAME_BOARD_SIZE / 20 ));
                square[i][j].putClientProperty("row", i);
                square[i][j].putClientProperty("col", j);
                square[i][j].setText(" ");
                square[i][j].setOpaque( true );
                square[i][j].setBackground( BG_COLOR );
                square[i][j].setForeground( GAME_FONT_COLOR );
                square[i][j].setFocusPainted(false);
                square[i][j].setFont( new Font( APP_FONT, Font.BOLD, GAME_FONT_SIZE ));
                int lw = GAME_LINE_WIDTH;
                Color lc = GAME_LINE_COLOR;

                // Square border.
                if ( i >= 0 & i <= 18 ) {
                    if ( j != 19 ) square[i][j].setBorder( BorderFactory.createMatteBorder( 0, 0, lw, lw, lc ));
                    if ( j == 19 ) square[i][j].setBorder( BorderFactory.createMatteBorder( 0, 0, lw, 0, lc ));
                } else {
                    if ( j != 19 ) square[i][j].setBorder( BorderFactory.createMatteBorder( 0, 0, 0, lw, lc ));
                    if ( j == 19 ) square[i][j].setBorderPainted( false );
                }
                gameBoard.add( square[i][j] ); // add the square
            }
        }

        // Game status label.
        ticTacPanel.add( Box.createRigidArea( new Dimension( 0, STATUS_TOP_PAD )));  // pad top
        gameStatusLabel.setForeground( STATUS_COLOR );
        gameStatusLabel.setFont( new Font( APP_FONT, Font.PLAIN, STATUS_FONT_SIZE ));
        gameStatusLabel.setAlignmentX( Component.CENTER_ALIGNMENT );
        ticTacPanel.add( gameStatusLabel );
        ticTacPanel.add( Box.createRigidArea( new Dimension( 0, STATUS_BTM_PAD )));  // pad bottom

        // JPanel containing game setting buttons.
        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque( false );
        btnPanel.setLayout( new GridLayout( 1, 2, BTN_GAP, BTN_GAP ) );
        btnPanel.setMaximumSize( new Dimension( GAME_BOARD_SIZE, APP_HEIGHT ));
        ticTacPanel.add( btnPanel );

        // Start new game button.
        resetBtn.setText( NEW_GAME_BTN_TEXT );
        resetBtn.setBorderPainted( false );
        resetBtn.setOpaque(true);
        resetBtn.setForeground( BTN_TEXT_COLOR );
        resetBtn.setBackground( RESET_BG_COLOR );
        resetBtn.setFocusPainted(false);
        resetBtn.setFont( new Font( APP_FONT, Font.BOLD, BTN_FONT_SIZE ));
        resetBtn.setMaximumSize( new Dimension( GAME_BOARD_SIZE, BTN_HGT ));
        resetBtn.setMinimumSize( new Dimension( GAME_BOARD_SIZE, BTN_HGT ));
        btnPanel.add( resetBtn );
        addNewGameButtonHoverState();


    } // end TicTacToeView() constructor

    //------------------------------------------------------------//
    //    VIEW UI UPDATES                                         //
    //    Allow controller to initiate UI updates of the view     //
    //------------------------------------------------------------//

    public void updateGameBoardUI( char[][] gameBoard )
    {
        for( int i = 0; i < 20; i++ ) {
            for( int j = 0; j < 20; j++ ) {
                square[i][j].setText( String.valueOf( gameBoard[i][j] ) );
            }
        }
    }

    public void updateSquareUIForHoverState( int row, int col )
    {
        square[row][col].setOpaque( true );
        square[row][col].setBackground( GAME_HOVER_COLOR );
    }

    public void updateSquareUIForNormalState( int row, int col )
    {
        square[row][col].setOpaque( false );
        square[row][col].setBackground( BG_COLOR );
    }


    public void updateGameStatusLabelText( String text )
    {
        gameStatusLabel.setText( text );
    }

    //------------------------------------------------------------------------------//
    //    EVENT LISTENERS                                                           //
    //    Allow the controller to handle events associated with a view component    //
    //    Display hover states on components                                        //
    //------------------------------------------------------------------------------//

    public void addGameBoardSquareButtonListener( ActionListener listenForSquareButtonClick )
    {
        for( int i = 0; i < 20; i++ ) {
            for( int j = 0; j < 20; j++ ) {
                square[i][j].addActionListener( listenForSquareButtonClick );
            }
        }
    }

    public void addGameBoardSquareButtonHoverListener( MouseAdapter listenForSquareButtonHover )
    {
        for( int i = 0; i < 20; i++ ) {
            for( int j = 0; j < 20; j++ ) {
                square[i][j].addMouseListener( listenForSquareButtonHover );
            }
        }
    }

    public void addNewGameButtonListener( ActionListener listenForNewGameButtonClick )
    {
        resetBtn.addActionListener( listenForNewGameButtonClick );
    }

    private void addNewGameButtonHoverState()
    {
        // Hover states - Game reset button.
        resetBtn.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseEntered( MouseEvent e ) {
                resetBtn.setBackground( RESET_BG_HOVER );
            }
            @Override
            public void mouseExited( MouseEvent e ) {
                resetBtn.setBackground( RESET_BG_COLOR );
            }
            @Override
            public void mousePressed( MouseEvent e ) {
                resetBtn.setBackground( RESET_BG_PRESS );
            }
            @Override
            public void mouseReleased( MouseEvent e ) {
                resetBtn.setBackground( RESET_BG_HOVER );
            }
        });
    }
}
