package run;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import data.ConnectionProvider;
import http.HttpRequest;
import http.HttpResponse;
import http.routing.Router;
import http.Server;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SunServer implements Server {

    private HttpServer server;
    private ConnectionProvider connectionProvider;
    private Router router;

    public SunServer(int port) throws IOException {
        server = HttpServer.create( new InetSocketAddress( port ), 0 );
    }

    @Override
    public void useDatabase(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public void useRouter(Router router) {
        this.router = router;
    }

    public void start() throws IOException {
        server.createContext( "/", exchange -> {
            handle( exchange );
        } );
        server.start();
    }

    public void stop() {
        server.stop(0);
    }

    private void handle(HttpExchange exchange) throws IOException {
        HttpRequest request = buildRequest( exchange );
        HttpResponse response = router.firstEndpointMatching( request ).handle( request );
        sendResponse( exchange, response );
    }

    private HttpRequest buildRequest(HttpExchange exchange) {
        HttpRequest request = new HttpRequest();
        request.query = exchange.getRequestURI().getRawQuery();
        request.path = exchange.getRequestURI().getPath();
        return request;
    }

    private void sendResponse(HttpExchange exchange, HttpResponse response) throws IOException {
        response.headers.forEach( (header, value) -> exchange.getResponseHeaders().add( header, value ) );
        exchange.sendResponseHeaders( response.code, 0 );
        exchange.getResponseBody().write( response.body.getBytes() );
        exchange.close();
    }
}
