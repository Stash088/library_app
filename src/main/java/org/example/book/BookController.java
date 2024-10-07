package org.example.book;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;


public class BookController {
    public static void GetAllBooks  (Context ctx) {
        BookDao dao = BookDao.instance();
        List<Book> allBooks = dao.getAllBooks();
        for (Book book : allBooks) {
            if (book.getAuthor() == null) {
                System.out.println("Warning: Book with null data: " + book);
            }
        }
        ctx.json(allBooks);
    };

    public static void fetchByID  (Context ctx){
        try {
            int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
            System.out.println("Fetching book with ID: " + id);
            BookDao dao = BookDao.instance();
            Optional<Book> book = dao.getBookByID(id);
            if (book.isPresent()) {
                System.out.println("Book found: " + book.get());
                ctx.json(book.get());
            } else {
                System.out.println("Book not found for ID: " + id);
                ctx.status(404).html("Book Not Found");
            }
        }
        catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("error", "Invalid ID format"));
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).json(Map.of("error", "Internal Server Error: " + e.getMessage()));
        }
    };

    public static void updateBookByID(Context ctx) {
        try {
            int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
            if (id <= 0) {
                ctx.status(400).json(Map.of("error", "Invalid ID"));
                return;
            }

            String title;
            String genre;
            String author;

            // Проверяем Content-Type
            String contentType = ctx.header("Content-Type");
            if (contentType != null && contentType.contains("application/json")) {
                // Обработка JSON (raw data)
                Book bookUpdate = ctx.bodyAsClass(Book.class);
                title = bookUpdate.getTitle();
                genre = bookUpdate.getGenre();
                author = bookUpdate.getAuthor();
            } else {
                // Обработка form-data
                title = ctx.formParam("title");
                genre = ctx.formParam("genre");
                author = ctx.formParam("author");
            }

            if (title == null || genre == null || author == null) {
                ctx.status(400).json(Map.of("error", "Missing required parameters"));
                return;
            }
            System.out.println("Updating book with ID: " + id);
            BookDao dao = BookDao.instance();
            Optional<Book> book = dao.updateBookByID(id, title, genre, author);
            if (book.isPresent()) {
                System.out.println("Book updated: " + book.get());
                ctx.status(200).json(book.get());
            } else {
                System.out.println("Book not found for ID: " + id);
                ctx.status(404).json(Map.of("error", "Book Not Found"));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("error", "Invalid ID format"));
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).json(Map.of("error", "Internal Server Error: " + e.getMessage()));
        }
    }

    public static void createBook(Context ctx) {
        try {
            String title;
            String genre;
            String author;

            // Проверяем Content-Type
            String contentType = ctx.header("Content-Type");
            if (contentType != null && contentType.contains("application/json")) {
                // Обработка JSON (raw data)
                Book bookUpdate = ctx.bodyAsClass(Book.class);

                // Log for debugging
                System.out.println("Received JSON: " + bookUpdate);

                title = bookUpdate.getTitle();
                genre = bookUpdate.getGenre();
                author = bookUpdate.getAuthor();
            } else {
                // Обработка form-data
                title = ctx.formParam("title");
                genre = ctx.formParam("genre");
                author = ctx.formParam("author");
            }

            // Check if any of the parameters are null or empty
            if (title == null || genre == null || author == null || title.isEmpty() || genre.isEmpty() || author.isEmpty()) {
                ctx.status(400).json(Map.of("error", "Missing required parameters"));
                return;
            }
            BookDao dao = BookDao.instance();
            Optional<Book> book = dao.createBook(title, genre, author);
            if (book.isPresent()) {
                // Log the book data before creation
                System.out.println("Book created: " + book.get());
                ctx.status(200).json(book.get());
            } else {
                ctx.status(404).json(Map.of("error", "Book Not Found"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).json(Map.of("error", "Internal Server Error: " + e.getMessage()));
        }
    }
}

