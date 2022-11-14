import java.util.List;

public class Trame {
    private String contenu; //La chaine de caractères pure contenant la trame
    private String sourceMac; //La chaine de caractère extraite de contenu qui contient l'adresse Source Mac
    private String destinationMac; //La chaine de caractère extraite de contenu qui contient l'adresse Destination Mac
    private String type; //La chaine de caractère extraite de contenu qui contient le type de trame 

    //attribut ipv4
    private Ipv4 ipv4 = null; //Un attribut de type Ipv4, qui contient tous les attributs liés si la trame est de type ipv4
  

    public Trame(String contenu){
        this.contenu = contenu;
        destinationMac();
        sourceMac();
        type();
        //En fonction du type de la trame, la trame est Ipv4 ou ???????????, on assigne alors le bon attribut avec un nouvel objet auquel on passe la chaine de caractère qui suit le type
        switch(type){
            case "0800" : //Si le type est 0800, c'est une trame Ipv4
                ipv4 = new Ipv4(this.contenu.substring(43, contenu.length() -1));
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

    /**
     * Extrait la sous chaine contenant la destination Mac
     */
    private void destinationMac(){
        this.destinationMac = contenu.substring(1,18);
        destinationMac = destinationMac.replace(" ", ":");
        // System.out.println("destination : " + this.destinationMac);
    }

    /**
     * Extrait la sous chaine contenant la source Mac
     */
    private void sourceMac(){
        this.sourceMac = contenu.substring(19,36);
        sourceMac = sourceMac.replace("\s", ":");
        // System.out.println("source : " + this.sourceMac);
    }

    /**
     * Extrait la sous chaine contenant le type
     */
    private void type(){
        this.type = contenu.substring(37,42);
        type = type.replace("\s", "");
        // System.out.println("type : " + this.type);
    }

    /**
     * @return la chaine de caractère correspondant à l'adresse Ip source (dans le cas d'une trame Ipv4)
     */
    public String getSourceIp(){
        if (ipv4 != null){
            return ipv4.getSourceIp();
        }
        return null;
    }

    /**
     * @return la chaine de caractère correspondant à l'adresse Ip destination (dans le cas d'une trame Ipv4)
     */
    public String getDestinationIp(){
        if (ipv4 != null){
            return ipv4.getDestinationIp();
        }
        return null;
    }

    /**
     * Utile pour créer le tableau qui permet de dessiner les flèches du graphqiue
     * @param ipList Une liste de Chaine de caractères contenant toutes les adresses Ip qui ont échangé des trames dans le fichier
     * @return l'indice auquel correspond l'adresse Ip source de cette trame
     */
    public int indiceSource(List<String> ipList){
        String source = getSourceIp();
        for(int i = 0; i < ipList.size(); i++){
            if(ipList.get(i).equals(source)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Utile pour créer le tableau qui permet de dessiner les flèches du graphqiue
     * @param ipList Une liste de Chaine de caractères contenant toutes les adresses Ip qui ont échangé des trames dans le fichier
     * @return l'indice auquel correspond l'adresse Ip destination de cette trame
     */
    public int indiceDestination(List<String> ipList){
        String destination = getDestinationIp();
        for(int i = 0; i < ipList.size(); i++){
            if(ipList.get(i).equals(destination)){
                return i;
            }
        }
        return -1;
    }

    public int getSourcePort(){
        return ipv4.getSourcePort();
    }

    public int getDestinationPort(){
        return ipv4.getDestinationPort();
    }

}
