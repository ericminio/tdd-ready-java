package run;

import data.Sokoban;
import data.UsersKeeper;
import domain.User;
import http.routing.Router;
import http.Server;

import java.sql.Connection;

public class Launcher {

    public static void main(String[] args) throws Exception {
        Sokoban.please().usePostgresqlDatabase().clearTables();
        try (Connection connection = Sokoban.please().getConnection()) {
            UsersKeeper users = new UsersKeeper(connection);
            users.save(new User("tecuser", "e276f2f92e1a5a85c6695895d51943530bd0edd6"));
        }
        int port = Integer.parseInt( args[ 0 ] );
        Server server = new SunServer(port);
        server.useRouter( Router.routing( new LoginRoutes() ));
        server.start();
    }
}
