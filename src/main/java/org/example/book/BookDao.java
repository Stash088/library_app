package org.example.book;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookDao {
    private static BookDao bookDao = null;
    List<Book> books;

    private BookDao() {
        this.books = BookRepository.getBooks();
    }
    static BookDao instance() {
        if (bookDao == null) {
            bookDao = new BookDao();
        }
        return bookDao;
    }


    Optional<Book> getBookByID(int id){
        return  books.stream()
                .filter(u -> u.id == id)
                .findFirst();
    }
    public List<Book> getAllByAuthor(String author) {
        List<Book> bookList = BookRepository.getBooks();
        return bookList.stream()
                .filter(book -> book.getAuthor().equals(author))
                .collect(Collectors.toList());
    }

    public List<Book> getAllByGenre(String genre) {
        List<Book> bookList = BookRepository.getBooks();
        return bookList.stream()
                .filter(book -> book.getAuthor().equals(genre))
                .collect(Collectors.toList());
    }

    public List<Book> getAllBooks(){
        List<Book> bookList = BookRepository.getBooks();
        return bookList;
    }

    public Optional<Book> updateBookByID(int id, String newTitle, String newGenre, String newAuthor) {
        List<Book> books = getAllBooks();
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.getId() == id) {
                Book updatedBook = new Book(id, newTitle, newGenre, newAuthor);
                books.set(i, updatedBook);
                books.remove(book);
                return Optional.of(updatedBook);
            }
        }
        return Optional.empty();
    }

    public Optional <Book> createBook(String newTitle, String newGenre, String newAuthor){
        List<Book> books = getAllBooks();
        Book new_book = new Book(newTitle ,newAuthor , newGenre);
        books.add(new_book);
        return Optional.of(new_book);
    }

}
