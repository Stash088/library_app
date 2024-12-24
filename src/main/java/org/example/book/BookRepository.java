package org.example.book;
import org.example.utils.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
            logger.info(query);
            logger.info(book.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Books with id {} not found", id);
        }
        return book;
    }
// Метод обновления книги по id 
public static Optional<Book> updateBookByID(int id, Book updatedBook) {
    String query = "UPDATE Books SET title = ?, author = ?, isbn = ?, genre = ?, copies_count = ?, status = ? WHERE id = ?";
    try (Connection connection = Database.getDataSource().getConnection();
         PreparedStatement pstmt = connection.prepareStatement(query)) {
        pstmt.setString(1, updatedBook.getTitle());
        pstmt.setString(2, updatedBook.getAuthor());
        pstmt.setString(3, updatedBook.getIsbn());
        pstmt.setString(4, updatedBook.getGenre());
        pstmt.setInt(5, updatedBook.getCopiesCount());
        pstmt.setString(6, updatedBook.getStatus());
        pstmt.setInt(7, id);
        int affectedRows = pstmt.executeUpdate();
        logger.info(query);
        logger.info(updatedBook.toString());
        if (affectedRows > 0) {
            // Если обновление прошло успешно, возвращаем обновленную книгу
            updatedBook.setId(id);
            return Optional.of(updatedBook);
        }
        else {
            // Если книга не была найдена или не обновлена
            return Optional.empty();
        }
    }
    catch (SQLException e) {
        e.printStackTrace();
        logger.error("Book with id {} not updated", id);
        return Optional.empty();
    }
}
public static List<Book> search(String title, String author, String isbn, String genre, Integer quantity, String status) {
    List<Book> books = BookRepository.getBooks(50, 1);
    
    // Предварительная обработка параметров
    final String titleLower = title != null ? title.toLowerCase() : null;
    final String authorLower = author != null ? author.toLowerCase() : null;
    final String genreLower = genre != null ? genre.toLowerCase() : null;
    final Set<String> statusSet = status != null ? 
        Arrays.stream(status.toLowerCase().split(",")).map(String::trim).collect(Collectors.toSet()) : 
        null;

    return books.parallelStream()
            .filter(book -> titleLower == null || book.getTitle().toLowerCase().contains(titleLower))
            .filter(book -> authorLower == null || book.getAuthor().toLowerCase().contains(authorLower))
            .filter(book -> isbn == null || book.getIsbn().equals(isbn))
            .filter(book -> genreLower == null || book.getGenre().toLowerCase().contains(genreLower))
            .filter(book -> quantity == null || book.getCopiesCount() == quantity)
            .filter(book -> statusSet == null || statusSet.isEmpty() || statusSet.contains(book.getStatus().toLowerCase()))
            .collect(Collectors.toList());
}
}