package org.example.book;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {
    @JsonProperty("id")
    int id;
    @JsonProperty("title")
    String title;
    @JsonProperty("author")
    String author;
    @JsonProperty("publication_date")
    String publication_date;
    @JsonProperty("isbn")
    String isbn;
    @JsonProperty("genre")
    String genre;
    @JsonProperty("copies_count")
    int copies_count;
    @JsonProperty("status")
    String status;



    // Конструктор с ID (например, для работы с базой данных)
    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    // Конструктор с основными полями
    public Book(int id, String title, String author, String genre, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
    }
        // Полный конструктор со всеми полями
    public Book(int id, String title, String author, String isbn, String genre, int copies_count, String status) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.isbn = isbn;
            this.genre = genre;
            this.copies_count = copies_count;
            this.status = status;
    }

    // Полный конструктор со всеми полями
    public Book(int id, String title, String author, String publication_date,
                String isbn, String genre, int copies_count, String status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publication_date = publication_date;
        this.isbn = isbn;
        this.genre = genre;
        this.copies_count = copies_count;
        this.status = status;
    }


    // Конструктор копирования
    public Book(Book other) {
        this.id = other.id;
        this.title = other.title;
        this.author = other.author;
        this.publication_date = other.publication_date;
        this.isbn = other.isbn;
        this.genre = other.genre;
        this.copies_count = other.copies_count;
        this.status = other.status;
    }

    // Конструктор для создания книги с указанием количества экземпляров
    public Book(String title, String author, int copies_count) {
        this.title = title;
        this.author = author;
        this.copies_count = copies_count;
    }

    // Конструктор для быстрого создания книги с статусом
    public Book(String title, String author, String status) {
        this.title = title;
        this.author = author;
        this.status = status;
    }
    public Book(String title, String author, String isbn, String genre, int copies_count, String status) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.copies_count = copies_count;
        this.status = status;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setCopiesCount(int copies_count) {
        this.copies_count = copies_count;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPublicationDate(String publication_date) {
        this.publication_date = publication_date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }


    public int getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public String getPublicationDate() {
        return publication_date;
    }

    public String getIsbn() {
        return  isbn;
    }


    public int getCopiesCount() {
        return copies_count;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publication_date=" + publication_date +
                ", isbn='" + isbn + '\'' +
                ", genre='" + genre + '\'' +
                ", copies_count=" + copies_count +
                ", status='" + status + '\'' +
                '}';
    }

}


