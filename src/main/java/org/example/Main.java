package org.example;

import io.javalin.Javalin;
import org.example.book.BookController;
import org.example.book.BookService;
import org.example.utils.HealthCheckController;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {
    public static void main(String[] args) {
        BookService bookService = new BookService();
        BookController bookController = new BookController(bookService);
        var app = Javalin.create(javalinConfig -> {
            javalinConfig.jetty.defaultPort = 7070;
            javalinConfig.router.apiBuilder(() -> {
                get("/", ctx -> ctx.result("Hello Javalin"));
                path("api/v1/", () -> {
                    path("/health", () -> {
                        get(HealthCheckController::healthCheck);
                });
                    path("/books", () -> {
                        get(bookController::getAllBooks);
                        path("/search", () -> {
                            get(bookController::searchBooks);
                    });
                        path("/{id}", () -> {
                            get(bookController::fetchByID);
                            put(bookController::updateBookByID);
                        path("/status", () -> {
                            get(bookController::getAllBooks);
                        });
                    });
                });
            });
        });
        }).start();
        }
}