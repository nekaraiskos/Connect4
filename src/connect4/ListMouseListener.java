/*
 * This listener is connected to the frame of the JList containing the history. When I double click on one of the list's objects, 
 * close the opened window and play back the game that's linekd to that object
 */
package connect4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.Timer;
// import javax.swing.UIDefaults.ProxyLazyValue;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 *
 * @author nekaraiskos
 */
public class ListMouseListener implements MouseListener{
    Connect4 connect4;
    JList list;
    int i;
    int[] moves = new int[43];
    int isRunning;

    public ListMouseListener(Connect4 connect4, JList list) {
        this.connect4 = connect4;
        this.list =list;
        resetMoves();
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        //Check for double click
        if(e.getClickCount() == 2) {

            
            connect4.historyFrame.dispatchEvent(new WindowEvent(connect4.historyFrame, WindowEvent.WINDOW_CLOSING));
            
            //Check if there is currently a playback running
            if(connect4.playbackIsRunning == true) {
                String error = "A playback is already running!";
                JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int pos = list.getSelectedIndex();

            //Get filepath for the selected object
            File fileName = new File(connect4.historyHandler.connect4Dir, connect4.fileNames[connect4.fileNames.length - 1 - pos]);

            connect4.resetBoard();
            connect4.gridComponent.repaint();
            

            //Get the series and priority of the round's moves
            moves = connect4.historyHandler.getMoves(fileName);

            //Disable mouse and keyboard
            connect4.nextPlayer = -1;
            connect4.boardKeyListener.changeNextPlayer(-1);

            //Play back the moves in the correct order with 1 second pause every move            
            i = 1;
            Timer timer = new Timer(1000,  new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if (connect4.boardKeyListener.nextPlayer == -1 && i < 43 && moves[i] != -1) {
                        if (i % 2 == 1) {
                            if(moves[0] == 1) {
                                connect4.root.placeYellow(moves[i]);
                                connect4.playbackIsRunning = true;
                            }
                            else{
                                connect4.root.placeRed(moves[i]);
                                connect4.playbackIsRunning = true;
                            }
                        } 
                        else {
                            if(moves[0] == 1) {
                                connect4.root.placeRed(moves[i]);
                                connect4.playbackIsRunning = true;
                            }
                            else {
                                connect4.root.placeYellow(moves[i]);
                                connect4.playbackIsRunning = true;
                            }
                        }
                        connect4.gridComponent.repaint();
                        i++;
                    } 
                    else {
                        connect4.playbackIsRunning = false;
                        ((Timer) evt.getSource()).stop();
                    }  
                }
            
            });
            timer.start();
        }
        
    }

    public void resetMoves() {
        for(int i = 0; i < 43; i++) {
            moves[i] = -1;
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}

