package com.SBS.springbookseller.Service;

import com.SBS.springbookseller.DAO.Repositories.BookRepository;
import com.SBS.springbookseller.DAO.Repositories.CartRepository;
import com.SBS.springbookseller.DAO.Repositories.PurchaseHstryRepository;
import com.SBS.springbookseller.DAO.Repositories.UserRepository;
import com.SBS.springbookseller.DAO.entities.Book;
import com.SBS.springbookseller.DAO.entities.Cart;
import com.SBS.springbookseller.DAO.entities.PurchaseHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PurchaseHstryRepository purchaseHstryRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;



    @Override
    public Page<Book> searchByTitle(String word, int size, int page) {

        return bookRepository.findByTitleContainingIgnoreCase(word, PageRequest.of(page, size));
    }

    @Override
    public Book getBookById(Long Id) {
        return bookRepository.findById(Id).orElse(null);
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }


    @Override
    public Book saveBookWithImage(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book deleteBook(Book book) {
        Book book1 = book;
        bookRepository.delete(book);
        return book1;
    }

    @Override
    public Book deleteBook(Long IdBook) {
        Book book1 = bookRepository.findById(IdBook).orElse(null);
        List<Cart> carts = cartRepository.findAll();
        for (Cart c:carts){
            c.getBooks().remove(book1);
        }
        bookRepository.deleteById(IdBook);
        return book1;
    }

    @Override
    public Cart AddToCart(Long IdBook, Long userId) {
        if(cartRepository.findByUserId(userId).isPresent()){
            Cart cart = cartRepository.findByUserId(userId).get();
            Book book= bookRepository.findById(IdBook).get();
            cart.getBooks().add(book);
            return cartRepository.save(cart);
        }
        else {
            Cart cart = new Cart(null,userRepository.findById(userId).get(),List.of(bookRepository.findById(IdBook).get()));
            return cartRepository.save(cart);
        }
    }

    @Override
    public Cart getCart(Long userId) {
        return cartRepository.findByUserId(userId).get();
    }

    @Override
    public List<Book> deleteBookFromCart(Long bookid, Long userId) {
       Cart cart = cartRepository.findByUserId(userId).get();
        cart.getBooks().remove(bookRepository.findById(bookid).get());
        return cartRepository.save(cart).getBooks();
    }



    @Override
    public List<Book> getAllBooksByAuthor(String Author) {
        return bookRepository.findAllByAuthor(Author);
    }
}
