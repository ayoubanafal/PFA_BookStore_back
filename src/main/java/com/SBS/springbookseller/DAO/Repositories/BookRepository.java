package com.SBS.springbookseller.DAO.Repositories;

import com.SBS.springbookseller.DAO.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {

    Page<Book> findByTitleContainingIgnoreCase(String word,Pageable pageable);

    List<Book> findAllByAuthor(String author);

}
