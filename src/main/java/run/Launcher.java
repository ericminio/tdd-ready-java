package run;

import http.Server;

public class Launcher {

    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt( args[ 0 ] );
        Server server = new Server(port);
        server.start();
    }
}
