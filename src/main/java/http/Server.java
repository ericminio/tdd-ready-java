package http;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    private int port;
    private HttpServer server;

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        server = HttpServer.create( new InetSocketAddress( port ), 0 );
        server.createContext( "/", exchange -> {
            String body = "<html>" +
                    "<head>" +
                    "<link rel=\"stylesheet\" href=\"/main.css\">" +
                    "</head>"
                    + "<body>"
                    + "<label id=\"greetings\">Hello World</label>"
                    + "</body>"
                    + "</html>";
            exchange.getResponseHeaders().add( "content-type", "text/html" );
            exchange.sendResponseHeaders( 200, body.length() );
            exchange.getResponseBody().write( body.getBytes() );
            exchange.close();
        } );
        server.createContext( "/main.css", exchange -> {
            String body = "#greetings { color: #00FF00; }";
            exchange.getResponseHeaders().add( "content-type", "text/css" );
            exchange.sendResponseHeaders( 200, body.length() );
            exchange.getResponseBody().write( body.getBytes() );
            exchange.close();
        } );
        server.start();
    }

    public void stop() {
        server.stop(0);
    }
}
