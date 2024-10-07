package org.example.book;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Book {
    @JsonProperty("id")
    int id;
    @JsonProperty("title")
    String title;

    @JsonProperty("author")
    String author;

    @JsonProperty("genre")
    String genre;

    Book(){
        title = title;
        author = author;
        genre = genre;
    }

    Book(String title , String genre){
        this.title = title;
        this.genre = genre;
    }
    Book(int id, String title, String author, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    Book(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    Book(int id , String title , String author){
        this.id = id;
        this.title = title;
        this.author = author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Book{id=" + id + ", title='" + title + "', author='" + author + "', genre='" + genre + "'}";
    }
}


