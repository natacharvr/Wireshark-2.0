import javax.swing.*;
import java.awt.*;

import java.awt.Graphics;

public class Graph extends JPanel {
    int nbLignes;
    int nbTrames;
    int [][] sourceDest;
    public static final int deltaX = 150;
    public static final int deltaY = 48;

    public Graph(int nbLignes, int nbTrames, int[][] sourceDest) {
        super();
        this.nbLignes = nbLignes;
        this.nbTrames = nbTrames;
        this.sourceDest = sourceDest;
    }

    @Override
    public Dimension getPreferredSize() {
        if (isPreferredSizeSet()) {
            return super.getPreferredSize();
        }
        return new Dimension(getWidth(), getHeight());
    }

    @Override
    public int getHeight(){
        return nbTrames * (deltaY + 2); 
    }

    @Override
    public int getWidth(){
        return nbLignes * (deltaX + 1); 
    }

    public void drawArrow(Graphics g, int x1, int y1, int x2, int y2){
        Polygon triangle;
        if (x1 < x2){
            int[] xpoints = {x2 - 6, x2 - 6, x2};
            int[] ypoints = {y2 - 4, y2 + 4, y2};
            triangle = new Polygon(xpoints, ypoints, 3);

        }
        else {
            int[] xpoints = {x2 + 6, x2 + 6, x2};
            int[] ypoints = {y2 - 4, y2 + 4, y2};
            triangle = new Polygon(xpoints, ypoints, 3);

        }
        
        g.drawLine(x1, y1, x2, y2);
        g.drawPolygon(triangle);
        g.fillPolygon(triangle);
    }

    public void paint(Graphics g){
        int x = 0;
        int y = 0;
        int x1 = 0;
        int x2 = 0;

        for (int i = 0; i < nbLignes; i ++){
            x = (i+1) * deltaX;
            g.drawLine(x, 0, x, getHeight());
        }
        
        for (int i = 0; i < nbTrames; i ++){
            y = (i+1) * deltaY;
            x1 = (sourceDest[i][0] + 1) * deltaX;
            x2 = (sourceDest[i][1] + 1) * deltaX;
            drawArrow(g, x1, y, x2, y);
        }
    }

//      public static void main(String[] args) {
//         JFrame frame = new JFrame("Wireshark 2.0");
//         frame.setSize(500,500);
//         Graph g = new Graph(5, 10);
//         frame.setLayout(new GridLayout(2, 1));

//         frame.getContentPane().add(g);

//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setVisible(true);

//     }
    
}
