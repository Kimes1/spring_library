package com.niedzielski.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.niedzielski.model.Book;
import com.niedzielski.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public List<Book> getAll() {
		return bookRepository.findAll();
	}

	public Book create(@RequestBody Book book) {
		return bookRepository.saveAndFlush(book);
	}

	public Book get(@PathVariable Long id) {
		return bookRepository.findOne(id);
	}

	public Book update(@PathVariable Long id, @RequestBody Book book) {
		Book existingBook = bookRepository.findOne(id);
		BeanUtils.copyProperties(book, existingBook);
		return bookRepository.saveAndFlush(existingBook);
	}

	public Book delete(@PathVariable Long id) {
		Book existingBook = bookRepository.findOne(id);
		bookRepository.delete(existingBook);
		return existingBook;
	}
}
