/*
 * This is the class of the leaf nodes of the tree
 */
package connect4;

// import javax.swing.InternalFrameFocusTraversalPolicy;
// import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

/**
 *
 * @author nekaraiskos
 */
public class LeafTreeNode extends InsideTreeNode{
    
    //empty initialization
    public LeafTreeNode() {
        
    }
    
    //Give it a board to use
    public LeafTreeNode(int[][] givenBoard) {
        this.nodeType = -1;
        this.board = givenBoard;
        this.isAvailable = 1;
    }

    //Make it unavailable
    public LeafTreeNode(int availability) {
        if(availability == 0) {
            this.isAvailable = 0;
        }
    }
}
