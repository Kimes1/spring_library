package com.niedzielski.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niedzielski.exception.CopyUnavailableException;
import com.niedzielski.model.Book;
import com.niedzielski.model.Book.Status;
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
		return bookRepository.saveAndFlush(book);
	}

	public Book rentBook(Long isbn, String username) throws CopyUnavailableException {
		Book existingBook = bookRepository.findOneByIsbnAndStatus(isbn, Status.AVAILABLE);
		User existingUser = userRepository.findOneByUsername(username);
		LocalDate rentalDate = LocalDate.now();

		if (existingBook == null) {
			throw new CopyUnavailableException("Book is not available");
		}
		existingBook.setUser(existingUser);
		existingBook.setRentalDate(rentalDate);
		existingBook.setReturnDate(rentalDate.plusMonths(1));
		existingBook.setStatus(Status.NON_AVAILABLE);
		existingUser.addBookToUserList(existingBook);

		return bookRepository.saveAndFlush(existingBook);

	}

	public Book returnBook(Long isbn, String username) {
		User existingUser = userRepository.findOneByUsername(username);
		Book existingBook = bookRepository.findOneByIsbnAndUser(isbn, existingUser);

		existingBook.setStatus(Status.AVAILABLE);
		existingBook.setRentalDate(null);
		existingBook.setReturnDate(null);
		existingUser.removeBookFromUserList(existingBook);

		return bookRepository.saveAndFlush(existingBook);
	}
}
