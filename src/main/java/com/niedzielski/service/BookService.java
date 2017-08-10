package com.niedzielski.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niedzielski.exception.CopyUnavailableException;
import com.niedzielski.model.Book;
import com.niedzielski.model.User;
import com.niedzielski.repository.BookRepository;
import com.niedzielski.repository.UserRepository;

@Service
public class BookService {

	private final BookRepository bookRepository;
	private final UserRepository userRepository;

	@Autowired
	public BookService(BookRepository bookRepository, UserRepository userRepository) {
		this.bookRepository = bookRepository;
		this.userRepository = userRepository;
	}

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public Book createBook(Book book) {
		return bookRepository.saveAndFlush(book);
	}

	public Book getBook(Long id) {
		return bookRepository.findOne(id);
	}

	public Book updateBook(Long id, Book book) {
		Book existingBook = bookRepository.findOne(id);
		BeanUtils.copyProperties(book, existingBook);
		return bookRepository.saveAndFlush(existingBook);
	}

	public Book deleteBook(Long id) {
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

	public Book lendBook(Long id, Long userId) throws CopyUnavailableException {
		Book existingBook = bookRepository.findOne(id);
		User existingUser = userRepository.findOne(id);
		if (existingBook.getNumberOfCopies() > 0) {
			existingBook.decNumberOfCopies();
			existingBook.addUsers(existingUser);
			existingUser.addBooks(existingBook);
			return bookRepository.saveAndFlush(existingBook);
		}
		throw new CopyUnavailableException("Book is not available");
	}
}
