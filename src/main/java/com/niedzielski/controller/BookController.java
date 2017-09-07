package com.niedzielski.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.niedzielski.exception.BookRentedByUserException;
import com.niedzielski.exception.CopyUnavailableException;
import com.niedzielski.exception.UserNotExistException;
import com.niedzielski.model.Book;
import com.niedzielski.service.BookService;

@RestController
@RequestMapping("api")
public class BookController {

	private final BookService bookService;

	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping(value = "books")
	public List<Book> list() {
		return bookService.getAllBooks();
	}

	@PostMapping(value = "books")
	public Book create(@RequestBody Book book) {
		return bookService.addBook(book);
	}

	@GetMapping(value = "books/{id}")
	public Book get(@PathVariable Long id) {
		return bookService.getBook(id);
	}

	@PutMapping(value = "books/{id}")
	public Book update(@PathVariable Long id, @RequestBody Book book) {
		
		return bookService.updateBook(id, book);
	}

	@DeleteMapping(value = "books/{id}")
	public Book delete(@PathVariable Long id) {
		Book existingBook = bookService.getBook(id);
		bookService.deleteBook(id);
		return existingBook;
	}

	@PutMapping(value = "books/rent/{isbn}")
	public Book rentBook(@PathVariable Long isbn, @RequestParam(name = "username") String username)
			throws CopyUnavailableException, BookRentedByUserException, UserNotExistException {
		return bookService.rentBook(isbn, username);
	}

	@PutMapping(value = "books/return/{isbn}")
	public Book returnBook(@PathVariable Long isbn, @RequestParam(name = "username") String username)
			throws UserNotExistException {
		return bookService.returnBook(isbn, username);
	}
}
