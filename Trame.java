public class Trame {
    private String contenu;
    private String sourceMac;
    private String destinationMac;
    private String type;

    //attributs ipv4
    private Ipv4 ipv4;

    //attributs Tcp
  

    public Trame(String contenu){
        this.contenu = contenu;
        destinationMac();
        sourceMac();
        type();
        switch(type){
            case "0800" :
                ipv4 = new Ipv4(contenu.substring(43, contenu.length() -1));
                break;
        }
    }

    public String toString(){
        String res = "";
        res += "contenu: " + contenu + "\n";
        res += "sourceMac: " + sourceMac + "\n";
        res += "destinationMac: " + destinationMac + "\n";
        res += "type: " + type + "\n";
        res += ipv4.toString();
        return res;
    }

    private void destinationMac(){
        this.destinationMac = contenu.substring(1,18);
        destinationMac = destinationMac.replace(" ", ":");
        // System.out.println("destination : " + this.destinationMac);
    }

    private void sourceMac(){
        this.sourceMac = contenu.substring(19,36);
        sourceMac = sourceMac.replace("\s", ":");
        // System.out.println("source : " + this.sourceMac);
    }

    private void type(){
        this.type = contenu.substring(37,42);
        type = type.replace("\s", "");
        // System.out.println("type : " + this.type);
    }

}
