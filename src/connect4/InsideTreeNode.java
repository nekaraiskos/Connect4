/*
 * This is the class of an inside tree node. It adds the ability to recognize the type of a node, a children array that is connected to its children nodes
 * and the doubles alpha,beta used for pruning
 */
package connect4;

/**
 *
 * @author nekaraiskos
 */
public class InsideTreeNode extends BasicTreeNode{
    InsideTreeNode[] children;
    double alpha, beta;
    int nodeType, maxEval;

    public InsideTreeNode() {
        maxEval = 0;
        alpha = java.lang.Double.MIN_VALUE;
        beta = java.lang.Double.MAX_VALUE;
        children = new InsideTreeNode[7];
    }

    public InsideTreeNode(int[][] givenBoard) {
        maxEval = 0;
        this.board = givenBoard;
        alpha = java.lang.Double.MIN_VALUE;
        beta = java.lang.Double.MAX_VALUE;
        children = new InsideTreeNode[7];
    }
}
