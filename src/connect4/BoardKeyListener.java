/*
 * This class is the implementation of a key listener to the program. It is used for the player inputs 0 -> 6 in order to place the red pieces on the 
 * correct column for each number (0-6 equals left to right column). In the overriden method of the class, "keyPressed", the keyboard inputs are processed and also,
 * the mechanism of the turns between player and AI is created.
 */
package connect4;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
// import javax.swing.JOptionPane;

/**
 *
 * @author nekaraiskos
 */

public class BoardKeyListener implements KeyListener{
    MaximizerTreeNode root;
    Connect4GridComponent component;
    HistoryHandler historyHandler;
    int nextPlayer; 
    int checkButton;
    Connect4 connect4;
    
    public BoardKeyListener(Connect4GridComponent comp, MaximizerTreeNode rootGiven, int nextPlayer, Connect4 connect4, HistoryHandler historyHandler) {
        root = rootGiven;
        component = comp;
        this.connect4 = connect4;
        this.historyHandler = historyHandler;
    }
    
    @Override
    public void keyPressed(KeyEvent evt) {
        int keyPressed = evt.getKeyCode();
        int checkPiece, checkBoard;
        component.repaint();

        //If a button is pressed and it's the player's turn, check if its one of the buttons connected to the board
        if(nextPlayer == 0) {
            checkButton = 0;
            switch(keyPressed) {
                case KeyEvent.VK_0: 
                    checkPiece = root.checkColumn(0);
                    if(checkPiece != 0) {
                        root.placeRed(0);
                        checkButton = 1;

                        connect4.roundMoves[connect4.numberOfMoves] = 0;
                        connect4.numberOfMoves++;
                    }
                    break;
                
                case KeyEvent.VK_1:
                checkPiece = root.checkColumn(1);
                    if(checkPiece != 0) {
                        root.placeRed(1);
                        checkButton = 1;

                        connect4.roundMoves[connect4.numberOfMoves] = 1;
                        connect4.numberOfMoves++;
                    }
                    break;

                case KeyEvent.VK_2:
                checkPiece = root.checkColumn(2);
                    if(checkPiece != 0) {
                        root.placeRed(2);
                        checkButton = 1;

                        connect4.roundMoves[connect4.numberOfMoves] = 2;
                        connect4.numberOfMoves++;
                    }
                    break;

                case KeyEvent.VK_3:
                checkPiece = root.checkColumn(3);
                    if(checkPiece != 0) {
                        root.placeRed(3);
                        checkButton = 1;

                        connect4.roundMoves[connect4.numberOfMoves] = 3;
                        connect4.numberOfMoves++;
                    }
                    break;

                case KeyEvent.VK_4:
                checkPiece = root.checkColumn(4);
                    if(checkPiece != 0) {
                        root.placeRed(4);
                        checkButton = 1;

                        connect4.roundMoves[connect4.numberOfMoves] = 4;
                        connect4.numberOfMoves++;
                    }
                    break;

                case KeyEvent.VK_5:
                checkPiece = root.checkColumn(5);
                    if(checkPiece != 0) {
                        root.placeRed(5);
                        checkButton = 1;

                        connect4.roundMoves[connect4.numberOfMoves] = 5;
                        connect4.numberOfMoves++;
                    }
                    break;

                case KeyEvent.VK_6:
                checkPiece = root.checkColumn(6);
                    if(checkPiece != 0) {
                        root.placeRed(6);
                        checkButton = 1;

                        connect4.roundMoves[connect4.numberOfMoves] = 6;
                        connect4.numberOfMoves++;
                    }
                    break;
                default: break;
            }

            //If a piece has been placed, do the following
            if(checkButton == 1) {
                component.repaint();
                checkBoard = root.evaluateBoard();
                //If the player has won, display the correct message, end the round and save the game.
                if(checkBoard <= -5000) {
                    connect4.gameResult(-1);
                    
                    try {
                        connect4.historyHandler.createGameEntry(-1, connect4.roundMoves);
                    } catch (IOException ex) {
                        Logger.getLogger(BoardKeyListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    connect4.historyHandler.getFileNames();
                    connect4.nextPlayer = -1;
                    nextPlayer = -1;
                }
                //Else prepare for the next player
                else {
                    connect4.nextPlayer = -1;
                    nextPlayer = 1;
                }
            }
            component.repaint();
            //Condition for the AI's turn
            if(nextPlayer == 1) {
                try {
                    nextPlayer = connect4.turnOfAI(root);
                } catch (IOException ex) {
                    Logger.getLogger(BoardKeyListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else if(nextPlayer == 1) {
            try {
                nextPlayer = connect4.turnOfAI(root);
            } catch (IOException ex) {
                Logger.getLogger(BoardKeyListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//    }
    }

    public void changeNextPlayer(int player) {
        if(player == 0) {
            nextPlayer = 0;
        }
        else if(player == 1) {
            nextPlayer = 1;
        }
        else if(player == -1) {
            nextPlayer = -1;
        } 
    }
    
    @Override
    public void keyReleased(KeyEvent evt) {
        
    }
    
    @Override
    public void keyTyped(KeyEvent evt) {
        
    }
}
