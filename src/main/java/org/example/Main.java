package org.example;
import io.javalin.Javalin;
//import org.example.book.BookController;
import io.javalin.apibuilder.ApiBuilder.*;
import org.example.book.BookController;

public class Main {


    public static void main(String[] args) {
        var app = Javalin.create(javalinConfig -> {
            javalinConfig.jetty.defaultPort = 7070;
            javalinConfig.useVirtualThreads = true;
                })
                .get("/", ctx -> ctx.result("Hello World"))
                .get("/books" ,BookController::GetAllBooks)
                .get("/books/{id}", BookController::fetchByID)
                .post("/books/create", BookController::createBook)
                .patch("/books/{id}", BookController::updateBookByID)
                .start(7070);

    }
}
