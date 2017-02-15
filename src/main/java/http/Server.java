package http;

import data.ConnectionProvider;
import http.routing.Router;

public interface Server {

    void useRouter(Router router);

    void useDatabase(ConnectionProvider connectionProvider);

    void start() throws Exception;

    void stop() throws Exception;
}
