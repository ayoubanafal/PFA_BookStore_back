package com.SBS.springbookseller.Service;

import com.SBS.springbookseller.DAO.entities.Book;
import com.SBS.springbookseller.DAO.entities.Cart;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {
    Page<Book> searchByTitle(String word, int size, int page);
    Book getBookById(Long Id);
    List<Book> getAllBooksByAuthor(String Author);
    Book saveBook(Book book);
    Book updateBook(Book book);
    Book deleteBook(Book book);
    Book deleteBook(Long IdBook);

    Cart AddToCart(Long IdBook, Long userId);

    Cart getCart(Long userId);

    List<Book> deleteBookFromCart(Long bookId, Long userId);

    Book saveBookWithImage(Book book);
}
