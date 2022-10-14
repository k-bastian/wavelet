import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    String word = "";
    String key = "";
    ArrayList<String> list = new ArrayList<String>();
    ArrayList<String> searched = new ArrayList<String>();

    public String handleRequest(URI url) {
        System.out.println("Path: " + url.getPath());

        if (url.getPath().equals("/")) {
            return String.format("Search Engine");
        }
        else if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                word = parameters[1];
                list.add(word);
                return String.format("Word added: %s!", word);
            }
        }
        else if (url.getPath().contains("/search")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                key = parameters[1];
                for(String s : list) {
                    if(s.contains(key)) {
                        searched.add(s);
                    }
                }          
                
                ArrayList<String> noDuplicates = new ArrayList<String>();

                for(String s: searched) {
                    if (!noDuplicates.contains(s)) {
                        noDuplicates.add(s);
                    }
                }

                return String.format("Found %s in %s", key, noDuplicates);
            }
        }
        return "404 Not Found!";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
