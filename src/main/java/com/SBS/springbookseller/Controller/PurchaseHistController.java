package com.SBS.springbookseller.Controller;


import com.SBS.springbookseller.DAO.entities.Book;
import com.SBS.springbookseller.DAO.entities.PurchaseHistory;
import com.SBS.springbookseller.Service.BookService;
import com.SBS.springbookseller.Service.PurchaseHstryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/purchase")
public class PurchaseHistController {
    @Autowired
    PurchaseHstryService purchaseHstryService;

    @PostMapping
    ResponseEntity<?> savePurchase(@RequestParam("cartId") Long cartId ,
                                   @RequestBody Long userId){
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        System.out.println("---------------------------------------------");
        System.out.println(cartId);
        System.out.println("---------------------------------------------");
        return new ResponseEntity<>(purchaseHstryService.savePurchaseHistory(purchaseHistory, cartId,userId), HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<?> getAllPurchase(@RequestParam(name = "size" , defaultValue ="2")int size,
                                     @RequestParam(name = "page", defaultValue ="0") int page){
        return new ResponseEntity<>(purchaseHstryService.findAllPurchase(size,page), HttpStatus.OK);
    }


    @GetMapping("/user/{Id}")
    ResponseEntity<?> getAllPurchase(@PathVariable("Id")Long id,
                                     @RequestParam(name = "size" , defaultValue ="7")int size,
                                     @RequestParam(name = "page", defaultValue ="0") int page){
        return new ResponseEntity<>(purchaseHstryService.findPurchasedItemByUserId(id, page,size),HttpStatus.OK);
    }




}
