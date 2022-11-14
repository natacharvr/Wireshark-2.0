import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ipv4 {
    private String contenu; //La chaine de caractère contenant tout ce qui suit l'entête de la trame
    private int version; //La chaine de caractère extraite de contenu qui contient la version
    private int headerLength; //L'entier extrait de contenu qui correspond à la taille de l'entête/5
    private int size; // La taille de l'entête ipv4
    private int identifier;
    private String sourceIp;
    private String destinationIp;
    private String protocol;
    private int TTL; //Time to live
    private Tcp tcp = null; //Si la trame est de de protocol tcp


    /**
     * @return le Time to Live de la trame
     */
    public int getTTL() {
        return TTL;
    }

    /**
     * @return la version ipv4 de la trame
     */
    public int getVersion() {
        return version;
    }

    /**
     * @return la taille de l'entête/5
     */
    public int getHeaderLength() {
        return headerLength;
    }

    /**
     * @return la taille de la trame Ipv4
     */
    public int getSize() {
        return size;
    }

    /**
     * @return L'identifiant de la trame ipv4
     */
    public int getIdentifier() {
        return identifier;
    }

    /**
     * @return l'adresse Ip source de la trame Ipv4
     */
    public String getSourceIp() {
        return sourceIp;
    }

    /**
     * @return l'adresse Ip destination de la trame Ipv4
     */
    public String getDestinationIp() {
        return destinationIp;
    }

    /**
     * @return Le protocol de la trame ipv4
     */
    public String getProtocol() {
        return protocol;
    }

    public Ipv4(String s){
        contenu = s;
        version();
        headerLength();
        size();
        identifier();
        sourceIp();
        destinationIp();
        protocol();
        TTL();

        //En fonction du protocol on initialise les attributs correspondants
        switch(protocol){
            case "06" :
                tcp = new Tcp(s.substring(headerLength * 3 , s.length() -1));
                break;
        }

    }

    /**
     * Coupe la string contenu pour récupérer la version, et la transforme en int
     */
    public void version(){
        version = Integer.parseInt("" + contenu.charAt(0));
    }

    /**
     * Coupe la string contenu pour récupérer la headerLength, et la transforme en int
     */
    public void headerLength(){
        headerLength = Integer.parseInt("" + contenu.charAt(1)) * 4;
    }

    /**
     * Coupe la string contenu pour récupérer la taille, et la transforme en int
     */
    public void size(){
        String res = contenu.substring(6, 11);
        res = res.replace(" ", "");
        size = Integer.parseInt(res, 16);
    }
    
    /**
     * Récupère la sous chaine contenant l'identifiant de la trame et la transforme en int, en convertissant depuis la base 16
     */
    public void identifier(){
        String res = contenu.substring(12, 17);
        res = res.replace("\s", "");
        identifier = Integer.parseInt(res, 16);
    }

    /**
     * Récupère le time to live de la trame
     */
    public void TTL(){
        TTL = Integer.parseInt(contenu.substring(24, 26), 16);
    }

    /**
     * Récupère le protocol de la trame (notamment si c'est du tcp ou non)
     */
    public void protocol(){
        protocol = contenu.substring(27, 29);
    }


    /**
     * Récupère l'adresse Ip source, la convertit en base 10 et l'assemble pour avoirdes points entre les nombres
     */
    public void sourceIp(){
        String res = contenu.substring(36, 47);
        Pattern p = Pattern.compile("(..) (..) (..) (..)"); //Permet de récupérer un tableau de taille 5 avec les motifs qui correspondent (un motif est entre parenthèses)
        Matcher m = p.matcher(res);
        if(m.find()){
            res = "";
            for (int i = 1; i < 4; i ++){ //On itère à partir de 1 car la case 0 contient toute la string qui matche le pattern
                res += Integer.parseInt(m.group(i), 16) + ".";
            }
            res += Integer.parseInt(m.group(4), 16);
        }
        sourceIp = res;
    }

    /**
     * Récupère l'adresse Ip destination, la convertit en base 10 et l'assemble pour avoirdes points entre les nombres
     */
    public void destinationIp(){
        String res = contenu.substring(48, 59);
        Pattern p = Pattern.compile("(..) (..) (..) (..)");
        Matcher m = p.matcher(res);
        if(m.find()){
            res = "";
            for (int i = 1; i < 4; i ++){
                res += Integer.parseInt(m.group(i), 16) + ".";
            }
            res += Integer.parseInt(m.group(4), 16);

        }
        destinationIp = res;
    }

    public String toString(){
        String res = "sourceIp: " + sourceIp + "\n";
        res += "destinationIp: " + destinationIp + "\n";
        res += "version: " + version + "\n";
        res += "headerLength: " + headerLength + "\n";
        res += "size: " + size + "\n";
        res += "identifier: " + identifier + "\n";
        res += "protocol: " + protocol + "\n";
        res += "TTL: " + TTL + "\n";
        res += tcp.toString();
        return res;
    }

    public int getSourcePort(){
        if (tcp != null)
        return tcp.getSourcePort();
        return -1;
    }

    public int getDestinationPort(){
        if (tcp != null)
        return tcp.getDestinationPort();
        return -1;
    }

}
