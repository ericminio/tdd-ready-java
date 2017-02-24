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
            users.save(new User("tecuser", "9f3da7ab6093d9d8d74c3979ccadfc7b11b37f630a932ad8d522d836edc1f11e"));
        }
        int port = Integer.parseInt( args[ 0 ] );
        Server server = new SunServer(port);
        server.useRouter( Router.routing( new LoginRoutes() ));
        server.start();
    }
}
