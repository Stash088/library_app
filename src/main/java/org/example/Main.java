package org.example;
import io.javalin.Javalin;
import org.example.book.BookController;
import org.example.utils.HealthCheckController;
import static io.javalin.apibuilder.ApiBuilder.*;


public class Main {
    public static void main(String[] args) {
        var app = Javalin.create(javalinConfig -> {
            javalinConfig.jetty.defaultPort = 7070;
            javalinConfig.router.apiBuilder(() -> {
                get("/", ctx -> ctx.result("Hello Javalin"));
                path("api/health", () -> {
                    get(HealthCheckController::healthCheck);
                });
                path("api/books", () -> {
                        get(BookController::GetAllBooks);
                    path("/search", () -> {
                            get(BookController::searchBooks);
                        });
                    path("/{id}", () -> {
                        get(BookController::fetchByID);
                        put(BookController::updateBookByID);
                    });
                });
            });
        }).start();
    }
}