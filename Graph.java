import javax.swing.*;
import java.awt.*;
import java.awt.GridLayout;

import java.awt.Graphics;

public class Graph extends JPanel {
    int nbLignes;
    int nbTrames;

    public Graph(int nbLignes, int nbTrames) {
        this.nbLignes = nbLignes;
        this.nbTrames = nbTrames;
    }

    public void paint(Graphics g){
        int tailleX = getWidth();
        int tailleY = getHeight();
        int deltaX = tailleX / (nbLignes+1);
        int deltaY = tailleY / (nbTrames+1);
        int x = 0;
        int y = 0;

        for (int i = 0; i < nbLignes; i ++){
            x = (i+1) * deltaX;
            g.drawLine(x, 0, x, getHeight());
        }
        for (int i = 0; i < nbTrames; i ++){
            y = (i+1) * deltaY;
            g.drawLine(0, y, getWidth(), y);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Wireshark 2.0");
        frame.setSize(500,500);
        Graph g = new Graph(5, 10);
        frame.setLayout(new GridLayout(2, 1));

        frame.getContentPane().add(g);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
    
}
