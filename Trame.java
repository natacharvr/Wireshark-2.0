public class Trame {
    private String contenu;
    private String sourceMac;
    private String destinationMac;
    private String type;

    //attributs ipv4
    private int version;
    private int headerLength;
    private int size;
    private String sourceIp;
    private String destinationIp;

    public Trame(String contenu){
        this.contenu = contenu;
        destinationMac();
        sourceMac();
        type();
        switch(type){
            case "0800" :
                Ipv4(contenu.substring(43, contenu.length() -1));
                break;
        }
    }

    public String toString(){
        String res = "";
        res += "contenu: " + contenu + "\n";
        res += "sourceMac: " + sourceMac + "\n";
        res += "destinationMac: " + destinationMac + "\n";
        res += "type: " + type + "\n";
        res += "sourceIp: " + sourceIp + "\n";
        res += "destinationIp: " + destinationIp + "\n";
        res += "version: " + version + "\n";
        res += "headerLength: " + headerLength + "\n";
        res += "size: " + size + "\n";
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

    private void Ipv4(String s){
        version = Ipv4.version(s);
        headerLength = Ipv4.headerLength(s);
        size = Ipv4.size(s);
        sourceIp = Ipv4.sourceIp(s);
        destinationIp = Ipv4.destinationIp(s);
    }
}
