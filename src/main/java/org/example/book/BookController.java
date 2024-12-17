package org.example.book;

import java.util.*;
import io.javalin.http.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.book.BookRepository.findByID;

public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    public static void GetAllBooks  (Context ctx) {
        try {
            int pageSize = ctx.queryParamAsClass("pageSize", Integer.class).getOrDefault(10);
            int pageNumber = ctx.queryParamAsClass("pageNumber", Integer.class).getOrDefault(1);
            List<Book> books = BookRepository.getBooks(pageSize,pageNumber);
            for (Book book : books) {
                if (book.getAuthor() == null) {
                    System.out.println("Warning: Book with null data: " + book);
                }
            }
            ctx.json(books);
        }
        catch (Exception e){
            ctx.status(500).json(Map.of("error", "Internal Server Error"));
        }
    }

    public static void fetchByID(Context ctx) {
        try {
            int id = Integer.parseInt((ctx.pathParam("id")));
            logger.debug("Fetching book with id: {}", id);
            Book book = BookRepository.findByID(id);
            if (book != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("title", book.getTitle());
                response.put("author", book.getAuthor());
                response.put("isbn", book.getIsbn());
                response.put("genre", book.getGenre());
                response.put("copies_count", book.getCopiesCount());
                response.put("status", book.getStatus());
                ctx.json(response);
                logger.debug("Book found and returned: {}", book);
            } else {
                ctx.status(404).json(Map.of("error", "Book Not Found"));
                logger.error("Book with id {} not found", id);
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("error", "Invalid ID format"));
            logger.error("Invalid ID format: {}", ctx.pathParam("id"));
        } catch (Exception e) {
            logger.error("Error fetching book: ", e);
            ctx.status(500).json(Map.of("error", "Internal Server Error"));
        }
    }

//    public static void fetchByAuthor(Context ctx) {
//        try {
//            String author = (Objects.requireNonNull(ctx.queryParam("author")));
//            BookDao dao = BookDao.instance();
//            Optional<Book> bookOptional = dao.getBookByAuthor(author);
//            if (bookOptional.isPresent()) {
//                Book book = bookOptional.get();
//                // Простой Map только с нужными полями
//                Map<String, String> response = Map.of(
//                        "id", String.valueOf(book.getId()),
//                        "title", book.getTitle()
//                );
//                ctx.json(response);
//            } else {
//                ctx.status(404).html("Book Not Found");
//            }
//        } catch (NumberFormatException e) {
//            ctx.status(400).json(Map.of("error", "Invalid author format"));
//        } catch (Exception e) {
//            ctx.status(500).json(Map.of("error", "Internal Server Error"));
//        }
//    }
//
//
//    public static void updateBookByID(Context ctx) {
//        try {
//            int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
//            if (id <= 0) {
//                ctx.status(400).json(Map.of("error", "Invalid ID"));
//                return;
//            }
//
//            String title;
//            String genre;
//            String author;
//
//            // Проверяем Content-Type
//            String contentType = ctx.header("Content-Type");
//            if (contentType != null && contentType.contains("application/json")) {
//                // Обработка JSON (raw data)
//                Book bookUpdate = ctx.bodyAsClass(Book.class);
//                title = bookUpdate.getTitle();
//                author = bookUpdate.getAuthor();
//            } else {
//                // Обработка form-data
//                title = ctx.formParam("title");
//                author = ctx.formParam("author");
//            }
//
//            if (title == null  || author == null) {
//                ctx.status(400).json(Map.of("error", "Missing required parameters"));
//                return;
//            }
//            System.out.println("Updating book with ID: " + id);
//            BookDao dao = BookDao.instance();
//            Optional<Book> book = dao.updateBookByID(id, title , author);
//            if (book.isPresent()) {
//                System.out.println("Book updated: " + book.get());
//                ctx.status(200).json(book.get());
//            } else {
//                System.out.println("Book not found for ID: " + id);
//                ctx.status(404).json(Map.of("error", "Book Not Found"));
//            }
//        } catch (NumberFormatException e) {
//            ctx.status(400).json(Map.of("error", "Invalid ID format"));
//        } catch (Exception e) {
//            e.printStackTrace();
//            ctx.status(500).json(Map.of("error", "Internal Server Error: " + e.getMessage()));
//        }
//    }
//
//    public static void createBook(Context ctx) {
//        try {
//            String title;String author;
//
//            // Проверяем Content-Type
//            String contentType = ctx.header("Content-Type");
//            if (contentType != null && contentType.contains("application/json")) {
//                // Обработка JSON (raw data)
//                Book bookUpdate = ctx.bodyAsClass(Book.class);
//
//                // Log for debugging
//                System.out.println("Received JSON: " + bookUpdate);
//
//                title = bookUpdate.getTitle();
//                author = bookUpdate.getAuthor();
//            } else {
//                // Обработка form-data
//                title = ctx.formParam("title");
//                author = ctx.formParam("author");
//            }
//
//            // Check if any of the parameters are null or empty
//            if (title == null  || author == null || title.isEmpty() || author.isEmpty()) {
//                ctx.status(400).json(Map.of("error", "Missing required parameters"));
//                return;
//            }
//            BookDao dao = BookDao.instance();
//            Optional<Book> book = dao.createBook(title, author);
//            if (book.isPresent()) {
//                // Log the book data before creation
//                System.out.println("Book created: " + book.get());
//                ctx.status(200).json(book.get());
//            } else {
//                ctx.status(404).json(Map.of("error", "Book Not Found"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            ctx.status(500).json(Map.of("error", "Internal Server Error: " + e.getMessage()));
//        }
//    }
}

