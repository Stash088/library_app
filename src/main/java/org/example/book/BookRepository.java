package org.example.book;
import org.example.utils.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    // Метод для получения всех книг
    private static final Logger logger = LoggerFactory.getLogger(BookRepository.class);
    public static List<Book> getBooks(int pageSize, int pageNumber) {
        List<Book> bookList = new ArrayList<>();
        int offset = (pageNumber - 1) * pageSize;
        String query = String.format("SELECT id, title, author, isbn, genre, copies_count, status " 
            + "FROM books LIMIT %d OFFSET %d", pageSize, offset);
        try (Connection connection = Database.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            int rowCount = 0;
            while (resultSet.next()) {
                int  id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String isnb = resultSet.getString("isbn");
                String genre = resultSet.getString("genre");
                int copies_count = resultSet.getInt("copies_count");
                String status = resultSet.getString("status");
                Book book = new Book(id,title, author , isnb ,genre, copies_count ,status);
                bookList.add(book);
                rowCount++;
            }
            logger.info("Total books retrieved: " + rowCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }
    //  Метод  получения  книги по id
    public static Book findByID(int id) {
        String query = String.format("SELECT title, author, isbn, genre, copies_count, status FROM Books WHERE id = %d", id);
        Book book = null;
        try (Connection connection = Database.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String isbn = resultSet.getString("isbn");
                String genre = resultSet.getString("genre");
                int copies_count = resultSet.getInt("copies_count");
                String status = resultSet.getString("status");
                book = new Book(id, title, author, isbn, genre, copies_count, status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Books with id {} not found", id);
        }
        return book;
    }
}