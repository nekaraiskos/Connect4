/*
 * This class connects the mouse to the game's board. When the player double clicks on a column and it's the player's turn,
 * the game tries to place a piece there.
 */
package connect4;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
// import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

/**
 *
 * @author nekaraiskos
 */
public class BoardMouseListener implements MouseListener{
    MaximizerTreeNode root;
    Connect4GridComponent component;
    HistoryHandler historyHandler;
    Connect4 connect4;
    int width;
    int checkButton, checkPiece, checkBoard;

    
    public BoardMouseListener(Connect4 connect4, MaximizerTreeNode root){
        this.root = root;
        this.connect4 = connect4;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

        //If it's the player's turn and he double clicks on the board, act accordingly
        if(connect4.boardKeyListener.nextPlayer == 0) {
        if(e.getClickCount() == 2) {
            int coordinates = e.getX();
            switch(coordinates / 100) {
                //Find the column double clicked and, if it's not full, place a red piece on that column
                case (0) :
                checkPiece = root.checkColumn(0);
                if(checkPiece != 0) {
                    root.placeRed(0);
                    checkButton = 1;

                    connect4.roundMoves[connect4.numberOfMoves] = 0;
                    connect4.numberOfMoves++;
                }
                break;

                case (1) :
                checkPiece = root.checkColumn(1);
                if(checkPiece != 0) {
                    root.placeRed(1);
                    checkButton = 1;

                    connect4.roundMoves[connect4.numberOfMoves] = 1;
                    connect4.numberOfMoves++;
                }
                break;

                case (2) :
                checkPiece = root.checkColumn(2);
                if(checkPiece != 0) {
                    root.placeRed(2);
                    checkButton = 1;

                    connect4.roundMoves[connect4.numberOfMoves] = 2;
                    connect4.numberOfMoves++;
                }
                break;

                case (3) :
                checkPiece = root.checkColumn(3);
                if(checkPiece != 0) {
                    root.placeRed(3);
                    checkButton = 1;

                    connect4.roundMoves[connect4.numberOfMoves] = 3;
                    connect4.numberOfMoves++;
                }
                break;

                case (4) :
                checkPiece = root.checkColumn(4);
                if(checkPiece != 0) {
                    root.placeRed(4);
                    checkButton = 1;

                    connect4.roundMoves[connect4.numberOfMoves] = 4;
                    connect4.numberOfMoves++;
                }
                break;

                case (5) :
                checkPiece = root.checkColumn(5);
                if(checkPiece != 0) {
                    root.placeRed(5);
                    checkButton = 1;

                    connect4.roundMoves[connect4.numberOfMoves] = 5;
                    connect4.numberOfMoves++;
                }
                break;

                case (6) :
                checkPiece = root.checkColumn(6);
                if(checkPiece != 0) {
                    root.placeRed(6);
                    checkButton = 1;

                    connect4.roundMoves[connect4.numberOfMoves] = 6;
                    connect4.numberOfMoves++;
                }
                break;
            }
            //If the piece has been placed act accordingly
            if(checkButton == 1) {
                connect4.gridComponent.repaint();
                checkBoard = root.evaluateBoard();
                //If the player has won, save round and finish it.
                if(checkBoard <= -5000) {
                    
                    connect4.boardKeyListener.changeNextPlayer(-1);
                    connect4.nextPlayer = -1;
                    connect4.gameResult(-1);

                    try {
                        connect4.historyHandler.createGameEntry(-1, connect4.roundMoves);
                    } catch (IOException ex) {
                        Logger.getLogger(BoardMouseListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //Else prepare for the AI's turn
                else {
                    try {
                        connect4.turnOfAI(root);
                    } catch (IOException ex) {
                        Logger.getLogger(BoardMouseListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            connect4.gridComponent.repaint();
            if(connect4.boardKeyListener.nextPlayer == 1) {
                try {
                    connect4.boardKeyListener.nextPlayer = connect4.turnOfAI(root);
                } catch (IOException ex) {
                    Logger.getLogger(BoardMouseListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Handle mouse press event on column
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Handle mouse release event on column
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Handle mouse enter event on column
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Handle mouse exit event on column
    }
}

