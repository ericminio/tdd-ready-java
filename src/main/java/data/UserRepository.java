package data;

import domain.User;

public class UserRepository {

    private ConnectionProvider connectionProvider;

    public UserRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void save(User user) {

    }
}
