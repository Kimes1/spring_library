package com.niedzielski.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niedzielski.exception.CopyUnavailableException;
import com.niedzielski.model.Book;
import com.niedzielski.service.BookService;

@RestController
@RequestMapping("api")
public class BookController {

	@Autowired
	private BookService bookService;

	@RequestMapping(value = "books", method = RequestMethod.GET)
	public List<Book> list() {
		return bookService.getAll();
	}

	@RequestMapping(value = "books", method = RequestMethod.POST)
	public Book create(@RequestBody Book book) {
		return bookService.addBook(book);
	}

	@RequestMapping(value = "books/{id}", method = RequestMethod.GET)
	public Book get(@PathVariable Long id) {
		return bookService.get(id);
	}

	@RequestMapping(value = "books/{id}", method = RequestMethod.PUT)
	public Book update(@PathVariable Long id, @RequestBody Book book) {
		Book existingBook = bookService.get(id);
		BeanUtils.copyProperties(book, existingBook);
		return bookService.create(existingBook);
	}

	@RequestMapping(value = "books{id}", method = RequestMethod.DELETE)
	public Book delete(@PathVariable Long id) {
		Book existingBook = bookService.get(id);
		bookService.delete(id);
		return existingBook;
	}

	@RequestMapping(value = "books/lend/{id}", method = RequestMethod.PUT)
	public Book lendBook(@PathVariable Long id) throws CopyUnavailableException {
		return bookService.lendBook(id);
	}
}
