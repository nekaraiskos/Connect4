/*
 * This is the main class of the game. It creates the frame of the game and is responsible for all actions regarding the menus made by the user.
 * It also contains the method responsible for every move the AI makes each round
 * 
 */
package connect4;

import java.awt.BorderLayout;
/**
 *
 * @author nekaraiskos
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class Connect4 extends javax.swing.JFrame{

    int difficulty = 0;
    int firstPlayer = 1;
    int[][] board = new int[6][7];
    MaximizerTreeNode root;
    int nextPlayer;
    Connect4GridComponent gridComponent;
    BoardKeyListener boardKeyListener;
    HistoryHandler historyHandler;
    JFrame frame;
    int[] roundMoves = new int[43];
    int numberOfMoves;
    String[] fileNames;
    JFrame historyFrame;
    JMenuBar menuBar;
    boolean playbackIsRunning = false;

    public static void main(String[] args) throws IOException {
        Connect4 connect4 = new Connect4();
        
    }

    public Connect4() throws IOException {
        root = new MaximizerTreeNode(board);
        nextPlayer = 1;
        addComponents();

    }
    
    //Add the GUI components to Connect4
    public void addComponents() throws IOException {

        //Create the main frame
        frame = new JFrame("Connect 4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(800, 800);
        
        //Create the game's menubar
        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        //Create the menus and add them to the bar
        JMenu newGame = new JMenu("New Game");
        JMenu firstPlayer = new JMenu("1st Player");
        JMenu history = new JMenu("History");
        JMenu help = new JMenu("Help");
        help.setEnabled(false);
        
        menuBar.add(newGame);
        menuBar.add(firstPlayer);
        menuBar.add(history);
        menuBar.add(help);


        //Create the menu items, add an action listener for each one of them and connect them with their menu

        JMenuItem trivial = new JMenuItem("Trivial");
        trivial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    trivialActionPerformed(evt);
                } catch (IOException ex) {
                    Logger.getLogger(Connect4.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });

        JMenuItem medium = new JMenuItem("Medium");
        medium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    mediumActionPerformed(evt);
                } catch (IOException ex) {
                    Logger.getLogger(Connect4.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });

        JMenuItem hard = new JMenuItem("Hard");   
        hard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    hardActionPerformed(evt);
                } catch (IOException ex) {
                    Logger.getLogger(Connect4.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        
        newGame.add(trivial);
        newGame.add(medium);
        newGame.add(hard);
        
        JMenuItem firstAI = new JMenuItem("AI");
        firstAI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                firstAIActionPerformed(evt);
            }
            
        });

        JMenuItem firstYou = new JMenuItem("You");
        firstYou.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                firstYouActionPerformed(evt);
            }
            
        });

        firstPlayer.add(firstAI);
        firstPlayer.add(firstYou);

        //Create a MenuListener for the "History" menu.
        history.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent evt) {
                try {
                    historyActionPerformed();
                } catch (IOException ex) {
                    Logger.getLogger(Connect4.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });

        //Create the gridComponent, responsible for the board graphics
        gridComponent = new Connect4GridComponent(root);
        frame.add(gridComponent);
        
        //Create and link a new key listener to the main frame
        boardKeyListener = new BoardKeyListener(gridComponent, root, nextPlayer, this, historyHandler);
        frame.addKeyListener(boardKeyListener);

        //Create and link a mouse listener to gridComponent
        BoardMouseListener boardMouseListener = new BoardMouseListener(this, root);
        gridComponent.addMouseListener(boardMouseListener);

        frame.pack();
        frame.setVisible(true);  

        //Create the handler responsible for the history of games stored in the system
        historyHandler = new HistoryHandler(difficulty);  
        
        //Reset for new round
        historyHandler.updateForNewGame(difficulty);
        resetRoundMoves();
        roundMoves[numberOfMoves] = 1;
        numberOfMoves++;

        nextPlayer = -1;
        boardKeyListener.changeNextPlayer(-1);
        // nextPlayer = turnOfAI(root);
        
    }

    //Change difficulty to trivial and start new game
    public void trivialActionPerformed(java.awt.event.ActionEvent evt) throws IOException {

        difficulty = 0;
        resetBoard();
        
        historyHandler.updateForNewGame(difficulty);
        resetRoundMoves();

        //Act accordingly to the selected first player
        if(nextPlayer == 1) {
            roundMoves[numberOfMoves] = 1;
            numberOfMoves++;

            this.nextPlayer = turnOfAI(root);
            boardKeyListener.nextPlayer = 0;
            boardKeyListener.changeNextPlayer(0);
            
        }
        else if(nextPlayer == 0) {
            roundMoves[numberOfMoves] = 0;
            numberOfMoves++;
            boardKeyListener.nextPlayer = nextPlayer;
            boardKeyListener.changeNextPlayer(0);
        }
        
    }
    //Change difficulty to medium and start new game
    public void mediumActionPerformed(java.awt.event.ActionEvent evt) throws IOException {

        difficulty = 2;
        resetBoard();

        historyHandler.updateForNewGame(difficulty);
        resetRoundMoves();

        //Act accordingly to the selected first player
        if(nextPlayer == 1) {
            roundMoves[numberOfMoves] = 1;
            numberOfMoves++;

            this.nextPlayer = turnOfAI(root);
            boardKeyListener.changeNextPlayer(0);
            
        }
        else if(nextPlayer == 0) {
            roundMoves[numberOfMoves] = 0;
            numberOfMoves++;

            boardKeyListener.changeNextPlayer(0);
        }
    }
    //Change difficulty to hard and start new game
    public void hardActionPerformed(ActionEvent evt) throws IOException {

        difficulty = 4;
        resetBoard();

        historyHandler.updateForNewGame(difficulty);
        resetRoundMoves();

        //Act accordingly to the selected first player
        if(nextPlayer == 1) {
            roundMoves[numberOfMoves] = 1;
            numberOfMoves++;

            this.nextPlayer = turnOfAI(root);
            boardKeyListener.changeNextPlayer(0);
            
        }
        else if(nextPlayer == 0) {
            roundMoves[numberOfMoves] = 0;
            numberOfMoves++;

            boardKeyListener.changeNextPlayer(0);
        }       
    }

    //Chose the AI as the first player
    public void firstAIActionPerformed(ActionEvent evt) {
        firstPlayer = 1;
    }

    //Chose the player as the first player
    public void firstYouActionPerformed(ActionEvent evt) {
        firstPlayer = 0;
    }
    
    //Create and display the games history list
    public void historyActionPerformed() throws IOException {

        //Get path of saved files
        fileNames = historyHandler.getFileNames();

        //Create a default list model and add to it the string/description for each saved game
        DefaultListModel<String> defaultListModel = new DefaultListModel<>();
        String[] jListStrings = historyHandler.makeGameStrings(fileNames);
        for(int i = 0; i < jListStrings.length; i++) {
            defaultListModel.addElement(jListStrings[i]);
        }
        
        //Create a new JList, add to it the default list model and create a mouse listener for it
        JList<String> historyList = new JList<>(defaultListModel);
        ListMouseListener listMouseListener = new ListMouseListener(this, historyList);
        historyList.addMouseListener(listMouseListener);
        
        //History frame settings and start
        historyFrame = new JFrame("Game History");
        JScrollPane historyPane = new JScrollPane(historyList);
        historyFrame.setLayout(new BorderLayout());
        historyFrame.add(historyPane);
        historyFrame.setAlwaysOnTop(true);
        historyFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        historyFrame.setSize(350, 350);
        historyFrame.setVisible(true);

    }

    //Show the game's result in a JOptionPane
    public void gameResult(int winner) {
        String message = (winner == -1) ? "You won!" : "You lost!";
        JOptionPane.showMessageDialog(null, message, "Game Ended", JOptionPane.INFORMATION_MESSAGE);
    }

    //Reset the board for a new round
    public void resetBoard() {
        
        root.deleteContents();
        nextPlayer = firstPlayer;
        this.gridComponent.repaint();
    }

    //The method for each turn the AI plays
    public int turnOfAI(MaximizerTreeNode root) throws IOException{
        
        int checkBoard;

        gridComponent.repaint();

        //Create a new tree with the root of the program that contains the board and solve the MinMax algorithm for it
        Tree tree = new Tree();
        tree.createTree(root, difficulty);
        tree.solveMinMax(root, -10000000, 10000000);

        int eval = -10000000;
        int evalPos = -1;

        //Pick the best move for the round
        for(int i = 0; i < 7; i++) {
            if(root.children[i].isAvailable != 0) {
                if(root.children[i].maxEval > eval) {
                    eval = root.children[i].maxEval;
                    evalPos = i;
                        
                }
            }
        }
        //Make the move
        root.placeYellow(evalPos);
        gridComponent.repaint();

        roundMoves[numberOfMoves] = evalPos;
        numberOfMoves++;

        
        checkBoard = root.evaluateBoard();
        //If AI won, show correct message, save the round and end it.
        if(checkBoard >= 5000) {
            gameResult(1);
            historyHandler.createGameEntry(1, roundMoves);
            nextPlayer = -1;
        }
        //Else, prepare for the next player
        else {
            nextPlayer = 0;
        }
        
       return nextPlayer;
    }

    //Reset the int array that stores each game's moves in the correct order
    public void resetRoundMoves() {
        for(int i = 0; i < 43; i++) {
            roundMoves[i] = -1;
        }
        numberOfMoves = 0;
    }
}
