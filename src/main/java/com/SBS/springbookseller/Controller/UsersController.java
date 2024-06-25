package com.SBS.springbookseller.Controller;
import com.SBS.springbookseller.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    @Autowired
    UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    ResponseEntity<?> getAllUsers(@RequestParam(name = "size" , defaultValue ="2")int size,
                                  @RequestParam(name = "page", defaultValue ="0") int page){
        return new ResponseEntity<>(userService.getAllUsers(page, size),HttpStatus.OK);
    }


    @PutMapping("/updateEmail/{id}")
    ResponseEntity<?> updateEmail(@PathVariable("id")Long userId, @RequestBody String email){
        return new ResponseEntity<>(userService.UpdateEmail(userId,email),HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteMyAccount(@PathVariable("id")Long userId){
        return new ResponseEntity<>(userService.deleteUser(userId),HttpStatus.OK);
    }


}
