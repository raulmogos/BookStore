package repository.database;

import models.Purchase;
import models.validation.ValidatorException;
import repository.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.sql.*;

public class DatabasePurchaseRepository implements Repository<Long, Purchase> {
    private static final String url = "jdbc:postgresql://localhost/BookStore";

    @Override
    public Optional findOne(Long o) {
        Connection conn;
        PreparedStatement stmt;

        try {
            Class.forName("com.postgresql.jdbc.Driver");

            conn = DriverManager.getConnection(url);
            String sql = "SELECT * FROM Purchases WHERE id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Math.toIntExact(o));

            ResultSet result = stmt.executeQuery();
            return Optional.of(new Purchase(o, (long) result.getInt("bookId"), (long) result.getInt("clientId"), result.getDate("lastModified").toLocalDate()));
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
            String sql = "SELECT * FROM Purchases";
            ResultSet result = stmt.executeQuery(sql);

            ArrayList<Purchase> books = new ArrayList<>();
            while (result.next()) {
                long id = (long) result.getInt("id");
                long bookId = result.getInt("bookId");
                long clientId = result.getInt("clientId");
                LocalDate date = result.getDate("lastModified").toLocalDate();

                books.add(new Purchase(id, bookId, clientId, date));
            }
            return books;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<Purchase>();
    }

    @Override
    public Optional save(Purchase entity) throws ValidatorException {
        Connection conn;
        PreparedStatement stmt;

        try {
            Class.forName("com.postgresql.jdbc.Driver");

            conn = DriverManager.getConnection(url);
            String sql = "INSERT INTO Purchases(id, bookId, clientId, lastModified) VALUES(?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Math.toIntExact(entity.getId()));
            stmt.setInt(2, Math.toIntExact(entity.getBookId()));
            stmt.setInt(3, Math.toIntExact(entity.getClientId()));
            stmt.setDate(4, Date.valueOf(entity.getLastModifiedDateTime()));

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
            String sql = "DELETE FROM Books WHERE id=?";
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
    public Optional update(Book entity) throws ValidatorException {
        Connection conn;
        PreparedStatement stmt;

        try {
            Class.forName("com.postgresql.jdbc.Driver");

            conn = DriverManager.getConnection(url);
            String sql = "UPDATE Books SET title=?, author=?, price=? WHERE id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, entity.getTitle());
            stmt.setString(2, entity.getAuthor());
            stmt.setInt(3, entity.getPrice());
            stmt.setInt(4, Math.toIntExact(entity.getId()));

            stmt.executeUpdate();
            return Optional.empty();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(entity);
    }
}