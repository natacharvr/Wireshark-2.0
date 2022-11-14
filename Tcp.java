public class Tcp { // A COMPLETER
    private String contenu;
    private int sourcePort;
    private int destinationPort;
    
    public Tcp(String s){
        contenu = s;
        sourcePort();
        destinationPort();
    }

    private void sourcePort(){
        sourcePort = 8; //valeurs par défaut à changer, mises juste pour les tests
    }

    private void destinationPort(){
        destinationPort = 4; //valeurs par défaut à changer, mises juste pour les tests
    }

    public int getSourcePort(){
        return sourcePort;
    }

    public int getDestinationPort(){
        return destinationPort;
    }

}
