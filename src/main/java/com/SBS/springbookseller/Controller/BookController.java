package com.SBS.springbookseller.Controller;


import com.SBS.springbookseller.DAO.entities.Book;
import com.SBS.springbookseller.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {
    @Autowired
    BookService bookService;


    @GetMapping("/getBooks")
    ResponseEntity<?> getBooks(@RequestParam(name = "Search", defaultValue ="")String kw,
                               @RequestParam(name = "size" , defaultValue ="2")int size,
                               @RequestParam(name = "page", defaultValue ="0") int page){
        return new ResponseEntity<>(bookService.searchByTitle(kw,size,page), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    ResponseEntity<?> saveBook(@RequestBody Book book){
        return new ResponseEntity<>(bookService.saveBook(book), HttpStatus.CREATED);
    }

    @PostMapping("/SaveImage")
    ResponseEntity<?> saveBookWithImage(@RequestBody Book book){
        return new ResponseEntity<>(bookService.saveBookWithImage(book), HttpStatus.CREATED);
    }

    @GetMapping("/getBooks/{id}")
    ResponseEntity<?> getBookById(@PathVariable("id") Long id){
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteBook(@PathVariable("id") Long id){
        return new ResponseEntity<>(bookService.deleteBook(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/updateU/{id}")
    ResponseEntity<?> updateBook(@PathVariable("id")Long id, @RequestBody Book book){
        book.setId(id);
        return new ResponseEntity<>(bookService.updateBook(book),HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateA/{id}")
    ResponseEntity<?> updateBooka(@PathVariable("id")Long id, @RequestBody Book book){
        book.setId(id);
        return new ResponseEntity<>(bookService.updateBook(book),HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @GetMapping("/Cart/{id}")
    ResponseEntity<?> Cart(@PathVariable("id") Long userId){
        return new ResponseEntity<>(bookService.getCart(userId), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @PostMapping("/addToCart/{id}")
    ResponseEntity<?> addToCart(@PathVariable("id")Long id,@RequestBody Long userId){
        return new ResponseEntity<>(bookService.AddToCart(id, userId), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @DeleteMapping("/cart/{userId}/{bookId}")
    ResponseEntity<?> deleteFromCart(@PathVariable("bookId") Long bookId,@PathVariable("userId") Long userId){
        return new ResponseEntity<>(bookService.deleteBookFromCart(bookId,userId), HttpStatus.OK);
    }






}
