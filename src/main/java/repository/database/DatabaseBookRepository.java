package repository.database;

import models.Book;
import models.validation.ValidatorException;
import repository.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.sql.*;

public class DatabaseBookRepository implements Repository<Long, Book> {
    private static final String url = "jdbc:postgresql://localhost/BookStore";

    @Override
    public Optional findOne(Long o) {
        Connection conn;
        PreparedStatement stmt;

        try {
            Class.forName("com.postgresql.jdbc.Driver");

            conn = DriverManager.getConnection(url);
            String sql = "SELECT * FROM Books WHERE id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Math.toIntExact(o));

            ResultSet result = stmt.executeQuery();
            return Optional.of(new Book(o, result.getString("title"), result.getString("author"), result.getInt("price")));
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
            String sql = "SLECT * FROM Books";
            ResultSet result = stmt.executeQuery(sql);

            ArrayList<Book> books = new ArrayList<>();
            while (result.next()) {
                Long id = (long) result.getInt("id");
                String title = result.getString("title");
                String author = result.getString("author");
                int price = result.getInt("price");

                books.add(new Book(id, title, author, price));
            }
            return books;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<Book>();
    }

    @Override
    public Optional save(Book entity) throws ValidatorException {
        Connection conn;
        PreparedStatement stmt;

        try {
            Class.forName("com.postgresql.jdbc.Driver");

            conn = DriverManager.getConnection(url);
            String sql = "INSERT INTO Books(id, title, author, price) VALUES(?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Math.toIntExact(entity.getId()));
            stmt.setString(2, entity.getTitle());
            stmt.setString(3, entity.getAuthor());
            stmt.setInt(4, entity.getPrice());

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
