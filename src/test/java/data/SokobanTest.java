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
            canCreateOneUser(connection);
        }
    }
    @Test
    public void createsTheTablesInLocalPostgresqlDatabase() throws SQLException {
        Sokoban.please().usePostgresqlDatabase();
        try (Connection connection = Sokoban.please().getConnection()) {
            canCreateOneUser(connection);
        }
    }
    @Test
    public void offersAWayToClearTheTables() throws SQLException {
        Sokoban.please().useInMemoryDatabase();
        try (Connection connection = Sokoban.please().getConnection()) {
            canCreateOneUser(connection);
            Sokoban.please().clearTables();
            verifyThatUsersIsNowEmpty(connection);
        }
    }
    @Test
    public void offersAWayToClearTheTablesInPostgresqlDatabase() throws SQLException {
        Sokoban.please().usePostgresqlDatabase();
        try (Connection connection = Sokoban.please().getConnection()) {
            canCreateOneUser(connection);
            Sokoban.please().clearTables();
            verifyThatUsersIsNowEmpty(connection);
        }
    }
    private void canCreateOneUser(Connection connection) throws SQLException {
        UsersKeeper users = new UsersKeeper(connection);
        users.save(new User("known", "user"));
        PreparedStatement statement = connection.prepareStatement(UsersKeeper.SELECT_USERS);
        ResultSet result = statement.executeQuery();

        assertThat(result.next(), equalTo(true));
    }
    private void verifyThatUsersIsNowEmpty(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UsersKeeper.SELECT_USERS);
        ResultSet result = statement.executeQuery();

        assertThat(result.next(), equalTo(false));
    }
}