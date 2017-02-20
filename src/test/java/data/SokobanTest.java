package data;

import domain.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class SokobanTest {

    @Test
    public void createsTheTablesInMemoryDatabase() throws SQLException {
        Sokoban.please().useInMemoryDatabase();
        try (Connection connection = Sokoban.please().getConnection()) {
            String sql = "SELECT login FROM users";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeQuery();
        }
    }
    @Test
    public void offersAWayToClearTheTables() throws SQLException {
        Sokoban.please().useInMemoryDatabase();
        try (Connection connection = Sokoban.please().getConnection()) {
            UsersKeeper users = new UsersKeeper(connection);
            users.save(new User("known", "user"));
            Sokoban.please().clearTables();
            String sql = "SELECT login FROM users";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            assertThat(result.next(), equalTo(false));
        }
    }
    @Test
    public void createsTheTablesInLocalPostgresqlDatabase() throws SQLException {
        Sokoban.please().usePostgresqlDatabase();
        try (Connection connection = Sokoban.please().getConnection()) {
            String sql = "SELECT login FROM users";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeQuery();
        }
    }
    @Test
    public void offersAWayToClearTheTablesInPostgresqlDatabase() throws SQLException {
        Sokoban.please().usePostgresqlDatabase();
        try (Connection connection = Sokoban.please().getConnection()) {
            UsersKeeper users = new UsersKeeper(connection);
            users.save(new User("known", "user"));
            Sokoban.please().clearTables();
            String sql = "SELECT login FROM users";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            assertThat(result.next(), equalTo(false));
        }
    }

}
