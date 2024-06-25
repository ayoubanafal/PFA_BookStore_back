package com.SBS.springbookseller.Service;

import com.SBS.springbookseller.DAO.Repositories.CartRepository;
import com.SBS.springbookseller.DAO.Repositories.PurchaseHstryRepository;
import com.SBS.springbookseller.DAO.Repositories.UserRepository;
import com.SBS.springbookseller.DAO.entities.Book;
import com.SBS.springbookseller.DAO.entities.Cart;
import com.SBS.springbookseller.DAO.entities.PurchaseHistory;
import com.SBS.springbookseller.DAO.entities.User;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PurchaseHstryServiceImpl implements PurchaseHstryService{

    @Autowired
    PurchaseHstryRepository pRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;

    @Override
    public PurchaseHistory savePurchaseHistory(PurchaseHistory purchaseHistory, Long cartId,Long userId) {
        Double price=0d;
        Cart cart =cartRepository.findById(cartId).get();
        User user = userRepository.findById(userId).get();
        Hibernate.initialize(cart.getBooks());
        purchaseHistory.setBooks(cart.getBooks());
        for(Book book:purchaseHistory.getBooks()) {
            price+= book.getPrice();
        }
        purchaseHistory.setPurchaseDate(new Date());
        purchaseHistory.setUser(user);
        cart.setBooks(null);
        cartRepository.save(cart);
        purchaseHistory.setPrice(price);
        return pRepository.save(purchaseHistory) ;
    }


    @Override
    public Page<PurchaseHistory> findPurchasedItemByUserId(Long userId, int size, int page) {
        return pRepository.findAllByUserId(userId, PageRequest.of(size,page));
    }

    @Override
    public Page<PurchaseHistory> findAllPurchase(int size, int page) {
        return pRepository.findAll(PageRequest.of(size,page));
    }
}
