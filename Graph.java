import javax.swing.*;
import java.awt.*;

import java.awt.Graphics;

public class Graph extends JPanel {
    //Classe qui permet de dessiner le graphique de l'interface
    int nbLignes; //Le nombre d'Ip différentes 
    int nbTrames; //Le nombre de trames à dessiner
    int [][] sourceDest; //Un tableau qui contient des couples indice Ip source, indice Ip destination (donc compris entre 0 et nbTrames)
    Analyseur a;
    public static final int deltaX = 150; //L'espace entre deux lignes verticales
    public static final int deltaY = 48; //L'escapce entre deux lignes horizontales

    public Graph(int nbLignes, int nbTrames, int[][] sourceDest, Analyseur a) {
        super();
        this.nbLignes = nbLignes;
        this.nbTrames = nbTrames;
        this.sourceDest = sourceDest;
        this.a = a;
    }

    /* 
     * Permet d'obtenir la vraie taille du panel pour l'affichage (autrement problèmes avec la scrollBar)
     */
    @Override
    public Dimension getPreferredSize() {
        if (isPreferredSizeSet()) {
            return super.getPreferredSize();
        }
        return new Dimension(getWidth(), getHeight());
    }

    @Override
    public int getHeight(){
        return (nbTrames + 2) * deltaY; 
    }

    @Override
    public int getWidth(){
        return (nbLignes + 1) * (deltaX); 
    }

    /**
     * @param g L'outil graphique
     * @param x1 l'abscisse de l'origine de la flèche
     * @param y1 L'ordonnée de l'origine de la flèche
     * @param x2 L'abscisse de la destination de la flèche
     * @param y2 L'ordonnée de la destination de la flèche
     */
    public void drawArrow(Graphics g, int x1, int y1, int x2, int y2, int sourcePort, int destinationPort){
        Polygon triangle;
        String sp = "port source : " + sourcePort;
        String dp = "port destination : " + destinationPort;
        if (x1 < x2){
            int[] xpoints = {x2 - 6, x2 - 6, x2};
            int[] ypoints = {y2 - 4, y2 + 4, y2};
            triangle = new Polygon(xpoints, ypoints, 3);
            g.drawString(sp, x1, y1 - 2);
            g.drawString(dp, x2 - 15, y2 + 11);

        }
        else {
            int[] xpoints = {x2 + 6, x2 + 6, x2};
            int[] ypoints = {y2 - 4, y2 + 4, y2};
            triangle = new Polygon(xpoints, ypoints, 3);
            g.drawString(sp, x1 - 15, y1 - 2);
            g.drawString(dp, x2, y2 + 11);

        }
        
        g.drawLine(x1, y1, x2, y2);
        g.drawPolygon(triangle);
        g.fillPolygon(triangle);
    }

    /* (non-Javadoc)
     * Dessine le graphqiue, avec nbTrames flèches espacées de deltaY, dont les coordonnées en x sont données grâce au tableau sourceDest
     */
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
            drawArrow(g, x1, y, x2, y, a.getSourcePortI(i), a.getDestinationPortI(i));
        }
    }
    
}
