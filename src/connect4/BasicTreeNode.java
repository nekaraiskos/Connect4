/*
 * This is the class for the basic tree node of the minmax tree. Every other node is derived from it.
 * It contains all the basic methods for node manipulation
 */
package connect4;

import java.util.Arrays;

/**
 *
 * @author nekaraiskos
 */
public class BasicTreeNode {
    int[][] board;
    int isAvailable;

    //initialize the connect4 board conatined inside the node
    public BasicTreeNode() {
        board = new int[6][7];
        isAvailable = 1;
    }

    //Make a basic node with a given board
    public BasicTreeNode(int[][] givenBoard) {
        this.board = givenBoard;
        isAvailable = 1;
    }

    //If a column is full, we don't want to ever interact with this node
    public BasicTreeNode(int fullColumn) {
        if(fullColumn == 0) {
            isAvailable = 0;
        }
        else {
            isAvailable = 1;
        }
    }
    
    //return information about the piece on a board position
    public int pieceOnPos(int i, int j) {
        return this.board[i][j];
    }
    
    //Print board to terminal
    public void printBoard() {
        char symbol;
        int i,j;

        for(i = 0; i < 6; i++) {
            for(j = 0; j < 7; j++) {
                
                switch(pieceOnPos(i,j)) {
                    case 0 : symbol = '-'; 
                        break;
                    case 1 : symbol = 'O'; 
                        break;
                    default : symbol = 'X'; 
                        break;
                }
                System.out.print("| " + symbol + " | ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    //Evaluate the board by separating in every possible group of 4, calculating it's evaluation for the AI and return the sum of every evaluation
    public int evaluateBoard() {
        int i,j,k;
        int sumEval = 0;
        int[] subBoard = new int[4];

        for(i = 0; i < 6 ; i++) {
            for(j = 0; j < 4; j++) {
                for(k = 0 ; k < 4; k++) {
                    subBoard[k] = board[i][j+k];
                }
                sumEval = sumEval + calculateGroupEval(subBoard);
            }
        }

        //Evaluate the vertical groups of 4
        for(j = 0; j < 7; j++) {
            for(i = 0; i < 3; i++) {
                for(k = 0; k < 4; k++) {
                    subBoard[k] = board[i+k][j];
                }
                sumEval = sumEval + calculateGroupEval(subBoard);
            }
        }

        //Evaluate the upper diagonal groups of 4
        for (i = 3; i < 6 ; i++) {
            for(j = 0; j < 4; j++) {
                for(k = 0; k < 4; k++) {
                    subBoard[k] = board[i-k][j+k];
                }
                sumEval = sumEval + calculateGroupEval(subBoard);                
            }
        }

        //Evaluate the lower diagonal groups of 4
        for(i = 3; i < 6; i++) {
            for(j = 3; j < 7; j++) {
                for(k = 0; k < 4; k++) {
                    subBoard[k] = board[i-k][j-k];
                }
                sumEval = sumEval + calculateGroupEval(subBoard);
            }
        }
        
        return sumEval;
    }

    //Evaluate a single group of 4 
    public int calculateGroupEval(int[] subBoard) {

        int i, checkers, colour = 0;

        for(i = 0; i < 4; i++){
            if(subBoard[i] == -1) {
                colour = -1;
                break;
            }
            else if(subBoard[i] == 1) {
                colour = 1;
                break;
            }
        }
        if(colour == 0) {

            return 0;
        }
        if(colour == -1) {
            for(i = 0, checkers = 0; i < 4; i++) {
                if(subBoard[i] == -1) {
                    checkers = checkers + 1;
                }
                else if(subBoard[i] == 1) {
                    return 0;
                }
            }
            switch(checkers) {
            case 0: return 0;
            case 1: return 1;
            case 2: return 4;
            case 3: return 16;
            default: return 10000;
            }
        }
        if(colour == 1) {
            for(i = 0, checkers = 0; i < 4; i++) {
                if(subBoard[i] == 1) {
                    checkers = checkers + 1;
                }
                else if(subBoard[i] == -1) {
                    return 0;
                }
            }
            switch(checkers) {
            case 0: return 0;
            case 1: return -1;
            case 2: return -4;
            case 3: return -16;
            default: return -10000;
            }
        }
        return 0;
    }
       
    //If a column is not full place a yellow piece in it, else return fail
    public int placeYellow(int j) {
        int i = 0;
        
        for(i = 0; i < 6 && this.pieceOnPos(i,j) == 0; i++) {
        }
        if(i == 0) {
            return -1;
        }
        else {
            board[i-1][j] = -1;
            return 1;
        }
            
    }
    
    //If a column is not full, place a red piece in it, else return fail
    public int placeRed(int j) {
        int i = 0;
        
        for(i = 0; i < 6 && this.pieceOnPos(i,j) == 0; i++) {
        }
        if(i == 0) {
            return -1;
        }
        else {
            board[i-1][j] = 1;
            return 1;
        }
            
    }

    //Copy the contents of a board to a new one
    public int[][] copyBoard() {
        int[][] tempBoard = new int[6][7];

        for(int i = 0; i< 6; i++) {
            tempBoard[i] = Arrays.copyOf(this.board[i], 7);
        }
        return tempBoard;
    }

    //Check if a column in full
    public int checkColumn(int colNum) {
        int i;
        for(i = 0; i < 6 && this.board[i][colNum] == 0; i++);
        if(i == 0) {
            return 0;
        }
        else {
            return 1;
        }
    }

    //Reset the state of a board
    public void deleteContents() {
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 7; j++) {
                this.board[i][j] = 0;
            }
        }
    }
}
