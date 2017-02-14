package database.postgresql;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class ConnectionTest {

    @Test
    public void canConnectToLocalPostgresDatabase() throws SQLException {
        String url = "jdbc:postgresql:travis_ci_test";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password", System.getenv("POSTGRESQL_PASSWORD"));
        Connection connection = DriverManager.getConnection(url, props);

        assertThat(connection, not(equalTo(null)));
    }

    @Test
    public void canConnectToInMemoryDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:mymemdb", "SA", "");

        assertThat(connection, not(equalTo(null)));
    }
}
