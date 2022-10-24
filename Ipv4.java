import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ipv4 {
    private String contenu;
    private int version;
    private int headerLength;
    private int size;
    private int identifier;
    private String sourceIp;
    private String destinationIp;
    private String protocol;
    private int TTL;
    private Tcp tcp;


    public int getTTL() {
        return TTL;
    }

    public int getVersion() {
        return version;
    }

    public int getHeaderLength() {
        return headerLength;
    }

    public int getSize() {
        return size;
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public String getDestinationIp() {
        return destinationIp;
    }

    public String getProtocol() {
        return protocol;
    }

    public Ipv4(String s){
        contenu = s;
        version(s);
        headerLength(s);
        size(s);
        identifier(s);
        sourceIp(s);
        destinationIp(s);
        protocol(s);
        TTL(s);

        switch(protocol){
            case "06" :
                tcp = new Tcp(s.substring(headerLength * 3 , s.length() -1));
                break;
        }

    }

    public void version(String contenu){
        version = Integer.parseInt("" + contenu.charAt(0));
    }

    public void headerLength(String contenu){
        headerLength = Integer.parseInt("" + contenu.charAt(1)) * 4;
    }

    public void size(String contenu){
        String res = contenu.substring(6, 11);
        res = res.replace(" ", "");
        size = Integer.parseInt(res, 16);
    }
    
    public void identifier(String contenu){
        String res = contenu.substring(13, 17); //METTRE LES BONS INDICES"
        res = res.replace("\s", "");
        identifier = Integer.parseInt(res, 16);
    }

    public void TTL(String contenu){
        TTL = Integer.parseInt(contenu.substring(24, 26), 16);
    }

    public void protocol(String contenu){
        protocol = contenu.substring(27, 29);
    }


    public void sourceIp(String contenu){
        String res = contenu.substring(36, 47);
        Pattern p = Pattern.compile("(..) (..) (..) (..)");
        Matcher m = p.matcher(res);
        if(m.find()){
            res = "";
            for (int i = 1; i < 4; i ++){
                res += Integer.parseInt(m.group(i), 16) + ".";
            }
            res += Integer.parseInt(m.group(4), 16);
        }
        sourceIp = res;
    }

    public void destinationIp(String contenu){
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
        return res;
    }

}
