import javax.swing.*;
import java.awt.*;

import java.awt.Graphics;

public class Graph extends JPanel {
    //Classe qui permet de dessiner le graphique de l'interface
    int nbLignes; //Le nombre d'Ip differentes 
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
     * @param y1 L'ordonnee de l'origine de la flèche
     * @param x2 L'abscisse de la destination de la flèche
     * @param y2 L'ordonnee de la destination de la flèche
     */
    public void drawArrow(Graphics g, int x1, int y1, int x2, int y2, int sourcePort, int destinationPort){
    	Color initialColor = g.getColor();
        Polygon triangle;
        String sp = "port " + sourcePort;
        String dp = "port " + destinationPort;
        if (x1 < x2){
            int[] xpoints = {x2 - 6, x2 - 6, x2};
            int[] ypoints = {y2 - 4, y2 + 4, y2};
            triangle = new Polygon(xpoints, ypoints, 3);
            if (sourcePort != -1){
                g.setColor(Color.blue);
                g.drawString(sp, x1 - 75 , y1 + 5);
                g.setColor(Color.red);
                g.drawString(dp, x2 + 8, y2 + 5);
            }
            //bleu si flèche vers la droite
            g.setColor(Color.blue);
            g.drawLine(x1, y1, x2, y2);
            g.drawPolygon(triangle);
            g.fillPolygon(triangle);

        }
        else {
            int[] xpoints = {x2 + 6, x2 + 6, x2};
            int[] ypoints = {y2 - 4, y2 + 4, y2};
            triangle = new Polygon(xpoints, ypoints, 3);
            if (sourcePort != -1){
                g.setColor(Color.blue);
                g.drawString(sp, x1 + 8, y1 + 5);
                g.setColor(Color.red);
                g.drawString(dp, x2 - 75, y2 + 5);
            }
            //rouge si flèche vers la gauche
            g.setColor(Color.red);
            g.drawLine(x1, y1, x2, y2);
            g.drawPolygon(triangle);
            g.fillPolygon(triangle);
        }
        g.setColor(initialColor);
        
        g.drawLine(x1, y1, x2, y2);
        g.drawPolygon(triangle);
        g.fillPolygon(triangle);
    }

    /* (non-Javadoc)
     * Dessine le graphique, avec nbTrames flèches espacees de deltaY, dont les coordonnees en x sont donnees grâce au tableau sourceDest
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
