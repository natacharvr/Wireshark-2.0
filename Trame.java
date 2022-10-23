public class Trame {
    private String contenu;
    private String sourceIp;
    private String destinationIp;
    private String type;

    public Trame(String contenu){
        this.contenu = contenu;
        destinationIp();
        sourceIp();
        type();
    }

    public String toString(){
        return contenu;
    }

    private void destinationIp(){
        this.destinationIp = contenu.substring(0,18);
        destinationIp = destinationIp.replace(" ", ":");
        System.out.println("destination : " + this.destinationIp);
    }

    private void sourceIp(){
        this.sourceIp = contenu.substring(19,36);
        sourceIp = sourceIp.replace("\s", ":");
        System.out.println("source : " + this.sourceIp);
    }

    private void type(){
        this.type = contenu.substring(37,42);
        type = type.replace("\s", "");
        System.out.println("type : " + this.type);
    }
}
