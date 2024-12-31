package org.example.book;

import org.example.utils.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;



public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    public List<Book> getAllBooks(int pageSize, int pageNumber) {
        List<Book> books = BookRepository.getBooks(pageSize, pageNumber);
        for (Book book : books) {
            if (book.getAuthor() == null) {
                logger.warn("Warning: Book with null data: {}", book);
            }
        }
        return books;
    }

    public Book fetchBookById(int id) throws IllegalArgumentException {
        if (id < 0) {
            logger.error("Invalid negative ID: {}", id);
            throw new IllegalArgumentException("Invalid ID: must be non-negative");
        }
        logger.debug("Fetching book with id: {}", id);
        Book book = BookRepository.findByID(id);
        if (book == null) {
            logger.error("Book with id {} not found", id);
            throw new NotFoundException("Book Not Found");
        }
        logger.debug("Book found: {}", book);
        return book;
    }

    public Map<String, Object> bookToMap(Book book) {
        Map<String, Object> response = new HashMap<>();
        response.put("title", book.getTitle());
        response.put("author", book.getAuthor());
        response.put("isbn", book.getIsbn());
        response.put("genre", book.getGenre());
        response.put("copies_count", book.getCopiesCount());
        response.put("status", book.getStatus());
        return response;
    }

    public Book updateBookById(int id, Book bookUpdate) throws IllegalArgumentException {
        if (id <= 0) {
            logger.error("Invalid ID: {}", id);
            throw new IllegalArgumentException("Invalid ID");
        }
        Optional<Book> updatedBook = BookRepository.updateBookByID(id, bookUpdate);
        if (updatedBook.isPresent()) {
            logger.info("Book updated: {}", updatedBook.get());
            return updatedBook.get();
        } else {
            logger.error("Book not found for ID: {}", id);
            throw new NotFoundException("Book Not Found");
        }
    }

    public List<Book> searchBooks(Map<String, String> searchParams) {
        Integer copiesCount = null;
        if (searchParams.containsKey("copies_count")) {
            try {
                copiesCount = Integer.parseInt(searchParams.get("copies_count"));
                if (copiesCount < 0) {
                    throw new IllegalArgumentException("Invalid copies count: must be non-negative");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid copies count format");
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

        if (books.isEmpty()) {
            logger.info("No books found for the given search criteria");
        } else {
            logger.debug("Found {} books matching the search criteria", books.size());
        }

        return books;
    }
}