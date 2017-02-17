package http;

import data.Sokoban;
import http.routing.Router;

public interface Server {

    void useRouter(Router router);

    void start() throws Exception;

    void stop() throws Exception;
}
