package org.example.dao;

import org.example.book.Book;
import org.example.book.BookRepository;

import java.util.List;
import java.util.Optional;


public class BookDao {
    private static BookDao bookDao = null;
    List<Book> books;

    private BookDao() {
        this.books = BookRepository.getBooks();
    }
    public static BookDao instance() {
        if (bookDao == null) {
            bookDao = new BookDao();
        }
        return bookDao;
    }

//
//    Optional<Book> getBookByID(int id){
//        Optional<Book> books = BookRepository.findByID(id);
//        return  books;
//    }
//    public List<Book> getAllByAuthor(String author) {
//        List<Book> bookList = BookRepository.getBooks();
//        return bookList.stream()
//                .filter(book -> book.getAuthor().equals(author))
//                .collect(Collectors.toList());
//    }
//
//    public Optional<Book> getBookByAuthor(String author){
//        Optional<Book> book = BookRepository.findByAuthor(author);
//        return  book;
//    }



    public List<Book> getAllBooks(){
        List<Book> bookList = BookRepository.getBooks();
        return bookList;
    }

    public Optional<Book> updateBookByID(int id, String newTitle, String newAuthor) {
        List<Book> books = getAllBooks();
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.getId() == id) {
                Book updatedBook = new Book(id, newTitle, newAuthor);
                books.set(i, updatedBook);
                books.remove(book);
                return Optional.of(updatedBook);
            }
        }
        return Optional.empty();
    }

//    public Optional <Book> createBook(String newTitle,  String newAuthor){
//        List<Book> books = getAllBooks();
//        Book new_book = new Book(newTitle ,newAuthor);
//        books.add(new_book);
//        return Optional.of(new_book);
//    }

}

