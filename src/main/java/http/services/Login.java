package http.services;

import data.Sokoban;
import data.UsersRepository;
import domain.User;
import http.HttpRequest;
import http.HttpResponse;

import java.sql.Connection;

public class Login implements Endpoint {

    @Override
    public HttpResponse handle(HttpRequest request) throws Exception {
        HttpResponse response = new HttpResponse();
        response.headers.put("content-type", "application/json");
        String login = request.getValueOf("user");
        String password = request.getValueOf("password");
        User user = new User(login, password);
        try (Connection connection = Sokoban.please().getConnection()) {
            UsersRepository users = new UsersRepository(connection);
            response.code = users.isAuthorized(user) ? 200 : 401;
        }
        response.body = "{}";

        return response;
    }
}
