package org.example;
import io.javalin.Javalin;
//import org.example.book.BookController;
import io.javalin.apibuilder.ApiBuilder.*;
import org.example.book.BookController;
import org.example.utils.HealthCheckController;
import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {
    public static void main(String[] args) {
        var app = Javalin.create(javalinConfig -> {
            javalinConfig.jetty.defaultPort = 7070;
            javalinConfig.useVirtualThreads = true;
            javalinConfig.router.apiBuilder(() -> {
                get("/", ctx -> ctx.result("Hello Javalin"));
                path("api/health", () -> {
                    get(HealthCheckController::healthCheck);
                });
                path("api/books", () -> {
                    get(BookController::GetAllBooks);
                    post("/create", BookController::createBook);
                    path("/{id}", () -> {
                        get(BookController::fetchByID);
                        patch(BookController::updateBookByID);
                    });
                });
            });
        }).start();
    }
}