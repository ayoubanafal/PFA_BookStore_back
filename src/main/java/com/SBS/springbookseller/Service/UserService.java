package com.SBS.springbookseller.Service;

import com.SBS.springbookseller.DAO.entities.Role;
import com.SBS.springbookseller.DAO.entities.User;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Page<User> getAllUsers(int page, int size);

    User saveUser(User user);
    User findByUsername(String Username);
    Optional<User> MakeAdmin(User user);
    User deleteUser(User user);
    User deleteUser(Long userId);
    User UpdateEmail(Long Id, String email);

}
