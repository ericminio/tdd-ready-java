package http;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Server {

    private int port;
    private HttpServer server;

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        server = HttpServer.create( new InetSocketAddress( port ), 0 );
        server.createContext( "/", exchange -> {
            String body = Files.readAllLines(Paths.get("build", "resources", "main", "index.html")).stream().collect(Collectors.joining("\n"));
            exchange.getResponseHeaders().add( "content-type", "text/html" );
            exchange.sendResponseHeaders( 200, body.length() );
            exchange.getResponseBody().write( body.getBytes() );
            exchange.close();
        } );
        server.createContext( "/main.css", exchange -> {
            String body = Files.readAllLines(Paths.get("build", "resources", "main", "main.css")).stream().collect(Collectors.joining("\n"));
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
