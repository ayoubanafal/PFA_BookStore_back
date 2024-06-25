package com.SBS.springbookseller.Service;

import com.SBS.springbookseller.DAO.Repositories.CartRepository;
import com.SBS.springbookseller.DAO.Repositories.PurchaseHstryRepository;
import com.SBS.springbookseller.DAO.Repositories.UserRepository;
import com.SBS.springbookseller.DAO.entities.Role;
import com.SBS.springbookseller.DAO.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    PurchaseHstryRepository purchaseHstryRepository;

    @Override
    public Page<User> getAllUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page,size));
    }
    @Override
    public User saveUser(User user){
        user.setPassword(user.getPassword());
        user.setRole(Role.USER);
        user.setCreatedAt(new Date());
        User SavedUser = userRepository.save(user);
        return SavedUser;
    }
    @Override
    public User findByUsername(String Username){
        return userRepository.findByUsername(Username).orElse(null);
    }
    @Override
    public Optional<User> MakeAdmin(User user){
        return userRepository.updateUser(user.getUsername(),Role.ADMIN);
    }

    @Override
    public User deleteUser(User user) {
        User user1 = user;
        userRepository.delete(user);

        return user1;
    }

    @Override
    public User deleteUser(Long userId) {
        User user1 = userRepository.findById(userId).orElse(null);
        System.out.println("------------------------h-----------------------");
        System.out.println(userId);

        if(cartRepository.findByUserId(user1.getId()).isPresent());
        {
            cartRepository.delete(cartRepository.findByUserId(user1.getId()).get());
        }

        if(!purchaseHstryRepository.findAllByUser(user1).isEmpty()){

            purchaseHstryRepository.deleteAll(purchaseHstryRepository.findAllByUser(user1));
        }

        System.out.println("------------------------hh-----------------------");
        System.out.println(userId);
        //purchaseHstryRepository.deleteAllByUserId(user1.getId());
        userRepository.deleteById(userId);
        System.out.println("------------------------hhh-----------------");
        System.out.println(userId);

        return user1;
    }

    @Override
    public User UpdateEmail(Long Id, String email) {
        if(userRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("Email Already Exists!");
        }
        User user = userRepository.findById(Id).get();
        user.setEmail(email);
        System.out.println(user);
        return  userRepository.save(user);
    }

}
