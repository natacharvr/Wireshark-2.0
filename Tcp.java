public class Tcp implements CoucheTransport {
    private String contenu;
    private int sourcePort;
    private int destinationPort;
    private long sequenceNumber;
    private long acknowledgmentNumber;
    private int THL; //Transport Header Length (x4 octets)
    private int flagURG; //Pour les donnees prioritaires
    private int flagACK; //Si le champ AN est valide
    private int flagPSH; //Push sans attendre le remplissage complet de la trame
    private int flagRST; //Reinitialisation de la connexion
    private int flagSYN; //Ouverture de la connexion
    private int flagFIN; //Fermeture de connexion
    // private int window; //Taille des tampons libres en reception de l'emetteur du segment
    // private int checksum;
    // private int urgentPointer;
    // private String options;
    private CoucheApplication application = null;
    
    
    
    public Tcp(String s){
    	//Retire les espaces de la trame pour pouvoir l'analyser plus facilement
        contenu = s.replace(" ", "");
        sourcePort = extraction(0,4,16);
        destinationPort = extraction(4,8    ,16);
        sequenceNumber = Long.parseLong(contenu.substring(8,16),16);
        acknowledgmentNumber = Long.parseLong(contenu.substring(16,24),16);
        THL = extraction(24,25,16);
        flagURG = extraction(26,27) / 2; //Division par 2 pour extraire le second bit de poids faible
        flagACK = extraction(26,27) % 2; //%2 pour extraire le bit de poid faible de l'octet
        flagPSH = extraction(27,28) / 8;
        flagRST = (extraction(27,28) / 4) % 2; //On retire 2 bit à droite et un bit à gauche
        flagSYN = (extraction(27,28) / 2) % 2;
        flagFIN = extraction(27,28) % 2;
        // window = extraction(28,32,16);
        // checksum = extraction(32,36,16);
        // urgentPointer = extraction(36,39,16);
        // if (contenu.length() > 39)
        // options = contenu.substring(40);
        if (isHttp()){
            application = new Http(contenu.substring(THL*8));
        }
    }
    
    /*
     * Pour limiter la repetition de la ligne d'extraction complète
     */
    private int extraction(int debut, int fin) {
    	return Integer.parseInt(contenu.substring(debut,fin),16);
    }
    
    /*
     * Surcharge pour pouvoir specifier la base
     */
    private int extraction(int debut, int fin, int base) {
    	return Integer.parseInt(contenu.substring(debut, fin), base);
    }
    
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	// sb.append("sourcePort: " + sourcePort + "\n");
    	// sb.append("destinationPort: " + destinationPort + "\n");
    	// sb.append("THL: " + THL + "\n");
        if (flagURG == 1)
    	sb.append("<font color='red'> URG: </font>");
        if (flagACK == 1)
    	sb.append("<font color='red'> ACK </font>");
        if (flagPSH == 1)
    	sb.append("<font color='red'> PSH </font>");
        if (flagRST == 1)
    	sb.append("<font color='red'> RST </font>");
        if (flagSYN == 1)
    	sb.append("<font color='red'> SYN </font>");
        if (flagFIN == 1)
    	sb.append("<font color='red'> FIN </font>");
    	sb.append("Sequence Number: <font color='green'>" + sequenceNumber + "</font> ");
    	sb.append("Acknowledgment Number: <font color='green'>" + acknowledgmentNumber + " </font>");
    	// sb.append("Window: " + window + "\n");
    	// sb.append("Checksum: " + checksum + "\n");
    	// sb.append("Urgent Pointer: " + urgentPointer + "\n");
    	// sb.append("Options RAW: " + options + "\n");
        if (application != null){
            sb.append("<font color = 'orange'> http : </font>");
            sb.append(application.toString());
        }
    	return sb.toString();
    }

    public int getSourcePort(){
        return sourcePort;
    }

    public int getDestinationPort(){
        return destinationPort;
    }

    public boolean isHttp(){
        if (contenu.length() > THL*8){
            return Http.isHttp(contenu.substring(THL*8));
        }
        return false;
    }

}
