package repository.database;

import models.Client;
import models.validation.ValidatorException;
import repository.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.sql.*;

public class DatabaseClientRepository implements Repository<Long, Client> {
    private static final String url = "jdbc:postgresql://localhost/BookStore";

    @Override
    public Optional findOne(Long o) {
        Connection conn;
        PreparedStatement stmt;

        try {
            Class.forName("com.postgresql.jdbc.Driver");

            conn = DriverManager.getConnection(url);
            String sql = "SELECT * FROM Clients WHERE id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Math.toIntExact(o));

            ResultSet result = stmt.executeQuery();
            return Optional.of(new Client(o, result.getString("firstname"), result.getString("lastname"), result.getInt("spendings")));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Iterable findAll() {
        Connection conn;
        Statement stmt;

        try {
            Class.forName("com.postgresql.jdbc.Driver");

            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM Clients";
            ResultSet result = stmt.executeQuery(sql);

            ArrayList<Client> books = new ArrayList<>();
            while (result.next()) {
                long id = (long) result.getInt("id");
                String firstname = result.getString("firstname");
                String lastname = result.getString("lastname");
                int spendings = result.getInt("spendings");

                books.add(new Client(id, firstname, lastname, spendings));
            }
            return books;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<Client>();
    }

    @Override
    public Optional save(Client entity) throws ValidatorException {
        Connection conn;
        PreparedStatement stmt;

        try {
            Class.forName("com.postgresql.jdbc.Driver");

            conn = DriverManager.getConnection(url);
            String sql = "INSERT INTO Clients(id, firstname, lastname, spendings) VALUES(?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Math.toIntExact(entity.getId()));
            stmt.setString(2, entity.getFirstName());
            stmt.setString(3, entity.getLastName());
            stmt.setInt(4, entity.getMoneySpent());

            stmt.executeUpdate();
            return Optional.empty();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(entity);
    }

    @Override
    public Optional delete(Long o) {
        Connection conn;
        PreparedStatement stmt;

        try {
            Class.forName("com.postgresql.jdbc.Driver");

            conn = DriverManager.getConnection(url);
            String sql = "DELETE FROM Clients WHERE id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Math.toIntExact(o));

            stmt.executeUpdate();
            return Optional.empty();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(findOne(o));
    }

    @Override
    public Optional update(Client entity) throws ValidatorException {
        Connection conn;
        PreparedStatement stmt;

        try {
            Class.forName("com.postgresql.jdbc.Driver");

            conn = DriverManager.getConnection(url);
            String sql = "UPDATE Clients SET firstname=?, lastname=?, spendings=? WHERE id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setInt(3, entity.getMoneySpent());
            stmt.setInt(4, Math.toIntExact(entity.getId()));

            stmt.executeUpdate();
            return Optional.empty();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(entity);
    }
}
