import javax.swing.*;
import java.awt.*;

import java.awt.Graphics;

public class Graph extends JPanel {
    int nbLignes;

    public Graph(int nbLignes) {
        this.nbLignes = nbLignes;
    }

    public void paint(Graphics g){
        int tailleX = getWidth();
        int deltaX = tailleX / (nbLignes+1);
        int x = 0;

        for (int i = 0; i < nbLignes; i ++){
            x = (i+1) * deltaX;
            g.drawLine(x, 0, x, getHeight());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Wireshark 2.0");
        frame.setSize(300,300);
        Graph g = new Graph(5);
        frame.setLayout(new GridLayout(1, 2));

        frame.getContentPane().add(g);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

    }
    
}
