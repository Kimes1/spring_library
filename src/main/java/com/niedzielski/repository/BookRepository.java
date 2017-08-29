package com.niedzielski.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.niedzielski.model.Book;
import com.niedzielski.model.Book.Status;
import com.niedzielski.model.User;

public interface BookRepository extends JpaRepository<Book, Long> {

	public Book findOneByIsbn(Long isbn);

	public Book findOneByIsbnAndStatus(Long isbn, Status status);

	public Book findOneByIsbnAndUser(Long isbn, User user);

	public List<Book> findByStatus(Status status);

	public List<Book> findByIsbnAndStatus(Long isbn, Status status);

}
