package org.example.book;

import java.util.*;
import java.util.stream.Collectors;

import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            int id = Integer.parseInt(ctx.pathParam("id"));
            if (id < 0) {
                ctx.status(400).json(Map.of("error", "Invalid ID: must be non-negative"));
                logger.error("Invalid negative ID: {}", id);
                return;
            }
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
            logger.error("Server Error fetching book: ", e);
            ctx.status(500).json(Map.of("error", "Internal Server Error"));
        }
    }


    public static void updateBookByID(Context ctx) {
        try {
            int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
            if (id <= 0) {
                ctx.status(400).json(Map.of("error", "Invalid ID"));
                logger.error("Invalid ID");
                return;
            }
            Book bookUpdate = null;
            String contentType = ctx.header("Content-Type");
            if (contentType != null && contentType.contains("application/json")) {
                bookUpdate = ctx.bodyAsClass(Book.class);
            } else {
                logger.error("Invalid Content-Type");
                ctx.status(400).json(Map.of("error", "Invalid Content-Type"));
                return;
            }
            Optional<Book> updatedBook = BookRepository.updateBookByID(id, bookUpdate);
            if (updatedBook.isPresent()) {
                logger.info("Book updated: " + updatedBook.get());
                ctx.status(200);
            } else {
                logger.error("Book not found for ID: " + id);
                ctx.status(404).json(Map.of("error", "Book Not Found"));
            }
        } catch (NumberFormatException e) {
            logger.error("Invalid ID format");
            ctx.status(400).json(Map.of("error", "Invalid ID format"));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Internal Server Error");
            ctx.status(500).json(Map.of("error", "Internal Server Error: " + e.getMessage()));
        }
    }
    public static void searchBooks(Context ctx) {
        try {
            Map<String, String> searchParams = new HashMap<>();
            // Получаем все параметры запроса
            ctx.queryParamMap().forEach((key, values) -> {
                if (!values.isEmpty()) {
                    searchParams.put(key, values.get(0));
                }
            });

            Integer copiesCount = null;
            if (searchParams.containsKey("copies_count")) {
                try {
                    copiesCount = Integer.parseInt(searchParams.get("copies_count"));
                    if (copiesCount < 0) {
                        ctx.status(400).json(Map.of("error", "Invalid copies count: must be non-negative"));
                        logger.error("Invalid negative copies count: {}", copiesCount);
                        return;
                    }
                } catch (NumberFormatException e) {
                    ctx.status(400).json(Map.of("error", "Invalid copies count format"));
                    logger.error("Invalid copies count format: {}", searchParams.get("copies_count"));
                    return;
                }
            }

            logger.debug("Searching for books with parameters: {}", searchParams);

            List<Book> books = BookRepository.search(
                searchParams.get("title"),
                searchParams.get("author"),
                searchParams.get("isbn"),
                searchParams.get("genre"),
                copiesCount,
                searchParams.get("status")
            );

            if (!books.isEmpty()) {
                List<Map<String, Object>> response = books.stream()
                    .map(book -> {
                        Map<String, Object> bookMap = new HashMap<>();
                        bookMap.put("title", book.getTitle());
                        bookMap.put("author", book.getAuthor());
                        bookMap.put("isbn", book.getIsbn());
                        bookMap.put("genre", book.getGenre());
                        bookMap.put("copies_count", book.getCopiesCount());
                        bookMap.put("status", book.getStatus());
                        return bookMap;
                    })
                    .collect(Collectors.toList());
                
                ctx.json(response);
                logger.debug("Found {} books matching the search criteria", books.size());
            } else {
                ctx.status(404).json(Map.of("message", "No books found matching the criteria"));
                logger.info("No books found for the given search criteria");
            }
        } catch (Exception e) {
            logger.error("Server Error during book search: ", e);
            ctx.status(500).json(Map.of("error", "Internal Server Error"));
        }
    }
}