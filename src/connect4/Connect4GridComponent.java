/*
 * This class is responsible for creating the game's board graphics and updating them
 */
package connect4;

/**
 *
 * @author nekaraiskos
 */
import javax.swing.*;
import java.awt.*;

public class Connect4GridComponent extends JPanel {
    public static final int NUM_ROWS = 6;
    public static final int NUM_COLUMNS = 7; 

    int[][] board;

    public Connect4GridComponent(MaximizerTreeNode root) {
        //Set the size of the grid component according to the rows and cols of the connect4 board in pixels
        int width = NUM_COLUMNS * 100; 
        int height = NUM_ROWS * 100;  
        setPreferredSize(new Dimension(width, height));
        this.board = root.board;
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //Get the cells' size, the radius of the circles and the width of the border between them
        int cellSize = getWidth() / NUM_COLUMNS; 
        int radius = cellSize / 3;  
        int borderWidth = 2;  

        // Enable anti-aliasing 
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the grid background
        g2d.setColor(Color.BLUE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Draw the circles
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLUMNS; col++) {
                int x = col * cellSize + cellSize / 2;
                int y = row * cellSize + cellSize / 2;

                // Draw the border line between background and circles
                g2d.setColor(Color.DARK_GRAY);
                g2d.setStroke(new BasicStroke(borderWidth));
                g2d.drawOval(x - radius - borderWidth/2, y - radius - borderWidth/2, 2 * radius + borderWidth, 2 * radius + borderWidth);

                // Draw the circle according to the current state of the root's board
                if(board[row][col] == 1) {
                    g2d.setColor(Color.RED);
                }
                else if(board[row][col] == -1) {
                    g2d.setColor(Color.YELLOW);
                }
                else {
                    g2d.setColor(Color.WHITE);
                }
                g2d.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
            }
        }
    }
}
