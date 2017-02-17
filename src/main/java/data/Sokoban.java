package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class Sokoban {

    private static Sokoban instance;
    private Properties props;
    private String url;

    public static Sokoban please() {
        if (instance == null) {
            instance = new Sokoban();
        }

        return instance;
    }

    private void execute(Connection connection, String sql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, props);
    }

    public Sokoban useInMemoryDatabase() throws SQLException {
        url = "jdbc:hsqldb:mem:mymemdb";
        props = new Properties();
        props.setProperty("user","SA");
        props.setProperty("password", "");
        props.setProperty("hsqldb.syntax_pgs", "true");
        createTables();
        return this;
    }

    public Sokoban usePostgresqlDatabase() throws SQLException {
        url = "jdbc:postgresql:travis_ci_test";
        props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password", System.getenv("POSTGRESQL_PASSWORD"));
        createTables();
        return this;
    }

    public void createTables() throws SQLException {
        try (Connection connection = getConnection()) {
            execute(connection, UsersRepository.CREATE_TABLE);
        }
    }

    public void clearTables() throws SQLException {
        try (Connection connection = getConnection()) {
            execute(connection, UsersRepository.TRUNCATE_TABLE);
        }
    }
}
