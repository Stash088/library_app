package org.example.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.book.Book;
import org.example.book.BookRepository;
import org.example.utils.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserRepository {
        private static final Logger logger = LoggerFactory.getLogger(BookRepository.class);
    public static List<Book> getUsers(int pageSize, int pageNumber) {
        List<Book> bookList = new ArrayList<>();
        int offset = (pageNumber - 1) * pageSize;
        String query = String.format("SELECT id, title, author, isbn, genre, copies_count, status " 
            + "FROM books LIMIT %d OFFSET %d", pageSize, offset);
        try (Connection connection = Database.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
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
                logger.info(book.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }
}
