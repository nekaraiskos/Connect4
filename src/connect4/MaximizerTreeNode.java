/*
 * This class is for a maximizer node of the tree
 */
package connect4;

/**
 *
 * @author nekaraiskos
 */
public class MaximizerTreeNode extends InsideTreeNode{

    //Empty creation
    public MaximizerTreeNode() {
        this.nodeType = 0;
        this.isAvailable = 1;
        this.children = new InsideTreeNode[7];
        this.alpha = java.lang.Double.MIN_VALUE;
        this.beta = java.lang.Double.MAX_VALUE;
    }

    //Give it a specific board
    public MaximizerTreeNode(int[][] givenBoard) {
        this.nodeType = 0;
        this.isAvailable = 1;
        this.board = givenBoard;
        this.children = new InsideTreeNode[7];
        this.alpha = java.lang.Double.MIN_VALUE;
        this.beta = java.lang.Double.MAX_VALUE;
    }

    //Make it inaccessible
    public MaximizerTreeNode(int availability) {
        this.nodeType = 1;
        if(availability == 0) {
            isAvailable = 0;
        }
    }

    //Get the evaluation of every available child, compare them and keep the max
    public int getMaxEval() {
        
        int maxEval = Integer.MIN_VALUE;
        
        for(int i = 0; i < 7; i++) {
            if(this.children[i].isAvailable != 0) {
                System.out.println("Child " + i + ":");
                children[i].printBoard();
                System.out.println("Eval is: " +children[i].evaluateBoard());
                if(children[i].evaluateBoard() > maxEval) {
                    maxEval = children[i].evaluateBoard();
                }
            }
        }
        return maxEval;
    }
    
}
