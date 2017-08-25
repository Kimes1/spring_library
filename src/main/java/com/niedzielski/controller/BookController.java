package com.niedzielski.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
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

import com.niedzielski.exception.CopyUnavailableException;
import com.niedzielski.model.Book;
import com.niedzielski.service.BookService;
import com.niedzielski.service.UserService;

@RestController
@RequestMapping("api")
public class BookController {

	private final BookService bookService;
	private final UserService userService;

	@Autowired
	public BookController(BookService bookService, UserService userService) {
		this.bookService = bookService;
		this.userService = userService;
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
		Book existingBook = bookService.getBook(id);
		BeanUtils.copyProperties(book, existingBook);
		return bookService.createBook(existingBook);
	}

	@DeleteMapping(value = "books{id}")
	public Book delete(@PathVariable Long id) {
		Book existingBook = bookService.getBook(id);
		bookService.deleteBook(id);
		return existingBook;
	}

	@PutMapping(value = "books/lend/{id}")
	public Book lendBook(@PathVariable Long isbn, @RequestParam(name = "user") String userName)
			throws CopyUnavailableException {
		return bookService.rentBook(isbn, userName);
	}
}
