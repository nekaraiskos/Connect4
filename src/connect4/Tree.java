/*
 * This is the class of the minmax tree of the program
 */
package connect4;

// import javax.swing.text.AsyncBoxView.ChildLocator;

/**
 *
 * @author nekaraiskos
 */
public class Tree {
    MaximizerTreeNode root;
    
    public Tree() {
        root = new MaximizerTreeNode();
    }

    //Give it the root that contains the current board and the difficulty of the round
    //It's a recursive method in which every node has 7 children. Every level we add a new piece on the board
    //If the new node is a minimizer or a leaf we add a yellow on the position that is the same as the one 
    //in which the child is in the children matrix of the parent. If the new node is a maximizer, we add the same way a red piece
    //If it's the root add nothing
    public void createTree(InsideTreeNode currNode, int levelsRemaining) { 
        int i, checkPiece;
        int[][] tempBoard = new int[6][7];

        
        if(levelsRemaining > 0) {
            if(currNode.nodeType == 0) {
                for(i = 0; i < 7; i++) {
                    tempBoard = currNode.copyBoard();
                    MinimizerTreeNode tempNode = new MinimizerTreeNode(tempBoard);
                    checkPiece = tempNode.placeYellow(i);
                    if(checkPiece == -1) {
                        MinimizerTreeNode newNode = new MinimizerTreeNode(0);
                        currNode.children[i] = newNode;
                        
                    }   
                    else {
                        currNode.children[i] = tempNode;
                        createTree(currNode.children[i], levelsRemaining - 1);//, 1);
                    }          
                }
                return;
            }
            else if(currNode.nodeType == 1) {
                for(i = 0; i < 7; i++) {
                    tempBoard = currNode.copyBoard();
                    MaximizerTreeNode tempNode = new MaximizerTreeNode(tempBoard);
                    checkPiece = tempNode.placeRed(i);
                    if(checkPiece == -1) {
                        MaximizerTreeNode newNode = new MaximizerTreeNode(0);
                        currNode.children[i] = newNode;
                        
                    }   
                    else {
                        currNode.children[i] = tempNode;
                        createTree(currNode.children[i], levelsRemaining - 1);
                    }          
                }
                return;
            }
        }
        else {
            for(i = 0; i < 7; i++) {
                tempBoard = currNode.copyBoard();
                LeafTreeNode tempNode = new LeafTreeNode(tempBoard);
                checkPiece = tempNode.placeYellow(i);
                if(checkPiece == -1) {
                    LeafTreeNode newNode = new LeafTreeNode(0);
                    currNode.children[i] = newNode;
                }
                else {
                    currNode.children[i] = tempNode;
                }

            }
            return;
        }
    }

    //Solve the minmax tree with alpha beta pruning
    public int solveMinMax(InsideTreeNode currNode, double alpha, double beta) {

        int bestEval, i, tempEval, isPruned = 0;

        if (currNode.nodeType == -1) {
            currNode.maxEval = currNode.evaluateBoard();
            return currNode.evaluateBoard();
        }

        if(currNode.nodeType == 0) {
            bestEval = -100000;
            for(i =0; i < 7; i++) {
                if( currNode.children[i].isAvailable != 0) {
                    if(isPruned == 0) {
                        tempEval = solveMinMax(currNode.children[i], alpha, beta);
                        bestEval = Math.max(bestEval, tempEval);
                        alpha = Math.max(alpha, tempEval);
                    }
                    else {
                        currNode.children[i].isAvailable = 0;
                    }
                    if(beta <= alpha) {
                        isPruned = 1;
                    }
                }
            }
            currNode.maxEval = bestEval;
            return currNode.maxEval;
        }
        else {
            bestEval = 100000;
            for(i = 0; i < 7; i++) {
                if(currNode.children[i].isAvailable != 0) {
                    if(isPruned == 0) {
                        tempEval = solveMinMax(currNode.children[i], alpha, beta);
                        bestEval = Math.min(bestEval, tempEval);
                        beta = Math.min(tempEval, beta);
                    }
                    else {
                        currNode.children[i].isAvailable = 0;
                    }
                    if(beta <= alpha) {
                        isPruned = 1;
                    }
                }
            }
            currNode.maxEval = bestEval;
            return currNode.maxEval;
        }
    }

    //print the tree
    public void printTree(InsideTreeNode curr, int levelsRemaining) {
        curr.printBoard();
        System.out.println("Eval: " + curr.maxEval + ", pruned: " + curr.isAvailable);
        if(levelsRemaining >= 0) {
            for(int i = 0; i< 7; i++) {
                if(curr.children[i] != null && curr.children[i].isAvailable != 0) {
                    printTree(curr.children[i], levelsRemaining - 1);
                }
            }
            return;
        }
        else {
            return;
        }
    }

}
