package data;

import domain.User;

import java.sql.*;

public class UsersRepository {

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users(login varchar(200), password varchar(200))";
    public static final String TRUNCATE_TABLE = "TRUNCATE TABLE users";
    private Connection connection;

    public UsersRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(User user) throws SQLException {
        String sql = "INSERT INTO users (login, password) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.login);
        statement.setString(2, user.password);
        statement.execute();
    }

    public boolean isAuthorized(User user) throws SQLException {
        String sql = "SELECT login FROM users WHERE login=? AND password=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.login);
        statement.setString(2, user.password);
        ResultSet result = statement.executeQuery();

        return result.next();
    }
}
