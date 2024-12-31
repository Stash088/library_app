package org.example.book;
import io.javalin.http.Context;
import org.example.utils.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    public void getAllBooks(Context ctx) {
        try {
            int pageSize = ctx.queryParamAsClass("pageSize", Integer.class).getOrDefault(10);
            int pageNumber = ctx.queryParamAsClass("pageNumber", Integer.class).getOrDefault(1);
            List<Book> books = bookService.getAllBooks(pageSize, pageNumber);
            ctx.json(books);
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", "Internal Server Error"));
        }
    }

    public void fetchByID(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Book book = bookService.fetchBookById(id);
            ctx.json(bookService.bookToMap(book));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("error", "Invalid ID format"));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("error", e.getMessage()));
        } catch (NotFoundException e) {
            ctx.status(404).json(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Server Error fetching book: ", e);
            ctx.status(500).json(Map.of("error", "Internal Server Error"));
        }
    }

    public void updateBookByID(Context ctx) {
        try {
            int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
            String contentType = ctx.header("Content-Type");
            if (contentType == null || !contentType.contains("application/json")) {
                ctx.status(400).json(Map.of("error", "Invalid Content-Type"));
                return;
            }
            Book bookUpdate = ctx.bodyAsClass(Book.class);
            Book updatedBook = bookService.updateBookById(id, bookUpdate);
            ctx.json(bookService.bookToMap(updatedBook));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("error", "Invalid ID format"));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("error", e.getMessage()));
        } catch (NotFoundException e) {
            ctx.status(404).json(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Server Error updating book: ", e);
            ctx.status(500).json(Map.of("error", "Internal Server Error"));
        }
    }

    public void searchBooks(Context ctx) {
        try {
            Map<String, String> searchParams = new HashMap<>();
            ctx.queryParamMap().forEach((key, values) -> {
                if (!values.isEmpty()) {
                    searchParams.put(key, values.get(0));
                }
            });

            List<Book> books = bookService.searchBooks(searchParams);

            if (!books.isEmpty()) {
                List<Map<String, Object>> response = books.stream()
                    .map(bookService::bookToMap)
                    .collect(Collectors.toList());
                ctx.json(response);
            } else {
                ctx.status(404).json(Map.of("message", "No books found matching the criteria"));
            }
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Server Error during book search: ", e);
            ctx.status(500).json(Map.of("error", "Internal Server Error"));
        }
    }
}