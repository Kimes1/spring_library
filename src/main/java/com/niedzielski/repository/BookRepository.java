package com.niedzielski.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.niedzielski.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
