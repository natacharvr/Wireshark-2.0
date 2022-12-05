public class Http implements CoucheApplication {
    private String ligne1, host;

    public static boolean isHttp(String http){
        
        String httpAscii = asciiToString(http);
        // System.out.println(httpAscii);
        return (httpAscii.contains("HTTP") || httpAscii.contains("http"));
    }

    public static String asciiToString(String http){
        String httpAscii = "";

        for (int i = 0; i < http.length()-1; i+=2){
            int c = Integer.parseInt(""+ http.charAt(i) + http.charAt(i+1), 16);
            httpAscii += Character.toString(c);
        }
        return httpAscii;
    }

    public Http(String contenu){
        String ascii = asciiToString(contenu);
        String[] splitAscii = ascii.split("\n");
        ligne1 = splitAscii[0];
        if (splitAscii[1].contains("Host")){
            host = splitAscii[1].split("host")[0];
        } else {
            host = "";
        }
    }

    public String toString(){
        return ligne1 + " " + host;
    }
    
}
