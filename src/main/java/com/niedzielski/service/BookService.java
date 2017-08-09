package com.niedzielski.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niedzielski.exception.CopyUnavailableException;
import com.niedzielski.model.Book;
import com.niedzielski.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public List<Book> getAll() {
		return bookRepository.findAll();
	}

	public Book create(Book book) {
		return bookRepository.saveAndFlush(book);
	}

	public Book get(Long id) {
		return bookRepository.findOne(id);
	}

	public Book update(Long id, Book book) {
		Book existingBook = bookRepository.findOne(id);
		BeanUtils.copyProperties(book, existingBook);
		return bookRepository.saveAndFlush(existingBook);
	}

	public Book delete(Long id) {
		Book existingBook = bookRepository.findOne(id);
		bookRepository.delete(existingBook);
		return existingBook;
	}

	public Book addBook(Book book) {
		Book existingBook = bookRepository.findOneByIsbn(book.getIsbn());
		if (existingBook == null) {
			return bookRepository.saveAndFlush(book);
		}
		existingBook.incNumberOfCopies();
		return bookRepository.saveAndFlush(existingBook);
	}

	public Book lendBook(Long id) throws CopyUnavailableException {
		Book existingBook = bookRepository.findOne(id);
		if (existingBook.getNumberOfCopies() > 0) {
			existingBook.decNumberOfCopies();
			return bookRepository.saveAndFlush(existingBook);
		}
		throw new CopyUnavailableException("Book is not available");
	}
}
