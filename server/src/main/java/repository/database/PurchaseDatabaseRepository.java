package repository.database;

import models.Purchase;
import models.validation.ValidatorException;
import repository.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.sql.*;

public class PurchaseDatabaseRepository implements Repository<Long, Purchase> {
    private static final String url = "jdbc:postgresql://localhost/BookStore";
    private static final String user = "postgres";
    private static final String password = "password";

    @Override
    public Optional findOne(Long o) {
        Connection conn;
        PreparedStatement stmt;

        try {

            conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM \"Purchases\" WHERE purchaseId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Math.toIntExact(o));

            ResultSet result = stmt.executeQuery();
            result.next();
            return Optional.of(new Purchase(o, (long) result.getInt("bookId"), (long) result.getInt("clientId"), result.getDate("lastModified").toLocalDate()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Iterable findAll() {
        Connection conn;
        Statement stmt;

        try {

            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM \"Purchases\"";
            ResultSet result = stmt.executeQuery(sql);

            ArrayList<Purchase> books = new ArrayList<>();
            while (result.next()) {
                long id = (long) result.getInt("purchaseId");
                long bookId = result.getInt("bookId");
                long clientId = result.getInt("clientId");
                LocalDate date = result.getDate("lastModified").toLocalDate();

                books.add(new Purchase(id, bookId, clientId, date));
            }
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<Purchase>();
    }

    @Override
    public Optional save(Purchase entity) throws ValidatorException {
        Connection conn;
        PreparedStatement stmt;

        try {

            conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO \"Purchases\"(purchaseId, bookId, clientId, lastModified) VALUES(?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Math.toIntExact(entity.getId()));
            stmt.setInt(2, Math.toIntExact(entity.getBookId()));
            stmt.setInt(3, Math.toIntExact(entity.getClientId()));
            stmt.setDate(4, Date.valueOf(entity.getLastModifiedDate()));

            stmt.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(entity);
    }

    @Override
    public Optional delete(Long o) {
        Connection conn;
        PreparedStatement stmt;

        try {

            conn = DriverManager.getConnection(url, user, password);
            String sql = "DELETE FROM \"Purchases\" WHERE purchaseId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Math.toIntExact(o));

            stmt.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(findOne(o));
    }

    @Override
    public Optional update(Purchase entity) throws ValidatorException {
        Connection conn;
        PreparedStatement stmt;

        try {

            conn = DriverManager.getConnection(url, user, password);
            String sql = "UPDATE \"Purchases\" SET title=?, author=?, price=? WHERE purchaseId=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(2, Math.toIntExact(entity.getBookId()));
            stmt.setInt(3, Math.toIntExact(entity.getClientId()));
            stmt.setDate(4, Date.valueOf(entity.getLastModifiedDate()));
            stmt.setInt(4, Math.toIntExact(entity.getId()));

            stmt.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(entity);
    }
}
