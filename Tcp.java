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
        sourcePort = 0;
    }

    private void destinationPort(){
        destinationPort = 0;
    }

}
