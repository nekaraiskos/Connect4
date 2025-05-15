/*
 * This class is of the minimizer nodes of the tree
 */
package connect4;
// import java.lang.Math;

/**
 *
 * @author nekaraiskos
 */
public class MinimizerTreeNode extends InsideTreeNode {

    //Empty initialization
    public MinimizerTreeNode() {

    }
    //Give it a specific board and make it available
    public MinimizerTreeNode(int[][] givenBoard) {
        this.nodeType = 1;
        this.isAvailable = 1;
        this.board = givenBoard;
        this.children = new InsideTreeNode[7];
        this.alpha = java.lang.Double.MIN_VALUE;
        this.beta = java.lang.Double.MAX_VALUE;
    }

    //Make it unavailable
    public MinimizerTreeNode(int availability) {
        this.nodeType = 1;
        if(availability == 0) {
            isAvailable = 0;
        }
    }

    //get the evaluation of every child and keep the min
    public int getMinEval() {
        
        int minEval = Integer.MAX_VALUE;
        
        for(int i = 0; i < 7; i++) {
            if(children[i].isAvailable != 0) {
                System.out.println("Child " + i + ":");
                children[i].printBoard();
                System.out.println("Eval is: " +children[i].evaluateBoard());
                if(children[i].evaluateBoard() < minEval) {
                    minEval = children[i].evaluateBoard();
                }
            }
        }
        return minEval;
    }
    
}
