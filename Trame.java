public class Trame {
    private String contenu;
    private String source;
    private String destination;

    public Trame(String contenu){
        this.contenu = contenu;
        destination();
        source();
    }

    public String toString(){
        return contenu;
    }

    private void destination(){
        this.destination = contenu.substring(0,12);
        System.out.println("destination : " + this.destination);
    }

    private void source(){
        this.source = contenu.substring(13,25);
        System.out.println("source : " + this.source);
    }
}
