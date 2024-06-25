package com.SBS.springbookseller.DAO.Repositories;

import com.SBS.springbookseller.DAO.entities.Role;
import com.SBS.springbookseller.DAO.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String Email);
    @Modifying
    @Query("update User set role = :role where username = :username")
    Optional<User> updateUser(@Param("username") String Username, @Param("role")Role role);
}
