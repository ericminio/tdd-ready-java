package run;

import http.routing.Router;
import http.Server;

public class Launcher {

    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt( args[ 0 ] );
        Server server = new SunServer(port);
        server.useRouter( Router.routing( new Routes() ));
        server.start();
    }
}
